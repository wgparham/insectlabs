"""Compile the matched pair and test actual user callbacks plus filter responses.

Usage: python validate.py [development-folder]
SDK: C:/ProgramData/Voltage/voltage.jar. Disposable classes use a system temp folder.
"""
from pathlib import Path
import sys, subprocess, tempfile, json, re, os
sys.dont_write_bytecode=True
repo=Path(r'C:\Users\wgparham\Dropbox\git\insectlabs')
sys.path.insert(0,str(repo/'voltage-modular/colorbox/tools'))
from value_tree import Reader,props,encode
from support import normalized,regions
base=Path(sys.argv[1]) if len(sys.argv)>1 else Path(__file__).resolve().parent
if not (base/'selective_service.java').exists(): base=base.parent/'development'
source=(base/'selective_service.java').read_text(encoding='utf-8-sig')
raw=(base/'selective_service.vmod').read_bytes();reader=Reader(raw);tree=reader.tree()
assert reader.p==len(raw) and encode(tree)==raw
embedded=props(next(n for n in tree[2] if n[0]=='module code'))['MODULE.SOURCE']
assert normalized(source)==normalized(embedded)
assert props(tree)['notes']=='v1.0.0'
controls={props(n)['variable name']:props(n) for n in next(n for n in tree[2] if n[0]=='controls')[2]}
for name,c in controls.items():
    assert re.fullmatch('[a-z][a-zA-Z0-9]*',name),name
    assert c['CEditableControl.name']==name and c['display name']!=name
for name,default in [('fineKnob',0),('gainKnob',0),('amplitudeKnob',0),('widthSlider',0),('slopeSlider',4),('powerSwitch',0)]:
    assert controls[name]['defaultValue']==default==float(controls[name]['defaultValueString']),name
assert float(controls['courtesyOutput']['width'])==22==float(controls['courtesyOutput']['height'])
assert controls['slopeSlider']['Slider:numDiscreteSteps']==12
assert controls['gainKnob']['minValue']==-24 and controls['gainKnob']['maxValue']==24
assert controls['amplitudeKnob']['minValue']==-1 and controls['amplitudeKnob']['maxValue']==1
assert source.count('super.Destroy();')==1 and source.count('StopGuiUpdateTimer();')==1
r=regions(source)
h='''public class Checks extends MockModule {
 static class VoltageComponent {
   double value; boolean connected; int reads;
   double GetValue(){reads++;return value;} void SetValue(double x){value=x;}
   boolean IsConnected(){reads++;return connected;}
 }
 static class VoltageAudioJack extends VoltageComponent {}
 enum ModuleNotifications {GUI_Update_Timer,Reset,Preset_Loading_Finish,Variation_Loading_Finish}
 void StartGuiUpdateTimer() {} void StopGuiUpdateTimer() {}
'''
for name in controls:
    if name.endswith('Label'):continue
    kind='VoltageAudioJack' if name.endswith(('Input','Output')) else 'VoltageComponent'
    h+=f' {kind} {name}=new {kind}();\n'
for name,sig in [('Initialize','void initialize()'),('ProcessSample','void process()'),('ProcessBypassedSample','void bypass()'),('Notify','boolean notify(VoltageComponent component,ModuleNotifications notification)'),('GetTooltipText','String tooltip(VoltageComponent component)'),('EditComponentValue','void edit(VoltageComponent component,double newValue,String newText)')]:
    body=r[name]
    if name=='EditComponentValue':body+='super.EditComponentValue(component,newValue,newText);'
    h+=sig+' {\n'+body+'\n}\n'
