from pathlib import Path
import re, subprocess, json
from support import regions, ROOT, VERSIONS, tokens, rename_rgb

out=Path('C:/InsectLabs-Build/colorbox/tests');out.mkdir(parents=True,exist_ok=True)
head='''public class Regression {
static class VoltageComponent { double v; boolean connected; int reads; double GetValue(){reads++;return v;} void SetValue(double x){v=x;} boolean IsConnected(){return connected;} }
static void equal(double a,double b,String message){if(!Double.isFinite(a)||Double.doubleToLongBits(a)!=Double.doubleToLongBits(b))throw new AssertionError(message+": "+a+" != "+b);}
'''
tests=[];classes=[]
for name in VERSIONS:
    oldver,newver=VERSIONS[name]
    old=(ROOT/name/'versions'/oldver/(name+'_'+oldver.replace('.','-')+'.java')).read_text(encoding='utf-8-sig')
    new=(ROOT/name/'versions'/newver/(name+'_'+newver.replace('.','-')+'.java')).read_text(encoding='utf-8')
    ob,nb=regions(old),regions(new)
    # All executable audio code must retain the same operations, except the intentional switch inversion.
    for key in ['Initialize','ProcessSample','ProcessBypassedSample','code-and-variables']:
        expected=rename_rgb(ob[key]) if name=='rgb' else ob[key]
        if name=='hsb':expected=expected.replace('topTopology.GetValue() > 0.5','topTopology.GetValue() < 0.5').replace('bottomTopology.GetValue() > 0.5','bottomTopology.GetValue() < 0.5').replace('private boolean wasBypassed;','private boolean wasBypassed = false;')
        assert tokens(expected)==tokens(nb[key]),(name,key,'unexpected audio code change')
    controls=re.findall(r'private Voltage(Knob|Switch|AudioJack) (\w+);',old)
    for suffix,b in [('Old',ob),('New',nb)]:
        cls=name+suffix
        c='static class '+cls+' {\n'+''.join('VoltageComponent '+n+'=new VoltageComponent();\n' for typ,n in controls)
        c+=b['code-and-variables']
        for key in ['Initialize','ProcessSample','ProcessBypassedSample']:c+='void '+key+'(){\n'+b[key]+'}\n'
        c+='String tooltip(VoltageComponent component){\n'+b['GetTooltipText'].replace('super.GetTooltipText(component)','"fallback"').replace('super.GetTooltipText( component )','"fallback"')+'}\n'
        classes.append(c+'}\n')
    t=f'static long test{name}() {{ long checked=0; for(int mask=0;mask<128;mask++){{ {name}Old a=new {name}Old(); {name}New b=new {name}New();\n'
    for typ,n in controls:
        default=0
        if typ=='Knob':
            m=re.search(re.escape(n)+r'\.SetRange\(\s*([^,]+),\s*([^,]+),\s*([^,]+),',old)
            lo,hi,default=map(float,m.groups())
        if typ=='Switch':default=1
        t+=f'a.{n}.v=b.{n}.v={default};\n'
        if name=='hsb' and typ=='Switch':t+=f'a.{n}.v=1-b.{n}.v;\n'
    t+='a.Initialize();b.Initialize();for(int i=0;i<3000;i++){\n'
    for j,(typ,n) in enumerate(controls):
        if typ=='Knob':
            m=re.search(re.escape(n)+r'\.SetRange\(\s*([^,]+),\s*([^,]+),',old);lo,hi=map(float,m.groups())
            t+=f'a.{n}.v=b.{n}.v={lo}+({hi-lo})*((i/250+{j})%5)/4.0;\n'
        elif typ=='Switch':t+=f'a.{n}.v=b.{n}.v=((i/333+{j})%2);\n'
        else:
            t+=f'a.{n}.connected=b.{n}.connected=((mask>>( {j}%7 ))&1)!=0;\n'
            if not n.endswith('Out'):t+=f'a.{n}.v=b.{n}.v=a.{n}.connected?Math.sin(i*0.071+{j})*(i%4==0?12:4):0;\n'
        if name=='hsb' and typ=='Switch':t+=f'a.{n}.v=1-b.{n}.v;\n'
    knobs=[n for typ,n in controls if typ in ('Knob','Switch')]
    readsum='+'.join('b.'+n+'.reads' for n in knobs)
    t+=f'int reads={readsum};boolean bypass=i<20||(i>=1700&&i<2300);if(bypass){{a.ProcessBypassedSample();b.ProcessBypassedSample();if(reads!={readsum})throw new AssertionError("{name} bypass read controls");}}else{{a.ProcessSample();b.ProcessSample();}}\n'
    for typ,n in controls:
        if n.endswith('Out'):t+=f'equal(a.{n}.v,b.{n}.v,"{name} {n} mask="+mask+" frame="+i);checked++;\n'
    # Test tooltip coverage, both switch positions, fallback, and meaningful endpoint descriptions.
    if name!='hsb':
        for typ,n in controls:t+=f'if(b.tooltip(b.{n}).equals("fallback")||b.tooltip(b.{n}).isEmpty())throw new AssertionError("Missing tooltip: {name} {n}");\n'
        t+='if(!b.tooltip(b.dcBlockSwitch).equals(b.dcBlockSwitch.v>0.5?"DC blocking: ON":"DC blocking: OFF"))throw new AssertionError("DC tooltip");\n'
    else:
        for n in ['topTopology','bottomTopology']:t+=f'if(!b.tooltip(b.{n}).startsWith(b.{n}.v==1?"STANDARD":"CUSTOM"))throw new AssertionError("HSB tooltip mapping");\n'
    t+='}}return checked;}\n';tests.append(t)
(out/'Regression.java').write_text(head+''.join(classes)+''.join(tests)+'public static void main(String[] args){\n'+''.join(f'System.out.println("PASS {name}: "+test{name}()+" bit-identical output samples, bypass control inactivity, tooltip coverage");\n' for name in VERSIONS)+'}}',encoding='utf-8')
print('PASS: all audio code tokens match expected baseline (HSB topology inversion only); regression harness generated.')
