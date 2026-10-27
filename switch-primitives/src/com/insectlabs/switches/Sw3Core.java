package com.insectlabs.switches;

public final class Sw3Core {
        public static final double FS = 48000.0;
        public static final double GATE_HIGH_V = 3.0, GATE_LOW_V = 1.8, GATE_OUT_V = 10.0, PULSE_S = 0.005, REF_MAX_V = 10.0, FADE_MAX_MS = 200.0, FADE_DEFAULT_MS = 0.25;
        public static final int PULSE_SAMPLES = (int) Math.round(PULSE_S * FS);

        /** FADE knob taper: exponential, 0 at the CCW end, 200 ms at CW. FADE_RATIO 1521 puts 5 ms at half travel (0.14 ms at 10 %, 0.7 ms at 25 %, 32 ms at 75 %). [GUESS] */
        public static final double FADE_RATIO = 1521.0;
        public static double fadeMsFromPos(double p) {
            p = clamp(fin(p), 0.0, 1.0);
            return FADE_MAX_MS * (Math.pow(FADE_RATIO, p) - 1.0) / (FADE_RATIO - 1.0);
        }
        public static double posFromFadeMs(double ms) {
            ms = clamp(fin(ms), 0.0, FADE_MAX_MS);
            return Math.log(1.0 + ms * (FADE_RATIO - 1.0) / FADE_MAX_MS) / Math.log(FADE_RATIO);
        }

        private static double fin(double v) { return (Double.isNaN(v) || Double.isInfinite(v)) ? 0.0 : v; }
        private static double clamp(double v, double lo, double hi) { return v < lo ? lo : (v > hi ? hi : v); }

        /** One gate / state machine. */
        public static final class Gate {
            public boolean secondary, gateOut, lamp;
            public double mix;                                   // 0 = PRIMARY route, 1 = SECONDARY route, ramps between
            private boolean cvHigh, cvPrev, btnPrev, primed;
            private int pulse;

            void unprime() { primed = false; pulse = 0; gateOut = false; lamp = false; }

            void step(double cv, boolean btn, boolean latch, double fadeStep) {
                cv = fin(cv);
                if (cvHigh) { if (cv < GATE_LOW_V) cvHigh = false; } else if (cv >= GATE_HIGH_V) cvHigh = true;
                boolean high = cvHigh || btn;                    // gate OUT / lamp follow this
                boolean sec = cvHigh ^ btn;                      // MOMENTARY state: XOR
                if (!primed) {                                   // first sample after reset or bypass: take the inputs as they are, no edge
                    cvPrev = cvHigh; btnPrev = btn; primed = true;
                    if (!latch) secondary = sec;
                    pulse = 0;
                    mix = secondary ? 1.0 : 0.0;
                } else {
                    boolean rise = (cvHigh && !cvPrev) || (btn && !btnPrev);
                    if (latch) {
                        if (rise) secondary = !secondary;
                    } else {
                        secondary = sec;
                    }
                    if (rise) pulse = PULSE_SAMPLES;                       // triggers on rising edges only, none when MOMENTARY falls back
                    cvPrev = cvHigh; btnPrev = btn;
                }
                if (primed) { double t = secondary ? 1.0 : 0.0; mix = mix < t ? Math.min(t, mix + fadeStep) : Math.max(t, mix - fadeStep); }
                gateOut = high || pulse > 0;
                if (pulse > 0) pulse--;
                lamp = secondary || gateOut;
            }
        }

        public final Gate gx = new Gate(), gy = new Gate();
        public double outX, outY1, outY2, outGateX, outGateY;

        /** Latched state for presets: 2 bytes, 1 = SECONDARY (X, then Y). */
        public byte[] getState() { return new byte[] { (byte) (gx.secondary ? 1 : 0), (byte) (gy.secondary ? 1 : 0) }; }
        public void setState(byte[] b) {
            if (b == null || b.length < 2) return;
            gx.secondary = b[0] != 0; gy.secondary = b[1] != 0;
            gx.unprime(); gy.unprime();                          // the next sample re-reads the gates without an edge; MOMENTARY follows the gate again
        }

        public void reset() { gx.unprime(); gy.unprime(); gx.secondary = false; gy.secondary = false; outX = outY1 = outY2 = outGateX = outGateY = 0.0; }

        /** X: gate jack, button, latch flag, IN X1 (connected?), IN X2 (connected?), reference volts. Y: gate jack, button, latch flag, IN Y (connected?), reference volts. */
        public void process(double cvX, boolean btnX, boolean latchX, double x1, boolean c1, double x2, boolean c2, double refX,
                            double cvY, boolean btnY, boolean latchY, double y, boolean cy, double refY) {
            process(cvX, btnX, latchX, x1, c1, x2, c2, refX, cvY, btnY, latchY, y, cy, refY, FADE_DEFAULT_MS);
        }

        /** fadeMs: crossfade time in ms from the FADE knob (0..200); 0 or less = hard switch. */
        public void process(double cvX, boolean btnX, boolean latchX, double x1, boolean c1, double x2, boolean c2, double refX,
                            double cvY, boolean btnY, boolean latchY, double y, boolean cy, double refY, double fadeMs) {
            fadeMs = clamp(fin(fadeMs), 0.0, FADE_MAX_MS);
            double fadeStep = fadeMs <= 0.0 ? 1.0 : Math.min(1.0, 1000.0 / (fadeMs * FS));
            gx.step(cvX, btnX, latchX, fadeStep);
            gy.step(cvY, btnY, latchY, fadeStep);
            double mx = gx.mix, my = gy.mix;
            double xp = c1 ? fin(x1) : clamp(fin(refX), -REF_MAX_V, REF_MAX_V), xs = c2 ? fin(x2) : 0.0;
            outX = (1.0 - mx) * xp + mx * xs;
            double yin = cy ? fin(y) : clamp(fin(refY), -REF_MAX_V, REF_MAX_V);       // IN Y unpatched: the Y reference is the source
            outY1 = (1.0 - my) * yin;
            outY2 = my * yin;
            outGateX = gx.gateOut ? GATE_OUT_V : 0.0;
            outGateY = gy.gateOut ? GATE_OUT_V : 0.0;
        }

        /** Bypass: no switching. The PRIMARY routes carry the inputs; gates are silent. The next processed sample re-reads gates without edges. */
        public void bypass(double x1, boolean c1, double y, boolean cy) {
            gx.unprime(); gy.unprime();
            outX = c1 ? fin(x1) : 0.0;
            outY1 = cy ? fin(y) : 0.0;
            outY2 = 0.0; outGateX = 0.0; outGateY = 0.0;
        }
    }
