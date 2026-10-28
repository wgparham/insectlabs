// TurboSynth main window: a classic menu bar over a desktop with the Patch,
// Module Info and Sound windows. Renders with the engine, plays through the
// audio device, exports WAV/AIFF.
#pragma once

#include "ClassicLook.h"
#include "Engine.h"
#include "ModulePanel.h"
#include "PatchView.h"
#include "WaveView.h"

#include <juce_audio_utils/juce_audio_utils.h>

#include <atomic>
#include <condition_variable>
#include <mutex>
#include <optional>
#include <thread>

class MainComponent : public juce::Component, public juce::MenuBarModel, private juce::Timer
{
public:
    explicit MainComponent(int forceTheme = -1); // 0 light, 1 dark, -1 saved choice
    ~MainComponent() override;

    void openFile(const juce::File&);
    void selectModule(int index) { patchView.selected = index; modulePanel.show(&patch, index); }

    void paint(juce::Graphics&) override;
    void resized() override;
    bool keyPressed(const juce::KeyPress&) override;

    juce::StringArray getMenuBarNames() override;
    juce::PopupMenu getMenuForIndex(int index, const juce::String& name) override;
    void menuItemSelected(int id, int menuIndex) override;

private:
    void timerCallback() override;
    void newPatch();
    void choosePatch();
    void save(bool as);
    void exportSound();
    void loadSoundFor(int index);
    void changed();              // something edited: re-render soon
    // Rendering runs on a worker thread so the interface stays responsive.
    // module 0 = the whole patch. With play set, playback starts when it is ready.
    void render(int module = 0, bool play = false);
    void renderNow(int module); // synchronous (export)
    void workerLoop();
    void applyResult(turbosynth::RenderResult r, int module, bool play);
    void play();
    void stop();
    void setDark(bool);
    void updateTitle();

    classic::LookAndFeel lnf;
    juce::MenuBarComponent menuBar { this };
    PatchView patchView;
    ModulePanel modulePanel;
    WaveView waveView;
    juce::TextButton playButton { "Play" }, stopButton { "Stop" };
    juce::Rectangle<int> patchFrame, infoFrame, soundFrame;

    turbosynth::Patch patch;
    juce::File file;
    bool dirty = false;
    int renderPending = 0;      // countdown in timer ticks
    int previewModule = 0;
    turbosynth::RenderResult result;
    struct Job { turbosynth::Patch patch; turbosynth::RenderOptions options; int module = 0; bool play = false; };
    std::mutex jobLock;
    std::condition_variable jobReady;
    std::optional<Job> job;
    bool quitting = false, rendering = false;
    bool syncRender = false; // snapshots and export render on the calling thread
    std::thread worker;
    std::atomic<int> latestRequest { 0 };

    juce::AudioDeviceManager devices;
    juce::AudioSourcePlayer player;
    juce::AudioTransportSource transport;
    std::unique_ptr<juce::MemoryAudioSource> memorySource;
    juce::AudioBuffer<float> playBuffer;
    std::unique_ptr<juce::FileChooser> chooser;
    juce::ApplicationProperties settings;
};
