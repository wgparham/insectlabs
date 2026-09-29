package com.insectlabs.laboratory.faderdistr;

/** Shared native-rate DSP for the fixed-law Fader|Distr modules. */
public final class FaderDistrCore {
    private static final double SMOOTH_SECONDS = 0.005;

    private final boolean equalPower;
    private final WeightRamp leftWeight;
    private final WeightRamp rightWeight;
    private final RelayVoice signalVoice;
    private final RelayVoice xVoice;
    private final RelayVoice yVoice;
    private boolean resumePending = true;
    private double targetPosition = Double.NaN;
    private double leftOutput;
    private double rightOutput;
    private double mixOutput;

    public FaderDistrCore(boolean equalPower, double sampleRate) {
        if (!Double.isFinite(sampleRate) || sampleRate < 1 || sampleRate > 1_000_000) {
            throw new IllegalArgumentException("Invalid sample rate");
        }
        this.equalPower = equalPower;
        int smoothingSamples = Math.max(1, (int) Math.round(sampleRate * SMOOTH_SECONDS));
        double center = equalPower ? Math.sqrt(0.5) : 0.5;
        leftWeight = new WeightRamp(center, smoothingSamples);
        rightWeight = new WeightRamp(center, smoothingSamples);
        signalVoice = new RelayVoice(sampleRate);
        xVoice = new RelayVoice(sampleRate);
        yVoice = new RelayVoice(sampleRate);
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
        double voicedSignal = signalVoice.process(panInput);
        double voicedX = xVoice.process(inputA);
        double voicedY = yVoice.process(inputB);
        leftOutput = voicedSignal * left;
        rightOutput = voicedSignal * right;
        mixOutput = voicedX * left + voicedY * right;
    }

    public void processBypassed(double panInput, double inputA) {
        leftOutput = panInput;
        rightOutput = panInput;
        mixOutput = inputA;
        signalVoice.reset();
        xVoice.reset();
        yVoice.reset();
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

    /**
     * The fixed character of the relay contacts, transformer and long signal path.
     * It is intentionally native-rate: one soft knee and one low-pass state per input.
     */
    private static final class RelayVoice {
        private static final double KNEE_VOLTS = 1.5;
        private static final double DRIVE_START_VOLTS = 0.9;
        private static final double DRIVE_SPAN_VOLTS = 5.0;
        private static final double POSITIVE_COMPRESSION = 0.10;
        private static final double NEGATIVE_COMPRESSION = 0.08;
        private static final double CLEAN_CUTOFF_HZ = 10_000.0;
        private static final double DRIVEN_CUTOFF_HZ = 6_500.0;

        private final double cleanCoefficient;
        private final double drivenCoefficient;
        private double toneState;
        private boolean ready;

        private RelayVoice(double sampleRate) {
            cleanCoefficient = onePoleCoefficient(CLEAN_CUTOFF_HZ, sampleRate);
            drivenCoefficient = onePoleCoefficient(DRIVEN_CUTOFF_HZ, sampleRate);
        }

        private double process(double input) {
            double shaped = shape(input);
            if (!ready) {
                toneState = shaped;
                ready = true;
                return toneState;
            }
            double drive = clamp((Math.abs(input) - DRIVE_START_VOLTS) / DRIVE_SPAN_VOLTS, 0, 1);
            double coefficient = cleanCoefficient + drive * (drivenCoefficient - cleanCoefficient);
            toneState += coefficient * (shaped - toneState);
            return toneState;
        }

        private void reset() {
            ready = false;
            toneState = 0;
        }

        private static double shape(double input) {
            double magnitude = Math.abs(input);
            if (magnitude <= KNEE_VOLTS) return input;
            double excess = magnitude - KNEE_VOLTS;
            double compression = input >= 0 ? POSITIVE_COMPRESSION : NEGATIVE_COMPRESSION;
            double shaped = KNEE_VOLTS + excess / (1 + compression * excess);
            return Math.copySign(shaped, input);
        }

        private static double onePoleCoefficient(double cutoff, double sampleRate) {
            return 1 - Math.exp(-2 * Math.PI * cutoff / sampleRate);
        }
    }
}
