"""Exercise exported host callback bodies using spies, without native Designer objects."""
import re,subprocess

def run(source, folder, sdk, flags):
    def region(name):
        return re.search(r'//\[user-'+name+r'\][^\n]*\n(.*?)//\[/user-'+name+r'\]',source,re.S)[1]
    helpers=source.split('    private final FollowingCore',1)[1].split('    // Independent of host objects',1)[0]
    helpers=('    private final FollowingCore'+helpers).replace('FollowingCore','following.FollowingCore')
    code='''package com.insectlabs.following;
public class FollowingRoutingChecks {
    static class VoltageComponent {}
    static class Control extends VoltageComponent { void SetValue(double v){value=v;} double value; int reads; double GetValue(){++reads;return value;} }
    static class VoltageAudioJack extends Control { void SetValue(double v){value=v;} }
    static class Lamp { double value; int writes; void SetValue(double v){value=v;++writes;} }
    final Control powerSwitch=new Control(), attackModeSwitch=new Control(), amplitudeKnob=new Control(),
        filterKnob=new Control(), attackKnob=new Control(), thresholdKnob=new Control(),
        delayKnob=new Control(), balanceKnob=new Control();
    final VoltageAudioJack signalInput=new VoltageAudioJack(), signalThroughOutput=new VoltageAudioJack(),
        positiveEnvelopeOutput=new VoltageAudioJack(), negativeEnvelopeOutput=new VoltageAudioJack(),
        gateOutput=new VoltageAudioJack(), delayedEnvelopeOutput=new VoltageAudioJack(), delayedGateOutput=new VoltageAudioJack();
    final Lamp powerLed=new Lamp(), gateLed=new Lamp(), delayedGateLed=new Lamp();
'''+helpers
    for name in ('Initialize','ProcessSample','ProcessBypassedSample'):
        code+='\nvoid '+name+'(){\n'+region(name)+'\n}\n'
    code+='\nvoid EditComponentValue(VoltageComponent component,double newValue,String newText){\n'+region('EditComponentValue')+'\n}\n'
    code+='''
    long trace=0xcbf29ce484222325L;
    void record(){
        double[] values={signalThroughOutput.value,positiveEnvelopeOutput.value,negativeEnvelopeOutput.value,
            gateOutput.value,delayedEnvelopeOutput.value,delayedGateOutput.value,gateLed.value,delayedGateLed.value};
        for(double v:values){trace^=Double.doubleToLongBits(v);trace*=0x100000001b3L;}
    }
    static void check(boolean value,String message){if(!value)throw new AssertionError(message);}
    int controlReads(){return powerSwitch.reads+attackModeSwitch.reads+amplitudeKnob.reads+filterKnob.reads
        +attackKnob.reads+thresholdKnob.reads+delayKnob.reads+balanceKnob.reads;}
    void lampsMatch(){
        check(gateLed.value==(gateOutput.value>0?1:0),"G lamp tracks G");
        check(delayedGateLed.value==(delayedGateOutput.value>0?1:0),"delayed lamp tracks delayed gate");
    }
    public static void main(String[] args){
        FollowingRoutingChecks f=new FollowingRoutingChecks();
        f.Initialize();f.ProcessSample();f.record();f.lampsMatch();
        check(f.gateLed.value==0 && f.delayedGateLed.value==0,"initial/off dark");
        f.powerSwitch.value=1;f.amplitudeKnob.value=.5;f.attackModeSwitch.value=1;
        f.thresholdKnob.value=.3;f.delayKnob.value=Math.log1p(100)/Math.log(3001);
        int edges=0,delayedEdges=0;boolean high=false,delayedHigh=false;
        int initialWrites=f.gateLed.writes,initialDelayedWrites=f.delayedGateLed.writes;
        for(int i=0;i<72000;i++){
            f.signalInput.value=i<12000||i>55000 ? 3*Math.sin(2*Math.PI*440*i/48000.0):0;
            f.ProcessSample();f.record();f.lampsMatch();
            boolean g=f.gateOutput.value>0,d=f.delayedGateOutput.value>0;
            if(high!=g)edges++;if(delayedHigh!=d)delayedEdges++;high=g;delayedHigh=d;
        }
        check(edges>=3 && delayedEdges>=3,"lamps exercise opening, closing, reopening");
        check(f.gateLed.writes-initialWrites==edges && f.delayedGateLed.writes-initialDelayedWrites==delayedEdges,"writes only on gate transitions");
        int reads=f.controlReads();f.signalInput.value=-2.75;f.ProcessBypassedSample();f.record();f.lampsMatch();
        check(f.gateLed.value==0 && f.delayedGateLed.value==0,"bypass clears lamps");
        check(f.controlReads()==reads && f.signalThroughOutput.value==-2.75,"bypass no control reads and raw audio");
        int writes=f.gateLed.writes+f.delayedGateLed.writes;
        for(int i=0;i<100;i++)f.ProcessBypassedSample();f.record();
        check(writes==f.gateLed.writes+f.delayedGateLed.writes,"bypass no redundant lamp writes");
        f.signalInput.value=0;f.ProcessSample();f.record();f.lampsMatch();
        check(f.delayedGateLed.value==0,"resume no stale delayed light");
        for(int i=0;i<10000;i++){f.signalInput.value=3*Math.sin(i*.2);f.ProcessSample();f.record();}
        check(f.gateLed.value==1 && f.delayedGateLed.value==1,"gates open before off test");
        f.powerSwitch.value=0;f.ProcessSample();f.record();f.lampsMatch();
        check(f.gateLed.value==0 && f.delayedGateLed.value==0,"power switch immediately clears gate lamps");
        for(int i=0;i<1000;i++){f.ProcessSample();f.record();f.lampsMatch();}
        double position=.37;
        Control[] knobs={f.amplitudeKnob,f.filterKnob,f.thresholdKnob,f.attackKnob,f.delayKnob,f.balanceKnob};
        double[] physical={4*position*position,20*Math.pow(10,position),.05*Math.expm1(position*Math.log(101)),
            .2*Math.pow(5000,position),Math.expm1(position*Math.log(3001)),100*position};
        for(int i=0;i<knobs.length;i++){
            f.EditComponentValue(knobs[i],physical[i],"");
            check(Math.abs(knobs[i].value-position)<1e-12,"typed physical unit inverse");
            f.EditComponentValue(knobs[i],Double.NaN,"");
            check(Math.abs(knobs[i].value-position)<1e-12,"reject nonfinite edit");
            f.EditComponentValue(knobs[i],-1,"");check(knobs[i].value==0,"edit minimum clamp");
            f.EditComponentValue(knobs[i],1e6,"");check(knobs[i].value==1,"edit maximum clamp");
        }
        System.out.println("TRACE="+Long.toUnsignedString(f.trace));
        System.out.println("PASS: actual host callback bodies, independent LED edges, power/bypass/resume, no redundant LED writes.");
    }
}
'''
    path=folder/'FollowingRoutingChecks.java';path.write_text(code,encoding='utf-8')
    classes=folder/'classes';cp=str(classes)+';'+str(sdk)
    subprocess.run(['javac',*flags,'-cp',cp,'-d',str(classes),str(path)],check=True)
    output=subprocess.check_output(['java','-cp',cp,'com.insectlabs.following.FollowingRoutingChecks'],text=True)
    print(output,end='')
    return next(line for line in output.splitlines() if line.startswith('TRACE='))
