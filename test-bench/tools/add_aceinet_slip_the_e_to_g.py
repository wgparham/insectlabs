"""Import aceinet's supplied guitar loop into TestBench v1 (NumPy + stdlib)."""
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
DELIVERY = '07-recordings/slip_the_e_to_g_aceinet_mono.wav'
SOURCE_ID = 'aceinet_slip_the_e_to_g'
SOURCE_FILENAME = '417150__aceinet__slip-the-e-to-g_loop.wav'
SOURCE_SHA256 = 'cdcfa517c0dc27431a641b1f1d1b8e36cf2ece4f29aea1f0a0591fd249fc1cc1'
FIELDS = ['file', 'seconds', 'channels', 'kind', 'loop', 'peak_dbfs',
          'rms_dbfs', 'purpose', 'notes', 'license', 'sha256']


def digest(path):
    h = hashlib.sha256()
    with path.open('rb') as stream:
        for chunk in iter(lambda: stream.read(1024 * 1024), b''):
            h.update(chunk)
    return h.hexdigest()


def write_24bit(path, audio):
    q = np.rint(audio * 8388608).astype(np.int32)
    u = q.astype(np.uint32)
    packed = np.stack([(u >> shift) & 255 for shift in (0, 8, 16)], axis=1)
    with wave.open(str(path), 'wb') as wav:
        wav.setnchannels(1)
        wav.setsampwidth(3)
        wav.setframerate(RATE)
        wav.writeframes(packed.astype(np.uint8).tobytes())
    return q.astype(np.float64) / 8388608


