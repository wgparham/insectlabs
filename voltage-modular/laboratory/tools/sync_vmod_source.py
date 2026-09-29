"""Replace a Designer project's embedded source and preserve editor line anchors."""
from pathlib import Path
import argparse
import hashlib
import json
import re
import sys

root = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(root.parent / 'colorbox' / 'tools'))
from value_tree import Reader, encode, props, setprop
from support import normalized

parser = argparse.ArgumentParser()
parser.add_argument('--java', type=Path, required=True)
parser.add_argument('--vmod', type=Path, required=True)
parser.add_argument('--manifest', type=Path)
parser.add_argument('--asset', type=Path, action='append', default=[])
args = parser.parse_args()

raw = args.vmod.read_bytes()
reader = Reader(raw)
tree = reader.tree()
if reader.p != len(raw) or encode(tree) != raw:
    raise RuntimeError('Designer project did not round-trip before editing')
code = next(node for node in tree[2] if node[0] == 'module code')
old_source = props(code)['MODULE.SOURCE']
exported_source = args.java.read_text(encoding='utf-8-sig')

# Designer stores generic comments instead of exported user-region markers.
def designer_opener(match):
    indent, name = match['indent'], match['name']
    if name == 'imports':
        return '// Add your own imports here'
    if name == 'inheritance':
        return ''
    if name == 'code-and-variables':
        return '// Add your own variables and functions here'
    return indent + '// add your own code here'

# Derive ownership from explicit export markers, never from matching brace text.
new_lines = []
readonly = []
active_region = None
for line in exported_source.splitlines():
    opener = re.match(r'^(?P<indent>[ \t]*)//\[user-(?P<name>[^\]]+)\]', line)
    closer = re.match(r'^[ \t]*//\[/user-([^\]]+)\]', line)
    if opener:
        if active_region is not None:
            raise RuntimeError('Nested user region')
        active_region = opener['name']
        line = designer_opener(opener)
        is_readonly = active_region != 'inheritance'
    elif closer:
        if closer[1] != active_region:
            raise RuntimeError('Mismatched user-region end')
        active_region = None
        continue
    else:
        is_readonly = active_region is None
    if is_readonly:
        readonly.append(len(new_lines))
    new_lines.append(line)
if active_region is not None:
    raise RuntimeError('Unclosed user region')
new_source = '\n'.join(new_lines) + '\n'

# Preserve named Designer section/control markers and move their original records.
import difflib
old_lines = old_source.splitlines()
mapping = {}
for block in difflib.SequenceMatcher(a=old_lines, b=new_lines, autojunk=False).get_matching_blocks():
    for offset in range(block.size):
        mapping[block.a + offset] = block.b + offset
line_properties = code[2][0]
controls = next(node for node in tree[2] if node[0] == 'controls')
control_names = {props(control)['UUID']: props(control)['variable name']
                 for control in controls[2]}

def control_line(info, old_number):
    """Find a renamed Designer control's generated line by its immutable UUID."""
    control_ids = [props(child).get('Control ID') for child in info[2]
                   if props(child).get('Control ID')]
    if not control_ids:
        return None
    name = control_names[control_ids[0]]
    old_line = old_lines[old_number]
    rewritten = re.sub(r'\b(?:knob|inputJack|outputJack|textLabel|line|button|switch|LED)\d+\b',
                       name, old_line)
    candidates = [index for index, line in enumerate(new_lines)
                  if line == rewritten or line.strip() == rewritten.strip()]
    if candidates:
        return min(candidates, key=lambda index: abs(index - old_number))
    # A control may have been deliberately renamed. Its Designer placement is
    # immutable, so use the generated SetPosition line as a stable fallback.
    declaration_index = next((index for index in range(old_number, -1, -1)
                              if re.match(r'\s*' + re.escape(name) + r'\s*=\s*new ', old_lines[index])), None)
    if declaration_index is not None:
        old_position = next((line.strip() for line in old_lines[declaration_index:]
                             if line.strip().startswith(name + '.SetPosition(')), None)
        if old_position:
            position_suffix = old_position[len(name):]
            position_matches = [index for index, line in enumerate(new_lines)
                                if line.strip().endswith(position_suffix)]
            if len(position_matches) == 1:
                position_index = position_matches[0]
                new_declaration = next(index for index in range(position_index, -1, -1)
                                       if ' = new ' in new_lines[index])
                new_name = re.match(r'\s*(\w+)\s*=\s*new ', new_lines[new_declaration]).group(1)
                rewritten = re.sub(r'\b' + re.escape(name) + r'\b', new_name, old_line)
                candidates = [index for index, line in enumerate(new_lines)
                              if line == rewritten or line.strip() == rewritten.strip()]
                if candidates:
                    return min(candidates, key=lambda index: abs(index - new_declaration))
                return new_declaration - 1
    if not old_line.strip():
        declaration = next(index for index, line in enumerate(new_lines)
                           if line.startswith('    ' + name + ' = new '))
        return declaration - 1
    raise RuntimeError(f'Cannot locate generated control line for {name}: {old_line!r}')

for info in line_properties[2]:
    old_number = props(info)['line number']
    ids = {props(child).get('ID') for child in info[2]}
    if 'class closing bracket' in ids:
        number = max(i for i, line in enumerate(new_lines) if line.strip() == '}')
    elif 'user code and variables' in ids:
        number = new_lines.index('// Add your own variables and functions here')
    else:
        number = control_line(info, old_number)
        if number is None and old_number not in mapping:
            raise RuntimeError(f'Cannot preserve Designer section at line {old_number}')
        if number is None:
            number = mapping[old_number]
    setprop(info, 'line number', number)
all_ids = [props(child).get('ID') for info in line_properties[2] for child in info[2]]
assert all_ids.count('class closing bracket') == 1
assert all_ids.count('user code and variables') == 1
assert len(set(all_ids)) > 50, 'Missing Designer section identifiers'

setprop(code, 'MODULE.SOURCE', new_source)
args.vmod.write_bytes(encode(tree))
check = Reader(args.vmod.read_bytes()).tree()
embedded = props(next(node for node in check[2] if node[0] == 'module code'))['MODULE.SOURCE']
if normalized(embedded) != normalized(exported_source):
    raise RuntimeError('Embedded/exported source mismatch after writing')

if args.manifest:
    files = [args.vmod.name, args.java.name]
    assets = args.asset or [args.vmod.parent / 'sigproc_hero.png']
    for asset in assets:
        asset = asset if asset.is_absolute() else args.vmod.parent / asset
        if not asset.is_file():
            raise RuntimeError('Missing manifest asset: ' + str(asset))
        files.append(asset.name)
    hashes = {name: hashlib.sha256((args.vmod.parent / name).read_bytes()).hexdigest()
              for name in files}
    args.manifest.write_text(json.dumps(hashes, indent=2) + '\n')
print('Synchronized embedded source and editor anchors.')
