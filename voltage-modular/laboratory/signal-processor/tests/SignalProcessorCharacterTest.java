package com.insectlabs.laboratory.signalprocessor;

public final class SignalProcessorCharacterTest {
    private static int checks;

    private static void near(double expected, double actual, double tolerance, String label) {
        checks++;
        if (Math.abs(expected - actual) > tolerance) {
            throw new AssertionError(label + ": expected " + expected + ", got " + actual);
        }
    }

    private static void truth(boolean condition, String label) {
        checks++;
        if (!condition) throw new AssertionError(label);
    }

    private static double settle(SignalProcessorCore core, double input, double cv, boolean top) {
        for (int i = 0; i < 12000; i++) core.process(input, cv, 0, 0);
        return top ? core.getTopOutput() : core.getBottomOutput();
    }

    private static SignalProcessorCore configured(double gain, double offset,
            double amount, boolean vca) {
        SignalProcessorCore core = new SignalProcessorCore(48000);
        core.setTopControls(gain, offset, amount, vca);
        core.setBottomControls(1, 0, 0, false);
        return core;
    }

    private static void processorRangeAndOffset() {
        near(3, settle(configured(3, 0, 0, false), 1, 0, true), 1e-9,
                "PROC reaches +3 below character knee");
        near(-2, settle(configured(-2, 0, 0, false), 1, 0, true), 1e-9,
                "PROC reaches -2");
        near(-3, settle(configured(-3, 0, 0, false), 1, 0, true), 1e-9,
                "PROC reaches symmetric -3 below character knee");
        near(5, settle(configured(0, 5, 0, false), 0, 0, true), 1e-9,
                "post-character offset remains exact");
        near(-2, settle(configured(0, 0, 1, false), 1, -10, true), 1e-9,
                "PROC external control remains bipolar");
    }

    private static void vcaLawAndTiming() {
        near(0.375, settle(configured(0.5, 0, 0, true), 1, 0, true), 1e-9,
                "VCA rounded half-scale law");
        near(1, settle(configured(1, 0, 0, true), 1, 0, true), 1e-9,
                "VCA unity is exact");
        near(0, settle(configured(-2, 0, 0, true), 1, 0, true), 1e-12,
                "VCA negative control closes rather than inverts");
        near(0, settle(configured(0, 0, 1, true), 1, -5, true), 1e-12,
                "negative VCA control closes");
        near(3, settle(configured(0, 0, 1, true), 1, 15, true), 1e-9,
                "VCA control reaches +3 push region");

        SignalProcessorCore core = configured(0, 0, 1, true);
        settle(core, 1, 0, true);
        core.process(1, 5, 0, 0);
        double openingFirst = core.getTopOutput();
        for (int i = 0; i < 143; i++) core.process(1, 5, 0, 0);
        double opening144 = core.getTopOutput();
        truth(openingFirst > 0 && openingFirst < 0.01, "VCA opening begins smoothly");
        truth(opening144 > 0.60 && opening144 < 0.66, "VCA opening time is about 3 ms");

        for (int i = 0; i < 5000; i++) core.process(1, 5, 0, 0);
        core.process(1, 0, 0, 0);
        double closingFirst = core.getTopOutput();
        for (int i = 0; i < 1439; i++) core.process(1, 0, 0, 0);
        double closing1440 = core.getTopOutput();
        truth(closingFirst > 0.99, "VCA release is slower than attack");
        truth(closing1440 > 0.36 && closing1440 < 0.38, "VCA release time is about 30 ms");
    }

    private static void restrainedCharacter() {
        SignalProcessorCore positive = configured(3, 0, 0, false);
        SignalProcessorCore negative = configured(3, 0, 0, false);
        double pos = settle(positive, 2, 0, true);
        double neg = settle(negative, -2, 0, true);
        near(5.5, pos, 1e-8, "+6 V drive is softly compressed");
        near(-5.583333333333333, neg, 1e-8, "negative drive has slightly different compression");
        truth(Math.abs(neg) > pos, "iron stage is mildly asymmetric");

        SignalProcessorCore negativeGain = configured(-3, 0, 0, false);
        double cleanerInversion = settle(negativeGain, 2, 0, true);
        near(-5.854166666666667, cleanerInversion, 1e-8,
                "negative-gain push retains only 35 percent character");
        truth(Math.abs(cleanerInversion) > Math.abs(neg),
                "negative knob direction saturates less than positive direction");

        SignalProcessorCore highFrequency = configured(3, 0, 0, false);
        double sum = 0;
        for (int i = 0; i < 24000; i++) {
            highFrequency.process((i & 1) == 0 ? 2 : -2, 0, 0, 0);
            if (i > 1000) sum += Math.abs(highFrequency.getTopOutput());
        }
        double average = sum / 22999;
        truth(average > 3.5 && average < 4.5,
                "driven Nyquist-region energy is mildly rounded: " + average);
    }

    private static void channelsAndBypass() {
        SignalProcessorCore core = new SignalProcessorCore(48000);
        core.setTopControls(2, 1, 0, false);
        core.setBottomControls(-1, -2, 0, false);
        for (int i = 0; i < 10000; i++) core.process(1, 0, 3, 0);
        near(3, core.getTopOutput(), 1e-9, "top stage independence");
        near(-5, core.getBottomOutput(), 1e-9, "bottom stage independence");
        core.processBypassed(7.25, -8.5);
        near(7.25, core.getTopOutput(), 0, "top bypass is exact");
        near(-8.5, core.getBottomOutput(), 0, "bottom bypass is exact");
        core.setTopControls(3, 0, 0, false);
        core.process(1, 0, 0, 0);
        near(3, core.getTopOutput(), 1e-9, "resume uses current controls without fade");
    }

    public static void main(String[] args) {
        processorRangeAndOffset();
        vcaLawAndTiming();
        restrainedCharacter();
        channelsAndBypass();
        System.out.println("PASS Signal Processor character: " + checks + " checks");
    }
}
