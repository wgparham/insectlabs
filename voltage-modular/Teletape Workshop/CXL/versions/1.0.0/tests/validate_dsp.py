#!/usr/bin/env python3
"""Headless regression measurements for the CXL DSP classes in cxl.java."""
from __future__ import annotations

import json
import math
import pathlib
import re
import shutil
import subprocess
import sys
import tempfile

import numpy as np

ROOT = pathlib.Path(__file__).resolve().parents[1]
SOURCE = ROOT / "cxl.java"
OUT = pathlib.Path(tempfile.gettempdir()) / "cxl-dsp-headless"
JAVAC = pathlib.Path(r"C:\Program Files\Java\jdk-27\bin\javac.exe")
JAVA = pathlib.Path(r"C:\Program Files\Java\jdk-27\bin\java.exe")
FS = 48000


def extract_dsp(source: str) -> str:
    start = source.index("    private static final class DelayLine {")
    end = source.index("//[/user-code-and-variables]", start)
    return source[start:end]


def build_harness() -> None:
    source = SOURCE.read_text(encoding="utf-8")
    dsp = extract_dsp(source).replace(
        "        private int randomState;\n",
        "        private int randomState;\n        boolean ditherEnabled = true;\n        boolean shapeEnabled;\n        double shapingError;\n",
    ).replace(
        "        void reset() { randomState = seed; }",
        "        void reset() { randomState = seed; shapingError = 0.0; }",
    ).replace(
        "            double dither = random() - random();",
        "            double dither = ditherEnabled ? random() - random() : 0.0;\n            double quantizeInput = shapeEnabled ? input + 0.85 * shapingError : input;",
    ).replace(
        "            return Math.rint(input / step + dither) * step;",
        "            double output = Math.rint(quantizeInput / step + dither) * step;\n            if (shapeEnabled) shapingError = quantizeInput - output;\n            return output;",
    )
    harness = f'''import java.io.*;
import java.nio.file.*;
import java.util.*;
public final class CxlDspChecks {{
    private static final double SAMPLE_RATE = 48000.0;
    private static final int FS = 48000;
    private static final double CONTROL_SMOOTH = 1.0 - Math.exp(-1.0 / (0.010 * SAMPLE_RATE));
    private static final double MODE_SMOOTH = 1.0 - Math.exp(-1.0 / (0.025 * SAMPLE_RATE));
    private static double cleanState(double value) {{ return Math.abs(value) < 1e-20 ? 0.0 : value; }}
{dsp}
    private static void require(boolean ok, String message) {{ if (!ok) throw new AssertionError(message); }}
    private static void delayChecks() {{
        DelayLine d = new DelayLine(16);
        int found = -1;
        for (int n = 0; n < 10; n++) {{
            double y = d.process(n == 0 ? 1.0 : 0.0, 3);
            if (y != 0.0) found = n;
        }}
        require(found == 3, "integer delay tap expected sample 3, got " + found);
        d.clear();
        require(d.predelay(0.75, 0.0) == 0.75, "zero pre-delay must be transparent");
        d.clear();
        d.predelay(1.0, 0.0);
        require(d.predelay(0.0, 1.0) == 1.0, "one-sample pre-delay timing");
        d.clear();
        d.predelay(1.0, 0.5);
        require(d.predelay(0.0, 0.5) == 0.5, "fractional pre-delay interpolation");
        DelayLine longDelay = new DelayLine(12004);
        int delayedAt = -1;
        for (int n = 0; n < 12010; n++) {{
            double y = longDelay.predelay(n == 0 ? 1.0 : 0.0, 12000.0);
            if (y != 0.0) delayedAt = n;
        }}
        require(delayedAt == 12000, "250 ms pre-delay endpoint expected 12000 samples, got " + delayedAt);
    }}
    private static void allpassChecks() {{
        AllPass ap = new AllPass(142, 0.75, false);
        double energy = 0.0;
        for (int n = 0; n < 12000; n++) {{
            double y = ap.process(n == 0 ? 1.0 : 0.0, 0.0);
            energy += y * y;
            require(Double.isFinite(y), "all-pass produced non-finite sample");
        }}
        require(Math.abs(energy - 1.0) < 1e-8, "all-pass impulse energy=" + energy);
    }}
    private static void writeExciter() throws Exception {{
        double[] frequencies = {{1000.0, 5000.0, 9000.0, 12000.0, 17000.0}};
        try (PrintWriter w = new PrintWriter(Files.newBufferedWriter(Path.of("exciter.csv")))) {{
            Exciter e = new Exciter();
            for (double f : frequencies) {{
                e.clear();
                int length = FS;
                for (int n = 0; n < length; n++) {{
                    double x = 2.0 * Math.sin(2.0 * Math.PI * f * n / FS);
                    double y = e.process(x);
                    if (n >= FS / 4) w.printf(Locale.ROOT, "%.1f,%d,%.17g%n", f, n - FS / 4, y);
                }}
            }}
        }}
    }}
    private static void writeDecay() throws Exception {{
        double[] targets = {{0.8, 5.0, 3.0, 30.0}};
        try (PrintWriter w = new PrintWriter(Files.newBufferedWriter(Path.of("decay.csv")))) {{
            for (double target : targets) {{
                PlateTank tank = new PlateTank(); tank.configure(target, 0.5, true);
                for (int n = 0; n < 14 * FS; n++) {{
                    double x = n < 2 * FS ? 0.2 * Math.sin(2.0 * Math.PI * 500.0 * n / FS) : 0.0;
                    tank.process(x, x);
                    if (n % 8 == 0) w.printf(Locale.ROOT, "%.1f,%d,%.17g,%.17g%n", target, n, tank.wetLeft, tank.wetRight);
                    require(Double.isFinite(tank.wetLeft) && Double.isFinite(tank.wetRight), "tank non-finite");
                }}
            }}
        }}
    }}
    private static void stabilityChecks() {{
        PlateTank t = new PlateTank();
        double[][] settings = {{{{0.8, 0.0}}, {{0.8, 1.0}}, {{5.0, 0.0}}, {{5.0, 1.0}}, {{30.0, 0.0}}, {{30.0, 1.0}}}};
        for (double[] s : settings) {{
            t.clear(); t.configure(s[0], s[1], true);
            for (int n = 0; n < 4 * FS; n++) {{
                double in = n == 0 ? 4.0 : 0.0;
                t.process(in, -in);
                require(Double.isFinite(t.wetLeft) && Double.isFinite(t.wetRight), "unstable setting " + Arrays.toString(s));
                require(Math.abs(t.wetLeft) < 1e6 && Math.abs(t.wetRight) < 1e6, "unbounded tank at " + Arrays.toString(s));
            }}
        }}
    }}
    private static void writeConverter() throws Exception {{
        try (PrintWriter w = new PrintWriter(Files.newBufferedWriter(Path.of("converter.csv")))) {{
            for (double inverse : new double[] {{1.0 / 2047.0, 1.0 / 127.0}}) {{
                SteppedConverter c = new SteppedConverter(0x1234567);
                double square = 0.0, mean = 0.0; int count = 200000;
                for (int n = 0; n < count; n++) {{
                    double y = c.process(0.25, inverse);
                    if (n >= 1000) {{ double error = y - 0.25; square += error * error; mean += error; }}
                }}
                double used = count - 1000.0;
                w.printf(Locale.ROOT, "%.12g,%.12g,%.12g%n", inverse, Math.sqrt(square / used), mean / used);
                require(c.process(0.0, inverse) == 0.0, "converter must return digital zero for silence");
            }}
        }}
    }}
    private static void writeGrainVariants() throws Exception {{
        double[][] variants = {{{{1.0/127.0, 1.0/127.0}}, {{1.0/127.0, 1.0/127.0}},
                {{1.0/255.0, 1.0/255.0}}, {{1.0/180.0, 1.0/180.0}}, {{1.0/511.0, 1.0/511.0}}, {{1.0/127.0, 1.0/2047.0}},
                {{1.0/127.0, 1.0/127.0}}}};
        String[] names = {{"8bit_full_dither", "8bit_no_output_dither", "9bit_both", "8p5bit_both", "10bit_both", "8bit_input_12bit_output", "8bit_noise_shaped"}};
        try (PrintWriter w = new PrintWriter(Files.newBufferedWriter(Path.of("grain-variants.csv")))) {{
            for (int v = 0; v < variants.length; v++) {{
                SteppedConverter input = new SteppedConverter(0x34a21 + v);
                SteppedConverter output = new SteppedConverter(0x95713 + v);
                if (v == 1) output.ditherEnabled = false;
                if (v == 6) {{ input.shapeEnabled = true; output.shapeEnabled = true; }}
                for (int n = 0; n < FS; n++) {{
                    double x = 0.25 * Math.sin(2.0 * Math.PI * 1000.0 * n / FS);
                    double y = output.process(input.process(x, variants[v][0]), variants[v][1]);
                    w.printf(Locale.ROOT, "%s,%d,%.17g,%.17g%n", names[v], n, x, y);
                }}
            }}
        }}
    }}
    private static double cpuBenchmark() {{
        PlateTank t = new PlateTank(); t.configure(5.0, 0.5, true);
        for (int n = 0; n < FS; n++) t.process(Math.sin(n * 0.017) * 0.05, Math.cos(n * 0.013) * 0.05);
        long start = System.nanoTime();
        for (int n = 0; n < 5 * FS; n++) t.process(Math.sin(n * 0.017) * 0.05, Math.cos(n * 0.013) * 0.05);
        return (System.nanoTime() - start) / (double)(5 * FS);
    }}
    public static void main(String[] args) throws Exception {{
        delayChecks(); allpassChecks(); writeExciter(); writeDecay(); writeConverter(); writeGrainVariants(); stabilityChecks();
        double nsPerFrame = cpuBenchmark();
        Files.writeString(Path.of("cpu.txt"), String.format(Locale.ROOT, "%.3f%n", nsPerFrame));
        System.out.printf(Locale.ROOT, "PASS delay taps, zero/fractional/250ms pre-delay, all-pass energy, finite/stability sweep; PlateTank %.1f ns/frame (Java headless benchmark)%n", nsPerFrame);
    }}
}}
'''
    OUT.mkdir(exist_ok=True)
    (OUT / "CxlDspChecks.java").write_text(harness, encoding="utf-8")
    (OUT / "exciter.csv").unlink(missing_ok=True)
    (OUT / "decay.csv").unlink(missing_ok=True)
    (OUT / "converter.csv").unlink(missing_ok=True)
    (OUT / "cpu.txt").unlink(missing_ok=True)
    (OUT / "grain-variants.csv").unlink(missing_ok=True)
    subprocess.run([str(JAVAC), "-Xlint:all", "-Werror", "-d", str(OUT), str(OUT / "CxlDspChecks.java")], check=True)
    subprocess.run([str(JAVA), "-cp", str(OUT), "CxlDspChecks"], cwd=OUT, check=True)


