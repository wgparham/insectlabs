from pathlib import Path
import sys,re,subprocess,tempfile,json,hashlib,argparse
ROOT=next((p for p in Path(__file__).resolve().parents if (p/'voltage-modular/colorbox/tools').is_dir()),Path(r'C:\Users\wgparham\Dropbox\git\insectlabs'))
sys.path.insert(0,str(ROOT/'voltage-modular/colorbox/tools'))
from value_tree import Reader,encode,props
from support import normalized,regions
parser=argparse.ArgumentParser();parser.add_argument('--module',type=Path,default=Path(__file__).resolve().parents[1]);parser.add_argument('--baseline',type=Path);args=parser.parse_args()
module=args.module;dev=module if (module/'lm-21_mk3.java').exists() else module/'development';java=dev/'lm-21_mk3.java';vmod=dev/'lm-21_mk3.vmod';source=java.read_text(encoding='utf-8-sig');raw=vmod.read_bytes();reader=Reader(raw);tree=reader.tree();assert reader.p==len(raw) and encode(tree)==raw
embedded=props(next(n for n in tree[2] if n[0]=='module code'))['MODULE.SOURCE'];assert normalized(source)==normalized(embedded)

controls=next(n for n in tree[2] if n[0]=='controls')[2]
assert props(tree)['notes'].splitlines()[0]=='v1.0.1'
names=[props(c)['variable name'] for c in controls];assert len(names)==len(set(names))
for control in controls:
 q=props(control);name=q['variable name'];assert q['CEditableControl.name']==name and q['display name'].strip()
 match=re.search(r'\b'+re.escape(name)+r'\s*=\s*new\s+Voltage\w+\(\s*"([^"]*)"\s*,\s*"([^"]*)"',source)
 if match:assert (match[1],match[2])==(name,q['display name'])
line_props=next(n for n in next(n for n in tree[2] if n[0]=='module code')[2] if n[0]=='line properties')
assert all(0<=props(n)['line number']<len(embedded.splitlines()) for n in line_props[2] if n[0]=='line info')
manifest=dev/'SHA256.json'
if manifest.exists():
 for relative,expected in json.loads(manifest.read_text()).items():assert hashlib.sha256((dev/relative).read_bytes()).hexdigest()==expected,relative

