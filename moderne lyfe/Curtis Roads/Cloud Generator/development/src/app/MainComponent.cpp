#include "MainComponent.h"
#include "Waveforms.h"

#include <cmath>
#include <cstring>

namespace {

enum MenuIds { kSelectSource = 1, kGenerate, kPlay, kExport, kQuit, kDark = 20, kAbout = 30 };

// Renders one or more clouds on a background thread behind a progress window
// with Stop (several for an A/B comparison).
class RenderJob : public juce::ThreadWithProgressWindow
{
public:
    struct Item { cloudgen::CloudParams params; cloudgen::Waveform wave; };

    RenderJob(std::vector<Item> items, const cloudgen::SourceSound* s, bool audio,
              std::function<void(std::vector<cloudgen::RenderResult>)> done)
        : ThreadWithProgressWindow("Generating cloud...", true, true),
          jobs(std::move(items)), source(s), renderAudio(audio), onDone(std::move(done))
    {
        setStatusMessage("Processing...");
    }

    void run() override
    {
        for (size_t i = 0; i < jobs.size() && !threadShouldExit(); ++i) {
            const double base = (double) i / (double) jobs.size(), share = 1.0 / (double) jobs.size();
            results.push_back(cloudgen::render(jobs[i].params, jobs[i].wave, source, renderAudio,
                [&, base, share](double fraction, const cloudgen::GrainEvent&) {
                    setProgress(base + fraction * share);
                    return !threadShouldExit();
                }));
        }
    }

    void threadComplete(bool) override
    {
        onDone(std::move(results));
        delete this;
    }

private:
    std::vector<Item> jobs;
    const cloudgen::SourceSound* source;
    bool renderAudio;
    std::function<void(std::vector<cloudgen::RenderResult>)> onDone;
    std::vector<cloudgen::RenderResult> results;
};

struct FixInfo { const char* group; const char* name; bool cloudgen::Fixes::* flag; const char* tip; };

const FixInfo kFixInfo[] = {
    { "Bugs", "Interpolate over duration", &cloudgen::Fixes::interpolateOverDuration,
      "Bandlimits, amplitude, grain duration and fixed panning reach their final values when the start time is not 0." },
    { "Bugs", "Coherent random panning", &cloudgen::Fixes::coherentPanning,
      "With one Random box checked, grains move between a random position and the other, fixed setting." },
    { "Bugs", "Grain duration range", &cloudgen::Fixes::grainDurationRange,
      "Random grain durations fall between Minimum and Maximum, drawn fresh for every grain." },
    { "Bugs", "Wavetable wrap", &cloudgen::Fixes::wavetableWrap,
      "All 512 table entries are used and the oscillator wraps to the start instead of reading into the envelope." },
    { "Bugs", "Oscillator without 16-bit wrap", &cloudgen::Fixes::oscillatorNoWrap,
      "Interpolation between table entries no longer overflows at square and saw edges." },
    { "Bugs", "Sawtooth first point", &cloudgen::Fixes::sawtoothFirstPoint,
      "The sawtooth's first sample lies on its ramp (0) instead of 16383.5." },
    { "Bugs", "Safe granulation", &cloudgen::Fixes::safeGranulation,
      "Grains start on whole stereo frames and stay inside the source file." },
    { "Bugs", "Resample source", &cloudgen::Fixes::resampleSource,
      "Sources recorded at other sample rates play at their correct pitch." },
    { "Bugs", "Drop unused random draw", &cloudgen::Fixes::dropUnusedRandom,
      "Removes a random number the original drew and discarded for every grain." },
    { "Signal quality", "Floating-point mix", &cloudgen::Fixes::floatMix,
      "Grains are summed in floating point and clipped once, instead of wrapping overloads to -32768." },
    { "Signal quality", "Smooth envelope", &cloudgen::Fixes::smoothEnvelope,
      "A continuous Gaussian replaces the 512-step table, which stair-steps on long grains." },
    { "Signal quality", "Keep cloud tail", &cloudgen::Fixes::keepCloudTail,
      "Grains near the end ring out instead of being cut off or dropped." },
    { "Sound-defining", "Full-scale envelope", &cloudgen::Fixes::fullScaleEnvelope,
      "The Gaussian peaks at full scale instead of about 0.80 (louder grains)." },
    { "Sound-defining", "Logarithmic frequency spread", &cloudgen::Fixes::logFrequency,
      "Grain frequencies are spread evenly in pitch between the bandlimits rather than evenly in Hz." },
};

// Toggle list shown in a call-out from the FIXES button.
class FixesPanel : public juce::Component
{
public:
    FixesPanel(cloudgen::Fixes& target, std::function<void()> changed) : fixes(target), onChange(std::move(changed))
    {
        const char* group = "";
        for (const auto& info : kFixInfo) {
            if (std::strcmp(group, info.group) != 0) {
                group = info.group;
                auto* heading = headings.add(new juce::Label({}, juce::String(group).toUpperCase()));
                heading->setFont(classic::uiFont(12.0f));
                addAndMakeVisible(heading);
                rows.push_back({ heading, nullptr });
            }
            auto* toggle = toggles.add(new juce::ToggleButton(info.name));
            toggle->setTooltip(info.tip);
            toggle->setToggleState(fixes.*(info.flag), juce::dontSendNotification);
            toggle->onClick = [this, toggle, flag = info.flag] { fixes.*flag = toggle->getToggleState(); onChange(); };
            addAndMakeVisible(toggle);
            rows.push_back({ nullptr, toggle });
        }
        for (auto* b : { &model1, &model2 }) addAndMakeVisible(b);
        model1.onClick = [this] { apply(cloudgen::Fixes::model1()); };
        model2.onClick = [this] { apply(cloudgen::Fixes::model2()); };
        setSize(320, (int) rows.size() * 24 + 44);
    }