def build_high_rate_exciter() -> None:
    source = SOURCE.read_text(encoding="utf-8")
    start = source.index("    private static final class Exciter {")
    brace = source.index("{", start)
    depth = 0
    end = brace
    for end in range(brace, len(source)):
        if source[end] == "{": depth += 1
        elif source[end] == "}":
            depth -= 1
            if depth == 0:
                end += 1
                break
    exciter = source[start:end]
    text = f'''import java.io.*; import java.nio.file.*; import java.util.*;
public final class CxlExciterHigh {{
    private static final double SAMPLE_RATE = 384000.0;
    private static double cleanState(double value) {{ return Math.abs(value) < 1e-20 ? 0.0 : value; }}
{exciter}
    public static void main(String[] args) throws Exception {{
        double[] frequencies = {{1000.0, 5000.0, 9000.0, 12000.0, 17000.0}};
        try (PrintWriter w = new PrintWriter(Files.newBufferedWriter(Path.of("exciter-high.csv")))) {{
            Exciter e = new Exciter();
            for (double f : frequencies) {{
                e.clear();
                for (int n = 0; n < 384000; n++) {{
                    double y = e.process(2.0 * Math.sin(2.0 * Math.PI * f * n / SAMPLE_RATE));
                    if (n >= 96000) w.printf(Locale.ROOT, "%.1f,%d,%.17g%n", f, n - 96000, y);
                }}
            }}
        }}
    }}
}}
'''
    java_file = OUT / "CxlExciterHigh.java"
    java_file.write_text(text, encoding="utf-8")
    subprocess.run([str(JAVAC), "-Xlint:all", "-Werror", "-d", str(OUT), str(java_file)], check=True)
    subprocess.run([str(JAVA), "-cp", str(OUT), "CxlExciterHigh"], cwd=OUT, check=True)


