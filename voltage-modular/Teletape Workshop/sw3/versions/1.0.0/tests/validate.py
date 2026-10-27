"""Validate the archived SW3 pair and run the SDK-independent switch-core checks."""
from pathlib import Path
import argparse
import hashlib
import json
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
                    block = block.replace("private static final class Sw3Core",
                                          "public final class Sw3Core", 1)
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

    source_path = module / "sw3.java"
    project_path = module / "sw3.vmod"
    source = source_path.read_text(encoding="utf-8-sig")
    raw = project_path.read_bytes()
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
    assert metadata["class name"] == "sw3"
    assert metadata["package name"] == "com.insectlabs.sw3"
    assert metadata["module type"] == "ModuleType.ModuleType_Switches"
    assert metadata["notes"].strip() == "v1.0.0"
    controls = next(child for child in tree[2] if child[0] == "controls")[2]
    control_props = [props(control) for control in controls]
    assert len({item["UUID"] for item in control_props}) == len(control_props), "control UUIDs unique"
    assert all(not item.get("notes", "").strip() for item in control_props), "stale control notes removed"
    for variable, minimum, maximum, default in (
        ("knobTrimX", -10.0, 10.0, 0.0),
        ("knobTrimY", -10.0, 10.0, 0.0),
        ("knobFade", 0.0, 200.0, 0.0),
    ):
        control = next(item for item in control_props if item["variable name"] == variable)
        assert (float(control["minValueString"]), float(control["maxValueString"]),
                float(control["defaultValueString"])) == (minimum, maximum, default), variable

    exported_core = java_block(source, "private static final class Sw3Core {")
    primitive_path = repo / "switch-primitives/src/com/insectlabs/switches/Sw3Core.java"
    primitive_source = primitive_path.read_text(encoding="utf-8")
    primitive_core = java_block(primitive_source, "public final class Sw3Core {")
    assert normalized(exported_core) == normalized(primitive_core), "archived primitive differs from release core"

    protected = next(child for child in module_code[2] if child[0] == "line properties")[2]
    line_numbers = [props(line)["line number"] for line in protected]
    assert line_numbers == sorted(set(line_numbers)), "Designer line anchors are ordered and unique"
    assert all(0 <= line < len(embedded.splitlines()) for line in line_numbers), "Designer line anchor bounds"
    assert (module / "sw3_hero.png").is_file() and (module / "sw3_hero.png").stat().st_size > 10_000

    assert args.sdk.is_file(), f"Voltage SDK not found: {args.sdk}"
    assert Path(args.javac).is_file(), f"javac not found: {args.javac}"
    assert Path(args.java).is_file(), f"java not found: {args.java}"

    test_source = test_dir / "Sw3CoreTest.java"
    with tempfile.TemporaryDirectory(prefix=".sw3-validation-", dir=repo) as temporary:
        build = Path(temporary)
        for label, java_text in (("exported", source), ("embedded", embedded)):
            folder = build / label
            folder.mkdir()
            java_file = folder / "sw3.java"
            java_file.write_text(java_text, encoding="utf-8", newline="\n")
            compile_java(args.javac, [java_file], args.sdk, folder)
            print(f"PASS: {label} Java compiles against Voltage SDK", flush=True)

        test_folder = build / "core-tests"
        test_folder.mkdir()
        core_file = test_folder / "Sw3Core.java"
        core_file.write_text(primitive_source, encoding="utf-8", newline="\n")
        test_java = test_folder / "Sw3CoreTest.java"
        test_java.write_text(test_source.read_text(encoding="utf-8"), encoding="utf-8", newline="\n")
        compile_java(args.javac, [core_file, test_java], None, test_folder)
        subprocess.run([args.java, "-cp", str(test_folder), "com.insectlabs.switches.Sw3CoreTest"], check=True)

    print("PASS: SHA-256, Designer round-trip, identity, notes, ranges, UUIDs, source parity, primitive parity, and line anchors")


if __name__ == "__main__":
    main()
