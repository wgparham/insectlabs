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
// that is reproduced at the call sites (see Cloud::lateDenom).
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
    const Fixes& fx;
    sane::Rand rnd;
    std::array<int16_t, kTableSize + 1> table;
    const std::array<int16_t, kTableSize + 1>& gauss;
    const SourceSound* source;
    bool renderAudio;

    float rate;              // output sample rate (44100 in Model 1)
    float start, dur, end;
    int32_t frames;          // nominal output length in frames
    int32_t bufferFrames;    // frames, or more with Fixes::keepCloudTail
    float panInitial, panFinal; // 0..1, or kRandomPan
    float grainInitial, grainFinal; // mutated per grain when random (Model 1)
    std::vector<int16_t>& out;
    std::vector<double> mix; // Fixes::floatMix accumulator, int16 units
    std::vector<double> grainBuffer;

    Cloud(const CloudParams& params, const Waveform& wave, const SourceSound* src, bool audio, std::vector<int16_t>& buffer)
        : p(params), fx(params.fixes), rnd(params.seed), table(oscillatorTable(wave, params.fixes.wavetableWrap)),
          gauss(gaussTable(params.fixes.fullScaleEnvelope)), source(src), renderAudio(audio), out(buffer)
    {
        rate = (float) p.sampleRate;
        start = p.startTime;
        dur = p.duration;
        end = (float) ((double) start + (double) dur);
        frames = sane::toInt32((double) end * rate);
        bufferFrames = std::max(0, frames);
        if (fx.keepCloudTail)
            bufferFrames += (int32_t) std::ceil(longestGrain() * rate) + 1;
        out.assign((size_t) bufferFrames * 2, 0);
        if (fx.floatMix)
            mix.assign((size_t) bufferFrames * 2, 0.0);
        panInitial = p.stereoInitial.random ? kRandomPan : (float) ((double) (float) p.stereoInitial.position / 100.0f);
        panFinal = p.stereoFinal.random ? kRandomPan : (float) ((double) (float) p.stereoFinal.position / 100.0f);
        grainInitial = p.grainDurationInitial;
        grainFinal = p.grainDurationFinal;
    }

    double longestGrain() const
    {
        const double a = std::fabs(p.grainDurationInitial), b = std::fabs(p.grainDurationFinal);
        if (p.randomGrainDuration && !fx.grainDurationRange)
            return a + b; // Minimum + r * Maximum
        return std::max(a, b);
    }

    // Denominator for the parameters the original interpolated over the end
    // time instead of the duration.
    float lateDenom() const { return fx.interpolateOverDuration ? dur : end; }

    // Wavetable oscillator with linear interpolation. In the original the
    // difference between neighbouring entries is a 16-bit subtraction that can
    // wrap and the result is truncated to 16 bits.
    double oscillator(double phase) const
    {
        double ip;
        const double frac = std::modf(phase, &ip);
        const int32_t i = (int32_t) ip;
        const int16_t s0 = table[i];
        if (fx.oscillatorNoWrap)
            return (double) s0 + ((double) table[i + 1] - (double) s0) * frac;
        const int16_t d = (int16_t) (table[i + 1] - s0);
        return sane::toInt16((double) d * frac + (double) s0);
    }

    float densityAt(float t) const
    {
        const float denom = (float) ((double) end - (double) start);
        return interp(start, denom, t, p.densityInitial, p.densityFinal);
    }

    // Fixes::grainDurationRange: a fresh duration per grain between the two
    // fields, whichever order they are in.
    float drawGrainDuration()
    {
        const float lo = std::min(p.grainDurationInitial, p.grainDurationFinal);
        const float hi = std::max(p.grainDurationInitial, p.grainDurationFinal);
        return (float) ((double) lo + (double) randUnit(rnd) * ((double) hi - (double) lo));
    }

    float pickFrequency(float lo, float hi)
    {
        if (fx.logFrequency && lo > 0.0f && hi > 0.0f) {
            const double r = (double) randUnit(rnd);
            return (float) ((double) lo * std::pow((double) hi / (double) lo, r));
        }
        const float width = (float) ((double) hi - (double) lo);
        return (float) ((double) std::abs(rnd()) * kInvRandMax * width + lo);
    }

    float pickPan(float t)
    {
        if (panInitial == kRandomPan && panFinal == kRandomPan)
            return randUnit(rnd);
        if (fx.coherentPanning && (panInitial == kRandomPan || panFinal == kRandomPan)) {
            // The random end is a fresh position for each grain; the grain
            // sits between it and the fixed end according to its time.
            const float r = randUnit(rnd);
            const float a = panInitial == kRandomPan ? r : panInitial;
            const float b = panFinal == kRandomPan ? r : panFinal;
            return interp(start, dur, t, a, b);
        }
        if (panInitial == kRandomPan) {
            // As in the original: the fixed final position is not used, the
            // grains narrow from fully random to hard left.
            const float a = interp(start, dur, t, 1.0f, panFinal);
            const float b = interp(start, dur, t, 0.0f, panFinal);
            const double r = (double) std::abs(rnd()) * kInvRandMax;
            return (float) (((double) a - (double) b) * r);
        }
        if (panFinal == kRandomPan) {
            const float a = interp(start, dur, t, panInitial, 1.0f);
            const float b = interp(start, dur, t, panInitial, 0.0f);
            const double r = (double) std::abs(rnd()) * kInvRandMax;
            return (float) (((double) a - (double) b) * r);
        }
        return interp(start, lateDenom(), t, panInitial, panFinal);
    }

    // Per-grain parameters shared by both cloud types. presetDuration >= 0 is
    // a duration already drawn by the caller (Fixes::grainDurationRange).
    GrainEvent makeGrain(float t, double& phaseInc, int32_t& onset, int32_t& length, float presetDuration = -1.0f)
    {
        if (p.randomGrainDuration && !fx.grainDurationRange) {
            const float g = (float) ((double) std::abs(rnd()) * kInvRandMax * p.grainDurationInitial + p.grainDurationFinal);
            grainInitial = grainFinal = g;
        }
        if (p.randomGrainDuration && fx.grainDurationRange && presetDuration < 0.0f)
            presetDuration = drawGrainDuration();

        const float hi = p.highInitial == p.highFinal ? p.highInitial : interp(start, lateDenom(), t, p.highInitial, p.highFinal);
        const float lo = p.lowInitial == p.lowFinal ? p.lowInitial : interp(start, lateDenom(), t, p.lowInitial, p.lowFinal);
        float freq = pickFrequency(lo, hi);

        float amp = interp(start, lateDenom(), t, p.amplitudeInitial, p.amplitudeFinal);

        // RE: the original interpolates two uninitialised locals here and, when
        // the result has a fractional part, draws one random number it never
        // uses. With stack garbage that is almost always the case, so the draw
        // is modelled unconditionally to keep the random sequence aligned.
        if (!fx.dropUnusedRandom)
            rnd();

        const float pan = pickPan(t);

        phaseInc = (double) freq / (double) rate * 512.0;
        onset = (int32_t) std::trunc((double) t * rate);
        const float gd = presetDuration >= 0.0f ? presetDuration : interp(start, lateDenom(), t, grainInitial, grainFinal);
        length = sane::toInt32((double) rate * (double) gd);

        if (p.source == SourceKind::Granulated)
            freq = 0.0f;
        return { t, gd, amp, freq, pan };
    }

    // Selects and copies the source samples of a granulated grain.
    void fetchGranulated(int32_t onset, int32_t length, bool synchronous)
    {
        if (fx.safeGranulation || fx.resampleSource) {
            fetchGranulatedFixed(onset, length);
            return;
        }
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
        grainBuffer.assign((size_t) std::max(0, length) * 2, 0.0);
        for (int64_t k = 0; k < 2 * (int64_t) length; ++k) {
            const int64_t s = pos + k;
            grainBuffer[(size_t) k] = s >= 0 && s < total ? source->interleaved[(size_t) s] : 0;
        }
    }

    // Model 2 granulation: positions in frames, grains kept inside the source
    // (Fixes::safeGranulation) and read at the source's own rate
    // (Fixes::resampleSource). The selection orders are those of the original.
    void fetchGranulatedFixed(int32_t onset, int32_t length)
    {
        const int64_t srcFrames = (int64_t) source->interleaved.size() / 2;
        const double step = fx.resampleSource ? source->sampleRate / (double) rate : 1.0;
        const double span = (double) length * step;         // source frames the grain reads
        const double room = std::max(0.0, (double) srcFrames - span - 1.0); // last valid start
        const double progress = frames > 0 ? std::clamp((double) onset / (double) frames, 0.0, 1.0) : 0.0;

        double pos = 0.0;
        switch (p.selectionOrder) {
            case SelectionOrder::Random:
                pos = (double) randUnit(rnd) * room;
                break;
            case SelectionOrder::StatisticalEvolution: {
                const double target = progress * room;
                for (;;) {
                    const double cand = (double) randUnit(rnd) * room;
                    const double reach = cand > target ? room - target : target;
                    const double dist = reach > 0.0 ? std::fabs(cand - target) / reach : 0.0;
                    if (!(dist > (double) randUnit(rnd))) { pos = cand; break; }
                }
                break;
            }
            case SelectionOrder::DeterministicProgression:
                pos = progress * room;
                break;
        }
        if (!fx.safeGranulation)
            pos = std::floor(pos); // resampling alone: start on a whole frame

        grainBuffer.assign((size_t) std::max(0, length) * 2, 0.0);
        for (int32_t n = 0; n < length; ++n) {
            const double x = pos + n * step;
            const int64_t i = (int64_t) std::floor(x);
            const double f = x - (double) i;
            for (int c = 0; c < 2; ++c) {
                auto at = [&](int64_t k) { return k >= 0 && k < srcFrames ? (double) source->interleaved[(size_t) (k * 2 + c)] : 0.0; };
                grainBuffer[(size_t) n * 2 + c] = at(i) + (at(i + 1) - at(i)) * f;
            }
        }
    }

    static int16_t mix16(int16_t existing, double env, double sample, float amp, double gain)
    {
        double v = env / 32767.0;
        v *= sample;
        v *= amp;
        v *= gain;
        v += existing;
        return sane::toInt16(v);
    }

    double envelopeAt(int32_t n, int32_t length) const
    {
        if (fx.smoothEnvelope)
            return gaussEnvelope(((double) n + 0.5) / (double) length, fx.fullScaleEnvelope);
        return gauss[(size_t) sane::toInt16((double) n / (double) length * 512.0)];
    }

    void mixGrain(const GrainEvent& g, int32_t onset, int32_t length, double phaseInc)
    {
        const int32_t stop = onset + length;
        const float amp = g.amplitude;
        const double left = 1.0 - (double) g.pan;
        double phase = 0.0;
        int32_t n = 0;
        for (int32_t i = onset; i < stop && i < bufferFrames; ++i, ++n) {
            const double env = envelopeAt(n, length);
            double sl, sr;
            if (p.source == SourceKind::Synthetic) {
                sl = oscillator(phase);
                sr = oscillator(phase);
                phase += phaseInc;
                if (phase >= 512.0) phase -= 512.0;
            } else {
                sl = grainBuffer[(size_t) n * 2];
                sr = grainBuffer[(size_t) n * 2 + 1];
            }
            if (fx.floatMix) {
                mix[(size_t) i * 2] += env / 32767.0 * sl * amp * left;
                mix[(size_t) i * 2 + 1] += env / 32767.0 * sr * amp * g.pan;
            } else {
                int16_t& l = out[(size_t) i * 2];
                int16_t& r = out[(size_t) i * 2 + 1];
                l = mix16(l, env, sl, amp, left);
                r = mix16(r, env, sr, amp, g.pan);
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
            // Model 2 draws a random duration before the end test so the test
            // uses the grain's own duration.
            const float drawn = p.randomGrainDuration && fx.grainDurationRange ? drawGrainDuration() : -1.0f;
            const float gd = drawn >= 0.0f ? drawn : interp(start, lateDenom(), t, grainInitial, grainFinal);
            if ((double) t + (double) gd > (double) end)
                break;
            const float density = densityAt(t);

            double phaseInc;
            int32_t onset, length;
            const GrainEvent g = makeGrain(t, phaseInc, onset, length, drawn);
            if (!fx.keepCloudTail && 2 * ((int64_t) length + onset) > 2 * (int64_t) frames) {
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

    // Fixes::floatMix: round and clip the mix once.
    void finish(RenderResult& result)
    {
        if (!fx.floatMix)
            return;
        result.mixed.resize(mix.size());
        for (size_t i = 0; i < mix.size(); ++i) {
            result.mixed[i] = (float) (mix[i] / 32768.0);
            out[i] = (int16_t) std::clamp(std::lround(mix[i]), -32768L, 32767L);
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
    if (!(p.sampleRate >= 8000.0 && p.sampleRate <= 384000.0)) return "Sample rate must be between 8000 and 384000 Hz.";
    return {};
}

RenderResult render(const CloudParams& p, const Waveform& wave, const SourceSound* source, bool renderAudio,
                    const ProgressFn& progress)
{
    RenderResult result;
    result.sampleRate = p.sampleRate;
    Cloud cloud(p, wave, source, renderAudio, result.interleaved);
    if (p.type == CloudType::Asynchronous)
        cloud.runAsynchronous(result, progress);
    else
        cloud.runSynchronous(result, progress);
    cloud.finish(result);
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