h+=r['code-and-variables']
h+='''
 static int checks;
 static void check(boolean value,String message){checks++;if(!value)throw new AssertionError(message);}
 static void near(double actual,double expected,double tolerance,String message){check(Math.abs(actual-expected)<=tolerance,message+": "+actual+" vs "+expected);}
 static Checks unit(){Checks a=new Checks();a.frequencyKnob.value=Math.log(50)/Math.log(900);a.slopeSlider.value=4;a.signalInput.connected=true;a.initialize();return a;}
 void run(int n){for(int i=0;i<n;i++)process();}
 static double[] measure(int order,double center,double width,double tone){
   FilterBank bank=new FilterBank();bank.setOrder(order);
   // Broad bands at very low centers include a slow overdamped pole.
   int settle=Math.max(96000,(int)(12.0*SAMPLE_RATE/(Math.PI*width*Math.sin(Math.PI/(2*order)))));
   int count=48000;double sb=0,cb=0,sn=0,cn=0;
   for(int i=0;i<settle+count;i++){
     double p=2*Math.PI*tone*i/SAMPLE_RATE, s=Math.sin(p), c=Math.cos(p);
     bank.process(s,center,width);
     if(i>=settle){sb+=bank.bandpass*s;cb+=bank.bandpass*c;sn+=bank.bandreject*s;cn+=bank.bandreject*c;}
   }
   return new double[]{2*Math.hypot(sb,cb)/count,2*Math.hypot(sn,cn)/count};
 }
 static void response(int order,double fc,double bw,double hz){
   double g=Math.tan(Math.PI*fc/SAMPLE_RATE),t=Math.tan(Math.PI*hz/SAMPLE_RATE);
   double b=Math.tan(Math.PI*bw/SAMPLE_RATE)*(1+g*g);
   double detuning=(t*t-g*g)/(b*t),power=Math.pow(Math.abs(detuning),2*order);
   double eb=1/Math.sqrt(1+power),en=Math.sqrt(power/(1+power));
   double[] actual=measure(order,fc,bw,hz);
   near(actual[0],eb,.012,"measured BP "+order+"/"+fc+"/"+bw+"/"+hz);
   near(actual[1],en,.012,"measured BR "+order+"/"+fc+"/"+bw+"/"+hz);
   if(hz==fc){near(actual[0],1,.001,"unity center");check(actual[1]<.0002,"center notch below -74 dB: order="+order+", center="+fc+", width="+bw+", measured="+actual[1]);}
 }
 public static void main(String[] args){
   Checks a=unit();a.signalInput.value=2;a.process();near(a.mainOutput.value,0,0,"loads OFF main silent");near(a.courtesyOutput.value,2,0,"OFF passive C");
   a.gainKnob.value=15;a.run(3000);near(a.courtesyOutput.value,2,0,"OFF bypasses input gain");
   a.signalInput.value=40;a.process();check(a.courtesyOutput.value<=20,"OFF ceiling");
   a.powerSwitch.value=1;a.process();near(a.selectiveCore.powerMix,1.0/480,1e-14,"power first step");a.run(479);near(a.selectiveCore.powerMix,1,1e-12,"10 ms on");
   a.powerSwitch.value=0;a.run(480);near(a.mainOutput.value,0,0,"10 ms off");
   a=unit();a.powerSwitch.value=1;a.signalInput.value=1;a.run(1000);
   double frozen=a.selectiveCore.banks[a.selectiveCore.activeBank].sections[0].bpState1;
   int reads=a.frequencyKnob.reads+a.fmInput.reads+a.pitchInput.reads+a.gainKnob.reads+a.powerSwitch.reads+a.amplitudeKnob.reads+a.widthSlider.reads+a.slopeSlider.reads+a.fineKnob.reads;
   for(double v:new double[]{-100,-12,-1,0,1,12,100}){a.signalInput.value=v;a.bypass();near(a.mainOutput.value,v,0,"dry MAIN");near(a.courtesyOutput.value,v,0,"dry C");}
   near(a.selectiveCore.banks[a.selectiveCore.activeBank].sections[0].bpState1,frozen,0,"bypass freezes filter");
   check(reads==a.frequencyKnob.reads+a.fmInput.reads+a.pitchInput.reads+a.gainKnob.reads+a.powerSwitch.reads+a.amplitudeKnob.reads+a.widthSlider.reads+a.slopeSlider.reads+a.fineKnob.reads,"bypass no control/CV reads");
   a.updateIndicators();near(a.signalLed.value+a.overloadLed.value,0,0,"bypass LEDs dark");
   a.pitchInput.connected=a.fmInput.connected=true;a.pitchInput.value=1;a.fmInput.value=2;a.fineKnob.value=2;a.process();near(a.selectiveCore.effectiveFrequency,2202,1e-8,"V/Oct+fine+FM sum and bypass resume");
   a.pitchInput.value=30;a.process();near(a.selectiveCore.effectiveFrequency,18000,0,"upper clamp");
   a.pitchInput.value=-30;a.fmInput.value=-5;a.process();near(a.selectiveCore.effectiveFrequency,20,0,"lower clamp");
   a=unit();a.process();a.frequencyKnob.value=1;a.powerSwitch.value=1;a.process();check(a.selectiveCore.smoothFrequency>1000 && a.selectiveCore.smoothFrequency<18000,"manual smoothing");
   a.edit(a.frequencyKnob,1000,"");near(frequencyHz(a.frequencyKnob.value),1000,1e-10,"frequency tooltip inverse");
   a.edit(a.widthSlider,10,"");near(widthHz(a.widthSlider.value),10,1e-12,"width tooltip inverse");
   a.edit(a.amplitudeKnob,-12,"");near(a.amplitudeKnob.value,-.5,0,"negative AMP mapping");a.edit(a.amplitudeKnob,18,"");near(a.amplitudeKnob.value,.5,0,"positive AMP mapping");
   a=unit();Checks other=unit();a.powerSwitch.value=other.powerSwitch.value=1;other.amplitudeKnob.value=1;
   for(int i=0;i<48000;i++){a.signalInput.value=other.signalInput.value=Math.sin(2*Math.PI*1000*i/SAMPLE_RATE);a.process();other.process();near(a.courtesyOutput.value,other.courtesyOutput.value,0,"C independent of AMP");}
   for(int order:new int[]{1,4,12}) for(double fc:new double[]{20,1000,18000}) for(double bw:new double[]{2,100}){
     response(order,fc,bw,fc);
     double g=Math.tan(Math.PI*fc/SAMPLE_RATE),b=Math.tan(Math.PI*bw/SAMPLE_RATE)*(1+g*g);
     double low=2*g*g/(Math.sqrt(b*b+4*g*g)+b),hi=low+b;
     near((Math.atan(hi)-Math.atan(low))*SAMPLE_RATE/Math.PI,bw,1e-8,"exact digital bandwidth");
     if(fc==1000){response(order,fc,bw,Math.atan(low)*SAMPLE_RATE/Math.PI);response(order,fc,bw,Math.atan(hi)*SAMPLE_RATE/Math.PI);response(order,fc,bw,fc+3*bw);}
   }

   near(amplitudeDb(-1),-24,0,"AMP lower endpoint");near(amplitudeDb(0),0,0,"AMP noon");near(amplitudeDb(1),36,0,"AMP upper endpoint");
   a=unit();a.powerSwitch.value=1;a.widthSlider.value=1;a.gainKnob.value=24;a.amplitudeKnob.value=1;
   double energy=0;
   for(int i=0;i<96000;i++){a.signalInput.value=.001*Math.sin(2*Math.PI*1000*i/SAMPLE_RATE);a.process();if(i>=48000)energy+=a.mainOutput.value*a.mainOutput.value;}
   near(Math.sqrt(2*energy/48000),1,.0001,"actual 60 dB combined boost");
   a=unit();a.powerSwitch.value=1;a.widthSlider.value=1;a.gainKnob.value=-24;a.amplitudeKnob.value=-1;energy=0;
   for(int i=0;i<96000;i++){a.signalInput.value=Math.sin(2*Math.PI*1000*i/SAMPLE_RATE);a.process();if(i>=48000)energy+=a.mainOutput.value*a.mainOutput.value;}
   near(Math.sqrt(2*energy/48000),Math.pow(10,-48./20),1e-7,"actual -48 dB combined cut");
   near(SelectiveCore.ceiling(20,MAIN_KNEE_VOLTS,MAIN_CEILING_VOLTS),20,0,"MAIN clean to 20 V");
   near(SelectiveCore.ceiling(16,COURTESY_KNEE_VOLTS,COURTESY_CEILING_VOLTS),16,0,"C clean to 16 V");
   near(SelectiveCore.ceiling(1000,MAIN_KNEE_VOLTS,MAIN_CEILING_VOLTS),24,1e-12,"MAIN ceiling 24 V");
   near(SelectiveCore.ceiling(-1000,COURTESY_KNEE_VOLTS,COURTESY_CEILING_VOLTS),-20,1e-12,"C negative ceiling 20 V");
   System.out.println("Static response checks passed (center, width, skirts, notch).");
   for(int order=2;order<=11;order++) if(order!=4) response(order,1000,2,1000);
   a=unit();a.powerSwitch.value=1;a.pitchInput.connected=a.fmInput.connected=true;a.slopeSlider.value=12;
   double moderatePeak=0;
   for(int i=0;i<96000;i++){
     a.signalInput.value=5*Math.sin(.131*i);a.pitchInput.value=Math.sin(.031*i);a.fmInput.value=5*Math.sin(.17*i);
     a.process();moderatePeak=Math.max(moderatePeak,Math.abs(a.selectiveCore.banks[a.selectiveCore.activeBank].bandpass));
   }
   check(moderatePeak<100,"ordinary audio-rate FM has controlled internal levels");
   a.bypass();a.slopeSlider.value=3;a.process();check(a.selectiveCore.banks[a.selectiveCore.activeBank].order==3,"bypass resume adopts slope");
   a.notify(null,ModuleNotifications.Reset);check(a.resetPending,"GUI reset deferred to audio thread");a.process();check(!a.resetPending,"audio applies reset");
   System.out.println("Ordinary 1 V pitch / 5 V FM stress peak="+moderatePeak);
   a=unit();a.powerSwitch.value=1;a.pitchInput.connected=a.fmInput.connected=true;a.gainKnob.value=24;a.amplitudeKnob.value=1;
   double maxInternal=0;
   for(int i=0;i<240000;i++){
     a.signalInput.value=10*Math.sin(.081*i);a.pitchInput.value=3*Math.sin(.12*i);a.fmInput.value=100*Math.sin(.033*i);
     a.slopeSlider.value=1+(i/20000)%12;a.widthSlider.value=(i%40000)/39999.0;
     a.process();check(Double.isFinite(a.mainOutput.value)&&Math.abs(a.mainOutput.value)<=24,"FM MAIN bounded");check(Double.isFinite(a.courtesyOutput.value)&&Math.abs(a.courtesyOutput.value)<=20,"FM C bounded");
     double bp=a.selectiveCore.banks[a.selectiveCore.activeBank].bandpass;check(Double.isFinite(bp),"internal filter finite");maxInternal=Math.max(maxInternal,Math.abs(bp));
   }
   check(maxInternal<1e8,"extreme FM internal state bounded");
   System.out.println("Extreme audio-rate modulation passed; largest pre-ceiling BP="+maxInternal);
   a.selectiveCore.signalEnvelope=5;a.selectiveCore.overloadEnvelope=1;a.bypassed=false;a.updateIndicators();check(a.signalLed.value>0 && a.overloadLed.value==1,"meter sources");
   for(int order:new int[]{4,12}){
     FilterBank bank=new FilterBank();bank.setOrder(order);double sum=0;
     for(int i=0;i<100000;i++)bank.process(Math.sin(.13*i),1000,2);
     long start=System.nanoTime();for(int i=0;i<480000;i++){bank.process(Math.sin(.13*i),1000,2);sum+=bank.bandpass;}
     double staticMs=(System.nanoTime()-start)/1e6;
     start=System.nanoTime();for(int i=0;i<480000;i++){bank.process(Math.sin(.13*i),1000+400*Math.sin(.031*i),2);sum+=bank.bandpass;}
     double fmMs=(System.nanoTime()-start)/1e6;
     System.out.println("10 s DSP core, order "+order+": static="+staticMs+" ms, audio FM="+fmMs+" ms; checksum="+sum);
   }
   System.out.println("PASS "+checks+" checks");
 }
}
class MockModule {
 String GetTooltipText(Object x){return "";}
 void EditComponentValue(Checks.VoltageComponent c,double v,String t){c.SetValue(v);}
}
'''
sdk=Path(os.environ.get('PROGRAMDATA',r'C:\ProgramData'))/'Voltage/voltage.jar'
with tempfile.TemporaryDirectory(prefix='selective-service-') as td:
    build=Path(td)
    for label,text in [('export',source),('embedded',embedded)]:
        folder=build/label;folder.mkdir();(folder/'com/insectlabs/selectiveservice').mkdir(parents=True)
        path=folder/'selectiveService.java';path.write_text(text,encoding='utf-8')
        subprocess.run(['javac','--release','17','-encoding','UTF-8','-Xlint:all','-Werror','-cp',str(sdk),'-d',str(folder),str(path)],check=True)
    (build/'Checks.java').write_text(h,encoding='utf-8')
    subprocess.run(['javac','--release','17','-d',str(build),str(build/'Checks.java')],check=True)
    run=subprocess.run(['java','-cp',str(build),'Checks'],capture_output=True,text=True)
    print(run.stdout,run.stderr,end='')
    run.check_returncode()

    baseline=base/'behavior-baseline.java'
    if not baseline.exists(): baseline=base.parent/'references/review-baseline-v1.0.0rc.java'
    if not baseline.exists(): baseline=base.parent.parent/'references/review-baseline-v1.0.0rc.java'
    before=regions(baseline.read_text(encoding='utf-8-sig'))['code-and-variables']
    after=r['code-and-variables']
    wrapper='static class VoltageAudioJack {boolean IsConnected(){return true;}double GetValue(){return 0;}void SetValue(double x){}} VoltageAudioJack signalLed=new VoltageAudioJack(),overloadLed=new VoltageAudioJack();'
    parity='public class Parity { static class Before {'+wrapper+before+'} static class After {'+wrapper+after+'}'
    parity+=r"""
      public static void main(String[] args) {
        Before.SelectiveCore a=new Before.SelectiveCore(48000);After.SelectiveCore b=new After.SelectiveCore(48000);
        for(int i=0;i<120000;i++) {
          double input=7*Math.sin(.09*i),frequency=(i%7000)/6999.,fine=2*Math.sin(.0001*i),width=(i%3000)/2999.;
          int order=1+(i/3000)%12;double gain=24*Math.sin(.0003*i),amp=-24+60*(i%10000)/9999.;
          double pitch=Math.sin(.001*i),fm=5*Math.sin(.2*i);boolean on=(i/10000)%2==0,snap=i%7000==0;
          a.process(input,frequency,fine,width,order,gain,amp,pitch,fm,on,snap);
          b.process(input,frequency,fine,width,order,gain,amp,pitch,fm,on,snap);
          if(Double.doubleToLongBits(a.main)!=Double.doubleToLongBits(b.main)
            ||Double.doubleToLongBits(a.courtesy)!=Double.doubleToLongBits(b.courtesy)
            ||Double.doubleToLongBits(a.signalEnvelope)!=Double.doubleToLongBits(b.signalEnvelope)
            ||Double.doubleToLongBits(a.overloadEnvelope)!=Double.doubleToLongBits(b.overloadEnvelope))
              throw new AssertionError("Cleanup parity at "+i);
        }
        System.out.println("PASS: cleanup is bit-identical for 120000 samples across both outputs and indicators.");
      }
    }"""
    (build/'Parity.java').write_text(parity,encoding='utf-8')
    subprocess.run(['javac','--release','17','-d',str(build),str(build/'Parity.java')],check=True)
    subprocess.run(['java','-cp',str(build),'Parity'],check=True)

print('PASS: both source forms compile against SDK; metadata and embedded/exported source match.')
