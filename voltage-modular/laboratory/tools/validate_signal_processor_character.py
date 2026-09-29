"""Extract the integrated core, compile it independently, and run character tests."""
from pathlib import Path
import hashlib
import json
import re
import subprocess
import sys

root = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(root.parent / 'colorbox/tools'))
from value_tree import Reader, encode, props
from support import normalized, regions

module = root / 'signal-processor'
index = json.loads((root / 'CANONICAL.json').read_text())
folder = root / index['signal-processor']['path']
java_path = folder / 'signal_proc.java'
vmod_path = folder / 'signal_proc.vmod'
source = java_path.read_text(encoding='utf-8-sig')
raw = vmod_path.read_bytes()
reader = Reader(raw)
tree = reader.tree()
assert reader.p == len(raw) and encode(tree) == raw
embedded = props(next(node for node in tree[2] if node[0] == 'module code'))['MODULE.SOURCE']
assert normalized(embedded) == normalized(source)
hashes = json.loads((folder / 'SHA256.json').read_text())
for name, digest in hashes.items():
    assert hashlib.sha256((folder / name).read_bytes()).hexdigest() == digest
assert 'leftMode.GetValue() >= 0.5' in source
assert 'rightMode.GetValue() >= 0.5' in source
assert source.count('SetRange( -3.0, 3.0, 1.0') == 2
assert 'effectiveGain = clamp(requestedGain, -3, 3);' in source
assert 'signalProcessor.processBypassed(readInput(leftInput), readInput(rightInput));' in source
tooltips = regions(source)['GetTooltipText']
for control in ('leftGain', 'leftOffset', 'leftExternalLevel', 'leftMode',
                'rightGain', 'rightOffset', 'rightExternalLevel', 'rightMode',
                'leftInput', 'leftExternalInput', 'rightInput', 'rightExternalInput',
                'leftOutput', 'rightOutput'):
    assert 'component == ' + control in tooltips, 'missing tooltip: ' + control
print('PASS matched Designer/source pair, hashes, symmetric gain, PROC defaults, direct bypass, and tooltips')

def class_block(text, declaration):
    start = text.index(declaration)
    opening = text.index('{', start)
    depth = 0
    for index in range(opening, len(text)):
        if text[index] == '{':
            depth += 1
        elif text[index] == '}':
            depth -= 1
            if depth == 0:
                return text[start:index + 1]
    raise RuntimeError('Unclosed class: ' + declaration)

constants = '\n'.join(re.findall(r'^private static final double [^;]+;', source, re.M))
dsp = class_block(source, 'private static final class SignalProcessorDsp')
stage = class_block(source, 'private static final class ProcessorStage')
ramp = class_block(source, 'private static final class ControlRamp')
clamp = re.search(
    r'private static double clamp\(double value, double minimum, double maximum\) \{.*?^\}',
    source, re.M | re.S).group(0)
wrapper = f'''public final class SignalProcessorCore {{
{constants}
private final SignalProcessorDsp dsp;
public SignalProcessorCore(double sampleRate) {{ dsp = new SignalProcessorDsp(sampleRate); }}
public void setTopControls(double gain, double offset, double level, boolean vca) {{
    dsp.setLeftControls(gain, offset, level, vca);
}}
public void setBottomControls(double gain, double offset, double level, boolean vca) {{
    dsp.setRightControls(gain, offset, level, vca);
}}
public void process(double leftInput, double leftCv, double rightInput, double rightCv) {{
    dsp.process(leftInput, leftCv, rightInput, rightCv);
}}
public void processBypassed(double leftInput, double rightInput) {{
    dsp.processBypassed(leftInput, rightInput);
}}
public double getTopOutput() {{ return dsp.getLeftOutput(); }}
public double getBottomOutput() {{ return dsp.getRightOutput(); }}
{dsp}
{stage}
{ramp}
{clamp}
}}
'''
generated = 'package com.insectlabs.laboratory.signalprocessor;\n\n' + wrapper

build = root / 'build/signal-processor-character'
build.mkdir(parents=True, exist_ok=True)
(build / 'SignalProcessorCore.java').write_text(generated, encoding='utf-8')
test = module / 'tests/SignalProcessorCharacterTest.java'
subprocess.run(['javac', '--release', '17', '-Xlint:all', '-Werror', '-d', str(build),
                str(build / 'SignalProcessorCore.java'), str(test)], check=True)
subprocess.run(['java', '-cp', str(build),
                'com.insectlabs.laboratory.signalprocessor.SignalProcessorCharacterTest'], check=True)
