package com.insectlabs.laboratory.signalprocessor;

/** Mono channel pair for the Signal Processor prototype; independent of Designer controls. */
public final class SignalProcessorCore {
    public static final double CV_REFERENCE_VOLTS = 5.0;
    private final Channel top;
    private final Channel bottom;
    private boolean resumePending = true;
    private double topOutput;
    private double bottomOutput;

    public SignalProcessorCore(double sampleRate) {
        if (!Double.isFinite(sampleRate) || sampleRate < 1 || sampleRate > 1000000) {
            throw new IllegalArgumentException("Invalid sample rate");
        }
        int rampSamples = Math.max(1, (int) Math.round(sampleRate * 0.005));
        top = new Channel(rampSamples);
        bottom = new Channel(rampSamples);
    }

    /** Call on parameter notifications, including initialization and restored settings. */
    public void setTopControls(double gain, double offset, double cvAmount, boolean vca) {
        top.setControls(gain, offset, cvAmount, vca);
    }

    public void setBottomControls(double gain, double offset, double cvAmount, boolean vca) {
        bottom.setControls(gain, offset, cvAmount, vca);
    }

    /** Pass zero for unpatched inputs. CV remains unsmoothed and both channels are independent. */
    public void process(double topInput, double topCv, double bottomInput, double bottomCv) {
        if (resumePending) {
            top.snap();
            bottom.snap();
            resumePending = false;
        }
        topOutput = top.process(topInput, topCv);
        bottomOutput = bottom.process(bottomInput, bottomCv);
    }

    /** Direct bypass: no control/CV access and no advancement of processing histories. */
    public void processBypassed(double topInput, double bottomInput) {
        topOutput = topInput;
        bottomOutput = bottomInput;
        resumePending = true;
    }

    public double getTopOutput() {
        return topOutput;
    }

    public double getBottomOutput() {
        return bottomOutput;
    }

    private static double clamp(double value, double minimum, double maximum) {
        return Math.max(minimum, Math.min(maximum, value));
    }

    private static final class Channel {
        private final Ramp gain;
        private final Ramp offset;
        private final Ramp cvAmount;
        private boolean vca;

        private Channel(int samples) {
            gain = new Ramp(1, samples);
            offset = new Ramp(0, samples);
            cvAmount = new Ramp(0, samples);
        }

        private void setControls(double newGain, double newOffset, double newAmount, boolean newVca) {
            if (!Double.isFinite(newGain) || !Double.isFinite(newOffset)
                    || !Double.isFinite(newAmount)) {
                throw new IllegalArgumentException("Controls must be finite");
            }
            gain.setTarget(clamp(newGain, -2, 2));
            offset.setTarget(clamp(newOffset, -5, 5));
            cvAmount.setTarget(clamp(newAmount, -2, 2));
            vca = newVca;
        }

        private void snap() {
            gain.snap();
            offset.snap();
            cvAmount.snap();
        }

        private double process(double input, double cv) {
            double effectiveGain = clamp(
                    gain.next() + cvAmount.next() * (cv / CV_REFERENCE_VOLTS),
                    vca ? 0 : -2, 2);
            return input * effectiveGain + offset.next();
        }
    }

    private static final class Ramp {
        private final int samples;
        private double current;
        private double target;
        private double step;
        private int remaining;

        private Ramp(double initial, int samples) {
            this.samples = samples;
            current = target = initial;
        }

        private void setTarget(double value) {
            if (value == target) {
                return;
            }
            target = value;
            step = (target - current) / samples;
            remaining = samples;
        }

        private double next() {
            if (remaining > 0) {
                current += step;
                if (--remaining == 0) {
                    current = target;
                }
            }
            return current;
        }

        private void snap() {
            current = target;
            remaining = 0;
        }
    }
}
