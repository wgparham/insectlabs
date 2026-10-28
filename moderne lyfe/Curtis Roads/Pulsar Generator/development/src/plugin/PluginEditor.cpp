#include "PluginEditor.h"
#include "Look.h"

namespace {

// Table grid order of the original window layout (PG.initGUISpecs).
const int kGridOrder[] = {
    pulsar::FundFreq, pulsar::FormFreq1, pulsar::FormFreq2, pulsar::FormFreq3,
    pulsar::PulseMask, pulsar::Pan1, pulsar::Pan2, pulsar::Pan3,
    pulsar::Pulsaret, pulsar::Amp1, pulsar::Amp2, pulsar::Amp3,
    pulsar::PulseEnv
};

} // namespace

// ---------------------------------------------------------------------------
void ScopeView::pull()
{
    int s1, n1, s2, n2;
    const int ready = proc.scopeFifo.getNumReady();
    proc.scopeFifo.prepareToRead(ready, s1, n1, s2, n2);
    auto take = [&](int start, int count) {
        for (int i = 0; i < count; i += 2) { // every other sample is plenty for the display
            l[(size_t) write] = proc.scopeL[(size_t) (start + i)];
            r[(size_t) write] = proc.scopeR[(size_t) (start + i)];
            write = (write + 1) % (int) l.size();
        }
    };
    take(s1, n1);
    take(s2, n2);
    proc.scopeFifo.finishedRead(n1 + n2);
}

void ScopeView::paint(juce::Graphics& g)
{
    auto r0 = getLocalBounds().toFloat();
    g.setColour(look::screen);
    g.fillRoundedRectangle(r0, 4.0f);
    g.setColour(look::legend.withAlpha(0.25f));
    g.drawRoundedRectangle(r0.reduced(0.5f), 4.0f, 1.0f);
    g.setColour(look::legend);
    g.setFont(juce::FontOptions(11.0f, juce::Font::bold));
    g.drawText("SCOPE", r0.reduced(6.0f, 2.0f).withHeight(14.0f), juce::Justification::centredLeft);

    const auto plot = r0.reduced(6.0f).withTrimmedTop(14.0f);
    g.setColour(look::grid);
    for (int i = 1; i < 10; ++i) g.drawVerticalLine((int) (plot.getX() + plot.getWidth() * i / 10.0f), plot.getY(), plot.getBottom());
    for (int i = 1; i < 4; ++i) g.drawHorizontalLine((int) (plot.getY() + plot.getHeight() * i / 4.0f), plot.getX(), plot.getRight());

    auto trace = [&](const std::array<float, 1024>& d, float centre, juce::Colour c) {
        juce::Path p;
        const int n = (int) d.size();
        for (int i = 0; i < n; ++i) {
            const float v = juce::jlimit(-1.0f, 1.0f, d[(size_t) ((write + i) % n)]);
            const float x = plot.getX() + plot.getWidth() * (float) i / (float) (n - 1);
            const float y = centre - v * plot.getHeight() * 0.24f;
            if (i == 0) p.startNewSubPath(x, y); else p.lineTo(x, y);
        }
        g.setColour(c.withAlpha(0.25f));
        g.strokePath(p, juce::PathStrokeType(2.5f));
        g.setColour(c);
        g.strokePath(p, juce::PathStrokeType(1.0f));
    };
    trace(l, plot.getY() + plot.getHeight() * 0.25f, look::trace);
    trace(r, plot.getY() + plot.getHeight() * 0.75f, look::trace);
}

