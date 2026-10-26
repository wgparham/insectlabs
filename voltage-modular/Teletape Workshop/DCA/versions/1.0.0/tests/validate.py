"""Validate DCA release identity, Designer source parity, SDK compilation and DSP behavior."""
from pathlib import Path
import hashlib
import json
import os
import re
import subprocess
import sys
import tempfile

MODULE = Path(__file__).resolve().parents[1]
REPO = next(parent for parent in MODULE.parents if (parent / "voltage-modular").is_dir())
sys.path.insert(0, str(REPO / "voltage-modular" / "colorbox" / "tools"))
from value_tree import Reader, encode, props  # noqa: E402
from support import normalized  # noqa: E402

SOURCE = MODULE / "dca.java"
VMOD = MODULE / "dca.vmod"
HERO = MODULE / "dca_hero.png"

def extract_nested_class(source, marker):
    start = source.index(marker)
    brace = source.index("{", start)
    depth = 0
    in_string = in_char = line_comment = block_comment = escaped = False
    for i in range(brace, len(source)):
        ch = source[i]
        nxt = source[i + 1] if i + 1 < len(source) else ""
        if line_comment:
            if ch == "\n": line_comment = False
            continue
        if block_comment:
            if ch == "*" and nxt == "/": block_comment = False
            continue
        if in_string or in_char:
            if escaped: escaped = False
            elif ch == "\\": escaped = True
            elif in_string and ch == '"': in_string = False
            elif in_char and ch == "'": in_char = False
            continue
        if ch == "/" and nxt == "/": line_comment = True; continue
        if ch == "/" and nxt == "*": block_comment = True; continue
        if ch == '"': in_string = True; continue
        if ch == "'": in_char = True; continue
        if ch == "{": depth += 1
        elif ch == "}":
            depth -= 1
            if depth == 0:
                return source[start:i + 1]
    raise ValueError("Could not extract DcaCore")

source = SOURCE.read_text(encoding="utf-8-sig")
reader = Reader(VMOD.read_bytes())
tree = reader.tree()
assert reader.p == len(reader.b) and encode(tree) == reader.b, "Designer file must round-trip"
root_props = props(tree)
assert root_props["class name"] == "dca"
assert root_props["package name"] == "com.insectlabs.dca"
assert [line.strip() for line in root_props["notes"].splitlines() if line.strip()] == ["dca", "v1.0.0", "canon"]
module_code = next(child for child in tree[2] if child[0] == "module code")
embedded = props(module_code)["MODULE.SOURCE"]
assert normalized(source) == normalized(embedded), "exported and embedded Java source differ"
controls = next(child for child in tree[2] if child[0] == "controls")[2]
uuids = [props(control).get("UUID") for control in controls]
assert all(uuids) and len(uuids) == len(set(uuids)), "control UUIDs must be present and unique"
assert HERO.is_file() and HERO.read_bytes().startswith(b"\x89PNG\r\n\x1a\n"), "missing or invalid hero PNG"
manifest_path = MODULE / "SHA256.json"
if manifest_path.exists():
    for name, digest in json.loads(manifest_path.read_text(encoding="utf-8")).items():
        assert hashlib.sha256((MODULE / name).read_bytes()).hexdigest() == digest, f"SHA-256 mismatch: {name}"

jdk = Path(r"C:\Program Files\Java\jdk-27\bin")
javac = jdk / "javac.exe"
java = jdk / "java.exe"
sdk = Path(r"C:\ProgramData\Voltage\voltage.jar")
assert javac.is_file() and java.is_file() and sdk.is_file(), "JDK or Voltage SDK not found"
with tempfile.TemporaryDirectory(prefix="dca-release-check-", dir=MODULE, ignore_cleanup_errors=True) as temp:
    temp = Path(temp)
    for label, java_source in (("export", source), ("designer", embedded)):
        folder = temp / label
        folder.mkdir()
        actual_class = re.search(r"public\s+class\s+(\w+)", java_source).group(1)
        file = folder / f"{actual_class}.java"
        file.write_text(java_source, encoding="utf-8")
        subprocess.run([str(javac), "--release", "17", "-Xlint:all", "-Werror", "-encoding", "UTF-8",
                        "-cp", str(sdk), "-d", str(folder), str(file)], check=True)

    core = extract_nested_class(source, "private static final class DcaCore {")
    core = core.replace("private static final class DcaCore {", "public static final class DcaCore {", 1)
    fixture = temp / "DcaCoreFixture.java"
    fixture.write_text("public final class DcaCoreFixture {\n" + core + "\n}\n", encoding="utf-8")
    checks = MODULE / "tests" / "DcaChecks.java"
    subprocess.run([str(javac), "--release", "17", "-Xlint:all", "-Werror", "-encoding", "UTF-8",
                    "-d", str(temp), str(fixture), str(checks)], check=True)
    subprocess.run([str(java), "-cp", str(temp), "DcaChecks"], check=True)

print("PASS: DCA source parity, Designer binary round-trip, canonical identity, UUIDs, hero and hashes.")
print("PASS: exported and Designer-embedded module compile against Voltage SDK (Java 17, all lint warnings fatal).")
