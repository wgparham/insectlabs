"""Build and exercise the FPA DSP from the exact exported and embedded source."""
from pathlib import Path
import argparse
import hashlib
import json
import re
import subprocess
import sys
import tempfile
import textwrap

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
                state = "string"
                escaped = False
            elif char == "'":
                state = "char"
                escaped = False
            elif char == "{":
                depth += 1
            elif char == "}":
                depth -= 1
                if depth == 0:
                    block = source[start:i + 1]
                    block = block.replace("private static final class PreampCore", "final class PreampCore", 1)
                    return textwrap.dedent(block)
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


def compile_java(javac: str, sources: list[Path], sdk: Path, output: Path) -> None:
    command = [javac, "--release", "17", "-Xlint:all", "-Werror", "-encoding", "UTF-8"]
    if sdk:
        command += ["-classpath", str(sdk)]
    command += ["-d", str(output), *(str(source) for source in sources)]
    subprocess.run(command, check=True)


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--sdk", type=Path, default=Path(r"C:\ProgramData\Voltage\voltage.jar"))
    parser.add_argument("--javac", default=r"C:\Program Files\Java\jdk-27\bin\javac.exe")
    parser.add_argument("--java", default=r"C:\Program Files\Java\jdk-27\bin\java.exe")
    args = parser.parse_args()

    test_dir = Path(__file__).resolve().parent
    module = test_dir.parent if test_dir.name == "tests" else test_dir
    repo = next(path for path in module.parents if (path / "voltage-modular").is_dir())
    sys.path.insert(0, str(repo / "voltage-modular/colorbox/tools"))
    from support import normalized
    from value_tree import Reader, encode, props

    source = (module / "fpa.java").read_text(encoding="utf-8-sig")
    raw = (module / "fpa.vmod").read_bytes()
    manifest = json.loads((module / "SHA256.json").read_text(encoding="utf-8"))
    for relative, expected in manifest.items():
        actual = hashlib.sha256((module / relative).read_bytes()).hexdigest()
        assert actual == expected, f"SHA-256 mismatch: {relative}"
    reader = Reader(raw)
    tree = reader.tree()
    assert reader.p == len(raw) and encode(tree) == raw, "Designer project round-trip"
    module_code = next(child for child in tree[2] if child[0] == "module code")
    embedded = props(module_code)["MODULE.SOURCE"]
    assert normalized(source) == normalized(embedded), "exported/embedded source mismatch"

    metadata = props(tree)
    assert metadata["class name"] == "fpa"
    assert metadata["package name"] == "com.insectlabs.fpa"
    assert metadata["notes"].strip() == "v1.0.0"
    controls = next(child for child in tree[2] if child[0] == "controls")[2]
    control_props = [props(control) for control in controls]
    assert len({item["UUID"] for item in control_props}) == len(control_props), "control UUIDs unique"
    for index in range(1, 5):
        control = next(item for item in control_props if item["variable name"] == f"knobTone{index}")
        assert (float(control["minValueString"]), float(control["maxValueString"]),
                float(control["defaultValueString"])) == (-1.0, 1.0, 0.0), f"tone {index} range/default"
    for index in range(1, 5):
        control = next(item for item in control_props if item["variable name"] == f"buttonPhase{index}")
        assert control["Control Type"] == 4 and not control["isInitiallyToggled"], f"phase {index} toggle/default"
        assert control["display name"] == f"Phase {index} Polarity"
        assert f'buttonPhase{index} = new VoltageToggle' in source
        assert f'component == buttonPhase{index}' in source, f"phase {index} event wiring"
    assert "toneKnobs = new VoltageKnob[] { knobTone1, knobTone2, knobTone3, knobTone4 }" in source
    display_names = {
        "textLabel3": "C Voicing Label", "textLabel4": "Re Voicing Label",
        "textLabel5": "D Voicing Label", "textLabel6": "O Voicing Label",
        "labelMix": "Mix Output Label", "label1": "Channel Number 1",
        "label2": "Channel Number 2", "label3": "Channel Number 3",
        "label4": "Channel Number 4", "labelTone": "Tone Label",
    }
    for variable, display in display_names.items():
        control = next(item for item in control_props if item["variable name"] == variable)
        assert control["display name"] == display, f"display name for {variable}"
        assert f'"{variable}", "{display}"' in source, f"source display name for {variable}"

    protected = next(child for child in module_code[2] if child[0] == "line properties")[2]
    line_numbers = [props(line)["line number"] for line in protected]
    source_lines = embedded.splitlines()
    assert line_numbers == sorted(set(line_numbers))
    assert all(0 <= line < len(source_lines) for line in line_numbers), "Designer protected-line anchors"

    assert args.sdk.is_file(), f"Voltage SDK not found: {args.sdk}"
    assert Path(args.javac).is_file(), f"javac not found: {args.javac}"
    assert Path(args.java).is_file(), f"java not found: {args.java}"

    test_source = (test_dir / "PreampCoreTest.java").read_text(encoding="utf-8")
    extracted_core = java_block(source, "private static final class PreampCore {")
    with tempfile.TemporaryDirectory(prefix="fpa-validation-") as temporary:
        build = Path(temporary)
        for label, java_text in (("exported", source), ("embedded", embedded)):
            folder = build / label
            folder.mkdir()
            java_file = folder / "fpa.java"
            java_file.write_text(java_text, encoding="utf-8", newline="\n")
            compile_java(args.javac, [java_file], args.sdk, folder)
            print(f"PASS: {label} Java compiles against Voltage SDK", flush=True)

        test_folder = build / "dsp-tests"
        test_folder.mkdir()
        (test_folder / "PreampCore.java").write_text(extracted_core, encoding="utf-8", newline="\n")
        test_java = test_folder / "PreampCoreTest.java"
        test_java.write_text(test_source, encoding="utf-8", newline="\n")
        core_java = test_folder / "PreampCore.java"
        compile_java(args.javac, [core_java, test_java], None, test_folder)
        subprocess.run([args.java, "-cp", str(test_folder), "PreampCoreTest"], check=True)

    print("PASS: SHA-256 manifest, identity, metadata, tone range, unique IDs, Designer round-trip and protected anchors")


if __name__ == "__main__":
    main()