sdk=Path(r'C:\ProgramData\Voltage\voltage.jar')
HARNESS=r'''
static void check(boolean ok,String msg){if(!ok)throw new AssertionError(msg);}
static void near(double a,double b,double tol,String msg){check(Math.abs(a-b)<=tol,msg+": "+a+" vs "+b);}
static Headless fresh(){Headless m=new Headless();for(VoltageKnob k:new VoltageKnob[]{knobBassA_placeholder}){}return m;}
static Headless configured(){Headless m=new Headless();m.powerSwitch.value=1;
VoltageKnob[] bass={m.knobBassA,m.knobBassB,m.knobBassC,m.knobBassD};VoltageKnob[] treble={m.knobTrebleA,m.knobTrebleB,m.knobTrebleC,m.knobTrebleD};VoltageKnob[] level={m.levelA,m.levelB,m.levelC,m.levelD};
for(int i=0;i<4;i++){bass[i].value=.5;treble[i].value=.5;level[i].value=.5;}m.Initialize();m.powerGain=1;return m;}
static VoltageAudioJack[] inputs(Headless m){return new VoltageAudioJack[]{m.input1,m.input2,m.inputJack3,m.input4};}
static VoltageAudioJack[] outputs(Headless m){return new VoltageAudioJack[]{m.outputa,m.outputb,m.outputc,m.outputd};}
static VoltageKnob[][] matrix(Headless m){return new VoltageKnob[][]{{m.knobA1,m.knobA2,m.knobA3,m.knobA4},{m.knobB1,m.knobB2,m.knobB3,m.knobB4},{m.knobC,m.knobC2,m.knobC3,m.knobC4},{m.knobD1,m.knobD2,m.knobD3,m.knobD4}};}
static void refresh(Headless m){m.syncControlTargets();for(int r=0;r<4;r++){System.arraycopy(m.matrixTarget[r],0,m.matrixSmooth[r],0,4);m.bassGainSmooth[r]=m.bassGainTarget[r];m.trebleGainSmooth[r]=m.trebleGainTarget[r];m.levelSmooth[r]=m.levelTarget[r];m.distanceSmooth[r]=m.distanceTarget[r];}m.feedbackSmooth=m.feedbackTarget;m.dampFactSmooth=m.dampFactTarget;m.ageSmooth=m.ageTarget;m.pitchModSmooth=m.pitchModTarget;}
public static void main(String[] args){
boolean strict=args.length==0;long digest=1;long started=System.nanoTime();

for(int row=0;row<4;row++)for(int col=0;col<4;col++){
Headless n=configured();matrix(n)[row][col].value=-.37;n.Notify(matrix(n)[row][col],ModuleNotifications.Knob_Changed,-.37,0,0,0,null);near(n.matrixTarget[row][col],-.37,0,"Knob_Changed mapping");for(int r=0;r<4;r++)for(int c=0;c<4;c++)if(r!=row||c!=col)near(n.matrixTarget[r][c],0,0,"notification independence");}
Headless edits=configured();for(double db:new double[]{-12,0,12}){edits.EditComponentValue(edits.knobBassA,db,"");near(edits.knobBassA.value,db/24+.5,1e-12,"typed EQ inverse");}for(double seconds:new double[]{.5,5.5,30}){edits.EditComponentValue(edits.distancePerspective,seconds,"");near(perspectiveToSeconds(edits.distancePerspective.value),seconds,1e-12,"typed decay inverse");check(edits.GetTooltipText(edits.distancePerspective).contains("s"),"decay tooltip");}edits.EditComponentValue(edits.levelA,0,"");near(edits.levelA.value,.5,1e-12,"typed unity gain");edits.EditComponentValue(edits.distanceA,50,"");near(edits.distanceA.value,.5,0,"typed distance percent");edits.EditComponentValue(edits.levelA,Double.NaN,"");near(edits.levelA.value,.5,0,"nonfinite edit ignored");edits.Notify(null,ModuleNotifications.Preset_Loading_Finish,0,0,0,0,null);check(edits.resumePending,"preset restore resumes safely");

for(int row=0;row<4;row++)for(int col=0;col<4;col++)for(int polarity:new int[]{-1,1}){
Headless m=configured();matrix(m)[row][col].value=polarity;refresh(m);double energy=0;
for(int i=0;i<4096;i++){inputs(m)[col].value=Math.sin(i*2*Math.PI*1000/48000);m.ProcessSample();for(int r=0;r<4;r++){double out=outputs(m)[r].value;check(Double.isFinite(out),"finite output");if(r==row)energy+=out*out;else near(out,0,0,"row independence");digest=31*digest+Double.doubleToLongBits(out);}near(m.outputMix.value,gentleFullMix(.25*outputs(m)[row].value),1e-14,"FULL MIX");}check(energy>100,"matrix routing "+row+"/"+col);
}
Headless m=configured();m.multIn.value=-3.25;m.ProcessSample();for(VoltageAudioJack j:new VoltageAudioJack[]{m.multOut1,m.multOut2,m.multOut3})near(j.value,-3.25,0,"multiple active");
for(int k=0;k<4;k++)inputs(m)[k].value=k+1;double frozen=m.powerGain;int reads=m.powerSwitch.reads;double history=m.inputPrevY[0];m.ProcessBypassedSample();for(int k=0;k<4;k++)near(outputs(m)[k].value,k+1,0,"bypass direct");near(m.outputMix.value,0,0,"bypass FULL MIX");near(m.inputPrevY[0],history,0,"bypass DSP frozen");if(strict){near(m.powerGain,frozen,0,"bypass power frozen");check(m.powerSwitch.reads==reads,"bypass must not read controls");}
m.ProcessSample();check(!m.resumePending,"resume clears pending state");
m=configured();m.powerGain=0;for(int i=0;i<652801;i++)m.updatePowerState(true);near(m.powerGain,1,1e-12,"13.6s warmup");for(int i=0;i<100801;i++)m.updatePowerState(false);near(m.powerGain,0,1e-12,"2.1s cooldown");m.powerSwitch.value=0;m.multIn.value=2;m.ProcessSample();for(VoltageAudioJack j:outputs(m))near(j.value,0,0,"power-off row mute");near(m.outputMix.value,0,0,"power-off mix mute");near(m.multOut1.value,2,0,"power-off multiple live");
for(int i=0;i<=1000;i++){double p=i/1000.;near(secondsToPerspective(perspectiveToSeconds(p)),p,1e-12,"perspective inverse");}near(perspectiveToSeconds(0),.5,0,"minimum decay");near(perspectiveToSeconds(.6),5.5,1e-12,"decay breakpoint");near(perspectiveToSeconds(1),30,1e-12,"maximum decay");near(mixKnobToGain(.5),1,0,"noon unity");near(mixKnobToGain(1),4,0,"maximum MIX");
for(double p:new double[]{0,.6,1}){
m=configured();m.distancePerspective.value=p;m.distanceA.value=1;matrix(m)[0][0].value=1;m.levelA.value=1;m.knobBassA.value=1;m.knobTrebleA.value=1;refresh(m);double peak=0,late=0;
for(int i=0;i<1440000;i++){m.input1.value=i<128?20*Math.sin(i*.17):0;m.ProcessSample();for(VoltageAudioJack j:outputs(m))check(Double.isFinite(j.value)&&Math.abs(j.value)<100,"reverb stability");peak=Math.max(peak,Math.abs(m.outputa.value));if(i>1300000)late+=m.outputa.value*m.outputa.value;near(m.outputb.value,0,0,"reverb row isolation");digest=31*digest+Double.doubleToLongBits(m.outputa.value);}check(peak>1,"reverb excited");if(p==0)check(late<1e-5,"short tail decays");}
m=configured();matrix(m)[0][0].value=1;refresh(m);for(double bad:new double[]{Double.NaN,Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY}){m.input1.value=bad;m.ProcessSample();check(Double.isFinite(m.outputa.value),"nonfinite input sanitized");}m.input1.connected=false;m.input1.value=99;near(readInput(m.input1),0,0,"unpatched input silent");
System.out.println("PASS: 32 signed crosspoints, row/reverb independence, FULL MIX, MULT, bypass/resume, power, 1001 taper inverses, hot-input/30s-tail stability, nonfinite/unpatched inputs");
System.out.println("ACTIVE_DIGEST="+Long.toUnsignedString(digest));System.out.println("Offline harness elapsed seconds="+(System.nanoTime()-started)/1e9);
}
'''
HARNESS=re.sub(r'static Headless fresh\(\).*?\n','',HARNESS)
# Adapt historical identifiers to cleaned metadata without changing DSP equations.
ALIASES={'input1':'input1Jack','input2':'input2Jack','inputJack3':'input3Jack','input4':'input4Jack','knobC':'matrixC1Knob','outputMix':'fullMixOutput','distancePerspective':'perspectiveKnob'}
for row in 'ABCD':
 for col in '1234':ALIASES[f'knob{row}{col}']=f'matrix{row}{col}Knob'
 ALIASES['output'+row.lower()]='output'+row+'Jack';ALIASES['level'+row]='mix'+row+'Knob';ALIASES['distance'+row]='distance'+row+'Knob';ALIASES['knobBass'+row]='bass'+row+'Knob';ALIASES['knobTreble'+row]='treble'+row+'Knob'
