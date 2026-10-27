"""Validate the swCXI draft pair and run SDK-independent routing checks."""
from pathlib import Path
import argparse
import hashlib
import json
import os
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
                state, escaped = "string", False
            elif char == "'":
                state, escaped = "char", False
            elif char == "{":
                depth += 1
            elif char == "}":
                depth -= 1
                if depth == 0:
                    block = source[start:i + 1]
                    block = block.replace("private static final class SwitchCore",
                                          "final class SwitchCore", 1)
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


def compile_java(javac: str, sources: list[Path], sdk: Path | None, output: Path) -> None:
    command = [javac, "--release", "17", "-Xlint:all", "-Werror", "-encoding", "UTF-8"]
    if sdk is not None:
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
    module = test_dir.parent
    repo = next(path for path in module.parents if (path / "voltage-modular").is_dir())
    sys.dont_write_bytecode = True
    sys.path.insert(0, str(repo / "voltage-modular/colorbox/tools"))
    from support import normalized
    from value_tree import Reader, encode, props

    source_path = module / "swCXI.java"
    project_path = module / "swCXI.vmod"
    source = source_path.read_text(encoding="utf-8-sig")
    raw = project_path.read_bytes()
    reader = Reader(raw)
    tree = reader.tree()
    assert reader.p == len(raw) and encode(tree) == raw, "Designer project round-trip"
    module_code = next(child for child in tree[2] if child[0] == "module code")
    embedded = props(module_code)["MODULE.SOURCE"]
    assert normalized(source) == normalized(embedded), "exported/embedded source mismatch"

    metadata = props(tree)
    assert metadata["class name"] == "swcxi"
    assert metadata["package name"] == "com.insectlabs.swcxi"
    assert metadata["module type"] == "ModuleType.ModuleType_Switches"
    expected_version = "v1.0.0rc" if module.name == "release-candidate" else "v1.0.0"
    assert metadata["notes"].strip() == expected_version
    if (module / "SHA256.json").is_file():
        for name, digest in json.loads((module / "SHA256.json").read_text()).items():
            assert hashlib.sha256((module / name).read_bytes()).hexdigest() == digest, name
    controls = next(child for child in tree[2] if child[0] == "controls")[2]
    control_props = [props(control) for control in controls]
    assert len({item["UUID"] for item in control_props}) == len(control_props), "control UUIDs unique"
    variables = {item.get("variable name") for item in control_props}
    for name in ("inputSelect", "inputClock", "inputReset", "inputCommon", "outputCommon", "knobSelect"):
        assert name in variables, f"missing panel control: {name}"
    line_properties = next(child for child in module_code[2] if child[0] == "line properties")[2]
    line_numbers = [props(line)["line number"] for line in line_properties]
    assert line_numbers == sorted(set(line_numbers)), "Designer source anchors ordered and unique"
    assert all(0 <= line < len(embedded.splitlines()) for line in line_numbers), "Designer anchor bounds"
    assert (module / "swCXI_hero.png").is_file()
    assert all(not item.get("notes", "").strip() for item in control_props), "obsolete working Notes"
    knob = next(item for item in control_props if item.get("variable name") == "knobSelect")
    for key, value in (("minValue", 1.0), ("maxValue", 10.0), ("defaultValue", 1.0),
                       ("minValueString", "1"), ("maxValueString", "10"), ("defaultValueString", "1")):
        assert str(knob[key]) == str(value), key
    baseline_path = module.parents[1] / "research/supplied-v0.1.2/swCXI_v0.1.2.java"
    baseline = baseline_path.read_text(encoding="utf-8-sig")
    baseline_project = baseline_path.with_suffix(".vmod")
    baseline_tree = Reader(baseline_project.read_bytes()).tree()
    before = {props(c)["UUID"]: props(c) for c in next(n for n in baseline_tree[2] if n[0] == "controls")[2]}
    after = {props(c)["UUID"]: props(c) for c in controls}
    assert before.keys() == after.keys(), "control identities preserved"
    for uuid in before:
        for key in ("variable name", "left", "top", "width", "height", "skin"):
            assert before[uuid].get(key) == after[uuid].get(key), (uuid, key)
    assert (module / "swCXI_hero.png").read_bytes() == (module.parents[1] / "research/supplied-v0.1.2/swCXI_hero.png").read_bytes()
    primitive = (repo / "switch-primitives/src/com/insectlabs/switches/SequentialSwitchCore.java").read_text()
    primitive = primitive[primitive.index("public final class SequentialSwitchCore"):].replace("public final class SequentialSwitchCore", "final class SwitchCore").replace("SequentialSwitchCore()", "SwitchCore()")
    assert normalized(java_block(source, "private static final class SwitchCore {")) == normalized(primitive), "primitive parity"


    assert Path(args.javac).is_file(), f"javac not found: {args.javac}"
    assert Path(args.java).is_file(), f"java not found: {args.java}"
    assert args.sdk.is_file(), f"Voltage SDK not found: {args.sdk}"

    with tempfile.TemporaryDirectory(prefix=".swcxi-validation-", dir=repo) as temporary:
        build = Path(temporary)
        for label, java_text in (("exported", source), ("embedded", embedded)):
            folder = build / label
            folder.mkdir()
            java_file = folder / "swcxi.java"
            java_file.write_text(java_text, encoding="utf-8", newline="\n")
            compile_java(args.javac, [java_file], args.sdk, folder)
            print(f"PASS: {label} Java compiles against Voltage SDK", flush=True)

        integration = build / "exported"
        baseline_file = integration / "BaselineModule.java"
        baseline = baseline.replace("public class swcxi", "public class BaselineModule")
        baseline = re.sub(r"public swcxi(?=\s*\()", "public BaselineModule", baseline)
        baseline_file.write_text(baseline, encoding="utf-8")
        wiring_file = integration / "ModuleWiringTest.java"
        wiring_file.write_text((test_dir / "ModuleWiringTest.java").read_text(), encoding="utf-8")
        integration_cp = os.pathsep.join((str(args.sdk), str(integration)))
        compile_java(args.javac, [baseline_file, wiring_file], integration_cp, integration)
        version_text = subprocess.run([args.java, "-version"], capture_output=True, text=True, check=True)
        version_match = re.search(r'version "(\d+)', version_text.stderr + version_text.stdout)
        mutation_flags = ["--enable-final-field-mutation=ALL-UNNAMED"] if version_match and int(version_match[1]) >= 26 else []
        subprocess.run([args.java, *mutation_flags, "-cp", integration_cp, "ModuleWiringTest"], check=True)


        core_folder = build / "core-tests"
        core_folder.mkdir()
        core_file = core_folder / "SwitchCore.java"
        core_file.write_text(java_block(source, "private static final class SwitchCore {"), encoding="utf-8", newline="\n")
        test_file = core_folder / "SwitchCoreTest.java"
        test_file.write_text((test_dir / "SwitchCoreTest.java").read_text(encoding="utf-8"), encoding="utf-8", newline="\n")
        compile_java(args.javac, [core_file, test_file], None, core_folder)
        subprocess.run([args.java, "-cp", str(core_folder), "SwitchCoreTest"], check=True)

    print("PASS: release identity, source parity, Designer binary, controls, anchors, and headless route behavior")


if __name__ == "__main__":
    main()
