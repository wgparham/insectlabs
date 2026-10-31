// rsrcx: list a raw resource fork; optionally dump every resource as
// outdir/TYPE_ID.bin (TYPE with unsafe characters hex-escaped).
// Usage: rsrcx file.rsrc [outdir]
#include <cstdint>
#include <cstdio>
#include <filesystem>
#include <fstream>
#include <map>
#include <string>
#include <vector>

int main(int argc, char** argv)
{
    if (argc < 2) return 1;
    std::ifstream f(std::filesystem::u8path(argv[1]), std::ios::binary);
    std::vector<uint8_t> r(std::istreambuf_iterator<char>(f), {});
    if (r.size() < 16) { std::fprintf(stderr, "too small\n"); return 1; }
    auto u16 = [&](size_t o) { return (uint32_t) (r[o] << 8 | r[o + 1]); };
    auto u32 = [&](size_t o) { return u16(o) << 16 | u16(o + 2); };
    if (u32(0) == 0x00051607 || u32(0) == 0x00051600) { // AppleDouble / AppleSingle: take entry 2
        const int n = (int) u16(24);
        for (int i = 0; i < n; ++i) {
            const size_t e = 26 + (size_t) i * 12;
            if (u32(e) == 2) { std::vector<uint8_t> fork(r.begin() + u32(e + 4), r.begin() + u32(e + 4) + u32(e + 8)); r.swap(fork); break; }
        }
    }
    const uint32_t dataOff = u32(0), mapOff = u32(4);
    const uint32_t typeList = mapOff + u16(mapOff + 24), nameList = mapOff + u16(mapOff + 26);
    const int nTypes = (int) u16(typeList) + 1;
    std::map<std::string, std::pair<int, size_t>> summary;
    for (int t = 0; t < nTypes; ++t) {
        const size_t e = typeList + 2 + (size_t) t * 8;
        std::string type((const char*) &r[e], 4);
        const int n = (int) u16(e + 4) + 1;
        const size_t refs = typeList + u16(e + 6);
        for (int i = 0; i < n; ++i) {
            const size_t ro = refs + (size_t) i * 12;
            const int id = (int16_t) u16(ro);
            const uint16_t nameOff = (uint16_t) u16(ro + 2);
            const uint32_t off = (u32(ro + 4) & 0xFFFFFF) + dataOff;
            const uint32_t len = u32(off);
            std::string name;
            if (nameOff != 0xFFFF) name.assign((const char*) &r[nameList + nameOff + 1], r[nameList + nameOff]);
            auto& s = summary[type]; s.first++; s.second += len;
            if (argc > 2) {
                std::string safe;
                for (char c : type) { if (isalnum((unsigned char) c)) safe += c; else { char b[4]; std::snprintf(b, 4, "%02X", (unsigned char) c); safe += b; } }
                std::filesystem::create_directories(argv[2]);
                std::ofstream o(std::string(argv[2]) + "/" + safe + "_" + std::to_string(id) + ".bin", std::ios::binary);
                o.write((const char*) &r[off + 4], len);
            }
            if (argc > 3 || type == "CODE" || type == "STR " || type == "MENU" || type == "vers")
                std::printf("  %s %6d %7u  %s\n", type.c_str(), id, len, name.c_str());
        }
    }
    for (auto& [t, s] : summary) std::printf("%s x%-4d %8zu bytes\n", t.c_str(), s.first, s.second);
}
