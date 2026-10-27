package com.insectlabs.switches;

public final class Sw3CoreTest {
    private static int checks;

    private Sw3CoreTest() {}

    private static void check(boolean condition, String message) {
        checks++;
        if (!condition) throw new AssertionError(message);
    }

    private static void near(double actual, double expected, double tolerance, String message) {
        check(Double.isFinite(actual) && Math.abs(actual - expected) <= tolerance,
                message + ": expected " + expected + ", got " + actual);
    }

    private static Sw3Core routeX(double cv, boolean button, boolean latch,
                                 double x1, boolean connected1, double x2, boolean connected2,
                                 double referenceX, double fadeMs) {
        Sw3Core core = new Sw3Core();
        core.process(cv, button, latch, x1, connected1, x2, connected2, referenceX,
                0.0, false, false, 0.0, false, 0.0, fadeMs);
        return core;
    }

    private static void testFadeMapping() {
        near(Sw3Core.fadeMsFromPos(-1.0), 0.0, 0.0, "fade lower clamp");
        near(Sw3Core.fadeMsFromPos(0.0), 0.0, 0.0, "fade minimum");
        near(Sw3Core.fadeMsFromPos(1.0), 200.0, 1e-12, "fade maximum");
        near(Sw3Core.fadeMsFromPos(2.0), 200.0, 1e-12, "fade upper clamp");
        double previous = -1.0;
        for (int i = 0; i <= 100; i++) {
            double position = i / 100.0;
            double time = Sw3Core.fadeMsFromPos(position);
            check(time >= previous, "fade mapping monotonic at " + position);
            near(Sw3Core.posFromFadeMs(time), position, 1e-12, "fade inverse at " + position);
            previous = time;
        }
        check(Sw3Core.fadeMsFromPos(0.5) < 6.0, "half-travel fade remains short");
        check(Sw3Core.fadeMsFromPos(0.25) < Sw3Core.fadeMsFromPos(0.5), "exponential fade taper");
        near(Sw3Core.fadeMsFromPos(Double.NaN), 0.0, 0.0, "nonfinite position maps to zero");
        near(Sw3Core.posFromFadeMs(Double.POSITIVE_INFINITY), 0.0, 0.0, "nonfinite time maps to zero");
    }

    private static void testMomentaryAndHysteresis() {
        Sw3Core core = routeX(0.0, false, false, 2.0, true, -3.0, true, 0.0, 0.0);
        near(core.outX, 2.0, 0.0, "X primary route at rest");
        check(!core.gx.secondary, "X starts primary");
        check(!core.gx.gateOut, "gate output low at rest");

        core.process(3.0, false, false, 2.0, true, -3.0, true, 0.0,
                0.0, false, false, 0.0, false, 0.0, 0.0);
        near(core.outX, -3.0, 0.0, "X secondary route at gate threshold");
        check(core.gx.secondary && core.gx.lamp, "momentary gate selects secondary and lights lamp");
        near(core.outGateX, 10.0, 0.0, "gate output voltage");

        core.process(2.0, false, false, 2.0, true, -3.0, true, 0.0,
                0.0, false, false, 0.0, false, 0.0, 0.0);
        check(core.gx.secondary, "Schmitt gate remains high inside hysteresis band");
        near(core.outGateX, 10.0, 0.0, "gate output remains high in hysteresis band");

        core.process(1.79, false, false, 2.0, true, -3.0, true, 0.0,
                0.0, false, false, 0.0, false, 0.0, 0.0);
        check(!core.gx.secondary, "Schmitt gate releases below 1.8 V");
        near(core.outX, 2.0, 0.0, "X returns to primary after gate release");
        near(core.outGateX, 10.0, 0.0, "trigger pulse sustains gate output after a brief gate");
        for (int i = 0; i < Sw3Core.PULSE_SAMPLES; i++) {
            core.process(1.79, false, false, 2.0, true, -3.0, true, 0.0,
                    0.0, false, false, 0.0, false, 0.0, 0.0);
        }
        near(core.outGateX, 0.0, 0.0, "gate output releases after its minimum pulse");

        core.process(2.99, false, false, 2.0, true, -3.0, true, 0.0,
                0.0, false, false, 0.0, false, 0.0, 0.0);
        check(!core.gx.secondary, "gate does not trigger below 3 V when low");
        core.process(3.0, false, false, 2.0, true, -3.0, true, 0.0,
                0.0, false, false, 0.0, false, 0.0, 0.0);
        check(core.gx.secondary, "gate triggers at exactly 3 V");

        core.reset();
        core.process(0.0, true, false, 2.0, true, -3.0, true, 0.0,
                0.0, false, false, 0.0, false, 0.0, 0.0);
        near(core.outX, -3.0, 0.0, "button selects momentary secondary");
        near(core.outGateX, 10.0, 0.0, "button drives gate output while held");
        core.process(0.0, false, false, 2.0, true, -3.0, true, 0.0,
                0.0, false, false, 0.0, false, 0.0, 0.0);
        near(core.outX, 2.0, 0.0, "button release returns momentary path to primary");
    }

