import com.insectlabs.dsp.filters.EmsSvf96;

public final class EmsSvfChecks {
    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void main(String[] args) {
        EmsSvf96 core = new EmsSvf96();
        core.configure(1000.0, 0.0);
        double last = 0.0;
        for (int i = 0; i < 96000; i++) {
            core.process(i < 48000 ? 1.0 : 0.0);
            double high = core.getHigh();
            double band = core.getBand();
            double low = core.getLow();
            check(Double.isFinite(high) && Double.isFinite(band) && Double.isFinite(low),
                    "finite outputs at low response");
            check(Math.abs(high) < 20.0 && Math.abs(band) < 20.0 && Math.abs(low) < 20.0,
                    "bounded outputs at low response");
            last = high + band + low;
        }
        check(Double.isFinite(last), "finite tail");
        core.reset();
        check(core.getHigh() == 0.0 && core.getBand() == 0.0 && core.getLow() == 0.0,
                "reset clears output taps");

        core.configure(20000.0, 1.0);
        for (int i = 0; i < 96000; i++) {
            core.process(i % 2 == 0 ? 20.0 : -20.0);
            check(Double.isFinite(core.getHigh()) && Double.isFinite(core.getBand())
                    && Double.isFinite(core.getLow()), "finite outputs under high-response stress");
        }
        expectBadFrequency(0.0);
        expectBadFrequency(48000.0);
        expectBadFrequency(Double.NaN);
        expectBadFrequency(Double.POSITIVE_INFINITY);
        System.out.println("PASS: EmsSvf96 finite/bounded signal, reset, high-response stress and frequency preconditions.");
    }

    private static void expectBadFrequency(double frequency) {
        try {
            new EmsSvf96().configure(frequency, 0.5);
            throw new AssertionError("invalid frequency accepted");
        } catch (IllegalArgumentException expected) {
            // Expected API precondition.
        }
    }
}
