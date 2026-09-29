"""Validate the Designer pair, compile with the real SDK, and simulate its callbacks."""
from pathlib import Path
import argparse
import hashlib
import json
import re
import subprocess
import sys

root = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(root.parent / 'colorbox' / 'tools'))
from value_tree import Reader, encode, props, setprop
from support import regions, normalized

parser = argparse.ArgumentParser()
parser.add_argument('--sdk', type=Path, required=True)
args = parser.parse_args()
module = root / 'signal-processor'
index = json.loads((root / 'CANONICAL.json').read_text())
folder = root / index['signal-processor']['path']
build = root / 'build' / 'signal-processor-project'
build.mkdir(parents=True, exist_ok=True)
raw = (folder / 'signal_proc.vmod').read_bytes()
reader = Reader(raw)
tree = reader.tree()
assert reader.p == len(raw) and encode(tree) == raw
control_names = {
    'leftGain', 'leftOffset', 'leftExternalLevel', 'rightGain', 'leftMode',
    'rightOffset', 'rightExternalLevel', 'rightMode', 'leftInput',
    'leftExternalInput', 'rightInput', 'rightExternalInput', 'leftOutput',
    'rightOutput', 'channelDivider', 'manufacturerLabel', 'moduleTitleLabel',
    'leftGainLabel', 'rightGainLabel', 'leftOffsetLabel', 'rightOffsetLabel',
    'leftInputLabel', 'rightInputLabel', 'leftOutputLabel', 'rightOutputLabel',
    'leftExternalLevelLabel', 'rightExternalLevelLabel',
    'leftExternalInputLabel', 'rightExternalInputLabel', 'leftModeLabel',
    'rightModeLabel',
}
controls = next(node for node in tree[2] if node[0] == 'controls')
uuids = set()
for control in controls[2]:
    values = props(control)
    current = values['variable name']
    assert current in control_names
    assert values['CEditableControl.name'] == current
    assert values['display name'] == current
    assert values['UUID'] not in uuids
    uuids.add(values['UUID'])
assert len(controls[2]) == len(control_names)
code = next(n for n in tree[2] if n[0] == 'module code')
embedded = props(code)['MODULE.SOURCE']
exported = (folder / 'signal_proc.java').read_text()
assert normalized(embedded) == normalized(exported)
source_lines = embedded.splitlines()
readonly_lines = {props(info)['line number'] for info in code[2][0][2]
                  if props(info).get('read only') == 1}
dsp_start = source_lines.index('// Add your own variables and functions here') + 1
outer_close = max(i for i, line in enumerate(source_lines) if line.strip() == '}')
assert not readonly_lines.intersection(range(dsp_start, outer_close)), 'Generated boundary inside DSP user code'
assert outer_close in readonly_lines, 'Outer module brace must be generated'
section_lines = {}
for info in code[2][0][2]:
    for child in info[2]:
        section_lines.setdefault(props(child).get('ID'), []).append(props(info)['line number'])
assert section_lines.get('class closing bracket') == [outer_close]
assert section_lines.get('user code and variables') == [dsp_start - 1]
assert len(section_lines) > 50, 'Missing Designer section/control markers'

print('PASS complete DSP user region and final generated class boundary', flush=True)
for anchor in code[2][0][2]:
    assert 0 <= props(anchor)['line number'] < len(source_lines)
for name, digest in json.loads((folder / 'SHA256.json').read_text()).items():
    assert hashlib.sha256((folder / name).read_bytes()).hexdigest() == digest
print('PASS project/source agreement, hashes, editor anchors, and descriptive control metadata', flush=True)
for kind, text in [('embedded', embedded), ('exported', exported)]:
    dest = build / kind
    dest.mkdir(exist_ok=True)
    src = dest / 'SIGPROC.java'
    src.write_text(text)
    subprocess.run(['javac', '--release', '17', '-Xlint:all', '-Werror', '-cp', str(args.sdk), '-d', str(dest), str(src)], check=True)
print('PASS real SDK compilation of both source forms', flush=True)

