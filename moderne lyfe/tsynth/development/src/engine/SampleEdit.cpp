#include "SampleEdit.h"
#include "MacMath.h"

#include <algorithm>
#include <climits>
#include <cstdlib>

namespace turbosynth::sampleedit {

using namespace mac;

namespace {

int16_t clip(int32_t v) { return (int16_t) std::clamp<int32_t>(v, -0x8000, 0x7FFF); }

// x * g for a 16.16 gain the way F28_059C6 / F28_05A64 do it: the integer
// part with muls, the fraction halved to Q15, then clipped.
int16_t applyGain(int16_t x, Fixed g)
{
    const int32_t whole = muls(x, (int16_t) hiWord(g));
    const int32_t frac = (int16_t) (((uint32_t) muls(x, (int16_t) ((uint16_t) loWord(g) >> 1)) << 1) >> 16);
    return clip(whole + frac);
}

// F28_0594A: count samples from position 0 stepping by ratio (16.16) through
// src, interpolating between neighbours, truncated toward the earlier one.
void resampleInto(int16_t* out, int32_t count, const int16_t* src, int32_t srcCount, Fixed ratio)
{
    int64_t pos = 0;
    for (int32_t n = 0; n < count; ++n, pos += ratio) {
        const int64_t i = pos >> 16;
        const int32_t a = i < srcCount ? src[i] : (srcCount ? src[srcCount - 1] : 0);
        const int32_t b = i + 1 < srcCount ? src[i + 1] : a;
        const uint32_t f = (uint32_t) (pos & 0xFFFF);
        const int32_t d = b - a;
        out[n] = d >= 0 ? (int16_t) (a + (int32_t) (((uint32_t) d * f) >> 16)) : (int16_t) (a - (int32_t) (((uint32_t) -d * f) >> 16));
    }
}

} // namespace

void convertRate(std::vector<int16_t>& data, int32_t oldRate, int32_t newRate, int32_t& loopStart, int32_t& loopEnd)
{
    if (oldRate <= 0 || newRate <= 0 || oldRate == newRate || data.empty()) return;
    const Fixed ratio = fixDiv(oldRate, newRate);
    const int32_t newLen = fixDiv((int32_t) data.size(), ratio);
    if (newLen <= 0) return;
    const std::vector<int16_t> src = data; // the original works in place; reading a copy gives the same samples
    data.assign((size_t) newLen, 0);
    resampleInto(data.data(), newLen, src.data(), (int32_t) src.size(), ratio);
    if (loopStart != -1) loopStart = fixDiv(loopStart, ratio);
    if (loopEnd != -1) loopEnd = fixDiv(loopEnd, ratio);
}

void scaleGain(std::vector<int16_t>& data, int percent)
{
    const Fixed g = fixRatio((int16_t) std::clamp(percent, 1, 2000), 100);
    for (auto& x : data) x = applyGain(x, g);
}

void normalize(std::vector<int16_t>& data)
{
    if (data.empty()) return;
    const auto [lo, hi] = std::minmax_element(data.begin(), data.end());
    int16_t peak = *hi;
    if (*lo == -32768) peak = 0x7FFF;
    else if ((int16_t) -*lo > peak) peak = (int16_t) -*lo;
    if (peak < 0x7FFF && peak > 0)
        for (auto& x : data) x = divs(((int32_t) ((uint32_t) (uint16_t) x << 16) >> 1) - x, peak);
}

void removeEnvelope(std::vector<int16_t>& data, int32_t rate, int responseMs)
{
    const int32_t len = (int32_t) data.size();
    responseMs = std::clamp(responseMs, 1, 1000);
    if (len == 0 || rate <= 0) return;
    const int32_t blocks = ldiv(lmul(len, ldiv(1000, responseMs)), rate); // 1000/response first, as the original divides
    const int32_t blockLen = ldiv(lmul(rate, responseMs), 1000);
    auto ramp = [&data](int32_t from, int32_t to, Fixed gain, Fixed slope) { // F28_05A64
        for (int32_t i = from; i < to && i < (int32_t) data.size(); ++i, gain += slope) data[(size_t) i] = applyGain(data[(size_t) i], gain);
    };
    int32_t pos = 0;
    Fixed gain = fixRatio(1, 1);
    for (int32_t b = 0; b < blocks; ++b) {
        int32_t from = lmul(b, blockLen);
        if (from == 0) from = 1;
        const int32_t to = std::min(len, lmul(b + 1, blockLen));
        // F6_00D7C: the block's peak and the index just after it.
        int16_t peak = 0;
        int32_t peakPos = from;
        for (int32_t i = from; i < to; ++i) {
            const int16_t v = data[(size_t) i] == -32768 ? (int16_t) 0x7FFF : (int16_t) std::abs(data[(size_t) i]);
            if (v >= peak) { peak = v; peakPos = i + 1; }
        }
        const Fixed target = peak > 0 ? fixRatio(30000, peak) : gain;
        const Fixed slope = peakPos != pos ? ldiv(target - gain, peakPos - pos) : 0;
        ramp(pos, peakPos, gain, slope);
        pos = peakPos;
        gain = target;
    }
    ramp(pos, len, gain, 0);
}

void extendLoop(std::vector<int16_t>& data, int32_t length, int32_t loopStart, int32_t loopEnd)
{
    // F28_01FF0: the buffer is already the module's length (the info box sets
    // it); from the loop end to its end, copies of the loop replace whatever is there.
    if (loopStart < 0 || loopEnd <= loopStart || loopEnd > (int32_t) data.size() || length <= loopEnd) return;
    data.resize((size_t) length, 0);
    const int32_t loop = loopEnd - loopStart;
    for (int32_t at = loopEnd; at < length; at += loop)
        std::copy_n(data.begin() + loopStart, std::min(loop, length - at), data.begin() + at);
}

void reverse(std::vector<int16_t>& data, int32_t from, int32_t to)
{
    from = std::clamp<int32_t>(from, 0, (int32_t) data.size());
    to = std::clamp<int32_t>(to, from, (int32_t) data.size());
    std::reverse(data.begin() + from, data.begin() + to);
}

void silence(std::vector<int16_t>& data, int32_t from, int32_t to)
{
    from = std::clamp<int32_t>(from, 0, (int32_t) data.size());
    to = std::clamp<int32_t>(to, from, (int32_t) data.size());
    std::fill(data.begin() + from, data.begin() + to, (int16_t) 0);
}

std::vector<int16_t> loopAsWaveform(const std::vector<int16_t>& data, int32_t loopStart, int32_t loopEnd)
{
    // F25_002B6: step through the loop by FixRatio(loop length, 256) with the
    // oscillator's resampler (F39_02870), then normalize the 256 points.
    std::vector<int16_t> wave(257, 0);
    if (loopStart < 0 || loopEnd <= loopStart || loopEnd >= (int32_t) data.size()) return wave;
    const Fixed step = fixRatio((int16_t) (loopEnd - loopStart), 256); // a word: loops over 32767 samples wrap, as in the original
    uint32_t pos = 0;
    for (int n = 0; n < 256; ++n, pos += (uint32_t) step) {
        const int32_t i = loopStart + (int32_t) (int16_t) (pos >> 16);
        const int16_t a = i >= 0 && i < (int32_t) data.size() ? data[(size_t) i] : 0;
        const int16_t b = i + 1 >= 0 && i + 1 < (int32_t) data.size() ? data[(size_t) i + 1] : a;
        const int16_t d = (int16_t) (b - a);                               // sub.w
        const int16_t f = (int16_t) ((uint16_t) pos >> 1);                 // the fraction as Q15
        wave[(size_t) n] = (int16_t) (a + (int16_t) ((uint32_t) (muls(d, f) * 2) >> 16));
    }
    std::vector<int16_t> body(wave.begin(), wave.begin() + 256);
    normalize(body); // F23_008B8 + F29_000A2: the module normalize
    std::copy(body.begin(), body.end(), wave.begin());
    wave[256] = wave[0];
    return wave;
}

int32_t autoLoopPoint(const std::vector<int16_t>& data, int32_t loopStart, int32_t loopEnd, bool moveEnd, int direction)
{
    const int32_t n = (int32_t) data.size();
    const int d = direction < 0 ? -1 : 1;
    // The ranges CODE 35 passes for the four arrows (and when each one is off).
    int32_t from, bound, ref;
    if (moveEnd) {
        from = loopEnd;
        ref = loopStart;
        if (d < 0) { if (loopEnd <= 1 || loopStart + 1 >= loopEnd) return loopEnd; bound = loopStart == -1 ? 1 : loopStart + 1; }
        else { if (loopEnd == -1 || n - 1 <= loopEnd) return loopEnd; bound = n - 1; }
    } else {
        from = loopStart;
        ref = loopEnd;
        if (d < 0) { if (loopStart <= 0) return loopStart; bound = 0; }
        else { if (loopStart == -1) return loopStart; bound = loopEnd == -1 ? n - 2 : loopEnd - 1; if (bound <= loopStart) return loopStart; }
    }
    auto at = [&data, n](int32_t i) { return i >= 0 && i < n ? (int32_t) data[(size_t) i] : 0; };
    // F6_00DDA: score each position by the summed differences of three
    // samples (it, the next and the one after in the search direction) from
    // the three around the reference; keep the lowest, ties going to the
    // later one. The result is the middle of the three.
    const int32_t r0 = at(ref - d), r1 = at(ref), r2 = at(ref + d);
    int32_t best = INT32_MAX, result = from;
    for (int32_t p = from; p != bound; ) {
        const int32_t score = std::abs(at(p) - r0) + std::abs(at(p + d) - r1) + std::abs(at(p + 2 * d) - r2);
        p += d;
        if (score <= best) { best = score; result = p; }
    }
    return result;
}

} // namespace turbosynth::sampleedit
