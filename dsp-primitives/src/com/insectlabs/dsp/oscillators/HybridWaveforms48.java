package com.insectlabs.dsp.oscillators;

/** Approved OSC 1.0.0 waveform model; fixed 48 kHz host bandwidth policy.
 * Caller owns signed phase, gains, modulation interpolation and resampling.
 * Construct/load outside audio callbacks; callers supply finite bounded inputs. */
public final class HybridWaveforms48 {
    private static final double HOST_RATE = 48000.0;
    private static final int TABLE_SIZE = 32768;
    private static final int TABLE_MASK = TABLE_SIZE - 1;
    private static final int TABLE_BANKS = 14;
    private static final float[][][] WAVE_TABLES = makeWaveTables();
    private static final float[] SINE_TABLE = makeSineTable();
    private int mipBank;
    private double mipBlend, selectedBandwidthHz = Double.NaN;
    private static double safe(double value, double low, double high, double fallback) {
        return Double.isFinite(value) ? Math.max(low, Math.min(high, value)) : fallback;
    }

    public static double linearFmMean(double base0, double base1, double m0, double m1) {
        if (base0 == base1) return base0 * (m0 + m1) * 0.5;
        double a = Math.log(base1 / base0);
        double f0, f1;
        if (Math.abs(a) < 1e-4) {
            double a2 = a*a, a3 = a2*a;
            f0 = 1 + a/2 + a2/6 + a3/24;
            f1 = 0.5 + a/3 + a2/8 + a3/30;
        } else {
            double e = Math.exp(a);
            f0 = Math.expm1(a) / a;
            f1 = ((a - 1)*e + 1) / (a*a);
        }
        return base0 * (m0*f0 + (m1-m0)*f1);
    }

    public static double wrap(double x) {
        double wrapped = x - Math.floor(x);
        // A negative sub-ulp remainder can round to 1.0 instead of below it.
        return wrapped >= 1.0 ? 0.0 : wrapped;
    }

    private static double lookup(float[] table, double cycle) {
        double at = wrap(cycle) * TABLE_SIZE;
        int index = (int) at;
        double t = at - index;
        double a = table[(index - 1) & TABLE_MASK], b = table[index];
        double c = table[(index + 1) & TABLE_MASK], d = table[(index + 2) & TABLE_MASK];
        return b + 0.5 * t * (c - a + t * (2*a - 5*b + 4*c - d + t * (3*(b-c) + d-a)));
    }

    public void selectBandwidth(double hz) {
        if (hz == selectedBandwidthHz) return;
        selectedBandwidthHz = hz;
        // Harmonic support stays below 19.2 kHz. Two adjacent conservative
        // banks crossfade continuously; no bank with support above the limit is used.
        double log = Math.log(Math.max(1.0, 0.4 * HOST_RATE / Math.max(1e-9, Math.abs(hz)))) / Math.log(2.0);
        mipBank = Math.min(TABLE_BANKS - 1, (int) log);
        mipBlend = Math.min(1.0, log - mipBank);
        mipBlend = mipBlend * mipBlend * (3.0 - 2.0 * mipBlend);
        if (mipBank == 0) mipBlend = 1.0;
    }

    private double wave(int kind, double cycle) {
        double high = lookup(WAVE_TABLES[kind][mipBank], cycle);
        if (mipBank == 0) return high;
        double low = lookup(WAVE_TABLES[kind][mipBank - 1], cycle);
        return low + mipBlend * (high - low);
    }

    private static double harmonicGain(double hz, int harmonic) {
        double x = safe((0.4 * HOST_RATE / Math.max(1e-9, Math.abs(hz)) - harmonic) / harmonic, 0, 1, 0);
        return x*x*(3.0 - 2.0*x);
    }