// ---------------------------------------------------------------------------
FixesPanel::FixesPanel(PulsarProcessor& proc)
{
    for (int i = 0; i < pulsar::Fixes::kCount; ++i) {
        Row r;
        r.toggle = std::make_unique<juce::ToggleButton>(pulsar::Fixes::name(i));
        r.toggle->setColour(juce::ToggleButton::textColourId, look::legend);
        r.toggle->setColour(juce::ToggleButton::tickColourId, look::accent);
        r.text = std::make_unique<juce::Label>(juce::String(), pulsar::Fixes::description(i));
        r.text->setFont(juce::FontOptions(12.0f));
        r.text->setColour(juce::Label::textColourId, look::legend.withAlpha(0.7f));
        r.text->setInterceptsMouseClicks(false, false);
        addAndMakeVisible(*r.toggle);
        addAndMakeVisible(*r.text);
        attachments.push_back(std::make_unique<juce::AudioProcessorValueTreeState::ButtonAttachment>(
            proc.params, juce::String("fix_") + pulsar::Fixes::id(i), *r.toggle));
        rows.push_back(std::move(r));
    }
    setSize(600, 3 * 26 + pulsar::Fixes::kCount * 52 + 12);
}

void FixesPanel::paint(juce::Graphics& g)
{
    g.fillAll(look::panel);
    g.setFont(juce::FontOptions(11.0f, juce::Font::bold));
    for (const auto& [title, y] : headings) {
        g.setColour(look::accent);
        g.drawText(title, 12, y, getWidth() - 24, 22, juce::Justification::bottomLeft);
        g.setColour(look::legend.withAlpha(0.25f));
        g.drawHorizontalLine(y + 23, 12.0f, (float) getWidth() - 12.0f);
    }
}

void FixesPanel::resized()
{
    headings.clear();
    int y = 4;
    for (int i = 0; i < (int) rows.size(); ++i) {
        if (i == 0 || i == 4 || i == 8) {
            headings.push_back({ i == 0 ? "BUGS  (MODEL 2 PRESET)" : i == 4 ? "SIGNAL QUALITY  (MODEL 2 PRESET)"
                                                                        : "SOUND-DEFINING  (OPTIONAL)", y });
            y += 26;
        }
        rows[(size_t) i].toggle->setBounds(12, y, getWidth() - 24, 22);
        rows[(size_t) i].text->setBounds(32, y + 20, getWidth() - 44, 30);
        y += 52;
    }
}

// ---------------------------------------------------------------------------
void PulsarEditor::setupSlider(juce::Slider& s, juce::Label& l, const juce::String& text)
{
    s.setSliderStyle(juce::Slider::LinearHorizontal);
    s.setTextBoxStyle(juce::Slider::TextBoxRight, false, 52, 20);
    l.setText(text, juce::dontSendNotification);
    l.setJustificationType(juce::Justification::centredLeft);
    addAndMakeVisible(s);
    addAndMakeVisible(l);
}

void PulsarEditor::setupToggle(juce::TextButton& b, const juce::String& text)
{
    b.setButtonText(text);
    b.setClickingTogglesState(true);
    b.setColour(juce::TextButton::buttonOnColourId, look::accent.darker(0.2f));
    addAndMakeVisible(b);
}

