from pathlib import Path
import sys,re,json,hashlib,subprocess,tempfile
sys.dont_write_bytecode=True
repo=next(p for p in Path(__file__).resolve().parents if (p/'voltage-modular/colorbox/tools').is_dir());sys.path.insert(0,str(repo/'voltage-modular/colorbox/tools'))
from value_tree import Reader,props,encode
from support import normalized,regions
root=Path(__file__).resolve().parent.parent
release=root/'versions'/'1.0.0'
source=(release/'standards.java').read_text(encoding='utf-8-sig');raw=(release/'standards.vmod').read_bytes();reader=Reader(raw);tree=reader.tree();assert reader.p==len(raw) and encode(tree)==raw
code=next(n for n in tree[2] if n[0]=='module code');embedded=props(code)['MODULE.SOURCE'];assert normalized(source)==normalized(embedded)
assert props(tree)['CEditableModule.name']=='sn46 - standards' and props(tree)['notes']=='v1.0.0'
assert props(tree)['class name']=='standards' and props(tree)['package name']=='com.insectlabs.standards'
baseline=json.loads((root/'tests/REVIEW-BASELINE.json').read_text(encoding='utf-8'))
for i,n in enumerate(tree[2]):
 key=str(i)+':'+n[0]
 if key in baseline['art']:assert hashlib.sha256(encode(n)).hexdigest()==baseline['art'][key],key
controls=next(n for n in tree[2] if n[0]=='controls')[2];assert len(controls)==len(baseline['geometry'])
for c in controls:
 p=props(c);assert baseline['geometry'][p['UUID']]=={k:p.get(k) for k in ['left','top','width','height']}
 name=p['variable name'];assert name==p['CEditableControl.name']
 m=re.search(re.escape(name)+r' = new Voltage\w+\( "([^"]+)", "([^"]+)"',source);assert m and m[1]==name and m[2]==p['display name'],name
for child in code[2]:
 for node in child[2]:
  p=props(node)
  if 'line number' in p:assert 0<=p['line number']<len(embedded.splitlines())
rs=regions(source)
stub='''import java.util.EnumSet;
import voltage.core.VoltageDigitalCounter.Flags;
class VoltageComponent {double value,visual;int reads;double GetValue(){reads++;return value;}void SetValue(double v){value=v;}void UpdateGUIValue(double v){visual=v;}}
class Base {int undoCount,destroyCount,stopCount;void StartGuiUpdateTimer(int n){}void StopGuiUpdateTimer(){stopCount++;}void Destroy(){destroyCount++;}void CreateUndoNode(String label,String type,double previous,double current){undoCount++;}String GetTooltipText(VoltageComponent c){return "";}void EditComponentValue(VoltageComponent c,double value,String text){c.value=value;}}
'''
fields=re.findall(r'private (Voltage\w+) (\w+);',source)
for typ in sorted(set(t for t,n in fields)):
 body='boolean[] bars=new boolean[4];void SetBarsForDigit(int d,EnumSet<Flags> flags){bars[d]=true;}void ClearBarsForDigit(int d){bars[d]=false;}' if typ=='VoltageDigitalCounter' else ''
 stub+='class '+typ+' extends VoltageComponent {'+body+'}\n'
stub+='public class Sn46Test extends Base {\nenum ModuleNotifications {Button_Changed,GUI_Update_Timer,Preset_Loading_Finish,Variation_Loading_Finish,Reset,Other}\n'
stub+='\n'.join(t+' '+n+'=new '+t+'();' for t,n in fields)+'\n'+rs['code-and-variables']
signatures={'Initialize':'void Initialize()','Destroy':'void Destroy()','Notify':'boolean Notify(VoltageComponent component,ModuleNotifications notification,double doubleValue,long longValue,int x,int y,Object object)','ProcessSample':'void ProcessSample()','ProcessBypassedSample':'void ProcessBypassedSample()','GetTooltipText':'String GetTooltipText(VoltageComponent component)','OnUndoRedo':'void OnUndoRedo(String undoType,double newValue,Object optionalObject)','GetStateInformation':'byte[] GetStateInformation()','SetStateInformation':'void SetStateInformation(byte[] stateInfo)','GetStateInformationForVariations':'byte[] GetStateInformationForVariations()','SetStateInformationForVariations':'void SetStateInformationForVariations(byte[] stateInfo)'}
for name,sig in signatures.items():
 body=rs[name]
 if name=='Destroy':body='super.Destroy();'+body
 stub+='\n'+sig+'{'+body+'}\n'
