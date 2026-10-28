// dsp56dis: minimal Motorola DSP56000/56001 disassembler for the small
// SACD programs in TurboSynth (raw big-endian P-memory images from P:0).
// The ALU and register tables follow Hatari's dsp_disasm.c. Anything not
// recognised is printed as "dc".
// Usage: dsp56dis SACD_110.bin [first_address]
#include <cstdint>
#include <cstdio>
#include <fstream>
#include <string>
#include <vector>

static const char* alu[256] = {
"move","tfr b,a","addr b,a","tst a","?","cmp b,a","subr b,a","cmpm b,a",
"?","tfr a,b","addr a,b","tst b","?","cmp a,b","subr a,b","cmpm a,b",
"add b,a","rnd a","addl b,a","clr a","sub b,a","?","subl b,a","not a",
"add a,b","rnd b","addl a,b","clr b","sub a,b","?","subl a,b","not b",
"add x,a","adc x,a","asr a","lsr a","sub x,a","sbc x,a","abs a","ror a",
"add x,b","adc x,b","asr b","lsr b","sub x,b","sbc x,b","abs b","ror b",
"add y,a","adc y,a","asl a","lsl a","sub y,a","sbc y,a","neg a","rol a",
"add y,b","adc y,b","asl b","lsl b","sub y,b","sbc y,b","neg b","rol b",
"add x0,a","tfr x0,a","or x0,a","eor x0,a","sub x0,a","cmp x0,a","and x0,a","cmpm x0,a",
"add x0,b","tfr x0,b","or x0,b","eor x0,b","sub x0,b","cmp x0,b","and x0,b","cmpm x0,b",
"add y0,a","tfr y0,a","or y0,a","eor y0,a","sub y0,a","cmp y0,a","and y0,a","cmpm y0,a",
"add y0,b","tfr y0,b","or y0,b","eor y0,b","sub y0,b","cmp y0,b","and y0,b","cmpm y0,b",
"add x1,a","tfr x1,a","or x1,a","eor x1,a","sub x1,a","cmp x1,a","and x1,a","cmpm x1,a",
"add x1,b","tfr x1,b","or x1,b","eor x1,b","sub x1,b","cmp x1,b","and x1,b","cmpm x1,b",
"add y1,a","tfr y1,a","or y1,a","eor y1,a","sub y1,a","cmp y1,a","and y1,a","cmpm y1,a",
"add y1,b","tfr y1,b","or y1,b","eor y1,b","sub y1,b","cmp y1,b","and y1,b","cmpm y1,b",
};
static std::string aluName(int op)
{
    if (op < 128) return alu[op];
    static const char* pairs[8] = { "x0,x0","y0,y0","x1,x0","y1,y0","x0,y1","y0,x0","x1,y0","y1,x1" };
    static const char* kinds[4] = { "mpy","mpyr","mac","macr" };
    return std::string(kinds[op & 3]) + ((op & 4) ? " -" : " +") + pairs[(op >> 4) & 7] + ((op & 8) ? ",b" : ",a");
}
static const char* reg[64] = {
"?","?","?","?","x0","x1","y0","y1","a0","b0","a2","b2","a1","b1","a","b",
"r0","r1","r2","r3","r4","r5","r6","r7","n0","n1","n2","n3","n4","n5","n6","n7",
"m0","m1","m2","m3","m4","m5","m6","m7","?","?","?","?","?","?","?","?",
"?","?","?","?","?","?","?","?","?","sr","omr","sp","ssh","ssl","la","lc" };
static const char* cc[16] = { "cc","ge","ne","pl","nn","ec","lc","gt","cs","lt","eq","mi","nr","es","ls","le" };
static const char* lreg[8] = { "a10","b10","x","y","a","b","ab","ba" };

static std::vector<uint32_t> p;
static uint32_t ext; static bool usedExt;

