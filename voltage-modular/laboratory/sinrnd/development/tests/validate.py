"""Compile and exercise the candidate's actual callback regions with test controls."""
from pathlib import Path
import sys, subprocess, os
repo=next((p for p in Path(__file__).resolve().parents if (p/'voltage-modular/colorbox/tools').is_dir()),Path(r'C:\Users\wgparham\Dropbox\git\insectlabs'))
sys.path.insert(0,str(repo/'voltage-modular/colorbox/tools'))
from support import regions, normalized
from value_tree import Reader, props, encode
base=Path(__file__).resolve().parent
module=base if (base/'sinrnd.java').is_file() else base.parent
source=(module/'nineteenfortyseven.java').read_text(encoding='utf-8-sig'); r=regions(source)
assert 'frequencyKnob.SetRange( 0.0, 1.0, 0.281152240467025, false, 0 );' in source
tree=Reader((module/'nineteenfortyseven.vmod').read_bytes()).tree()
assert props(tree)['notes']=='v2.0.0rc'
assert props(tree)['class name']=='nineteenfortyseven'
embedded=props(next(n for n in tree[2] if n[0]=='module code'))['MODULE.SOURCE']
assert normalized(source)==normalized(embedded)
controls=next(n for n in tree[2] if n[0]=='controls')
assert len(controls[2])==len({props(n)['UUID'] for n in controls[2]})
frequency_control = next(n for n in controls[2] if props(n)['variable name'] == 'frequencyKnob')
assert props(frequency_control)['defaultValueString'] == '0.281152240467025'
assert props(frequency_control)['defaultValue'] == 0.281152240467025
mod_level_control = next(n for n in controls[2] if props(n)['variable name'] == 'externalFmLevelKnob')
assert (props(mod_level_control)['minValue'], props(mod_level_control)['maxValue']) == (-2.0, 2.0)
assert encode(tree)==(module/'nineteenfortyseven.vmod').read_bytes()
for name in ['ProcessSample','ProcessBypassedSample','Initialize']:
    assert len(r[name].strip())>20,name
