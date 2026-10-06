package com.insectlabs.sherlock;

public final class SherlockChecks {
    private static int checks;
    private static void check(boolean condition, String name) {
        checks++;
        if (!condition) throw new AssertionError(name);
    }
    private static void near(double actual, double expected, double tolerance, String name) {
        check(Math.abs(actual - expected) <= tolerance, name + ": " + actual + " expected " + expected);
    }
    private static sherlock.SherlockCore core(double rate, double seconds, boolean fast) {
        sherlock.SherlockCore c = new sherlock.SherlockCore(rate);
        double knob = sherlock.SherlockCore.knobForTime(seconds,fast);
        c.setControls(knob,knob,0,0,fast,fast,false);
        return c;
    }
    private static void ordinary(double rate) {
        sherlock.SherlockCore c=core(rate,1,true);
        for(int i=0;i<(int)(rate*.25);i++) c.process(10,-10,0,0,0,0);
        near(c.positive,1.25,1e-9,"positive quarter-second ramp");
        near(c.negative,-1.25,1e-9,"negative quarter-second ramp");
        c.process(-3,7,0,0,0,0);
        near(c.positive,-3,0,"positive unrestricted falling edge");
        near(c.negative,7,0,"negative unrestricted rising edge");
        c.process(1,-2,0,0,0,0);
        near(c.positive,-3+5/rate,1e-12,"positive slew below zero");
        near(c.negative,7-5/rate,1e-12,"negative slew above zero");
        c=core(rate,.0001,true);
        for(int i=0;i<100;i++) c.process(1.234,-2.345,0,0,0,0);
        near(c.positive,1.234,0,"settles exactly without overshoot");
        near(c.negative,-2.345,0,"settles exactly without undershoot");
        c=core(rate,.01,true);
        for(int i=0;i<2000;i++) c.process(8,0,0,0,0,0);
        near(c.positive,8,0,"8V external signal preserved in 5V mode");
        near(c.negative,0,0,"no normal between halves");
        c=core(rate,1,true);
        for(int i=0;i<(int)(rate*2);i++) c.process(10,-10,0,0,0,0);
        near(c.positive,10,2e-8,"10V traversal takes 2s");
        near(c.negative,-10,2e-8,"negative 10V traversal takes 2s");
    }
    private static void rangesAndCv(double rate) {
        for(int i=0;i<=100;i++) {
            double k=i/100.0;
            near(sherlock.SherlockCore.timeFor(k,false)/sherlock.SherlockCore.timeFor(k,true),1000,1e-10,"exact magnitude switch");
            near(sherlock.SherlockCore.knobForTime(sherlock.SherlockCore.timeFor(k,true),true),k,1e-14,"typed time inverse");
            sherlock.SherlockCore.RateControl a=new sherlock.SherlockCore.RateControl(rate);
            sherlock.SherlockCore.RateControl b=new sherlock.SherlockCore.RateControl(rate);
            a.set(k,.4);b.set(k,.4);a.snap();b.snap();
            near(a.step(2,true)/b.step(2,false),1000,1e-10,"range factor with CV");
        }
        near(sherlock.SherlockCore.timeFor(0,true),4,1e-12,"FAST slow end");
        near(sherlock.SherlockCore.timeFor(1,true),.0001,1e-12,"FAST fast end");
        near(sherlock.SherlockCore.timeFor(0,false),4000,1e-9,"SLOW slow end");
        near(sherlock.SherlockCore.timeFor(1,false),.1,1e-12,"SLOW fast end");
        sherlock.SherlockCore.RateControl a=new sherlock.SherlockCore.RateControl(rate);
        a.set(.5,1);a.snap();double base=a.step(0,true);
        near(a.step(1,true),2*base,1e-12,"+1V doubles rate");
        near(a.step(-1,true),.5*base,1e-12,"-1V halves rate");
        a.set(.5,-1);a.snap();near(a.step(1,true),.5*base,1e-12,"negative attenuverter reverses CV");
        a.set(.5,0);a.snap();near(a.step(999,true),base,1e-12,"zero attenuverter blocks CV");
        a.set(1,1);a.snap();near(a.step(999,true),50000/rate,1e-10,"fast cap");
        a.set(0,1);a.snap();near(a.step(-999,false),.00125/rate,1e-12,"slow cap");
    }
    private static int riseLength(double rate, boolean ten, boolean retriggers) {
        sherlock.SherlockCore c=core(rate,.01,true);
        double k=sherlock.SherlockCore.knobForTime(.01,true);
        c.setControls(k,k,0,0,true,true,ten);
        int count=0;
        do {
            c.process(0,0,0,0,count==0 || (retriggers && count%10==0)?5:0,0);
            count++;
            check(count<rate,"rise must terminate");
        } while(c.positivePhase!=sherlock.SherlockCore.AT_PEAK);
        near(c.positive,ten?10:5,0,"generated exact peak");
        c.process(0,0,0,0,0,0);
        near(c.positive,0,0,"hard reset after peak");
        near(c.positiveHigh,0,0,"HI low on reset");
        near(c.positiveLow,5,0,"LO high on reset");
        return count;
    }
    private static void triggering(double rate) {
        int five=riseLength(rate,false,false);
        int ten=riseLength(rate,true,false);
        // Each endpoint can round up by one native sample; doubling that rounding permits two.
        check(Math.abs(ten-2*five)<=2,"10V doubles generated rise time: "+five+" / "+ten);
        check(riseLength(rate,false,true)==five,"busy START ignored");
        sherlock.SherlockCore c=core(rate,.01,true);
        for(int i=0;i<(int)(rate*.1);i++) c.process(0,0,0,0,0,5);
        near(c.positive,5,0,"SUSTAIN starts and holds");near(c.positiveHigh,5,0,"HI stays high during sustain");
        near(c.positiveLow,0,0,"LO is voltage detector at peak");
        double holdKnob=sherlock.SherlockCore.knobForTime(.01,true);
        c.setControls(holdKnob,holdKnob,0,0,true,true,true);
        c.process(0,0,0,0,0,5);
        near(c.positive,5+500/rate,1e-12,"5-to-10V switch while held obeys positive slew");
        for(int i=0;i<(int)(rate*.02);i++)c.process(0,0,0,0,0,5);
        near(c.positive,10,0,"held contour reaches new 10V peak");
        c.process(0,0,0,0,0,0);near(c.positive,0,0,"release resets");
        c=core(rate,.01,true);c.process(0,0,0,0,0,5);
        for(int i=1;i<50;i++)c.process(0,0,0,0,0,0);
        check(c.positive>0 && c.positivePhase==sherlock.SherlockCore.RISING,"short sustain still completes rise");
        c=core(rate,.0001,true);for(int i=0;i<100;i++) c.process(5,5,0,0,0,0);
        near(c.positiveHigh,0,0);near(c.positiveLow,0,0,"HI and LO are not complements");
        c.process(-2,-2,0,0,0,0);near(c.positiveLow,5,0,"negative voltage asserts positive LO");
        for(int i=0;i<20;i++)c.process(-2,-2,0,0,0,0);
        near(c.negativePulse,5,0,"negative voltage asserts negative PULSE");
    }
    private static void near(double a,double b,double t) {near(a,b,t,"voltage");}
    private static double loop(double rate, boolean positiveSection, boolean ten, int cableDelay) {
        sherlock.SherlockCore c=new sherlock.SherlockCore(rate);
        c.setControls(sherlock.SherlockCore.defaultKnob(true,rate),sherlock.SherlockCore.defaultKnob(false,rate),0,0,true,false,ten);
        double[] cable=new double[cableDelay];int slot=0,events=0,first=0,last=0;boolean wasHigh=false;
        for(int i=0;i<(int)(rate*2);i++) {
            double feedback=cable[slot];
            c.process(0,positiveSection?0:feedback,0,0,positiveSection?feedback:0,0);
            double pulse=positiveSection?c.positiveLow:c.negativePulse;
            cable[slot]=pulse;slot=(slot+1)%cableDelay;
            if(pulse>0 && !wasHigh) {if(events==1)first=i;last=i;events++;}
            wasHigh=pulse>0;
        }
        check(events>5,"feedback loop runs");
        double hz=(events-2)*rate/(last-first);
        if(!ten && cableDelay==1) {
            double target=positiveSection?261.6255653005986:8.175798915643707;
            check(Math.abs(1200*Math.log(hz/target)/Math.log(2))<6,"near C default");
        }
        System.out.println((positiveSection?"Positive":"Negative")+" loop @ "+rate+" Hz, "+(ten?10:5)+"V switch, delay "+cableDelay+": "+hz+" Hz");
        return hz;
    }
    private static void limits(double rate) {
        sherlock.SherlockCore c=core(rate,.01,true);
        for(int i=0;i<10000;i++) {
            double v=i%3==0?Double.NaN:(i%3==1?Double.POSITIVE_INFINITY:-1e300);
            c.setControls(v,v,v,v,true,false,false);
            c.process(v,v,v,v,v,v);
            check(Double.isFinite(c.positive)&&Double.isFinite(c.negative),"finite hostile values");
        }
        c=core(rate,.01,true);c.process(0,0,0,0,5,0);
        c.resumePending=true;c.process(0,0,0,0,0,0);
        near(c.positive,0,0,"resume discards stale contour");
        near(c.negative,0,0,"resume independent zero state");
    }
    private static void longSlew() {
        sherlock.SherlockCore c=core(48000,4000,false);
        int samples=192000000;
        for(int i=0;i<samples;i++)c.process(5,-5,0,0,0,0);
        near(c.positive,5,3e-8,"full 4000-second positive ramp");
        near(c.negative,-5,3e-8,"full 4000-second negative ramp");
        System.out.println("PASS: full 4000-second ramps at 48 kHz.");
    }
    public static void main(String[] args) {
        for(double rate:new double[]{48000,96000}) {
            ordinary(rate);rangesAndCv(rate);triggering(rate);limits(rate);
            double hz5=loop(rate,true,false,1),hz10=loop(rate,true,true,1);
            check(Math.abs(hz5/hz10-2)<.015,"10V loop approximately octave below");
            loop(rate,false,false,1);loop(rate,false,true,1);
            loop(rate,true,false,2);loop(rate,false,false,2);
        }
        if(args.length>0 && args[0].equals("long"))longSlew();
        System.out.println("PASS: "+checks+" Sherlock numerical checks.");
    }
}
