// Probe TurboSynth documents: for each module record try every reader
// layout from GReadFile and report which ones leave the stream at a valid
// next record. Usage: trbdprobe file...
#include <cstdint>
#include <cstdio>
#include <filesystem>
#include <fstream>
#include <map>
#include <set>
#include <string>
#include <vector>

static std::vector<uint8_t> d;
static int be16(size_t o) { return o + 1 < d.size() ? (int16_t) (d[o] << 8 | d[o + 1]) : -32768; }
static int32_t be32(size_t o) { return (int32_t) ((uint32_t) (uint16_t) be16(o) << 16 | (uint16_t) be16(o + 2)); }

enum Reader { Base, Output, Samp, Osc, Env, Shaper, Mod5, Mix32, Compand4, Words3, Noise4, kReaders };
static const char* rname[] = { "base", "output", "samp", "osc", "env", "shaper", "5w", "mix32", "4w", "3w", "noise4" };

static size_t baseLen(int ver) { return 8 + 2 + (ver <= 4 ? 1 : 0) + 256 + 4 + 1 + 4; }

// Returns end offset or 0 if the layout runs off the end.
static size_t tryReader(Reader r, size_t o, int ver)
{
    const int32_t length = be32(o + 8 + 2 + (ver <= 4 ? 1 : 0) + 256 + 4 + 1);
    o += baseLen(ver);
    switch (r) {
    case Base: break;
    case Output: o += ver == 1 ? 2 + 8 : 1 + 4 + 1 + 4 + 4; break;
    case Samp: {
        o += 8; if (ver > 3) o += 6;
        const int flag = o < d.size() ? d[o] : 0; o += 1;
        if (flag) { if (length < 0 || length > 20000000) return 0; o += (size_t) length * 2; }
        break; }
    case Osc: { o += 4; const int n = be16(o); o += 2; if (n < 0 || n > 64) return 0; o += (size_t) n * 554; break; }
    case Env: { if (ver > 1) o += 2; const int n = be16(o); o += 2; if (n < 0 || n > 2000) return 0; o += (size_t) n * 12; break; }
    case Shaper: o += 257 * 2; break;
    case Mod5: o += 10; break;
    case Mix32: o += 64; break;
    case Compand4: o += 8; break;
    case Words3: o += 6; break;
    case Noise4: o += 8; break;
    default: break;
    }
    return o <= d.size() ? o : 0;
}
static bool validType(int t) { return (t >= 5 && t <= 20) || t == 100 || t == 101; }

int main(int argc, char** argv)
{
    std::map<int, std::map<std::string, int>> votes;
    for (int a = 1; a < argc; ++a) {
        std::ifstream f(std::filesystem::u8path(argv[a]), std::ios::binary);
        d.assign(std::istreambuf_iterator<char>(f), {});
        size_t o = 120;
        const int ver = be16(o); o += 2;
        if (ver > 2 && ver < 5) o += 1;
        if (ver == 1) o += 12;
        const int count = be16(o); o += 2;
        std::printf("%s: version %d, %d modules\n", std::filesystem::u8path(argv[a]).filename().u8string().c_str(), ver, count);
        for (int m = 0; m < count; ++m) {
            const int type = be16(o); o += 2;
            const size_t nameAt = o + 8 + 2 + (ver <= 4 ? 1 : 0);
            std::string name((const char*) &d[nameAt + 1], std::min<size_t>(d[nameAt], 40));
            std::vector<std::pair<Reader, size_t>> ok;
            Reader fixed = type == 5 ? Osc : type == 6 ? Samp : (type==7||type==8||type==9||type==19) ? Env : (type==10||type==13||type==16) ? Mod5 : type==11 ? Shaper : type==12 ? Mix32 : (type==14||type==20) ? Words3 : (type==15||type==17||type==18) ? Compand4 : Output;
            for (int r = fixed; r <= fixed; ++r) {
                const size_t e = tryReader((Reader) r, o, ver);
                if (!e) continue;
                const bool last = m == count - 1;
                if ((!last && validType(be16(e))) || (last && e + 2 <= d.size() && be16(e) >= 0 && e + 2 + (size_t) be16(e) * 4 <= d.size() + 64))
                    ok.push_back({ (Reader) r, e });
            }
            std::string cands;
            for (auto& [r, e] : ok) cands += std::string(rname[r]) + " ";
            std::printf("  type %3d id %5d '%s' sr %d len %d -> %s\n", type, be16(o + 8), name.c_str(), be32(nameAt + 256), be32(nameAt + 256 + 5), cands.c_str());
            if (ok.empty()) { std::printf("  !! lost sync\n"); break; }
            if (ok.size() == 1) votes[type][rname[ok[0].first]]++;
            o = ok[0].second;
        }
        const int nc = be16(o);
        std::printf("  connections %d:", nc);
        for (int i = 0; i < nc && i < 40; ++i) std::printf(" %d>%d", be16(o + 2 + i * 4), be16(o + 4 + i * 4));
        std::printf("   (end %zu of %zu)\n", o + 2 + (size_t) nc * 4, d.size());
    }
    std::printf("\nUnambiguous reader per type:\n");
    for (auto& [t, m] : votes) { std::printf("  %3d:", t); for (auto& [r, n] : m) std::printf(" %s x%d", r.c_str(), n); std::printf("\n"); }
}
