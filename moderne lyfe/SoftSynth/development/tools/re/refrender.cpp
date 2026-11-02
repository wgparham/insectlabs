// refrender: runs Softsynth's own 68K code on the Musashi core to make reference
// renders. It executes the program's startup (0x366..0x3DA, including its init at
// 0x7FBE), its table setup, and its whole render function F00C28E with the
// original's float library. Only the OS is replaced: GEMDOS file calls, XBIOS
// Random, the status window, and the file dialogs at the start of F00C28E.
//
// refrender SOFTSYNT.PRG file.prm out.sd [--seed N | --zero-random] [--no-normalize]
//           [--buffer BYTES] [--wav out.wav]
#include <cstdint>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <string>
#include <vector>
extern "C" {
#include "m68k.h"
}

static const uint32_t kMem = 0x01000000, kBasepage = 0x0000FF00, kText = kBasepage + 0x100;
static const uint32_t kPartials = 0x00300000, kNoise = 0x00380000, kBuffer = 0x00400000;
static const uint32_t kStop = 0x00FFFFF0;
static std::vector<uint8_t> mem(kMem, 0);

extern "C" {
unsigned int m68k_read_memory_8(unsigned int a) { return mem[a & 0xFFFFFF]; }
unsigned int m68k_read_memory_16(unsigned int a) { return m68k_read_memory_8(a) << 8 | m68k_read_memory_8(a + 1); }
unsigned int m68k_read_memory_32(unsigned int a) { return m68k_read_memory_16(a) << 16 | m68k_read_memory_16(a + 2); }
void m68k_write_memory_8(unsigned int a, unsigned int v) { mem[a & 0xFFFFFF] = (uint8_t) v; }
void m68k_write_memory_16(unsigned int a, unsigned int v) { m68k_write_memory_8(a, v >> 8); m68k_write_memory_8(a + 1, v); }
void m68k_write_memory_32(unsigned int a, unsigned int v) { m68k_write_memory_16(a, v >> 16); m68k_write_memory_16(a + 2, v); }
unsigned int m68k_read_disassembler_8(unsigned int a) { return m68k_read_memory_8(a); }
unsigned int m68k_read_disassembler_16(unsigned int a) { return m68k_read_memory_16(a); }
unsigned int m68k_read_disassembler_32(unsigned int a) { return m68k_read_memory_32(a); }
}

static uint32_t rd32(uint32_t a) { return m68k_read_memory_32(a); }
static uint16_t rd16(uint32_t a) { return (uint16_t) m68k_read_memory_16(a); }
static void wr32(uint32_t a, uint32_t v) { m68k_write_memory_32(a, v); }
static void wr16(uint32_t a, uint16_t v) { m68k_write_memory_16(a, v); }

static std::vector<uint8_t> readFile(const std::string& p)
{
    std::vector<uint8_t> d;
    FILE* f = std::fopen(p.c_str(), "rb");
    if (!f) { std::fprintf(stderr, "can't open %s\n", p.c_str()); std::exit(1); }
    int c;
    while ((c = std::fgetc(f)) != EOF) d.push_back((uint8_t) c);
    std::fclose(f);
    return d;
}

// ---- XBIOS Random: the formula TOS uses, from a seed we choose -------------------
static bool zeroRandom = false;
static uint32_t seed = 1;
static uint32_t randomCalls = 0;
static uint32_t xbiosRandom()
{
    ++randomCalls;
    if (zeroRandom) return 0;
    seed = seed * 3141592621u + 1;
    return (seed >> 8) & 0xFFFFFF;
}

// ---- GEMDOS: one in-memory output file --------------------------------------------
static const uint16_t kHandle = 7;
static std::vector<uint8_t> file;
static size_t filePos = 0;
static bool fatal = false;

static uint32_t sp() { return m68k_get_reg(nullptr, M68K_REG_SP); }

