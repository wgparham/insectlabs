"""Prepare credited recording excerpts; run after generate.py. NumPy + stdlib."""
import argparse, csv, hashlib, json, wave
from pathlib import Path
import numpy as np

p = argparse.ArgumentParser()
p.add_argument('--output', type=Path, required=True)
p.add_argument('--sources', type=Path, required=True)
a = p.parse_args()
out = a.output
manifest = json.loads((out/'manifest.json').read_text())
records = [r for r in manifest['files'] if not r['file'].startswith('07-recordings/')]
for r in records:
    r['license'] = 'CC0-1.0'
sources = [
    dict(id='jazz_guitar', title='more Jazz guitar.wav', author='Sub-d', sound=49658,
         user='Sub-d', license='CC0-1.0', license_url='https://creativecommons.org/publicdomain/zero/1.0/',
         note='Real guitar performance. Recording chain and existing effects not established.'),
    dict(id='piano', title='Piano Melody', author='benpm (formerly credited as Lemoncreme)', sound=186942,
         user='benpm', license='CC-BY-4.0', license_url='https://creativecommons.org/licenses/by/4.0/',
         note='Sourced piano performance; acoustic versus digital instrument is unspecified by the author.'),
    dict(id='birds2', title='Bird call in spring.mp3', author='jmiddlesworth', sound=364663,
         user='jmiddlesworth', license='CC0-1.0', license_url='https://creativecommons.org/publicdomain/zero/1.0/',
         note='Real field recording, originally lossy MP3. WAV conversion does not restore lost detail.'),
    dict(id='rain', title='Rain, Moderate, C.wav', author='InspectorJ (www.jshaw.co.uk)', sound=401275,
         user='InspectorJ', license='CC-BY-4.0', license_url='https://creativecommons.org/licenses/by/4.0/',
         note='Real moderate-rain field recording.')]
commit = '333258a3644677d8de12a30ff50982bcfe23138d'
signals = {}
for s in sources:
    path = a.sources/(s['id']+'.wav')
    s['url'] = f"https://freesound.org/people/{s['user']}/sounds/{s['sound']}/"
    s['mirror'] = f"https://raw.githubusercontent.com/IBM/MAX-Audio-Embedding-Generator/{commit}/samples/demo_assets/{s['id']}.wav"
    s['sha256'] = hashlib.sha256(path.read_bytes()).hexdigest()
    with wave.open(str(path)) as w:
        assert w.getsampwidth() == 2 and w.getnchannels() == 2 and w.getframerate() == 44100
        x = np.frombuffer(w.readframes(w.getnframes()), dtype='<i2').reshape(-1,2).astype(float)/32768
    # Ten-second musical/rain excerpts; 24-second birds. Fades prevent hard cuts.
    length = 24 if s['id']=='birds2' else 10
    x = x[:length*44100].copy()
    assert len(x) == length*44100
    x -= x.mean(axis=0)
    f = np.linspace(0,1,2205)
    x[:len(f)] *= f[:,None]; x[-len(f):] *= f[::-1,None]
    # Fourier interpolation, with correct even-length Nyquist splitting.
    n = len(x); m = length*48000
    sp = np.fft.rfft(x,axis=0); sp[-1] *= .5
    y = np.fft.irfft(sp,n=m,axis=0)*(m/n)
    f = np.linspace(0,1,2400)
    y[:len(f)] *= f[:,None]; y[-len(f):] *= f[::-1,None]
    signals[s['id']] = y*(10**(-9/20)/np.max(np.abs(y)))

def add(name,x,source_ids,purpose):
    if x.ndim==1: x=x[:,None]
    q=np.rint(x*8388608).astype(np.int32)
    u=q.reshape(-1).astype(np.uint32)
    b=np.stack([(u>>shift)&255 for shift in (0,8,16)],axis=1).astype(np.uint8)
    path=out/'07-recordings'/(name+'.wav'); path.parent.mkdir(exist_ok=True)
    with wave.open(str(path),'wb') as w:
        w.setnchannels(x.shape[1]);w.setsampwidth(3);w.setframerate(48000);w.writeframes(b.tobytes())
    x=q/8388608
    db=lambda v: [float(20*np.log10(z)) if z else None for z in v]
    records.append(dict(file=path.relative_to(out).as_posix(),frames=len(x),seconds=len(x)/48000,
        channels=x.shape[1],sample_rate=48000,bits=24,kind='third-party recording excerpt',
        loop='50 ms faded restart; not a seamless musical loop',peak_dbfs=db(np.max(np.abs(x),axis=0)),
        rms_dbfs=db(np.sqrt(np.mean(x*x,axis=0))),dc_mean=x.mean(axis=0).tolist(),
        correlation=float(np.corrcoef(x.T)[0,1]) if x.shape[1]==2 else None,
        boundary_step=np.abs(x[0]-x[-1]).tolist(),purpose=purpose,
        notes='See ATTRIBUTION.md. Excerpted, mean removed, Fourier resampled 44.1 to 48 kHz, faded, peak adjusted; no added effects. Original mirror is 16-bit.',
        sources=source_ids,license='See source-specific licenses in ATTRIBUTION.md',
        sha256=hashlib.sha256(path.read_bytes()).hexdigest()))

