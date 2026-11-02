#include "Edit.h"
#include "StFloat.h"

#include <cstring>

namespace softsynth {

namespace fp = stfloat;

namespace {

// A word-sized divs.w: on overflow the 68000 leaves the dividend, so its low word is
// what the caller then stores. A zero divisor does the same here. On the 68000 that is
// a trap, and the handler in EmuTOS returns, leaving the registers (see RE-NOTES).
int16_t divs(int32_t n, int16_t d)
{
    if (d == 0)
        return (int16_t) n;
    const int32_t q = n / d;
    return q < -32768 || q > 32767 ? (int16_t) n : (int16_t) q;
}

// Most of the original's scaling: muls.w, then ext.l, which keeps only the product's
// low word, then divs.w.
int16_t mulDiv(int a, int b, int16_t d)
{
    return divs((int16_t) ((int16_t) a * (int16_t) b), d);
}

uint8_t* ampRec(Patch& p, int partial, int i) { return p.partial(partial) + kAmpRecords + (size_t) i * kRecSize; }
const uint8_t* ampRec(const Patch& p, int partial, int i) { return p.partial(partial) + kAmpRecords + (size_t) i * kRecSize; }
uint8_t* freqRec(Patch& p, int partial, int i) { return p.partial(partial) + kFreqRecords + (size_t) i * kRecSize; }
const uint8_t* freqRec(const Patch& p, int partial, int i) { return p.partial(partial) + kFreqRecords + (size_t) i * kRecSize; }

void setAmpX(Patch& p, int partial, int i, int x) { writeBe16(ampRec(p, partial, i) + kRecX, (uint16_t) x); }
void setAmpY(Patch& p, int partial, int i, int y) { writeBe16(ampRec(p, partial, i) + kRecY, (uint16_t) y); }
void setFreqX(Patch& p, int partial, int i, int x) { writeBe16(freqRec(p, partial, i) + kRecX, (uint16_t) x); }
void setFreqY(Patch& p, int partial, int i, int y) { writeBe16(freqRec(p, partial, i) + kRecY, (uint16_t) y); }
void setLastAmp(Patch& p, int partial, int v) { writeBe16(p.partial(partial) + kLastAmp, (uint16_t) v); }
void setLastFreq(Patch& p, int partial, int v) { writeBe16(p.partial(partial) + kLastFreq, (uint16_t) v); }
void setFader(Patch& p, int partial, int v) { writeBe16(p.partial(partial) + kFader, (uint16_t) v); }
int32_t ratio(const Patch& p, int partial) { return (int32_t) readBe32(p.partial(partial) + kRatio); }
void setRatio(Patch& p, int partial, int32_t v) { writeBe32(p.partial(partial) + kRatio, (uint32_t) v); }

// F004D22, the range helper. The arithmetic is on words, so a range whose top is below
// its bottom wraps instead of failing.
int16_t rnd(XbiosRandom& r, int lo, int hi)
{
    const uint16_t span = (uint16_t) (hi - lo + 1);
    const uint32_t product = (uint32_t) span * (uint16_t) r.next();
    return (int16_t) ((product >> 16) + (uint16_t) lo);
}

// The float at +0xA in the data segment, 65536.
constexpr uint32_t kFixedOneFloat = 0x47800000u;
// The doubles at +0x3C0 and +0x3D0 (10000) and +0x3C8 and +0x3D8 (1). The 10000s carry
// a stray low bit, which the library ignores.
const fp::Value kTenThousand{ 0x40C38800u, 0x00000003u };
const fp::Value kOne{ 0x3FF00000u, 0x00000000u };

// divu.w then swap: whole units in the high word and the remainder, not a fraction, in
// the low one. `over` is the patch's frequency in whole Hz.
int32_t hzToRatio(uint16_t hz, uint16_t over)
{
    const uint32_t q = hz / over, rem = hz % over;
    return (int32_t) ((q << 16) | rem);
}

uint16_t wholeHz(const Patch& p) { return (uint16_t) ((uint32_t) p.frequency >> 16); }

// The sound's length in x units of 3.75 ms, using the original's 267 for 800/3.
int32_t lengthInX(const Patch& p)
{
    return (int32_t) ((uint32_t) p.length * 0x10Bu) / p.rate;
}

void clearAmp(Patch& p, int i)
{
    setLastAmp(p, i, 0);
    setAmpPoint(p, i, 0, 0, 0);
    setAmpPoint(p, i, 1, 0, kEndX);
}

void clearFreq(Patch& p, int i)
{
    setLastFreq(p, i, 0);
    setFreqPoint(p, i, 0, 0, 0);
    setFreqPoint(p, i, 1, 0, kEndX);
}

void shiftAmpUp(Patch& p, int partial, int from, int index)
{
    for (int n = from; n > index; --n) {
        setAmpX(p, partial, n, ampX(p, partial, n - 1));
        setAmpY(p, partial, n, ampY(p, partial, n - 1));
    }
}

void removeAmp(Patch& p, int partial, int index)
{
    for (int n = index; n <= lastAmp(p, partial); ++n) {
        setAmpX(p, partial, n, ampX(p, partial, n + 1));
        setAmpY(p, partial, n, ampY(p, partial, n + 1));
    }
    setLastAmp(p, partial, lastAmp(p, partial) - 1);
}

void removeHarmonic(SliceList& h, int index)
{
    for (int n = index; n <= h.count; ++n) {
        h.x[(size_t) n] = h.x[(size_t) n + 1];
        h.y[(size_t) n] = h.y[(size_t) n + 1];
    }
    --h.count;
}

// The interpolation in F00DD8E and F00DECA, whose round trips through float change the
// result.
int16_t interpolate(int y0, int x0, int y1, int x1, int x)
{
    const fp::Value top = fp::sub(fp::fromFloatBits(fp::toFloatBits(fp::fromLong(y1))), fp::fromLong(y0));
    const uint32_t slope = fp::toFloatBits(fp::div(top, fp::fromLong((int16_t) (x1 - x0))));
    const fp::Value part = fp::mul(fp::fromFloatBits(slope), fp::fromLong((int16_t) (x - x0)));
    return (int16_t) fp::toLong(fp::add(fp::fromLong(y0), part));
}

// F00DFE6: is a record within 6 of the straight line through its neighbours?
bool nearLine(int yPrev, int xPrev, int y, int x, int yNext, int xNext)
{
    const uint32_t dy = fp::toFloatBits(fp::fromLong((int16_t) (yNext - yPrev)));
    const uint32_t dx = fp::toFloatBits(fp::fromLong((int16_t) (xNext - xPrev)));
    const uint32_t slope = fp::toFloatBits(fp::div(fp::fromFloatBits(dy), fp::fromFloatBits(dx)));
    const fp::Value part = fp::mul(fp::fromFloatBits(slope), fp::fromLong((int16_t) (x - xPrev)));
    const int16_t line = (int16_t) fp::toLong(fp::add(fp::fromLong(yPrev), part));
    return (int16_t) y >= (int16_t) (line - 6) && (int16_t) y <= (int16_t) (line + 6);
}

bool ampNearLine(const Patch& p, int i, int n)
{
    return nearLine(ampY(p, i, n - 1), ampX(p, i, n - 1), ampY(p, i, n), ampX(p, i, n), ampY(p, i, n + 1), ampX(p, i, n + 1));
}

int* settingOf(SmartSynthSettings& s, int group)
{
    int* const fields[12] = { &s.harmonicSeries, &s.partialDetuning, &s.attackRate, &s.decayRate,
                              &s.harmonicRange, &s.doubling, &s.frequencyAttack, &s.secondaryLevel,
                              &s.harmonicFilter, &s.frequencyMovement, &s.percussion, &s.fade };
    return fields[group];
}

} // namespace

int16_t ampX(const Patch& p, int partial, int i) { return readBe16(ampRec(p, partial, i) + kRecX); }
int16_t ampY(const Patch& p, int partial, int i) { return readBe16(ampRec(p, partial, i) + kRecY); }
int16_t freqX(const Patch& p, int partial, int i) { return readBe16(freqRec(p, partial, i) + kRecX); }
int16_t freqY(const Patch& p, int partial, int i) { return readBe16(freqRec(p, partial, i) + kRecY); }
void setAmpPoint(Patch& p, int partial, int i, int y, int x) { setAmpY(p, partial, i, y); setAmpX(p, partial, i, x); }
void setFreqPoint(Patch& p, int partial, int i, int y, int x) { setFreqY(p, partial, i, y); setFreqX(p, partial, i, x); }
int16_t lastAmp(const Patch& p, int partial) { return readBe16(p.partial(partial) + kLastAmp); }
int16_t lastFreq(const Patch& p, int partial) { return readBe16(p.partial(partial) + kLastFreq); }

// ---- SmartSynth ---------------------------------------------------------------

bool SmartSynthDialog::isHeadingOn(int group) const
{
    return *settingOf(const_cast<SmartSynthSettings&>(settings), group) != 0;
}

void SmartSynthDialog::clickHeading(int group)
{
    int* v = settingOf(settings, group);
    if (*v != 0) {
        *v = 0;
        return;
    }
    if (group == 6 || group == 9)
        *settingOf(settings, 15 - group) = 0;
    *v = 1;
}

void SmartSynthDialog::clickOption(int group, int option)
{
    if (*settingOf(settings, group) != 0)
        *settingOf(settings, group) = option;
}

void smartSynthGenerate(Patch& p, const SmartSynthSettings& s, XbiosRandom& r)
{
    if (s.harmonicSeries != 0) {
        setFader(p, 0, kFaderMax);
        for (int i = 1; i < 16; ++i) {
            const bool on = s.harmonicSeries == 1 || (i & 1) == s.harmonicSeries - 2;
            setFader(p, i, on ? rnd(r, 10, 63) : 0);
        }
    }

    int count = 16;
    if (s.harmonicRange == 1) count = rnd(r, 12, 16);
    else if (s.harmonicRange == 2) count = rnd(r, 8, 11);
    else if (s.harmonicRange == 3) count = rnd(r, 3, 7);
    for (int i = count; i < kPartials; ++i)
        setFader(p, i, 0);

    if (s.harmonicFilter != 0) {
        for (int i = 1; i < count; ++i) {
            if (p.fader(i) == 0)
                continue;
            int v = 0;
            if (s.harmonicFilter == 1) {
                if (rnd(r, 0, 7) != 0)
                    v = rnd(r, 15, 63 - divs((int16_t) (i * 40), (int16_t) count));
            } else if (s.harmonicFilter == 2) {
                if (rnd(r, 0, 3) != 0)
                    v = rnd(r, 5, 63 - divs((int16_t) (i * 55), (int16_t) count));
            } else {
                if (rnd(r, 0, 1) != 0)
                    v = rnd(r, 5, divs((int16_t) (count * 7), (int16_t) (i + 1)));
            }
            setFader(p, i, v);
        }
    }

    if (s.partialDetuning != 0) {
        setRatio(p, 0, 0x10000);
        const int amount = s.partialDetuning == 3 ? 5000 : 100 * s.partialDetuning;
        for (int h = 2; h <= 16; ++h) {
            const fp::Value offset = fp::div(fp::fromLong(rnd(r, -amount, amount)), kTenThousand);
            const fp::Value scale = fp::add(kOne, offset);
            const fp::Value number = fp::fromFloatBits(fp::toFloatBits(fp::fromLong(h)));
            setRatio(p, h - 1, fp::toLong(fp::mul(fp::mul(number, scale), fp::fromFloatBits(kFixedOneFloat))));
        }
    }

    if (s.frequencyAttack == 1) {
        for (int i = 0; i < 16; ++i) {
            p.partial(i)[kScale] = 2;
            setLastFreq(p, i, 3);
            setFreqPoint(p, i, 1, -20, 0);
            setFreqX(p, i, 2, 2);
            setFreqY(p, i, 2, -rnd(r, 1, 10));
            setFreqX(p, i, 3, rnd(r, 5, 10));
            setFreqY(p, i, 3, 0);
            setFreqPoint(p, i, 4, 0, kEndX);
        }
    } else if (s.frequencyAttack == 2) {
        for (int i = 0; i < 16; ++i) {
            p.partial(i)[kScale] = 2;
            setLastFreq(p, i, 6);
            setFreqPoint(p, i, 1, 30, 0);
            setFreqPoint(p, i, 2, 0, 1);
            setFreqPoint(p, i, 3, 20, 2);
            setFreqPoint(p, i, 4, 0, 3);
            setFreqPoint(p, i, 5, 10, 4);
            setFreqPoint(p, i, 6, 0, 5);
            setFreqPoint(p, i, 7, 0, kEndX);
        }
    } else if (s.frequencyAttack == 3) {
        p.partial(0)[kScale] = 2;
        setLastFreq(p, 0, 0);
        setFreqPoint(p, 0, 1, 0, kEndX);
        for (int i = 1; i < 16; ++i) {
            p.partial(i)[kScale] = 2;
            setLastFreq(p, i, 3);
            const int v = rnd(r, 0, 1) != 0 ? -50 : 50;
            setFreqPoint(p, i, 1, v, 0);
            const int x = rnd(r, 2, 5);
            setFreqPoint(p, i, 2, v, x);
            setFreqPoint(p, i, 3, 0, x + 1);
            setFreqPoint(p, i, 4, 0, kEndX);
        }
    }

    if (s.frequencyMovement != 0) {
        const int lo = s.frequencyMovement == 1 ? 2 : s.frequencyMovement == 2 ? 7 : 15;
        const int hi = s.frequencyMovement == 1 ? 7 : s.frequencyMovement == 2 ? 15 : 30;
        for (int i = 0; i < 16; ++i) {
            p.partial(i)[kScale] = 1;
            int16_t x = rnd(r, 30, 80);
            int n = 0;
            while (n < 15 && x < lengthInX(p)) {
                ++n;
                setFreqX(p, i, n, x);
                x = (int16_t) (x + rnd(r, 30, 80));
                const int m = rnd(r, lo, hi);
                setFreqY(p, i, n, (n & 1) != 0 ? -m : m);
            }
            setLastFreq(p, i, n);
            setFreqPoint(p, i, n + 1, 0, kEndX);
        }
    }

    // A corrupt record count can spin the original's two loops forever. Here they stop
    // once they have removed more than a list can hold.
    const auto removeWhile = [&](int i, int a, int b) {
        for (int guard = 0; ampX(p, i, a) >= ampX(p, i, b) && guard < kAmpRecordCount; ++guard)
            removeAmp(p, i, b);
    };

    if (s.attackRate != 0) {
        for (int i = 0; i < 16; ++i) {
            if (lastAmp(p, i) == 0) {
                setLastAmp(p, i, 1);
                setAmpPoint(p, i, 2, 0, kEndX);
            }
            const int x = s.attackRate == 1 ? rnd(r, 0, 5) : s.attackRate == 2 ? rnd(r, 10, 20) : rnd(r, 30, 80);
            setAmpPoint(p, i, 1, kLevelMax, x);
            removeWhile(i, 1, 2);
        }
    }

    if (s.decayRate != 0) {
        for (int i = 0; i < 16; ++i) {
            if (lastAmp(p, i) == 0)
                setAmpPoint(p, i, 1, kLevelMax, rnd(r, 5, 50));
            if (lastAmp(p, i) < 2) {
                setLastAmp(p, i, 2);
                setAmpY(p, i, 2, rnd(r, 30, 60));
                setAmpPoint(p, i, 3, 0, kEndX);
            }
            const int d = s.decayRate == 1 ? rnd(r, 5, 20) : s.decayRate == 2 ? rnd(r, 20, 70) : rnd(r, 80, 160);
            setAmpX(p, i, 2, ampX(p, i, 1) + d);
            removeWhile(i, 2, 3);
        }
    }

    if (s.secondaryLevel != 0) {
        for (int i = 0; i < 16; ++i) {
            if (lastAmp(p, i) == 0)
                setAmpPoint(p, i, 1, kLevelMax, rnd(r, 5, 20));
            if (lastAmp(p, i) < 2) {
                setLastAmp(p, i, 2);
                setAmpX(p, i, 2, ampX(p, i, 1) + rnd(r, 10, 30));
                setAmpPoint(p, i, 3, 0, kEndX);
            }
            const int y = s.secondaryLevel == 1 ? rnd(r, 60, 90) : s.secondaryLevel == 2 ? rnd(r, 30, 50) : rnd(r, 5, 20);
            setAmpY(p, i, 2, y);
            for (int n = 3; n <= lastAmp(p, i); ++n) {
                const int d = (int16_t) (ampY(p, i, n) - y);
                if ((d < 0 ? -d : d) > 20)
                    setAmpY(p, i, n, y);
            }
        }
    }

    if (s.fade != 0) {
        for (int i = 0; i < 16; ++i) {
            if (lastAmp(p, i) == 0)
                setAmpPoint(p, i, 1, kLevelMax, rnd(r, 5, 50));
            if (lastAmp(p, i) < 2) {
                setAmpX(p, i, 2, rnd(r, 30, 50));
                setAmpY(p, i, 2, rnd(r, 30, 60));
            }
            if (lastAmp(p, i) < 3) {
                setLastAmp(p, i, 3);
                setAmpPoint(p, i, 4, 0, kEndX);
            }
            const int last = lastAmp(p, i);
            setAmpX(p, i, last, lengthInX(p));
            if (s.fade == 1 || s.fade == 2) {
                setAmpY(p, i, last, 0);
                // Divides by the partial's index, and the first is 0.
                if (s.fade == 2)
                    for (int n = 3; n <= last; ++n)
                        setAmpX(p, i, n, divs(ampX(p, i, n), (int16_t) i));
            } else if (i < count) {
                const int16_t d = (int16_t) (count - i);
                setAmpY(p, i, last, divs(ampY(p, i, last - 1), d));
                // The original's loop counts from 3 to last but always divides the last
                // record, so that x is divided again on each pass.
                for (int n = 3; n <= last; ++n)
                    setAmpX(p, i, last, divs(ampX(p, i, last), d));
            }
            for (int n = 1; n <= last; ++n)
                if (ampX(p, i, n) >= ampX(p, i, n + 1))
                    setAmpX(p, i, n + 1, ampX(p, i, n) + 17 - i);
        }
    }

    if (s.doubling != 0) {
        int n = s.doubling == 1 ? rnd(r, 12, 40) : s.doubling == 2 ? rnd(r, 50, 125) : rnd(r, 150, 300);
        if (rnd(r, 0, 1) != 0)
            n = -n;
        const uint32_t scale = fp::toFloatBits(fp::add(fp::div(fp::fromLong(n), kTenThousand), kOne));
        for (int i = 16; i < kPartials; ++i) {
            std::memcpy(p.partial(i), p.partial(i - 16), kPartialSize);
            setRatio(p, i, fp::toLong(fp::mul(fp::fromLong(ratio(p, i)), fp::fromFloatBits(scale))));
            uint8_t* const src = &p.routing[(size_t) (i - 16) * 2];
            uint8_t* const dst = &p.routing[(size_t) i * 2];
            const int mod = (int8_t) src[1];
            if (mod > 16) {
                src[1] = dst[1] = 0;
                src[0] = dst[0] = 1;
            } else if (mod != 0) {
                dst[1] = (uint8_t) (mod + 16);
                dst[0] = src[0];
            } else {
                dst[1] = 0;
                dst[0] = 1;
            }
        }
    }

    if (s.percussion != 0) {
        const int i = 31;
        setFader(p, i, kFaderMax);
        setAmpY(p, i, 1, kLevelMax);
        setFreqY(p, i, 1, 0);
        setLastFreq(p, i, 0);
        setAmpY(p, i, 3, 0);
        setFreqX(p, i, 1, kEndX);
        if (s.percussion == 1) {
            p.partial(i)[kWave] = (uint8_t) rnd(r, 1, 3);
            setRatio(p, i, hzToRatio((uint16_t) rnd(r, 2000, 10000), wholeHz(p)));
            setLastAmp(p, i, 3);
            setAmpY(p, i, 4, 0);
            setAmpX(p, i, 1, 0);
            setAmpX(p, i, 2, rnd(r, 5, 20));
            setAmpY(p, i, 2, rnd(r, 10, 90));
            setAmpX(p, i, 3, rnd(r, 25, 60));
            setAmpX(p, i, 4, kEndX);
        } else if (s.percussion == 2) {
            setRatio(p, i, 0x10000 / rnd(r, 2, 5));
            setLastAmp(p, i, 2);
            setAmpX(p, i, 1, 1);
            setAmpX(p, i, 2, rnd(r, 5, 15));
            setAmpY(p, i, 2, 0);
            setAmpX(p, i, 3, kEndX);
        } else {
            p.partial(i)[kWave] = 4;
            setRatio(p, i, hzToRatio((uint16_t) rnd(r, 800, 4000), wholeHz(p)));
            setLastAmp(p, i, 2);
            setAmpX(p, i, 1, 0);
            setAmpX(p, i, 2, rnd(r, 5, 15));
            setAmpY(p, i, 2, 0);
            setAmpX(p, i, 3, kEndX);
        }
    }
}

// ---- File > New ---------------------------------------------------------------

void initPartials(Patch& p)
{
    for (int i = 0; i < kPartials; ++i) {
        setFader(p, i, 0);
        setRatio(p, i, (i + 1) << 16);
        p.partial(i)[kWave] = 1;
        p.partial(i)[kScale] = 1;
        clearAmp(p, i);
        clearFreq(p, i);
        p.routing[(size_t) i * 2] = 1;
        p.routing[(size_t) i * 2 + 1] = 0;
    }
}

void newPatch(Patch& p, Sampler sampler)
{
    initPartials(p);
    p.frequency = 0x1B80000; // 440 Hz
    p.length = 20000;
    p.nameField[0] = 0;
    switch (sampler) {
    case Sampler::AkaiS612: p.rate = 26320; break;
    case Sampler::AkaiS700: case Sampler::AkaiS900: case Sampler::SequentialP2000: case Sampler::MmaSds: p.rate = 31250; break;
    case Sampler::CasioFZ1: p.rate = 36000; break;
    case Sampler::EmuEmax: case Sampler::EmuSP1200: p.rate = 26000; break;
    case Sampler::EnsoniqMirage: p.rate = 29412; break;
    case Sampler::KorgDSS1: p.rate = 32000; break;
    case Sampler::RolandMKS100: case Sampler::RolandS10: case Sampler::RolandS220: case Sampler::RolandS50: p.rate = 30000; break;
    case Sampler::OberheimDPX1: break;
    }
}

// ---- Break points -------------------------------------------------------------

bool findPoint(const Patch& p, const SliceList& h, Graph g, int partial, int x, int16_t& index)
{
    const int16_t want = (int16_t) x;
    const int current = g == Graph::TimeSlices ? 0 : partial;
    const int last = g == Graph::Freq ? lastFreq(p, current) : g == Graph::Harmonics ? h.count : lastAmp(p, current);
    for (index = 1; index <= last + 1; ++index) {
        const int16_t v = g == Graph::Freq ? freqX(p, current, index) : g == Graph::Harmonics ? h.x[(size_t) index] : ampX(p, current, index);
        if (v == want) return true;
        if (v > want) return false;
    }
    index = 0;
    return false;
}

bool insertPoint(Patch& p, SliceList& h, Graph g, int partial, int16_t& index, bool force, int sliceX)
{
    if (index == 0)
        return true;
    switch (g) {
    case Graph::Amp:
        if (lastAmp(p, partial) < kMaxAmpSegments) {
            setLastAmp(p, partial, lastAmp(p, partial) + 1);
            shiftAmpUp(p, partial, lastAmp(p, partial) + 1, index);
        } else if (force) {
            shiftAmpUp(p, partial, lastAmp(p, partial), index);
        } else {
            index = 0;
            return false;
        }
        break;
    case Graph::Freq:
        if (lastFreq(p, partial) >= kMaxFreqSegments) {
            index = 0;
            return false;
        }
        setLastFreq(p, partial, lastFreq(p, partial) + 1);
        for (int n = lastFreq(p, partial) + 1; n > index; --n) {
            setFreqX(p, partial, n, freqX(p, partial, n - 1));
            setFreqY(p, partial, n, freqY(p, partial, n - 1));
        }
        break;
    case Graph::Harmonics: {
        int from;
        if (h.count < kMaxSlices) {
            ++h.count;
            from = h.count + 1;
        } else if (force) {
            from = h.count;
        } else {
            index = 0;
            return false;
        }
        for (int n = from; n > index; --n) {
            h.x[(size_t) n] = h.x[(size_t) n - 1];
            h.y[(size_t) n] = h.y[(size_t) n - 1];
        }
        break;
    }
    case Graph::TimeSlices:
        if (lastAmp(p, 0) >= kMaxAmpSegments) {
            index = 0;
            return false;
        }
        for (int i = 0; i < kPartials; ++i) {
            setLastAmp(p, i, lastAmp(p, i) + 1);
            shiftAmpUp(p, i, lastAmp(p, i) + 1, index);
            setAmpY(p, i, index, ampAt(p, i, sliceX));
            setAmpX(p, i, index, sliceX);
        }
        break;
    }
    return true;
}

void removePoint(Patch& p, SliceList& h, Graph g, int partial, int index)
{
    switch (g) {
    case Graph::Amp:
        removeAmp(p, partial, index);
        break;
    case Graph::Freq:
        for (int n = index; n <= lastFreq(p, partial); ++n) {
            setFreqX(p, partial, n, freqX(p, partial, n + 1));
            setFreqY(p, partial, n, freqY(p, partial, n + 1));
        }
        setLastFreq(p, partial, lastFreq(p, partial) - 1);
        break;
    case Graph::Harmonics:
        removeHarmonic(h, index);
        break;
    case Graph::TimeSlices:
        for (int i = 0; i < kPartials; ++i)
            removeAmp(p, i, index);
        break;
    }
}

int16_t ampAt(const Patch& p, int partial, int x)
{
    const int last = lastAmp(p, partial);
    for (int n = 1; n <= last + 1; ++n) {
        const int16_t nx = ampX(p, partial, n);
        if (nx == (int16_t) x) return ampY(p, partial, n);
        if (nx > (int16_t) x)
            return interpolate(ampY(p, partial, n - 1), ampX(p, partial, n - 1), ampY(p, partial, n), nx, x);
    }
    return (int16_t) (last + 2);
}

int16_t harmonicAt(const SliceList& h, int x)
{
    for (int n = 1; n <= h.count + 1; ++n) {
        const int16_t nx = h.x[(size_t) n];
        if (nx == (int16_t) x) return h.y[(size_t) n];
        if (nx > (int16_t) x)
            return interpolate(h.y[(size_t) n - 1], h.x[(size_t) n - 1], h.y[(size_t) n], nx, x);
    }
    return (int16_t) (h.count + 2);
}

// ---- Time slices --------------------------------------------------------------

void partialsToSlices(Patch& p, SliceList& h)
{
    // Faders go into the envelopes, and then all read 63.
    for (int i = 0; i < kPartials; ++i) {
        if (p.fader(i) != 0) {
            for (int n = 1; n <= lastAmp(p, i); ++n)
                setAmpY(p, i, n, mulDiv(ampY(p, i, n), p.fader(i), kFaderMax));
        } else {
            clearAmp(p, i);
        }
        setFader(p, i, kFaderMax);
    }

    // The harmonic curve's times: all of every partial's, in order.
    h.count = 0;
    h.y[0] = 0;
    h.x[0] = 0;
    h.y[1] = 0;
    h.x[1] = kEndX;
    for (int i = 0; i < kPartials; ++i) {
        for (int n = 1; n <= lastAmp(p, i); ++n) {
            const int16_t x = ampX(p, i, n);
            for (int k = 1; k <= kMaxSlices; ++k) {
                if (h.x[(size_t) k] == x)
                    break;
                if (h.x[(size_t) k] > x) {
                    int16_t at = (int16_t) k;
                    insertPoint(p, h, Graph::Harmonics, 0, at, true);
                    h.x[(size_t) k] = x;
                    break;
                }
            }
        }
    }

    // Every partial gets a record at each of those times.
    for (int i = 0; i < kPartials; ++i) {
        for (int k = 1; k <= h.count; ++k) {
            const int16_t hx = h.x[(size_t) k];
            for (int n = 1; n <= kMaxSlices; ++n) {
                const int16_t x = ampX(p, i, n);
                if (x == hx)
                    break;
                if (x > hx) {
                    int16_t at = (int16_t) n;
                    insertPoint(p, h, Graph::Amp, i, at, true);
                    setAmpY(p, i, n, ampAt(p, i, hx));
                    setAmpX(p, i, n, hx);
                    break;
                }
            }
        }
    }

    // Each timbre event is normalized to 103, and the harmonic curve keeps the levels.
    int16_t peak = 0;
    for (int k = 1; k <= h.count; ++k) {
        int16_t largest = 0, sum = 0;
        for (int i = 0; i < kPartials; ++i) {
            const int16_t y = ampY(p, i, k);
            if (y > largest) largest = y;
            sum = (int16_t) (sum + y);
        }
        h.y[(size_t) k] = sum;
        if (sum > peak) peak = sum;
        if (largest != 0)
            for (int i = 0; i < kPartials; ++i)
                setAmpY(p, i, k, mulDiv(ampY(p, i, k), kLevelMax, largest));
    }
    if (peak != 0)
        for (int k = 1; k <= h.count; ++k)
            h.y[(size_t) k] = (int16_t) ((int32_t) h.y[(size_t) k] * kLevelMax / peak);

    // Records that have become unnecessary go. After a removal the same place is tested
    // again.
    for (int k = 1; k <= h.count;) {
        if (nearLine(h.y[(size_t) k - 1], h.x[(size_t) k - 1], h.y[(size_t) k], h.x[(size_t) k], h.y[(size_t) k + 1], h.x[(size_t) k + 1]))
            removeHarmonic(h, k);
        else
            ++k;
    }
    for (int k = 1; k <= lastAmp(p, 0);) {
        bool all = true;
        for (int i = 0; i < kPartials && all; ++i)
            all = ampNearLine(p, i, k);
        if (all) {
            for (int i = 0; i < kPartials; ++i)
                removeAmp(p, i, k);
        } else {
            ++k;
        }
    }
}

bool slicesToPartials(Patch& p, SliceList& h)
{
    bool ok = true;
    // Every harmonic curve time gets a timbre event, if there is room.
    for (int k = 1; k <= h.count; ++k) {
        if (lastAmp(p, 0) >= kMaxAmpSegments)
            continue;
        const int16_t hx = h.x[(size_t) k];
        for (int n = 1; n <= lastAmp(p, 0) + 1; ++n) {
            const int16_t x = ampX(p, 0, n);
            if (x > hx) {
                for (int i = 0; i < kPartials; ++i) {
                    int16_t at = (int16_t) n;
                    if (!insertPoint(p, h, Graph::Amp, i, at, false))
                        ok = false;
                    const int16_t y = ampAt(p, i, hx);
                    setAmpY(p, i, n, y);
                    setAmpX(p, i, n, hx);
                }
                break;
            }
            if (x == hx)
                break;
        }
    }

    // Each event's levels are shared out by the harmonic curve.
    for (int k = 1; k <= lastAmp(p, 0); ++k) {
        int16_t sum = 0;
        for (int i = 0; i < kPartials; ++i)
            sum = (int16_t) (sum + ampY(p, i, k));
        for (int i = 0; i < kPartials; ++i) {
            int16_t v = 0;
            if (sum != 0)
                v = mulDiv(ampY(p, i, k), harmonicAt(h, ampX(p, 0, k)), sum);
            setAmpY(p, i, k, v);
        }
    }

    // Levels go back into faders.
    for (int i = 0; i < kPartials; ++i) {
        int16_t largest = 0;
        for (int n = 1; n <= lastAmp(p, i); ++n)
            if (ampY(p, i, n) > largest) largest = ampY(p, i, n);
        if (largest != 0) {
            for (int n = 1; n <= lastAmp(p, i); ++n)
                setAmpY(p, i, n, mulDiv(ampY(p, i, n), kLevelMax, largest));
            setFader(p, i, mulDiv(kFaderMax, largest, kLevelMax));
        } else {
            clearAmp(p, i);
            setFader(p, i, 0);
        }
    }

    for (int i = 0; i < kPartials; ++i) {
        for (int n = 1; n <= lastAmp(p, i);) {
            if (ampNearLine(p, i, n))
                removeAmp(p, i, n);
            else
                ++n;
        }
    }

    int16_t largest = 0;
    for (int i = 0; i < kPartials; ++i)
        if (p.fader(i) > largest) largest = (int16_t) p.fader(i);
    if (largest != 0)
        for (int i = 0; i < kPartials; ++i)
            setFader(p, i, mulDiv(p.fader(i), kFaderMax, largest));
    return ok;
}

void setSliceLevel(Patch& p, int partial, int sliceX, int level)
{
    for (int n = 1; n <= lastAmp(p, partial); ++n) {
        if (ampX(p, partial, n) == (int16_t) sliceX) {
            setAmpY(p, partial, n, level);
            return;
        }
    }
}

void moveSlice(Patch& p, int index, int x)
{
    for (int i = 0; i < kPartials; ++i)
        setAmpX(p, i, index, x);
}

// ---- Edit > Copy and Paste, Clear ---------------------------------------------

void copyPartials(Patch& p, const PartialFields& f, int from, int first, int last)
{
    --from;
    --first;
    --last;
    if (from > 31 || from < 0) from = 0;
    if (first > 31 || first < 0) first = 0;
    if (last > 31) last = 31;
    else if (last < first) last = first;
    const uint8_t* const src = p.partial(from);
    for (int i = first; i <= last; ++i) {
        uint8_t* const dst = p.partial(i);
        if (f.ampEnvelope)
            std::memmove(dst + kLastAmp, src + kLastAmp, 0x151 * 2);
        if (f.frequencyContour) {
            std::memmove(dst + kLastFreq, src + kLastFreq, 0x89 * 2);
            dst[kScale] = src[kScale];
        }
        if (f.waveType)
            dst[kWave] = src[kWave];
        if (f.frequencyRatio)
            std::memmove(dst + kRatio, src + kRatio, 4);
        if (f.faderLevel)
            std::memmove(dst + kFader, src + kFader, 2);
    }
}

void clearPartials(Patch& p, const PartialFields& f, int first, int last)
{
    --first;
    --last;
    if (first > 31 || first < 0) first = 0;
    if (last > 31) last = 31;
    else if (last < first) last = first;
    for (int i = first; i <= last; ++i) {
        if (f.ampEnvelope)
            clearAmp(p, i);
        if (f.frequencyContour) {
            clearFreq(p, i);
            p.partial(i)[kScale] = 1;
        }
        if (f.waveType)
            p.partial(i)[kWave] = 1;
        if (f.frequencyRatio)
            setRatio(p, i, (i + 1) << 16);
        if (f.faderLevel)
            setFader(p, i, 0);
    }
}

void copyTimbre(Patch& p, int from, int first, int last)
{
    const int count = lastAmp(p, 0);
    if (from > count || from < 1) from = 0;
    if (first > count || first < 1) first = 0;
    if (last > count || last < 1) last = count;
    for (int t = first; t <= last; ++t)
        for (int i = 0; i < kPartials; ++i)
            setAmpY(p, i, t, ampY(p, i, from));
}

void clearTimbre(Patch& p, int first, int last)
{
    const int count = lastAmp(p, 0);
    if (first > count || first < 1) first = 0;
    if (last > count || last < 1) last = count;
    for (int t = first; t <= last; ++t)
        for (int i = 0; i < kPartials; ++i)
            setAmpY(p, i, t, 0);
}

// ---- Edit > FM Patching -------------------------------------------------------

bool isModulator(const Patch& p, int partial)
{
    for (int i = 0; i < kPartials; ++i)
        if (p.modulator(i) == partial + 1)
            return true;
    return false;
}

void fmSet(Patch& p, int modulator, int target)
{
    const int old = p.modulator(target) - 1;
    p.routing[(size_t) target * 2 + 1] = (uint8_t) (modulator + 1);
    if (old >= 0 && !isModulator(p, old))
        p.routing[(size_t) old * 2] = 1;
    p.routing[(size_t) modulator * 2] = 0;
}

void fmClear(Patch& p, int modulator, int target)
{
    if (p.modulator(target) != modulator + 1)
        return;
    p.routing[(size_t) target * 2 + 1] = 0;
    if (!isModulator(p, modulator))
        p.routing[(size_t) modulator * 2] = 1;
}

void fmClearAll(Patch& p)
{
    for (int i = 0; i < kPartials; ++i) {
        p.routing[(size_t) i * 2 + 1] = 0;
        p.routing[(size_t) i * 2] = 1;
    }
}

void fmToggleMix(Patch& p, int partial)
{
    // The dialog disables the checkbox for anything else (F002A56).
    if (isModulator(p, partial))
        p.routing[(size_t) partial * 2] = p.routing[(size_t) partial * 2] == 0 ? 1 : 0;
}

} // namespace softsynth
