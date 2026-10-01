"""SDK, Designer metadata and actual callback tests for the Deep Tone candidate."""
from pathlib import Path
import sys, subprocess, os, re, math
repo=next((p for p in Path(__file__).resolve().parents if (p/'voltage-modular/colorbox/tools').is_dir()),Path(r'C:\Users\wgparham\Dropbox\git\insectlabs'))
sys.path.insert(0,str(repo/'voltage-modular/colorbox/tools'))
from support import regions, normalized
from value_tree import Reader, props, encode
base=Path(__file__).resolve().parent
module=base if (base/'deeptone.java').exists() else base.parent
source=(module/'deeptone.java').read_text(encoding='utf-8-sig'); r=regions(source)
raw=(module/'deeptone.vmod').read_bytes();reader=Reader(raw);tree=reader.tree()
assert reader.p==len(raw) and encode(tree)==raw
embedded=props(next(n for n in tree[2] if n[0]=='module code'))['MODULE.SOURCE']
assert normalized(source)==normalized(embedded),'source pair differs'
controls=next(n for n in tree[2] if n[0]=='controls');p={props(n)['variable name']:props(n) for n in controls[2]}
assert len(p)==len({v['UUID'] for v in p.values()})==len(controls[2])
assert props(tree)['module type']=='ModuleType.ModuleType_Oscillators'
assert 'Version: v1.0.0' in props(tree)['notes'] and 'rc' not in props(tree)['notes']
assert float(p['swellDepthKnob']['maxValueString'])==p['swellDepthKnob']['maxValue']==2.0
for name,default in [('wholeKnob',2.0),('fractionKnob',.616255653005986),('multiplierKnob',3.0)]:
    assert float(p[name]['defaultValueString'])==default and p[name]['defaultValue']==default
    assert p[name]['minValue']==float(p[name]['minValueString'])
    assert p[name]['maxValue']==float(p[name]['maxValueString'])
    assert re.search(name+r'\.SetRange\( [^,]+, [^,]+, '+re.escape(str(default)),source)
assert int(p['fractionKnob']['num discrete steps'])==0
assert int(p['wholeKnob']['num discrete steps'])==11
assert int(p['frequencyDisplay']['numDigits'])==2
assert int(p['frequencyDisplay_1']['numDigits'])==2
assert source.count('super.Destroy();')==1
build=Path(r'C:\InsectLabs-Build\deeptone\checks');build.mkdir(parents=True,exist_ok=True)
embedded_path=build/'embedded/deeptone.java';embedded_path.parent.mkdir(exist_ok=True);embedded_path.write_text(embedded,encoding='utf-8')
sdk=Path(os.environ.get('PROGRAMDATA',r'C:\ProgramData'))/'Voltage/voltage.jar'
for label,path in [('export',module/'deeptone.java'),('embedded',embedded_path)]:
    subprocess.run(['javac','--release','17','-encoding','UTF-8','-Xlint:all','-Werror','-cp',str(sdk),'-d',str(build/label),str(path)],check=True)
h='''public class DeepToneChecks extends MockModule {
 static class VoltageComponent {
   double value, gui; boolean connected; int reads;
   double GetValue() { reads++; return value; }
   void SetValue(double x) { value=x; }
   void UpdateGUIValue(double x) { gui=x; }
   boolean IsConnected() { return connected; }
 }
 enum ModuleNotifications { Button_Changed, GUI_Update_Timer, Reset, Preset_Loading_Finish, Variation_Loading_Finish }
'''
names=['wholeKnob','fractionKnob','multiplierKnob','shapeKnob','offsetKnob','beatDepthKnob','swellDepthKnob','amplitudeKnob','fmSwitch','courtesyPolaritySwitch','externalButton','toneButton','beatButton','swellButton','mainOutput','courtesyOutput','externalInput','frequencyDisplay','frequencyDisplay_1']
h+='\n'.join(' VoltageComponent '+n+' = new VoltageComponent();' for n in names)
for name,signature in [('Initialize','void initialize()'),('ProcessSample','void process()'),('ProcessBypassedSample','void bypass()'),('Notify','boolean notify(VoltageComponent component, ModuleNotifications notification, double doubleValue)'),('OnUndoRedo','void undo(String undoType, double newValue)'),('GetStateInformation','byte[] state()'),('SetStateInformation','void restore(byte[] stateInfo)'),('GetTooltipText','String tooltip(VoltageComponent component)'),('EditComponentValue','void edit(VoltageComponent component, double newValue, String newText)')]:
    h+='\n'+signature+' {\n'+r[name]
    if name=='EditComponentValue':h+='\nsuper.EditComponentValue(component,newValue,newText);'
    h+='\n}\n'
