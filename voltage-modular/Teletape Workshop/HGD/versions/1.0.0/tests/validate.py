"""Compile and exercise the exact HGD source pair with a headless DSP harness."""
from pathlib import Path
import argparse
import subprocess
import sys
import tempfile

module_dir = Path(__file__).resolve().parents[1]
repo = next(path for path in module_dir.parents if (path / "voltage-modular").is_dir())
sys.path.insert(0, str(repo / "voltage-modular/colorbox/tools"))
from value_tree import Reader, encode, props
from support import normalized


def java_block(source: str, declaration: str) -> str:
    start = source.index(declaration)
    brace = source.index("{", start)
    depth = 0
    state = "code"
    escaped = False
    i = brace
    while i < len(source):
        char = source[i]
        nxt = source[i + 1] if i + 1 < len(source) else ""
        if state == "code":
            if char == "/" and nxt == "/":
                state = "line-comment"
                i += 1
            elif char == "/" and nxt == "*":
                state = "block-comment"
                i += 1
            elif char == '"':
                state, escaped = "string", False
            elif char == "'":
                state, escaped = "char", False
            elif char == "{":
                depth += 1
            elif char == "}":
                depth -= 1
                if depth == 0:
                    block = source[start:i + 1]
                    return block.replace("private static final class HgdCore", "final class HgdCore", 1)
        elif state == "line-comment":
            if char == "\n":
                state = "code"
        elif state == "block-comment":
            if char == "*" and nxt == "/":
                state = "code"
                i += 1
        elif state in ("string", "char"):
            if escaped:
                escaped = False
            elif char == "\\":
                escaped = True
            elif (state == "string" and char == '"') or (state == "char" and char == "'"):
                state = "code"
        i += 1
    raise AssertionError(f"Unclosed Java block: {declaration}")


def compile_java(javac: Path, sources: list[Path], sdk: Path, output: Path) -> None:
    subprocess.run([str(javac), "--release", "17", "-Xlint:all", "-Werror", "-encoding", "UTF-8",
                    "-classpath", str(sdk), "-d", str(output), *(str(p) for p in sources)], check=True)


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--sdk", type=Path, default=Path(r"C:\ProgramData\Voltage\voltage.jar"))
    parser.add_argument("--javac", type=Path, default=Path(r"C:\Program Files\Java\jdk-27\bin\javac.exe"))
    parser.add_argument("--java", type=Path, default=Path(r"C:\Program Files\Java\jdk-27\bin\java.exe"))
    args = parser.parse_args()

    source_path = module_dir / "hgd.java"
    vmod_path = module_dir / "hgd.vmod"
    source = source_path.read_text(encoding="utf-8-sig")
    raw = vmod_path.read_bytes()
    reader = Reader(raw)
    tree = reader.tree()
    assert reader.p == len(raw) and encode(tree) == raw, "Designer round-trip must preserve bytes"
    module_code = next(child for child in tree[2] if child[0] == "module code")
    embedded = props(module_code)["MODULE.SOURCE"]
    assert normalized(source) == normalized(embedded), "exported and embedded Java differ"
    metadata = props(tree)
    assert metadata.get("class name") == "hgd"
    assert metadata.get("package name") == "com.insectlabs.hgd"
    assert metadata.get("notes", "").strip() == "v1.0.0"

    controls = next(child for child in tree[2] if child[0] == "controls")[2]
    component_props = [props(control) for control in controls]
    assert len({item["UUID"] for item in component_props}) == len(component_props), "control UUIDs must be unique"
    assert all("_" not in item["display name"] for item in component_props), "display names must be human-readable"
    assert all("_" not in item["variable name"] for item in component_props), "variable names must use lowerCamelCase"
    for index in range(1, 5):
        gain = next(item for item in component_props if item["variable name"] == f"knobGain{index}")
        tone = next(item for item in component_props if item["variable name"] == f"knobTone{index}")
        assert (float(gain["minValueString"]), float(gain["maxValueString"],),
                float(gain["defaultValueString"])) == (0.0, 1.0, 0.0), f"gain {index} range/default"
        assert (float(tone["minValueString"]), float(tone["maxValueString"]),
                float(tone["defaultValueString"])) == (-1.0, 1.0, 0.0), f"tone {index} range/default"
    for index, expected in enumerate((3, 2, 1, 0), 1):
        assert f'new VoltageSwitch( "switch{index}", "Mode Selector Switch {index}", this, {expected} )' in source, \
            f"voice selector {index} shipped position"

    protected = next(child for child in module_code[2] if child[0] == "line properties")[2]
    line_numbers = [props(line)["line number"] for line in protected]
    source_lines = embedded.splitlines()
    assert all(0 <= line < len(source_lines) for line in line_numbers), "Designer editor anchors must be in range"
    assert args.sdk.is_file(), f"Voltage SDK not found: {args.sdk}"
    assert args.javac.is_file(), f"javac not found: {args.javac}"
    assert args.java.is_file(), f"java not found: {args.java}"

    test_source = Path(__file__).with_name("HgdCoreTest.java")
    core_source = java_block(source, "private static final class HgdCore {")
    scratch = repo / "tmp"
    scratch.mkdir(exist_ok=True)
    with tempfile.TemporaryDirectory(prefix="hgd-validation-", dir=scratch) as temporary:
        build = Path(temporary)
        for label, text in (("exported", source), ("embedded", embedded)):
            output = build / label
            output.mkdir()
            java_file = output / "hgd.java"
            java_file.write_text(text, encoding="utf-8", newline="\n")
            compile_java(args.javac, [java_file], args.sdk, output)
            print(f"PASS: {label} Java compiles against Voltage SDK", flush=True)
        dsp_dir = build / "dsp"
        dsp_dir.mkdir()
        core_file = dsp_dir / "HgdCore.java"
        core_file.write_text("package com.insectlabs.hgd;\n" + core_source + "\n", encoding="utf-8", newline="\n")
        compile_java(args.javac, [core_file, test_source], args.sdk, dsp_dir)
        subprocess.run([str(args.java), "-cp", str(dsp_dir), "com.insectlabs.hgd.HgdCoreTest"], check=True)
    print("PASS: VMOD round-trip, source pairing, release metadata, control defaults, and DSP harness")


if __name__ == "__main__":
    main()