def import_loop(output, sources):
    source_path = sources / SOURCE_FILENAME
    if digest(source_path) != SOURCE_SHA256:
        raise ValueError(f'Supplied source SHA-256 does not match: {source_path}')
    with wave.open(str(source_path), 'rb') as wav:
        if (wav.getframerate(), wav.getsampwidth(), wav.getnchannels()) != (RATE, 2, 2):
            raise ValueError('Expected the supplied 48 kHz, 16-bit, stereo PCM WAV')
        samples = np.frombuffer(wav.readframes(wav.getnframes()), dtype='<i2')
        audio = samples.reshape(-1, 2).astype(np.float64) / 32768
    if not np.array_equal(audio[:, 0], audio[:, 1]):
        raise ValueError('Expected identical dual-mono source channels')

    audio = audio[:, 0].copy()
    frames = len(audio)
    audio -= audio.mean()
    blend_frames = round(BLEND_MS * RATE / 1000)
    tail = audio[-blend_frames:].copy()
    audio = np.concatenate((audio[blend_frames:], audio[:blend_frames]))
    fade = np.linspace(0.0, 1.0, blend_frames, endpoint=True)
    audio[-blend_frames:] = tail * (1.0 - fade) + audio[-blend_frames:] * fade
    audio *= (10 ** (TARGET_DBFS / 20)) / np.max(np.abs(audio))

    path = output / DELIVERY
    path.parent.mkdir(parents=True, exist_ok=True)
    measured = write_24bit(path, audio)
    peak = float(np.max(np.abs(measured)))
    rms = float(np.sqrt(np.mean(measured ** 2)))
    db = lambda value: float(20 * math.log10(value)) if value else None
    purpose = 'Recorded electric-guitar phrase for distortion pedals, overdrives, fuzz, preamps, gates, EQ and compression tests.'
    notes = ('User-supplied 24.262-second loop cut from aceinet’s Freesound recording. '
             'The supplied dual-mono 48 kHz/16-bit PCM was converted to mono 48 kHz/24-bit PCM, '
             'mean removed, circularly crossfaded across 10 ms at the wrap, and peak adjusted to '
             '-12 dBFS for pedal input headroom. No resampling, time stretching, or added effects. '
             'The Freesound listing describes a 44-second stereo original; this supplied loop cut '
             'is preserved verbatim in sources/. See ATTRIBUTION.md.')
    source_sha = SOURCE_SHA256
    record = dict(file=DELIVERY, frames=len(measured), seconds=len(measured) / RATE,
                  channels=1, sample_rate=RATE, bits=24, kind='third-party guitar recording',
                  loop='24.262-second supplied loop cut with 10 ms circular wrap crossfade; no silence; not tempo-locked',
                  peak_dbfs=[db(peak)], rms_dbfs=[db(rms)], dc_mean=[float(measured.mean())],
                  correlation=None, boundary_step=[float(abs(measured[0] - measured[-1]))],
                  purpose=purpose, notes=notes, license='CC0-1.0', sources=[SOURCE_ID],
                  source_sha256=source_sha, sha256=digest(path),
                  circular_loop=dict(frames=len(measured), sample_rate=RATE,
                                     wrap_crossfade_frames=blend_frames,
                                     wrap_method='10 ms circular linear crossfade; no inserted silence or endpoint fade'))

    manifest_path = output / 'manifest.json'
    manifest = json.loads(manifest_path.read_text(encoding='utf-8'))
    manifest['files'] = [r for r in manifest['files'] if r['file'] != DELIVERY] + [record]
    manifest['version'] = '1.9.0'
    source_record = dict(id=SOURCE_ID, title='slip the e to g.wav', author='aceinet', user='aceinet',
                         sound=417150, url='https://freesound.org/people/aceinet/sounds/417150/',
                         license='CC0-1.0', license_label='Creative Commons 0',
                         license_url='https://creativecommons.org/publicdomain/zero/1.0/',
                         license_checked='2026-10-21', filename=SOURCE_FILENAME,
                         sha256=SOURCE_SHA256,
                         source_page_format=dict(channels=2, sample_rate=44100, bits=16, seconds=44.0),
                         supplied_file_format=dict(channels=2, sample_rate=48000, bits=16,
                                                   frames=frames, seconds=frames / RATE),
                         delivery=DELIVERY, purpose=purpose,
                         note='The supplied file is a shorter loop cut than the 44-second source described on the Freesound page; the supplied file is archived unchanged.')
    manifest['sources'] = [r for r in manifest.get('sources', []) if r.get('id') != SOURCE_ID] + [source_record]
    manifest_path.write_text(json.dumps(manifest, indent=2) + '\n', encoding='utf-8')
    sources_json = sources / 'SOURCES.json'
    sources_list = json.loads(sources_json.read_text(encoding='utf-8'))
    sources_list = [r for r in sources_list if r.get('id') != SOURCE_ID] + [source_record]
    sources_json.write_text(json.dumps(sources_list, indent=2) + '\n', encoding='utf-8')
    source_notes = sources / 'SOURCE.md'
    existing_notes = source_notes.read_text(encoding='utf-8') if source_notes.exists() else ''
    section = ('\n\n# Freesound source 417150\n\n'
               '- Title: slip the e to g.wav\n- Author: aceinet\n'
               '- Source: [https://freesound.org/people/aceinet/sounds/417150/](https://freesound.org/people/aceinet/sounds/417150/)\n'
               '- License: [Creative Commons 0](https://creativecommons.org/publicdomain/zero/1.0/)\n'
               '- License evidence: Freesound listing explicitly shows Creative Commons 0; checked 2026-10-21.\n'
               f'- Preserved file: `{SOURCE_FILENAME}`\n- SHA-256: `{SOURCE_SHA256}`\n'
               f'- Supplied file format: 2 channels, 48000 Hz, 16-bit PCM, {frames / RATE:.9f} seconds.\n'
               '- The Freesound page describes a 44-second, 44.1 kHz, stereo original. The user supplied a shorter loop cut; this received file is archived unchanged.\n')
    prefix = existing_notes.split('\n\n# Freesound source 417150')[0].rstrip()
    source_notes.write_text(prefix + section, encoding='utf-8')

    with (output / 'catalog.csv').open('w', newline='', encoding='utf-8') as stream:
        writer = csv.DictWriter(stream, fieldnames=FIELDS, extrasaction='ignore')
        writer.writeheader()
        writer.writerows(manifest['files'])
    (output / 'all-files.m3u8').write_text('#EXTM3U\n' + '\n'.join(r['file'] for r in manifest['files']) + '\n', encoding='utf-8')
    catalog = '# Audio file catalog\n\n48 kHz, 24-bit PCM WAV. Levels measured after quantization. Null means digital silence.\n\n'
    for r in manifest['files']:
        catalog += (f"## {r['file']}\n\n{r['seconds']:g} seconds; {r['channels']} channel(s); {r['kind']}.\n\n"
                    f"Loop: {r['loop']}. Peak dBFS: {r['peak_dbfs']}; RMS dBFS: {r['rms_dbfs']}.\n\n"
                    f"{r['purpose']}\n\n{r['notes']}\n\n")
    (output / 'CATALOG.md').write_text(catalog, encoding='utf-8')

    attr = output / 'ATTRIBUTION.md'
    old = attr.read_text(encoding='utf-8').split('\n## Guitar recording added in 1.9.0')[0].rstrip()
    old += ('\n\n## Guitar recording added in 1.9.0\n\n'
            f"[slip the e to g.wav](https://freesound.org/people/aceinet/sounds/417150/) by **aceinet**, "
            '[CC0 1.0](https://creativecommons.org/publicdomain/zero/1.0/). The listing was checked on '
            '2026-10-21 and explicitly identifies the recording as Creative Commons 0. The exact supplied '
            f"WAV is preserved in `test-bench/sources/guitar-loops/{SOURCE_FILENAME}`; SHA-256 `{SOURCE_SHA256}`.\n\n"
            'The Freesound listing describes a 44-second, 44.1 kHz, stereo, 16-bit source. The user-supplied '
            'loop cut is 24.262 seconds, 48 kHz, stereo, 16-bit PCM and identical dual mono. Delivery is mono '
            '48 kHz/24-bit PCM with mean removed, a 10 ms circular wrap crossfade, and -12 dBFS peak for pedal '
            'headroom. No resampling, time stretching, or added effects. The loop is not tempo-locked.\n')
    attr.write_text(old, encoding='utf-8')

    print(json.dumps(dict(file=DELIVERY, seconds=record['seconds'], peak_dbfs=record['peak_dbfs'],
                          rms_dbfs=record['rms_dbfs'], boundary_step=record['boundary_step'],
                          sha256=record['sha256']), indent=2))


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('--output', type=Path, required=True)
    parser.add_argument('--sources', type=Path, required=True)
    args = parser.parse_args()
    import_loop(args.output, args.sources)
