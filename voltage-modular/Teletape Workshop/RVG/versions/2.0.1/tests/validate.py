from __future__ import annotations

import hashlib
import json
import argparse
import os
import re
import shutil
import subprocess
import sys
import tempfile
from pathlib import Path


ROOT = next(
    parent
    for parent in Path(__file__).resolve().parents
    if (parent / "voltage-modular" / "colorbox" / "tools").is_dir()
)
RELEASE = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / "voltage-modular" / "colorbox" / "tools"))

from support import normalized, regions
from value_tree import Reader, encode, props


def java_tool(name: str) -> str | None:
    java_home = os.environ.get("JAVA_HOME")
    if java_home:
        candidate = Path(java_home) / "bin" / (name + (".exe" if os.name == "nt" else ""))
        if candidate.is_file():
            return str(candidate)
    discovered = shutil.which(name)
    if discovered and "javapath" in discovered.lower():
        roots = [Path(os.environ.get("ProgramFiles", r"C:\Program Files")) / "Java"]
        candidates = [candidate for root in roots for candidate in root.glob(f"jdk*/bin/{name}.exe")]
        if candidates:
            return str(sorted(candidates)[-1])
    return discovered


JAVAC = java_tool("javac")
JAVA = java_tool("java")


def check(condition: bool, message: str) -> None:
    if not condition:
        raise AssertionError(message)


def compile_source(source: str, filename: str, output: Path, sdk: Path | None) -> None:
    output.mkdir(parents=True)
    source_path = output / filename
    source_path.write_text(source, encoding="utf-8")
    command = [JAVAC, "--release", "17", "-Xlint:all", "-encoding", "utf-8", "-Werror"]
    if sdk:
        command.extend(["-cp", str(sdk)])
    command.extend(["-d", str(output), str(source_path)])
    print(f"Compile: {filename} -> {output.name}", flush=True)
    subprocess.run(command, check=True, timeout=120)