PulsarEditor::PulsarEditor(PulsarProcessor& p) : AudioProcessorEditor(p), proc(p), scope(p)
{
    setColour(juce::ResizableWindow::backgroundColourId, look::panel);
    getLookAndFeel().setColour(juce::Label::textColourId, look::legend);
    auto& params = proc.params;
    auto attachS = [&](juce::Slider& s, const char* id) { sliderAttachments.add(new SliderAttachment(params, id, s)); };
    auto attachB = [&](juce::Button& b, const char* id) { buttonAttachments.add(new ButtonAttachment(params, id, b)); };

    for (int i = 0; i < 3; ++i) {
        setupToggle(trainOn[(size_t) i], "ON");
        setupSlider(trainLevel[(size_t) i], trainLabel[(size_t) i], "Volume " + juce::String(i + 1));
        attachB(trainOn[(size_t) i], ("train" + std::to_string(i + 1) + "On").c_str());
        attachS(trainLevel[(size_t) i], ("train" + std::to_string(i + 1) + "Level").c_str());
    }
    setupToggle(runButton, "S T A R T");
    runButton.setColour(juce::TextButton::buttonColourId, juce::Colour(0xff2b582b)); // PG.darkGreen
    runButton.setColour(juce::TextButton::buttonOnColourId, look::record);
    runButton.onStateChange = [this] { runButton.setButtonText(runButton.getToggleState() ? "S T O P" : "S T A R T"); };
    attachB(runButton, "run");
    setupToggle(loopButton, "LOOP");       attachB(loopButton, "loop");
    setupToggle(muteButton, "M U T E");    attachB(muteButton, "mute");
    muteButton.setColour(juce::TextButton::buttonOnColourId, look::record);
    setupToggle(freezeButton, "[F]");      attachB(freezeButton, "freeze");
    setupToggle(backButton, "<-");         attachB(backButton, "backwards");
    setupToggle(burstOn, "BURST RATIO");   attachB(burstOn, "burstOn");
    setupSlider(burstOns, burstOnsLabel, "on");     attachS(burstOns, "burstOns");
    setupSlider(burstOffs, burstOffsLabel, "off");  attachS(burstOffs, "burstOffs");
    setupSlider(trainDur, trainDurLabel, "Train Duration"); attachS(trainDur, "trainDur");
    setupSlider(edge, edgeLabel, "Edge");           attachS(edge, "edge");
    setupSlider(master, masterLabel, "Master");     attachS(master, "master");
    setupSlider(fadeVolTime, fadeVolLabel, "Fade time");
    fadeVolTime.setRange(0.0, 100.0, 0.1);
    fadeVolTime.setValue(5.0);
    fadeButton.setButtonText("FADE");
    addAndMakeVisible(fadeButton);
    fadeButton.onClick = [this] {
        // Fade the master volume to 0, or from 0 back to 1, over the fade time.
        fadeFrom = (float) master.getValue();
        fadeTo = fadeFrom == 0.0f ? 1.0f : 0.0f;
        fadeStart = juce::Time::getMillisecondCounterHiRes() / 1000.0;
        fadeLength = fadeVolTime.getValue();
        masterFading = true;
    };
    randomButton.setButtonText("Randomize Controls");
    addAndMakeVisible(randomButton);
    randomButton.onClick = [this] { proc.randomizeControls(); };

    recordButton.setButtonText("RECORD");
    recordButton.setColour(juce::TextButton::buttonOnColourId, look::record);
    recordButton.setVisible(proc.isStandalone());
    addChildComponent(recordButton);
    recordButton.onClick = [this] {
        if (proc.isRecording()) { proc.stopRecording(); recordButton.setToggleState(false, juce::dontSendNotification); return; }
        chooseFile("Record to", "*.aiff;*.wav", true, [this](const juce::File& f) {
            juce::String err;
            const auto file = f.hasFileExtension("wav;aif;aiff") ? f : f.withFileExtension("aiff");
            if (proc.startRecording(file, err)) { recordButton.setToggleState(true, juce::dontSendNotification); report("Recording to " + file.getFileName()); }
            else report(err);
        });
    };

    cycle.setSliderStyle(juce::Slider::LinearHorizontal);
    cycle.setTextBoxStyle(juce::Slider::NoTextBox, false, 0, 0);
    cycle.setRange(0.0, 1.0);
    cycle.onDragEnd = [this] { proc.jumpToPosition(cycle.getValue()); };
    cycle.onValueChange = [this] { if (cycle.isMouseButtonDown()) proc.jumpToPosition(cycle.getValue()); };
    addAndMakeVisible(cycle);
    addAndMakeVisible(pulsarCount);

    // Tables bank
    auto bankButton = [&](juce::TextButton& b, const juce::String& t) { b.setButtonText(t); addAndMakeVisible(b); };
    bankButton(loadBank, "Load Bank...");
    bankButton(saveBank, "Save Bank...");
    bankButton(loadSet, "Load Tables...");
    bankButton(saveSet, "Save Tables...");
    bankButton(addCurrent, "Current To Panel");
    bankButton(defaultTables, "Default Tables");
    setupToggle(linkSettings, "Link Play Controls");
    attachB(linkSettings, "linkSettings");
    setupSlider(fadeTime, fadeTimeLabel, "Fade Time");
    attachS(fadeTime, "fadeTime");
    bankLabel.setJustificationType(juce::Justification::centredLeft);
    bankLabel.setColour(juce::Label::textColourId, look::accent);
    addAndMakeVisible(bankLabel);
    bankViewport.setViewedComponent(&bankList, false);
    bankViewport.setScrollBarsShown(true, false);
    addAndMakeVisible(bankViewport);

    loadBank.onClick = [this] {
        chooseFile("Load bank", "*.bank", false, [this](const juce::File& f) {
            juce::String err;
            if (proc.loadBank(f, err)) { rebuildBankButtons(); report("Loaded " + f.getFileName()); } else report(err);
        });
    };
    saveBank.onClick = [this] {
        chooseFile("Save bank (sets are saved next to it)", "*.bank", true, [this](const juce::File& f) {
            juce::String err;
            if (proc.saveBank(f.withFileExtension("bank"), err)) report("Saved bank " + f.getFileName()); else report(err);
        });
    };
    loadSet.onClick = [this] {
        chooseFile("Load tables", "*.set", false, [this](const juce::File& f) {
            juce::String err;
            if (proc.loadSetFile(f, err)) { rebuildBankButtons(); report("Loaded " + f.getFileName()); } else report(err);
        });
    };
    saveSet.onClick = [this] {
        chooseFile("Save tables", "*.set", true, [this](const juce::File& f) {
            juce::String err;
            if (proc.saveCurrentSet(f.withFileExtension("set"), err)) report("Saved " + f.getFileName()); else report(err);
        });
    };
    addCurrent.onClick = [this] { proc.addCurrentToBank(); rebuildBankButtons(); };
    defaultTables.onClick = [this] { proc.restoreDefaultTables(); };

    // Ring modulator
    ringLoad.setButtonText("Pick Soundfile To Loop...");
    addAndMakeVisible(ringLoad);
    setupSlider(ringMix, ringLabel, "Dry/Wet");
    attachS(ringMix, "ringMix");
    ringLoad.onClick = [this] {
        chooseFile("Sound for the ring modulator", "*.wav;*.aif;*.aiff;*.flac;*.ogg;*.mp3", false, [this](const juce::File& f) {
            juce::String err;
            if (proc.loadRingModSound(f, err)) report("Ring modulator: " + f.getFileName()); else report(err);
        });
    };

    for (int id : kGridOrder) {
        auto* v = tableViews.add(new TableView(id));
        v->table = [this, id] { return &proc.editableTables().tables[(size_t) id]; };
        v->onEdited = [this](bool) { proc.tablesEdited(); };
        v->onSelected = [this](TableView& sel) { for (auto* t : tableViews) t->setSelected(t == &sel); };
        v->position = [this] { return proc.isRunning() ? proc.position() : -1.0; };
        addAndMakeVisible(v);
    }
    addAndMakeVisible(scope);
    status.setColour(juce::Label::textColourId, look::accent);
    addAndMakeVisible(status);

    // Model 1 / Model 2
    modelSelector.addItem("Model 1 (original)", 1);
    modelSelector.addItem("Model 2 (corrected)", 2);
    modelSelector.onChange = [this] {
        const int id = modelSelector.getSelectedId();
        if (id == 1) proc.setFixes(pulsar::Fixes::model1());
        else if (id == 2) proc.setFixes(pulsar::Fixes::model2());
    };
    addAndMakeVisible(modelSelector);
    fixesButton.onClick = [this] {
        juce::CallOutBox::launchAsynchronously(std::make_unique<FixesPanel>(proc), fixesButton.getScreenBounds(), nullptr);
    };
    addAndMakeVisible(fixesButton);
    for (auto* b : { &showA, &showB }) {
        b->setClickingTogglesState(true);
        b->setRadioGroupId(1001);
        b->setColour(juce::TextButton::buttonOnColourId, look::accent.darker(0.2f));
        addAndMakeVisible(*b);
    }
    showA.setTooltip("Hear Model 1 (the original) with the same tables and controls");
    showB.setTooltip("Hear the current model and fixes");
    showA.onClick = [this] { if (auto* a = proc.params.getParameter("abModel1")) a->setValueNotifyingHost(1.0f); };
    showB.onClick = [this] { if (auto* a = proc.params.getParameter("abModel1")) a->setValueNotifyingHost(0.0f); };
    updateModelControls();

    proc.onTablesChanged = [this] { refreshTables(); rebuildBankButtons(); };
    rebuildBankButtons();
    setWantsKeyboardFocus(true);
    setResizable(true, true);
    setResizeLimits(1100, 720, 2400, 1600);
    setSize(1240, 820);
    startTimerHz(30);
}

