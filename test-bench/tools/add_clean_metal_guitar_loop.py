"""Add an original, dry Drop-D metal guitar loop to an existing TestBench.

Run from the repository root:
    python test-bench/tools/add_clean_metal_guitar_loop.py --output test-bench/v1

The mono 48 kHz / 24-bit fixture is a deterministic plucked-string model,
not a recording. It contains no amplifier, pedal, distortion, EQ, compression,
or reverb so it can be used as an input source for drive pedals and processors.
"""

from __future__ import annotations

import argparse
import csv
import hashlib
import json
import math
import wave
from pathlib import Path

import numpy as np


SAMPLE_RATE = 48_000
TEMPO_BPM = 180
BARS = 8
BEATS_PER_BAR = 4
SIXTEENTHS_PER_BEAT = 4
FRAMES_PER_SIXTEENTH = round(SAMPLE_RATE * 60 / TEMPO_BPM / SIXTEENTHS_PER_BEAT)
FRAMES = FRAMES_PER_SIXTEENTH * SIXTEENTHS_PER_BEAT * BEATS_PER_BAR * BARS
TARGET_PEAK_DBFS = -12.0
FILENAME = "05-musical/synthetic_clean_death_metal_guitar_drop_d_180bpm.wav"
CATALOG_FIELDS = [
    "file", "seconds", "channels", "kind", "loop", "peak_dbfs",
    "rms_dbfs", "purpose", "notes", "license", "sha256",
]


def midi_hz(note: int) -> float:
    return 440.0 * 2.0 ** ((note - 69) / 12.0)


def pluck(note: int, duration: float, seed: int, palm_muted: bool) -> np.ndarray:
    """A dry, deterministic Karplus-Strong string with a pick-position comb."""
    frequency = midi_hz(note)
    count = max(2, round(SAMPLE_RATE * duration))
    delay = SAMPLE_RATE / frequency
    ring_size = math.ceil(delay) + 2
    rng = np.random.default_rng(seed)

    # A short noise burst stands in for the pick. The comb represents a pluck
    # away from the bridge and suppresses some even harmonics like a pickup.
    excitation = rng.standard_normal(ring_size)
    excitation *= np.hanning(ring_size)
    pick_offset = max(1, round(ring_size * 0.18))
    excitation[pick_offset:] -= 0.55 * excitation[:-pick_offset]
    excitation /= max(float(np.max(np.abs(excitation))), 1e-12)

    line = np.zeros(ring_size, dtype=np.float64)
    line[:] = excitation * 0.72
    output = np.empty(count, dtype=np.float64)
    write = 0
    previous = 0.0

    # Set the loop loss by a musical decay time. Muted chugs decay quickly;
    # open notes and power-chord accents ring longer without artificial gain.
    decay_seconds = 0.095 if palm_muted else 0.62
    round_trip = math.exp(-1.0 / (frequency * decay_seconds))
    feedback = round_trip ** (1.0 / delay)
    for i in range(count):
        read = write - delay
        while read < 0.0:
            read += ring_size
        i0 = int(read)
        frac = read - i0
        i1 = (i0 + 1) % ring_size
        value = line[i0] * (1.0 - frac) + line[i1] * frac
        output[i] = value
        filtered = 0.5 * (value + previous)
        previous = value
        line[write] = feedback * filtered
        write = (write + 1) % ring_size

    # A small linear attack and release prevent edits from becoming clicks.
    attack = min(count, round(0.0015 * SAMPLE_RATE))
    if attack > 1:
        output[:attack] *= np.sin(np.linspace(0.0, math.pi / 2.0, attack)) ** 2
    release = min(count, round(0.008 * SAMPLE_RATE))
    if release > 1:
        output[-release:] *= np.linspace(1.0, 0.0, release) ** 2
    return output


def add_circular(track: np.ndarray, signal: np.ndarray, start: int, gain: float) -> None:
    """Mix a note into the repeating cycle, wrapping its natural tail."""
    first = start % len(track)
    offset = 0
    while offset < len(signal):
        amount = min(len(signal) - offset, len(track) - first)
        track[first:first + amount] += signal[offset:offset + amount] * gain
        offset += amount
        first = 0


