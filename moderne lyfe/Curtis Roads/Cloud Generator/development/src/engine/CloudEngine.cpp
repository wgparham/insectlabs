#include "CloudEngine.h"
#include "Sane.h"
#include "Waveforms.h"

#include <algorithm>
#include <cmath>
#include <cstdio>
#include <cstdlib>

namespace cloudgen {

namespace {

constexpr float kRandomPan = 999.0f;    // sentinel stored for a "Random" checkbox
constexpr float kInvRandMax = 3.05185094e-05f; // 1/32767 as the float the original used

// The original's single interpolation routine (RA.main): computes
// a + ((t - start) / denom) * (b - a) and stores it as a float.
// Most callers pass the cloud *end* time as denom rather than its duration;
// that is reproduced at the call sites.
float interp(float start, float denom, float t, float a, float b)
{
    const double range = (double) b - (double) a;
    double x = (double) t - (double) start;
    x /= (double) denom;
    x *= range;
    x += (double) a;
    return (float) x;
}

float randUnit(sane::Rand& rnd) { return (float) ((double) std::abs(rnd()) * kInvRandMax); }

struct Cloud {
    const CloudParams& p;
    sane::Rand rnd;
    std::array<int16_t, kTableSize + 1> table;
    const std::array<int16_t, kTableSize + 1>& gauss = gaussTable();
    const SourceSound* source;
    bool renderAudio;

    float start, dur, end;
    int32_t frames;          // output length in frames
    float panInitial, panFinal; // 0..1, or kRandomPan
    float grainInitial, grainFinal; // mutated per grain when random
    std::vector<int16_t>& out;
    std::vector<int16_t> grainBuffer;

    Cloud(const CloudParams& params, const Waveform& wave, const SourceSound* src, bool audio, std::vector<int16_t>& buffer)
        : p(params), rnd(params.seed), table(oscillatorTable(wave)), source(src), renderAudio(audio), out(buffer)
    {
        start = p.startTime;
        dur = p.duration;
        end = (float) ((double) start + (double) dur);
        frames = sane::toInt32((double) end * 44100.0f);
        out.assign((size_t) std::max(0, frames) * 2, 0);
        panInitial = p.stereoInitial.random ? kRandomPan : (float) ((double) (float) p.stereoInitial.position / 100.0f);
        panFinal = p.stereoFinal.random ? kRandomPan : (float) ((double) (float) p.stereoFinal.position / 100.0f);
        grainInitial = p.grainDurationInitial;
        grainFinal = p.grainDurationFinal;
    }

    // Wavetable oscillator with linear interpolation; the difference between
    // neighbouring entries is a 16-bit subtraction that can wrap.
    int16_t oscillator(double phase) const
    {
        double ip;
        const double frac = std::modf(phase, &ip);
        const int32_t i = (int32_t) ip;
        const int16_t s0 = table[i];
        const int16_t d = (int16_t) (table[i + 1] - s0);
        return sane::toInt16((double) d * frac + (double) s0);
    }

    float densityAt(float t) const
    {
        const float denom = (float) ((double) end - (double) start);
        return interp(start, denom, t, p.densityInitial, p.densityFinal);
    }

    // Per-grain parameters shared by both cloud types. Returns the grain.
    GrainEvent makeGrain(float t, double& phaseInc, int32_t& onset, int32_t& length)
    {
        if (p.randomGrainDuration) {
            const float g = (float) ((double) std::abs(rnd()) * kInvRandMax * p.grainDurationInitial + p.grainDurationFinal);
            grainInitial = grainFinal = g;
        }

        const float hi = p.highInitial == p.highFinal ? p.highInitial : interp(start, end, t, p.highInitial, p.highFinal);
        const float lo = p.lowInitial == p.lowFinal ? p.lowInitial : interp(start, end, t, p.lowInitial, p.lowFinal);
        const float width = (float) ((double) hi - (double) lo);
        float freq = (float) ((double) std::abs(rnd()) * kInvRandMax * width + lo);

        float amp = interp(start, end, t, p.amplitudeInitial, p.amplitudeFinal);

        // RE: the original interpolates two uninitialised locals here and, when
        // the result has a fractional part, draws one random number it never
        // uses. With stack garbage that is almost always the case, so the draw
        // is modelled unconditionally to keep the random sequence aligned.
        rnd();

        float pan;
        if (panInitial == kRandomPan && panFinal == kRandomPan) {
            pan = randUnit(rnd);
        } else if (panInitial == kRandomPan) {
            // As in the original: the fixed final position is not used, the
            // grains narrow from fully random to hard left.
            const float a = interp(start, dur, t, 1.0f, panFinal);
            const float b = interp(start, dur, t, 0.0f, panFinal);
            const double r = (double) std::abs(rnd()) * kInvRandMax;
            pan = (float) (((double) a - (double) b) * r);
        } else if (panFinal == kRandomPan) {
            const float a = interp(start, dur, t, panInitial, 1.0f);
            const float b = interp(start, dur, t, panInitial, 0.0f);
            const double r = (double) std::abs(rnd()) * kInvRandMax;
            pan = (float) (((double) a - (double) b) * r);
        } else {
            pan = interp(start, end, t, panInitial, panFinal);
        }

        phaseInc = (double) freq / 44100.0 * 512.0;
        onset = (int32_t) std::trunc((double) t * 44100.0f);
        const float gd = interp(start, end, t, grainInitial, grainFinal);
        length = sane::toInt32(44100.0 * (double) gd);

        if (p.source == SourceKind::Granulated)
            freq = 0.0f;
        return { t, gd, amp, freq, pan };
    }

