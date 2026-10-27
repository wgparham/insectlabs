#include "Waveforms.h"
#include "Sane.h"

#include <cmath>

namespace cloudgen {

namespace {

// 80-bit constants exactly as stored in the original code.
// 2*pi literal used for the sine table (exp $4001, mantissa C90FDAA2922A7E48).
const double kTwoPiLiteral = std::ldexp((double) 0xC90FDAA2922A7E48ull, 2 - 63);
// "3.14159" literal used for the Gaussian (exp $4001, mantissa C90FCF80DC33721D).
const double kPiLiteral = std::ldexp((double) 0xC90FCF80DC33721Dull, 1 - 63);
// sqrt(2*pi) literal (exp $4000, mantissa A0624DD2F1A9FBE7).
const double kSqrtTwoPiLiteral = std::ldexp((double) 0xA0624DD2F1A9FBE7ull, 1 - 63);

} // namespace

Waveform makeSine()
{
    Waveform w;
    w.points[0] = 0.0f;
    for (int i = 1; i < kTableSize; ++i)
        w.points[i] = (float) (std::sin(kTwoPiLiteral * i / 512.0) * 32767.0);
    return w;
}

Waveform makeSquare()
{
    Waveform w;
    w.points[0] = 32767.0f;
    for (int i = 1; i < kTableSize; ++i)
        w.points[i] = i < 256 ? 32767.0f : -32767.0f;
    return w;
}

Waveform makeSawtooth(bool correctFirstPoint)
{
    Waveform w;
    w.points[0] = correctFirstPoint ? 0.0f : 16383.5f; // the original's value is off the ramp
    for (int i = 1; i < kTableSize; ++i) {
        const int k = i + 256 <= 512 ? i + 256 : i - 256;
        w.points[i] = (float) ((double) k / 512.0f * 65535.0f - 32767.5f);
    }
    return w;
}

Waveform importWaveform(const SourceSound& sound)
{
    Waveform w;
    for (int i = 0; i < kTableSize; ++i) {
        const size_t k = (size_t) i * 2;
        w.points[i] = k < sound.interleaved.size() ? (float) sound.interleaved[k] : 0.0f;
    }
    return w;
}

const std::array<int16_t, kTableSize + 1>& gaussTable(bool fullScale)
{
    static const auto build = [](bool full) {
        std::array<int16_t, kTableSize + 1> t{};
        // The original divides by pi, not sqrt(2 pi); the full-scale variant
        // drops the normalisation so the peak is 1.
        const double scale = full ? 1.0 / kSqrtTwoPiLiteral : 1.0 / kPiLiteral;
        const double step = 6.0 / 512.0;
        for (int i = 0; i <= kTableSize; ++i) {
            const double x = i * step - 3.0;
            const double e = scale * std::exp(-(x * x) / 2.0);
            t[i] = sane::toInt16(e * kSqrtTwoPiLiteral * 32767.0);
        }
        return t;
    };
    static const std::array<int16_t, kTableSize + 1> original = build(false), full = build(true);
    return fullScale ? full : original;
}

double gaussEnvelope(double position, bool fullScale)
{
    const double x = position * 6.0 - 3.0;
    const double peak = fullScale ? 32767.0 : 32767.0 * kSqrtTwoPiLiteral / kPiLiteral;
    return peak * std::exp(-(x * x) / 2.0);
}

std::array<int16_t, kTableSize + 1> oscillatorTable(const Waveform& wave, bool wrap)
{
    std::array<int16_t, kTableSize + 1> t{};
    if (wrap) {
        for (int i = 0; i < kTableSize; ++i)
            t[i] = sane::toInt16(wave.points[i]);
        t[kTableSize] = t[0];
        return t;
    }
    for (int i = 0; i < kTableSize - 1; ++i) // loop bound "< 511" in the original
        t[i] = sane::toInt16(wave.points[i]);
    t[kTableSize - 1] = 0;
    t[kTableSize] = gaussTable()[0];
    return t;
}

} // namespace cloudgen
