// Compares SmartSynth and the time slice conversions with hashes of what the original's
// 68K code did from the same starting points (refrender --edit-golden writes them).
// Copy, clear, FM patching and the break point routines are compared with the 68K code
// directly by refrender --edit-test, which needs Musashi.
//
// test_edit TBWERD1.PRM tests/reference/edits.txt
#include "Edit.h"

#include <cinttypes>
#include <cstdio>
#include <cstring>
#include <string>

using namespace softsynth;

static uint64_t hashState(const Patch& p, const SliceList& h)
{
    uint64_t v = 0xCBF29CE484222325ull;
    const auto add = [&](uint8_t b) { v = (v ^ b) * 0x100000001B3ull; };
    for (uint8_t b : p.partials) add(b);
    for (uint8_t b : p.routing) add(b);
    const auto addWord = [&](int16_t w) { add((uint8_t) ((uint16_t) w >> 8)); add((uint8_t) w); };
    addWord(h.count);
    for (size_t i = 0; i < 42; ++i) {
        addWord(h.y[i]);
        addWord(h.x[i]);
    }
    return v;
}

int main(int argc, char** argv)
{
    if (argc < 3) return 2;
    Patch tb;
    std::string error;
    if (!loadPatch(argv[1], tb, &error)) {
        std::printf("%s\n", error.c_str());
        return 1;
    }
    FILE* f = std::fopen(argv[2], "r");
    if (!f) {
        std::printf("no references\n");
        return 1;
    }

    char line[512];
    int cases = 0, failed = 0;
    while (std::fgets(line, sizeof line, f)) {
        if (line[0] == '#') continue;
        int base, v[12];
        unsigned seed;
        uint64_t want[3];
        if (std::sscanf(line, "%d %d %d %d %d %d %d %d %d %d %d %d %d %u %" SCNx64 " %" SCNx64 " %" SCNx64,
                        &base, &v[0], &v[1], &v[2], &v[3], &v[4], &v[5], &v[6], &v[7], &v[8], &v[9], &v[10], &v[11],
                        &seed, &want[0], &want[1], &want[2]) != 17) {
            std::printf("bad line: %s", line);
            ++failed;
            continue;
        }
        SmartSynthSettings s;
        s.harmonicSeries = v[0]; s.partialDetuning = v[1]; s.attackRate = v[2]; s.decayRate = v[3];
        s.harmonicRange = v[4]; s.doubling = v[5]; s.frequencyAttack = v[6]; s.secondaryLevel = v[7];
        s.harmonicFilter = v[8]; s.frequencyMovement = v[9]; s.percussion = v[10]; s.fade = v[11];

        Patch p = tb;
        SliceList h;
        if (base == 1)
            newPatch(p, Sampler::AkaiS900);
        XbiosRandom random(seed);
        smartSynthGenerate(p, s, random);
        const uint64_t got1 = hashState(p, h);
        partialsToSlices(p, h);
        const uint64_t got2 = hashState(p, h);
        slicesToPartials(p, h);
        const uint64_t got3 = hashState(p, h);
        ++cases;
        if (got1 != want[0] || got2 != want[1] || got3 != want[2]) {
            std::printf("FAIL  %s", line);
            ++failed;
        }
    }
    std::fclose(f);
    std::printf("%d of %d cases identical\n", cases - failed, cases);
    return failed != 0 || cases == 0;
}