    // Selects and copies the source samples of a granulated grain.
    void fetchGranulated(int32_t onset, int32_t length, bool synchronous)
    {
        const int32_t total = (int32_t) source->interleaved.size(); // samples, both channels
        auto proportional = [&] {
            const float twice = (float) ((double) onset * 2.0);
            return sane::toInt32((double) twice / (double) (2 * (int64_t) frames) * (double) total);
        };
        auto randomPos = [&] { return sane::toInt32((double) rnd() / 32767.0 * (double) total); };

        int32_t pos = 0;
        switch (p.selectionOrder) {
            case SelectionOrder::Random:
                pos = randomPos();
                break;
            case SelectionOrder::StatisticalEvolution: {
                for (;;) {
                    const int32_t cand = randomPos();
                    const int32_t prop = proportional();
                    int32_t num, den;
                    if (cand > prop) { num = cand - prop; den = total - prop; }
                    else { num = prop - cand; den = prop; }
                    const float dist = (float) ((double) num / (double) den);
                    const float r = (float) ((double) rnd() / 32767.0);
                    if (!(dist > r)) { pos = cand; break; }
                }
                const int64_t last = (int64_t) pos + 2 * (int64_t) length;
                if (synchronous ? last > total : last >= total)
                    pos = 0;
                break;
            }
            case SelectionOrder::DeterministicProgression:
                pos = proportional();
                break;
        }

        // Interleaved samples from pos; an odd pos swaps the channels, as in
        // the original. Reads past the end, which the original took from
        // whatever memory followed, are silent.
        grainBuffer.assign((size_t) std::max(0, length) * 2, 0);
        for (int64_t k = 0; k < 2 * (int64_t) length; ++k) {
            const int64_t s = pos + k;
            grainBuffer[(size_t) k] = s >= 0 && s < total ? source->interleaved[(size_t) s] : 0;
        }
    }

    static int16_t mix(int16_t existing, double env, double sample, float amp, double gain)
    {
        double v = env / 32767.0;
        v *= sample;
        v *= amp;
        v *= gain;
        v += existing;
        return sane::toInt16(v);
    }

    void mixGrain(const GrainEvent& g, int32_t onset, int32_t length, double phaseInc)
    {
        const int32_t stop = onset + length;
        const float amp = g.amplitude;
        const double left = 1.0 - (double) g.pan;
        double phase = 0.0;
        int32_t n = 0;
        for (int32_t i = onset; i < stop && i < frames; ++i, ++n) {
            const int16_t env = gauss[(size_t) sane::toInt16((double) n / (double) length * 512.0)];
            int16_t& l = out[(size_t) i * 2];
            int16_t& r = out[(size_t) i * 2 + 1];
            if (p.source == SourceKind::Synthetic) {
                l = mix(l, env, oscillator(phase), amp, left);
                r = mix(r, env, oscillator(phase), amp, g.pan);
                phase += phaseInc;
                if (phase >= 512.0) phase -= 512.0;
            } else {
                l = mix(l, env, grainBuffer[(size_t) n * 2], amp, left);
                r = mix(r, env, grainBuffer[(size_t) n * 2 + 1], amp, g.pan);
            }
        }
    }

    // Everything after the grain's parameters are known.
    void emit(GrainEvent g, int32_t onset, int32_t length, double phaseInc, bool synchronous, RenderResult& result)
    {
        g.amplitude = (float) ((double) g.amplitude / 100.0f);
        if (p.source == SourceKind::Granulated)
            fetchGranulated(onset, length, synchronous);
        result.grains.push_back(g);
        if (renderAudio && onset >= 0)
            mixGrain(g, onset, length, phaseInc);
    }

