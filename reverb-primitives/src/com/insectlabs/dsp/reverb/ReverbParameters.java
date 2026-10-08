package com.insectlabs.dsp.reverb;

/** Control-rate parameter conversions used by LM-21; compute outside process(). */
public final class ReverbParameters {
    private ReverbParameters() {}
    public static double feedbackForRt60(double seconds) {
        if (!Double.isFinite(seconds) || seconds <= 0.0)
            throw new IllegalArgumentException("RT60 must be finite and positive");
        return Math.pow(10.0, -3.0 * 0.06838 / seconds);
    }
    public static double dampingForCutoff(double cutoffHz, double sampleRate) {
        if (!Double.isFinite(sampleRate) || sampleRate < 8000.0 || sampleRate > 192000.0
                || !Double.isFinite(cutoffHz))
            throw new IllegalArgumentException("Invalid cutoff or sample rate");
        double cutoff = Math.max(40.0, Math.min(sampleRate * 0.45, cutoffHz));
        double d = 2.0 - Math.cos(cutoff * 2.0 * Math.PI / sampleRate);
        return d - Math.sqrt(d * d - 1.0);
    }
}
