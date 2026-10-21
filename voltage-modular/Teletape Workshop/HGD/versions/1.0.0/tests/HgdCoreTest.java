package com.insectlabs.hgd;

public final class HgdCoreTest {
    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    private static double rms(HgdCore core, int channel, int count, double frequency) {
        double square = 0.0;
        for (int n = 0; n < count; n++) {
            double x = 0.4 * Math.sin(2.0 * Math.PI * frequency * n / 48000.0);
            double y = core.process(channel, x);
            if (n >= count / 2) square += y * y;
        }
        return Math.sqrt(square / (count - count / 2));
    }

    public static void main(String[] args) {
        double[] voiceRms = new double[4];
        for (int voice = 0; voice < 4; voice++) {
            HgdCore core = new HgdCore(48000.0);
            core.setVoice(0, voice);
            core.setGain(0, 0.65);
            core.setTone(0, 0.5);
            core.setThird(0, 0.5);
            voiceRms[voice] = rms(core, 0, 24000, 440.0);
            require(Double.isFinite(voiceRms[voice]) && voiceRms[voice] > 1e-6,
                    "voice " + voice + " produces finite non-silent output");
        }
        for (int a = 0; a < 4; a++) {
            for (int b = a + 1; b < 4; b++) {
                require(Math.abs(voiceRms[a] - voiceRms[b]) > 1e-5,
                        "voice RMS distinguishes modes " + a + " and " + b);
            }
        }

        HgdCore stressed = new HgdCore(48000.0);
        double[] inputs = {-10.0, -5.0, -1.0, 0.0, 1.0, 5.0, 10.0,
                           Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY};
        for (int voice = 0; voice < 4; voice++) {
            stressed.setVoice(0, voice);
            for (double gain : new double[]{0.0, 0.5, 1.0}) {
                for (double tone : new double[]{0.0, 0.5, 1.0}) {
                    for (double third : new double[]{0.0, 0.5, 1.0}) {
                        stressed.setGain(0, gain);
                        stressed.setTone(0, tone);
                        stressed.setThird(0, third);
                        for (double input : inputs) {
                            double output = stressed.process(0, input);
                            require(Double.isFinite(output), "finite output at all control/input boundaries");
                            require(Math.abs(output) <= HgdCore.CEILING + 1e-12,
                                    "output stays within the modeled ceiling");
                        }
                    }
                }
            }
        }

        HgdCore bounded = new HgdCore(48000.0);
        bounded.setGain(0, Double.NaN);
        bounded.setTone(0, Double.POSITIVE_INFINITY);
        bounded.setThird(0, Double.NEGATIVE_INFINITY);
        for (int n = 0; n < 4096; n++) {
            double y = bounded.process(0, Math.sin(2.0 * Math.PI * 1000.0 * n / 48000.0));
            require(Double.isFinite(y), "non-finite controls fall back safely");
        }

        HgdCore laneA = new HgdCore(48000.0);
        HgdCore laneB = new HgdCore(48000.0);
        laneA.setVoice(1, HgdCore.V_HM);
        laneB.setVoice(1, HgdCore.V_HM);
        for (int n = 0; n < 4096; n++) {
            double x = 0.3 * Math.sin(2.0 * Math.PI * 220.0 * n / 48000.0);
            laneA.process(0, x); // activity on lane 0 must not perturb lane 1
            double yA = laneA.process(1, x);
            double yB = laneB.process(1, x);
            require(Double.doubleToLongBits(yA) == Double.doubleToLongBits(yB),
                    "lane histories and seeded HM noise remain independent");
        }

        HgdCore repeatA = new HgdCore(48000.0);
        HgdCore repeatB = new HgdCore(48000.0);
        repeatA.setVoice(2, HgdCore.V_HM);
        repeatB.setVoice(2, HgdCore.V_HM);
        for (int n = 0; n < 2048; n++) {
            double x = 0.25 * Math.sin(2.0 * Math.PI * 97.0 * n / 48000.0);
            require(Double.doubleToLongBits(repeatA.process(2, x)) ==
                    Double.doubleToLongBits(repeatB.process(2, x)),
                    "seeded HM noise is deterministic for reproducible tests");
        }

        require(HgdCore.mix(1.0, -1.0, 0.5, 0.5) == 0.5,
                "mix is the half-sum of four channel outputs");
        require(HgdCore.mix(0.0, 0.0, 0.0, 0.0) == 0.0,
                "silent channel mix remains silent");
        System.out.println("PASS: HGD voicings, boundary safety, limits, lane independence, " +
                "repeatable HM noise, and mix law");
    }
}
