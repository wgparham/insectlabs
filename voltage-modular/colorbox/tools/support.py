from pathlib import Path
import re
ROOT = Path(__file__).resolve().parent.parent
VERSIONS = {'rgb': ('4.0.2', '4.0.3'), 'cmyk': ('1.0.2', '1.0.3'), 'hsb': ('1.0.1', '1.0.2')}
REGION = re.compile(r'(?P<open>^[ \t]*//\[user-(?P<name>[^\]]+)\][^\n]*\n)(?P<body>.*?)(?P<close>^[ \t]*//\[/user-(?P=name)\][^\n]*)', re.M | re.S)
def regions(s): return {m['name']: m['body'] for m in REGION.finditer(s)}
def tokens(s):
    return re.findall(r'"(?:\\.|[^"\\])*"|\'(?:\\.|[^\'\\])*\'|[A-Za-z_$][\w$]*|\d+(?:\.\d*)?(?:[eE][+-]?\d+)?|[^\s]', re.sub(r'//[^\n]*|/\*.*?\*/', '', s, flags=re.S))
def normalized(s):
    # Designer exports declarations in a different order. Their initialization is in the constructor.
    decl = re.compile(r'^[ \t]*private Voltage\w+ \w+;[ \t]*$', re.M)
    return tokens(decl.sub('', s)), sorted(line.strip() for line in decl.findall(s))
def rename_rgb(s):
    names = {'resetOversamplingState':'resetDsp', 'PARAM_SMOOTH':'CONTROL_SMOOTH'}
    for color in ('red','green','blue'):
        names[color+'UpSampler'] = color+'InputUpsampler'
        names[color+'DownSampler'] = color+'OutputDownsampler'
        names['reset'+color.title()+'DC'] = 'reset'+color.title()+'Dc'
        for suffix in ('PrevInput','PrevOutput'): names[color+'DC'+suffix] = color+'Dc'+suffix
    return re.sub(r'\b('+'|'.join(names)+r')\b', lambda m:names[m[0]], s)
