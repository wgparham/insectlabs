"""Compile both n01 source forms and exercise actual callbacks without a GUI host."""
from pathlib import Path
import sys,subprocess,os,re,json
sys.dont_write_bytecode=True
repo=next((p for p in Path(__file__).resolve().parents if (p/'voltage-modular/colorbox/tools').is_dir()),Path(r'C:\Users\wgparham\Dropbox\git\insectlabs'))
sys.path.insert(0,str(repo/'voltage-modular/colorbox/tools'))
from value_tree import Reader,props,encode
from support import normalized,regions
base=Path(__file__).resolve().parent
module=base if (base/'n01.java').exists() else base.parent
source=(module/'n01.java').read_text(encoding='utf-8-sig');r=regions(source)
raw=(module/'n01.vmod').read_bytes();reader=Reader(raw);tree=reader.tree()
assert reader.p==len(raw) and encode(tree)==raw
embedded=props(next(n for n in tree[2] if n[0]=='module code'))['MODULE.SOURCE']
assert normalized(source)==normalized(embedded)
assert 'Version: v1.0.0' in props(tree)['notes']
controls=next(n for n in tree[2] if n[0]=='controls')[2]
assert len({props(n)['UUID'] for n in controls})==len(controls)
for n in controls:
    p=props(n);name=p['variable name']
    assert re.fullmatch('[a-z][a-zA-Z0-9]*',name),name
    assert p['CEditableControl.name']==name
    assert p['display name']!=name
    if name.endswith('Knob'):
        assert p['defaultValue']==float(p['defaultValueString'])==.5
assert source.count('super.Destroy();')==1
assert source.count('StopGuiUpdateTimer();')==1  # Designer regenerates base-call ordering.
assert len(r['ProcessSample'])>1000 and 'new ' not in r['ProcessSample']
build=Path(r'C:\InsectLabs-Build\n01\checks');build.mkdir(parents=True,exist_ok=True)
ep=build/'embedded/n01.java';ep.parent.mkdir(exist_ok=True);ep.write_text(embedded,encoding='utf-8')
sdk=Path(os.environ.get('PROGRAMDATA',r'C:\ProgramData'))/'Voltage/voltage.jar'
for label,p in [('export',module/'n01.java'),('embedded',ep)]:
    subprocess.run(['javac','--release','17','-encoding','UTF-8','-Xlint:all','-Werror','-cp',str(sdk),'-d',str(build/label),str(p)],check=True)
h='''public class NoiseChecks extends MockModule {
 static class VoltageComponent {
   double value; boolean connected; int reads;
   double GetValue() { reads++;return value; }
   void SetValue(double v) { value=v; }
   boolean IsConnected() { reads++;return connected; }
 }
 enum ModuleNotifications { Button_Changed, GUI_Update_Timer, Reset, Preset_Loading_Finish, Variation_Loading_Finish }
'''
names=['redKnob','blueKnob','randomRateKnob','randomLevelKnob','whiteOutput','spectraOutput','slowRandomOutput','sampleSourceOutput','steppedOutput','triggerInput','manualTriggerButton','negativeLed','positiveLed']
h+='\n'.join(' VoltageComponent '+n+'=new VoltageComponent();' for n in names)
for name,signature in [('Initialize','void initialize()'),('ProcessSample','void process()'),('ProcessBypassedSample','void bypass()'),('Notify','boolean notify(VoltageComponent component,ModuleNotifications notification,double doubleValue)'),('GetStateInformation','byte[] state()'),('SetStateInformation','void restore(byte[] stateInfo)'),('GetTooltipText','String tooltip(VoltageComponent component)'),('EditComponentValue','void edit(VoltageComponent component,double newValue,String newText)')]:
    h+='\n'+signature+' {\n'+r[name]
    if name=='EditComponentValue':h+='\nsuper.EditComponentValue(component,newValue,newText);'
    h+='\n}\n'
