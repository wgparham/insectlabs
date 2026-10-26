package com.insectlabs.dsp.delay;

import com.insectlabs.dsp.delay.BbdAnalogDelay;

/** Compares the extraction with FMC 1.0.0's archived DM-2 voice. */
public final class BbdAnalogDelayParityTest {
    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    public static void main(String[] args) {
        final double fs = 48000.0;
        final long seed = 0x9E3779B97F4A7C15L;
        FmcParityFixture.FmcCore reference = new FmcParityFixture.FmcCore(fs);
        BbdAnalogDelay extracted = new BbdAnalogDelay(fs, seed);
        reference.setVoice(0, FmcParityFixture.FmcCore.V_DM);

        check(BbdAnalogDelay.delaySecondsForPosition(0.0) == BbdAnalogDelay.MIN_DELAY_SECONDS,
                "minimum time mapping");
        check(Math.abs(BbdAnalogDelay.delaySecondsForPosition(1.0) - BbdAnalogDelay.MAX_DELAY_SECONDS) < 1e-14,
                "maximum time mapping");
        double position = 0.37;
        check(Math.abs(BbdAnalogDelay.timePositionForSeconds(
                BbdAnalogDelay.delaySecondsForPosition(position)) - position) < 1e-14,
                "time-position mappings round trip");

        double[][] controls = {
                {0.0, 0.0, 0.0, 0.0, 0.0, 1.0},
                {0.16, 0.54, 0.8, -0.25, 0.0, 1.0},
                {0.5, 0.88, 1.0, 0.0, 1.0, 1.0},
                {0.94, 0.98, 0.45, 0.6, 0.0, 0.0},
                {1.0, 0.7, 0.9, -0.4, 0.0, 1.0},
                {0.31, 0.35, 0.0, 0.1, 0.0, 1.0}
        };
        int sectionLength = 48000;
        int samples = sectionLength * controls.length;
        double maxError = 0.0;
        for (int n = 0; n < samples; n++) {
            double[] c = controls[n / sectionLength];
            boolean hold = c[4] > 0.5;
            boolean patched = c[5] > 0.5;
            reference.setA(0, c[0]);
            reference.setB(0, c[1]);
            reference.setC(0, 1.0); // compare the FMC wet-only result
            reference.setD(0, c[2]);
            reference.setRateCv(0, c[3]);
            reference.setHold(0, hold);
            reference.setPatched(0, patched);

            extracted.setTimePosition(c[0]);
            extracted.setFeedbackPosition(c[1]);
            extracted.setGrit(c[2]);
            extracted.setTimeCv(c[3]);
            extracted.setHold(hold);
            extracted.setInputConnected(patched);

            double input = patched
                    ? 0.47 * Math.sin(2.0 * Math.PI * 173.0 * n / fs)
                    + 0.13 * Math.sin(2.0 * Math.PI * 997.0 * n / fs)
                    : 0.0;
            double expected = reference.process(0, input);
            double actual = extracted.process(input);
            double error = Math.abs(expected - actual);
            if (error > maxError) maxError = error;
            check(Double.doubleToLongBits(expected) == Double.doubleToLongBits(actual),
                    "DM-2 extraction diverged at sample " + n + ", error=" + error);
        }
        check(maxError == 0.0, "exact FMC arithmetic parity");

        extracted.reset();
        reference.resetChannel(0);
        for (int n = 0; n < 4096; n++) {
            double input = 0.0;
            check(Double.doubleToLongBits(reference.process(0, input))
                    == Double.doubleToLongBits(extracted.process(input)), "reset parity at sample " + n);
        }
        check(!extracted.wantsProcessing(), "reset clears active tail state");
        System.out.println("PASS: BbdAnalogDelay is bit-identical to FMC 1.0.0 DM-2 over 288000 samples and reset.");
    }
}
