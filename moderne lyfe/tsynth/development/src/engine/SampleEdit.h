// Sample and Output window processing, recovered from the Sample module's
// window code (CODE 28) and the shared routines it calls (CODE 6, 29).
// docs/RE-NOTES.md has the details. Loop points are sample indices, -1 = none.
#pragma once

#include <cstdint>
#include <vector>

namespace turbosynth::sampleedit {

// Sample Rate Conversion (F28_01870 with kernel F28_0594A): linear
// interpolation in place; the loop points are scaled with the sound.
void convertRate(std::vector<int16_t>& data, int32_t oldRate, int32_t newRate, int32_t& loopStart, int32_t& loopEnd);

// Gain Scaling (F28_01380 / F28_059C6): percent of the original (1..2000), clipped.
void scaleGain(std::vector<int16_t>& data, int percent);
// Gain Scaling's Normalize: the module normalize (F29_001BA), peak to full scale.
void normalize(std::vector<int16_t>& data);

// Envelope Removal (the routine after F28_01B6E): blocks of responseMs; each
// block's peak is brought to 30000 with a gain ramped from peak to peak.
void removeEnvelope(std::vector<int16_t>& data, int32_t rate, int responseMs);

// Extending the Loop (F28_01FF0): the sound becomes length samples, and from
// the loop end on copies of the loop replace what was there.
void extendLoop(std::vector<int16_t>& data, int32_t length, int32_t loopStart, int32_t loopEnd);

// Edit menu on a selection [from, to).
void reverse(std::vector<int16_t>& data, int32_t from, int32_t to); // F28_05A28
void silence(std::vector<int16_t>& data, int32_t from, int32_t to);

// Save loop as waveform (F25_002B6): the loop resampled to the oscillator's
// 256 points with its resampler (F39_02870), then normalized.
std::vector<int16_t> loopAsWaveform(const std::vector<int16_t>& data, int32_t loopStart, int32_t loopEnd);

// The Loop window's Auto arrows (CODE 35 with F6_00DDA): move the loop end
// (moveEnd) or start in direction (+1/-1) to the best match, over the whole
// range up to the other marker or the end of the sound, for the three samples
// around the other loop point. Returns the point unchanged if the arrow is off.
int32_t autoLoopPoint(const std::vector<int16_t>& data, int32_t loopStart, int32_t loopEnd, bool moveEnd, int direction);

} // namespace turbosynth::sampleedit
