"""Add aceinet's CC0 guitar phrase to TestBench v1 (NumPy + stdlib)."""
import argparse
import csv
import hashlib
import json
import math
import wave
from pathlib import Path

import numpy as np

RATE = 48000
TARGET_DBFS = -12.0
BLEND_MS = 10
FIELDS = ['file', 'seconds', 'channels', 'kind', 'loop', 'peak_dbfs',
          'rms_dbfs', 'purpose', 'notes', 'license', 'sha256']


def digest(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def write_24bit(path, audio):
    quantized = np.rint(audio * 8388608).astype(np.int32)
    unsigned = quantized.astype(np.uint32)
    packed = np.stack([(unsigned >> shift) & 255 for shift in (0, 8, 16)], axis=1)
    with wave.open(str(path), 'wb') as wav:
        wav.setnchannels(1)
        wav.setsampwidth(3)
        wav.setframerate(RATE)
        wav.writeframes(packed.astype(np.uint8).tobytes())
    return quantized.astype(np.float64) / 8388608


def add(output, sources):
    source_records = json.loads((sources / 'SOURCES.json').read_text(encoding='utf-8'))
    source = source_records[0]
    original = sources / source['filename']
    assert digest(original) == source['sha256'], f'Source SHA-256 mismatch: {original}'
    with wave.open(str(original), 'rb') as wav:
        assert (wav.getframerate(), wav.getsampwidth(), wav.getnchannels()) == (44100, 2, 2)
        raw = np.frombuffer(wav.readframes(wav.getnframes()), dtype='<i2')
        raw = raw.reshape(-1, 2).astype(np.float64) / 32768
    assert np.array_equal(raw[:, 0], raw[:, 1]), 'Expected the supplied stereo file to be dual mono'

    audio = raw[:, 0].copy()
    source_frames = len(audio)
    audio -= audio.mean()
    blend_frames = round(BLEND_MS * 44100 / 1000)
    outgoing_tail = audio[-blend_frames:].copy()
    audio = np.concatenate((audio[blend_frames:], audio[:blend_frames]))
    fade = np.linspace(0, 1, blend_frames, endpoint=True)
    audio[-blend_frames:] = outgoing_tail * (1 - fade) + audio[-blend_frames:] * fade

    # Fourier interpolation is appropriate after circularly joining the phrase.
    output_frames = round(source_frames * RATE / 44100)
    spectrum = np.fft.rfft(audio)
    spectrum[-1] *= 0.5
    audio = np.fft.irfft(spectrum, n=output_frames) * (output_frames / source_frames)
    audio *= 10 ** (TARGET_DBFS / 20) / np.max(np.abs(audio))

    delivery = '07-recordings/slide_the_e_sandman_aceinet_mono.wav'
    path = output / delivery
    path.parent.mkdir(parents=True, exist_ok=True)
    measured = write_24bit(path, audio)
    peak = float(np.max(np.abs(measured)))
    rms = float(np.sqrt(np.mean(measured ** 2)))
    db = lambda value: float(20 * math.log10(value)) if value else None
    loop_data = dict(frames=len(measured), sample_rate=RATE,
                     wrap_crossfade_frames=round(BLEND_MS * RATE / 1000),
                     wrap_method='10 ms circular linear crossfade; no inserted silence or endpoint fade')
    purpose = 'Recorded electric-guitar phrase for distortion pedals, overdrives, fuzz, preamps, gates, EQ and compression tests.'
    notes = ('Complete 29.5-second source phrase; original channels are identical dual mono, delivered as mono. '
             'Mean removed, circular 10 ms wrap crossfade, Fourier resampled 44.1 to 48 kHz, and peak adjusted '
             'to -12 dBFS for pedal input headroom. No added effects or time stretching. Tempo is not locked. '
             'See ATTRIBUTION.md.')
    record = dict(file=delivery, frames=len(measured), seconds=len(measured) / RATE,
                  channels=1, sample_rate=RATE, bits=24, kind='third-party guitar recording',
                  loop='29.5-second complete phrase with 10 ms circular wrap crossfade; no silence; not tempo-locked',
                  peak_dbfs=[db(peak)], rms_dbfs=[db(rms)], dc_mean=[float(measured.mean())],
                  correlation=None, boundary_step=[float(abs(measured[0] - measured[-1]))],
                  purpose=purpose, notes=notes, license='CC0-1.0', sources=[source['id']],
                  source_sha256=source['sha256'], sha256=digest(path), circular_loop=loop_data)

    manifest_path = output / 'manifest.json'
    manifest = json.loads(manifest_path.read_text(encoding='utf-8'))
    files = [item for item in manifest['files'] if item['file'] != delivery]
    files.append(record)
    manifest['files'] = files
    manifest['version'] = '1.8.0'
    old_sources = manifest.get('sources', [])
    manifest['sources'] = [item for item in old_sources if item['id'] != source['id']] + [source]
    manifest['audio_rights'] = ('Mixed licenses: generated audio CC0-1.0; third-party recordings retain '
                                 'individual licenses; user-owned original composition is included by permission. '
                                 'See ATTRIBUTION.md.')
    manifest_path.write_text(json.dumps(manifest, indent=2) + '\n', encoding='utf-8')

    with (output / 'catalog.csv').open('w', newline='', encoding='utf-8') as stream:
        writer = csv.DictWriter(stream, fieldnames=FIELDS, extrasaction='ignore')
        writer.writeheader()
        writer.writerows(files)
    (output / 'all-files.m3u8').write_text('#EXTM3U\n' + '\n'.join(item['file'] for item in files) + '\n', encoding='utf-8')
    catalog = '# Audio file catalog\n\n48 kHz, 24-bit PCM WAV. Levels measured after quantization. Null means digital silence.\n\n'
    for item in files:
        catalog += (f"## {item['file']}\n\n{item['seconds']:g} seconds; {item['channels']} channel(s); {item['kind']}.\n\n"
                    f"Loop: {item['loop']}. Peak dBFS: {item['peak_dbfs']}; RMS dBFS: {item['rms_dbfs']}.\n\n"
                    f"{item['purpose']}\n\n{item['notes']}\n\n")
    (output / 'CATALOG.md').write_text(catalog, encoding='utf-8')

    attribution = output / 'ATTRIBUTION.md'
    current = attribution.read_text(encoding='utf-8').split('## Guitar recording added in 1.8.0')[0].rstrip()
    current += ('\n\n## Guitar recording added in 1.8.0\n\n'
                f"[{source['title']}]({source['url']}) by **{source['author']}**, "
                f"[CC0 1.0]({source['license_url']}). The project owner confirmed the listing's license on "
                f"{source['license_confirmed']}. The original WAV is preserved in "
                f"`test-bench/sources/guitar-loops/`; source SHA-256 `{source['sha256']}`.\n\n"
                'The delivery is a mono 48 kHz/24-bit PCM loop. The supplied stereo channels are identical. '
                'The complete phrase is retained, mean removed, circularly crossfaded over 10 ms, Fourier '
                'resampled from 44.1 to 48 kHz, and peak adjusted to -12 dBFS. No time stretching or added '
                'effects. The loop is not tempo-locked.\n')
    attribution.write_text(current, encoding='utf-8')
    print(json.dumps(dict(file=delivery, seconds=record['seconds'], peak_dbfs=record['peak_dbfs'],
                          rms_dbfs=record['rms_dbfs'], boundary_step=record['boundary_step'],
                          sha256=record['sha256']), indent=2))


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('--output', type=Path, required=True)
    parser.add_argument('--sources', type=Path, required=True)
    args = parser.parse_args()
    add(args.output, args.sources)
