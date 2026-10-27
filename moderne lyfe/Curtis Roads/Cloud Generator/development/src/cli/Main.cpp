// cloudgen-cli: render a cloud without the GUI.
// Used for scripting and for comparing output against the original program.
//
//   cloudgen-cli out.wav [key=value ...]
//   cloudgen-cli out.txt text [key=value ...]
//
// Keys (console values): start dur dens0 dens1 amp0 amp1 hi0 hi1 lo0 lo1
//   grain0 grain1 graindur=random pan0 pan1 (0..100 or "random")
//   type=sync|async wave=sine|square|saw seed=0..65535

#include "CloudEngine.h"
#include "Waveforms.h"

#include <cstdint>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <string>

using namespace cloudgen;

namespace {

void put16(FILE* f, uint16_t v) { std::fputc(v & 0xFF, f); std::fputc(v >> 8, f); }
void put32(FILE* f, uint32_t v) { put16(f, (uint16_t) (v & 0xFFFF)); put16(f, (uint16_t) (v >> 16)); }

bool writeWav16(const char* path, const std::vector<int16_t>& interleaved)
{
    FILE* f = std::fopen(path, "wb");
    if (!f) return false;
    const uint32_t dataBytes = (uint32_t) interleaved.size() * 2;
    std::fwrite("RIFF", 1, 4, f); put32(f, 36 + dataBytes);
    std::fwrite("WAVEfmt ", 1, 8, f); put32(f, 16);
    put16(f, 1); put16(f, 2); put32(f, 44100); put32(f, 44100 * 4);
    put16(f, 4); put16(f, 16);
    std::fwrite("data", 1, 4, f); put32(f, dataBytes);
    for (int16_t s : interleaved)
        put16(f, (uint16_t) s);
    std::fclose(f);
    return true;
}

void setPan(StereoLocation& loc, const char* v)
{
    if (std::strcmp(v, "random") == 0) loc.random = true;
    else { loc.random = false; loc.position = std::atoi(v); }
}

} // namespace

int main(int argc, char** argv)
{
    if (argc < 2) {
        std::fprintf(stderr, "usage: cloudgen-cli out.wav [text] [key=value ...]\n");
        return 1;
    }
    CloudParams p;
    Waveform wave = makeSine();
    bool text = false;

    for (int i = 2; i < argc; ++i) {
        std::string a = argv[i];
        if (a == "text") { text = true; continue; }
        const size_t eq = a.find('=');
        if (eq == std::string::npos) { std::fprintf(stderr, "bad argument: %s\n", argv[i]); return 1; }
        const std::string k = a.substr(0, eq);
        const char* v = argv[i] + eq + 1;
        const float d = std::strtof(v, nullptr);
        if (k == "start") p.startTime = d;
        else if (k == "dur") p.duration = d;
        else if (k == "dens0") p.densityInitial = d;
        else if (k == "dens1") p.densityFinal = d;
        else if (k == "amp0") p.amplitudeInitial = d;
        else if (k == "amp1") p.amplitudeFinal = d;
        else if (k == "hi0") p.highInitial = d;
        else if (k == "hi1") p.highFinal = d;
        else if (k == "lo0") p.lowInitial = d;
        else if (k == "lo1") p.lowFinal = d;
        else if (k == "grain0") p.grainDurationInitial = d;
        else if (k == "grain1") p.grainDurationFinal = d;
        else if (k == "graindur") p.randomGrainDuration = std::strcmp(v, "random") == 0;
        else if (k == "pan0") setPan(p.stereoInitial, v);
        else if (k == "pan1") setPan(p.stereoFinal, v);
        else if (k == "type") p.type = std::strcmp(v, "async") == 0 ? CloudType::Asynchronous : CloudType::Synchronous;
        else if (k == "wave") wave = std::strcmp(v, "square") == 0 ? makeSquare() : std::strcmp(v, "saw") == 0 ? makeSawtooth() : makeSine();
        else if (k == "seed") p.seed = (uint16_t) std::strtoul(v, nullptr, 10);
        else { std::fprintf(stderr, "unknown key: %s\n", k.c_str()); return 1; }
    }

    const std::string err = validate(p, nullptr);
    if (!err.empty()) { std::fprintf(stderr, "%s\n", err.c_str()); return 1; }

    const RenderResult r = render(p, wave, nullptr, !text);
    if (text) {
        FILE* f = std::fopen(argv[1], "w");
        if (!f) return 1;
        std::fputs(grainsAsText(r.grains).c_str(), f);
        std::fclose(f);
    } else if (!writeWav16(argv[1], r.interleaved)) {
        std::fprintf(stderr, "cannot write %s\n", argv[1]);
        return 1;
    }
    std::printf("%zu grains, %.2f s\n", r.grains.size(), (double) r.interleaved.size() / 2.0 / kSampleRate);
    return 0;
}
