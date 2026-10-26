"""Check delivery bytes, calibration and exact stereo relationships."""
import argparse, hashlib, json, re, wave
from pathlib import Path
import numpy as np
p=argparse.ArgumentParser();p.add_argument('folder',type=Path);a=p.parse_args()
m=json.loads((a.folder/'manifest.json').read_text());signals={};checks=[]
keep_fragments={'silence','left_only','right_only','dual_mono','opposite_polarity',
                'recorded_guitar_left_piano_right','recorded_piano_left_guitar_right',
                'guitar_recording_mono','piano_recording_mono',
                'selectivity_1k_neighbours_8s',
                'model62_head_spacing_impulses_150Hz_minus18dBFS'}
keep_stems={Path(r['file']).stem for r in m['files']
             if any(part in Path(r['file']).stem for part in keep_fragments)}
keep_stems.update(Path(r['file']).stem for r in m['files']
                   if r.get('selectivity_probe') or r.get('musical_cycle') or
                   r.get('loop_edit') or r.get('circular_loop'))
keep_stems.update(Path(r['file']).stem for r in m['files']
                   if re.fullmatch(r'sine_\d+Hz_minus12dBFS',Path(r['file']).stem))
def check(condition, description):
    if not condition: raise AssertionError(description)
    checks.append(description)

def sha256_file(path):
    digest=hashlib.sha256()
    with path.open('rb') as stream:
        for chunk in iter(lambda:stream.read(1024*1024),b''):
            digest.update(chunk)
    return digest.hexdigest()

for r in m['files']:
    path=a.folder/r['file']
    check(sha256_file(path)==r['sha256'],r['file']+': SHA-256')
    tone=re.fullmatch(r'sine_(\d+)Hz_minus12dBFS',path.stem)
    keep=path.stem in keep_stems
    pieces=[] if keep else None
    peak=np.zeros(r['channels']);square_sum=np.zeros(r['channels']);sample_count=0
    first=last=None
    with wave.open(str(path)) as w:
        check((w.getframerate(),w.getsampwidth(),w.getnchannels(),w.getnframes())==(48000,3,r['channels'],r['frames']),r['file']+': format and duration')
        while True:
            raw=w.readframes(48000)
            if not raw: break
            b=np.frombuffer(raw,dtype=np.uint8).reshape(-1,3).astype(np.int32)
            q=b[:,0]|(b[:,1]<<8)|(b[:,2]<<16)
            q=np.where(q&0x800000,q-0x1000000,q).reshape(-1,r['channels'])
            if first is None: first=q[0].copy()
            last=q[-1].copy()
            peak=np.maximum(peak,np.max(np.abs(q),axis=0))
            square_sum+=np.sum(q.astype(np.float64)**2,axis=0)
            sample_count+=len(q)
            if keep: pieces.append(q)
    check(np.max(peak)/8388608<.999,r['file']+': no sample clipping')
    expected=np.array([10**(z/20) if z is not None else 0 for z in r['peak_dbfs']])
    check(np.allclose(peak/8388608,expected,atol=1e-6),r['file']+': measured peak agrees')
    rms=np.sqrt(square_sum/sample_count)/8388608
    if keep: signals[path.stem]=np.concatenate(pieces,axis=0)
    if tone:
        freq=int(tone[1]);x=signals[path.stem][:,0]/8388608;t=np.arange(len(x))/48000
        check(np.max(np.abs(x-10**(-12/20)*np.sin(2*np.pi*freq*t)))<=.51/8388608,r['file']+': calibrated sine within half a quantization step')
        check(abs(np.fft.rfftfreq(len(x),1/48000)[np.argmax(np.abs(np.fft.rfft(x)))]-freq)<.01,r['file']+': frequency')
    if r['file'].startswith('07-recordings/') and not r.get('loop_edit') and not r.get('circular_loop'):
        check(np.all(first==0) and np.all(last==0),r['file']+': faded endpoints')