stub+='''static void check(boolean ok,String label){if(!ok)throw new AssertionError(label);}static void near(double actual,double expected,double tolerance,String label){check(Math.abs(actual-expected)<=tolerance,label+": "+actual+" vs "+expected);}
void event(ModuleNotifications n,double v){Notify(reference432Button,n,v,0,0,0,null);}
public static void main(String[] args){
 Sn46Test m=new Sn46Test();m.Initialize();near(m.decimalPointLed.value,1,0,"fixed decimal");near(m.voltageCounter.value,0,0,"initial display");check(m.leadingZeroOverrides[1]&&m.leadingZeroOverrides[2]&&m.leadingZeroOverrides[3],"zero padded startup");
 m.voltageKnob.value=2.25;m.fineVoltageKnob.value=.125;m.ProcessSample();near(m.outputPositive.value,2.375,0,"combined volts");near(m.outputNegative.value,-2.375,0,"inverted volts");m.refreshDisplay();near(m.voltageCounter.value,238,0,"rounded hundredths");check(!m.leadingZeroOverrides[1]&&!m.leadingZeroOverrides[2]&&m.leadingZeroOverrides[3],"leading-zero transition");
 near(m.output33V.value,3.3,0,"3.3V");near(m.output5V.value,5,0,"5V");near(m.output9V.value,9,0,"9V");near(m.output0V.value,0,0,"0V");
 near(m.phaseA,440./48000,1e-14,"A tuning");near(m.phaseFsharp,440*Math.pow(2,-3./12)/48000,1e-14,"lower F sharp");near(m.phase1Hz,1./48000,1e-14,"1Hz");near(m.phase100Hz,100./48000,1e-14,"100Hz");
 m.voltageKnob.value=99;m.fineVoltageKnob.value=2;m.ProcessSample();near(m.outputPositive.value,13,0,"13V maximum");m.refreshDisplay();near(m.voltageCounter.value,1300,0,"max display");check(!m.leadingZeroOverrides[3],"no leading zero at 13V");
 m.voltageKnob.value=Double.NaN;m.fineVoltageKnob.value=Double.POSITIVE_INFINITY;m.ProcessSample();near(m.outputPositive.value,0,0,"invalid knob values bounded");
 m.voltageKnob.value=-1;m.fineVoltageKnob.value=-1;m.ProcessSample();near(m.outputPositive.value,0,0,"negative knobs bounded");
 Sn46Test tones=new Sn46Test();int impulses=0,crossings=0;double previous=0;double peak=0,noiseEnergy=0;for(int i=0;i<48000;i++){tones.ProcessSample();if(tones.outputPulses.value!=0){near(tones.outputPulses.value,5,0,"pulse height");check(i%4096==0,"pulse schedule");impulses++;}if(previous<=0&&tones.outputA.value>0)crossings++;previous=tones.outputA.value;peak=Math.max(peak,Math.abs(previous));check(Double.isFinite(tones.outputNoise.value),"finite pink noise");noiseEnergy+=tones.outputNoise.value*tones.outputNoise.value;}
 check(impulses==12&&crossings==440,"measured pulse and A rates");near(peak,5,1e-8,"tone amplitude");check(noiseEnergy>0,"noise alive");
 tones.event(ModuleNotifications.Button_Changed,1);tones.event(ModuleNotifications.Button_Changed,0);check(tones.reference432&&tones.reference432Button.visual==1&&tones.undoCount==1,"432 press/release and undo");for(int i=0;i<12000;i++)tones.ProcessSample();near(tones.tuningHz,432,1e-8,"432 settling");
 byte[] saved=tones.GetStateInformation();check(saved.length==5&&saved[4]==1,"saved tuning");Sn46Test restored=new Sn46Test();restored.SetStateInformation(saved);check(restored.reference432&&restored.reference432Button.visual==1,"recall tuning and lamp");restored.SetStateInformationForVariations(tones.GetStateInformationForVariations());check(restored.reference432,"variation recall");
 restored.SetStateInformation(new byte[]{'S','N','4','6',2});check(restored.reference432,"invalid flag rejected");restored.SetStateInformation(new byte[]{'S','N','4','6',0,1});check(restored.reference432,"invalid length rejected");restored.SetStateInformation(null);check(restored.reference432,"null safe");
 restored.OnUndoRedo("reference432",0,null);check(!restored.reference432&&restored.reference432Button.visual==0,"undo tuning");restored.OnUndoRedo("reference432",1,null);check(restored.reference432,"redo tuning");for(int i=0;i<500;i++)restored.event(ModuleNotifications.Button_Changed,1);check(restored.reference432,"500 rapid toggles without releases");restored.event(ModuleNotifications.Reset,0);check(!restored.reference432&&restored.reference432Button.visual==0,"reset tuning");
 double phase=tones.phaseA;long seed=tones.noise.seed;int count=tones.pulseSample;int reads=tones.voltageKnob.reads;tones.ProcessBypassedSample();VoltageComponent[] outs={tones.outputPositive,tones.outputNegative,tones.output0V,tones.output33V,tones.output5V,tones.output9V,tones.outputA,tones.outputFsharp,tones.outputPulses,tones.output1Hz,tones.output100Hz,tones.outputNoise};for(VoltageComponent o:outs)near(o.value,0,0,"silent bypass");near(tones.phaseA,phase,0,"bypass phase freeze");check(tones.noise.seed==seed&&tones.pulseSample==count&&tones.voltageKnob.reads==reads,"bypass histories and reads frozen");tones.refreshDisplay();near(tones.voltageCounter.value,0,0,"bypass display");tones.ProcessSample();check(!tones.bypassed&&tones.phaseA!=phase,"resume");
 check(m.GetTooltipText(m.fineVoltageKnob).contains("V")&&m.GetTooltipText(m.outputFsharp).contains("below"),"units and corrected pitch tooltip");m.Destroy();check(m.destroyCount==1&&m.stopCount==1,"single base destruction and timer shutdown");
 System.out.println("PASS DC bounds/polarity/fixed outputs, precision display, sine/pulse timing, noise, 432 switching/undo/rapid presses/state/reset, bypass freeze and lifecycle");
}
}'''
with tempfile.TemporaryDirectory(prefix='sn46-checks-') as tmp:
 tmp=Path(tmp)
 for label,s in [('exported',source),('embedded',embedded)]:
  p=tmp/label;p.mkdir();f=p/'standards.java';f.write_text(s,encoding='utf-8');subprocess.run(['javac','--release','17','-Xlint:all','-Werror','-cp','C:/ProgramData/Voltage/voltage.jar','-d',str(p),str(f)],check=True)
 f=tmp/'Sn46Test.java';f.write_text(stub,encoding='utf-8');subprocess.run(['javac','--release','17','-cp','C:/ProgramData/Voltage/voltage.jar','-d',str(tmp),str(f)],check=True);subprocess.run(['java','-cp',str(tmp)+';C:/ProgramData/Voltage/voltage.jar','Sn46Test'],check=True)
print('PASS strict SDK compilation of both sources, metadata, artwork/layout preservation and binary integrity')
