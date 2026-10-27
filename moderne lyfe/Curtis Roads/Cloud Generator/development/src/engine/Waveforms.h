#pragma once

#include "CloudEngine.h"

namespace cloudgen {

// The waveform choices of the "synth waveform select" dialog, built exactly
// as RA.main fills the editor buffer.
Waveform makeSine();
Waveform makeSquare();
Waveform makeSawtooth();

// Import...: the first 512 left-channel samples of the sound.
Waveform importWaveform(const SourceSound& sound);

// The 513-entry int16 Gaussian grain envelope ("gauss envelope" segment).
const std::array<int16_t, kTableSize + 1>& gaussTable();

// The oscillator's int16 table ("sine table" segment). Entry 511 is never
// written by the original and stays 0; the interpolating oscillator reads one
// past the end, where the Gaussian table begins, so entry 512 is gauss[0].
std::array<int16_t, kTableSize + 1> oscillatorTable(const Waveform& wave);

} // namespace cloudgen
