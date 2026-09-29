"""Generate InsectLabs' original, deterministic audio test bench (NumPy + stdlib)."""
from pathlib import Path
import argparse
import csv
import hashlib
import json
import math
import wave
import numpy as np

SR = 48000
VERSION = '1.0.0'
SEED = 19710510
RNG = np.random.default_rng(SEED)
parser = argparse.ArgumentParser()
parser.add_argument('--output', type=Path, required=True)
args = parser.parse_args()
OUT = args.output.resolve()
OUT.mkdir(parents=True, exist_ok=True)
records = []

def time(seconds):
    return np.arange(round(seconds * SR), dtype=np.float64) / SR

def db(value):
    return 10 ** (value / 20)

def normalized(x, peak_db):
    peak = np.max(np.abs(x))
    return x * (db(peak_db) / peak) if peak else x

def noise(seconds, slope=0, seed=None):
    """Periodic spectral noise; slope is power-spectrum exponent (pink=-1)."""
    n = round(seconds * SR)
    rng = np.random.default_rng(seed) if seed is not None else RNG
    spectrum = np.fft.rfft(rng.standard_normal(n))
    f = np.fft.rfftfreq(n, 1 / SR)
    spectrum[0] = 0
    spectrum[1:] *= np.maximum(f[1:], 10) ** (slope / 2)
    # Avoid unbounded infrasonic brown-noise energy; keep the reference DC-free.
    spectrum[f < 10] = 0
    return np.fft.irfft(spectrum, n)

def smooth_noise(seconds, cutoff, seed):
    n = round(seconds * SR)
    x = np.random.default_rng(seed).standard_normal(n)
    sp = np.fft.rfft(x)
    f = np.fft.rfftfreq(n, 1 / SR)
    sp *= np.exp(-(f / cutoff) ** 4)
    sp[0] = 0
    y = np.fft.irfft(sp, n)
    return y / max(np.max(np.abs(y)), 1e-15)

def write_pcm(path, x):
    x = np.asarray(x, dtype=np.float64)
    if x.ndim == 1:
        x = x[:, None]
    assert np.isfinite(x).all() and np.max(np.abs(x)) < 1
    # No dither: preserve exact digital silence, polarity, and stereo relationships.
    q = np.rint(x * 8388608).astype(np.int32)
    u = q.reshape(-1).astype(np.uint32)
    data = np.empty((len(u), 3), dtype=np.uint8)
    for i in range(3):
        data[:, i] = (u >> (8 * i)) & 255
    path.parent.mkdir(parents=True, exist_ok=True)
    with wave.open(str(path), 'wb') as wav:
        wav.setnchannels(x.shape[1])
        wav.setsampwidth(3)
        wav.setframerate(SR)
        wav.writeframes(data.tobytes())
    return q.astype(np.float64) / 8388608

def add(name, signal, purpose, kind='analytic', loop='periodic', level=None, notes=''):
    if level is not None:
        signal = normalized(signal, level)
    path = OUT / (name + '.wav')
    x = write_pcm(path, signal)
    peaks = np.max(np.abs(x), axis=0)
    rms = np.sqrt(np.mean(x * x, axis=0))
    to_db = lambda v: round(20 * math.log10(v), 5) if v > 0 else None
    correlation = None
    if x.shape[1] == 2 and np.std(x[:, 0]) > 0 and np.std(x[:, 1]) > 0:
        correlation = round(float(np.corrcoef(x.T)[0, 1]), 7)
    records.append(dict(
        file=path.relative_to(OUT).as_posix(), frames=len(x), seconds=len(x)/SR,
        channels=x.shape[1], sample_rate=SR, bits=24, kind=kind, loop=loop,
        peak_dbfs=[to_db(v) for v in peaks], rms_dbfs=[to_db(v) for v in rms],
        dc_mean=[float(v) for v in x.mean(axis=0)], correlation=correlation,
        boundary_step=[float(v) for v in np.abs(x[0]-x[-1])],
        purpose=purpose, notes=notes,
        sha256=hashlib.sha256(path.read_bytes()).hexdigest()))
    print(path.relative_to(OUT), flush=True)

