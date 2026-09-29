package com.insectlabs.laboratory.faderdistr;

public final class FaderDistrCoreTest {
    private static long checks;

    public static void main(String[] args) {
        testLaw(false);
        testLaw(true);
        testSmoothing();
        testBypassAndResume();
        testRelayVoicing();
        testInvalidConstruction();
        System.out.println("PASS Fader|Distr core: " + checks + " checks");
    }

    private static void testLaw(boolean equalPower) {
        FaderDistrCore core = new FaderDistrCore(equalPower, 48_000);
        double center = equalPower ? Math.sqrt(0.5) : 0.5;

        core.process(0, 1.0, 0.75, -1.0);
        near(1.0 * center, core.getLeftOutput());
        near(1.0 * center, core.getRightOutput());
        near((0.75 - 1.0) * center, core.getMixOutput());

        core.processBypassed(0, 0);
        core.process(-1, 1.0, 0.75, -1.0);
        near(1.0, core.getLeftOutput());
        near(0, core.getRightOutput());
        near(0.75, core.getMixOutput());

        core.processBypassed(0, 0);
        core.process(1, 1.0, 0.75, -1.0);
        near(0, core.getLeftOutput());
        near(1.0, core.getRightOutput());
        near(-1.0, core.getMixOutput());

        core.processBypassed(0, 0);
        core.process(0, 0, 1, 1);
        near(equalPower ? Math.sqrt(2) : 1, core.getMixOutput());

        core.processBypassed(0, 0);
        core.process(-2, 1, 1, 1);
        near(1, core.getLeftOutput());
        near(0, core.getRightOutput());
        near(1, core.getMixOutput());
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
        core.process(-1, 1, 1, 1);
        core.processBypassed(0.75, -0.5);
        near(0.75, core.getLeftOutput());
        near(0.75, core.getRightOutput());
        near(-0.5, core.getMixOutput());
        core.process(1, 1, 0.5, 0.75);
        near(0, core.getLeftOutput());
        near(1, core.getRightOutput());
        near(0.75, core.getMixOutput());
    }

    private static void testRelayVoicing() {
        FaderDistrCore core = new FaderDistrCore(false, 48_000);
        core.processBypassed(0, 0);

        // At a fully selected output, ordinary signal level remains unity.
        core.process(-1, 1.0, 0.75, -0.5);
        near(1.0, core.getLeftOutput());
        near(0.0, core.getRightOutput());
        near(0.75, core.getMixOutput());

        // Strong material receives the fixed warm compression without clipping or gain boost.
        core.processBypassed(0, 0);
        core.process(-1, 5.0, 5.0, 0.0);
        between(4.0, 5.0, core.getLeftOutput());
        between(4.0, 5.0, core.getMixOutput());
        core.processBypassed(0, 0);
        core.process(-1, -5.0, -5.0, 0.0);
        between(-5.0, -4.0, core.getLeftOutput());
        between(-5.0, -4.0, core.getMixOutput());
    }

    private static void testInvalidConstruction() {
        expectFailure(() -> new FaderDistrCore(false, 0));
        expectFailure(() -> new FaderDistrCore(false, Double.NaN));
        FaderDistrCore core = new FaderDistrCore(false, 48_000);
        core.process(Double.NaN, 1, 1, 1);
        near(0.5, core.getLeftOutput());
        near(0.5, core.getRightOutput());
        near(1, core.getMixOutput());
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

    private static void between(double minimum, double maximum, double actual) {
        checks++;
        if (!Double.isFinite(actual) || actual <= minimum || actual >= maximum) {
            throw new AssertionError(actual + " is not between " + minimum + " and " + maximum);
        }
    }
}
