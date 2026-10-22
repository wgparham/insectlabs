package com.insectlabs.fmc;

public final class FmcCoreTest {
    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    private static double positionFor(double millis, double minimum, double maximum) {
        return Math.log(millis / minimum) / Math.log(maximum / minimum);
    }

    private static int echoPeakSample(FmcCore core, int voice, double delaySeconds) {
        core.setVoice(0, voice);
        double millis = delaySeconds * 1000.0;
        double position = voice == FmcCore.V_DM
                ? positionFor(millis, 20.0, 2400.0)
                : positionFor(millis, 12.5, 6400.0);
        core.setA(0, position);
        core.setB(0, 0.0);
        core.setC(0, 1.0);
        core.setD(0, 0.0);
        core.setHold(0, false);
        core.setPatched(0, true);
        core.resetChannel(0);
        int count = (int) (delaySeconds * 48000.0) + 4800;
        int peakAt = -1;
        double peak = 0.0;
        for (int n = 0; n < count; n++) {
            double y = core.process(0, n == 0 ? 0.5 : 0.0);
            if (n > 1200 && Math.abs(y) > peak) {
                peak = Math.abs(y);
                peakAt = n;
            }
        }
        require(peak > 1e-6, "delay voice produces a measurable echo");
        return peakAt;
    }

