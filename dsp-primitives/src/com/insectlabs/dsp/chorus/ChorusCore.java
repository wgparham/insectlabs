package com.insectlabs.dsp.chorus;

/*
 * ChorusCore: framework-free DSP core for a Dimension-style / CE-1-style chorus.
 *
 * Contains no Voltage Modular dependencies and no allocation after construction.
 * Java 8 language level. To embed in a Designer module, paste the body into the
 * [user-code-and-variables] region as "private static final class ChorusCore".
 *
 * Units: audio is normalized so that 1.0 = 5 V (Voltage's nominal audio peak).
 * The module layer converts volts <-> normalized at the jack boundary.
 *
 * Evidence tags used below:
 *   [DOC]   stated in the SDD-320 service notes
 *   [READ]  my reading of a schematic annotation (needs native/hardware confirmation)
 *   [MODEL] my modeling choice, not a hardware claim
 *   [GUESS] placeholder value, tune by ear
 */
public final class ChorusCore {

    public static final double SAMPLE_RATE = 48000.0;
    public static final double SAMPLE_PERIOD = 1.0 / SAMPLE_RATE;

    public enum Mode { DIMENSION, CE1 }

    /** How several simultaneously pressed Dimension buttons are combined. */
    public enum CombineLaw { AVERAGE, SUM_LFO, PARALLEL }

    // ---- Dimension mode tables ------------------------------------------------------

    /** [DOC] sweep minimum, ms, per button (service notes checking table, a). */
    private static final double[] DIM_MIN_MS = {8.0, 5.0, 6.0, 6.0};
    /** [DOC] sweep maximum, ms, per button (service notes checking table, b). */
    private static final double[] DIM_MAX_MS = {12.0, 10.0, 9.0, 9.0};
    /** [READ] LFO rate: schematic annotation "1,2 - 4 sec; 3,4 - 2 sec" read as the period. */
    private static final double[] DIM_RATE_HZ = {0.25, 0.25, 0.5, 0.5};
    /** [MODEL] distinct start phases so same-rate buttons are not identical when combined. */
    private static final double[] DIM_PHASE = {0.0, 0.25, 0.5, 0.75};

    private static final double MIN_DELAY_MS = 0.5;    // [MODEL] floor so a delay can never reach zero
    private static final double MAX_DELAY_MS = 20.0;
    private static final int BUFFER_SIZE = 2048;       // power of two, > 20 ms at 48 kHz + interpolation

    // [GUESS] wet-path bandwidth per mode
    private static final double DIM_BANDWIDTH_HZ = 12000.0;
    private static final double CE1_BANDWIDTH_HZ = 6500.0;

    // [MODEL] button weight smoothing (about 20 ms)
    private static final double WEIGHT_COEF = 1.0 - Math.exp(-SAMPLE_PERIOD / 0.020);
    // [MODEL] manual control smoothing (handbook section 10 suggests about 5 ms)
    private static final double CONTROL_COEF = 1.0 - Math.exp(-SAMPLE_PERIOD / 0.005);

    // [GUESS] CE-1 sweep around a center delay; intensity scales the half-range
    private static final double CE1_CENTER_MS = 3.8;
    private static final double CE1_MAX_HALF_RANGE_MS = 2.4;

    // ---- Parameters (written by the UI thread, read by the audio thread) --------------

    private volatile Mode mode = Mode.DIMENSION;
    private volatile CombineLaw combineLaw = CombineLaw.SUM_LFO;
    private volatile int buttonMask = 0;          // bit i = Dimension button i+1 is down
    private volatile boolean inputMono = false;
    private volatile double mix = 1.0;            // 0..1; 0 = exact dry
    private volatile double rateScale = 1.0;      // multiplies LFO rates, 0.05..4
    private volatile double depthScale = 1.0;     // scales sweep half-range, 0..2
    private volatile double noiseLevel = 0.0;     // normalized rms-ish amplitude, 0 = off
    private volatile boolean companderOn = false;
    private volatile double ce1RateHz = 1.0;
    private volatile double ce1Intensity = 0.5;   // 0..1
    private volatile boolean ce1Vibrato = false;
    private volatile double ce1Drive = 1.5;       // preamp gain ahead of soft clip

