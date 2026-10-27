#include "MainComponent.h"
#include "Waveforms.h"

#include <cmath>

namespace {

namespace Colours {
const juce::Colour panel   { 0xff2b2e2c };  // instrument chassis
const juce::Colour bezel   { 0xff3a3e3b };
const juce::Colour legend  { 0xffd8d2c0 };  // silkscreen lettering
const juce::Colour accent  { 0xffe0a040 };  // amber indicator
const juce::Colour screen  { 0xff0c140f };
const juce::Colour trace   { 0xff7cf0a0 };  // P31 phosphor
const juce::Colour grid    { 0xff1f3326 };
}

// Renders a cloud on a background thread behind a progress window with Stop.
class RenderJob : public juce::ThreadWithProgressWindow
{
public:
    RenderJob(cloudgen::CloudParams p, cloudgen::Waveform w, const cloudgen::SourceSound* s, bool audio,
              std::function<void(cloudgen::RenderResult)> done)
        : ThreadWithProgressWindow("Generating cloud...", true, true),
          params(p), wave(w), source(s), renderAudio(audio), onDone(std::move(done))
    {
        setStatusMessage("Processing...");
    }

    void run() override
    {
        result = cloudgen::render(params, wave, source, renderAudio, [this](double fraction, const cloudgen::GrainEvent&) {
            setProgress(fraction);
            return !threadShouldExit();
        });
    }

    void threadComplete(bool) override
    {
        onDone(std::move(result));
        delete this;
    }

private:
    cloudgen::CloudParams params;
    cloudgen::Waveform wave;
    const cloudgen::SourceSound* source;
    bool renderAudio;
    std::function<void(cloudgen::RenderResult)> onDone;
    cloudgen::RenderResult result;
};

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
    auto r = getLocalBounds().toFloat();
    g.setColour(Colours::screen);
    g.fillRoundedRectangle(r, 6.0f);

    auto plotArea = r.reduced(28.0f, 22.0f);
    g.setColour(Colours::grid);
    for (int i = 0; i <= 10; ++i) {
        const float x = plotArea.getX() + plotArea.getWidth() * (float) i / 10.0f;
        g.drawVerticalLine((int) x, plotArea.getY(), plotArea.getBottom());
    }
    for (int i = 0; i <= 8; ++i) {
        const float y = plotArea.getY() + plotArea.getHeight() * (float) i / 8.0f;
        g.drawHorizontalLine((int) y, plotArea.getX(), plotArea.getRight());
    }

    g.setColour(Colours::trace.withAlpha(0.6f));
    g.setFont(juce::FontOptions(juce::Font::getDefaultMonospacedFontName(), 11.0f, juce::Font::plain));
    g.drawText(juce::String(maxY, 0) + " Hz", r.withHeight(20.0f).reduced(8.0f, 0.0f), juce::Justification::centredLeft);
    g.drawText(juce::String(minY, 0) + " Hz", r.withTrimmedTop(r.getHeight() - 20.0f).reduced(8.0f, 0.0f), juce::Justification::centredLeft);
    g.drawText(juce::String(end, 2) + " s", r.withTrimmedTop(r.getHeight() - 20.0f).reduced(8.0f, 0.0f), juce::Justification::centredRight);

