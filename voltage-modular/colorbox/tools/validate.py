"""Validate the archived Designer projects and run SDK/audio regression checks."""
from pathlib import Path
import argparse, hashlib, json, subprocess, sys
from support import ROOT, VERSIONS, normalized
from value_tree import Reader, encode, props

def run(args): subprocess.run(args, check=True)

def main():
    parser=argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--sdk',type=Path,help='Path to the installed Voltage Modular voltage.jar')
    args=parser.parse_args()
    build=Path('C:/InsectLabs-Build/colorbox');build.mkdir(parents=True,exist_ok=True)
    for name,(old,new) in VERSIONS.items():
        trees={}
        for version in (old,new):
            folder=ROOT/name/'versions'/version
            for filename,digest in json.loads((folder/'SHA256.json').read_text()).items():
                assert hashlib.sha256((folder/filename).read_bytes()).hexdigest()==digest,(name,version,filename)
            stem=name+'_'+version.replace('.','-')
            raw=(folder/(stem+'.vmod')).read_bytes();r=Reader(raw);tree=r.tree()
            assert r.p==len(raw) and encode(tree)==raw
            trees[version]=tree
            module=next(x for x in tree[2] if x[0]=='module code')
            source=props(module)['MODULE.SOURCE'];export=(folder/(stem+'.java')).read_text(encoding='utf-8-sig')
            assert normalized(source)==normalized(export),(name,version,'source mismatch')
            if version==new:
                for kind,text in [('embedded',source),('exported',export)]:
                    dest=build/kind/name;dest.mkdir(parents=True,exist_ok=True)
                    (dest/(name.upper()+'.java')).write_text(text,encoding='utf-8')
        for typ in ['controls','art resources','resource names','skin names','test mode parent']:
            assert [encode(x) for x in trees[old][2] if x[0]==typ]==[encode(x) for x in trees[new][2] if x[0]==typ],(name,typ)
        # Read-only line entries must continue to identify the same generated lines and marker IDs.
        oldmodule=next(x for x in trees[old][2] if x[0]=='module code')
        newmodule=next(x for x in trees[new][2] if x[0]=='module code')
        def anchored(m):
            lines=props(m)['MODULE.SOURCE'].splitlines()
            return [(lines[props(n)['line number']],encode(['markers',n[1][1:],n[2]])) for n in m[2][0][2]]
        assert anchored(oldmodule)==anchored(newmodule),(name,'editor anchors')
        print('PASS',name,'hashes, binary round-trip, source agreement, panel/resource preservation, editor anchors',flush=True)
    if args.sdk:
        assert args.sdk.is_file(),args.sdk
        for kind in ('embedded','exported'):
            run(['javac','--release','17','-Xlint:all','-encoding','utf-8','-classpath',str(args.sdk),'-d',str(build/(kind+'-classes'))]+[str(build/kind/n/(n.upper()+'.java')) for n in VERSIONS])
            print('PASS SDK compilation:',kind,flush=True)
    else:print('SKIP SDK compilation: supply --sdk /path/to/voltage.jar',flush=True)
    run([sys.executable,str(Path(__file__).with_name('audio_regression.py'))])
    run(['javac','--release','17','-encoding','utf-8','-d',str(build/'test-classes'),str(build/'tests/Regression.java')])
    run(['java','-cp',str(build/'test-classes'),'Regression'])

if __name__=='__main__':main()
