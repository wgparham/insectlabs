// Settings of the selected module, laid out like a classic "Get Info"
// dialog: numeric fields plus envelope, waveform and shaper editors.
#pragma once

#include "MacMath.h"
#include "Patch.h"
#include "Presets.h"
#include "WaveView.h"

#include <juce_gui_basics/juce_gui_basics.h>

#include <optional>

class ModulePanel : public juce::Component
{
public:
    std::function<void()> onEdited;          // settings changed: re-render
    std::function<void(int)> onPreview;      // hear module
    std::function<void(int)> onLoadSound;    // sample module: pick a sound file
    std::function<void(std::function<void(const juce::File&)>)> onChooseWaveform; // oscillator: pick a waveform file
    std::function<void(const turbosynth::Waveform&)> onSaveWaveform;                // oscillator: save the current waveform
    // Sample and Output windows: their tools act on the sound, so the main window does them.
    enum SoundTool { kLoadSound = 1, kRateConvert, kExtendLoop, kGainScale, kRemoveEnvelope, kCrossfade, kLoopToSelection, kStartMarker, kEndMarker, kLoopWindow, kSampling };
    std::function<void(int)> onSoundTool;

    void show(turbosynth::Patch* patch, int index); // index 0 = nothing selected
    void forgetAnalysis() { harmIndex = -1; } // the waveforms changed behind the panel (undo)
    // The Edit menu in an Oscillator window works on its selected waveforms.
    bool wavesSelected() const;
    bool canSelectAllWaves() const;
    void selectAllWaves();
    void clearWaves();
    void duplicateWaves();
    void paint(juce::Graphics&) override;
    void resized() override;

private:
    struct Field {
        std::unique_ptr<juce::Label> label;
        std::unique_ptr<juce::TextEditor> edit;
        std::function<int()> get;
        std::function<void(int)> set;
        int lo = -32768, hi = 32767;
        std::function<int()> hiNow; // when set, the upper limit depends on the current settings
        std::function<bool(int)> accepts; // when set, a new value the original would refuse is reverted
        const char* refusal = nullptr;    // the alert for a refused value (default: the rate and length message)
        bool refuses = false; // out-of-range text is refused with an alert, as the Info boxes do; otherwise it is clamped
    };
    void addField(const juce::String& name, int lo, int hi, std::function<int()> get, std::function<void(int)> set);
    void commit(Field&);
    void edited();
    turbosynth::Module* module() const;

    turbosynth::Patch* patch = nullptr;
    int index = 0;
    std::vector<std::unique_ptr<Field>> fields;
    juce::TextEditor nameEdit;
    juce::ToggleButton normalize { "Normalize" };
    juce::TextButton hear { "Hear" }, loadSound { "Load Sound..." }, addWave { "+" }, removeWave { "-" }, saveWave { "Save" };
    WaveTimeline timeline;
    std::set<int> waveSelection;
    std::optional<turbosynth::Waveform> armedWave; // chosen from the palette, waiting for its click on the time line
    void armWave(const turbosynth::Waveform&);
    int waveSelectionFor = -1; // the module the selection belongs to
    void sortWaves(turbosynth::Module&);
    HarmonicEditor harmEditor;
    juce::Label soundInfo; // Sample and Output: where the sound is, the loop, crossfade
    bool harmonicMode = false; // the oscillator shows the Harmonic window
    turbosynth::presets::Harmonics harm;
    int harmIndex = -1, harmWave = -1; // which waveform harm was analyzed from
    void refreshWave();             // after the current waveform changed
    BreakpointEditor envelope;
    TableEditor table;
    ToolPalette tools;
    turbosynth::mac::Random rng;
    void setupTools(turbosynth::Module&);
    int wave = 0;
    bool hasEnvelope = false, hasTable = false, hasWaves = false, hasTools = false, hasHarm = false;
};
