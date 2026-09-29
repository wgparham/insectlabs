package com.insectlabs.laboratory.panfade;

/** Shared native-rate DSP for the fixed-law Panfade modules. */
public final class PanfadeCore {
    private static final double SMOOTH_SECONDS = 0.005;

    private final boolean equalPower;
    private final WeightRamp leftWeight;
    private final WeightRamp rightWeight;
    private boolean resumePending = true;
    private double targetPosition = Double.NaN;
    private double leftOutput;
    private double rightOutput;
    private double mixOutput;

    public PanfadeCore(boolean equalPower, double sampleRate) {
        if (!Double.isFinite(sampleRate) || sampleRate < 1 || sampleRate > 1_000_000) {
            throw new IllegalArgumentException("Invalid sample rate");
        }
        this.equalPower = equalPower;
        int smoothingSamples = Math.max(1, (int) Math.round(sampleRate * SMOOTH_SECONDS));
        double center = equalPower ? Math.sqrt(0.5) : 0.5;
        leftWeight = new WeightRamp(center, smoothingSamples);
        rightWeight = new WeightRamp(center, smoothingSamples);
    }

    public void process(double position, double panInput, double inputA, double inputB) {
        setPosition(position);
        if (resumePending) {
            leftWeight.snap();
            rightWeight.snap();
            resumePending = false;
        }
        double left = leftWeight.next();
        double right = rightWeight.next();
        leftOutput = panInput * left;
        rightOutput = panInput * right;
        mixOutput = inputA * left + inputB * right;
    }

    public void processBypassed(double panInput, double inputA) {
        leftOutput = panInput;
        rightOutput = panInput;
        mixOutput = inputA;
        resumePending = true;
    }

    public double getLeftOutput() {
        return leftOutput;
    }

    public double getRightOutput() {
        return rightOutput;
    }

    public double getMixOutput() {
        return mixOutput;
    }

    private void setPosition(double position) {
        double limited = clamp(position, -1, 1);
        if (limited == targetPosition) return;
        targetPosition = limited;
        double p = 0.5 * (limited + 1);
        if (equalPower) {
            leftWeight.setTarget(Math.cos(0.5 * Math.PI * p));
            rightWeight.setTarget(Math.sin(0.5 * Math.PI * p));
        } else {
            leftWeight.setTarget(1 - p);
            rightWeight.setTarget(p);
        }
    }

    private static double clamp(double value, double minimum, double maximum) {
        if (!Double.isFinite(value)) return 0;
        return Math.max(minimum, Math.min(maximum, value));
    }

    private static final class WeightRamp {
        private final int durationSamples;
        private double current;
        private double target;
        private double step;
        private int remainingSamples;

        private WeightRamp(double initial, int durationSamples) {
            this.durationSamples = durationSamples;
            current = target = initial;
        }

        private void setTarget(double value) {
            if (value == target) return;
            target = value;
            step = (target - current) / durationSamples;
            remainingSamples = durationSamples;
        }

        private double next() {
            if (remainingSamples > 0) {
                current += step;
                if (--remainingSamples == 0) current = target;
            }
            return current;
        }

        private void snap() {
            current = target;
            remainingSamples = 0;
        }
    }
}
