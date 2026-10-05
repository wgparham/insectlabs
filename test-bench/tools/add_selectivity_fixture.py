"""Add an original periodic five-tone narrow-band selectivity fixture to TestBench.

Run against an existing collection: python add_selectivity_fixture.py --output test-bench/v1
Preserves existing files and records. Rebuilds catalogs and playlist from the updated manifest.
"""
from pathlib import Path
import argparse, csv, hashlib, json, wave
import numpy as np

parser=argparse.ArgumentParser()
parser.add_argument('--output',type=Path,required=True)
out=parser.parse_args().output
manifest=json.loads((out/'manifest.json').read_text(encoding='utf-8'))
sr=48000
frequencies=[990,999,1000,1001,1010]
t=np.arange(sr,dtype=np.float64)/sr
period=sum(np.sin(2*np.pi*f*t) for f in frequencies)
period*=10**(-12/20)/np.max(np.abs(period))
q=np.tile(np.rint(period*8388608).astype(np.int32),8)
packed=np.stack((q&255,(q>>8)&255,(q>>16)&255),axis=-1).astype(np.uint8)
name='01-calibration/selectivity_1k_neighbours_8s.wav'
path=out/name;path.parent.mkdir(parents=True,exist_ok=True)
with wave.open(str(path),'wb') as wav:
    wav.setnchannels(1);wav.setsampwidth(3);wav.setframerate(sr);wav.writeframes(packed.tobytes())
x=q/8388608
spec=np.abs(np.fft.rfft(x[:sr]))*2/sr
levels=spec[frequencies]
assert np.max(levels)-np.min(levels)<1e-8
residual=spec.copy();residual[frequencies]=0
assert np.sum(residual**2)<1e-10*np.sum(levels**2)
assert np.array_equal(q[:sr],q[-sr:])
record=dict(file=name,frames=len(q),seconds=8.,channels=1,sample_rate=sr,bits=24,
    kind='analytic five-tone selectivity probe',loop='periodic; exact repeated one-second period',
    peak_dbfs=[float(20*np.log10(np.max(np.abs(x))))],rms_dbfs=[float(20*np.log10(np.sqrt(np.mean(x*x))))],
    dc_mean=[float(np.mean(x))],correlation=None,boundary_step=[float(abs(x[0]-x[-1]))],
    purpose='Measure narrow bandpass selection, notch rejection and neighbouring-frequency leakage around 1 kHz.',
    notes='Equal-amplitude 990, 999, 1000, 1001 and 1010 Hz sine components. Composite peak -12 dBFS; each component is lower. One-second period repeated eight times without fades or dither. Loop for settling of very narrow/high-order filters. With a 1 kHz, 2 Hz-wide matched BP/notch pair: center should pass MAIN and null C; neighbours near +/-1 Hz sit near the transition edges, while +/-10 Hz test rejection/preservation. Finite slopes necessarily overlap at the band edges. Use an FFT window long enough to resolve 1 Hz spacing. Playback voltage depends on the player.',
    frequencies_hz=frequencies,component_peak_dbfs=[float(20*np.log10(v)) for v in levels],
    source='Original analytic NumPy construction; test-bench/tools/add_selectivity_fixture.py',
    generator_sha256=hashlib.sha256(Path(__file__).read_bytes()).hexdigest(),numpy_version=np.__version__,
    license='CC0-1.0 (generated audio)',sha256=hashlib.sha256(path.read_bytes()).hexdigest())
manifest['files']=[r for r in manifest['files'] if r['file']!=name]+[record]
if tuple(map(int,manifest['version'].split('.'))) < (1,3,0):manifest['version']='1.3.0'
(out/'manifest.json').write_text(json.dumps(manifest,indent=2)+'\n',encoding='utf-8')
records=manifest['files']
with (out/'catalog.csv').open('w',newline='',encoding='utf-8') as handle:
    writer=csv.DictWriter(handle,fieldnames=['file','seconds','channels','kind','loop','peak_dbfs','rms_dbfs','purpose','notes','license','sha256'],extrasaction='ignore')
    writer.writeheader();writer.writerows(records)
catalog='# Audio file catalog\n\n48 kHz, 24-bit PCM WAV. Levels measured after quantization. Null means digital silence.\n\n'
for r in records:
    catalog+=f"## {r['file']}\n\n{r['seconds']:g} seconds; {r['channels']} channel(s); {r['kind']}.\n\nLoop: {r['loop']}. Peak dBFS: {r['peak_dbfs']}; RMS dBFS: {r['rms_dbfs']}.\n\n{r['purpose']}\n\n{r['notes']}\n\n"
(out/'CATALOG.md').write_text(catalog,encoding='utf-8')
(out/'all-files.m3u8').write_text('#EXTM3U\n'+'\n'.join(r['file'] for r in records)+'\n',encoding='utf-8')
print(json.dumps({'file':name,'total_files':len(records),'peak_dbfs':record['peak_dbfs'],'component_peak_dbfs':record['component_peak_dbfs'],'sha256':record['sha256'],'spectral_checks':'PASS'},indent=2))
