"""Add Model 62 head-spacing impulses to an existing TestBench collection.

Run from the repository root:
    python test-bench/tools/add_model62_head_impulses.py --output test-bench/v1

The generator produces a stereo 48 kHz / 24-bit PCM fixture with 150 Hz,
10 ms Hann-windowed impulses. Pulse-pair offsets approximate Model 62's
33 mm playback-head spacing at 1x, 1/2x, and 4x transport speed.
"""

from __future__ import annotations

import argparse
import csv
import hashlib
import json
import wave
from pathlib import Path

import numpy as np


SAMPLE_RATE = 48_000
BITS = 24
PCM_SCALE = 1 << (BITS - 1)
MAX_PCM = PCM_SCALE - 1
TARGET_PEAK_DBFS = -18.0
PULSE_HZ = 150
PULSE_SECONDS = 0.010
GAPS_MS = (54.0, 108.0, 13.5)
SILENCE_SECONDS = 2.0
FILENAME = "02-dynamics/model62_head_spacing_impulses_150Hz_minus18dBFS.wav"
SUPPLIED_WAV_SHA256 = "7a9959da5f32e18ef81061a6512548dad8673635d7e8d909445f6e84c291680c"
SUPPLIED_SCRIPT_SHA256 = "edb10a6204cc44ec6464e602505a070dd777ee26d83a0989b9c462f99752738a"
CATALOG_FIELDS = [
    "file", "seconds", "channels", "kind", "loop", "peak_dbfs",
    "rms_dbfs", "purpose", "notes", "license", "sha256",
]


def pcm24_bytes(samples: np.ndarray) -> bytes:
    signed = np.rint(np.clip(samples, -1.0, MAX_PCM / PCM_SCALE) * PCM_SCALE).astype(np.int32)
    packed = signed.reshape(-1).astype(np.uint32)
    data = np.empty((packed.size, 3), dtype=np.uint8)
    data[:, 0] = packed & 0xFF
    data[:, 1] = (packed >> 8) & 0xFF
    data[:, 2] = (packed >> 16) & 0xFF
    return data.tobytes()


def make_pulse() -> np.ndarray:
    count = round(SAMPLE_RATE * PULSE_SECONDS)
    t = np.arange(count, dtype=np.float64) / SAMPLE_RATE
    pulse = np.sin(2.0 * np.pi * PULSE_HZ * t) * np.hanning(count)
    pulse *= 10.0 ** (TARGET_PEAK_DBFS / 20.0) / np.max(np.abs(pulse))
    return pulse


def generate() -> np.ndarray:
    pulse = make_pulse()
    sections: list[np.ndarray] = []
    for gap_ms in GAPS_MS:
        gap = round(SAMPLE_RATE * gap_ms / 1000.0)
        pair = np.zeros((gap + len(pulse), 2), dtype=np.float64)
        pair[: len(pulse), 0] = pulse
        pair[gap : gap + len(pulse), 1] = pulse
        sections.extend((pair, np.zeros((round(SAMPLE_RATE * SILENCE_SECONDS), 2))))
    return np.concatenate(sections, axis=0)


