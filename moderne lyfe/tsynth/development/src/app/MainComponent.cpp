#include "MainComponent.h"

using namespace turbosynth;

namespace {
enum MenuIds {
    kNew = 1, kOpen, kSave, kSaveAs, kExport, kQuit,
    kPlay = 20, kStop, kHearSelected,
    kDark = 40,
    kAbout = 60,
};
}

MainComponent::MainComponent(int forceTheme)
{
    juce::PropertiesFile::Options o;
    o.applicationName = "TurboSynth";
    o.filenameSuffix = ".settings";
    o.osxLibrarySubFolder = "Application Support";
    settings.setStorageParameters(o);
    classic::setDark(forceTheme >= 0 ? forceTheme == 1 : settings.getUserSettings()->getBoolValue("darkMode", false));
    lnf.refresh();
    setLookAndFeel(&lnf);
    juce::LookAndFeel::setDefaultLookAndFeel(&lnf);

    addAndMakeVisible(menuBar);
    patchView.patch = &patch;
    patchView.onSelect = [this](int i) { modulePanel.show(&patch, i); };
    patchView.onChanged = [this] { modulePanel.show(&patch, patchView.selected); changed(); };
    patchView.onPreview = [this](int i) { render(i); play(); };
    modulePanel.onEdited = [this] { patchView.repaint(); changed(); };
    modulePanel.onPreview = [this](int i) { render(i); play(); };
    modulePanel.onLoadSound = [this](int i) { loadSoundFor(i); };
    addAndMakeVisible(patchView);
    addAndMakeVisible(modulePanel);
    addAndMakeVisible(waveView);
    playButton.onClick = [this] { if (previewModule) render(0); play(); };
    stopButton.onClick = [this] { stop(); };
    addAndMakeVisible(playButton);
    addAndMakeVisible(stopButton);

    if (forceTheme < 0) devices.initialiseWithDefaultDevices(0, 2); // no audio device for snapshots
    player.setSource(&transport);
    devices.addAudioCallback(&player);

    setWantsKeyboardFocus(true);
    setSize(1180, 780);
    newPatch();
    startTimerHz(30);
}

MainComponent::~MainComponent()
{
    stopTimer();
    transport.setSource(nullptr);
    player.setSource(nullptr);
    devices.removeAudioCallback(&player);
    juce::LookAndFeel::setDefaultLookAndFeel(nullptr);
    setLookAndFeel(nullptr);
}

// ---------------------------------------------------------------------------
juce::StringArray MainComponent::getMenuBarNames() { return { "File", "Sound", "View", "Help" }; }

juce::PopupMenu MainComponent::getMenuForIndex(int index, const juce::String&)
{
    juce::PopupMenu m;
    if (index == 0) {
        m.addItem(kNew, "New Patch");
        m.addItem(kOpen, "Open...");
        m.addSeparator();
        m.addItem(kSave, "Save");
        m.addItem(kSaveAs, "Save As...");
        m.addItem(kExport, "Export Sound...");
        m.addSeparator();
        m.addItem(kQuit, "Quit");
    } else if (index == 1) {
        m.addItem(kPlay, "Play", true);
        m.addItem(kStop, "Stop");
        m.addItem(kHearSelected, "Hear Selected Module", patchView.selected > 0);
    } else if (index == 2) {
        m.addItem(kDark, "Dark Mode", true, classic::theme().dark);
    } else {
        m.addItem(kAbout, "About TurboSynth...");
    }
    return m;
}

void MainComponent::menuItemSelected(int id, int)
{
    switch (id) {
    case kNew: newPatch(); break;
    case kOpen: choosePatch(); break;
    case kSave: save(false); break;
    case kSaveAs: save(true); break;
    case kExport: exportSound(); break;
    case kQuit: juce::JUCEApplication::getInstance()->systemRequestedQuit(); break;
    case kPlay: if (previewModule) render(0); play(); break;
    case kStop: stop(); break;
    case kHearSelected: render(patchView.selected); play(); break;
    case kDark: setDark(!classic::theme().dark); break;
    case kAbout:
        juce::AlertWindow::showMessageBoxAsync(juce::MessageBoxIconType::NoIcon, "TurboSynth",
            "A port of Digidesign Turbosynth SC 2.22 (1988-1995).\n\n"
            "The synthesis follows the original program's 68000 code, and the Diffuser its DSP-card program.");
        break;
    default: break;
    }
}

