// TurboSynth main window: a classic menu bar over a desktop with the patch
// window and, as in the original, a window for each opened module (a Sample
// or Output window also shows the sound). Renders with the engine, plays through the
// audio device, exports WAV/AIFF.
#pragma once

#include "ClassicLook.h"
#include "DocumentInfo.h"
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
#include <deque>
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
    void startRender(int module, bool play, bool popup);
    void renderPopup(int module); // a popup's module, rendered without playing
    void renderNow(int module); // synchronous (export)
    void workerLoop();
    void applyResult(turbosynth::RenderResult r, int module, bool play, bool popup = false);
    void play();
    void stop();
    void setDark(bool);
    void updateTitle();
    // Edit menu.
    void undo();
    void redo();
    struct HistoryEntry { turbosynth::Patch patch; std::vector<int> selection; }; // Undo puts the selection back too
    void stepHistory(std::vector<HistoryEntry>& from, std::vector<HistoryEntry>& to);
    void resetHistory();          // a new or opened document starts without undo
    void copySelected();
    void clearSelected();
    struct ClipItem { turbosynth::Module module; int partner = -1; }; // partner: the slot of the other half of a stereo sample
    std::vector<ClipItem> selectedItems() const; // the selected modules, without Outputs
    void paste(const std::vector<ClipItem>&, const std::vector<int>& alsoSelect = {});
    int firstSelected(const std::function<bool(int)>&) const; // the first selected module that qualifies, as the original's menu tests find it (F2_0095E)
    std::function<bool(int)> convertibleTest() const; // Convert to Sample: neither an Output nor a Sample
    std::function<bool(int)> monoSampleTest() const;  // Convert to Envelope or Oscillator: a Sample with no partner
    std::function<bool(int)> linkedHalfTest() const;  // Convert Stereo Sample to Dual Mono
    ModulePanel* frontWavePanel() const;              // the panel of an Oscillator window in front, whose Edit menu works on its waveforms
    void convertToSample();
    void doConvertToSample(int index);
    void convertSample(bool oscillator); // Convert to Envelope / Oscillator
    void sendLoopToOutput();
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
    struct HearPopup;
    void openModuleWindow(int index);
    void closeModuleWindow(ModuleWindow*);
    void closeAllModuleWindows();
    void refreshModuleWindows();   // after edits: every open window shows its module again
    void moduleRemoved(int index); // windows follow the renumbering
    void showDocumentInfo();       // File > Get Info: samples used and free, the memory indicator
    void refreshDocumentInfo();    // after edits, while the Info box is open
    void releaseClipboard();
    void placeSoundStrip();        // the sound view goes to the selected module's window, if it has one
    void openHearPopup(int module);     // the speaker icon: the module's waveform in a popup, kept until closed
    void closeHearPopup(HearPopup*);
    void refreshHearPopups();           // after edits: every open popup renders its module again
    void nextHearRefresh();             // starts the next popup's render once the worker is free
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
    // The speaker icon's popups: one per module, kept until closed and re-rendered after edits.
    struct HearPopup {
        int index = 0;
        WaveView view;
        std::unique_ptr<InnerWindow> win;
    };
    std::vector<std::unique_ptr<HearPopup>> hearPopups;
    std::deque<int> popupQueue; // modules waiting to re-render

    turbosynth::Patch patch;
    turbosynth::Patch committed;                 // the patch as of the last edit
    std::vector<int> committedSelection;         // the selection going with it
    std::vector<HistoryEntry> undoStack;         // earlier states, newest last
    std::vector<HistoryEntry> redoStack;         // undone states, newest last
    std::vector<ClipItem> clipboard;             // the cut or copied modules
    std::vector<int16_t> soundClipboard;         // a cut or copied piece of a sample (the left, for a stereo pair)
    std::vector<int16_t> soundClipboardRight;    // the right half's piece, when copied from a stereo pair
    int soundShown = 0;                          // the Sample module whose sound the sound view shows, or 0
    std::unique_ptr<LoopWindow> loopWindow;
    std::unique_ptr<DocumentInfo> docInfo;  // the document's Info box (File > Get Info)
    std::unique_ptr<InnerWindow> docWindow;
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
        bool directStereo = false;  // Direct Sampling Mono or Stereo (-$519); direct sampling itself is not ported, so the choice is only kept
    } params;
    juce::File file;
    bool dirty = false;
    int renderPending = 0;      // countdown in timer ticks
    int previewModule = 0;
    turbosynth::RenderResult result;
    struct Job { turbosynth::Patch patch; turbosynth::RenderOptions options; int module = 0; bool play = false; bool popup = false; };
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