    void resized() override
    {
        auto r = getLocalBounds().reduced(8);
        auto buttons = r.removeFromBottom(28);
        model1.setBounds(buttons.removeFromLeft(buttons.getWidth() / 2).reduced(2));
        model2.setBounds(buttons.reduced(2));
        for (auto& row : rows) {
            auto line = r.removeFromTop(24);
            if (row.heading) row.heading->setBounds(line);
            else row.toggle->setBounds(line.withTrimmedLeft(8));
        }
    }

private:
    void apply(const cloudgen::Fixes& preset)
    {
        fixes = preset;
        size_t i = 0;
        for (const auto& info : kFixInfo)
            toggles[(int) i++]->setToggleState(fixes.*(info.flag), juce::dontSendNotification);
        onChange();
    }

    struct Row { juce::Label* heading; juce::ToggleButton* toggle; };
    cloudgen::Fixes& fixes;
    std::function<void()> onChange;
    juce::OwnedArray<juce::Label> headings;
    juce::OwnedArray<juce::ToggleButton> toggles;
    std::vector<Row> rows;
    juce::TextButton model1{ "Model 1 preset" }, model2{ "Model 2 preset" };
    juce::TooltipWindow tooltips{ this };
};

bool sameFixes(const cloudgen::Fixes& a, const cloudgen::Fixes& b)
{
    for (const auto& info : kFixInfo)
        if (a.*(info.flag) != b.*(info.flag)) return false;
    return true;
}

} // namespace

// ---------------------------------------------------------------------------
void GrainPlot::setGrains(std::vector<cloudgen::GrainEvent> g, double cloudStart, double cloudEnd, double lo, double hi)
{
    grains = std::move(g);
    start = cloudStart;
    end = cloudEnd > cloudStart ? cloudEnd : cloudStart + 1.0;
    minY = lo;
    maxY = hi > lo ? hi : lo + 1.0;
    repaint();
}

void GrainPlot::paint(juce::Graphics& g)
{
    // 1-bit: paper, a dotted grid, and each grain as a 3x3 ink dot.
    const auto& t = classic::theme();
    auto r = getLocalBounds();
    g.fillAll(t.paper);

    const auto plotArea = r.reduced(28, 22);
    g.setColour(t.ink);
    g.drawRect(plotArea.expanded(1), 1);
    for (int i = 1; i < 10; ++i) {
        const int x = plotArea.getX() + plotArea.getWidth() * i / 10;
        for (int y = plotArea.getY(); y < plotArea.getBottom(); y += 3) g.fillRect(x, y, 1, 1);
    }
    for (int i = 1; i < 8; ++i) {
        const int y = plotArea.getY() + plotArea.getHeight() * i / 8;
        for (int x = plotArea.getX(); x < plotArea.getRight(); x += 3) g.fillRect(x, y, 1, 1);
    }

    g.setFont(classic::smallFont(10.0f));
    g.drawText(juce::String(maxY, 0) + " Hz", r.withHeight(20).reduced(8, 0), juce::Justification::centredLeft);
    g.drawText(juce::String(minY, 0) + " Hz", r.withTrimmedTop(r.getHeight() - 20).reduced(8, 0), juce::Justification::centredLeft);
    g.drawText(juce::String(end, 2) + " s", r.withTrimmedTop(r.getHeight() - 20).reduced(8, 0), juce::Justification::centredRight);

    for (const auto& gr : grains) {
        const int x = plotArea.getX() + (int) (plotArea.getWidth() * (gr.onset - start) / (end - start));
        // Granulated grains carry no frequency; plot them along the centre line.
        const float yn = gr.frequency > 0.0f ? (float) ((gr.frequency - minY) / (maxY - minY)) : 0.5f;
        const int y = plotArea.getBottom() - (int) ((float) plotArea.getHeight() * juce::jlimit(0.0f, 1.0f, yn));
        g.fillRect(x - 1, y - 1, 3, 3);
    }
}

