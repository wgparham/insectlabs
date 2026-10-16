"""Build both actual SDK sources and exercise exported callbacks with headless controls."""
from pathlib import Path
import os
import json
import hashlib
import re
import subprocess
import sys

module = Path(__file__).resolve().parents[1]
repo = next(p for p in module.parents if (p / 'voltage-modular').is_dir())
sys.path.insert(0, str(repo / 'voltage-modular/colorbox/tools'))
from value_tree import Reader, encode, props
from support import normalized, regions

source = (module / 'zoid.java').read_text(encoding='utf-8-sig')
raw = (module / 'zoid.vmod').read_bytes()
reader = Reader(raw)
tree = reader.tree()
assert reader.p == len(raw) and encode(tree) == raw
embedded = props(next(c for c in tree[2] if c[0] == 'module code'))['MODULE.SOURCE']
assert normalized(source) == normalized(embedded), 'Java/Designer source mismatch'
assert props(tree)['notes'].strip() == 'v1.0.0'
assert props(tree)['class name'] == 'zoid'
assert props(tree)['package name'] == 'com.insectlabs.zoid'
for name, digest in json.loads((module / 'SHA256.json').read_text(encoding='utf-8')).items():
    assert hashlib.sha256((module / name).read_bytes()).hexdigest() == digest, name
controls = next(c for c in tree[2] if c[0] == 'controls')[2]
assert len({props(c)['UUID'] for c in controls}) == len(controls)
for name in ('knobZoid1', 'knobZoid2'):
    control = next(props(c) for c in controls if props(c)['variable name'] == name)
    assert (float(control['minValueString']), float(control['maxValueString']), float(control['defaultValueString'])) == (-1, 1, 0)
parts = regions(source)
template = (module / 'tests/harness.template').read_text()
declarations = re.findall(r'private (Voltage\w+) (\w+);', source)
fields = '\n'.join(f'{kind} {name} = new {kind}();' for kind, name in declarations)
defaults = []
for name, val in re.findall(r'(\w+) = new VoltageKnob\([^\n]*?,\s*([-\d.]+)\s*\);', source):
    defaults.append(f'{name}.value = {val};')
for name in re.findall(r'(\w+)\.DisplayValueInPercent\( true \)', source):
    defaults.append(f'{name}.percent = true;')
template = template.replace('@@FIELDS@@', fields).replace('@@DEFAULTS@@', '\n'.join(defaults))
for tag in ['code-and-variables', 'Initialize', 'Notify', 'ProcessSample',
            'ProcessBypassedSample', 'SetStateInformation', 'GetTooltipText', 'EditComponentValue']:
    template = template.replace('@@' + tag + '@@', parts[tag])
assert '@@' not in template
jdk = Path(r'C:\Program Files\Java\jdk-27\bin')
build_root = Path(os.environ['TEMP']) / 'InsectLabs-Build/zoid-canonical-checks'
build_root.mkdir(parents=True, exist_ok=True)
build = build_root / 'current'
build.mkdir(parents=True, exist_ok=True)
for name, text in [('export', source), ('embedded', embedded)]:
    out = build / name
    out.mkdir(exist_ok=True)
    class_name = re.search(r'public\s+class\s+(\w+)', text).group(1)
    java = out / f'{class_name}.java'
    java.write_text(text, encoding='utf-8')
    subprocess.run([str(jdk / 'javac.exe'), '--release', '17', '-Xlint:all', '-Werror',
                    '-encoding', 'UTF-8', '-cp', r'C:\ProgramData\Voltage\voltage.jar',
                    '-d', str(out), str(java)], check=True)
java = build / 'ZoidChecks.java'
java.write_text(template, encoding='utf-8')
subprocess.run([str(jdk / 'javac.exe'), '--release', '17', '-Xlint:all', '-Werror',
                '-d', str(build), str(java)], check=True)
subprocess.run([str(jdk / 'java.exe'), '-cp', str(build), 'ZoidChecks'], check=True)
print('PASS: exported/embedded builds, version identity, hashes, Designer round-trip/source parity, UUIDs, DSP/callback checks')