def update_collection(folder: Path) -> dict[str, object]:
    manifest_path = folder / "manifest.json"
    manifest = json.loads(manifest_path.read_text(encoding="utf-8"))
    audio = generate()
    output = folder / FILENAME
    output.parent.mkdir(parents=True, exist_ok=True)
    packed_audio = pcm24_bytes(audio)
    with wave.open(str(output), "wb") as wav:
        wav.setnchannels(2)
        wav.setsampwidth(3)
        wav.setframerate(SAMPLE_RATE)
        wav.writeframes(packed_audio)

    quantized = np.frombuffer(packed_audio, dtype=np.uint8).reshape(-1, 3).astype(np.int32)
    values = quantized[:, 0] | (quantized[:, 1] << 8) | (quantized[:, 2] << 16)
    values = np.where(values & 0x800000, values - 0x1000000, values).reshape(-1, 2)
    normalized = values / PCM_SCALE
    peak = np.max(np.abs(normalized), axis=0)
    rms = np.sqrt(np.mean(normalized * normalized, axis=0))
    record = {
        "file": FILENAME,
        "frames": len(normalized),
        "seconds": len(normalized) / SAMPLE_RATE,
        "channels": 2,
        "sample_rate": SAMPLE_RATE,
        "bits": BITS,
        "kind": "analytic stereo impulse-pair fixture",
        "loop": "one-shot sequence with two seconds of digital silence between pairs",
        "peak_dbfs": [round(float(20 * np.log10(v)), 5) for v in peak],
        "rms_dbfs": [round(float(20 * np.log10(v)), 5) for v in rms],
        "dc_mean": [round(float(v), 10) for v in np.mean(normalized, axis=0)],
        "correlation": None,
        "boundary_step": [0.0, 0.0],
        "purpose": "Check playback-head spacing, short echo timing, stereo offset, transient response, and transport-speed relationships on Model 62 and other delay or recorder modules.",
        "notes": "Three 150 Hz, 10 ms Hann-windowed pulse pairs. Left pulse leads right by 54 ms (33 mm at 1x), 108 ms (1/2x), then 13.5 ms (4x); two seconds of digital silence separates pairs. Fixed -18 dBFS peak per channel. One-shot, not a seamless loop.",
        "source": "Analytic construction based on the supplied impulses_sequence.wav and generate_impulses_sequence.py; test-bench/tools/add_model62_head_impulses.py",
        "source_wav_sha256": SUPPLIED_WAV_SHA256,
        "source_generator_sha256": SUPPLIED_SCRIPT_SHA256,
        "license": "CC0-1.0",
        "sha256": hashlib.sha256(output.read_bytes()).hexdigest(),
        "generator_sha256": hashlib.sha256(Path(__file__).read_bytes()).hexdigest(),
        "numpy_version": np.__version__,
    }
    files = [r for r in manifest["files"] if r["file"] != FILENAME]
    files.append(record)
    manifest["files"] = files
    manifest["version"] = "1.4.0"
    manifest_path.write_text(json.dumps(manifest, indent=2) + "\n", encoding="utf-8")

    with (folder / "catalog.csv").open("w", newline="", encoding="utf-8") as handle:
        writer = csv.DictWriter(handle, fieldnames=CATALOG_FIELDS, extrasaction="ignore")
        writer.writeheader()
        writer.writerows(files)

    catalog = "# Audio file catalog\n\n48 kHz, 24-bit PCM WAV. Levels measured after quantization. Null means digital silence.\n\n"
    for item in files:
        catalog += (
            f"## {item['file']}\n\n{item['seconds']:g} seconds; {item['channels']} channel(s); {item['kind']}.\n\n"
            f"Loop: {item['loop']}. Peak dBFS: {item['peak_dbfs']}; RMS dBFS: {item['rms_dbfs']}.\n\n"
            f"{item['purpose']}\n\n{item['notes']}\n\n"
        )
    (folder / "CATALOG.md").write_text(catalog, encoding="utf-8")
    (folder / "all-files.m3u8").write_text(
        "#EXTM3U\n" + "\n".join(item["file"] for item in files) + "\n", encoding="utf-8"
    )
    return {"file": FILENAME, "frames": len(normalized), "seconds": len(normalized) / SAMPLE_RATE,
            "peak_dbfs": record["peak_dbfs"], "rms_dbfs": record["rms_dbfs"],
            "sha256": record["sha256"], "generator_sha256": record["generator_sha256"]}


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--output", type=Path, required=True, help="Existing TestBench collection folder")
    args = parser.parse_args()
    print(json.dumps(update_collection(args.output), indent=2))


if __name__ == "__main__":
    main()
