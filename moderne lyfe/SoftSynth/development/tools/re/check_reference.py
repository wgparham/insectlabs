#!/usr/bin/env python3
"""Checks refrender's output and writes the reference renders.

Usage: check_reference.py refrender.exe SOFTSYNT.PRG file.prm outdir
"""
import cmath
import math
import os
import struct
import subprocess
import sys

HEADER = 0x538


def render(exe, prg, prm, path, *opts):
    r = subprocess.run([exe, prg, prm, path, *opts], capture_output=True, text=True)
    if r.returncode:
        raise SystemExit(f"refrender failed: {r.stdout}{r.stderr}")
    return r.stdout


def samples(path):
    with open(path, "rb") as f:
        d = f.read()
    n = (len(d) - HEADER) // 2
    return d, list(struct.unpack(f">{n}h", d[HEADER:]))


def level(x, freq, rate):
    """Amplitude of one frequency, by direct correlation with a Hann window."""
    n = len(x)
    z = sum(v * (0.5 - 0.5 * math.cos(2 * math.pi * i / n)) * cmath.exp(-2j * math.pi * freq * i / rate)
            for i, v in enumerate(x))
    return abs(z) * 4 / n


def main():
    exe, prg, prm, out = sys.argv[1:5]
    os.makedirs(out, exist_ok=True)
    p = lambda name: os.path.join(out, name)
    results = []

    def check(name, ok, detail=""):
        results.append(ok)
        print(f"{'PASS' if ok else 'FAIL'}  {name}{'  ' + detail if detail else ''}")

    render(exe, prg, prm, p("tbwerd1-seed1.sd"), "--seed", "1", "--wav", p("tbwerd1-seed1.wav"))
    render(exe, prg, prm, p("tbwerd1-seed1-raw.sd"), "--seed", "1", "--no-normalize")
    render(exe, prg, prm, p("tbwerd1-zero-raw.sd"), "--zero-random", "--no-normalize")
    render(exe, prg, prm, p("tmp-repeat.sd"), "--seed", "1")
    render(exe, prg, prm, p("tmp-seed2.sd"), "--seed", "2")
    render(exe, prg, prm, p("tbwerd1-buffer2048-raw.sd"), "--seed", "1", "--no-normalize", "--buffer", "0x1000")

    # Variants, because the sample patch only sounds waveforms 1 and 2.
    with open(prm, "rb") as f:
        original = f.read()

    def variant(name, wave=None, fm=True):
        m = bytearray(original)
        for i in range(32):
            if not fm:
                m[0x77CC + 2 * i] = 1
                m[0x77CD + 2 * i] = 0
            if wave is not None and i in (4, 7):  # P5 and P8
                m[956 * i + 1] = wave
        path = p(f"tmp-{name}.prm")
        with open(path, "wb") as f:
            f.write(m)
        return path

    for wave in (1, 2, 3, 4, 5):
        render(exe, prg, variant(f"w{wave}", wave), p(f"tbwerd1-wave{wave}-fm-raw.sd"), "--seed", "1", "--no-normalize")
        render(exe, prg, variant(f"w{wave}p", wave, False), p(f"tbwerd1-wave{wave}-plain-raw.sd"), "--seed", "1", "--no-normalize")
    nofm = variant("nofm", None, False)
    render(exe, prg, nofm, p("tmp-nofm.sd"), "--seed", "1", "--no-normalize")
    render(exe, prg, nofm, p("tmp-nofm-small.sd"), "--seed", "1", "--no-normalize", "--buffer", "0x1000")

    file1, norm = samples(p("tbwerd1-seed1.sd"))
    _, raw = samples(p("tbwerd1-seed1-raw.sd"))
    _, zero = samples(p("tbwerd1-zero-raw.sd"))
    file2, _ = samples(p("tmp-repeat.sd"))
    _, seed2 = samples(p("tmp-seed2.sd"))
    _, small = samples(p("tbwerd1-buffer2048-raw.sd"))
    _, nofm_full = samples(p("tmp-nofm.sd"))
    _, nofm_small = samples(p("tmp-nofm-small.sd"))

    check("same seed gives an identical file", file1 == file2)
    check("different seed gives different audio", norm != seed2,
          f"{sum(a != b for a, b in zip(norm, seed2))} of {len(norm)} samples differ")
    check("zero Random differs from seed 1", zero != raw)
    # The FM kernel 0xBC2E runs one extra step per buffer, the plain kernel 0xBD9E does not.
    check("without FM, buffer size changes nothing", nofm_full == nofm_small)
    first = next((i for i, (a, b) in enumerate(zip(raw, small)) if a != b), None)
    check("with FM, a 2048-sample buffer first differs at sample 2048", first == 2048, f"first difference {first}")

    rate = struct.unpack_from(">I", file1, 0x3FC)[0]
    nbytes = struct.unpack_from(">I", file1, 0xB8)[0]
    name = file1[0xFF:0xFF + file1[0xFE]].decode("latin1")
    check("file header", rate == 44100 and nbytes == 2 * len(norm) and struct.unpack_from(">H", file1)[0] == HEADER,
          f"rate {rate}, {nbytes} bytes, name {name!r}")

    peak = max(abs(v) for v in raw)

    # F00BFCC: x * 32767 / peak, truncated toward zero by divs.w.
    def scale(x):
        num = x * 32767
        q = abs(num) // peak
        return q if num >= 0 else -q

    bad = sum(scale(a) != b for a, b in zip(raw, norm))
    check("normalize formula predicts every sample", bad == 0, f"raw peak {peak}, {bad} mismatches")

    print(f"raw: peak {peak}, rms {math.sqrt(sum(v * v for v in raw) / len(raw)):.0f}, "
          f"first samples {raw[:6]}, last samples {raw[-3:]}")

    # P5 and P8 are the sounding carriers. P5 sits near 440 x 0.94 and P8 near 440 x 2.45.
    # Compare them with frequencies between the partials.
    expected = {"P5 carrier": 440 * 0.940, "P8 carrier": 440 * 2.450}
    between = {"between": 440 * 1.6, "between 2": 440 * 4.1}
    for label, f in {**expected, **between}.items():
        print(f"  {label:12s} {f:7.1f} Hz  level {level(raw, f, rate):8.0f}")
    quiet = max(level(raw, f, rate) for f in between.values())
    check("carriers stand above the gaps", all(level(raw, f, rate) > 2 * quiet for f in expected.values()))

    for wave in (1, 2, 3, 4, 5):
        for kind in ("fm", "plain"):
            _, v = samples(p(f"tbwerd1-wave{wave}-{kind}-raw.sd"))
            print(f"  wave {wave} {kind:5s}: peak {max(abs(x) for x in v):6d}  rms {math.sqrt(sum(x * x for x in v) / len(v)):6.0f}")

    for name in os.listdir(out):
        if name.startswith("tmp-"):
            os.remove(p(name))
    print(f"\n{sum(results)}/{len(results)} checks passed")
    sys.exit(0 if all(results) else 1)


if __name__ == "__main__":
    main()
