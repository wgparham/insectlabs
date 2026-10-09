from pathlib import Path
import re
import subprocess
import sys
import tempfile


ROOT = next(
    path for path in Path(__file__).resolve().parents
    if (path / "voltage-modular" / "colorbox" / "tools").is_dir()
)
DEVELOPMENT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / "voltage-modular" / "colorbox" / "tools"))

from support import normalized, regions
from value_tree import Reader, encode, props


def compile_source(source: str, class_name: str, output: Path, sdk: Path | None) -> None:
    output.mkdir()
    source_path = output / f"{class_name}.java"
    source_path.write_text(source, encoding="utf-8")
    command = ["javac", "--release", "17", "-encoding", "utf-8", "-Werror"]
    if sdk is not None:
        command.extend(["-cp", str(sdk)])
    command.extend(["-d", str(output), str(source_path)])
    subprocess.run(command, check=True)


def headless_source(source: str) -> str:
    module_regions = regions(source)
    return """public class Headless {
static class VoltageAudioJack {
    double value;
    boolean connected = true;
    boolean IsConnected() { return connected; }
    double GetValue() { return value; }
    void SetValue(double next) { value = next; }
}
static class VoltageKnob {
    double value;
    double GetValue() { return value; }
}
static class VoltageSwitch {
    double value;
    double GetValue() { return value; }
}
VoltageAudioJack jackInput = new VoltageAudioJack();
VoltageAudioJack jackOutput = new VoltageAudioJack();
VoltageKnob knobBass = new VoltageKnob();
VoltageKnob knobTreble = new VoltageKnob();
VoltageSwitch switchFrequency = new VoltageSwitch();
""" + module_regions["code-and-variables"] + """
void ProcessSample() {
""" + module_regions["ProcessSample"] + """
}
void ProcessBypassedSample() {
""" + module_regions["ProcessBypassedSample"] + """
}
static void check(boolean condition, String label) {
    if (!condition) throw new AssertionError(label);
}
static double rms(double frequency, double bass, double treble, int profile) {
    Headless module = new Headless();
    module.knobBass.value = bass;
    module.knobTreble.value = treble;
    module.switchFrequency.value = profile;
    double energy = 0.0;
    int samples = 48000;
    for (int index = 0; index < samples; index++) {
        module.jackInput.value = 0.1 * Math.sin(2.0 * Math.PI * frequency * index / SAMPLE_RATE);
        module.ProcessSample();
        if (index >= samples / 2) energy += module.jackOutput.value * module.jackOutput.value;
    }
    return Math.sqrt(energy / (samples / 2));
}
public static void main(String[] args) {
    double bassCut = rms(50.0, -1.0, 0.0, 0);
    double bassCenter = rms(50.0, 0.0, 0.0, 0);
    double bassBoost = rms(50.0, 1.0, 0.0, 0);
    check(bassCut < bassCenter && bassCenter < bassBoost, "Bass CCW cut / CW boost");

    double trebleCut = rms(10000.0, 0.0, -1.0, 0);
    double trebleCenter = rms(10000.0, 0.0, 0.0, 0);
    double trebleBoost = rms(10000.0, 0.0, 1.0, 0);
    check(trebleCut < trebleCenter && trebleCenter < trebleBoost, "Treble CCW cut / CW boost");

    double bassAtHighCut = rms(20000.0, -1.0, 0.0, 0);
    double bassAtHighBoost = rms(20000.0, 1.0, 0.0, 0);
    double trebleAtLowCut = rms(20.0, 0.0, -1.0, 0);
    double trebleAtLowBoost = rms(20.0, 0.0, 1.0, 0);
    System.out.printf("Shelf cross-band deltas: bass %.2f%%, treble %.2f%%%n",
        100.0 * Math.abs(bassAtHighCut - bassAtHighBoost) / bassAtHighBoost,
        100.0 * Math.abs(trebleAtLowCut - trebleAtLowBoost) / trebleAtLowBoost);
    check(Math.abs(bassAtHighCut - bassAtHighBoost) < bassAtHighBoost * 0.05,
          "Bass control has only a small effect at the top of the audio band");
    check(Math.abs(trebleAtLowCut - trebleAtLowBoost) < trebleAtLowBoost * 0.05,
          "Treble control has only a small effect at the bottom of the audio band");

    double reference = rms(3000.0, 0.0, 1.0, 0);
    double dark = rms(3000.0, 0.0, 1.0, 1);
    double extraDark = rms(3000.0, 0.0, 1.0, 2);
    check(reference < dark && dark < extraDark, "All three profile switch values affect shelf corners");

    Headless module = new Headless();
    module.jackInput.value = 3.25;
    module.ProcessBypassedSample();
    check(module.jackOutput.value == 3.25, "Bypass is an exact dry transfer");
    check(module.resetRequested, "Bypass requests clean DSP resumption");
    module.knobBass.value = 1.0;
    module.knobTreble.value = -1.0;
    module.switchFrequency.value = 2;
    module.jackInput.value = 0.0;
    module.ProcessSample();
    check(!module.resetRequested, "Resume clears its reset request");
    check(module.toneCore.bassControl == 0.0 && module.toneCore.trebleControl == 1.0,
          "Resume adopts current knob values immediately");
    check(module.toneCore.bassCornerHz == 17.5 && module.toneCore.trebleCornerHz == 1750.0,
          "Resume adopts the selected frequency profile");
    module = new Headless();
    module.jackInput.connected = false;
    module.ProcessSample();
    check(module.jackOutput.value == 0.0, "Unpatched input is silent");
    module.knobBass.value = Double.NaN;
    module.knobTreble.value = Double.POSITIVE_INFINITY;
    module.switchFrequency.value = Double.NaN;
    module.jackInput.value = 0.1;
    module.ProcessSample();
    check(Double.isFinite(module.jackOutput.value), "Nonfinite controls are bounded safely");

    check(Math.abs(safetyCeiling(1000.0)) < 24.000001, "Safety ceiling remains bounded");
    check(safetyCeiling(-1000.0) == -safetyCeiling(1000.0), "Safety ceiling is polarity symmetric");
    check(Double.isFinite(softDrive(1.0e6)), "High-level drive remains finite");
    check(softDrive(-1.0e6) == -softDrive(1.0e6), "Drive stage is polarity symmetric");
    System.out.printf(
        "PASS: knob direction and shelf routing; three profiles; exact bypass; unpatched input; symmetric bounds.%n"
    );
}
}
"""


