package com.insectlabs.dsp.delay;

import java.util.Arrays;

/**
 * Framework-free, mono BBD-style delay core extracted from FMC 1.0.0's DM-2 voice.
 *
 * <p>Audio uses the InsectLabs normalized domain (1.0 = 5 V). Setters accept
 * controls in the 0..1 range; time is logarithmic from 20 ms to 2.4 s,
 * feedback reaches 110%, grit controls compander/noise behavior, and time CV
 * is in core units (1.0 = 5 V, one octave per unit). {@link #process(double)}
 * returns wet only. The caller owns dry/wet balance, routing, module gain and
 * final output limiting.</p>
 *
 * <p>The class preserves the FMC DM-2 software model, not a component-accurate
 * BOSS circuit. It has no process-time allocations. One instance uses a
 * power-of-two delay ring sized for the sample rate: about 2 MiB at 48 kHz.</p>
 */
public final class BbdAnalogDelay {

    public static final double MIN_DELAY_SECONDS = 0.020;
    public static final double MAX_DELAY_SECONDS = 2.4;
    public static final double MAX_FEEDBACK_GAIN = 1.1;

    private static final double INPUT_LIMIT = 4.0;
    private static final double NOISE_RMS = 6.3e-4;
    private static final double MAX_G = 100.0;
    private static final double COMP_REF = 0.1;
    private static final double TAIL_FB_MAX = 0.7;
    private static final double ACTIVE_THR = 3e-5;
    private static final int QUIET_MAX = 1 << 30;
    private static final int ENV_DECIM = 48;
    private static final int ENV_RING = 6700;
    private static final double ENV_K = 10.0;
    private static final double HOLD_KAPPA = 0.5;
    private static final double LOOP_LIMIT = 1.5;
    private static final double LN2 = 0.6931471805599453;
    private static final double LOG_TIME_RATIO = Math.log(MAX_DELAY_SECONDS / MIN_DELAY_SECONDS);

    private final double sampleRate;
    private final int ringMask;
    private final double[] delayBuffer;
    private final double[] gainBuffer;
    private final double[] envRing = new double[ENV_RING];
    private final double[] inFilter = new double[3];
    private final double[] outFilter = new double[3];
    private final double controlSmoothing;
    private final double cvSmoothing;
    private final double timeSmoothing;
    private final double cutoffAtTime;
    private final Compander compander;

    // Parameters may be written outside the audio thread; smoothed state below is audio-thread-owned.
    private volatile double targetTimePosition = 0.5;
    private volatile double targetFeedbackPosition = 0.5;
    private volatile double targetGrit = 0.5;
    private volatile double targetTimeCv;
    private volatile boolean hold;
    private volatile boolean inputConnected = true;

    private double timePosition = 0.5;
    private double feedbackPosition = 0.5;
    private double grit;
    private double timeCv;
    private boolean fresh = true;
    private int writePosition;
    private double logTime = Math.log(MIN_DELAY_SECONDS);
    private double feedbackSignal;
    private int envWritePosition;
    private int envDivider;
    private double holdLogGain;
    private double holdEnvelope;
    private boolean holdWasActive;
    private int holdAge;
    private double lastDelaySeconds = 0.1;
    private double meter;
    private int quiet = QUIET_MAX;
    private long noiseState;

    public BbdAnalogDelay(double sampleRate) {
        this(sampleRate, 0x9E3779B97F4A7C15L);
    }

    /** Creates an instance with a reproducible, caller-selected noise seed. */
    public BbdAnalogDelay(double sampleRate, long noiseSeed) {
        if (!isFinite(sampleRate) || sampleRate <= 8000.0 || sampleRate >= 400000.0) {
            throw new IllegalArgumentException("sampleRate must be finite and between 8000 and 400000 Hz");
        }
        this.sampleRate = sampleRate;
        int ringSize = nextPowerOfTwo((int) Math.ceil(sampleRate * MAX_DELAY_SECONDS) + 8);
        ringMask = ringSize - 1;
        delayBuffer = new double[ringSize];
        gainBuffer = new double[ringSize];
        controlSmoothing = 1.0 - Math.exp(-1.0 / (0.005 * sampleRate));
        cvSmoothing = 1.0 - Math.exp(-1.0 / (0.002 * sampleRate));
        timeSmoothing = 1.0 - Math.exp(-1.0 / (0.040 * sampleRate));
        cutoffAtTime = lpCoef(dmCutoffHz(0.3), sampleRate);
        compander = new Compander(sampleRate);
        noiseState = noiseSeed == 0L ? 0x1234567L : noiseSeed;
        Arrays.fill(gainBuffer, MAX_G);
    }

    /** Sets logarithmic delay-time position: 0 = 20 ms, 1 = 2.4 s. */
    public void setTimePosition(double position) {
        if (isFinite(position)) targetTimePosition = clamp01(position);
    }

    /** Convenience mapping from seconds to the logarithmic time control. */
    public void setDelaySeconds(double seconds) {
        if (isFinite(seconds)) targetTimePosition = timePositionForSeconds(seconds);
    }

