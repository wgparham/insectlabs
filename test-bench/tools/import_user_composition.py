"""Import a user-owned full mix as a 48 kHz, 24-bit TestBench fixture."""
import argparse
import csv
import hashlib
import json
import wave
from pathlib import Path

import numpy as np


parser = argparse.ArgumentParser()
parser.add_argument("--source", type=Path, required=True)
parser.add_argument("--output", type=Path, required=True)
parser.add_argument("--name", default="after_drinking_at_emalines_original_mix")
args = parser.parse_args()

source = args.source
output = args.output
destination = output / "05-musical" / f"{args.name}.wav"
manifest_path = output / "manifest.json"


def hash_file(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def dbfs(values):
    return [float(20 * np.log10(value)) if value else None for value in values]


def write_pcm24(handle, values):
    values = np.asarray(values, dtype=np.int32)
    unsigned = values.reshape(-1).astype(np.uint32)
    packed = np.stack([(unsigned >> shift) & 255 for shift in (0, 8, 16)], axis=1)
    handle.writeframes(packed.astype(np.uint8).tobytes())


with wave.open(str(source)) as reader:
    if (reader.getcomptype(), reader.getsampwidth(), reader.getnchannels()) != ("NONE", 2, 2):
        raise ValueError("Source must be uncompressed stereo 16-bit PCM WAV")
    source_rate = reader.getframerate()
    frames = reader.getnframes()

target_rate = 48000
target_frames = round(frames * target_rate / source_rate)
target_peak = 10 ** (-6 / 20)
destination.parent.mkdir(parents=True, exist_ok=True)

def resampled_blocks():
    """Yield overlapping, Fourier-resampled blocks without holding the mix in memory."""
    core_frames = source_rate * 30
    overlap_frames = source_rate // 2
    ratio = target_rate / source_rate
    with wave.open(str(source)) as reader:
        for core_start in range(0, frames, core_frames):
            core_end = min(frames, core_start + core_frames)
            source_start = max(0, core_start - overlap_frames)
            source_end = min(frames, core_end + overlap_frames)
            reader.setpos(source_start)
            block = np.frombuffer(reader.readframes(source_end - source_start), dtype="<i2").reshape(-1, 2).astype(np.float64) / 32768.0
            converted_frames = round(len(block) * ratio)
            spectrum = np.fft.rfft(block, axis=0)
            if len(block) % 2 == 0:
                spectrum[-1] *= 0.5
            converted = np.fft.irfft(spectrum, n=converted_frames, axis=0) * (converted_frames / len(block))
            expected_frames = round(core_end * ratio) - round(core_start * ratio)
            crop_start = round((core_start - source_start) * ratio)
            yield converted[crop_start:crop_start + expected_frames]


raw_peak = 0.0
for block in resampled_blocks():
    raw_peak = max(raw_peak, float(np.max(np.abs(block))))
scale = target_peak / raw_peak
peak = np.zeros(2, dtype=np.int64)
sums = np.zeros(2, dtype=np.float64)
squares = np.zeros(2, dtype=np.float64)
cross = 0.0
first = None
last = None
with wave.open(str(destination), "wb") as writer:
    writer.setnchannels(2)
    writer.setsampwidth(3)
    writer.setframerate(target_rate)
    for block in resampled_blocks():
        chunk = np.rint(block * scale * 8388608).astype(np.int32)
        peak = np.maximum(peak, np.max(np.abs(chunk), axis=0))
        floats = chunk / 8388608.0
        sums += np.sum(floats, axis=0)
        squares += np.sum(floats * floats, axis=0)
        cross += float(np.sum(floats[:, 0] * floats[:, 1]))
        if first is None:
            first = chunk[0].copy()
        last = chunk[-1].copy()
        write_pcm24(writer, chunk)

means = sums / target_frames
rms = np.sqrt(squares / target_frames)
covariance = cross / target_frames - means[0] * means[1]
correlation = covariance / np.sqrt((rms[0] ** 2 - means[0] ** 2) * (rms[1] ** 2 - means[1] ** 2))

manifest = json.loads(manifest_path.read_text(encoding="utf-8"))
record = {
    "file": destination.relative_to(output).as_posix(),
    "frames": target_frames,
    "seconds": target_frames / target_rate,
    "channels": 2,
    "sample_rate": target_rate,
    "bits": 24,
    "kind": "user-owned original experimental music mix",
    "loop": "Full composition; not loopable.",
    "peak_dbfs": dbfs(peak / 8388608.0),
    "rms_dbfs": dbfs(rms),
    "dc_mean": means.tolist(),
    "correlation": float(correlation),
    "boundary_step": (np.abs(first - last) / 8388608.0).tolist(),
    "purpose": "Full-mix audition fixture for testing InsectLabs modules on the composer's own experimental music.",
    "notes": "User-supplied stereo mix. Fourier resampled from 22.05 kHz/16-bit PCM to 48 kHz, then globally peak adjusted to -6 dBFS and exported as 24-bit PCM. No fades, effects, EQ, dynamics, or channel processing added.",
    "source": source.name,
    "source_sha256": hash_file(source),
    "license": "Copyright retained by the composer; included in the TestBench by permission.",
    "sha256": hash_file(destination),
}
records = [item for item in manifest["files"] if item["file"] != record["file"]]
records.append(record)
manifest["files"] = records
manifest["version"] = "1.1.0"
manifest["audio_rights"] = "Mixed licenses: generated audio CC0-1.0; third-party recordings retain individual licenses; user-owned original composition is included by permission. See ATTRIBUTION.md."
manifest_path.write_text(json.dumps(manifest, indent=2) + "\n", encoding="utf-8")

with (output / "catalog.csv").open("w", newline="", encoding="utf-8") as handle:
    fields = ["file", "seconds", "channels", "kind", "loop", "peak_dbfs", "rms_dbfs", "purpose", "notes", "license", "sha256"]
    writer = csv.DictWriter(handle, fieldnames=fields, extrasaction="ignore")
    writer.writeheader()
    writer.writerows(records)

catalog = "# Audio file catalog\n\n48 kHz, 24-bit PCM WAV. Levels measured after quantization. Null means digital silence.\n\n"
for item in records:
    catalog += f"## {item['file']}\n\n{item['seconds']:g} seconds; {item['channels']} channel(s); {item['kind']}.\n\n"
    catalog += f"Loop: {item['loop']}. Peak dBFS: {item['peak_dbfs']}; RMS dBFS: {item['rms_dbfs']}.\n\n"
    catalog += f"{item['purpose']}\n\n{item['notes']}\n\n"
(output / "CATALOG.md").write_text(catalog, encoding="utf-8")
(output / "all-files.m3u8").write_text("#EXTM3U\n" + "\n".join(item["file"] for item in records) + "\n", encoding="utf-8")

attribution_path = output / "ATTRIBUTION.md"
attribution = attribution_path.read_text(encoding="utf-8")
marker = "## User-owned original composition\n"
if marker in attribution:
    attribution = attribution.split(marker)[0].rstrip() + "\n\n"
attribution += marker
attribution += f"`05-musical/{args.name}.wav` is an original composition and fully mixed stereo recording supplied by the project creator. Copyright remains with the composer; it is included in this TestBench collection with permission for InsectLabs development and audition. Source SHA-256: `{record['source_sha256']}`.\n"
attribution_path.write_text(attribution, encoding="utf-8")

print(json.dumps({"file": record["file"], "seconds": record["seconds"], "peak_dbfs": record["peak_dbfs"], "sha256": record["sha256"]}, indent=2))
