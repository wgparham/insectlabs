#include "Presets.h"
#include "Engine.h"

#include <algorithm>
#include <climits>
#include <cmath>

namespace turbosynth::presets {

using namespace mac;

namespace {

const char* const kShaperPresetNames[kShaperPresets] = {
    "Straight line", "Zigzag", "Fold", "V", "Parabola", "Cubic", "Random", "Flat",
};
const char* const kShaperModifierNames[kShaperModifiers] = {
    "Mirror right half onto left", "Mirror left half onto right", "Flip left half", "Flip right half",
    "Square law", "Inverse square law", "Bow down", "Bow up", "Randomize", "Smooth",
};
const char* const kWavePresetNames[kWavePresets] = {
    "Sine", "Triangle", "Sawtooth", "Square", "Even harmonics", "Every 4th harmonic",
    "Every 3rd harmonic", "Formant", "Fibonacci harmonics", "Prime harmonics", "Octaves",
};
const char* const kWaveModifierNames[kWaveModifiers] = {
    "Amplify positive half", "Amplify negative half", "Amplify", "Fade in", "Fade out", "Attenuate",
    "Shift up", "Shift down", "Normalize", "Compress left", "Compress right", "Compress both ends",
    "Rotate left", "Rotate right", "Invert", "Add noise", "Smooth", "Smooth (wrap around)",
};
const char* const kEnvPresetNames[kEnvPresets] = {
    "Attack, decay, sustain, release", "Decay", "Rise and fall", "Rise",
    "Slow tremolo", "Fast tremolo", "Half level", "Full level",
};
const char* const kEnvModifierNames[kEnvModifiers] = {
    "Lower levels", "Raise levels", "Compress time", "Expand time", "Less contrast", "More contrast",
};

const char* name(const char* const* names, int count, int n) { return n >= 0 && n < count ? names[n] : ""; }

// ---------------------------------------------------------------------------
// Waveshaper (CODE 41). The table is at +132 of the module.

// F41_009E6: in-place moving average, 3/5/7 points, ends held.
void shaperSmooth(int16_t* t)
{
    t[1] = (int16_t) ldiv(t[0] + t[1] + t[2], 3);
    t[2] = (int16_t) ldiv(t[0] + t[1] + t[2] + t[3] + t[4], 5);
    for (int i = 3; i <= 252; ++i)
        t[i] = (int16_t) ldiv(t[i - 3] + t[i - 2] + t[i - 1] + t[i] + t[i + 1] + t[i + 2] + t[i + 3], 7);
    t[253] = (int16_t) ldiv(t[251] + t[252] + t[253] + t[254] + t[255], 5);
    t[254] = (int16_t) ldiv(t[253] + t[254] + t[255], 3);
}

int32_t signedSquare(int16_t v) // F41_007E0 / F41_00896
{
    return v < 0 ? -ldiv(lmul(v, v), 0x8000) : ldiv(lmul(v, v), 0x7FFF);
}

// ---------------------------------------------------------------------------
// Waveforms (CODE 38).

// F38_0098C with F38_04A54 / 04ABC / 04AF0: sum the harmonics with the sine
// table, then scale to 0x7FF8 if the sum overflows, else by 0xFFFF / 65536.
void synthesizeHarmonics(const Harmonics& h, Waveform& w)
{
    int32_t buf[256] = {};
    for (int k = 0; k <= 64; ++k) {
        int idx = h[(size_t) k].phase;
        for (int n = 0; n < 256; ++n) {
            buf[n] += q15(muls(kSineTable[idx & 0xFF], h[(size_t) k].level));
            idx = (idx + k) & 0xFF;
        }
    }
    int32_t peak = 0;
    for (int32_t v : buf) peak = std::max(peak, v < 0 ? -v : v);
    const uint16_t scale = (uint16_t) (peak > 0x7FFF ? loWord(fixDiv(0x7FF8, peak)) : 0xFFFF);
    for (int n = 0; n < 256; ++n) {
        const int32_t hi = muls((int16_t) (buf[n] >> 16), (int16_t) scale);
        const int32_t lo = (int16_t) (((uint32_t) (uint16_t) buf[n] * scale) >> 16);
        w.samples[(size_t) n] = (int16_t) (hi + lo);
    }
    w.samples[256] = w.samples[0];
}

// F38_00C5A: the sample at a 16.16 position, interpolated (uses the guard point).
int16_t interp(const Waveform& w, Fixed pos)
{
    const int idx = hiWord(pos) & 0xFF;
    const int32_t frac = pos & 0xFFFF;
    return (int16_t) ldiv(lmul(w.samples[(size_t) idx], 0x10000 - frac) + lmul(w.samples[(size_t) idx + 1], frac), 0x10000);
}

int16_t clamp16(int32_t v) { return (int16_t) std::clamp<int32_t>(v, -0x8000, 0x7FFF); }

// ---------------------------------------------------------------------------
// Envelopes (CODE 40). Times in ms, levels 0..100.
int16_t durationMs(const Module& m) { return (int16_t) ldiv(lmul(m.length, 1000), m.sampleRate); }

void addPoint(Module& m, int level, int timeMs)
{
    EnvPoint p;
    p.level = (int16_t) level;
    p.timeMs = (int16_t) timeMs;
    setPointRect(p);
    m.points.push_back(p);
}

} // namespace

const char* shaperPresetName(int n) { return name(kShaperPresetNames, kShaperPresets, n); }
const char* shaperModifierName(int n) { return name(kShaperModifierNames, kShaperModifiers, n); }
const char* wavePresetName(int n) { return name(kWavePresetNames, kWavePresets, n); }
const char* waveModifierName(int n) { return name(kWaveModifierNames, kWaveModifiers, n); }
const char* envPresetName(int n) { return name(kEnvPresetNames, kEnvPresets, n); }
const char* envModifierName(int n) { return name(kEnvModifierNames, kEnvModifiers, n); }

void shaperPreset(int16_t* t, int n, Random& rng, uint32_t ticks)
{
    switch (n) {
    case 0: // F41_00004
        for (int i = 0; i < 256; ++i) t[i] = shaperIdentity(i);
        break;
    case 1: // F41_0003C: eight straight segments
        for (int i = 0; i < 32; ++i) t[i] = (int16_t) (lmul(i, 0x800) - 0x8000);
        for (int i = 32; i < 64; ++i) t[i] = (int16_t) (0x7FFF - lmul(i - 32, 0x400));
        for (int i = 64; i < 96; ++i) t[i] = (int16_t) lmul(i - 64, 0x200);
        for (int i = 96; i < 128; ++i) t[i] = (int16_t) (0x4000 - lmul(i - 96, 0x400));
        for (int i = 128; i < 160; ++i) t[i] = (int16_t) (lmul(i - 128, 0x200) - 0x4000);
        for (int i = 160; i < 192; ++i) t[i] = (int16_t) -lmul(i - 160, 0x300);
        for (int i = 192; i < 224; ++i) t[i] = (int16_t) (lmul(i - 192, 0x200) - 0x6000);
        for (int i = 224; i < 256; ++i) t[i] = (int16_t) (-0x2000 - lmul(i - 224, 0x300));
        break;
    case 2: // F41_00202
        for (int i = 0; i <= 85; ++i) t[i] = (int16_t) (i * 0x200 - 0x8000);
        for (int i = 86; i <= 169; ++i) t[i] = (int16_t) (0x7F00 - (int16_t) (i << 8));
        for (int i = 170; i < 256; ++i) t[i] = (int16_t) (-t[255 - i] - 1);
        break;
    case 3: // F41_00294
        for (int i = 0; i <= 63; ++i) t[i] = (int16_t) (0x7FFF - (int16_t) (i * 0x400));
        for (int i = 63; i <= 127; ++i) t[i] = (int16_t) ((i - 63) * 0x200 - 0x8000);
        for (int i = 128; i < 256; ++i) t[i] = (int16_t) ((i - 128) << 8);
        break;
    case 4: // F41_00320
        for (int i = 0; i <= 127; ++i) t[i] = (int16_t) (0x7FFF - (int16_t) ((i * i) << 2));
        for (int i = 128; i < 256; ++i) t[i] = t[255 - i];
        break;
    case 5: // F41_00386: x^3/16 - 768x
        for (int i = 0; i <= 127; ++i) {
            const int32_t x = i - 128;
            t[i] = (int16_t) std::min<int32_t>(ldiv(lmul(lmul(x, x), x), 16) - lmul(0x300, x), 0x7FFF);
        }
        for (int i = 128; i < 256; ++i) t[i] = (int16_t) (-t[255 - i] - 1);
        break;
    case 6: { // F41_00426: a random walk that turns at the edges, then a random modifier
        for (uint32_t k = 1; k <= ticks % 8; ++k) rng.next();
        auto absl = [](int32_t v) { return v < 0 ? -v : v; };
        int32_t y = -0x8000;
        uint32_t slope = (uint32_t) absl((int32_t) rng.next() << 7);
        int32_t bend = absl((int32_t) rng.next() << 6);
        for (int i = 0; i < 256; ++i) {
            t[i] = (int16_t) y;
            y = hiWord((int32_t) slope) + y + 0x100;
            slope += (uint32_t) bend;
            if (y < -0x8000) {
                y = -0x8000;
                slope = (uint32_t) fixRatio(0x100, 1);
                bend = absl((int32_t) rng.next() << 7);
            } else if (y > 0x7FFF) {
                y = 0x7F00;
                slope = (uint32_t) fixRatio(-0x100, 1);
                bend = -absl((int32_t) rng.next() << 7);
            } else if (rng.next() % 32 == 0) {
                slope = (uint32_t) ((int32_t) rng.next() << 7);
                bend = (int32_t) rng.next() << 6;
            }
        }
        static const int kRandomModifier[8] = { 0, 1, 4, 5, 6, 7, 8, 9 };
        const int r = rng.next() % 8;
        if (r >= 0) shaperModifier(t, kRandomModifier[r], rng);
        break; }
    case 7: // F41_005D8
        for (int i = 0; i < 256; ++i) t[i] = 0;
        break;
    default: break;
    }
}

void shaperModifier(int16_t* t, int n, Random& rng)
{
    switch (n) {
    case 0: for (int i = 0; i <= 127; ++i) t[i] = t[255 - i]; break;   // F41_0068A
    case 1: for (int i = 128; i < 256; ++i) t[i] = t[255 - i]; break;  // F41_006C8
    case 2: for (int i = 0; i <= 127; ++i) t[i] = (int16_t) (-t[i] - 1); break;  // F41_00608
    case 3: for (int i = 128; i < 256; ++i) t[i] = (int16_t) (-t[i] - 1); break; // F41_00648
    case 4: // F41_007E0
        for (int i = 0; i < 256; ++i) t[i] = (int16_t) signedSquare(t[i]);
        shaperSmooth(t);
        break;
    case 5: // F41_00896
        for (int i = 0; i < 256; ++i) t[i] = (int16_t) (2 * t[i] - signedSquare(t[i]));
        shaperSmooth(t);
        break;
    case 6: // F41_00708
        for (int i = 0; i < 256; ++i) {
            const int32_t u = t[i] + 0x8000;
            t[i] = (int16_t) (ldiv(lmul(u, u), 0x10000) - 0x8000);
        }
        shaperSmooth(t);
        break;
    case 7: // F41_00770
        for (int i = 0; i < 256; ++i) {
            const int32_t u = t[i] + 0x8000;
            t[i] = (int16_t) (u - ldiv(lmul(u, u), 0x10000) + u - 0x8000);
        }
        shaperSmooth(t);
        break;
    case 8: // F41_0096E
        for (int i = 0; i < 256; ++i) t[i] = clamp16(t[i] + ldiv(rng.next(), 32));
        break;
    case 9: shaperSmooth(t); break;
    default: break;
    }
}

bool harmonicPreset(Harmonics& h, int n)
{
    h = Harmonics{};
    switch (n) {
    case 0: h[1].level = 0x7FFF; break; // F38_00D28
    case 1: { // F38_00D5E
        bool flip = false;
        for (int k = 1; k <= 63; k += 2) { h[(size_t) k] = { (int16_t) ldiv(0x6872, k * k), (int16_t) (flip ? 0x80 : 0) }; flip = !flip; }
        break; }
    case 2: for (int k = 1; k <= 64; ++k) h[(size_t) k].level = (int16_t) ldiv(0x7FFF, k); break;        // F38_00DEA
    case 3: for (int k = 1; k <= 63; k += 2) h[(size_t) k].level = (int16_t) ldiv(0x7FFF, k); break;     // F38_00E3E
    case 4: for (int k = 1; k <= 64; ++k) if (k == 1 || k % 2 == 0) h[(size_t) k].level = (int16_t) ldiv(0x70A2, k); break; // F38_00E9A
    case 5: { // F38_00F06
        bool flip = false;
        for (int k = 1; k <= 64; ++k)
            if (k % 4 == 1) { h[(size_t) k] = { (int16_t) ldiv(0x5C8C, k), (int16_t) (flip ? 0x80 : 0) }; flip = !flip; }
        break; }
    case 6: for (int k = 1; k <= 64; ++k) if (k % 3 == 1) h[(size_t) k].level = (int16_t) ldiv(0x7FFF, k); break; // F38_00F94
    case 7: // F38_00FF8
        h[1].level = 0x7FFF;
        for (int k = 20; k <= 22; ++k) h[(size_t) k].level = (int16_t) ldiv(0x7FFF, 23 - k);
        for (int k = 23; k <= 25; ++k) h[(size_t) k].level = (int16_t) ldiv(0x7FFF, k - 22);
        break;
    case 8: for (int k : { 1, 2, 3, 5, 8, 13, 21, 34, 55 }) h[(size_t) k].level = 0x7FFF; break; // F38_01086 (a Pascal set)
    case 9: // F38_010F4
        for (int k : { 1, 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61 }) h[(size_t) k].level = 0x7FFF;
        break;
    case 10: for (int k = 1; k <= 64; k *= 2) h[(size_t) k].level = 0x7FFF; break; // F38_01162
    default: return false;
    }
    return true;
}

void wavePreset(Waveform& w, int n)
{
    Harmonics h;
    if (!harmonicPreset(h, n)) return;
    synthesizeHarmonics(h, w);
    w.name = wavePresetName(n);
}

void waveModifier(Waveform& w, int n, Random& rng)
{
    auto& s = w.samples;
    switch (n) {
    case 0: // F38_011C4
        for (int i = 0; i < 256; ++i) { const int32_t d = ldiv(lmul(s[i], 10), 9); s[i] = d < 0 ? s[i] : (int16_t) std::min(d, 0x7FFF); }
        break;
    case 1: // F38_01232
        for (int i = 0; i < 256; ++i) { const int32_t d = ldiv(lmul(s[i], 10), 9); s[i] = d > 0 ? s[i] : (int16_t) std::max(d, -0x8000); }
        break;
    case 2: for (int i = 0; i < 256; ++i) s[i] = clamp16(ldiv(lmul(s[i], 10), 9)); break;         // F38_012A0
    case 3: for (int i = 0; i < 256; ++i) s[i] = (int16_t) ldiv(lmul(s[i], i + 245), 500); break;  // F38_0130A
    case 4: for (int i = 0; i < 256; ++i) s[i] = (int16_t) ldiv(lmul(s[i], 500 - i), 500); break;  // F38_0135E
    case 5: for (int i = 0; i < 256; ++i) s[i] = (int16_t) ldiv(lmul(s[i], 9), 10); break;         // F38_013B2
    case 6: for (int i = 0; i < 256; ++i) s[i] = (int16_t) std::min(s[i] + 2000, 0x7FFF); break;   // F38_013FE
    case 7: for (int i = 0; i < 256; ++i) s[i] = (int16_t) std::max(s[i] - 2000, -0x8000); break;  // F38_0144C
    case 8: { // F38_0149A
        int32_t peak = 0;
        for (int i = 0; i < 256; ++i) {
            if (s[i] == -0x8000) peak = 0x7FFF;
            else if (s[i] > peak) peak = s[i];
            else if (s[i] < 0 && -s[i] > peak) peak = -s[i];
        }
        if (peak != 0) // the original divides by zero here
            for (int i = 0; i < 256; ++i) s[i] = (int16_t) ldiv(lmul(s[i], 0x7FFF), peak);
        break; }
    case 9: { // F38_0155E: resample with a falling step, in place
        s[256] = s[0];
        Fixed pos = 0, step = fixRatio(2, 1);
        const Fixed dec = fixRatio(1, 255);
        for (int i = 0; i < 256; ++i) { s[i] = interp(w, pos); pos = lmul(i, step); step -= dec; }
        break; }
    case 10: { // F38_015E0: the same from the other end
        s[256] = s[0];
        Fixed pos = fixRatio(255, 1), step = fixRatio(2, 1);
        const Fixed dec = fixRatio(1, 255);
        for (int i = 0; i < 256; ++i) { s[255 - i] = interp(w, pos); pos = fixRatio(255, 1) - lmul(i, step); step -= dec; }
        break; }
    case 11: waveModifier(w, 9, rng); waveModifier(w, 10, rng); break; // F38_01678
    case 12: std::rotate(s.begin(), s.begin() + 4, s.begin() + 256); break; // F38_01692
    case 13: std::rotate(s.begin(), s.begin() + 252, s.begin() + 256); break; // F38_016EE
    case 14: for (int i = 0; i < 256; ++i) s[i] = (int16_t) -(s[i] == -0x8000 ? -0x7FFF : s[i]); break; // F38_0174C
    case 15: for (int i = 0; i < 256; ++i) s[i] = clamp16(s[i] + ldiv(rng.next(), 32)); break;          // F38_01796
    case 16: // F38_01806: in-place moving average using the guard point
        s[256] = s[0];
        s[1] = (int16_t) ldiv(s[0] + s[1] + s[2], 3);
        s[2] = (int16_t) ldiv(s[0] + s[1] + s[2] + s[3] + s[4], 5);
        for (int i = 3; i <= 253; ++i) s[i] = (int16_t) ldiv(s[i - 3] + s[i - 2] + s[i - 1] + s[i] + s[i + 1] + s[i + 2] + s[i + 3], 7);
        s[254] = (int16_t) ldiv(s[252] + s[253] + s[254] + s[255] + s[256], 5);
        s[255] = (int16_t) ldiv(s[254] + s[255] + s[256], 3);
        break;
    case 17: // F38_01968: 7-point average wrapping round the cycle, in place
        for (int i = 0; i < 256; ++i) {
            int32_t sum = 0;
            for (int k = -3; k <= 3; ++k) sum += s[(size_t) ((i + k) & 0xFF)];
            s[i] = (int16_t) ldiv(sum, 7);
        }
        break;
    default: return;
    }
    // The guard point is left as it was (checked against the original); the
    // engine sets it before rendering.
}

void synthesize(const Harmonics& h, Waveform& w) { synthesizeHarmonics(h, w); }

namespace {
// F38_049E6: correlate the wave with the sine table at harmonic k, starting at
// phase offset (64 = a quarter cycle); the sum saturates; result is its high word.
int16_t correlate(const Waveform& w, int k, int offset)
{
    int32_t sum = 0;
    int idx = offset;
    for (int n = 0; n < 256; ++n) {
        const int32_t term = muls(w.samples[(size_t) n], kSineTable[idx & 0xFF]) >> 6;
        const int64_t s = (int64_t) sum + term;
        if (s > 0x7FFFFFFF) { sum = 0x7FFF0000; break; }
        if (s < INT32_MIN) { sum = (int32_t) 0x80000000; break; }
        sum = (int32_t) s;
        idx = (idx + k) & 0xFF;
    }
    const int16_t hi = (int16_t) (sum >> 16);
    return hi == -1 ? 0 : hi;
}

// Toolbox FracSqrt: square root of an unsigned 2.30 Fract, rounded.
uint32_t fracSqrt(uint32_t x)
{
    const uint64_t v = (uint64_t) x << 30;
    uint64_t r = (uint64_t) std::sqrt((double) v);
    while (r * r > v) --r;
    while ((r + 1) * (r + 1) <= v) ++r;
    if (v - r * r > r) ++r; // round to nearest
    return (uint32_t) std::min<uint64_t>(r, 0xFFFFFFFFu);
}
} // namespace

Harmonics analyze(const Waveform& w) // F38_007AC
{
    Harmonics h{};
    for (int k = 0; k <= 64; ++k) {
        const int32_t s = correlate(w, k, 0), c = correlate(w, k, 0x40);
        uint32_t mag = fracSqrt((uint32_t) lmul(s, s) + (uint32_t) lmul(c, c));
        if (mag > 0x3FFFFFFF) mag = 0x3FFFFFFF;
        h[(size_t) k].level = hiWord((int32_t) (mag << 1));
        int32_t phase;
        if (s == 0 && c == 0) phase = 0; // a linear stand-in for the arctangent, one quadrant at a time
        else if (s >= 0 && c >= 0) phase = ldiv(c << 6, s + c);
        else if (s < 0 && c >= 0) phase = ldiv(-(s << 6), c - s) + 0x40;
        else if (s < 0 && c < 0) phase = ldiv(c << 6, c + s) + 0x80;
        else phase = ldiv(s << 6, s - c) + 0xC0;
        h[(size_t) k].phase = (int16_t) phase;
    }
    return h;
}

void harmonicModifier(Harmonics& h, int n, Random& rng)
{
    auto L = [&h](int k) -> int16_t& { return h[(size_t) k].level; };
    switch (n) {
    case 0: for (int k = 1; k <= 64; ++k) L(k) = L(k) < 0x200 ? 0 : (int16_t) (L(k) - 0x200); break; // F38_01A82
    case 1: // F38_01AD6: only harmonics already present grow
        for (int k = 1; k <= 64; ++k) { if (L(k) > 0x7DFF) L(k) = 0x7FFF; else if (L(k) > 8) L(k) = (int16_t) (L(k) + 0x200); }
        break;
    case 2: { // F38_01B3E: normalize
        int16_t peak = 0;
        for (int k = 1; k <= 64; ++k) {
            if (L(k) == -0x8000) peak = 0x7FFF;
            else if (L(k) > peak) peak = L(k);
            else if (L(k) < 0 && (int16_t) -L(k) > peak) peak = (int16_t) -L(k);
        }
        if (peak != 0) for (int k = 1; k <= 64; ++k) L(k) = (int16_t) ldiv(lmul(L(k), 0x7FFF), peak);
        break; }
    case 3: for (int k = 1; k <= 64; ++k) L(k) = (int16_t) ldiv(lmul(L(k), k + 64), 128); break; // F38_01C18
    case 4: // F38_01C72
        for (int k = 1; k <= 32; ++k) L(k) = (int16_t) ldiv(lmul(L(k), 128 - k), 128);
        for (int k = 33; k <= 64; ++k) L(k) = (int16_t) ldiv(lmul(L(k), k + 64), 128);
        break;
    case 5: for (int k = 1; k <= 64; ++k) L(k) = (int16_t) ldiv(lmul(L(k), 128 - k), 128); break; // F38_01D10
    case 6: for (int k = 1; k <= 64; ++k) L(k) = (int16_t) std::min<int32_t>(ldiv(lmul(L(k), 129 - k), 64), 0x7FFF); break; // F38_01D6A
    case 7: // F38_01DD2
        for (int k = 1; k <= 64; ++k) L(k) = (int16_t) std::min<int32_t>(ldiv(lmul(L(k), k < 33 ? k + 64 : 129 - k), 64), 0x7FFF);
        break;
    case 8: for (int k = 1; k <= 64; ++k) L(k) = (int16_t) std::min<int32_t>(ldiv(lmul(L(k), k + 64), 64), 0x7FFF); break; // F38_01E6C
    case 9: // F38_01ED4: everything down one place, the second harmonic adding into the first
        L(1) = (int16_t) std::min<int32_t>(L(1) + L(2), 0x7FFF);
        for (int k = 2; k <= 63; ++k) L(k) = L(k + 1);
        L(64) = 0;
        break;
    case 10: for (int k = 64; k >= 2; --k) L(k) = L(k - 1); break; // F38_01F40
    case 11: // F38_01F7C
        for (int k = 1; k <= 64; ++k) {
            int32_t v = L(k) + divs(rng.next(), 0x100);
            if (v < 0x40) v = 0; else if (v > 0x7FFF) v = 0x7FFF;
            L(k) = (int16_t) v;
        }
        break;
    case 12: // F38_01FEA: spacing halved
        for (int k = 2; k <= 64; ++k) L(k) = k > 32 ? 0 : (L(2 * k) > 0 ? L(2 * k) : L(2 * k - 1));
        break;
    case 13: // F38_0206C: spacing doubled (odd places take k/2, even ones clear)
        for (int k = 64; k >= 4; --k) L(k) = (k & 1) ? L(k / 2) : 0;
        L(3) = 0;
        L(2) = 0;
        break;
    case 14: // F38_020CE: spacing stretched by 3/2
        for (int k = 64; k >= 2; --k) L(k) = (int16_t) ldiv(L(2 * k / 3) + L(2 * (k + 1) / 3), 2);
        break;
    default: break;
    }
}

const char* harmonicModifierName(int n)
{
    static const char* const names[kHarmonicModifiers] = {
        "Lower all harmonics", "Raise harmonics present", "Normalize", "Tilt up", "Scoop the middle", "Tilt down",
        "Boost the lows", "Boost the middle", "Boost the highs", "Shift down one harmonic", "Shift up one harmonic",
        "Randomize", "Squeeze spacing", "Spread spacing", "Spread spacing by 3/2",
    };
    return n >= 0 && n < kHarmonicModifiers ? names[n] : "";
}

void setPointRect(EnvPoint& p) // F40_00C2C: SetRect(time-1, 99-level, time+2, 102-level)
{
    p.extra = { (int16_t) (99 - p.level), (int16_t) (p.timeMs - 1), (int16_t) (102 - p.level), (int16_t) (p.timeMs + 2) };
}

void envPreset(Module& m, int n)
{
    if (n < 0 || n >= kEnvPresets) return;
    const int16_t dur = durationMs(m);
    m.points.clear();
    switch (n) {
    case 0: // F40_000C0
        addPoint(m, 0, 0);
        if (dur > 30) addPoint(m, 100, 30);
        if (dur > 100) addPoint(m, 50, 100);
        if ((int16_t) (dur - 100) > 100) addPoint(m, 50, dur - 100);
        if (dur > 0) addPoint(m, 0, dur);
        break;
    case 1: addPoint(m, 100, 0); if (dur > 0) addPoint(m, 0, dur); break; // F40_00270
    case 2: // F40_00334
        addPoint(m, 0, 0);
        if (dur > 1) addPoint(m, 100, dur / 2);
        if (dur > 2) addPoint(m, 0, dur);
        break;
    case 3: addPoint(m, 0, 0); if (dur > 1) addPoint(m, 100, dur); break; // F40_0044A
    case 4: for (int t = 0; t <= dur; t += 201) addPoint(m, (t & 1) ? 55 : 45, t); break;  // F40_00510
    case 5: for (int t = 0; t <= dur; t += 61) addPoint(m, (t & 1) ? 100 : 50, t); break;  // F40_005B0
    case 6: addPoint(m, 50, 0); break;  // F40_00650
    case 7: addPoint(m, 100, 0); break; // F40_006AA
    default: break;
    }
}

void envModifier(Module& m, int n)
{
    auto& pts = m.points;
    auto scaled = [](int v, int mul, int div) { return divs((int16_t) (v * mul), (int16_t) div); }; // muls.w, ext.l, divs.w
    switch (n) {
    case 0: for (auto& p : pts) p.level = scaled(p.level, 9, 10); break; // F40_00704
    case 1: for (auto& p : pts) p.level = std::min<int16_t>(scaled(p.level + 1, 10, 9), 100); break; // F40_0075E
    case 2: // F40_007CC: times from the second point on, kept increasing
        for (size_t i = 1; i < pts.size(); ++i) {
            int16_t t = scaled(pts[i].timeMs, 9, 10); // 9 * time wraps in 16 bits past 3640 ms, as in the original
            if (t <= pts[i - 1].timeMs) t = (int16_t) (pts[i - 1].timeMs + 1);
            pts[i].timeMs = t;
        }
        break;
    case 3: { // F40_00852, then F40_0005A drops the points past the end
        for (size_t i = 1; i < pts.size(); ++i) pts[i].timeMs = (int16_t) (ldiv(lmul(pts[i].timeMs, 10), 9) + 1);
        const int32_t end = ldiv(lmul(m.length, 1000), m.sampleRate);
        pts.erase(std::remove_if(pts.begin(), pts.end(), [end](const EnvPoint& p) { return p.timeMs > end; }), pts.end());
        break; }
    case 4: for (auto& p : pts) p.level = (int16_t) (scaled(p.level - 50, 9, 10) + 50); break; // F40_008C4
    case 5: // F40_00928
        for (auto& p : pts) {
            int16_t l = (int16_t) (scaled(p.level - 50, 10, 9) + 50);
            if (l > 50) l = std::min<int16_t>((int16_t) (scaled(l - 49, 10, 9) + 50), 100);
            else if (l < 50) l = std::max<int16_t>((int16_t) (scaled(l - 51, 10, 9) + 50), 0);
            p.level = l;
        }
        break;
    default: return;
    }
    for (auto& p : pts) setPointRect(p);
}

} // namespace turbosynth::presets
