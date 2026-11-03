#include "MainComponent.h"

using namespace softsynth;

namespace {

// The 1 MB ST's sample buffer, 690,512 bytes (EmuTOS, see RE-NOTES). The engine counts
// samples, so this is the byte count halved, as in softsynth-cli.
constexpr int32_t kMegabyteSTBufferSamples = 690512 >> 1;

// Each heading's options, in the order of the Set up dialog. The engine stores the
// index: 0 is the heading switched off, 1 the first option.
const std::array<std::pair<const char*, const char*>, 12> kGroupNames = { {
    { "Harmonic series", "All,Odd,Even" },
    { "Partial detuning", "Slight,Medium,Extreme" },
    { "Attack rate", "Fast,Medium,Slow" },
    { "Decay rate", "Fast,Medium,Slow" },
    { "Harmonic range", "High,Medium,Low" },
    { "Doubling", "Slight,Medium,Extreme" },
    { "Frequency attack", "Bend,Bite,Blip" },
    { "Secondary level", "High,Medium,Low" },
    { "Harmonic filter", "Slight,Medium,Extreme" },
    { "Frequency movement", "Slight,Medium,Extreme" },
    { "Percussion", "Dink,Bump,Click" },
    { "Fade", "Equal rate,Highs first,Lows first" },
} };

// Maps a heading's group to its field, in the order of kGroupNames.
int* settingField(SmartSynthSettings& s, size_t group)
{
    int* const fields[12] = { &s.harmonicSeries, &s.partialDetuning, &s.attackRate, &s.decayRate,
                              &s.harmonicRange, &s.doubling, &s.frequencyAttack, &s.secondaryLevel,
                              &s.harmonicFilter, &s.frequencyMovement, &s.percussion, &s.fade };
    return fields[group];
}

const int& settingField(const SmartSynthSettings& s, size_t group)
{
    const int* const fields[12] = { &s.harmonicSeries, &s.partialDetuning, &s.attackRate, &s.decayRate,
                                    &s.harmonicRange, &s.doubling, &s.frequencyAttack, &s.secondaryLevel,
                                    &s.harmonicFilter, &s.frequencyMovement, &s.percussion, &s.fade };
    return *fields[group];
}

} // namespace

void FaderLookAndFeel::drawLinearSlider(juce::Graphics& g, int x, int y, int w, int h, float pos, float minPos,
                                          float maxPos, juce::Slider::SliderStyle style, juce::Slider& s)
{
    if (style != juce::Slider::LinearVertical) {
        classic::LookAndFeel::drawLinearSlider(g, x, y, w, h, pos, minPos, maxPos, style, s);
        return;
    }
    const auto& t = classic::theme();
    const float cx = (float) x + (float) w * 0.5f;
    g.setColour(t.ink);
    g.drawVerticalLine(juce::roundToInt(cx), (float) y, (float) (y + h));

    // pos is the box's centre, already at the value's height (top is the largest).
    const float boxW = juce::jmin(18.0f, (float) w - 6.0f), boxH = 7.0f;
    const juce::Rectangle<float> box(cx - boxW * 0.5f, pos - boxH * 0.5f, boxW, boxH);
    g.setColour(t.paper);
    g.fillRect(box);
    g.setColour(t.ink);
    g.drawRect(box, 1.0f);
}

