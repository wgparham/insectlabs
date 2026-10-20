package com.insectlabs.ced;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import voltage.controllers.*;
import voltage.core.*;

/**
 * Drives the assembled module through its real entry points against STUB SDK classes.
 * Verifies my wiring/mapping logic only. It cannot establish real SDK behavior.
 */
public final class ModuleWiringTest {

    private static int failures = 0;

    private static void check(String name, boolean ok, String detail) {
        System.out.println((ok ? "PASS  " : "FAIL  ") + name + (detail.isEmpty() ? "" : "  [" + detail + "]"));
        if (!ok) failures++;
    }

    // ---- reflection helpers ---------------------------------------------------------------
    private static void setField(Object o, String name, Object v) throws Exception {
        Field f = o.getClass().getDeclaredField(name); f.setAccessible(true); f.set(o, v);
    }

    private static Object getField(Object o, String name) throws Exception {
        Field f = o.getClass().getDeclaredField(name); f.setAccessible(true); return f.get(o);
    }

    /** Wraps a nested ChorusCore instance (module's own, or a fresh reference copy). */
    private static final class CoreRef {
        final Object core; final Class<?> cls;
        CoreRef(Object core) { this.core = core; this.cls = core.getClass(); }
        static CoreRef fresh(Class<?> cls) throws Exception {
            Constructor<?> c = cls.getDeclaredConstructor(); c.setAccessible(true); return new CoreRef(c.newInstance());
        }
        void call(String name, Class<?> argType, Object arg) throws Exception {
            Method m = cls.getMethod(name, argType); m.setAccessible(true); m.invoke(core, arg);
        }
        @SuppressWarnings({"unchecked", "rawtypes"})
        void enumCall(String name, String enumName, String constant) throws Exception {
            for (Class<?> k : cls.getDeclaredClasses()) {
                if (k.getSimpleName().equals(enumName)) {
                    Method m = cls.getMethod(name, k); m.setAccessible(true);
                    m.invoke(core, Enum.valueOf((Class) k, constant));
                }
            }
        }
        void process(double l, double r) throws Exception {
            Method m = cls.getMethod("process", double.class, double.class); m.setAccessible(true); m.invoke(core, l, r);
        }
        void reset() throws Exception { Method m = cls.getMethod("reset"); m.setAccessible(true); m.invoke(core); }
        double outL() throws Exception { return (Double) cls.getField("outL").get(core); }
        double outR() throws Exception { return (Double) cls.getField("outR").get(core); }
    }

    // ---- fixture ----------------------------------------------------------------------------
    private static final double RATE_DEFAULT_POS = Math.log(20.0) / Math.log(80.0);
    private static final double CE_RATE_DEFAULT_POS = Math.log(20.0) / Math.log(240.0);
    private static final double CE_DRIVE_DEFAULT_POS = Math.log(15.0) / Math.log(100.0);

    private static ced build() throws Exception {
        ced m = new ced(1L, new VoltageObjects());
        String[] jacks = {"audioInLeft", "audioInRight", "audioOutLeft", "audioOutRight", "rateCvIn", "depthCvIn"};
        for (String j : jacks) setField(m, j, new VoltageAudioJack(j, m));
        for (int i = 1; i <= 4; i++) setField(m, "dimensionButton" + i, new VoltageToggle("b" + i, m));
        setField(m, "exclusiveToggle", new VoltageToggle("exclusive", m));
        setField(m, "companderToggle", new VoltageToggle("compander", m));
        for (String s : new String[]{"modeSwitch", "combineSwitch", "inputSwitch", "vibratoSwitch"})
            setField(m, s, new VoltageSwitch(s, m, 0.0));
        setField(m, "mixKnob", new VoltageKnob("mix", m, 1.0));
        setField(m, "rateKnob", new VoltageKnob("rate", m, RATE_DEFAULT_POS));
        setField(m, "depthKnob", new VoltageKnob("depth", m, 1.0));
        setField(m, "noiseKnob", new VoltageKnob("noise", m, 0.0));
        setField(m, "ceRateKnob", new VoltageKnob("ceRate", m, CE_RATE_DEFAULT_POS));
        setField(m, "ceIntensityKnob", new VoltageKnob("ceIntensity", m, 0.5));
        setField(m, "ceDriveKnob", new VoltageKnob("ceDrive", m, CE_DRIVE_DEFAULT_POS));
        m.Initialize();
        return m;
    }