    private static void testLatchAndState() {
        Sw3Core core = routeX(0.0, false, true, 1.0, true, 4.0, true, 0.0, 0.0);
        check(!core.gx.secondary, "latched mode starts primary");
        core.process(3.0, false, true, 1.0, true, 4.0, true, 0.0,
                0.0, false, false, 0.0, false, 0.0, 0.0);
        check(core.gx.secondary, "latch toggles on rising gate");
        core.process(5.0, false, true, 1.0, true, 4.0, true, 0.0,
                0.0, false, false, 0.0, false, 0.0, 0.0);
        check(core.gx.secondary, "held gate does not retrigger latch");
        core.process(0.0, false, true, 1.0, true, 4.0, true, 0.0,
                0.0, false, false, 0.0, false, 0.0, 0.0);
        core.process(3.0, false, true, 1.0, true, 4.0, true, 0.0,
                0.0, false, false, 0.0, false, 0.0, 0.0);
        check(!core.gx.secondary, "second rising gate toggles latch back");

        core.reset();
        core.process(0.0, false, true, 1.0, true, 4.0, true, 0.0,
                0.0, false, false, 0.0, false, 0.0, 0.0);
        core.process(0.0, true, true, 1.0, true, 4.0, true, 0.0,
                0.0, false, false, 0.0, false, 0.0, 0.0);
        check(core.gx.secondary, "button rising edge toggles latch");
        core.process(0.0, false, true, 1.0, true, 4.0, true, 0.0,
                0.0, false, false, 0.0, false, 0.0, 0.0);
        core.process(0.0, true, true, 1.0, true, 4.0, true, 0.0,
                0.0, false, false, 0.0, false, 0.0, 0.0);
        check(!core.gx.secondary, "second button rising edge toggles latch back");

        byte[] state = core.getState();
        check(state.length == 2, "serialized state length");
        Sw3Core restored = new Sw3Core();
        restored.setState(state);
        restored.process(0.0, false, true, 1.0, true, 4.0, true, 0.0,
                0.0, false, false, 0.0, false, 0.0, 0.0);
        check(!restored.gx.secondary, "restored latch state preserved without synthetic edge");
        core.setState(new byte[] {1, 1});
        core.process(0.0, false, true, 1.0, true, 4.0, true, 0.0,
                0.0, false, true, 1.0, true, 0.0, 0.0);
        check(core.gx.secondary && core.gy.secondary, "both latch states restored");
        core.setState(new byte[] {0});
        check(core.gx.secondary && core.gy.secondary, "short state is safely ignored");
    }

    private static void testPulseWidth() {
        Sw3Core.Gate gate = new Sw3Core.Gate();
        gate.step(0.0, false, false, 1.0);
        gate.step(3.0, false, false, 1.0);
        check(gate.gateOut, "rising edge starts gate output");
        for (int sample = 1; sample < Sw3Core.PULSE_SAMPLES; sample++) {
            gate.step(0.0, false, false, 1.0);
            check(gate.gateOut, "minimum 5 ms pulse sample " + sample);
        }
        gate.step(0.0, false, false, 1.0);
        check(!gate.gateOut, "5 ms pulse ends after exactly 240 samples");
        check(Sw3Core.PULSE_SAMPLES == 240, "pulse sample count at 48 kHz");
    }

    private static void testFadeTransition() {
        Sw3Core core = new Sw3Core();
        double fadeMs = 100.0;
        core.process(0.0, false, false, 0.0, true, 1.0, true, 0.0,
                0.0, false, false, 0.0, false, 0.0, fadeMs);
        core.process(3.0, false, false, 0.0, true, 1.0, true, 0.0,
                0.0, false, false, 0.0, false, 0.0, fadeMs);
        double step = 1000.0 / (fadeMs * Sw3Core.FS);
        near(core.gx.mix, step, 1e-15, "fade begins one sample after trigger");
        near(core.outX, step, 1e-15, "crossfade output starts at expected gain");
        for (int i = 1; i < 4800; i++) {
            core.process(3.0, false, false, 0.0, true, 1.0, true, 0.0,
                    0.0, false, false, 0.0, false, 0.0, fadeMs);
        }
        near(core.gx.mix, 1.0, 1e-12, "fade reaches target in 100 ms");
        near(core.outX, 1.0, 1e-12, "crossfade reaches secondary endpoint");
        core.process(0.0, false, false, 0.0, true, 1.0, true, 0.0,
                0.0, false, false, 0.0, false, 0.0, 0.0);
        near(core.gx.mix, 0.0, 0.0, "zero fade is a hard switch");
    }

