package com.insectlabs.switches;

/** Shared 10-stage selector/distributor from swCXI 1.0.0; signals and CV are in volts. */
public final class SequentialSwitchCore {
    public static final int STAGES = 10;
    public static final int FADE_SAMPLES = 48; // 1 ms at 48 kHz
    public static final int EXCLUSIVE_FADE_SAMPLES = FADE_SAMPLES / 2;
    public static final double CLOCK_HIGH_V = 3.0;
    public static final double CLOCK_LOW_V = 1.8;
    public static final double RESET_HIGH_V = 3.0;
    public static final double RESET_LOW_V = 1.8;

    public final double[] inputs = new double[STAGES];
    public final double[] outputs = new double[STAGES];
    public final double[] muxGains = new double[STAGES];
    public final double[] muxStartGains = new double[STAGES];
    public int selectedStage;
    public double outCommon;
    private int muxFadePosition = FADE_SAMPLES;
    private int exclusiveStage;
    private int exclusiveTarget;
    private int exclusivePhase; // 0 steady, 1 fade old route down, 2 fade new route up
    private int exclusiveFadePosition;
    private double exclusiveStartGain = 1.0;
    private double exclusiveGain = 1.0;
    private boolean clockHigh;
    private boolean resetHigh;
    private boolean resetOverride;
    private int resetManualStage;
    private int resetCvStage;
    private boolean primed;
    private boolean resumePending;

    public SequentialSwitchCore() {
        reset();
    }

    private static double finite(double value) {
        return Double.isFinite(value) ? value : 0.0;
    }
    private static int clamp(int value, int minimum, int maximum) {
        return value < minimum ? minimum : (value > maximum ? maximum : value);
    }
    private static int knobStage(double value) {
        return clamp((int) Math.round(finite(value)), 1, STAGES) - 1;
    }
    /** Wraps each 5 V cycle across the active stage count (knob); negative CV travels backward through the same bins. */
    public static int stageFromCv(double volts, int stages) {
        stages = clamp(stages, 1, STAGES);
        double value = finite(volts);
        double wrapped = value - 5.0 * Math.floor(value / 5.0);
        int stage = (int) Math.floor((wrapped / 5.0) * stages);
        return clamp(stage, 0, stages - 1);
    }
    private static boolean schmitt(boolean wasHigh, double value, double high, double low) {
        value = finite(value);
        return wasHigh ? value >= low : value >= high;
    }
    private static double smoothstep(double position) {
        double t = position < 0.0 ? 0.0 : (position > 1.0 ? 1.0 : position);
        return t * t * (3.0 - 2.0 * t);
    }

    public void reset() {
        selectedStage = 0;
        outCommon = 0.0;
        for (int i = 0; i < STAGES; i++) {
            inputs[i] = 0.0;
            outputs[i] = 0.0;
            muxGains[i] = i == 0 ? 1.0 : 0.0;
            muxStartGains[i] = muxGains[i];
        }
        muxFadePosition = FADE_SAMPLES;
        exclusiveStage = 0;
        exclusiveTarget = 0;
        exclusivePhase = 0;
        exclusiveFadePosition = 0;
        exclusiveStartGain = 1.0;
        exclusiveGain = 1.0;
        clockHigh = false;
        resetHigh = false;
        resetOverride = false;
        resetManualStage = 0;
        resetCvStage = 0;
        primed = false;
        resumePending = false;
    }

    public byte[] getState() {
        return new byte[] { (byte) selectedStage };
    }

    public void setState(byte[] state) {
        if (state == null || state.length < 1) {
            return;
        }
        selectedStage = clamp(state[0] & 0xff, 0, STAGES - 1);
        for (int i = 0; i < STAGES; i++) {
            muxGains[i] = i == selectedStage ? 1.0 : 0.0;
            muxStartGains[i] = muxGains[i];
            outputs[i] = 0.0;
        }
        muxFadePosition = FADE_SAMPLES;
        exclusiveStage = selectedStage;
        exclusiveTarget = selectedStage;
        exclusivePhase = 0;
        exclusiveFadePosition = 0;
        exclusiveStartGain = 1.0;
        exclusiveGain = 1.0;
        clockHigh = false;
        resetHigh = false;
        resetOverride = false;
        resetManualStage = 0;
        resetCvStage = 0;
        primed = false;
        resumePending = false;
    }

