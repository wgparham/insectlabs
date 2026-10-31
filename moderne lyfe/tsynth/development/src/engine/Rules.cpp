#include "Rules.h"
#include "MacMath.h"

#include <algorithm>

namespace turbosynth::rules {

using namespace mac;

namespace {

Module* at(Patch& p, int i) { return i >= 1 && i <= (int) p.modules.size() ? &p.modules[(size_t) i - 1] : nullptr; }

std::vector<int> inputsOf(const Patch& p, int i)
{
    std::vector<int> v;
    for (const auto& c : p.connections) if (c.to == i) v.push_back(c.from);
    return v;
}

// Message 5: set the length, then the modules fed hear of it.
void setLength(Patch& p, int i, int32_t len, const Options& o)
{
    Module* m = at(p, i);
    if (!m || len < 1) return;
    m->length = len;
    lengthChanged(p, i, o);
}

// Message 9: take the rate and pass it on (unconditionally; the sender checked the option).
void takeRate(Patch& p, int i, int32_t rate)
{
    Module* m = at(p, i);
    if (!m) return;
    m->sampleRate = rate;
    for (const auto& c : p.connections) if (c.from == i) takeRate(p, c.to, rate);
}

} // namespace

void lengthChanged(Patch& patch, int index, const Options& o)
{
    const Module* m = at(patch, index);
    if (!m) return;
    const int32_t len = m->length;
    const std::vector<Connection> cords = patch.connections; // the handlers may change lengths, not cords
    for (const auto& c : cords) if (c.from == index) inputLengthChanged(patch, c.to, len, o);
}

void inputLengthChanged(Patch& patch, int index, int32_t L, const Options& o)
{
    Module* m = at(patch, index);
    if (!m) return;
    const bool dyn = o.dynamicLength;
    int32_t len = L;
    switch (m->type) {
    case kDelay: // F23_02794 (TDELAY): the delay time, repeated feedback/5 times
        if (dyn) {
            const int32_t delay = ldiv(lmul((int16_t) (m->p[0] * 100 + m->p[1]), m->sampleRate), 10000);
            len = L + lmul(divs(m->p[2], 5), delay);
        }
        break;
    case kResonator: break; // TDELAY too, but the formula is the Delay's only
    case kStretchExpand: // F23_02828 (TCOMPAND)
        if (dyn) len = L + fixMul(L, fixRatio(25, (int16_t) (101 - m->p[3])));
        break;
    case kStretchCompress:
        if (dyn && m->p[3] > 80) len = ldiv(lmul(L, 110 - m->p[3]), 40);
        break;
    case kPitchShifter: // F23_028C8: L * -semitones / 6, never shorter than L
        if (dyn) len = std::max(L, ldiv(lmul(L, -m->p[0]), 6));
        break;
    case kDiffuser: // F23_02936: the same formula on the Size coarse value, so it stays L
        if (dyn) len = std::max(L, ldiv(lmul(L, -m->p[0]), 6));
        break;
    case kOutput: case kOutputR: break; // F23_018F4: the output always follows its input
    case kMixer: { // F23_02612: with Inherit length, the longest input (F23_025B8)
        if (!o.inheritLength) return;
        len = 0;
        for (int in : inputsOf(patch, index)) if (const Module* s = at(patch, in)) len = std::max(len, s->length);
        break; }
    case kAmpEnv: case kFilter: case kSpectralInverter: case kPitchEnv: { // F23_02346: with Inherit length
        if (!o.inheritLength) return;
        m->length = L;
        const int32_t end = ldiv(lmul(L, 1000), m->sampleRate); // F40_0005A drops points past the end
        m->points.erase(std::remove_if(m->points.begin(), m->points.end(), [end](const EnvPoint& pt) { return pt.timeMs > end; }), m->points.end());
        lengthChanged(patch, index, o);
        return; }
    default: // TBLOCK (F23_014F4): only with Inherit length
        if (!o.inheritLength) return;
        break;
    }
    setLength(patch, index, len, o);
}

void rateChanged(Patch& patch, int index, const Options& o)
{
    const Module* m = at(patch, index);
    if (!m || !o.inheritRate) return;
    for (const auto& c : patch.connections) if (c.from == index) takeRate(patch, c.to, m->sampleRate);
}

void connected(Patch& patch, int from, int to, const Options& o)
{
    const Module* src = at(patch, from);
    Module* dst = at(patch, to);
    if (!src || !dst) return;
    if (dst->type == kMixer && o.inheritLength) inputLengthChanged(patch, to, 0, o); // message 11: the mixer re-measures
    if (o.inheritLength) inputLengthChanged(patch, to, src->length, o);
    if (o.inheritRate) takeRate(patch, to, src->sampleRate);
}

void parametersChanged(Patch& patch, int index, const Options& o)
{
    const Module* m = at(patch, index);
    if (!m || !o.dynamicLength) return;
    switch (m->type) {
    case kDelay: case kStretchExpand: case kStretchCompress: case kPitchShifter: case kDiffuser: break; // the Delay window tests for the Delay type
    default: return;
    }
    const auto ins = inputsOf(patch, index);
    if (ins.size() != 1) return;
    if (const Module* s = at(patch, ins[0])) inputLengthChanged(patch, index, s->length, o);
}

} // namespace turbosynth::rules
