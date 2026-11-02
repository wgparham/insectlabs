#include "Engine.h"

#include "StFloat.h"
#include "WaveTables.h"

#include <algorithm>
#include <array>
#include <cstdlib>
#include <cstring>

namespace softsynth {

namespace fp = stfloat;

namespace {

// 0x6400: the low 32 bits of the product.
inline int32_t lmul(int32_t a, int32_t b) { return (int32_t) ((uint32_t) a * (uint32_t) b); }

// 0x638E: truncates toward zero. Its divide by zero runs a real divu #0, which
// would bomb an ST; here the caller gets 0 and a warning.
inline int32_t ldiv(int32_t a, int32_t b, bool* divideByZero)
{
    if (b == 0) {
        *divideByZero = true;
        return 0;
    }
    const uint32_t ua = a < 0 ? 0u - (uint32_t) a : (uint32_t) a;
    const uint32_t ub = b < 0 ? 0u - (uint32_t) b : (uint32_t) b;
    const uint32_t q = ua / ub;
    return (int32_t) ((a < 0) != (b < 0) ? 0u - q : q);
}

// The per-partial state block at -$16E0(A4), 64 bytes each.
struct State {
    int16_t out = 0;          // +04
    int wave = 0;             // +06
    size_t freqRecord = 0;    // +08, offset into the patch's partials
    size_t ampRecord = 0;     // +0C
    uint32_t phase = 0;       // +10; a cycle is 2^29
    uint32_t inc = 0;         // +14
    uint32_t incSlope = 0;    // +18
    uint32_t amp = 0;         // +1C; the high word is the level
    uint32_t ampSlope = 0;    // +20
    int mix = 0;              // +24
    int modulator = -1;       // +26; index of the partial, or none
    uint32_t nextFreq = 0;    // +32
    uint32_t nextAmp = 0;     // +36
};

class Render {
public:
    Render(Patch& p, const std::vector<uint8_t>& noise) : patch(p), noiseBytes(noise) {}

    // Reading past the end of the partials reads other memory in the original.
    uint32_t record(size_t offset) const
    {
        return offset + 4 <= patch.partials.size() ? readBe32(&patch.partials[offset]) : 0;
    }

    bool isModulator(int i) const
    {
        for (int k = 0; k < kPartials; ++k)
            if (patch.modulator(k) == i + 1) return true;
        return false;
    }

