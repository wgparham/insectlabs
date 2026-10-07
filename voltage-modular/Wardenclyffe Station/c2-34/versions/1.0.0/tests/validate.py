"""Compile both c2-34 source forms and exercise the actual nested DSP core."""
from __future__ import annotations

import argparse
import hashlib
import json
import re
import subprocess
import sys
import tempfile
from pathlib import Path


RELEASE = Path(__file__).resolve().parents[1]
MODULE = RELEASE.parent.parent
ROOT = RELEASE.parents[4]
TOOLS = ROOT / "voltage-modular" / "colorbox" / "tools"
sys.path.insert(0, str(TOOLS))
from support import normalized  # noqa: E402
from value_tree import Reader, encode, props  # noqa: E402


parser = argparse.ArgumentParser()
parser.add_argument("--sdk", type=Path, default=Path(r"C:\ProgramData\Voltage\voltage.jar"))
args = parser.parse_args()


def require(condition: bool, message: str) -> None:
    if not condition:
        raise SystemExit(message)


java_path = RELEASE / "c234.java"
vmod_path = RELEASE / "c234.vmod"
hero_path = RELEASE / "c234_hero.png"
for path in (java_path, vmod_path, hero_path, RELEASE / "README.md", RELEASE / "USER-MANUAL.md"):
    require(path.is_file(), f"Missing release file: {path}")

java_source = java_path.read_text(encoding="utf-8-sig")
raw_project = vmod_path.read_bytes()
reader = Reader(raw_project)
project = reader.tree()
require(reader.p == len(raw_project), "Designer project parser did not consume the file")
require(encode(project) == raw_project, "Designer project failed byte-for-byte round-trip")
embedded_source = props(next(node for node in project[2] if node[0] == "module code"))["MODULE.SOURCE"]
require(normalized(java_source) == normalized(embedded_source), "Exported and embedded Java differ")
require(props(project).get("notes") == "v1.0.0", "Designer module Notes must be exactly v1.0.0")

controls = next(node for node in project[2] if node[0] == "controls")[2]
names = [props(control)["variable name"] for control in controls]
display_names = [props(control)["display name"] for control in controls]
require(len(names) == len(set(names)), "Duplicate Designer variable names")
require(all(display and display.strip() for display in display_names), "Empty Designer Display Name")
require(not any(re.fullmatch(r"(?:knob|inputJack|outputJack|textLabel|logoLabel)\d+", name)
                for name in names), "Generic generated control identifier remains")
for control in controls:
    control_props = props(control)
    require(not control_props.get("notes"), f"Working Notes remain on {control_props['variable name']}")

bypass_region = java_source.split("//[user-ProcessBypassedSample]", 1)[1].split(
    "//[/user-ProcessBypassedSample]", 1
)[0]
require("outputJack.SetValue(readInput(signalInputJack))" in bypass_region,
        "Bypass no longer directly routes X to Z")
require("GetValue()" not in bypass_region and "process(" not in bypass_region,
        "Bypass reads controls or runs DSP")
require("return \"BAL / UNBAL (\"" in java_source and "return \"Amplitude (\"" in java_source,
        "Expected descriptive control tooltips are missing")

manifest_path = RELEASE / "SHA256.json"
manifest = json.loads(manifest_path.read_text(encoding="utf-8"))
for relative, expected in manifest.items():
    target = RELEASE / relative
    require(target.is_file(), f"Missing file listed in SHA-256 manifest: {relative}")
    actual = hashlib.sha256(target.read_bytes()).hexdigest()
    require(actual == expected, f"SHA-256 mismatch: {relative}")

if not args.sdk.is_file():
    raise SystemExit(f"Voltage Modular SDK not found: {args.sdk}")

with tempfile.TemporaryDirectory(prefix="c234-validation-") as temporary:
    build = Path(temporary)
    exported_classes = build / "exported-classes"
    embedded_classes = build / "embedded-classes"
    test_classes = build / "test-classes"
    embedded_source_path = build / "embedded" / "c234.java"
    for directory in (exported_classes, embedded_classes, test_classes, embedded_source_path.parent):
        directory.mkdir(parents=True, exist_ok=True)
    embedded_source_path.write_text(embedded_source, encoding="utf-8")

    for source_path, classes in ((java_path, exported_classes),
                                 (embedded_source_path, embedded_classes)):
        subprocess.run([
            "javac", "--release", "17", "-Xlint:unchecked", "-Werror",
            "-cp", str(args.sdk), "-d", str(classes), str(source_path),
        ], check=True)

    test_source = RELEASE / "tests" / "BalancedModulatorChecks.java"
    subprocess.run([
            "javac", "--release", "17", "-Xlint:unchecked", "-Werror",
        "-cp", str(args.sdk), "-d", str(test_classes), str(test_source),
    ], check=True)
    for classes in (exported_classes, embedded_classes):
        classpath = f"{classes};{test_classes};{args.sdk}"
        subprocess.run([
            "java", "-cp", classpath, "com.insectlabs.c234.BalancedModulatorChecks",
        ], check=True)

print("Project/source integrity, metadata, hashes, Java 17 compilation, bypass routing, and DSP checks passed.")