PulsarEditor::~PulsarEditor()
{
    proc.onTablesChanged = nullptr;
}

void PulsarEditor::refreshTables()
{
    for (auto* v : tableViews) v->repaint();
}

void PulsarEditor::rebuildBankButtons()
{
    bankButtons.clear();
    bankLabel.setText(proc.bankName.isEmpty() ? "Bank" : "Bank: " + proc.bankName, juce::dontSendNotification);
    for (int i = 0; i < (int) proc.bank.size(); ++i) {
        auto* b = bankButtons.add(new juce::TextButton(juce::String(proc.bank[(size_t) i].name)));
        b->setColour(juce::TextButton::buttonColourId, i == proc.selectedBankSet() ? look::accent.darker(0.4f) : look::bezel);
        b->onClick = [this, i] { proc.selectBankSet(i); };
        bankList.addAndMakeVisible(b);
    }
    const int w = bankViewport.getMaximumVisibleWidth() > 0 ? bankViewport.getMaximumVisibleWidth() : 200;
    for (int i = 0; i < bankButtons.size(); ++i) bankButtons[i]->setBounds(0, i * 24, w, 22);
    bankList.setSize(w, juce::jmax(1, bankButtons.size() * 24));
}

void PulsarEditor::chooseFile(const juce::String& title, const juce::String& patterns, bool save,
                              std::function<void(const juce::File&)> action)
{
    chooser = std::make_unique<juce::FileChooser>(title, juce::File(), patterns);
    const int mode = save ? juce::FileBrowserComponent::saveMode | juce::FileBrowserComponent::warnAboutOverwriting
                          : juce::FileBrowserComponent::openMode | juce::FileBrowserComponent::canSelectFiles;
    chooser->launchAsync(mode, [action](const juce::FileChooser& fc) {
        if (fc.getResult() != juce::File()) action(fc.getResult());
    });
}