# Calibration: peak levels are fixed, not loudness-normalized.
t = time(4)
for freq in [20, 50, 60, 100, 220, 415, 432, 440, 442, 1000, 3000, 8000, 12000]:
    add(f'01-calibration/sine_{freq:05d}Hz_minus12dBFS', db(-12)*np.sin(2*np.pi*freq*t),
        'Gain, frequency response, tuning, polarity, and steady-state distortion.',
        notes=f'{freq} Hz; fixed -12 dBFS sample peak, approximately -15.01 dBFS RMS. 4-second integer-cycle loop.')
t = time(8)
add('01-calibration/silence_8s', np.zeros(len(t)), 'Output noise, stale DSP state, offset, and bypass leakage.')
add('01-calibration/two_tone_440_443_beating', np.sin(2*np.pi*440*t)+np.sin(2*np.pi*443*t),
    'Slow beating, compression, gain pumping, and envelope following.', level=-12)
add('01-calibration/two_tone_700_1900_IMD', np.sin(2*np.pi*700*t)+np.sin(2*np.pi*1900*t),
    'Listen or measure for intermodulation products.', level=-12,
    notes='Equal-amplitude tones; reference, not a formal SMPTE/DIN IMD measurement.')
freqs = [50, 100, 250, 500, 1000, 2000, 4000, 8000, 12000, 16000]
multi = sum(np.sin(2*np.pi*f*t - np.pi*k*(k-1)/len(freqs)) for k,f in enumerate(freqs))
add('01-calibration/multitone_10bands', multi, 'Broadband response, saturation, and selective filtering.',
    level=-12, notes='Equal component amplitudes, phase-spread to reduce crest factor; frequencies 50,100,250,500,1000,2000,4000,8000,12000,16000 Hz.')
f0,f1,duration = 20,18000,12
t = time(duration)
phase = 2*np.pi*f0*duration/np.log(f1/f0)*((f1/f0)**(t/duration)-1)
fade = np.ones(len(t)); size=round(.02*SR)
fade[:size]=np.sin(np.linspace(0,np.pi/2,size))**2
fade[-size:]=fade[:size][::-1]
add('01-calibration/log_sweep_20Hz_18kHz_12s', db(-12)*np.sin(phase)*fade,
    'Find resonances, high-frequency aliasing, and bandwidth limits.', loop='faded restart',
    notes='20 ms end fades; loop deliberately restarts at 20 Hz. No deconvolution inverse is supplied.')

# Dynamics and modulation.
levels=[-48,-36,-24,-18,-12,-6,-6,-12,-18,-24,-36,-48]
segments=[]
for level in levels:
    tt=time(1); env=np.ones(len(tt)); count=round(.005*SR)
    env[:count]=np.sin(np.linspace(0,np.pi/2,count))**2;env[-count:]=env[:count][::-1]
    segments.append(db(level)*np.sin(2*np.pi*1000*tt)*env)
add('02-dynamics/sine_1kHz_level_staircase',np.concatenate(segments),
    'Compare level-dependent drive and output gain.', loop='faded restart',
    notes='One second per level: -48,-36,-24,-18,-12,-6,-6,-12,-18,-24,-36,-48 dBFS peak. 5 ms fades around each segment.')
t=time(8)
add('02-dynamics/sine_440Hz_AM_2Hz',db(-12)*np.sin(2*np.pi*440*t)*(.5-.5*np.cos(2*np.pi*2*t)),
    'Envelope following, dynamic modulation, and VCA response.', notes='Smooth 100% amplitude modulation at 2 Hz.')
gate=(np.mod(t,1)<.125).astype(float)
add('02-dynamics/tone_bursts_1kHz_125ms',db(-12)*np.sin(2*np.pi*1000*t)*gate,
    'Transient response, release tails, gating, and timing.', notes='125 ms burst every second; deliberately abrupt gate edges.')
