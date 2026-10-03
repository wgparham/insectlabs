"""Validate the sn-16u Designer pair and execute actual user callbacks in a simulated host."""
from pathlib import Path
import sys,re,subprocess,os,json,hashlib
sys.dont_write_bytecode=True
root=next(p for p in Path(__file__).resolve().parents if (p/'.git').exists())
sys.path.insert(0,str(root/'voltage-modular/colorbox/tools'))
from value_tree import Reader,props,encode
from support import regions,normalized
module=Path(sys.argv[1]) if len(sys.argv)>1 else Path(__file__).resolve().parent.parent
source=(module/'sn16u.java').read_text(encoding='utf-8-sig');r=regions(source)
raw=(module/'sn16u.vmod').read_bytes();reader=Reader(raw);tree=reader.tree()
assert reader.p==len(raw) and encode(tree)==raw
code=next(n for n in tree[2] if n[0]=='module code');embedded=props(code)['MODULE.SOURCE']
assert normalized(source)==normalized(embedded)
assert props(tree)['notes'].startswith('v1.0.0')
assert len(r['ProcessSample'])>2000 and len(r['ProcessBypassedSample'])>200
assert source.count('super.Destroy();')==1
assert 'new ' not in r['ProcessSample']
controls=next(n for n in tree[2] if n[0]=='controls')[2]
assert len({props(n)['UUID'] for n in controls})==len(controls)==165
byname={props(n)['variable name']:props(n) for n in controls}
assert byname['fixedPlus24Output']['left']==129 and byname['fixedPlus24Output']['top']==40
assert byname['frequencyInput']['left']==308 and byname['frequencyInput']['top']==84
assert byname['amplitudeInput']['top']==154
assert byname['meterASwitch']['initialState']==1 and byname['meterBSwitch']['initialState']==0
assert byname['meterModeSwitch']['initialState']==1
for name in ['wholeVoltsKnob','fractionVoltsKnob','fineTuneKnob','tuningSelector','highPassKnob','lowPassKnob']:
 d=byname[name]
 assert d['defaultValue']==float(d['defaultValueString'])
for n in code[2][0][2]:assert 0<=props(n)['line number']<len(embedded.splitlines())
build=Path(r'C:/InsectLabs-Build/sn16u/checks');build.mkdir(parents=True,exist_ok=True)
sdk=Path(r'C:/ProgramData/Voltage/voltage.jar')
for label,text in [('exported',source),('embedded',embedded)]:
 folder=build/label;folder.mkdir(exist_ok=True)
 p=folder/'sn16u.java';p.write_text(text,encoding='utf-8')
 subprocess.run(['javac','--release','17','-encoding','UTF-8','-Xlint:all','-Werror','-cp',str(sdk),'-d',str(folder/'classes'),str(p)],check=True)
types=set(re.findall(r'private (Voltage\w+) \w+;',source))
h='''public class BenchChecks extends MockModule {
 static class VoltageComponent {
  double value, guiValue; boolean connected; int reads;
  void UpdateGUIValue(double v){guiValue=v;}
  double GetValue(){reads++;return value;}
  void SetValue(double v){value=v;}
  boolean IsConnected(){reads++;return connected;}
 }
 enum ModuleNotifications {Button_Changed, GUI_Update_Timer, Reset, Preset_Loading_Finish, Variation_Loading_Finish}
'''
h+='\n'.join(' static class '+t+' extends VoltageComponent {}' for t in types)
for typ,name in re.findall(r'private (Voltage\w+) (\w+);',source):h+=f'\n {typ} {name}=new {typ}();'
for region,signature in [('Initialize','void initialize()'),('ProcessSample','void process()'),('ProcessBypassedSample','void bypass()'),('Notify','boolean notify(VoltageComponent component,ModuleNotifications notification,double doubleValue)'),('GetTooltipText','String tooltip(VoltageComponent component)'),('EditComponentValue','void edit(VoltageComponent component,double newValue,String newText)')]:
 h+='\n'+signature+' {\n'+r[region]
 if region=='EditComponentValue':h+='\nsuper.EditComponentValue(component,newValue,newText);'
 h+='\n}\n'
