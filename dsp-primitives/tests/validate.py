from pathlib import Path
import subprocess
import tempfile

PROJECT = Path(__file__).resolve().parents[1]
REPO = PROJECT.parent
FMC_SOURCE = REPO / "voltage-modular" / "Teletape Workshop" / "FMC" / "versions" / "1.0.0" / "fmc.java"


def extract_fmc_core(source):
    marker = "private static final class FmcCore {"
    start = source.index(marker)
    brace = source.index("{", start)
    depth = 0
    in_string = False
    in_char = False
    line_comment = False
    block_comment = False
    escaped = False
    for index in range(brace, len(source)):
        char = source[index]
        next_char = source[index + 1] if index + 1 < len(source) else ""
        if line_comment:
            if char == "\n":
                line_comment = False
            continue
        if block_comment:
            if char == "*" and next_char == "/":
                block_comment = False
            continue
        if in_string or in_char:
            if escaped:
                escaped = False
            elif char == "\\":
                escaped = True
            elif in_string and char == '"':
                in_string = False
            elif in_char and char == "'":
                in_char = False
            continue
        if char == "/" and next_char == "/":
            line_comment = True
            continue
        if char == "/" and next_char == "*":
            block_comment = True
            continue
        if char == '"':
            in_string = True
            continue
        if char == "'":
            in_char = True
            continue
        if char == "{":
            depth += 1
        elif char == "}":
            depth -= 1
            if depth == 0:
                core = source[start:index + 1]
                return core.replace(marker, "public static final class FmcCore {", 1)
    raise ValueError("Could not locate the end of FmcCore in archived FMC source")


test=r"""
import com.insectlabs.dsp.filters.HalfBand19;
import com.insectlabs.dsp.interpolation.CubicHermite;
public class FilterChecks {
static void check(boolean c,String s){if(!c)throw new AssertionError(s);}
public static void main(String[] args){
 check(CubicHermite.interpolate(-2, 0, 2, 4, 0) == 0, "cubic start sample");
 check(CubicHermite.interpolate(-2, 0, 2, 4, 1) == 2, "cubic end sample");
 for(int i=0;i<=100;i++){
  double t=i/100.0;
  check(Math.abs(CubicHermite.interpolate(3,3,3,3,t)-3)<1e-12,"cubic constant signal");
  check(Math.abs(CubicHermite.interpolate(-1,0,1,2,t)-t)<1e-12,"cubic linear ramp");
 }
 HalfBand19 f=new HalfBand19();double[] impulse=new double[19];double sum=0;
 for(int n=0;n<19;n++){impulse[n]=f.process(n==0?1:0);sum+=impulse[n];}
 check(Math.abs(sum-1)<1e-12,"unity DC gain");
 for(int n=0;n<19;n++){check(impulse[n]==impulse[18-n],"linear-phase symmetry");if(n%2==1&&n!=9)check(impulse[n]==0,"half-band alternating zero tap");check(Math.abs(impulse[9])>=Math.abs(impulse[n]),"central peak at delay 9");}
 for(int n=0;n<40;n++)f.process(1);check(Math.abs(f.process(1)-1)<1e-12,"steady DC output");f.reset();for(int n=0;n<40;n++)check(f.process(0)==0,"reset silence");
 HalfBand19 fresh=new HalfBand19(),silent=new HalfBand19();
 for(int n=0;n<1000;n++){double x=Math.sin(n*.37);check(Double.doubleToLongBits(f.process(x))==Double.doubleToLongBits(fresh.process(x)),"deterministic reset");check(silent.process(0)==0,"instance independence");}
 System.out.println("PASS: HalfBand19 impulse/symmetry/gain/reset/independence; CubicHermite endpoints, constant and linear interpolation.");
}
}
"""
with tempfile.TemporaryDirectory(prefix="insectlabs-primitives-", dir=PROJECT, ignore_cleanup_errors=True) as tmp:
    temp_path = Path(tmp)
    check_source = temp_path / "FilterChecks.java"
    check_source.write_text(test, encoding="utf-8")
    fmc_source = FMC_SOURCE.read_text(encoding="utf-8")
    fixture = temp_path / "FmcParityFixture.java"
    fixture.write_text(
        "package com.insectlabs.dsp.delay;\npublic final class FmcParityFixture {\n"
        + extract_fmc_core(fmc_source)
        + "\n}\n",
        encoding="utf-8",
    )
    sources = [
        *map(str, (PROJECT / "src").rglob("*.java")),
        str(check_source),
        str(fixture),
        str(PROJECT / "tests" / "EmsSvfChecks.java"),
        str(PROJECT / "tests" / "ChorusCoreTest.java"),
        str(PROJECT / "tests" / "BbdAnalogDelayParityTest.java"),
        str(PROJECT / "tests" / "MidpointOversampler2xTest.java"),
    ]
    subprocess.run(
        ["javac", "--release", "17", "-encoding", "utf-8", "-Xlint:all", "-Werror", "-d", tmp, *sources],
        check=True,
    )
    for test_class in (
        "FilterChecks",
        "MidpointOversampler2xTest",
        "EmsSvfChecks",
        "com.insectlabs.dsp.chorus.ChorusCoreTest",
        "com.insectlabs.dsp.delay.BbdAnalogDelayParityTest",
    ):
        subprocess.run(["java", "-cp", tmp, test_class], check=True)
