"""Compile TRM's exported and embedded sources, then exercise the DSP and callbacks."""
from pathlib import Path
import argparse
import re
import subprocess
import sys
import tempfile

module_dir = Path(__file__).resolve().parents[1]
repo = next(path for path in module_dir.parents if (path / "voltage-modular").exists())
sys.path.insert(0, str(repo / "voltage-modular/colorbox/tools"))
from value_tree import Reader, encode, props
from support import regions, normalized

parser = argparse.ArgumentParser()
parser.add_argument("--build-root", type=Path, required=True)
args = parser.parse_args()
java_source = (module_dir / "trm.java").read_text(encoding="utf-8-sig")
vmod_bytes = (module_dir / "trm.vmod").read_bytes()
tree = Reader(vmod_bytes).tree()
assert encode(tree) == vmod_bytes, "VMOD parse/encode must round-trip byte-identically"
module_code = next(child for child in tree[2] if child[0] == "module code")
embedded_source = props(module_code)["MODULE.SOURCE"]
assert normalized(java_source) == normalized(embedded_source), "exported and embedded Java differ"
assert props(tree)["notes"] == "v1.0.0", "Designer Notes must identify canonical version"

java_regions = regions(java_source)
harness = (module_dir / "tests/harness.template").read_text(encoding="utf-8")
for placeholder, key in (("CORE", "code-and-variables"), ("INIT", "Initialize"),
                         ("PROCESS", "ProcessSample"), ("BYPASS", "ProcessBypassedSample")):
    harness = harness.replace("@@" + placeholder + "@@", java_regions[key])
needed_names = ("input1A", "input1B", "input2A", "input2B", "input3A", "input3B",
                "out1", "out2", "out3", "knobCarrier1", "knobCarrier2", "knobCarrier3",
                "knobModulator1", "knobModulator2", "knobModulator3", "switchDiode1",
                "switchDiode2", "switchDiode3", "lamp1", "lamp2", "lamp3")
declarations = {name: kind for kind, name in re.findall(r"private\s+(Voltage\w+)\s+(\w+);", java_source)}
fields = "\n".join(f"    {declarations[name]} {name};" for name in needed_names)
harness = harness.replace("@@FIELDS@@", fields)
assert "@@" not in harness, "unfilled harness placeholder"

args.build_root.mkdir(parents=True, exist_ok=True)
jdk = Path(r"C:\Program Files\Java\jdk-27\bin")
sdk = r"C:\ProgramData\Voltage\voltage.jar"
with tempfile.TemporaryDirectory(dir=args.build_root) as temporary:
    build = Path(temporary)
    for label, source in (("export", java_source), ("embedded", embedded_source)):
        output = build / label
        output.mkdir()
        source_file = output / "trm.java"
        source_file.write_text(source, encoding="utf-8", newline="")
        subprocess.run([str(jdk / "javac.exe"), "--release", "17", "-Xlint:all", "-Werror",
                        "-encoding", "UTF-8", "-cp", sdk, "-d", str(output), str(source_file)],
                       check=True)
    harness_file = build / "TrmChecks.java"
    harness_file.write_text(harness, encoding="utf-8", newline="")
    primitive = repo / "dsp-primitives/src/com/insectlabs/dsp/filters/HalfBand19.java"
    subprocess.run([str(jdk / "javac.exe"), "--release", "17", "-Xlint:all", "-Werror",
                    "-encoding", "UTF-8", "-d", str(build), str(primitive), str(harness_file)], check=True)
    subprocess.run([str(jdk / "java.exe"), "-cp", str(build), "TrmChecks"], check=True)

print("PASS: exported/embedded SDK builds, exact project round-trip, source agreement, and DSP/callback harness")