// ---------------------------------------------------------------------------
MainComponent::Field& MainComponent::addField(const juce::String& name, const juce::String& units, double initial)
{
    auto* f = fields.add(new Field());
    f->label.setText(name, juce::dontSendNotification);
    f->label.setJustificationType(juce::Justification::centredRight);
    f->units.setText(units, juce::dontSendNotification);
    f->editor.setText(juce::String(initial), false);
    f->editor.setInputRestrictions(12, "-0123456789.");
    f->editor.setJustification(juce::Justification::centredRight);
    for (juce::Component* c : { (juce::Component*) &f->label, (juce::Component*) &f->units, (juce::Component*) &f->editor })
        addAndMakeVisible(c);
    return *f;
}

void MainComponent::setupPot(Pot& pot, const juce::String& name, const cloudgen::StereoLocation& initial)
{
    pot.label.setText(name, juce::dontSendNotification);
    pot.slider.setRange(0.0, 100.0, 1.0);
    pot.slider.setValue(initial.position, juce::dontSendNotification);
    pot.slider.textFromValueFunction = [](double v) { return juce::String(juce::roundToInt(v * 2.0 - 100.0)); };
    pot.slider.valueFromTextFunction = [](const juce::String& t) { return (t.getDoubleValue() + 100.0) / 2.0; };
    pot.slider.setTextBoxStyle(juce::Slider::TextBoxRight, false, 44, 20);
    pot.slider.updateText();
    pot.random.setToggleState(initial.random, juce::dontSendNotification);
    pot.random.onClick = [&pot] { pot.slider.setEnabled(!pot.random.getToggleState()); };
    pot.slider.setEnabled(!initial.random);
    for (juce::Component* c : { (juce::Component*) &pot.label, (juce::Component*) &pot.slider, (juce::Component*) &pot.random })
        addAndMakeVisible(c);
}

