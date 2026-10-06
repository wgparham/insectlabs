"""Compile both Designer source forms and execute the current DSP in stub controls."""
from pathlib import Path
import re, subprocess, sys

module = Path(__file__).resolve().parents[1]
repo = next(p for p in module.parents if (p/'voltage-modular/colorbox/tools').is_dir())
sys.path.insert(0, str(repo/'voltage-modular/colorbox/tools'))
from support import regions, tokens
from value_tree import Reader, props, encode

source = (module/'function.java').read_text(encoding='utf-8-sig')
raw = (module/'function.vmod').read_bytes()
reader = Reader(raw); tree = reader.tree()
assert reader.p == len(raw) and encode(tree) == raw
embedded = props(next(n for n in tree[2] if n[0]=='module code'))['MODULE.SOURCE']
def semantic_source(value):
    declarations = re.compile(r'^\s*private Voltage\w+ \w+;\s*$', re.M)
    return tokens(declarations.sub('', value)), sorted(declarations.findall(value))
assert semantic_source(source) == semantic_source(embedded)
r = regions(source)
harness = (Path(__file__).parent/'FunctionCallbackTest.java').read_text()
for name in ('Initialize','ProcessSample','ProcessBypassedSample'):
    assert len(tokens(r[name])) > 5, 'Empty callback: '+name
    start = harness.index('void '+name+'() {')
    brace = harness.index('{',start); end = brace+1; depth = 1
    while depth:
        if harness[end]=='{': depth+=1
        if harness[end]=='}': depth-=1
        end+=1
    harness = harness[:start]+'void '+name+'() {\n'+r[name]+'\n}'+harness[end:]
start=harness.index('    private static final double SAMPLE_RATE')
end=harness.index('    void StartGuiUpdateTimer()',start)
harness=harness[:start]+r['code-and-variables']+'\n'+harness[end:]
build=Path(r'C:\InsectLabs-Build\function-callback-validation')
for part in ('embedded','export-classes','embedded-classes','test-classes'):
    (build/part).mkdir(parents=True,exist_ok=True)
(build/'embedded/function.java').write_text(embedded,encoding='utf-8')
(build/'FunctionCallbackTest.java').write_text(harness,encoding='utf-8')
for path,dest in [(module/'function.java','export-classes'),(build/'embedded/function.java','embedded-classes'),(build/'FunctionCallbackTest.java','test-classes')]:
    subprocess.run(['javac','--release','17','-encoding','UTF-8','-Xlint:all','-Werror','-cp',r'C:\ProgramData\Voltage\voltage.jar','-d',str(build/dest),str(path)],check=True)
subprocess.run(['java','-cp',str(build/'test-classes'),'FunctionCallbackTest'],check=True)
print('PASS: matched sources and SDK compilation; actual callbacks, range matrix, output independence, bypass/resume')