// ---------------------------------------------------------------------------
void MainComponent::paint(juce::Graphics& g)
{
    classic::fillDesktop(g, getLocalBounds().withTrimmedTop(menuBar.getHeight()));
    const juce::String name = file.existsAsFile() ? file.getFileName() : juce::String("Untitled");
    classic::drawWindow(g, patchFrame, name + (dirty ? " *" : ""));
    classic::drawWindow(g, infoFrame, "Module Info");
    classic::drawWindow(g, soundFrame, previewModule ? "Sound: " + juce::String(patch.modules[(size_t) previewModule - 1].name) : juce::String("Sound"));
}

void MainComponent::resized()
{
    auto r = getLocalBounds();
    menuBar.setBounds(r.removeFromTop(22));
    r.reduce(10, 10);
    auto bottom = r.removeFromBottom(210);
    auto right = r.removeFromRight(330);
    r.removeFromRight(12);
    bottom.removeFromTop(12);
    patchFrame = r;
    infoFrame = right;
    soundFrame = bottom;
    patchView.setBounds(patchFrame.reduced(1).withTrimmedTop(classic::kTitleHeight));
    modulePanel.setBounds(infoFrame.reduced(1).withTrimmedTop(classic::kTitleHeight));
    auto sound = soundFrame.reduced(1).withTrimmedTop(classic::kTitleHeight);
    auto buttons = sound.removeFromLeft(90).reduced(10, 12);
    playButton.setBounds(buttons.removeFromTop(28));
    buttons.removeFromTop(8);
    stopButton.setBounds(buttons.removeFromTop(28));
    waveView.setBounds(sound.reduced(4));
}

bool MainComponent::keyPressed(const juce::KeyPress& k)
{
    const bool cmd = k.getModifiers().isCommandDown();
    if (k == juce::KeyPress::spaceKey) { if (transport.isPlaying()) stop(); else { if (previewModule) render(0); play(); } return true; }
    if (cmd && k.getKeyCode() == 'S') { save(k.getModifiers().isShiftDown()); return true; }
    if (cmd && k.getKeyCode() == 'O') { choosePatch(); return true; }
    if (cmd && k.getKeyCode() == 'N') { newPatch(); return true; }
    if ((k == juce::KeyPress::deleteKey || k == juce::KeyPress::backspaceKey) && patchView.selected) {
        removeModule(patch, patchView.selected);
        patchView.selected = 0;
        modulePanel.show(&patch, 0);
        patchView.repaint();
        changed();
        return true;
    }
    return false;
}

// ---------------------------------------------------------------------------
void MainComponent::newPatch()
{
    stop();
    patch = Patch{};
    patch.version = 5;
    auto out = makeModule(kOutput, 44100, 44100 * 2);
    out.rect = { 100, 380, 132, 412 };
    auto osc = makeModule(kOscillator, 44100, 44100 * 2);
    osc.name = "Osc-1"; osc.rect = { 100, 80, 132, 112 };
    auto env = makeModule(kAmpEnv, 44100, 44100 * 2);
    env.name = "Amp Env-1"; env.rect = { 100, 230, 132, 262 };
    patch.modules = { out, osc, env };
    patch.connections = { { 2, 3 }, { 3, 1 } };
    file = juce::File();
    dirty = false;
    patchView.selected = 0;
    modulePanel.show(&patch, 0);
    render();
    updateTitle();
    repaint();
}

void MainComponent::choosePatch()
{
    chooser = std::make_unique<juce::FileChooser>("Open a TurboSynth document", file.existsAsFile() ? file.getParentDirectory() : juce::File(), "*");
    chooser->launchAsync(juce::FileBrowserComponent::openMode | juce::FileBrowserComponent::canSelectFiles, [this](const juce::FileChooser& fc) {
        if (fc.getResult() != juce::File()) openFile(fc.getResult());
    });
}

