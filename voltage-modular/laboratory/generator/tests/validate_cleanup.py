"""Verify Generator 1.0.1: cleanup plus intentional duty-cycle smoothing against 1.0.0."""
from pathlib import Path
import argparse, hashlib, json, re, subprocess, sys, tempfile

parser=argparse.ArgumentParser()
parser.add_argument('--repo',type=Path,required=True)
parser.add_argument('--candidate',type=Path,required=True)
args=parser.parse_args()
sys.path.insert(0,str(args.repo/'voltage-modular/colorbox/tools'))
from value_tree import Reader,props,encode
from support import tokens

base=args.repo/'voltage-modular/laboratory/generator/versions/1.0.0'
names=json.loads((Path(__file__).parent/'control-renames.json').read_text())
old=(base/'generator.java').read_text(encoding='utf-8-sig')
new=(args.candidate/'generator.java').read_text(encoding='utf-8-sig')
assert not re.search(r'\b(?:knob|switch|inputJack|outputJack|textLabel|analogMeter)\d+\b',new)
assert (base/'generator_hero.png').read_bytes()==(args.candidate/'generator_hero.png').read_bytes()
for name,digest in json.loads((args.candidate/'SHA256.json').read_text()).items():
    assert hashlib.sha256((args.candidate/name).read_bytes()).hexdigest()==digest
trees=[Reader((folder/'generator.vmod').read_bytes()).tree() for folder in (base,args.candidate)]
before,after=trees
# The latest user panel supersedes 1.0.0 metadata (category, skin registration and saved state).
panel=json.loads((Path(__file__).parent/'panel-baseline.json').read_text(encoding='utf-8'))
import copy
from value_tree import setprop
inspection=copy.deepcopy(after)
inspection[2]=[n for n in inspection[2] if n[0]!='module code']
for c in next(n for n in inspection[2] if n[0]=='controls')[2]:
    p=props(c)
    assert p['display name']==panel['displayNames'][p['variable name']]
    pattern=r'new Voltage\w+\( "'+re.escape(p['variable name'])+r'", "'+re.escape(p['display name'])+r'", this'
    assert re.search(pattern,new), 'Source display name differs from Designer'
    setprop(c,'display name','')
assert hashlib.sha256(encode(inspection)).hexdigest()==panel['sha256']
assert props(after)['module type']=='ModuleType.ModuleType_Oscillators'
assert 'ModuleType.ModuleType_Oscillators' in new
code=next(n for n in after[2] if n[0]=='module code')
embedded=props(code)['MODULE.SOURCE']
def comparable(s):
    decl=re.compile(r'^\s*private Voltage\w+ \w+;',re.M)
    return tokens(decl.sub('',s)),sorted(x.strip() for x in decl.findall(s))
assert comparable(new)==comparable(embedded)
for line in code[2][0][2]:
    assert 0<=props(line)['line number']<len(embedded.splitlines())

def region(s,name):
    return s.split('//[user-'+name+']',1)[1].split('//[/user-'+name+']',1)[0].split('\n',1)[1]
def harness_class(s,name):
    body=region(s,'code-and-variables').replace('VoltageAudioJack','Control').replace('VoltageComponent','Control')
    fields=re.findall(r'private Voltage\w+ (\w+);',s)
    result='static class '+name+' extends StubModule {\n'
    result+='\n'.join('Control '+n+'=new Control();' for n in fields)+'\n'+body
    result+='\nvoid process() {\n'+region(s,'ProcessSample')+'\n}'
    result+='\nvoid bypass() {\n'+region(s,'ProcessBypassedSample')+'\n}'
    if name=='Candidate':
        result+='\nString GetTooltipText(Control component) {\n'+region(s,'GetTooltipText')+'\n}'
        result+='\nvoid EditComponentValue(Control component,double newValue,String newText) {\n'+region(s,'EditComponentValue')+'\nsuper.EditComponentValue(component,newValue,newText);\n}'
    return result+'\n}\n'

