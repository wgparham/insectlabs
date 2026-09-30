from pathlib import Path
import re, subprocess, tempfile, sys

repo = Path(__file__).resolve().parents[4]
source = (repo / 'voltage-modular/laboratory/sw2/versions/1.0.0/sw2.java').read_text(encoding='utf-8-sig')
sdk = r'C:\ProgramData\Voltage\voltage.jar'
# Bypass must not read controls or process/reset audio state in its callback.
bypass = source.split('//[user-ProcessBypassedSample]',1)[1].split('//[/user-ProcessBypassedSample]',1)[0]
assert 'selectedPosition()' not in bypass and 'GetValue()' not in bypass
assert 'clickFilterEnabled()' not in bypass
assert 'cachedPosition' in bypass
assert not re.search(r'\b(?:knob|inputJack|outputJack|textLabel|switch)\d+\b',source)

body = source.split('//[user-code-and-variables]', 1)[1].split('//[/user-code-and-variables]', 1)[0]
body = body[body.index('    private static final'):]
for name in ('readInput', 'selectedPosition', 'clickFilterEnabled'):
    body, n = re.subn(r'    private (?:static )?(?:double|int|boolean) '+name+r'\([^)]*\) \{.*?\n    \}', '', body, flags=re.S)
    assert n == 1