h+=r['code-and-variables']
h+='''
 static int checks;
 static void check(boolean ok,String message) { checks++;if(!ok)throw new AssertionError(message); }
 static void near(double a,double b,double tolerance,String message) { check(Math.abs(a-b)<=tolerance,message+": "+a+" / "+b); }
 static DeepToneChecks unit() {
   DeepToneChecks u=new DeepToneChecks();u.wholeKnob.value=2;u.fractionKnob.value=.616255653005986;
   u.multiplierKnob.value=3;u.courtesyPolaritySwitch.value=1;return u;
 }
 void run(int n) { for(int i=0;i<n;i++)process(); }
 void click(VoltageComponent c) {notify(c,ModuleNotifications.Button_Changed,1);notify(c,ModuleNotifications.Button_Changed,0);}
 public static void main(String[] args) {
   DeepToneChecks a=unit();a.initialize();a.process();
   near(a.frequency,261.6255653005986,1e-10,"C4 default");near(a.frequencyDisplay.value,2,0,"whole display");near(a.frequencyDisplay_1.value,61,0,"fraction hundredths display");
   check(a.mainOutput.value==0,"all functions off silent main");near(a.courtesyOutput.value,-3,1e-12,"zero offset held courtesy");
   for(int bit=0;bit<4;bit++) {
     VoltageComponent[] buttons={a.externalButton,a.toneButton,a.beatButton,a.swellButton};
     a.click(buttons[bit]);check(a.enabledFunctions==(1<<(bit+1))-1,"independent button latch");
   }
   check(a.undoCalls==4,"button undo recorded");
   a.notify(null,ModuleNotifications.GUI_Update_Timer,0);check(a.toneButton.gui==1 && a.swellButton.gui==1,"latched button indicators");
   DeepToneChecks b=unit();b.restore(a.state());check(b.enabledFunctions==15,"save and restore all function latches");
   b.undo("deepToneFunctions",2);check(b.enabledFunctions==2 && b.toneButton.gui==1,"undo restores mask and indicator");
   b.restore(new byte[]{0,15});check(b.enabledFunctions==2,"invalid state ignored");
   b.notify(null,ModuleNotifications.Reset,0);check(b.enabledFunctions==0,"reset disengages functions");
   for(int mask=0;mask<16;mask++){
     a=unit();b=unit();a.offsetKnob.value=b.offsetKnob.value=Math.pow(2.0/65.0,.2);
     b.enabledFunctions=mask;b.amplitudeKnob.value=15;b.beatDepthKnob.value=1;b.swellDepthKnob.value=1;b.fmSwitch.value=1;
     b.externalInput.connected=true;b.externalInput.value=5;
     for(int i=0;i<24000;i++){a.process();b.process();check(a.courtesyOutput.value==b.courtesyOutput.value,"courtesy independent of main controls/functions");}
     near(a.swellPhase,0,1e-9,"2 Hz courtesy period");
   }
   a=unit();b=unit();a.offsetKnob.value=.5;b.offsetKnob.value=-.5;b.courtesyPolaritySwitch.value=0;
   for(int i=0;i<48000;i++){a.process();b.process();near(a.courtesyOutput.value,-b.courtesyOutput.value,1e-12,"offset sign gives same rate; switch inverts courtesy");}
   a=unit();a.enabledFunctions=TONE;a.amplitudeKnob.value=Math.sqrt(1.0/15.0)*15.0;a.run(48000);
   double peak=0;int crossings=0;double previous=a.mainOutput.value;
   for(int i=0;i<48000;i++){a.process();peak=Math.max(peak,Math.abs(a.mainOutput.value));if(previous<=0 && a.mainOutput.value>0)crossings++;previous=a.mainOutput.value;}
   check(crossings>=261 && crossings<=262,"C4 measured from audio");check(peak>.999 && peak<=1,"clean output level");
   a=unit();a.enabledFunctions=EXTERNAL;a.externalInput.connected=true;a.externalInput.value=5;a.amplitudeKnob.value=Math.sqrt(1.0/15.0)*15.0;a.run(48000);
   near(a.mainOutput.value,1,1e-9,"external normal 5V input scaling");
   a.swellDepthKnob.value=1;a.enabledFunctions|=SWELL;a.offsetKnob.value=Math.pow(2.0/65.0,.2);a.run(48000);
   double low=100,high=-100;for(int i=0;i<48000;i++){a.process();low=Math.min(low,a.mainOutput.value);high=Math.max(high,a.mainOutput.value);}
   check(low<.001 && high>.999,"Swell full-depth AM reaches zero and unity");
   a=unit();a.enabledFunctions=BEAT;a.beatDepthKnob.value=1;a.amplitudeKnob.value=5;a.run(1000);check(a.mainOutput.value!=0,"Beat mix works without Tone");
   a.fmSwitch.value=1;a.run(20000);check(Math.abs(a.mainOutput.value)<1e-10,"FM Beat does not leak as separate audio");
   a=unit();a.enabledFunctions=EXTERNAL|TONE;a.externalInput.connected=true;a.externalInput.value=-123.456;a.run(1000);
   double phase=a.swellPhase,tone=a.tonePhase,beat=a.beatPhase;int reads=a.offsetKnob.reads;
   for(int i=0;i<1000;i++)a.bypass();
   check(a.mainOutput.value==-123.456 && a.courtesyOutput.value==0,"bypass direct external and silent courtesy");
   check(a.offsetKnob.reads==reads && a.swellPhase==phase && a.tonePhase==tone && a.beatPhase==beat,"bypass skips controls and freezes phases");
   a.wholeKnob.value=1;a.fractionKnob.value=0;a.multiplierKnob.value=1;a.process();near(a.frequency,1,1e-12,"resume adopts frequency");
   a.enabledFunctions=0;a.bypass();check(a.mainOutput.value==0,"bypass muted when External disabled");
   a=unit();a.offsetKnob.value=.5;a.run(1000);a.offsetKnob.value=0;a.run(24000);phase=a.swellPhase;a.run(1000);check(a.swellPhase==phase,"zero offset settles and holds courtesy phase");
   for(int mask=0;mask<16;mask++)for(int edge=0;edge<2;edge++){
     a=unit();a.enabledFunctions=mask;a.wholeKnob.value=edge*10;a.fractionKnob.value=edge;a.multiplierKnob.value=3;
     a.offsetKnob.value=edge==0?-1:1;a.shapeKnob.value=edge==0?-1:1;a.beatDepthKnob.value=1;a.swellDepthKnob.value=2;a.amplitudeKnob.value=15;a.fmSwitch.value=edge;
     a.externalInput.connected=true;a.externalInput.value=edge==0?Double.NaN:Double.MAX_VALUE;
     for(int i=0;i<4000;i++){a.process();check(Double.isFinite(a.mainOutput.value) && Math.abs(a.mainOutput.value)<=14.000001,"bounded finite extremes");check(Math.abs(a.courtesyOutput.value)<=3.000001,"courtesy fixed bounds");}
   }
   a=unit();a.edit(a.offsetKnob,-2.03125,"");near(a.offsetKnob.value,-.5,1e-12,"typed offset inverse");
   a.edit(a.amplitudeKnob,1,"");near(15*Math.pow(a.amplitudeKnob.value/15,2),1,1e-12,"typed amplitude inverse");
   a.edit(a.beatDepthKnob,50,"");near(a.beatDepthKnob.value,.5,1e-12,"typed depth inverse");
   double old=a.offsetKnob.value;a.edit(a.offsetKnob,Double.NaN,"");check(old==a.offsetKnob.value,"reject invalid typed input");
   a=unit();a.fractionKnob.value=1;a.updatePanel();check(a.frequencyDisplay.value==3 && a.frequencyDisplay_1.value==0,"display carry at fraction endpoint");
   a.fractionKnob.value=.03;a.updatePanel();check(a.frequencyDisplay.value==2 && a.frequencyDisplay_1.value==3,"fraction hundredths below ten");
   for(double rate:new double[]{.1,2,30,65})for(double form:new double[]{-1,-.5,.5,1}) {
     a=unit();a.offsetKnob.value=Math.pow(rate/65.0,.2);a.shapeKnob.value=form;
     a.enabledFunctions=EXTERNAL|SWELL;a.externalInput.connected=true;a.externalInput.value=5;
     a.swellDepthKnob.value=1;a.amplitudeKnob.value=Math.sqrt(1.0/15.0)*15.0;
     // Begin immediately before the reset, with the output conditioner settled.
     a.process();a.swellPhase=1-0.004*rate;a.run(20);
     double beforeOut=a.mainOutput.value,beforeC=a.courtesyOutput.value,maxOutStep=0,maxCourtesyStep=0;
     for(int i=0;i<500;i++){a.process();maxOutStep=Math.max(maxOutStep,Math.abs(a.mainOutput.value-beforeOut));maxCourtesyStep=Math.max(maxCourtesyStep,Math.abs(a.courtesyOutput.value-beforeC));beforeOut=a.mainOutput.value;beforeC=a.courtesyOutput.value;}
     check(maxOutStep<.023,"Swell reset softened below 2.3% per sample");
     check(maxCourtesyStep<.14,"Courtesy reset softened below 0.14 V per sample");
   }
   for(double hz:new double[]{20,50,100,300,1200}){
     double step=hz/SAMPLE_RATE,oldMax=0,newMax=0;
     double oldPrev=0,newPrev=0;
     for(int i=0;i<4800;i++){
       double ph=advance(0,i*step);
       double oldWave=2*ph-1-edgeCorrection(ph,step);
       double newWave=shapedWave(ph,-1,step,BEAT_EDGE_HALF_SAMPLES);
       if(i>0){oldMax=Math.max(oldMax,Math.abs(oldWave-oldPrev));newMax=Math.max(newMax,Math.abs(newWave-newPrev));}
       oldPrev=oldWave;newPrev=newWave;
       near(shapedWave(ph,0,step,BEAT_EDGE_HALF_SAMPLES),1-4*Math.abs(ph-.5),0,"triangle is unchanged");
     }
     check(newMax<oldMax*.4,"audible Beat reset reduced by at least 60%");
   }
   near(offsetHz(.5),2.03125,0,"half offset taper");near(offsetHz(-.25),-.0634765625,0,"quarter offset taper");
   a=unit();a.edit(a.swellDepthKnob,200,"");near(a.swellDepthKnob.value,2,0,"typed 200 percent depth");
   a.edit(a.beatDepthKnob,200,"");near(a.beatDepthKnob.value,1,0,"Beat depth remains 100 percent maximum");
   a=unit();b=unit();a.enabledFunctions=b.enabledFunctions=EXTERNAL|SWELL;
   a.externalInput.connected=b.externalInput.connected=true;a.externalInput.value=b.externalInput.value=5;
   a.amplitudeKnob.value=b.amplitudeKnob.value=Math.sqrt(1.0/15.0)*15.0;
   a.offsetKnob.value=b.offsetKnob.value=Math.pow(2.0/65.0,.2);a.swellDepthKnob.value=1;b.swellDepthKnob.value=2;
   a.run(48000);b.run(48000);int silence100=0,silence200=0;double peak200=0;
   for(int i=0;i<48000;i++) {a.process();b.process();if(a.mainOutput.value<.0001)silence100++;if(b.mainOutput.value<.0001)silence200++;peak200=Math.max(peak200,b.mainOutput.value);check(b.mainOutput.value>=0,"200 percent AM never reverses polarity");}
   check(silence200>23000 && silence200<25000,"200 percent triangle AM spends half cycle silent");
   check(silence100<100 && peak200>.999,"deeper AM preserves peak instead of adding amplification");
   for(double rate:new double[]{.1,2,30}) {
     double step=rate/SAMPLE_RATE,oldMax=0,newMax=0,oldPrev=0,newPrev=0;
     for(int i=0;i<600;i++){
       double ph=advance(1-.004*rate,i*step);
       double before=shapedWave(ph,-1,step,48.0),after=shapedWave(ph,-1,step,SWELL_EDGE_HALF_SAMPLES);
       if(i>0){oldMax=Math.max(oldMax,Math.abs(before-oldPrev));newMax=Math.max(newMax,Math.abs(after-newPrev));}
       oldPrev=before;newPrev=after;
     }
     check(newMax<oldMax*.4,"new slow-rate reset gentler than previous smoothing pass");
   }
   near(SWELL_EDGE_HALF_SAMPLES,144,0,"Swell remains 6 ms");
   near(BEAT_EDGE_HALF_SAMPLES,24,0,"Beat remains 1 ms");
   for(double form:new double[]{-1,-.5,0,.5,1}) {
     a=unit();a.offsetKnob.value=Math.pow(2.0/65.0,.2);a.shapeKnob.value=form;
     a.process();a.swellPhase=.995;double difference=0;
     for(int i=0;i<400;i++) {
       a.process();double step=Math.abs(a.offset)/SAMPLE_RATE;
       double expected=3*shapedWave(a.swellPhase,a.shape,step,48);
       near(a.courtesyOutput.value,expected,0,"Courtesy matches previous 2 ms reset");
       difference=Math.max(difference,Math.abs(expected-3*shapedWave(a.swellPhase,a.shape,step,144)));
     }
     if(form!=0)check(difference>.3,"Courtesy is sharper than Swell at reset");
   }
   near(offsetHz(-1),-65,0,"negative offset endpoint");near(offsetHz(1),65,0,"positive offset endpoint");
   near(offsetHz(0),0,0,"offset center");
   for(double position:new double[]{-.9,-.5,-.25,.25,.5,.9}) {
     near(offsetHz(position),65*Math.pow(position,5),1e-12,"same fifth-power taper");
     a=unit();a.edit(a.offsetKnob,offsetHz(position),"");near(a.offsetKnob.value,position,1e-12,"typed offset round trip");
   }
   a=unit();a.edit(a.offsetKnob,100,"");near(a.offsetKnob.value,1,0,"typed positive limit");
   a.edit(a.offsetKnob,-100,"");near(a.offsetKnob.value,-1,0,"typed negative limit");
   System.out.println(checks+" callback assertions passed; both SDK source forms compiled.");
 }
}
class MockModule {
 int undoCalls;
 void StartGuiUpdateTimer(long ms) {}
 void CreateUndoNode(String label,String type,double before,double after) {undoCalls++;}
 String GetTooltipText(DeepToneChecks.VoltageComponent c) {return "";}
 void EditComponentValue(DeepToneChecks.VoltageComponent c,double value,String text) {c.SetValue(value);}
}
'''
test=build/'DeepToneChecks.java';test.write_text(h,encoding='utf-8')
subprocess.run(['javac','--release','17','-encoding','UTF-8','-Xlint:all','-Werror','-d',str(build),str(test)],check=True)
subprocess.run(['java','-cp',str(build),'DeepToneChecks'],check=True)