driver='''
static long checks;
static void exact(double a,double b) {
 checks++; if(Double.doubleToLongBits(a)!=Double.doubleToLongBits(b))throw new AssertionError(a+" != "+b);
}
static void near(double a,double b) {
 checks++;if(!Double.isFinite(a)||Math.abs(a-b)>1e-9)throw new AssertionError(a+" != "+b);
}
public static void main(String[] args) {
 Baseline a=new Baseline();Candidate b=new Candidate();a.resetGenerator();b.resetGenerator();
 java.util.Random random=new java.util.Random(106101);
 double expectedDuty=Double.NaN;
 for(int i=0;i<1000000;i++) {
  if(i%137==0) {
   a.knob1.value=b.amplitudeKnob.value=random.nextDouble();
   a.knob2.value=b.frequencyScaleKnob.value=1+random.nextInt(4);
   a.knob3.value=b.frequencyKnob.value=random.nextDouble()*2-1;
   a.knob4.value=b.waveformKnob.value=1+random.nextInt(2);
   a.knob5.value=b.dutyCycleKnob.value=random.nextDouble()*2-1;
   a.knob6.value=b.modulationModeKnob.value=1+random.nextInt(4);
   a.knob7.value=b.modulationAdjustKnob.value=random.nextDouble();
   a.switch1.value=b.powerSwitch.value=random.nextInt(5)==0?0:1;
   a.switch2.value=b.referenceSwitch.value=random.nextInt(3);
   a.inputJack1.connected=b.externalFmInput.connected=random.nextBoolean();
  }
  a.inputJack1.value=b.externalFmInput.value=12*Math.sin(i*.731);
  if((i/997)%7==0) {a.bypass();b.bypass();} else {
   if(b.powerSwitch.value>=.5) {
    double target=b.dutyCycleKnob.value;
    if(Double.isNaN(expectedDuty)) expectedDuty=target;
    else expectedDuty+=(1-Math.exp(-1.0/(48000*.010)))*(target-expectedDuty);
    a.knob5.value=expectedDuty;
   }
   a.process();b.process();
  }
  exact(b.smoothedDutyCycle,expectedDuty);
  exact(a.outputJack1.value,b.referenceOutput.value);exact(a.outputJack2.value,b.mainOutput.value);
  exact(a.analogMeter1.value,b.outputMeter.value);exact(a.mainPhase,b.mainPhase);
  exact(a.mainPhaseReference,b.referencePhase);exact(a.internalModulationPhase,b.internalModulationPhase);
  exact(a.internalDriftPhase,b.modulationDriftPhase);exact(a.smoothedAmplitude,b.smoothedAmplitude);
  exact(a.smoothedFrequency,b.smoothedFrequency);exact(a.meterEnvelope,b.meterEnvelope);
 }
 Candidate duty=new Candidate();duty.resetGenerator();
 near(duty.smoothDutyCycle(-1),-1);
 double previous=-1;
 for(int i=0;i<4800;i++) {
  double value=duty.smoothDutyCycle(1);
  if(value<previous || value>1 || value-previous>.00417)throw new AssertionError("duty smoothing step");
  previous=value;
 }
 if(Math.abs(previous-1)>.0001)throw new AssertionError("duty settling");
 near(duty.smoothDutyCycle(1),previous+(1-Math.exp(-1.0/(48000*.010)))*(1-previous));
 duty.waveformKnob.value=1;
 near(duty.selectedWaveform(.123,-1),duty.selectedWaveform(.123,1));
 for(int scale=1;scale<=4;scale++) {
  b.frequencyScaleKnob.value=scale;
  for(double raw:new double[]{-1,-.25,0,.7,1}) {
   b.frequencyKnob.value=raw;
   near(b.controlValueFromDisplay(b.frequencyKnob,b.mainFrequencyHz()),raw);
  }
 }
 for(int mode=1;mode<=4;mode++) {
  b.modulationModeKnob.value=mode;
  for(double raw:new double[]{0,.25,.5,1}) {
   b.modulationAdjustKnob.value=raw;
   double display=mode==4?raw*100:b.internalModulationRateHz();
   near(b.controlValueFromDisplay(b.modulationAdjustKnob,display),raw);
  }
 }
 near(b.controlValueFromDisplay(b.amplitudeKnob,5),.5);
 near(b.controlValueFromDisplay(b.dutyCycleKnob,8),-1);
 near(b.controlValueFromDisplay(b.dutyCycleKnob,50),0);
 near(b.controlValueFromDisplay(b.dutyCycleKnob,92),1);
 b.modulationModeKnob.value=2;b.modulationAdjustKnob.value=0;
 if(!b.GetTooltipText(b.modulationAdjustKnob).contains("0.05 Hz"))throw new AssertionError("rate precision");
 if(!b.GetTooltipText(b.modulationModeKnob).contains("INT/4"))throw new AssertionError("mode tooltip");
 b.EditComponentValue(b.amplitudeKnob,7.5,"");near(b.amplitudeKnob.value,.75);
 b.EditComponentValue(b.amplitudeKnob,Double.NaN,"");near(b.amplitudeKnob.value,.75);
 for(Control c:new Control[]{b.powerSwitch,b.referenceSwitch,b.amplitudeKnob,b.frequencyScaleKnob,
 b.frequencyKnob,b.waveformKnob,b.dutyCycleKnob,b.modulationModeKnob,b.modulationAdjustKnob,
 b.externalFmInput,b.mainOutput,b.referenceOutput,b.outputMeter}) {
  if(b.GetTooltipText(c).isEmpty())throw new AssertionError("missing tooltip");
 }
 System.out.println("PASS: "+checks+" checks; 1,000,000 samples match 1.0.0 given the independently smoothed duty input; all other DSP unchanged; duty response and tooltip entry verified");
}
'''
harness='''public class GeneratorCleanupTest {
static class Control {
 double value;boolean connected;double GetValue(){return value;}
 void SetValue(double v){value=v;}boolean IsConnected(){return connected;}
}
static class StubModule {
 String GetTooltipText(Control c){return "";}
 void EditComponentValue(Control c,double v,String text){c.value=v;}
}
'''+harness_class(old,'Baseline')+harness_class(new,'Candidate')+driver+'\n}'
with tempfile.TemporaryDirectory(prefix='generator-cleanup-') as temp:
    work=Path(temp)
    for label,text in [('export',new),('embedded',embedded)]:
        d=work/label;d.mkdir();(d/'generator.java').write_text(text,encoding='utf-8')
        subprocess.run(['javac','--release','17','-encoding','UTF-8','-Xlint:all','-Werror','-cp','C:/ProgramData/Voltage/voltage.jar','-d',str(d),str(d/'generator.java')],check=True)
    (work/'GeneratorCleanupTest.java').write_text(harness,encoding='utf-8')
    subprocess.run(['javac','--release','17','-encoding','UTF-8','-d',str(work),str(work/'GeneratorCleanupTest.java')],check=True)
    subprocess.run(['java','-cp',str(work),'GeneratorCleanupTest'],check=True)
print('PASS: SDK compilation, source pair, hashes, control names/UUIDs, artwork, ranges/defaults, saved state and anchors')
