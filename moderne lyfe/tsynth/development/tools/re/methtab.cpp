// methtab: decode MPW Object Pascal method dispatch stubs in %_MethTables.
// Each stub is "JSR selector" (4EBA disp) followed by: count, 0000 0000,
// then count pairs of (class word, jump-table offset).
// Usage: methtab CODE_18.bin jumptable.txt
#include <cstdint>
#include <cstdio>
#include <fstream>
#include <map>
#include <sstream>
#include <string>
#include <vector>

int main(int, char** argv)
{
    std::ifstream f(argv[1], std::ios::binary);
    std::vector<uint8_t> c(std::istreambuf_iterator<char>(f), {});
    std::map<unsigned, std::string> jt; // A5 offset of entry -> function
    std::ifstream j(argv[2]);
    for (std::string line; std::getline(j, line);) {
        unsigned off; 
        if (std::sscanf(line.c_str(), "A5+$%x", &off) == 1) { auto p = line.rfind(' '); jt[off] = line.substr(p + 1); }
    }
    std::map<int, std::string> cls = {
        {0xAC,"TBLOCK"},{0xAE,"TLBLOCK"},{0xB0,"TOUTPUT"},{0xB2,"TSAMP"},{0xB4,"TOSC"},{0xB6,"TENV"},{0xB8,"TAMP"},{0xBA,"TFILTER"},
        {0xBC,"TSI"},{0xBE,"TPENV"},{0xC0,"TSHAPER"},{0xC2,"TMOD"},{0xC4,"TMIX"},{0xC6,"TDELAY"},{0xC8,"TTIME"},{0xCA,"TRES"},
        {0xCC,"TCOMPAND"},{0xCE,"TEXPANDER"},{0xD0,"TCOMPRESS"},{0xD2,"TPSHIFT"},{0xD4,"TNOISE"},{0xD6,"TDIFFUSE"},{0xA8,"TCARD"},{0xAA,"TSOUNDPLAYER"}};
    auto u16 = [&](size_t o) { return (unsigned) (c[o] << 8 | c[o + 1]); };
    const size_t base = 4; // first code byte (listing addresses are file offsets)
    for (size_t o = base; o + 8 < c.size(); o += 2) {
        if (u16(o) != 0x4EBA) continue;
        const int disp = (int16_t) u16(o + 2);
        if ((long) o + 2 + disp != 0x1C) continue;
        const unsigned n = u16(o + 4);
        if (n == 0 || n > 64 || u16(o + 6) || u16(o + 8)) continue;
        bool modules = false;
        std::ostringstream s;
        for (unsigned i = 0; i < n; ++i) {
            const unsigned cw = u16(o + 10 + i * 4), to = u16(o + 10 + i * 4 + 2);
            const int id = cw & 0xFF;
            if (id >= 0xAC && id <= 0xD6) modules = true;
            auto it = cls.find(id);
            s << "    " << (it != cls.end() ? it->second : std::to_string(id)) << " -> " << (jt.count(to - 2) ? jt[to - 2] : "?") << "\n";
        }
        if (modules) std::printf("selector @%05zX (JT entry for callers: F18_%05zX)\n%s", o - base, o - base, s.str().c_str());
        o += 10 + n * 4 - 2;
    }
}