MainComponent::MainComponent()
{
    setLookAndFeel(&lookAndFeel);
    // The untitled patch is New with the Akai S900, as the program starts (RE-NOTES, startup defaults).
    newPatch(patch, Sampler::AkaiS900);

    for (int i = 0; i < kPartials; ++i) {
        auto slider = std::make_unique<juce::Slider>(juce::Slider::LinearVertical, juce::Slider::TextBoxBelow);
        slider->setRange(0, kFaderMax, 1);
        slider->setTextBoxStyle(juce::Slider::TextBoxBelow, false, 36, 18);
        slider->setComponentID("fader" + juce::String(i));
        slider->setTooltip("Partial " + juce::String(i + 1) + " fader");
        slider->onValueChange = [this, i, s = slider.get()] {
            writeBe16(patch.partial(i) + kFader, (uint16_t) (int) s->getValue());
        };
        addAndMakeVisible(*slider);
        faders[(size_t) i] = std::move(slider);
    }

    for (auto* b : { &openButton, &saveButton, &generateButton, &synthesizeButton })
        addAndMakeVisible(*b);
    openButton.onClick = [this] { openPatch(); };
    saveButton.onClick = [this] { savePatch(); };
    generateButton.onClick = [this] { generate(); };
    synthesizeButton.onClick = [this] { synthesize(); };

    seedEditor.setText("1", false);
    seedEditor.setInputRestrictions(10, "0123456789");
    addAndMakeVisible(seedLabel);
    addAndMakeVisible(seedEditor);
    normalizeToggle.setToggleState(true, juce::dontSendNotification);
    addAndMakeVisible(normalizeToggle);
    addAndMakeVisible(bufferToggle);
    addAndMakeVisible(darkToggle);
    darkToggle.onClick = [this] { setDarkMode(darkToggle.getToggleState()); };
    bufferToggle.setTooltip("The original's buffer was its free memory. FM sounds longer than this change where it splits.");
    addAndMakeVisible(status);
    status.setText("Open a .PRM file, or make a new one with Generate.", juce::dontSendNotification);

    groups.reserve(kGroupNames.size());
    for (const auto& [name, options] : kGroupNames) {
        Group g;
        g.name = name;
        g.options.addTokens(options, ",", "");
        groups.push_back(g);
    }
    groupLabels.resize(groups.size());
    groupBoxes.resize(groups.size());
    for (size_t g = 0; g < groups.size(); ++g) {
        auto box = std::make_unique<juce::ComboBox>();
        box->addItem("Off", 1);
        for (int o = 0; o < groups[g].options.size(); ++o)
            box->addItem(groups[g].options[o], o + 2);
        box->setTooltip(groups[g].name);
        // Frequency attack and Frequency movement switch each other off (F003E10).
        box->onChange = [this, g] {
            if (groupBoxes[g]->getSelectedId() == 1)
                return;
            if (g == 6)
                groupBoxes[9]->setSelectedId(1, juce::dontSendNotification);
            else if (g == 9)
                groupBoxes[6]->setSelectedId(1, juce::dontSendNotification);
        };
        groupLabels[g] = std::make_unique<juce::Label>(juce::String(), groups[g].name);
        addAndMakeVisible(*groupLabels[g]);
        addAndMakeVisible(*box);
        groupBoxes[g] = std::move(box);
    }
    // The defaults are the startup code's (0x8A0), as in SmartSynthSettings.
    const SmartSynthSettings defaults;
    for (size_t g = 0; g < groups.size(); ++g)
        groupBoxes[g]->setSelectedId(settingField(defaults, g) + 1, juce::dontSendNotification);

    graph = std::make_unique<BreakPointGraph>(patch, [this](const juce::String& text) { setStatus(text); });
    addAndMakeVisible(*graph);

    for (int i = 1; i <= kPartials; ++i)
        partialBox.addItem(juce::String(i), i);
    partialBox.setSelectedId(1, juce::dontSendNotification);
    partialBox.onChange = [this] {
        graph->setPartial(partialBox.getSelectedId() - 1);
        refreshGraphControls();
    };
    modeBox.addItem("Amplitude envelope", 1);
    modeBox.addItem("Frequency contour", 2);
    modeBox.setSelectedId(1, juce::dontSendNotification);
    modeBox.onChange = [this] {
        graph->setMode(modeBox.getSelectedId() == 1 ? BreakPointGraph::Mode::Amplitude : BreakPointGraph::Mode::Frequency);
        refreshGraphControls();
    };
    // The partial's scale (F003E10 and the Partial screen): 2% is 1, 50% is 2.
    scaleBox.addItem("Scale 2%", 1);
    scaleBox.addItem("Scale 50%", 2);
    scaleBox.onChange = [this] {
        patch.partial(partialBox.getSelectedId() - 1)[kScale] = scaleBox.getSelectedId() == 1 ? 1 : 2;
        graph->repaint();
    };
    for (auto* c : { &partialBox, &modeBox, &scaleBox })
        addAndMakeVisible(*c);
    addAndMakeVisible(partialLabel);

    refreshFromPatch();
    // Fit the window to the screen it opens on: 1100 by 720 at most, with room for the title bar.
    const auto usable = juce::Desktop::getInstance().getDisplays().getPrimaryDisplay()->userArea;
    setSize(juce::jmin(1100, usable.getWidth() - 40), juce::jmin(720, usable.getHeight() - 80));
}

