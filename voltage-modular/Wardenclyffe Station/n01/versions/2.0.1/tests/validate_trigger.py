from pathlib import Path
import sys,re,subprocess,tempfile
sys.dont_write_bytecode=True
repo=next(p for p in Path(__file__).resolve().parents if (p/'voltage-modular/colorbox/tools').is_dir());sys.path.insert(0,str(repo/'voltage-modular/colorbox/tools'))
from value_tree import Reader,props,encode
from support import regions,normalized
dev=Path(__file__).resolve().parent.parent
s=(dev/'n01.java').read_text(encoding='utf-8-sig');raw=(dev/'n01.vmod').read_bytes();reader=Reader(raw);tree=reader.tree();assert reader.p==len(raw) and encode(tree)==raw
embedded=props(next(n for n in tree[2] if n[0]=='module code'))['MODULE.SOURCE'];assert normalized(s)==normalized(embedded)
assert props(tree)['notes']=='v2.0.1'
rs=regions(s)
stub='''class VoltageComponent {double value,visual;double GetValue(){return value;}void SetValue(double v){value=v;visual=v;}void SetValueNoNotification(double v,boolean gui){if(value!=v){value=v;if(gui)visual=v;}}}
class Base {void StartGuiUpdateTimer(int n){}void StopGuiUpdateTimer(){}}
'''
fields=re.findall(r'private (Voltage\w+) (\w+);',s)
for typ in sorted(set(t for t,n in fields)):
 stub+='class '+typ+' extends VoltageComponent {'+('boolean connected;boolean IsConnected(){return connected;}' if typ=='VoltageAudioJack' else '')+'}\n'
stub+='public class FlashTest extends Base {\nenum ModuleNotifications {Button_Changed,GUI_Update_Timer,Reset,Preset_Loading_Finish,Variation_Loading_Finish,Other}\n'
stub+='\n'.join(t+' '+n+'=new '+t+'();' for t,n in fields)+'\n'+rs['code-and-variables']
for region,sig in [('Initialize','void Initialize()'),('Notify','boolean Notify(VoltageComponent component,ModuleNotifications notification,double doubleValue,long longValue,int x,int y,Object object)'),('ProcessSample','void ProcessSample()'),('ProcessBypassedSample','void ProcessBypassedSample()')]:stub+='\n'+sig+'{'+rs[region]+'}\n'
stub+='''static void check(boolean ok,String message){if(!ok)throw new AssertionError(message);}
void event(ModuleNotifications n,double v){manualTriggerButton.value=v;manualTriggerButton.visual=v;Notify(manualTriggerButton,n,v,0,0,0,null);}
public static void main(String[] args){
 FlashTest m=new FlashTest();m.Initialize();m.triggerInput.connected=true;m.ProcessSample();m.updateLamps();check(m.manualTriggerButton.visual==0,"initial dark");
 m.triggerInput.value=5;m.ProcessSample();double held=m.heldVoltage;m.updateLamps();check(m.manualTriggerButton.visual==1&&m.triggerFlashSamples==4800,"external edge lights button");
 for(int i=0;i<4800;i++)m.ProcessSample();m.updateLamps();check(m.manualTriggerButton.visual==0&&m.heldVoltage==held,"held gate does not retrigger and lamp expires");
 m.triggerInput.value=.2;m.ProcessSample();m.triggerInput.value=5;m.ProcessSample();check(m.triggerFlashSamples==0,"hysteresis not rearmed above .1V");
 m.triggerInput.value=0;m.ProcessSample();m.triggerInput.value=5;m.ProcessSample();check(m.triggerFlashSamples==4800,"new edge renews flash");
 m.event(ModuleNotifications.Button_Changed,1);m.ProcessSample();m.event(ModuleNotifications.Button_Changed,0);check(m.manualTriggerButton.visual==1&&m.manualRequests.get()==0,"manual release preserves flash without resampling");
 for(int i=0;i<500;i++){
  m.event(ModuleNotifications.Button_Changed,1);m.ProcessSample();m.updateLamps();
  check(m.manualRequests.get()==0&&m.triggerFlashSamples==4800&&m.heldVoltage==m.sampleSourceOutput.value,"rapid press without release remains accepted");
  m.ProcessSample();
 }
 m.event(ModuleNotifications.Button_Changed,0);check(m.manualRequests.get()==0,"release never samples");
 m.ProcessBypassedSample();m.updateLamps();check(m.manualTriggerButton.visual==0,"bypass dark");m.ProcessSample();check(m.triggerFlashSamples==0,"resume high gate suppressed");
 m.triggerInput.value=0;m.ProcessSample();m.triggerInput.value=Double.NaN;m.ProcessSample();check(m.triggerFlashSamples==0,"nonfinite input ignored");
 m.event(ModuleNotifications.Button_Changed,1);m.ProcessSample();m.Notify(null,ModuleNotifications.Reset,0,0,0,0,null);check(m.triggerFlashSamples==0&&m.manualTriggerButton.visual==0,"reset clears flash");
 System.out.println("PASS external/manual flash, 100ms expiry, held gate, hysteresis, release, bypass/resume, invalid input, reset and 500 rapid presses with omitted release notifications");
}
}'''
with tempfile.TemporaryDirectory(prefix='n01-flash-') as t:
 t=Path(t)
 for label,source in [('exported',s),('embedded',embedded)]:
  folder=t/label;folder.mkdir();p=folder/'n01.java';p.write_text(source,encoding='utf-8')
  subprocess.run(['javac','--release','17','-Xlint:all','-Werror','-cp','C:/ProgramData/Voltage/voltage.jar','-d',str(folder),str(p)],check=True)
 f=t/'FlashTest.java';f.write_text(stub,encoding='utf-8')
 subprocess.run(['javac','--release','17','-d',str(t),str(f)],check=True)
 subprocess.run(['java','-cp',str(t),'FlashTest'],check=True)
print('PASS both SDK compilations and VMOD round-trip/source synchronization')