for s in sources:
    add(s['id']+'_recording_stereo',signals[s['id']],[s['id']],s['note'])
g=signals['jazz_guitar'].mean(axis=1); pi=signals['piano'].mean(axis=1)
add('guitar_recording_mono',g,['jazz_guitar'],'Single-channel guitar performance for mono-first modules.')
add('piano_recording_mono',pi,['piano'],'Single-channel piano performance; instrument technology unspecified.')
add('recorded_guitar_left_piano_right',np.column_stack((g,pi)),['jazz_guitar','piano'],'Independent, unsynchronized performances for channel identification, not a composed duet.')
add('recorded_piano_left_guitar_right',np.column_stack((pi,g)),['jazz_guitar','piano'],'Reversed channel assignment for routing checks.')
manifest['files']=records;manifest['sources']=sources
manifest['audio_rights']='Mixed licenses: generated audio CC0-1.0; recordings retain individual source licenses. See ATTRIBUTION.md.'
(out/'manifest.json').write_text(json.dumps(manifest,indent=2)+'\n',encoding='utf-8')
with (out/'catalog.csv').open('w',newline='',encoding='utf-8') as f:
    w=csv.DictWriter(f,fieldnames=['file','seconds','channels','kind','loop','peak_dbfs','rms_dbfs','purpose','notes','license','sha256'],extrasaction='ignore');w.writeheader();w.writerows(records)
(out/'all-files.m3u8').write_text('#EXTM3U\n'+'\n'.join(r['file'] for r in records)+'\n')
text='# Audio file catalog\n\n48 kHz, 24-bit PCM WAV. Levels measured after quantization. Null means digital silence.\n\n'
for r in records:
    text+=f"## {r['file']}\n\n{r['seconds']:g} seconds; {r['channels']} channel(s); {r['kind']}.\n\nLoop: {r['loop']}. Peak dBFS: {r['peak_dbfs']}; RMS dBFS: {r['rms_dbfs']}.\n\n{r['purpose']}\n\n{r['notes']}\n\n"
(out/'CATALOG.md').write_text(text,encoding='utf-8')
text='# Audio credits and redistribution\n\nGenerated audio in folders 01–06: original procedural material, dedicated under [CC0 1.0](https://creativecommons.org/publicdomain/zero/1.0/). No third-party samples were used in those folders. Generator code follows the repository license separately.\n\nFolder 07 contains third-party recordings. Keep these credits with all copies, including derived channel combinations. No endorsement by the source authors is implied.\n\n'
for s in sources:
    text+=f"## {s['id']}\n\n[{s['title']}]({s['url']}) by **{s['author']}**, [{s['license']}]({s['license_url']}).\n\n{s['note']}\n\n[Downloaded WAV mirror]({s['mirror']}); SHA-256 `{s['sha256']}`. IBM's sample README identifies the original sources and notes conversion to 16-bit where necessary. Source license pages checked 2026-09-28.\n\n"
text+='## Changes made for this collection\n\nUsed the first 10 seconds of the mirrored guitar, piano and rain files, and the first 24 seconds of birds. Removed the mean; applied 50 ms endpoint fades, Fourier resampled from 44.1 to 48 kHz, reapplied endpoint fades, and adjusted stereo peak to -9 dBFS. Exported as 24-bit PCM without adding effects. This does not restore precision lost in earlier conversions. Mono versions average left and right. Split-channel files pair those mono versions; the performances are independent and not tempo matched. The piano-containing pairs include CC BY 4.0 material: retain the piano credit and license link.\n'
(out/'ATTRIBUTION.md').write_text(text,encoding='utf-8')
print(f'Prepared {len(records)} total WAVs, including 8 recording-based files.')
