from __future__ import annotations

import os
import shutil
import subprocess
import tempfile
from pathlib import Path
import sys

ROOT = next(parent for parent in Path(__file__).resolve().parents
            if (parent / "voltage-modular" / "colorbox" / "tools").is_dir())
RELEASE = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / "voltage-modular" / "colorbox" / "tools"))
from support import normalized, regions
from value_tree import Reader, encode, props, value


def java_tool(name: str) -> str | None:
    java_home = os.environ.get("JAVA_HOME")
    if java_home:
        candidate = Path(java_home) / "bin" / (name + ".exe")
        if candidate.is_file():
            return str(candidate)
    discovered = shutil.which(name)
    if discovered and "javapath" in discovered.lower():
        root = Path(os.environ.get("ProgramFiles", r"C:\Program Files")) / "Java"
        candidates = list(root.glob(f"jdk*/bin/{name}.exe"))
        if candidates:
            return str(sorted(candidates)[-1])
    return discovered


def check(condition: bool, message: str) -> None:
    if not condition:
        raise AssertionError(message)


def compile_source(source: str, label: str, sdk: Path) -> None:
    javac = java_tool("javac")
    check(javac is not None, "Java 17 javac is required")
    with tempfile.TemporaryDirectory(prefix="b16-validate-", dir=RELEASE) as tmp:
        source_path = Path(tmp) / "b16.java"
        source_path.write_text(source, encoding="utf-8")
        subprocess.run([javac, "--release", "17", "-encoding", "utf-8", "-Werror",
                        "-cp", str(sdk), "-d", tmp, str(source_path)],
                       check=True, timeout=120)
    print(f"PASS: {label} Java 17 SDK compile")


def main() -> None:
    sdk = Path(os.environ.get("VOLTAGE_SDK", r"C:\ProgramData\Voltage\voltage.jar"))
    check(sdk.is_file(), f"Voltage SDK not found: {sdk}")
    java_path = RELEASE / "b16.java"
    vmod_path = RELEASE / "b16.vmod"
    source = java_path.read_text(encoding="utf-8-sig")
    raw = vmod_path.read_bytes()
    reader = Reader(raw)
    tree = reader.tree()
    check(reader.p == len(raw) and encode(tree) == raw,
          "Designer project must parse and round-trip byte-for-byte")
    metadata = {key: value(val) for key, val in tree[1]}
    code = props(next(node for node in tree[2] if node[0] == "module code"))
    embedded = code["MODULE.SOURCE"]
    check(normalized(embedded) == normalized(source), "embedded and exported Java must agree")
    check(metadata["CEditableModule.name"] == "blank16", "Designer module name")
    check(metadata["class name"] == "b16", "Java class identity")
    check(metadata["package name"] == "com.insectlabs.b16", "Java package identity")
    check(metadata["width Inches"] == "3.2", "16 HP physical width")
    check(metadata["notes"].startswith("2.0.0"), "published Designer version marker")
    controls = next(node for node in tree[2] if node[0] == "controls")[2]
    check(len(controls) == 5, "expected exactly three images and two labels")
    source_regions = regions(source)
    check(not source_regions["ProcessSample"].strip(), "custom ProcessSample region must be empty")
    check(not any(token in source for token in ("VoltageAudioJack(", "VoltageKnob(",
                                                 "VoltageSwitch(", "VoltageButton(")),
          "blank panel must not add functional controls or jacks")
    compile_source(source, "exported source", sdk)
    compile_source(embedded, "embedded source", sdk)
    print("PASS: Designer metadata, component inventory, source pair and artwork-only DSP scope")


if __name__ == "__main__":
    main()
