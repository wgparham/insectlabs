"""Validate the Fader|Distr A Designer/source pair and its fixed linear wiring."""
from pathlib import Path
import argparse
import hashlib
import json
import re
import subprocess
import sys

root = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(root.parent / 'colorbox' / 'tools'))
from value_tree import Reader, encode, props
from support import normalized, regions

parser = argparse.ArgumentParser()
parser.add_argument('--sdk', type=Path, required=True)
args = parser.parse_args()

folder = root / 'panfade' / 'development'
java_path = folder / 'faderdistrA.java'
vmod_path = folder / 'faderdistrA.vmod'
manifest_path = folder / 'SHA256-faderdistrA.json'

raw = vmod_path.read_bytes()
reader = Reader(raw)
tree = reader.tree()
assert reader.p == len(raw) and encode(tree) == raw
embedded = props(next(node for node in tree[2] if node[0] == 'module code'))['MODULE.SOURCE']
exported = java_path.read_text(encoding='utf-8-sig')
assert normalized(embedded) == normalized(exported)
for name, digest in json.loads(manifest_path.read_text()).items():
    assert hashlib.sha256((folder / name).read_bytes()).hexdigest() == digest

controls = next(node for node in tree[2] if node[0] == 'controls')
expected = {
    'bias', 'xInput', 'yInput', 'zOutput', 'signalInput', 'oneOutput', 'twoOutput',
    'moduleTitleLabel', 'manufacturerLabel', 'xLabel', 'yLabel', 'zLabel', 'oneLabel',
    'twoLabel', 'signalLabel', 'xOneBiasLabel', 'yTwoBiasLabel', 'faderSectionLabel',
    'distrSectionLabel', 'biasLabel', 'faderLeftRule', 'faderRightRule', 'distrLeftRule',
    'distrRightRule',
}
actual = {props(control)['variable name'] for control in controls[2]}
assert actual == expected
for control in controls[2]:
    values = props(control)
    assert values['CEditableControl.name'] == values['variable name']
    assert values['display name'] == values['variable name']

assert 'new FaderDistrCore(false, SAMPLE_RATE)' in exported
assert 'faderDistr.process(bias.GetValue(), readInput(signalInput),' in exported
assert 'readInput(xInput), readInput(yInput));' in exported
assert 'zOutput.SetValue(faderDistr.getMixOutput());' in exported
assert 'oneOutput.SetValue(faderDistr.getLeftOutput());' in exported
assert 'twoOutput.SetValue(faderDistr.getRightOutput());' in exported
assert 'faderDistr.processBypassed(readInput(signalInput), readInput(xInput));' in exported
tooltip = regions(exported)['GetTooltipText']
for control in ('bias', 'xInput', 'yInput', 'zOutput', 'signalInput', 'oneOutput', 'twoOutput'):
    assert 'component == ' + control in tooltip
print('PASS Fader|Distr A pair, linear mapping, direct bypass, and tooltips', flush=True)

build = root / 'build' / 'faderdistrA-sdk'
for kind, text in [('exported', exported), ('embedded', embedded)]:
    dest = build / kind
    dest.mkdir(parents=True, exist_ok=True)
    source = dest / 'faderdistrA.java'
    source.write_text(text, encoding='utf-8')
    subprocess.run([
        'javac', '--release', '17', '-Xlint:all', '-Werror', '-cp', str(args.sdk),
        '-d', str(dest), str(source)
    ], check=True)
print('PASS Fader|Distr A SDK compilation of both source forms')
