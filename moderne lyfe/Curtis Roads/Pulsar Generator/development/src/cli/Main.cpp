// pulsargen-cli: render a table set offline, for testing and scripting.
//
//   pulsargen-cli in.set out.wav [seconds=10] [seed=1] [rate=44100]
//
// The set's own play-control settings are used.

#include "PulsarEngine.h"

#include <cmath>
#include <cstdint>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <string>
#include <vector>

using namespace pulsar;

namespace {

void put16(FILE* f, uint16_t v) { std::fputc(v & 0xFF, f); std::fputc(v >> 8, f); }
void put32(FILE* f, uint32_t v) { put16(f, (uint16_t) (v & 0xFFFF)); put16(f, (uint16_t) (v >> 16)); }

bool writeWav(const char* path, const std::vector<float>& l, const std::vector<float>& r, uint32_t rate)
{
    FILE* f = std::fopen(path, "wb");
    if (!f) return false;
    const uint32_t bytes = (uint32_t) l.size() * 4;
    std::fwrite("RIFF", 1, 4, f); put32(f, 36 + bytes);
    std::fwrite("WAVEfmt ", 1, 8, f); put32(f, 16);
    put16(f, 1); put16(f, 2); put32(f, rate); put32(f, rate * 4); put16(f, 4); put16(f, 16);
    std::fwrite("data", 1, 4, f); put32(f, bytes);
    for (size_t i = 0; i < l.size(); ++i)
        for (float s : { l[i], r[i] })
            put16(f, (uint16_t) (int16_t) std::lround(std::fmax(-1.0f, std::fmin(1.0f, s)) * 32767.0f));
    std::fclose(f);
    return true;
}

} // namespace

int main(int argc, char** argv)
{
    if (argc < 3) {
        std::fprintf(stderr, "usage: pulsargen-cli in.set out.wav [seconds=10] [seed=1] [rate=44100]\n");
        return 1;
    }
    double seconds = 10.0, rate = 44100.0;
    uint32_t seed = 1;
    for (int i = 3; i < argc; ++i) {
        const char* a = argv[i];
        if (!std::strncmp(a, "seconds=", 8)) seconds = std::atof(a + 8);
        else if (!std::strncmp(a, "seed=", 5)) seed = (uint32_t) std::strtoul(a + 5, nullptr, 10);
        else if (!std::strncmp(a, "rate=", 5)) rate = std::atof(a + 5);
        else { std::fprintf(stderr, "unknown argument: %s\n", a); return 1; }
    }

    TableSet set;
    std::string err;
    if (!loadSet(argv[1], set, &err)) { std::fprintf(stderr, "%s\n", err.c_str()); return 1; }

    Engine engine;
    engine.prepare(rate);
    engine.setSeed(seed);
    engine.setTables(TableData::from(set));
    engine.setControls(Controls::fromSettings(set.settings, Controls{}));
    engine.start();

    const size_t frames = (size_t) (seconds * rate);
    std::vector<float> l(frames), r(frames);
    size_t done = 0;
    while (done < frames && engine.isRunning()) {
        const int n = (int) std::min<size_t>(512, frames - done);
        engine.process(l.data() + done, r.data() + done, n);
        done += (size_t) n;
    }
    l.resize(done);
    r.resize(done);

    float peak = 0.0f;
    double sum = 0.0;
    for (size_t i = 0; i < done; ++i) {
        peak = std::fmax(peak, std::fmax(std::fabs(l[i]), std::fabs(r[i])));
        sum += (double) l[i] * l[i] + (double) r[i] * r[i];
    }
    if (!writeWav(argv[2], l, r, (uint32_t) rate)) { std::fprintf(stderr, "cannot write %s\n", argv[2]); return 1; }
    std::printf("%s: %.2f s, %lld pulsars, peak %.3f, rms %.4f%s\n", set.name.c_str(), done / rate,
                engine.pulsarCount(), peak, std::sqrt(sum / (2.0 * std::max<size_t>(1, done))),
                done < frames ? " (stopped at end of tables)" : "");
    return 0;
}
