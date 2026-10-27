public final class SwitchCoreTest {
    private static int checks;

    private static void check(boolean condition, String message) {
        checks++;
        if (!condition) throw new AssertionError(message);
    }

    private static void near(double actual, double expected, double tolerance, String message) {
        check(Math.abs(actual - expected) <= tolerance, message + ": expected " + expected + ", got " + actual);
    }

    private static double[] signals() {
        double[] values = new double[SwitchCore.STAGES];
        for (int i = 0; i < values.length; i++) values[i] = (i + 1) * 0.5;
        return values;
    }

    private static void settle(SwitchCore core, double knob, double cv, boolean cvConnected,
                               double clock, boolean clockConnected, double reset, boolean resetConnected,
                               double common, boolean commonConnected, boolean commonOutConnected,
                               double[] inputs, int samples) {
        for (int i = 0; i < samples; i++) {
            core.process(knob, cv, cvConnected, clock, clockConnected, reset, resetConnected,
                common, commonConnected, commonOutConnected, inputs);
        }
    }

    private static void pulse(SwitchCore core, double knob, double cv, boolean cvConnected,
                              double reset, boolean resetConnected, double common, boolean commonConnected,
                              boolean commonOutConnected, double[] inputs) {
        settle(core, knob, cv, cvConnected, 0.0, true, reset, resetConnected, common,
            commonConnected, commonOutConnected, inputs, 2);
        settle(core, knob, cv, cvConnected, 5.0, true, reset, resetConnected, common,
            commonConnected, commonOutConnected, inputs, 1);
        settle(core, knob, cv, cvConnected, 5.0, true, reset, resetConnected, common,
            commonConnected, commonOutConnected, inputs, 2);
        settle(core, knob, cv, cvConnected, 0.0, true, reset, resetConnected, common,
            commonConnected, commonOutConnected, inputs, 2);
    }

    public static void main(String[] args) {
        check(SwitchCore.stageFromCv(0.0, 10) == 0, "0 V selects stage 1");
        check(SwitchCore.stageFromCv(0.499, 10) == 0, "first half-volt bin selects stage 1");
        check(SwitchCore.stageFromCv(0.5, 10) == 1, "0.5 V selects stage 2");
        check(SwitchCore.stageFromCv(4.999, 10) == 9, "last bin selects stage 10");
        check(SwitchCore.stageFromCv(5.0, 10) == 0, "5 V wraps to stage 1");
        check(SwitchCore.stageFromCv(10.0, 10) == 0, "10 V wraps to stage 1");
        check(SwitchCore.stageFromCv(-0.001, 10) == 9, "negative CV wraps backward to stage 10");
        check(SwitchCore.stageFromCv(-5.0, 10) == 0, "negative 5 V wraps to stage 1");
        check(SwitchCore.stageFromCv(4.9, 3) == 2, "active sequence length maps full CV cycle across three stages");
        check(SwitchCore.stageFromCv(2.5, 3) == 1, "middle of CV cycle maps to middle active stage");

        double[] inputs = signals();
        SwitchCore core = new SwitchCore();
        settle(core, 4.0, 0.0, false, 0.0, false, 0.0, false, 0.0, false, false, inputs, 55);
        check(core.selectedStage == 3, "manual knob selects stage 4");
        near(core.outCommon, 2.0, 1e-12, "common output selects stage 4 input");
        near(core.outputs[3], inputs[3], 1e-12, "unpatched common jacks pass selected pair through");
        for (int i = 0; i < SwitchCore.STAGES; i++) {
            if (i != 3) near(core.outputs[i], 0.0, 1e-12, "unselected pass-through output is silent");
        }

        core = new SwitchCore();
        settle(core, 2.0, 0.0, false, 0.0, false, 0.0, false, 7.25, true, true, inputs, 55);
        check(core.selectedStage == 1, "manual selection also selects the shared route stage");
        near(core.outCommon, 1.0, 1e-12, "10-to-1 common output receives selected input");
        near(core.outputs[1], 7.25, 1e-12, "1-to-10 common input reaches selected output");
        for (int i = 0; i < SwitchCore.STAGES; i++) {
            if (i != 1) near(core.outputs[i], 0.0, 1e-12, "demultiplexer remains one-hot");
        }

        core = new SwitchCore();
        settle(core, 10.0, 2.75, true, 0.0, false, 0.0, false, 0.0, false, false, inputs, 55);
        check(core.selectedStage == 5, "unclocked CV selects immediately");
        near(core.outCommon, inputs[5], 1e-12, "immediate CV route reaches common output");

        core = new SwitchCore();
        settle(core, 4.0, 4.9, true, 0.0, false, 0.0, false, 0.0, false, false, inputs, 55);
        check(core.selectedStage == 3, "unclocked CV maps its 5 V cycle across the knob's four stages");
        settle(core, 4.0, 1.3, true, 0.0, false, 0.0, false, 0.0, false, false, inputs, 55);
        check(core.selectedStage == 1, "1.3 V of a four-stage cycle selects stage 2");

        core = new SwitchCore();
        settle(core, 3.0, 0.0, false, 0.0, true, 0.0, false, 0.0, false, false, inputs, 2);
        check(core.selectedStage == 0, "clock connection starts from stage 1 without a phantom edge");
        pulse(core, 3.0, 0.0, false, 0.0, false, 0.0, false, false, inputs);
        check(core.selectedStage == 1, "first rising clock advances to stage 2");
        settle(core, 3.0, 0.0, false, 5.0, true, 0.0, false, 0.0, false, false, inputs, 4);
        check(core.selectedStage == 2, "a new rising edge advances to stage 3");
        settle(core, 3.0, 0.0, false, 5.0, true, 0.0, false, 0.0, false, false, inputs, 4);
        check(core.selectedStage == 2, "held clock does not repeatedly advance");
        pulse(core, 3.0, 0.0, false, 0.0, false, 0.0, false, false, inputs);
        check(core.selectedStage == 0, "clock wraps after stage 3 in a three-stage sequence");
        pulse(core, 3.0, 0.0, false, 0.0, false, 0.0, false, false, inputs);
        check(core.selectedStage == 1, "clock sequence resumes from stage 1");

        core = new SwitchCore();
        settle(core, 10.0, 0.0, false, 5.0, true, 0.0, false, 0.0, false, false, inputs, 1);
        settle(core, 10.0, 0.0, false, 5.0, true, 0.0, false, 0.0, false, false, inputs, 2);
        check(core.selectedStage == 0, "clock already high at startup does not create an edge");
        settle(core, 10.0, 0.0, false, 2.0, true, 0.0, false, 0.0, false, false, inputs, 2);
        settle(core, 10.0, 0.0, false, 5.0, true, 0.0, false, 0.0, false, false, inputs, 2);
        check(core.selectedStage == 0, "clock hysteresis prevents repeated edges before rearm");
        settle(core, 10.0, 0.0, false, 1.7, true, 0.0, false, 0.0, false, false, inputs, 2);
        settle(core, 10.0, 0.0, false, 5.0, true, 0.0, false, 0.0, false, false, inputs, 2);
        check(core.selectedStage == 1, "clock rearms below its low threshold");

        core = new SwitchCore();
        settle(core, 3.0, 1.0, true, 0.0, true, 0.0, false, 0.0, false, false, inputs, 3);
        check(core.selectedStage == 0, "clocked select CV waits for a rising edge");
        pulse(core, 3.0, 2.0, true, 0.0, false, 0.0, false, false, inputs);
        check(core.selectedStage == 1, "clock edge samples current select CV");
        settle(core, 3.0, 4.9, true, 0.0, true, 0.0, false, 0.0, false, false, inputs, 3);
        check(core.selectedStage == 1, "CV changes are held until the next clock");
        pulse(core, 3.0, 4.9, true, 0.0, false, 0.0, false, false, inputs);
        check(core.selectedStage == 2, "sampled CV maps within the active three-stage range");

        core = new SwitchCore();
        settle(core, 10.0, 0.0, false, 0.0, true, 0.0, false, 0.0, false, false, inputs, 2);
        pulse(core, 10.0, 0.0, false, 0.0, false, 0.0, false, false, inputs);
        check(core.selectedStage == 1, "clocked sequence reaches stage 2 before reset test");
        settle(core, 10.0, 0.0, false, 0.0, true, 5.0, true, 0.0, false, false, inputs, 2);
        check(core.selectedStage == 0, "reset returns to stage 1");
        settle(core, 10.0, 0.0, false, 0.0, true, 0.0, false, 0.0, false, false, inputs, 2);
        check(core.selectedStage == 0, "reset holds stage 1 until a new clock");
        pulse(core, 10.0, 0.0, false, 0.0, false, 0.0, false, false, inputs);
        check(core.selectedStage == 1, "clock resumes from stage 1 after reset");

        core = new SwitchCore();
        settle(core, 5.0, 0.0, false, 0.0, false, 0.0, false, 0.0, false, false, inputs, 55);
        check(core.selectedStage == 4, "manual mode stage 5 before reset");
        settle(core, 5.0, 0.0, false, 5.0, false, 5.0, true, 0.0, false, false, inputs, 2);
        check(core.selectedStage == 0, "manual-mode reset selects stage 1");
        settle(core, 5.0, 0.0, false, 0.0, false, 0.0, false, 0.0, false, false, inputs, 2);
        check(core.selectedStage == 0, "manual reset stays at stage 1 until the knob moves");

        core = new SwitchCore();
        settle(core, 10.0, 2.5, true, 0.0, false, 0.0, false, 0.0, false, false, inputs, 55);
        check(core.selectedStage == 5, "CV mode is at its current address before reset");
        settle(core, 10.0, 2.5, true, 0.0, false, 5.0, true, 0.0, false, false, inputs, 2);
        check(core.selectedStage == 0, "reset forces stage 1 in unclocked CV mode");
        settle(core, 10.0, 2.5, true, 0.0, false, 0.0, false, 0.0, false, false, inputs, 2);
        check(core.selectedStage == 0, "reset holds until the CV address changes");
        settle(core, 10.0, 3.0, true, 0.0, false, 0.0, false, 0.0, false, false, inputs, 2);
        check(core.selectedStage == 6, "unclocked CV resumes immediate selection after moving");

        core = new SwitchCore();
        settle(core, 4.0, 0.0, false, 0.0, false, 0.0, false, 0.0, false, true, inputs, 55);
        check(core.selectedStage == 3, "manual selection remains active with Common Out patched");
        for (int i = 0; i < SwitchCore.STAGES; i++)
            near(core.outputs[i], 0.0, 1e-12, "paired outputs stay disconnected unless both common jacks are unpatched");

        core = new SwitchCore();
        settle(core, 2.0, 0.0, false, 0.0, false, 0.0, false, 0.0, false, false, inputs, 55);
        core.bypass(3.0, true, true, inputs);
        check(core.selectedStage == 1, "bypass freezes the selected stage");
        near(core.outCommon, inputs[1], 1e-12, "bypass keeps the selected common-output route active");
        near(core.outputs[1], 3.0, 1e-12, "bypass keeps the selected common-input route active");
        for (int i = 0; i < SwitchCore.STAGES; i++) {
            if (i != 1) near(core.outputs[i], 0.0, 1e-12, "bypass output remains one-hot");
        }

        core = new SwitchCore();
        settle(core, 2.0, 0.0, false, 0.0, false, 0.0, false, 0.0, false, false, inputs, 55);
        core.process(8.0, 0.0, false, 0.0, false, 0.0, false, 0.0, false, false, inputs);
        check(core.selectedStage == 7, "active route changes to stage 8");
        for (int i = 0; i < 20; i++) {
            core.process(8.0, 0.0, false, 0.0, false, 0.0, false, 0.0, false, false, inputs);
            int active = 0;
            for (double value : core.outputs) if (value != 0.0) active++;
            check(active <= 1, "declick fade never connects two paired outputs at once");
        }
        settle(core, 8.0, 0.0, false, 0.0, false, 0.0, false, 0.0, false, false, inputs, 55);
        near(core.outCommon, inputs[7], 1e-12, "common output reaches final selected input after 1 ms");
        near(core.outputs[7], inputs[7], 1e-12, "pass-through reaches selected paired output after 1 ms");

        byte[] saved = core.getState();
        SwitchCore restored = new SwitchCore();
        restored.setState(saved);
        check(restored.selectedStage == 7, "selected stage is saved and restored");

        core = new SwitchCore();
        double[] constant = new double[10];
        java.util.Arrays.fill(constant, 1.0);
        for (int frame = 0; frame < 5000; frame++) {
            double cv = Math.sin(frame * 0.713) * 14.0;
            core.process(10.0, cv, true, 0.0, false, 0.0, false, 1.0, true, true, constant);
            near(core.outCommon, 1.0, 1e-12, "rapid mux retargeting preserves identical DC inputs");
            int active = 0;
            for (double value : core.outputs) {
                check(Double.isFinite(value) && value >= 0.0 && value <= 1.0, "rapid exclusive fades stay bounded");
                if (value != 0.0) active++;
            }
            check(active <= 1, "rapid retargeting preserves one active row output");
        }

        System.out.println("PASS: " + checks + " sequential-switch core assertions");
    }
}