def analyze() -> dict:
    rows = np.loadtxt(OUT / "exciter.csv", delimiter=",")
    high_rows = np.loadtxt(OUT / "exciter-high.csv", delimiter=",")
    alias = {}
    for f in np.unique(rows[:, 0]):
        signal = rows[rows[:, 0] == f, 2]
        high = high_rows[high_rows[:, 0] == f, 2]
        # Reference uses the same exciter equations at 8x sample rate and a
        # Blackman-windowed low-pass before decimation to remove out-of-band energy.
        taps_count = 2049
        x = np.arange(taps_count) - (taps_count - 1) / 2
        cutoff = 20500.0 / (8 * FS)
        taps = 2 * cutoff * np.sinc(2 * cutoff * x) * np.blackman(taps_count)
        taps /= taps.sum()
        nfft = 1 << int(math.ceil(math.log2(len(high) + taps_count - 1)))
        convolved = np.fft.irfft(np.fft.rfft(high, nfft) * np.fft.rfft(taps, nfft), nfft)
        centered = convolved[taps_count // 2:taps_count // 2 + len(high)]
        reference = centered[::8]
        count = min(len(signal), len(reference))
        # Ignore filter startup and estimate waveform error relative to the
        # 8x-bandlimited reference. Includes aliasing plus small sample-rate-model error.
        signal = signal[256:count]
        reference = reference[256:count]
        error = signal - reference
        relative_error_db = 20 * math.log10(max(float(np.sqrt(np.mean(error * error))), 1e-30)
                                             / max(float(np.sqrt(np.mean(reference * reference))), 1e-30))
        alias[f"{int(f)}"] = {
            "native_vs_8x_bandlimited_error_db": round(relative_error_db, 2),
        }

    data = np.loadtxt(OUT / "decay.csv", delimiter=",")
    decay_results = {}
    for target in np.unique(data[:, 0]):
        rows_t = data[data[:, 0] == target]
        t = rows_t[:, 1] / FS
        rms = np.sqrt((rows_t[:, 2] ** 2 + rows_t[:, 3] ** 2) / 2.0)
        step = max(1, round(0.01 * FS / 8))
        bins_t, bins_rms = [], []
        for i in range(2 * FS // 8, len(rms) - step, step):
            bins_t.append(float(np.mean(t[i:i + step])))
            bins_rms.append(float(np.sqrt(np.mean(rms[i:i + step] ** 2))))
        bins_t = np.asarray(bins_t)
        bins_db = 20 * np.log10(np.maximum(bins_rms, 1e-30) / max(bins_rms[0], 1e-30))
        mask = (bins_db <= -5.0) & (bins_db >= -24.0)
        rt60, slope = None, None
        if mask.sum() >= 8:
            slope = float(np.polyfit(bins_t[mask], bins_db[mask], 1)[0])
            rt60 = -60.0 / slope if slope < 0 else None
        decay_results[f"{target:.1f}"] = {
            "measured_rt60_seconds": None if rt60 is None else round(rt60, 3),
            "fit_slope_db_per_second": None if slope is None else round(slope, 3),
            "fit_sample_count": int(mask.sum()),
        }
    variant_data = np.genfromtxt(OUT / "grain-variants.csv", delimiter=",", dtype=str)
    variant_results = {}
    for name in np.unique(variant_data[:, 0]):
        subset = variant_data[variant_data[:, 0] == name]
        reference = subset[:, 2].astype(float)
        output = subset[:, 3].astype(float)
        error = output - reference
        spectrum = np.abs(np.fft.rfft(error))
        output_spectrum = np.abs(np.fft.rfft(output))
        def pickup_lpf(values):
            filtered = np.empty_like(values)
            state = 0.0
            for i, value in enumerate(values):
                state += 0.591 * (value - state)
                filtered[i] = state
            return filtered
        pickup_error = pickup_lpf(output) - pickup_lpf(reference)
        bins = np.fft.rfftfreq(len(error), 1.0 / FS)
        nonharmonic = np.ones(len(bins), dtype=bool)
        nonharmonic[0] = False
        harmonic_bins = []
        for harmonic in range(1, 21):
            ix = int(round(harmonic * 1000.0 * len(error) / FS))
            nonharmonic[ix] = False
            if harmonic > 1:
                harmonic_bins.append(ix)
        variant_results[str(name)] = {
            "error_rms": round(float(np.sqrt(np.mean(error * error))), 8),
            "post_6p8khz_pickup_lpf_error_rms": round(float(np.sqrt(np.mean(pickup_error * pickup_error))), 8),
            "nonharmonic_error_rms": round(float(np.sqrt(np.sum(spectrum[nonharmonic] ** 2)) / len(error)), 8),
            "low_band_nonharmonic_error_rms_below_6khz": round(float(np.sqrt(np.sum(spectrum[nonharmonic & (bins < 6000)] ** 2)) / len(error)), 8),
            "high_band_nonharmonic_error_rms_above_8khz": round(float(np.sqrt(np.sum(spectrum[nonharmonic & (bins > 8000)] ** 2)) / len(error)), 8),
            "largest_spur_harmonic_dbc": round(20 * math.log10(max(float(np.max(output_spectrum[harmonic_bins])), 1e-30)
                                                                      / max(float(output_spectrum[1000]), 1e-30)), 2),
        }
    return {
        "sample_rate_hz": FS,
        "exciter_sample_rate_comparison_2x_input": alias,
        "exciter_comparison_note": "Error versus the same equations at 8x sample rate, low-pass filtered at 20.5 kHz then decimated. This is an alias proxy that also includes discrete-time envelope/filter differences; it is not a pure spectral alias measurement.",
        "stepped_converter": {
            "constant_input": 0.25,
            "noise_and_mean_error": np.loadtxt(OUT / "converter.csv", delimiter=",").tolist(),
            "rows": ["inverse_step", "rms_error", "mean_error"],
            "note": "Rows are the 12-bit then 8-bit relative granular steps; zero input is separately asserted to return exact digital zero.",
        },
        "8bit_noise_reduction_variants": {
            "tone": "1 kHz, 0.25 amplitude, one second, direct cascaded converter stages; this omits tank diffusion and is for relative comparison only",
            "metrics": variant_results,
        },
        "tank_decay": {
            "targets_and_measurements_seconds": decay_results,
            "fit_range_db": [-5, -24],
            "rms_window_ms": 10,
            "note": "Raw tank response, driven at 500 Hz for 2 s then released; excludes converter, exciter, and output pickup filters. 8-bit max is fitted over a 19 dB window because the 12 s captured tail cannot span 30 dB at the nominal 30 s setting.",
        },
        "plate_tank_cpu": {
            "java_ns_per_sample": float((OUT / "cpu.txt").read_text(encoding="utf-8").strip()),
            "estimated_percent_of_one_core_at_48khz": round(float((OUT / "cpu.txt").read_text(encoding="utf-8").strip()) * FS / 1e7, 2),
            "scope": "PlateTank.process only; excludes outer module callback and host. Desktop JIT benchmark, not a host guarantee.",
        },
    }


if __name__ == "__main__":
    build_harness()
    build_high_rate_exciter()
    results = analyze()
    (ROOT / "tests" / "DSP-RESULTS.json").write_text(json.dumps(results, indent=2) + "\n", encoding="utf-8")
    print(json.dumps(results, indent=2))