    /** Sets feedback control position; its full sweep maps to 0..110% loop gain. */
    public void setFeedbackPosition(double position) {
        if (isFinite(position)) targetFeedbackPosition = clamp01(position);
    }

    /** Sets the grit/compander-noise control, 0..1. */
    public void setGrit(double amount) {
        if (isFinite(amount)) targetGrit = clamp01(amount);
    }

    /** Sets rate/time CV in core units: 1.0 is +5 V and halves delay time. */
    public void setTimeCv(double coreUnits) {
        if (isFinite(coreUnits)) targetTimeCv = clamp(coreUnits, -4.0, 4.0);
    }

    /** Enables the hold-level controller. The held loop ignores new input. */
    public void setHold(boolean enabled) { hold = enabled; }

    /** Indicates whether an input is patched; an unpatched tail is capped at 70% feedback. */
    public void setInputConnected(boolean connected) { inputConnected = connected; }

    /** Processes one normalized sample and returns the wet signal only. */
    public double process(double input) {
        double x = !isFinite(input) ? 0.0 : clamp(input, -INPUT_LIMIT, INPUT_LIMIT);
        double cvTarget = targetTimeCv;
        timeCv += cvSmoothing * (cvTarget - timeCv);
        if (fresh) {
            timePosition = targetTimePosition;
            feedbackPosition = targetFeedbackPosition;
            grit = targetGrit;
            timeCv = cvTarget;
            fresh = false;
            logTime = Math.log(MIN_DELAY_SECONDS) + timePosition * LOG_TIME_RATIO;
        } else {
            timePosition += controlSmoothing * (targetTimePosition - timePosition);
            feedbackPosition += controlSmoothing * (targetFeedbackPosition - feedbackPosition);
            grit += controlSmoothing * (targetGrit - grit);
        }

        double targetLogTime = Math.log(MIN_DELAY_SECONDS) + timePosition * LOG_TIME_RATIO;
        logTime += timeSmoothing * (targetLogTime - logTime);
        double t = Math.exp(clamp(logTime - timeCv * LN2,
                Math.log(MIN_DELAY_SECONDS), Math.log(MAX_DELAY_SECONDS)));
        lastDelaySeconds = t;
        double k = t >= 0.3 ? cutoffAtTime : lpCoef(dmCutoffHz(t), sampleRate);
        double u = limit((hold ? 0.0 : x) + feedbackSignal);
        trackLoop(u, t);

        double s = u;
        for (int i = 0; i < 3; i++) {
            inFilter[i] += k * (s - inFilter[i]);
            s = inFilter[i];
        }

        boolean gritty = grit > 0.001;
        double gain = gritty ? compander.gain(s) : 1.0;
        delayBuffer[writePosition] = s * gain;
        gainBuffer[writePosition] = gain;
        writePosition = (writePosition + 1) & ringMask;

        double delaySamples = t * sampleRate;
        double readDelay = delaySamples < 4.0 ? 4.0
                : (delaySamples > delayBuffer.length - 8 ? delayBuffer.length - 8 : delaySamples);
        double delayed = hermite(delayBuffer, ringMask, writePosition, readDelay);
        if (gritty) delayed += grit * NOISE_RMS * 1.7321 * noise();

        double filtered = delayed;
        for (int i = 0; i < 3; i++) {
            outFilter[i] += k * (filtered - outFilter[i]);
            filtered = outFilter[i];
        }
        double wet = filtered;
        if (gritty) {
            double delayedGain = hermite(gainBuffer, ringMask, writePosition, readDelay);
            wet = filtered / (delayedGain < 1e-3 ? 1e-3 : delayedGain);
        }

        double normalFeedback = 1.1 * feedbackPosition;
        feedbackSignal = loopGain(normalFeedback, wet, t) * wet;
        if (meter > ACTIVE_THR || hold) quiet = 0;
        else if (quiet < QUIET_MAX) quiet++;
        return wet;
    }

    /** True while hold is active or the unpatched delay tail still merits processing. */
    public boolean wantsProcessing() {
        if (hold) return true;
        int limit = (int) (1.2 * lastDelaySeconds * sampleRate) + 4800;
        return quiet < limit;
    }

    /** Current smoothed, CV-adjusted delay time in seconds. */
    public double currentDelaySeconds() { return lastDelaySeconds; }

    /** Clears audio history and hold/tail state while preserving parameters and PRNG position. */
    public void reset() {
        Arrays.fill(delayBuffer, 0.0);
        Arrays.fill(gainBuffer, MAX_G);
        Arrays.fill(envRing, 0.0);
        Arrays.fill(inFilter, 0.0);
        Arrays.fill(outFilter, 0.0);
        writePosition = 0;
        feedbackSignal = 0.0;
        envWritePosition = 0;
        envDivider = 0;
        holdLogGain = 0.0;
        holdEnvelope = 0.0;
        holdWasActive = false;
        holdAge = 0;
        meter = 0.0;
        quiet = QUIET_MAX;
        compander.reset();
        fresh = true;
    }