imp=np.zeros(len(t));imp[::SR]=db(-18)
add('02-dynamics/impulses_1persecond_minus18dBFS',imp,
    'Impulse response, ringing, echoes, and bypass tails.',notes='Single-sample positive impulses; deliberately wideband.')
add('02-dynamics/sine_100Hz_plus_DC_0p1',.15*np.sin(2*np.pi*100*t)+.1,
    'Check DC preservation and offset handling in an audio file path.',
    notes='DC +0.1 FS, sine amplitude 0.15 FS. Importers/host paths may remove DC; use a native DC source for authoritative CV tests.')

for label,slope in [('white',0),('pink',-1),('brown',-2),('blue',1)]:
    add('03-noise/'+label+'_noise_8s',noise(8,slope),'Noise coloration, filtering, and saturation.',
        kind='seeded synthetic noise',level=-12,
        notes='Periodic FFT construction, DC removed, frequencies below 10 Hz removed; peak-normalized, not equal-RMS noise.')
t=time(8)
add('03-noise/pink_noise_AM_0p5Hz',noise(8,-1)*(.5-.5*np.cos(2*np.pi*.5*t)),
    'Slow envelope transfer and noise-level-dependent coloration.',kind='seeded synthetic noise',level=-12)
slow=smooth_noise(8,2,441)
frequency=440+35*slow
# Force an integral total cycle count for continuous periodic FM.
frequency += (round(frequency.sum()/SR)-frequency.sum()/SR)/8
phase=2*np.pi*np.concatenate(([0.],np.cumsum(frequency[:-1])/SR))
add('03-noise/sine_440Hz_random_FM',db(-12)*np.sin(phase),
    'Sine/random behavior, pitch fluctuation, and narrowband processing.',kind='seeded synthetic modulation',
    notes='Approximately 440 Hz carrier, +/-35 Hz deviation, smoothed random frequency modulation around 2 Hz.')
add('03-noise/noise_random_envelope',noise(8,0)*(.5+.5*smooth_noise(8,3,442)),
    'Irregular envelope following and amplitude selection.',kind='seeded synthetic noise',level=-12)

# Stereo identity checks. A silent channel contains literal digital zeros.
t=time(8);a=db(-12)*np.sin(2*np.pi*440*t);b=db(-12)*np.sin(2*np.pi*880*t);z=np.zeros(len(t))
for name,x,purpose in [
    ('left_only_440Hz',np.column_stack((a,z)),'Detect cross-channel leakage and routing errors.'),
    ('right_only_880Hz',np.column_stack((z,b)),'Detect cross-channel leakage and routing errors.'),
    ('left440_right880',np.column_stack((a,b)),'Identify channels by pitch and compare independent processing.'),
    ('dual_mono_correlated',np.column_stack((a,a)),'Pan law and mono-sum gain checks; L equals R.'),
    ('opposite_polarity',np.column_stack((a,-a)),'Polarity and cancellation checks; L plus R must null.'),
    ('alternating_channels_1s',np.column_stack((a*(np.floor(t)%2==0),b*(np.floor(t)%2==1))),
     'Alternating one-second left/right identification.')]:
    add('04-stereo/'+name,x,purpose)
add('04-stereo/independent_pink_noise',np.column_stack((noise(8,-1,800),noise(8,-1,801))),
    'Crossfade uncorrelated sources and inspect stereo independence.',kind='seeded synthetic noise',level=-12)

# Original synthesized performance sources: no external recordings or samples.
def pitch(note):
    return 440*2**((note-69)/12)

