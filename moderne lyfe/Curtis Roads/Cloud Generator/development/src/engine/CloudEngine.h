// Cloud Generator synthesis engine.
//
// Reimplementation of the granular engine in Cloud Generator Model 1
// (C. Roads and J. Alexander, 1996), reconstructed from a disassembly of the
// 68K application (CODE 3 "RA.aiff", CODE 4 "sine table", CODE 5 "gauss
// envelope", CODE 2 "RA.main"). See docs/ALGORITHM.md for the derivation.
//
// The original evaluated everything with SANE 80-bit arithmetic and stored
// most intermediates as 32-bit floats. This port keeps every float store,
// truncation and 16-bit conversion of the original, but computes the 80-bit
// intermediates in 64-bit double so results are identical on every platform.
//
// Model 2 is the same engine with the original's quirks corrected. Each
// correction is a separate switch in Fixes so their effects can be compared
// one at a time; with every switch off the output is Model 1, bit for bit.
// docs/MODEL2.md describes each fix.
#pragma once

#include <array>
#include <cstdint>
#include <functional>
#include <string>
#include <vector>

namespace cloudgen {

constexpr double kSampleRate = 44100.0; // fixed in the original
constexpr int kTableSize = 512;

enum class CloudType { Synchronous, Asynchronous };
enum class SourceKind { Synthetic, Granulated };
enum class SelectionOrder { Random, StatisticalEvolution, DeterministicProgression };

struct Fixes {
    // Clear bugs.
    bool interpolateOverDuration = false; // final values reached even when start time > 0
    bool coherentPanning = false;         // one Random box no longer ignores the other setting
    bool grainDurationRange = false;      // random duration spans Minimum..Maximum, per grain
    bool wavetableWrap = false;           // oscillator wraps to entry 0; all 512 entries used
    bool oscillatorNoWrap = false;        // no 16-bit wraparound between table entries
    bool sawtoothFirstPoint = false;      // saw starts on its ramp, not at 16383.5
    bool safeGranulation = false;         // frame-aligned grains that stay inside the source
    bool resampleSource = false;          // sources at other sample rates play at the right pitch
    bool dropUnusedRandom = false;        // no wasted random draw per grain

    // Signal quality.
    bool floatMix = false;       // mix in floating point, clip once at the end (no -32768 wrap)
    bool smoothEnvelope = false; // continuous Gaussian instead of 512 steps
    bool keepCloudTail = false;  // grains near the end ring out instead of being cut or dropped

    // Sound-defining choices (not part of the Model 2 preset).
    bool fullScaleEnvelope = false; // Gaussian peaks at full scale instead of ~0.80
    bool logFrequency = false;      // frequencies spread evenly in pitch rather than in Hz

    static Fixes model1() { return {}; }
    static Fixes model2()
    {
        Fixes f;
        f.interpolateOverDuration = f.coherentPanning = f.grainDurationRange = f.wavetableWrap
            = f.oscillatorNoWrap = f.sawtoothFirstPoint = f.safeGranulation = f.resampleSource
            = f.dropUnusedRandom = f.floatMix = f.smoothEnvelope = f.keepCloudTail = true;
        return f;
    }
};

// Console stereo slider: 0 = hard left, 100 = hard right (displayed -100..100).
struct StereoLocation {
    int position = 50;
    bool random = false;
};

// Values exactly as entered on the console.
struct CloudParams {
    float startTime = 0.0f;       // sec
    float duration = 10.0f;       // sec
    float densityInitial = 5.0f;  // grains/sec
    float densityFinal = 99.0f;
    float amplitudeInitial = 90.0f; // percent
    float amplitudeFinal = 99.0f;
    float highInitial = 30.0f;    // Hz
    float highFinal = 6400.0f;
    float lowInitial = 4000.0f;
    float lowFinal = 20.0f;
    CloudType type = CloudType::Synchronous;
    StereoLocation stereoInitial{ 1, false };
    StereoLocation stereoFinal{ 50, true };
    // Grain duration fields. With randomGrainDuration the original relabels
    // the first field "Maximum" and the second "Minimum" and draws
    // duration = Minimum + r * Maximum for every grain.
    float grainDurationInitial = 0.001f; // sec
    float grainDurationFinal = 0.001f;
    bool randomGrainDuration = false;
    SelectionOrder selectionOrder = SelectionOrder::Random;
    SourceKind source = SourceKind::Synthetic;
    uint16_t seed = 1; // srand() argument; the original used the clock

    Fixes fixes;                    // all off = Model 1
    double sampleRate = kSampleRate; // output rate; anything but 44100 is a Model 2 feature
};

// The waveform editor's 512-point float buffer (amplitude +/-32767).
struct Waveform {
    std::array<float, kTableSize> points{};
};

// Stereo 16-bit input for granulated clouds, interleaved L R L R.
struct SourceSound {
    std::vector<int16_t> interleaved;
    double sampleRate = kSampleRate; // ignored by Model 1, as in the original
};

// One grain: drives the plot and the "Text" output.
struct GrainEvent {
    float onset;     // seconds
    float duration;  // seconds
    float amplitude; // 0..1
    float frequency; // Hz (0 for granulated clouds, as in the original)
    float pan;       // 0 = left, 1 = right
};

struct RenderResult {
    std::vector<int16_t> interleaved; // stereo at p.sampleRate, includes start-time silence
    // With Fixes::floatMix: the unclipped mix, interleaved, 1.0 = 32768.
    // interleaved is then this mix rounded and clipped to 16 bits.
    std::vector<float> mixed;
    double sampleRate = kSampleRate;
    std::vector<GrainEvent> grains;
    bool stopped = false;
};

// Returns an empty string if the parameters are valid, else the original
// program's error message.
std::string validate(const CloudParams& p, const SourceSound* source);

// Called once per grain with progress 0..1; return false to stop.
using ProgressFn = std::function<bool(double, const GrainEvent&)>;

// renderAudio=false is the original "Text" output mode: grains are scheduled
// (consuming the same random numbers) but no audio is mixed.
RenderResult render(const CloudParams& p,
                    const Waveform& wave,
                    const SourceSound* source = nullptr,
                    bool renderAudio = true,
                    const ProgressFn& progress = {});

// The original "Text" output: one line per grain, consecutive duplicate
// onsets removed, in the original's printf format.
std::string grainsAsText(const std::vector<GrainEvent>& grains);

} // namespace cloudgen