static std::string ea(int mmm, int rrr)
{
    char b[32];
    switch (mmm) {
    case 0: std::snprintf(b, sizeof b, "(r%d)-n%d", rrr, rrr); break;
    case 1: std::snprintf(b, sizeof b, "(r%d)+n%d", rrr, rrr); break;
    case 2: std::snprintf(b, sizeof b, "(r%d)-", rrr); break;
    case 3: std::snprintf(b, sizeof b, "(r%d)+", rrr); break;
    case 4: std::snprintf(b, sizeof b, "(r%d)", rrr); break;
    case 5: std::snprintf(b, sizeof b, "(r%d+n%d)", rrr, rrr); break;
    case 6: usedExt = true; std::snprintf(b, sizeof b, rrr == 4 ? "#$%06x" : "$%04x", ext); break;
    default: std::snprintf(b, sizeof b, "-(r%d)", rrr); break;
    }
    return b;
}
static std::string hex(uint32_t v) { char b[16]; std::snprintf(b, sizeof b, "$%x", v); return b; }

// Parallel move field (bits 23..8); "" if none.
static std::string pmove(uint32_t w)
{
    const int top = (w >> 20) & 15;
    if (top == 2 || top == 3) { // 001x
        if (((w >> 18) & 7) == 0) { // 0010 00..
            if ((w & 0xFFFF00) == 0x200000) return "";
            if ((w & 0xFFE000) == 0x204000) return ea((w >> 11) & 7, (w >> 8) & 7) + " (update)";
            return std::string(reg[(w >> 13) & 31]) + "," + reg[(w >> 8) & 31];
        }
        return "#" + hex((w >> 8) & 0xFF) + "," + reg[(w >> 16) & 31];
    }
    if (top >= 4 && top <= 7) { // 01..
        const bool write = (w >> 15) & 1; // 1: memory -> register
        const std::string addr = ((w >> 14) & 1) ? ea((w >> 11) & 7, (w >> 8) & 7) : hex((w >> 8) & 0x3F);
        const int d = ((w >> 17) & 0x18) | ((w >> 16) & 7);
        if (((w >> 20) & 3) == 0 && ((w >> 18) & 1) == 0) { // L:
            const int l = ((w >> 17) & 4) | ((w >> 16) & 3);
            return write ? "l:" + addr + "," + lreg[l] : std::string(lreg[l]) + ",l:" + addr;
        }
        const char* space = ((w >> 19) & 1) ? "y:" : "x:";
        return write ? space + addr + "," + reg[d] : std::string(reg[d]) + "," + space + addr;
    }
    if (top == 1) { // X:R and R:Y
        const std::string a = ea((w >> 11) & 7, (w >> 8) & 7);
        const bool write = (w >> 15) & 1;
        if (((w >> 14) & 1) == 0) { // 0001 ffdf W0MM MRRR: X:ea <-> ff ; S2(a/b by d) -> x0/x1? (d: A->X0/B->X1? see manual)
            static const char* f1[4] = { "x0","x1","a","b" };
            const char* r1 = f1[(w >> 18) & 3];
            const char* s2 = ((w >> 17) & 1) ? "b" : "a";
            const char* d2 = ((w >> 16) & 1) ? "y1" : "y0";
            return (write ? "x:" + a + "," + r1 : std::string(r1) + ",x:" + a) + " " + s2 + "," + d2;
        } else {        // 0001 deff W1MM MRRR
            static const char* f2[4] = { "y0","y1","a","b" };
            const char* r2 = f2[(w >> 16) & 3];
            const char* s1 = ((w >> 19) & 1) ? "b" : "a";
            const char* d1 = ((w >> 18) & 1) ? "x1" : "x0";
            return std::string(s1) + "," + d1 + " " + (write ? "y:" + a + "," + r2 : std::string(r2) + ",y:" + a);
        }
    }
    if (top >= 8) { // XY: 1wmm eeff WrrM MRRR
        static const char* xr[4] = { "x0","x1","a","b" };
        static const char* yr[4] = { "y0","y1","a","b" };
        static const int modes[4] = { 4, 1, 2, 3 }; // (Rn), (Rn)+Nn, (Rn)-, (Rn)+
        const int rx = (w >> 8) & 7, mx = modes[(w >> 11) & 3];
        const int ry = ((rx & 4) ? 0 : 4) + ((w >> 13) & 3), my = modes[(w >> 20) & 3];
        const bool wx = (w >> 15) & 1, wy = (w >> 22) & 1;
        const std::string X = wx ? "x:" + ea(mx, rx) + "," + xr[(w >> 18) & 3] : std::string(xr[(w >> 18) & 3]) + ",x:" + ea(mx, rx);
        const std::string Y = wy ? "y:" + ea(my, ry) + "," + yr[(w >> 16) & 3] : std::string(yr[(w >> 16) & 3]) + ",y:" + ea(my, ry);
        return X + " " + Y;
    }
    return "?pm";
}