def note_sound(note,duration,instrument,velocity=1):
    tt=time(duration);f=pitch(note);y=np.zeros(len(tt))
    partials=min(28,int(21000/f))
    for k in range(1,partials+1):
        if instrument=='guitar':
            amp=np.sin(np.pi*k*.23)/(k**1.25)
            env=np.exp(-tt*(1.3+.18*k))
            y+=amp*env*np.sin(2*np.pi*f*k*tt + .13*k)
        elif instrument=='piano':
            freq=f*k*np.sqrt(1+0.00012*k*k)
            amp=(1 if k==1 else .7)/(k**1.35)
            env=np.exp(-tt*(.7+.12*k))
            y+=amp*env*(np.sin(2*np.pi*freq*tt)+.25*np.sin(2*np.pi*freq*1.0007*tt))
        else:
            y+=np.exp(-tt*3)*np.sin(2*np.pi*f*k*tt)/(k*k)
    attack=np.minimum(tt/(.004 if instrument=='guitar' else .008),1)
    y*=np.sin(attack*np.pi/2)**2
    end=min(len(y),round(.04*SR));y[-end:]*=np.linspace(1,0,end)**2
    return y*velocity

def place(track,sound,start):
    # Circular overlap-add preserves tails across the loop boundary.
    start=round(start*SR)%len(track); pos=0
    while pos<len(sound):
        count=min(len(sound)-pos,len(track)-start)
        track[start:start+count]+=sound[pos:pos+count];pos+=count;start=0

length=16
guitar=np.zeros(length*SR);piano=guitar.copy();bass=guitar.copy();drums=guitar.copy()
chords=[[45,52,57,60],[41,48,53,57],[48,55,60,64],[43,50,55,59]]
for bar in range(8):
    chord=chords[bar%4];start=bar*2
    for step in range(8):
        note=chord[[0,1,2,3,2,1,3,1][step]]+12
        place(guitar,note_sound(note,2.6,'guitar',.7 if step%2 else 1),start+step*.25)
    for beat in [0,1]:
        for note in chord:
            place(piano,note_sound(note+12,3.5,'piano',.55),start+beat)
    for step in range(4):
        place(piano,note_sound(chord[(step+bar)%4]+24,2,'piano',.38),start+step*.5+.25)
        place(bass,note_sound(chord[0]-12,.7,'bass',1),start+step*.5)
    for step in range(4):
        dt=time(.3)
        if step%2==0:
            drum=np.sin(2*np.pi*(45*dt+50*.025*(1-np.exp(-dt/.025))))*np.exp(-dt*18)
        else:
            drum=RNG.standard_normal(len(dt))*np.exp(-dt*28)*.35+np.sin(2*np.pi*180*dt)*np.exp(-dt*22)*.15
        drum[:100]*=np.linspace(0,1,100);drum[-100:]*=np.linspace(1,0,100)
        place(drums,drum,start+step*.5)
    for step in range(8):
        dt=time(.08);hat=RNG.standard_normal(len(dt));hat=np.concatenate(([0],np.diff(hat)))
        hat*=np.exp(-dt*75)*.035;hat[:20]*=np.linspace(0,1,20);hat[-100:]*=np.linspace(1,0,100)
        place(drums,hat,start+step*.25)
guitar=normalized(guitar,-9);piano=normalized(piano,-9);bass=normalized(bass,-12);drums=normalized(drums,-12)
music_notes='Original synthesized performance at 120 BPM, 8 bars/16 s, A minor-F-C-G. Not an instrument recording. No samples, external compositions, or reverb.'
add('05-musical/synthetic_clean_guitar_mono',guitar,'Plucked attacks, decay, and gain/offset behavior.',kind='original synthesized performance',notes=music_notes)
add('05-musical/synthetic_clean_piano_mono',piano,'Piano-like attacks, harmonic decay, and transient response.',kind='original synthesized performance',notes=music_notes)
add('05-musical/guitar_left_piano_right',np.column_stack((guitar,piano)),
    'Distinguishable sources for independent channels, swapping, and crossfading.',kind='original synthesized performance',notes=music_notes)
add('05-musical/piano_left_guitar_right',np.column_stack((piano,guitar)),
    'Reverse the musical channel-assignment check.',kind='original synthesized performance',notes=music_notes)
