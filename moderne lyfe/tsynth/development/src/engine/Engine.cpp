#include "Engine.h"
#include "MacMath.h"

#include <algorithm>
#include <cstdlib>
#include <filesystem>

namespace turbosynth {

using namespace mac;

namespace {

// Samples for t milliseconds at sr (LMUL then LDIV).
int32_t msToSamples(int32_t t, int32_t sr) { return ldiv(lmul(t, sr), 1000); }

// Halve a (sample count, amount) pair until the count fits in 16 bits, as the
// segment builders do before FixRatio.
void fitCount(int32_t& count, int16_t& amount)
{
    while (count > 0x7FFF) { count = ldiv(count, 2); amount = divs(amount, 2); }
}

// Envelope segment: start value, per-sample slope, absolute end sample.
struct Segment { int32_t value = 0, slope = 0, end = 0; };

// Linear interpolation between in[i] and in[i+1] with an unsigned 16-bit
// fraction, the way the resampling kernels do it (sign split around mulu).
int16_t interp(int16_t a, int16_t b, uint16_t f)
{
    int32_t d = (int32_t) b - a;
    if (d >= 0) return (int16_t) (a + (int32_t) (((uint32_t) d * f) >> 16));
    return (int16_t) (a - (int32_t) (((uint32_t) (-d) * f) >> 16));
}

int16_t at(const std::vector<int16_t>& v, int64_t i) { return i >= 0 && i < (int64_t) v.size() ? v[(size_t) i] : 0; }

// Normalize (F29_001BA): scale to peak 7FFF.
void normalizeBuffer(std::vector<int16_t>& b, int32_t len)
{
    if (len <= 0) return;
    int16_t lo = 32767, hi = -32768;
    for (int32_t i = 0; i < len; ++i) { lo = std::min(lo, b[(size_t) i]); hi = std::max(hi, b[(size_t) i]); }
    int16_t peak = hi;
    if (lo == -32768) peak = 0x7FFF;
    else if ((int16_t) -lo > peak) peak = (int16_t) -lo;
    if (peak < 0x7FFF && peak > 0)
        for (int32_t i = 0; i < len; ++i) {
            const int32_t x = b[(size_t) i];
            const int32_t n = (int32_t) ((uint32_t) (uint16_t) x << 16 >> 1) - x; // (zero-extended x << 16) >> 1, minus x
            b[(size_t) i] = divs(n, peak);
        }
}

// out = (in*g1 + out*g0)*2 >> 16 over the shorter length (F29_00004).
void blend(std::vector<int16_t>& out, int32_t outLen, const std::vector<int16_t>& in, int32_t inLen, int16_t gOut, int16_t gIn)
{
    for (int32_t i = 0; i < outLen; ++i) {
        if (i < inLen) out[(size_t) i] = q15(muls(at(in, i), gIn) + muls(out[(size_t) i], gOut));
        else out[(size_t) i] = q15(muls(out[(size_t) i], gOut));
    }
}

int16_t pct7fff(int v) { return (int16_t) ldiv(lmul(v, 0x7FFF), 100); }

} // namespace

Engine::Engine(const Patch& p, RenderOptions o) : patch(p), options(std::move(o))
{
    buffers.resize(patch.modules.size());
    done.assign(patch.modules.size(), false);
    busy.assign(patch.modules.size(), false);
}

std::vector<int> Engine::inputsOf(int index) const
{
    std::vector<int> v;
    for (const auto& c : patch.connections)
        if (c.to == index && c.from >= 1 && c.from <= (int) patch.modules.size()) v.push_back(c.from);
    return v;
}

const std::vector<int16_t>& Engine::moduleOutput(int index)
{
    if (index < 1 || index > (int) patch.modules.size()) return empty;
    compute(index);
    return buffers[(size_t) index - 1];
}

const std::vector<int16_t>& Engine::inputBuffer(int index, int which, int32_t& length)
{
    const auto in = inputsOf(index);
    if (which < 1 || which > (int) in.size()) { length = 0; return empty; }
    const auto& b = moduleOutput(in[(size_t) which - 1]);
    length = patch.modules[(size_t) in[(size_t) which - 1] - 1].length;
    length = std::min<int32_t>(length, (int32_t) b.size());
    return b;
}

void Engine::compute(int index)
{
    const size_t k = (size_t) index - 1;
    if (done[k] || busy[k]) return;
    busy[k] = true;
    Module& m = patch.modules[k];
    auto& out = buffers[k];
    out.assign((size_t) std::max(0, m.length), 0);
    int32_t inLen = 0;
    const auto& in = inputBuffer(index, 1, inLen);
    bool norm = m.normalize;
    switch (m.type) {
    case kOscillator: oscillator(m, out); norm = false; break;
    case kSample: sample(m, out); norm = false; break;
    case kNoise: noise(m, out); norm = false; break;
    case kAmpEnv: ampEnv(m, out, in, inLen); break;
    case kFilter: filter(m, out, in, inLen); break;
    case kSpectralInverter: spectralInverter(m, out, in, inLen); break;
    case kPitchEnv: pitchEnv(m, out, in, inLen); break;
    case kWaveshaper: waveshaper(m, out, in, inLen); break;
    case kModulator: modulator(index, m, out); break;
    case kMixer: mixer(index, m, out); break;
    case kDelay: delayLike(m, out, in, inLen, false); break;
    case kResonator: delayLike(m, out, in, inLen, true); break;
    case kStretchExpand: stretch(m, out, in, inLen, true); break;
    case kStretchCompress: stretch(m, out, in, inLen, false); break;
    case kPitchShifter: pitchShifter(m, out, in.empty() || inputsOf(index).size() != 1 ? empty : in, inputsOf(index).size() == 1 ? inLen : 0); break;
    case kDiffuser: diffuser(m, out, in, inLen); break;
    case kOutput: case kOutputR: // the result is the input's buffer
        out.assign(in.begin(), in.begin() + std::min<size_t>(in.size(), (size_t) std::max(0, inLen)));
        norm = false;
        break;
    default: break;
    }
    if (norm) normalizeBuffer(out, m.length);
    busy[k] = false;
    done[k] = true;
}

RenderResult Engine::render()
{
    RenderResult r;
    int left = 0, right = 0;
    for (int i = 1; i <= (int) patch.modules.size(); ++i) {
        const auto t = patch.modules[(size_t) i - 1].type;
        if (t == kOutput && !left) left = i;
        if (t == kOutputR && !right) right = i;
    }
    if (left) {
        r.left = moduleOutput(left);
        r.sampleRate = patch.modules[(size_t) left - 1].sampleRate;
        // TurboSynth 2.0 documents store 0 for the output's rate: use its source's.
        const auto src = inputsOf(left);
        if (r.sampleRate <= 0 && !src.empty()) r.sampleRate = patch.modules[(size_t) src[0] - 1].sampleRate;
        if (r.sampleRate <= 0) r.sampleRate = 44100;
    }
    if (right) r.right = moduleOutput(right);
    if (!r.right.empty()) r.right.resize(std::max(r.left.size(), r.right.size()), 0), r.left.resize(r.right.size(), 0);
    r.warnings = warnings;
    return r;
}

// ---------------------------------------------------------------------------
void Engine::oscillator(Module& m, std::vector<int16_t>& out)
{
    const int32_t len = m.length;
    // Duration in ms; later waves are removed.
    const int32_t durMs = ldiv(lmul(1000, len), m.sampleRate);
    std::vector<Waveform> w;
    for (const auto& x : m.waveforms) if (x.timeMs <= durMs) w.push_back(x);
    const int n = (int) w.size();
    if (n == 0) return; // buffer cleared
    // Bubble pass by time; equal times push the later wave on by 10 ms.
    for (int pass = 1; pass <= n - 1; ++pass)
        for (int k = 0; k + 1 < n; ++k) {
            if (w[(size_t) k].timeMs > w[(size_t) k + 1].timeMs) std::swap(w[(size_t) k], w[(size_t) k + 1]);
            else if (w[(size_t) k].timeMs == w[(size_t) k + 1].timeMs) w[(size_t) k + 1].timeMs = (int16_t) (w[(size_t) k + 1].timeMs + 10);
        }
    for (auto& x : w) x.samples[256] = x.samples[0];
    struct OscSeg { int32_t slope, end; int a, b; };
    std::vector<OscSeg> segs;
    for (int k = 0; k + 1 < n; ++k) {
        int32_t d = k == 0 ? w[1].timeMs : (int32_t) (w[(size_t) k + 1].timeMs - w[(size_t) k].timeMs);
        d = msToSamples(d, m.sampleRate);
        int16_t num = 0x7FFF;
        fitCount(d, num);
        segs.push_back({ fixRatio(num, loWord(d)), msToSamples(w[(size_t) k + 1].timeMs, m.sampleRate), k, k + 1 });
    }
    segs.push_back({ 0, len, n - 1, n - 1 });

    // Phase increment: FixMul(freq, FixRatio(2000h, sr)), halving while sr > 7FFF.
    int16_t sc = 0x2000;
    int32_t s = m.sampleRate;
    while (s > 0x7FFF) { s = ldiv(s, 2); sc = divs(sc, 2); }
    const int32_t inc = fixMul(m.oscFreq, fixRatio(sc, loWord(s)));

    auto expand = [](const Waveform& wf, std::vector<int16_t>& t) {
        t.resize(4096);
        size_t o = 0;
        for (int i = 0; i < 256; ++i) {
            int16_t v = wf.samples[(size_t) i];
            const int16_t d = divs((int32_t) wf.samples[(size_t) i + 1] - wf.samples[(size_t) i], 16);
            for (int j = 0; j < 16; ++j) { t[o++] = v; v = (int16_t) (v + d); }
        }
    };
    std::vector<int16_t> A, B;
    expand(w[(size_t) segs[0].a], A);
    expand(w[(size_t) segs[0].b], B);
    uint32_t phase = 0;
    int32_t pos = 0;
    for (size_t si = 0; si < segs.size(); ++si) {
        if (si > 0) { std::swap(A, B); expand(w[(size_t) segs[si].b], B); }
        int32_t gA = 0x7FFF0000, gB = 0;
        const int32_t end = std::min(segs[si].end, len);
        while (pos < end) {
            const size_t idx = ((phase >> 16) & 0x1FFE) >> 1;
            const int32_t sum = muls(hiWord(gA), A[idx]) + muls(hiWord(gB), B[idx]);
            const int16_t v = hiWord(sum);
            out[(size_t) pos++] = (int16_t) (v + v);
            phase += (uint32_t) inc;
            gA -= segs[si].slope;
            gB += segs[si].slope;
        }
        if (pos >= len) break;
    }
}

void Engine::sample(Module& m, std::vector<int16_t>& out)
{
    std::vector<int16_t> data = m.embedded;
    if (data.empty()) {
        std::string nm = m.name;
        while (!nm.empty() && nm.back() == ' ') nm.pop_back();
        bool found = false;
        for (const auto& folder : options.soundFolders) {
            for (const char* sub : { "", "Sound files", "Sound Files" }) {
                const auto p = std::filesystem::u8path(folder) / sub / std::filesystem::u8path(nm);
                std::error_code ec;
                if (std::filesystem::is_regular_file(p, ec) && loadSoundDesigner(p.u8string(), data, nullptr)) { found = true; break; }
            }
            if (found) break;
        }
        if (!found) warnings.push_back("Sound file not found for sample module '" + nm + "'");
    }
    for (size_t i = 0; i < out.size() && i < data.size(); ++i) out[i] = data[i];
}

void Engine::ampEnv(Module& m, std::vector<int16_t>& out, const std::vector<int16_t>& in, int32_t inLen)
{
    const auto& p = m.points;
    std::vector<Segment> segs;
    for (size_t k = 0; k + 1 < p.size(); ++k) {
        Segment s;
        s.value = fixRatio((int16_t) muls(p[k].level, 0x147), 1);
        int16_t diff = (int16_t) muls((int16_t) (p[k + 1].level - p[k].level), 0x147);
        int32_t ds = msToSamples((int16_t) (p[k + 1].timeMs - p[k].timeMs), m.sampleRate);
        fitCount(ds, diff);
        s.slope = fixRatio(diff, loWord(ds));
        s.end = msToSamples(p[k + 1].timeMs, m.sampleRate);
        segs.push_back(s);
    }
    if (!p.empty()) segs.push_back({ fixRatio((int16_t) muls(p.back().level, 0x147), 1), 0, m.length });
    const int32_t len = m.length;
    int32_t pos = 0;
    for (const auto& s : segs) {
        int32_t g = s.value;
        while (pos < len && pos < s.end && pos < inLen) { out[(size_t) pos] = q15(muls(hiWord(g), in[(size_t) pos])); ++pos; g += s.slope; }
        if (pos >= inLen || pos >= len) break;
    }
    while (pos < len) out[(size_t) pos++] = 0;
}

void Engine::spectralInverter(Module& m, std::vector<int16_t>& out, const std::vector<int16_t>& in, int32_t inLen)
{
    const auto& p = m.points;
    auto g = [](int lv) { return (int32_t) 0x7FFF - ldiv(lmul(lv, 0xFFFE), 100); };
    std::vector<Segment> segs;
    for (size_t k = 0; k + 1 < p.size(); ++k) {
        Segment s;
        s.value = fixRatio((int16_t) g(p[k].level), 1);
        int32_t d = g(p[k + 1].level) - g(p[k].level);
        int32_t ds = msToSamples((int16_t) (p[k + 1].timeMs - p[k].timeMs), m.sampleRate);
        while (d > 0x7FFF || d < -0x8000 || ds > 0x7FFF) { d = ldiv(d, 2); ds = ldiv(ds, 2); }
        s.slope = fixRatio((int16_t) d, loWord(ds));
        s.end = msToSamples(p[k + 1].timeMs, m.sampleRate);
        segs.push_back(s);
    }
    if (!p.empty()) segs.push_back({ fixRatio((int16_t) g(p.back().level), 1), 0, m.length });
    const uint8_t step = m.envMode == 1 ? 0x80 : 0x40;
    uint8_t ph = 0;
    int32_t pos = 0;
    const int32_t len = m.length;
    for (const auto& s : segs) {
        int32_t gain = s.value;
        while (pos < len && pos < s.end && pos < inLen) {
            const int16_t x = in[(size_t) pos];
            if (ph == 0) out[(size_t) pos] = x;
            else if (ph == 0x80) out[(size_t) pos] = q15(muls(hiWord(gain), x));
            else out[(size_t) pos] = q15(muls((int16_t) (((int32_t) hiWord(gain) + 0x7FFF) >> 1), x));
            ++pos; gain += s.slope; ph = (uint8_t) (ph + step);
        }
        if (pos >= inLen || pos >= len) break;
    }
    while (pos < len) out[(size_t) pos++] = 0;
}

void Engine::filter(Module& m, std::vector<int16_t>& out, const std::vector<int16_t>& in, int32_t inLen)
{
    const auto& p = m.points;
    auto coef = [](int lv) { return fixRatio((int16_t) (103 - lv), 2); };
    std::vector<Segment> segs;
    for (size_t k = 0; k + 1 < p.size(); ++k) {
        Segment s;
        s.value = coef(p[k].level);
        int32_t d = coef(p[k + 1].level) - s.value;
        int32_t ds = msToSamples((int16_t) (p[k + 1].timeMs - p[k].timeMs), m.sampleRate);
        while (ds > 0x7FFF) { ds = ldiv(ds, 2); d = ldiv(d, 2); }
        s.slope = fixDiv(d, fixRatio(loWord(ds), 1));
        s.end = msToSamples(p[k + 1].timeMs, m.sampleRate);
        segs.push_back(s);
    }
    if (!p.empty()) segs.push_back({ coef(p.back().level), 0, m.length });
    const int32_t len = m.length;
    if (segs.empty()) return;
    int32_t c = segs[0].value, slope = segs[0].slope;
    int cnt = hiWord(c);
    int64_t tail = 0, head = 0;
    int32_t S = 0, S1 = 0;
    for (int i = 0; i < cnt; ++i) { S += at(in, head); S1 += at(in, head); ++head; }
    int16_t nxt = at(in, head++);
    S1 += nxt;
    int32_t pos = 0;
    size_t si = 0;
    while (pos < len) {
        if (head > inLen) break; // the head reached the input's end
        if (pos >= segs[si].end) {
            if (si + 1 >= segs.size()) break;
            ++si; c = segs[si].value; slope = segs[si].slope;
            continue;
        }
        const int16_t a = divs(S, (int16_t) cnt);
        int16_t o = a;
        const int16_t b = divs(S1, (int16_t) (cnt + 1));
        o = (int16_t) (o + q15(muls((int16_t) (b - a), (int16_t) ((uint16_t) loWord(c) >> 1))));
        out[(size_t) pos++] = o;
        const int16_t x = at(in, tail++);
        S -= x; S1 -= x; S += nxt; nxt = at(in, head++); S1 += nxt;
        c += slope;
        const int n = hiWord(c);
        if (cnt > n) { S1 -= nxt; head -= 2; nxt = at(in, head++); S -= nxt; --cnt; }
        else if (cnt < n) { S += nxt; nxt = at(in, head++); S1 += nxt; ++cnt; }
    }
    while (pos < len) out[(size_t) pos++] = 0;
}

void Engine::pitchEnv(Module& m, std::vector<int16_t>& out, const std::vector<int16_t>& in, int32_t inLen)
{
    static const int32_t T[12] = { 0x800000, 0xAAAB00, 0xCB2900, 0xF19C00, 0xFC2800, 0x1000000,
                                   0x103D700, 0x10F3B00, 0x1429500, 0x1800000, 0x2000000, 0x2000000 };
    auto table = [&](int i) { return (i >= 0 && i <= 11) ? T[i] : 0x2000000; };
    auto rate = [&](int lv) {
        const int i = divs(lv, 10), r = (int16_t) (lv - divs(lv, 10) * 10);
        const int32_t base = table(i);
        return base + ldiv(lmul(r, table(i + 1) - base), 10);
    };
    const auto& p = m.points;
    std::vector<Segment> segs;
    for (size_t k = 0; k + 1 < p.size(); ++k) {
        Segment s;
        s.value = rate(p[k].level);
        const int32_t ds = msToSamples((int16_t) (p[k + 1].timeMs - p[k].timeMs), m.sampleRate);
        s.slope = ldiv(rate(p[k + 1].level) - s.value, ds);
        s.end = msToSamples(p[k + 1].timeMs, m.sampleRate);
        segs.push_back(s);
    }
    if (!p.empty()) segs.push_back({ rate(p.back().level), 0, m.length });
    const int32_t len = m.length;
    int32_t pos = 0;
    if (inLen > 4 && !segs.empty()) {
        int64_t base = 0;
        const int64_t limit = inLen - 4;
        int32_t P = 0;
        bool stop = false;
        for (const auto& s : segs) {
            int32_t rt = s.value;
            while (pos < len && pos < s.end) {
                const int32_t q = P >> 8;
                const int64_t i = base + hiWord(q);
                if (i >= limit) { stop = true; break; }
                out[(size_t) pos++] = interp(at(in, i), at(in, i + 1), (uint16_t) loWord(q));
                rt += s.slope;
                const int64_t np = (int64_t) P + rt;
                if (np > 0x7FFFFFFF || np < -0x80000000LL) { base += 128; P = (int32_t) ((uint32_t) np & 0x7FFFFFFF); }
                else P = (int32_t) np;
            }
            if (stop || pos >= len) break;
        }
    }
    while (pos < len) out[(size_t) pos++] = 0;
}

void Engine::waveshaper(Module& m, std::vector<int16_t>& out, const std::vector<int16_t>& in, int32_t inLen)
{
    auto t = m.shaper;
    t[256] = t[255];
    for (int32_t i = 0; i < m.length; ++i) {
        if (i >= inLen) { out[(size_t) i] = 0; continue; }
        const uint16_t x = (uint16_t) in[(size_t) i];
        const size_t idx = (size_t) (((x ^ 0x8000u) >> 7) & 0x1FE) / 2;
        const int16_t f = (int16_t) ((x & 0xFF) << 7);
        const int16_t d = (int16_t) (t[idx + 1] - t[idx]);
        out[(size_t) i] = (int16_t) (t[idx] + q15(muls(d, f)));
    }
}

void Engine::modulator(int index, Module& m, std::vector<int16_t>& out)
{
    int32_t modLen = 0, carLen = 0;
    const bool noInputs = inputsOf(index).empty();
    const auto& mod = noInputs ? empty : inputBuffer(index, m.p[3], modLen);
    const auto& car = noInputs ? empty : inputBuffer(index, m.p[4], carLen);
    if (noInputs) { modLen = carLen = 0; }
    const int16_t dry = pct7fff(100 - m.p[1]), wet = pct7fff(m.p[1]);
    const int32_t len = m.length;
    switch (m.p[2]) {
    case 1: {
        const int16_t d = pct7fff(m.p[0]);
        const int16_t rest = (int16_t) (0x7FFF - d);
        for (int32_t i = 0; i < len; ++i) {
            if (i >= carLen) { out[(size_t) i] = 0; continue; }
            if (i < modLen) {
                const int16_t g = (int16_t) (q15(muls(mod[(size_t) i], d)) + rest);
                out[(size_t) i] = q15(muls(g, car[(size_t) i]));
            } else out[(size_t) i] = q15(muls(car[(size_t) i], rest));
        }
        break; }
    case 2: {
        const int16_t d = m.p[0];
        int64_t c = 20;
        const int64_t stop = (int64_t) carLen - 30;
        for (int32_t i = 0; i < len; ++i) {
            if (c >= stop) { out[(size_t) i] = 0; continue; }
            if (i >= modLen) { out[(size_t) i] = at(car, c++); continue; }
            const int32_t v = muls(mod[(size_t) i], d);
            const int16_t off = hiWord(v);
            out[(size_t) i] = interp(at(car, c + off), at(car, c + off + 1), (uint16_t) loWord(v));
            ++c;
        }
        break; }
    case 3: {
        const int16_t d = (int16_t) (m.p[0] * 30);
        int64_t base = 2, last = 2;
        const int64_t limit = (int64_t) carLen - 2;
        int32_t P = 0;
        bool stopped = false;
        for (int32_t i = 0; i < len; ++i) {
            if (stopped) { out[(size_t) i] = 0; continue; }
            if (i >= modLen) { // copy the carrier from the last read position
                if (last >= limit) { stopped = true; out[(size_t) i] = 0; continue; }
                out[(size_t) i] = at(car, last++);
                continue;
            }
            int64_t np = (int64_t) P + hiWord(muls(mod[(size_t) i], d));
            if (np > 0x7FFFFFFF || np < -0x80000000LL) { base += 32768; np = (uint32_t) np & 0x7FFFFFFF; }
            P = (int32_t) np;
            const int64_t idx = base + (P >> 16);
            if (idx >= limit) { stopped = true; out[(size_t) i] = 0; continue; }
            out[(size_t) i] = interp(at(car, idx), at(car, idx + 1), (uint16_t) loWord(P));
            last = idx;
            np = (int64_t) P + 0x10000;
            if (np > 0x7FFFFFFF) { base += 32768; np = (uint32_t) np & 0x7FFFFFFF; }
            P = (int32_t) np;
        }
        break; }
    default: break;
    }
    if (m.p[1] < 100) blend(out, len, car, carLen, wet, dry);
}

void Engine::mixer(int index, Module& m, std::vector<int16_t>& out)
{
    const auto in = inputsOf(index);
    int total = 0;
    for (size_t i = 0; i < in.size() && i < 32; ++i) total = (int16_t) (total + m.mixGains[i]);
    if (in.empty() || total == 0) return;
    struct Src { int16_t g; const std::vector<int16_t>* buf; int32_t len; };
    std::vector<Src> src;
    for (size_t i = 0; i < in.size() && i < 32; ++i) {
        const int16_t s = m.mixGains[i];
        const bool simple = in.size() == 1 || s == total || total < 100;
        const int16_t g = simple ? loWord(fixRatio(s, 200)) : loWord(fixRatio(s, (int16_t) (total * 2)));
        int32_t l = 0;
        const auto& b = inputBuffer(index, (int) i + 1, l);
        src.push_back({ g, &b, l });
    }
    for (int32_t j = 0; j < m.length; ++j) {
        int32_t sum = 0;
        for (const auto& s : src) if (j < s.len) sum += muls(s.g, (*s.buf)[(size_t) j]);
        out[(size_t) j] = q15(sum);
    }
}

void Engine::delayLike(Module& m, std::vector<int16_t>& out, const std::vector<int16_t>& in, int32_t inLen, bool resonator)
{
    const int32_t len = m.length;
    int32_t D;
    if (resonator) {
        Fixed pitch = fixRatio((int16_t) (m.p[0] * 100 + m.p[1]), 10);
        if (pitch == 0) pitch = 1;
        D = fixDiv(m.sampleRate, pitch);
        if (m.p[3] == 2) D = ldiv(D, 2);
    } else {
        D = ldiv(lmul((int16_t) (m.p[0] * 100 + m.p[1]), m.sampleRate), 10000);
    }
    if (D == 0) D = 1; else if (D > len) D = len;
    int16_t fb = pct7fff(m.p[2]);
    if (m.p[3] == 2) fb = (int16_t) -fb;
    const int16_t dry = pct7fff(100 - m.p[4]), wet = pct7fff(m.p[4]);
    for (int32_t j = 0; j < len; ++j) {
        if (j < D) { out[(size_t) j] = 0; continue; }
        const int16_t fbv = q15(muls(out[(size_t) j - D], fb));
        if (j - D < inLen) {
            const int32_t v = (int32_t) fbv + in[(size_t) (j - D)];
            out[(size_t) j] = v > 32767 ? (int16_t) 0x7FFF : v < -32768 ? INT16_MIN : (int16_t) v;
        } else out[(size_t) j] = fbv;
    }
    if (m.p[4] < 100) blend(out, len, in, inLen, wet, dry);
}

void Engine::stretch(Module& m, std::vector<int16_t>& out, const std::vector<int16_t>& in, int32_t inLen, bool expand)
{
    const Fixed pitch = fixRatio((int16_t) (m.p[0] * 100 + m.p[1]), 10);
    const int32_t P = pitch == 0 ? m.sampleRate : fixDiv(m.sampleRate, pitch);
    const int32_t S0 = msToSamples(lmul(m.p[2], 10), m.sampleRate);
    const int32_t len = m.length;
    int32_t pos = 0;
    int64_t A = 0;
    auto put = [&](int16_t v) { if (pos < len) out[(size_t) pos++] = v; };
    if (expand) {
        if (P <= 0) return;
        const int32_t adv = ldiv(lmul(lmul(P, 4), 101 - m.p[3]), 100);
        const int32_t S = std::max(S0, P);
        const int32_t slope = ldiv(0x7FFF0000, P);
        int64_t boundary = S;
        while (pos < len && A < inLen && A < boundary) put(in[(size_t) A++]);
        while (pos < len && A < inLen) {
            int64_t B = A - P;
            int32_t gA = 0x7FFF0000, gB = 0;
            while (pos < len && A < inLen && B < boundary) {
                const int32_t s = muls(hiWord(gA), at(in, A++)) + muls(hiWord(gB), at(in, B++));
                put(hiWord((int32_t) ((uint32_t) s << 1)));
                gA -= slope; gB += slope;
            }
            A = B;
            boundary += adv;
            if (adv <= 0 && P <= 0) break;
            while (pos < len && A < inLen && A < boundary) put(in[(size_t) A++]);
        }
    } else {
        const int32_t L = ldiv(lmul(P, 101 - m.p[3]), 10);
        if (L <= 0) return;
        const int32_t slope = ldiv(0x7FFF0000, L);
        while (pos < len && A < inLen && A < S0) put(in[(size_t) A++]);
        while (pos < len) {
            int64_t B = A + P;
            const int64_t end = A + L;
            int32_t gA = 0x7FFF0000, gB = 0;
            bool stop = false;
            while (A < end) {
                if (pos >= len) break;
                if (B >= inLen) { stop = true; break; }
                const int32_t s = muls(hiWord(gA), at(in, A++)) + muls(hiWord(gB), at(in, B++));
                put(hiWord((int32_t) ((uint32_t) s << 1)));
                gA -= slope; gB += slope;
            }
            if (stop) break;
            A = B;
        }
    }
    while (pos < len) out[(size_t) pos++] = 0;
}

void Engine::pitchShifter(Module& m, std::vector<int16_t>& out, const std::vector<int16_t>& in, int32_t inLen)
{
    Fixed r = fixRatio(1, 1);
    if (m.p[0] > 0) { const Fixed u = fixRatio(10595, 10000); for (int k = 1; k <= m.p[0]; ++k) r = fixMul(r, u); }
    else if (m.p[0] < 0) { const Fixed u = fixRatio(10000, 10595); for (int k = -1; k >= m.p[0]; --k) r = fixMul(r, u); }
    const Fixed down = fixMul(r, fixRatio(10000, 10595));
    const int32_t rate = r + ldiv(lmul(m.p[1], r - down), 100);
    const int16_t dry = pct7fff(100 - m.p[2]), wet = pct7fff(m.p[2]);
    const int32_t len = m.length;
    int64_t base = 0;
    const int64_t limit = (int64_t) inLen - 3;
    int32_t P = 0;
    int32_t pos = 0;
    while (pos < len) {
        const int64_t i = base + (P >> 16);
        if (i >= limit) break;
        out[(size_t) pos++] = interp(at(in, i), at(in, i + 1), (uint16_t) loWord(P));
        int64_t np = (int64_t) P + rate;
        if (np > 0x7FFFFFFF || np < -0x80000000LL) { base += 32768; np = (uint32_t) np & 0x7FFFFFFF; }
        P = (int32_t) np;
    }
    while (pos < len) out[(size_t) pos++] = 0;
    if (m.p[2] < 100) blend(out, len, in, inLen, wet, dry);
}

void Engine::noise(Module& m, std::vector<int16_t>& out)
{
    Random rnd; rnd.seed = 12345;
    int16_t sc = 0x200;
    int32_t s = m.sampleRate;
    while (s > 0x7FFF) { sc = divs(sc, 2); s = ldiv(s, 2); }
    const Fixed freq = fixRatio((int16_t) (m.p[0] * 10), 1) + fixRatio(m.p[1], 10);
    const int32_t inc = fixMul(freq, fixRatio(sc, loWord(s)));
    const int a = m.p[2], f = m.p[3];
    int16_t ampMask = 0;
    if (a >= 1 && a <= 49) ampMask = (int16_t) (ldiv(lmul(a, 0xFFFF), 100) | 0xFF);
    else if (a >= 50 && a <= 100) ampMask = (int16_t) (ldiv(lmul(a, 0xFFFF), 100) | 0xFFF);
    int16_t freqMask = 0;
    if (f >= 1 && f <= 90) freqMask = (int16_t) ((1 << ((f + 5) / 6)) - 1);
    else if (f >= 91 && f <= 100) freqMask = -1;
    uint32_t M = (uint32_t) inc;
    for (int k = 0; k < 21; ++k) M |= (uint32_t) inc >> (k + 1);
    uint32_t phase = 0;
    for (int32_t i = 0; i < m.length; ++i) {
        const int16_t r = rnd.next();
        const int16_t sv = kSineTable[((phase >> 16) & 0x1FE) >> 1];
        out[(size_t) i] = (int16_t) (sv - q15(muls((int16_t) (r & ampMask), sv)));
        int32_t j = (int32_t) (((uint32_t) (int32_t) (int16_t) (r & freqMask) << 5) & M);
        if (r < 0) j = -j;
        phase += (uint32_t) inc;
        phase += (uint32_t) j;
    }
}

void Engine::diffuser(Module& m, std::vector<int16_t>& out, const std::vector<int16_t>& in, int32_t inLen)
{
    const Fixed size = fixRatio((int16_t) (m.p[0] * 100 + m.p[1]), 10);
    int32_t D = fixRound(fixMul(fixDiv(m.sampleRate, 10000), size));
    if (D > m.length) D = m.length;
    if (D <= 0) D = 1;
    const int16_t g16 = (int16_t) ldiv(lmul(m.p[2], 0x7FBC), 100);
    const int64_t G = (int64_t) g16 << 8;
    std::vector<int16_t> L1((size_t) D, 0), L2((size_t) D, 0), L3((size_t) D, 0);
    auto sat24 = [](int64_t v) { return v > 0x7FFFFF ? 0x7FFFFF : v < -0x800000 ? -0x800000 : v; };
    auto floorShift = [](int64_t v) { return v >> 24; }; // arithmetic: A1 of the accumulator
    auto stage = [&](int64_t X, int16_t line, int64_t& W) {
        W = sat24(floorShift(2 * G * X + ((int64_t) line << 8) * (1LL << 24)));
        return sat24(floorShift(X * (1LL << 24) - 2 * G * W));
    };
    auto send = [](int64_t Y) { return (int16_t) (Y >> 8); };
    size_t k = 0;
    for (int32_t i = 0; i < m.length; ++i) {
        const int64_t X = (int64_t) (i < inLen ? in[(size_t) i] : 0) << 8;
        int64_t W1, W2, W3;
        L1[k] = send(stage(X, L1[k], W1));
        L2[k] = send(stage(W1, L2[k], W2));
        const int64_t S3 = stage(W2, L3[k], W3);
        out[(size_t) i] = send(W3);
        L3[k] = send(S3);
        if (++k >= (size_t) D) k = 0;
    }
}

} // namespace turbosynth
