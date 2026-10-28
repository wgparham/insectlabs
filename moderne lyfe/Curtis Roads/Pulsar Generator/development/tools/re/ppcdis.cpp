// Minimal PowerPC disassembler for CodeWarrior PEF code.
// Usage: ppcdis file.bin <function-name>   (finds the function via its traceback table)
//        ppcdis file.bin @<offset> <bytes>
#include <cstdint>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <string>
#include <vector>

static std::vector<unsigned char> d;
static uint32_t w32(size_t o) { return (uint32_t) d[o] << 24 | (uint32_t) d[o + 1] << 16 | (uint32_t) d[o + 2] << 8 | d[o + 3]; }

static std::string r(int n) { return "r" + std::to_string(n); }
static std::string simm(uint32_t i) { int v = (int16_t) (i & 0xFFFF); char b[32]; std::snprintf(b, sizeof b, v < 0 ? "-0x%X" : "0x%X", v < 0 ? -v : v); return b; }
static std::string uimm(uint32_t i) { char b[32]; std::snprintf(b, sizeof b, "0x%X", i & 0xFFFF); return b; }

static std::string dis(uint32_t i, size_t pc)
{
    const int op = i >> 26, rd = (i >> 21) & 31, ra = (i >> 16) & 31, rb = (i >> 11) & 31;
    const int xo = (i >> 1) & 1023;
    char b[128];
    auto mem = [&](const char* n) { std::snprintf(b, sizeof b, "%-8s %s, %s(%s)", n, r(rd).c_str(), simm(i).c_str(), r(ra).c_str()); return std::string(b); };
    switch (op) {
        case 7: std::snprintf(b, sizeof b, "mulli    %s, %s, %s", r(rd).c_str(), r(ra).c_str(), simm(i).c_str()); return b;
        case 8: std::snprintf(b, sizeof b, "subfic   %s, %s, %s", r(rd).c_str(), r(ra).c_str(), simm(i).c_str()); return b;
        case 10: std::snprintf(b, sizeof b, "cmplwi   cr%d, %s, %s", rd >> 2, r(ra).c_str(), uimm(i).c_str()); return b;
        case 11: std::snprintf(b, sizeof b, "cmpwi    cr%d, %s, %s", rd >> 2, r(ra).c_str(), simm(i).c_str()); return b;
        case 12: std::snprintf(b, sizeof b, "addic    %s, %s, %s", r(rd).c_str(), r(ra).c_str(), simm(i).c_str()); return b;
        case 14: if (ra == 0) std::snprintf(b, sizeof b, "li       %s, %s", r(rd).c_str(), simm(i).c_str());
                 else std::snprintf(b, sizeof b, "addi     %s, %s, %s", r(rd).c_str(), r(ra).c_str(), simm(i).c_str()); return b;
        case 15: std::snprintf(b, sizeof b, "addis    %s, %s, %s", r(rd).c_str(), r(ra).c_str(), simm(i).c_str()); return b;
        case 16: { int bo = rd, bi = ra; int bd = (int16_t) (i & 0xFFFC); std::snprintf(b, sizeof b, "bc%s     %d, cr%d.%s, ->%zu", (i & 1) ? "l" : "", bo, bi / 4, (const char*[]){ "lt", "gt", "eq", "so" }[bi % 4], pc + bd); return b; }
        case 18: { int li = (int) (i & 0x03FFFFFC); if (li & 0x02000000) li -= 0x04000000; std::snprintf(b, sizeof b, "b%s       ->%zu", (i & 1) ? "l" : "", (i & 2) ? (size_t) li : pc + li); return b; }
        case 19:
            if (xo == 16) { std::snprintf(b, sizeof b, "bclr     %d, %d%s", rd, ra, rd == 20 ? "  (blr)" : ""); return b; }
            if (xo == 528) { std::snprintf(b, sizeof b, "bcctr%s  %d, %d", (i & 1) ? "l" : "", rd, ra); return b; }
            break;
        case 20: std::snprintf(b, sizeof b, "rlwimi   %s, %s, %d, %d, %d", r(ra).c_str(), r(rd).c_str(), rb, (i >> 6) & 31, (i >> 1) & 31); return b;
        case 21: std::snprintf(b, sizeof b, "rlwinm%s  %s, %s, %d, %d, %d", (i & 1) ? "." : "", r(ra).c_str(), r(rd).c_str(), rb, (i >> 6) & 31, (i >> 1) & 31); return b;
        case 24: if (i == 0x60000000) return "nop"; std::snprintf(b, sizeof b, "ori      %s, %s, %s", r(ra).c_str(), r(rd).c_str(), uimm(i).c_str()); return b;
        case 25: std::snprintf(b, sizeof b, "oris     %s, %s, %s", r(ra).c_str(), r(rd).c_str(), uimm(i).c_str()); return b;
        case 28: std::snprintf(b, sizeof b, "andi.    %s, %s, %s", r(ra).c_str(), r(rd).c_str(), uimm(i).c_str()); return b;
        case 31: {
            const char* n = nullptr;
            switch (xo) {
                case 0: std::snprintf(b, sizeof b, "cmpw     cr%d, %s, %s", rd >> 2, r(ra).c_str(), r(rb).c_str()); return b;
                case 32: std::snprintf(b, sizeof b, "cmplw    cr%d, %s, %s", rd >> 2, r(ra).c_str(), r(rb).c_str()); return b;
                case 8: n = "subfc"; break; case 10: n = "addc"; break; case 40: n = "subf"; break;
                case 104: std::snprintf(b, sizeof b, "neg      %s, %s", r(rd).c_str(), r(ra).c_str()); return b;
                case 138: n = "adde"; break; case 266: n = "add"; break; case 235: n = "mullw"; break;
                case 491: n = "divw"; break; case 459: n = "divwu"; break; case 11: n = "mulhwu"; break; case 75: n = "mulhw"; break;
                case 23: n = "lwzx"; break; case 87: n = "lbzx"; break; case 279: n = "lhzx"; break; case 343: n = "lhax"; break;
                case 151: n = "stwx"; break; case 215: n = "stbx"; break; case 407: n = "sthx"; break;
                case 55: n = "lwzux"; break; case 183: n = "stwux"; break;
                case 28: std::snprintf(b, sizeof b, "and      %s, %s, %s", r(ra).c_str(), r(rd).c_str(), r(rb).c_str()); return b;
                case 444: if (rd == rb) std::snprintf(b, sizeof b, "mr       %s, %s", r(ra).c_str(), r(rd).c_str());
                          else std::snprintf(b, sizeof b, "or       %s, %s, %s", r(ra).c_str(), r(rd).c_str(), r(rb).c_str()); return b;
                case 316: std::snprintf(b, sizeof b, "xor      %s, %s, %s", r(ra).c_str(), r(rd).c_str(), r(rb).c_str()); return b;
                case 124: std::snprintf(b, sizeof b, "nor      %s, %s, %s", r(ra).c_str(), r(rd).c_str(), r(rb).c_str()); return b;
                case 60: std::snprintf(b, sizeof b, "andc     %s, %s, %s", r(ra).c_str(), r(rd).c_str(), r(rb).c_str()); return b;
                case 24: std::snprintf(b, sizeof b, "slw      %s, %s, %s", r(ra).c_str(), r(rd).c_str(), r(rb).c_str()); return b;
                case 536: std::snprintf(b, sizeof b, "srw      %s, %s, %s", r(ra).c_str(), r(rd).c_str(), r(rb).c_str()); return b;
                case 792: std::snprintf(b, sizeof b, "sraw     %s, %s, %s", r(ra).c_str(), r(rd).c_str(), r(rb).c_str()); return b;
                case 824: std::snprintf(b, sizeof b, "srawi    %s, %s, %d", r(ra).c_str(), r(rd).c_str(), rb); return b;
                case 954: std::snprintf(b, sizeof b, "extsb    %s, %s", r(ra).c_str(), r(rd).c_str()); return b;
                case 922: std::snprintf(b, sizeof b, "extsh    %s, %s", r(ra).c_str(), r(rd).c_str()); return b;
                case 26: std::snprintf(b, sizeof b, "cntlzw   %s, %s", r(ra).c_str(), r(rd).c_str()); return b;
                case 339: { int spr = ((i >> 16) & 31) | ((i >> 6) & 0x3E0); std::snprintf(b, sizeof b, "mfspr    %s, %s", r(rd).c_str(), spr == 8 ? "lr" : spr == 9 ? "ctr" : std::to_string(spr).c_str()); return b; }
                case 467: { int spr = ((i >> 16) & 31) | ((i >> 6) & 0x3E0); std::snprintf(b, sizeof b, "mtspr    %s, %s", spr == 8 ? "lr" : spr == 9 ? "ctr" : std::to_string(spr).c_str(), r(rd).c_str()); return b; }
                case 19: std::snprintf(b, sizeof b, "mfcr     %s", r(rd).c_str()); return b;
                case 535: std::snprintf(b, sizeof b, "lfsx     f%d, %s, %s", rd, r(ra).c_str(), r(rb).c_str()); return b;
                case 599: std::snprintf(b, sizeof b, "lfdx     f%d, %s, %s", rd, r(ra).c_str(), r(rb).c_str()); return b;
                case 663: std::snprintf(b, sizeof b, "stfsx    f%d, %s, %s", rd, r(ra).c_str(), r(rb).c_str()); return b;
                case 727: std::snprintf(b, sizeof b, "stfdx    f%d, %s, %s", rd, r(ra).c_str(), r(rb).c_str()); return b;
                case 567: std::snprintf(b, sizeof b, "lfsux    f%d, %s, %s", rd, r(ra).c_str(), r(rb).c_str()); return b;
                case 695: std::snprintf(b, sizeof b, "stfsux   f%d, %s, %s", rd, r(ra).c_str(), r(rb).c_str()); return b;
                case 144: std::snprintf(b, sizeof b, "mtcrf    0x%X, %s", (i >> 12) & 0xFF, r(rd).c_str()); return b;
            }
            if (n) { std::snprintf(b, sizeof b, "%-8s %s, %s, %s%s", n, r(rd).c_str(), r(ra).c_str(), r(rb).c_str(), (i & 1) ? " ." : ""); return b; }
            break;
        }
        case 32: return mem("lwz"); case 33: return mem("lwzu"); case 34: return mem("lbz"); case 35: return mem("lbzu");
        case 36: return mem("stw"); case 37: return mem("stwu"); case 38: return mem("stb"); case 39: return mem("stbu");
        case 40: return mem("lhz"); case 41: return mem("lhzu"); case 42: return mem("lha"); case 44: return mem("sth"); case 45: return mem("sthu");
        case 46: return mem("lmw"); case 47: return mem("stmw");
        case 48: case 49: case 50: case 51: case 52: case 53: case 54: case 55: {
            static const char* n[] = { "lfs", "lfsu", "lfd", "lfdu", "stfs", "stfsu", "stfd", "stfdu" };
            std::snprintf(b, sizeof b, "%-8s f%d, %s(%s)", n[op - 48], rd, simm(i).c_str(), r(ra).c_str());
            return b;
        }
        case 59: case 63: {
            const char* s = op == 59 ? "s" : "";
            const int a5 = (i >> 1) & 31, rc = (i >> 6) & 31;
            const char* dot = (i & 1) ? "." : "";
            switch (a5) {
                case 18: std::snprintf(b, sizeof b, "fdiv%s%s   f%d, f%d, f%d", s, dot, rd, ra, rb); return b;
                case 20: std::snprintf(b, sizeof b, "fsub%s%s   f%d, f%d, f%d", s, dot, rd, ra, rb); return b;
                case 21: std::snprintf(b, sizeof b, "fadd%s%s   f%d, f%d, f%d", s, dot, rd, ra, rb); return b;
                case 22: std::snprintf(b, sizeof b, "fsqrt%s   f%d, f%d", s, rd, rb); return b;
                case 23: std::snprintf(b, sizeof b, "fsel     f%d, f%d, f%d, f%d", rd, ra, rc, rb); return b;
                case 24: std::snprintf(b, sizeof b, "fres     f%d, f%d", rd, rb); return b;
                case 25: std::snprintf(b, sizeof b, "fmul%s%s   f%d, f%d, f%d", s, dot, rd, ra, rc); return b;
                case 26: std::snprintf(b, sizeof b, "frsqrte  f%d, f%d", rd, rb); return b;
                case 28: std::snprintf(b, sizeof b, "fmsub%s  f%d, f%d, f%d, f%d   (a*c-b)", s, rd, ra, rc, rb); return b;
                case 29: std::snprintf(b, sizeof b, "fmadd%s  f%d, f%d, f%d, f%d   (a*c+b)", s, rd, ra, rc, rb); return b;
                case 30: std::snprintf(b, sizeof b, "fnmsub%s f%d, f%d, f%d, f%d   -(a*c-b)", s, rd, ra, rc, rb); return b;
                case 31: std::snprintf(b, sizeof b, "fnmadd%s f%d, f%d, f%d, f%d   -(a*c+b)", s, rd, ra, rc, rb); return b;
            }
            if (op == 63) {
                switch (xo) {
                    case 0: std::snprintf(b, sizeof b, "fcmpu    cr%d, f%d, f%d", rd >> 2, ra, rb); return b;
                    case 32: std::snprintf(b, sizeof b, "fcmpo    cr%d, f%d, f%d", rd >> 2, ra, rb); return b;
                    case 12: std::snprintf(b, sizeof b, "frsp     f%d, f%d", rd, rb); return b;
                    case 14: std::snprintf(b, sizeof b, "fctiw    f%d, f%d", rd, rb); return b;
                    case 15: std::snprintf(b, sizeof b, "fctiwz   f%d, f%d", rd, rb); return b;
                    case 40: std::snprintf(b, sizeof b, "fneg     f%d, f%d", rd, rb); return b;
                    case 72: std::snprintf(b, sizeof b, "fmr      f%d, f%d", rd, rb); return b;
                    case 136: std::snprintf(b, sizeof b, "fnabs    f%d, f%d", rd, rb); return b;
                    case 264: std::snprintf(b, sizeof b, "fabs     f%d, f%d", rd, rb); return b;
                    case 583: std::snprintf(b, sizeof b, "mffs     f%d", rd); return b;
                    case 711: std::snprintf(b, sizeof b, "mtfsf    0x%X, f%d", (i >> 17) & 0xFF, rb); return b;
                    case 70: std::snprintf(b, sizeof b, "mtfsb0   %d", rd); return b;
                    case 38: std::snprintf(b, sizeof b, "mtfsb1   %d", rd); return b;
                }
            }
            break;
        }
    }
    std::snprintf(b, sizeof b, ".long    0x%08X", i);
    return b;
}

