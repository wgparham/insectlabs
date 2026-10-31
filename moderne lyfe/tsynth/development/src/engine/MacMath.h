// Classic Mac Toolbox fixed-point routines and 68000 arithmetic helpers,
// as TurboSynth's code uses them. docs/RE-NOTES.md lists which behaviours
// are verified and which are assumptions.
#pragma once

#include <cstdint>

namespace turbosynth::mac {

using Fixed = int32_t;

inline int32_t roundDiv(int64_t n, int64_t d)
{
    if (d == 0) return n >= 0 ? 0x7FFFFFFF : (int32_t) 0x80000000;
    const bool neg = (n < 0) != (d < 0);
    const int64_t an = n < 0 ? -n : n, ad = d < 0 ? -d : d;
    int64_t q = (an + ad / 2) / ad;
    if (neg) q = -q;
    if (q > 0x7FFFFFFF) return 0x7FFFFFFF;
    if (q < -0x7FFFFFFF - 1) return (int32_t) 0x80000000;
    return (int32_t) q;
}
inline int32_t truncDiv(int64_t n, int64_t d)
{
    if (d == 0) return n >= 0 ? 0x7FFFFFFF : (int32_t) 0x80000000;
    const int64_t q = n / d; // C division truncates toward zero
    if (q > 0x7FFFFFFF) return 0x7FFFFFFF;
    if (q < -0x7FFFFFFF - 1) return (int32_t) 0x80000000;
    return (int32_t) q;
}

// FixRatio truncates: verified in the emulator (Quadra 650 ROM, System 7.5.3),
// FixRatio(4096, 22050) = 12173, not 12174 (oscillator phase increments).
inline Fixed fixRatio(int16_t n, int16_t d) { return truncDiv((int64_t) n << 16, d); }
inline Fixed fixMul(Fixed a, Fixed b)
{
    const int64_t p = (int64_t) a * b;
    return (int32_t) (p >= 0 ? (p + 0x8000) >> 16 : -((-p + 0x8000) >> 16));
}
// FixDiv rounds: verified in the emulator (truncating breaks the filter and stretcher renders).
inline Fixed fixDiv(Fixed a, Fixed b) { return roundDiv((int64_t) a << 16, b); }
inline int16_t fixRound(Fixed f) { return (int16_t) ((f + 0x8000) >> 16); }
inline int16_t loWord(int32_t v) { return (int16_t) (v & 0xFFFF); }
inline int16_t hiWord(int32_t v) { return (int16_t) ((uint32_t) v >> 16); }

// MPW runtime long multiply / divide (CODE 1): wrap-around product, truncating quotient.
inline int32_t lmul(int32_t a, int32_t b) { return (int32_t) ((uint32_t) a * (uint32_t) b); }
inline int32_t ldiv(int32_t a, int32_t b) { return b == 0 ? 0 : (int32_t) ((int64_t) a / b); }

// muls.w a,b ; add.l d,d ; swap -> (a*b*2) >> 16 as a 16-bit value.
inline int16_t q15(int32_t product) { return (int16_t) (((uint32_t) product << 1) >> 16); }
inline int32_t muls(int16_t a, int16_t b) { return (int32_t) a * b; }
// divs.w: 32/16 signed, quotient truncated; on overflow the register keeps its low word.
inline int16_t divs(int32_t n, int16_t d)
{
    if (d == 0) return (int16_t) n;
    const int32_t q = n / d;
    if (q > 32767 || q < -32768) return (int16_t) n;
    return (int16_t) q;
}

// Toolbox Random with QuickDraw's randSeed.
struct Random {
    uint32_t seed = 1;
    int16_t next()
    {
        seed = (uint32_t) (((uint64_t) seed * 16807u) % 0x7FFFFFFFu);
        const int16_t r = (int16_t) (seed & 0xFFFF);
        return r == INT16_MIN ? 0 : r;
    }
};

} // namespace turbosynth::mac
