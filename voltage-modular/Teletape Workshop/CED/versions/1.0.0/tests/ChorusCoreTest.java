import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

/** Headless checks of ChorusCore. Run: java -cp out ChorusCoreTest [demo.wav] */
public final class ChorusCoreTest {

    private static int failures = 0;
    private static final double FS = ChorusCore.SAMPLE_RATE;

    private static void check(String name, boolean ok, String detail) {
        System.out.println((ok ? "PASS  " : "FAIL  ") + name + (detail.isEmpty() ? "" : "  [" + detail + "]"));
        if (!ok) failures++;
    }

    private static String f(double v) { return String.format("%.4f", v); }

    private static ChorusCore fresh() {
        ChorusCore c = new ChorusCore();
        c.setMode(ChorusCore.Mode.DIMENSION);
        c.setButtonMask(0);
        c.reset();
        return c;
    }

    private static void settle(ChorusCore c, int samples) {
        for (int i = 0; i < samples; i++) c.process(0.0, 0.0);
    }

    // ---- T1: documented sweep ranges and LFO period, per button, both channels ----------------
    private static void testSweeps() {
        double[] a = {8, 5, 6, 6}, b = {12, 10, 9, 9}, period = {4, 4, 2, 2};
        for (int btn = 0; btn < 4; btn++) {
            ChorusCore c = fresh();
            c.setButtonMask(1 << btn);
            c.setCombineLaw(ChorusCore.CombineLaw.SUM_LFO);
            settle(c, 48000);                               // let the button weight settle
            double minL = 1e9, maxL = -1e9, minR = 1e9, maxR = -1e9;
            double mirrorErr = 0;
            int crossings = 0; long first = -1, last = -1;
            double mid = 0.5 * (a[btn] + b[btn]), prev = 0;
            int n = (int) (FS * period[btn] * 3);           // three periods
            for (int i = 0; i < n; i++) {
                c.process(0.0, 0.0);
                double dl = c.getLastDelayMs(0), dr = c.getLastDelayMs(1);
                minL = Math.min(minL, dl); maxL = Math.max(maxL, dl);
                minR = Math.min(minR, dr); maxR = Math.max(maxR, dr);
                if (i > 0 && prev < mid && dl >= mid) {
                    crossings++; if (first < 0) first = i; last = i;
                }
                prev = dl;
            }
            double measuredPeriod = crossings > 1 ? (last - first) / FS / (crossings - 1) : -1;
            String d = "L " + f(minL) + ".." + f(maxL) + "  R " + f(minR) + ".." + f(maxR)
                    + "  period " + f(measuredPeriod) + " s";
            boolean ok = Math.abs(minL - a[btn]) < 0.01 && Math.abs(maxL - b[btn]) < 0.01
                    && Math.abs(minR - a[btn]) < 0.01 && Math.abs(maxR - b[btn]) < 0.01
                    && Math.abs(measuredPeriod - period[btn]) < 0.01;
            check("T1 button " + (btn + 1) + " sweep " + a[btn] + ".." + b[btn] + " ms, period " + period[btn] + " s", ok, d);
        }
    }

    // ---- T2: opposite polarity and mono-sum behavior -----------------------------------------
    private static void testPolarity() {
        ChorusCore c = fresh();
        c.setButtonMask(1 << 1);
        settle(c, 48000);
        double minAvg = 1e9, maxAvg = -1e9;
        for (int i = 0; i < (int) (FS * 4); i++) {
            c.process(0, 0);
            double dl = c.getLastDelayMs(0), dr = c.getLastDelayMs(1);
            double avg = 0.5 * (dl + dr);
            minAvg = Math.min(minAvg, avg); maxAvg = Math.max(maxAvg, avg);
        }
        // first-order cancellation: L and R move in opposite directions, mean moves only 2nd order
        check("T2 button 2: L/R delays move in opposite directions (mean varies far less than each)",
                (maxAvg - minAvg) < 0.5 * (10.0 - 5.0),
                "mean of L,R spans " + f(minAvg) + ".." + f(maxAvg) + " ms vs each 5..10 ms");
    }

