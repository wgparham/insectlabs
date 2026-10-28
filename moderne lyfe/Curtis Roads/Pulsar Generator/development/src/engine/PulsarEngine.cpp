#include "PulsarEngine.h"

#include <algorithm>
#include <cmath>
#include <limits>

namespace pulsar {

namespace {

constexpr double kPi = 3.14159265358979323846;
constexpr int kMaxVoices = 1024;       // raisedFundCap
constexpr int kOriginalMaxVoices = 512;

int roundSamples(double seconds, double sr) { return std::max(1, (int) std::floor(seconds * sr + 0.5)); }

constexpr int kMinLevelSize = 8;
constexpr int kTaps = 31, kMid = kTaps / 2; // half-band filter

// Half-band low-pass (31-tap Blackman-windowed sinc, cutoff a quarter of the
// sample rate), then every other sample. Ends are extended with their values.
std::vector<float> halve(const std::vector<float>& x)
{
    static const std::array<double, kTaps> h = [] {
        std::array<double, kTaps> c{};
        double sum = 0.0;
        for (int k = 0; k < kTaps; ++k) {
            const double t = k - kMid;
            const double sinc = t == 0.0 ? 0.5 : std::sin(0.5 * kPi * t) / (kPi * t);
            const double w = 0.42 - 0.5 * std::cos(2.0 * kPi * k / (kTaps - 1)) + 0.08 * std::cos(4.0 * kPi * k / (kTaps - 1));
            c[(size_t) k] = sinc * w;
            sum += c[(size_t) k];
        }
        for (auto& v : c) v /= sum;
        return c;
    }();
    const int n = (int) x.size();
    std::vector<float> y((size_t) n / 2);
    for (int j = 0; j < n / 2; ++j) {
        double acc = 0.0;
        for (int k = 0; k < kTaps; ++k)
            acc += h[(size_t) k] * x[(size_t) std::clamp(2 * j + k - kMid, 0, n - 1)];
        y[(size_t) j] = (float) acc;
    }
    return y;
}

} // namespace

// ---------------------------------------------------------------------------
std::shared_ptr<const TableData> TableData::from(const TableSet& set)
{
    auto d = std::make_shared<TableData>();
    d->tables = set.tables;
    for (auto& t : d->tables) t.resize(kTableSize, 0.0f);
    d->combo = set.comboSignal();
    d->comboLevels.push_back(d->combo);
    while ((int) d->comboLevels.back().size() > kMinLevelSize)
        d->comboLevels.push_back(halve(d->comboLevels.back()));
    return d;
}

bool Fixes::* Fixes::member(int i)
{
    static constexpr bool Fixes::* m[kCount] = {
        &Fixes::noChannelSwap, &Fixes::loopEndBothWays, &Fixes::controlsAtStart, &Fixes::burstFromFirstStep,
        &Fixes::silenceAfterPulsaret, &Fixes::exactFormant, &Fixes::smoothControls, &Fixes::rateIndependentDC,
        &Fixes::burstEveryPulse, &Fixes::formantEnvelopeRule, &Fixes::raisedFundCap, &Fixes::bandLimited,
    };
    return m[std::clamp(i, 0, kCount - 1)];
}

const char* Fixes::id(int i)
{
    static const char* ids[kCount] = {
        "noChannelSwap", "loopEndBothWays", "controlsAtStart", "burstFromFirstStep",
        "silenceAfterPulsaret", "exactFormant", "smoothControls", "rateIndependentDC",
        "burstEveryPulse", "formantEnvelopeRule", "raisedFundCap", "bandLimited",
    };
    return ids[std::clamp(i, 0, kCount - 1)];
}

const char* Fixes::name(int i)
{
    static const char* names[kCount] = {
        "No channel swap", "Loop end both ways", "Controls at start", "Burst from first step",
        "Silence after pulsaret", "Exact formant", "Smooth controls", "Rate-independent DC block",
        "Burst on every pulse", "Formant envelope rule", "Raised fundamental cap", "Band-limited pulsarets",
    };
    return names[std::clamp(i, 0, kCount - 1)];
}

const char* Fixes::description(int i)
{
    static const char* text[kCount] = {
        "The original swaps left and right at its output (.reverse), so pan tables play mirrored.",
        "With Loop off the original only notices the end of the tables going forwards, so Backwards stops at once.",
        "The original loads the control tables 3 ms after starting, so the first pulses play with every control at zero.",
        "The original's burst counter starts at -2, so the pattern begins on its last step.",
        "The original holds the pulsaret's last value until the envelope cuts it, leaving a DC step after each pulse.",
        "The original rounds the pulsaret to whole samples, so high formants go out of tune and sweeps move in steps.",
        "The original reads the control tables once per 64 samples, so fast sweeps step audibly.",
        "The original's DC blocker coefficient is fixed, so its cutoff rises at higher sample rates (15 Hz at 96 kHz).",
        "The original only advances the burst pattern on pulses that won the mask coin, stretching it when masking.",
        "The source meant to drop the cutoff envelope when the lowest formant is at or above the fundamental; a bug disabled it.",
        "The original caps the fundamental at 2000-3000 Hz, depending on the number of trains, for 2001 CPUs.",
        "The original reads the pulsaret with linear interpolation, so high formants alias.",
    };
    return text[std::clamp(i, 0, kCount - 1)];
}

Controls Controls::fromSettings(const Settings& s, const Controls& keep)
{
    Controls c = keep;
    auto has = [&](int slot) { return s[(size_t) slot] != -99.0f; };
    for (int i = 0; i < 3; ++i) {
        if (has(kTrain1On + i)) c.trainOn[(size_t) i] = s[(size_t) (kTrain1On + i)] > 0.0f;
        if (has(kTrain1Level + i)) c.trainLevel[(size_t) i] = std::clamp(s[(size_t) (kTrain1Level + i)], 0.0f, 10.0f);
    }
    if (has(kBurstOn)) c.burstOn = s[kBurstOn] > 0.0f;
    if (has(kBurstOns)) c.burstOns = std::clamp((int) std::lround(s[kBurstOns]), 1, 100);
    if (has(kBurstOffs)) c.burstOffs = std::clamp((int) std::lround(s[kBurstOffs]), 0, 100);
    if (has(kTrainDuration)) c.trainDuration = std::clamp(s[kTrainDuration], 0.1f, 100.0f);
    if (has(kEdge)) c.edge = std::clamp(s[kEdge], 0.0f, 10.0f);
    return c;
}

void Controls::toSettings(Settings& s) const
{
    for (int i = 0; i < 3; ++i) {
        s[(size_t) (kTrain1On + i)] = trainOn[(size_t) i] ? 1.0f : 0.0f;
        s[(size_t) (kTrain1Level + i)] = trainLevel[(size_t) i];
    }
    s[kBurstOn] = burstOn ? 1.0f : 0.0f;
    s[kBurstOns] = (float) burstOns;
    s[kBurstOffs] = (float) burstOffs;
    s[kTrainDuration] = trainDuration;
    s[kEdge] = edge;
}

// ---------------------------------------------------------------------------
void Engine::prepare(double sr)
{
    sampleRate = sr;
    checkInterval = std::max(1, (int) std::lround(0.0333 * sr)); // OscXFadeBank checkLoopPos
    voices.assign(kMaxVoices, Voice{});
    numActiveVoices = 0;
    blockRead = kBlockSize;
    if (!tables) setTables(TableData::from(TableSet::defaults()));
}

void Engine::setTables(std::shared_ptr<const TableData> t)
{
    tables = std::move(t);
    fadeFrom.reset();
    pulsaretNew = tables;
    pulsaretOld.reset();
    fadeSamplesLeft = fadeSamplesTotal = 0;
}

void Engine::crossfadeTo(std::shared_ptr<const TableData> t, double fadeTime)
{
    if (!running || fadeTime <= 0.0) { setTables(std::move(t)); return; }
    // PG.beginXFade: the control tables (OscXFadeBank.startFade) and the
    // pulsaret (PulsarEngine4.startFade) both crossfade over fadeTime.
    fadeFrom = tables;
    pulsaretOld = pulsaretNew;
    tables = std::move(t);
    pulsaretNew = tables;
    fadeSamplesTotal = fadeSamplesLeft = std::max(1, (int) std::lround(fadeTime * sampleRate));
}

void Engine::setControls(const Controls& c) { ctl = c; }

float Engine::cutoffTime() const
{
    // PulsarEngine4.setEdgeFactor / setCutoffTime
    const double t = 0.002 * std::pow(0.6915, std::clamp((double) ctl.edge, 0.0, 10.0));
    return (float) std::clamp(t, 0.0, 0.02);
}

void Engine::start()
{
    running = true;
    stoppedAtEnd = false;
    lastCheckedPos = tablePos;
    checkCounter = 0;
    impulsePhase = 1.0;   // Impulse starts full: the first sample triggers
    lowestForm = 0.0f;
    nextPulseWillPlay.reset();
    burstPos = fixes.burstFromFirstStep ? -1 : -2; // PG.burstLoop.setPos(-2)
    numPulsars = 0;
    for (auto& v : voices) v = Voice{};
    numActiveVoices = 0;
    dcX = dcY = { 0.0f, 0.0f };
    masterGain = ctl.masterVolume;
    ctlValues.fill(0.0f);
    ctlNext.fill(0.0f);
    blockRead = kBlockSize;
    // OscXFadeBank triggers its tables after 3 ms.
    controlsDelay = fixes.controlsAtStart ? 0 : (int) std::ceil(0.003 * sampleRate);
    wrapped = false;
    // A backwards run from the very start of the tables plays them from the end.
    if (fixes.loopEndBothWays && ctl.backwards && tablePos == 0.0) tablePos = lastCheckedPos = std::nextafter(1.0, 0.0);
}

void Engine::stop()
{
    running = false;
    for (auto& v : voices) v = Voice{};
    numActiveVoices = 0;
    blockRead = kBlockSize;
}

void Engine::jumpToPosition(double p)
{
    tablePos = p - std::floor(p);
    lastCheckedPos = tablePos;
    wrapped = false;
}

double Engine::position() const { return tablePos; }

float Engine::readTable(const std::vector<float>& t, double phase01) const
{
    // Osc.kr with freq 0: wavetable lookup at the phase, linear interpolation,
    // wrapping from the last point to the first.
    const double x = (phase01 - std::floor(phase01)) * kTableSize;
    const int i = std::min((int) x, kTableSize - 1);
    const float f = (float) (x - i);
    const float a = t[(size_t) i], b = t[(size_t) ((i + 1) % kTableSize)];
    return a + (b - a) * f;
}

void Engine::controlBlock()
{
    // Control values for this block, read at its first sample.
    const float x = fadeSamplesTotal > 0 ? 1.0f - (float) fadeSamplesLeft / (float) fadeSamplesTotal : 1.0f;
    auto readControls = [&](std::array<float, kNumTables>& out, double pos, float fade) {
        for (int k = 0; k < kNumTables; ++k) {
            if (k == Pulsaret || k == PulseEnv) continue;
            float v = readTable(tables->tables[(size_t) k], pos);
            if (fadeFrom && fadeSamplesLeft > 0)
                v = readTable(fadeFrom->tables[(size_t) k], pos) * (1.0f - fade) + v * fade;
            out[(size_t) k] = v;
        }
    };
    const bool live = controlsDelay <= 0;
    if (!live) {
        ctlValues.fill(0.0f);
        controlsDelay -= kBlockSize;
    } else {
        readControls(ctlValues, tablePos, x);
    }

    // Phasor: one pass through the tables per train duration.
    if (!ctl.freeze) {
        const double dir = ctl.backwards ? -1.0 : 1.0;
        tablePos += dir * kBlockSize / (sampleRate * std::max(0.1f, ctl.trainDuration));
        if (tablePos >= 1.0 || tablePos < 0.0) wrapped = true;
        tablePos -= std::floor(tablePos);
    }
    if (fixes.smoothControls) {
        // Values at the next block's start, for per-sample interpolation.
        if (live) {
            const float xNext = fadeSamplesTotal > 0 ? std::min(1.0f, x + (float) kBlockSize / (float) fadeSamplesTotal) : 1.0f;
            readControls(ctlNext, tablePos, xNext);
        } else {
            ctlNext = ctlValues;
        }
    }
    // checkLoopPos (every 1/30 s): a wrap of the phase ends the run when Loop
    // is off. Like the original it only checks for the phase going down, so
    // running backwards with Loop off stops at the first check
    // (loopEndBothWays checks for a wrap either way).
    checkCounter += kBlockSize;
    if (checkCounter >= checkInterval) {
        checkCounter -= checkInterval;
        const bool ended = fixes.loopEndBothWays ? wrapped : tablePos < lastCheckedPos;
        if (ended && !ctl.loop) {
            stop();
            stoppedAtEnd = true;
        }
        lastCheckedPos = tablePos;
        wrapped = false;
    }

    if (fadeSamplesLeft > 0) {
        fadeSamplesLeft = std::max(0, fadeSamplesLeft - kBlockSize);
        if (fadeSamplesLeft == 0) { fadeFrom.reset(); pulsaretOld.reset(); } // endFade
    }
}

bool Engine::burstNext()
{
    // BurstLoop.next: SC's % is a true modulo, so the initial setPos(-2)
    // makes the first answer the last step of the pattern.
    const int length = ctl.burstOns + ctl.burstOffs;
    burstPos = ((burstPos + 1) % length + length) % length;
    return burstPos < ctl.burstOns;
}

void Engine::spawnVoice(const std::array<float, 4>& train, float fund, bool predict, bool noEnvelope)
{
    const float formFreq = train[0], pan = train[1], amp = train[2], level = train[3];
    Voice* v = nullptr;
    const size_t limit = fixes.raisedFundCap ? voices.size() : (size_t) kOriginalMaxVoices;
    for (size_t k = 0; k < limit; ++k) if (!voices[k].active) { v = &voices[k]; break; }
    if (!v) return;

    *v = Voice{};
    v->active = true;
    // Osc1: one pass through the table in round(sr / formFreq) samples,
    // linear interpolation, then the last value is held.
    const double dur = 1.0 / std::max(1.0, (double) std::fabs(formFreq));
    if (fixes.exactFormant) {
        // The pass takes exactly dur seconds; samples with a phase short of
        // the table's end are interpolated.
        const double length = std::max(1.0, dur * sampleRate);
        v->inc = (double) (kTableSize - 1) / length;
        v->remaining = (int) std::ceil(length);
    } else {
        const int n = roundSamples(dur, sampleRate);
        v->inc = (double) (kTableSize - 1) / (double) n;
        v->remaining = n;
    }
    if (fixes.bandLimited) {
        // Smallest level read at no more than one point per sample.
        const int levels = (int) pulsaretNew->comboLevels.size();
        v->mipLevel = std::clamp((int) std::ceil(std::log2(std::max(1.0, v->inc)) - 1e-9), 0, levels - 1);
    }
    v->tablesA = pulsaretNew;
    if (pulsaretOld && fadeSamplesTotal > 0) {
        // xFade spawn function: new * fadeIn + old * (1 - fadeIn), fadeIn polled now.
        const float fadeIn = 1.0f - (float) fadeSamplesLeft / (float) fadeSamplesTotal;
        v->tablesB = pulsaretOld;
        v->mulA = fadeIn * amp * level;
        v->mulB = (1.0f - fadeIn) * amp * level;
    } else {
        v->mulA = amp * level;
    }
    // Pan2: index trunc(1024 * pos + 1024) into a quarter-sine table.
    const int idx = std::clamp((int) (1024.0f * pan + 1024.0f), 0, 2048);
    v->gainL = (float) std::sin((2048 - idx) / 2048.0 * kPi / 2.0);
    v->gainR = (float) std::sin(idx / 2048.0 * kPi / 2.0);

    if (noEnvelope) v->env = Voice::Env::None;
    else if (predict) {
        v->env = Voice::Env::Predict;
        v->holdLeft = roundSamples(std::min(1.0 / fund, 1.0), sampleRate);
    } else v->env = Voice::Env::Release;
    v->step = 1.0f / (float) roundSamples(cutoffTime(), sampleRate);
    ++numActiveVoices;
}

void Engine::trigger(int)
{
    // PulsarEngine4.asUgen TSpawn function. Whether this pulse plays was
    // decided at the previous pulse; decide the next one now, so a pulsaret
    // that will be followed by silence is not cut short.
    auto flag = [&] {
        const bool coin = std::uniform_real_distribution<float>(0.0f, 1.0f)(rng) < control(PulseMask);
        if (!ctl.burstOn) return coin;
        if (fixes.burstEveryPulse) { const bool burst = burstNext(); return coin && burst; }
        return coin && burstNext(); // "and:" only advances the burst on a coin win
    };
    std::optional<bool> playThis = nextPulseWillPlay;
    nextPulseWillPlay = flag();
    const bool play = playThis.value_or(*nextPulseWillPlay);
    if (!play || ctl.mute) return;

    // thisSpawn.releaseAll: sustaining pulsarets start their cutoff; ones
    // without an envelope end.
    for (auto& v : voices) {
        if (!v.active) continue;
        if (v.env == Voice::Env::Release && v.stage == Voice::Stage::Hold) v.stage = Voice::Stage::Ramp;
        else if (v.env == Voice::Env::None) { v.active = false; --numActiveVoices; }
    }
    ++numPulsars;

    std::array<std::array<float, 4>, 3> active{};
    int count = 0;
    float pulseLowestForm = std::numeric_limits<float>::infinity(); // formantEnvelopeRule
    for (int i = 0; i < 3; ++i) {
        if (!ctl.trainOn[(size_t) i] || ctl.trainLevel[(size_t) i] <= 0.0f) continue;
        const int base = FormFreq1 + i;
        const float formFreq = control(base);
        active[(size_t) count++] = { formFreq, control(Pan1 + i), control(Amp1 + i), ctl.trainLevel[(size_t) i] * 0.1f };
        lowestForm = std::min(formFreq, lowestForm); // never reset, so it stays <= 0
        pulseLowestForm = std::min(std::fabs(formFreq), pulseLowestForm);
    }
    const float fund = std::fabs(control(FundFreq));
    // lowestForm < fund is effectively always true: the envelope always applies.
    // formantEnvelopeRule takes this pulse's lowest formant instead, as intended.
    const bool noEnvelope = fixes.formantEnvelopeRule ? !(pulseLowestForm < fund) : !(lowestForm < fund);
    const bool predict = !(fund < 20.0f || !*nextPulseWillPlay);
    for (int i = 0; i < count; ++i)
        spawnVoice(active[(size_t) i], fund, predict, noEnvelope);
}

void Engine::renderBlock()
{
    blockL.fill(0.0f);
    blockR.fill(0.0f);
    if (!running) return;

    controlBlock();
    if (!running) return; // stopped at the end of the tables

    int activeTrains = 0;
    for (int i = 0; i < 3; ++i) if (ctl.trainOn[(size_t) i] && ctl.trainLevel[(size_t) i] > 0.0f) ++activeTrains;
    double maxFund = maxFundFreqs[(size_t) activeTrains];
    if (fixes.raisedFundCap) maxFund = std::max(maxFund, std::min(8000.0, 0.45 * sampleRate));
    auto impulseInc = [&] { return std::min((double) std::fabs(control(FundFreq)), maxFund) / sampleRate; };
    double inc = impulseInc();

    for (int s = 0; s < kBlockSize; ++s) {
        blockSample = s;
        if (fixes.smoothControls) inc = impulseInc();
        impulsePhase += inc;
        if (impulsePhase >= 1.0) {
            impulsePhase -= 1.0;
            trigger(s);
        }
        float l = 0.0f, r = 0.0f;
        for (auto& v : voices) {
            if (!v.active) continue;
            if (fixes.silenceAfterPulsaret && v.started && v.remaining <= 0) {
                // The pulsaret has ended: nothing more to hear from this voice.
                v.active = false; v.tablesA.reset(); v.tablesB.reset(); --numActiveVoices;
                continue;
            }
            auto osc = [&](const TableData& t) {
                const auto& c = t.combo;
                if (!v.started) return c[0];
                if (v.remaining <= 0) return c[kTableSize - 1];
                if (v.mipLevel > 0) {
                    const auto& m = t.comboLevels[(size_t) std::min(v.mipLevel, (int) t.comboLevels.size() - 1)];
                    const double ph = v.phase * (double) m.size() / kTableSize;
                    const int i = std::min((int) ph, (int) m.size() - 2);
                    return m[(size_t) i] + (m[(size_t) i + 1] - m[(size_t) i]) * (float) (ph - i);
                }
                const int i = (int) v.phase;
                return c[(size_t) i] + (c[(size_t) i + 1] - c[(size_t) i]) * (float) (v.phase - i);
            };
            float x = osc(*v.tablesA) * v.mulA;
            if (v.tablesB) x += osc(*v.tablesB) * v.mulB;
            if (!v.started) { v.started = true; v.phase = v.inc; v.remaining -= 1; }
            else if (v.remaining > 0) { v.phase += v.inc; v.remaining -= 1; }

            x *= v.level;
            l += x * v.gainL;
            r += x * v.gainR;

            if (v.env == Voice::Env::Predict && v.stage == Voice::Stage::Hold && --v.holdLeft <= 0)
                v.stage = Voice::Stage::Ramp;
            if (v.stage == Voice::Stage::Ramp) {
                v.level -= v.step;
                if (v.level <= 0.0f) { v.active = false; v.tablesA.reset(); v.tablesB.reset(); --numActiveVoices; }
            }
        }
        blockL[(size_t) s] = l;
        blockR[(size_t) s] = r;
    }

    // Ring modulator with a looping sound (PG.run): out * direct + sound * out * wet.
    if (loopSound && !loopSound->left.empty()) {
        const float wet = ctl.ringModBlend * 0.3f, direct = 1.0f - ctl.ringModBlend * 0.1f;
        const double step = loopSound->sampleRate / sampleRate;
        const size_t len = loopSound->left.size();
        for (int s = 0; s < kBlockSize; ++s) {
            const size_t i = (size_t) loopPos % len, j = (i + 1) % len;
            const float f = (float) (loopPos - std::floor(loopPos));
            const auto& R = loopSound->right.empty() ? loopSound->left : loopSound->right;
            const float sl = loopSound->left[i] + (loopSound->left[j] - loopSound->left[i]) * f;
            const float sr = R[i] + (R[j] - R[i]) * f;
            blockL[(size_t) s] = blockL[(size_t) s] * direct + sl * blockL[(size_t) s] * wet;
            blockR[(size_t) s] = blockR[(size_t) s] * direct + sr * blockR[(size_t) s] * wet;
            loopPos += step;
            if (loopPos >= (double) len) loopPos -= (double) len;
        }
    }

    // LeakDC(out, 0.999, masterVol), then .reverse swaps the channels.
    // rateIndependentDC keeps the cutoff 0.999 gives at 44.1 kHz (about 7 Hz).
    const float coef = fixes.rateIndependentDC ? (float) std::pow(0.999, 44100.0 / sampleRate) : 0.999f;
    const float g0 = masterGain, g1 = ctl.masterVolume;
    for (int s = 0; s < kBlockSize; ++s) {
        const float g = g0 + (g1 - g0) * (float) (s + 1) / kBlockSize;
        float* ch[2] = { &blockL[(size_t) s], &blockR[(size_t) s] };
        for (int c = 0; c < 2; ++c) {
            const float x = *ch[c];
            const float y = x - dcX[(size_t) c] + coef * dcY[(size_t) c];
            dcX[(size_t) c] = x;
            dcY[(size_t) c] = y;
            *ch[c] = y * g;
        }
        if (!fixes.noChannelSwap) std::swap(blockL[(size_t) s], blockR[(size_t) s]);
    }
    masterGain = g1;
}

void Engine::process(float* left, float* right, int n)
{
    int done = 0;
    while (done < n) {
        if (blockRead >= kBlockSize) {
            renderBlock();
            blockRead = 0;
        }
        const int take = std::min(n - done, kBlockSize - blockRead);
        std::copy_n(blockL.data() + blockRead, take, left + done);
        std::copy_n(blockR.data() + blockRead, take, right + done);
        blockRead += take;
        done += take;
    }
}

// ---------------------------------------------------------------------------
void SettingsMorph::begin(const Settings& from, const Settings& to, double fadeTime)
{
    start = current = from;
    target = to;
    duration = fadeTime;
    elapsed = stepElapsed = 0.0;
    running = true;
}

Settings SettingsMorph::advance(double dt)
{
    if (!running) return current;
    elapsed += dt;
    if (duration <= 0.1 || elapsed >= duration) {
        // Final step: everything, including the switches, at the target.
        for (int i : { 1, 2, 3, 21, 11, 12, 13, 31, 41, 22, 23 })
            if (target[(size_t) i] != -99.0f) current[(size_t) i] = target[(size_t) i];
        running = false;
        return current;
    }
    // Morphables move in 0.1 s steps; numSteps as in SettingsLib.xFadeTo.
    const int numSteps = std::max(1, (int) (std::round(duration / 0.1) - 1));
    const int stepsDone = std::min(numSteps, (int) (elapsed / 0.1));
    for (int i : { 11, 12, 13, 31, 41 }) {
        if (target[(size_t) i] == -99.0f || start[(size_t) i] == -99.0f) continue;
        current[(size_t) i] = start[(size_t) i] + (target[(size_t) i] - start[(size_t) i]) * (float) stepsDone / (float) numSteps;
    }
    // Steppables count through whole numbers spread over the fade.
    for (int i : { 22, 23 }) {
        if (target[(size_t) i] == -99.0f || start[(size_t) i] == -99.0f) continue;
        const float diff = target[(size_t) i] - start[(size_t) i];
        const int steps = (int) std::fabs(diff);
        if (steps == 0) continue;
        const int taken = std::min(steps, (int) (elapsed / (duration / steps)));
        current[(size_t) i] = start[(size_t) i] + (diff > 0 ? 1.0f : -1.0f) * (float) taken;
    }
    return current;
}

Settings randomSettings(std::mt19937& rng)
{
    Settings s;
    s.fill(-99.0f);
    std::uniform_int_distribution<int> coin(0, 1);
    std::uniform_real_distribution<float> unit(0.0f, 1.0f);
    for (int i : { 1, 2, 3, 21 }) s[(size_t) i] = (float) coin(rng);
    for (int i : { 11, 12, 13, 41 }) s[(size_t) i] = unit(rng) * 10.0f;
    auto randInt = [&](int n) { return n <= 0 ? 0 : std::uniform_int_distribution<int>(0, n - 1)(rng); };
    s[22] = (float) std::min(randInt(50), randInt(50));   // 50.linrand
    s[23] = (float) randInt((int) s[22]);                  // rand(newState.at(22))
    s[31] = (float) (10 + randInt(11) - randInt(11));      // 10 + 10.bilinrand
    return s;
}

} // namespace pulsar