    for (const auto& gr : grains) {
        const float x = plotArea.getX() + plotArea.getWidth() * (float) ((gr.onset - start) / (end - start));
        // Granulated grains carry no frequency; plot them along the centre line.
        const float yn = gr.frequency > 0.0f ? (float) ((gr.frequency - minY) / (maxY - minY)) : 0.5f;
        const float y = plotArea.getBottom() - plotArea.getHeight() * juce::jlimit(0.0f, 1.0f, yn);
        g.setColour(Colours::trace.withAlpha(0.25f));
        g.fillEllipse(x - 3.0f, y - 3.0f, 6.0f, 6.0f);
        g.setColour(Colours::trace);
        g.fillEllipse(x - 1.2f, y - 1.2f, 2.4f, 2.4f);
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

MainComponent::MainComponent()
{
    setColour(juce::Label::textColourId, Colours::legend);
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
    sourceName.setColour(juce::Label::textColourId, Colours::accent);
    // Shares the waveform menu's slot; it must never swallow the menu's clicks.
    sourceName.setInterceptsMouseClicks(false, false);

    for (juce::Component* c : std::initializer_list<juce::Component*>{ &cloudType, &sourceKind, &waveform, &selectionOrder,
             &outputKind, &grainRandom, &selectButton, &sourceName, &generateButton, &playButton, &exportButton, &plot, &status })
        addAndMakeVisible(c);

    // The original relabels the grain duration fields Maximum (first) and
    // Minimum (second) when Random is checked.
    grainRandom.onClick = [this] {
        const bool r = grainRandom.getToggleState();
        grainInitial->label.setText(r ? "Maximum" : "Initial", juce::dontSendNotification);
        grainFinal->label.setText(r ? "Minimum" : "Final", juce::dontSendNotification);
    };

    selectButton.onClick = [this] { chooseSource(); };
    generateButton.setColour(juce::TextButton::buttonColourId, Colours::accent.darker(0.3f));
    generateButton.onClick = [this] { generate(); };
    playButton.onClick = [this] { play(); };
    exportButton.onClick = [this] { exportResult(); };
    status.setText("Ready.", juce::dontSendNotification);
    updateSourceControls();

    deviceManager.initialiseWithDefaultDevices(0, 2);
    sourcePlayer.setSource(&transport);
    deviceManager.addAudioCallback(&sourcePlayer);

    setSize(1180, 760);
}

MainComponent::~MainComponent()
{
    transport.setSource(nullptr);
    sourcePlayer.setSource(nullptr);
    deviceManager.removeAudioCallback(&sourcePlayer);
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
    const auto wave = waveform.getSelectedId() == 2 ? cloudgen::makeSquare()
                    : waveform.getSelectedId() == 3 ? cloudgen::makeSawtooth()
                    : waveform.getSelectedId() == 4 && haveImportedWave ? importedWave
                    : cloudgen::makeSine();
    const bool text = outputKind.getSelectedId() == 2;

    transport.stop();
    generateButton.setEnabled(false);
    auto* job = new RenderJob(p, wave, granulated ? &granulationSource : nullptr, !text,
        [this, p, text](cloudgen::RenderResult r) {
            lastRender = std::move(r);
            lastWasText = text;
            const float bands[] = { p.highInitial, p.highFinal, p.lowInitial, p.lowFinal };
            plot.setGrains(lastRender.grains, p.startTime, (double) p.startTime + p.duration,
                           *std::min_element(std::begin(bands), std::end(bands)),
                           *std::max_element(std::begin(bands), std::end(bands)));
            status.setText((lastRender.stopped ? "Stopped. " : "Done. ")
                               + juce::String((int) lastRender.grains.size()) + " grains.",
                           juce::dontSendNotification);
            generateButton.setEnabled(true);
        });
    job->launchThread();
}

void MainComponent::play()
{
    if (lastRender.interleaved.empty() || lastWasText) return;
    transport.stop();
    transport.setSource(nullptr);
    const int frames = (int) (lastRender.interleaved.size() / 2);
    playBuffer.setSize(2, frames);
    for (int i = 0; i < frames; ++i)
        for (int c = 0; c < 2; ++c)
            playBuffer.setSample(c, i, lastRender.interleaved[(size_t) i * 2 + (size_t) c] / 32768.0f);
    memorySource = std::make_unique<juce::MemoryAudioSource>(playBuffer, false);
    transport.setSource(memorySource.get(), 0, nullptr, cloudgen::kSampleRate);
    transport.setPosition(0.0);
    transport.start();
}

void MainComponent::exportResult()
{
    if (lastRender.grains.empty()) return;
    const auto patterns = lastWasText ? juce::String("*.txt") : juce::String("*.aiff;*.aif;*.wav");
    chooser = std::make_unique<juce::FileChooser>(lastWasText ? "Save TEXT file as:" : "Save sound file as:",
                                                  juce::File(), patterns);
    chooser->launchAsync(juce::FileBrowserComponent::saveMode | juce::FileBrowserComponent::warnAboutOverwriting,
        [this](const juce::FileChooser& fc) {
            auto file = fc.getResult();
            if (file == juce::File()) return;
            if (lastWasText) {
                file.replaceWithText(cloudgen::grainsAsText(lastRender.grains));
                status.setText("Saved " + file.getFileName(), juce::dontSendNotification);
                return;
            }
            std::unique_ptr<juce::AudioFormat> format;
            if (file.hasFileExtension("wav")) format = std::make_unique<juce::WavAudioFormat>();
            else format = std::make_unique<juce::AiffAudioFormat>();
            file.deleteFile();
            std::unique_ptr<juce::OutputStream> stream(file.createOutputStream().release());
            if (stream == nullptr) return;
            std::unique_ptr<juce::AudioFormatWriter> writer(
                format->createWriterFor(stream.get(), cloudgen::kSampleRate, 2, 16, {}, 0));
            if (writer == nullptr) return;
            stream.release(); // writer owns it now
            // Write the 16-bit samples exactly (JUCE expects left-aligned 32-bit ints).
            const int frames = (int) (lastRender.interleaved.size() / 2);
            std::vector<int> left((size_t) frames), right((size_t) frames);
            for (int i = 0; i < frames; ++i) {
                left[(size_t) i] = (int) lastRender.interleaved[(size_t) i * 2] * 65536;
                right[(size_t) i] = (int) lastRender.interleaved[(size_t) i * 2 + 1] * 65536;
            }
            const int* chans[] = { left.data(), right.data(), nullptr };
            writer->write(chans, frames);
            status.setText("Saved " + file.getFileName(), juce::dontSendNotification);
        });
}

// ---------------------------------------------------------------------------
void MainComponent::paint(juce::Graphics& g)
{
    g.fillAll(Colours::panel);

    g.setColour(Colours::legend);
    g.setFont(juce::FontOptions(20.0f, juce::Font::bold));
    g.drawText("CLOUD GENERATOR", 20, 8, 400, 28, juce::Justification::centredLeft);
    g.setColour(Colours::accent);
    g.setFont(juce::FontOptions(12.0f));
    g.drawText("MODEL 1  /  GRANULAR SYNTHESIS", 240, 8, 400, 28, juce::Justification::centredLeft);

    for (const auto& ps : panels) {
        auto r = ps.area.toFloat();
        g.setColour(Colours::bezel);
        g.fillRoundedRectangle(r, 5.0f);
        g.setColour(Colours::legend.withAlpha(0.35f));
        g.drawRoundedRectangle(r, 5.0f, 1.0f);
        g.setColour(Colours::legend);
        g.setFont(juce::FontOptions(11.0f, juce::Font::bold));
        g.drawText(ps.title.toUpperCase(), ps.area.withHeight(20).reduced(10, 0), juce::Justification::centredLeft);
    }
}

void MainComponent::resized()
{
    panels.clear();
    auto area = getLocalBounds().reduced(16);
    area.removeFromTop(32);

    auto bottom = area.removeFromBottom(44);
    auto controls = area.removeFromLeft(600);
    area.removeFromLeft(12);
    plot.setBounds(area);

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
        return r.withTrimmedTop(22).reduced(8, 2);
    };

    auto left = controls.removeFromLeft(280);
    controls.removeFromLeft(12);
    auto right = controls;

    layoutPair(panel(left, "Time", 80), startTime, duration);
    layoutPair(panel(left, "Density", 80), densityInitial, densityFinal);
    layoutPair(panel(left, "Amplitude", 80), ampInitial, ampFinal);
    layoutPair(panel(left, "High Bandlimit", 80), highInitial, highFinal);
    layoutPair(panel(left, "Low Bandlimit", 80), lowInitial, lowFinal);
    outputKind.setBounds(panel(left, "Output", 56).removeFromTop(rowH));

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
    status.setBounds(bottom);
}
