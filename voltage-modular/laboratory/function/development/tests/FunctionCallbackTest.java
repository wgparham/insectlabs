public class FunctionCallbackTest {
    static class VoltageKnob {
        double value;
        VoltageKnob(double v) { value = v; }
        double GetValue() { return value; }
        void SetValue(double v) { value = v; }
    }
    VoltageKnob squareAmplitudeKnob = new VoltageKnob(0), squareRangeKnob = new VoltageKnob(2),
        sineAmplitudeKnob = new VoltageKnob(0), sineRangeKnob = new VoltageKnob(2), pulseWidthKnob = new VoltageKnob(2.5),
        frequencyKnob = new VoltageKnob(0.4177), frequencyMultiplierKnob = new VoltageKnob(2), powerSwitch = new VoltageKnob(0),
        powerIndicator = new VoltageKnob(0), frequencyDisplay = new VoltageKnob(0),
        squareOutput = new VoltageKnob(0), sineOutput = new VoltageKnob(0);
void Initialize() {
        resetFunctionGenerator();
        smoothedFrequencyHz = effectiveBaseFrequencyHz() * selectedMultiplier();
        frequencyDisplay.SetValue(effectiveBaseFrequencyHz());
        powerIndicator.SetValue(0.0);
        squareOutput.SetValue(0.0);
        sineOutput.SetValue(0.0);
        StartGuiUpdateTimer();

}
void ProcessSample() {
        double baseHz = effectiveBaseFrequencyHz();
        bypassed = false;
        if (resumePending) {
            smoothedFrequencyHz = baseHz * selectedMultiplier();
            smoothedSquareAmplitude = selectedAmplitude(squareAmplitudeKnob, squareRangeKnob);
            smoothedSineAmplitude = selectedAmplitude(sineAmplitudeKnob, sineRangeKnob);
            smoothedDutyCycle = requestedDutyCycle();
            resumePending = false;
        }
        boolean powered = powerSwitch.GetValue() >= 0.5;
        updatePowerGain(powered);
        if (!powered && powerGain <= 0.0) {
            squareOutput.SetValue(0.0);
            sineOutput.SetValue(0.0);
            return;
        }

        smoothedFrequencyHz += FREQUENCY_SMOOTH
                * (baseHz * selectedMultiplier() - smoothedFrequencyHz);
        smoothedSquareAmplitude += AMPLITUDE_SMOOTH
                * (selectedAmplitude(squareAmplitudeKnob, squareRangeKnob) - smoothedSquareAmplitude);
        smoothedSineAmplitude += AMPLITUDE_SMOOTH
                * (selectedAmplitude(sineAmplitudeKnob, sineRangeKnob) - smoothedSineAmplitude);
        smoothedDutyCycle += DUTY_SMOOTH * (requestedDutyCycle() - smoothedDutyCycle);

        // Independent oscillator boards share tuning but never reset each other's phase.
        double squareFrequency = Math.min(MAXIMUM_FREQUENCY_HZ, smoothedFrequencyHz
                * (1.0 + 0.0015 * Math.sin(squareDriftPhase * TWO_PI)));
        double sineFrequency = Math.min(MAXIMUM_FREQUENCY_HZ, smoothedFrequencyHz
                * (1.0 - 0.0010 * Math.sin(sineDriftPhase * TWO_PI + 0.73)));
        squarePhase = advancePhase(squarePhase, squareFrequency);
        sinePhase = advancePhase(sinePhase, sineFrequency);
        squareDriftPhase = advancePhase(squareDriftPhase, 0.071);
        sineDriftPhase = advancePhase(sineDriftPhase, 0.043);

        double squareSignal = bandLimitedPulse(squarePhase, smoothedDutyCycle, squareFrequency)
                * smoothedSquareAmplitude;
        double sineSignal = voicedSine(sinePhase) * smoothedSineAmplitude;
        squareOutput.SetValue(outputStage(squareSignal) * powerGain);
        sineOutput.SetValue(outputStage(sineSignal) * powerGain);

}
void ProcessBypassedSample() {
        // Source bypass is immediate silence; oscillator and smoothing state stay frozen.
        squareOutput.SetValue(0.0);
        sineOutput.SetValue(0.0);
        bypassed = true;
        resumePending = true;

}
    private static final double SAMPLE_RATE = 48000.0;
    private static final double TWO_PI = Math.PI * 2.0;
    private static final double C4_HZ = 261.625565;
    private static final double DEFAULT_FREQUENCY_POSITION = 0.4177;
    private static final double DEFAULT_BASE_HZ = C4_HZ / 10.0;
    private static final double MINIMUM_BASE_HZ = 0.1;
    private static final double MAXIMUM_BASE_HZ = 100.0;
    private static final double MAXIMUM_FREQUENCY_HZ = 23760.0;
    private static final double POWER_FADE_STEP = 1.0 / (SAMPLE_RATE * 0.008);
    private static final double FREQUENCY_SMOOTH = 1.0 - Math.exp(-1.0 / (SAMPLE_RATE * 0.010));
    private static final double AMPLITUDE_SMOOTH = 1.0 - Math.exp(-1.0 / (SAMPLE_RATE * 0.008));
    private static final double DUTY_SMOOTH = 1.0 - Math.exp(-1.0 / (SAMPLE_RATE * 0.006));
    private double squarePhase;
    private double sinePhase = 0.347;
    private double squareDriftPhase = 0.137;
    private double sineDriftPhase = 0.691;
    private volatile double powerGain;
    private volatile boolean bypassed;
    private volatile boolean resumePending;
    private double smoothedFrequencyHz = C4_HZ;
    private double smoothedSquareAmplitude;
    private double smoothedSineAmplitude;
    private double smoothedDutyCycle = 0.5;
    private double cachedFrequencyPosition = Double.NaN;
    private double cachedBaseHz = DEFAULT_BASE_HZ;

    private void resetFunctionGenerator() {
        squarePhase = 0.0; sinePhase = 0.347; squareDriftPhase = 0.137; sineDriftPhase = 0.691;
        powerGain = 0.0; smoothedFrequencyHz = C4_HZ; smoothedSquareAmplitude = 0.0;
        smoothedSineAmplitude = 0.0; smoothedDutyCycle = 0.5; cachedFrequencyPosition = Double.NaN; bypassed = false;
    }

    private void updatePowerGain(boolean powered) {
        powerGain = powered ? Math.min(1.0, powerGain + POWER_FADE_STEP) : Math.max(0.0, powerGain - POWER_FADE_STEP);
    }

    private double selectedMultiplier() {
        switch ((int) Math.round(frequencyMultiplierKnob.GetValue())) {
            case 1: return 1.0; case 2: return 10.0; case 3: return 100.0; case 4: return 1000.0; default: return 10000.0;
        }
    }

    private String multiplierLabel() {
        double value = selectedMultiplier();
        return value >= 1000.0 ? "X" + (int) (value / 1000.0) + "K" : "X" + (int) value;
    }

    // The custom taper keeps the existing panel's 0.4177 default exactly at C4 on X10.
    private double requestedBaseFrequencyHz() {
        double position = Math.max(0.0, Math.min(1.0, frequencyKnob.GetValue()));
        if (position == cachedFrequencyPosition) return cachedBaseHz;
        cachedFrequencyPosition = position;
        if (position <= DEFAULT_FREQUENCY_POSITION) {
            double portion = position / DEFAULT_FREQUENCY_POSITION;
            cachedBaseHz = MINIMUM_BASE_HZ * Math.pow(DEFAULT_BASE_HZ / MINIMUM_BASE_HZ, portion);
            return cachedBaseHz;
        }
        double portion = (position - DEFAULT_FREQUENCY_POSITION) / (1.0 - DEFAULT_FREQUENCY_POSITION);
        cachedBaseHz = DEFAULT_BASE_HZ * Math.pow(MAXIMUM_BASE_HZ / DEFAULT_BASE_HZ, portion);
        return cachedBaseHz;
    }

    private double effectiveBaseFrequencyHz() { return Math.min(requestedBaseFrequencyHz(), MAXIMUM_FREQUENCY_HZ / selectedMultiplier()); }
    private double requestedDutyCycle() { return 0.05 + 0.90 * (pulseWidthKnob.GetValue() / 5.0); }
    private double selectedAmplitude(VoltageKnob amplitude, VoltageKnob range) { return amplitude.GetValue() * selectedRangeVolts(range); }
    private double selectedRangeVolts(VoltageKnob range) { switch ((int) Math.round(range.GetValue())) { case 1: return 0.1; case 2: return 1.0; default: return 10.0; } }
    private String rangeLabel(VoltageKnob range) { double volts = selectedRangeVolts(range); return volts < 1.0 ? "0.1 V" : (int) volts + " V"; }
    private double advancePhase(double phase, double frequency) { phase += Math.max(0.0, Math.min(MAXIMUM_FREQUENCY_HZ, frequency)) / SAMPLE_RATE; return phase >= 1.0 ? phase - Math.floor(phase) : phase; }
    private double polyBlep(double phase, double increment) {
        if (phase < increment) { double t = phase / increment; return t + t - t * t - 1.0; }
        if (phase > 1.0 - increment) { double t = (phase - 1.0) / increment; return t * t + t + t + 1.0; }
        return 0.0;
    }
    private double bandLimitedPulse(double phase, double duty, double frequency) {
        double increment = Math.min(0.495, frequency / SAMPLE_RATE);
        double signal = phase < duty ? 1.0 : -1.0;
        signal += polyBlep(phase, increment);
        double fallingPhase = phase - duty; if (fallingPhase < 0.0) fallingPhase += 1.0;
        signal -= polyBlep(fallingPhase, increment);
        return signal;
    }
    private double voicedSine(double phase) {
        double angle = phase * TWO_PI;
        return Math.sin(angle) + 0.018 * Math.sin(angle * 2.0 + 0.21) + 0.006 * Math.sin(angle * 3.0);
    }
    private double outputStage(double signal) {
        double magnitude = Math.abs(signal);
        if (magnitude <= 5.0) return signal;
        double sign = signal < 0.0 ? -1.0 : 1.0;
        return sign * (5.0 + 4.6 * Math.tanh((magnitude - 5.0) / 4.6));
    }
    private double displayBaseFrequencyHz() {
        double position = Math.max(0.0, Math.min(1.0, frequencyKnob.GetValue()));
        double base = position <= DEFAULT_FREQUENCY_POSITION
                ? MINIMUM_BASE_HZ * Math.pow(DEFAULT_BASE_HZ / MINIMUM_BASE_HZ, position / DEFAULT_FREQUENCY_POSITION)
                : DEFAULT_BASE_HZ * Math.pow(MAXIMUM_BASE_HZ / DEFAULT_BASE_HZ,
                    (position - DEFAULT_FREQUENCY_POSITION) / (1.0 - DEFAULT_FREQUENCY_POSITION));
        return Math.min(base, MAXIMUM_FREQUENCY_HZ / selectedMultiplier());
    }
    private String formatHz(double hertz) { return hertz >= 1000.0 ? String.format(java.util.Locale.ROOT, "%.3f kHz", hertz / 1000.0) : String.format(java.util.Locale.ROOT, "%.3f Hz", hertz); }

    void StartGuiUpdateTimer() {}
    static void check(boolean yes, String label) { if (!yes) throw new AssertionError(label); }
    void run() {
        Initialize();
        check(Math.abs(frequencyDisplay.value - C4_HZ) < 0.000001, "C4 display default");
        ProcessSample();
        check(squareOutput.value == 0 && sineOutput.value == 0, "off silence");
        powerSwitch.value = 1; squareAmplitudeKnob.value = 1; sineAmplitudeKnob.value = 1;
        double sqEnergy = 0, sinEnergy = 0;
        for (int i=0; i<48000; i++) {
            ProcessSample();
            check(Double.isFinite(squareOutput.value) && Double.isFinite(sineOutput.value), "finite audio");
            sqEnergy += squareOutput.value * squareOutput.value;
            sinEnergy += sineOutput.value * sineOutput.value;
        }
        check(sqEnergy > 1000 && sinEnergy > 1000, "both outputs produce audio");
        double a=squarePhase, b=sinePhase, g=powerGain, f=smoothedFrequencyHz;
        for(int i=0;i<100;i++) ProcessBypassedSample();
        check(squareOutput.value == 0 && sineOutput.value == 0, "bypass silence");
        check(a==squarePhase && b==sinePhase && g==powerGain && f==smoothedFrequencyHz, "bypass freezes DSP");
        powerSwitch.value = 0;
        for(int i=0;i<385;i++) ProcessSample();
        check(powerGain==0 && squareOutput.value==0 && sineOutput.value==0, "power fades to silence");
        a=squarePhase; b=sinePhase;
        for(int i=0;i<100;i++) ProcessSample();
        check(a==squarePhase && b==sinePhase, "off freezes boards");
        frequencyKnob.value=1; frequencyMultiplierKnob.value=5;
        check(Math.abs(effectiveBaseFrequencyHz()-2.376)<1e-9, "ceiling display");
        powerSwitch.value=1; squareRangeKnob.value=3; sineRangeKnob.value=3;
        for(int i=0;i<48000;i++) {
            ProcessSample();
            check(Double.isFinite(squareOutput.value) && Double.isFinite(sineOutput.value), "finite ceiling audio");
            check(Math.abs(squareOutput.value)<10 && Math.abs(sineOutput.value)<10, "bounded output");
        }
        // All ranges and width extremes must be bounded and stay independent.
        for (int frequencyRange=1; frequencyRange<=5; frequencyRange++) {
            frequencyMultiplierKnob.value=frequencyRange;
            for (double position : new double[]{0.0,0.4177,1.0}) {
                frequencyKnob.value=position;
                for (int ampRange=1; ampRange<=3; ampRange++) {
                    squareRangeKnob.value=ampRange; sineRangeKnob.value=ampRange;
                    for (double width : new double[]{0.0,2.5,5.0}) {
                        pulseWidthKnob.value=width;
                        for (int i=0;i<1200;i++) {
                            ProcessSample();
                            check(Double.isFinite(squareOutput.value) && Double.isFinite(sineOutput.value), "range matrix finite");
                            check(Math.abs(squareOutput.value)<10 && Math.abs(sineOutput.value)<10, "range matrix bounds");
                        }
                    }
                }
            }
        }
        ProcessBypassedSample();
        squareAmplitudeKnob.value=0; sineAmplitudeKnob.value=1; sineRangeKnob.value=2; frequencyKnob.value=0.4177; frequencyMultiplierKnob.value=2;
        ProcessSample();
        check(squareOutput.value==0, "resume adopts square mute immediately");
        check(Math.abs(smoothedFrequencyHz-C4_HZ)<1e-6, "resume adopts current frequency");
        double sineOnly=0;
        for (int i=0;i<2000;i++) { ProcessSample(); check(squareOutput.value==0,"square mute independent"); sineOnly+=sineOutput.value*sineOutput.value; }
        check(sineOnly>100,"sine remains active with square muted");
        ProcessBypassedSample(); squareAmplitudeKnob.value=1; sineAmplitudeKnob.value=0;
        ProcessSample();
        check(sineOutput.value==0,"sine mute independent");
        check(selectedRangeVolts(new VoltageKnob(1))==0.1 && selectedRangeVolts(new VoltageKnob(2))==1 && selectedRangeVolts(new VoltageKnob(3))==10,"range voltages");
        double[] minimumHz = {0.1, 1.0, 10.0, 100.0, 1000.0};
        double[] maximumHz = {100.0, 1000.0, 10000.0, 23760.0, 23760.0};
        for (int range=1; range<=5; range++) {
            frequencyMultiplierKnob.value=range;
            double previous=-1;
            for (int i=0;i<=1000;i++) {
                frequencyKnob.value=i/1000.0;
                double hz=displayFrequencyHz();
                check(hz>previous,"full dial strictly increases on every range");
                check(Math.abs(hz-effectiveBaseFrequencyHz()*selectedMultiplier())<1e-7,"display equals DSP target");
                check(Math.abs(positionFromOutputHz(hz)-frequencyKnob.value)<1e-9,"typed Hz round-trip");
                if(i==0) check(Math.abs(hz-minimumHz[range-1])<1e-7,"range minimum");
                if(i==1000) check(Math.abs(hz-maximumHz[range-1])<1e-7,"range maximum");
                previous=hz;
            }
        }
        frequencyKnob.value=0.6;
        frequencyMultiplierKnob.value=4; double first=effectiveBaseFrequencyHz();
        frequencyMultiplierKnob.value=5; double second=effectiveBaseFrequencyHz();
        check(first!=second,"range change invalidates tuning cache");
        frequencyKnob.value=0.4177; frequencyMultiplierKnob.value=2;
        check(Math.abs(displayFrequencyHz()-C4_HZ)<1e-6,"C4 default retained");
        System.out.println("PASS: actual callbacks produce both signals; C4, ceiling, power fade and bypass freeze verified");
    }
    public static void main(String[] args) { new FunctionCallbackTest().run(); }
}