duo=np.column_stack((guitar+.25*piano,.25*guitar+piano))
add('05-musical/stereo_acoustic_style_duo',duo,'Musical crossfading, panning, and dynamics.',kind='original synthesized mix',level=-6,notes=music_notes)
mix=np.column_stack((.8*guitar+.35*piano+.55*bass+.6*drums,.35*guitar+.8*piano+.55*bass+.6*drums))
add('05-musical/stereo_full_music_mix',mix,'Dense full-band musical processing, transient/body balance, and bypass comparisons.',kind='original synthesized mix',level=-6,notes=music_notes)

# Outdoor-style textures, explicitly procedural rather than field recordings.
seconds=24;t=time(seconds)
wind1=noise(seconds,-2,901)*(1+.6*np.sin(2*np.pi*t/12))
wind2=noise(seconds,-2,902)*(1+.5*np.sin(2*np.pi*t/8+.6))
rain1=noise(seconds,0,903);rain2=noise(seconds,0,904)
ambient=np.column_stack((normalized(wind1,-18)+normalized(rain1,-26),normalized(wind2,-18)+normalized(rain2,-26)))
for i,start in enumerate([1.1,3.7,7.2,11.6,15.3,19.8,22.1]):
    tt=time(.65);en=np.sin(np.pi*np.arange(len(tt))/len(tt))**2
    chirp=np.sin(2*np.pi*((1700+100*(i%3))*tt+550*tt**2+12*np.sin(2*np.pi*5*tt)))*en*.025
    place(ambient[:,i%2],chirp,start)
add('06-ambience/synthetic_garden_wind_rain_birds',ambient,
    'Low-level stereo detail, noise texture, isolation, and envelope extraction.',kind='procedural outdoor-style ambience',level=-9,
    notes='Not a field recording. Independent wind/rain layers and synthesized bird-like chirps; circular 24 s scene.')
water=np.column_stack((noise(seconds,-1,905)*(1+.35*smooth_noise(seconds,7,907)),noise(seconds,-1,906)*(1+.35*smooth_noise(seconds,6,908))))
add('06-ambience/synthetic_stream_texture',water,'Continuous textured ambience and slow stereo dynamics.',
    kind='procedural outdoor-style ambience',level=-9,notes='Not a field recording. Filtered noise with independent smooth modulation; 24 s periodic texture.')

manifest={'collection':'InsectLabs Audio Test Bench','version':VERSION,'seed':SEED,
          'generator':'generate.py','numpy_version':np.__version__,
          'format':'WAV PCM signed 24-bit little-endian, 48000 Hz',
          'audio_rights':'Original generated audio dedicated under CC0-1.0; no third-party samples.',
          'files':records}
(OUT/'manifest.json').write_text(json.dumps(manifest,indent=2)+'\n')
with (OUT/'catalog.csv').open('w',newline='',encoding='utf-8') as stream:
    columns=['file','seconds','channels','kind','loop','peak_dbfs','rms_dbfs','purpose','notes','sha256']
    writer=csv.DictWriter(stream,fieldnames=columns,extrasaction='ignore');writer.writeheader();writer.writerows(records)
(OUT/'all-files.m3u8').write_text('#EXTM3U\n'+'\n'.join(r['file'] for r in records)+'\n')
with (OUT/'CATALOG.md').open('w',encoding='utf-8') as stream:
    stream.write('# Audio file catalog\n\nAll files: 48 kHz, 24-bit PCM WAV. Levels below are measured after quantization. `null` denotes digital silence (-infinity dBFS).\n\n')
    for r in records:
        stream.write(f"## {r['file']}\n\n- {r['seconds']:g} s; {r['channels']} channel(s); {r['kind']}; loop: {r['loop']}.\n- Peak dBFS: {r['peak_dbfs']}; RMS dBFS: {r['rms_dbfs']}.\n- Use: {r['purpose']}\n- Notes: {r['notes']}\n\n")
print(f'Generated {len(records)} files at {OUT}',flush=True)
