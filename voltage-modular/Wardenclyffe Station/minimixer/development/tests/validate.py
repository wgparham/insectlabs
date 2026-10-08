from pathlib import Path
import sys, re, subprocess, tempfile
ROOT = next(p for p in Path(__file__).resolve().parents if (p/'voltage-modular/colorbox/tools').exists())
DEV = Path(__file__).resolve().parents[1]
sys.path.insert(0,str(ROOT/'voltage-modular/colorbox/tools'))
from value_tree import Reader,encode,props
from support import normalized,regions
stem='rm1010' if (DEV/'rm1010.java').exists() else 'minimixer'
source=(DEV/(stem+'.java')).read_text(encoding='utf-8-sig');raw=(DEV/(stem+'.vmod')).read_bytes();reader=Reader(raw);tree=reader.tree()
assert reader.p==len(raw) and encode(tree)==raw
code=props(next(n for n in tree[2] if n[0]=='module code'))['MODULE.SOURCE'];assert normalized(code)==normalized(source)
assert props(tree)['notes'].splitlines()[0]=='v1.0.0'
rs=regions(source);decls=re.findall(r'private (Voltage\w+) (\w+);',source)
fields='\n'.join(f'{typ} {name}=new {typ}();' for typ,name in decls)
stubs='\n'.join(f'static class {typ} extends VoltageComponent {{}}' for typ in sorted({typ for typ,name in decls}))
enum=','.join(dict.fromkeys(re.findall(r'case (\w+):',rs['Notify'])))
h=r"""
static void check(boolean ok,String s){if(!ok)throw new AssertionError(s);}
static void near(double a,double b,double e,String s){check(Math.abs(a-b)<=e,s+": "+a+" / "+b);}
static Headless configured(int ch,double tilt,double send){Headless m=new Headless();VoltageSwitch[] ranges={m.switchRange1,m.switchRange2,m.switchRange3,m.switchRange4};VoltageKnob[] volumes={m.volume1,m.volume2,m.volume3,m.volume4};VoltageKnob[] tilts={m.balance1,m.balance2,m.balance3,m.balance4};VoltageKnob[] sends={m.process1,m.process2,m.process3,m.process4};ranges[ch].value=1;volumes[ch].value=1;tilts[ch].value=tilt;sends[ch].value=send;m.Initialize();return m;}
static VoltageAudioJack[] ins(Headless m){return new VoltageAudioJack[]{m.input1,m.input2,m.input3,m.input4};}
static double tone(double f,double tilt){Headless m=configured(0,tilt,0);double energy=0;for(int n=0;n<24000;n++){m.input1.value=.05*Math.sin(2*Math.PI*f*n/48000);m.ProcessSample();if(n>=12000)energy+=m.output3.value*m.output3.value;}return Math.sqrt(energy/12000);}
public static void main(String[] args){
for(int ch=0;ch<4;ch++){
 Headless m=configured(ch,0,1);double a=0,b=0,s=0;
 for(int n=0;n<12000;n++){ins(m)[ch].value=Math.sin(2*Math.PI*1000*n/48000);m.ProcessSample();a+=m.output3.value*m.output3.value;b+=m.output7.value*m.output7.value;s+=m.sendJack.value*m.sendJack.value;}
 check((ch<2?a:b)>100,"channel routing "+ch);near(ch<2?b:a,0,0,"pair independence");check(s>100,"channel send "+ch);
 m.rangeSwitches[ch].value=0;m.Notify(m.rangeSwitches[ch],ModuleNotifications.Switch_Changed,0,0,0,0,null);
 for(int n=0;n<48000;n++){ins(m)[ch].value=Math.sin(2*Math.PI*1000*n/48000);m.ProcessSample();}
 near(m.output3.value,0,1e-6,"mute 3");near(m.output7.value,0,1e-6,"mute 7");near(m.sendJack.value,0,1e-6,"mute send");check(m.mixerCore.ledEnvelope[ch]>.1,"muted LED active");
}
Headless m=configured(0,0,0);m.input1.value=1;m.input2.value=2;m.input3.value=3;m.input4.value=4;m.returnJack.value=5;m.linkInput.value=6;int reads=m.gain1.reads+m.switchRange1.reads;int pos=m.mixerCore.up[0].position;int countdown=m.ledCountdown;m.ProcessBypassedSample();near(m.output3.value,3,0,"raw bypass 3");near(m.output7.value,7,0,"raw bypass 7");near(m.output10.value,21,0,"raw bypass 10");near(m.sendJack.value,0,0,"bypass S");check(reads==m.gain1.reads+m.switchRange1.reads,"no bypass control reads");check(pos==m.mixerCore.up[0].position&&countdown==m.ledCountdown,"bypass histories frozen");m.ProcessSample();check(!m.resumePending,"resume handled");
for(int ext=0;ext<2;ext++){m=new Headless();m.Initialize();double energy=0;for(int n=0;n<12000;n++){if(ext==0)m.returnJack.value=Math.sin(n*.1);else m.linkInput.value=Math.sin(n*.1);m.ProcessSample();near(m.output3.value,0,0,"external excluded from 3");near(m.output7.value,0,0,"external excluded from 7");near(m.sendJack.value,0,0,"external excluded from S");energy+=m.output10.value*m.output10.value;}check(energy>100,"external enters final");}
Rm1010Core c=new Rm1010Core();for(int r=1;r<=3;r++){c.setControls(0,1,0,1,0,r);near(c.gainForKnob(0,1),new double[]{0,2,5,100}[r],0,"gain maximum");near(c.gainForKnob(0,0),1,0,"gain unity");near(c.gainForKnob(0,.5),1+(new double[]{0,2,5,100}[r]-1)*.25,0,"squared taper");}
for(double f:new double[]{100,900,9000}){double flat=tone(f,0),left=tone(f,-1),right=tone(f,1);if(f==900){near(left/flat,1,.025,"left pivot unity");near(right/flat,1,.025,"right pivot unity");}else if(f<900){check(left>flat*1.8&&right<flat*.65,"bass tilt direction");}else check(right>flat*1.8&&left<flat*.65,"treble tilt direction");}
m=configured(0,0,0);m.switchRange1.value=3;m.Notify(m.switchRange1,ModuleNotifications.Switch_Changed,3,0,0,0,null);m.EditComponentValue(m.gain1,50,"");near(m.mixerCore.target[0][0],50,1e-10,"typed gain");m.EditComponentValue(m.volume1,50,"");near(m.mixerCore.target[0][1],.5,0,"typed volume");m.EditComponentValue(m.process1,75,"");near(m.mixerCore.target[0][2],.75,0,"typed process");m.EditComponentValue(m.balance1,3,"");near(m.balance1.value,.5,0,"typed tilt");m.Notify(null,ModuleNotifications.Preset_Loading_Finish,0,0,0,0,null);check(m.resumePending,"preset restore");
m=new Headless();m.Initialize();for(int ch=0;ch<4;ch++){m.rangeSwitches[ch].value=3;m.gainKnobs[ch].value=1;m.volumeKnobs[ch].value=1;m.processKnobs[ch].value=1;m.balanceKnobs[ch].value=ch%2==0?1:-1;}m.syncControlTargets();for(int n=0;n<96000;n++){for(int ch=0;ch<4;ch++)ins(m)[ch].value=1000*Math.sin(n*(.14+ch*.11));m.ProcessSample();for(double v:m.mixerCore.outputs)check(Double.isFinite(v)&&Math.abs(v)<30,"hot signal stability");}m.input1.value=Double.NaN;m.input2.value=Double.POSITIVE_INFINITY;m.input3.connected=false;m.ProcessSample();for(double v:m.mixerCore.outputs)check(Double.isFinite(v),"invalid/unpatched inputs safe");
System.out.println("DIGEST="+Long.toUnsignedString(audioDigest));
System.out.println("PASS: channel/pair/send routing, mute with live LEDs, RETURN/LINK isolation, gain ranges/taper, 900 Hz tilt, typed edits, preset/resume, raw bypass/frozen histories, hot-input stability.");
}
"""
def make_headless(text):
 rs=regions(text);decls=re.findall(r'private (Voltage\w+) (\w+);',text)
 fields='\n'.join(f'{typ} {name}=new {typ}();' for typ,name in decls)
 stubs='\n'.join(f'static class {typ} extends VoltageComponent {{}}' for typ in sorted({typ for typ,name in decls}))
 enum=','.join(dict.fromkeys(re.findall(r'case (\w+):',rs['Notify'])))
 headless='class TestBase {String GetTooltipText(Headless.VoltageComponent c){return "";}}\npublic class Headless extends TestBase {\nstatic long audioDigest = 1;\nstatic class VoltageComponent {double value;boolean connected=true;int reads;double GetValue(){reads++;return value;}void SetValue(double v){value=v;}boolean IsConnected(){return connected;}}\n'+stubs+'\nenum ModuleNotifications {'+enum+'}\n'+fields+'\n'+rs['code-and-variables']
 for name,sig in [('Initialize','void Initialize()'),('ProcessSample','void ProcessSample()'),('ProcessBypassedSample','void ProcessBypassedSample()'),('Notify','boolean Notify(VoltageComponent component,ModuleNotifications notification,double doubleValue,long longValue,int x,int y,Object object)'),('GetTooltipText','String GetTooltipText(VoltageComponent component)'),('EditComponentValue','void EditComponentValue(VoltageComponent component,double newValue,String newText)')]:headless+='\n'+sig+'{'+rs[name]+('\nfor(double sample:mixerCore.outputs) audioDigest=31*audioDigest+Double.doubleToLongBits(sample);' if name=='ProcessSample' else '')+'}\n'
 headless+=h+'\n}'
 return headless