MainComponent::~MainComponent()
{
    setLookAndFeel(nullptr);
}

void MainComponent::paint(juce::Graphics& g)
{
    g.fillAll(classic::theme().paper);
}

void MainComponent::setDarkMode(bool dark)
{
    classic::setDark(dark);
    lookAndFeel.refresh();
    darkToggle.setToggleState(dark, juce::dontSendNotification);
    repaint();
    graph->repaint();
}

void MainComponent::resized()
{
    auto area = getLocalBounds().reduced(10);

    auto toolbar = area.removeFromTop(30);
    for (auto* b : { &openButton, &saveButton, &generateButton, &synthesizeButton }) {
        b->setBounds(toolbar.removeFromLeft(110));
        toolbar.removeFromLeft(6);
    }
    seedLabel.setBounds(toolbar.removeFromLeft(90));
    seedEditor.setBounds(toolbar.removeFromLeft(80));
    toolbar.removeFromLeft(16);
    normalizeToggle.setBounds(toolbar.removeFromLeft(210));

    auto controls = area.removeFromTop(28);
    partialLabel.setBounds(controls.removeFromLeft(56));
    partialBox.setBounds(controls.removeFromLeft(70));
    controls.removeFromLeft(10);
    modeBox.setBounds(controls.removeFromLeft(180));
    controls.removeFromLeft(10);
    scaleBox.setBounds(controls.removeFromLeft(120));
    controls.removeFromLeft(16);
    darkToggle.setBounds(controls.removeFromLeft(110));
    controls.removeFromLeft(10);
    bufferToggle.setBounds(controls.removeFromLeft(210));

    area.removeFromTop(6);
    graph->setBounds(area.removeFromTop(260));
    area.removeFromTop(8);

    status.setBounds(area.removeFromBottom(22));
    area.removeFromBottom(8);

    // Set up, six headings a row, two rows.
    auto setup = area.removeFromBottom(120);
    const int columns = 6;
    const int cellWidth = setup.getWidth() / columns;
    for (size_t g = 0; g < groups.size(); ++g) {
        const int col = (int) g % columns, row = (int) g / columns;
        auto cell = juce::Rectangle<int>(setup.getX() + col * cellWidth, setup.getY() + row * 60, cellWidth - 8, 56);
        groupLabels[g]->setBounds(cell.removeFromTop(20));
        groupBoxes[g]->setBounds(cell.removeFromTop(26));
    }

    area.removeFromBottom(10);
    const int faderWidth = area.getWidth() / kPartials;
    for (int i = 0; i < kPartials; ++i)
        faders[(size_t) i]->setBounds(area.getX() + i * faderWidth, area.getY(), faderWidth, area.getHeight());
}

void MainComponent::openPatch()
{
    chooser = std::make_unique<juce::FileChooser>("Open a parameter file", juce::File(), "*.PRM");
    chooser->launchAsync(juce::FileBrowserComponent::openMode | juce::FileBrowserComponent::canSelectFiles,
        [this](const juce::FileChooser& c) {
            const auto file = c.getResult();
            if (file == juce::File())
                return;
            Patch loaded;
            std::string error;
            if (!loadPatch(file.getFullPathName().toStdString(), loaded, &error)) {
                alert("Open", error);
                return;
            }
            patch = loaded;
            patchPath = file.getFullPathName().toStdString();
            refreshFromPatch();
            setStatus("Opened " + file.getFileName() + ".");
        });
}