test = '''
    private static int checks;
    private static void near(double a, double b, double tolerance) {
        checks++;
        if (Math.abs(a-b)>tolerance) throw new AssertionError(a + " != " + b);
    }
    public static void main(String[] args) {
        for (double v : new double[]{-3,-2,-1,0,1,2,3}) near(amplifierCurve(v), v, 0);
        near(amplifierCurve(5),4.88,1e-12);
        near(amplifierCurve(-5),-4.868,1e-12);
        near(amplifierCurve(10),8.416923076923077,1e-12);
        double last = amplifierCurve(-30);
        for (int i=1;i<=6000;i++) {
            double v=-30+i*.01;
            double shaped=amplifierCurve(v);
            if (shaped < last || Math.abs(shaped)>Math.abs(v)+1e-10) throw new AssertionError("curve");
            last=shaped;
        }
        for (boolean clk : new boolean[]{false,true}) {
            Sw2Harness h=new Sw2Harness();
            h.resetRouting();
            for (int route : new int[]{0,1,2,3,4,0,4,1,0}) {
                for (int i=0;i<4096;i++) h.processRouting(route,.5,1,1.5,2,2.5,clk);
                near(h.selectedOutput, route * .5, 1e-12);
                for (int j=0;j<4;j++) near(h.destinationOutputs[j],j+1==route?2.5:0,1e-12);
            }
            h.processBypassedRouting(3,1,2,20,4,-20);
            near(h.selectedOutput,20,0);
            near(h.destinationOutputs[2],-20,0);
            h.processRouting(3,1,2,3,4,5,clk);
            near(h.selectedOutput,3,0);
            near(h.destinationOutputs[2],4.88,1e-12);
        }
        Sw2Harness rapid = new Sw2Harness();
        rapid.processRouting(1,1,2,3,4,2,true);
        for (int i=0;i<1000;i++) {
            double[] previous = rapid.routeWeights.clone();
            rapid.processRouting(i % 5,1,2,3,4,2,true);
            double sum=0;
            for (int j=0;j<4;j++) {
                if (Math.abs(rapid.routeWeights[j]-previous[j])>1.0/96+1e-12)
                    throw new AssertionError("Retarget discontinuity");
                sum+=rapid.routeWeights[j];
            }
            if (sum < -1e-12 || sum > 1+1e-12) throw new AssertionError("Route gain");
        }
        for (int i=0;i<96;i++) rapid.processRouting(4,1,2,3,4,2,true);
        near(rapid.routeWeights[3],1,0);
        for(int i=0;i<3;i++) near(rapid.routeWeights[i],0,0);
        double[] amp=rapid.amplifierState.clone();
        double[] contacts=rapid.destinationFilterStates.clone();
        double[] weights=rapid.routeWeights.clone();
        double selectedFilter=rapid.selectedFilterState;
        for(int n=0;n<100;n++) rapid.processBypassedRouting(n%5,1,2,3,4,5);
        for(int i=0;i<2;i++) near(rapid.amplifierState[i],amp[i],0);
        for(int i=0;i<4;i++) {
            near(rapid.destinationFilterStates[i],contacts[i],0);
            near(rapid.routeWeights[i],weights[i],0);
        }
        near(rapid.selectedFilterState,selectedFilter,0);
        rapid.processRouting(2,1,2,3,4,2.5,true);
        near(rapid.selectedOutput,2,0);
        near(rapid.destinationOutputs[1],2.5,0);
        Sw2Harness a=new Sw2Harness();
        for (int i=0;i<48000;i++) {
            double x=10*Math.sin(2*Math.PI*997*i/48000.0);
            a.processRouting(2,0,x,0,0,x,false);
            near(a.selectedOutput,a.destinationOutputs[1],0);
            near(a.destinationOutputs[0],0,0);
            near(a.destinationOutputs[2],0,0);
            near(a.destinationOutputs[3],0,0);
        }
        System.out.println("PASS routing, OFF, bank matching/isolation, DC, bypass/resume, monotonic compression: " + checks + " checks");
        System.out.println("Peak +10 V -> " + amplifierCurve(10) + "; -10 V -> " + amplifierCurve(-10));
    }
}
'''
with tempfile.TemporaryDirectory(prefix='sw2-character-') as tmp:
    d=Path(tmp)
    (d/'sw2.java').write_text(source,encoding='utf-8')
    subprocess.run(['javac','--release','17','-Xlint:all','-Werror','-cp',sdk,'-d',str(d),str(d/'sw2.java')],check=True)
    print('PASS exported source SDK compilation')
    sys.path.insert(0,str(repo/'voltage-modular/colorbox/tools'))
    from value_tree import Reader, props
    tree=Reader((repo/'voltage-modular/laboratory/sw2/versions/1.0.0/sw2.vmod').read_bytes()).tree()
    code=next(n for n in tree[2] if n[0]=='module code')
    embedded=props(code)['MODULE.SOURCE']
    from support import normalized
    canonicalize=lambda t: normalized('\n'.join(line.lstrip() for line in t.splitlines()))
    assert canonicalize(embedded)==canonicalize(source)
    for control in next(n for n in tree[2] if n[0]=='controls')[2]:
        if props(control).get('CEditableControl.IsBackgroundObject') and props(control)['Control Type'] == 31:
            continue  # Painted panel traces have no generated Java component.
        name=props(control)['variable name']
        assert re.search(r'\b'+name+r'\s*=\s*new ',source),name
    for line in code[2][0][2]:
        index=props(line)['line number']
        assert 0 <= index < len(embedded.splitlines())
        for item in line[2]:
            ident=props(item).get('ID','')
            if ident.endswith('.signature') or ident.endswith('.Signature'):
                assert 'public ' in embedded.splitlines()[index],ident
            if ident=='class closing bracket':assert embedded.splitlines()[index].strip()=='}'
    route=next(c for c in next(n for n in tree[2] if n[0]=='controls')[2] if props(c)['variable name']=='routeKnob')
    assert props(route)['defaultValueString']=='0' and props(route)['defaultValue']==0.0
    assert 'routeKnob.SetRange( 0, 4, 0, false, 5 );' in source
    print('PASS source equivalence, descriptive control IDs and Designer method anchors')
    (d/'sw2.java').write_text(embedded,encoding='utf-8')
    subprocess.run(['javac','--release','17','-Xlint:all','-Werror','-cp',sdk,'-d',str(d),str(d/'sw2.java')],check=True)
    print('PASS embedded source SDK compilation')
    (d/'Sw2Harness.java').write_text('public class Sw2Harness {\n'+body+test,encoding='utf-8')
    subprocess.run(['javac','-d',str(d),str(d/'Sw2Harness.java')],check=True)
    subprocess.run(['java','-cp',str(d),'Sw2Harness'],check=True)
