#!/usr/bin/env python3
"""Validate CED's exported/embedded pair and its Dimension/CE-1 DSP implementation."""
from __future__ import annotations

import argparse
import os
from pathlib import Path
import re
import shutil
import subprocess
import sys
import tempfile

TESTS = Path(__file__).resolve().parent
DEVELOPMENT = TESTS.parent
REPOSITORY = next(parent for parent in TESTS.parents if (parent / "dsp-primitives").is_dir())
PRIMITIVE = REPOSITORY / "dsp-primitives/src/com/insectlabs/dsp/chorus/ChorusCore.java"
PRIMITIVE_TEST = REPOSITORY / "dsp-primitives/tests/ChorusCoreTest.java"
sys.path.insert(0, str(REPOSITORY / "voltage-modular/colorbox/tools"))
import value_tree as vt  # noqa: E402


def run(command: list[str]) -> None:
    print(f"RUN {Path(command[0]).name}: {' '.join(command[1:])}", flush=True)
    subprocess.run(command, check=True)


def strip_method_body(source: str, signature: str) -> str:
    start = source.index(signature)
    opening = source.index("{", start)
    depth = 0
    for pos in range(opening, len(source)):
        if source[pos] == "{":
            depth += 1
        elif source[pos] == "}":
            depth -= 1
            if depth == 0:
                return source[:opening] + "{\n    // Designer controls are injected by ModuleWiringTest.\n}" + source[pos + 1:]
    raise ValueError(f"unclosed method: {signature}")


def normalized_region_text(source: str) -> str:
    return "\n".join(line.strip() for line in source.splitlines() if line.strip())


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--module-dir", type=Path, help="folder containing ced.java and ced.vmod")
    parser.add_argument("--notes", default="v1.0.0", help="expected Designer module Notes version marker")
    parser.add_argument(
        "--sdk-jar",
        type=Path,
        default=Path(os.environ.get("VOLTAGE_SDK_JAR", r"C:/ProgramData/Voltage/voltage.jar")),
    )
    parser.add_argument("--java-home", type=Path, default=Path(os.environ["JAVA_HOME"]) if os.environ.get("JAVA_HOME") else None)
    parser.add_argument("--demo-wav", type=Path, help="also render the deterministic 24-bit CED demo WAV")
    args = parser.parse_args()
    module_dir = args.module_dir or (DEVELOPMENT / "module" if (DEVELOPMENT / "module").is_dir() else DEVELOPMENT)
    java_file, vmod_file = module_dir / "ced.java", module_dir / "ced.vmod"
    if not java_file.is_file() or not vmod_file.is_file():
        raise FileNotFoundError(f"expected ced.java and ced.vmod under {module_dir}")
    if not args.sdk_jar.is_file():
        raise FileNotFoundError(f"Voltage SDK jar not found: {args.sdk_jar}; pass --sdk-jar or set VOLTAGE_SDK_JAR")
    java_bin = args.java_home / "bin" if args.java_home else None
    javac = str((java_bin / "javac.exe") if java_bin and (java_bin / "javac.exe").exists() else shutil.which("javac") or "javac")
    java = str((java_bin / "java.exe") if java_bin and (java_bin / "java.exe").exists() else shutil.which("java") or "java")
    java_source = java_file.read_text(encoding="utf-8")
    raw_vmod = vmod_file.read_bytes()
    tree = vt.Reader(raw_vmod).tree()
    if vt.encode(tree) != raw_vmod:
        raise AssertionError("VMOD value-tree roundtrip changed bytes")

    module_node = next(node for node in tree[2] if node[0] == "module code")
    props = vt.props(tree)
    embedded = vt.props(module_node)["MODULE.SOURCE"]
    if props.get("class name") != "ced" or props.get("package name") != "com.insectlabs.ced":
        raise AssertionError("VMOD module identity does not match the exported CED class")
    if props.get("notes", "").strip() != args.notes:
        raise AssertionError(f"module Notes must be exactly {args.notes}, got {props.get('notes')!r}")
    controls = next(node for node in tree[2] if node[0] == "controls")
    variables = [vt.props(control).get("variable name", "") for control in controls[2]]
    uuids = [vt.props(control).get("UUID", "") for control in controls[2]]
    if len(variables) != 49 or any(not re.fullmatch(r"[a-z][A-Za-z0-9]*", name) for name in variables):
        raise AssertionError(f"control count/naming failed: {len(variables)} controls")
    if len(set(uuids)) != len(uuids) or any(not value for value in uuids):
        raise AssertionError("control UUIDs must be present and unique")
    regions = re.compile(r"//\[user-([^\]]+)\][^\n]*\n(.*?)//\[/user-\1\]", re.S)
    normalized_embedded = normalized_region_text(embedded)
    embedded_region_count = 0
    for match in regions.finditer(java_source):
        body = normalized_region_text(match.group(2))
        if body and body not in normalized_embedded:
            raise AssertionError(f"exported user region missing or stale in VMOD source: {match.group(1)}")
        embedded_region_count += 1
    if embedded_region_count != 15:
        raise AssertionError(f"expected 15 exported Designer regions, found {embedded_region_count}")

    with tempfile.TemporaryDirectory(prefix=".ced-validation-", dir=REPOSITORY, ignore_cleanup_errors=True) as tmp_name:
        print("PASS VMOD value-tree and metadata checks", flush=True)
        tmp = Path(tmp_name)
        sdk_classes, embedded_classes, test_classes = (tmp / name for name in ("sdk", "embedded", "tests"))
        for folder in (sdk_classes, embedded_classes, test_classes):
            folder.mkdir()
        embedded_file = tmp / "ced.java"
        embedded_file.write_text(embedded, encoding="utf-8", newline="\n")
        lint = ["--release", "17", "-encoding", "UTF-8", "-Xlint:all,-options", "-Werror"]
        run([javac, *lint, "-cp", str(args.sdk_jar), "-d", str(sdk_classes), str(java_file)])
        run([javac, *lint, "-cp", str(args.sdk_jar), "-d", str(embedded_classes), str(embedded_file)])
        print(f"PASS Java/VMOD compile; value-tree roundtrip; {len(variables)} named controls; {embedded_region_count} synchronized user regions")

        test_module = tmp / "ced.java"
        test_module.write_text(strip_method_body(java_source, "void InitializeControls()"), encoding="utf-8", newline="\n")
        test_file = tmp / "ModuleWiringTest.java"
        test_file.write_text((TESTS / "ModuleWiringTest.java").read_text(encoding="utf-8"), encoding="utf-8", newline="\n")
        stub_dir = TESTS / "stubs" if (TESTS / "stubs").is_dir() else DEVELOPMENT / "stubs"
        stub_files = sorted(stub_dir.rglob("*.java"))
        run([javac, *lint, "-d", str(test_classes), *(str(file) for file in stub_files), str(test_module), str(test_file), str(PRIMITIVE)])
        run([java, "-cp", str(test_classes), "com.insectlabs.ced.ModuleWiringTest"])
        run([javac, *lint, "-d", str(test_classes), str(PRIMITIVE), str(PRIMITIVE_TEST)])
        command = [java, "-cp", str(test_classes), "com.insectlabs.dsp.chorus.ChorusCoreTest"]
        if args.demo_wav:
            args.demo_wav.parent.mkdir(parents=True, exist_ok=True)
            command.append(str(args.demo_wav.resolve()))
        run(command)
    print("ALL CED VALIDATIONS PASSED (wiring checks use stubs; no native Designer run)")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())