def generate() -> np.ndarray:
    track = np.zeros(FRAMES, dtype=np.float64)
    seed = 0x544557
    low_d = 38  # Drop-D low string: D2.

    # Sixteenth-note slots per bar. Fret numbers are on the low D string;
    # the final accent in selected bars is a D5-style root/fifth/octave chord.
    patterns = [
        [(0, 0), (2, 0), (3, 0), (4, 0), (6, 0), (7, 0), (8, 0), (10, 0), (11, 0), (12, 0), (14, 0), (15, 0)],
        [(0, 0), (2, 0), (3, 0), (4, 3), (6, 3), (7, 3), (8, 5), (10, 5), (11, 5), (12, 7), (14, 5), (15, 5)],
        [(0, 0), (2, 0), (3, 0), (4, 1), (6, 1), (7, 1), (8, 4), (10, 4), (11, 4), (12, 5), (14, 4), (15, 4)],
        [(0, 0), (2, 0), (3, 0), (4, 0), (6, 0), (7, 0), (8, 3), (10, 3), (11, 3), (12, 7), (14, 0), (15, 0)],
        [(0, 0), (1, 0), (2, 0), (4, 0), (6, 0), (7, 0), (8, 0), (9, 0), (10, 0), (12, 5), (14, 5), (15, 5)],
        [(0, 0), (2, 0), (3, 0), (4, 3), (6, 3), (7, 3), (8, 6), (10, 6), (11, 6), (12, 5), (14, 3), (15, 3)],
        [(0, 0), (2, 0), (3, 0), (4, 1), (6, 1), (7, 1), (8, 4), (10, 4), (11, 4), (12, 8), (14, 7), (15, 7)],
        [(0, 0), (1, 0), (2, 0), (3, 0), (4, 0), (6, 0), (7, 0), (8, 3), (10, 3), (11, 3), (12, 0), (15, 0)],
    ]

    for bar, events in enumerate(patterns):
        bar_start = bar * 16 * FRAMES_PER_SIXTEENTH
        for slot, fret in events:
            is_chord = slot == 12 and bar in (0, 2, 3, 4, 6, 7)
            duration = 0.46 if is_chord else (0.24 if fret else 0.078)
            muted = not is_chord and duration < 0.2
            velocity = 0.72 if slot % 4 else 0.95
            if is_chord:
                velocity = 0.70
            note = low_d + fret
            start = bar_start + slot * FRAMES_PER_SIXTEENTH
            pitches = (note, note + 7, note + 12) if is_chord else (note,)
            for string_index, pitch in enumerate(pitches):
                seed += 1
                sound = pluck(pitch, duration, seed, muted)
                string_gain = (1.0, 0.48, 0.30)[string_index]
                add_circular(track, sound, start, velocity * string_gain)

    peak = float(np.max(np.abs(track)))
    if not math.isfinite(peak) or peak <= 0.0:
        raise RuntimeError("Generated guitar loop is silent or nonfinite")
    track *= 10.0 ** (TARGET_PEAK_DBFS / 20.0) / peak
    return track


def write_pcm24(path: Path, audio: np.ndarray) -> np.ndarray:
    quantized = np.rint(np.clip(audio, -1.0, (2**23 - 1) / 2**23) * (2**23)).astype(np.int32)
    unsigned = quantized.astype(np.uint32)
    packed = np.empty((len(quantized), 3), dtype=np.uint8)
    packed[:, 0] = unsigned & 255
    packed[:, 1] = (unsigned >> 8) & 255
    packed[:, 2] = (unsigned >> 16) & 255
    path.parent.mkdir(parents=True, exist_ok=True)
    with wave.open(str(path), "wb") as wav:
        wav.setnchannels(1)
        wav.setsampwidth(3)
        wav.setframerate(SAMPLE_RATE)
        wav.writeframes(packed.tobytes())
    return quantized.astype(np.float64) / (2**23)


