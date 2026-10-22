"""Compile and exercise the exact FMC source pair and its framework-free DSP core."""
from __future__ import annotations

from pathlib import Path
import argparse
import hashlib
import os
import re
import shutil
import subprocess
import tempfile
import sys

TESTS = Path(__file__).resolve().parent
DEVELOPMENT = TESTS.parent
MODULE_ROOT = DEVELOPMENT.parent
REPOSITORY = next(parent for parent in MODULE_ROOT.parents if (parent / "dsp-primitives").is_dir())
sys.path.insert(0, str(REPOSITORY / "voltage-modular" / "colorbox" / "tools"))
from value_tree import Reader, encode, props
from support import normalized


def java_block(source: str, declaration: str) -> str:
    start = source.index(declaration)
    brace = source.index("{", start)
    depth = 0
    state = "code"
    escaped = False
    for pos in range(brace, len(source)):
        char = source[pos]
        nxt = source[pos + 1] if pos + 1 < len(source) else ""
        if state == "code":
            if char == "/" and nxt == "/":
                state = "line-comment"
            elif char == "/" and nxt == "*":
                state = "block-comment"
            elif char == '"':
                state, escaped = "string", False
            elif char == "'":
                state, escaped = "char", False
            elif char == "{":
                depth += 1
            elif char == "}":
                depth -= 1
                if depth == 0:
                    return source[start:pos + 1].replace(
                        "private static final class FmcCore", "public final class FmcCore", 1)
        elif state == "line-comment":
            if char == "\n": state = "code"
        elif state == "block-comment":
            if char == "*" and nxt == "/": state = "code"
        elif state in ("string", "char"):
            if escaped:
                escaped = False
            elif char == "\\":
                escaped = True
            elif (state == "string" and char == '"') or (state == "char" and char == "'"):
                state = "code"
    raise AssertionError(f"Unclosed Java block: {declaration}")


def strip_method_body(source: str, signature: str) -> str:
    start = source.index(signature)
    opening = source.index("{", start)
    depth = 0
    state = "code"
    escaped = False
    for pos in range(opening, len(source)):
        char = source[pos]
        nxt = source[pos + 1] if pos + 1 < len(source) else ""
        if state == "code":
            if char == "/" and nxt == "/": state = "line-comment"
            elif char == "/" and nxt == "*": state = "block-comment"
            elif char == '"': state, escaped = "string", False
            elif char == "'": state, escaped = "char", False
            elif char == "{": depth += 1
            elif char == "}":
                depth -= 1
                if depth == 0:
                    return source[:opening] + "{\n    // Designer controls supplied by VMOD metadata.\n}" + source[pos + 1:]
        elif state == "line-comment":
            if char == "\n": state = "code"
        elif state == "block-comment":
            if char == "*" and nxt == "/": state = "code"
        elif state in ("string", "char"):
            if escaped: escaped = False
            elif char == "\\\\": escaped = True
            elif (state == "string" and char == '"') or (state == "char" and char == "'"): state = "code"
    raise AssertionError(f"Unclosed method: {signature}")