    public void setMode(Mode m) { mode = m; }
    public void setCombineLaw(CombineLaw law) { combineLaw = law; }
    public void setButtonMask(int mask) { buttonMask = mask & 0x0F; }
    public void setInputMono(boolean mono) { inputMono = mono; }
    public void setMix(double v) { mix = clampParameter(v, 0.0, 1.0, 1.0); }
    public void setRateScale(double v) { rateScale = clampParameter(v, 0.05, 4.0, 1.0); }
    public void setDepthScale(double v) { depthScale = clampParameter(v, 0.0, 2.0, 1.0); }
    public void setNoiseLevel(double v) { noiseLevel = clampParameter(v, 0.0, 0.05, 0.0); }
    public void setCompanderOn(boolean on) { companderOn = on; }
    public void setCe1RateHz(double v) { ce1RateHz = clampParameter(v, 0.05, 12.0, 1.0); }
    public void setCe1Intensity(double v) { ce1Intensity = clampParameter(v, 0.0, 1.0, 0.5); }
    public void setCe1Vibrato(boolean on) { ce1Vibrato = on; }
    public void setCe1Drive(double v) { ce1Drive = clampParameter(v, 0.1, 10.0, 1.5); }

    // ---- Outputs -----------------------------------------------------------------------

    public double outL;
    public double outR;

    // ---- State (audio thread only) -----------------------------------------------------

    private final Delay[] delay = { new Delay(), new Delay() };
    private final OnePole[][] preLp = new OnePole[2][2];
    private final OnePole[][] postLp = new OnePole[2][2];
    private final Compander[] compressor = { new Compander(), new Compander() };
    private final Compander[] expander = { new Compander(), new Compander() };
    private final double[] lfoPhase = new double[4];
    private double averagePhase = 0.0;
    private final double[] weight = new double[4];
    private double ce1Phase = 0.0;
    private double ce1RateSm = 1.0, ce1IntensitySm = 0.5;
    private long noiseState = 0x2545F4914F6CDD1DL;
    private Mode lastMode = Mode.DIMENSION;

    // per-sample scratch (fields, not locals, so nothing is allocated)
    private final double[] pa = new double[4], pb = new double[4], pk = new double[4];
    private final double[] pd0 = new double[4], pmid = new double[4], lfoOut = new double[4];
    private final double[] lastDelayMs = new double[2];   // test/diagnostic: delay used last sample

    public ChorusCore() {
        for (int c = 0; c < 2; c++) {
            for (int s = 0; s < 2; s++) {
                preLp[c][s] = new OnePole();
                postLp[c][s] = new OnePole();
            }
        }
        compressor[0].configure(true);  compressor[1].configure(true);
        expander[0].configure(false);   expander[1].configure(false);
        setBandwidth(DIM_BANDWIDTH_HZ);
        for (int i = 0; i < 4; i++) lfoPhase[i] = DIM_PHASE[i];
        averagePhase = DIM_PHASE[0];
    }

    /** Clears audio histories; keeps LFO phases (policy: free-running) and adopts current buttons. */
    public void reset() {
        for (int c = 0; c < 2; c++) {
            delay[c].clear();
            compressor[c].clear();
            expander[c].clear();
            for (int s = 0; s < 2; s++) { preLp[c][s].clear(); postLp[c][s].clear(); }
        }
        int mask = buttonMask;
        for (int i = 0; i < 4; i++) weight[i] = ((mask >> i) & 1);
        ce1RateSm = ce1RateHz;
        ce1IntensitySm = ce1Intensity;
    }

    /** Diagnostic: the delay (ms) used for channel 0 (left) or 1 (right) on the last sample. */
    public double getLastDelayMs(int channel) { return lastDelayMs[channel]; }