    // F00B3C8: every break point, the starting state, and the headroom shift.
    int calculate(XbiosRandom& rng)
    {
        int fadersInMix = 0;
        for (int i = 0; i < kPartials; ++i) {
            uint8_t* const a3 = patch.partial(i);
            const size_t base = (size_t) i * kPartialSize;
            State& s = states[(size_t) i];
            s.out = 0;
            const int fader = patch.fader(i);
            if (fader == 0)
                continue;
            if (patch.inMix(i) != 0)
                fadersInMix += fader;

            writeBe32(a3 + kAmpRecords + kRecTime, 1);
            s.nextAmp = 1;
            s.ampRecord = base + kAmpRecords;
            s.phase = rng.next() << 1;
            s.wave = (int8_t) a3[kWave];
            s.mix = patch.inMix(i);
            const int mod = patch.modulator(i);
            s.modulator = mod >= 1 && mod <= kPartials ? mod - 1 : -1;

            writeBe32(a3 + kAmpRecords + kRecValue, 0);
            const int32_t constant = isModulator(i) ? lmul(fader, 0xA42) : 0x50CB4;
            const int lastAmp = readBe16(a3 + kLastAmp);
            for (int n = 0; n <= lastAmp && recordFits(a3 + kAmpRecords, n); ++n) {
                uint8_t* const r = a3 + kAmpRecords + (size_t) n * kRecSize;
                uint8_t* const next = r + kRecSize;
                const int32_t time = pointTime(next);
                const int32_t value = lmul(lmul(readBe16(next + kRecY), fader), constant);
                writeBe32(next + kRecTime, (uint32_t) time);
                writeBe32(next + kRecValue, (uint32_t) value);
                const int32_t dt = (int32_t) ((uint32_t) time - readBe32(r + kRecTime));
                const int32_t dv = (int32_t) ((uint32_t) value - readBe32(r + kRecValue));
                writeBe32(r + kRecSlope, (uint32_t) ldiv(dv, dt, &divideByZero));
            }

            writeBe32(a3 + kFreqRecords + kRecTime, 1);
            s.nextFreq = 1;
            s.freqRecord = base + kFreqRecords;
            // The code converts to float and back around each of these steps.
            const fp::Value rate = fp::fromLong(patch.rate);
            const uint32_t product = fp::toFloatBits(fp::mul(fp::fromFloatBits(fp::toFloatBits(fp::fromLong(patch.frequency >> 3))),
                                                             fp::fromLong((int32_t) readBe32(a3 + kRatio))));
            writeBe32(a3 + kFreqRecords + kRecValue, (uint32_t) fp::toLong(fp::div(fp::fromFloatBits(product), rate)));
            const int divisor = (int8_t) a3[kScale] == 1 ? 2500 : 100;
            const int lastFreq = readBe16(a3 + kLastFreq);
            for (int n = 0; n <= lastFreq && recordFits(a3 + kFreqRecords, n); ++n) {
                uint8_t* const r = a3 + kFreqRecords + (size_t) n * kRecSize;
                uint8_t* const next = r + kRecSize;
                const int32_t time = pointTime(next);
                const fp::Value part = fp::div(fp::mul(fp::fromFloatBits(product), fp::fromLong(readBe16(next + kRecY))), fp::fromLong(divisor));
                const int32_t value = fp::toLong(fp::div(fp::add(fp::fromFloatBits(product), part), rate));
                writeBe32(next + kRecTime, (uint32_t) time);
                writeBe32(next + kRecValue, (uint32_t) value);
                const int32_t dt = (int32_t) ((uint32_t) time - readBe32(r + kRecTime));
                const int32_t dv = (int32_t) ((uint32_t) value - readBe32(r + kRecValue));
                writeBe32(r + kRecSlope, (uint32_t) ldiv(dv, dt, &divideByZero));
            }
        }
        int shift = 0;
        while (fadersInMix > 0x40) {
            fadersInMix >>= 1;
            ++shift;
        }
        return shift;
    }

    // F00B2A2 and F00B336. Both lists are built by pushing, so they run from the
    // highest partial down, and a modulator above its carrier is heard a sample early.
    void buildLists()
    {
        fmList.clear();
        plainList.clear();
        for (int i = kPartials - 1; i >= 0; --i) {
            if (patch.fader(i) == 0)
                continue;
            if (patch.modulator(i) != 0 || isModulator(i))
                fmList.push_back(i);
            else
                plainList.push_back(i);
        }
    }

    // 0xBC2E: partials that take part in FM, one sample at a time across all of them.
    // Runs through `end` inclusive, which is the extra step per buffer.
    void kernelFm(uint32_t start, uint32_t end, int shift, int16_t* buffer)
    {
        for (uint32_t d0 = start;; ++d0) {
            int32_t sum = 0;
            for (const int i : fmList) {
                State& s = states[(size_t) i];
                loadAmp(s, d0);
                loadFreq(s, d0);
                const int level = (int16_t) (s.amp >> 16);
                int32_t v;
                switch (s.wave) {
                case 1: case 2: case 3: {
                    uint16_t index = (uint16_t) (s.phase >> 16);
                    if (s.modulator >= 0) index = (uint16_t) (index + (uint16_t) states[(size_t) s.modulator].out);
                    v = high(table(s.wave)[(index & 0x1FFE) / 2] * level);
                    break;
                }
                case 4: {
                    const int ring = high(kNoise[d0 & 0x3FFF] * kSine[((s.phase >> 16) & 0x1FFE) / 2]);
                    v = high(ring * level);
                    break;
                }
                default:
                    v = ((int8_t) noiseBytes[d0 & 0x3FFF] * level) >> 7;
                    break;
                }
                s.out = (int16_t) v;
                if (s.mix != 0)
                    sum += s.out;
                s.inc += s.incSlope;
                s.phase += s.inc;
                s.amp += s.ampSlope;
            }
            *buffer++ = (int16_t) (sum >> shift);
            if (d0 >= end)
                break;
        }
    }

