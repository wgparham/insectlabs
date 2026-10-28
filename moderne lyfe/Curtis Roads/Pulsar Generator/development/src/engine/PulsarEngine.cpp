#include "PulsarEngine.h"

#include <algorithm>
#include <cmath>

namespace pulsar {

namespace {

constexpr double kPi = 3.14159265358979323846;
constexpr int kMaxVoices = 512;

int roundSamples(double seconds, double sr) { return std::max(1, (int) std::floor(seconds * sr + 0.5)); }

} // namespace

// ---------------------------------------------------------------------------
std::shared_ptr<const TableData> TableData::from(const TableSet& set)
{
    auto d = std::make_shared<TableData>();
    d->tables = set.tables;
    for (auto& t : d->tables) t.resize(kTableSize, 0.0f);
    d->combo = set.comboSignal();
    return d;
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
    burstPos = -2;        // PG.burstLoop.setPos(-2)
    numPulsars = 0;
    for (auto& v : voices) v = Voice{};
    numActiveVoices = 0;
    dcX = dcY = { 0.0f, 0.0f };
    masterGain = ctl.masterVolume;
    ctlValues.fill(0.0f);
    blockRead = kBlockSize;
    controlsDelay = (int) std::ceil(0.003 * sampleRate); // OscXFadeBank triggers its tables after 3 ms
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
    if (controlsDelay > 0) {
        ctlValues.fill(0.0f);
        controlsDelay -= kBlockSize;
    } else {
        const float x = fadeSamplesTotal > 0 ? 1.0f - (float) fadeSamplesLeft / (float) fadeSamplesTotal : 1.0f;
        for (int k = 0; k < kNumTables; ++k) {
            if (k == Pulsaret || k == PulseEnv) continue;
            float v = readTable(tables->tables[(size_t) k], tablePos);
            if (fadeFrom && fadeSamplesLeft > 0)
                v = readTable(fadeFrom->tables[(size_t) k], tablePos) * (1.0f - x) + v * x;
            ctlValues[(size_t) k] = v;
        }
    }

    // Phasor: one pass through the tables per train duration.
    if (!ctl.freeze) {
        const double dir = ctl.backwards ? -1.0 : 1.0;
        tablePos += dir * kBlockSize / (sampleRate * std::max(0.1f, ctl.trainDuration));
        tablePos -= std::floor(tablePos);
    }
    // checkLoopPos (every 1/30 s): a wrap of the phase ends the run when Loop
    // is off. Like the original it only checks for the phase going down, so
    // running backwards with Loop off stops at the first check.
    checkCounter += kBlockSize;
    if (checkCounter >= checkInterval) {
        checkCounter -= checkInterval;
        if (tablePos < lastCheckedPos && !ctl.loop) {
            stop();
            stoppedAtEnd = true;
        }
        lastCheckedPos = tablePos;
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
    for (auto& candidate : voices) if (!candidate.active) { v = &candidate; break; }
    if (!v) return;

    *v = Voice{};
    v->active = true;
    // Osc1: one pass through the table in round(sr / formFreq) samples,
    // linear interpolation, then the last value is held.
    const double dur = 1.0 / std::max(1.0, (double) std::fabs(formFreq));
    const int n = roundSamples(dur, sampleRate);
    v->inc = (double) (kTableSize - 1) / (double) n;
    v->remaining = n;
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
        return ctl.burstOn ? (coin && burstNext()) : coin; // "and:" only advances the burst on a coin win
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
    for (int i = 0; i < 3; ++i) {
        if (!ctl.trainOn[(size_t) i] || ctl.trainLevel[(size_t) i] <= 0.0f) continue;
        const int base = FormFreq1 + i;
        const float formFreq = control(base);
        active[(size_t) count++] = { formFreq, control(Pan1 + i), control(Amp1 + i), ctl.trainLevel[(size_t) i] * 0.1f };
        lowestForm = std::min(formFreq, lowestForm); // never reset, so it stays <= 0
    }
    const float fund = std::fabs(control(FundFreq));
    // lowestForm < fund is effectively always true: the envelope always applies.
    const bool noEnvelope = !(lowestForm < fund);
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
    const double freq = std::min((double) std::fabs(control(FundFreq)), (double) maxFundFreqs[(size_t) activeTrains]);
    const double inc = freq / sampleRate;

    for (int s = 0; s < kBlockSize; ++s) {
        impulsePhase += inc;
        if (impulsePhase >= 1.0) {
            impulsePhase -= 1.0;
            trigger(s);
        }
        float l = 0.0f, r = 0.0f;
        for (auto& v : voices) {
            if (!v.active) continue;
            auto osc = [&](const TableData& t) {
                const auto& c = t.combo;
                if (!v.started) return c[0];
                if (v.remaining <= 0) return c[kTableSize - 1];
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
    const float g0 = masterGain, g1 = ctl.masterVolume;
    for (int s = 0; s < kBlockSize; ++s) {
        const float g = g0 + (g1 - g0) * (float) (s + 1) / kBlockSize;
        float* ch[2] = { &blockL[(size_t) s], &blockR[(size_t) s] };
        for (int c = 0; c < 2; ++c) {
            const float x = *ch[c];
            const float y = x - dcX[(size_t) c] + 0.999f * dcY[(size_t) c];
            dcX[(size_t) c] = x;
            dcY[(size_t) c] = y;
            *ch[c] = y * g;
        }
        std::swap(blockL[(size_t) s], blockR[(size_t) s]);
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