void PulsarEditor::updateModelControls()
{
    const auto f = proc.fixes();
    // Only follow changes to the fixes: the ComboBox reports a choice
    // asynchronously, so re-syncing on every tick could undo it first.
    if (!shownFixes || *shownFixes != f) {
        shownFixes = f;
        const int id = f == pulsar::Fixes::model1() ? 1 : f == pulsar::Fixes::model2() ? 2 : 0;
        if (id == 0) modelSelector.setText(proc.modelName(), juce::dontSendNotification);
        else modelSelector.setSelectedId(id, juce::dontSendNotification);
    }
    const bool hearingA = proc.params.getRawParameterValue("abModel1")->load() > 0.5f;
    showA.setToggleState(hearingA, juce::dontSendNotification);
    showB.setToggleState(!hearingA, juce::dontSendNotification);
    // A/B only means something once a fix is on.
    const bool any = f.any();
    showA.setEnabled(any || hearingA);
    showB.setEnabled(any || hearingA);
}

void PulsarEditor::report(const juce::String& message) { status.setText(message, juce::dontSendNotification); }

bool PulsarEditor::keyPressed(const juce::KeyPress& key)
{
    if (key == juce::KeyPress::spaceKey) { proc.setRunning(!proc.isRunning()); return true; }
    if (key.getTextCharacter() == 'z') { proc.undoTableSwitch(); return true; }
    if (key.getTextCharacter() == 'f') { freezeButton.triggerClick(); return true; }
    if (key.getTextCharacter() == 'a') { (showA.getToggleState() ? showB : showA).triggerClick(); return true; } // A/B
    return false;
}

