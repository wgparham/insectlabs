// The Model 1 window: the 32 partial faders, SmartSynth, and file and synthesis commands.
#pragma once

#include "Edit.h"
#include "Engine.h"
#include "BreakPointGraph.h"
#include "ClassicLook.h"
#include "Patch.h"

#include <juce_gui_basics/juce_gui_basics.h>

#include <array>
#include <memory>

// The look, with the partial faders drawn as the original's: a thin track with a small
// box that rides it, from 0 at the bottom to 63 at the top.
class FaderLookAndFeel : public classic::LookAndFeel
{
public:
    void drawLinearSlider(juce::Graphics&, int x, int y, int w, int h, float pos, float minPos, float maxPos,
                          juce::Slider::SliderStyle, juce::Slider&) override;
};

class MainComponent : public juce::Component
{
public:
    MainComponent();
    ~MainComponent() override;

    void setDarkMode(bool dark);

    void paint(juce::Graphics&) override;
    void resized() override;

private:
    FaderLookAndFeel lookAndFeel;

    void openPatch();
    void savePatch();
    void generate();
    void synthesize();
    void refreshFromPatch();
    void refreshGraphControls();
    void setStatus(const juce::String&);
    void alert(const juce::String& title, const juce::String& message);
    uint32_t currentSeed() const;
    softsynth::SmartSynthSettings currentSettings() const;

    softsynth::Patch patch;
    std::string patchPath;
    std::unique_ptr<juce::FileChooser> chooser;

    std::array<std::unique_ptr<juce::Slider>, softsynth::kPartials> faders;
    juce::TextButton openButton { "Open..." }, saveButton { "Save..." }, generateButton { "Generate" }, synthesizeButton { "Synthesize..." };
    juce::Label seedLabel { {}, "Random seed" };
    juce::TextEditor seedEditor;
    juce::ToggleButton normalizeToggle { "Normalize during synthesis" };
    juce::ToggleButton bufferToggle { "Match a 1 MB ST's buffer" };
    juce::ToggleButton darkToggle { "Dark Mode" };
    juce::Label status;

    // The break point graph and its partial, mode and contour scale.
    std::unique_ptr<BreakPointGraph> graph;
    juce::ComboBox partialBox, modeBox, scaleBox;
    juce::Label partialLabel { {}, "Partial" };

    // SmartSynth Set up: one combo per heading, Off then the options in the dialog's order.
    struct Group {
        juce::String name;
        juce::StringArray options;
    };
    std::vector<Group> groups;
    std::vector<std::unique_ptr<juce::Label>> groupLabels;
    std::vector<std::unique_ptr<juce::ComboBox>> groupBoxes;
};
