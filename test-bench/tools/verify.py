"""Check delivery bytes, calibration and exact stereo relationships."""
import argparse, hashlib, json, re, wave
from pathlib import Path
import numpy as np
p=argparse.ArgumentParser();p.add_argument('folder',type=Path);a=p.parse_args()
m=json.loads((a.folder/'manifest.json').read_text());signals={};checks=[]
def check(condition, description):
    if not condition: raise AssertionError(description)
    checks.append(description)
for r in m['files']:
    path=a.folder/r['file']
    check(hashlib.sha256(path.read_bytes()).hexdigest()==r['sha256'],r['file']+': SHA-256')
    with wave.open(str(path)) as w:
        check((w.getframerate(),w.getsampwidth(),w.getnchannels(),w.getnframes())==(48000,3,r['channels'],r['frames']),r['file']+': format and duration')
        b=np.frombuffer(w.readframes(w.getnframes()),dtype=np.uint8).reshape(-1,3).astype(np.int32)
    q=b[:,0]|(b[:,1]<<8)|(b[:,2]<<16);q=np.where(q&0x800000,q-0x1000000,q).reshape(-1,r['channels'])
    x=q/8388608;signals[path.stem]=q
    check(np.max(np.abs(x))<.999,r['file']+': no sample clipping')
    peak=np.max(np.abs(x),axis=0)
    expected=np.array([10**(z/20) if z is not None else 0 for z in r['peak_dbfs']])
    check(np.allclose(peak,expected,atol=1e-6),r['file']+': measured peak agrees')
    tone=re.fullmatch(r'sine_(\d+)Hz_minus12dBFS',path.stem)
    if tone:
        freq=int(tone[1]);t=np.arange(len(x))/48000
        check(np.max(np.abs(x[:,0]-10**(-12/20)*np.sin(2*np.pi*freq*t)))<=.51/8388608,r['file']+': calibrated sine within half a quantization step')
        check(abs(np.fft.rfftfreq(len(x),1/48000)[np.argmax(np.abs(np.fft.rfft(x[:,0])))]-freq)<.01,r['file']+': frequency')
    if r['file'].startswith('07-recordings/'):
        check(np.all(q[[0,-1]]==0),r['file']+': faded endpoints')
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
