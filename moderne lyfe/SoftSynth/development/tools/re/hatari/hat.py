"""Drives the patched Hatari through its command file. Steps: start, click X Y, move X Y, dclick X Y,
key K, wait S, shot NAME, cmd TEXT, ff on|off, quit."""
import os, shlex, struct, subprocess, sys, time, zlib
ROOT = r"C:\Users\wgparham\MacEmu"
RUN = os.path.join(ROOT, "run-softsynth3")
CMD = os.path.join(RUN, "cmd.txt")
HERE = os.path.dirname(os.path.abspath(__file__))
def send(text):
    tmp = CMD + ".tmp"
    with open(tmp, "w", newline="\n") as f: f.write(text + "\n")
    for _ in range(100):
        if not os.path.exists(CMD): break
        time.sleep(0.05)
    os.replace(tmp, CMD)
    for _ in range(200):
        if not os.path.exists(CMD): return
        time.sleep(0.05)
    raise SystemExit("Hatari did not take the command: " + text)
def ev(t): send("hatari-event " + t)
POS = os.path.join(HERE, "pos3.txt")
def move(x, y):
    if not os.path.exists(POS):
        ev("mousemove 3000 3000"); time.sleep(0.6); cx, cy = 639, 399
    else:
        cx, cy = map(int, open(POS).read().split())
    ev(f"mousemove {x - cx} {y - cy}"); time.sleep(0.5)
    open(POS, "w").write(f"{x} {y}")
def click(x, y, n=1):
    move(x, y)
    for _ in range(n):
        ev("leftdown"); time.sleep(0.15); ev("leftup"); time.sleep(0.15)
    time.sleep(0.4)
def bmp_to_png(src, dst):
    d = open(src, "rb").read()
    off = struct.unpack_from("<I", d, 10)[0]; w, h, _, bpp = struct.unpack_from("<iiHH", d, 18)
    stride = ((w * bpp + 31) // 32) * 4; rows = []
    for y in range(abs(h)):
        o = off + (abs(h) - 1 - y if h > 0 else y) * stride
        r = d[o:o + w * bpp // 8]
        step = bpp // 8
        rows.append(b"\0" + b"".join(bytes((r[i + 2], r[i + 1], r[i])) for i in range(0, len(r), step)))
    def ch(t, b): return struct.pack(">I", len(b)) + t + b + struct.pack(">I", zlib.crc32(t + b))
    open(dst, "wb").write(b"\x89PNG\r\n\x1a\n" + ch(b"IHDR", struct.pack(">IIBBBBB", w, abs(h), 8, 2, 0, 0, 0)) + ch(b"IDAT", zlib.compress(b"".join(rows))) + ch(b"IEND", b""))
def shot(name):
    b = os.path.join(HERE, name + ".bmp")
    if os.path.exists(b): os.remove(b)
    send("hatari-debug screenshot " + b)
    for _ in range(100):
        if os.path.exists(b) and os.path.getsize(b) > 1000: break
        time.sleep(0.1)
    time.sleep(0.2); bmp_to_png(b, os.path.join(HERE, name + ".png")); os.remove(b)
def start():
    os.makedirs(os.path.join(RUN, "C"), exist_ok=True)
    if os.path.exists(CMD): os.remove(CMD)
    src = r"C:\Users\wgparham\Dropbox\git\insectlabs\moderne lyfe\SoftSynth"
    for f in ("SOFTSYN.INF", "SOFTSYN.RSC", "SOFTSYNT.PRG"):
        open(os.path.join(RUN, "C", f), "wb").write(open(os.path.join(src, f), "rb").read())
    env = dict(os.environ, HATARI_CMD_FILE=CMD, SDL_VIDEODRIVER=os.environ.get("HVID", "dummy"), SDL_AUDIODRIVER="dummy")
    log = open(os.path.join(RUN, "hatari.out"), "w")
    p = subprocess.Popen([ROOT + r"\hatari-ss-build\src\hatari.exe", "--tos", ROOT + r"\tos\emutos-1.4\emutos-192k-1.4\etos192us.img",
        "--machine", "st", "--memsize", "1", "--mono", "--fast-boot", "1", "--sound", "off", "--confirm-quit", "0",
        "--harddrive", os.path.join(RUN, "C"), "--auto", r"C:\SOFTSYNT.PRG"], cwd=RUN, env=env, stdout=log, stderr=log)
    open(os.path.join(HERE, "pid3.txt"), "w").write(str(p.pid)); print("pid", p.pid)
def main(a):
    i = 0
    while i < len(a):
        k = a[i]; i += 1
        if k == "start": start()
        elif k in ("click", "move", "dclick"):
            x, y = int(a[i]), int(a[i + 1]); i += 2
            {"click": click, "move": move, "dclick": lambda x, y: click(x, y, 2)}[k](x, y)
        elif k == "key": ev("keypress " + a[i]); i += 1; time.sleep(0.3)
        elif k == "wait": time.sleep(float(a[i])); i += 1
        elif k == "shot": shot(a[i]); i += 1
        elif k == "cmd": send(a[i]); i += 1
        elif k == "ff": send("hatari-option --fast-forward " + a[i]); i += 1
        elif k == "quit": send("hatari-option --confirm-quit 0"); subprocess.run(["taskkill", "/PID", open(os.path.join(HERE, "pid3.txt")).read(), "/F"], capture_output=True)
        else: raise SystemExit("unknown step " + k)
main(sys.argv[1:])