parts = regions(exported)
controls = re.findall(r'^private Voltage(Knob|AudioJack) (\w+);', exported, re.M)
head = '''public final class ProjectTest {
static class VoltageComponent { double value; boolean connected; int reads;
 double GetValue(){reads++;return value;} void SetValue(double x){value=x;} boolean IsConnected(){return connected;} }
static class VoltageKnob extends VoltageComponent {}
static class VoltageAudioJack extends VoltageComponent {}
'''
fields = '\n'.join('Voltage'+kind+' '+name+' = new Voltage'+kind+'();' for kind, name in controls)
defaults = []
for kind, name in controls:
    if kind == 'Knob':
        match = re.search(re.escape(name) + r'\.SetRange\(\s*([^,]+),\s*([^,]+),\s*([^,]+),', exported)
        defaults.append(name + '.value=' + match[3] + ';')
body = fields + '\n' + parts['code-and-variables']
for method in ['Initialize', 'ProcessSample', 'ProcessBypassedSample']:
    body += '\nvoid ' + method + '(){\n' + parts[method] + '\n}\n'
tooltip_body = re.sub(r'super\.GetTooltipText\(\s*component\s*\)', '"fallback"', parts['GetTooltipText'])
body += 'String tooltip(VoltageComponent component){' + tooltip_body + '}\n'
body += 'void defaults(){' + ''.join(defaults) + 'Initialize();}\n'
head += 'static class Module {\n' + body + '}\n'
tests = '''
static long checks;
static void eq(double expected,double actual){checks++;if(!Double.isFinite(actual)||Math.abs(expected-actual)>1e-9)throw new AssertionError(expected+" != "+actual);}
static void check(boolean condition,String message){checks++;if(!condition)throw new AssertionError(message);}
public static void main(String[] args){
 Module m=new Module();m.defaults();
 check(m.tooltip(m.leftMode).contains("mode: PROC"),"left default");
 check(m.tooltip(m.rightMode).contains("mode: PROC"),"right default");
 m.leftInput.connected=m.rightInput.connected=true;
 m.leftInput.value=3;m.rightInput.value=-4;
 for(int i=0;i<5000;i++)m.ProcessSample();
 check(Double.isFinite(m.leftOutput.value),"finite left processing");
 check(Double.isFinite(m.rightOutput.value),"finite right processing");
 check(Math.abs(m.leftOutput.value-m.rightOutput.value)>1e-6,"independent stages");
 // Bypass must read only the two primary inputs and copy them exactly.
 VoltageComponent[] inactive={m.leftGain,m.leftOffset,m.leftExternalLevel,m.rightGain,m.leftMode,m.rightOffset,m.rightExternalLevel,m.rightMode,m.leftExternalInput,m.rightExternalInput};
 int before=0;for(VoltageComponent x:inactive){before+=x.reads;x.value=Double.NaN;}
 for(int i=0;i<50;i++){m.ProcessBypassedSample();eq(3,m.leftOutput.value);eq(-4,m.rightOutput.value);}
 int after=0;for(VoltageComponent x:inactive)after+=x.reads;
 check(before==after,"bypass control/CV reads");
 m=new Module();m.defaults();m.ProcessBypassedSample();eq(0,m.leftOutput.value);eq(0,m.rightOutput.value);
 m.ProcessSample();check(Double.isFinite(m.leftOutput.value)&&Double.isFinite(m.rightOutput.value),"unpatched processing");
'''
for _, name in controls:
    tests += 'check(!m.tooltip(m.'+name+').equals("fallback"),"tooltip '+name+'");\n'
tests += 'System.out.println("PASS integrated callbacks: "+checks+" checks (mapping, independence, CV, bypass, tooltips)");}}\n'
test = build / 'ProjectTest.java'
test.write_text(head + tests)
subprocess.run(['javac', '--release', '17', '-Xlint:all', '-Werror', '-d', str(build), str(test)], check=True)
subprocess.run(['java', '-cp', str(build), 'ProjectTest'], check=True)
