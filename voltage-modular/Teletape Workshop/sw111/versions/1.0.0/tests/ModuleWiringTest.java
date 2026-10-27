import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import voltage.core.Jack.JackType;
import voltage.core.VoltageAudioJack;
import voltage.core.VoltageKnob;
import voltage.core.VoltageLED;
import voltage.core.VoltageModule;

/** Constructor-free SDK test doubles exercise the actual module callbacks without native GUI calls. */
public final class ModuleWiringTest {
    private static int checks;

    private static void check(boolean ok, String message) {
        checks++;
        if (!ok) throw new AssertionError(message);
    }

    private static <T> T allocate(Class<T> type) throws Exception {
        Class<?> unsafeType = Class.forName("sun.misc.Unsafe");
        Field singleton = unsafeType.getDeclaredField("theUnsafe");
        singleton.setAccessible(true);
        Object instance = unsafeType.getMethod("allocateInstance", Class.class)
            .invoke(singleton.get(null), type);
        return type.cast(instance);
    }

    public static final class TestJack extends VoltageAudioJack {
        boolean connected;
        double signal;
        double[] slots;
        int frame;
        int reads;
        int writes;
        public TestJack() { super("test", null, JackType.JackType_AudioInput); }
        @Override public boolean IsConnected() { return connected; }
        @Override public double GetValue() { reads++; return signal; }
        @Override public void SetValue(double value) { writes++; slots[frame & 1] = value; }
    }

    public static final class TestKnob extends VoltageKnob {
        double value;
        int reads;
        public TestKnob() { super("test", null, 1.0, 10.0, 1.0); }
        @Override public double GetValue() { reads++; return value; }
    }

    public static final class TestLamp extends VoltageLED {
        double value;
        public TestLamp() { super("test", null); }
        @Override public void SetValue(double value) { this.value = value; }
    }

    private static final class Rig {
        final VoltageModule module;
        final Class<?> type;
        final Object core;
        final TestJack[] inputs = new TestJack[10];
        final TestJack[] outputs = new TestJack[10];
        final TestLamp[] lamps = new TestLamp[10];
        final TestJack cv;
        final TestJack clock;
        final TestJack reset;
        final TestJack commonIn;
        final TestJack commonOut;
        final TestKnob knob;
        int frame;

        Rig(String name) throws Exception {
            type = Class.forName("com.insectlabs.swcxi." + name);
            module = (VoltageModule) allocate(type);
            Class<?> coreType = Arrays.stream(type.getDeclaredClasses())
                .filter(c -> c.getSimpleName().equals("SwitchCore")).findFirst().orElseThrow();
            Constructor<?> constructor = coreType.getDeclaredConstructor();
            constructor.setAccessible(true);
            core = constructor.newInstance();
            field("core", core);
            field("lastLampStage", -1);
            for (int i = 0; i < 10; i++) {
                inputs[i] = jack("input" + (i + 1));
                outputs[i] = jack("output" + (i + 1));
                lamps[i] = allocate(TestLamp.class);
                field("lamp" + (i + 1), lamps[i]);
            }
            cv = jack("inputSelect");
            clock = jack("inputClock");
            reset = jack("inputReset");
            commonIn = jack("inputCommon");
            commonOut = jack("outputCommon");
            knob = allocate(TestKnob.class);
            knob.value = 1.0;
            field("knobSelect", knob);
            module.Initialize();
        }

        void field(String name, Object value) throws Exception {
            Field f = type.getDeclaredField(name);
            f.setAccessible(true);
            f.set(module, value);
        }

        TestJack jack(String name) throws Exception {
            TestJack jack = allocate(TestJack.class);
            jack.slots = new double[2];
            field(name, jack);
            return jack;
        }

        int selected() throws Exception {
            Field f = core.getClass().getDeclaredField("selectedStage");
            f.setAccessible(true);
            return f.getInt(core);
        }

        String history() throws Exception {
            StringBuilder s = new StringBuilder();
            for (Field f : core.getClass().getDeclaredFields()) {
                if (java.lang.reflect.Modifier.isStatic(f.getModifiers())) continue;
                if (Arrays.asList("inputs", "outputs", "outCommon", "resumePending").contains(f.getName())) continue;
                f.setAccessible(true);
                Object value = f.get(core);
                s.append(f.getName()).append('=');
                s.append(value instanceof double[] ? Arrays.toString((double[]) value) : value).append(';');
            }
            return s.toString();
        }

        void step(boolean bypass) {
            for (TestJack out : outputs) out.frame = frame;
            commonOut.frame = frame;
            if (bypass) module.ProcessBypassedSample(); else module.ProcessSample();
            frame++;
        }

