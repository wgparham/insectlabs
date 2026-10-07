from pathlib import Path
import hashlib, json, subprocess, sys
sys.dont_write_bytecode = True

work=Path(__file__).resolve().parent
repo=next((p for p in work.parents if (p/'voltage-modular/colorbox/tools').is_dir()),Path(r'C:\Users\wgparham\Dropbox\git\insectlabs'))
pair=Path(sys.argv[1]) if len(sys.argv)>1 else (work.parent if (work.parent/'following_2.vmod').is_file() else work/'candidate')
build=Path(r'C:\InsectLabs-Build\following\first-dsp')
build.mkdir(parents=True,exist_ok=True)
sdk=Path(r'C:\ProgramData\Voltage\voltage.jar')
sys.path.insert(0,str(repo/'voltage-modular/colorbox/tools'))
from value_tree import Reader,encode,props
from support import normalized

raw=(pair/'following_2.vmod').read_bytes(); r=Reader(raw); tree=r.tree()
assert r.p==len(raw) and encode(tree)==raw
embedded=props(next(n for n in tree[2] if n[0]=='module code'))['MODULE.SOURCE']
exported=(pair/'following_2.java').read_text(encoding='utf-8-sig')
assert normalized(embedded)==normalized(exported),'source mismatch'
assert props(tree)['notes'] == 'v1.0.0'
def verify_notes(node):
    if node is not tree and 'notes' in props(node): assert props(node)['notes']==''
    for child in node[2]: verify_notes(child)
verify_notes(tree)
# Bypass wrapper must not sample controls; the core test also verifies frozen histories.
bypass=exported.split('//[user-ProcessBypassedSample]',1)[1].split('//[/user-ProcessBypassedSample]',1)[0]
assert 'GetValue()' in bypass and bypass.count('GetValue()')==1 and 'signalInput.GetValue()' in bypass
assert 'setControls' not in bypass
controls=next(n for n in tree[2] if n[0]=='controls')
for n in controls[2]:
    p=props(n)
    if p['variable name'].endswith('Knob'):
        assert p['defaultValue']==float(p['defaultValueString']),p['variable name']
    if p['variable name'].endswith('Switch'):
        assert p['defaultValue']==float(p['defaultValueString'])==p['initialState'],p['variable name']
flags=['-Werror','--release','17','-Xlint:cast,deprecation,divzero,empty,fallthrough,finally,rawtypes,unchecked']
for label,source in [('exported',exported),('embedded',embedded)]:
    folder=build/label; folder.mkdir(exist_ok=True)
    classes=folder/'classes'; classes.mkdir(exist_ok=True)
    (folder/'following.java').write_text(source,encoding='utf-8')
    subprocess.run(['javac',*flags,'-cp',str(sdk),'-d',str(classes),str(folder/'following.java')],check=True)
    harness=work/'FollowingChecks.java'
    subprocess.run(['javac',*flags,'-cp',str(classes)+';'+str(sdk),'-d',str(classes),str(harness)],check=True)
    subprocess.run(['java','-cp',str(classes)+';'+str(sdk),'com.insectlabs.following.FollowingChecks'],check=True)
# Exercise the actual callback bodies with lightweight host-object spies.
import routing_checks
routing_checks.run(exported, build/'exported', sdk, flags)
print('PASS: both Java forms compile against the SDK; source agreement and DSP checks pass.')
print(json.dumps({name:hashlib.sha256((pair/name).read_bytes()).hexdigest() for name in ('following_2.java','following_2.vmod')},indent=2))
