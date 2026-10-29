// tsynth-cli: render a TurboSynth document to a WAV file.
//
//   tsynth-cli patch out.wav [sounds=folder] [info] [module=N]
//
// Sample modules without embedded sound look for their file next to the
// patch, in a "Sound files" folder there, and in the folder given by sounds=.

#include "Engine.h"

#include <algorithm>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <filesystem>
#include <string>
#include <vector>

using namespace turbosynth;

namespace {

void put16(FILE* f, uint16_t v) { std::fputc(v & 0xFF, f); std::fputc(v >> 8, f); }
void put32(FILE* f, uint32_t v) { put16(f, (uint16_t) (v & 0xFFFF)); put16(f, (uint16_t) (v >> 16)); }

bool writeWav(const char* path, const RenderResult& r)
{
    FILE* f = std::fopen(path, "wb");
    if (!f) return false;
    const uint16_t ch = r.right.empty() ? 1 : 2;
    const uint32_t frames = (uint32_t) r.left.size(), bytes = frames * ch * 2;
    std::fwrite("RIFF", 1, 4, f); put32(f, 36 + bytes);
    std::fwrite("WAVEfmt ", 1, 8, f); put32(f, 16);
    put16(f, 1); put16(f, ch); put32(f, (uint32_t) r.sampleRate); put32(f, (uint32_t) r.sampleRate * ch * 2); put16(f, (uint16_t) (ch * 2)); put16(f, 16);
    std::fwrite("data", 1, 4, f); put32(f, bytes);
    for (uint32_t i = 0; i < frames; ++i) {
        put16(f, (uint16_t) r.left[i]);
        if (ch == 2) put16(f, (uint16_t) r.right[i]);
    }
    std::fclose(f);
    return true;
}

} // namespace

#ifdef _WIN32
#include <windows.h>
#include <shellapi.h>
// Windows passes main() its arguments in the ANSI code page, which loses
// characters such as the pi in "π Mutant". Re-read them as UTF-8.
static std::vector<std::string> utf8Args()
{
    std::vector<std::string> out;
    int n = 0;
    if (wchar_t** w = CommandLineToArgvW(GetCommandLineW(), &n)) {
        for (int i = 0; i < n; ++i) {
            const int len = WideCharToMultiByte(CP_UTF8, 0, w[i], -1, nullptr, 0, nullptr, nullptr);
            std::string s((size_t) std::max(0, len - 1), '\0');
            WideCharToMultiByte(CP_UTF8, 0, w[i], -1, s.data(), len, nullptr, nullptr);
            out.push_back(s);
        }
        LocalFree(w);
    }
    return out;
}
#endif

int main(int argc, char** argv)
{
#ifdef _WIN32
    const auto wide = utf8Args();
    std::vector<char*> fixed;
    for (const auto& s : wide) fixed.push_back(const_cast<char*>(s.c_str()));
    if ((int) fixed.size() == argc) argv = fixed.data();
#endif
    if (argc < 3) { std::fprintf(stderr, "usage: tsynth-cli patch out.wav [sounds=folder] [info] [module=N]\n"); return 1; }
    RenderOptions opt;
    const auto dir = std::filesystem::u8path(argv[1]).parent_path();
    opt.soundFolders.push_back(dir.u8string());
    opt.soundFolders.push_back(dir.parent_path().u8string());
    bool info = false;
    int only = 0;
    for (int i = 3; i < argc; ++i) {
        if (!std::strncmp(argv[i], "sounds=", 7)) opt.soundFolders.push_back(argv[i] + 7);
        else if (!std::strcmp(argv[i], "info")) info = true;
        else if (!std::strncmp(argv[i], "module=", 7)) only = std::atoi(argv[i] + 7);
        else { std::fprintf(stderr, "unknown argument: %s\n", argv[i]); return 1; }
    }
    Patch patch;
    std::string err;
    if (!loadPatch(argv[1], patch, &err)) { std::fprintf(stderr, "%s\n", err.c_str()); return 1; }
    if (info) {
        for (size_t i = 0; i < patch.modules.size(); ++i) {
            const auto& m = patch.modules[i];
            std::printf("%2zu %-22s '%s' %d Hz, %d samples%s\n", i + 1, moduleTypeName(m.type), m.name.c_str(), m.sampleRate, m.length, m.normalize ? ", normalize" : "");
            std::printf("     kind %d at (%d,%d)-(%d,%d)  p: %d %d %d %d %d", m.kind, m.rect[1], m.rect[0], m.rect[3], m.rect[2], m.p[0], m.p[1], m.p[2], m.p[3], m.p[4]);
            if (m.type == kOscillator) std::printf("  freq %.3f Hz, %zu waves", m.oscFreq / 65536.0, m.waveforms.size());
            if (m.type == kOutput || m.type == kOutputR) std::printf("  crossfade %d len %d type %d loop %d..%d", m.out[0], m.out[1], m.out[2], m.out[3], m.out[4]);
            if (m.type == kSample) std::printf("  loop %d..%d  c %d %d %d", m.sampA, m.sampB, m.sampC[0], m.sampC[1], m.sampC[2]);
            for (const auto& pt : m.points) std::printf("  (%d@%dms)", pt.level, pt.timeMs);
            std::printf("\n");
        }
        for (const auto& c : patch.connections) std::printf("   %d -> %d\n", c.from, c.to);
    }
    Engine engine(patch, opt);
    RenderResult r = engine.render();
    if (only > 0) { // write one module's output instead
        r.left = engine.moduleOutput(only);
        r.right.clear();
        r.sampleRate = patch.modules[(size_t) only - 1].sampleRate;
        int crossings = 0;
        for (size_t i = 1; i < r.left.size(); ++i) if (r.left[i - 1] < 0 && r.left[i] >= 0) ++crossings;
        std::printf("module %d: %.2f Hz by upward zero crossings\n", only, r.left.empty() ? 0.0 : crossings * (double) r.sampleRate / r.left.size());
    }
    for (const auto& w : r.warnings) std::fprintf(stderr, "warning: %s\n", w.c_str());
    if (r.left.empty()) { std::fprintf(stderr, "the patch produced no output\n"); return 1; }
    if (!writeWav(argv[2], r)) { std::fprintf(stderr, "cannot write %s\n", argv[2]); return 1; }
    int peak = 0;
    for (int16_t v : r.left) peak = std::max(peak, std::abs((int) v));
    std::printf("%zu samples at %d Hz%s, peak %d\n", r.left.size(), r.sampleRate, r.right.empty() ? "" : ", stereo", peak);
    return 0;
}
