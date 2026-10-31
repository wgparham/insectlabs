#include "PluginEditor.h"

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
    const auto& th = classic::theme();
    const auto inside = classic::drawWindow(g, getLocalBounds().withTrimmedRight(1).withTrimmedBottom(1), "Scope");
    const auto plot = inside.toFloat().reduced(6.0f);
    g.setColour(th.ink);
    for (int i = 1; i < 10; ++i) {
        const int x = (int) (plot.getX() + plot.getWidth() * i / 10.0f);
        for (int y = (int) plot.getY(); y < (int) plot.getBottom(); y += 3) g.fillRect(x, y, 1, 1);
    }
    for (int i = 1; i < 4; ++i) {
        const int y = (int) (plot.getY() + plot.getHeight() * i / 4.0f);
        for (int x = (int) plot.getX(); x < (int) plot.getRight(); x += 3) g.fillRect(x, y, 1, 1);
    }

    auto trace = [&](const std::array<float, 1024>& d, float centre, juce::Colour c) {
        juce::Path p;
        const int n = (int) d.size();
        for (int i = 0; i < n; ++i) {
            const float v = juce::jlimit(-1.0f, 1.0f, d[(size_t) ((write + i) % n)]);
            const float x = plot.getX() + plot.getWidth() * (float) i / (float) (n - 1);
            const float y = centre - v * plot.getHeight() * 0.24f;
            if (i == 0) p.startNewSubPath(x, y); else p.lineTo(x, y);
        }
        g.setColour(c);
        g.strokePath(p, juce::PathStrokeType(1.0f));
    };
    trace(l, plot.getY() + plot.getHeight() * 0.25f, th.ink);
    trace(r, plot.getY() + plot.getHeight() * 0.75f, th.ink);
}

