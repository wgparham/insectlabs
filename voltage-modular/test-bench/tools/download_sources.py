"""Download the four pinned, credited WAV sources and verify archival hashes."""
import argparse, hashlib, json, urllib.request
from pathlib import Path
p=argparse.ArgumentParser();p.add_argument('--output',type=Path,required=True);a=p.parse_args()
m=json.loads((Path(__file__).resolve().parent.parent/'archive-metadata/manifest.json').read_text())
a.output.mkdir(parents=True,exist_ok=True)
for s in m['sources']:
    dest=a.output/(s['id']+'.wav')
    data=dest.read_bytes() if dest.exists() else urllib.request.urlopen(s['mirror'],timeout=60).read()
    if hashlib.sha256(data).hexdigest()!=s['sha256']:
        raise RuntimeError('Source hash mismatch: '+s['id'])
    if not dest.exists(): dest.write_bytes(data)
    print('Verified '+dest.name)