MainComponent::MainComponent(int forceTheme)
{
    juce::PropertiesFile::Options o;
    o.applicationName = "Cloud Generator";
    o.filenameSuffix = ".settings";
    o.osxLibrarySubFolder = "Application Support";
    settings.setStorageParameters(o);
    classic::setDark(forceTheme >= 0 ? forceTheme == 1 : settings.getUserSettings()->getBoolValue("darkMode", false));
    lnf.refresh();
    setLookAndFeel(&lnf);
    juce::LookAndFeel::setDefaultLookAndFeel(&lnf); // dialogs and the Fixes call-out too
    addAndMakeVisible(menuBar);
    formats.registerBasicFormats();

    const cloudgen::CloudParams d;
    startTime      = &addField("Start Time", "sec", d.startTime);
    duration       = &addField("Duration", "sec", d.duration);
    densityInitial = &addField("Initial", "g/s", d.densityInitial);
    densityFinal   = &addField("Final", "g/s", d.densityFinal);
    ampInitial     = &addField("Initial", "%", d.amplitudeInitial);
    ampFinal       = &addField("Final", "%", d.amplitudeFinal);
    highInitial    = &addField("Initial", "Hz", d.highInitial);
    highFinal      = &addField("Final", "Hz", d.highFinal);
    lowInitial     = &addField("Initial", "Hz", d.lowInitial);
    lowFinal       = &addField("Final", "Hz", d.lowFinal);
    grainInitial   = &addField("Initial", "sec", d.grainDurationInitial);
    grainFinal     = &addField("Final", "sec", d.grainDurationFinal);
    setupPot(panInitial, "Initial", d.stereoInitial);
    setupPot(panFinal, "Final", d.stereoFinal);

    cloudType.addItemList({ "Synchronous", "Asynchronous" }, 1);
    cloudType.setSelectedId(1);
    sourceKind.addItemList({ "Synthetic", "Granulated" }, 1);
    sourceKind.setSelectedId(1);
    sourceKind.onChange = [this] { updateSourceControls(); };
    waveform.addItemList({ "Sine", "Square", "Sawtooth", "Imported" }, 1);
    waveform.setItemEnabled(4, false);
    waveform.setSelectedId(1);
    selectionOrder.addItemList({ "Random", "Statistical Evolution", "Deterministic Progression" }, 1);
    selectionOrder.setSelectedId(1);
    outputKind.addItemList({ "AIFF / WAV", "Text" }, 1);
    outputKind.setSelectedId(1);
    sourceName.setJustificationType(juce::Justification::centredLeft);
    // Shares the waveform menu's slot; it must never swallow the menu's clicks.
    sourceName.setInterceptsMouseClicks(false, false);

    modelSelector.addItemList({ "Model 1 (original)", "Model 2 (corrected)" }, 1);
    modelSelector.setSelectedId(1, juce::dontSendNotification);
    modelSelector.onChange = [this] {
        if (modelSelector.getSelectedId() == 0) return; // "Custom" text
        fixes = modelSelector.getSelectedId() == 2 ? cloudgen::Fixes::model2() : cloudgen::Fixes::model1();
        updateModelControls();
    };
    fixesButton.onClick = [this] { showFixesPanel(); };
    sampleRateBox.addItemList({ "44.1 kHz", "48 kHz", "88.2 kHz", "96 kHz" }, 1);
    sampleRateBox.setSelectedId(1);
    bitDepthBox.addItemList({ "16-bit", "24-bit", "32-bit float" }, 1);
    bitDepthBox.setSelectedId(1);
    compareToggle.setTooltip("Also render Model 1 from the same random seed, to compare A and B");
    showA.setClickingTogglesState(true);
    showB.setClickingTogglesState(true);
    showA.setRadioGroupId(1);
    showB.setRadioGroupId(1);
    showA.onClick = [this] { showRender(0); };
    showB.onClick = [this] { showRender(1); };

    for (juce::Component* c : std::initializer_list<juce::Component*>{ &cloudType, &sourceKind, &waveform, &selectionOrder,
             &outputKind, &grainRandom, &selectButton, &sourceName, &generateButton, &playButton, &exportButton, &plot, &status,
             &modelSelector, &fixesButton, &sampleRateBox, &bitDepthBox, &compareToggle, &showA, &showB })
        addAndMakeVisible(c);
    showA.setVisible(false); // shown after an A/B render
    showB.setVisible(false);

    // The original relabels the grain duration fields Maximum (first) and
    // Minimum (second) when Random is checked.
    grainRandom.onClick = [this] {
        const bool r = grainRandom.getToggleState();
        grainInitial->label.setText(r ? "Maximum" : "Initial", juce::dontSendNotification);
        grainFinal->label.setText(r ? "Minimum" : "Final", juce::dontSendNotification);
    };

    selectButton.onClick = [this] { chooseSource(); };
    generateButton.onClick = [this] { generate(); };
    playButton.onClick = [this] { play(); };
    exportButton.onClick = [this] { exportResult(); };
    status.setText("Ready.", juce::dontSendNotification);
    updateSourceControls();
    updateModelControls();

    if (forceTheme < 0) deviceManager.initialiseWithDefaultDevices(0, 2); // no audio device for snapshots
    sourcePlayer.setSource(&transport);
    deviceManager.addAudioCallback(&sourcePlayer);

    setSize(1180, 800);
}

MainComponent::~MainComponent()
{
    transport.setSource(nullptr);
    sourcePlayer.setSource(nullptr);
    deviceManager.removeAudioCallback(&sourcePlayer);
    setLookAndFeel(nullptr);
    juce::LookAndFeel::setDefaultLookAndFeel(nullptr);
}

// ---------------------------------------------------------------------------
juce::StringArray MainComponent::getMenuBarNames() { return { "File", "View", "Help" }; }

juce::PopupMenu MainComponent::getMenuForIndex(int index, const juce::String&)
{
    juce::PopupMenu m;
    if (index == 0) {
        m.addItem(kSelectSource, "Select Source...", sourceKind.getSelectedId() == 2);
        m.addItem(kGenerate, "Generate");
        m.addItem(kPlay, "Play", !lastRender().grains.empty());
        m.addItem(kExport, "Export...", !lastRender().grains.empty());
        m.addSeparator();
        m.addItem(kQuit, "Quit");
    } else if (index == 1) {
        m.addItem(kDark, "Dark Mode", true, classic::theme().dark);
    } else {
        m.addItem(kAbout, "About Cloud Generator...");
    }
    return m;
}

void MainComponent::menuItemSelected(int id, int)
{
    switch (id) {
    case kSelectSource: chooseSource(); break;
    case kGenerate: generate(); break;
    case kPlay: play(); break;
    case kExport: exportResult(); break;
    case kQuit: juce::JUCEApplication::getInstance()->systemRequestedQuit(); break;
    case kDark: setDark(!classic::theme().dark); break;
    case kAbout:
        juce::AlertWindow::showMessageBoxAsync(juce::MessageBoxIconType::NoIcon, "Cloud Generator",
            "A port of Cloud Generator by Curtis Roads and John Alexander (1996).\n\n"
            "Model 1 follows the original program's 68000 code; Model 2 adds switchable fixes.");
        break;
    default: break;
    }
}

