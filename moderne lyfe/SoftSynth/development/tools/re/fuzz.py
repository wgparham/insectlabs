#!/usr/bin/env python3
"""Differential test: random patches through the original's 68K code and the engine.

Compares the complete output files. Also feeds the engine damaged files, which it
must survive; the original has no such protection and writes through its memory.

Usage: fuzz.py refrender.exe softsynth-cli.exe SOFTSYNT.PRG workdir [count] [first_seed]
"""
import os
import random
import struct
import subprocess
import sys

PARTIAL = 956


def make_patch(r):
    m = bytearray(0x780C)
    length = r.choice((64, 200, 1000, 3000, r.randint(64, 6000)))
    rate = r.choice((8000, 11025, 22050, 32000, 44100, 48000, r.randint(100, 100000)))
    freq = r.randint(20, 2000) << 16 | r.choice((0, 0x8000, r.randint(0, 0xFFFF)))
    if rate < 2000:
        rate = 8000 if r.random() < 0.9 else rate  # very low rates give equal break point times
    active = r.sample(range(32), r.randint(0, 32) if r.random() < 0.3 else r.randint(1, 8))
    illegal = r.random() < 0.05  # sometimes try a partial above half the rate
    for p in range(32):
        o = p * PARTIAL
        # Records that aren't in use keep whatever the file holds, and the kernels can read them.
        if r.random() < 0.5:
            for a in range(o + 0xA, o + PARTIAL - 32, 16):
                if r.random() < 0.2:
                    struct.pack_into(">iiihh", m, a, r.randint(0, length + 50), r.randint(-1 << 30, 1 << 30), r.randint(-1 << 14, 1 << 14), r.randint(-99, 99), 10000)
        if p not in active:
            if r.random() < 0.3:
                m[o + 1] = r.randint(0, 6)
            continue
        m[o] = r.choice((1, 2, 2, 0))
        m[o + 1] = r.choice((1, 1, 2, 3, 4, 5, 0, 6))
        struct.pack_into(">h", m, o + 2, r.choice((1, 4, 13, 54, 63, 100, r.randint(1, 120))))
        limit = (rate / 2) / (freq / 65536)
        ratio = r.uniform(limit * 0.01, min(16, limit * 0.9)) if not illegal else limit * r.uniform(0.5, 4)
        struct.pack_into(">i", m, o + 4, max(1, int(ratio * 65536)))
        for count_at, recs_at, cap, ys in ((8, 0xA, 40, (0, 110)), (0x2AA, 0x2AC, 15, (-60, 60))):
            n = r.randint(0, min(cap, 12))
            struct.pack_into(">h", m, o + count_at, n)
            x = 0
            for k in range(n + 2):
                a = o + recs_at + k * 16
                if k:
                    x += r.randint(1, 40)
                y = 0 if k == 0 else r.randint(*ys)
                if r.random() < 0.05:
                    y = r.randint(-1000, 1000)
                struct.pack_into(">hh", m, a + 12, y, x)
            if r.random() < 0.3:
                struct.pack_into(">h", m, o + recs_at + (n + 2) * 16 + 14, 10000)
    for p in range(32):
        m[0x77CC + 2 * p] = 1 if r.random() < 0.7 else 0
        if r.random() < 0.3:
            m[0x77CD + 2 * p] = r.choice(active) + 1 if active else 0
    if r.random() < 0.05:
        m[0x77CD] = 1  # a partial modulating itself
    name = b"fuzz%d" % r.randint(0, 999)
    m[0x7780] = len(name)
    m[0x7781:0x7781 + len(name)] = name
    struct.pack_into(">iii", m, 0x77C0, length, rate, freq)
    return bytes(m)


def main():
    exe, cli, prg, work = sys.argv[1:5]
    count = int(sys.argv[5]) if len(sys.argv) > 5 else 200
    first = int(sys.argv[6]) if len(sys.argv) > 6 else 1
    os.makedirs(work, exist_ok=True)
    a, b, pm, pa = (os.path.join(work, n) for n in ("a.sd", "b.sd", "p.prm", "after.prm"))
    tally = {"identical": 0, "both rejected": 0}
    waves = {}
    fails = 0
    for s in range(first, first + count):
        r = random.Random(s)
        data = make_patch(r)
        with open(pm, "wb") as f:
            f.write(data)
        opts = ["--seed", str(r.randint(0, 0xFFFFFF))] if r.random() < 0.8 else ["--zero-random"]
        if r.random() < 0.5:
            opts.append("--no-normalize")
        if r.random() < 0.3:
            opts += ["--buffer", str(2 * r.choice((64, 128, 500, 1024, 2000)))]
        for f in (a, b):
            if os.path.exists(f):
                os.remove(f)
        ra = subprocess.run([exe, prg, pm, a, *opts], capture_output=True, text=True)
        rb = subprocess.run([cli, pm, b, *opts], capture_output=True, text=True)
        if "stray jump" in ra.stderr and "divide by zero" in rb.stderr:
            tally["both divide by zero"] = tally.get("both divide by zero", 0) + 1
            continue
        rejected_a = "rejected a frequency" in ra.stderr
        rejected_b = rb.returncode == 3
        if rejected_a or rejected_b:
            if rejected_a and rejected_b:
                tally["both rejected"] += 1
                continue
            print(f"seed {s}: only one side rejected it: 68K {ra.stderr.strip()[:80]!r}, engine {rb.stderr.strip()[:80]!r}")
            fails += 1
            continue
        if ra.returncode or rb.returncode:
            print(f"seed {s}: 68K exit {ra.returncode} {ra.stderr.strip()[:80]!r}; engine exit {rb.returncode} {rb.stderr.strip()[:80]!r}")
            fails += 1
            continue
        with open(a, "rb") as f:
            fa = f.read()
        with open(b, "rb") as f:
            fb = f.read()
        if fa != fb:
            n = next((i for i, (x, y) in enumerate(zip(fa, fb)) if x != y), -1)
            print(f"seed {s}: files differ, sizes {len(fa)} {len(fb)}, first at {n:#x} (sample {(n - 0x538) // 2}), options {opts}")
            fails += 1
            if fails >= 10:
                break
            continue
        tally["identical"] += 1
        for p in range(32):
            if struct.unpack_from(">h", data, p * PARTIAL + 2)[0]:
                waves[data[p * PARTIAL + 1]] = waves.get(data[p * PARTIAL + 1], 0) + 1
    # Damaged files: the engine has to finish, whatever they hold.
    survived = 0
    for s in range(first, first + count):
        r = random.Random(s + 1000000)
        data = bytearray(make_patch(r))
        for _ in range(r.randint(1, 200)):
            data[r.randrange(0x77C0)] = r.randrange(256)
        if r.random() < 0.3:
            data[:0x7780] = bytes(r.randrange(256) for _ in range(0x7780))
        with open(pm, "wb") as f:
            f.write(data)
        rb = subprocess.run([cli, pm, b, "--seed", "1"], capture_output=True, text=True)
        if rb.returncode in (0, 3):
            survived += 1
        else:
            print(f"damaged file, seed {s}: engine exit {rb.returncode:#x}")
            fails += 1
    print(f"damaged files survived: {survived} of {count}")
    print(tally, "failures", fails)
    print("active partials by waveform value:", dict(sorted(waves.items())))
    sys.exit(1 if fails else 0)


if __name__ == "__main__":
    main()
