// The editors' preset and modifier tools, recovered from the module windows'
// 68K code: waveshaper (CODE 41), waveform (CODE 38) and envelope (CODE 40).
// docs/RE-NOTES.md has the details.
#pragma once

#include "MacMath.h"
#include "Patch.h"

#include <array>
#include <cstdint>

namespace turbosynth::presets {

constexpr int kShaperPresets = 8, kShaperModifiers = 10;
constexpr int kWavePresets = 11, kWaveModifiers = 18;
constexpr int kEnvPresets = 8, kEnvModifiers = 6;

// Names in palette order (the order of the tool codes).
const char* shaperPresetName(int n);
const char* shaperModifierName(int n);
const char* wavePresetName(int n);
const char* waveModifierName(int n);
const char* envPresetName(int n);
const char* envModifierName(int n);

// Waveshaper transfer function: entries 0..255 (the guard entry is left alone).
// The random preset uses the Toolbox Random and TickCount, like the original.
void shaperPreset(int16_t* table, int n, mac::Random& rng, uint32_t ticks);
void shaperModifier(int16_t* table, int n, mac::Random& rng);
// The waveshaper eraser: the straight line that leaves the input unchanged.
inline int16_t shaperIdentity(int i) { return (int16_t) ((i - 128) << 8); }

// Oscillator waveforms: the presets are harmonic recipes summed with the
// TSWT sine table; the modifiers work on the 256 samples.
void wavePreset(Waveform& w, int n);
void waveModifier(Waveform& w, int n, mac::Random& rng);

// Harmonic mode. A harmonic is a level and a phase (0..255 of a cycle);
// index 0 is the DC term, 1..64 the harmonics the window shows.
struct Harmonic { int16_t level = 0, phase = 0; };
using Harmonics = std::array<Harmonic, 65>;
constexpr int kHarmonicModifiers = 15;
const char* harmonicModifierName(int n);
// The recipe behind waveform preset n (false for the disk tool).
bool harmonicPreset(Harmonics& h, int n);
// Harmonics to waveform (normalized only if the sum overflows) and back.
void synthesize(const Harmonics& h, Waveform& w);
Harmonics analyze(const Waveform& w);
void harmonicModifier(Harmonics& h, int n, mac::Random& rng);

// Breakpoint envelopes (Amplitude, Filter, Spectral Inverter, Pitch).
void envPreset(Module& m, int n);
void envModifier(Module& m, int n);
// The handle rectangle the original keeps with each breakpoint.
void setPointRect(EnvPoint& p);

} // namespace turbosynth::presets
