package com.insectlabs.laboratory.faderdistr;

public final class FaderDistrCoreTest {
    private static long checks;

    public static void main(String[] args) {
        testLaw(false);
        testLaw(true);
        testSmoothing();
        testBypassAndResume();
        testInvalidConstruction();
        System.out.println("PASS Panfade core: " + checks + " checks");
    }

    private static void testLaw(boolean equalPower) {
        FaderDistrCore core = new FaderDistrCore(equalPower, 48_000);
        double center = equalPower ? Math.sqrt(0.5) : 0.5;

        core.process(0, 8, 3, -5);
        near(8 * center, core.getLeftOutput());
        near(8 * center, core.getRightOutput());
        near((3 - 5) * center, core.getMixOutput());

        core.processBypassed(0, 0);
        core.process(-1, 8, 3, -5);
        near(8, core.getLeftOutput());
        near(0, core.getRightOutput());
        near(3, core.getMixOutput());

        core.processBypassed(0, 0);
        core.process(1, 8, 3, -5);
        near(0, core.getLeftOutput());
        near(8, core.getRightOutput());
        near(-5, core.getMixOutput());

        core.processBypassed(0, 0);
        core.process(0, 0, 4, 4);
        near(equalPower ? 4 * Math.sqrt(2) : 4, core.getMixOutput());

        core.processBypassed(0, 0);
        core.process(-2, 1, 2, 3);
        near(1, core.getLeftOutput());
        near(0, core.getRightOutput());
        near(2, core.getMixOutput());
    }

    private static void testSmoothing() {
        FaderDistrCore core = new FaderDistrCore(false, 1_000);
        core.process(0, 1, 1, 0);
        core.process(1, 1, 1, 0);
        near(0.4, core.getLeftOutput());
        near(0.6, core.getRightOutput());
        near(0.4, core.getMixOutput());
        for (int i = 0; i < 4; i++) core.process(1, 1, 1, 0);
        near(0, core.getLeftOutput());
        near(1, core.getRightOutput());
    }

    private static void testBypassAndResume() {
        FaderDistrCore core = new FaderDistrCore(true, 48_000);
        core.process(-1, 1, 2, 3);
        core.processBypassed(7, -4);
        near(7, core.getLeftOutput());
        near(7, core.getRightOutput());
        near(-4, core.getMixOutput());
        core.process(1, 5, 6, 8);
        near(0, core.getLeftOutput());
        near(5, core.getRightOutput());
        near(8, core.getMixOutput());
    }

    private static void testInvalidConstruction() {
        expectFailure(() -> new FaderDistrCore(false, 0));
        expectFailure(() -> new FaderDistrCore(false, Double.NaN));
        FaderDistrCore core = new FaderDistrCore(false, 48_000);
        core.process(Double.NaN, 2, 3, 5);
        near(1, core.getLeftOutput());
        near(1, core.getRightOutput());
        near(4, core.getMixOutput());
    }

    private static void expectFailure(Runnable operation) {
        checks++;
        try {
            operation.run();
            throw new AssertionError("Expected failure");
        } catch (IllegalArgumentException expected) {
            // Expected.
        }
    }

    private static void near(double expected, double actual) {
        checks++;
        if (!Double.isFinite(actual) || Math.abs(expected - actual) > 1e-12) {
            throw new AssertionError(expected + " != " + actual);
        }
    }
}
