// hfsx: list and extract a classic HFS volume image (data forks, plus
// resource forks as "name.rsrc"). Usage: hfsx image.img [outdir]
#include <cstdint>
#include <cstdio>
#include <cstring>
#include <filesystem>
#include <fstream>
#include <map>
#include <string>
#include <vector>

static std::vector<uint8_t> img;
static uint16_t be16(size_t o) { return (uint16_t) (img[o] << 8 | img[o + 1]); }
static uint32_t be32(size_t o) { return (uint32_t) be16(o) << 16 | be16(o + 2); }
static uint32_t blkSize, blkStart;
static uint32_t xtFileSize; static uint8_t xtExt[12];

struct Extent { uint16_t start, count; };
static std::vector<Extent> extents(const uint8_t* rec)
{
    std::vector<Extent> v;
    for (int i = 0; i < 3; ++i) {
        Extent e{ (uint16_t) (rec[i * 4] << 8 | rec[i * 4 + 1]), (uint16_t) (rec[i * 4 + 2] << 8 | rec[i * 4 + 3]) };
        if (e.count) v.push_back(e);
    }
    return v;
}
static std::vector<uint8_t> readFork(const std::vector<Extent>& ex, uint32_t len, bool* truncated)
{
    std::vector<uint8_t> out;
    for (auto& e : ex) {
        const size_t off = (size_t) blkStart * 512 + (size_t) e.start * blkSize, n = (size_t) e.count * blkSize;
        if (off + n > img.size()) break;
        out.insert(out.end(), img.begin() + (long) off, img.begin() + (long) (off + n));
    }
    *truncated = out.size() < len;
    if (out.size() > len) out.resize(len);
    return out;
}
static std::string macRoman(const uint8_t* p, int n)
{
    // Keep ASCII; map the few common high characters; replace path-unsafe ones.
    std::string s;
    for (int i = 0; i < n; ++i) {
        uint8_t c = p[i];
        if (c == 0xAA) s += "(TM)"; else if (c == 0xA8) s += "(R)"; else if (c == 0xA9) s += "(C)";
        else if (c == '/' || c == ':' || c == '\\' || c == '*' || c == '?' || c == '"' || c == '<' || c == '>' || c == '|' || c < 32) s += '_';
        else if (c >= 0x80) { char b[8]; std::snprintf(b, sizeof b, "%%%02X", c); s += b; }
        else s += (char) c;
    }
    return s;
}

int main(int argc, char** argv)
{
    if (argc < 2) { std::fprintf(stderr, "usage: hfsx image [outdir]\n"); return 1; }
    std::ifstream f(argv[1], std::ios::binary);
    img.assign(std::istreambuf_iterator<char>(f), {});
    if (img.size() < 1536 || be16(1024) != 0x4244) { std::fprintf(stderr, "not an HFS volume\n"); return 1; }
    const size_t m = 1024;
    blkSize = be32(m + 0x14);
    blkStart = be16(m + 0x1C);
    std::printf("volume '%s' block size %u, first block sector %u\n", macRoman(&img[m + 0x25], img[m + 0x24]).c_str(), blkSize, blkStart);
    const uint32_t ctSize = be32(m + 0x92);
    bool tr;
    auto cat = readFork(extents(&img[m + 0x96]), ctSize, &tr);
    if (tr) std::fprintf(stderr, "warning: catalog uses overflow extents; listing may be incomplete\n");

    struct Node { uint32_t parent; std::string name; };
    std::map<uint32_t, Node> dirs;   // dirID -> parent/name
    struct File { uint32_t parent; std::string name; char type[5], creator[5]; std::vector<uint8_t> data, rsrc; bool trunc; };
    std::vector<File> files;

    const uint32_t nodeSize = 512;
    uint32_t node = (uint32_t) (cat[14 + 10] << 24 | cat[14 + 11] << 16 | cat[14 + 12] << 8 | cat[14 + 13]); // bthFNode
    auto c16 = [&](size_t o) { return (uint16_t) (cat[o] << 8 | cat[o + 1]); };
    auto c32 = [&](size_t o) { return (uint32_t) c16(o) << 16 | c16(o + 2); };
    int guard = 0;
    while (node && ++guard < 100000) {
        const size_t base = (size_t) node * nodeSize;
        if (base + nodeSize > cat.size()) break;
        const uint16_t nrecs = c16(base + 10);
        for (int r = 0; r < nrecs; ++r) {
            const size_t ro = base + c16(base + nodeSize - 2 * (r + 1));
            const uint8_t keyLen = cat[ro];
            if (keyLen == 0) continue;
            const uint32_t parID = c32(ro + 2);
            const std::string name = macRoman(&cat[ro + 7], cat[ro + 6]);
            size_t d = ro + keyLen + 1; if (d & 1) ++d;
            const uint8_t type = cat[d];
            if (type == 1) dirs[c32(d + 6)] = { parID, name };
            else if (type == 2) {
                File fi; fi.parent = parID; fi.name = name;
                std::memcpy(fi.type, &cat[d + 4], 4); fi.type[4] = 0;
                std::memcpy(fi.creator, &cat[d + 8], 4); fi.creator[4] = 0;
                bool t1, t2;
                fi.data = readFork(extents(&cat[d + 74]), c32(d + 26), &t1);
                fi.rsrc = readFork(extents(&cat[d + 86]), c32(d + 36), &t2);
                fi.trunc = t1 || t2;
                files.push_back(std::move(fi));
            }
        }
        node = c32(base); // fLink
    }
    auto pathOf = [&](uint32_t id) {
        std::string p;
        for (int depth = 0; id > 1 && depth < 64; ++depth) {
            auto it = dirs.find(id);
            if (it == dirs.end()) break;
            if (it->second.parent > 1 || true) p = it->second.name + (p.empty() ? "" : "/") + p;
            id = it->second.parent;
        }
        return p;
    };
    for (auto& fi : files) {
        const std::string rel = pathOf(fi.parent) + "/" + fi.name;
        std::printf("%-4s %-4s %8zu %8zu %s%s\n", fi.type, fi.creator, fi.data.size(), fi.rsrc.size(), rel.c_str(), fi.trunc ? "  [TRUNCATED: overflow extents]" : "");
        if (argc > 2) {
            const auto out = std::filesystem::u8path(std::string(argv[2]) + "/" + rel);
            std::filesystem::create_directories(out.parent_path());
            if (!fi.data.empty() || fi.rsrc.empty()) { std::ofstream o(out, std::ios::binary); o.write((const char*) fi.data.data(), (std::streamsize) fi.data.size()); }
            if (!fi.rsrc.empty()) { std::ofstream o(out.string() + ".rsrc", std::ios::binary); o.write((const char*) fi.rsrc.data(), (std::streamsize) fi.rsrc.size()); }
        }
    }
    std::printf("%zu files, %zu folders\n", files.size(), dirs.size());
}