    private void requestStage(int stage) {
        stage = clamp(stage, 0, STAGES - 1);
        if (stage == selectedStage) {
            return;
        }
        selectedStage = stage;
        System.arraycopy(muxGains, 0, muxStartGains, 0, STAGES);
        muxFadePosition = 0;

        if (exclusivePhase == 0) {
            if (stage != exclusiveStage) {
                exclusiveTarget = stage;
                exclusivePhase = 1;
                exclusiveFadePosition = 0;
                exclusiveStartGain = exclusiveGain;
            }
        } else if (exclusivePhase == 1) {
            if (stage == exclusiveStage) {
                exclusiveTarget = exclusiveStage;
                exclusivePhase = 2;
                exclusiveFadePosition = 0;
                exclusiveStartGain = exclusiveGain;
            } else {
                exclusiveTarget = stage;
            }
        } else if (stage != exclusiveStage) {
            exclusiveTarget = stage;
            exclusivePhase = 1;
            exclusiveFadePosition = 0;
            exclusiveStartGain = exclusiveGain;
        }
    }

    private void updateFades() {
        if (muxFadePosition < FADE_SAMPLES) {
            muxFadePosition++;
            double mix = smoothstep((double) muxFadePosition / FADE_SAMPLES);
            for (int i = 0; i < STAGES; i++) {
                double target = i == selectedStage ? 1.0 : 0.0;
                muxGains[i] = muxStartGains[i] * (1.0 - mix) + target * mix;
            }
        }
        if (exclusivePhase == 1) {
            exclusiveFadePosition++;
            double mix = smoothstep((double) exclusiveFadePosition / EXCLUSIVE_FADE_SAMPLES);
            exclusiveGain = exclusiveStartGain * (1.0 - mix);
            if (exclusiveFadePosition >= EXCLUSIVE_FADE_SAMPLES) {
                exclusiveGain = 0.0;
                exclusiveStage = exclusiveTarget;
                exclusivePhase = 2;
                exclusiveFadePosition = 0;
                exclusiveStartGain = 0.0;
            }
        }
        if (exclusivePhase == 2) {          // also runs in the sample phase 1 ends, so the new route starts rising at once (no silent sample)
            exclusiveFadePosition++;
            double mix = smoothstep((double) exclusiveFadePosition / EXCLUSIVE_FADE_SAMPLES);
            exclusiveGain = exclusiveStartGain + (1.0 - exclusiveStartGain) * mix;
            if (exclusiveFadePosition >= EXCLUSIVE_FADE_SAMPLES) {
                exclusiveGain = 1.0;
                exclusivePhase = 0;
                exclusiveFadePosition = 0;
            }
        }
    }

