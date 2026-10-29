// Main console: the parameter panels of the original "laboratory signal
// generator" as Classic Mac windows (the TurboSynth port's look, with a dark
// mode), plus a plot of the grains.
#pragma once

#include "ClassicLook.h"
#include "CloudEngine.h"

#include <juce_audio_utils/juce_audio_utils.h>
#include <juce_gui_extra/juce_gui_extra.h>

// Plot of grains on the frequency-versus-time plane.
class GrainPlot : public juce::Component
{
public:
    void setGrains(std::vector<cloudgen::GrainEvent> g, double cloudStart, double cloudEnd, double lo, double hi);
    void paint(juce::Graphics&) override;

private:
    std::vector<cloudgen::GrainEvent> grains;
    double start = 0.0, end = 1.0, minY = 20.0, maxY = 20000.0;
};

class MainComponent : public juce::Component, public juce::FileDragAndDropTarget, public juce::MenuBarModel
{
public:
    explicit MainComponent(int forceTheme = -1); // 0 light, 1 dark, -1 the saved choice (snapshots force one)
    ~MainComponent() override;

    void paint(juce::Graphics&) override;
    void resized() override;

    bool isInterestedInFileDrag(const juce::StringArray& files) override;
    void filesDropped(const juce::StringArray& files, int x, int y) override;

    juce::StringArray getMenuBarNames() override;
    juce::PopupMenu getMenuForIndex(int index, const juce::String& name) override;
    void menuItemSelected(int id, int) override;

    void generateNow(); // render on the calling thread (snapshots)

private:
    // Declared first so it outlives every component that uses it.
    classic::LookAndFeel lnf;
    juce::ApplicationProperties settings;
    juce::MenuBarComponent menuBar{ this };
    void setDark(bool);

    // A labelled numeric entry ("Initial [ 5 ] g/s").
    struct Field
    {
        juce::Label label, units;
        juce::TextEditor editor;
        float value() const { return editor.getText().getFloatValue(); }
    };

    // Console stereo potentiometer: 0..100, shown as L -100 .. +100 R.
    struct Pot
    {
        juce::Label label;
        juce::Slider slider{ juce::Slider::LinearHorizontal, juce::Slider::TextBoxRight };
        juce::ToggleButton random{ "Random" };
    };

    Field& addField(const juce::String& name, const juce::String& units, double initial);
    void setupPot(Pot&, const juce::String& name, const cloudgen::StereoLocation&);
    cloudgen::CloudParams gatherParams() const;
    cloudgen::Waveform currentWave(const cloudgen::Fixes&) const;
    void updateSourceControls();
    void updateModelControls();
    void showFixesPanel();
    void showRender(int index);
    bool loadSound(const juce::File&, cloudgen::SourceSound&);
    void chooseSource();
    void generate();
    void play();
    void exportResult();

    juce::OwnedArray<Field> fields;
    Field *startTime, *duration, *densityInitial, *densityFinal, *ampInitial, *ampFinal,
          *highInitial, *highFinal, *lowInitial, *lowFinal, *grainInitial, *grainFinal;
    Pot panInitial, panFinal;

    juce::ComboBox cloudType, waveform, selectionOrder, sourceKind, outputKind;
    juce::ToggleButton grainRandom{ "Random" };
    juce::TextButton selectButton{ "SELECT..." };
    juce::Label sourceName;
    juce::TextButton generateButton{ "GENERATE" }, playButton{ "PLAY" }, exportButton{ "EXPORT..." };
    GrainPlot plot;
    juce::Label status;

    // Model 1 / Model 2 and the individual fixes.
    juce::ComboBox modelSelector, sampleRateBox, bitDepthBox;
    juce::TextButton fixesButton{ "FIXES..." };
    juce::ToggleButton compareToggle{ "A/B vs Model 1" };
    juce::TextButton showA{ "A: MODEL 1" }, showB{ "B: CURRENT" };
    cloudgen::Fixes fixes = cloudgen::Fixes::model1();

    struct Panel { juce::String title; juce::Rectangle<int> area; };
    std::vector<Panel> panels; // framed groups, laid out in resized()

    cloudgen::Waveform importedWave;
    bool haveImportedWave = false;
    cloudgen::SourceSound granulationSource;
    juce::String granulationName;

    // renders[1] is the current settings; renders[0] the Model 1 render made
    // alongside it when A/B comparing. shown selects which one plays/exports.
    std::array<cloudgen::RenderResult, 2> renders;
    int shown = 1;
    bool compared = false;
    bool lastWasText = false;
    cloudgen::CloudParams lastParams;
    const cloudgen::RenderResult& lastRender() const { return renders[(size_t) shown]; }

    juce::AudioFormatManager formats;
    juce::AudioDeviceManager deviceManager;
    juce::AudioSourcePlayer sourcePlayer;
    juce::AudioTransportSource transport;
    std::unique_ptr<juce::MemoryAudioSource> memorySource;
    juce::AudioBuffer<float> playBuffer;
    std::unique_ptr<juce::FileChooser> chooser;

    JUCE_DECLARE_NON_COPYABLE_WITH_LEAK_DETECTOR(MainComponent)
};
