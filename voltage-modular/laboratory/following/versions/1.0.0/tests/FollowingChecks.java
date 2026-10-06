package com.insectlabs.following;

import java.util.Arrays;

public final class FollowingChecks {
    static int assertions;
    static void check(boolean ok, String name) {
        ++assertions;
        if (!ok) throw new AssertionError(name);
    }
    static void near(double actual, double expected, double error, String name) {
        check(Double.isFinite(actual) && Math.abs(actual-expected)<=error,
                name+": got "+actual+", expected "+expected);
    }
    static following.FollowingCore core(double rate, double mix, double seconds) {
        following.FollowingCore c = new following.FollowingCore(rate);
        c.setControls(.5, .0, .0, .5, Math.log1p(seconds*1000)/Math.log(3001), mix, false);
        c.process(0,true);
        for(int i=0;i<rate/10;i++) c.process(0,true);
        return c;
    }
    static void testDelay(double rate, double samples) {
        following.FollowingCore c = new following.FollowingCore(rate);
        c.delaySamples = samples;
        int whole = (int)samples;
        for(int i=0;i<(int)(rate*3)+20;i++) {
            double expected = i==whole ? 1-(samples-whole) : (i==whole+1 ? samples-whole : 0);
            near(c.delayEnvelope(i==0?1:0), expected, 1e-12, "delay impulse "+samples+" sample "+i);
        }
    }
    static void mainChecks(double rate) {
        near(following.FollowingCore.gainFor(.5),1,0,"unity at noon");
        near(following.FollowingCore.gainFor(1),4,0,"maximum gain");
        near(following.FollowingCore.filterFor(0),20,0,"HPF minimum");
        near(following.FollowingCore.filterFor(1),200,1e-12,"HPF maximum");
        near(following.FollowingCore.attackFor(1),1,1e-12,"attack maximum");
        near(following.FollowingCore.delayFor(1),3,1e-12,"delay maximum");
        near(following.FollowingCore.thresholdFor(1),5,1e-12,"threshold maximum");
        for(double delay:new double[]{0,1,2.5,rate*3}) testDelay(rate,delay);

        following.FollowingCore direct=core(rate,0,.1), mixed=core(rate,.5,.1), wet=core(rate,1,.1);
        int offset=(int)(rate*.1), count=(int)(rate*.8);
        double[] envelope=new double[count], gate=new double[count];
        for(int i=0;i<count;i++) {
            double in=i<rate*.2 ? 4*Math.sin(2*Math.PI*440*i/rate) : 0;
            direct.process(in,true); mixed.process(in,true); wet.process(in,true);
            envelope[i]=direct.positive; gate[i]=direct.immediateGate;
            near(wet.positive,i>=offset?envelope[i-offset]:0,1e-9,"whole envelope shifted");
            near(wet.delayedGate,i>=offset?gate[i-offset]:0,0,"whole gate shifted");
            near(mixed.positive,(direct.positive+wet.positive)*.5,1e-12,"linear balance");
            near(mixed.delayed,wet.delayed,1e-12,"courtesy independent");
            near(mixed.immediateGate,direct.immediateGate,0,"gate independent of balance");
            near(mixed.negative,-mixed.positive,0,"exact inversion");
            near(direct.through,wet.through,0,"audio independent of balance");
        }
        following.FollowingCore fast=core(rate,0,0), slow=core(rate,0,0);
        fast.attack=fast.quickAttack; slow.attack=slow.coefficient(1);
        for(int i=0;i<rate*.02;i++){fast.followEnvelope(3);slow.followEnvelope(3);}
        check(fast.envelope>2.99 && slow.envelope<.07,"manual attack span");

        following.FollowingCore release=core(rate,0,0);
        for(int i=0;i<rate;i++)release.followEnvelope(3);
        double start=release.envelope;
        for(int i=0;i<rate*.01;i++)release.followEnvelope(0);
        double earlyRatio=release.envelope/start;
        for(int i=0;i<rate*.3;i++)release.followEnvelope(0);
        double late=release.envelope;
        for(int i=0;i<rate*.01;i++)release.followEnvelope(0);
        double lateRatio=release.envelope/late;
        check(lateRatio>earlyRatio,"release eases into slower tail");
        following.FollowingCore brief=core(rate,0,0), sustained=core(rate,0,0);
        for(int i=0;i<rate*.01;i++) brief.followEnvelope(3);
        for(int i=0;i<rate;i++) sustained.followEnvelope(3);
        double bs=brief.envelope, ss=sustained.envelope;
        for(int i=0;i<rate*.02;i++){brief.followEnvelope(0);sustained.followEnvelope(0);}
        check(sustained.envelope/ss>brief.envelope/bs,"duration affects release");

        following.FollowingCore driven=core(rate,0,.02);
        driven.setControls(1,0,0,.5,0,0,true);
        double peak=0;
        for(int i=0;i<rate;i++){
            driven.process(5*Math.sin(2*Math.PI*220*i/rate),true);
            peak=Math.max(peak,Math.abs(driven.through));
            check(driven.positive>=0 && driven.positive<=5,"CV ceiling");
        }
        check(peak>6 && peak<8.5,"strong, bounded driven audio");
        for(int i=0;i<rate*5;i++)driven.process(2,true);
        check(driven.envelope<.001,"detector rejects DC");
        check(driven.through>3.5,"DC through is post gain and character, not HPF");

        following.FollowingCore bypass=core(rate,1,3);
        for(int i=0;i<rate*.1;i++)bypass.process(Math.sin(i*.1)*4,true);
        int index=bypass.writeIndex, hash=Arrays.hashCode(bypass.delayLine);
        double state=bypass.envelope, tone=bypass.toneState;
        for(int i=0;i<1000;i++){
            double input=(i-500)*.035;
            bypass.processBypassed(input);
            near(bypass.through,input,0,"sample exact bypass");
            near(bypass.positive+bypass.negative+bypass.delayed+bypass.immediateGate+bypass.delayedGate,0,0,"CV silent in bypass");
        }
        check(index==bypass.writeIndex && hash==Arrays.hashCode(bypass.delayLine)
                && state==bypass.envelope && tone==bypass.toneState,"bypass histories frozen");
        bypass.process(0,true);
        check(bypass.envelope==0 && bypass.delayed==0 && bypass.validSamples==1,"resume clears stale detector/delay history");
        for(int i=0;i<rate*.02;i++)bypass.process(3,false);
        check(bypass.powerGain==0 && bypass.through==0 && bypass.delayed==0 && bypass.validSamples==0,"power off fully silent/reset");
        check(!following.FollowingCore.gateState(false,0,0),"zero threshold does not gate silence");
        check(following.FollowingCore.gateState(false,1,1),"gate opens at threshold");
        check(following.FollowingCore.gateState(true,.99,1),"hysteresis holds");
        check(!following.FollowingCore.gateState(true,.97,1),"hysteresis releases");
        check(following.FollowingCore.gateState(false,5,5),"maximum threshold reachable");
        check(following.FollowingCore.gateState(false,5,following.FollowingCore.thresholdFor(1)),"mapped maximum threshold reachable");
        following.FollowingCore lowFilter=core(rate,0,0), highFilter=core(rate,0,0);
        highFilter.setControls(.5,1,0,.5,0,0,false);
        for(int i=0;i<rate*.2;i++) {
            double input=3*Math.sin(2*Math.PI*50*i/rate);
            lowFilter.process(input,true); highFilter.process(input,true);
            near(lowFilter.through,highFilter.through,0,"detector HPF does not change through audio");
        }
        check(highFilter.envelope<lowFilter.envelope,"detector HPF actually changes envelope");
        following.FollowingCore edge=core(rate,1,3);
        for(int i=0;i<10000;i++){
            double knob=(i%1000)/999.0;
            edge.setControls(knob,1-knob,knob,knob,knob,1-knob,(i&1)==0);
            edge.process(i%3==0?Double.NaN:(i%3==1?1e100:-1e100),true);
            check(Double.isFinite(edge.through) && Math.abs(edge.through)<=8.5 && edge.positive>=0 && edge.positive<=5,"extreme controls and signals finite");
        }
        System.out.println("PASS at "+rate+" Hz; early release ratio="+earlyRatio+", tail ratio="+lateRatio+", driven peak="+peak);
    }
    public static void main(String[] args) {
        mainChecks(48000); mainChecks(96000);
        System.out.println("PASS: "+assertions+" numerical assertions on actual FollowingCore.");
    }
}
