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
    static std::shared_ptr<const TableData> from(const TableSet& set);
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
    float control(int table) const { return ctlValues[(size_t) table]; }
    float cutoffTime() const;

    double sampleRate = 44100.0;
    Controls ctl;
    std::array<float, 4> maxFundFreqs{ 3000.0f, 3000.0f, 2400.0f, 2000.0f };

    std::shared_ptr<const TableData> tables, fadeFrom;   // control tables
    std::shared_ptr<const TableData> pulsaretNew, pulsaretOld; // engine pulsaret crossfade
    int fadeSamplesTotal = 0, fadeSamplesLeft = 0;

    bool running = false, stoppedAtEnd = false;
    double tablePos = 0.0;      // 0..1, wraps
    double lastCheckedPos = 0.0;
    int checkCounter = 0, checkInterval = 1470;
    std::array<float, kNumTables> ctlValues{};
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
    double loopPos = 0.0;

    std::array<float, kBlockSize> blockL{}, blockR{};
    int blockRead = kBlockSize;
};

// Morphs play-control settings over time the way SettingsLib.xFadeTo does:
// levels, train duration and edge glide in 0.1 s steps, burst counts step
// through whole numbers, and switches change at the end.
class SettingsMorph {
public:
    void begin(const Settings& from, const Settings& to, double fadeTime);
    bool active() const { return running; }
    // Advance by dt seconds; returns the settings to apply now.
    Settings advance(double dt);
private:
    Settings start{}, target{}, current{};
    double duration = 0.0, elapsed = 0.0, stepElapsed = 0.0;
    bool running = false;
};

// SettingsLib.randomState.
Settings randomSettings(std::mt19937& rng);

} // namespace pulsar
