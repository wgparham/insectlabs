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
};

// The waveform editor's 512-point float buffer (amplitude +/-32767).
struct Waveform {
    std::array<float, kTableSize> points{};
};

// Stereo 16-bit input for granulated clouds, interleaved L R L R.
struct SourceSound {
    std::vector<int16_t> interleaved;
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
    std::vector<int16_t> interleaved; // stereo, 44.1 kHz, includes start-time silence
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