    private static void testRoutesAndNormalization() {
        Sw3Core core = new Sw3Core();
        core.process(0.0, false, false, 5.0, false, 7.0, false, 4.25,
                0.0, false, false, 0.0, false, -3.5, 0.0);
        near(core.outX, 4.25, 0.0, "unpatched X primary uses reference trim");
        near(core.outY1, -3.5, 0.0, "unpatched Y uses reference trim on primary output");
        near(core.outY2, 0.0, 0.0, "inactive Y secondary output is silent");

        core.process(3.0, false, false, 5.0, false, 7.0, true, 4.25,
                0.0, false, false, -2.0, true, -3.5, 0.0);
        near(core.outX, 7.0, 0.0, "connected secondary X input selected when primary is unpatched");
        near(core.outY1, -2.0, 0.0, "Y primary connected input routes to first output");

        core.process(3.0, false, false, 5.0, false, 7.0, true, 4.25,
                3.0, false, false, 2.0, true, -3.5, 0.0);
        near(core.outX, 7.0, 0.0, "X alternate route remains independent");
        near(core.outY1, 0.0, 0.0, "Y secondary route mutes first output");
        near(core.outY2, 2.0, 0.0, "Y secondary route feeds second output");

        core.process(0.0, false, false, 0.0, true, 0.0, true, 100.0,
                0.0, false, false, 0.0, false, -100.0, 0.0);
        near(core.outX, 0.0, 0.0, "connected X primary zero is preserved");
        near(core.outY1, -10.0, 0.0, "reference trim clamps to minus ten volts");

        core.process(3.0, false, false, 0.0, true, Double.NaN, true, 0.0,
                Double.NaN, false, false, Double.NEGATIVE_INFINITY, true, 0.0, 0.0);
        check(Double.isFinite(core.outX) && Double.isFinite(core.outY1) && Double.isFinite(core.outY2),
                "nonfinite inputs cannot leak to outputs");
        near(core.outX, 0.0, 0.0, "nonfinite X source is sanitized");
        near(core.outY2, 0.0, 0.0, "nonfinite Y source is sanitized");
    }

    private static void testBypass() {
        Sw3Core core = new Sw3Core();
        core.process(0.0, false, true, 1.0, true, 2.0, true, 0.0,
                0.0, false, true, 4.0, true, 0.0, 50.0);
        core.process(3.0, false, true, 1.0, true, 2.0, true, 0.0,
                3.0, false, true, 4.0, true, 0.0, 50.0);
        check(core.gx.secondary && core.gy.secondary, "latches are active before bypass");
        core.bypass(-2.0, true, 7.0, true);
        near(core.outX, -2.0, 0.0, "bypass copies X primary input");
        near(core.outY1, 7.0, 0.0, "bypass copies Y input to primary output");
        near(core.outY2, 0.0, 0.0, "bypass silences Y secondary output");
        near(core.outGateX, 0.0, 0.0, "bypass silences X gate output");
        near(core.outGateY, 0.0, 0.0, "bypass silences Y gate output");
        check(!core.gx.gateOut && !core.gx.lamp && !core.gy.gateOut && !core.gy.lamp,
                "bypass clears gate and lamp state");
        core.process(0.0, false, true, 3.0, true, 4.0, true, 0.0,
                0.0, false, true, 0.0, false, 0.0, 0.0);
        near(core.outX, 4.0, 0.0, "resume preserves latched X route without replaying stale gate");
        near(core.outY2, 0.0, 0.0, "unpatched Y source remains zero on resumed secondary route");

        core.bypass(100.0, false, 100.0, false);
        near(core.outX, 0.0, 0.0, "unpatched bypass X is silent");
        near(core.outY1, 0.0, 0.0, "unpatched bypass Y is silent");
    }

    public static void main(String[] args) {
        testFadeMapping();
        testMomentaryAndHysteresis();
        testLatchAndState();
        testPulseWidth();
        testFadeTransition();
        testRoutesAndNormalization();
        testBypass();
        System.out.println("PASS: " + checks + " SW3 core checks");
    }
}
