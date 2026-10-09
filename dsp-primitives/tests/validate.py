from pathlib import Path
import subprocess,tempfile
PROJECT=Path(__file__).resolve().parents[1]
test=r"""
import com.insectlabs.dsp.filters.HalfBand19;
import com.insectlabs.dsp.interpolation.CubicHermite;
public class FilterChecks {
static void check(boolean c,String s){if(!c)throw new AssertionError(s);}
public static void main(String[] args){
 check(CubicHermite.interpolate(-2, 0, 2, 4, 0) == 0, "cubic start sample");
 check(CubicHermite.interpolate(-2, 0, 2, 4, 1) == 2, "cubic end sample");
 for(int i=0;i<=100;i++){
  double t=i/100.0;
  check(Math.abs(CubicHermite.interpolate(3,3,3,3,t)-3)<1e-12,"cubic constant signal");
  check(Math.abs(CubicHermite.interpolate(-1,0,1,2,t)-t)<1e-12,"cubic linear ramp");
 }
 HalfBand19 f=new HalfBand19();double[] impulse=new double[19];double sum=0;
 for(int n=0;n<19;n++){impulse[n]=f.process(n==0?1:0);sum+=impulse[n];}
 check(Math.abs(sum-1)<1e-12,"unity DC gain");
 for(int n=0;n<19;n++){check(impulse[n]==impulse[18-n],"linear-phase symmetry");if(n%2==1&&n!=9)check(impulse[n]==0,"half-band alternating zero tap");check(Math.abs(impulse[9])>=Math.abs(impulse[n]),"central peak at delay 9");}
 for(int n=0;n<40;n++)f.process(1);check(Math.abs(f.process(1)-1)<1e-12,"steady DC output");f.reset();for(int n=0;n<40;n++)check(f.process(0)==0,"reset silence");
 HalfBand19 fresh=new HalfBand19(),silent=new HalfBand19();
 for(int n=0;n<1000;n++){double x=Math.sin(n*.37);check(Double.doubleToLongBits(f.process(x))==Double.doubleToLongBits(fresh.process(x)),"deterministic reset");check(silent.process(0)==0,"instance independence");}
 System.out.println("PASS: HalfBand19 impulse/symmetry/gain/reset/independence; CubicHermite endpoints, constant and linear interpolation.");
}
}
"""
with tempfile.TemporaryDirectory(prefix='insectlabs-halfband-') as tmp:
 p=Path(tmp)/'FilterChecks.java';p.write_text(test,encoding='utf-8');subprocess.run(['javac','--release','17','-encoding','utf-8','-Xlint:all','-Werror','-d',tmp,*map(str,(PROJECT/'src').rglob('*.java')),str(p)],check=True);subprocess.run(['java','-cp',tmp,'FilterChecks'],check=True)