    private static VoltageAudioJack jack(Object m, String n) throws Exception { return (VoltageAudioJack) getField(m, n); }
    private static VoltageToggle toggle(Object m, String n) throws Exception { return (VoltageToggle) getField(m, n); }
    private static VoltageKnob knob(Object m, String n) throws Exception { return (VoltageKnob) getField(m, n); }

    private static CoreRef refFor(ced m, int mask, double rateScale, double depthScale) throws Exception {
        Class<?> cls = Class.forName("com.insectlabs.dsp.chorus.ChorusCore");
        CoreRef r = CoreRef.fresh(cls);
        r.enumCall("setMode", "Mode", "DIMENSION");
        r.enumCall("setCombineLaw", "CombineLaw", "SUM_LFO");
        r.call("setMix", double.class, 1.0);
        r.call("setRateScale", double.class, rateScale);
        r.call("setDepthScale", double.class, depthScale);
        r.call("setNoiseLevel", double.class, 0.0);
        r.call("setCe1RateHz", double.class, 1.0);
        r.call("setCe1Intensity", double.class, 0.5);
        r.call("setCe1Drive", double.class, 1.5);
        r.reset();                                          // weights start at 0 ...
        r.call("setButtonMask", int.class, mask);           // ... then fade in, as in the module
        return r;
    }

    private static double input(int i) { return 4.0 * Math.sin(2 * Math.PI * 330.0 * i / 48000.0); }

    /** Runs the module for n samples comparing against ref; returns the largest output difference in volts. */
    private static double compare(ced m, CoreRef ref, int start, int n) throws Exception {
        double worst = 0;
        for (int i = start; i < start + n; i++) {
            double x = input(i);
            jack(m, "audioInLeft").value = x;
            m.ProcessSample();
            ref.process(x / 5.0, x / 5.0);
            worst = Math.max(worst, Math.abs(jack(m, "audioOutLeft").value - ref.outL() * 5.0));
            worst = Math.max(worst, Math.abs(jack(m, "audioOutRight").value - ref.outR() * 5.0));
        }
        return worst;
    }

    // ---- tests ------------------------------------------------------------------------------
    private static void testDefaultsAndRouting() throws Exception {
        ced m = build();
        jack(m, "audioInLeft").connected = true;
        double worst = 0;
        for (int i = 0; i < 4800; i++) {
            double x = input(i);
            jack(m, "audioInLeft").value = x;
            m.ProcessSample();
            worst = Math.max(worst, Math.max(Math.abs(jack(m, "audioOutLeft").value - x),
                    Math.abs(jack(m, "audioOutRight").value - x)));
        }
        check("W1 no buttons: exact dry, R normalled to L, volts pass through unscaled", worst < 1e-12, "max error " + worst);
    }

    private static void testButtonWiring() throws Exception {
        ced m = build();
        jack(m, "audioInLeft").connected = true;
        toggle(m, "dimensionButton2").userClick(true);
        double defaultScale = ced.rateScaleFromPosition(RATE_DEFAULT_POS);
        CoreRef ref = refFor(m, 0b0010, defaultScale, 1.0);
        double err = compare(m, ref, 0, 96000);
        check("W2 button 2 through Notify matches reference core (+/-4 V input, 2 s)", err < 1e-9, "max diff " + err + " V");
    }

