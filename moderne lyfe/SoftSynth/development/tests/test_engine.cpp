// Compares the engine's whole output files with the renders of the original's
// 68K code in tests/reference (see tools/re/check_reference.py).
//
// test_engine TBWERD1.PRM tests/reference
#include "Engine.h"

#include <cstdio>
#include <string>
#include <vector>

using namespace softsynth;

static std::vector<uint8_t> readFile(const std::string& path)
{
    std::vector<uint8_t> d;
    if (FILE* f = std::fopen(path.c_str(), "rb")) {
        int c;
        while ((c = std::fgetc(f)) != EOF) d.push_back((uint8_t) c);
        std::fclose(f);
    }
    return d;
}

struct Case {
    std::string file;
    bool normalize = false, zero = false, plain = false;
    int wave = 0;
    int32_t buffer = 0;
};

int main(int argc, char** argv)
{
    if (argc < 3) return 2;
    const std::string refs = std::string(argv[2]) + "/";
    std::vector<Case> cases = {
        { "tbwerd1-seed1.sd", true },
        { "tbwerd1-seed1-raw.sd" },
        { "tbwerd1-zero-raw.sd", false, true },
        { "tbwerd1-buffer2048-raw.sd", false, false, false, 0, 2048 },
    };
    for (int w = 1; w <= 5; ++w) {
        cases.push_back({ "tbwerd1-wave" + std::to_string(w) + "-fm-raw.sd", false, false, false, w });
        cases.push_back({ "tbwerd1-wave" + std::to_string(w) + "-plain-raw.sd", false, false, true, w });
    }

    int failed = 0;
    for (const Case& c : cases) {
        Patch patch;
        std::string error;
        if (!loadPatch(argv[1], patch, &error)) {
            std::printf("%s\n", error.c_str());
            return 1;
        }
        for (int i = 0; i < kPartials; ++i) {
            if (c.plain) {
                patch.routing[(size_t) i * 2] = 1;
                patch.routing[(size_t) i * 2 + 1] = 0;
            }
            if (c.wave != 0 && (i == 4 || i == 7))
                patch.partial(i)[kWave] = (uint8_t) c.wave;
        }
        Engine engine(c.zero ? XbiosRandom::alwaysZero() : XbiosRandom(1));
        RenderOptions options;
        options.normalize = c.normalize;
        options.bufferSamples = c.buffer;
        RenderResult result;
        if (!engine.render(patch, options, result, &error)) {
            std::printf("FAIL  %s: %s\n", c.file.c_str(), error.c_str());
            ++failed;
            continue;
        }
        const auto mine = soundDesignerFile(patch, result.samples);
        const auto want = readFile(refs + c.file);
        size_t first = 0, differ = 0;
        const size_t n = std::min(mine.size(), want.size());
        for (size_t i = n; i-- > 0;)
            if (mine[i] != want[i]) { first = i; ++differ; }
        if (want.empty() || mine.size() != want.size() || differ != 0) {
            std::printf("FAIL  %s: sizes %zu, %zu; %zu bytes differ, first at %#zx\n", c.file.c_str(), mine.size(), want.size(), differ, first);
            ++failed;
        } else {
            std::printf("PASS  %s\n", c.file.c_str());
        }
    }
    std::printf("%zu of %zu files identical\n", cases.size() - (size_t) failed, cases.size());
    return failed ? 1 : 0;
}