h+=r['code-and-variables']
h+='''
 static int checks;
 static void check(boolean ok,String message){checks++;if(!ok)throw new AssertionError(message);}
 static void near(double a,double b,double tolerance,String message){check(Double.isFinite(a)&&Math.abs(a-b)<=tolerance,message+": "+a+" expected "+b);}
 static BenchChecks unit(){
  BenchChecks a=new BenchChecks();a.tuningSelector.value=4;a.lowPassKnob.value=1;
  a.polaritySwitch.value=a.standardsSwitch.value=a.meterASwitch.value=a.meterModeSwitch.value=1;
  a.noise.seed=19710510;a.initialize();return a;
 }
 void run(int n){for(int i=0;i<n;i++)process();}
 static void connect(VoltageComponent input,double value){input.connected=true;input.value=value;}
 void press(){notify(sweepTriggerButton,ModuleNotifications.Button_Changed,1);notify(sweepTriggerButton,ModuleNotifications.Button_Changed,0);}
 public static void main(String[] args)throws Exception{
  BenchChecks a=unit();a.process();
  near(a.fixedPlus24Output.value,24,0,"positive 24 jack");near(a.fixedMinus24Output.value,-24,0,"negative 24 jack");
  near(a.fixed3v3Output.value,3.3,0,"3.3 jack");near(a.fixed9Output.value,9,0,"9 jack");
  near(a.fixedMinus15Output.value,-15,0,"-15");near(a.fixedPlus15Output.value,15,0,"+15");
  near(a.fixedMinus12Output.value,-12,0,"-12");near(a.fixedPlus12Output.value,12,0,"+12");
  near(a.fixedMinus10Output.value,-10,0,"-10");near(a.fixedPlus10Output.value,10,0,"+10");
  near(a.fixedMinus5Output.value,-5,0,"-5");near(a.fixedPlus5Output.value,5,0,"+5");
  near(a.fixedMinus1Output.value,-1,0,"-1");near(a.fixedPlus1Output.value,1,0,"+1");near(a.fixedZeroOutput.value,0,0,"zero");
  a.press();a.process();a.refreshDisplays();near(a.sweepTriggerButton.guiValue,1,0,"sweep light stays on after button release");near(a.sweepTriggerButton.value,0,0,"visual update does not latch trigger");
  a.bypass();a.refreshDisplays();near(a.sweepTriggerButton.guiValue,0,0,"bypass light off");
  a=unit();a.process();
  near(a.fastPulseOutput.value,5,0,"first fast impulse");near(a.slowPulseOutput.value,5,0,"first slow impulse");
  int fast=1,slow=1;for(int i=1;i<8192;i++){a.process();if(a.fastPulseOutput.value==5)fast++;if(a.slowPulseOutput.value==5)slow++;}
  check(fast==16&&slow==2,"exact impulse counts");
  near(a.aReferenceOutput.value,0,0,"tone off");check(Math.abs(a.hundredHzOutput.value)>0,"courtesy independent");
  a.wholeVoltsKnob.value=2;a.fractionVoltsKnob.value=.25;connect(a.addAInput,1);connect(a.addBInput,-.5);a.process();
  near(a.positiveDcOutput.value,2.75,0,"sum");near(a.negativeDcOutput.value,-2.75,0,"inverse");
  a.polaritySwitch.value=0;a.process();near(a.positiveDcOutput.value,-1.75,0,"polarity only manual");
  a.polaritySwitch.value=1;a.standardsSwitch.value=2;a.process();near(a.positiveDcOutput.value,1.375,0,"half volt standard");
  a.standardsSwitch.value=0;a.process();near(a.positiveDcOutput.value,Math.pow(2,1.75),1e-12,"convert summed pitch once");
  near(convertPitch(4,0),8,0,"C4 conversion");near(pitchHz(4,0),C0_HZ*8,1e-12,"4 Hz/V volts = C3");
  near(pitchHz(4,1),C0_HZ*16,1e-12,"4 native volts = C4");near(pitchHz(2,2),C0_HZ*16,1e-12,"2 half-volts = C4");
  near(pitchHz(0,0),0,0,"Hz/V zero");near(pitchHz(-5,0),0,0,"Hz/V negative floor");
  near(fineCents(.025),0,0,"center dead zone");near(fineCents(-1),-100,0,"fine lower endpoint");near(fineCents(1),100,0,"fine upper endpoint");
  a.edit(a.fineTuneKnob,.01,"");near(fineCents(a.fineTuneKnob.value),.01,1e-12,"tiny typed cents preserved");
  a.edit(a.lowPassKnob,1000,"");near(cutoffHz(a.lowPassKnob.value),1000,1e-9,"typed cutoff");
  a.edit(a.tuningSelector,435,"");near(a.tuningSelector.value,5,0,"nonmonotonic selector typed input");
  a=unit();a.toneSwitch.value=1;a.run(12000);
  double energyA=0,energyC=0;for(int i=0;i<48000;i++){a.process();energyA+=a.aReferenceOutput.value*a.aReferenceOutput.value;energyC+=a.cReferenceOutput.value*a.cReferenceOutput.value;}
  near(Math.sqrt(energyA/48000),5/Math.sqrt(2),1e-8,"A sine RMS");check(Math.abs(Math.sqrt(energyC/48000)-5/Math.sqrt(2))<.01,"C sine RMS");
  a=unit();a.toneSwitch.value=1;a.run(12000);
  FrequencyMeter sharp=new FrequencyMeter(),courtesy=new FrequencyMeter();double sharpEnergy=0,courtesyEnergy=0;
  for(int i=0;i<48000;i++){a.process();sharp.process(a.fSharpReferenceOutput.value,true);courtesy.process(a.thousandHzOutput.value,true);sharpEnergy+=a.fSharpReferenceOutput.value*a.fSharpReferenceOutput.value;courtesyEnergy+=a.thousandHzOutput.value*a.thousandHzOutput.value;}
  near(sharp.frequency,440*Math.pow(2,-.25),.001,"F#4 frequency");near(courtesy.frequency,1000,.0001,"1k courtesy frequency");
  near(Math.sqrt(sharpEnergy/48000),5/Math.sqrt(2),.01,"F# RMS");near(Math.sqrt(courtesyEnergy/48000),5/Math.sqrt(2),1e-8,"1k RMS");
  a.toneSwitch.value=0;a.run(12000);near(a.fSharpReferenceOutput.value,0,1e-8,"F# follows tone off");
  double offEnergy=0;for(int i=0;i<48;i++){a.process();offEnergy+=a.thousandHzOutput.value*a.thousandHzOutput.value;}near(Math.sqrt(offEnergy/48),5/Math.sqrt(2),1e-8,"courtesy ignores tone off");
  a.tuningSelector.value=1;a.fineTuneKnob.value=1;a.toneSwitch.value=1;a.run(12000);sharp=new FrequencyMeter();courtesy=new FrequencyMeter();
  for(int i=0;i<48000;i++){a.process();sharp.process(a.fSharpReferenceOutput.value,true);courtesy.process(a.thousandHzOutput.value,true);}
  near(sharp.frequency,392*Math.pow(2,1.0/12-.25),.001,"F# follows tuning and fine");near(courtesy.frequency,1000,.0001,"courtesy ignores tuning and fine");
  double phaseSharp=a.phaseFSharp,phaseK=a.phaseThousand;a.bypass();near(a.fSharpReferenceOutput.value,0,0,"F# bypass");near(a.thousandHzOutput.value,0,0,"1k bypass");near(a.phaseFSharp,phaseSharp,0,"F# frozen");near(a.phaseThousand,phaseK,0,"1k frozen");
  a=unit();connect(a.meterAInput,24);connect(a.meterBInput,24);connect(a.amplitudeInput,-5);a.run(48000);
  near(a.shownA,24,0,"DC A");near(a.shownB,0,0,"Vpp B constant");near(a.shownRms,5,0,"RMS includes DC");
  a.meterModeSwitch.value=0;a.standardsSwitch.value=0;a.meterAInput.value=4;a.meterBInput.value=8;a.run(480);
  near(a.shownA,C0_HZ*8,1e-10,"pitch A");near(a.shownB,C0_HZ*16,1e-10,"pitch B ignores Vpp");
  a.meterModeSwitch.value=1;a.run(48000);near(a.shownA,4,0,"back to DC");near(a.shownB,0,0,"back to Vpp");
  a=unit();connect(a.meterBInput,0);connect(a.amplitudeInput,0);connect(a.frequencyInput,0);
  for(int i=0;i<96000;i++){double v=5*Math.sin(TWO_PI*1000*i/SAMPLE_RATE);a.meterBInput.value=a.amplitudeInput.value=a.frequencyInput.value=v;a.process();}
  near(a.shownB,10,1e-10,"sine Vpp");near(a.shownRms,5/Math.sqrt(2),1e-10,"sine RMS");near(a.shownFrequency,1000,1e-7,"counter audio");
  a.frequencyInput.value=0;a.run(100000);near(a.shownFrequency,0,0,"counter stale timeout");
  a.meterBInput.connected=false;a.amplitudeInput.connected=false;a.run(480);near(a.shownB,0,0,"disconnect clears Vpp");near(a.shownRms,0,0,"disconnect clears RMS");
  for(double f:new double[]{.1,1,11.71875,93.75,440,5000,18000}){
   FrequencyMeter m=new FrequencyMeter();int count=(int)Math.max(48000,3*SAMPLE_RATE/f);
   for(int i=0;i<count;i++)m.process(5*Math.sin(TWO_PI*f*i/SAMPLE_RATE),true);
   near(m.frequency,f,Math.max(.00001,f*.0002),"counter range "+f);
  }
  a=unit();a.highPassKnob.value=a.lowPassKnob.value=Math.log(1000)/Math.log(20000);connect(a.highPassInput,0);connect(a.lowPassInput,0);a.run(12000);
  double highEnergy=0,lowEnergy=0;
  for(int i=0;i<96000;i++){double v=Math.sin(TWO_PI*1000*i/SAMPLE_RATE);a.highPassInput.value=a.lowPassInput.value=v;a.process();if(i>=48000){highEnergy+=a.highPassOutput.value*a.highPassOutput.value;lowEnergy+=a.lowPassOutput.value*a.lowPassOutput.value;}}
  near(Math.sqrt(2*highEnergy/48000),1/Math.sqrt(2),1e-9,"HPF -3dB at cutoff");near(Math.sqrt(2*lowEnergy/48000),1/Math.sqrt(2),1e-9,"LPF -3dB at cutoff");
  a.highPassInput.value=3;a.lowPassInput.value=2;a.run(48000);near(a.highPassOutput.value,0,1e-12,"HPF DC rejection");near(a.lowPassOutput.value,2,1e-12,"LPF DC unity");
  a=unit();a.press();a.run(1234);int sweepPosition=a.sweepSample;double phase=a.phaseA,noiseState=a.noise.b0;int reads=a.wholeVoltsKnob.reads+a.standardsSwitch.reads+a.meterAInput.reads;
  connect(a.addAInput,2);connect(a.addBInput,3);connect(a.highPassInput,7);connect(a.lowPassInput,-8);
  for(int i=0;i<1000;i++)a.bypass();
  near(a.positiveDcOutput.value,5,0,"bypass raw sum");near(a.negativeDcOutput.value,-5,0,"bypass inverse sum");near(a.highPassOutput.value,7,0,"bypass HPF");near(a.lowPassOutput.value,-8,0,"bypass LPF");
  near(a.fixedPlus24Output.value,0,0,"bypass fixed sources silent");near(a.oneHzOutput.value,0,0,"bypass courtesy silent");near(a.phaseA,phase,0,"phase frozen");near(a.noise.b0,noiseState,0,"noise frozen");
  check(a.sweepSample==sweepPosition,"sweep frozen");check(reads==a.wholeVoltsKnob.reads+a.standardsSwitch.reads+a.meterAInput.reads,"bypass skips controls/meters");
  a.press();a.process();check(a.sweepSample==sweepPosition+1,"bypass trigger discarded, resume continues");
  a.press();a.process();check(a.sweepSample==1,"active retrigger restarts");
  a.notify(null,ModuleNotifications.Reset,0);a.process();check(!a.sweepActive,"reset stops sweep");
  for(int mode=0;mode<2;mode++){
   a=unit();a.sweepCurveSwitch.value=mode;a.press();
   try(java.io.DataOutputStream out=new java.io.DataOutputStream(new java.io.BufferedOutputStream(new java.io.FileOutputStream(args[0]+"/sweep-"+mode+".f64")))){
    for(int i=0;i<SWEEP_SAMPLES;i++){
     a.process();out.writeDouble(a.sweepOutput.value);
     if(i==SWEEP_SAMPLES/2)near(a.sweepHz,mode==0?SWEEP_START_HZ+SWEEP_STEP*(i+1):SWEEP_START_HZ*Math.pow(SWEEP_RATIO,i+1),.001,"sweep midpoint");
     check(Double.isFinite(a.sweepOutput.value)&&Math.abs(a.sweepOutput.value)<=5,"sweep bounds");
    }
   }
   a.refreshDisplays();near(a.sweepTriggerButton.guiValue,0,0,"completed sweep light off");
   check(!a.sweepActive,"exact 42-second stop");near(a.sweepHz,.01,0,"sweep reset");a.run(100);near(a.sweepOutput.value,0,0,"sweep stays silent");
  }
  a=unit();try(java.io.DataOutputStream out=new java.io.DataOutputStream(new java.io.BufferedOutputStream(new java.io.FileOutputStream(args[0]+"/noise.f64")))){
   for(int i=0;i<48000*12;i++){a.process();out.writeDouble(a.pinkOutput.value);out.writeDouble(a.blueOutput.value);}
  }
  System.out.println("PASS "+checks+" callback/numerical checks; no native GUI or CPU benchmark claimed.");
 }
}
class MockModule {
 void StartGuiUpdateTimer(long interval){} void StopGuiUpdateTimer(){}
 String GetTooltipText(BenchChecks.VoltageComponent c){return "";}
 void EditComponentValue(BenchChecks.VoltageComponent c,double value,String text){c.value=value;}
}
'''
(build/'BenchChecks.java').write_text(h,encoding='utf-8')
subprocess.run(['javac','--release','17','-encoding','UTF-8','-Xlint:all','-Werror','-d',str(build),str(build/'BenchChecks.java')],check=True)
subprocess.run(['java','-cp',str(build),'BenchChecks',str(build)],check=True)
print('PASS SDK export/embedded compilation, pair/metadata/round-trip and callback checks.')
