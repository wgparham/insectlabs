// Pulsar Generator processor: the engine, its parameters, and the table sets
// and bank (the original TablesBank). Runs as a standalone app or a plugin.
#pragma once

#include "PulsarEngine.h"

#include <juce_audio_formats/juce_audio_formats.h>
#include <juce_audio_processors/juce_audio_processors.h>
#include <juce_audio_utils/juce_audio_utils.h>

class PulsarProcessor : public juce::AudioProcessor, private juce::Timer
{
public:
    PulsarProcessor();
    ~PulsarProcessor() override;

    // AudioProcessor
    void prepareToPlay(double sampleRate, int samplesPerBlock) override;
    void releaseResources() override {}
    bool isBusesLayoutSupported(const BusesLayout&) const override;
    void processBlock(juce::AudioBuffer<float>&, juce::MidiBuffer&) override;
    juce::AudioProcessorEditor* createEditor() override;
    bool hasEditor() const override { return true; }
    const juce::String getName() const override { return "Pulsar Generator"; }
    bool acceptsMidi() const override { return false; }
    bool producesMidi() const override { return false; }
    double getTailLengthSeconds() const override { return 1.0; }
    int getNumPrograms() override { return 1; }
    int getCurrentProgram() override { return 0; }
    void setCurrentProgram(int) override {}
    const juce::String getProgramName(int) override { return {}; }
    void changeProgramName(int, const juce::String&) override {}
    void getStateInformation(juce::MemoryBlock&) override;
    void setStateInformation(const void*, int) override;

    juce::AudioProcessorValueTreeState params;

    // ---- Tables and bank (message thread) ----
    const pulsar::TableSet& currentTables() const { return current; }
    pulsar::TableSet& editableTables() { return current; }
    // Call after editing currentTables(): the engine picks the change up at once.
    void tablesEdited();
    // Make a set current. While playing this crossfades over the fade time and,
    // with Link Settings on, morphs the play controls to the set's settings.
    void useTables(const pulsar::TableSet& set);
    void restoreDefaultTables() { useTables(pulsar::TableSet::defaults()); }
    void undoTableSwitch(); // the "z" key: back to the previous set

    std::vector<pulsar::TableSet> bank;
    juce::String bankName;
    bool loadBank(const juce::File&, juce::String& error);
    bool saveBank(const juce::File& bankFile, juce::String& error);
    bool loadSetFile(const juce::File&, juce::String& error); // into the bank and current
    bool saveCurrentSet(const juce::File&, juce::String& error);
    void addCurrentToBank();
    void selectBankSet(int index);
    int selectedBankSet() const { return selectedSet; }

    // ---- Model 1 / Model 2 ----
    pulsar::Fixes fixes() const;            // the fix switches (A/B aside)
    void setFixes(const pulsar::Fixes& f);  // sets the fix parameters
    juce::String modelName() const;         // "Model 1 (original)", "Model 2 (corrected)", ...

    // Copies the current play controls into the current set's settings.
    void captureSettings();
    void randomizeControls();

    // ---- Ring modulator ----
    bool loadRingModSound(const juce::File&, juce::String& error);
    juce::String ringModSoundName() const { return ringModName; }

    // ---- Transport, recording (standalone), displays ----
    bool isStandalone() const { return wrapperType == wrapperType_Standalone; }
    void setRunning(bool run);
    bool isRunning() const { return engineRunning.load(); }
    bool startRecording(const juce::File& file, juce::String& error);
    void stopRecording();
    bool isRecording() const { return recorder != nullptr; }
    void jumpToPosition(double pos) { jumpRequest.store(pos); }
    double position() const { return enginePosition.load(); }
    long long pulsarCount() const { return enginePulsars.load(); }
    juce::AbstractFifo scopeFifo{ 4096 };
    std::array<float, 4096> scopeL{}, scopeR{};

    std::function<void()> onTablesChanged; // editor refresh hook (message thread)

private:
    void timerCallback() override;
    void pushTables(bool crossfade);
    pulsar::Controls readControls() const;
    void applySettingsToParams(const pulsar::Settings& s);

    // Raw parameter values, looked up once (no string work on the audio thread).
    struct ParamPointers {
        std::array<std::atomic<float>*, 3> trainOn{}, trainLevel{};
        std::atomic<float> *run{}, *burstOn{}, *burstOns{}, *burstOffs{}, *trainDur{}, *edge{}, *master{},
                           *mute{}, *loop{}, *freeze{}, *backwards{}, *ringMix{}, *fadeTime{}, *linkSettings{};
        std::array<std::atomic<float>*, pulsar::Fixes::kCount> fixes{};
        std::atomic<float>* abModel1{};
    } p;

    pulsar::Engine engine;
    pulsar::TableSet current = pulsar::TableSet::defaults(), previous = pulsar::TableSet::defaults();
    int selectedSet = -1;
    pulsar::SettingsMorph morph;
    std::mt19937 rng{ (uint32_t) juce::Time::currentTimeMillis() };

    // Hand-off to the audio thread.
    juce::SpinLock pendingLock;
    std::shared_ptr<const pulsar::TableData> pendingTables;
    bool pendingCrossfade = false;
    double pendingFadeTime = 0.0;
    std::shared_ptr<const pulsar::LoopSound> pendingLoop;
    bool pendingLoopSet = false;
    std::atomic<double> jumpRequest{ -1.0 };
    std::atomic<bool> runRequest{ false }, engineRunning{ false }, stoppedAtEnd{ false };
    std::atomic<double> enginePosition{ 0.0 };
    std::atomic<long long> enginePulsars{ 0 };
    juce::String ringModName;

    juce::TimeSliceThread writerThread{ "Pulsar Generator recorder" };
    std::unique_ptr<juce::AudioFormatWriter::ThreadedWriter> recorder;
    juce::SpinLock recorderLock;

    JUCE_DECLARE_NON_COPYABLE_WITH_LEAK_DETECTOR(PulsarProcessor)
};