def headless_source(source: str) -> str:
    source_regions = regions(source)
    fields = re.findall(r"private\s+(Voltage\w+)\s+(\w+);", source)
    declarations = "\n".join(f"    {kind} {name} = new {kind}();" for kind, name in fields)
    return f"""import java.util.concurrent.atomic.AtomicBoolean;
class VoltageComponent {{
    double value;
    int reads;
    double GetValue() {{ reads++; return value; }}
    void SetValue(double next) {{ value = next; }}
}}
class VoltageAudioJack extends VoltageComponent {{
    boolean connected;
    boolean IsConnected() {{ return connected; }}
}}
class VoltageKnob extends VoltageComponent {{}}
class VoltageSwitch extends VoltageComponent {{}}
class VoltageButton extends VoltageComponent {{}}
class VoltageLED extends VoltageComponent {{}}
class VoltageLabel extends VoltageComponent {{}}
class VoltageImage extends VoltageComponent {{}}
class VoltageModule {{
    int timerStarts;
    int timerStops;
    int destroys;
    void StartGuiUpdateTimer(int interval) {{ timerStarts++; }}
    void StopGuiUpdateTimer() {{ timerStops++; }}
    void Destroy() {{ destroys++; }}
    String GetTooltipText(VoltageComponent component) {{ return ""; }}
}}
public class HeadlessRVG extends VoltageModule {{
    enum ModuleNotifications {{ Button_Changed, Switch_Changed, GUI_Update_Timer, Reset, Other }}
{declarations}
{source_regions['code-and-variables']}
    void Initialize() {{{source_regions['Initialize']}}}
    boolean Notify(VoltageComponent component, ModuleNotifications notification,
                   double doubleValue, long longValue, int x, int y, Object object) {{
{source_regions['Notify']}
    }}
    void ProcessSample() {{{source_regions['ProcessSample']}}}
    void ProcessBypassedSample() {{{source_regions['ProcessBypassedSample']}}}
    String GetTooltipText(VoltageComponent component) {{{source_regions['GetTooltipText']}}}
    void DestroyModule() {{ super.Destroy(); {source_regions['Destroy']} }}
    static void check(boolean condition, String message) {{
        if (!condition) throw new AssertionError(message);
    }}
    static void near(double actual, double expected, double tolerance, String message) {{
        check(Math.abs(actual - expected) <= tolerance,
              message + ": " + actual + " != " + expected);
    }}
    static void runFor(HeadlessRVG module, int samples) {{
        for (int index = 0; index < samples; index++) module.ProcessSample();
    }}
    static void testDroop(int state, double rate, String label) {{
        HeadlessRVG module = new HeadlessRVG();
        module.switchComp.value = state;
        module.switchInhibit.value = 1.0;
        module.Initialize();
        module.ProcessSample();
        module.selectionPending.set(false);
        module.hasSelection = true;
        module.heldV1 = 1.0;
        module.heldV2 = -1.0;
        module.Notify(module.switchComp, ModuleNotifications.Switch_Changed,
                      state, 0, 0, 0, null);
        check(module.compensationMode == state, label + " switch state mapping");
        runFor(module, 48000);
        near(module.heldV1, 1.0 - rate, 2.0e-9, label + " positive droop per second");
        near(module.heldV2, -1.0 + rate, 2.0e-9, label + " negative droop per second");
        module.DestroyModule();
        check(module.timerStops == 1 && module.destroys == 1, label + " timer/lifecycle shutdown");
    }}
    public static void main(String[] args) {{
        HeadlessRVG module = new HeadlessRVG();
        module.switchComp.value = 1.0;
        module.switchInhibit.value = 0.0;
        module.inputSelect.connected = false;
        module.Initialize();
        check(module.timerStarts == 1, "GUI timer starts once");
        check(module.compensationMode == 1, "saved center position starts in FACTORY");
        check(module.selectionPending.get(), "startup queues a selection");
        module.knobV1.value = 1.0;
        module.knobV2.value = 1.0;
        module.ProcessSample();
        check(Math.abs(module.heldV1) <= 2.5 && Math.abs(module.heldV2) <= 2.5,
              "independent random outputs stay within range");
        near(module.outputTrig.value, 4.0, 0.0, "startup trigger level");
        runFor(module, 479);
        near(module.outputTrig.value, 4.0, 0.0, "trigger lasts 480 samples");
        module.ProcessSample();
        near(module.outputTrig.value, 0.0, 0.0, "trigger ends after 10 ms");

        near(HeadlessRVG.shapedVoltage(2.5), 2.5, 1e-12, "normalized positive endpoint");
        near(HeadlessRVG.shapedVoltage(-2.5), -2.5, 1e-12, "normalized negative endpoint");
        module.switchInhibit.value = 1.0;
        module.compensationMode = 2;
        module.heldV1 = 2.5;
        module.heldV2 = -2.5;
        module.knobV1.value = .5;
        module.knobV2.value = .5;
        module.ProcessSample();
        near(module.outputV1.value, 1.25, 1e-12, "post-shaping half Level");
        near(module.outputV2.value, -1.25, 1e-12, "negative half Level");
        module.switchInhibit.value = 0.0;
        module.triggerSamplesRemaining = 0;
        module.clockPhase = .9999999;
        module.ProcessSample();
        near(module.outputTrig.value, 4.0, 0.0, "clock first high sample");
        runFor(module, 479);
        near(module.outputTrig.value, 4.0, 0.0, "clock pulse sample 480");
        module.ProcessSample();
        near(module.outputTrig.value, 0.0, 0.0, "clock pulse ends at sample 481");
        testDroop(2, 0.0, "COMPENSATED");
        testDroop(1, 0.015, "FACTORY");
        testDroop(0, 0.115, "BLOWN CIRCUIT");
        check(HeadlessRVG.normalizeCompensationMode(2.0) == 2,
              "up position maps to COMPENSATED");
        check(HeadlessRVG.normalizeCompensationMode(1.0) == 1,
              "center position maps to FACTORY");
        check(HeadlessRVG.normalizeCompensationMode(0.0) == 0,
              "down position maps to BLOWN CIRCUIT");
        near(HeadlessRVG.droopTowardZero(0.01, 0.02), 0.0, 0.0,
             "positive droop clamps at zero");
        near(HeadlessRVG.droopTowardZero(-0.01, 0.02), 0.0, 0.0,
             "negative droop clamps at zero");

        module.switchInhibit.value = 1.0;
        module.clockPhase = 0.75;
        module.ProcessSample();
        near(module.clockPhase, 0.0, 0.0, "Inhibit stops and resets clock phase");
        module.selectionPending.set(false);
        module.Notify(module.buttonSelect, ModuleNotifications.Button_Changed,
                      1.0, 0, 0, 0, null);
        module.ProcessSample();
        near(module.outputTrig.value, 4.0, 0.0,
             "manual reselection remains available while inhibited");

        module.inputSelect.connected = true;
        module.inputSelect.value = 0.0;
        module.ProcessSample();
        module.selectionPending.set(false);
        module.inputSelect.value = 1.0;
        module.ProcessSample();
        near(module.outputTrig.value, 4.0, 0.0, "external gate triggers at 1 V");
        module.triggerSamplesRemaining = 0;
        module.inputSelect.value = 0.5;
        module.ProcessSample();
        near(module.triggerSamplesRemaining, 0.0, 0.0,
             "gate remains unarmed above the low threshold");
        module.inputSelect.value = 0.0;
        module.ProcessSample();
        module.inputSelect.value = 1.0;
        module.ProcessSample();
        check(module.triggerSamplesRemaining > 0, "gate rearms below 0.1 V");

        double held = module.heldV1;
        double phase = module.clockPhase;
        module.ProcessBypassedSample();
        near(module.outputV1.value, 0.0, 0.0, "bypass silences V1");
        near(module.outputV2.value, 0.0, 0.0, "bypass silences V2");
        near(module.outputTrig.value, 0.0, 0.0, "bypass silences trigger");
        near(module.heldV1, held, 0.0, "bypass freezes held voltage");
        near(module.clockPhase, phase, 0.0, "bypass freezes clock phase");
        module.inputSelect.value = 1.0;
        module.ProcessSample();
        check(!module.selectArmed, "resume adopts current high gate without false trigger");

        check(module.GetTooltipText(module.switchComp).contains("15 mV/s"),
              "switch tooltip reflects tuned rates");
        check(module.GetTooltipText(module.knobRate).contains("0.5 V"),
              "Mean CV tooltip includes scale");
        check(HeadlessRVG.TRIGGER_VOLTS == 4.0,
              "trigger tooltip includes output level");
        module.Notify(module.lampSelection, ModuleNotifications.GUI_Update_Timer,
                      0.0, 0, 0, 0, null);
        near(module.lampSelection.value, module.selectionLampLevel, 0.0,
             "GUI timer publishes selection state");
        module.DestroyModule();
        check(module.timerStops == 1 && module.destroys == 1,
              "GUI timer stops and base lifecycle runs once");
        System.out.println("PASS: startup/default mode, three hold states and droop, trigger timing, inhibit/reselect, external gate hysteresis, bypass/resume, tooltips and lifecycle");
    }}
}}
"""


