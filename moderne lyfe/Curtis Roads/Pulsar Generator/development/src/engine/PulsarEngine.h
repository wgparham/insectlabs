// Pulsar Generator synthesis engine.
//
// Reimplementation of PulsarEngine4 and OscXFadeBank from Pulsar Generator
// 2001 (C. Roads and A. de Campo), recovered from the SuperCollider 2 class
// library inside the original application, with the behaviour of the SC2
// unit generators it relies on (Impulse, TSpawn, Osc1, Pan2, EnvGen, Osc,
// Phasor, LeakDC) taken from the PowerPC runtime. docs/ALGORITHM.md has the
// details. Single-threaded: call everything from the audio thread.
#pragma once

#include "Tables.h"

#include <array>
#include <cstdint>
#include <memory>
#include <optional>
#include <random>
#include <vector>

namespace pulsar {

// Table data prepared off the audio thread.
struct TableData {
    std::array<std::vector<float>, kNumTables> tables;
    std::vector<float> combo; // pulsaret * pulseEnv
    // combo low-passed and halved in length, level by level (2048, 1024, ...
    // 8 points), for band-limited playback. comboLevels[0] is combo.
    std::vector<std::vector<float>> comboLevels;
    static std::shared_ptr<const TableData> from(const TableSet& set);
};

// Model 2: corrections to the original's quirks. All off is Model 1, the
// original behaviour. docs/MODEL2.md describes each one.
struct Fixes {
    // Clear bugs.
    bool noChannelSwap = false;     // left is left: no final .reverse
    bool loopEndBothWays = false;   // Loop off ends a backwards run at the start of the tables
    bool controlsAtStart = false;   // control tables live from the first sample: no silent first pulses
    bool burstFromFirstStep = false;// the burst pattern starts on its first step

    // Signal quality.
    bool silenceAfterPulsaret = false; // zero, not the last table value, once the pulsaret ends
    bool exactFormant = false;      // fractional pulsaret length: formants in tune and gliding smoothly
    bool smoothControls = false;    // control tables interpolated per sample, not per 64-sample block
    bool rateIndependentDC = false; // DC blocker cutoff stays at 7 Hz at any sample rate

    // Sound-defining choices (not in the Model 2 preset).
    bool burstEveryPulse = false;   // the burst pattern advances on every pulse, not only coin wins
    bool formantEnvelopeRule = false; // no cutoff envelope when the lowest formant >= fundamental, as intended
    bool raisedFundCap = false;     // fundamental up to 8 kHz with any number of trains
    bool bandLimited = false;       // pulsarets band-limited to the output rate

    static constexpr int kCount = 12;
    bool& operator[](int i) { return this->*member(i); }
    bool operator[](int i) const { return this->*member(i); }
    static bool Fixes::* member(int i);
    bool operator==(const Fixes& o) const { for (int i = 0; i < kCount; ++i) if ((*this)[i] != o[i]) return false; return true; }
    bool operator!=(const Fixes& o) const { return !(*this == o); }
    bool any() const { return *this != model1(); }

    static Fixes model1() { return {}; }
    static Fixes model2()
    {
        Fixes f;
        for (int i = 0; i < 8; ++i) f[i] = true;
        return f;
    }
    static const char* id(int i);   // "noChannelSwap", ...
    static const char* name(int i); // "No channel swap", ...
    static const char* description(int i);
};

// A looping sound for the ring modulator (FX_RingMod window).
struct LoopSound {
    std::vector<float> left, right; // right empty for mono
    double sampleRate = 44100.0;
};

// The control panel's play controls.
struct Controls {
    std::array<bool, 3> trainOn{ true, true, true };
    std::array<float, 3> trainLevel{ 3.0f, 3.0f, 3.0f }; // 0..10; 0 mutes the train
    bool burstOn = false;
    int burstOns = 2, burstOffs = 1;
    float trainDuration = 10.0f; // seconds per pass through the tables
    float edge = 5.0f;           // 0..10, sets the pulsaret cutoff time
    float masterVolume = 1.0f;   // 0..3
    bool mute = false;           // no new pulsars
    bool loop = true;            // off: stop at the end of the tables
    bool freeze = false;         // hold the current position in the tables
    bool backwards = false;
    float ringModBlend = 0.0f;   // dry/wet, 0..1, with a loop sound loaded

    static Controls fromSettings(const Settings& s, const Controls& keep);
    void toSettings(Settings& s) const;
};

class Engine {
public:
    static constexpr int kBlockSize = 64; // SC2 control period

    void prepare(double sampleRate);
    void setSeed(uint32_t seed) { rng.seed(seed); }

    // New tables: immediately, or (while running) crossfaded over fadeTime
    // seconds, as switching sets in the Tables Bank does.
    void setTables(std::shared_ptr<const TableData> tables);
    void crossfadeTo(std::shared_ptr<const TableData> tables, double fadeTime);
    bool isCrossfading() const { return fadeSamplesLeft > 0; }