void MainComponent::openFile(const juce::File& f)
{
    Patch p;
    std::string err;
    if (!loadPatch(f.getFullPathName().toStdString(), p, &err)) {
        juce::AlertWindow::showMessageBoxAsync(juce::MessageBoxIconType::WarningIcon, "TurboSynth", "Cannot open " + f.getFileName() + ":\n" + err);
        return;
    }
    stop();
    patch = p;
    file = f;
    dirty = false;
    patchView.selected = 0;
    modulePanel.show(&patch, 0);
    render();
    updateTitle();
    repaint();
}

void MainComponent::save(bool as)
{
    auto write = [this](const juce::File& f) {
        std::string err;
        if (!savePatch(f.getFullPathName().toStdString(), patch, &err)) {
            juce::AlertWindow::showMessageBoxAsync(juce::MessageBoxIconType::WarningIcon, "TurboSynth", err);
            return;
        }
        file = f; dirty = false; updateTitle(); repaint();
    };
    if (!as && file != juce::File()) { write(file); return; }
    chooser = std::make_unique<juce::FileChooser>("Save the patch", file.existsAsFile() ? file : juce::File(), "*");
    chooser->launchAsync(juce::FileBrowserComponent::saveMode | juce::FileBrowserComponent::warnAboutOverwriting,
                         [write](const juce::FileChooser& fc) { if (fc.getResult() != juce::File()) write(fc.getResult()); });
}

void MainComponent::exportSound()
{
    render(0);
    if (result.left.empty()) return;
    chooser = std::make_unique<juce::FileChooser>("Export the sound", juce::File(), "*.wav;*.aif;*.aiff");
    chooser->launchAsync(juce::FileBrowserComponent::saveMode | juce::FileBrowserComponent::warnAboutOverwriting, [this](const juce::FileChooser& fc) {
        auto f = fc.getResult();
        if (f == juce::File()) return;
        if (!f.hasFileExtension("wav;aif;aiff")) f = f.withFileExtension("wav");
        std::unique_ptr<juce::AudioFormat> fmt;
        if (f.hasFileExtension("wav")) fmt = std::make_unique<juce::WavAudioFormat>(); else fmt = std::make_unique<juce::AiffAudioFormat>();
        f.deleteFile();
        std::unique_ptr<juce::OutputStream> stream(f.createOutputStream().release());
        const int ch = result.right.empty() ? 1 : 2;
        std::unique_ptr<juce::AudioFormatWriter> w(stream ? fmt->createWriterFor(stream.get(), result.sampleRate, (unsigned) ch, 16, {}, 0) : nullptr);
        if (!w) { juce::AlertWindow::showMessageBoxAsync(juce::MessageBoxIconType::WarningIcon, "TurboSynth", "Cannot write " + f.getFileName()); return; }
        stream.release();
        juce::AudioBuffer<float> b(ch, (int) result.left.size());
        for (int i = 0; i < b.getNumSamples(); ++i) {
            b.setSample(0, i, result.left[(size_t) i] / 32768.0f);
            if (ch == 2) b.setSample(1, i, result.right[(size_t) i] / 32768.0f);
        }
        w->writeFromAudioSampleBuffer(b, 0, b.getNumSamples());
    });
}