void MainComponent::setDark(bool dark)
{
    classic::setDark(dark);
    settings.getUserSettings()->setValue("darkMode", dark);
    settings.saveIfNeeded();
    lnf.refresh();
    sendLookAndFeelChange();
    if (auto* w = findParentComponentOfClass<juce::DocumentWindow>()) w->setBackgroundColour(classic::theme().paper);
    repaint();
}

void MainComponent::generateNow()
{
    const auto p = gatherParams();
    renders[1] = cloudgen::render(p, currentWave(p.fixes), nullptr, false);
    lastParams = p;
    showRender(1);
    status.setText("Done. " + juce::String((int) renders[1].grains.size()) + " grains, seed " + juce::String(p.seed) + ".",
                   juce::dontSendNotification);
}

void MainComponent::updateSourceControls()
{
    const bool granulated = sourceKind.getSelectedId() == 2;
    waveform.setVisible(!granulated);
    sourceName.setVisible(granulated);
    selectionOrder.setEnabled(granulated);
    sourceName.setText(granulated ? (granulationName.isEmpty() ? "none selected" : granulationName)
                                  : juce::String(), juce::dontSendNotification);
    for (Field* f : { highInitial, highFinal, lowInitial, lowFinal })
        f->editor.setEnabled(!granulated);
}

void MainComponent::updateModelControls()
{
    const bool original = sameFixes(fixes, cloudgen::Fixes::model1());
    if (original) modelSelector.setSelectedId(1, juce::dontSendNotification);
    else if (sameFixes(fixes, cloudgen::Fixes::model2())) modelSelector.setSelectedId(2, juce::dontSendNotification);
    else modelSelector.setText("Model 2 (custom fixes)", juce::dontSendNotification);

    // Other output rates and bit depths are Model 2 features.
    sampleRateBox.setEnabled(!original);
    if (original) sampleRateBox.setSelectedId(1, juce::dontSendNotification);
    bitDepthBox.setEnabled(fixes.floatMix);
    if (!fixes.floatMix) bitDepthBox.setSelectedId(1, juce::dontSendNotification);
    compareToggle.setEnabled(!original);
    if (original) compareToggle.setToggleState(false, juce::dontSendNotification);
    repaint(); // header shows the model
}

void MainComponent::showFixesPanel()
{
    auto panel = std::make_unique<FixesPanel>(fixes, [this] { updateModelControls(); });
    juce::CallOutBox::launchAsynchronously(std::move(panel), fixesButton.getScreenBounds(), nullptr);
}

void MainComponent::showRender(int index)
{
    shown = compared ? index : 1;
    showA.setToggleState(shown == 0, juce::dontSendNotification);
    showB.setToggleState(shown == 1, juce::dontSendNotification);
    const auto& p = lastParams;
    const float bands[] = { p.highInitial, p.highFinal, p.lowInitial, p.lowFinal };
    plot.setGrains(lastRender().grains, p.startTime, (double) p.startTime + p.duration,
                   *std::min_element(std::begin(bands), std::end(bands)),
                   *std::max_element(std::begin(bands), std::end(bands)));
    transport.stop();
}

cloudgen::Waveform MainComponent::currentWave(const cloudgen::Fixes& f) const
{
    switch (waveform.getSelectedId()) {
        case 2: return cloudgen::makeSquare();
        case 3: return cloudgen::makeSawtooth(f.sawtoothFirstPoint);
        case 4: if (haveImportedWave) return importedWave; break;
        default: break;
    }
    return cloudgen::makeSine();
}

bool MainComponent::loadSound(const juce::File& file, cloudgen::SourceSound& out)
{
    std::unique_ptr<juce::AudioFormatReader> reader(formats.createReaderFor(file));
    if (reader == nullptr || reader->lengthInSamples <= 0)
        return false;
    juce::AudioBuffer<float> buffer((int) reader->numChannels, (int) reader->lengthInSamples);
    reader->read(&buffer, 0, buffer.getNumSamples(), 0, true, true);
    // The original read raw 16-bit stereo frames; mono files are duplicated
    // to stereo here rather than refused.
    const int right = buffer.getNumChannels() > 1 ? 1 : 0;
    out.sampleRate = reader->sampleRate; // used by Model 2's resampling only
    out.interleaved.resize((size_t) buffer.getNumSamples() * 2);
    for (int i = 0; i < buffer.getNumSamples(); ++i)
        for (int c = 0; c < 2; ++c)
            out.interleaved[(size_t) i * 2 + (size_t) c] =
                (int16_t) juce::jlimit(-32768, 32767, juce::roundToInt(buffer.getSample(c == 0 ? 0 : right, i) * 32768.0f));
    return true;
}

