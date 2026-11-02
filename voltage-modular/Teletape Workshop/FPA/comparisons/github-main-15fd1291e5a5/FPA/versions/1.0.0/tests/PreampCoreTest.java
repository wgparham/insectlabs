public class PreampCoreTest {
    static int fails=0;
    static void chk(String n, boolean ok, String d){ System.out.println((ok?"PASS ":"FAIL ")+n+" "+d); if(!ok)fails++; }
    static double channelValue(PreampCore p,String field)throws Exception{
        java.lang.reflect.Field channels=PreampCore.class.getDeclaredField("ch");channels.setAccessible(true);
        Object channel=((Object[])channels.get(p))[0];java.lang.reflect.Field value=channel.getClass().getDeclaredField(field);value.setAccessible(true);return value.getDouble(channel);
    }
    static double rms(PreampCore p,int c,double amp,double hz,int n){
        double w=2*Math.PI*hz/48000,a=0; for(int i=0;i<n;i++){double y=p.process(c,amp*Math.sin(w*i)); if(i>=n/2)a+=y*y;} return Math.sqrt(a/(n/2));}
    public static void main(String[] x)throws Exception{
        String[] nm={"C","Re","D","O"};
        for(int m=0;m<4;m++){
            double prev=-1; boolean mono=true,calib=true; StringBuilder sb=new StringBuilder();
            for(int k=0;k<=10;k++){
                double g=k/10.0; PreampCore p=new PreampCore(48000); p.setMode(0,m);p.setGain(0,g);p.setTone(0,0.0);p.reset();
                double r=rms(p,0,1.0,1000,9600);
                double db=20*Math.log10(r/0.70710678);
                if(Math.abs(db-PreampCore.targetRmsDb(g))>0.5)calib=false;
                if(r<prev-1e-9)mono=false; prev=r; sb.append(String.format("%.1f ",db));
            }
            chk(nm[m]+" calibration within 0.5 dB",calib,sb.toString());
            chk(nm[m]+" loudness monotonic in GAIN",mono,"");
            // ceiling
            PreampCore p=new PreampCore(48000);p.setMode(0,m);p.setGain(0,1);p.setTone(0,0);p.reset();
            double pk=0;for(int i=0;i<20000;i++){pk=Math.max(pk,Math.abs(p.process(0,10*Math.sin(i*0.13))));}
            chk(nm[m]+" ceiling <= 2.0",pk<=2.0,"peak="+pk);
            // silence
            p=new PreampCore(48000);p.setMode(0,m);p.setGain(0,0.7);p.reset();
            double mx=0;for(int i=0;i<5000;i++)mx=Math.max(mx,Math.abs(p.process(0,0)));
            chk(nm[m]+" zero in -> zero out",mx==0,"max="+mx);
            // bad input recovery
            p=new PreampCore(48000);p.setMode(0,m);p.setGain(0,0.6);p.reset();
            boolean fin=true;for(int i=0;i<3000;i++){double in=(i%97==0)?Double.NaN:(i%89==0?Double.POSITIVE_INFINITY:Math.sin(i*0.05));double y=p.process(0,in);if(Double.isNaN(y)||Double.isInfinite(y))fin=false;}
            chk(nm[m]+" NaN/Inf inputs stay finite",fin,"");
            // polarity: exact negation after settle
            PreampCore a=new PreampCore(48000),b=new PreampCore(48000);
            for(PreampCore q:new PreampCore[]{a,b}){q.setMode(0,m);q.setGain(0,0.8);q.setTone(0,-0.3);q.reset();}
            b.setPhase(0,true); double e=0;
            for(int i=0;i<6000;i++){double s=Math.sin(i*0.07)*0.8;double ya=a.process(0,s),yb=b.process(0,s);if(i>3000)e=Math.max(e,Math.abs(ya+yb));}
            chk(nm[m]+" polarity = exact negation after settle",e<1e-9,"err="+e);
            // tone sweep sanity
            boolean tf=true;double lowMin=0,lowMax=0,highMin=0,highMax=0;
            for(int k=0;k<=4;k++){p=new PreampCore(48000);p.setMode(0,m);p.setGain(0,0.5);p.setTone(0,-1.0+k*0.5);p.reset();
                double rl=rms(p,0,0.05,200,9600),rh=rms(p,0,0.05,7000,9600);if(!Double.isFinite(rl)||!Double.isFinite(rh))tf=false;if(k==0){lowMin=rl;highMin=rh;}if(k==4){lowMax=rl;highMax=rh;}}
            double lowRatio=lowMin>1e-12?lowMax/lowMin:Double.NaN;
            double highRatio=highMin>1e-12?highMax/highMin:Double.NaN;
            if(m==PreampCore.MODE_O)tf=tf&&highMax>highMin*1.2;
            else tf=tf&&Double.isFinite(lowRatio)&&Double.isFinite(highRatio)&&highRatio>1.1;
            String toneDetail=m==PreampCore.MODE_O?String.format("7kHz endpoint RMS %.3g -> %.3g",highMin,highMax):String.format("200Hz x%.2f 7kHz x%.2f (+1 vs -1)",lowRatio,highRatio);
            chk(nm[m]+" signed tone sweep moves spectrum",tf,toneDetail);
            if(m<3){
                PreampCore centered=new PreampCore(48000);centered.setMode(0,m);centered.setTone(0,0.0);centered.process(0,0.0);
                double lowGain=channelValue(centered,"gLo"),highGain=channelValue(centered,"gHi");
                chk(nm[m]+" tone center is neutral at 0",lowGain==1.0&&highGain==1.0,"low="+lowGain+" high="+highGain);
            } else {
                PreampCore centered=new PreampCore(48000);centered.setMode(0,m);centered.setTone(0,0.0);centered.process(0,0.0);
                double actual=channelValue(centered,"aT1"),expected=1.0-Math.exp(-2.0*Math.PI*Math.sqrt(1500.0*8000.0)/48000.0);
                chk("O tone center uses geometric cutoff midpoint",Math.abs(actual-expected)<1e-15,"error="+Math.abs(actual-expected));
            }
        }
        PreampCore toneBounds = new PreampCore(48000), toneClamped = new PreampCore(48000);
        toneBounds.setMode(0,PreampCore.MODE_C);toneClamped.setMode(0,PreampCore.MODE_C);
        toneBounds.setGain(0,0.5);toneClamped.setGain(0,0.5);
        toneBounds.setTone(0,-5.0);toneClamped.setTone(0,-1.0);toneBounds.reset();toneClamped.reset();
        double clampError=0;for(int n=0;n<4000;n++){double in=0.4*Math.sin(n*0.07);clampError=Math.max(clampError,Math.abs(toneBounds.process(0,in)-toneClamped.process(0,in)));}
        chk("tone clamp accepts signed -1..+1 and bounds negative outlier",clampError<1e-12,"err="+clampError);
        toneBounds=new PreampCore(48000);toneClamped=new PreampCore(48000);
        toneBounds.setMode(0,PreampCore.MODE_C);toneClamped.setMode(0,PreampCore.MODE_C);toneBounds.setGain(0,0.5);toneClamped.setGain(0,0.5);
        toneBounds.setTone(0,5.0);toneClamped.setTone(0,1.0);toneBounds.reset();toneClamped.reset();clampError=0;
        for(int n=0;n<4000;n++){double in=0.4*Math.sin(n*0.07);clampError=Math.max(clampError,Math.abs(toneBounds.process(0,in)-toneClamped.process(0,in)));}
        chk("tone clamp bounds positive outlier",clampError<1e-12,"err="+clampError);
        chk("mix scale",Math.abs(PreampCore.mix(1,1,1,1)-2.0)<1e-12,"");
        // channels independent
        PreampCore p=new PreampCore(48000);p.setMode(1,2);p.setGain(1,0.9);p.reset();
        double mx=0;for(int i=0;i<2000;i++){p.process(1,Math.sin(i*.1));mx=Math.max(mx,Math.abs(p.process(0,0)));}
        chk("channels independent",mx==0,"");
        // timing
        p=new PreampCore(48000);for(int c=0;c<4;c++){p.setMode(c,c);p.setGain(c,0.9);}p.reset();
        double[] signal=new double[1024];for(int i=0;i<signal.length;i++)signal[i]=Math.sin(i*0.01);
        double sink=0;for(int i=0;i<50000;i++)for(int c=0;c<4;c++)sink+=p.process(c,signal[i&(signal.length-1)]);
        int frames=500000;long start=System.nanoTime();
        for(int i=0;i<frames;i++)for(int c=0;c<4;c++)sink+=p.process(c,signal[i&(signal.length-1)]);
        long elapsed=System.nanoTime()-start;double nsPerFrame=elapsed/(double)frames;
        System.out.printf("cost %.0f ns per 4-channel frame (%.2f%% of one 48k core) %f%n",nsPerFrame,nsPerFrame/20833.3333*100.0,sink*0);
        System.out.println(fails==0?"ALL PASS":"FAILS="+fails);
    }
}
