package com.insectlabs.c234;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public final class BalancedModulatorChecks {
    private static final Class<?> DSP_CLASS = loadDspClass();
    private static final Constructor<?> DSP_CONSTRUCTOR = findConstructor();
    private static final Method PROCESS = findMethod("process", double.class, double.class);
    private static final Method SET_CONTROLS = findMethod("setControls", double.class, double.class);
    private static final Method GET_OUTPUT = findMethod("getOutput");

    private BalancedModulatorChecks() {}

    public static void main(String[] args) throws Exception {
        checkClose("UNBAL unity at +5 V", render(0.0, 1.0, 5.0, 5.0), 5.0, 1.0e-12);
        checkClose("UNBAL closes at 0 V", render(0.0, 1.0, 5.0, 0.0), 0.0, 1.0e-12);
        checkClose("UNBAL rejects negative control", render(0.0, 1.0, 5.0, -5.0), 0.0, 1.0e-12);
        checkClose("BAL unity at +5 V", render(1.0, 1.0, 5.0, 5.0), 5.0, 1.0e-12);
        checkClose("BAL inverts at -5 V", render(1.0, 1.0, 5.0, -5.0), -5.0, 1.0e-12);
        checkClose("BAL suppresses carrier at 0 V", render(1.0, 1.0, 5.0, 0.0), 0.0, 1.0e-12);
        checkClose("Amplitude reaches silence", render(0.0, 0.0, 5.0, 5.0), 0.0, 1.0e-12);

        checkClose("Carrier leak is capped at 6%", render(0.55, 1.0, 5.0, 0.0), 0.3, 1.0e-10);
        checkClose("Modulator leak is capped at 6%", render(0.38, 1.0, 0.0, 5.0), 0.3, 1.0e-10);
        checkClose("Combined center bleed stays at 12%", render(0.47, 1.0, 5.0, 5.0), 5.6, 1.0e-10);
        checkClose("UNBAL endpoint remains clean", render(0.0, 1.0, 5.0, 5.0), 5.0, 1.0e-12);
        checkClose("BAL endpoint remains clean", render(1.0, 1.0, 5.0, 5.0), 5.0, 1.0e-12);

        checkControlRamp();
        System.out.println("Balanced Modulator checks passed: 14 assertions.");
    }

    private static double render(double mode, double amplitude, double x, double y) throws Exception {
        Object dsp = DSP_CONSTRUCTOR.newInstance(mode, amplitude);
        PROCESS.invoke(dsp, x, y);
        return ((Number) GET_OUTPUT.invoke(dsp)).doubleValue();
    }

    private static void checkControlRamp() throws Exception {
        Object dsp = DSP_CONSTRUCTOR.newInstance(0.0, 1.0);
        PROCESS.invoke(dsp, 5.0, -5.0);
        SET_CONTROLS.invoke(dsp, 1.0, 1.0);
        double first = 0.0;
        double previous = 0.0;
        double last = 0.0;
        for (int sample = 0; sample < 240; sample++) {
            PROCESS.invoke(dsp, 5.0, -5.0);
            last = ((Number) GET_OUTPUT.invoke(dsp)).doubleValue();
            if (sample == 0) first = last;
            if (sample > 0 && Math.abs(last - previous) > 0.2) {
                throw new AssertionError("Mode ramp produced an abrupt sample step");
            }
            previous = last;
        }
        checkClose("Mode ramp starts at its previous state", first, 0.0, 0.1);
        checkClose("Mode ramp reaches BAL endpoint in 5 ms", last, -5.0, 1.0e-12);
    }

    private static Class<?> loadDspClass() {
        try {
            return Class.forName("com.insectlabs.c234.c234$BalancedModulatorDsp");
        } catch (ClassNotFoundException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static Constructor<?> findConstructor() {
        try {
            Constructor<?> constructor = DSP_CLASS.getDeclaredConstructor(double.class, double.class);
            constructor.setAccessible(true);
            return constructor;
        } catch (ReflectiveOperationException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static Method findMethod(String name, Class<?>... parameterTypes) {
        try {
            Method method = DSP_CLASS.getDeclaredMethod(name, parameterTypes);
            method.setAccessible(true);
            return method;
        } catch (ReflectiveOperationException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static void checkClose(String name, double actual, double expected, double tolerance) {
        if (!Double.isFinite(actual) || Math.abs(actual - expected) > tolerance) {
            throw new AssertionError(name + ": expected " + expected + ", got " + actual);
        }
    }
}
