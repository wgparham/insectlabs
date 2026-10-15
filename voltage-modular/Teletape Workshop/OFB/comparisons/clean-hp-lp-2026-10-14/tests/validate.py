from pathlib import Path
import sys,re,subprocess,tempfile,argparse
p=Path(__file__).resolve().parents[1]
repo=next(x for x in p.parents if (x/'dsp-primitives').exists())
sys.path.insert(0,str(repo/'voltage-modular/colorbox/tools'))
from support import regions,normalized
from value_tree import Reader,props,encode
args=argparse.ArgumentParser();args.add_argument('--build-root',type=Path,required=True);a=args.parse_args()
s=(p/'ofb.java').read_text(encoding='utf-8-sig');r=regions(s);raw=(p/'ofb.vmod').read_bytes();t=Reader(raw).tree();assert encode(t)==raw;e=props(next(x for x in t[2] if x[0]=='module code'))['MODULE.SOURCE'];assert normalized(s)==normalized(e)
h=(p/'tests/topology-harness.template').read_text()
fields='\n'.join(kind+' '+name+'=new '+kind+'();' for kind,name in re.findall(r'private\s+(Voltage\w+)\s+(\w+);',s))
for token,key in [('@@CORE@@','code-and-variables'),('@@INIT@@','Initialize'),('@@PROCESS@@','ProcessSample'),('@@BYPASS@@','ProcessBypassedSample')]:h=h.replace(token,r[key])
h=h.replace('@@FIELDS@@',fields)
with tempfile.TemporaryDirectory(dir=a.build_root) as temp:
 b=Path(temp);jdk=Path(r'C:\Program Files\Java\jdk-27\bin');sdk=r'C:\ProgramData\Voltage\voltage.jar'
 for label,text in [('export',s),('embedded',e)]:
  folder=b/label;folder.mkdir();f=folder/'ofb.java';f.write_text(text,encoding='utf-8');subprocess.run([str(jdk/'javac.exe'),'--release','17','-Xlint:all','-Werror','-encoding','UTF-8','-cp',sdk,'-d',str(folder),str(f)],check=True)
 f=b/'OfbTopologyChecks.java';f.write_text(h,encoding='utf-8');subprocess.run([str(jdk/'javac.exe'),'--release','17','-d',str(b),str(f)],check=True);subprocess.run([str(jdk/'java.exe'),'-cp',str(b),'OfbTopologyChecks'],check=True)
