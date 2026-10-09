from pathlib import Path
import re, textwrap, subprocess, tempfile
ROOT = Path(__file__).resolve().parents[2]
PROJECT = Path(__file__).resolve().parents[1]
reference = ROOT / 'voltage-modular/Wardenclyffe Station/lm-21/versions/1.0.1/references/lm-21_mk3_reverbsc_prototype.java.txt'
source = reference.read_text(encoding='utf-8-sig')
start = source.index('    private static final class ReverbSCMono')
opening = source.index('{', start); depth = 1; end = opening + 1
while depth:
    depth += (source[end] == '{') - (source[end] == '}'); end += 1
core = textwrap.dedent(source[start:end])
test = r"""
import com.insectlabs.dsp.reverb.*;
public class ReverbChecks {
static final double SAMPLE_RATE=48000;
REFERENCE
static void check(boolean ok,String label){if(!ok)throw new AssertionError(label);}
public static void main(String[] args){
for(double[] p:new double[][]{{.7,.2,.4,0},{.95,.7,1,0},{.984,.85,2,1}}){
 ReverbSCMono original=new ReverbSCMono();ReverbScMono extracted=new ReverbScMono();
 for(int n=0;n<480000;n++){
  double input=n<2048?Math.sin(n*.143)*3:0;
  double a=original.process(input,p[0],p[1],p[2],p[3]);
  double b=extracted.process(input,p[0],p[1],p[2],p[3]);
  check(Double.doubleToLongBits(a)==Double.doubleToLongBits(b),"48k parity sample "+n);
 }
 extracted.reset();for(int n=0;n<96000;n++)check(extracted.process(0,p[0],p[1],p[2],p[3])==0,"reset silence");
}
for(double sr:new double[]{44100,48000,96000}){
 ReverbScMono a=new ReverbScMono(sr),b=new ReverbScMono(sr);double peak=0;
 double feedback=ReverbParameters.feedbackForRt60(3),damp=ReverbParameters.dampingForCutoff(8000,sr);
 for(int n=0;n<(int)(sr*2);n++){double v=a.process(n==0?5:0,feedback,damp,.4,0);check(Double.isFinite(v)&&Math.abs(v)<100,"sample-rate stability");peak=Math.max(peak,Math.abs(v));check(b.process(0,feedback,damp,.4,0)==0,"instance independence");}
 check(peak>0,"impulse response nonzero");
 a.reset();ReverbScMono fresh=new ReverbScMono(sr);
 for(int n=0;n<8192;n++){double x=n==0?1:0;check(Double.doubleToLongBits(a.process(x,feedback,damp,.4,0))==Double.doubleToLongBits(fresh.process(x,feedback,damp,.4,0)),"deterministic reset");}
}
check(ReverbParameters.feedbackForRt60(.5)>0 && ReverbParameters.feedbackForRt60(30)<1,"feedback bounds");
check(ReverbParameters.feedbackForRt60(.5)<ReverbParameters.feedbackForRt60(30),"decay feedback monotonic");
check(ReverbParameters.dampingForCutoff(40,48000)>ReverbParameters.dampingForCutoff(10000,48000),"damping cutoff monotonic");
check(ReverbParameters.dampingForCutoff(-1,48000)==ReverbParameters.dampingForCutoff(40,48000),"minimum cutoff clamp");
for(double sr:new double[]{0,Double.NaN,Double.POSITIVE_INFINITY,200000}){
 try{new ReverbScMono(sr);throw new AssertionError("invalid sample rate accepted");}catch(IllegalArgumentException expected){}
}
System.out.println("PASS: Java 17 warning-clean builds; 1,440,000 sample-exact reference comparisons; reset, independence, parameter mappings and 44.1/48/96 kHz stability.");
}
}
""".replace('REFERENCE', core)
with tempfile.TemporaryDirectory(prefix='insectlabs-reverb-') as tmp:
    build = Path(tmp); path = build/'ReverbChecks.java'; path.write_text(test, encoding='utf-8')
    sources=list((PROJECT/'src').rglob('*.java'))
    subprocess.run(['javac','--release','17','-encoding','utf-8','-Xlint:all','-Werror','-d',str(build),*map(str,sources),str(path)],check=True)
    subprocess.run(['java','-cp',str(build),'ReverbChecks'],check=True)
