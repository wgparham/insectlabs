// The waveform tables stored in the original program.
#pragma once

#include <cstdint>

namespace softsynth {

extern const int16_t kSine[4096];     // waveform 1
extern const int16_t kSquare[4096];   // waveform 2, odd harmonics at 1/k
extern const int16_t kTriangle[4096]; // waveform 3, odd harmonics at 1/k^2
extern const int16_t kNoise[16384];   // waveform 4, read by sample number

} // namespace softsynth