    // ---- T3: delay actually applied to audio (impulse, depth 0) ------------------------------
    private static void testImpulseDelay() {
        double[] mid = {10.0, 7.5, 7.5, 7.5};
        double filterDelay = 4 * (1.0 - (1.0 - Math.exp(-2 * Math.PI * 12000.0 / FS)))
                / (1.0 - Math.exp(-2 * Math.PI * 12000.0 / FS));   // 4 one-poles, DC group delay in samples
        for (int btn = 0; btn < 4; btn++) {
            ChorusCore c = fresh();
            c.setButtonMask(1 << btn);
            c.setDepthScale(0.0);
            c.setMix(1.0);
            settle(c, 48000);
            int t0 = 500, n = 2000;
            double[] out = new double[n], dry = new double[n];
            for (int i = 0; i < n; i++) {
                double x = (i == t0) ? 1.0 : 0.0;
                c.process(x, 0.0);
                out[i] = c.outL;
                dry[i] = x;
            }
            // wet = 2*out - dry (since out = 0.5 dry + 0.5 wet at mix 1, engaged)
            double sum = 0, wsum = 0;
            for (int i = t0 + 10; i < n; i++) {
                double w = 2.0 * out[i] - dry[i];
                sum += w; wsum += w * (i - t0);
            }
            double centroid = wsum / sum;
            double expected = mid[btn] * FS / 1000.0 + filterDelay;
            check("T3 button " + (btn + 1) + " impulse delay at depth 0 = " + mid[btn] + " ms (+filter delay)",
                    Math.abs(centroid - expected) < 0.25,
                    "measured " + f(centroid) + " samples, expected " + f(expected));
        }
    }

    // ---- T4: combinations stay bounded, finite, and above the delay floor ---------------------
    private static void testCombinations() {
        int[][] masks = {{0b1001}, {0b1111}, {0b0011}, {0b0110}};
        String[] names = {"1+4", "1+2+3+4", "1+2", "2+3"};
        ChorusCore.CombineLaw[] laws = ChorusCore.CombineLaw.values();
        for (int mi = 0; mi < masks.length; mi++) {
            for (ChorusCore.CombineLaw law : laws) {
                ChorusCore c = fresh();
                c.setCombineLaw(law);
                c.setButtonMask(masks[mi][0]);
                c.setMix(1.0);
                double minD = 1e9, maxD = -1e9, peak = 0;
                boolean finite = true;
                int n = (int) (FS * 8);
                for (int i = 0; i < n; i++) {
                    double x = 0.5 * Math.sin(2 * Math.PI * 220.0 * i / FS);
                    c.process(x, x);
                    if (i > 48000) {
                        minD = Math.min(minD, Math.min(c.getLastDelayMs(0), c.getLastDelayMs(1)));
                        maxD = Math.max(maxD, Math.max(c.getLastDelayMs(0), c.getLastDelayMs(1)));
                    }
                    finite &= Double.isFinite(c.outL) && Double.isFinite(c.outR);
                    peak = Math.max(peak, Math.max(Math.abs(c.outL), Math.abs(c.outR)));
                }
                check("T4 " + names[mi] + " / " + law, finite && minD >= 0.5 && maxD <= 20.0 && peak < 0.75,
                        "delay " + f(minD) + ".." + f(maxD) + " ms, peak " + f(peak) + " (input 0.5)");
            }
        }
    }

    // ---- T5: compander unity and noise reduction ----------------------------------------------
    private static void testCompander() {
        double worst = 0;
        double[] levels = {0.3, 0.1, 0.03, 0.01, 0.003};
        for (double amp : levels) {
            ChorusCore.Compander cp = new ChorusCore.Compander(), ex = new ChorusCore.Compander();
            cp.configure(true); ex.configure(false);
            double inSq = 0, outSq = 0;
            int n = 48000 * 2;
            for (int i = 0; i < n; i++) {
                double x = amp * Math.sin(2 * Math.PI * 1000.0 * i / FS);
                double y = ex.process(cp.process(x));
                if (i > 48000) { inSq += x * x; outSq += y * y; }
            }
            double gainDb = 10 * Math.log10(outSq / inSq);
            worst = Math.max(worst, Math.abs(gainDb));
        }
        check("T5a compressor->expander pair is unity within 1 dB, 0.3 .. 0.003 (-24..-64 dBFS-ish)", worst < 1.0,
                "worst deviation " + f(worst) + " dB");

        double[] rms = new double[2];
        for (int on = 0; on < 2; on++) {
            ChorusCore c = fresh();
            c.setButtonMask(1);
            c.setMix(1.0);
            c.setNoiseLevel(0.01);
            c.setCompanderOn(on == 1);
            settle(c, 48000);
            double s = 0; int n = 48000 * 2;
            for (int i = 0; i < n; i++) { c.process(0, 0); s += c.outL * c.outL; }
            rms[on] = Math.sqrt(s / n);
        }
        double reductionDb = 20 * Math.log10(rms[1] / rms[0]);
        check("T5b compander lowers BBD noise on silence by roughly 30 dB", reductionDb < -25 && reductionDb > -35,
                "reduction " + f(reductionDb) + " dB");
    }