    public static double delaySecondsForPosition(double position) {
        double p = isFinite(position) ? clamp01(position) : 0.5;
        return MIN_DELAY_SECONDS * Math.exp(p * LOG_TIME_RATIO);
    }

    public static double timePositionForSeconds(double seconds) {
        double t = isFinite(seconds) ? clamp(seconds, MIN_DELAY_SECONDS, MAX_DELAY_SECONDS) : 0.1;
        return Math.log(t / MIN_DELAY_SECONDS) / LOG_TIME_RATIO;
    }

    private void trackLoop(double signal, double t) {
        double magnitude = signal < 0.0 ? -signal : signal;
        lastDelaySeconds = t;
        holdEnvelope += ENV_K / sampleRate * (magnitude - holdEnvelope);
        if (++envDivider >= ENV_DECIM) {
            envDivider = 0;
            envWritePosition = (envWritePosition + 1) % ENV_RING;
            envRing[envWritePosition] = holdEnvelope;
        }
    }

    private double loopGain(double normal, double wet, double t) {
        meter = wet < 0.0 ? -wet : wet;
        if (hold) {
            if (!holdWasActive) {
                holdWasActive = true;
                holdLogGain = 0.0;
                holdAge = 0;
            }
            holdAge++;
            int delayedEnvelopeSamples = (int) (t * (sampleRate / ENV_DECIM) + 0.5);
            delayedEnvelopeSamples = delayedEnvelopeSamples < 1 ? 1
                    : (delayedEnvelopeSamples > ENV_RING - 2 ? ENV_RING - 2 : delayedEnvelopeSamples);
            double past = envRing[(envWritePosition - delayedEnvelopeSamples + ENV_RING) % ENV_RING];
            double now = holdEnvelope;
            if (holdAge < (int) (t * sampleRate) + (int) (0.3 * sampleRate)) return Math.exp(holdLogGain);
            past = past < 1e-6 ? 1e-6 : past;
            now = now < 1e-6 ? 1e-6 : now;
            holdLogGain += (HOLD_KAPPA / (t * sampleRate)) * (Math.log(past) - Math.log(now));
            holdLogGain = clamp(holdLogGain, -0.357, 0.0953);
            return Math.exp(holdLogGain);
        }
        holdWasActive = false;
        return (!inputConnected && normal > TAIL_FB_MAX) ? TAIL_FB_MAX : normal;
    }

    private double noise() {
        long x = noiseState;
        x ^= x << 13;
        x ^= x >>> 7;
        x ^= x << 17;
        noiseState = x;
        return ((x >>> 11) * (1.0 / 9007199254740992.0)) * 2.0 - 1.0;
    }

    private static double limit(double x) {
        double u = x * (1.0 / LOOP_LIMIT);
        double q = u * u;
        q = q * q;
        return x / Math.sqrt(Math.sqrt(1.0 + q));
    }

    private static double hermite(double[] buffer, int mask, int wp, double delay) {
        double position = wp - delay;
        int index = (int) Math.floor(position);
        double fraction = position - index;
        double y0 = buffer[(index - 1) & mask];
        double y1 = buffer[index & mask];
        double y2 = buffer[(index + 1) & mask];
        double y3 = buffer[(index + 2) & mask];
        double c1 = 0.5 * (y2 - y0);
        double c2 = y0 - 2.5 * y1 + 2.0 * y2 - 0.5 * y3;
        double c3 = 0.5 * (y3 - y0) + 1.5 * (y1 - y2);
        return ((c3 * fraction + c2) * fraction + c1) * fraction + y1;
    }

    private static double dmCutoffHz(double seconds) {
        double effectiveTime = seconds < 0.3 ? seconds : 0.3;
        double clock = 4096.0 / (2.0 * effectiveTime);
        double cutoff = 0.45 * clock;
        return cutoff > 5000.0 ? 5000.0 : cutoff;
    }

    private static double lpCoef(double cutoff, double fs) {
        double f = cutoff < 10.0 ? 10.0 : (cutoff > 0.45 * fs ? 0.45 * fs : cutoff);
        return 1.0 - Math.exp(-2.0 * Math.PI * f / fs);
    }

    private static double clamp01(double value) { return clamp(value, 0.0, 1.0); }
    private static double clamp(double value, double low, double high) {
        return value < low ? low : (value > high ? high : value);
    }
    private static boolean isFinite(double value) { return !Double.isNaN(value) && !Double.isInfinite(value); }

    private static int nextPowerOfTwo(int value) {
        int result = 1;
        while (result < value) result <<= 1;
        return result;
    }

    private static final class Compander {
        private double envelope;
        private final double release;

        Compander(double fs) { release = Math.exp(-1.0 / (0.050 * fs)); }

        double gain(double input) {
            double magnitude = input < 0.0 ? -input : input;
            double decayed = envelope * release;
            envelope = magnitude > decayed ? magnitude : decayed;
            double level = envelope < 1e-5 ? 1e-5 : envelope;
            return Math.sqrt(COMP_REF / level);
        }

        void reset() { envelope = 0.0; }
    }
}
