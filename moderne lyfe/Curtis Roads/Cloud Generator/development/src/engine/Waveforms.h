#pragma once

#include "CloudEngine.h"

namespace cloudgen {

// The waveform choices of the "synth waveform select" dialog, built exactly
// as RA.main fills the editor buffer.
Waveform makeSine();
Waveform makeSquare();
// correctFirstPoint (Model 2): W[0] on the ramp (0) instead of 16383.5.
Waveform makeSawtooth(bool correctFirstPoint = false);

// Import...: the first 512 left-channel samples of the sound.
Waveform importWaveform(const SourceSound& sound);

// The 513-entry int16 Gaussian grain envelope ("gauss envelope" segment).
// fullScale (Model 2 option): peak 32767 instead of 32758.
const std::array<int16_t, kTableSize + 1>& gaussTable(bool fullScale = false);

// Continuous Gaussian envelope at grain position 0..1, in the table's units
// (Fixes::smoothEnvelope).
double gaussEnvelope(double position, bool fullScale);

// The oscillator's int16 table ("sine table" segment). Entry 511 is never
// written by the original and stays 0; the interpolating oscillator reads one
// past the end, where the Gaussian table begins, so entry 512 is gauss[0].
// wrap (Fixes::wavetableWrap): all 512 entries copied and entry 512 = entry 0.
std::array<int16_t, kTableSize + 1> oscillatorTable(const Waveform& wave, bool wrap = false);

} // namespace cloudgen