def update_collection(folder: Path) -> dict[str, object]:
    manifest_path = folder / "manifest.json"
    manifest = json.loads(manifest_path.read_text(encoding="utf-8"))
    output = folder / FILENAME
    quantized = write_pcm24(output, generate())
    peak = float(np.max(np.abs(quantized)))
    rms = float(np.sqrt(np.mean(quantized * quantized)))
    record = {
        "file": FILENAME,
        "frames": len(quantized),
        "seconds": len(quantized) / SAMPLE_RATE,
        "channels": 1,
        "sample_rate": SAMPLE_RATE,
        "bits": 24,
        "kind": "original synthesized clean electric-guitar performance",
        "loop": "exact 8-bar 4/4 cycle at 180 BPM; circular note tails; seamless periodic restart",
        "peak_dbfs": [round(20.0 * math.log10(peak), 6)],
        "rms_dbfs": [round(20.0 * math.log10(rms), 6)],
        "dc_mean": [round(float(np.mean(quantized)), 10)],
        "correlation": None,
        "boundary_step": [round(float(abs(quantized[0] - quantized[-1])), 10)],
        "purpose": "Dry, high-gain guitar input source for distortion pedals, overdrives, fuzz, preamps, gates, EQ and compression tests.",
        "notes": "Drop-D tuning; 180 BPM; eight bars in 4/4. Mono Karplus-Strong plucked-string model with palm-muted sixteenth-note chugs, syncopated fretted notes and open power-chord accents. Clean direct-style source: no amp model, distortion, EQ, compression, reverb or other effects. Exact repeating cycle with note tails wrapped across the boundary. Peak-normalized to -12 dBFS for pedal headroom; WAV level is not an absolute voltage.",
        "source": "Original deterministic NumPy string-model construction; test-bench/tools/add_clean_metal_guitar_loop.py",
        "generator_sha256": hashlib.sha256(Path(__file__).read_bytes()).hexdigest(),
        "numpy_version": np.__version__,
        "license": "CC0-1.0 (generated audio)",
        "sha256": hashlib.sha256(output.read_bytes()).hexdigest(),
        "musical_cycle": {
            "frames": FRAMES,
            "sample_rate": SAMPLE_RATE,
            "bpm": TEMPO_BPM,
            "bars_4_4": BARS,
            "tuning": "Drop D (D2 A2 D3 G3 B3 E4)",
            "wrap_method": "Plucked-string tails are circularly mixed across the exact loop boundary; no fade or silence is inserted.",
        },
    }
    manifest["files"] = [r for r in manifest["files"] if r["file"] != FILENAME] + [record]
    manifest["version"] = "1.7.0"
    manifest_path.write_text(json.dumps(manifest, indent=2) + "\n", encoding="utf-8")

    with (folder / "catalog.csv").open("w", newline="", encoding="utf-8") as handle:
        writer = csv.DictWriter(handle, fieldnames=CATALOG_FIELDS, extrasaction="ignore")
        writer.writeheader()
        writer.writerows(manifest["files"])

    catalog = "# Audio file catalog\n\n48 kHz, 24-bit PCM WAV. Levels measured after quantization. Null means digital silence.\n\n"
    for item in manifest["files"]:
        catalog += (
            f"## {item['file']}\n\n{item['seconds']:g} seconds; {item['channels']} channel(s); {item['kind']}.\n\n"
            f"Loop: {item['loop']}. Peak dBFS: {item['peak_dbfs']}; RMS dBFS: {item['rms_dbfs']}.\n\n"
            f"{item['purpose']}\n\n{item['notes']}\n\n"
        )
    (folder / "CATALOG.md").write_text(catalog.rstrip() + "\n", encoding="utf-8")
    (folder / "all-files.m3u8").write_text(
        "#EXTM3U\n" + "\n".join(item["file"] for item in manifest["files"]) + "\n",
        encoding="utf-8",
    )
    return {
        "file": FILENAME,
        "frames": FRAMES,
        "seconds": FRAMES / SAMPLE_RATE,
        "peak_dbfs": record["peak_dbfs"],
        "rms_dbfs": record["rms_dbfs"],
        "boundary_step": record["boundary_step"],
        "sha256": record["sha256"],
        "generator_sha256": record["generator_sha256"],
    }


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--output", type=Path, required=True, help="Existing TestBench collection folder")
    args = parser.parse_args()
    print(json.dumps(update_collection(args.output), indent=2))


if __name__ == "__main__":
    main()