def compile_java(javac: Path, source: Path, sdk: Path, output: Path) -> None:
    subprocess.run([str(javac), "--release", "17", "-Xlint:all,-options", "-Werror", "-encoding", "UTF-8",
                    "-classpath", str(sdk), "-d", str(output), str(source)], check=True)


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--sdk", type=Path, default=Path(r"C:/ProgramData/Voltage/voltage.jar"))
    parser.add_argument("--javac", type=Path, default=Path(r"C:/Program Files/Java/jdk-27/bin/javac.exe"))
    parser.add_argument("--java", type=Path, default=Path(r"C:/Program Files/Java/jdk-27/bin/java.exe"))
    args = parser.parse_args()

    java_path, vmod_path, hero_path = DEVELOPMENT / "fmc.java", DEVELOPMENT / "fmc.vmod", DEVELOPMENT / "fmc_hero.png"
    source = java_path.read_text(encoding="utf-8-sig")
    raw = vmod_path.read_bytes()
    reader = Reader(raw)
    tree = reader.tree()
    assert reader.p == len(raw) and encode(tree) == raw, "VMOD value-tree round-trip changed bytes"
    module = props(tree)
    code = next(child for child in tree[2] if child[0] == "module code")
    embedded = props(code)["MODULE.SOURCE"]
    assert normalized(source) == normalized(embedded), "exported and embedded Java differ"
    assert module.get("class name") == "fmc" and module.get("package name") == "com.insectlabs.fmc"
    assert module.get("notes", "").strip() == "v1.0.0", "Designer Notes must match canonical version"
    assert int.from_bytes(hero_path.read_bytes()[16:20], "big") == 230, "hero width must match four-channel panel"
    assert int.from_bytes(hero_path.read_bytes()[20:24], "big") == 720, "hero height must match four-channel panel"

    controls = next(child for child in tree[2] if child[0] == "controls")[2]
    items = [props(control) for control in controls]
    variables = [item.get("variable name", "") for item in items]
    uuids = [item.get("UUID", "") for item in items]
    assert len(items) == 59, f"expected 59 controls, found {len(items)}"
    assert len(set(uuids)) == len(uuids) and all(uuids), "control UUIDs must be unique"
    assert all(re.fullmatch(r"[a-z][A-Za-z0-9]*", name) for name in variables), "control variables use lowerCamelCase"
    assert all("_" not in item.get("display name", "") for item in items), "display names must be human readable"
    constructors = {match.group(1): match.group(2) for match in re.finditer(
        r'new\s+Voltage\w+\(\s*"([^"]+)"\s*,\s*"([^"]+)"', source)}
    for item in items:
        if item.get("Control Type") == 31:  # Designer panel guide lines are metadata-only.
            continue
        name = item["variable name"]
        assert constructors.get(name) == item.get("display name"), f"Java/Designer identity mismatch: {name}"
    for channel, position in enumerate((3, 2, 1, 0), 1):
        selector = f"channel{('One', 'Two', 'Three', 'Four')[channel - 1]}VoiceSwitch"
        assert re.search(rf'new VoltageSwitch\(\s*"{selector}"\s*,\s*"Channel {channel} Voice Selector"\s*,\s*this\s*,\s*{position}\s*\)', source), \
            f"channel {channel} selector default/identity"
    line_properties = next(child for child in code[2] if child[0] == "line properties")
    line_count = len(embedded.splitlines())
    assert all(0 <= props(anchor)["line number"] < line_count for anchor in line_properties[2]), \
        "Designer source anchors must target embedded lines"
    assert source.count("super.EditComponentValue( component, newValue, newText );") == 1, \
        "typed edit must invoke SDK handler exactly once"
    bypass = java_block(source, "public void ProcessBypassedSample()")
    assert "mixOutput.SetValue(FmcCore.MIX_SCALE * sum);" in bypass, "bypass preserves the half-sum mix output"
    assert "resumePending = true;" in bypass, "bypass resume policy is marked"
    assert args.sdk.is_file() and args.javac.is_file() and args.java.is_file()

    with tempfile.TemporaryDirectory(prefix="fmc-validation-", dir=REPOSITORY / "tmp") as temporary:
        build = Path(temporary)
        for label, text in (("exported", source), ("embedded", embedded)):
            out = build / label
            out.mkdir()
            file = out / "fmc.java"
            file.write_text(text, encoding="utf-8", newline="\n")
            compile_java(args.javac, file, args.sdk, out)
            print(f"PASS: {label} Java compiles against Voltage SDK", flush=True)
        dsp = build / "dsp"
        dsp.mkdir()
        core_file = dsp / "FmcCore.java"
        core_file.write_text("package com.insectlabs.fmc;\n" + java_block(source, "private static final class FmcCore {") + "\n",
                             encoding="utf-8", newline="\n")
        test_file = TESTS / "FmcCoreTest.java"
        subprocess.run([str(args.javac), "--release", "17", "-Xlint:all,-options", "-Werror", "-encoding", "UTF-8",
                        "-d", str(dsp), str(core_file), str(test_file)], check=True)
        subprocess.run([str(args.java), "-cp", str(dsp), "com.insectlabs.fmc.FmcCoreTest"], check=True)
        stubs = TESTS / "stubs"
        module_source = build / "module"
        module_source.mkdir()
        assembled = module_source / "fmc.java"
        assembled.write_text(strip_method_body(source, "void InitializeControls()"), encoding="utf-8", newline="\n")
        wiring_out = build / "wiring"
        wiring_out.mkdir()
        stub_files = sorted(stubs.rglob("*.java"))
        wiring_test = TESTS / "FmcModuleWiringTest.java"
        subprocess.run([str(args.javac), "--release", "17", "-Xlint:all,-options", "-Werror", "-encoding", "UTF-8",
                        "-d", str(wiring_out), *(str(path) for path in stub_files), str(assembled),
                        str(core_file), str(wiring_test)], check=True)
        subprocess.run([str(args.java), "-cp", str(wiring_out), "com.insectlabs.fmc.FmcModuleWiringTest"], check=True)
    print("PASS: VMOD round-trip, source parity, notes/version, control identity/defaults, editor anchors, and core tests")


if __name__ == "__main__":
    main()