    // ---- T6: dry exactness, bad input recovery ------------------------------------------------
    private static void testRobustness() {
        ChorusCore c = fresh();
        c.setMix(0.0);
        c.setButtonMask(0b1111);
        double worst = 0;
        for (int i = 0; i < 48000; i++) {
            double x = 0.3 * Math.sin(2 * Math.PI * 330.0 * i / FS);
            c.process(x, -x);
            worst = Math.max(worst, Math.max(Math.abs(c.outL - x), Math.abs(c.outR + x)));
        }
        check("T6a mix 0 passes dry exactly", worst < 1e-12, "max error " + worst);

        c = fresh();
        c.setButtonMask(0b0101);
        c.setMix(1.0);
        boolean ok = true;
        for (int i = 0; i < 20000; i++) {
            double x = (i % 777 == 0) ? Double.NaN : (i % 1111 == 0 ? Double.POSITIVE_INFINITY : 0.2);
            c.process(x, x);
            ok &= Double.isFinite(c.outL) && Double.isFinite(c.outR);
        }
        for (int i = 0; i < 4000; i++) { c.process(0.2, 0.2); ok &= Double.isFinite(c.outL); }
        check("T6b NaN/Inf input keeps outputs finite and recovers", ok, "");

        c = fresh();
        c.setButtonMask(0b1111);
        c.setRateScale(Double.NaN); c.setDepthScale(Double.POSITIVE_INFINITY); c.setMix(Double.NaN);
        ok = true;
        for (int i = 0; i < 48000; i++) { c.process(0.1, 0.1); ok &= Double.isFinite(c.outL); }
        check("T6c nonfinite parameters fall back to safe values", ok, "");
    }

    // ---- T7: CE-1 mode ---------------------------------------------------------------------
    private static void testCe1() {
        ChorusCore c = fresh();
        c.setMode(ChorusCore.Mode.CE1);
        c.setCe1Intensity(1.0);
        c.setCe1RateHz(2.0);
        settle(c, 48000);
        double minD = 1e9, maxD = -1e9, peak = 0; boolean finite = true;
        for (int i = 0; i < 48000 * 4; i++) {
            double x = 0.6 * Math.sin(2 * Math.PI * 440.0 * i / FS);
            c.process(x, 0);
            minD = Math.min(minD, c.getLastDelayMs(0)); maxD = Math.max(maxD, c.getLastDelayMs(0));
            finite &= Double.isFinite(c.outL);
            peak = Math.max(peak, Math.abs(c.outL));
        }
        check("T7 CE-1 delay spans intensity range [GUESS 1.4..6.2 ms], finite, bounded",
                finite && Math.abs(minD - 1.4) < 0.02 && Math.abs(maxD - 6.2) < 0.02 && peak <= 1.0,
                "delay " + f(minD) + ".." + f(maxD) + " ms, peak " + f(peak));
    }

    // ---- T8: indicative cost -------------------------------------------------------------------
    private static void benchmark() {
        ChorusCore c = fresh();
        c.setCombineLaw(ChorusCore.CombineLaw.PARALLEL);
        c.setButtonMask(0b1111);
        c.setCompanderOn(true);
        c.setNoiseLevel(0.001);
        int n = 48000 * 20;
        for (int i = 0; i < 48000 * 5; i++) c.process(0.2, 0.2);        // warm up JIT
        long t = System.nanoTime();
        double sink = 0;
        for (int i = 0; i < n; i++) { c.process(0.2 * Math.sin(i * 0.05), 0.1); sink += c.outL; }
        double sec = (System.nanoTime() - t) / 1e9;
        System.out.println("INFO  worst-case config (PARALLEL, 4 buttons, compander, noise): "
                + String.format("%.0f", sec / n * 1e9) + " ns/sample = "
                + String.format("%.2f", 100.0 * (sec / n) * FS) + " % of one core's real-time budget"
                + "  (headless JVM here; NOT a Voltage Modular measurement)  sink=" + (sink > 1e12));
    }

    // ---- Demo render ---------------------------------------------------------------------------
    private static final class Seg {
        final String label; final ChorusCore.Mode mode; final int mask; final ChorusCore.CombineLaw law;
        final boolean vibrato; final boolean dryOnly;
        Seg(String label, ChorusCore.Mode mode, int mask, ChorusCore.CombineLaw law, boolean vibrato, boolean dryOnly) {
            this.label = label; this.mode = mode; this.mask = mask; this.law = law;
            this.vibrato = vibrato; this.dryOnly = dryOnly;
        }
    }

