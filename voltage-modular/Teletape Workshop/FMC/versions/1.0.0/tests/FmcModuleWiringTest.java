package com.insectlabs.fmc;

import java.lang.reflect.Field;
import voltage.controllers.*;
import voltage.core.*;

/** Checks the module's control notifications and jack routes against an independent extracted DSP core. */
public final class FmcModuleWiringTest {
    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
    private static void set(Object target, String name, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(target, value);
    }
    private static VoltageAudioJack jack(fmc module, String name) throws Exception {
        Field field = fmc.class.getDeclaredField(name); field.setAccessible(true);
        return (VoltageAudioJack) field.get(module);
    }
    private static VoltageKnob knob(fmc module, String name) throws Exception {
        Field field = fmc.class.getDeclaredField(name); field.setAccessible(true);
        return (VoltageKnob) field.get(module);
    }
    private static VoltageSwitch selector(fmc module, String name) throws Exception {
        Field field = fmc.class.getDeclaredField(name); field.setAccessible(true);
        return (VoltageSwitch) field.get(module);
    }
    private static VoltageToggle hold(fmc module, String name) throws Exception {
        Field field = fmc.class.getDeclaredField(name); field.setAccessible(true);
        return (VoltageToggle) field.get(module);
    }
    private static String chName(int i, String suffix) {
        return "channel" + new String[]{"One", "Two", "Three", "Four"}[i] + suffix;
    }
    private static double signal(int channel, int n) {
        return (0.15 + 0.03 * channel) * Math.sin(2.0 * Math.PI * (110.0 + 37.0 * channel) * n / 48000.0);
    }

    private static fmc build() throws Exception {
        fmc module = new fmc(1L, new VoltageObjects());
        int[] switchPositions = {3, 2, 1, 0};
        for (int c = 0; c < 4; c++) {
            VoltageAudioJack input = new VoltageAudioJack(chName(c, "Input"), module);
            VoltageAudioJack output = new VoltageAudioJack(chName(c, "Output"), module);
            VoltageAudioJack cv = new VoltageAudioJack(chName(c, "RateCvInput"), module);
            VoltageSwitch voice = new VoltageSwitch(chName(c, "VoiceSwitch"), module, switchPositions[c]);
            VoltageToggle hold = new VoltageToggle(chName(c, "HoldToggle"), module);
            set(module, chName(c, "Input"), input);
            set(module, chName(c, "Output"), output);
            set(module, chName(c, "RateCvInput"), cv);
            set(module, chName(c, "VoiceSwitch"), voice);
            set(module, chName(c, "HoldToggle"), hold);
            input.connected = true;
            for (int r = 0; r < 4; r++) {
                String field = chName(c, "Parameter" + (char) ('A' + r));
                set(module, field, new VoltageKnob(field, module, r == 3 ? 0.5 : 0.0));
            }
        }
        set(module, "mixOutput", new VoltageAudioJack("mixOutput", module));
        module.Initialize();
        return module;
    }

    private static FmcCore reference() {
        FmcCore core = new FmcCore(48000.0);
        int[] voices = {FmcCore.V_BF, FmcCore.V_TR, FmcCore.V_DM, FmcCore.V_DD};
        for (int c = 0; c < 4; c++) {
            core.setVoice(c, voices[c]);
            core.setA(c, 0.0); core.setB(c, 0.0); core.setC(c, 0.0); core.setD(c, 0.5);
            core.setHold(c, false); core.setRateCv(c, 0.0); core.setPatched(c, true);
        }
        core.reset();
        return core;
    }

    private static void compare(fmc module, FmcCore expected, int first, int count) throws Exception {
        double worst = 0.0, mixError = 0.0;
        VoltageAudioJack mix = jack(module, "mixOutput");
        for (int n = first; n < first + count; n++) {
            for (int c = 0; c < 4; c++) jack(module, chName(c, "Input")).value = signal(c, n) * 5.0;
            double[] reference = new double[4];
            for (int c = 0; c < 4; c++) reference[c] = expected.process(c, signal(c, n));
            module.ProcessSample();
            double outputSum = 0.0;
            for (int c = 0; c < 4; c++) {
                double actual = jack(module, chName(c, "Output")).value;
                worst = Math.max(worst, Math.abs(actual - reference[c] * 5.0));
                outputSum += actual;
            }
            mixError = Math.max(mixError, Math.abs(mix.value - 0.5 * outputSum));
        }
        require(worst < 1e-9, "module notifications/routing match reference core; max error=" + worst + " V");
        require(mixError < 1e-12, "active mix output is the half-sum of channel outputs; error=" + mixError);
    }

    public static void main(String[] args) throws Exception {
        fmc module = build();
        FmcCore reference = reference();
        compare(module, reference, 0, 24000);

        selector(module, "channelOneVoiceSwitch").SetValue(2.0); // physical position 2 selects TR-2
        reference.setVoice(0, FmcCore.V_TR);
        knob(module, "channelThreeParameterC").SetValue(0.8);
        reference.setC(2, 0.8);
        jack(module, "channelOneRateCvInput").connected = true;
        jack(module, "channelOneRateCvInput").value = 2.5;
        reference.setRateCv(0, 0.5);
        hold(module, "channelFourHoldToggle").userClick(true);
        reference.setHold(3, true);
        compare(module, reference, 24000, 96000);
        int editCalls = module.editComponentValueCalls;
        VoltageKnob dmTime = knob(module, "channelThreeParameterA");
        module.EditComponentValue(dmTime, 300.0, "300 ms");
        double expectedPosition = FmcCore.dmPosForMs(300.0);
        require(module.editComponentValueCalls == editCalls + 1,
                "typed edit delegates to SDK exactly once");
        require(Math.abs(dmTime.GetValue() - expectedPosition) < 1e-12,
                "typed DM-2 milliseconds map back to knob position");
        require(module.GetTooltipText(dmTime).contains("300 ms"),
                "DM-2 tooltip reports typed delay in the selected voice");
        require(module.GetTooltipText(selector(module, "channelThreeVoiceSwitch")).contains("BF-2"),
                "voice selector tooltip lists voice order");

        int cvReads = jack(module, "channelOneRateCvInput").getValueCalls;
        int parameterReads = dmTime.getValueCalls;

        double[] raw = {1.25, -2.5, 3.75, -5.0};
        double bypassSum = 0.0;
        for (int c = 0; c < 4; c++) jack(module, chName(c, "Input")).value = raw[c];
        module.ProcessBypassedSample();
        require(jack(module, "channelOneRateCvInput").getValueCalls == cvReads,
                "host bypass does not read CV inputs");
        require(dmTime.getValueCalls == parameterReads,
                "host bypass does not read control values");
        for (int c = 0; c < 4; c++) {
            require(jack(module, chName(c, "Output")).value == raw[c], "host bypass routes channel " + (c + 1) + " exactly");
            bypassSum += raw[c];
        }
        require(jack(module, "mixOutput").value == 0.5 * bypassSum,
                "host bypass preserves the mix jack's half-sum routing");
        reference.setA(2, expectedPosition);
        reference.reset();
        for (int c = 0; c < 4; c++) jack(module, chName(c, "Input")).value = 0.0;
        for (int c = 0; c < 4; c++) reference.setPatched(c, true);
        compare(module, reference, 120000, 1);
        System.out.println("PASS FMC default voice mapping, notifications, four-way output/mix, direct bypass, and clean resume (stub SDK)");
    }
}