for r in m['files']:
    if r.get('loop_edit'):
        q=signals[Path(r['file']).stem]
        edit=r['loop_edit']
        check(len(q)==edit['frames'],'Edited drum loop: exact musical-cycle length')
        check(edit['frames']==round(8*4*60/300*48000),'Edited drum loop: eight bars at 300 BPM')
        check(np.max(np.abs(q[0].astype(float)-q[-1]))/8388608 < 0.02,
              'Edited drum loop: wrap sample step below -34 dBFS')
        check(np.sqrt(np.mean((q[:480]/8388608)**2))>0.005,
              'Edited drum loop: no silence inserted at wrap')
    if r.get('circular_loop'):
        q=signals[Path(r['file']).stem]
        loop=r['circular_loop']
        check(len(q)==loop['frames'],'Circular recording loop: declared frame count')
        check(loop['sample_rate']==48000 and r['sample_rate']==48000,
              'Circular recording loop: sample-rate metadata agrees')
        check(loop['wrap_crossfade_frames']==round(0.010*48000),
              'Circular recording loop: 10 ms wrap crossfade')
        check(np.max(np.abs(q[0].astype(float)-q[-1]))/8388608<0.02,
              'Circular recording loop: wrap sample step below -34 dBFS')
        check(np.sqrt(np.mean(q[:,0].astype(float)**2))/8388608>0.005,
              'Circular recording loop: non-silent musical content')
    if r.get('musical_cycle'):
        q=signals[Path(r['file']).stem]
        cycle=r['musical_cycle']
        expected=round(cycle['bars_4_4']*4*60/cycle['bpm']*cycle['sample_rate'])
        check(len(q)==cycle['frames']==expected,
              'Generated musical loop: exact declared bar/tempo duration')
        check(cycle['sample_rate']==48000 and r['sample_rate']==48000,
              'Generated musical loop: sample-rate metadata agrees')
        check(float(np.max(np.abs(q[0].astype(float)-q[-1])))/8388608<0.02,
              'Generated musical loop: wrap step below -34 dBFS')
        check(float(np.sqrt(np.mean((q[:,0]/8388608)**2)))>0.01,
              'Generated musical loop: non-silent musical content')

def find(fragment):
    return next(v for k,v in signals.items() if fragment in k)
check(np.all(find('silence')==0),'Digital silence is exact')
check(np.all(find('left_only')[:,1]==0),'Left-only file has silent right')
check(np.all(find('right_only')[:,0]==0),'Right-only file has silent left')
x=find('dual_mono');check(np.array_equal(x[:,0],x[:,1]),'Dual mono nulls exactly when one side is inverted')
x=find('opposite_polarity');check(np.array_equal(x[:,0],-x[:,1]),'Opposite polarity cancels exactly when summed')
x=signals['recorded_guitar_left_piano_right'];y=signals['recorded_piano_left_guitar_right']
check(np.array_equal(x,y[:,::-1]),'Recorded channel-swap pair is exact')
check(np.array_equal(x[:,0],signals['guitar_recording_mono'][:,0]),'Split left matches mono guitar')
check(np.array_equal(x[:,1],signals['piano_recording_mono'][:,0]),'Split right matches mono piano')
if 'selectivity_1k_neighbours_8s' in signals:
    probe=signals['selectivity_1k_neighbours_8s'][:,0]
    check(np.array_equal(probe,np.tile(probe[:48000],8)),'Selectivity probe: exact one-second periodic construction')
    spectrum=abs(np.fft.rfft(probe[:48000]/8388608))*2/48000
    bins=[990,999,1000,1001,1010];components=spectrum[bins]
    check(np.ptp(components)<1e-8,'Selectivity probe: five equal-amplitude component levels')
    residual=spectrum.copy();residual[bins]=0
    check(np.sum(residual**2)<1e-10*np.sum(components**2),'Selectivity probe: residual spectral energy below -100 dB')
if 'model62_head_spacing_impulses_150Hz_minus18dBFS' in signals:
    probe=signals['model62_head_spacing_impulses_150Hz_minus18dBFS']
    pulse_frames=round(0.010*48000)
    starts=(0, 99072, 200736)
    gaps=(2592, 5184, 648)
    occupied=np.zeros(len(probe),dtype=bool)
    for start,gap in zip(starts,gaps):
        check(np.max(np.abs(probe[start:start+pulse_frames,0]))>0,
              f'Model 62 impulse pair at {start}: left lead pulse present')
        right_start=start+gap
        check(np.max(np.abs(probe[right_start:right_start+pulse_frames,1]))>0,
              f'Model 62 impulse pair at {start}: right pulse at expected head gap')
        occupied[start:start+pulse_frames]=True
        occupied[right_start:right_start+pulse_frames]=True
    check(np.all(probe[~occupied]==0),'Model 62 impulse fixture: silence outside six pulse windows')
    check(np.allclose(np.max(np.abs(probe),axis=0)/8388608,10**(-18/20),atol=1e-6),
          'Model 62 impulse fixture: fixed -18 dBFS peak in both channels')
check(len(list(a.folder.rglob('*.wav')))==len(m['files']),'No uncatalogued WAV files')
report={'result':'PASS','files':len(m['files']),'checks':len(checks),'details':checks,
        'scope':'File integrity and numerical audio checks. No host playback or subjective listening validation performed.'}
(a.folder/'QA.json').write_text(json.dumps(report,indent=2)+'\n')
print(json.dumps({k:v for k,v in report.items() if k!='details'}))
