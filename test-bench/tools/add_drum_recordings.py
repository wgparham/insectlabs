"""Import the pinned CC0 drum recordings into TestBench (NumPy and FFmpeg).

Usage: python add_drum_recordings.py --output test-bench/v1
       --sources test-bench/sources/drum-loops --ffmpeg <ffmpeg.exe>
Inputs are original Freesound files, pinned by source metadata and checksums.
"""
import argparse,csv,hashlib,json,subprocess,wave
from pathlib import Path
import numpy as np

RATE=48000
FADE=144  # 3 ms endpoints; preserve transients elsewhere.
TARGET_DBFS=-9
FIELDS=['file','seconds','channels','kind','loop','peak_dbfs','rms_dbfs','purpose','notes','license','sha256']

def update(output,sources,ffmpeg):
 manifest=json.loads((output/'manifest.json').read_text(encoding='utf-8'))
 source_list=json.loads((sources/'SOURCES.json').read_text(encoding='utf-8'))
 names={s['delivery'] for s in source_list}
 files=[r for r in manifest['files'] if r['file'] not in names]
 for source in source_list:
  path=sources/source['filename']
  assert hashlib.sha256(path.read_bytes()).hexdigest()==source['sha256'],f'Source changed: {path}'
  result=subprocess.run([ffmpeg,'-v','error','-i',str(path),'-ac','2','-ar',str(RATE),'-f','f64le','-acodec','pcm_f64le','pipe:1'],capture_output=True,check=True)
  audio=np.frombuffer(result.stdout,dtype='<f8').reshape(-1,2).copy()
  assert len(audio)>2*FADE and np.isfinite(audio).all()
  edit=source.get('loop_edit')
  if edit:
   start=edit['start_frame'];frames=edit['frames'];blend=edit['wrap_crossfade_frames']
   assert start>=blend and start+frames<=len(audio) and 0<blend<frames//2
   before=audio[start-blend:start].copy()
   audio=audio[start:start+frames].copy()
   mix=np.linspace(0,1,blend)[:,None]
   audio[-blend:]=audio[-blend:]*(1-mix)+before*mix
   audio-=audio.mean(axis=0)
  else:
   audio-=audio.mean(axis=0)
   fade=np.linspace(0,1,FADE)
   audio[:FADE]*=fade[:,None];audio[-FADE:]*=fade[::-1,None]
  audio*=10**(TARGET_DBFS/20)/np.max(np.abs(audio))
  q=np.rint(audio*8388608).astype(np.int32)
  u=q.reshape(-1).astype(np.uint32)
  packed=np.stack([(u>>shift)&255 for shift in (0,8,16)],axis=1).astype(np.uint8).tobytes()
  dest=output/source['delivery'];dest.parent.mkdir(parents=True,exist_ok=True)
  with wave.open(str(dest),'wb') as w:
   w.setnchannels(2);w.setsampwidth(3);w.setframerate(RATE);w.writeframes(packed)
  x=q/8388608
  db=lambda values:[float(20*np.log10(v)) if v else None for v in values]
  record=dict(file=source['delivery'],frames=len(x),seconds=len(x)/RATE,channels=2,sample_rate=RATE,bits=24,
   kind='third-party drum-loop recording',loop=('6.4-second repeating musical cycle; 8 bars in 4/4 at 300 BPM (or 4 bars at half-time 150 BPM); 10 ms wrap-tail blend, no added silence' if edit else 'Complete source loop with 3 ms faded restart; musical seam not guaranteed seamless'),
   peak_dbfs=db(np.max(np.abs(x),axis=0)),rms_dbfs=db(np.sqrt(np.mean(x*x,axis=0))),
   dc_mean=x.mean(axis=0).tolist(),correlation=float(np.corrcoef(x.T)[0,1]),boundary_step=np.abs(x[0]-x[-1]).tolist(),
   purpose=source['purpose'],notes=f"CC0 source: {source['source_format']}. Decoded/resampled to stereo 48 kHz, mean removed, {'10 ms wrap-tail blend' if edit else '3 ms endpoint fades'}, linked peak adjusted to -9 dBFS, rounded to 24-bit PCM without dither. No compression or additional coloration. Conversion does not restore source precision or MP3 losses. See ATTRIBUTION.md.",
   sources=[source['id']],license='CC0-1.0',source_sha256=source['sha256'],sha256=hashlib.sha256(dest.read_bytes()).hexdigest())
  if edit: record['loop_edit']=edit
  files.append(record)
  print(json.dumps({k:record[k] for k in ('file','seconds','peak_dbfs','rms_dbfs')}))
 manifest['files']=files;manifest['version']='1.5.1'
 old_sources=manifest.get('sources',[]);ids={s['id'] for s in source_list}
 manifest['sources']=[s for s in old_sources if s['id'] not in ids]+source_list
 (output/'manifest.json').write_text(json.dumps(manifest,indent=2)+'\n',encoding='utf-8')
 with (output/'catalog.csv').open('w',newline='',encoding='utf-8') as f:
  writer=csv.DictWriter(f,fieldnames=FIELDS,extrasaction='ignore');writer.writeheader();writer.writerows(files)
 text='# Audio file catalog\n\n48 kHz, 24-bit PCM WAV. Levels measured after quantization. Null means digital silence.\n\n'
 for r in files:
  text+=f"## {r['file']}\n\n{r['seconds']:g} seconds; {r['channels']} channel(s); {r['kind']}.\n\nLoop: {r['loop']}. Peak dBFS: {r['peak_dbfs']}; RMS dBFS: {r['rms_dbfs']}.\n\n{r['purpose']}\n\n{r['notes']}\n\n"
 (output/'CATALOG.md').write_text(text,encoding='utf-8')
 (output/'all-files.m3u8').write_text('#EXTM3U\n'+'\n'.join(r['file'] for r in files)+'\n',encoding='utf-8')
 credits=output/'ATTRIBUTION.md';text=credits.read_text(encoding='utf-8').split('## Drum loops added in 1.5.0')[0].rstrip()+'\n\n## Drum loops added in 1.5.0\n\n'
 for s in source_list:
  text+=f"[{s['title']}]({s['url']}) by **{s['author']}**, [CC0 1.0]({s['license_url']}). Source page and license checked {s['checked']}. [Original download]({s['download_url']}); SHA-256 `{s['sha256']}`.\n\n"
 text+='The complete original source is retained in sources/drum-loops with its source-page snapshot and metadata. Delivery files are converted and level adjusted as recorded in the catalog. Original Hip-Hop WAV is lossless 16-bit PCM; original Metal recording is 320 kbps MP3. Earlier HQ previews remain as provenance only. These are sourced recordings, not generated percussion or copies of commercial tracks. Metal delivery uses the 6.4-second cycle from the sample-aligned phrase boundary near 3.2 seconds, with a 10 ms wrap-tail blend; the original recording remains intact. No fair-use exception is relied upon.\n'
 credits.write_text(text,encoding='utf-8')

if __name__=='__main__':
 p=argparse.ArgumentParser();p.add_argument('--output',type=Path,required=True);p.add_argument('--sources',type=Path,required=True);p.add_argument('--ffmpeg',required=True);a=p.parse_args();update(a.output,a.sources,a.ffmpeg)
