// Settings of the selected module, laid out like a classic "Get Info"
// dialog: numeric fields plus envelope, waveform and shaper editors.
#pragma once

#include "Patch.h"
#include "WaveView.h"

#include <juce_gui_basics/juce_gui_basics.h>

class ModulePanel : public juce::Component
{
public:
    std::function<void()> onEdited;          // settings changed: re-render
    std::function<void(int)> onPreview;      // hear module
    std::function<void(int)> onLoadSound;    // sample module: pick a sound file

    void show(turbosynth::Patch* patch, int index); // index 0 = nothing selected
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
    juce::TextButton hear { "Hear" }, loadSound { "Load Sound..." }, addWave { "+" }, removeWave { "-" };
    juce::ComboBox waveSelect;
    BreakpointEditor envelope;
    TableEditor table;
    int wave = 0;
    bool hasEnvelope = false, hasTable = false, hasWaves = false;
};