void MainComponent::chooseSource()
{
    const bool granulated = sourceKind.getSelectedId() == 2;
    chooser = std::make_unique<juce::FileChooser>(granulated ? "Select a sound to granulate" : "Import waveform from a sound file",
                                                  juce::File(), formats.getWildcardForAllFormats());
    chooser->launchAsync(juce::FileBrowserComponent::openMode | juce::FileBrowserComponent::canSelectFiles,
        [this, granulated](const juce::FileChooser& fc) {
            const auto file = fc.getResult();
            if (file == juce::File()) return;
            cloudgen::SourceSound sound;
            if (!loadSound(file, sound)) {
                status.setText("Could not read " + file.getFileName(), juce::dontSendNotification);
                return;
            }
            if (granulated) {
                granulationSource = std::move(sound);
                granulationName = file.getFileName();
            } else {
                importedWave = cloudgen::importWaveform(sound);
                haveImportedWave = true;
                waveform.setItemEnabled(4, true);
                waveform.setSelectedId(4);
            }
            updateSourceControls();
        });
}

bool MainComponent::isInterestedInFileDrag(const juce::StringArray& files)
{
    return files.size() == 1 && formats.findFormatForFileExtension(juce::File(files[0]).getFileExtension()) != nullptr;
}

void MainComponent::filesDropped(const juce::StringArray& files, int, int)
{
    cloudgen::SourceSound sound;
    if (!loadSound(juce::File(files[0]), sound))
        return;
    granulationSource = std::move(sound);
    granulationName = juce::File(files[0]).getFileName();
    sourceKind.setSelectedId(2);
    updateSourceControls();
}

cloudgen::CloudParams MainComponent::gatherParams() const
{
    cloudgen::CloudParams p;
    p.startTime = startTime->value();
    p.duration = duration->value();
    p.densityInitial = densityInitial->value();
    p.densityFinal = densityFinal->value();
    p.amplitudeInitial = ampInitial->value();
    p.amplitudeFinal = ampFinal->value();
    p.highInitial = highInitial->value();
    p.highFinal = highFinal->value();
    p.lowInitial = lowInitial->value();
    p.lowFinal = lowFinal->value();
    p.grainDurationInitial = grainInitial->value();
    p.grainDurationFinal = grainFinal->value();
    p.randomGrainDuration = grainRandom.getToggleState();
    p.stereoInitial = { (int) panInitial.slider.getValue(), panInitial.random.getToggleState() };
    p.stereoFinal = { (int) panFinal.slider.getValue(), panFinal.random.getToggleState() };
    p.type = cloudType.getSelectedId() == 2 ? cloudgen::CloudType::Asynchronous : cloudgen::CloudType::Synchronous;
    p.source = sourceKind.getSelectedId() == 2 ? cloudgen::SourceKind::Granulated : cloudgen::SourceKind::Synthetic;
    p.selectionOrder = (cloudgen::SelectionOrder) (selectionOrder.getSelectedId() - 1);
    // The original seeded rand() from the clock.
    p.seed = (uint16_t) juce::Time::getMillisecondCounter();
    p.fixes = fixes;
    const double rates[] = { 44100.0, 48000.0, 88200.0, 96000.0 };
    p.sampleRate = rates[juce::jlimit(1, 4, sampleRateBox.getSelectedId()) - 1];
    return p;
}

void MainComponent::generate()
{
    const auto p = gatherParams();
    const bool granulated = p.source == cloudgen::SourceKind::Granulated;
    const auto err = cloudgen::validate(p, granulated ? &granulationSource : nullptr);
    if (!err.empty()) {
        status.setText(err, juce::dontSendNotification);
        return;
    }
    const bool text = outputKind.getSelectedId() == 2;
    const bool compare = compareToggle.isEnabled() && compareToggle.getToggleState();

    // A/B: the same settings and seed through Model 1, then the current fixes.
    std::vector<RenderJob::Item> items;
    if (compare) {
        auto original = p;
        original.fixes = cloudgen::Fixes::model1();
        original.sampleRate = cloudgen::kSampleRate;
        items.push_back({ original, currentWave(original.fixes) });
    }
    items.push_back({ p, currentWave(p.fixes) });

    transport.stop();
    generateButton.setEnabled(false);
    auto* job = new RenderJob(std::move(items), granulated ? &granulationSource : nullptr, !text,
        [this, p, text, compare](std::vector<cloudgen::RenderResult> results) {
            generateButton.setEnabled(true);
            if (results.empty()) return;
            renders[1] = std::move(results.back());
            compared = compare && results.size() == 2;
            renders[0] = compared ? std::move(results.front()) : cloudgen::RenderResult{};
            lastWasText = text;
            lastParams = p;
            showA.setVisible(compared);
            showB.setVisible(compared);
            showRender(1);
            juce::String msg = (renders[1].stopped ? "Stopped. " : "Done. ")
                               + juce::String((int) renders[1].grains.size()) + " grains, seed " + juce::String(p.seed) + ".";
            if (compared)
                msg << " Model 1: " << (int) renders[0].grains.size() << " grains.";
            status.setText(msg, juce::dontSendNotification);
        });
    job->launchThread();
}