static uint32_t gemdos()
{
    const uint32_t s = sp() + 4; // past the return address
    const uint16_t fn = rd16(s);
    if (fn == 0x3E) return 0;                          // Fclose
    if (fn == 0x3F || fn == 0x40) {                    // Fread, Fwrite
        const uint32_t count = rd32(s + 4), ptr = rd32(s + 8);
        if (fn == 0x40) {
            if (filePos + count > file.size()) file.resize(filePos + count);
            for (uint32_t i = 0; i < count; ++i) file[filePos + i] = mem[(ptr + i) & 0xFFFFFF];
            filePos += count;
            return count;
        }
        const uint32_t n = (uint32_t) std::min<size_t>(count, file.size() - filePos);
        for (uint32_t i = 0; i < n; ++i) mem[(ptr + i) & 0xFFFFFF] = file[filePos + i];
        filePos += n;
        return n;
    }
    if (fn == 0x42) {                                  // Fseek
        const int32_t off = (int32_t) rd32(s + 2);
        const uint16_t mode = rd16(s + 8);
        filePos = (size_t) ((mode == 0 ? 0 : mode == 1 ? (int64_t) filePos : (int64_t) file.size()) + off);
        return (uint32_t) filePos;
    }
    std::fprintf(stderr, "unhandled GEMDOS call %#x\n", fn);
    fatal = true;
    return 0xFFFFFFFF;
}

// ---- Execution hook ----------------------------------------------------------------
static bool stopped = false;
static uint32_t startupEnd = 0;

static void returnFromStub(uint32_t d0, bool setD0)
{
    const uint32_t s = sp();
    if (setD0) m68k_set_reg(M68K_REG_D0, d0);
    m68k_set_reg(M68K_REG_SP, s + 4);
    m68k_set_reg(M68K_REG_PC, rd32(s));
}

extern "C" void host_instr_hook(unsigned int pc)
{
    pc &= 0xFFFFFF;
    if (pc == kStop || pc == startupEnd) { stopped = true; m68k_end_timeslice(); return; }
    if (pc < kText) { std::fprintf(stderr, "stray jump to %#x\n", pc); fatal = true; stopped = true; m68k_end_timeslice(); return; }
    const uint32_t t = pc - kText;
    switch (t) {
    case 0x394:   // Mshrink in the startup code
        m68k_set_reg(M68K_REG_D0, 0);
        m68k_set_reg(M68K_REG_PC, pc + 2);
        break;
    case 0x6634:  // XBIOS wrapper
        if (rd16(sp() + 4) != 0x11) { std::fprintf(stderr, "unhandled XBIOS call %#x\n", rd16(sp() + 4)); fatal = true; }
        returnFromStub(xbiosRandom(), true);
        break;
    case 0x6650:  // GEMDOS wrapper
        returnFromStub(gemdos(), true);
        break;
    case 0xBC18:  // "Illegal frequency" alert
        std::fprintf(stderr, "the original rejected a frequency above half the sample rate\n");
        fatal = true;
        returnFromStub(0, false);
        break;
    case 0xBFFE: case 0xC032: case 0xC064: // status window
        returnFromStub(0, false);
        break;
    case 0xC31E:  // past the frequency check: skip the file dialogs, "open" our file
        wr16(m68k_get_reg(nullptr, M68K_REG_A6) - 4, kHandle);
        m68k_set_reg(M68K_REG_PC, kText + 0xC430);
        break;
    default:
        break;
    }
}

static uint64_t runUntilStop()
{
    uint64_t cycles = 0;
    stopped = false;
    while (!stopped && !fatal && cycles < 400000000000ull) cycles += (uint64_t) m68k_execute(1000000);
    if (!stopped) { std::fprintf(stderr, "did not finish\n"); std::exit(1); }
    return cycles;
}

static uint64_t call(uint32_t textOffset)
{
    const uint32_t s = sp() - 4;
    wr32(s, kStop);
    m68k_set_reg(M68K_REG_SP, s);
    m68k_set_reg(M68K_REG_PC, kText + textOffset);
    return runUntilStop();
}

