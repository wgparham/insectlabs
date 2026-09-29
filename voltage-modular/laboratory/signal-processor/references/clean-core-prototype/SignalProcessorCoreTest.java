package com.insectlabs.laboratory.signalprocessor;

public final class SignalProcessorCoreTest {
    private static long checks;

    private static void equal(double expected, double actual, String label) {
        checks++;
        if (!Double.isFinite(actual) || Math.abs(expected - actual) > 1e-10) {
            throw new AssertionError(label + ": expected " + expected + ", got " + actual);
        }
    }

    private static SignalProcessorCore configured(double gain, double offset, double amount, boolean vca) {
        SignalProcessorCore core = new SignalProcessorCore(48000);
        core.setTopControls(gain, offset, amount, vca);
        return core;
    }

    private static void staticProcessing() {
        SignalProcessorCore core = configured(-2, 3, 0, false);
        core.process(12, 0, -7, 0);
        equal(-21, core.getTopOutput(), "inversion, amplification, offset, headroom");
        equal(-7, core.getBottomOutput(), "independent bottom defaults");
        core = configured(0, -4, 0, false);
        core.process(11, 0, 0, 0);
        equal(-4, core.getTopOutput(), "post-gain offset survives zero gain");
        equal(0, core.getBottomOutput(), "unpatched bottom is not normalized");
        core = configured(0.25, 0, 0, false);
        core.process(4, 0, 0, 0);
        equal(1, core.getTopOutput(), "attenuation");
    }

    private static void modulation() {
        SignalProcessorCore core = configured(0, 0, 1, true);
        double[] cvs = {0, 5, 2.5, -5, 10, 20, 0, 5};
        double[] expected = {0, 4, 2, 0, 8, 8, 0, 4};
        for (int i = 0; i < cvs.length; i++) {
            core.process(4, cvs[i], 0, 0);
            equal(expected[i], core.getTopOutput(), "VCA CV sample " + i);
        }
        core = configured(0, 0, 1, false);
        core.process(4, -5, 0, 0);
        equal(-4, core.getTopOutput(), "bipolar multiplication");
        core = configured(1, 0, -1, true);
        core.process(4, 5, 0, 0);
        equal(0, core.getTopOutput(), "negative CV amount closes VCA");
    }

    private static void smoothingAndBypass() {
        SignalProcessorCore core = new SignalProcessorCore(48000);
        core.process(1, 0, 0, 0);
        core.setTopControls(-1, 0, 0, false);
        // Repeated notification of the same target must not restart a transition.
        double previous = 1;
        for (int i = 0; i < 240; i++) {
            core.setTopControls(-1, 0, 0, false);
            core.process(1, 0, 0, 0);
            double actual = core.getTopOutput();
            if (actual > previous || actual < -1.0000000001) {
                throw new AssertionError("Nonmonotonic gain transition");
            }
            previous = actual;
        }
        equal(-1, core.getTopOutput(), "5 ms ramp endpoint");
        core.setTopControls(2, 5, 0, false);
        core.process(1, 0, 0, 0);
        for (int i = 0; i < 1000; i++) {
            core.processBypassed(-12, 9);
            equal(-12, core.getTopOutput(), "direct top bypass");
            equal(9, core.getBottomOutput(), "direct bottom bypass");
        }
        core.setTopControls(0, 2, 1, true);
        core.process(4, 5, 0, 0);
        equal(6, core.getTopOutput(), "resume uses current controls, not stale ramp");
        core = configured(-2, -5, -2, false);
        core.processBypassed(3, 8);
        equal(3, core.getTopOutput(), "start bypassed");
        core.process(3, 0, 8, 0);
        equal(-11, core.getTopOutput(), "first active sample uses restored controls");
    }

    private static void audioAndDc() {
        SignalProcessorCore core = configured(-0.75, 1.25, 0, false);
        core.setBottomControls(0.5, -2, 0, false);
        for (int i = 0; i < 48000; i++) {
            double input = 12 * Math.sin(i * 0.17);
            core.process(input, 0, 3, 0);
            equal(-0.75 * input + 1.25, core.getTopOutput(), "audio transfer");
            equal(-0.5, core.getBottomOutput(), "DC isolation");
        }
    }

    public static void main(String[] args) {
        staticProcessing();
        modulation();
        smoothingAndBypass();
        audioAndDc();
        System.out.println("PASS Signal Processor core: " + checks + " numeric checks");
    }
}