    // 0xBD9E: the other partials, each across the whole range, mixed onto the buffer.
    void kernelPlain(uint32_t start, uint32_t end, int shift, int16_t* buffer)
    {
        for (const int i : plainList) {
            State& s = states[(size_t) i];
            int16_t* out = buffer;
            const int16_t* const wave = s.wave >= 1 && s.wave <= 3 ? table(s.wave) : nullptr;
            for (uint32_t d0 = start; d0 < end; ++d0) {
                loadAmp(s, d0);
                const int level = (int16_t) (s.amp >> 16);
                int16_t v;
                if (s.wave == 4 || wave) {
                    loadFreq(s, d0);
                    if (wave) {
                        v = (int16_t) high(wave[((s.phase >> 16) & 0x1FFE) / 2] * level);
                    } else {
                        // and.w on the swapped register: this clears bit 16 of the phase for good.
                        s.phase &= 0x1FFEFFFFu;
                        const int ring = high(kNoise[d0 & 0x3FFF] * kSine[(s.phase >> 16) / 2]);
                        v = (int16_t) high(ring * level);
                    }
                    s.inc += s.incSlope;
                    s.phase += s.inc;
                } else {
                    // The 8-bit noise never reads its frequency contour or moves its phase.
                    v = (int16_t) (((int8_t) noiseBytes[d0 & 0x3FFF] * level) >> 7);
                }
                *out = (int16_t) (*out + (v >> std::min(shift, 15)));
                ++out;
                s.amp += s.ampSlope;
            }
        }
    }

    bool sawDivideByZero() const { return divideByZero; }
    bool sawBadCount() const { return badCount; }

private:
    // A corrupt record count made the original write through its own memory.
    bool recordFits(const uint8_t* first, int n)
    {
        const size_t end = (size_t) (first - patch.partials.data()) + ((size_t) n + 2) * kRecSize;
        if (end <= patch.partials.size())
            return true;
        badCount = true;
        return false;
    }

    int32_t pointTime(const uint8_t* record) const
    {
        const int32_t x3 = (int16_t) (readBe16(record + kRecX) * 3);
        bool unused = false;
        return ldiv(lmul(x3, patch.rate), 800, &unused) + 2;
    }

    void loadAmp(State& s, uint32_t d0)
    {
        if (s.nextAmp == d0) {
            s.amp = record(s.ampRecord + kRecValue);
            s.ampSlope = record(s.ampRecord + kRecSlope);
            s.ampRecord += kRecSize;
            s.nextAmp = record(s.ampRecord + kRecTime);
        }
    }

    void loadFreq(State& s, uint32_t d0)
    {
        if (s.nextFreq == d0) {
            s.inc = record(s.freqRecord + kRecValue);
            s.incSlope = record(s.freqRecord + kRecSlope);
            s.freqRecord += kRecSize;
            s.nextFreq = record(s.freqRecord + kRecTime);
        }
    }

    static const int16_t* table(int wave) { return wave == 1 ? kSine : wave == 2 ? kSquare : kTriangle; }

    // muls, add.l D7,D7, swap: the high word of twice a 32-bit product.
    static int high(int32_t product) { return (int16_t) (((uint32_t) product * 2u) >> 16); }