// ---------------------------------------------------------------------------
FixesPanel::FixesPanel(PulsarProcessor& proc)
{
    for (int i = 0; i < pulsar::Fixes::kCount; ++i) {
        Row r;
        r.toggle = std::make_unique<juce::ToggleButton>(pulsar::Fixes::name(i));
        r.text = std::make_unique<juce::Label>(juce::String(), pulsar::Fixes::description(i));
        r.text->setFont(classic::smallFont(11.0f));
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
    const auto& th = classic::theme();
    g.fillAll(th.paper);
    g.setColour(th.ink);
    g.setFont(classic::uiFont(12.0f));
    for (const auto& [title, y] : headings) {
        g.drawText(title, 12, y, getWidth() - 24, 22, juce::Justification::bottomLeft);
        for (int x = 12; x < getWidth() - 12; x += 2) g.fillRect(x, y + 23, 1, 1); // dotted rule
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
    b.setClickingTogglesState(true); // on shows inverted, like a selected Mac button
    addAndMakeVisible(b);
}

PulsarEditor::PulsarEditor(PulsarProcessor& p) : AudioProcessorEditor(p), proc(p), scope(p)
{
    juce::PropertiesFile::Options o;
    o.applicationName = "Pulsar Generator Look"; // apart from the standalone wrapper's own settings file
    o.folderName = "Pulsar Generator";
    o.filenameSuffix = ".settings";
    o.osxLibrarySubFolder = "Application Support";
    settings.setStorageParameters(o);
    classic::setDark(settings.getUserSettings()->getBoolValue("darkMode", false));
    lnf.refresh();
    setLookAndFeel(&lnf);
    darkMode.setToggleState(classic::theme().dark, juce::dontSendNotification);
    darkMode.onClick = [this] { setDark(darkMode.getToggleState()); };
    addAndMakeVisible(darkMode);
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
    runButton.onStateChange = [this] { runButton.setButtonText(runButton.getToggleState() ? "S T O P" : "S T A R T"); };
    attachB(runButton, "run");
    setupToggle(loopButton, "LOOP");       attachB(loopButton, "loop");
    setupToggle(muteButton, "M U T E");    attachB(muteButton, "mute");
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
    addAndMakeVisible(bankLabel);
    bankViewport.setViewedComponent(&bankList, false);
    bankViewport.setScrollBarsShown(true, false);
    addAndMakeVisible(bankViewport);

    loadBank.onClick = [this] {
        if (refuseWhileRunning("Sorry, no bank load while playing.")) return;
        chooseFile("Load bank", "*.bank", false, [this](const juce::File& f) {
            juce::String err;
            if (proc.loadBank(f, err)) { rebuildBankButtons(); report("Loaded " + f.getFileName()); } else report(err);
        });
    };
    saveBank.onClick = [this] {
        if (refuseWhileRunning("Sorry, no bank save while playing.")) return;
        chooseFile("Save bank (sets are saved next to it)", "*.bank", true, [this](const juce::File& f) {
            juce::String err;
            if (proc.saveBank(f.withFileExtension("bank"), err)) report("Saved bank " + f.getFileName()); else report(err);
        });
    };
    loadSet.onClick = [this] {
        if (refuseWhileRunning("Sorry, no loading while Synth is on.")) return;
        chooseFile("Load tables", "*.set", false, [this](const juce::File& f) {
            juce::String err;
            if (proc.loadSetFile(f, err)) { rebuildBankButtons(); report("Loaded " + f.getFileName()); } else report(err);
        });
    };
    saveSet.onClick = [this] {
        if (refuseWhileRunning("Sorry, no saving while Synth is on.")) return;
        chooseFile("Save tables", "*.set", true, [this](const juce::File& f) {
            juce::String err;
            if (proc.saveCurrentSet(f.withFileExtension("set"), err)) report("Saved " + f.getFileName()); else report(err);
        });
    };
    addCurrent.onClick = [this] {
        if (!proc.addCurrentToBank()) { report("Sorry, Bank is full!"); return; }
        rebuildBankButtons();
    };
    defaultTables.onClick = [this] { proc.restoreDefaultTables(); };

    // Ring modulator
    ringLoad.setButtonText("Pick Soundfile To Loop...");
    addAndMakeVisible(ringLoad);
    setupSlider(ringMix, ringLabel, "Dry/Wet");
    attachS(ringMix, "ringMix");
    ringLoad.onClick = [this] {
        if (refuseWhileRunning("Sorry, cannot pick soundfile while Synth is on.")) return;
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
        // Inside the editor, so the call-out uses the editor's look.
        juce::CallOutBox::launchAsynchronously(std::make_unique<FixesPanel>(proc), fixesButton.getBounds(), this);
    };
    addAndMakeVisible(fixesButton);
    for (auto* b : { &showA, &showB }) {
        b->setClickingTogglesState(true);
        b->setRadioGroupId(1001);
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
    // Slider text boxes were made before their sliders joined the editor, with
    // the default look; send ours down so they are rebuilt with it.
    sendLookAndFeelChange();
    startTimerHz(30);
}

PulsarEditor::~PulsarEditor()
{
    proc.onTablesChanged = nullptr;
    setLookAndFeel(nullptr);
}

void PulsarEditor::setDark(bool dark)
{
    classic::setDark(dark);
    settings.getUserSettings()->setValue("darkMode", dark);
    settings.saveIfNeeded();
    lnf.refresh();
    sendLookAndFeelChange();
    rebuildBankButtons();
    repaint();
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
        b->setToggleState(i == proc.selectedBankSet(), juce::dontSendNotification); // the current set shows inverted
        b->onClick = [this, i] {
            if (proc.xFadeLocked()) { report("Wait... (crossfade in progress)"); return; } // TablesPanel.xFadeRefused
            proc.selectBankSet(i);
        };
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
    if (key.getTextCharacter() == 'f') { if (freezeButton.isEnabled()) freezeButton.triggerClick(); return true; }
    if (key.getTextCharacter() == 'a') { (showA.getToggleState() ? showB : showA).triggerClick(); return true; } // A/B
    return false;
}

void PulsarEditor::timerCallback()
{
    if (!cycle.isMouseButtonDown()) cycle.setValue(proc.position(), juce::dontSendNotification);
    pulsarCount.setText(juce::String(proc.pulsarCount()) + " pulsars", juce::dontSendNotification);
    updateModelControls();
    // The original's [F] and <- buttons do nothing with Loop off.
    const bool loopOn = loopButton.getToggleState();
    freezeButton.setEnabled(loopOn);
    backButton.setEnabled(loopOn);
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
    // A dithered desktop with each panel as a Classic Mac window (the tables and
    // the scope draw their own).
    classic::fillDesktop(g, getLocalBounds());
    for (const auto& p : panels) classic::drawWindow(g, p.area, p.title);
}

void PulsarEditor::resized()
{
    panels.clear();
    auto area = getLocalBounds().reduced(12);
    auto content = [](juce::Rectangle<int> r) { return r.withTrimmedTop(classic::kTitleHeight + 3).reduced(8, 2); };
    auto headerWindow = area.removeFromTop(classic::kTitleHeight + 36);
    area.removeFromTop(10);
    panels.push_back({ "Pulsar Generator - 2001 - Pulsar Synthesis", headerWindow });
    auto header = content(headerWindow);
    darkMode.setBounds(header.removeFromLeft(120));
    {
        auto r = header.reduced(0, 1);
        showB.setBounds(r.removeFromRight(96));
        r.removeFromRight(2);
        showA.setBounds(r.removeFromRight(96));
        r.removeFromRight(10);
        fixesButton.setBounds(r.removeFromRight(80));
        r.removeFromRight(6);
        modelSelector.setBounds(r.removeFromRight(190));
    }
    auto footerWindow = area.removeFromBottom(classic::kTitleHeight + 30);
    area.removeFromBottom(10);
    panels.push_back({ "Cycle", footerWindow });
    auto footer = content(footerWindow);
    status.setBounds(footer.removeFromLeft(322));
    pulsarCount.setBounds(footer.removeFromRight(140));
    cycle.setBounds(footer.withTrimmedLeft(12).withTrimmedRight(110)); // under the table grid

    const int row = 26;
    auto panel = [&](juce::Rectangle<int>& col, const juce::String& title, int h) {
        auto r = col.removeFromTop(h);
        col.removeFromTop(8);
        panels.push_back({ title, r });
        return content(r);
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
