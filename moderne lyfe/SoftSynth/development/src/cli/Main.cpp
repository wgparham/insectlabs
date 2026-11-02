// softsynth-cli: renders a .PRM file to a Sound Designer I or WAV file.
//
// softsynth-cli in.prm out.sd|out.wav [--seed N | --zero-random] [--no-normalize]
//               [--buffer BYTES] [--save-patch out.prm]
#include "Engine.h"

#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <string>

using namespace softsynth;

static bool hasSuffix(const std::string& s, const char* suffix)
{
    const size_t n = std::strlen(suffix);
    return s.size() >= n && s.compare(s.size() - n, n, suffix) == 0;
}

static bool writeWav(const std::string& path, const std::vector<int16_t>& s, uint32_t rate)
{
    FILE* f = std::fopen(path.c_str(), "wb");
    if (!f) return false;
    auto le32 = [&](uint32_t v) { for (int i = 0; i < 4; ++i) std::fputc((int) ((v >> (i * 8)) & 0xFF), f); };
    auto le16 = [&](uint16_t v) { std::fputc(v & 0xFF, f); std::fputc(v >> 8, f); };
    const uint32_t bytes = (uint32_t) s.size() * 2;
    std::fwrite("RIFF", 1, 4, f); le32(36 + bytes); std::fwrite("WAVEfmt ", 1, 8, f);
    le32(16); le16(1); le16(1); le32(rate); le32(rate * 2); le16(2); le16(16);
    std::fwrite("data", 1, 4, f); le32(bytes);
    for (const int16_t v : s) le16((uint16_t) v);
    return std::fclose(f) == 0;
}

int main(int argc, char** argv)
{
    if (argc < 3) {
        std::fprintf(stderr, "usage: softsynth-cli in.prm out.sd|out.wav [--seed N | --zero-random] [--no-normalize] [--buffer BYTES] [--save-patch out.prm]\n");
        return 2;
    }
    uint32_t seed = 1;
    bool zero = false;
    RenderOptions options;
    std::string savePath;
    for (int i = 3; i < argc; ++i) {
        const std::string a = argv[i];
        if (a == "--seed" && i + 1 < argc) seed = (uint32_t) std::strtoul(argv[++i], nullptr, 0);
        else if (a == "--zero-random") zero = true;
        else if (a == "--no-normalize") options.normalize = false;
        else if (a == "--buffer" && i + 1 < argc) options.bufferSamples = (int32_t) (std::strtoul(argv[++i], nullptr, 0) >> 1);
        else if (a == "--save-patch" && i + 1 < argc) savePath = argv[++i];
        else { std::fprintf(stderr, "bad option %s\n", a.c_str()); return 2; }
    }

    Patch patch;
    std::string error;
    if (!loadPatch(argv[1], patch, &error)) {
        std::fprintf(stderr, "%s\n", error.c_str());
        return 1;
    }
    Engine engine(zero ? XbiosRandom::alwaysZero() : XbiosRandom(seed));
    RenderResult result;
    if (!engine.render(patch, options, result, &error)) {
        std::fprintf(stderr, "%s\n", error.c_str());
        return 3;
    }
    for (const auto& w : result.warnings)
        std::fprintf(stderr, "warning: %s\n", w.c_str());

    const std::string out = argv[2];
    bool ok;
    if (hasSuffix(out, ".wav")) {
        ok = writeWav(out, result.samples, (uint32_t) patch.rate);
    } else {
        const auto file = soundDesignerFile(patch, result.samples);
        FILE* f = std::fopen(out.c_str(), "wb");
        ok = f && std::fwrite(file.data(), 1, file.size(), f) == file.size();
        if (f) std::fclose(f);
    }
    if (!ok) {
        std::fprintf(stderr, "can't write %s\n", out.c_str());
        return 1;
    }
    if (!savePath.empty() && !savePatch(savePath, patch, &error)) {
        std::fprintf(stderr, "%s\n", error.c_str());
        return 1;
    }
    return 0;
}
