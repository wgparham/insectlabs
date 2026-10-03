"""Import deterministic sn-16u callback captures after running its validation harness.

Raw captures are big-endian float64 volts at 48 kHz. They are transient build
products; the delivered audio and this importer preserve reproducible provenance.
"""
from pathlib import Path
import argparse,csv,hashlib,json,wave
import numpy as np
p=argparse.ArgumentParser()
p.add_argument('--captures',type=Path,default=Path('C:/InsectLabs-Build/sn16u/checks'))
p.add_argument('--output',type=Path,required=True)
p.add_argument('--version',required=True,help='Module version that produced these captures, e.g. v0.1.3a')
p.add_argument('--source',type=Path,required=True,help='Matching sn16u.java used by the harness')
a=p.parse_args();out=a.output
manifest=json.loads((out/'manifest.json').read_text(encoding='utf-8'))
def sha(path):return hashlib.sha256(path.read_bytes()).hexdigest()
def db(x):return [float(20*np.log10(v)) if v>0 else None for v in x]
noise=np.fromfile(a.captures/'noise.f64',dtype='>f8').reshape(-1,2)
assert len(noise)==12*48000
# Verify broad color trends on settled noise before fading/exporting it.
settled=noise[48000:];n=32768;window=np.hanning(n)
power=np.mean([abs(np.fft.rfft(settled[i:i+n]*window[:,None],axis=0))**2 for i in range(0,len(settled)-n+1,n//2)],axis=0)
f=np.fft.rfftfreq(n,1/48000)
bands=np.array([10*np.log10(power[(f>=lo)&(f<hi)].mean(axis=0)) for lo,hi in [(50,100),(200,400),(1000,2000),(4000,8000),(10000,18000)]])
assert np.all(np.diff(bands[:,0])<0) and np.all(np.diff(bands[:,1])>0),'Noise color slope'
fixtures=[]
for mode,name in [(0,'linear'),(1,'exponential')]:
 x=np.fromfile(a.captures/f'sweep-{mode}.f64',dtype='>f8')[:,None]
 assert len(x)==42*48000 and x[0,0]==0 and x[-1,0]==0
 fixtures.append((f'01-calibration/sn16u_{name}_sweep_0p01Hz_23760Hz_42s.wav',x,
  f'{name.capitalize()} sweep captured from sn-16u {a.version} DSP',
  'Check frequency response, sweep behavior and high-frequency handling.',
  'Full 42-second callback output, 0.01 Hz to 23760 Hz; original 5 ms endpoint fades retained. Nominal 5 V peak becomes 0.5 full scale; no resampling or normalization. Not a loop.'))
for channel,name in enumerate(['pink','blue']):
 x=settled[:,channel:channel+1].copy();fade=np.linspace(0,1,2400)
 x[:2400]*=fade[:,None];x[-2400:]*=fade[::-1,None]
 fixtures.append((f'03-noise/sn16u_{name}_noise_11s.wav',x,
  f'{name.capitalize()} noise captured from sn-16u {a.version} DSP',
  'Compare clean reference noise colors and measure approximate spectral response.',
  'Seed 19710510; first second discarded for settling; 50 ms endpoint fades added. Pink and blue come from the same run and are correlated. Original relative voltage levels retained; not peak or RMS normalized and not seamless. Pink uses the Paul Kellett filter; blue differentiates its output.'))
for name,volts,kind,purpose,notes in fixtures:
 x=volts*.1
 assert np.all(np.isfinite(x)) and np.max(abs(x))<.999
 q=np.rint(x*8388608).astype(np.int32);x=q/8388608
 packed=np.stack((q&255,(q>>8)&255,(q>>16)&255),axis=-1).astype(np.uint8)
 path=out/name;path.parent.mkdir(parents=True,exist_ok=True)
 with wave.open(str(path),'wb') as w:
  w.setnchannels(1);w.setsampwidth(3);w.setframerate(48000);w.writeframes(packed.tobytes())
 record=dict(file=name,frames=len(x),seconds=len(x)/48000,channels=1,sample_rate=48000,bits=24,kind=kind,loop='Not seamless; faded endpoints',peak_dbfs=db(abs(x).max(axis=0)),rms_dbfs=db(np.sqrt((x*x).mean(axis=0))),dc_mean=x.mean(axis=0).tolist(),correlation=None,boundary_step=abs(x[0]-x[-1]).tolist(),purpose=purpose,notes=notes+' Export scale: 0.1 full scale per volt; actual playback voltage depends on the player.',source=f'sn-16u {a.version} actual ProcessSample callback harness; tests/validate.py',source_sha256=sha(a.source),license='CC0-1.0 (generated audio)',sha256=sha(path))
 manifest['files']=[r for r in manifest['files'] if r['file']!=name]+[record]
manifest['version']='1.2.0'
(out/'manifest.json').write_text(json.dumps(manifest,indent=2)+'\n',encoding='utf-8')
records=manifest['files']
with (out/'catalog.csv').open('w',newline='',encoding='utf-8') as h:
 writer=csv.DictWriter(h,fieldnames=['file','seconds','channels','kind','loop','peak_dbfs','rms_dbfs','purpose','notes','license','sha256'],extrasaction='ignore');writer.writeheader();writer.writerows(records)
catalog='# Audio file catalog\n\n48 kHz, 24-bit PCM WAV. Levels measured after quantization. Null means digital silence.\n\n'
for r in records:
 catalog+=f"## {r['file']}\n\n{r['seconds']:g} seconds; {r['channels']} channel(s); {r['kind']}.\n\nLoop: {r['loop']}. Peak dBFS: {r['peak_dbfs']}; RMS dBFS: {r['rms_dbfs']}.\n\n{r['purpose']}\n\n{r['notes']}\n\n"
(out/'CATALOG.md').write_text(catalog,encoding='utf-8')
(out/'all-files.m3u8').write_text('#EXTM3U\n'+'\n'.join(r['file'] for r in records)+'\n',encoding='utf-8')
print(json.dumps({'added_or_refreshed':len(fixtures),'total':len(records),'source_sha256':sha(a.source),'noise_band_psd_db_pink_blue':bands.tolist()},indent=2))
