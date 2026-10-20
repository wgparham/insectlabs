"""Compile the actual pair and run extracted user-region DSP/alias checks."""
from pathlib import Path
import sys,subprocess,json,hashlib,struct
import numpy as np

dev=Path(__file__).resolve().parents[1]
repo=next(p for p in dev.parents if (p/'dsp-primitives').exists())
sys.path.insert(0,str(repo/'voltage-modular/colorbox/tools'))
from value_tree import Reader,encode,props
from support import regions,normalized
build=repo/'tmp/osc-checks'; build.mkdir(parents=True,exist_ok=True)
java=Path('C:/Program Files/Java/jdk-27/bin')
result_output=dev/'tests' if '--record-results' in sys.argv else build
source=(dev/'osc.java').read_text(encoding='utf-8-sig')
raw=(dev/'osc.vmod').read_bytes();reader=Reader(raw);tree=reader.tree()
assert reader.p==len(raw) and encode(tree)==raw
embedded=props(next(n for n in tree[2] if n[0]=='module code'))['MODULE.SOURCE']
assert normalized(embedded)==normalized(source)
assert props(tree)['notes']=='v1.0.0'
for name,digest in json.loads((dev/'SHA256.json').read_text(encoding='utf-8')).items():
    assert hashlib.sha256((dev/name).read_bytes()).hexdigest()==digest,name
controls=next(n for n in tree[2] if n[0]=='controls')[2]
assert len(set(props(n)['UUID'] for n in controls))==len(controls)
approved=json.loads((dev/'tests/APPROVED-METADATA.json').read_text(encoding='utf-8'))
by_uuid={props(n)['UUID']:props(n) for n in controls}
assert len(by_uuid)==len(approved['controls'])
for old in approved['controls']:
    current=by_uuid[old['UUID']]
    for key,value in old.items():
        if key=='variable name':value=approved['renames'].get(value,value)
        assert current.get(key)==value,(old['variable name'],key)
for key,value in approved['module'].items():assert props(tree)[key]==value
frequency=next(props(n) for n in controls if props(n).get('variable name')=='knobFrequency')
expected=np.log(261.6255653005986)/np.log(15000)
assert abs(float(frequency['defaultValueString'])-expected)<1e-14
assert frequency['defaultValue']==float(frequency['defaultValueString'])
assert source.count('super.Destroy();')==1
parts=regions(source)
template=(dev/'tests/harness.template').read_text()
for tag,key in [('CORE','code-and-variables'),('PROCESS','ProcessSample'),('BYPASS','ProcessBypassedSample'),('RECALL','SetStateInformation'),('TOOLTIP','GetTooltipText'),('EDIT','EditComponentValue')]:
    template=template.replace('@@'+tag+'@@',parts[key])
assert '@@' not in template
for name,text in [('export',source),('embedded',embedded)]:
    folder=build/name;folder.mkdir(exist_ok=True);path=folder/'osc.java';path.write_text(text,encoding="utf-8")
    subprocess.run([str(java/'javac.exe'),'--release','17','-Xlint:all','-Werror','-encoding','UTF-8','-cp','C:/ProgramData/Voltage/voltage.jar','-d',str(folder),str(path)],check=True)
path=build/'OscChecks.java';path.write_text(template,encoding="utf-8")
subprocess.run([str(java/'javac.exe'),'--release','17','-encoding','UTF-8','-Xlint:all','-Werror','-d',str(build),str(repo/'dsp-primitives/src/com/insectlabs/dsp/oscillators/HybridWaveforms48.java'),str(path)],check=True)
run=subprocess.run([str(java/'java.exe'),'-cp',str(build),'OscChecks',str(build)],text=True,capture_output=True)
if run.returncode:
    print(run.stdout)