int main(int argc, char** argv)
{
    FILE* f = std::fopen(argv[1], "rb"); int c; while ((c = std::fgetc(f)) != EOF) d.push_back((unsigned char) c); std::fclose(f);
    size_t start, len;
    if (argv[2][0] == '@') { start = std::strtoul(argv[2] + 1, nullptr, 0); len = std::strtoul(argv[3], nullptr, 0); }
    else {
        // traceback: ... 00000000 | 8 fixed bytes | [tb_offset] ... | name_len(2) | name
        // The name may be a prefix of the mangled symbol ("ugen_Osc1_a" matches
        // ".ugen_Osc1_a__FP..."); it must be followed by "__" or end the name.
        std::string name = std::string(".") + argv[2];
        size_t at = std::string::npos;
        for (size_t i = 0; i + name.size() + 2 < d.size(); ++i)
            if (std::memcmp(&d[i], name.data(), name.size()) == 0) {
                const size_t nlen = (size_t) d[i - 2] << 8 | d[i - 1];
                if (nlen == name.size() || (d[i + name.size()] == '_' && d[i + name.size() + 1] == '_')) { at = i; break; }
            }
        if (at == std::string::npos) { std::fprintf(stderr, "not found\n"); return 1; }
        const size_t tb = at - 2 - 4 - 8 - 4; // zero word
        len = w32(at - 2 - 4);
        start = tb - len;
        const size_t nlen = (size_t) d[at - 2] << 8 | d[at - 1];
        std::printf("; %.*s: code %zu..%zu (%zu bytes)\n", (int) nlen, (const char*) &d[at], start, tb, len);
    }
    for (size_t pc = start; pc < start + len; pc += 4) std::printf("%8zu  %08X  %s\n", pc, w32(pc), dis(w32(pc), pc).c_str());
}