static void writeWav(const std::string& path, const std::vector<int16_t>& s, uint32_t rate)
{
    FILE* f = std::fopen(path.c_str(), "wb");
    if (!f) return;
    const uint32_t bytes = (uint32_t) s.size() * 2, riff = 36 + bytes, brate = rate * 2, fmt = 16;
    const uint16_t pcm = 1, ch = 1, align = 2, bits = 16;
    std::fwrite("RIFF", 1, 4, f); std::fwrite(&riff, 4, 1, f); std::fwrite("WAVEfmt ", 1, 8, f);
    std::fwrite(&fmt, 4, 1, f); std::fwrite(&pcm, 2, 1, f); std::fwrite(&ch, 2, 1, f);
    std::fwrite(&rate, 4, 1, f); std::fwrite(&brate, 4, 1, f); std::fwrite(&align, 2, 1, f); std::fwrite(&bits, 2, 1, f);
    std::fwrite("data", 1, 4, f); std::fwrite(&bytes, 4, 1, f); std::fwrite(s.data(), 2, s.size(), f);
    std::fclose(f);
}

int main(int argc, char** argv)
{
    if (argc < 4) { std::fprintf(stderr, "usage: refrender SOFTSYNT.PRG file.prm out.sd [--seed N | --zero-random] [--no-normalize] [--buffer BYTES] [--wav out.wav]\n"); return 2; }
    bool normalize = true;
    uint32_t bufferBytes = 0x200000;
    std::string wavPath;
    for (int i = 4; i < argc; ++i) {
        const std::string a = argv[i];
        if (a == "--seed" && i + 1 < argc) seed = (uint32_t) std::strtoul(argv[++i], nullptr, 0);
        else if (a == "--zero-random") zeroRandom = true;
        else if (a == "--no-normalize") normalize = false;
        else if (a == "--buffer" && i + 1 < argc) bufferBytes = (uint32_t) std::strtoul(argv[++i], nullptr, 0);
        else if (a == "--wav" && i + 1 < argc) wavPath = argv[++i];
        else { std::fprintf(stderr, "bad option %s\n", a.c_str()); return 2; }
    }

    // Load the program the way GEMDOS does: basepage, text, data, then relocate.
    const auto prg = readFile(argv[1]);
    auto be32 = [&](size_t o) { return (uint32_t) prg[o] << 24 | prg[o + 1] << 16 | prg[o + 2] << 8 | prg[o + 3]; };
    const uint32_t tlen = be32(2), dlen = be32(6), blen = be32(10), slen = be32(14);
    std::memcpy(&mem[kText], &prg[28], tlen + dlen);
    size_t r = 28 + tlen + dlen + slen;
    uint32_t off = be32(r);
    r += 4;
    int relocs = 0;
    if (off) {
        wr32(kText + off, rd32(kText + off) + kText);
        ++relocs;
        while (r < prg.size() && prg[r]) {
            if (prg[r] == 1) off += 254;
            else { off += prg[r]; wr32(kText + off, rd32(kText + off) + kText); ++relocs; }
            ++r;
        }
    }
    wr32(kBasepage + 0x08, kText);
    wr32(kBasepage + 0x0C, tlen);
    wr32(kBasepage + 0x10, kText + tlen);
    wr32(kBasepage + 0x14, dlen);
    wr32(kBasepage + 0x18, kText + tlen + dlen);
    wr32(kBasepage + 0x1C, blen);

    // Run the real startup up to its return from the init routine.
    wr32(0, 0x8000);
    wr32(4, kText);
    wr32(0x8004, kBasepage);
    m68k_init();
    m68k_set_cpu_type(M68K_CPU_TYPE_68000);
    m68k_pulse_reset();
    startupEnd = kText + 0x3DA;
    runUntilStop();
    startupEnd = 0;
    const uint32_t a4 = m68k_get_reg(nullptr, M68K_REG_A4);
    if (fatal) return 1;
    call(0x1CB7A); // waveform table pointers (jump table 0x34E)
    call(0x1CBA0); // noise table pointer (jump table 0x354)

    // What main does before rendering: the buffers and the noise table.
    wr32(a4 - 0x77C, kPartials);
    wr32(a4 - 0x16F4, kNoise);
    wr32(a4 - 0xE34, bufferBytes);
    wr32(a4 - 0xE2C, kBuffer);
    for (uint32_t i = 0; i < 0x2000; ++i) wr16(kNoise + i * 2, (uint16_t) xbiosRandom());
    wr16(a4 - 0x76A, 0x3C);
    wr16(a4 - 0x768, normalize ? 1 : 0);

    // What the .PRM loader F003116 does.
    const auto prm = readFile(argv[2]);
    if (prm.size() != 0x780C) { std::fprintf(stderr, "not a .PRM file\n"); return 1; }
    std::memcpy(&mem[kPartials], prm.data(), 0x7780);
    const auto prmBe32 = [&](size_t o) { return (uint32_t) prm[o] << 24 | prm[o + 1] << 16 | prm[o + 2] << 8 | prm[o + 3]; };
    const uint32_t length = prmBe32(0x77C0), rate = prmBe32(0x77C4), frequency = prmBe32(0x77C8);
    wr32(a4 - 0x778, length);
    wr32(a4 - 0x774, rate);
    wr32(a4 - 0x770, frequency);
    std::memcpy(&mem[a4 - 0x7BC], &prm[0x77CC], 0x40);
    const size_t nameLen = prm[0x7780] & 0x3F;
    std::memcpy(&mem[a4 - 0x6C4], &prm[0x7781], nameLen);
    mem[a4 - 0x6C4 + nameLen] = 0;

    const uint32_t firstRandom = randomCalls;
    const uint64_t cycles = call(0xC28E);
    if (fatal) return 1;

    // The original's code has now recomputed every break point. For partials in
    // use, the result should equal what it stored in the file.
    int active = 0, diffs = 0;
    for (int p = 0; p < 32; ++p) {
        const uint32_t base = (uint32_t) p * 956;
        if (!(prm[base + 2] | prm[base + 3])) continue;
        ++active;
        const int namp = (int16_t) (prm[base + 8] << 8 | prm[base + 9]);
        const int nfreq = (int16_t) (prm[base + 0x2AA] << 8 | prm[base + 0x2AB]);
        auto checkRecs = [&](uint32_t start, int count, const char* what) {
            for (int n = 0; n < count; ++n)
                for (uint32_t b = 0; b < 12; b += 4) {
                    const uint32_t o = base + start + (uint32_t) n * 16 + b;
                    const uint32_t stored = prmBe32(o), now = rd32(kPartials + o);
                    if (stored != now) {
                        ++diffs;
                        std::printf("  break point differs: P%d %s[%d] %s: original's code %d, file %d\n", p + 1, what, n,
                                    b == 0 ? "time" : b == 4 ? "value" : "slope", (int32_t) now, (int32_t) stored);
                    }
                }
        };
        checkRecs(0xA, namp + 2, "amp");
        checkRecs(0x2AC, nfreq + 2, "freq");
    }

    const size_t header = 0x538;
    if (file.size() != header + (size_t) length * 2) { std::fprintf(stderr, "unexpected output size %zu\n", file.size()); return 1; }
    FILE* f = std::fopen(argv[3], "wb");
    if (!f) { std::fprintf(stderr, "can't write %s\n", argv[3]); return 1; }
    std::fwrite(file.data(), 1, file.size(), f);
    std::fclose(f);

    std::vector<int16_t> samples(length);
    int peak = 0;
    for (uint32_t i = 0; i < length; ++i) {
        samples[i] = (int16_t) (file[header + 2 * i] << 8 | file[header + 2 * i + 1]);
        peak = std::max(peak, std::abs((int) samples[i]));
    }
    if (!wavPath.empty()) writeWav(wavPath, samples, rate);

    std::printf("relocations %d; A4 at text+%#x; startup and render ran\n", relocs, a4 - kText);
    std::printf("%u samples at %u Hz, peak %d; %llu cycles (%.1f s on an 8 MHz ST)\n", length, rate, peak,
                (unsigned long long) cycles, (double) cycles / 8e6);
    std::printf("Random calls: %u at setup, %u in the render\n", firstRandom, randomCalls - firstRandom);
    std::printf("active partials %d; stored break points that differ: %d\n", active, diffs);
    return 0;
}