java_path = DEVELOPMENT / "21-24eq.java"
vmod_path = DEVELOPMENT / "21-24eq.vmod"
java_source = java_path.read_text(encoding="utf-8-sig")
raw_project = vmod_path.read_bytes()
reader = Reader(raw_project)
tree = reader.tree()
assert reader.p == len(raw_project), "Unexpected trailing bytes in Designer project"
assert encode(tree) == raw_project, "Designer project does not round-trip"
metadata = props(tree)
assert metadata["notes"].splitlines()[0] in {"v1.0.1rc", "v1.0.1"}, (
    "Module Notes must identify the 1.0.1 candidate or release"
)
embedded_source = props(next(node for node in tree[2] if node[0] == "module code"))["MODULE.SOURCE"]
assert normalized(embedded_source) == normalized(java_source), "Embedded/exported source mismatch"
control_metadata = {
    props(control)["variable name"]: props(control)
    for control in next(node for node in tree[2] if node[0] == "controls")[2]
}
assert control_metadata["switchFrequency"]["Control Type"] == 22
assert control_metadata["switchFrequency"]["initialState"] == 0, (
    "FREQ switch should start on the reference profile"
)
skin_names = {}


def collect_skins(node):
    if node[0] == "skin names":
        for skin in node[2]:
            skin_props = props(skin)
            if skin_props.get("type") == "switch":
                skin_names[skin_props["ID"]] = skin_props["name"]
    for child in node[2]:
        collect_skins(child)