        void configure(int sample) {
            knob.value = 1 + (sample / 173) % 10;
            cv.connected = (sample / 997) % 2 == 1;
            clock.connected = (sample / 1103) % 2 == 1;
            reset.connected = true;
            cv.signal = Math.sin(sample * 0.0031) * 12.0;
            clock.signal = sample % 97 < 21 ? 5.0 : 0.0;
            reset.signal = sample % 1801 < 7 ? 5.0 : 0.0;
            commonIn.connected = (sample / 89) % 2 == 1;
            commonOut.connected = (sample / 131) % 2 == 1;
            commonIn.signal = Math.sin(sample * 0.029) * 24.0;
            for (int i = 0; i < 10; i++) {
                inputs[i].connected = ((sample / 211) + i) % 3 != 0;
                inputs[i].signal = Math.sin(sample * 0.019 + i) * (i + 1) * 3.0;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Rig baseline = new Rig("BaselineModule");
        Rig candidate = new Rig("swcxi");
        for (int frame = 0; frame < 50000; frame++) {
            baseline.configure(frame);
            candidate.configure(frame);
            baseline.step(false);
            candidate.step(false);
            int slot = frame & 1;
            for (int i = 0; i < 10; i++) {
                check(Double.doubleToLongBits(baseline.outputs[i].slots[slot])
                    == Double.doubleToLongBits(candidate.outputs[i].slots[slot]), "active callback parity: output " + i);
                check(candidate.outputs[i].writes == frame + 1, "every stage output must be written every sample");
            }
            check(Double.doubleToLongBits(baseline.commonOut.slots[slot])
                == Double.doubleToLongBits(candidate.commonOut.slots[slot]), "active common-output callback parity");
            check(candidate.commonOut.writes == frame + 1, "common output must be written every sample");
            check(Arrays.equals(baseline.module.GetStateInformation(), candidate.module.GetStateInformation()), "selection state parity");
        }

        candidate = new Rig("swcxi");
        candidate.knob.value = 3.0;
        candidate.commonIn.connected = true;
        candidate.commonIn.signal = 24.0;
        for (int i = 0; i < 10; i++) {
            candidate.inputs[i].connected = true;
            candidate.inputs[i].signal = i + 1.0;
        }
        for (int i = 0; i < 100; i++) candidate.step(false);
        check(candidate.outputs[2].slots[0] == 24.0 && candidate.outputs[2].slots[1] == 24.0, "both cable slots carry settled DC");
        check(candidate.commonOut.slots[0] == 3.0 && candidate.commonOut.slots[1] == 3.0, "both Common Out slots carry selected DC");
        candidate.knob.value = 7.0;
        candidate.step(false); // enter bypass during a fade
        String history = candidate.history();
        int knobReads = candidate.knob.reads;
        int cvReads = candidate.cv.reads;
        int clockReads = candidate.clock.reads;
        int resetReads = candidate.reset.reads;
        candidate.cv.connected = true;
        candidate.clock.connected = true;
        candidate.reset.connected = true;
        candidate.cv.signal = -4.75;
        candidate.clock.signal = 5.0;
        candidate.reset.signal = 0.0;
        for (int i = 0; i < 100; i++) {
            candidate.commonIn.signal = 0.25 * i - 12.0;
            candidate.step(true);
            int slot = (candidate.frame - 1) & 1;
            check(candidate.outputs[6].slots[slot] == candidate.commonIn.signal, "bypass is sample-exact");
            check(candidate.commonOut.slots[slot] == 7.0, "bypass common output is direct");
            for (int j = 0; j < 10; j++) if (j != 6)
                check(candidate.outputs[j].slots[slot] == 0.0, "inactive bypass outputs stay silent");
        }
        check(history.equals(candidate.history()), "bypass freezes clock, selection and fade histories");
        check(candidate.knob.reads == knobReads && candidate.cv.reads == cvReads
            && candidate.clock.reads == clockReads && candidate.reset.reads == resetReads, "bypass reads no knob or control CV values");
        candidate.cv.connected = false;
        candidate.knob.value = 10.0;
        candidate.step(false);
        check(candidate.selected() == 6, "resume with a high clock does not synthesize an edge");
        check(candidate.commonOut.slots[(candidate.frame - 1) & 1] == 7.0, "resume discards the interrupted fade");
        candidate.clock.signal = 0.0;
        candidate.step(false);
        candidate.clock.signal = 5.0;
        candidate.step(false);
        check(candidate.selected() == 7, "next valid clock advances after resume");
        candidate.cv.connected = true;
        candidate.knob.value = 3.0;
        check(candidate.module.GetTooltipText(candidate.knob).equals("Active stages: 3"), "knob tooltip explains addressed range");
        candidate.cv.connected = false;
        candidate.clock.connected = false;
        check(candidate.module.GetTooltipText(candidate.knob).equals("Selected stage: 3"), "knob tooltip explains manual selection");
        System.out.println("PASS: " + checks + " module callback/cable/bypass assertions; 50,000 active samples match supplied v0.1.2 exactly");
    }
}
