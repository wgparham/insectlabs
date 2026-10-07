"""Exercise the actual exported callback bodies with host-object spies."""
import re,subprocess

def run(source, folder, sdk, flags):
    def region(name):
        return re.search(r'//\[user-'+name+r'\][^\n]*\n(.*?)//\[/user-'+name+r'\]',source,re.S)[1]
    helpers='    private final SherlockCore'+source.split('    private final SherlockCore',1)[1].split('    // Voltage Modular',1)[0]
    code='''package com.insectlabs.sherlock;
import com.insectlabs.sherlock.sherlock.SherlockCore;
public final class SherlockRoutingChecks {
    static class VoltageComponent {}
    static class Control extends VoltageComponent {
        double value;int reads,writes;
        double GetValue(){reads++;return value;}
        void SetValue(double v){value=v;writes++;}
    }
'''
    names=re.findall(r'private Voltage\w+ (\w+);',source)
    code+=''.join('    final Control '+n+' = new Control();\n' for n in names)+helpers
    for name in ['Initialize','ProcessSample','ProcessBypassedSample']:
        code+='\n    void '+name+'(){\n'+region(name)+'\n    }\n'
    code+='\n    void EditComponentValue(VoltageComponent component,double newValue,String newText){\n'+region('EditComponentValue')+'\n}\n'
    code+='''
    long trace=0xcbf29ce484222325L;
    void record(){
        double[] values={positiveOutput.value,negativeOutput.value,positiveHighOutput.value,
            positiveLowOutput.value,negativePulseOutput.value,positiveHighLed.value,
            positiveLowLed.value,negativePulseLed.value};
        for(double v:values){trace^=Double.doubleToLongBits(v);trace*=0x100000001b3L;}
    }
    static void check(boolean b,String why){if(!b)throw new AssertionError(why);}
    void lamps(){
        check(positiveHighLed.value==(positiveHighOutput.value>0?1:0),"positive HI lamp");
        check(positiveLowLed.value==(positiveLowOutput.value>0?1:0),"positive LO lamp");
        check(negativePulseLed.value==(negativePulseOutput.value>0?1:0),"negative pulse lamp");
    }
    int forbiddenReads(){return positiveRateKnob.reads+negativeRateKnob.reads+positiveCvKnob.reads+negativeCvKnob.reads
        +positiveRangeSwitch.reads+negativeRangeSwitch.reads+voltageSwitch.reads
        +positiveCvInput.reads+negativeCvInput.reads+startInput.reads+sustainInput.reads;}
    public static void main(String[] args){
        SherlockRoutingChecks f=new SherlockRoutingChecks();f.Initialize();
        f.positiveRateKnob.value=SherlockCore.defaultKnob(true,48000);
        f.negativeRateKnob.value=SherlockCore.defaultKnob(false,48000);
        f.positiveRangeSwitch.value=1;f.negativeRangeSwitch.value=0;
        int[] edges=new int[3];boolean[] prev=new boolean[3];
        int initialWrites=f.positiveHighLed.writes+f.positiveLowLed.writes+f.negativePulseLed.writes;
        for(int i=0;i<96000;i++){
            f.startInput.value=f.positiveLowOutput.value;
            f.negativeInput.value=f.negativePulseOutput.value;
            f.ProcessSample();f.record();f.lamps();
            boolean[] now={f.positiveHighOutput.value>0,f.positiveLowOutput.value>0,f.negativePulseOutput.value>0};
            for(int j=0;j<3;j++){if(now[j]!=prev[j])edges[j]++;prev[j]=now[j];}
        }
        check(edges[0]>900 && edges[1]>900 && edges[2]>25,"wrapper loops and three indicators active");
        check(f.positiveHighLed.writes+f.positiveLowLed.writes+f.negativePulseLed.writes-initialWrites==edges[0]+edges[1]+edges[2],"no redundant indicator writes");
        double pos=f.sherlockCore.positive,neg=f.sherlockCore.negative;
        double rate=f.sherlockCore.positiveRate.base;
        int phase=f.sherlockCore.positivePhase,reads=f.forbiddenReads();
        for(int i=0;i<1000;i++){
            f.positiveInput.value=Math.sin(i*.713)*23;f.negativeInput.value=Math.cos(i*.213)*17;
            f.ProcessBypassedSample();f.record();f.lamps();
            check(f.positiveOutput.value==f.positiveInput.value && f.negativeOutput.value==f.negativeInput.value,"exact independent bypass");
            check(f.positiveHighOutput.value==0 && f.positiveLowOutput.value==0 && f.negativePulseOutput.value==0,"bypass pulses silent");
        }
        check(reads==f.forbiddenReads(),"bypass reads no controls/CV/gates");
        check(f.sherlockCore.positive==pos && f.sherlockCore.negative==neg && f.sherlockCore.positiveRate.base==rate && f.sherlockCore.positivePhase==phase,"bypass freezes all processing state");
        f.positiveInput.value=0;f.negativeInput.value=0;f.startInput.value=0;f.ProcessSample();f.record();
        check(f.positiveOutput.value==0 && f.negativeOutput.value==0,"fresh resume");
        f.positiveInput.value=7;for(int i=0;i<3000;i++)f.ProcessSample();f.record();
        check(f.positiveOutput.value==7 && f.negativeOutput.value==0,"no hidden normal in wrapper");
        for(double k:new double[]{0,.37,1}){
            f.EditComponentValue(f.positiveRateKnob,SherlockCore.timeFor(k,true),"");
            f.EditComponentValue(f.negativeRateKnob,SherlockCore.timeFor(k,false),"");
            check(Math.abs(f.positiveRateKnob.value-k)<1e-12 && Math.abs(f.negativeRateKnob.value-k)<1e-12,"typed seconds inverse");
        }
        f.EditComponentValue(f.positiveCvKnob,-.4,"");check(f.positiveCvKnob.value==-.4,"typed bipolar gain");
        f.EditComponentValue(f.positiveCvKnob,Double.NaN,"");check(f.positiveCvKnob.value==-.4,"reject nonfinite edit");
        System.out.println("TRACE="+Long.toUnsignedString(f.trace));
        System.out.println("PASS: actual callback routing, independent bypass, frozen histories, resume, indicators, typed values.");
    }
}
'''
    path=folder/'SherlockRoutingChecks.java';path.write_text(code,encoding='utf-8')
    classes=folder/'classes';cp=str(classes)+';'+str(sdk)
    subprocess.run(['javac',*flags,'-cp',cp,'-d',str(classes),str(path)],check=True)
    output=subprocess.check_output(['java','-cp',cp,'com.insectlabs.sherlock.SherlockRoutingChecks'],text=True)
    print(output,end='')
    return next(line for line in output.splitlines() if line.startswith('TRACE='))
