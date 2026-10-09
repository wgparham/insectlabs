import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/** Headless numerical checks for the private one-pole core in xl-35c. */
public final class FilterCoreTest {
    private static final double SAMPLE_RATE = 48000.0;
    private static final double[] UPPER = {5.3, 41.0, 159.0, 800.0, 1591.0, 3000.0, 5300.0, 16000.0};
    private static final double[] LOWER = {16.0, 70.0, 100.0, 250.0, 482.0, 1000.0, 2000.0, 7500.0};
    private static double measure(Constructor<?> ctor, Method set, Method process, int step, double[] table, double hz) throws Exception {
        Object f=ctor.newInstance(); set.invoke(f,step,table); int warm=(int)(SAMPLE_RATE*2), count=(int)SAMPLE_RATE; double sum=0;
        for(int i=0;i<warm+count;i++){double y=(double)process.invoke(f,Math.sin(2*Math.PI*hz*i/SAMPLE_RATE));if(!Double.isFinite(y))throw new AssertionError("non-finite output");if(i>=warm)sum+=y*y;}
        return Math.sqrt(2*sum/count);
    }
    private static void test(String module,String sectionName,boolean hpf) throws Exception {
        Class<?> type=Class.forName(module+"$"+sectionName); Constructor<?> ctor=type.getDeclaredConstructor();ctor.setAccessible(true);
        Method set=type.getDeclaredMethod("setCutoff",int.class,double[].class);set.setAccessible(true);Method process=type.getDeclaredMethod("process",double.class);process.setAccessible(true);
        double worst=0;
        for(double[] table:new double[][]{UPPER,LOWER})for(int i=0;i<table.length;i++){double g=measure(ctor,set,process,i,table,table[i]);double e=Math.abs(g-Math.sqrt(.5));worst=Math.max(worst,e);if(e>.02)throw new AssertionError(module+" cutoff "+table[i]+" Hz: "+g);}
        for(double fc:new double[]{5.3,16,482,7500,16000}){Object f=ctor.newInstance();set.invoke(f,0,new double[]{fc});double y=0;for(int i=0;i<(int)SAMPLE_RATE;i++)y=(double)process.invoke(f,1.0);if(Math.abs(y-(hpf?0:1))>1e-7)throw new AssertionError(module+" DC "+fc+": "+y);}
        Method warmth=Class.forName(module).getDeclaredMethod("warmMakeupStage",double.class);warmth.setAccessible(true);double p=(double)warmth.invoke(null,1.0),n=(double)warmth.invoke(null,-1.0);if(!(p>0&&p<1&&Math.abs(p+n)<1e-12))throw new AssertionError(module+" upper-stage drive");
        Method nearest=Class.forName(module).getDeclaredMethod("nearestStep",double[].class,double.class);nearest.setAccessible(true);for(double[] table:new double[][]{UPPER,LOWER}){for(int i=0;i<table.length;i++){double step=(double)nearest.invoke(null,table,table[i]);if(step!=i+1)throw new AssertionError(module+" typed cutoff step "+table[i]+": "+step);}if((double)nearest.invoke(null,table,table[0]/100)!=1.0||(double)nearest.invoke(null,table,table[7]*100)!=8.0)throw new AssertionError(module+" typed cutoff clamp");}
        double rejected=measure(ctor,set,process,0,new double[]{6000},hpf?100:18000);double edge=measure(ctor,set,process,0,new double[]{6000},6000);if(!(rejected<edge))throw new AssertionError(module+" out-of-band response");
        Method reset=type.getDeclaredMethod("reset");reset.setAccessible(true);Object old=ctor.newInstance();set.invoke(old,0,new double[]{1200});for(int i=0;i<24000;i++)process.invoke(old,Math.sin(2*Math.PI*321*i/SAMPLE_RATE));reset.invoke(old);Object fresh=ctor.newInstance();set.invoke(fresh,0,new double[]{1200});for(int i=0;i<16;i++){double input=Math.sin(2*Math.PI*77*i/SAMPLE_RATE);double a=(double)process.invoke(old,input),b=(double)process.invoke(fresh,input);if(Math.abs(a-b)>1e-12)throw new AssertionError(module+" bypass-resume reset");}
        System.out.printf("PASS %s: 16 cutoff steps, DC response, band rejection, bypass-resume reset; max -3 dB error %.5f%n",module,worst);
    }
    public static void main(String[] args) throws Exception {test("com.insectlabs.lowpassfilters.XL35c","LowPassSection",false);}
}
