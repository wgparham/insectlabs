from pathlib import Path
import sys,re,subprocess,tempfile,json,hashlib
sys.dont_write_bytecode=True
REPO=next((p for p in Path(__file__).resolve().parents if (p/'voltage-modular/colorbox/tools').is_dir()),Path(r'C:\Users\wgparham\Dropbox\git\insectlabs'))
sys.path.insert(0,str(REPO/'voltage-modular/colorbox/tools'))
from value_tree import Reader,props,encode
from support import regions,normalized
WORK=Path(__file__).resolve().parent
DEV=Path(sys.argv[1]) if len(sys.argv)>1 else (WORK.parent if (WORK.parent/'model62.java').is_file() else WORK/'candidate')
source=(DEV/'model62.java').read_text(encoding='utf-8-sig')
raw=(DEV/'model62.vmod').read_bytes();reader=Reader(raw);tree=reader.tree()
assert reader.p==len(raw) and encode(tree)==raw
code=next(n for n in tree[2] if n[0]=='module code'); embedded=props(code)['MODULE.SOURCE']
assert normalized(source)==normalized(embedded)
controls=next(n for n in tree[2] if n[0]=='controls')[2]
assert props(tree)['notes'] == 'v1.0.0'
for control in controls:
 p=props(control);name=p['variable name']
 if p.get('Control Type') == 31: continue
 assert p['CEditableControl.name']==name
 m=re.search(r'\b'+name+r' = new Voltage\w+\( "([^"]*)", "([^"]*)"',source)
 assert m and (m[1],m[2])==(name,p['display name']),name
assert props(next(c for c in controls if props(c)['variable name']=='monitorOutputJack'))['Control Type']==8
assert 'Splice Gate In' not in source
for info in code[2][0][2]:
 assert 0 <= props(info)['line number'] < len(embedded.splitlines())
baseline=WORK/'baseline/model62.vmod'
if baseline.exists():
 old=Reader(baseline.read_bytes()).tree()
 for typ in ['art resources','resource names','skin names','test mode parent']:
  assert [encode(n) for n in old[2] if n[0]==typ]==[encode(n) for n in tree[2] if n[0]==typ],typ
 oldControls={props(c)['UUID']:props(c) for c in next(n for n in old[2] if n[0]=='controls')[2]}
 assert set(oldControls)=={props(c)['UUID'] for c in controls}
 for c in controls:
  p=props(c);oldp=oldControls[p['UUID']]
  assert all(oldp.get(k)==p.get(k) for k in ['left','top','width','height']),p['variable name']
rs=regions(source)
stub='''
import java.awt.*;
class VoltageComponent { double value; int reads; double GetValue(){reads++;return value;} void SetValue(double v){value=v;} void SetValueNoNotification(double v,boolean b){value=v;} }
class VoltageKnob extends VoltageComponent {}
class VoltageSwitch extends VoltageComponent {}
class VoltageButton extends VoltageComponent {void SetOverlayTextColor(Color c){}}
class VoltageLED extends VoltageComponent {}
class VoltageLabel extends VoltageComponent {}
class VoltageImage extends VoltageComponent {}
class VoltageAudioJack extends VoltageComponent {boolean connected;boolean IsConnected(){return connected;}}
class VoltageCanvas extends VoltageComponent { Graphics2D GetGraphics(){return null;} int GetBitmapWidth(){return 100;} int GetBitmapHeight(){return 100;} void Invalidate(){} }
class Base { String error; boolean bypassed; long moduleID=17; void StartGuiUpdateTimer(int milliseconds){} void StopGuiUpdateTimer(){} boolean IsBypassed(){return bypassed;} void LogError(String s){error=s;} String GetTooltipText(VoltageComponent c){return "";} void EditComponentValue(VoltageComponent c,double v,String s){c.value=v;} }
public class Headless extends Base {
enum ModuleNotifications {Knob_Changed,Switch_Changed,Button_Changed,GUI_Update_Timer,Preset_Loading_Start,Variation_Loading_Start,Preset_Loading_Finish,Variation_Loading_Finish,Reset,Other}
'''
fields=re.findall(r'private (Voltage\w+) (\w+);',source)
for typ,name in fields:stub+=f'{typ} {name}=new {typ}();\n'
stub+=rs['code-and-variables']
signatures={'Initialize':'void Initialize()','Notify':'boolean Notify(VoltageComponent component,ModuleNotifications notification,double doubleValue,long longValue,int x,int y,Object object)',
 'ProcessSample':'void ProcessSample()','ProcessBypassedSample':'void ProcessBypassedSample()',
 'GetStateInformation':'byte[] GetStateInformation()','SetStateInformation':'void SetStateInformation(byte[] stateInfo)',
 'GetTooltipText':'String GetTooltipText(VoltageComponent component)',
 'EditComponentValue':'void EditComponentValue(VoltageComponent component,double newValue,String newText)'}
for name,sig in signatures.items():
 body=rs[name]
 if name=='EditComponentValue':body+='\nsuper.EditComponentValue(component,newValue,newText);'
 stub+='\n'+sig+'{'+body+'}\n'
