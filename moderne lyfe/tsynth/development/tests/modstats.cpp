// Per-module output statistics for every module of the given patches.
#include "Engine.h"
#include <cmath>
#include <cstdio>
#include <filesystem>
#include <map>
using namespace turbosynth;
int main(int argc, char** argv)
{
    std::map<int, std::pair<int,int>> perType; // type -> (count, suspicious)
    for (int a = 1; a < argc; ++a) {
        Patch p; std::string err;
        if (!loadPatch(argv[a], p, &err)) { std::printf("%s: %s\n", argv[a], err.c_str()); continue; }
        RenderOptions o;
        Engine e(p, o);
        for (int i = 1; i <= (int) p.modules.size(); ++i) {
            const auto& b = e.moduleOutput(i);
            double sum = 0, sq = 0; int clip = 0, zc = 0;
            for (size_t k = 0; k < b.size(); ++k) { sum += b[k]; sq += (double) b[k] * b[k]; if (std::abs((int) b[k]) >= 32767) ++clip; if (k && (b[k-1] < 0) != (b[k] < 0)) ++zc; }
            const double n = b.empty() ? 1 : (double) b.size(), rms = std::sqrt(sq / n), dc = sum / n;
            const bool sus = rms < 1 || std::fabs(dc) > 0.5 * rms + 200 || clip > n * 0.05;
            auto& t = perType[p.modules[(size_t) i - 1].type]; t.first++; if (sus) t.second++;
            if (sus) std::printf("  %-28s %2d %-20s rms %7.0f dc %7.0f clip %5.1f%% zc/s %6.0f\n", std::filesystem::u8path(argv[a]).filename().u8string().c_str(), i, moduleTypeName(p.modules[(size_t) i - 1].type), rms, dc, 100.0 * clip / n, zc * (double) p.modules[(size_t) i - 1].sampleRate / n);
        }
    }
    for (auto& [t, c] : perType) std::printf("%-22s %3d modules, %d flagged\n", moduleTypeName(t), c.first, c.second);
}