java_path = RELEASE / "rvg.java"
vmod_path = RELEASE / "rvg.vmod"
java_source = java_path.read_text(encoding="utf-8-sig")
raw_project = vmod_path.read_bytes()
reader = Reader(raw_project)
tree = reader.tree()
check(reader.p == len(raw_project), "Designer project has unexpected trailing data")
check(encode(tree) == raw_project, "Designer binary does not round-trip")
metadata = props(tree)
check(metadata["notes"] == "v2.0.1", "Designer module Notes must be v2.0.1")
check(metadata["CEditableModule.name"] == "Random Voltage Generator", "Unexpected Designer module name")
check(metadata["class name"] == "rvg", "Unexpected Java class identity")
check(metadata["package name"] == "com.insectlabs.rvg", "Unexpected Java package identity")
embedded_source = props(next(node for node in tree[2] if node[0] == "module code"))["MODULE.SOURCE"]
check(normalized(embedded_source.replace("\r\n", "\n")) == normalized(java_source), "Embedded/exported Java mismatch")
check("buttonComp" not in java_source, "Stale compensated-mode button reference remains")
check("StopGuiUpdateTimer();" in regions(java_source)["Destroy"], "GUI timer is not stopped on destroy")

control_list = next(node for node in tree[2] if node[0] == "controls")[2]
controls = {props(control)["variable name"]: props(control) for control in control_list}
check(len(controls) == len(control_list), "Designer control names are not unique")
check(all(not control.get("notes") for control in controls.values()), "Working control Notes remain")
check(controls["switchComp"]["initialState"] == 1, "FACTORY must be the default center state")
check(controls["switchInhibit"]["initialState"] == 0, "Free Run must remain the default")
check("3-State Silver" in java_source, "Three-state silver switch skin is missing")
for control_name, display_name in re.findall(
    r'new\s+Voltage\w+\(\s*"([^\"]+)"\s*,\s*"([^\"]+)"', java_source
):
    check(control_name in controls, f"Missing Designer control {control_name}")
    check(controls[control_name]["display name"] == display_name,
          f"Designer/source display-name mismatch for {control_name}")

release_files = [
    "README.md", "USER-MANUAL.md", "DEVELOPER-NOTES.md", "RELEASE-NOTES.md",
    "CHERRY-AUDIO-NOTES.md", "RESEARCH-NOTES.md", "REVIEW.md", "rvg.java",
    "rvg.vmod", "rvg_hero.png", "tests/validate.py", "VALIDATION.txt",
]
manifest = json.loads((RELEASE / "SHA256.json").read_text(encoding="utf-8"))
check(set(manifest) == set(release_files), "Release checksum manifest file set is incomplete or stale")
for relative in release_files:
    actual = hashlib.sha256((RELEASE / relative).read_bytes()).hexdigest()
    check(manifest[relative] == actual, f"SHA-256 mismatch for {relative}")

sdk = Path(r"C:\ProgramData\Voltage\voltage.jar")
check(sdk.is_file(), f"Voltage SDK not found: {sdk}")
check(JAVAC is not None, "javac not found on PATH or JAVA_HOME")
check(JAVA is not None, "java not found on PATH or JAVA_HOME")
parser = argparse.ArgumentParser()
parser.add_argument("--build-root", type=Path)
arguments = parser.parse_args()
with tempfile.TemporaryDirectory(prefix="rvg-validation-", dir=arguments.build_root) as temporary:
    build = Path(temporary)
    compile_source(java_source, "rvg.java", build / "exported", sdk)
    compile_source(embedded_source, "rvg.java", build / "embedded", sdk)
    headless_dir = build / "headless"
    headless_dir.mkdir()
    headless_file = headless_dir / "HeadlessRVG.java"
    headless_file.write_text(headless_source(java_source), encoding="utf-8")
    headless_classes = build / "headless-classes"
    compile_source(headless_file.read_text(encoding="utf-8"), "HeadlessRVG.java", headless_classes, None)
    subprocess.run(
        [JAVA, "-cp", str(headless_classes), "HeadlessRVG"],
        check=True,
        timeout=30,
    )

print("PASS: project round-trip, metadata, source parity, two SDK compilations, release hashes, and RVG behavior checks")