    private static void testCv() throws Exception {
        ced m = build();
        jack(m, "audioInLeft").connected = true;
        jack(m, "rateCvIn").connected = true; jack(m, "rateCvIn").value = 2.5;
        jack(m, "depthCvIn").connected = true; jack(m, "depthCvIn").value = -2.5;
        toggle(m, "dimensionButton1").userClick(true);
        double scale = ced.rateScaleFromPosition(RATE_DEFAULT_POS) * Math.pow(2.0, 0.4 * 2.5);
        CoreRef ref = refFor(m, 0b0001, scale, 1.0 + 0.2 * -2.5);
        double err = compare(m, ref, 0, 96000);
        check("W3 rate CV +2.5 V = x2 rate, depth CV -2.5 V = -0.5 depth", err < 1e-9, "max diff " + err + " V");
        // unpatched CV is neutral (W2 already ran with CV jacks unconnected and matched the reference)
    }

    private static int mask(Object m) throws Exception {
        java.lang.reflect.Field f = m.getClass().getDeclaredField("pressedMask"); f.setAccessible(true);
        return f.getInt(m);
    }

    private static void testExclusive(boolean notifyOnSet) throws Exception {
        ced m = build();
        for (int i = 1; i <= 4; i++) toggle(m, "dimensionButton" + i).notifyOnSetValue = notifyOnSet;
        String tag = "(SetValue " + (notifyOnSet ? "re-notifies" : "silent") + ")";
        toggle(m, "dimensionButton1").userClick(true);
        toggle(m, "dimensionButton4").userClick(true);
        boolean combos = mask(m) == 0b1001 && toggle(m, "dimensionButton1").setValueCalls == 0;
        toggle(m, "exclusiveToggle").userClick(true);        // 1+4 down, last pressed 4 -> keep 4, release 1
        boolean collapsed = mask(m) == 0b1000 && toggle(m, "dimensionButton1").setValueCalls == 1;
        toggle(m, "dimensionButton2").userClick(true);       // exclusive: pressing 2 releases 4
        boolean swapped = mask(m) == 0b0010 && toggle(m, "dimensionButton4").setValueCalls == 1;
        toggle(m, "dimensionButton2").userClick(false);
        boolean cleared = mask(m) == 0;
        toggle(m, "exclusiveToggle").userClick(false);
        toggle(m, "dimensionButton1").userClick(true);
        toggle(m, "dimensionButton3").userClick(true);
        boolean combosAgain = mask(m) == 0b0101;
        check("W4 exclusive logic " + tag, combos && collapsed && swapped && cleared && combosAgain,
                "combos=" + combos + " collapsed=" + collapsed + " swapped=" + swapped + " cleared=" + cleared
                        + " combosAgain=" + combosAgain);
    }

    private static void testBypass() throws Exception {
        ced m = build();
        jack(m, "audioInLeft").connected = true;
        jack(m, "audioInLeft").value = 1.234;
        m.ProcessBypassedSample();
        boolean normalled = jack(m, "audioOutLeft").value == 1.234 && jack(m, "audioOutRight").value == 1.234;
        jack(m, "audioInRight").connected = true; jack(m, "audioInRight").value = -2.0;
        m.ProcessBypassedSample();
        boolean channels = jack(m, "audioOutLeft").value == 1.234 && jack(m, "audioOutRight").value == -2.0;
        jack(m, "audioInLeft").value = Double.NaN;
        m.ProcessBypassedSample();
        boolean sane = jack(m, "audioOutLeft").value == 0.0;
        check("W5a bypass: direct routing, R normalled to L when unpatched, channel identity kept, NaN -> 0",
                normalled && channels && sane, "");

        // resume: histories cleared once on return, LFO phase preserved
        m = build();
        jack(m, "audioInLeft").connected = true;
        toggle(m, "dimensionButton3").userClick(true);
        double scale = ced.rateScaleFromPosition(RATE_DEFAULT_POS);
        CoreRef ref = refFor(m, 0b0100, scale, 1.0);
        double err1 = compare(m, ref, 0, 48000);
        for (int i = 0; i < 25; i++) m.ProcessBypassedSample();
        ref.reset();
        double err2 = compare(m, ref, 48000, 4800);
        check("W5b resume after bypass equals reference reset once, then continued", err1 < 1e-9 && err2 < 1e-9,
                "before " + err1 + ", after " + err2);
    }

