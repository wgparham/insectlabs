"""Read-only checks for release integrity, canonical indexes and local Markdown links."""
import argparse
import hashlib
import json
from pathlib import Path
import re
import subprocess
import sys
from urllib.parse import unquote

parser = argparse.ArgumentParser(description=__doc__)
parser.add_argument('--repo', type=Path, default=Path(__file__).resolve().parents[1])
args = parser.parse_args()
repo = args.repo.resolve()
sys.dont_write_bytecode = True
sys.path.insert(0, str(repo / 'voltage-modular/colorbox/tools'))
from value_tree import Reader, props, encode
from support import normalized

tracked = subprocess.check_output(['git', '-c', f'safe.directory={repo.as_posix()}',
    '-C', str(repo), 'ls-files', '-z'], text=True).split('\0')
files = [repo / name for name in tracked if name]
errors = []
counts = {'manifests': 0, 'hashed_files': 0, 'pairs': 0, 'canonical_modules': 0, 'local_links': 0}

def check(ok, description):
    if not ok:
        errors.append(description)

def digest(path):
    with path.open('rb') as stream:
        return hashlib.file_digest(stream, 'sha256').hexdigest()

for manifest in files:
    if manifest.name != 'SHA256.json':
        continue
    counts['manifests'] += 1
    for name, expected in json.loads(manifest.read_text(encoding='utf-8-sig')).items():
        path = manifest.parent / name
        check(path.is_file(), f'Missing manifest entry: {path}')
        if path.is_file():
            counts['hashed_files'] += 1
            check(digest(path) == expected, f'Hash mismatch: {path}')

for index in files:
    if index.name != 'CANONICAL.json':
        continue
    for name, entry in json.loads(index.read_text(encoding='utf-8-sig')).items():
        counts['canonical_modules'] += 1
        folder = index.parent / entry['path']
        check(folder.is_dir() and (folder/'README.md').is_file(), f'Invalid canonical path: {name}: {folder}')
        check(folder.name == entry['version'], f'Canonical version/path mismatch: {name}')
        check((folder/'SHA256.json').is_file(), f'Missing canonical manifest: {name}')

for path in files:
    if path.suffix != '.vmod' or 'versions' not in path.parts:
        continue
    source = path.with_suffix('.java')
    check(source.is_file(), f'Missing Java export: {path}')
    if not source.is_file():
        continue
    try:
        raw = path.read_bytes(); reader = Reader(raw); tree = reader.tree()
        check(reader.p == len(raw) and encode(tree) == raw, f'Designer round-trip failed: {path}')
        embedded = props(next(n for n in tree[2] if n[0] == 'module code'))['MODULE.SOURCE']
        check(normalized(embedded) == normalized(source.read_text(encoding='utf-8-sig')),
              f'Source mismatch: {path}')
        counts['pairs'] += 1
    except (ValueError, KeyError, StopIteration, IndexError) as exc:
        errors.append(f'Cannot inspect {path}: {exc}')

for path in files:
    if path.suffix != '.md':
        continue
    # Local file links only; URL fragments are left to Markdown renderers.
    content = re.sub(r'```.*?```', '', path.read_text(encoding='utf-8-sig'), flags=re.S)
    for target in re.findall(r'\]\(([^)]+)\)', content):
        target = target.strip().strip('<>')
        if re.match(r'[a-zA-Z][\w+.-]*:', target) or target.startswith(('#', '/')):
            continue
        target = unquote(target.split('#', 1)[0])
        if target:
            counts['local_links'] += 1
            check((path.parent/target).exists(), f'Broken link: {path.relative_to(repo)} -> {target}')

print(json.dumps({'counts': counts, 'errors': errors}, indent=2))
raise SystemExit(bool(errors))