    private static void renderDemo(String path) throws IOException {
        ChorusCore.CombineLaw A = ChorusCore.CombineLaw.AVERAGE, S = ChorusCore.CombineLaw.SUM_LFO,
                P = ChorusCore.CombineLaw.PARALLEL;
        ChorusCore.Mode D = ChorusCore.Mode.DIMENSION, C = ChorusCore.Mode.CE1;
        Seg[] segs = {
            new Seg("dry", D, 0, S, false, true),
            new Seg("D 1", D, 1, S, false, false),
            new Seg("D 2", D, 2, S, false, false),
            new Seg("D 3", D, 4, S, false, false),
            new Seg("D 4", D, 8, S, false, false),
            new Seg("D 1+4 average", D, 9, A, false, false),
            new Seg("D 1+4 summed LFOs", D, 9, S, false, false),
            new Seg("D 1+4 parallel taps", D, 9, P, false, false),
            new Seg("D 1+2+3+4 summed LFOs", D, 15, S, false, false),
            new Seg("CE-1 chorus", C, 0, S, false, false),
            new Seg("CE-1 vibrato", C, 0, S, true, false),
        };
        int segLen = (int) (FS * 4.0);
        double[] left = new double[segs.length * segLen], right = new double[segs.length * segLen];
        ChorusCore core = new ChorusCore();
        for (int s = 0; s < segs.length; s++) {
            core.setMode(segs[s].mode);
            core.setButtonMask(segs[s].mask);
            core.setCombineLaw(segs[s].law);
            core.setCe1Vibrato(segs[s].vibrato);
            core.setCe1Drive(1.0);
            core.setCe1Intensity(0.6);
            core.setCe1RateHz(0.8);
            core.setMix(1.0);
            core.reset();
            for (int i = 0; i < segLen; i++) {
                double x = pluckChord(i);
                if (segs[s].dryOnly) { left[s * segLen + i] = x; right[s * segLen + i] = x; continue; }
                core.process(x, x);
                left[s * segLen + i] = core.outL;
                right[s * segLen + i] = core.outR;
            }
        }
        double peak = 1e-9;
        for (int i = 0; i < left.length; i++) peak = Math.max(peak, Math.max(Math.abs(left[i]), Math.abs(right[i])));
        double g = 0.7 / peak;
        int bytes = left.length * 4;
        try (DataOutputStream o = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(path)))) {
            o.writeBytes("RIFF"); o.writeInt(Integer.reverseBytes(36 + bytes)); o.writeBytes("WAVEfmt ");
            o.writeInt(Integer.reverseBytes(16)); o.writeShort(Short.reverseBytes((short) 1));
            o.writeShort(Short.reverseBytes((short) 2)); o.writeInt(Integer.reverseBytes(48000));
            o.writeInt(Integer.reverseBytes(48000 * 4)); o.writeShort(Short.reverseBytes((short) 4));
            o.writeShort(Short.reverseBytes((short) 16)); o.writeBytes("data"); o.writeInt(Integer.reverseBytes(bytes));
            for (int i = 0; i < left.length; i++) {
                o.writeShort(Short.reverseBytes((short) Math.round(left[i] * g * 32767.0)));
                o.writeShort(Short.reverseBytes((short) Math.round(right[i] * g * 32767.0)));
            }
        }
        StringBuilder sb = new StringBuilder("DEMO  4 s each: ");
        for (int s = 0; s < segs.length; s++) sb.append(s == 0 ? "" : " | ").append(s * 4).append("s ").append(segs[s].label);
        System.out.println(sb);
    }

    /** Plucked three-note saw chord retriggered every 1.5 s. */
    private static double pluckChord(int i) {
        double t = (i / FS) % 1.5;
        double env = Math.exp(-t * 2.2) * Math.min(1.0, t * 400.0);
        double[] freq = {146.83, 220.0, 293.66};
        double s = 0;
        for (double fr : freq) {
            double p1 = (t * fr) % 1.0, p2 = (t * fr * 1.004) % 1.0;
            s += (2 * p1 - 1) + (2 * p2 - 1);
        }
        return 0.12 * env * s;
    }

    public static void main(String[] args) throws IOException {
        testSweeps();
        testPolarity();
        testImpulseDelay();
        testCombinations();
        testCompander();
        testRobustness();
        testCe1();
        benchmark();
        if (args.length > 0) renderDemo(args[0]);
        System.out.println(failures == 0 ? "ALL CHECKS PASSED" : failures + " CHECK(S) FAILED");
        System.exit(failures == 0 ? 0 : 1);
    }
}