h+=r['code-and-variables']
h+='''
 static int checks;
 static void check(boolean b,String message) { checks++;if(!b)throw new AssertionError(message); }
 static void near(double a,double b,double tol,String message) { check(Math.abs(a-b)<=tol,message+": "+a+" / "+b); }
 static NoiseChecks unit() {
   NoiseChecks a=new NoiseChecks();a.noiseState=1234;a.rampState=5678;
   a.redKnob.value=a.blueKnob.value=a.randomRateKnob.value=a.randomLevelKnob.value=.5;
   return a;
 }
 void run(int n) {for(int i=0;i<n;i++)process();}
 void press() {notify(manualTriggerButton,ModuleNotifications.Button_Changed,1);}
 void release() {notify(manualTriggerButton,ModuleNotifications.Button_Changed,0);}
 public static void main(String[] args) {
   NoiseChecks a=unit();a.initialize();a.run(4800);
   near(a.steppedOutput.value,0,0,"no automatic sampling");
   a.press();a.process();near(a.steppedOutput.value,a.sampleSourceOutput.value,0,"manual captures source");
   double held=a.steppedOutput.value;a.press();a.run(2000);near(a.steppedOutput.value,held,0,"held button and repeated down event do not resample");
   a.release();a.press();a.process();check(a.steppedOutput.value!=held,"new press resamples");a.release();
   a.triggerInput.connected=true;a.triggerInput.value=5;a.process();near(a.steppedOutput.value,a.sampleSourceOutput.value,0,"external rising edge captures");
   held=a.steppedOutput.value;a.run(1000);near(a.steppedOutput.value,held,0,"high gate holds");
   a.triggerInput.value=.5;a.process();a.triggerInput.value=5;a.process();near(a.steppedOutput.value,held,0,"hysteresis rejects midlevel retrigger");
   a.triggerInput.value=0;a.process();a.triggerInput.value=5;a.process();check(a.steppedOutput.value!=held,"low rearms");
   a.triggerInput.value=Double.NaN;a.process();a.triggerInput.value=5;a.process();check(Double.isFinite(a.steppedOutput.value),"nonfinite trigger safely rearms");
   byte[] saved=a.state();held=a.heldVoltage;NoiseChecks b=unit();b.restore(saved);b.process();near(b.steppedOutput.value,held,0,"held value restore");
   b.restore(new byte[]{2});b.process();near(b.steppedOutput.value,held,0,"invalid state ignored");
   b.notify(null,ModuleNotifications.Reset,0);b.process();near(b.steppedOutput.value,0,0,"reset held value");
   a=unit();a.run(1000);a.press();a.process();held=a.heldVoltage;
   long ns=a.noiseState,rs=a.rampState;double phase=a.rampPhase,filter=a.driftSecond;
   int reads=a.redKnob.reads+a.blueKnob.reads+a.randomRateKnob.reads+a.randomLevelKnob.reads+a.triggerInput.reads;
   for(int i=0;i<1000;i++)a.bypass();
   near(a.whiteOutput.value+a.spectraOutput.value+a.slowRandomOutput.value+a.sampleSourceOutput.value+a.steppedOutput.value,0,0,"silent bypass all outputs");
   check(a.noiseState==ns && a.rampState==rs && a.rampPhase==phase && a.driftSecond==filter,"bypass freezes histories");
   check(reads==a.redKnob.reads+a.blueKnob.reads+a.randomRateKnob.reads+a.randomLevelKnob.reads+a.triggerInput.reads,"bypass reads no controls or trigger");
   a.release();a.press();a.triggerInput.connected=true;a.triggerInput.value=5;a.process();near(a.heldVoltage,held,0,"bypass discards presses and suppresses high resume gate");
   a.triggerInput.value=0;a.process();a.triggerInput.value=5;a.process();near(a.steppedOutput.value,a.sampleSourceOutput.value,0,"trigger rearms after resume");
   a=unit();b=unit();b.redKnob.value=0;b.blueKnob.value=1;b.randomRateKnob.value=1;b.randomLevelKnob.value=0;
   for(int i=0;i<48000;i++) {a.process();b.process();near(a.whiteOutput.value,b.whiteOutput.value,0,"white independent of controls");near(b.slowRandomOutput.value,0,0,"zero level silent");}
   b=unit();b.redKnob.value=b.blueKnob.value=0;b.run(10000);near(b.spectraOutput.value,0,0,"both colors zero");near(b.slowRandomOutput.value,0,0,"slow random derived from spectra");
   a=unit();a.run(48000);double sum=0,squares=0,stepSquares=0,prev=0;int[] bins=new int[10];
   for(int i=0;i<480000;i++) {
     a.process();double w=a.whiteOutput.value;sum+=w;squares+=w*w;stepSquares+=(w-prev)*(w-prev);prev=w;
     bins[Math.min(9,(int)((a.sampleSourceOutput.value+5)))]++;
     check(Math.abs(w)<=4.9 && Math.abs(a.spectraOutput.value)<11.112 && Math.abs(a.slowRandomOutput.value)<=2.5 && Math.abs(a.sampleSourceOutput.value)<=5,"finite bounded outputs");
   }
   double rms=Math.sqrt(squares/480000);check(Math.abs(sum/480000)<.02 && rms>1.4 && rms<2,"white DC and RMS");
   for(int n:bins)check(n>43000 && n<53000,"ramp voltage coverage");
   System.out.println("White RMS="+rms+" V; DC="+sum/480000+" V");
   a=unit();b=unit();a.randomRateKnob.value=0;b.randomRateKnob.value=1;a.run(480000);b.run(480000);
   double slowMove=0,fastMove=0,pa=a.slowRandomOutput.value,pb=b.slowRandomOutput.value;
   for(int i=0;i<480000;i++){a.process();b.process();slowMove+=Math.abs(a.slowRandomOutput.value-pa);fastMove+=Math.abs(b.slowRandomOutput.value-pb);pa=a.slowRandomOutput.value;pb=b.slowRandomOutput.value;}
   check(fastMove>slowMove*20,"rate materially changes wandering speed");
   a.edit(a.randomRateKnob,1,"");near(a.randomRateKnob.value,.5,1e-12,"rate inverse midpoint");
   a.edit(a.randomRateKnob,20,"");near(a.randomRateKnob.value,1,1e-12,"rate inverse upper");
   a.edit(a.randomLevelKnob,75,"");near(a.randomLevelKnob.value,.75,0,"level inverse");
   a.edit(a.redKnob,25,"");near(a.redKnob.value,.25,0,"color inverse");a.edit(a.redKnob,Double.NaN,"");near(a.redKnob.value,.25,0,"invalid edit ignored");
   a.randomMeter=-3;a.updateLamps();near(a.negativeLed.value,1,0,"negative lamp");near(a.positiveLed.value,0,0,"positive lamp off");
   a.randomMeter=3;a.updateLamps();near(a.positiveLed.value,1,0,"positive lamp");a.bypass();a.updateLamps();near(a.positiveLed.value,0,0,"bypass lamp off");
   a=unit();b=unit();check(new NoiseChecks().noiseState!=new NoiseChecks().noiseState,"module instances have different seeds");
   for(int edge=0;edge<2;edge++) {
     a.redKnob.value=a.blueKnob.value=a.randomLevelKnob.value=a.randomRateKnob.value=edge;
     for(int i=0;i<96000;i++){a.process();check(Double.isFinite(a.spectraOutput.value) && Math.abs(a.slowRandomOutput.value)<=5,"extreme controls safe");}
   }
   // Mean speed and jitter depth evaluated in the actual callback with held jitter.
   a=unit();a.process();near(a.rampStep*SAMPLE_RATE,60,1e-12,"source nominal center speed");
   a=unit();b=unit();a.randomRateKnob.value=0;b.randomRateKnob.value=1;a.process();b.process();
   near(a.rampStep*SAMPLE_RATE,48,1e-12,"rate gently slows source");near(b.rampStep*SAMPLE_RATE,72,1e-12,"rate gently speeds source");
   a=unit();b=unit();a.redKnob.value=1;b.redKnob.value=0;a.process();b.process();check(a.rampStep<b.rampStep,"red slows source");
   a=unit();b=unit();a.blueKnob.value=1;b.blueKnob.value=0;a.process();b.process();check(a.rampStep>b.rampStep,"blue speeds source");
   for(double jitter:new double[]{-1,0,1}) {
     a=unit();b=unit();a.randomLevelKnob.value=0;b.randomLevelKnob.value=1;a.rampJitter=b.rampJitter=jitter;a.process();b.process();
     if(jitter==0)near(a.rampStep,b.rampStep,0,"level leaves mean source speed alone");
     else check(Math.abs(b.rampStep*SAMPLE_RATE-60)>Math.abs(a.rampStep*SAMPLE_RATE-60),"level adds timing variation");
   }
   a=unit();a.run(3000);a.press();a.process();a.release();held=a.heldVoltage;
   a.redKnob.value=1;a.blueKnob.value=0;a.randomRateKnob.value=0;a.randomLevelKnob.value=1;
   for(int i=0;i<12000;i++) {
     double oldPhase=a.rampPhase;a.process();double expected=oldPhase+a.rampStep;expected-=Math.floor(expected);
     near(a.rampPhase,expected,1e-14,"knob moves never reset source phase");near(a.heldVoltage,held,0,"knob moves never resample held output");
     check(a.rampStep*SAMPLE_RATE>=20-1e-12 && a.rampStep*SAMPLE_RATE<=120+1e-12,"source speed bounded");
   }
   for(int mask=0;mask<16;mask++) {
     a=unit();a.redKnob.value=mask&1;a.blueKnob.value=(mask>>1)&1;a.randomRateKnob.value=(mask>>2)&1;a.randomLevelKnob.value=(mask>>3)&1;
     int cycles=0;
     for(int i=0;i<48000;i++){double oldPhase=a.rampPhase;a.process();if(a.rampPhase<oldPhase)cycles++;check(a.rampStep*SAMPLE_RATE>=20-1e-12 && a.rampStep*SAMPLE_RATE<=120+1e-12,"all corners source bounds");}
     check(cycles>=19 && cycles<=120,"measured source cycles much slower than original");
   }
   // Level-matched first-difference energy checks distinguish darkening from volume loss.
   // Reconstruct the previous filter network from the identical deterministic noise word.
   a=unit();double oldWhiteLow=0,oldRed=0,oldMid=0,oldUpper=0,oldBlue=0;
   double oldWhiteEnergy=0,newWhiteEnergy=0,oldWhiteDiff=0,newWhiteDiff=0;
   double oldColorEnergy=0,newColorEnergy=0,oldColorDiff=0,newColorDiff=0;
   double oldW=0,newW=0,oldC=0,newC=0;
   for(int i=0;i<144000;i++) {
     long bits=scramble(a.noiseState+SEED_STEP);
     double n=((int)bits/2147483648.0+(int)(bits>>>32)/2147483648.0)*.5;
     oldWhiteLow+=coefficient(6000)*(n-oldWhiteLow);
     oldRed+=coefficient(80)*(n-oldRed);oldMid+=coefficient(320)*(n-oldMid);
     oldUpper+=coefficient(1280)*(n-oldUpper);oldBlue+=coefficient(5120)*(n-oldBlue);
     double ow=4.5*(.82*n+.18*oldWhiteLow);
     double oc=warmLimit(3.2*(.5*(8*oldRed+4*oldMid+2*oldUpper+oldBlue)+.5*(1.8*n-.8*oldBlue)));
     a.process();double nw=a.whiteOutput.value,nc=a.spectraOutput.value;
     if(i>4800) {
       oldWhiteEnergy+=ow*ow;newWhiteEnergy+=nw*nw;oldWhiteDiff+=(ow-oldW)*(ow-oldW);newWhiteDiff+=(nw-newW)*(nw-newW);
       oldColorEnergy+=oc*oc;newColorEnergy+=nc*nc;oldColorDiff+=(oc-oldC)*(oc-oldC);newColorDiff+=(nc-newC)*(nc-newC);
     }
     oldW=ow;newW=nw;oldC=oc;newC=nc;
   }
   check(newWhiteDiff/newWhiteEnergy<oldWhiteDiff/oldWhiteEnergy*.96,"white darker after energy normalization");
   check(newColorDiff/newColorEnergy<oldColorDiff/oldColorEnergy*.8,"spectra darker after energy normalization");
   check(newWhiteEnergy/oldWhiteEnergy>.8 && newWhiteEnergy/oldWhiteEnergy<1.2,"white loudness remains close");
   System.out.println("Normalized brightness ratios: WHITE="+(newWhiteDiff/newWhiteEnergy)/(oldWhiteDiff/oldWhiteEnergy)+", SPECTRA="+(newColorDiff/newColorEnergy)/(oldColorDiff/oldColorEnergy));

   System.out.println(checks+" assertions passed; both SDK forms compiled.");
 }
}
class MockModule {
 void StartGuiUpdateTimer(long n) {}
 String GetTooltipText(NoiseChecks.VoltageComponent c) {return "";}
 void EditComponentValue(NoiseChecks.VoltageComponent c,double v,String s) {c.SetValue(v);}
}
'''
approved=json.loads((module/'tests/approved-regions.json').read_text(encoding='utf-8'))
baseline_h=h
for name in ['Initialize','ProcessSample','ProcessBypassedSample','Notify','GetStateInformation','SetStateInformation','GetTooltipText','EditComponentValue','code-and-variables']:
    assert r[name] in baseline_h,name
    baseline_h=baseline_h.replace(r[name],approved[name],1)
baseline_h=baseline_h.replace('NoiseChecks','ApprovedChecks').replace('MockModule','ApprovedMockModule')
bp=build/'ApprovedChecks.java';bp.write_text(baseline_h,encoding='utf-8')
p=build/'NoiseChecks.java';p.write_text(h,encoding='utf-8')
subprocess.run(['javac','--release','17','-encoding','UTF-8','-Xlint:all','-Werror','-d',str(build),str(p)],check=True)
subprocess.run(['java','-cp',str(build),'NoiseChecks'],check=True)

comparison=module/'tests/NoiseRegression.java'
subprocess.run(['javac','--release','17','-encoding','UTF-8','-Xlint:all','-Werror','-cp',str(build),'-d',str(build),str(bp),str(comparison)],check=True)
subprocess.run(['java','-cp',str(build),'NoiseRegression'],check=True)