    public static void main(String[] args) {
        require(Math.abs(FmcCore.dmTimeMs(0.0) - 20.0) < 1e-12, "DM-2 minimum delay mapping");
        require(Math.abs(FmcCore.dmTimeMs(1.0) - 2400.0) < 1e-9, "DM-2 maximum delay mapping");
        require(Math.abs(FmcCore.ddTimeMs(0.0) - 12.5) < 1e-12, "DD-2 minimum delay mapping");
        require(Math.abs(FmcCore.ddTimeMs(1.0) - 6400.0) < 1e-9, "DD-2 maximum delay mapping");
        require(FmcCore.mix(1.0, -1.0, 0.5, 0.5) == 0.5, "mix is half-sum");
        require(FmcCore.mixWet(0.0) == 0.0 && FmcCore.mixDry(0.0) == 1.0, "delay mix starts dry");
        require(FmcCore.mixWet(1.0) == 1.0 && FmcCore.mixDry(1.0) == 0.0, "delay mix ends wet");

        FmcCore core = new FmcCore(48000.0);
        double[] rmsByVoice = new double[4];
        for (int voice = 0; voice < 4; voice++) {
            core.resetChannel(0);
            core.setVoice(0, voice);
            core.setA(0, 0.45);
            core.setB(0, 0.6);
            core.setC(0, voice >= FmcCore.V_DM ? 0.8 : 0.5);
            core.setD(0, 0.4);
            double square = 0.0;
            for (int n = 0; n < 48000; n++) {
                double x = 0.3 * Math.sin(2.0 * Math.PI * 330.0 * n / 48000.0);
                double y = core.process(0, x);
                require(Double.isFinite(y) && Math.abs(y) <= FmcCore.CEILING,
                        "voice " + voice + " finite and bounded");
                if (n >= 24000) square += y * y;
            }
            rmsByVoice[voice] = Math.sqrt(square / 24000.0);
            require(rmsByVoice[voice] > 1e-5, "voice " + voice + " is audible");
        }
        for (int a = 0; a < 4; a++) {
            for (int b = a + 1; b < 4; b++) {
                require(Math.abs(rmsByVoice[a] - rmsByVoice[b]) > 1e-6,
                        "voices have distinct measured response " + a + "/" + b);
            }
        }

        int dmPeak = echoPeakSample(core, FmcCore.V_DM, 0.300);
        int ddPeak = echoPeakSample(core, FmcCore.V_DD, 0.250);
        require(Math.abs(dmPeak - 14400) < 240, "DM-2 300 ms echo location: " + dmPeak);
        require(Math.abs(ddPeak - 12000) < 240, "DD-2 250 ms echo location: " + ddPeak);

        FmcCore stressed = new FmcCore(48000.0);
        double[] hostileInputs = {-10.0, -4.0, 0.0, 4.0, 10.0,
                Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY};
        for (int voice = 0; voice < 4; voice++) {
            stressed.setVoice(0, voice);
            stressed.setA(0, Double.NaN);
            stressed.setB(0, Double.POSITIVE_INFINITY);
            stressed.setC(0, Double.NEGATIVE_INFINITY);
            stressed.setD(0, 1.0);
            stressed.setRateCv(0, Double.NaN);
            for (double x : hostileInputs) {
                for (int n = 0; n < 1024; n++) {
                    double y = stressed.process(0, x);
                    require(Double.isFinite(y) && Math.abs(y) <= FmcCore.CEILING,
                            "hostile input/control bounded for voice " + voice);
                }
            }
        }

        FmcCore laneA = new FmcCore(48000.0);
        FmcCore laneB = new FmcCore(48000.0);
        laneA.setVoice(1, FmcCore.V_DM);
        laneB.setVoice(1, FmcCore.V_DM);
        laneA.setD(1, 0.8);
        laneB.setD(1, 0.8);
        for (int n = 0; n < 4096; n++) {
            double x = 0.2 * Math.sin(2.0 * Math.PI * 110.0 * n / 48000.0);
            laneA.process(0, x); // other-channel work must not alter lane 1's state or noise stream
            require(Double.doubleToLongBits(laneA.process(1, x)) ==
                            Double.doubleToLongBits(laneB.process(1, x)),
                    "channel histories and deterministic noise remain independent");
        }

        FmcCore hold = new FmcCore(48000.0);
        hold.setVoice(0, FmcCore.V_DM);
        hold.setA(0, positionFor(80.0, 20.0, 2400.0));
        hold.setB(0, 0.7);
        hold.setC(0, 1.0);
        hold.setD(0, 0.25);
        double heldEnergy = 0.0;
        for (int n = 0; n < 96000; n++) {
            double x = 0.25 * Math.sin(2.0 * Math.PI * 220.0 * n / 48000.0);
            double y = hold.process(0, x);
            if (n >= 48000) heldEnergy += y * y;
        }
        hold.setHold(0, true);
        double holdEnergy = 0.0, peak = 0.0;
        for (int n = 0; n < 240000; n++) {
            double y = hold.process(0, 0.0);
            require(Double.isFinite(y) && Math.abs(y) <= FmcCore.CEILING, "HOLD remains bounded");
            if (n >= 192000) holdEnergy += y * y;
            peak = Math.max(peak, Math.abs(y));
        }
        require(heldEnergy > 1e-8 && holdEnergy > 1e-8 && peak > 1e-4,
                "DM-2 HOLD retains an audible repeat after input stops");

        FmcCore performance = new FmcCore(48000.0);
        for (int channel = 0; channel < 4; channel++) {
            performance.setVoice(channel, channel);
            performance.setA(channel, 0.45); performance.setB(channel, 0.6);
            performance.setC(channel, 0.5); performance.setD(channel, 0.4);
        }
        for (int n = 0; n < 96000; n++) {
            double input = ((n & 127) - 64) * 0.001;
            for (int channel = 0; channel < 4; channel++) performance.process(channel, input);
        }
        long benchmarkStart = System.nanoTime();
        int benchmarkFrames = 240000;
        for (int n = 0; n < benchmarkFrames; n++) {
            double input = ((n & 127) - 64) * 0.001;
            for (int channel = 0; channel < 4; channel++) performance.process(channel, input);
        }
        double nsPerFourChannelFrame = (System.nanoTime() - benchmarkStart) / (double) benchmarkFrames;
        System.out.printf(java.util.Locale.ROOT, "HEADLESS BENCH: %.1f ns/frame for 4 active voices (%.1f ns/channel-sample, JDK 27)%n",
                nsPerFourChannelFrame, nsPerFourChannelFrame / 4.0);

        System.out.println("PASS FMC core mappings, four voice response, echo timing, hostile boundaries, channel independence, and delay HOLD");
    }
}