void PulsarEditor::timerCallback()
{
    if (!cycle.isMouseButtonDown()) cycle.setValue(proc.position(), juce::dontSendNotification);
    pulsarCount.setText(juce::String(proc.pulsarCount()) + " pulsars", juce::dontSendNotification);
    updateModelControls();
    if (!proc.isRecording() && recordButton.getToggleState()) recordButton.setToggleState(false, juce::dontSendNotification);

    if (masterFading) {
        const double t = juce::Time::getMillisecondCounterHiRes() / 1000.0 - fadeStart;
        const float x = fadeLength <= 0.0 ? 1.0f : (float) juce::jlimit(0.0, 1.0, t / fadeLength);
        master.setValue(fadeFrom + (fadeTo - fadeFrom) * x);
        if (x >= 1.0f) masterFading = false;
    }

    scope.pull();
    scope.repaint();
    if (proc.isRunning()) for (auto* v : tableViews) v->repaint();
}

void PulsarEditor::paint(juce::Graphics& g)
{
    g.fillAll(look::panel);
    g.setColour(look::legend);
    g.setFont(juce::FontOptions(20.0f, juce::Font::bold));
    g.drawText("PULSAR GENERATOR", 18, 8, 300, 26, juce::Justification::centredLeft);
    g.setColour(look::accent);
    g.setFont(juce::FontOptions(12.0f));
    g.drawText("2001  /  PULSAR SYNTHESIS", 236, 8, 300, 26, juce::Justification::centredLeft);

    for (const auto& p : panels) {
        const auto r = p.area.toFloat();
        g.setColour(look::bezel);
        g.fillRoundedRectangle(r, 5.0f);
        g.setColour(look::legend.withAlpha(0.3f));
        g.drawRoundedRectangle(r.reduced(0.5f), 5.0f, 1.0f);
        g.setColour(look::legend);
        g.setFont(juce::FontOptions(11.0f, juce::Font::bold));
        g.drawText(p.title.toUpperCase(), p.area.withHeight(20).reduced(10, 0), juce::Justification::centredLeft);
    }
}

