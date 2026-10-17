"""Build both N02 sources against the SDK and exercise the actual DSP user regions."""
from pathlib import Path
import argparse
import hashlib
import json
import re
import subprocess
import sys
import tempfile

dev = Path(__file__).resolve().parents[1]
repo = next(p for p in dev.parents if (p / 'voltage-modular/colorbox/tools').exists())
sys.path.insert(0, str(repo / 'voltage-modular/colorbox/tools'))
from value_tree import Reader, encode, props
from support import regions, normalized

parser = argparse.ArgumentParser()
parser.add_argument('--build-root', type=Path, required=True)
args = parser.parse_args()
source = (dev / 'n02.java').read_text(encoding='utf-8-sig')
raw = (dev / 'n02.vmod').read_bytes()
reader = Reader(raw)
tree = reader.tree()
assert reader.p == len(raw) and encode(tree) == raw
embedded = props(next(n for n in tree[2] if n[0] == 'module code'))['MODULE.SOURCE']
assert normalized(source) == normalized(embedded)
assert props(tree)['notes'] == 'v1.0.0'
controls = next(n for n in tree[2] if n[0] == 'controls')[2]
assert len({props(n)['UUID'] for n in controls}) == len(controls)
for node in controls:
    d = props(node)
    name = d.get('variable name', '')
    if name.startswith('knob'):
        match = re.search(re.escape(name) + r' = new VoltageKnob\([^;]+this, ([^,]+), ([^,]+), ([^)]+)\)', source)
        assert match, name
        generated = tuple(float(v.strip()) for v in match.groups())
        declared = tuple(float(d[k]) for k in ('minValueString','maxValueString','defaultValueString'))
        assert generated == declared, (name, generated, declared)
manifest = dev / 'SHA256.json'
if manifest.exists():
    for name, digest in json.loads(manifest.read_text(encoding='utf-8')).items():
        assert hashlib.sha256((dev/name).read_bytes()).hexdigest() == digest, name

parts = regions(source)
harness = (dev / 'tests/harness.template').read_text(encoding='utf-8')
for marker, name in [('CORE', 'code-and-variables'), ('INIT', 'Initialize'),
                     ('PROCESS', 'ProcessSample'), ('BYPASS', 'ProcessBypassedSample'),
                     ('RECALL', 'SetStateInformation'), ('TOOLTIP', 'GetTooltipText'), ('EDIT', 'EditComponentValue')]:
    harness = harness.replace('@@' + marker + '@@', parts[name])
assert '@@' not in harness
jdk = Path(r'C:\Program Files\Java\jdk-27\bin')
args.build_root.mkdir(parents=True, exist_ok=True)
with tempfile.TemporaryDirectory(dir=args.build_root) as temporary:
    build = Path(temporary)
    for label, text in [('export', source), ('embedded', embedded)]:
        output = build / label
        output.mkdir()
        path = output / 'n02.java'
        path.write_text(text, encoding='utf-8')
        subprocess.run([str(jdk/'javac.exe'), '--release', '17', '-Xlint:all', '-Werror',
                        '-encoding', 'UTF-8', '-cp', r'C:\ProgramData\Voltage\voltage.jar',
                        '-d', str(output), str(path)], check=True)
    test = build / 'NoiseChecks.java'
    test.write_text(harness, encoding='utf-8')
    subprocess.run([str(jdk/'javac.exe'), '--release', '17', '-Xlint:all', '-Werror',
                    '-d', str(build), str(repo/'dsp-primitives/src/com/insectlabs/dsp/noise/GaloisLfsr.java'), str(test)], check=True)
    subprocess.run([str(jdk/'java.exe'), '-cp', str(build), 'NoiseChecks'], check=True)
print('PASS: SDK builds for export/embedded, source agreement, lossless VMOD and DSP harness')