void MainComponent::loadSoundFor(int index)
{
    chooser = std::make_unique<juce::FileChooser>("Choose a sound (WAV, AIFF or Sound Designer)", juce::File(), "*");
    chooser->launchAsync(juce::FileBrowserComponent::openMode | juce::FileBrowserComponent::canSelectFiles, [this, index](const juce::FileChooser& fc) {
        const auto f = fc.getResult();
        if (f == juce::File() || index < 1 || index > (int) patch.modules.size()) return;
        auto& m = patch.modules[(size_t) index - 1];
        std::vector<int16_t> data;
        juce::AudioFormatManager fm;
        fm.registerBasicFormats();
        if (std::unique_ptr<juce::AudioFormatReader> rd(fm.createReaderFor(f)); rd && rd->lengthInSamples > 0) {
            juce::AudioBuffer<float> b(1, (int) juce::jmin<juce::int64>(rd->lengthInSamples, 2147483));
            rd->read(&b, 0, b.getNumSamples(), 0, true, false);
            for (int i = 0; i < b.getNumSamples(); ++i) data.push_back((int16_t) juce::jlimit(-32768, 32767, (int) std::lround(b.getSample(0, i) * 32767.0f)));
            m.sampleRate = (int32_t) rd->sampleRate;
        } else if (!loadSoundDesigner(f.getFullPathName().toStdString(), data, nullptr)) {
            juce::AlertWindow::showMessageBoxAsync(juce::MessageBoxIconType::WarningIcon, "TurboSynth", "Cannot read " + f.getFileName());
            return;
        }
        m.embedded = data;
        m.length = (int32_t) data.size();
        m.name = f.getFileNameWithoutExtension().toStdString();
        modulePanel.show(&patch, index);
        patchView.repaint();
        changed();
    });
}

// ---------------------------------------------------------------------------
void MainComponent::changed()
{
    dirty = true;
    renderPending = 8; // about a quarter of a second after the last edit
    updateTitle();
    repaint();
}

void MainComponent::render(int module)
{
    RenderOptions opt;
    if (file.existsAsFile()) {
        opt.soundFolders.push_back(file.getParentDirectory().getFullPathName().toStdString());
        opt.soundFolders.push_back(file.getParentDirectory().getParentDirectory().getFullPathName().toStdString());
    }
    Engine engine(patch, opt);
    if (module > 0 && module <= (int) patch.modules.size()) {
        result = {};
        result.left = engine.moduleOutput(module);
        result.sampleRate = patch.modules[(size_t) module - 1].sampleRate;
        if (result.sampleRate <= 0) result.sampleRate = 44100;
    } else {
        result = engine.render();
        module = 0;
    }
    previewModule = module;
    waveView.setSamples(result.left, result.right, result.sampleRate,
                        module ? juce::String(patch.modules[(size_t) module - 1].name) : juce::String("Output"));
    repaint(soundFrame);
}

void MainComponent::play()
{
    stop();
    if (result.left.empty()) return;
    const int ch = result.right.empty() ? 1 : 2;
    playBuffer.setSize(2, (int) result.left.size());
    for (int i = 0; i < playBuffer.getNumSamples(); ++i) {
        const float l = result.left[(size_t) i] / 32768.0f;
        playBuffer.setSample(0, i, l);
        playBuffer.setSample(1, i, ch == 2 ? result.right[(size_t) i] / 32768.0f : l);
    }
    memorySource = std::make_unique<juce::MemoryAudioSource>(playBuffer, false, false);
    transport.setSource(memorySource.get(), 0, nullptr, result.sampleRate, 2);
    transport.setPosition(0.0);
    transport.start();
}

void MainComponent::stop()
{
    transport.stop();
    transport.setSource(nullptr);
    memorySource.reset();
    waveView.setPlayPosition(-1.0);
}

void MainComponent::timerCallback()
{
    if (renderPending > 0 && --renderPending == 0) render(previewModule);
    if (transport.isPlaying()) waveView.setPlayPosition(transport.getCurrentPosition());
    else if (memorySource) waveView.setPlayPosition(-1.0);
}

void MainComponent::setDark(bool dark)
{
    classic::setDark(dark);
    settings.getUserSettings()->setValue("darkMode", dark);
    settings.saveIfNeeded();
    lnf.refresh();
    sendLookAndFeelChange();
    modulePanel.show(&patch, patchView.selected);
    repaint();
}

void MainComponent::updateTitle()
{
    if (auto* w = findParentComponentOfClass<juce::DocumentWindow>())
        w->setName("TurboSynth - " + (file.existsAsFile() ? file.getFileName() : juce::String("Untitled")) + (dirty ? " *" : ""));
}