static std::string nonParallel(uint32_t w)
{
    switch (w) {
    case 0x000000: return "nop"; case 0x00000C: return "rts"; case 0x000004: return "rti";
    case 0x000005: return "illegal"; case 0x000086: return "wait"; case 0x000087: return "stop";
    case 0x000084: return "reset"; case 0x00008C: return "enddo";
    }
    const int hi = (w >> 16) & 0xFF;
    const int mmm = (w >> 11) & 7, rrr = (w >> 8) & 7;
    if (hi == 0x0C) return "jmp " + hex(w & 0xFFF);
    if (hi == 0x0D) return "jsr " + hex(w & 0xFFF);
    if ((w & 0xFF0000) == 0x0E0000) return std::string("j") + cc[(w >> 12) & 15] + " " + hex(w & 0xFFF);
    if ((w & 0xFF0000) == 0x0F0000) return std::string("js") + cc[(w >> 12) & 15] + " " + hex(w & 0xFFF);
    if ((w & 0xFFC0FF) == 0x0AC080) return "jmp " + ea(mmm, rrr);
    if ((w & 0xFFC0FF) == 0x0BC080) return "jsr " + ea(mmm, rrr);
    if ((w & 0xFFC0F0) == 0x0AC0A0) return std::string("j") + cc[w & 15] + " " + ea(mmm, rrr);
    if ((w & 0xFFC0F0) == 0x0BC0A0) return std::string("js") + cc[w & 15] + " " + ea(mmm, rrr);
    if (hi == 0x0A || hi == 0x0B) {
        // bit ops / jclr / jset: 0000 101x ccxx xxxx kSkb bbbb
        const int mode = (w >> 14) & 3; // 00 aa, 01 ea, 10 pp, 11 reg
        const int bit = w & 31;
        const bool S = (w >> 6) & 1, k5 = (w >> 5) & 1, k7 = (w >> 7) & 1;
        std::string loc;
        if (mode == 0) loc = std::string(S ? "y:" : "x:") + hex((w >> 8) & 0x3F);
        else if (mode == 1) loc = std::string(S ? "y:" : "x:") + ea(mmm, rrr);
        else if (mode == 2) loc = std::string(S ? "y:" : "x:") + hex(0xFFC0 + ((w >> 8) & 0x3F));
        else loc = reg[(w >> 8) & 63];
        std::string name;
        if (hi == 0x0A) name = k7 ? (k5 ? "jset" : "jclr") : (k5 ? "bset" : "bclr");
        else name = k7 ? (k5 ? "jsset" : "jsclr") : (k5 ? "btst" : "bchg");
        if (k7) { usedExt = true; return name + " #" + std::to_string(bit) + "," + loc + "," + hex(ext); }
        return name + " #" + std::to_string(bit) + "," + loc;
    }
    if (hi == 0x06) { // do / rep
        const bool rep = (w >> 5) & 1;
        std::string src;
        if (((w >> 14) & 3) == 1) src = std::string(((w >> 6) & 1) ? "y:" : "x:") + ea(mmm, rrr);
        else if (((w >> 14) & 3) == 0 && !((w >> 7) & 1)) src = std::string(((w >> 6) & 1) ? "y:" : "x:") + hex((w >> 8) & 0x3F);
        else if ((w >> 7) & 1) src = "#" + hex(((w >> 8) & 0xFF) | ((w & 15) << 8));
        else src = reg[(w >> 8) & 63];
        if (rep) return "rep " + src;
        usedExt = true; return "do " + src + "," + hex(ext);
    }
    if ((w & 0xFF4000) == 0x054000 || (w & 0xFF00E0) == 0x0500A0) { // movec ea / #xx
        const char* d = reg[w & 63];
        if ((w & 0xFF00E0) == 0x0500A0 && !((w >> 14) & 1) ) return "movec #" + hex((w >> 8) & 0xFF) + "," + d;
        const bool write = (w >> 15) & 1; const char* sp = ((w >> 6) & 1) ? "y:" : "x:";
        const std::string a = ea(mmm, rrr);
        return write ? std::string("movec ") + sp + a + "," + d : std::string("movec ") + d + "," + sp + a;
    }
    if ((w & 0xFF4080) == 0x044080) { // movec reg
        const bool write = (w >> 15) & 1;
        return write ? std::string("movec ") + reg[(w >> 8) & 63] + "," + reg[w & 63] : std::string("movec ") + reg[w & 63] + "," + reg[(w >> 8) & 63];
    }
    if ((w & 0xFFE0E0) == 0x044000) return "lua " + ea(mmm, rrr) + "," + reg[16 + (w & 15)];
    if ((w & 0xFE0000) == 0x080000) { // movep
        const bool write = (w >> 15) & 1; const char* sp = ((w >> 16) & 1) ? "y:" : "x:";
        const std::string pp = std::string(sp) + hex(0xFFC0 + (w & 0x3F));
        std::string other;
        if ((w >> 14) & 1) other = std::string(((w >> 6) & 1) ? "y:" : "x:") + ea(mmm, rrr);
        else other = reg[(w >> 8) & 63];
        return write ? "movep " + pp + "," + other : "movep " + other + "," + pp;
    }
    if ((w & 0xFF00FC) == 0x0000B8) { static const char* d[4] = { "mr","ccr","omr","?" }; return "andi #" + hex((w >> 8) & 0xFF) + "," + d[w & 3]; }
    if ((w & 0xFF00FC) == 0x0000F8) { static const char* d[4] = { "mr","ccr","omr","?" }; return "ori #" + hex((w >> 8) & 0xFF) + "," + d[w & 3]; }
    if ((w & 0xFFC0C0) == 0x018040) return "div ?"; // rarely used
    return "dc " + hex(w);
}

int main(int argc, char** argv)
{
    std::ifstream f(argv[1], std::ios::binary);
    std::vector<uint8_t> b(std::istreambuf_iterator<char>(f), {});
    for (size_t i = 0; i + 2 < b.size(); i += 3) p.push_back((uint32_t) b[i] << 16 | b[i + 1] << 8 | b[i + 2]);
    size_t a = argc > 2 ? std::stoul(argv[2], nullptr, 16) : 0;
    while (a < p.size()) {
        const uint32_t w = p[a];
        ext = a + 1 < p.size() ? p[a + 1] : 0; usedExt = false;
        std::string text;
        if (w < 0x100000) text = nonParallel(w);
        else {
            const std::string mv = pmove(w);
            text = aluName(w & 0xFF);
            if (!mv.empty()) text += std::string(18 - std::min<size_t>(17, text.size()), ' ') + mv;
        }
        std::printf("p:%04zx  %06x%s  %s\n", a, w, usedExt ? (" " + std::string(1, ' ')).c_str() : "", text.c_str());
        if (usedExt) { std::printf("p:%04zx  %06x\n", a + 1, ext); ++a; }
        ++a;
    }
}
