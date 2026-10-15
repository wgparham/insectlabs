package com.insectlabs.dsp.filters;

/**
 * Approved Teletape SVF 1.0.0 core, fixed 96 kHz; two nonlinear OTA integrators.
 * Caller owns resampling, input gain and routing. Configure with finite positive Hz below 48 kHz.
 * Response is normalized 0..1; outputs are simultaneous circuit-scaled HP/BP/LP volts.
 * The amplitude loss and output headroom are musical model choices, not measured device behavior.
 */
public final class EmsSvf96 {
    private static final double INTERNAL_RATE = 96000.0;
    private static final double INPUT_SUM_GAIN = 27000.0 / 100000.0;
    private static final double OUTPUT_GAIN = 1.0 + 10000.0 / 3300.0;
    private static final double POSITIVE_FEEDBACK = (2.0 + INPUT_SUM_GAIN) / 101.0;
    private static final double MAX_DAMPING = 27000.0 / 15000.0;
    private static final double OTA_SCALE = 6.4;
    private static final double LOOP_LOSS = 0.04;
    private static final double OUTPUT_HEADROOM = 10.0;
    private static final double SIGNAL_LIMIT = 10000.0;

    private double bandState;
    private double lowState;
    private double g;
    private double damping;
    private double cachedFrequency = -1.0;
    private boolean oscillationSeeded;
    private final double[] taps = new double[3];

    private static double unit(double value) {
        return Double.isFinite(value) ? Math.max(0.0, Math.min(1.0, value)) : 0.0;
    }

    private static double safeSignal(double value) {
        return Double.isFinite(value)
                ? Math.max(-SIGNAL_LIMIT, Math.min(SIGNAL_LIMIT, value)) : 0.0;
    }

    private static double dampingFor(double response) {
        double remaining = 1.0 - unit(response);
        return MAX_DAMPING * remaining * remaining - POSITIVE_FEEDBACK;
    }

    private static double outputStage(double value) {
        double scaled = OUTPUT_GAIN * value;
        double ratio = scaled / OUTPUT_HEADROOM;
        double squared = ratio * ratio;
        return scaled / Math.sqrt(Math.sqrt(1.0 + squared * squared));
    }

    /** Clears both integrators and all output/oscillation startup state. */
    public void reset() {
        bandState = 0.0;
        lowState = 0.0;
        oscillationSeeded = false;
        java.util.Arrays.fill(taps, 0.0);
    }

    /**
     * Sets cutoff and normalized response. Frequency must be finite and strictly between
     * 0 and 48 kHz; invalid cutoff throws IllegalArgumentException.
     */
    public void configure(double frequency, double response) {
        if (!Double.isFinite(frequency) || frequency <= 0.0 || frequency >= INTERNAL_RATE * 0.5) {
            throw new IllegalArgumentException("Frequency must be finite and within (0, 48000) Hz");
        }
        if (frequency != cachedFrequency) {
            g = Math.tan(Math.PI * frequency / INTERNAL_RATE);
            cachedFrequency = frequency;
        }
        damping = dampingFor(response);
        if (damping < 0.0 && !oscillationSeeded) {
            // A one-time microscopic perturbation substitutes for hardware noise.
            if (Math.abs(bandState) + Math.abs(lowState) < 1.0e-8) {
                bandState = 1.0e-6;
            }
            oscillationSeeded = true;
        } else if (damping >= 0.0) {
            oscillationSeeded = false;
        }
    }

    /** Processes one 96 kHz sample after a successful configure call. */
    public void process(double input) {
        if (!Double.isFinite(bandState) || !Double.isFinite(lowState)
                || Math.abs(bandState) > 64.0 || Math.abs(lowState) > 64.0) {
            reset();
        }
        double drive = -INPUT_SUM_GAIN * safeSignal(input);
        double midpointBand = (bandState + g * (drive - lowState))
                / (1.0 + g * damping + g * g);
        midpointBand = Math.max(-64.0, Math.min(64.0, midpointBand));
        double midpointLow = lowState;
        double high = 0.0;
        for (int iteration = 0; iteration < 10; iteration++) {
            double bandTanh = Math.tanh(midpointBand / OTA_SCALE);
            double bandDerivative = 1.0 - bandTanh * bandTanh;
            midpointLow = lowState + g * OTA_SCALE * bandTanh;
            high = drive - midpointLow - damping * midpointBand
                    - LOOP_LOSS * midpointBand * midpointBand * midpointBand;
            double highTanh = Math.tanh(high / OTA_SCALE);
            double residual = midpointBand - bandState - g * OTA_SCALE * highTanh;
            double derivative = 1.0 + g * (1.0 - highTanh * highTanh)
                    * (g * bandDerivative + damping
                    + 3.0 * LOOP_LOSS * midpointBand * midpointBand);
            double correction = residual / derivative;
            midpointBand -= correction;
            if (Math.abs(correction) < 1.0e-11) {
                break;
            }
        }
        midpointLow = lowState + g * OTA_SCALE * Math.tanh(midpointBand / OTA_SCALE);
        high = drive - midpointLow - damping * midpointBand
                - LOOP_LOSS * midpointBand * midpointBand * midpointBand;
        bandState = 2.0 * midpointBand - bandState;
        lowState = 2.0 * midpointLow - lowState;
        if (!Double.isFinite(bandState) || !Double.isFinite(lowState)
                || Math.abs(bandState) > 64.0 || Math.abs(lowState) > 64.0) {
            reset();
            return;
        }
        taps[0] = outputStage(high);
        taps[1] = outputStage(midpointBand);
        taps[2] = outputStage(midpointLow);
    }

    public double getHigh() {
        return taps[0];
    }

    public double getBand() {
        return taps[1];
    }

    public double getLow() {
        return taps[2];
    }
}