with tempfile.TemporaryDirectory(prefix='rm1010-checks-') as tmp:
 tmp=Path(tmp);sdk=Path(r'C:\ProgramData\Voltage\voltage.jar')
 for label,text in [('exported',source),('embedded',code)]:
  folder=tmp/label;folder.mkdir();p=folder/'rm1010.java';p.write_text(text,encoding='utf-8');subprocess.run(['javac','--release','17','-encoding','utf-8','-Xlint:all','-Werror','-cp',str(sdk),'-d',str(folder),str(p)],check=True)
 digests=[]
 for label,text in [('candidate',source),('baseline',(DEV/'tests/baseline/pre-cleanup.java.txt').read_text(encoding='utf-8-sig'))]:
  folder=tmp/label;folder.mkdir(exist_ok=True);p=folder/'Headless.java';p.write_text(make_headless(text),encoding='utf-8');subprocess.run(['javac','--release','17','-encoding','utf-8','-Xlint:all','-Werror','-d',str(folder),str(p)],check=True);result=subprocess.run(['java','-cp',str(folder),'Headless'],check=True,capture_output=True,text=True);print(label+': '+result.stdout);digests.append(re.search(r'DIGEST=(\d+)',result.stdout)[1])
 assert digests[0]==digests[1], 'Cleanup changed audio output'
 print('PASS: baseline/candidate active callback outputs match sample-for-sample.')
print('PASS: both source forms compile warning-clean; normalized source agreement and Designer round-trip. Host load/listening/CPU remain user checks.')