void MainComponent::play()
{
    const auto& r = lastRender();
    if (r.interleaved.empty() || lastWasText) return;
    transport.stop();
    transport.setSource(nullptr);
    const int frames = (int) (r.interleaved.size() / 2);
    playBuffer.setSize(2, frames);
    for (int i = 0; i < frames; ++i)
        for (int c = 0; c < 2; ++c) {
            const size_t k = (size_t) i * 2 + (size_t) c;
            playBuffer.setSample(c, i, r.mixed.empty() ? r.interleaved[k] / 32768.0f : juce::jlimit(-1.0f, 1.0f, r.mixed[k]));
        }
    memorySource = std::make_unique<juce::MemoryAudioSource>(playBuffer, false);
    transport.setSource(memorySource.get(), 0, nullptr, r.sampleRate);
    transport.setPosition(0.0);
    transport.start();
}

void MainComponent::exportResult()
{
    if (lastRender().grains.empty()) return;
    const auto patterns = lastWasText ? juce::String("*.txt") : juce::String("*.aiff;*.aif;*.wav");
    chooser = std::make_unique<juce::FileChooser>(lastWasText ? "Save TEXT file as:" : "Save sound file as:",
                                                  juce::File(), patterns);
    chooser->launchAsync(juce::FileBrowserComponent::saveMode | juce::FileBrowserComponent::warnAboutOverwriting,
        [this](const juce::FileChooser& fc) {
            auto file = fc.getResult();
            if (file == juce::File()) return;
            const auto& r = lastRender();
            if (lastWasText) {
                file.replaceWithText(cloudgen::grainsAsText(r.grains));
                status.setText("Saved " + file.getFileName(), juce::dontSendNotification);
                return;
            }
            const bool wav = file.hasFileExtension("wav");
            std::unique_ptr<juce::AudioFormat> format;
            if (wav) format = std::make_unique<juce::WavAudioFormat>();
            else format = std::make_unique<juce::AiffAudioFormat>();

            // Higher bit depths come from the floating-point mix (Model 2).
            int bits = 16;
            juce::String note;
            if (!r.mixed.empty() && bitDepthBox.getSelectedId() == 2) bits = 24;
            if (!r.mixed.empty() && bitDepthBox.getSelectedId() == 3) {
                bits = wav ? 32 : 24; // JUCE writes 32-bit float WAV; AIFF float is not supported
                if (!wav) note = " (24-bit: float needs WAV)";
            }

            file.deleteFile();
            std::unique_ptr<juce::OutputStream> stream(file.createOutputStream().release());
            if (stream == nullptr) return;
            std::unique_ptr<juce::AudioFormatWriter> writer(format->createWriterFor(stream.get(), r.sampleRate, 2, bits, {}, 0));
            if (writer == nullptr) return;
            stream.release(); // writer owns it now
            const int frames = (int) (r.interleaved.size() / 2);
            if (bits == 16) {
                // Write the 16-bit samples exactly (JUCE expects left-aligned 32-bit ints).
                std::vector<int> left((size_t) frames), right((size_t) frames);
                for (int i = 0; i < frames; ++i) {
                    left[(size_t) i] = (int) r.interleaved[(size_t) i * 2] * 65536;
                    right[(size_t) i] = (int) r.interleaved[(size_t) i * 2 + 1] * 65536;
                }
                const int* chans[] = { left.data(), right.data(), nullptr };
                writer->write(chans, frames);
            } else {
                juce::AudioBuffer<float> buffer(2, frames);
                for (int i = 0; i < frames; ++i)
                    for (int c = 0; c < 2; ++c)
                        buffer.setSample(c, i, r.mixed[(size_t) i * 2 + (size_t) c]);
                writer->writeFromAudioSampleBuffer(buffer, 0, frames);
            }
            status.setText("Saved " + file.getFileName() + note, juce::dontSendNotification);
        });
}

// ---------------------------------------------------------------------------
void MainComponent::paint(juce::Graphics& g)
{
    // A dithered desktop with each panel as a Classic Mac window.
    classic::fillDesktop(g, getLocalBounds().withTrimmedTop(menuBar.getHeight()));
    const juce::String model = sameFixes(fixes, cloudgen::Fixes::model1()) ? "Model 1" : "Model 2";
    for (const auto& ps : panels)
        classic::drawWindow(g, ps.area, ps.title.isEmpty() ? "Cloud Generator - " + model + " - Granular Synthesis" : ps.title);
}