    public double sineWave(double cycle, double amount, double hz) {
        double sine = lookup(SINE_TABLE, cycle);
        // Small even/odd residuals model an imperfectly adjusted shaper.
        sine += 0.007 * harmonicGain(hz, 2) * lookup(SINE_TABLE, 2 * cycle)
              + 0.003 * harmonicGain(hz, 3) * lookup(SINE_TABLE, 3 * cycle);
        // At +/-1 retain the positive/negative half, respectively. Band-limited
        // absolute sine preserves its DC component and avoids a sampled hard kink.
        double normalization = (Math.sqrt(amount * amount + 0.04) - 0.2) / (Math.sqrt(1.04) - 0.2);
        return (sine + amount * wave(2, cycle)) / (1.0 + normalization);
    }

    public double triangleWave(double cycle, double amount) {
        double width = 0.5 - 0.49 * amount;
        // Integral of a rising saw has derivative equal to that saw. Its shifted
        // difference produces triangle -> either ramp with band-limited corners.
        return -(wave(1, cycle) - wave(1, cycle - width)) / (width * (1.0 - width));
    }

    public double pulseWave(double cycle, double amount) {
        double width = 0.5 + 0.49 * amount;
        return wave(0, cycle - width) - wave(0, cycle) + 2.0 * width - 1.0;
    }

    private static float[] makeSineTable() {
        float[] result = new float[TABLE_SIZE];
        for (int i = 0; i < TABLE_SIZE; ++i) result[i] = (float) Math.sin(2*Math.PI*i/TABLE_SIZE);
        return result;
    }

    private static float[][][] makeWaveTables() {
        float[][][] tables = new float[3][TABLE_BANKS][TABLE_SIZE];
        double[] real = new double[TABLE_SIZE], imaginary = new double[TABLE_SIZE];
        for (int kind = 0; kind < 3; ++kind) for (int bank = 0; bank < TABLE_BANKS; ++bank) {
            java.util.Arrays.fill(real, 0); java.util.Arrays.fill(imaginary, 0);
            int harmonics = 1 << bank;
            if (kind == 2) real[0] = 2.0 / Math.PI;
            for (int n = 1; n <= harmonics; ++n) {
                double taper = n <= harmonics * 0.5 || harmonics == 1 ? 1.0
                        : 0.5 + 0.5 * Math.cos(Math.PI * (2.0*n/harmonics - 1.0));
                if (kind == 0) {
                    imaginary[n] = taper / (Math.PI*n);
                    imaginary[TABLE_SIZE-n] = -imaginary[n];
                } else if (kind == 1) {
                    real[n] = taper / (2*Math.PI*Math.PI*n*n);
                    real[TABLE_SIZE-n] = real[n];
                } else if ((n & 1) == 0) {
                    real[n] = -taper * 2.0 / (Math.PI * (n*n - 1.0));
                    real[TABLE_SIZE-n] = real[n];
                }
            }
            inverseFourier(real, imaginary);
            for (int i = 0; i < TABLE_SIZE; ++i) tables[kind][bank][i] = (float) real[i];
        }
        return tables;
    }

    private static void inverseFourier(double[] real, double[] imaginary) {
        for (int i = 1, j = 0; i < TABLE_SIZE; ++i) {
            int bit = TABLE_SIZE >> 1;
            for (; (j & bit) != 0; bit >>= 1) j ^= bit;
            j ^= bit;
            if (i < j) {
                double t = real[i]; real[i] = real[j]; real[j] = t;
                t = imaginary[i]; imaginary[i] = imaginary[j]; imaginary[j] = t;
            }
        }
        for (int length = 2; length <= TABLE_SIZE; length <<= 1) {
            double wr = Math.cos(2*Math.PI/length), wi = Math.sin(2*Math.PI/length);
            for (int start = 0; start < TABLE_SIZE; start += length) {
                double ur = 1, ui = 0;
                for (int j = 0; j < length/2; ++j) {
                    int a = start+j, b = a+length/2;
                    double vr = real[b]*ur - imaginary[b]*ui, vi = real[b]*ui + imaginary[b]*ur;
                    real[b] = real[a]-vr; imaginary[b] = imaginary[a]-vi;
                    real[a] += vr; imaginary[a] += vi;
                    double next = ur*wr-ui*wi; ui = ur*wi+ui*wr; ur = next;
                }
            }
        }
    }
}