void MainComponent::savePatch()
{
    chooser = std::make_unique<juce::FileChooser>("Save a parameter file", juce::File(patchPath), "*.PRM");
    chooser->launchAsync(juce::FileBrowserComponent::saveMode | juce::FileBrowserComponent::canSelectFiles,
        [this](const juce::FileChooser& c) {
            const auto file = c.getResult();
            if (file == juce::File())
                return;
            const auto bytes = writePatch(patch);
            if (!file.replaceWithData(bytes.data(), bytes.size())) {
                alert("Save", "Can't write " + file.getFileName());
                return;
            }
            patchPath = file.getFullPathName().toStdString();
            setStatus("Saved " + file.getFileName() + ".");
        });
}

void MainComponent::generate()
{
    XbiosRandom random(currentSeed());
    smartSynthGenerate(patch, currentSettings(), random);
    refreshFromPatch();
    setStatus("Generated with seed " + juce::String(currentSeed()) + ". Break points are recalculated at synthesis.");
}

void MainComponent::synthesize()
{
    RenderOptions options;
    options.normalize = normalizeToggle.getToggleState();
    options.bufferSamples = bufferToggle.getToggleState() ? kMegabyteSTBufferSamples : 0;

    Engine engine{XbiosRandom(currentSeed())};
    RenderResult result;
    std::string error;
    Patch working = patch;
    if (!engine.render(working, options, result, &error)) {
        alert("Synthesize", error);
        return;
    }
    // Synthesis writes the calculated break points back into the patch, as the original does.
    patch = working;
    refreshFromPatch();

    chooser = std::make_unique<juce::FileChooser>("Save the sound", juce::File(), "*.SD");
    chooser->launchAsync(juce::FileBrowserComponent::saveMode | juce::FileBrowserComponent::canSelectFiles,
        [this, samples = std::make_shared<std::vector<int16_t>>(std::move(result.samples)), warnings = result.warnings](const juce::FileChooser& c) {
            const auto file = c.getResult();
            if (file == juce::File())
                return;
            const auto bytes = soundDesignerFile(patch, *samples);
            if (!file.replaceWithData(bytes.data(), bytes.size())) {
                alert("Synthesize", "Can't write " + file.getFileName());
                return;
            }
            juce::String message = "Wrote " + file.getFileName() + ".";
            for (const auto& w : warnings)
                message << " " << juce::String(w);
            setStatus(message);
        });
}

void MainComponent::refreshFromPatch()
{
    for (int i = 0; i < kPartials; ++i)
        faders[(size_t) i]->setValue(patch.fader(i), juce::dontSendNotification);
    refreshGraphControls();
}

void MainComponent::refreshGraphControls()
{
    const int i = partialBox.getSelectedId() - 1;
    scaleBox.setSelectedId(patch.partial(i)[kScale] == 1 ? 1 : 2, juce::dontSendNotification);
    graph->repaint();
}

void MainComponent::setStatus(const juce::String& text)
{
    status.setText(text, juce::dontSendNotification);
}

void MainComponent::alert(const juce::String& title, const juce::String& message)
{
    juce::AlertWindow::showMessageBoxAsync(juce::MessageBoxIconType::WarningIcon, title, message);
    setStatus(message);
}

uint32_t MainComponent::currentSeed() const
{
    return (uint32_t) seedEditor.getText().getLargeIntValue();
}

SmartSynthSettings MainComponent::currentSettings() const
{
    SmartSynthSettings s;
    for (size_t g = 0; g < groups.size(); ++g)
        *settingField(s, g) = groupBoxes[g]->getSelectedId() - 1;
    return s;
}
