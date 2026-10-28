// Pulsar Generator console: control panel, the 13 table views, the tables
// bank, ring modulator and scope, restyled as modern test equipment.
#pragma once

#include "PluginProcessor.h"
#include "TableView.h"

#include <juce_audio_processors/juce_audio_processors.h>

class ScopeView : public juce::Component
{
public:
    explicit ScopeView(PulsarProcessor& p) : proc(p) {}
    void pull(); // drain the processor's scope buffer
    void paint(juce::Graphics&) override;
private:
    PulsarProcessor& proc;
    std::array<float, 1024> l{}, r{};
    int write = 0;
};

// The FIXES... call-out: one switch per Model 2 fix, grouped as in docs/MODEL2.md.
class FixesPanel : public juce::Component
{
public:
    explicit FixesPanel(PulsarProcessor&);
    void paint(juce::Graphics&) override;
    void resized() override;
private:
    struct Row { std::unique_ptr<juce::ToggleButton> toggle; std::unique_ptr<juce::Label> text; };
    std::vector<Row> rows;
    std::vector<std::unique_ptr<juce::AudioProcessorValueTreeState::ButtonAttachment>> attachments;
    std::vector<std::pair<juce::String, int>> headings; // title, y
};

class PulsarEditor : public juce::AudioProcessorEditor, private juce::Timer
{
public:
    explicit PulsarEditor(PulsarProcessor&);
    ~PulsarEditor() override;

    void paint(juce::Graphics&) override;
    void resized() override;
    bool keyPressed(const juce::KeyPress&) override;

private:
    using SliderAttachment = juce::AudioProcessorValueTreeState::SliderAttachment;
    using ButtonAttachment = juce::AudioProcessorValueTreeState::ButtonAttachment;

    void timerCallback() override;
    void refreshTables();
    void rebuildBankButtons();
    void chooseFile(const juce::String& title, const juce::String& patterns, bool save,
                    std::function<void(const juce::File&)> action);
    void report(const juce::String& message);
    void setupSlider(juce::Slider&, juce::Label&, const juce::String& text);
    void setupToggle(juce::TextButton&, const juce::String& text);

    PulsarProcessor& proc;

    // Control panel
    std::array<juce::TextButton, 3> trainOn;
    std::array<juce::Slider, 3> trainLevel;
    std::array<juce::Label, 3> trainLabel;
    juce::TextButton burstOn, runButton, loopButton, muteButton, recordButton, freezeButton, backButton,
                     fadeButton, randomButton;
    juce::Slider burstOns, burstOffs, trainDur, edge, master, fadeVolTime;
    juce::Label burstOnsLabel, burstOffsLabel, trainDurLabel, edgeLabel, masterLabel, fadeVolLabel;
    juce::Slider cycle;
    juce::Label pulsarCount;

    // Tables bank
    juce::TextButton loadBank, saveBank, loadSet, saveSet, addCurrent, defaultTables, linkSettings;
    juce::Slider fadeTime;
    juce::Label fadeTimeLabel, bankLabel;
    juce::OwnedArray<juce::TextButton> bankButtons;
    juce::Viewport bankViewport;
    juce::Component bankList;

    // Ring modulator
    juce::TextButton ringLoad;
    juce::Slider ringMix;
    juce::Label ringLabel;

    // Model 1 / Model 2, the individual fixes, and A/B against Model 1.
    juce::ComboBox modelSelector;
    juce::TextButton fixesButton{ "FIXES..." }, showA{ "A: MODEL 1" }, showB{ "B: CURRENT" };
    void updateModelControls();
    std::optional<pulsar::Fixes> shownFixes; // what the selector last showed

    juce::OwnedArray<TableView> tableViews;
    ScopeView scope;
    juce::Label status;

    juce::OwnedArray<SliderAttachment> sliderAttachments;
    juce::OwnedArray<ButtonAttachment> buttonAttachments;
    std::unique_ptr<juce::FileChooser> chooser;

    struct Panel { juce::String title; juce::Rectangle<int> area; };
    std::vector<Panel> panels;

    // Master fade (the control panel's Fade button)
    float fadeFrom = 0.0f, fadeTo = 0.0f;
    double fadeStart = 0.0, fadeLength = 0.0;
    bool masterFading = false;

    JUCE_DECLARE_NON_COPYABLE_WITH_LEAK_DETECTOR(PulsarEditor)
};
