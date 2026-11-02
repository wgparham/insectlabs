#!/usr/bin/env python3
"""Dump a Softsynth .PRM file and check its stored break points.

The file holds time, value and slope for every break point, computed by the
original at its last synthesis. This recomputes them from the graph (x, y) data
using the formulas read from F00B3C8 and reports how many agree.

Usage: prmdump.py file.prm [-v]
"""
import struct
import sys

PARTIALS, PARTIAL_SIZE = 32, 956
AMP_RECS, FREQ_RECS = 42, 17
K_CARRIER, K_MODULATOR = 0x50CB4, 0xA42


def s32(v):
    v &= 0xFFFFFFFF
    return v - (1 << 32) if v & 0x80000000 else v


def trunc_div(a, b):
    q = abs(a) // abs(b)
    return q if (a < 0) == (b < 0) else -q


def recs(buf, off, n):
    return [dict(zip(("time", "value", "slope", "y", "x"),
                     struct.unpack_from(">iiihh", buf, off + i * 16))) for i in range(n)]


def parse(m):
    if len(m) != 0x780C:
        raise SystemExit(f"unexpected size {len(m):#x}")
    ps = []
    for i in range(PARTIALS):
        o = i * PARTIAL_SIZE
        ps.append(dict(index=i + 1, scale=m[o], wave=m[o + 1],
                       fader=struct.unpack_from(">h", m, o + 2)[0],
                       ratio=struct.unpack_from(">i", m, o + 4)[0],
                       namp=struct.unpack_from(">h", m, o + 8)[0],
                       amp=recs(m, o + 0xA, AMP_RECS),
                       nfreq=struct.unpack_from(">h", m, o + 0x2AA)[0],
                       freq=recs(m, o + 0x2AC, FREQ_RECS)))
    name = m[0x7781:0x7781 + (m[0x7780] & 0x3F)].decode("latin1")
    length, rate, freq = struct.unpack_from(">iii", m, 0x77C0)
    fm = [(m[0x77CC + 2 * i], m[0x77CD + 2 * i]) for i in range(PARTIALS)]
    return ps, name, length, rate, freq, fm


def check(ps, rate, freq, fm, verbose):
    mods = {src for _, src in fm if src}
    tally = {k: [0, 0] for k in ("amp time", "amp value", "amp slope",
                                 "freq time", "freq value trunc", "freq value round", "freq slope")}

    def score(key, got, want, where):
        tally[key][0] += got == want
        tally[key][1] += 1
        if verbose and got != want:
            print(f"  {where} {key}: computed {got}, stored {want}")

    for p in ps:
        if not p["fader"]:
            continue
        # A modulator's constant includes its fader, so the fader counts twice.
        k = s32(p["fader"] * K_MODULATOR) if p["index"] in mods else K_CARRIER
        for n in range(p["namp"] + 1):
            a, b = p["amp"][n], p["amp"][n + 1]
            where = f"P{p['index']} amp[{n}]"
            t = trunc_div(s32(b["x"] * 3 * rate), 800) + 2
            v = s32(s32(b["y"] * p["fader"]) * k)
            score("amp time", t, b["time"], where)
            score("amp value", v, b["value"], where)
            if t != a["time"]:
                score("amp slope", trunc_div(v - a["value"], t - a["time"]), a["slope"], where)
        # The original's float library works on IEEE doubles; keep its order of operations.
        base = float(freq >> 3) * float(p["ratio"])
        for n in range(p["nfreq"] + 1):
            a, b = p["freq"][n], p["freq"][n + 1]
            where = f"P{p['index']} freq[{n}]"
            div = 2500 if p["scale"] == 1 else 100
            exact = (base * b["y"] / div + base) / rate
            t = trunc_div(s32(b["x"] * 3 * rate), 800) + 2
            score("freq time", t, b["time"], where)
            score("freq value trunc", int(exact), b["value"], where)
            score("freq value round", round(exact), b["value"], where)
            if t != a["time"]:
                score("freq slope", trunc_div(b["value"] - a["value"], t - a["time"]), a["slope"], where)
    return tally


def main():
    verbose = "-v" in sys.argv
    with open(sys.argv[1], "rb") as f:
        m = f.read()
    ps, name, length, rate, freq, fm = parse(m)
    print(f"name {name!r}  length {length}  rate {rate}  freq {freq / 65536:.3f} Hz")
    print(" #  fader wave scale   ratio  namp nfreq  mix mod")
    for p, (mix, mod) in zip(ps, fm):
        if p["fader"] or mod:
            print(f"{p['index']:2d}  {p['fader']:5d} {p['wave']:4d} {p['scale']:5d} "
                  f"{p['ratio'] / 65536:7.3f} {p['namp']:5d} {p['nfreq']:5d}  {mix:3d} {mod:3d}")
    ys = [r["y"] for p in ps if p["fader"] for r in p["amp"][:p["namp"] + 2]]
    print(f"amp y range {min(ys)}..{max(ys)}; faders {sorted({p['fader'] for p in ps if p['fader']})}")
    print("check against stored values:")
    for k, (good, total) in check(ps, rate, freq, fm, verbose).items():
        print(f"  {k:18s} {good}/{total}")


if __name__ == "__main__":
    main()
