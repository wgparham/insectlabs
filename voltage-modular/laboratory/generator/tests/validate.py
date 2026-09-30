"""Validate Generator's release pair and run its actual DSP in a native-free harness."""
from pathlib import Path
import argparse, hashlib, json, re, subprocess, sys, tempfile

parser = argparse.ArgumentParser()
parser.add_argument('--repo', type=Path, required=True)
parser.add_argument('--folder', type=Path, required=True)
args = parser.parse_args()
sys.path.insert(0, str(args.repo / 'voltage-modular/colorbox/tools'))
from value_tree import Reader, props, encode
from support import tokens

folder = args.folder
source = (folder / 'generator.java').read_text(encoding='utf-8-sig')
raw = (folder / 'generator.vmod').read_bytes()
tree = Reader(raw).tree()
assert encode(tree) == raw
code = next(n for n in tree[2] if n[0] == 'module code')
embedded = props(code)['MODULE.SOURCE']
def comparable(s):
    declarations = re.compile(r'^\s*private Voltage\w+ \w+;', re.M)
    return tokens(declarations.sub('', s)), sorted(x.strip() for x in declarations.findall(s))
assert comparable(source) == comparable(embedded)
for name, digest in json.loads((folder / 'SHA256.json').read_text()).items():
    assert hashlib.sha256((folder / name).read_bytes()).hexdigest() == digest
for record in code[2][0][2]:
    assert 0 <= props(record)['line number'] < len(embedded.splitlines())

def region(name):
    body = source.split('//[user-' + name + ']', 1)[1].split('//[/user-' + name + ']', 1)[0]
    return body.split('\n', 1)[1]

body = region('code-and-variables').replace('VoltageAudioJack', 'Control')
controls = re.findall(r'private Voltage\w+ (\w+);', source)
harness = '''public class GeneratorReleaseTest {
static class Control {
 double value; boolean connected; double GetValue() { return value; }
 void SetValue(double v) { value=v; } boolean IsConnected() { return connected; }
}
''' + '\n'.join('Control '+n+'=new Control();' for n in controls) + body
harness += '\nvoid process() {\n' + region('ProcessSample') + '\n}\n'
harness += '\nvoid bypass() {\n' + region('ProcessBypassedSample') + '\n}\n'
harness += '''
static int checks;
static void near(double a,double b,double tolerance) {
 checks++; if(!Double.isFinite(a) || Math.abs(a-b)>tolerance) throw new AssertionError(a+" != "+b);
}
static GeneratorReleaseTest instrument() {
 GeneratorReleaseTest g=new GeneratorReleaseTest(); g.resetGenerator();
 g.switch1.value=1;g.switch2.value=1;g.knob2.value=3;g.knob4.value=1;g.knob6.value=1;
 return g;
}
public static void main(String[] args) {
 GeneratorReleaseTest g=instrument();
 for(int band=1;band<=4;band++) for(int position=-1;position<=1;position++) {
  g.knob2.value=band;g.knob3.value=position;
  near(g.mainFrequencyHz(),Math.pow(10,band-1+position),1e-9);
 }
 g.knob7.value=0;near(g.internalModulationRateHz(),.05,1e-12);
 g.knob7.value=1;near(g.internalModulationRateHz(),50,1e-12);
 near(internalDepthScale(2),.25,0);near(internalDepthScale(3),1,0);
 g=instrument();g.switch2.value=2;
 for(int i=0;i<48000;i++) {
  g.process();near(g.outputJack1.value,5*Math.sin(2*Math.PI*i/48.0),1e-9);near(g.outputJack2.value,0,0);
 }
 g.switch2.value=1;g.process();near(g.outputJack1.value,0,0);near(g.outputJack2.value,0,0);
 g.switch2.value=0;
 for(int i=0;i<100;i++) {
  double phase=g.mainPhaseReference;g.process();near(g.outputJack2.value,2.5*Math.sin(2*Math.PI*phase),1e-10);near(g.outputJack1.value,0,0);
 }
 g=instrument();g.knob1.value=1;g.process();near(g.smoothedAmplitude,10*AMPLITUDE_SMOOTH,1e-12);
 g.knob3.value=1;double before=g.smoothedFrequency;g.process();
 near(g.smoothedFrequency,before+FREQUENCY_SMOOTH*(1000-before),1e-12);
 for(int i=0;i<6000;i++) g.process();near(g.smoothedAmplitude,10,.0001);near(g.smoothedFrequency,1000,.01);
 double phase=g.mainPhase,modPhase=g.internalModulationPhase,refPhase=g.mainPhaseReference,amp=g.smoothedAmplitude;
 for(int i=0;i<100;i++) g.bypass();
 near(g.mainPhase,phase,0);near(g.internalModulationPhase,modPhase,0);near(g.mainPhaseReference,refPhase,0);near(g.smoothedAmplitude,amp,0);near(g.outputJack2.value,0,0);
 g.switch1.value=0;g.process();near(g.mainPhase,phase,0);near(g.outputJack1.value,0,0);
 double last=0;
 for(int i=0;i<=100000;i++) {
  double x=i/10000.0,y=outputStage(x);
  if(y<last-1e-12 || y>x+1e-12) throw new AssertionError("nonmonotonic compression");
  near(outputStage(-x),-y,0);if(x<=5) near(y,x,0);
  if(x>=7) near(y,5+(x-5)/(1+.17*(x-5)),1e-12);last=y;
 }
 GeneratorReleaseTest q=instrument(),full=instrument();q.knob6.value=2;full.knob6.value=3;
 q.process();full.process();
 near((q.mainPhase*48000-100)*4,full.mainPhase*48000-100,1e-10);
 g=instrument();g.knob6.value=4;g.knob7.value=1;g.inputJack1.connected=true;g.inputJack1.value=5;
 g.process();near(g.mainPhase*48000,118,1e-10);
 g=instrument();double low=1,high=0;
 for(int i=0;i<96000;i++) {
  double m=g.averagedMeterLevel(5*Math.sin(2*Math.PI*i/48.0));
  if(i>90000) {low=Math.min(low,m);high=Math.max(high,m);}
 }
 near((low+high)*.5,.5,.01);if(high-low>.005)throw new AssertionError("meter ripple");
 System.out.println("PASS: "+checks+" DSP checks (routing, reference, FM, smoothing, compression, meter, power and bypass)");
}
}
'''
with tempfile.TemporaryDirectory(prefix='generator-release-') as temp:
    work = Path(temp)
    for name, text in [('export', source), ('embedded', embedded)]:
        loc=work/name;loc.mkdir();(loc/'generator.java').write_text(text,encoding='utf-8')
        subprocess.run(['javac','--release','17','-encoding','UTF-8','-Xlint:all','-Werror','-cp','C:/ProgramData/Voltage/voltage.jar','-d',str(loc),str(loc/'generator.java')],check=True)
    (work/'GeneratorReleaseTest.java').write_text(harness,encoding='utf-8')
    subprocess.run(['javac','--release','17','-d',str(work),str(work/'GeneratorReleaseTest.java')],check=True)
    subprocess.run(['java','-cp',str(work),'GeneratorReleaseTest'],check=True)
print('PASS: release hashes, Designer anchors, source agreement and both SDK compilations')
