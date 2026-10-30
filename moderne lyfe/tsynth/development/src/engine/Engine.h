// TurboSynth synthesis: computes a patch the way Turbosynth SC 2.22's host
// (68K) code does, module by module, in 16-bit fixed point. The Diffuser
// follows the DSP card program (SACD 110). docs/RE-NOTES.md has the details.
#pragma once

#include "Patch.h"

#include <functional>
#include <string>
#include <vector>

namespace turbosynth {

struct RenderResult {
    std::vector<int16_t> left, right; // right empty for mono
    int32_t sampleRate = 44100;
    std::vector<std::string> warnings;
};

struct RenderOptions {
    // Stereo Preferences (F23_02DDC / F23_02F6E): pad the shorter channel (or
    // truncate the longer), and which rate a stereo sound plays at when the
    // two sides differ: 15 highest, 16 lowest, 17 the left's, 18 the right's.
    bool padStereo = true;
    int stereoRate = 15;
};

class Engine {
public:
    explicit Engine(const Patch& p, RenderOptions o = {});
    RenderResult render();
    // Output of one module (1-based), computed on demand.
    const std::vector<int16_t>& moduleOutput(int index);

private:
    void compute(int index);
    std::vector<int> inputsOf(int index) const;
    const std::vector<int16_t>& inputBuffer(int index, int which, int32_t& length); // which: 1-based position

    void oscillator(Module& m, std::vector<int16_t>& out);
    void sample(Module& m, std::vector<int16_t>& out);
    void ampEnv(Module& m, std::vector<int16_t>& out, const std::vector<int16_t>& in, int32_t inLen);
    void filter(Module& m, std::vector<int16_t>& out, const std::vector<int16_t>& in, int32_t inLen);
    void spectralInverter(Module& m, std::vector<int16_t>& out, const std::vector<int16_t>& in, int32_t inLen);
    void pitchEnv(Module& m, std::vector<int16_t>& out, const std::vector<int16_t>& in, int32_t inLen);
    void waveshaper(Module& m, std::vector<int16_t>& out, const std::vector<int16_t>& in, int32_t inLen);
    void modulator(int index, Module& m, std::vector<int16_t>& out);
    void mixer(int index, Module& m, std::vector<int16_t>& out);
    void delayLike(Module& m, std::vector<int16_t>& out, const std::vector<int16_t>& in, int32_t inLen, bool resonator);
    void stretch(Module& m, std::vector<int16_t>& out, const std::vector<int16_t>& in, int32_t inLen, bool expand);
    void pitchShifter(Module& m, std::vector<int16_t>& out, const std::vector<int16_t>& in, int32_t inLen);
    void noise(Module& m, std::vector<int16_t>& out);
    void diffuser(Module& m, std::vector<int16_t>& out, const std::vector<int16_t>& in, int32_t inLen);

    Patch patch;
    RenderOptions options;
    std::vector<std::vector<int16_t>> buffers;
    std::vector<bool> done, busy;
    std::vector<std::string> warnings;
    std::vector<int16_t> empty;
};

// Crossfade looping (Output and Sample modules): blend length samples around
// the loop start and end so the loop joins smoothly. False if it does not fit.
bool crossfadeLoop(std::vector<int16_t>& buf, int32_t length, int32_t loopStart, int32_t loopEnd, bool linear);

// A stereo sound saved to a mono file (F27_01BCC): each sample is
// q15(g·L + g·R), g = 0x3FFF with Stereo Preferences "Scale" or 0x7FFF with
// "Don't scale" (which can wrap). The shorter channel counts as silence.
std::vector<int16_t> foldToMono(const std::vector<int16_t>& left, const std::vector<int16_t>& right, bool scale);

// The TSWT "Sine Wave" resource used by the Noise module.
extern const int16_t kSineTable[256];

} // namespace turbosynth