    /** Stage selection, reset, clock and fades. Call before mix(); needsInput() tells which stage inputs mix() will use. */
    public void update(double knobValue, double selectCv, boolean selectConnected,
                double clockCv, boolean clockConnected, double resetCv, boolean resetConnected) {
        if (resumePending) {
            resume();
        }
        int stages = clamp((int) Math.round(finite(knobValue)), 1, STAGES);
        boolean nextClockHigh = clockConnected && schmitt(clockHigh, clockCv, CLOCK_HIGH_V, CLOCK_LOW_V);
        boolean risingClock = primed && nextClockHigh && !clockHigh;
        clockHigh = nextClockHigh;
        boolean priorResetHigh = resetHigh;
        resetHigh = resetConnected && schmitt(resetHigh, resetCv, RESET_HIGH_V, RESET_LOW_V);

        if (!primed) {
            primed = true;
            if (resetHigh) {
                resetOverride = true;
                resetManualStage = knobStage(knobValue);
                resetCvStage = stageFromCv(selectCv, stages);
                requestStage(0);
            }
            else if (!clockConnected) {
                requestStage(selectConnected ? stageFromCv(selectCv, stages) : knobStage(knobValue));
            }
        } else if (resetHigh) {
            if (!priorResetHigh) {
                resetOverride = true;
                resetManualStage = knobStage(knobValue);
                resetCvStage = stageFromCv(selectCv, stages);
            }
            requestStage(0);
        } else if (resetOverride) {
            if (clockConnected) {
                if (risingClock) {
                    resetOverride = false;
                    requestStage(selectConnected ? stageFromCv(selectCv, stages) : (selectedStage + 1) % stages);
                }
            } else if (selectConnected) {
                int cvStage = stageFromCv(selectCv, stages);
                if (cvStage != resetCvStage) {
                    resetOverride = false;
                    requestStage(cvStage);
                }
            } else {
                int manualStage = knobStage(knobValue);
                if (manualStage != resetManualStage) {
                    resetOverride = false;
                    requestStage(manualStage);
                }
            }
        } else if (!clockConnected) {
            requestStage(selectConnected ? stageFromCv(selectCv, stages) : knobStage(knobValue));
        } else if (selectConnected) {
            if (risingClock) {
                requestStage(stageFromCv(selectCv, stages));
            } else if (selectedStage >= stages) {
                requestStage(stages - 1);
            }
        } else {
            if (selectedStage >= stages) {
                requestStage(stages - 1);
            } else if (risingClock) {
                requestStage((selectedStage + 1) % stages);
            }
        }

        updateFades();
    }

    /** True when stage input i can reach an output this sample; the module only reads those jacks. */
    public boolean needsInput(int stage) {
        return muxGains[stage] != 0.0 || stage == exclusiveStage;
    }

    /** Routes the stage inputs (entries that needsInput() rejected may be 0) to Common Out and the paired outputs. */
    public void mix(double commonInput, boolean commonInputConnected, boolean commonOutputConnected, double[] stageInputs) {
        outCommon = 0.0;
        for (int i = 0; i < STAGES; i++) {
            double input = finite(stageInputs[i]);
            outCommon += input * muxGains[i];
            outputs[i] = commonInputConnected
                ? (i == exclusiveStage ? finite(commonInput) * exclusiveGain : 0.0)
                : (!commonOutputConnected && i == exclusiveStage ? input * exclusiveGain : 0.0);
        }
    }

    /** Whole sample in one call (headless tests); the module calls update(), reads the inputs it needs, then mix(). */
    public void process(double knobValue, double selectCv, boolean selectConnected,
                 double clockCv, boolean clockConnected, double resetCv, boolean resetConnected,
                 double commonInput, boolean commonInputConnected, boolean commonOutputConnected,
                 double[] stageInputs) {
        update(knobValue, selectCv, selectConnected, clockCv, clockConnected, resetCv, resetConnected);
        mix(commonInput, commonInputConnected, commonOutputConnected, stageInputs);
    }

    /** Direct selected routes only. Clock, reset and fade histories remain frozen. */
    public void bypass(double commonInput, boolean commonInputConnected, boolean commonOutputConnected,
                double[] stageInputs) {
        double selectedInput = finite(stageInputs[selectedStage]);
        outCommon = selectedInput;
        double routed = commonInputConnected ? finite(commonInput)
            : (!commonOutputConnected ? selectedInput : 0.0);
        for (int i = 0; i < STAGES; i++) {
            outputs[i] = i == selectedStage ? routed : 0.0;
        }
        resumePending = true;
    }

    /** Rejoin the direct bypass route, then prime current gate levels without replaying an edge. */
    private void resume() {
        exclusiveStage = selectedStage;
        exclusiveTarget = selectedStage;
        exclusivePhase = 0;
        exclusiveGain = 1.0;
        exclusiveStartGain = 1.0;
        exclusiveFadePosition = 0;
        muxFadePosition = FADE_SAMPLES;
        for (int i = 0; i < STAGES; i++) {
            muxGains[i] = i == selectedStage ? 1.0 : 0.0;
            muxStartGains[i] = muxGains[i];
        }
        clockHigh = false;
        resetHigh = false;
        primed = false;
        resumePending = false;
    }
}