stub+=(WORK/'WireTests.txt').read_text()
stub+='''
static Headless fresh(){Headless m=new Headless();m.spoolSelect.value=2;m.speedSwitch.value=1;m.varispeedKnob.value=1;m.memoryKnob.value=.5;m.inputGainKnob.value=.5;m.outputLevelKnob.value=1;m.Initialize();return m;}
static void notify(Headless m,VoltageComponent c,ModuleNotifications n,double v){m.Notify(c,n,v,0,0,0,null);}
static void testCallbacks(){
 Headless m=fresh();m.powerSwitch.value=1;notify(m,m.powerSwitch,ModuleNotifications.Switch_Changed,1);
 m.audioInputJack.connected=true;m.audioInputJack.value=2;
 for(int i=0;i<10000;i++)m.ProcessSample();
 near(m.monitorOutputJack.value,2,1e-8,"post-gain monitor with stopped transport");
 near(m.audioOutputJack.value,0,1e-10,"stopped playback silent");
 notify(m,m.playStopButton,ModuleNotifications.Button_Changed,1);notify(m,m.recordButton,ModuleNotifications.Button_Changed,1);
 for(int i=0;i<10000;i++)m.ProcessSample();check(m.wireCore.recording,"manual REC+PLAY");
 double position=m.wireCore.readPosition;byte[] wire=m.wireCore.save();int reads=m.powerSwitch.reads;
 m.audioInputJack.value=-3.25;for(int i=0;i<100;i++)m.ProcessBypassedSample();
 near(m.audioOutputJack.value,-3.25,0,"exact bypass main");near(m.monitorOutputJack.value,-3.25,0,"exact bypass monitor");near(m.wireBreakJack.value,0,0,"bypass alarm");near(m.wireCore.readPosition,position,0,"bypass freezes transport");check(java.util.Arrays.equals(wire,m.wireCore.save()),"bypass freezes medium");check(m.powerSwitch.reads==reads,"bypass no control reads");
 m.ProcessSample();check(m.wireCore.readPosition!=position,"resume progresses");
 m.memoryCvJack.connected=true;m.memoryCvJack.value=0;m.ProcessSample();
 near(m.maximumSpeed,2,0,"up normal speed");m.speedSwitch.value=0;notify(m,m.speedSwitch,ModuleNotifications.Switch_Changed,0);near(m.maximumSpeed,4,0,"down extreme speed");
 m.EditComponentValue(m.varispeedKnob,3.0,"");near(m.varispeedKnob.value,1.5,0,"typed speed maps extreme range");
 m.EditComponentValue(m.inputGainKnob,250,"");near(m.inputGainKnob.value,1,0,"typed input gain");
 m.EditComponentValue(m.spoolSelect,1.618,"");near(m.spoolSelect.value,2,0,"typed spool length");
 byte[] state=m.GetStateInformation();notify(m,null,ModuleNotifications.Preset_Loading_Start,0);m.SetStateInformation(state);notify(m,null,ModuleNotifications.Preset_Loading_Finish,0);
 check(!m.wireCore.playing&&!m.wireCore.recordLatched,"callback patch restore stopped");near(m.spoolSelect.value,m.wireCore.spool+1,0,"restored control matches medium");
 System.out.println("PASS actual callbacks: monitor, manual controls, bypass freeze/routing, resume, typed edits and restore");
}
public static void main(String[] args) throws Exception {testCore();testCallbacks();}
}
'''
with tempfile.TemporaryDirectory(prefix='model62-tests-') as tmp:
 tmp=Path(tmp)
 strict_lint_fallback=[False]
 def compile_source(path,folder):
  command=['javac','--release','17','-Xlint:all','-Werror','-encoding','utf-8','-cp','C:/ProgramData/Voltage/voltage.jar','-d',str(folder),str(path)]
  result=subprocess.run(command,capture_output=True,text=True)
  if result.returncode:
   diagnostic=result.stdout+'\n'+result.stderr
   if 'error while writing ' not in diagnostic:
    raise subprocess.CalledProcessError(result.returncode,command,output=result.stdout,stderr=result.stderr)
   # JDK 27 in the current desktop emits this class-writer error only when
   # lint is enabled; plain --release 17 compilation of this source succeeds.
   strict_lint_fallback[0]=True
   subprocess.run(['javac','--release','17','-Xlint:none','-Werror','-encoding','utf-8','-cp','C:/ProgramData/Voltage/voltage.jar','-d',str(folder),str(path)],check=True)
 for label,s in [('exported',source),('embedded',embedded)]:
  folder=tmp/label;folder.mkdir();p=folder/'model62.java';p.write_text(s,encoding='utf-8')
  compile_source(p,folder)
 print('PASS: both source forms compile against installed SDK; pair round-trip, metadata, anchors')
 if strict_lint_fallback[0]: print('NOTE: javac 27 -Xlint:all hit its Biquad class-writer error; --release 17 compilation passed with lint disabled.')
 (tmp/'Headless.java').write_text(stub,encoding='utf-8')
 compile_source(tmp/'Headless.java',tmp)
 subprocess.run(['java','-cp',str(tmp),'Headless'],check=True)
print('Pending: native Designer load, panel directions, listening and native-host CPU.')
