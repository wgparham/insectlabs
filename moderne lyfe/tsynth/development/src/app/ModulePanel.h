// Settings of the selected module, laid out like a classic "Get Info"
// dialog: numeric fields plus envelope, waveform and shaper editors.
#pragma once

#include "MacMath.h"
#include "Patch.h"
#include "Presets.h"
#include "WaveView.h"

#include <juce_gui_basics/juce_gui_basics.h>

class ModulePanel : public juce::Component
{
public:
    std::function<void()> onEdited;          // settings changed: re-render
    std::function<void(int)> onPreview;      // hear module
    std::function<void(int)> onLoadSound;    // sample module: pick a sound file
    std::function<void(std::function<void(const juce::File&)>)> onChooseWaveform; // oscillator: pick a waveform file
    std::function<void(const turbosynth::Waveform&)> onSaveWaveform;                // oscillator: save the current waveform
    // Sample and Output windows: their tools act on the sound, so the main window does them.
    enum SoundTool { kLoadSound = 1, kRateConvert, kExtendLoop, kGainScale, kRemoveEnvelope, kCrossfade, kLoopToSelection, kStartMarker, kEndMarker, kLoopWindow };
    std::function<void(int)> onSoundTool;

    void show(turbosynth::Patch* patch, int index); // index 0 = nothing selected
    void forgetAnalysis() { harmIndex = -1; } // the waveforms changed behind the panel (undo)
    void paint(juce::Graphics&) override;
    void resized() override;

private:
    struct Field {
        std::unique_ptr<juce::Label> label;
        std::unique_ptr<juce::TextEditor> edit;
        std::function<int()> get;
        std::function<void(int)> set;
        int lo = -32768, hi = 32767;
    };
    void addField(const juce::String& name, int lo, int hi, std::function<int()> get, std::function<void(int)> set);
    void commit(Field&);
    void edited();
    turbosynth::Module* module() const;

    turbosynth::Patch* patch = nullptr;
    int index = 0;
    std::vector<std::unique_ptr<Field>> fields;
    juce::TextEditor nameEdit;
    juce::ToggleButton normalize { "Normalize output" };
    juce::TextButton hear { "Hear" }, loadSound { "Load Sound..." }, addWave { "+" }, removeWave { "-" }, saveWave { "Save" };
    WaveTimeline timeline;
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