collect_skins(tree)
assert skin_names[control_metadata["switchFrequency"]["skin"]] == "3-State Slide Horizontal"
skin_names = {}


def collect_skins(node):
    if node[0] == "skin names":
        for skin in node[2]:
            skin_props = props(skin)
            if skin_props.get("type") == "switch":
                skin_names[skin_props["ID"]] = skin_props["name"]
    for child in node[2]:
        collect_skins(child)


collect_skins(tree)
assert skin_names[control_metadata["switchFrequency"]["skin"]] == "3-State Slide Horizontal"
for control_name, display_name in re.findall(
    r'new\s+Voltage\w+\(\s*"([^"]+)"\s*,\s*"([^"]+)"', java_source
):
    assert control_metadata[control_name]["display name"] == display_name, (
        f"Designer/source display-name mismatch for {control_name}"
    )
for control_name, text in re.findall(
    r'new\s+VoltageLabel\(\s*"([^"]+)"\s*,\s*"[^"]+"\s*,\s*this\s*,\s*"([^"]*)"',
    java_source,
):
    assert control_metadata[control_name]["the text"] == text, (
        f"Designer/source label text mismatch for {control_name}"
    )
assert all(not item["notes"] for item in control_metadata.values()), "Stale control Notes remain"
for stale in ("buttonFrequency", "frequencyRangeB", "Button_Changed", "VoltageButton"):
    assert stale not in java_source and stale not in embedded_source, f"Stale button reference: {stale}"
notify_source = regions(java_source)["Notify"]
for event in ("Preset_Loading_Finish", "Variation_Loading_Finish"):
    event_case = re.search(rf"case {event}:.*?break;", notify_source, flags=re.S)
    assert event_case and "resetRequested = true;" in event_case[0], (
        f"Missing DSP reset on {event}"
    )
notify_source = regions(java_source)["Notify"]
for event in ("Preset_Loading_Finish", "Variation_Loading_Finish"):
    event_case = re.search(rf"case {event}:.*?break;", notify_source, flags=re.S)
    assert event_case and "resetRequested = true;" in event_case[0], (
        f"Missing DSP reset on {event}"
    )
for required in (
    "switchFrequency.GetValue()",
    "(1.0 - bassKnob) * 0.5",
    "(1.0 - trebleKnob) * 0.5",
):
    assert required in java_source and required in embedded_source, f"Missing implementation: {required}"

class_name = re.search(r"public class\s+(\w+)", java_source)[1]
sdk = Path(r"C:\ProgramData\Voltage\voltage.jar")
if not sdk.is_file():
    raise FileNotFoundError(f"Voltage SDK is required for source compilation: {sdk}")

with tempfile.TemporaryDirectory(prefix="21-24eq-validation-") as temporary:
    build = Path(temporary)
    compile_source(java_source, class_name, build / "exported", sdk)
    compile_source(embedded_source, class_name, build / "embedded", sdk)
    headless_dir = build / "headless"
    headless_dir.mkdir()
    headless_java = headless_dir / "Headless.java"
    headless_java.write_text(headless_source(java_source), encoding="utf-8")
    headless_classes = build / "headless-classes"
    compile_source(headless_java.read_text(encoding="utf-8"), "Headless", headless_classes, None)
    result = subprocess.run(
        ["java", "-cp", str(headless_classes), "Headless"],
        check=False,
        capture_output=True,
        text=True,
    )
    print(result.stdout, end="")
    if result.returncode:
        print(result.stderr, file=sys.stderr, end="")
        raise RuntimeError("Headless behavior checks failed")

print("PASS: source agreement, project round-trip, clean button references, and SDK compilation.")
