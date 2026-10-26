"""Validate the canonical SN93T pair against the Voltage SDK and deterministic signal tests."""
from pathlib import Path
import argparse
import hashlib
import json
import subprocess
import sys
import tempfile

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
    version = Path(__file__).resolve().parent.parent
    repo = next(path for path in version.parents if (path / "voltage-modular").is_dir())
    sys.path.insert(0, str(repo / "voltage-modular/colorbox/tools"))
    from support import normalized
    from value_tree import Reader, encode, props

    source_path = version / "sn93t.java"
    vmod_path = version / "sn93t.vmod"
    source = source_path.read_text(encoding="utf-8-sig")
    raw = vmod_path.read_bytes()
    reader = Reader(raw)
    tree = reader.tree()
    assert reader.p == len(raw) and encode(tree) == raw, "Designer binary round-trip"
    metadata = props(tree)
    assert metadata["class name"] == "sn93t"
    assert metadata["package name"] == "com.insectlabs.sn93t"
    assert metadata["notes"].strip() == "sn93t\nv1.1.0"
    code = next(child for child in tree[2] if child[0] == "module code")
    embedded = props(code)["MODULE.SOURCE"]
    assert normalized(source) == normalized(embedded), "exported/embedded Java mismatch"
    assert "MPM_K = 0.80" in source and "MPM_K = 0.80" in embedded
    wiring_checks = (
        "private static final double DETECTOR_GAIN = 5.0",
        "thruput.SetValue( muteOn ? 0.0 : x / DETECTOR_GAIN )",
        "outputTone.SetValue( core.toneSample() )",
        "outputCents.SetValue( lastCentsV )",
        "cvCentsVolts = Math.max(-5, Math.min(5, centsErr / 10.0))",
        "outputVoct.SetValue( lastPitchV * CV_JACK_PER_VOLT )",
        "outputCal.SetValue( core.calCv * CV_JACK_PER_VOLT )",
    )
    assert all(check in source and check in embedded for check in wiring_checks), "candidate voltage scaling/wiring"
    controls = next(child for child in tree[2] if child[0] == "controls")[2]
    control_props = [props(control) for control in controls]
    uuids = [control["UUID"] for control in control_props]
    assert len(uuids) == len(set(uuids)), "control UUIDs must be unique"
    by_name = {control["variable name"]: control for control in control_props}
    assert all(name in by_name for name in ("input", "thruput", "outputTone", "outputVoct", "outputCents", "outputGate", "outputCal"))
    assert len([control for control in control_props if control["Control Type"] == 4]) == 5, "expected five panel buttons"
    protected = next(child for child in code[2] if child[0] == "line properties")[2]
    line_numbers = [props(line)["line number"] for line in protected]
    lines = embedded.splitlines()
    assert line_numbers == sorted(set(line_numbers)) and all(0 <= line < len(lines) for line in line_numbers), "protected-line anchors"
    print(f"PASS: Designer round-trip, source parity, module identity and Notes, {len(control_props)} controls, unique UUIDs, button/jack presence, protected lines and voltage-output wiring", flush=True)

    manifest = json.loads((version / "SHA256.json").read_text(encoding="utf-8"))
    for relative, expected in manifest.items():
        actual = hashlib.sha256((version / relative).read_bytes()).hexdigest()
        assert actual == expected, f"SHA-256 mismatch: {relative}"
    assert args.sdk.is_file(), f"Voltage SDK not found: {args.sdk}"
    assert Path(args.javac).is_file(), f"javac not found: {args.javac}"
    assert Path(args.java).is_file(), f"java not found: {args.java}"
    test_source = version / "tests/TnrCoreTest.java"
    fixture_dir = version / "tests/fixtures/tunings"
    with tempfile.TemporaryDirectory(prefix="sn93t-validation-", dir=version) as temporary:
        build = Path(temporary)
        for label, java_text in (("exported", source), ("embedded", embedded)):
            folder = build / label
            folder.mkdir()
            java_file = folder / "sn93t.java"
            java_file.write_text(java_text, encoding="utf-8", newline="\n")
            compile_java(args.javac, [java_file], args.sdk, folder)
            print(f"PASS: {label} Java compiles against Voltage SDK", flush=True)
        test_folder = build / "core-tests"
        test_folder.mkdir()
        test_java = test_folder / "TnrCoreTest.java"
        test_java.write_text(test_source.read_text(encoding="utf-8"), encoding="utf-8", newline="\n")
        compile_java(args.javac, [source_path, test_java], args.sdk, test_folder)
        subprocess.run([args.java, "-cp", f"{test_folder};{args.sdk}", "com.insectlabs.sn93t.TnrCoreTest", str(fixture_dir)], check=True)
    print("PASS: SHA-256 manifest and all deterministic core tests", flush=True)

if __name__ == "__main__":
    main()