    // ---- Audio ---------------------------------------------------------------------------

    /** Inputs and outputs are normalized (1.0 = 5 V). Outputs land in outL / outR. */
    public void process(double inL, double inR) {
        inL = finiteOrZero(inL);
        inR = finiteOrZero(inR);
        Mode m = mode;
        if (m != lastMode) {                 // mode switch: clear wet-path history once
            lastMode = m;
            setBandwidth(m == Mode.DIMENSION ? DIM_BANDWIDTH_HZ : CE1_BANDWIDTH_HZ);
            reset();
        }
        if (m == Mode.DIMENSION) processDimension(inL, inR);
        else processCe1(inL);
    }

    private void processDimension(double inL, double inR) {
        final int mask = buttonMask;
        final CombineLaw law = combineLaw;
        final double rs = rateScale;
        final double ds = depthScale;
        final double mixNow = mix;
        final double noise = noiseLevel;
        final boolean compand = companderOn;

        // 1. smoothed button weights
        double wSum = 0.0;
        for (int i = 0; i < 4; i++) {
            weight[i] += WEIGHT_COEF * (((mask >> i) & 1) - weight[i]);
            wSum += weight[i];
        }
        final double engage = Math.min(1.0, wSum);

        // 2. per-button sweep parameters (depth scales the half-range about the center)
        for (int i = 0; i < 4; i++) {
            double mid = 0.5 * (DIM_MIN_MS[i] + DIM_MAX_MS[i]);
            pa[i] = Math.max(MIN_DELAY_MS, mid - ds * (mid - DIM_MIN_MS[i]));
            pb[i] = Math.min(MAX_DELAY_MS, mid + ds * (DIM_MAX_MS[i] - mid));
            pmid[i] = mid;
            pk[i] = (pb[i] - pa[i]) / (pb[i] + pa[i]);
            pd0[i] = 2.0 * pa[i] * pb[i] / (pa[i] + pb[i]);
            lfoPhase[i] = advance(lfoPhase[i], DIM_RATE_HZ[i] * rs);
            lfoOut[i] = triangle(lfoPhase[i]);
        }

        // AVERAGE law: one LFO, blended endpoints and rate
        double avgA = 0.0, avgB = 0.0, avgRate = 0.0;
        final double wSafe = Math.max(wSum, 1.0e-3);
        for (int i = 0; i < 4; i++) {
            double wn = weight[i] / wSafe;
            avgA += wn * pa[i];
            avgB += wn * pb[i];
            avgRate += wn * DIM_RATE_HZ[i];
        }
        averagePhase = advance(averagePhase, avgRate * rs);
        final double avgM = triangle(averagePhase);
        final double avgK = (avgB - avgA) / (avgB + avgA + 1.0e-9);
        final double avgD0 = 2.0 * avgA * avgB / (avgA + avgB + 1.0e-9);

        final double parallelDen = Math.max(1.0e-3, wSum < 1.0 ? wSum : Math.sqrt(wSum));
        final double sumDen = Math.sqrt(Math.max(1.0, wSum));

        for (int ch = 0; ch < 2; ch++) {
            final double sign = (ch == 0) ? 1.0 : -1.0;   // [DOC] second LFO phase is an inverter
            final double dry = inputMono ? 0.5 * (inL + inR) : (ch == 0 ? inL : inR);

            // write path: compress -> pre-filter -> delay line
            double x = dry;
            if (compand) x = compressor[ch].process(x);
            x = preLp[ch][1].lp(preLp[ch][0].lp(x));
            delay[ch].write(x);

            // read path by combine law
            double tap;
            double reportedMs;
            if (law == CombineLaw.AVERAGE) {
                reportedMs = delayMs(avgD0, avgK, sign * avgM);
                tap = delay[ch].read(reportedMs * (SAMPLE_RATE / 1000.0));
            } else if (law == CombineLaw.SUM_LFO) {
                double center = 0.0, offsets = 0.0;
                for (int i = 0; i < 4; i++) {
                    if (weight[i] < 1.0e-4) continue;
                    center += weight[i] * pmid[i];
                    offsets += weight[i] * (delayMs(pd0[i], pk[i], sign * lfoOut[i]) - pmid[i]);
                }
                reportedMs = center / wSafe + offsets / sumDen;
                reportedMs = Math.max(MIN_DELAY_MS, Math.min(MAX_DELAY_MS, reportedMs));
                tap = delay[ch].read(reportedMs * (SAMPLE_RATE / 1000.0));
            } else {                                      // PARALLEL: several taps, one shared line
                double acc = 0.0, msAcc = 0.0;
                for (int i = 0; i < 4; i++) {
                    if (weight[i] < 1.0e-4) continue;
                    double d = delayMs(pd0[i], pk[i], sign * lfoOut[i]);
                    d = Math.max(MIN_DELAY_MS, Math.min(MAX_DELAY_MS, d));
                    acc += weight[i] * delay[ch].read(d * (SAMPLE_RATE / 1000.0));
                    msAcc += weight[i] * d;
                }
                tap = acc / parallelDen;
                reportedMs = msAcc / wSafe;
            }
            lastDelayMs[ch] = reportedMs;

            // BBD output: noise -> post-filter -> expand
            double y = tap;
            if (noise > 0.0) y += noise * nextNoise();
            y = postLp[ch][1].lp(postLp[ch][0].lp(y));
            if (compand) y = expander[ch].process(y);

            double wet = (law == CombineLaw.PARALLEL) ? y : y * engage;
            double out = dry * (1.0 - 0.5 * mixNow * engage) + 0.5 * mixNow * wet;
            if (ch == 0) outL = out; else outR = out;
        }
    }