void MainComponent::resized()
{
    panels.clear();
    auto area = getLocalBounds();
    menuBar.setBounds(area.removeFromTop(22));
    area.reduce(12, 12);
    auto content = [](juce::Rectangle<int> r) { return r.withTrimmedTop(classic::kTitleHeight + 3).reduced(8, 2); };

    auto headerWindow = area.removeFromTop(classic::kTitleHeight + 38);
    area.removeFromTop(10);
    panels.push_back({ {}, headerWindow }); // titled with the model in paint()
    auto header = content(headerWindow);
    compareToggle.setBounds(header.removeFromRight(150));
    fixesButton.setBounds(header.removeFromRight(100).reduced(4, 0));
    modelSelector.setBounds(header.removeFromRight(220));

    auto bottomWindow = area.removeFromBottom(classic::kTitleHeight + 42);
    area.removeFromBottom(10);
    panels.push_back({ "Generate", bottomWindow });
    auto bottom = content(bottomWindow);
    auto controls = area.removeFromLeft(600);
    area.removeFromLeft(12);
    panels.push_back({ "Grains", area });
    plot.setBounds(area.reduced(1).withTrimmedTop(classic::kTitleHeight));

    const int rowH = 26;
    auto layoutPair = [&](juce::Rectangle<int> r, Field* a, Field* b) {
        for (Field* f : { a, b }) {
            if (f == nullptr) continue;
            auto row = r.removeFromTop(rowH);
            f->label.setBounds(row.removeFromLeft(80));
            f->units.setBounds(row.removeFromRight(40));
            f->editor.setBounds(row.reduced(4, 2));
        }
        return r;
    };
    auto layoutPot = [&](juce::Rectangle<int> row, Pot& pot) {
        pot.label.setBounds(row.removeFromLeft(60));
        pot.random.setBounds(row.removeFromRight(84));
        pot.slider.setBounds(row);
    };
    auto panel = [&](juce::Rectangle<int>& col, const char* title, int h) {
        auto r = col.removeFromTop(h);
        col.removeFromTop(8);
        panels.push_back({ title, r });
        return content(r);
    };

    auto left = controls.removeFromLeft(280);
    controls.removeFromLeft(12);
    auto right = controls;

    layoutPair(panel(left, "Time", 80), startTime, duration);
    layoutPair(panel(left, "Density", 80), densityInitial, densityFinal);
    layoutPair(panel(left, "Amplitude", 80), ampInitial, ampFinal);
    layoutPair(panel(left, "High Bandlimit", 80), highInitial, highFinal);
    layoutPair(panel(left, "Low Bandlimit", 80), lowInitial, lowFinal);
    auto output = panel(left, "Output", 88);
    outputKind.setBounds(output.removeFromTop(rowH));
    auto formatRow = output.removeFromTop(rowH + 4).withTrimmedTop(4);
    sampleRateBox.setBounds(formatRow.removeFromLeft(formatRow.getWidth() / 2).withTrimmedRight(3));
    bitDepthBox.setBounds(formatRow.withTrimmedLeft(3));

    cloudType.setBounds(panel(right, "Type", 56).removeFromTop(rowH));

    auto stereo = panel(right, "Stereo Location", 84);
    layoutPot(stereo.removeFromTop(rowH + 2), panInitial);
    layoutPot(stereo.removeFromTop(rowH + 2), panFinal);

    auto grain = panel(right, "Grain Duration", 106);
    grainRandom.setBounds(grain.removeFromBottom(rowH).removeFromLeft(100));
    layoutPair(grain, grainInitial, grainFinal);

    selectionOrder.setBounds(panel(right, "Selection Order", 56).removeFromTop(rowH));

    auto wave = panel(right, "Waveform", 88);
    auto waveRow = wave.removeFromTop(rowH);
    selectButton.setBounds(waveRow.removeFromRight(100).reduced(2, 0));
    sourceKind.setBounds(waveRow.reduced(0, 0).withTrimmedRight(6));
    auto second = wave.removeFromTop(rowH + 4).withTrimmedTop(4);
    waveform.setBounds(second);
    sourceName.setBounds(second);

    generateButton.setBounds(bottom.removeFromRight(160).reduced(2));
    exportButton.setBounds(bottom.removeFromRight(120).reduced(2));
    playButton.setBounds(bottom.removeFromRight(100).reduced(2));
    showB.setBounds(bottom.removeFromRight(110).reduced(2));
    showA.setBounds(bottom.removeFromRight(110).reduced(2));
    status.setBounds(bottom);
}
