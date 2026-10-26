package com.insectlabs.dsp.interpolation;

import java.util.Objects;

/**
 * Fixed-cost 2x midpoint processing for nonlinear audio stages.
 *
 * <p>Each host-rate input is linearly interpolated with the preceding input at
 * the half-sample point. The supplied processor runs at that midpoint and at
 * the current input. The result is either the average of those two processed
 * values or the second value, which is useful when the processor itself owns
 * a 2x-rate stateful filter. This is a lightweight midpoint approximation,
 * not a band-limited oversampler.</p>
 *
 * <p>The processor is retained at construction and is called twice per host
 * sample after initialization. It should be allocation-free and safe to call
 * from the audio thread. {@link #reset()} resets only this class's input
 * history; reset any processor-owned state separately.</p>
 */
public final class MidpointOversampler2x {
    /** Selects how the two processed subsamples are reduced to one output. */
    public enum OutputMode {
        /** Average midpoint and endpoint outputs. */
        AVERAGE,
        /** Return the endpoint output; useful with a stateful 2x-rate filter. */
        SECOND
    }

    /** A caller-owned, allocation-free sample transform. */
    @FunctionalInterface
    public interface SampleProcessor {
        double process(double sample);
    }

    private final SampleProcessor processor;
    private final OutputMode outputMode;
    private double previousInput;
    private boolean initialized;

    /** Creates a 2x midpoint processor. */
    public MidpointOversampler2x(SampleProcessor processor, OutputMode outputMode) {
        this.processor = Objects.requireNonNull(processor, "processor");
        this.outputMode = Objects.requireNonNull(outputMode, "outputMode");
    }

    /** Processes one host-rate input sample without allocating. */
    public double process(double input) {
        if (!initialized) {
            previousInput = input;
            initialized = true;
            return processor.process(input);
        }

        double midpoint = (previousInput + input) * 0.5;
        previousInput = input;
        double midpointOutput = processor.process(midpoint);
        double endpointOutput = processor.process(input);
        return outputMode == OutputMode.AVERAGE
                ? 0.5 * (midpointOutput + endpointOutput)
                : endpointOutput;
    }

    /** Clears interpolation history; processor-owned state is left untouched. */
    public void reset() {
        previousInput = 0.0;
        initialized = false;
    }
}
