"""Compile both source forms and exercise the actual SVF core/callbacks."""
from pathlib import Path
import argparse,sys,re,subprocess,tempfile
p=Path(__file__).resolve().parents[1]
r=next(x for x in p.parents if (x/'dsp-primitives').exists())
sys.path.insert(0,str(r/'voltage-modular/colorbox/tools'))
from value_tree import Reader,encode,props
from support import regions,normalized
ap=argparse.ArgumentParser();ap.add_argument('--build-root',type=Path,required=True);args=ap.parse_args()
s=(p/'svf.java').read_text(encoding='utf-8-sig');raw=(p/'svf.vmod').read_bytes();t=Reader(raw).tree()
assert encode(t)==raw
e=props(next(x for x in t[2] if x[0]=='module code'))['MODULE.SOURCE']
assert normalized(s)==normalized(e)
assert props(t)['notes']=='v1.0.1'
z=regions(s);h=(p/'tests/harness.template').read_text()
for token,key in [('CORE','code-and-variables'),('INIT','Initialize'),('PROCESS','ProcessSample'),('BYPASS','ProcessBypassedSample'),('TOOLTIP','GetTooltipText'),('EDIT','EditComponentValue')]:
    h=h.replace('@@'+token+'@@',z[key])
fields='\n'.join(kind+' '+name+' = new '+kind+'();' for kind,name in re.findall(r'private\s+(Voltage\w+)\s+(\w+);',s))
h=h.replace('@@FIELDS@@',fields)
args.build_root.mkdir(parents=True,exist_ok=True)
with tempfile.TemporaryDirectory(dir=args.build_root) as temp:
    b=Path(temp);jdk=Path(r'C:\Program Files\Java\jdk-27\bin');sdk=r'C:\ProgramData\Voltage\voltage.jar'
    for label,text in [('export',s),('embedded',e)]:
        out=b/label;out.mkdir();f=out/'svf.java';f.write_text(text,encoding='utf-8')
        subprocess.run([str(jdk/'javac.exe'),'--release','17','-Xlint:all','-Werror','-encoding','UTF-8','-cp',sdk,'-d',str(out),str(f)],check=True)
    f=b/'SvfChecks.java';f.write_text(h,encoding='utf-8')
    subprocess.run([str(jdk/'javac.exe'),'--release','17','-Xlint:all','-Werror','-d',str(b),str(r/'dsp-primitives/src/com/insectlabs/dsp/filters/EmsSvf96.java'),str(f)],check=True)
    subprocess.run([str(jdk/'java.exe'),'-cp',str(b),'SvfChecks'],check=True)
print('PASS: both SDK compilations, embedded/export agreement and VMOD round-trip')