    private void processCe1(double in) {
        ce1RateSm += CONTROL_COEF * (ce1RateHz - ce1RateSm);
        ce1IntensitySm += CONTROL_COEF * (ce1Intensity - ce1IntensitySm);
        ce1Phase = advance(ce1Phase, ce1RateSm);
        double m = triangle(ce1Phase);

        // [MODEL] preamp: gain then soft clip (CE-1 input stage has roughly 50x gain; see brief)
        double x = softClip(in * ce1Drive);

        double half = ce1IntensitySm * CE1_MAX_HALF_RANGE_MS;
        double a = Math.max(MIN_DELAY_MS, CE1_CENTER_MS - half);
        double b = CE1_CENTER_MS + half;
        double k = (b - a) / (b + a);
        double d0 = 2.0 * a * b / (a + b);
        double ms = delayMs(d0, k, m);

        double w = preLp[0][1].lp(preLp[0][0].lp(x));
        delay[0].write(w);
        double tap = delay[0].read(ms * (SAMPLE_RATE / 1000.0));
        double noise = noiseLevel;
        if (noise > 0.0) tap += noise * nextNoise();
        double wet = postLp[0][1].lp(postLp[0][0].lp(tap));
        lastDelayMs[0] = ms;
        lastDelayMs[1] = ms;

        double out = ce1Vibrato ? wet : 0.5 * (x + wet);
        outL = out;
        outR = out;
    }

    // ---- Helpers -------------------------------------------------------------------------

    /** [MODEL] clock-controlled BBD: delay proportional to 1/f_clk, f_clk linear in the LFO.
     *  With m = +1 -> a (min), m = -1 -> b (max), so a..b endpoints match the documented sweep. */
    private static double delayMs(double d0, double k, double m) {
        return d0 / (1.0 + k * m);
    }

    private static double advance(double phase, double rateHz) {
        phase += rateHz * SAMPLE_PERIOD;
        if (phase >= 1.0) phase -= Math.floor(phase);
        return phase;
    }

    /** Triangle from phase 0..1, range -1..+1, starting at -1. */
    private static double triangle(double phase) {
        return phase < 0.5 ? 4.0 * phase - 1.0 : 3.0 - 4.0 * phase;
    }

