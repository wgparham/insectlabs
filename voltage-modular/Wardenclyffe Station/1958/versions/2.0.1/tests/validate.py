from __future__ import annotations

import argparse
import math
import os
from pathlib import Path
import subprocess
import sys
import tempfile


def repository_root(version_folder: Path) -> Path:
    configured = os.environ.get("INSECTLABS_VOLTAGE_MODULAR")
    if configured:
        root = Path(configured)
        if (root / "colorbox" / "tools" / "value_tree.py").is_file():
            return root
        raise RuntimeError("INSECTLABS_VOLTAGE_MODULAR does not point to the repository root")
    for parent in version_folder.parents:
        if (parent / "colorbox" / "tools" / "value_tree.py").is_file():
            return parent
    raise RuntimeError("Could not locate the voltage-modular repository root")


def main() -> None:
    parser = argparse.ArgumentParser(description="Validate the Wardenclyffe 1958 v2.0.1 release pair.")
    parser.add_argument("--sdk", type=Path, help="Voltage SDK JAR; compiles both source forms when supplied")
    parser.add_argument("--javac", default=os.environ.get("JAVAC", "javac"), help="Java compiler executable")
    args = parser.parse_args()

    version_folder = Path(__file__).resolve().parent.parent
    repo = repository_root(version_folder)
    sys.path.insert(0, str(repo / "colorbox" / "tools"))
    from value_tree import Reader, encode, props
    from support import normalized

    java_path = version_folder / "nineteenfiftyeight.java"
    vmod_path = version_folder / "nineteenfiftyeight.vmod"
    java = java_path.read_bytes().decode("utf-8-sig").replace("\r\n", "\n").replace("\r", "\n")
    raw = vmod_path.read_bytes()
    reader = Reader(raw)
    tree = reader.tree()
    assert reader.p == len(raw) and encode(tree) == raw, "VMOD serialization round-trip failed"
    root = props(tree)
    assert root.get("notes") == "v2.0.1", "Release Notes field is not v2.0.1"
    assert root.get("module type") == "ModuleType.ModuleType_Oscillators", "Wrong module category"
    code = next(child for child in tree[2] if child[0] == "module code")
    embedded = props(code)["MODULE.SOURCE"]
    assert normalized(java) == normalized(embedded), "Embedded and exported source differ"

    assert "REFERENCE_MIX_LEVEL = 0.02" in java
    assert "(source + REFERENCE_MIX_LEVEL * reference) * amplitude" in java
    assert "0.05 * Math.pow(\n            2000.0," in java
    assert "value,\n                        0.05,\n                        100.0" in java
    assert "1.0 / (SAMPLE_RATE * 1.8)" in java
    assert "1.0 / (SAMPLE_RATE * 0.4)" in java

    rate = lambda position: 0.05 * math.pow(2000.0, position)
    assert math.isclose(rate(0.0), 0.05, rel_tol=0.0, abs_tol=1e-12)
    assert math.isclose(rate(1.0), 100.0, rel_tol=0.0, abs_tol=1e-10)
    samples = [rate(i / 1000.0) for i in range(1001)]
    assert all(a <= b for a, b in zip(samples, samples[1:])), "Internal modulation rate is not monotonic"

    if args.sdk:
        if not args.sdk.is_file():
            raise FileNotFoundError(args.sdk)
        flags = [
            "-Werror", "--release", "17",
            "-Xlint:cast,deprecation,divzero,empty,fallthrough,finally,rawtypes,unchecked",
            "-cp", str(args.sdk),
        ]
        with tempfile.TemporaryDirectory(prefix=".validation-1958-", dir=version_folder) as temporary:
            temp = Path(temporary)
            forms = [("exported", java), ("embedded", embedded)]
            for label, source in forms:
                folder = temp / label
                classes = folder / "classes"
                folder.mkdir()
                classes.mkdir()
                source_file = folder / "nineteenfiftyeight.java"
                source_file.write_text(source, encoding="utf-8", newline="\n")
                subprocess.run([args.javac, *flags, "-d", str(classes), str(source_file)], check=True)
        print("PASS: exported and embedded Java compile against the Voltage SDK (Java 17 target, warnings as errors)")
    else:
        print("SKIP: SDK compilation (pass --sdk PATH to compile both source forms)")

    print("PASS: VMOD round-trip, source parity, Oscillators category, v2.0.1 Notes, mix routing, and 0.05–100 Hz rate mapping")


if __name__ == "__main__":
    main()
