#include "TableOps.h"

#include <algorithm>
#include <cmath>
#include <complex>

namespace pulsar::ops {

namespace {

constexpr double kPi = 3.14159265358979323846;

template <typename F> void map(Table& t, F f) { for (auto& x : t) x = f(x); }

// Position i of n as 0..1.
double pos(size_t i, size_t n) { return n > 1 ? (double) i / (double) (n - 1) : 0.0; }

} // namespace

void zero(Table& t) { std::fill(t.begin(), t.end(), 0.0f); }

void normalize(Table& t)
{
    float peak = 0.0f;
    for (float x : t) peak = std::max(peak, std::fabs(x));
    if (peak > 0.0f) scale(t, 1.0f / peak);
}

void reverse(Table& t) { std::reverse(t.begin(), t.end()); }
void invert(Table& t) { map(t, [](float x) { return -x; }); }
void absolute(Table& t) { map(t, [](float x) { return std::fabs(x); }); }
void square(Table& t) { map(t, [](float x) { return x * x; }); }
void cube(Table& t) { map(t, [](float x) { return x * x * x; }); }

void distort(Table& t)
{
    map(t, [](float x) { return x / (1.0f + std::fabs(x)); });
    normalize(t);
}

void fadeIn(Table& t) { for (size_t i = 0; i < t.size(); ++i) t[i] *= (float) pos(i, t.size()); }
void fadeOut(Table& t) { for (size_t i = 0; i < t.size(); ++i) t[i] *= (float) (1.0 - pos(i, t.size())); }
void clipNegative(Table& t) { map(t, [](float x) { return std::max(0.0f, x); }); }

void smooth(Table& t)
{
    // signal_smooth_ds: four passes of a 7-point Hann kernel,
    // 0.125 * (1 + cos(pi k / 4)) for k = -3..3, wrapping around the table.
    // Only tables whose size is a power of two are smoothed.
    const size_t n = t.size();
    if (n == 0 || (n & (n - 1)) != 0) return;
    const double x = 0.25 * std::sqrt(2.0);
    const double norm = 1.0 / (2.0 * (0.5 - x + 0.5 + 0.5 + x) + 1.0);
    const double w3 = (0.5 - x) * norm, w2 = 0.5 * norm, w1 = (0.5 + x) * norm, w0 = norm;
    const size_t mask = n - 1;
    Table src(n);
    for (int pass = 0; pass < 4; ++pass) {
        src = t;
        for (size_t i = 0; i < n; ++i) {
            auto at = [&](long k) { return (double) src[(size_t) ((long) i + k) & mask]; };
            t[i] = (float) (w2 * (at(-2) + at(2)) + w3 * (at(-3) + at(3)) + w1 * (at(-1) + at(1)) + w0 * at(0));
        }
    }
}

void scale(Table& t, float factor) { map(t, [factor](float x) { return x * factor; }); }

void scaleTo(Table& t, float lo, float hi)
{
    if (t.empty()) return;
    const auto mm = std::minmax_element(t.begin(), t.end());
    const float a = *mm.first, b = *mm.second;
    if (b - a <= 0.0f) { fillConstant(t, (lo + hi) * 0.5f); return; }
    map(t, [=](float x) { return lo + (x - a) / (b - a) * (hi - lo); });
}

// The windows divide by the table size, not size - 1 (signal_hanning_ds,
// signal_welch_ds, signal_parzen_ds).
void hanningWindow(Table& t)
{
    const double n = (double) t.size();
    for (size_t i = 0; i < t.size(); ++i)
        t[i] *= (float) (0.5 - 0.5 * std::cos(2.0 * kPi * (double) i / n));
}

void parzenWindow(Table& t)
{
    // A triangle rising by 0.5 / size per point from both ends, so it peaks
    // at 0.25 in the middle (the original's constant, kept).
    const size_t n = t.size();
    const double slope = 0.5 / (double) n;
    for (size_t i = 0; i < n / 2; ++i) {
        const float w = (float) (slope * (double) i);
        t[i] *= w;
        t[n - 1 - i] *= w;
    }
}

void welchWindow(Table& t)
{
    // A half sine.
    const double n = (double) t.size();
    for (size_t i = 0; i < t.size(); ++i)
        t[i] *= (float) std::sin(kPi * (double) i / n);
}

void comb(Table& t, float spacing, bool odd)
{
    // CombTableDialog: each point plus (odd comb) or minus (even comb) the
    // point size/spacing further on, wrapping, halved.
    const size_t n = t.size();
    if (n == 0 || spacing == 0.0f) return;
    long d = (long) ((double) n / spacing);
    if (d <= 1) d = std::max(1L, (long) n / 2);
    const Table src = t;
    for (size_t i = 0; i < n; ++i) {
        const float a = src[i], b = src[(i + (size_t) d) % n];
        t[i] = 0.5f * (odd ? a + b : a - b);
    }
}

void fillConstant(Table& t, float value) { std::fill(t.begin(), t.end(), value); }

void fillLine(Table& t, float from, float to)
{
    for (size_t i = 0; i < t.size(); ++i) t[i] = from + (to - from) * (float) pos(i, t.size());
}

void fourierFill(Table& t, const std::vector<float>& amps, const std::vector<float>& phases)
{
    const size_t n = t.size();
    for (size_t i = 0; i < n; ++i) {
        double x = 0.0;
        for (size_t h = 0; h < amps.size(); ++h) {
            const double ph = h < phases.size() ? phases[h] : 0.0;
            x += amps[h] * std::sin(2.0 * kPi * (double) (h + 1) * (double) i / (double) n + ph);
        }
        t[i] = (float) x;
    }
    normalize(t);
}

void chebyshevFill(Table& t, const std::vector<float>& amps)
{
    for (size_t i = 0; i < t.size(); ++i) {
        const double x = 2.0 * pos(i, t.size()) - 1.0;
        double tPrev = 1.0, tCur = x, sum = 0.0;
        for (size_t h = 0; h < amps.size(); ++h) {
            sum += amps[h] * tCur;
            const double next = 2.0 * x * tCur - tPrev;
            tPrev = tCur;
            tCur = next;
        }
        t[i] = (float) sum;
    }
    normalize(t);
}

void phaseModFill(Table& t, float carrier, float modulator, float index)
{
    const size_t n = t.size();
    for (size_t i = 0; i < n; ++i) {
        const double ph = (double) i / (double) n;
        t[i] = (float) std::sin(2.0 * kPi * carrier * ph + index * std::sin(2.0 * kPi * modulator * ph));
    }
}

void buzzFill(Table& t, int lowHarmonic, int numHarmonics, float coefficient, bool bilateral)
{
    // GBuzzFillDialog: the closed-form sum, with the phase starting at pi so
    // the pulse peaks in the middle of the table, then normalized.
    const size_t n = t.size();
    const double r = coefficient, nh = numHarmonics, low = lowHarmonic;
    const double rn = std::pow(r, nh);
    for (size_t i = 0; i < n; ++i) {
        const double th = kPi + 2.0 * kPi * (double) i / (double) n;
        const double denom = 1.0 + r * r - 2.0 * r * std::cos(th);
        const bool degenerate = denom <= 1e-5 && denom >= -1e-5;
        double x;
        if (bilateral) {
            // A symmetric spectrum of 2n+1 lines around the lowest harmonic.
            const double num = (1.0 - r * r) - 2.0 * rn * r * (std::cos((nh + 1.0) * th) - r * std::cos(nh * th));
            x = degenerate ? 0.0 : std::sin(low * th) * num / denom;
        } else {
            const double num = std::cos(low * th) - r * std::cos((low - 1.0) * th)
                             - rn * std::cos((low + nh) * th) + rn * r * std::cos((low + nh - 1.0) * th);
            x = degenerate ? nh : num / denom;
        }
        t[i] = (float) x;
    }
    normalize(t);
}

void grainEnvelopeFill(Table& t, GrainShape shape, float durationPercent)
{
    // GrainEnvFillDialog: the shape rises over the first `ramp` points and
    // falls over the last, holding 1 in between. The grain duration is the
    // share of the table that is held.
    const size_t n = t.size();
    long ramp = (long) (((double) n - (double) n * durationPercent / 100.0) * 0.5);
    if (ramp <= 1) ramp = std::max(1L, (long) n / 2);
    fillConstant(t, 1.0f);
    for (long i = 0; i < ramp && (size_t) i < n; ++i) {
        const double p = (double) i / (double) ramp;
        double w = 0.0;
        switch (shape) {
            case GrainShape::Hanning: w = 0.5 - 0.5 * std::cos(kPi * p); break;
            case GrainShape::Welch: w = std::sin(0.5 * kPi * p); break;
            case GrainShape::Parzen: w = p; break;
        }
        t[(size_t) i] = (float) w;
        t[n - 1 - (size_t) i] = (float) w;
    }
}

void noiseFill(Table& t, std::mt19937& rng, bool binary)
{
    std::uniform_real_distribution<float> d(-1.0f, 1.0f);
    for (auto& x : t) x = binary ? (d(rng) < 0.0f ? -1.0f : 1.0f) : d(rng);
}

} // namespace pulsar::ops
