// Table editing: the Alter and Fill menus of the original table windows.
//
// In SuperCollider 2 these were Signal/Wavetable primitives; the formulas
// here follow their documented behaviour (see Documentation/Alter_Menu and
// Fill_Menu in the original distribution).
#pragma once

#include <random>
#include <vector>

namespace pulsar::ops {

using Table = std::vector<float>;

// Alter menu
void zero(Table&);
void normalize(Table&);                 // peak to +/-1
void reverse(Table&);
void invert(Table&);
void absolute(Table&);
void square(Table&);
void cube(Table&);
void distort(Table&);                   // x / (1 + |x|), renormalized
void fadeIn(Table&);
void fadeOut(Table&);
void clipNegative(Table&);
void smooth(Table&);                    // 3-point moving average, wrapping
void scale(Table&, float factor);
void scaleTo(Table&, float lo, float hi);
void hanningWindow(Table&);
void parzenWindow(Table&);
void welchWindow(Table&);
// Comb Table: even comb notches harmonic 0 and multiples of spacing; odd comb
// notches odd multiples of spacing / 2.
void comb(Table&, float spacing, bool odd);

// Fill menu
void fillConstant(Table&, float value);
void fillLine(Table&, float from, float to);
// Sum of sine harmonics: amplitude and phase (radians) of harmonic i+1.
void fourierFill(Table&, const std::vector<float>& amps, const std::vector<float>& phases);
// Chebyshev transfer function: sum of amps[i] * T(i+1)(x) over x in -1..1.
void chebyshevFill(Table&, const std::vector<float>& amps);
// Phase modulation: sin(2pi c t + index * sin(2pi m t)).
void phaseModFill(Table&, float carrier, float modulator, float index);
// Band-limited pulse: numHarmonics harmonics from lowHarmonic, each scaled by
// coefficient^k.
void buzzFill(Table&, int lowHarmonic, int numHarmonics, float coefficient);
enum class GrainShape { Hanning, Welch, Parzen };
void grainEnvelopeFill(Table&, GrainShape);
void noiseFill(Table&, std::mt19937&, bool binary);

} // namespace pulsar::ops