    void setControls(const Controls& c);
    const Controls& controls() const { return ctl; }
    void setMaxFundFreqs(const std::array<float, 4>& m) { maxFundFreqs = m; } // by active train count
    void setFixes(const Fixes& f) { fixes = f; }
    const Fixes& currentFixes() const { return fixes; }
    void setRingModSound(std::shared_ptr<const LoopSound> s) { loopSound = std::move(s); loopPos = 0.0; }

    void start();
    void stop();
    bool isRunning() const { return running; }
    // True once after the tables ran out with Loop off.
    bool consumeStoppedAtEnd() { const bool s = stoppedAtEnd; stoppedAtEnd = false; return s; }

    void jumpToPosition(double position); // 0..1 through the tables
    double position() const;              // 0..1, as the cycle display shows it
    long long pulsarCount() const { return numPulsars; }
    int activeVoices() const { return numActiveVoices; }

    // Adds nothing: writes n samples of stereo output.
    void process(float* left, float* right, int n);

private:
    struct Voice {
        bool active = false;
        std::shared_ptr<const TableData> tablesA, tablesB; // B: outgoing set during a crossfade
        float mulA = 0.0f, mulB = 0.0f;
        double phase = 0.0, inc = 0.0;
        int remaining = 0;       // interpolated samples still to read
        int mipLevel = 0;        // comboLevels entry (band-limited playback)
        bool started = false;
        float gainL = 0.0f, gainR = 0.0f;
        enum class Env { None, Release, Predict } env = Env::None;
        enum class Stage { Hold, Ramp } stage = Stage::Hold;
        int holdLeft = 0;        // Predict: samples before the cutoff ramp
        float level = 1.0f, step = 0.0f;
    };

    void renderBlock();
    void controlBlock();
    void trigger(int offset);
    void spawnVoice(const std::array<float, 4>& train, float fund, bool predict, bool noEnvelope);
    bool burstNext();
    float readTable(const std::vector<float>& t, double phase01) const;
    float control(int table) const
    {
        if (!fixes.smoothControls) return ctlValues[(size_t) table];
        const float a = ctlValues[(size_t) table], b = ctlNext[(size_t) table];
        return a + (b - a) * (float) blockSample / kBlockSize;
    }
    float cutoffTime() const;

    double sampleRate = 44100.0;
    Controls ctl;
    Fixes fixes;
    std::array<float, 4> maxFundFreqs{ 3000.0f, 3000.0f, 2400.0f, 2000.0f };

    std::shared_ptr<const TableData> tables, fadeFrom;   // control tables
    std::shared_ptr<const TableData> pulsaretNew, pulsaretOld; // engine pulsaret crossfade
    int fadeSamplesTotal = 0, fadeSamplesLeft = 0;

    bool running = false, stoppedAtEnd = false;
    double tablePos = 0.0;      // 0..1, wraps
    double lastCheckedPos = 0.0;
    int checkCounter = 0, checkInterval = 1470;
    std::array<float, kNumTables> ctlValues{};
    std::array<float, kNumTables> ctlNext{}; // smoothControls: values at the next block's start
    std::array<float, kNumTables> ctlPrev{}; // K2A: the previous block's control values
    bool k2aStarted = false;
    int blockSample = 0;
    bool wrapped = false; // loopEndBothWays: the phase wrapped since the last check
    int controlsDelay = 0; // samples until the control tables start (3 ms after start)

    double impulsePhase = 1.0;
    float lowestForm = 0.0f;
    std::optional<bool> nextPulseWillPlay;
    int burstPos = -2;
    long long numPulsars = 0;
    std::mt19937 rng{ 1 };

    std::vector<Voice> voices;
    int numActiveVoices = 0;

    float masterGain = 1.0f;
    std::array<float, 2> dcX{}, dcY{};
    std::shared_ptr<const LoopSound> loopSound;
    std::shared_ptr<const LoopSound> ringSound; // the sound this run uses (loaded before Start)
    double loopPos = 0.0;
    float prevWet = 0.0f; // Pause level in the previous block

    std::array<float, kBlockSize> blockL{}, blockR{};
    int blockRead = kBlockSize;
};

// Morphs play-control settings over time the way SettingsLib.xFadeTo does:
// levels, train duration and edge glide in 0.1 s steps, burst counts step
// through whole numbers, and switches change at the end. A train switching on
// fades up from level 0; one switching off fades to 0 before it goes off. The
// burst switch does the same with the burst off count.
class SettingsMorph {
public:
    void begin(const Settings& from, const Settings& to, double fadeTime);
    bool active() const { return running; }
    // Advance by dt seconds; returns the settings to apply now.
    Settings advance(double dt);
private:
    struct Stepper { int slot = 0; int steps = 0; double interval = 0.0; float start = 0.0f, inc = 0.0f; bool done = true; };
    Settings target{}, current{}, base{};
    std::array<float, 5> morphInc{};
    int morphSteps = 1;
    bool morphDone = true;
    std::array<Stepper, 2> steppers{};
    double elapsed = 0.0;
    bool running = false;
};

// SettingsLib.randomState.
Settings randomSettings(std::mt19937& rng);

} // namespace pulsar