    Patch& patch;
    const std::vector<uint8_t>& noiseBytes;
    std::array<State, kPartials> states;
    std::vector<int> fmList, plainList;
    bool divideByZero = false, badCount = false;
};

// F00BF9E
int peakOf(const int16_t* s, int32_t count)
{
    int peak = 1;
    for (int32_t i = 0; i < count; ++i) {
        if (s[i] == -32768)
            return 0x7FFF;
        peak = std::max(peak, std::abs((int) s[i]));
    }
    return peak;
}

bool frequencyAllowed(const Patch& patch, int i)
{
    // The check at the top of F00C28E. The float constant is 65536.0.
    const fp::Value scale = fp::fromFloatBits(0x47800000u);
    fp::Value v = fp::fromLong((int32_t) readBe32(patch.partial(i) + kRatio));
    v = fp::div(v, scale);
    v = fp::mul(v, fp::fromLong(patch.frequency));
    v = fp::div(v, scale);
    return fp::compare(v, fp::fromLong(patch.rate >> 1)) <= 0;
}

void putPascal(uint8_t* to, const std::string& s)
{
    to[0] = (uint8_t) s.size();
    std::memcpy(to + 1, s.data(), s.size());
}

} // namespace

Engine::Engine(XbiosRandom random) : rng(random), noiseBytes(0x4000)
{
    for (size_t i = 0; i < 0x2000; ++i)
        writeBe16(&noiseBytes[i * 2], (uint16_t) rng.next());
}

bool Engine::render(Patch& patch, const RenderOptions& options, RenderResult& result, std::string* error)
{
    for (int i = 0; i < kPartials; ++i) {
        if (!frequencyAllowed(patch, i) && patch.fader(i) != 0) {
            if (error)
                *error = "Illegal frequency found! It's impossible to synthesize a sound containing frequencies higher than 1/2 the sample rate.";
            return false;
        }
    }

    Render r(patch, noiseBytes);
    const int shift = r.calculate(rng);
    r.buildLists();

    const int32_t length = patch.length;
    const int32_t bufferSamples = options.bufferSamples > 0 ? options.bufferSamples : length;
    result.samples.assign((size_t) length, 0);
    std::vector<int16_t> buffer;
    int peak = 0;
    int32_t remaining = length;
    int16_t* out = result.samples.data();
    while (remaining != 0) {
        const uint32_t start = (uint32_t) (length - remaining + 1);
        const int32_t chunk = std::min(remaining, bufferSamples);
        remaining -= chunk;
        buffer.assign((size_t) chunk + 1, 0);
        r.kernelFm(start, start + (uint32_t) chunk, shift, buffer.data());
        r.kernelPlain(start, start + (uint32_t) chunk, shift, buffer.data());
        if (options.normalize)
            peak = std::max(peak, peakOf(buffer.data(), chunk));
        std::copy_n(buffer.data(), chunk, out);
        out += chunk;
    }

    // F00BFCC, when the sound doesn't already reach full scale.
    if (options.normalize && peak < 0x7FFF) {
        for (auto& x : result.samples) {
            const int32_t q = (int32_t) x * 32767 / peak;
            if (q >= -32768 && q <= 32767)
                x = (int16_t) q;
        }
    }
    if (r.sawBadCount())
        result.warnings.push_back("A partial's break point count runs past the end of the data. This file is damaged.");
    if (r.sawDivideByZero())
        result.warnings.push_back("Two break points share a time. The original would have crashed here (divide by zero).");
    return true;
}

std::vector<uint8_t> soundDesignerFile(const Patch& patch, const std::vector<int16_t>& samples)
{
    constexpr size_t kHeader = 0x538;
    std::vector<uint8_t> f(kHeader + samples.size() * 2, 0);
    uint8_t* const h = f.data();
    // F00C726, then the fields set at 0xC43A..0xC48C.
    writeBe16(h + 0x000, 0x0538);
    writeBe16(h + 0x002, 0x000C);
    writeBe16(h + 0x038, 0xFF00);
    writeBe32(h + 0x03A, 0xFFFFFF80u);
    writeBe32(h + 0x03E, 0x147);
    h[0x042] = 4;
    putPascal(h + 0x044, "%Scale");
    writeBe32(h + 0x066, 1);
    putPascal(h + 0x06C, std::string(14, ' '));
    writeBe16(h + 0x0A8, 0x1D);
    writeBe16(h + 0x0AC, 0x0F);
    writeBe16(h + 0x0AE, 0x21);
    writeBe32(h + 0x0B8, (uint32_t) samples.size() * 2);
    putPascal(h + 0x0FE, patch.name() + " " + std::to_string((int16_t) (patch.frequency >> 16)));
    for (size_t i = 0; i < 10; ++i)
        h[0x15C + i * 0x28] = 1;
    writeBe32(h + 0x2F0, 0xFFFFFFFFu);
    writeBe32(h + 0x2F4, 0xFFFFFFFFu);
    h[0x2FA] = 1;
    writeBe32(h + 0x3FC, (uint32_t) patch.rate);
    writeBe32(h + 0x400, (uint32_t) (1000000 / patch.rate));
    writeBe16(h + 0x404, 0x10);
    putPascal(h + 0x2FC, " ");
    putPascal(h + 0x406, "Linear");
    writeBe32(h + 0x52C, 0xFFFFFFFFu);
    writeBe32(h + 0x530, 0xFFFFFFFFu);
    h[0x534] = 1;
    h[0x535] = 1;
    for (size_t i = 0; i < samples.size(); ++i)
        writeBe16(h + kHeader + i * 2, (uint16_t) samples[i]);
    return f;
}

} // namespace softsynth
