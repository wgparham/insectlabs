// TurboSynth main window: a classic menu bar over a desktop with the patch
// window and, as in the original, a window for each opened module (a Sample
// or Output window also shows the sound). Renders with the engine, plays through the
// audio device, exports WAV/AIFF.
#pragma once

#include "ClassicLook.h"
#include "InnerWindow.h"
#include "LoopWindow.h"
#include "Engine.h"
#include "Rules.h"
#include "ModulePanel.h"
#include "PatchView.h"
#include "WaveView.h"

#include <juce_audio_utils/juce_audio_utils.h>

#include <atomic>
#include <condition_variable>
#include <mutex>
#include <optional>
#include <thread>

class MainComponent : public juce::Component, public juce::MenuBarModel, public juce::KeyListener, private juce::Timer
{
public:
    explicit MainComponent(int forceTheme = -1); // 0 light, 1 dark, -1 saved choice
    ~MainComponent() override;

    void openFile(const juce::File&);
    void selectModule(int index) { patchView.selected = index; openModuleWindow(index); }

    void paint(juce::Graphics&) override;
    void resized() override;
    bool keyPressed(const juce::KeyPress&) override;
    // Registered on the window too, so the shortcuts work whichever control has focus.
    bool keyPressed(const juce::KeyPress& k, juce::Component* from) override { return from != this && !isParentOf(from) && keyPressed(k); }

    juce::StringArray getMenuBarNames() override;
    juce::PopupMenu getMenuForIndex(int index, const juce::String& name) override;
    void menuItemSelected(int id, int menuIndex) override;

private:
    void timerCallback() override;
    void newPatch();
    void choosePatch();
    void save(bool as);
    void exportSound();
    void writeSound(juce::File f, bool mono); // mono: a stereo sound folded to one channel
    void loadSoundFor(int index);
    void loadSoundFile(int index, const juce::File&, int channel);
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
    // Edit menu.
    void undo();
    void redo();
    void stepHistory(std::vector<turbosynth::Patch>& from, std::vector<turbosynth::Patch>& to);
    void resetHistory();          // a new or opened document starts without undo
    void copySelected();
    void clearSelected();
    void paste(const turbosynth::Module&, const turbosynth::Module* partner = nullptr); // partner: the other half of a stereo sample
    void convertToSample();
    // Options menu: the Parameter Manager's defaults and length/rate rules.
    void showParameterManager();
    void showStereoPreferences();
    void applyDefaults(turbosynth::Module&) const;      // a newly placed module
    void inheritFrom(int from, int to);                 // a new connection
    bool applyEditRules(int index);                     // after an edit: true if lengths or rates moved
    turbosynth::rules::Options ruleOptions() const;
    turbosynth::RenderOptions renderOptions() const;
    // Sample and Output windows (the Sound window edits the selected one).
    void showSoundEditor();                         // after selection, edits, renders
    std::vector<int16_t>* sampleData(int index);    // a Sample module's sound, stored in the document
    void sampleEdited(int index);                   // after the sound changed (both halves of a stereo pair)
    std::vector<int> sampleHalves(int index) const; // the module, then its stereo partner if it has one
    void convertToDualMono();
    void soundTool(int code);                       // ModulePanel::SoundTool
    bool soundSelectionActive() const;              // the Edit menu acts on the selected sound
    void soundEdit(int menuId);                     // Cut, Copy, Paste, Clear, Reverse, Silence on it
    void setLoop(int32_t start, int32_t end);       // loop markers of the selected Sample or Output
    void openLoopWindow();
    void syncLoopWindow();
    void playLoop();
    void playSamples(const std::vector<int16_t>& left, const std::vector<int16_t>& right, int rate);
    void saveLoopAsWaveform();
    void saveWaveformAs(const turbosynth::Waveform&);
    // Module windows (double-click or the Info tool opens one).
    struct ModuleWindow;
    void openModuleWindow(int index);
    void closeModuleWindow(ModuleWindow*);
    void closeAllModuleWindows();
    void refreshModuleWindows();   // after edits: every open window shows its module again
    void moduleRemoved(int index); // windows follow the renumbering
    void placeSoundStrip();        // the sound view goes to the selected module's window, if it has one
    void configurePanel(ModulePanel&);

    classic::LookAndFeel lnf;
    juce::MenuBarComponent menuBar { this };
    PatchView patchView;
    WaveView waveView;
    juce::TextButton playButton { "Play" }, stopButton { "Stop" };
    // The sound view with Play and Stop, shown in one Sample or Output window at a time.
    struct SoundStrip : juce::Component {
        SoundStrip(WaveView& w, juce::Button& p, juce::Button& s) : wave(w), play(p), stop(s) { addAndMakeVisible(wave); addAndMakeVisible(play); addAndMakeVisible(stop); }
        void resized() override;
        WaveView& wave; juce::Button &play, &stop;
    } soundStrip { waveView, playButton, stopButton };
    juce::TooltipWindow tooltips { this, 600 }; // names the editors' preset and modifier tools
    juce::Component desktop; // holds the windows
    std::unique_ptr<InnerWindow> patchWindow;
    std::vector<std::unique_ptr<ModuleWindow>> moduleWindows;
    ModuleWindow* soundHost = nullptr; // the window showing soundStrip

    turbosynth::Patch patch;
    turbosynth::Patch committed;                 // the patch as of the last edit
    std::vector<turbosynth::Patch> undoStack;    // earlier states, newest last
    std::vector<turbosynth::Patch> redoStack;    // undone states, newest last
    std::optional<turbosynth::Module> clipboard; // a cut or copied module
    std::optional<turbosynth::Module> clipboardPartner; // the other half, when that was a stereo sample
    std::vector<int16_t> soundClipboard;         // a cut or copied piece of a sample (the left, for a stereo pair)
    std::vector<int16_t> soundClipboardRight;    // the right half's piece, when copied from a stereo pair
    int soundShown = 0;                          // the Sample module whose sound the sound view shows, or 0
    std::unique_ptr<LoopWindow> loopWindow;
    bool autoPreview = false;                    // play after every edit
    struct Params {
        int rate = 44100, length = 88200, frequency = 440;
        bool inheritRate = false, inheritLength = false, dynamicLength = false, convertSamples = false;
        bool monitorLoop = false;   // Parameter Manager item 20: Play loops the output's loop
        // Stereo Preferences (DITL 10040)
        bool newDocStereo = false, padStereo = true;
        bool openBoth = true;       // stereo sound files: both channels as a linked pair, or one you choose
        int stereoRate = 15;        // 15 highest, 16 lowest, 17 left's, 18 right's
        bool monoScale = true;      // stereo saved to a mono file: halve both channels (DFLB 7)
        bool emptyStereo = false;   // an empty Sample module whose file dialog is cancelled becomes a pair (DFLB 11)
    } params;
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