destroy_start = source.index('public void Destroy()\n    {')
destroy_end = source.index('//-------------------------------------------------------------------------------', destroy_start)
destroy_body = source[destroy_start:destroy_end]
assert destroy_body.count('super.Destroy();') == 1
assert destroy_body.index('StopGuiUpdateTimer();') < destroy_body.index('super.Destroy();')
h='''public class SinrndChecks {
 static class Control {
   double value; boolean connected; int reads;
   double GetValue() { reads++; return value; }
   void SetValue(double x) { value=x; }
   boolean IsConnected() { return connected; }
 }
'''
names=['powerSwitch','frequencyKnob','fineFrequencyKnob','amplitudeKnob','amplitudeRangeKnob','externalFmLevelKnob','randomModulationKnob','modeKnob','filterKnob','mainOutput','externalFmInput']
h+='\n'.join(' Control '+n+' = new Control();' for n in names)
h+=r['code-and-variables']+'\n void process() {\n'+r['ProcessSample']+'\n}\n void bypass() {\n'+r['ProcessBypassedSample']+'\n}\n'
h+='''
 static int checks;
 static void check(boolean ok, String message) { checks++; if(!ok) throw new AssertionError(message); }
 static SinrndChecks unit(int mode) {
   SinrndChecks u=new SinrndChecks(); u.powerSwitch.value=1; u.modeKnob.value=mode;
   u.amplitudeRangeKnob.value=3; u.amplitudeKnob.value=1; u.filterKnob.value=.5;
   u.frequencyKnob.value=Math.log(440.0/50)/Math.log(360); u.randomModulationKnob.value=1;
   u.externalFmLevelKnob.value=1; return u;
 }
 public static void main(String[] args) {
   SinrndChecks a=unit(1),b=unit(4);
   a.externalFmInput.connected=b.externalFmInput.connected=true;
   for(int i=0;i<96000;i++) {
     double cv=4*Math.sin(i*.019);
     a.externalFmInput.value=b.externalFmInput.value=cv;
     a.process();b.process();
     check(a.mainOutput.value==b.mainOutput.value,"external FM matches in SIN and MOD");
   }
   a=unit(1);b=unit(4); b.externalFmInput.connected=true;
   for(int i=0;i<4800;i++){a.process();b.process();check(a.mainOutput.value==b.mainOutput.value,"silent cable suppresses noise FM");}
   b=unit(4);a=unit(1);b.externalFmInput.connected=true;b.externalFmInput.value=5;b.externalFmLevelKnob.value=0;
   for(int i=0;i<4800;i++){a.process();b.process();check(a.mainOutput.value==b.mainOutput.value,"zero attenuverter retains priority");}
   b.externalFmInput.connected=false;
   boolean different=false;for(int i=0;i<1000;i++){a.process();b.process();different|=a.mainOutput.value!=b.mainOutput.value;}
   check(different,"unpatch restores internal FM");
   a=unit(3);b=unit(3);b.externalFmInput.connected=true;b.externalFmInput.value=100;
   for(int i=0;i<48000;i++){a.process();b.process();check(a.mainOutput.value==b.mainOutput.value,"RD2 independent of external FM");}
   a=unit(3);b=unit(3);b.filterKnob.value=1; boolean filteredDifferent=false;
   for(int i=0;i<48000;i++){a.process();b.process();filteredDifferent|=a.mainOutput.value!=b.mainOutput.value;}
   check(filteredDifferent,"RD2 follows filter control");
   a=unit(5);b=unit(5);b.externalFmInput.connected=true;b.externalFmInput.value=5;b.randomModulationKnob.value=0;
   boolean fltAudioDifferent=false;
   for(int i=0;i<48000;i++){a.process();b.process();fltAudioDifferent|=a.mainOutput.value!=b.mainOutput.value;}
   check(fltAudioDifferent,"FLT input silences oscillator path");
   check(Math.abs(b.mainOutput.value+1.0)<.00001,"FLT input follows normal one-volt range scaling");
   b=unit(5);b.externalFmInput.connected=true;b.externalFmInput.value=5;b.externalFmLevelKnob.value=2;b.randomModulationKnob.value=0;
   for(int i=0;i<48000;i++)b.process();
   check(Math.abs(b.mainOutput.value+2.0)<.00001,"plus-two MOD Level doubles FLT input");
   a=unit(5);b=unit(5);a.externalFmInput.connected=b.externalFmInput.connected=true;
   a.externalFmInput.value=b.externalFmInput.value=0;a.randomModulationKnob.value=0;b.randomModulationKnob.value=1;
   boolean fltNoiseBlend=false;
   for(int i=0;i<48000;i++){a.process();b.process();fltNoiseBlend|=a.mainOutput.value!=b.mainOutput.value;}
   check(Math.abs(a.mainOutput.value)<.00001,"silent FLT input with zero RND MOD is silent");
   check(fltNoiseBlend,"RND MOD blends noise before FLT input filtering");
   a=unit(5);b=unit(5);a.externalFmInput.connected=b.externalFmInput.connected=true;
   a.externalFmInput.value=b.externalFmInput.value=4*Math.sin(.04);a.randomModulationKnob.value=b.randomModulationKnob.value=0;b.filterKnob.value=1;
   boolean inputFilterDifferent=false;
   for(int i=0;i<48000;i++){a.process();b.process();inputFilterDifferent|=a.mainOutput.value!=b.mainOutput.value;}
   check(inputFilterDifferent,"FILTER shapes FLT input audio");
   a=unit(2);b=unit(2);b.externalFmInput.connected=true;b.externalFmInput.value=100;
   double sum=0,squares=0;
   for(int i=0;i<96000;i++){a.process();b.process();check(a.mainOutput.value==b.mainOutput.value,"RND independent of external FM");if(i>=48000){sum+=a.mainOutput.value;squares+=a.mainOutput.value*a.mainOutput.value;}}
   check(Math.abs(sum/48000)<.015,"white noise mean");check(Math.abs(Math.sqrt(squares/48000)-Math.sqrt(1.0/3))<.015,"white noise RMS");
   check(a.externalFmInput.reads==0,"unpatched jack never read");
   a=unit(1);for(int i=0;i<48000;i++)a.process();
   double phase=a.phase,env=a.powerEnvelope;int rng=a.noiseState,reads=a.powerSwitch.reads;
   for(int i=0;i<1000;i++)a.bypass();
   check(a.mainOutput.value==0 && a.phase==phase && a.noiseState==rng && a.powerEnvelope==env,"bypass silent and frozen");
   check(a.powerSwitch.reads==reads,"bypass no control reads");
   a.frequencyKnob.value=1;a.process();check(a.frequency==18000,"resume adopts controls");
   a.powerSwitch.value=0;for(int i=0;i<1000;i++)a.process();phase=a.phase;rng=a.noiseState;
   for(int i=0;i<1000;i++)a.process();check(a.mainOutput.value==0 && phase==a.phase && rng==a.noiseState,"power off freezes histories");
   a=unit(1);double max=0; for(int i=0;i<48000;i++){a.process();if(i>4800)max=Math.max(max,Math.abs(a.mainOutput.value));}
   check(Math.abs(max-1)<1e-5,"ordinary sine unity peak");
   a.amplitudeRangeKnob.value=4;for(int i=0;i<48000;i++)a.process();
   max=0;for(int i=0;i<48000;i++){a.process();max=Math.max(max,Math.abs(a.mainOutput.value));}
   check(max>8 && max<9.2,"high range gentle compression");
   check(a.lampDisplay>.75,"overload lamp reaches a useful level");
   for(int mode=1;mode<=5;mode++)for(int range=1;range<=4;range++)for(int edge=0;edge<=1;edge++){
     a=unit(mode);a.amplitudeRangeKnob.value=range;a.frequencyKnob.value=edge;a.filterKnob.value=edge;
     a.externalFmInput.connected=true;a.externalFmInput.value=edge==0?Double.NaN:Double.MAX_VALUE;
     for(int i=0;i<2000;i++){a.process();check(Double.isFinite(a.mainOutput.value) && Math.abs(a.mainOutput.value)<=10.1,"finite extremes");}
   }
   a=unit(1);for(int i=0;i<1000;i++)a.process();double before=a.frequency;
   a.frequencyKnob.value=1;a.process();check(a.frequency>before && a.frequency<18000,"frequency smoothing");
   a.amplitudeKnob.value=0;double level=a.amplitude;a.process();check(a.amplitude>0 && a.amplitude<level,"amplitude smoothing");
   a=unit(1);a.fineFrequencyKnob.value=1;a.process();check(Math.abs(a.targetFrequency-440*Math.pow(2,1.0/6.0))<.00001,"fine tuning reaches plus 200 cents");
   a=unit(1);a.frequencyKnob.value=0.281152240467025;a.process();check(Math.abs(a.targetFrequency-261.6255653005986)<.00001,"frequency initializes at C4");
   System.out.println(checks+" callback/DSP assertions passed.");
 }
}
'''
build=Path(r'C:\InsectLabs-Build\sinrnd\checks');build.mkdir(parents=True,exist_ok=True)
sdk=Path(os.environ.get('PROGRAMDATA',r'C:\ProgramData'))/'Voltage/voltage.jar'
embedded_path=build/'embedded/nineteenfortyseven.java';embedded_path.parent.mkdir(exist_ok=True);embedded_path.write_text(embedded,encoding='utf-8')
for label,src in [('export',module/'nineteenfortyseven.java'),('embedded',embedded_path)]:
    subprocess.run(['javac','--release','17','-encoding','UTF-8','-Xlint:all','-Werror','-cp',str(sdk),'-d',str(build/label),str(src)],check=True)
java=build/'SinrndChecks.java';java.write_text(h,encoding='utf-8')
subprocess.run(['javac','--release','17','-encoding','UTF-8','-Xlint:all','-Werror','-d',str(build),str(java)],check=True)
subprocess.run(['java','-cp',str(build),'SinrndChecks'],check=True)