def harness(s):
 rs=regions(s);decls=re.findall(r'private (Voltage\w+) (\w+);',s)
 fields='\n'.join(f'private {typ} {name}=new {typ}();' for typ,name in decls)
 enums=','.join(dict.fromkeys(re.findall(r'case (\w+):',rs['Notify'])))
 stubs='static enum ModuleNotifications {'+enums+'}\n'+'\n'.join(f'static class {typ} extends Mock {{}}' for typ in sorted(set(t for t,n in decls)))
 out='class TestBase {String GetTooltipText(Headless.VoltageComponent c){return "";}}\npublic class Headless extends TestBase {\nstatic class VoltageComponent {}\nstatic class Mock extends VoltageComponent {double value;boolean connected=true;int reads;double GetValue(){reads++;return value;}void SetValue(double v){value=v;}boolean IsConnected(){return connected;}}\n'+stubs+'\n'+fields+'\n'+rs['code-and-variables']+'\nvoid Initialize(){'+rs['Initialize']+'}\nvoid ProcessSample(){'+rs['ProcessSample']+'}\nvoid ProcessBypassedSample(){'+rs['ProcessBypassedSample']+'}\nboolean Notify(VoltageComponent component,ModuleNotifications notification,double doubleValue,long longValue,int x,int y,Object object){'+rs['Notify']+'}\nString GetTooltipText(VoltageComponent component){'+rs['GetTooltipText']+'}\nvoid EditComponentValue(VoltageComponent component,double newValue,String newText){'+rs['EditComponentValue']+'}\n'+HARNESS+'\n}'
 if 'input1Jack' in s:
  out=re.sub(r'\b('+ '|'.join(map(re.escape,ALIASES))+r')\b',lambda m:ALIASES[m[0]],out)
 return out
with tempfile.TemporaryDirectory(prefix='lm21-tests-') as tmp:
 tmp=Path(tmp);classname=re.search(r'public class (\w+)',source)[1]
 for label,s in [('exported',source),('embedded',embedded)]:
  folder=tmp/label;folder.mkdir();p=folder/(classname+'.java');p.write_text(s,encoding='utf-8');subprocess.run(['javac','--release','17','-Xlint:all','-Werror','-encoding','utf-8','-cp',str(sdk),'-d',str(folder),str(p)],check=True)
 print('PASS: both source forms compile with all warnings treated as errors; source parity and project round-trip')
 results=[]
 for label,s in ([('baseline',args.baseline.read_text(encoding='utf-8-sig'))] if args.baseline else [])+[('candidate',source)]:
  folder=tmp/label;folder.mkdir(exist_ok=True);p=folder/'Headless.java';p.write_text(harness(s),encoding='utf-8');subprocess.run(['javac','--release','17','-Xlint:all','-Werror','-d',str(folder),str(p)],check=True)
  result=subprocess.run(['java','-cp',str(folder),'Headless']+(['baseline'] if label=='baseline' else []),capture_output=True,text=True);print(label+':\n'+result.stdout+result.stderr);result.check_returncode();results.append(re.search(r'ACTIVE_DIGEST=(\d+)',result.stdout)[1])
 if len(results)==2:assert results[0]==results[1],'Active DSP changed during cleanup';print('PASS: baseline/candidate active callback outputs are sample-for-sample identical')
print('LIMITATIONS: SDK controls mocked for callback tests; no native-host load/listening or host CPU measurement.')

