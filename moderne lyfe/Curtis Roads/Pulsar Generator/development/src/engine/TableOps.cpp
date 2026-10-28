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
    const size_t n = t.size();
    if (n < 3) return;
    Table src = t;
    for (size_t i = 0; i < n; ++i)
        t[i] = (src[(i + n - 1) % n] + src[i] + src[(i + 1) % n]) / 3.0f;
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

void hanningWindow(Table& t)
{
    for (size_t i = 0; i < t.size(); ++i)
        t[i] *= (float) (0.5 - 0.5 * std::cos(2.0 * kPi * pos(i, t.size())));
}

void parzenWindow(Table& t)
{
    for (size_t i = 0; i < t.size(); ++i)
        t[i] *= (float) (1.0 - std::fabs(2.0 * pos(i, t.size()) - 1.0));
}

void welchWindow(Table& t)
{
    for (size_t i = 0; i < t.size(); ++i) {
        const double x = 2.0 * pos(i, t.size()) - 1.0;
        t[i] *= (float) (1.0 - x * x);
    }
}

void comb(Table& t, float spacing, bool odd)
{
    // Multiply the spectrum by a raised-cosine comb (discrete Fourier series).
    const size_t n = t.size();
    if (n == 0 || spacing <= 0.0f) return;
    std::vector<std::complex<double>> spec(n / 2 + 1);
    for (size_t k = 0; k < spec.size(); ++k) {
        std::complex<double> s = 0.0;
        for (size_t i = 0; i < n; ++i) s += (double) t[i] * std::polar(1.0, -2.0 * kPi * (double) (k * i) / (double) n);
        const double phase = 2.0 * kPi * (double) k / spacing + (odd ? kPi : 0.0);
        spec[k] = s * (0.5 - 0.5 * std::cos(phase));
    }
    for (size_t i = 0; i < n; ++i) {
        double x = spec[0].real();
        for (size_t k = 1; k < spec.size(); ++k) {
            const double w = (k == n / 2 && n % 2 == 0) ? 1.0 : 2.0;
            x += w * (spec[k] * std::polar(1.0, 2.0 * kPi * (double) (k * i) / (double) n)).real();
        }
        t[i] = (float) (x / (double) n);
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

void buzzFill(Table& t, int lowHarmonic, int numHarmonics, float coefficient)
{
    const size_t n = t.size();
    lowHarmonic = std::max(1, lowHarmonic);
    numHarmonics = std::max(1, numHarmonics);
    for (size_t i = 0; i < n; ++i) {
        double x = 0.0, a = 1.0;
        for (int h = 0; h < numHarmonics; ++h, a *= coefficient)
            x += a * std::cos(2.0 * kPi * (double) (lowHarmonic + h) * (double) i / (double) n);
        t[i] = (float) x;
    }
    normalize(t);
}

void grainEnvelopeFill(Table& t, GrainShape shape)
{
    fillConstant(t, 1.0f);
    switch (shape) {
        case GrainShape::Hanning: hanningWindow(t); break;
        case GrainShape::Welch: welchWindow(t); break;
        case GrainShape::Parzen: parzenWindow(t); break;
    }
}

void noiseFill(Table& t, std::mt19937& rng, bool binary)
{
    std::uniform_real_distribution<float> d(-1.0f, 1.0f);
    for (auto& x : t) x = binary ? (d(rng) < 0.0f ? -1.0f : 1.0f) : d(rng);
}

} // namespace pulsar::ops