    private static void testTooltipsAndTypedEntry() throws Exception {
        ced m = build();
        String[] knobs = {"rateKnob", "ceRateKnob", "ceDriveKnob", "noiseKnob"};
        double[] positions = {0.0, 0.25, 0.5, 0.8, 1.0};
        boolean tips = true, roundTrip = true;
        for (String kn : knobs) {
            for (double p : positions) {
                knob(m, kn).SetValue(p);
                String t = m.GetTooltipText(knob(m, kn));
                tips &= t != null && t.length() > 5;
                double typed;
                if (kn.equals("rateKnob")) typed = ced.rateScaleFromPosition(p);
                else if (kn.equals("ceRateKnob")) typed = ced.ceRateHzFromPosition(p);
                else if (kn.equals("ceDriveKnob")) typed = ced.ceDriveFromPosition(p);
                else typed = p == 0.0 ? -120.0 : 20.0 * Math.log10(ced.noiseLevelFromPosition(p));
                knob(m, kn).SetValue(0.123);                      // disturb, then type the displayed value back
                m.EditComponentValue(knob(m, kn), typed, "");
                roundTrip &= Math.abs(knob(m, kn).GetValue() - p) < 1e-9;
            }
        }
        check("W6a tooltips exist and typed entry inverts the displayed mapping for 4 converted knobs",
                tips && roundTrip, "tooltips=" + tips + " roundTrip=" + roundTrip);

        boolean safe = true;
        for (String kn : knobs) {
            knob(m, kn).SetValue(0.4);
            m.EditComponentValue(knob(m, kn), Double.NaN, "abc");
            double v = knob(m, kn).GetValue();
            safe &= Double.isFinite(v) && v >= 0.0 && v <= 1.0;
            m.EditComponentValue(knob(m, kn), 1.0e9, "huge");
            v = knob(m, kn).GetValue();
            safe &= Double.isFinite(v) && v >= 0.0 && v <= 1.0;
        }
        check("W6b invalid / out-of-range typed values land inside the knob range", safe, "");
        check("W6c unhandled controls fall through to the base tooltip", m.GetTooltipText(knob(m, "mixKnob")) != null, "");
    }

    private static void testModeAndRobustness() throws Exception {
        ced m = build();
        jack(m, "audioInLeft").connected = true;
        ((VoltageSwitch) getField(m, "modeSwitch")).SetValue(1.0);
        boolean ok = true, mono = true;
        for (int i = 0; i < 48000; i++) {
            jack(m, "audioInLeft").value = (i % 5000 == 0) ? Double.NaN : input(i);
            m.ProcessSample();
            double l = jack(m, "audioOutLeft").value, r = jack(m, "audioOutRight").value;
            ok &= Double.isFinite(l) && Double.isFinite(r);
            mono &= l == r;
        }
        check("W7 CE-1 via mode switch: finite with NaN input bursts, identical L/R", ok && mono, "");
    }

    public static void main(String[] args) throws Exception {
        testDefaultsAndRouting();
        testButtonWiring();
        testCv();
        testExclusive(true);
        testExclusive(false);
        testBypass();
        testTooltipsAndTypedEntry();
        testModeAndRobustness();
        System.out.println(failures == 0 ? "ALL WIRING CHECKS PASSED (stub SDK)" : failures + " WIRING CHECK(S) FAILED");
        System.exit(failures == 0 ? 0 : 1);
    }
}
