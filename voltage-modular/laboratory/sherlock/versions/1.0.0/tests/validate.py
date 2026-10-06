from pathlib import Path
import sys,subprocess,json,hashlib
sys.dont_write_bytecode=True
work=Path(__file__).resolve().parent
repo=next(p for p in work.parents if (p/'voltage-modular/colorbox/tools').is_dir())
sys.path.insert(0,str(repo/'voltage-modular/colorbox/tools'))
from value_tree import Reader,props,encode
from support import normalized
pair=Path(sys.argv[1]) if len(sys.argv)>1 else work.parent
module=work.parents[2]
raw=(pair/'sherlock.vmod').read_bytes();rd=Reader(raw);t=rd.tree()
assert rd.p==len(raw) and encode(t)==raw
embedded=props(next(n for n in t[2] if n[0]=='module code'))['MODULE.SOURCE']
source=(pair/'sherlock.java').read_text(encoding='utf-8-sig')
assert normalized(source)==normalized(embedded)
controls=next(n for n in t[2] if n[0]=='controls')
original=Reader((module/'references/approved-0.1.0rc/sherlock.vmod').read_bytes()).tree()
oldControls={props(n)['UUID']:props(n) for n in next(n for n in original[2] if n[0]=='controls')[2]}
allowed={'notes'}
for n in controls[2]:
 p=props(n);old=oldControls[p['UUID']]
 assert all(p[k]==v for k,v in old.items() if k not in allowed),p['variable name']
 if p['variable name'].endswith(('Knob','Switch')):assert p['defaultValue']==float(p['defaultValueString'])
 if p['variable name'].endswith('Switch'):assert p['defaultValue']==p['initialState']
for name in ['art resources','resource names','test mode parent']:
 a=next(n for n in t[2] if n[0]==name);b=next(n for n in original[2] if n[0]==name)
 if name!='test mode parent':assert a==b,name
assert props(t)['notes']=='v1.0.0'
def notes_check(n):
 if n is not t and 'notes' in props(n):assert props(n)['notes']==''
 for c in n[2]:notes_check(c)
notes_check(t)
flags=['--release','17','-Werror','-Xlint:cast,deprecation,divzero,empty,fallthrough,finally,rawtypes,unchecked']
sdk=Path(r'C:/ProgramData/Voltage/voltage.jar')
build=Path(r'C:/InsectLabs-Build/sherlock/release-1.0.0')
for label,text in [('exported',source),('embedded',embedded)]:
 folder=build/label;folder.mkdir(parents=True,exist_ok=True)
 classes=folder/'classes';classes.mkdir(exist_ok=True)
 (folder/'sherlock.java').write_text(text,encoding='utf-8')
 subprocess.run(['javac',*flags,'-cp',str(sdk),'-d',str(classes),str(folder/'sherlock.java'),str(work/'SherlockChecks.java')],check=True)
 subprocess.run(['java','-cp',str(classes)+';'+str(sdk),'com.insectlabs.sherlock.SherlockChecks',*(['long'] if label=='exported' else [])],check=True)
import routing_checks
final_trace=routing_checks.run(source,build/'exported',sdk,flags)
baseline=(module/'references/approved-0.1.0rc/sherlock.java').read_text(encoding='utf-8-sig')
assert normalized(source)==normalized(baseline), 'Approved source tokens changed'
folder=build/'approved';classes=folder/'classes';classes.mkdir(parents=True,exist_ok=True)
(folder/'sherlock.java').write_text(baseline,encoding='utf-8')
subprocess.run(['javac',*flags,'-cp',str(sdk),'-d',str(classes),str(folder/'sherlock.java')],check=True)
assert final_trace==routing_checks.run(baseline,folder,sdk,flags),'Callback trace mismatch'
print('PASS: user-approved and canonical callback traces match exactly.')
print('PASS: paired source, metadata/artwork preservation, SDK compilation, and core checks.')
print(json.dumps({p.name:hashlib.sha256(p.read_bytes()).hexdigest() for p in pair.glob('sherlock.*')},indent=2))