parity_run=subprocess.check_output([str(java/'java.exe'),'-cp',str(build),'OscChecks',str(build),'parity'],text=True).strip()
assert parity_run==json.loads((dev/'tests/CLEANUP-PARITY.json').read_text())['result']
print(parity_run);print(run.stderr);run.check_returncode()
print(run.stdout)
parity_run=subprocess.check_output([str(java/'java.exe'),'-cp',str(build),'OscChecks',str(build),'parity'],text=True).strip()
assert parity_run==json.loads((dev/'tests/CLEANUP-PARITY.json').read_text())['result']
print(parity_run)
cpu_run=subprocess.run([str(java/'java.exe'),'-cp',str(build),'OscChecks',str(build),'cpu-tz'],check=True,text=True,capture_output=True)
print(cpu_run.stdout)
(result_output/'cpu-linear-fm.txt').write_text(cpu_run.stdout,encoding='utf-8')
rows=[]
with (build/'static.bin').open('rb') as f:
    size,count=struct.unpack('>ii',f.read(8))
    for _ in range(count):
        bin_,shape=struct.unpack('>id',f.read(12))
        audio=np.frombuffer(f.read(size*3*8),dtype='>f8').reshape(size,3)
        spectrum=np.abs(np.fft.rfft(audio,axis=0))**2
        intended=np.zeros(size//2+1,dtype=bool)
        intended[bin_::bin_]=True
        unwanted=~intended;unwanted[0]=False
        for channel,name in enumerate(['sine','triangle','pulse']):
            ratio=spectrum[unwanted,channel].sum()/max(1e-200,spectrum[intended,channel].sum())
            rows.append(dict(frequency_hz=48000*bin_/size,shape=shape,channel=name,alias_db=10*np.log10(max(1e-200,ratio)),dc_volts=float(audio[:,channel].mean())))
worst=max(rows,key=lambda row:row['alias_db'])
modulation=[]
x=np.arange(159)-79
coeff=(44000.0/96000.0)*np.sinc((44000.0/96000.0)*x)*np.kaiser(159,8.6)
coeff/=coeff.sum()
for case in range(8):
    with (build/f'mod-{case}.bin').open('rb') as f:
        count,factor=struct.unpack('>ii',f.read(8))
        settings=struct.unpack('>dddd',f.read(32))
        actual=np.frombuffer(f.read(),dtype='>f8').reshape(count,3)
    reference=np.memmap(build/f'reference-{case}.bin',dtype='>f8',mode='r',shape=(count*factor,3))
    impulse=np.zeros(count*factor);impulse[np.arange(159)*(factor//2)]=coeff
    transfer=np.fft.rfft(impulse)
    upperX=np.arange(31)-15
    upper=0.5*np.sinc(0.5*upperX)*np.kaiser(31,8.6);upper/=upper.sum()
    impulse.fill(0);impulse[np.arange(31)*(factor//4)]=upper
    transfer*=np.fft.rfft(impulse)
    transfer[count//2+1:]=0  # Ideal final Nyquist exclusion, retaining actual FIR response below it.
    for channel,name in enumerate(['sine','triangle','pulse']):
        filtered=np.fft.irfft(np.fft.rfft(reference[:,channel])*transfer)[factor-1::factor]
        target=filtered[8192:-8192]; signal=actual[8192:-8192,channel]
        window=np.hanning(len(target))
        error=np.sum(((signal-target)*window)**2)
        power=np.sum(((target-target.mean())*window)**2)
        db=10*np.log10(max(1e-200,error/max(1e-200,power)))
        modulation.append(dict(case=case,carrier_hz=settings[0],modulator_hz=settings[1],linear_fm_peak_volts=settings[2],shape_cv_volts=settings[3],channel=name,reference_error_db=float(db)))
    del reference
result={'method':'Coherent 32768-sample FFT after 2048 warmup samples; sum of all non-DC/non-harmonic power relative to intended harmonic power. Drift and shape-to-pitch coupling disabled only to make the tones coherent. Actual voiced waveform functions and actual 4x/two-stage output decimators are exercised.', 'static_worst':worst,'rows':rows,'modulation_method':'Actual 4x output versus a 32x evaluation of the same waveform model and signed linear-FM phase integral, filtered to host Nyquist with the actual two-stage FIR response retained in-band. Native CV samples have identical linear interpolation in both. Crop 8192 host samples at each end and apply a Hann window. Total reference-error power includes aliasing and modulation/interpolation differences; it is not a pure alias-power measurement.', 'modulation':modulation,'harness_output':run.stdout,'source_sha256':hashlib.sha256((dev/'osc.java').read_bytes()).hexdigest()}
result['through_zero_cpu_comparison']=cpu_run.stdout
(result_output/'results.json').write_text(json.dumps(result,indent=2)+'\n',encoding='utf-8')
print('STATIC WORST:',worst)
for name in ['sine','triangle','pulse']:
    print(name,max((r for r in rows if r['channel']==name),key=lambda r:r['alias_db']))
for case in range(8):print('MODULATION',case,[round(r['reference_error_db'],2) for r in modulation if r['case']==case])
assert worst['alias_db'] < -100, 'Static alias rejection target (-100 dB) not met'
assert max(r['reference_error_db'] for r in modulation if r['case']<2)<-80,'FM reference-error budget'
assert max(r['reference_error_db'] for r in modulation if r['case'] in (2,3))<-50,'Fast shape reference-error budget'
assert max(r['reference_error_db'] for r in modulation if r['case']==4)<-45,'Extreme combined reference-error budget'
assert max(r['reference_error_db'] for r in modulation if r['case']>=5)<-55,'Through-zero reference-error budget'
print('THROUGH ZERO:',[round(r['reference_error_db'],2) for r in modulation if r['case']>=5])
print('PASS: pair/source/SDK/metadata/geometry + headless DSP + static alias rejection')
