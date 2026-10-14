"""Validate this release using actual source regions; needs JDK and Voltage SDK."""
from pathlib import Path
import argparse
import json
import hashlib
import re
import subprocess
import sys
import tempfile

p = argparse.ArgumentParser()
p.add_argument('--jdk', default=r'C:\Program Files\Java\jdk-27\bin')
p.add_argument('--sdk', default=r'C:\ProgramData\Voltage\voltage.jar')
a = p.parse_args()
release = Path(__file__).resolve().parents[1]
repo = next(x for x in release.parents if (x / 'tools/check_repository.py').exists())
sys.path.insert(0, str(repo / 'voltage-modular/colorbox/tools'))
from value_tree import Reader, encode, props
from support import normalized, regions

source = (release / 'tsl.java').read_text(encoding='utf-8-sig')
raw = (release / 'tsl.vmod').read_bytes()
reader = Reader(raw)
tree = reader.tree()
assert reader.p == len(raw) and encode(tree) == raw
assert props(tree)['class name'] == 'tsl'
assert props(tree)['notes'] == 'v1.0.0'
embedded = props(next(n for n in tree[2] if n[0] == 'module code'))['MODULE.SOURCE']
assert normalized(source) == normalized(embedded)
for name, digest in json.loads((release / 'SHA256.json').read_text()).items():
    assert hashlib.sha256((release / name).read_bytes()).hexdigest() == digest, name
parts = regions(source)
scratch = repo / 'tmp'
scratch.mkdir(exist_ok=True)
with tempfile.TemporaryDirectory(prefix='tsl-validation-', dir=scratch) as tmp:
    tmp = Path(tmp)
    def compile_java(path, out, sdk=True):
        out.mkdir(exist_ok=True)
        cmd = [str(Path(a.jdk) / 'javac.exe'), '--release', '17', '-Xlint:all', '-Werror', '-encoding', 'UTF-8', '-d', str(out)]
        if sdk:
            cmd += ['-classpath', a.sdk]
        subprocess.run(cmd + [str(path)], check=True)
    for label, text in [('export', source), ('embedded', embedded)]:
        folder = tmp / label
        folder.mkdir()
        path = folder / 'tsl.java'
        path.write_text(text, encoding='utf-8')
        compile_java(path, folder / 'classes')
    harness = '''import java.util.concurrent.atomic.AtomicBoolean;
public class Checks {
static class VoltageAudioJack { double v; boolean connected=true; int reads; double GetValue(){reads++;return v;} void SetValue(double x){v=x;} boolean IsConnected(){return connected;} }
static class Knob { double v; int reads; double GetValue(){reads++;return v;} }
VoltageAudioJack inputV1=new VoltageAudioJack(),inputV2=new VoltageAudioJack(),inputV3=new VoltageAudioJack(),inputVC1=new VoltageAudioJack(),inputVC2=new VoltageAudioJack(),inputVC3=new VoltageAudioJack(),outputV1=new VoltageAudioJack(),outputV2=new VoltageAudioJack(),outputV3=new VoltageAudioJack();
Knob knobV1=new Knob(),knobV2=new Knob(),knobV3=new Knob();
'''+parts['code-and-variables']+'\nvoid process(){'+parts['ProcessSample']+'}\nvoid bypass(){'+parts['ProcessBypassedSample']+'''}
static void near(double a,double b){if(Math.abs(a-b)>1e-9)throw new AssertionError(a+" != "+b);}
public static void main(String[] args){
near(baseRateForKnob(0),.05);near(baseRateForKnob(1),1000);
for(double k:new double[]{0,.1,.5,.9,1})near(knobForRate(baseRateForKnob(k)),k);
Checks m=new Checks();m.knobV1.v=1;m.knobV2.v=1;m.knobV3.v=1;
m.inputV1.v=5;m.inputV2.v=-5;m.inputV3.v=3;m.inputVC3.connected=false;
m.process();near(m.outputV1.v,1000.0/48000);near(m.outputV2.v,-1000.0/48000);
for(int i=0;i<300;i++)m.process();near(m.outputV1.v,5);near(m.outputV2.v,-5);near(m.outputV3.v,3);
m.inputVC3.connected=true;m.inputVC3.v=3;m.inputV3.v=-3;m.process();near(m.outputV3.v,3);
m.inputVC3.v=3.01;m.process();near(m.outputV3.v,3-1000.0/48000);
m.knobV1.v=0;m.inputVC1.v=5;m.currentVolt1=0;m.process();near(m.outputV1.v,1000.0/48000);
m.knobV1.v=1;m.inputVC1.v=-5;m.currentVolt1=0;m.process();near(m.outputV1.v,.05/48000);
double old=m.currentVolt1;int reads=m.knobV1.reads+m.inputVC1.reads;
m.inputV1.v=12;m.inputV2.v=-8;m.inputV3.v=7;m.bypass();near(m.outputV1.v,12);near(m.outputV2.v,-8);near(m.outputV3.v,7);near(m.currentVolt1,old);
if(reads!=m.knobV1.reads+m.inputVC1.reads)throw new AssertionError("bypass control reads");
m.inputVC3.v=0;m.process();near(m.outputV1.v,12);near(m.outputV3.v,7);
m.inputV1.v=Double.NaN;m.process();if(!Double.isFinite(m.outputV1.v))throw new AssertionError("active NaN");
m.inputV1.connected=false;near(readInput(m.inputV1),0);
System.out.println("PASS: actual callback rate, polarity, settling, CV, T&H 3 V boundary, bypass/frozen histories/resume, input guard checks");
}}
'''
    path = tmp / 'Checks.java'
    path.write_text(harness, encoding='utf-8')
    compile_java(path, tmp / 'checks', False)
    subprocess.run([str(Path(a.jdk) / 'java.exe'), '-cp', str(tmp / 'checks'), 'Checks'], check=True)
print('PASS: both source forms compile with warnings as errors; pair, round-trip, identity and hashes agree.')
