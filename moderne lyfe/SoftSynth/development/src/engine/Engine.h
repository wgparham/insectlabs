// Softsynth synthesis: renders a patch the way Softsynth 2.1's F00C28E does.
// docs/RE-NOTES.md has the details; tests compare it with the original's 68K code.
#pragma once

#include "Patch.h"

#include <cstdint>
#include <string>
#include <vector>

namespace softsynth {

// XBIOS Random. The original seeds it from the machine, so its renders differ
// each time. Here the seed is explicit.
class XbiosRandom {
public:
    explicit XbiosRandom(uint32_t seed) : state(seed) {}
    static XbiosRandom alwaysZero() { XbiosRandom r(0); r.zero = true; return r; }
    uint32_t next()
    {
        if (zero) return 0;
        state = state * 3141592621u + 1u;
        return (state >> 8) & 0xFFFFFFu;
    }

private:
    uint32_t state;
    bool zero = false;
};

struct RenderOptions {
    bool normalize = true;  // Options > Normalize during synthesis
    // The size of the original's sample buffer, which was its free memory. Sounds
    // longer than this render in pieces, and the original's FM kernel slips one
    // sample at each join. 0 renders in one continuous pass.
    int32_t bufferSamples = 0;
};

struct RenderResult {
    std::vector<int16_t> samples;
    std::vector<std::string> warnings;
};

class Engine {
public:
    // Fills the 8-bit noise table from `random`, as the program does at startup.
    explicit Engine(XbiosRandom random);

    // Writes the calculated break points into `patch`, as the original does, so a
    // saved patch keeps them. False if a partial is above half the sample rate.
    bool render(Patch& patch, const RenderOptions& options, RenderResult& result, std::string* error);

private:
    XbiosRandom rng;
    std::vector<uint8_t> noiseBytes;
};

// The file F00C28E writes: a 0x538-byte Sound Designer I header, then big-endian samples.
std::vector<uint8_t> soundDesignerFile(const Patch& patch, const std::vector<int16_t>& samples);

} // namespace softsynth