void PulsarEditor::resized()
{
    panels.clear();
    auto area = getLocalBounds().reduced(14);
    auto header = area.removeFromTop(34);
    {
        auto r = header.withTrimmedLeft(440).reduced(0, 4);
        showB.setBounds(r.removeFromRight(96));
        r.removeFromRight(2);
        showA.setBounds(r.removeFromRight(96));
        r.removeFromRight(10);
        fixesButton.setBounds(r.removeFromRight(80));
        r.removeFromRight(6);
        modelSelector.setBounds(r.removeFromRight(190));
    }
    auto footer = area.removeFromBottom(26);
    status.setBounds(footer.removeFromLeft(330));
    pulsarCount.setBounds(footer.removeFromRight(140));
    cycle.setBounds(footer.withTrimmedLeft(12).withTrimmedRight(110)); // under the table grid

    const int row = 26;
    auto panel = [&](juce::Rectangle<int>& col, const juce::String& title, int h) {
        auto r = col.removeFromTop(h);
        col.removeFromTop(8);
        panels.push_back({ title, r });
        return r.withTrimmedTop(22).reduced(8, 2);
    };
    auto labelled = [&](juce::Rectangle<int> r, juce::Label& l, juce::Slider& s, int labelWidth = 92) {
        l.setBounds(r.removeFromLeft(labelWidth));
        s.setBounds(r);
    };

    // Left: play controls.
    auto left = area.removeFromLeft(330);
    area.removeFromLeft(12);
    auto trains = panel(left, "Trains", 22 + 3 * row + 8);
    for (int i = 0; i < 3; ++i) {
        auto r = trains.removeFromTop(row);
        trainOn[(size_t) i].setBounds(r.removeFromLeft(44).reduced(1, 2));
        r.removeFromLeft(6);
        labelled(r, trainLabel[(size_t) i], trainLevel[(size_t) i], 64);
    }
    auto transport = panel(left, "Synth", 22 + 3 * row + 10);
    {
        auto r = transport.removeFromTop(row + 4);
        runButton.setBounds(r.removeFromLeft(150).reduced(1, 1));
        r.removeFromLeft(6);
        loopButton.setBounds(r.removeFromLeft(70).reduced(1, 2));
        muteButton.setBounds(r.reduced(1, 2));
        r = transport.removeFromTop(row);
        recordButton.setBounds(r.removeFromLeft(90).reduced(1, 2));
        r.removeFromLeft(6);
        backButton.setBounds(r.removeFromLeft(40).reduced(1, 2));
        freezeButton.setBounds(r.removeFromLeft(40).reduced(1, 2));
        labelled(transport.removeFromTop(row), trainDurLabel, trainDur);
    }
    auto burst = panel(left, "Burst Masking", 22 + 3 * row + 8);
    burstOn.setBounds(burst.removeFromTop(row).removeFromLeft(140).reduced(1, 2));
    labelled(burst.removeFromTop(row), burstOnsLabel, burstOns, 40);
    labelled(burst.removeFromTop(row), burstOffsLabel, burstOffs, 40);
    auto pulsar = panel(left, "Pulsars", 22 + 2 * row + 8);
    labelled(pulsar.removeFromTop(row), edgeLabel, edge, 60);
    randomButton.setBounds(pulsar.removeFromTop(row).reduced(1, 2));
    auto out = panel(left, "Output", 22 + 2 * row + 8);
    labelled(out.removeFromTop(row), masterLabel, master, 60);
    {
        auto r = out.removeFromTop(row);
        fadeButton.setBounds(r.removeFromLeft(60).reduced(1, 2));
        r.removeFromLeft(6);
        labelled(r, fadeVolLabel, fadeVolTime, 64);
    }
    auto ring = panel(left, "FX Ring Mod", 22 + 2 * row + 8);
    ringLoad.setBounds(ring.removeFromTop(row).reduced(1, 2));
    labelled(ring.removeFromTop(row), ringLabel, ringMix, 60);

    // Right: tables bank.
    auto right = area.removeFromRight(238);
    area.removeFromRight(12);
    auto bank = panel(right, "Tables Bank", right.getHeight());
    for (auto* pair : { &loadBank, &loadSet, &addCurrent }) {
        auto r = bank.removeFromTop(row);
        juce::TextButton* second = pair == &loadBank ? &saveBank : pair == &loadSet ? &saveSet : &defaultTables;
        pair->setBounds(r.removeFromLeft(r.getWidth() / 2).reduced(1, 2));
        second->setBounds(r.reduced(1, 2));
    }
    labelled(bank.removeFromTop(row), fadeTimeLabel, fadeTime, 70);
    linkSettings.setBounds(bank.removeFromTop(row).reduced(1, 2));
    bankLabel.setBounds(bank.removeFromTop(row));
    bankViewport.setBounds(bank);
    rebuildBankButtons();

    // Centre: the table grid, 4 across, as in the original window layout.
    const int cols = 4, rows = 4, gap = 8;
    const int cw = (area.getWidth() - gap * (cols - 1)) / cols;
    const int ch = (area.getHeight() - gap * (rows - 1)) / rows;
    for (int i = 0; i < tableViews.size(); ++i)
        tableViews[i]->setBounds(area.getX() + (i % cols) * (cw + gap), area.getY() + (i / cols) * (ch + gap), cw, ch);
    scope.setBounds(area.getX() + (cw + gap), area.getY() + 3 * (ch + gap), 3 * cw + 2 * gap, ch);
}