    /** Rational tanh-like soft clip, exact +/-1 beyond |x| = 3, continuous and monotone. */
    private static double softClip(double x) {
        if (x >= 3.0) return 1.0;
        if (x <= -3.0) return -1.0;
        double x2 = x * x;
        return x * (27.0 + x2) / (27.0 + 9.0 * x2);
    }

    private double nextNoise() {                          // xorshift64*, uniform -1..1
        long s = noiseState;
        s ^= s >>> 12; s ^= s << 25; s ^= s >>> 27;
        noiseState = s;
        long r = s * 0x2545F4914F6CDD1DL;
        return (r >>> 11) * (2.0 / 9007199254740992.0) - 1.0;
    }

    private void setBandwidth(double hz) {
        for (int c = 0; c < 2; c++) {
            for (int s = 0; s < 2; s++) {
                preLp[c][s].setCutoff(hz);
                postLp[c][s].setCutoff(hz);
            }
        }
    }

    private static double finiteOrZero(double v) { return Double.isFinite(v) ? v : 0.0; }

    private static double clampParameter(double value, double min, double max, double fallback) {
        double safe = Double.isFinite(value) ? value : fallback;
        return Math.max(min, Math.min(max, safe));
    }

    // ---- Primitives ------------------------------------------------------------------------

    /** Ring buffer with 4-point Hermite fractional read. */
    private static final class Delay {
        private final double[] buf = new double[BUFFER_SIZE];
        private static final int MASK = BUFFER_SIZE - 1;
        private int w = 0;

        void clear() { java.util.Arrays.fill(buf, 0.0); w = 0; }
        void write(double x) { buf[w] = x; w = (w + 1) & MASK; }

        /** Returns the input from delaySamples ago; 0 is the sample just written. Needs >= 2. */
        double read(double delaySamples) {
            double d = delaySamples;
            int di = (int) d;
            double f = d - di;
            int i1 = (w - 1 - di) & MASK;
            double y0 = buf[(i1 + 1) & MASK];
            double y1 = buf[i1];
            double y2 = buf[(i1 - 1) & MASK];
            double y3 = buf[(i1 - 2) & MASK];
            double c1 = 0.5 * (y2 - y0);
            double c2 = y0 - 2.5 * y1 + 2.0 * y2 - 0.5 * y3;
            double c3 = 0.5 * (y3 - y0) + 1.5 * (y1 - y2);
            return ((c3 * f + c2) * f + c1) * f + y1;
        }
    }

    private static final class OnePole {
        private double a = 0.5, z = 0.0;
        void setCutoff(double fc) { a = 1.0 - Math.exp(-2.0 * Math.PI * fc / SAMPLE_RATE); }
        void clear() { z = 0.0; }
        double lp(double x) { z += a * (x - z); return z; }
    }

    /**
     * [MODEL] NE570-style 2:1 compressor / 1:2 expander half. Peak-ish envelope follower.
     * Floors are matched (expander floor = sqrt(compressor floor)) so the pair is unity for any level.
     * Attack/release are [GUESS].
     */
    static final class Compander {
        private static final double C_FLOOR = 1.0e-3;
        private static final double E_FLOOR = Math.sqrt(C_FLOOR);
        private static final double ATTACK = 1.0 - Math.exp(-SAMPLE_PERIOD / 0.005);
        private static final double RELEASE = 1.0 - Math.exp(-SAMPLE_PERIOD / 0.030);
        private double env = 0.0;
        private boolean isCompressor = true;

        void configure(boolean compressor) { isCompressor = compressor; }
        void clear() { env = 0.0; }

        double process(double x) {
            double a = Math.abs(x);
            env += (a > env ? ATTACK : RELEASE) * (a - env);
            if (isCompressor) return x / Math.sqrt(Math.max(env, C_FLOOR));
            return x * Math.max(env, E_FLOOR);
        }
    }
}