    void runAsynchronous(RenderResult& result, const ProgressFn& progress)
    {
        const float maxDensity = p.densityFinal > p.densityInitial ? p.densityFinal : p.densityInitial;
        // RE: the original stores this count in a 16-bit integer; above 32767
        // it overflows and the program never finishes. The true count is used.
        const int64_t candidates = (int64_t) std::trunc((double) maxDensity * (double) dur);
        for (int64_t c = 1; c <= candidates; ++c) {
            const float t = (float) ((double) std::abs(rnd()) * kInvRandMax * dur + start);
            const float ratio = (float) ((double) densityAt(t) / (double) maxDensity);
            if (ratio < randUnit(rnd))
                continue;

            double phaseInc;
            int32_t onset, length;
            const GrainEvent g = makeGrain(t, phaseInc, onset, length);
            emit(g, onset, length, phaseInc, false, result);
            if (progress && !progress((double) c / (double) candidates, result.grains.back())) {
                result.stopped = true;
                return;
            }
        }
    }

    void runSynchronous(RenderResult& result, const ProgressFn& progress)
    {
        float t = start;
        for (;;) {
            const float gd = interp(start, end, t, grainInitial, grainFinal);
            if ((double) t + (double) gd > (double) end)
                break;
            const float density = densityAt(t);

            double phaseInc;
            int32_t onset, length;
            const GrainEvent g = makeGrain(t, phaseInc, onset, length);
            if (2 * ((int64_t) length + onset) > 2 * (int64_t) frames) {
                length = 0; // the original drops grains that would overrun the buffer
                onset = 0;
            }
            emit(g, onset, length, phaseInc, true, result);
            if (progress && !progress(((double) t - start) / dur, result.grains.back())) {
                result.stopped = true;
                return;
            }
            t = (float) (1000.0 / (double) density / 1000.0 + (double) t);
        }
    }
};

} // namespace

std::string validate(const CloudParams& p, const SourceSound* source)
{
    if (p.startTime < 0.0f) return "Cloud Start time is less than zero.";
    if (p.duration == 0.0f) return "Cloud duration is equal to zero.";
    if (p.grainDurationInitial == 0.0f) return "Initial grain duration is equal to zero.";
    if (p.grainDurationFinal == 0.0f) return "Final grain duration is equal to zero.";
    if (p.source == SourceKind::Synthetic
        && (p.highInitial == 0.0f || p.highFinal == 0.0f || p.lowInitial == 0.0f || p.lowFinal == 0.0f))
        return "Bandwidth parameters must be entered for synthetic grains.";
    if (p.source == SourceKind::Granulated && (source == nullptr || source->interleaved.empty()))
        return "Soundfile must be selected for granulation.";
    if (p.amplitudeInitial == 0.0f && p.amplitudeFinal == 0.0f) return "Cloud amplitude equals zero.";
    // Not checked by the original, where a negative density never terminates.
    if (p.densityInitial < 0.0f || p.densityFinal < 0.0f) return "You must enter values for all cloud variables.";
    return {};
}

RenderResult render(const CloudParams& p, const Waveform& wave, const SourceSound* source, bool renderAudio,
                    const ProgressFn& progress)
{
    RenderResult result;
    Cloud cloud(p, wave, source, renderAudio, result.interleaved);
    if (p.type == CloudType::Asynchronous)
        cloud.runAsynchronous(result, progress);
    else
        cloud.runSynchronous(result, progress);
    return result;
}

std::string grainsAsText(const std::vector<GrainEvent>& grains)
{
    // The original writes this line per grain to a temporary file, then reads
    // it back as floats and rewrites it, skipping repeated onsets.
    std::string s;
    char line[256];
    float previousOnset = -100.0f;
    for (const auto& g : grains) {
        const float v[5] = { g.onset, g.duration, g.amplitude, g.frequency, g.pan };
        float r[5];
        for (int i = 0; i < 5; ++i) {
            char tmp[64];
            std::snprintf(tmp, sizeof tmp, "%f", (double) v[i]);
            r[i] = std::strtof(tmp, nullptr);
        }
        if (r[0] == previousOnset)
            continue;
        previousOnset = r[0];
        std::snprintf(line, sizeof line, "%f    %f    %f    %i     %i     %f    %f\n",
                      (double) r[0], (double) r[1], (double) r[2], 0, 0, (double) r[3], (double) r[4]);
        s += line;
    }
    return s;
}

} // namespace cloudgen
