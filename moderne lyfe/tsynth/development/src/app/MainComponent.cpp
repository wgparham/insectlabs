#include "MainComponent.h"
#include "Presets.h"
#include "SampleEdit.h"

using namespace turbosynth;

// A module's window: its settings and tools, and for a Sample or Output module
// the sound beside them (the original's palette on the left, sound on the right).
struct MainComponent::ModuleWindow {
    struct Body : juce::Component {
        explicit Body(ModulePanel& p) : panel(p) { addAndMakeVisible(panel); }
        void resized() override
        {
            auto r = getLocalBounds();
            if (sound) { panel.setBounds(r.removeFromLeft(300)); stripArea = r; if (strip) strip->setBounds(r); }
            else panel.setBounds(r);
        }
        void paint(juce::Graphics& g) override
        {
            g.fillAll(classic::theme().paper);
            if (sound && !strip && shot.isValid()) g.drawImageAt(shot, stripArea.getX(), stripArea.getY()); // as it was last shown
        }
        ModulePanel& panel;
        bool sound = false;
        juce::Component* strip = nullptr;
        juce::Image shot;
        juce::Rectangle<int> stripArea;
    };
    int index = 0;
    ModulePanel panel;
    Body body { panel };
    std::unique_ptr<InnerWindow> win;
};

void MainComponent::SoundStrip::resized()
{
    auto r = getLocalBounds();
    auto buttons = r.removeFromLeft(84).reduced(8, 10);
    play.setBounds(buttons.removeFromTop(26));
    buttons.removeFromTop(8);
    stop.setBounds(buttons.removeFromTop(26));
    wave.setBounds(r.reduced(4));
}

namespace {
enum MenuIds {
    kNew = 1, kOpen, kSave, kSaveAs, kExport, kQuit,
    kUndo = 10, kCut, kCopy, kPaste, kClear, kDuplicate, kConvert, kRedo, kReverse, kSilence,
    kDualMono = 50,
    kSaveLoopWave = 7,
    kPlay = 20, kStop, kHearSelected, kStereo,
    kAutoPreview = 30, kParams, kStereoPrefs,
    kDark = 40,
    kAbout = 60,
};

// One module's sound; a linked stereo sample plays as stereo (F29_00A66).
RenderResult renderModule(Engine& engine, const Patch& patch, int module)
{
    RenderResult r;
    r.left = engine.moduleOutput(module);
    r.sampleRate = patch.modules[(size_t) module - 1].sampleRate;
    if (const int p = samplePartner(patch, module)) {
        r.right = engine.moduleOutput(p);
        if (patch.modules[(size_t) module - 1].sampC[2] == 2) std::swap(r.left, r.right);
    }
    return r;
}
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
    {
        auto* s = settings.getUserSettings();
        autoPreview = s->getBoolValue("autoPreview", false);
        params.rate = s->getIntValue("pmRate", params.rate);
        params.length = s->getIntValue("pmLength", params.length);
        params.frequency = s->getIntValue("pmFrequency", params.frequency);
        params.inheritRate = s->getBoolValue("pmInheritRate", false);
        params.inheritLength = s->getBoolValue("pmInheritLength", false);
        params.dynamicLength = s->getBoolValue("pmDynamicLength", false);
        params.convertSamples = s->getBoolValue("pmConvertSamples", false);
        params.monitorLoop = s->getBoolValue("pmMonitorLoop", false);
        params.newDocStereo = s->getBoolValue("stNewDocStereo", false);
        params.padStereo = s->getBoolValue("stPadStereo", true);
        params.openBoth = s->getBoolValue("stOpenBoth", true);
        params.stereoRate = juce::jlimit(15, 18, s->getIntValue("stRate", 15));
        params.monoScale = s->getBoolValue("stMonoScale", true);
        params.emptyStereo = s->getBoolValue("stEmptyStereo", false);
    }
    setLookAndFeel(&lnf);
    juce::LookAndFeel::setDefaultLookAndFeel(&lnf);

    addAndMakeVisible(menuBar);
    patchView.patch = &patch;
    patchView.onSelect = [this](int) { showSoundEditor(); };
    waveView.onLoopChanged = [this] { setLoop(waveView.loopStart, waveView.loopEnd); };
    patchView.onChanged = [this] { refreshModuleWindows(); changed(); };
    patchView.onRemoving = [this](int i) { moduleRemoved(i); };
    patchView.onNewModule = [this](Module& m) { applyDefaults(m); };
    patchView.onConnected = [this](int from, int to) { inheritFrom(from, to); };
    patchView.onPreview = [this](int i) { render(i, true); };
    patchView.onOpen = [this](int i) {
        openModuleWindow(i);
        // F28_00628: opening an empty Sample module asks for a sound
        if (i >= 1 && i <= (int) patch.modules.size() && patch.modules[(size_t) i - 1].type == kSample && patch.modules[(size_t) i - 1].length == 0)
            loadSoundFor(i);
    };
    playButton.onClick = [this] { if (previewModule || renderPending || rendering) render(0, true); else play(); };
    stopButton.onClick = [this] { stop(); };
    addAndMakeVisible(desktop);
    desktop.setInterceptsMouseClicks(false, true); // the dither shows through; windows take the clicks
    patchWindow = std::make_unique<InnerWindow>("Untitled", patchView, false);
    patchWindow->onActivate = [this] { for (auto& w : moduleWindows) w->win->setActive(false); };
    desktop.addAndMakeVisible(*patchWindow);
    patchWindow->setActive(true);

    if (forceTheme < 0) devices.initialiseWithDefaultDevices(0, 2); // no audio device for snapshots
    syncRender = forceTheme >= 0;
    if (!syncRender) worker = std::thread([this] { workerLoop(); });
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
    {
        const std::lock_guard<std::mutex> lock(jobLock);
        quitting = true;
    }
    jobReady.notify_all();
    if (worker.joinable()) worker.join();
    transport.setSource(nullptr);
    player.setSource(nullptr);
    devices.removeAudioCallback(&player);
    juce::LookAndFeel::setDefaultLookAndFeel(nullptr);
    setLookAndFeel(nullptr);
}

// ---------------------------------------------------------------------------
juce::StringArray MainComponent::getMenuBarNames() { return { "File", "Edit", "Sound", "Options", "View", "Help" }; }

juce::PopupMenu MainComponent::getMenuForIndex(int index, const juce::String&)
{
    juce::PopupMenu m;
    const bool sel = patchView.selected > 0, editable = sel && !patchView.isOutput(patchView.selected);
    auto add = [&m](int id, const juce::String& text, const juce::String& key, bool enabled = true, bool ticked = false) {
        juce::PopupMenu::Item it(text);
        it.itemID = id; it.isEnabled = enabled; it.isTicked = ticked; it.shortcutKeyDescription = key;
        m.addItem(it);
    };
    if (index == 0) {
        m.addItem(kNew, "New Patch");
        m.addItem(kOpen, "Open...");
        m.addSeparator();
        m.addItem(kSave, "Save");
        m.addItem(kSaveAs, "Save As...");
        m.addItem(kExport, "Export Sound...");
        m.addItem(kSaveLoopWave, "Save Loop as Waveform...", sel && (patch.modules[(size_t) patchView.selected - 1].type == kSample || patchView.isOutput(patchView.selected)));
        m.addSeparator();
        m.addItem(kQuit, "Quit");
    } else if (index == 1) {
        // With a Sample module's sound shown in its window, the Edit menu works on its selection.
        const bool snd = soundSelectionActive(), sndSel = snd && waveView.hasSelection();
        add(kUndo, "Undo", "Ctrl+Z", !undoStack.empty());
        add(kRedo, "Redo", "Ctrl+Shift+Z", !redoStack.empty());
        m.addSeparator();
        add(kCut, "Cut", "Ctrl+X", snd ? sndSel : editable);
        add(kCopy, "Copy", "Ctrl+C", snd ? sndSel : editable);
        add(kPaste, "Paste", "Ctrl+V", snd ? !soundClipboard.empty() : clipboard.has_value());
        add(kClear, "Clear", "Ctrl+B", snd ? sndSel : editable);
        add(kDuplicate, "Duplicate", "Ctrl+D", editable);
        add(kReverse, "Reverse", "Ctrl+R", sndSel);
        add(kSilence, "Silence", "Ctrl+E", sndSel);
        m.addSeparator();
        m.addItem(kConvert, "Convert to Sample", editable && patch.modules[(size_t) patchView.selected - 1].type != kSample);
        m.addItem(kDualMono, "Convert Stereo Sample to Dual Mono", sel && samplePartner(patch, patchView.selected) != 0);
    } else if (index == 2) {
        m.addItem(kPlay, "Play", true);
        m.addItem(kStop, "Stop");
        m.addItem(kHearSelected, "Hear Selected Module", sel);
        m.addSeparator();
        m.addItem(kStereo, "Stereo Output", true, isStereo(patch));
    } else if (index == 3) {
        add(kAutoPreview, "Auto Preview", "Ctrl+T", true, autoPreview);
        m.addSeparator();
        add(kParams, "Parameter Manager...", "Ctrl+Y");
        m.addItem(kStereoPrefs, "Stereo Preferences...");
    } else if (index == 4) {
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
    case kPlay: if (previewModule || renderPending || rendering) render(0, true); else play(); break;
    case kStop: stop(); break;
    case kHearSelected: render(patchView.selected, true); break;
    case kStereo: // mono jack <-> the two halves of the stereo jack; Output R's cords go with it
        setStereo(patch, !isStereo(patch));
        patchView.selected = 0;
        closeAllModuleWindows();
        patchView.repaint();
        changed();
        break;
    case kUndo: undo(); break;
    case kRedo: redo(); break;
    case kCut: if (soundSelectionActive()) soundEdit(kCut); else { copySelected(); clearSelected(); } break;
    case kCopy: if (soundSelectionActive()) soundEdit(kCopy); else copySelected(); break;
    case kPaste: if (soundSelectionActive()) soundEdit(kPaste); else if (clipboard) paste(*clipboard, clipboardPartner ? &*clipboardPartner : nullptr); break;
    case kReverse: case kSilence: if (soundSelectionActive()) soundEdit(id); break;
    case kSaveLoopWave: saveLoopAsWaveform(); break;
    case kClear: if (soundSelectionActive()) soundEdit(kClear); else clearSelected(); break;
    case kDuplicate:
        if (patchView.selected && !patchView.isOutput(patchView.selected)) {
            const int p = samplePartner(patch, patchView.selected);
            const Module m = patch.modules[(size_t) patchView.selected - 1];
            const Module partner = p ? patch.modules[(size_t) p - 1] : Module{};
            paste(m, p ? &partner : nullptr);
        }
        break;
    case kConvert: convertToSample(); break;
    case kDualMono: convertToDualMono(); break;
    case kAutoPreview:
        autoPreview = !autoPreview;
        settings.getUserSettings()->setValue("autoPreview", autoPreview);
        break;
    case kParams: showParameterManager(); break;
    case kStereoPrefs: showStereoPreferences(); break;
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
}

void MainComponent::resized()
{
    auto r = getLocalBounds();
    menuBar.setBounds(r.removeFromTop(22));
    desktop.setBounds(r);
    if (patchWindow && patchWindow->getBounds().isEmpty()) // first layout: the patch window on the left
        patchWindow->setBounds(10, 10, juce::jmin(600, r.getWidth() - 20), r.getHeight() - 20);
}

// ---------------------------------------------------------------------------
void MainComponent::configurePanel(ModulePanel& p)
{
    p.onSoundTool = [this](int code) { soundTool(code); };
    p.onEdited = [this] {
        if (applyEditRules(patchView.selected)) { // refresh the fields once the edit is done
            juce::Component::SafePointer<MainComponent> self(this);
            juce::MessageManager::callAsync([self] { if (self) self->refreshModuleWindows(); });
        }
        for (auto& w : moduleWindows) if (w->index >= 1 && w->index <= (int) patch.modules.size()) w->win->setTitle(patch.modules[(size_t) w->index - 1].name);
        patchView.repaint();
        changed();
    };
    p.onSaveWaveform = [this](const Waveform& w) { saveWaveformAs(w); };
    p.onPreview = [this](int i) { render(i, true); };
    p.onLoadSound = [this](int i) { loadSoundFor(i); };
    p.onChooseWaveform = [this](std::function<void(const juce::File&)> done) {
        chooser = std::make_unique<juce::FileChooser>("Load a waveform", file.existsAsFile() ? file.getParentDirectory() : juce::File(), "*");
        chooser->launchAsync(juce::FileBrowserComponent::openMode | juce::FileBrowserComponent::canSelectFiles, [done](const juce::FileChooser& fc) {
            if (fc.getResult() != juce::File()) done(fc.getResult());
        });
    };
}

void MainComponent::saveWaveformAs(const Waveform& w)
{
    chooser = std::make_unique<juce::FileChooser>("Save the waveform", (file.existsAsFile() ? file.getParentDirectory() : juce::File()).getChildFile(juce::String(w.name)), "*");
    chooser->launchAsync(juce::FileBrowserComponent::saveMode | juce::FileBrowserComponent::warnAboutOverwriting, [w](const juce::FileChooser& fc) {
        std::string err;
        if (fc.getResult() != juce::File() && !saveWaveform(fc.getResult().getFullPathName().toStdString(), w, &err))
            juce::AlertWindow::showMessageBoxAsync(juce::MessageBoxIconType::WarningIcon, "TurboSynth", err);
    });
}

void MainComponent::openModuleWindow(int index)
{
    if (index < 1 || index > (int) patch.modules.size()) return;
    for (auto& w : moduleWindows)
        if (w->index == index) { w->win->activate(); return; }
    const Module& m = patch.modules[(size_t) index - 1];
    auto mw = std::make_unique<ModuleWindow>();
    mw->index = index;
    configurePanel(mw->panel);
    mw->body.sound = m.type == kSample || m.type == kOutput || m.type == kOutputR;
    mw->win = std::make_unique<InnerWindow>(juce::String(m.name), mw->body, true);
    auto* raw = mw.get();
    mw->win->onClose = [this, raw] {
        juce::Component::SafePointer<MainComponent> self(this);
        juce::MessageManager::callAsync([self, raw] { if (self) self->closeModuleWindow(raw); }); // not while its own handler runs
    };
    mw->win->onActivate = [this, raw] {
        patchWindow->setActive(false);
        for (auto& w : moduleWindows) if (w.get() != raw) w->win->setActive(false);
        patchView.selected = raw->index; // the front window's module is the one the menus act on
        patchView.repaint();
        showSoundEditor();
    };
    // Cascaded to the right of the patch window, like new windows in the original.
    const int k = (int) moduleWindows.size() % 8;
    const int w = mw->body.sound ? 760 : m.type == kOscillator ? 600 : 420;
    const int h = mw->body.sound ? 360 : 480;
    const int x = juce::jlimit(10, juce::jmax(10, desktop.getWidth() - w - 10), patchWindow->getRight() - 120 + 22 * k);
    const int y = juce::jlimit(10, juce::jmax(10, desktop.getHeight() - h - 10), 30 + 22 * k);
    desktop.addAndMakeVisible(*mw->win);
    mw->win->setBounds(x, y, w, h);
    mw->panel.show(&patch, index);
    moduleWindows.push_back(std::move(mw));
    raw->win->activate();
}

void MainComponent::closeModuleWindow(ModuleWindow* mw)
{
    if (soundHost == mw) { mw->body.removeChildComponent(&soundStrip); mw->body.strip = nullptr; soundHost = nullptr; }
    moduleWindows.erase(std::remove_if(moduleWindows.begin(), moduleWindows.end(), [mw](const auto& w) { return w.get() == mw; }), moduleWindows.end());
    if (!moduleWindows.empty()) moduleWindows.back()->win->activate(); else patchWindow->activate();
}

void MainComponent::closeAllModuleWindows()
{
    if (soundHost) { soundHost->body.removeChildComponent(&soundStrip); soundHost->body.strip = nullptr; soundHost = nullptr; }
    moduleWindows.clear();
    if (patchWindow) patchWindow->setActive(true);
}

void MainComponent::refreshModuleWindows()
{
    for (size_t k = moduleWindows.size(); k-- > 0;)
        if (moduleWindows[k]->index > (int) patch.modules.size()) closeModuleWindow(moduleWindows[k].get());
    for (auto& w : moduleWindows) {
        w->panel.show(&patch, w->index);
        w->win->setTitle(patch.modules[(size_t) w->index - 1].name);
    }
}

void MainComponent::moduleRemoved(int index)
{
    for (size_t k = moduleWindows.size(); k-- > 0;)
        if (moduleWindows[k]->index == index) closeModuleWindow(moduleWindows[k].get());
    for (auto& w : moduleWindows) if (w->index > index) --w->index;
}

void MainComponent::placeSoundStrip()
{
    ModuleWindow* target = nullptr;
    for (auto& w : moduleWindows) if (w->index == patchView.selected && w->body.sound) target = w.get();
    if (target == soundHost) return;
    if (soundHost) {
        if (!soundStrip.getBounds().isEmpty()) soundHost->body.shot = soundStrip.createComponentSnapshot(soundStrip.getLocalBounds());
        soundHost->body.removeChildComponent(&soundStrip);
        soundHost->body.strip = nullptr;
        soundHost->body.repaint();
    }
    soundHost = target;
    if (target) {
        target->body.addAndMakeVisible(soundStrip);
        target->body.strip = &soundStrip;
        target->body.resized();
    }
}

bool MainComponent::keyPressed(const juce::KeyPress& k)
{
    // Ctrl+letter can arrive as its control character (1..26) without the modifier.
    if (k.getKeyCode() >= 1 && k.getKeyCode() <= 26 && k.getKeyCode() != 8 && k.getKeyCode() != 9 && k.getKeyCode() != 13) // not Backspace, Tab, Return
        return keyPressed(juce::KeyPress('A' + k.getKeyCode() - 1, k.getModifiers().withFlags(juce::ModifierKeys::commandModifier), 0));
    const bool cmd = k.getModifiers().isCommandDown();
    if (k == juce::KeyPress::spaceKey) { if (transport.isPlaying()) stop(); else { if (previewModule || renderPending || rendering) render(0, true); else play(); } return true; }
    if (cmd && k.getKeyCode() == 'S') { save(k.getModifiers().isShiftDown()); return true; }
    if (cmd && k.getKeyCode() == 'O') { choosePatch(); return true; }
    if (cmd && k.getKeyCode() == 'N') { newPatch(); return true; }
    if (cmd && k.getKeyCode() == 'Z') { if (k.getModifiers().isShiftDown()) redo(); else undo(); return true; }
    if (cmd && k.getKeyCode() == 'X') { menuItemSelected(kCut, 1); return true; }
    if (cmd && k.getKeyCode() == 'C') { menuItemSelected(kCopy, 1); return true; }
    if (cmd && k.getKeyCode() == 'V') { menuItemSelected(kPaste, 1); return true; }
    if (cmd && k.getKeyCode() == 'R') { menuItemSelected(kReverse, 1); return true; }
    if (cmd && k.getKeyCode() == 'E') { menuItemSelected(kSilence, 1); return true; }
    if (cmd && k.getKeyCode() == 'D') { menuItemSelected(kDuplicate, 1); return true; }
    if (cmd && k.getKeyCode() == 'B') { menuItemSelected(kClear, 1); return true; }
    if (cmd && k.getKeyCode() == 'T') { menuItemSelected(kAutoPreview, 3); return true; }
    if (cmd && k.getKeyCode() == 'Y') { showParameterManager(); return true; }
    if (k == juce::KeyPress::deleteKey || k == juce::KeyPress::backspaceKey) { menuItemSelected(kClear, 1); return true; }
    return false;
}

// ---------------------------------------------------------------------------
void MainComponent::newPatch()
{
    stop();
    patch = Patch{};
    patch.version = 5;
    auto out = makeModule(kOutput, params.rate, params.length);
    out.rect = { 100, 380, 132, 412 };
    auto osc = makeModule(kOscillator, params.rate, params.length);
    osc.name = "Osc-1"; osc.rect = { 100, 80, 132, 112 }; osc.oscFreq = params.frequency << 16;
    auto env = makeModule(kAmpEnv, params.rate, params.length);
    env.name = "Amp Env-1"; env.rect = { 100, 230, 132, 262 };
    patch.modules = { out, osc, env };
    patch.connections = { { 2, 3 }, { 3, 1 } };
    if (params.newDocStereo) setStereo(patch, true); // Stereo Preferences: new documents as stereo
    file = juce::File();
    dirty = false;
    resetHistory();
    patchView.selected = 0;
    closeAllModuleWindows();
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
    resetHistory();
    patchView.selected = 0;
    closeAllModuleWindows();
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
    renderNow(0);
    if (result.left.empty()) return;
    chooser = std::make_unique<juce::FileChooser>("Export the sound", juce::File(), "*.wav;*.aif;*.aiff");
    chooser->launchAsync(juce::FileBrowserComponent::saveMode | juce::FileBrowserComponent::warnAboutOverwriting, [this](const juce::FileChooser& fc) {
        auto f = fc.getResult();
        if (f == juce::File()) return;
        if (!f.hasFileExtension("wav;aif;aiff")) f = f.withFileExtension("wav");
        if (result.right.empty()) { writeSound(f, false); return; }
        // A stereo sound: a stereo file, or one channel folded per Stereo Preferences (F27_01BCC).
        juce::Component::SafePointer<MainComponent> self(this);
        juce::AlertWindow::showAsync(juce::MessageBoxOptions().withIconType(juce::MessageBoxIconType::QuestionIcon).withTitle("Export the sound")
                                         .withMessage("Save the stereo sound as a stereo file or a mono file?")
                                         .withButton("Stereo").withButton("Mono").withAssociatedComponent(this),
                                     [self, f](int r) { if (self) self->writeSound(f, r == 0); });
    });
}

void MainComponent::writeSound(juce::File f, bool mono)
{
    {
        std::unique_ptr<juce::AudioFormat> fmt;
        if (f.hasFileExtension("wav")) fmt = std::make_unique<juce::WavAudioFormat>(); else fmt = std::make_unique<juce::AiffAudioFormat>();
        f.deleteFile();
        std::unique_ptr<juce::OutputStream> stream(f.createOutputStream().release());
        const int ch = result.right.empty() || mono ? 1 : 2;
        std::unique_ptr<juce::AudioFormatWriter> w(stream ? fmt->createWriterFor(stream.get(), result.sampleRate, (unsigned) ch, 16, {}, 0) : nullptr);
        if (!w) { juce::AlertWindow::showMessageBoxAsync(juce::MessageBoxIconType::WarningIcon, "TurboSynth", "Cannot write " + f.getFileName()); return; }
        stream.release();
        const auto& first = mono && !result.right.empty() ? foldToMono(result.left, result.right, params.monoScale) : result.left;
        juce::AudioBuffer<float> b(ch, (int) first.size());
        for (int i = 0; i < b.getNumSamples(); ++i) {
            b.setSample(0, i, first[(size_t) i] / 32768.0f);
            if (ch == 2) b.setSample(1, i, result.right[(size_t) i] / 32768.0f);
        }
        w->writeFromAudioSampleBuffer(b, 0, b.getNumSamples());
    }
}

void MainComponent::loadSoundFor(int index)
{
    chooser = std::make_unique<juce::FileChooser>("Choose a sound (WAV, AIFF or Sound Designer)", juce::File(), "*");
    chooser->launchAsync(juce::FileBrowserComponent::openMode | juce::FileBrowserComponent::canSelectFiles, [this, index](const juce::FileChooser& fc) {
        const auto f = fc.getResult();
        if (index < 1 || index > (int) patch.modules.size()) return;
        if (f == juce::File()) {
            // F28_00628: cancelled on an empty, unlinked Sample module with
            // "Empty Sample module: Stereo" makes it an empty linked pair.
            const auto& m = patch.modules[(size_t) index - 1];
            if (params.emptyStereo && m.type == kSample && m.length == 0 && !samplePartner(patch, index)) {
                makeStereoSample(patch, index, patchView.visibleRight());
                refreshModuleWindows();
                patchView.repaint();
                changed();
            }
            return;
        }
        juce::AudioFormatManager fm;
        fm.registerBasicFormats();
        std::unique_ptr<juce::AudioFormatReader> rd(fm.createReaderFor(f));
        if (rd && rd->numChannels > 1 && params.openBoth) { // Stereo Preferences "Open both channels"
            loadSoundFile(index, f, -1);
            return;
        }
        if (rd && rd->numChannels > 1) {
            // Stereo Preferences "Open specified channel": ask which side.
            juce::Component::SafePointer<MainComponent> self(this);
            juce::AlertWindow::showAsync(juce::MessageBoxOptions().withIconType(juce::MessageBoxIconType::QuestionIcon).withTitle("Stereo sound file")
                                             .withMessage(f.getFileName() + " is a stereo file. Choose which channel you want to open.")
                                             .withButton("Left").withButton("Right").withAssociatedComponent(this),
                                         [self, index, f](int r) { if (self) self->loadSoundFile(index, f, r == 1 ? 0 : 1); });
            return;
        }
        loadSoundFile(index, f, 0);
    });
}

void MainComponent::loadSoundFile(int index, const juce::File& f, int channel)
{
    // channel -1: both channels of a stereo file, into a linked pair (F28_00D8A, F28_00B90).
    if (index < 1 || index > (int) patch.modules.size()) return;
    std::vector<std::vector<int16_t>> data(channel < 0 ? 2 : 1);
    int32_t rate = patch.modules[(size_t) index - 1].sampleRate;
    juce::AudioFormatManager fm;
    fm.registerBasicFormats();
    if (std::unique_ptr<juce::AudioFormatReader> rd(fm.createReaderFor(f)); rd && rd->lengthInSamples > 0) {
        juce::AudioBuffer<float> b((int) rd->numChannels, (int) juce::jmin<juce::int64>(rd->lengthInSamples, 2147483));
        rd->read(&b, 0, b.getNumSamples(), 0, true, true);
        for (size_t k = 0; k < data.size(); ++k) {
            const int ch = juce::jlimit(0, b.getNumChannels() - 1, channel < 0 ? (int) k : channel);
            for (int i = 0; i < b.getNumSamples(); ++i) data[k].push_back((int16_t) juce::jlimit(-32768, 32767, (int) std::lround(b.getSample(ch, i) * 32767.0f)));
        }
        rate = (int32_t) rd->sampleRate;
    } else if (channel < 0 || !loadSoundDesigner(f.getFullPathName().toStdString(), data[0], nullptr)) {
        juce::AlertWindow::showMessageBoxAsync(juce::MessageBoxIconType::WarningIcon, "TurboSynth", "Cannot read " + f.getFileName());
        return;
    }
    std::vector<int> halves { index };
    if (channel < 0) { // the module becomes (or stays) a stereo pair: left gets channel 1
        const int r = makeStereoSample(patch, index, patchView.visibleRight());
        const int l = samplePartner(patch, r);
        halves = { l, r };
    } else if (samplePartner(patch, index)) {
        unlinkSample(patch, index); // one channel into half of a pair: both go mono (F28_00532)
    }
    for (size_t k = 0; k < halves.size(); ++k) {
        auto& m = patch.modules[(size_t) halves[k] - 1];
        m.sampleRate = rate;
        if (params.convertSamples && m.sampleRate != params.rate) { // Parameter Manager: convert to the default rate
            int32_t ls = -1, le = -1;
            sampleedit::convertRate(data[k], m.sampleRate, params.rate, ls, le);
            m.sampleRate = params.rate;
        }
        m.embedded = data[k];
        m.sampA = m.sampB = -1;
        m.length = (int32_t) m.embedded.size();
        m.name = f.getFileNameWithoutExtension().toStdString();
        rules::lengthChanged(patch, halves[k], ruleOptions()); // the new length reaches the modules it feeds
    }
    if (std::find(halves.begin(), halves.end(), patchView.selected) == halves.end()) patchView.selected = halves[0];
    index = patchView.selected;
    refreshModuleWindows();
    showSoundEditor();
    patchView.repaint();
    changed();
}

// ---------------------------------------------------------------------------
void MainComponent::changed()
{
    // One undo step per edit: keep the state from before it.
    undoStack.push_back(committed);
    if (undoStack.size() > 40) undoStack.erase(undoStack.begin());
    committed = patch;
    redoStack.clear(); // a new edit ends the redo chain
    dirty = true;
    renderPending = 8; // about a quarter of a second after the last edit
    updateTitle();
    repaint();
}

void MainComponent::render(int module, bool playWhenReady)
{
    if (module < 0 || module > (int) patch.modules.size()) module = 0;
    Job j;
    j.patch = patch;
    j.options = renderOptions();
    j.module = module;
    j.play = playWhenReady;
    if (syncRender) { // snapshots: render in place
        Engine engine(j.patch, j.options);
        RenderResult r;
        if (module > 0) r = renderModule(engine, patch, module);
        else r = engine.render();
        applyResult(std::move(r), module, false);
        return;
    }
    {
        const std::lock_guard<std::mutex> lock(jobLock);
        job = std::move(j); // a newer request replaces one not yet started
        rendering = true;
    }
    ++latestRequest;
    jobReady.notify_one();
    soundStrip.repaint();
}

void MainComponent::workerLoop()
{
    for (;;) {
        Job j;
        {
            std::unique_lock<std::mutex> lock(jobLock);
            jobReady.wait(lock, [this] { return quitting || job.has_value(); });
            if (quitting) return;
            j = std::move(*job);
            job.reset();
        }
        const int request = latestRequest.load();
        Engine engine(j.patch, j.options);
        RenderResult r;
        if (j.module > 0) r = renderModule(engine, j.patch, j.module);
        else r = engine.render();
        juce::MessageManager::callAsync([safe = juce::Component::SafePointer<MainComponent>(this), r = std::move(r), j, request]() mutable {
            if (!safe) return;
            if (request != safe->latestRequest.load()) return; // superseded by a newer edit
            {
                const std::lock_guard<std::mutex> lock(safe->jobLock);
                safe->rendering = safe->job.has_value();
            }
            safe->applyResult(std::move(r), j.module, j.play);
        });
    }
}

void MainComponent::applyResult(RenderResult r, int module, bool playNow)
{
    if (r.sampleRate <= 0) r.sampleRate = 44100;
    result = std::move(r);
    previewModule = module > 0 && module <= (int) patch.modules.size() ? module : 0;
    waveView.setSamples(result.left, result.right, result.sampleRate,
                        previewModule ? juce::String(patch.modules[(size_t) previewModule - 1].name) : juce::String("Output"));
    soundStrip.repaint();
    showSoundEditor();
    if (playNow) play();
}

void MainComponent::renderNow(int module)
{
    const bool saved = syncRender;
    syncRender = true;
    render(module);
    syncRender = saved;
}
void MainComponent::play()
{
    // With "Monitor loop at output" on, the output's loop keeps repeating
    // (about 20 seconds here) after the sound up to the loop end.
    const Module* out = nullptr;
    for (const auto& m : patch.modules) if (m.type == kOutput) { out = &m; break; }
    if (params.monitorLoop && previewModule == 0 && out && out->out[3] >= 0 && out->out[4] > out->out[3] && out->out[4] <= (int32_t) result.left.size()) {
        const int32_t ls = out->out[3], le = out->out[4];
        auto looped = [&](const std::vector<int16_t>& src) {
            if (src.empty()) return src;
            std::vector<int16_t> v(src.begin(), src.begin() + juce::jmin<int32_t>(le, (int32_t) src.size()));
            while ((int) v.size() < le + result.sampleRate * 20) v.insert(v.end(), src.begin() + ls, src.begin() + juce::jmin<int32_t>(le, (int32_t) src.size()));
            return v;
        };
        playSamples(looped(result.left), looped(result.right), result.sampleRate);
        return;
    }
    playSamples(result.left, result.right, result.sampleRate);
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
    if (renderPending > 0 && --renderPending == 0) render(previewModule, autoPreview);
    // Only touch the display when something moves.
    if (transport.isPlaying() && soundShown == 0) waveView.setPlayPosition(transport.getCurrentPosition()); // not over a sample being edited
    else waveView.setPlayPosition(-1.0);
}

void MainComponent::setDark(bool dark)
{
    classic::setDark(dark);
    settings.getUserSettings()->setValue("darkMode", dark);
    settings.saveIfNeeded();
    lnf.refresh();
    sendLookAndFeelChange();
    waveView.themeChanged();
    refreshModuleWindows();
    repaint();
}

void MainComponent::updateTitle()
{
    if (patchWindow) patchWindow->setTitle((file.existsAsFile() ? file.getFileName() : juce::String("Untitled")) + (dirty ? " *" : ""));
    if (auto* w = findParentComponentOfClass<juce::DocumentWindow>())
        w->setName("TurboSynth - " + (file.existsAsFile() ? file.getFileName() : juce::String("Untitled")) + (dirty ? " *" : ""));
}

// ---------------------------------------------------------------------------
void MainComponent::resetHistory()
{
    undoStack.clear();
    redoStack.clear();
    committed = patch;
}

void MainComponent::undo() { stepHistory(undoStack, redoStack); }
void MainComponent::redo() { stepHistory(redoStack, undoStack); }

void MainComponent::stepHistory(std::vector<Patch>& from, std::vector<Patch>& to)
{
    if (from.empty()) return;
    to.push_back(patch);
    patch = from.back();
    from.pop_back();
    committed = patch;
    for (auto& w : moduleWindows) w->panel.forgetAnalysis();
    patchView.selected = juce::jmin(patchView.selected, (int) patch.modules.size());
    refreshModuleWindows();
    soundShown = -1; // force the Sound window to re-read the sound
    showSoundEditor();
    patchView.repaint();
    dirty = true;
    renderPending = 8;
    updateTitle();
    repaint();
}

void MainComponent::copySelected()
{
    const int i = patchView.selected;
    if (!i || patchView.isOutput(i)) return;
    clipboard = patch.modules[(size_t) i - 1];
    clipboardPartner.reset();
    if (const int p = samplePartner(patch, i)) clipboardPartner = patch.modules[(size_t) p - 1]; // both halves are selected
}

void MainComponent::clearSelected()
{
    const int i = patchView.selected;
    if (!i || patchView.isOutput(i)) return; // the output jack cannot be erased
    const int p = samplePartner(patch, i); // both halves of a stereo sample are selected, so both go
    moduleRemoved(juce::jmax(i, p));
    removeModule(patch, juce::jmax(i, p));
    if (p) { moduleRemoved(juce::jmin(i, p)); removeModule(patch, juce::jmin(i, p)); }
    patchView.selected = 0;
    refreshModuleWindows();
    patchView.repaint();
    changed();
}

void MainComponent::paste(const Module& source, const Module* partner)
{
    // Pasted below and to the right of the original, without connections.
    // Pasted Sample modules get new ids (F16_02458); the two halves of a
    // stereo sample are linked to each other again (F16_02314) and lined up
    // 32 px apart (F16_024C4).
    int added[2] = { 0, 0 };
    const Module* src[2] = { &source, partner };
    for (int k = 0; k < 2 && src[k]; ++k) {
        Module m = *src[k];
        m.rect = { (int16_t) (m.rect[0] + 24), (int16_t) (m.rect[1] + 24), (int16_t) (m.rect[2] + 24), (int16_t) (m.rect[3] + 24) };
        if (m.type == kSample) { m.sampC[0] = 0; m.sampC[1] = 0; m.kind = 2001; }
        patch.modules.push_back(m);
        added[k] = (int) patch.modules.size();
        if (m.type == kSample) sampleId(patch, added[k]);
    }
    if (added[1]) {
        const bool firstIsLeft = source.sampC[2] == 1;
        const int l = firstIsLeft ? added[0] : added[1], r = firstIsLeft ? added[1] : added[0];
        linkSamplePair(patch, l, r);
        auto& lr = patch.modules[(size_t) l - 1].rect;
        auto& rr = patch.modules[(size_t) r - 1].rect;
        lr = { rr[0], (int16_t) (rr[1] - 32), rr[2], (int16_t) (rr[3] - 32) };
    } else if (patch.modules.back().type == kSample) {
        patch.modules.back().sampC[2] = 0; // a copy of one half alone is mono
    }
    patchView.selected = added[0];
    refreshModuleWindows();
    patchView.repaint();
    changed();
}

void MainComponent::convertToDualMono()
{
    const int i = patchView.selected;
    if (!samplePartner(patch, i)) return;
    unlinkSample(patch, i); // F28_00532: both halves become ordinary Sample modules where they stand
    refreshModuleWindows();
    patchView.repaint();
    changed();
}

void MainComponent::convertToSample()
{
    // Freeze the selected module's sound into a Sample module in its place;
    // its inputs are disconnected, its outputs stay.
    const int i = patchView.selected;
    if (!i || patchView.isOutput(i) || patch.modules[(size_t) i - 1].type == kSample) return;
    Engine engine(patch, renderOptions());
    const std::vector<int16_t> sound = engine.moduleOutput(i);
    const Module& old = patch.modules[(size_t) i - 1];
    Module s = makeModule(kSample, old.sampleRate, (int32_t) sound.size());
    s.rect = old.rect;
    s.name = old.name;
    s.embedded = sound;
    patch.modules[(size_t) i - 1] = s;
    auto& cs = patch.connections;
    cs.erase(std::remove_if(cs.begin(), cs.end(), [i](const Connection& c) { return c.to == i; }), cs.end());
    refreshModuleWindows();
    patchView.repaint();
    changed();
}

RenderOptions MainComponent::renderOptions() const
{
    RenderOptions o;
    o.padStereo = params.padStereo;
    o.stereoRate = params.stereoRate;
    return o;
}

void MainComponent::applyDefaults(Module& m) const
{
    if (m.type == kOutput || m.type == kOutputR) return;
    m.sampleRate = params.rate;
    m.length = params.length;
    if (m.type == kOscillator) m.oscFreq = params.frequency << 16;
    if (!m.points.empty()) { // envelopes end at the new length
        const int durMs = (int) juce::jmin<int64_t>(32767, (int64_t) m.length * 1000 / juce::jmax(1, m.sampleRate));
        m.points.back().timeMs = (int16_t) durMs;
        presets::setPointRect(m.points.back());
    }
}

rules::Options MainComponent::ruleOptions() const
{
    rules::Options o;
    o.inheritRate = params.inheritRate;
    o.inheritLength = params.inheritLength;
    o.dynamicLength = params.dynamicLength;
    return o;
}

void MainComponent::inheritFrom(int from, int to)
{
    rules::connected(patch, from, to, ruleOptions());
}

bool MainComponent::applyEditRules(int index)
{
    // What the change handlers do after an edit in Module Info: a new length
    // or rate travels on, and the windows of the length-changing modules
    // resend the input length (Dynamic length).
    if (index < 1 || index > (int) patch.modules.size() || committed.modules.size() != patch.modules.size()) return false;
    const Module& before = committed.modules[(size_t) index - 1];
    const Patch was = patch;
    const auto o = ruleOptions();
    if (patch.modules[(size_t) index - 1].length != before.length) rules::lengthChanged(patch, index, o);
    if (patch.modules[(size_t) index - 1].sampleRate != before.sampleRate) rules::rateChanged(patch, index, o);
    if (patch.modules[(size_t) index - 1].p != before.p) rules::parametersChanged(patch, index, o);
    for (size_t k = 0; k < patch.modules.size(); ++k)
        if (patch.modules[k].length != was.modules[k].length || patch.modules[k].sampleRate != was.modules[k].sampleRate) return true;
    return false;
}

void MainComponent::showParameterManager()
{
    auto* w = new juce::AlertWindow("Parameter Manager", "Defaults for new modules", juce::MessageBoxIconType::NoIcon, this);
    w->addTextEditor("rate", juce::String(params.rate), "Sample rate (Hz):");
    w->addTextEditor("length", juce::String(params.length), "Length (samples):");
    w->addTextEditor("frequency", juce::String(params.frequency), "Frequency (Hz):");
    auto toggle = [w](const juce::String& text, bool on) {
        auto* t = new juce::ToggleButton(text);
        t->setName({}); // the alert window would print the name as a second label
        t->setToggleState(on, juce::dontSendNotification);
        t->setSize(320, 22);
        w->addCustomComponent(t);
        return t;
    };
    auto* inheritRate = toggle("Inherit sample rate from input", params.inheritRate);
    auto* convertSamples = toggle("Convert sample modules to default", params.convertSamples);
    auto* inheritLength = toggle("Inherit length from input", params.inheritLength);
    auto* dynamic = toggle("Dynamic length based on parameters", params.dynamicLength);
    auto* monitor = toggle("Monitor loop at output", params.monitorLoop);
    w->addButton("OK", 1, juce::KeyPress(juce::KeyPress::returnKey));
    w->addButton("Cancel", 0, juce::KeyPress(juce::KeyPress::escapeKey));
    juce::Component::SafePointer<MainComponent> self(this);
    w->enterModalState(true, juce::ModalCallbackFunction::create([self, w, inheritRate, convertSamples, inheritLength, dynamic, monitor](int r) {
        if (self && r == 1) {
            auto& p = self->params;
            p.rate = juce::jlimit(1000, 96000, w->getTextEditorContents("rate").getIntValue());
            p.length = juce::jlimit(1, 2147483, w->getTextEditorContents("length").getIntValue());
            p.frequency = juce::jlimit(1, 20000, w->getTextEditorContents("frequency").getIntValue());
            p.inheritRate = inheritRate->getToggleState();
            p.convertSamples = convertSamples->getToggleState();
            p.inheritLength = inheritLength->getToggleState();
            p.dynamicLength = dynamic->getToggleState();
            p.monitorLoop = monitor->getToggleState();
            auto* s = self->settings.getUserSettings();
            s->setValue("pmRate", p.rate); s->setValue("pmLength", p.length); s->setValue("pmFrequency", p.frequency);
            s->setValue("pmInheritRate", p.inheritRate); s->setValue("pmInheritLength", p.inheritLength); s->setValue("pmDynamicLength", p.dynamicLength);
            s->setValue("pmConvertSamples", p.convertSamples);
            s->setValue("pmMonitorLoop", p.monitorLoop);
        }
        for (int i = w->getNumCustomComponents(); --i >= 0;) delete w->getCustomComponent(i);
    }), true);
}

// ---------------------------------------------------------------------------
// Sample and Output windows. The Sound window shows the selected Sample
// module's own sound (editable, with a selection) or, for an output, the
// patch's sound with the loop markers.
void MainComponent::showSoundEditor()
{
    placeSoundStrip();
    const int i = patchView.selected;
    const Module* m = i >= 1 && i <= (int) patch.modules.size() ? &patch.modules[(size_t) i - 1] : nullptr;
    waveView.editable = waveView.selectable = false;
    if (m && m->type == kSample) {
        if (auto* d = sampleData(i)) {
            m = &patch.modules[(size_t) i - 1];
            if (soundShown != i && soundShown != samplePartner(patch, i)) waveView.selStart = waveView.selEnd = -1; // a pair shares its selection
            waveView.setSamples(*d, {}, m->sampleRate, "Sample: " + juce::String(m->name));
            waveView.editable = waveView.selectable = true;
            waveView.loopStart = m->sampA;
            waveView.loopEnd = m->sampB;
            soundShown = i;
        }
    } else {
        if (soundShown) { // back to the patch's sound
            soundShown = 0;
            waveView.setSamples(result.left, result.right, result.sampleRate,
                                previewModule ? juce::String(patch.modules[(size_t) previewModule - 1].name) : juce::String("Output"));
        }
        if (m && (m->type == kOutput || m->type == kOutputR) && previewModule == 0) {
            waveView.editable = true;
            waveView.loopStart = m->out[3];
            waveView.loopEnd = m->out[4];
        }
    }
    waveView.repaint();
    syncLoopWindow();
}

std::vector<int16_t>* MainComponent::sampleData(int index)
{
    if (index < 1 || index > (int) patch.modules.size() || patch.modules[(size_t) index - 1].type != kSample) return nullptr;
    Module& m = patch.modules[(size_t) index - 1];
    return m.embedded.empty() ? nullptr : &m.embedded;
}

std::vector<int> MainComponent::sampleHalves(int index) const
{
    std::vector<int> v { index };
    if (const int p = samplePartner(patch, index)) v.push_back(p);
    return v;
}

void MainComponent::sampleEdited(int index)
{
    for (int h : sampleHalves(index)) {
        Module& m = patch.modules[(size_t) h - 1];
        if (m.embedded.empty()) continue;
        m.length = juce::jmax<int32_t>(1, (int32_t) m.embedded.size());
        if (m.sampA >= m.length) m.sampA = -1;
        if (m.sampB > m.length) m.sampB = -1;
    }
    if (waveView.selEnd > patch.modules[(size_t) index - 1].length) waveView.selStart = waveView.selEnd = -1;
    refreshModuleWindows();
    showSoundEditor();
    changed();
}

void MainComponent::setLoop(int32_t start, int32_t end)
{
    const int i = patchView.selected;
    if (i < 1 || i > (int) patch.modules.size()) return;
    Module& m = patch.modules[(size_t) i - 1];
    if (m.type == kSample) {
        for (int h : sampleHalves(i)) { patch.modules[(size_t) h - 1].sampA = start; patch.modules[(size_t) h - 1].sampB = end; } // a stereo pair keeps one loop
    } else if (m.type == kOutput || m.type == kOutputR) {
        m.out[3] = start;
        m.out[4] = end;
        if (const int p = patchView.partnerOf(i)) { // a stereo pair shares its markers
            patch.modules[(size_t) p - 1].out[3] = start;
            patch.modules[(size_t) p - 1].out[4] = end;
        }
    } else return;
    waveView.loopStart = start;
    waveView.loopEnd = end;
    waveView.repaint();
    refreshModuleWindows();
    changed();
    syncLoopWindow();
}

bool MainComponent::soundSelectionActive() const
{
    const int i = patchView.selected;
    return i >= 1 && i <= (int) patch.modules.size() && patch.modules[(size_t) i - 1].type == kSample && soundShown == i;
}

void MainComponent::soundEdit(int id)
{
    // The edit commands (CODE 16) do the same to the partner of a stereo
    // sample, over its own selection, which follows this one.
    const int i = patchView.selected;
    if (!sampleData(i)) return;
    const bool sel = waveView.hasSelection();
    const int32_t selStart = waveView.selStart, selEnd = waveView.selEnd;
    const auto halves = sampleHalves(i);
    if ((id == kCut || id == kCopy) && sel) { soundClipboard.clear(); soundClipboardRight.clear(); }
    int32_t newStart = selStart, newEnd = selEnd;
    for (int h : halves) {
        auto* d = sampleData(h);
        if (!d) continue;
        Module& m = patch.modules[(size_t) h - 1];
        const int32_t a = juce::jlimit<int32_t>(0, (int32_t) d->size(), selStart);
        const int32_t b = juce::jlimit<int32_t>(a, (int32_t) d->size(), selEnd);
        if ((id == kCut || id == kCopy) && sel) (m.sampC[2] == 2 ? soundClipboardRight : soundClipboard).assign(d->begin() + a, d->begin() + b);
        if ((id == kCut || id == kClear) && sel) { d->erase(d->begin() + a, d->begin() + b); newStart = newEnd = a; }
        if (id == kCut && sel && m.sampB >= (int32_t) d->size()) m.sampA = m.sampB = -1; // F16_00B2C: a loop end past the new end clears both markers
        if (id == kPaste && !soundClipboard.empty()) {
            const auto& clip = m.sampC[2] == 2 && !soundClipboardRight.empty() ? soundClipboardRight : soundClipboard;
            const int32_t at = selStart >= 0 ? a : (int32_t) d->size();
            if (sel) d->erase(d->begin() + a, d->begin() + b);
            d->insert(d->begin() + at, clip.begin(), clip.end());
            if (h == i) { newStart = at; newEnd = at + (int32_t) clip.size(); }
        }
        if (id == kReverse && sel) sampleedit::reverse(*d, a, b);
        if (id == kSilence && sel) sampleedit::silence(*d, a, b);
    }
    if ((id == kCut || id == kCopy) && sel && soundClipboard.empty()) std::swap(soundClipboard, soundClipboardRight); // copied from a right half alone
    waveView.selStart = newStart;
    waveView.selEnd = newEnd;
    if (id != kCopy) sampleEdited(i);
}

void MainComponent::soundTool(int code)
{
    const int i = patchView.selected;
    if (i < 1 || i > (int) patch.modules.size()) return;
    Module& m = patch.modules[(size_t) i - 1];
    const bool isSample = m.type == kSample;
    auto alert = [](const juce::String& text) { juce::AlertWindow::showMessageBoxAsync(juce::MessageBoxIconType::WarningIcon, "TurboSynth", text); };
    auto ask = [this](const juce::String& title, std::function<void(juce::AlertWindow&)> build, std::function<void(juce::AlertWindow&)> done) {
        auto* w = new juce::AlertWindow(title, {}, juce::MessageBoxIconType::NoIcon, this);
        build(*w);
        w->addButton("OK", 1, juce::KeyPress(juce::KeyPress::returnKey));
        w->addButton("Cancel", 0, juce::KeyPress(juce::KeyPress::escapeKey));
        juce::Component::SafePointer<MainComponent> self(this);
        w->enterModalState(true, juce::ModalCallbackFunction::create([self, w, done](int r) {
            if (self && r == 1) done(*w);
            for (int k = w->getNumCustomComponents(); --k >= 0;) delete w->getCustomComponent(k);
        }), true);
    };
    auto toggle = [](juce::AlertWindow& w, const juce::String& text, bool on) {
        auto* t = new juce::ToggleButton(text);
        t->setName({}); // the alert window would print the name as a second label
        t->setToggleState(on, juce::dontSendNotification);
        t->setSize(300, 22);
        w.addCustomComponent(t);
    };
    using ST = ModulePanel::SoundTool;
    switch (code) {
    case ST::kLoadSound: loadSoundFor(i); break;
    case ST::kStartMarker: case ST::kEndMarker:
        if (!isSample && previewModule != 0) render(0); // the output's markers sit on the patch's sound
        waveView.tool = code == ST::kStartMarker ? WaveView::Tool::PlaceStart : WaveView::Tool::PlaceEnd;
        showSoundEditor();
        break;
    case ST::kLoopWindow: openLoopWindow(); break;
    case ST::kLoopToSelection:
        if (m.sampA < 0 || m.sampB <= m.sampA) { alert("Place both loop markers first."); break; }
        waveView.selStart = m.sampA;
        waveView.selEnd = m.sampB;
        waveView.repaint();
        break;
    case ST::kRateConvert: {
        const int rateNow = m.sampleRate;
        ask("Sample Rate Conversion", [rateNow](juce::AlertWindow& w) {
            w.addTextBlock("Current rate: " + juce::String(rateNow) + " Hz");
            w.addTextEditor("rate", juce::String(rateNow), "Desired rate (Hz):");
        }, [this, i](juce::AlertWindow& w) {
            const int rate = w.getTextEditorContents("rate").getIntValue();
            if (rate < 100 || rate > 100000 || !sampleData(i)) return;
            for (int h : sampleHalves(i)) { // F28_01B6E converts both halves of a stereo sample
                auto* d = sampleData(h);
                if (!d) continue;
                Module& mm = patch.modules[(size_t) h - 1];
                sampleedit::convertRate(*d, mm.sampleRate, rate, mm.sampA, mm.sampB);
                mm.sampleRate = rate;
            }
            sampleEdited(i);
        });
        break; }
    case ST::kGainScale:
        ask("Gain Scaling", [toggle](juce::AlertWindow& w) {
            toggle(w, "Normalize", false);
            w.addTextEditor("percent", "100", "Or scale by (%):");
        }, [this, i](juce::AlertWindow& w) {
            if (!sampleData(i)) return;
            const bool norm = static_cast<juce::ToggleButton*>(w.getCustomComponent(0))->getToggleState();
            const int percent = juce::jlimit(1, 2000, w.getTextEditorContents("percent").getIntValue());
            if (norm) sampleedit::normalize(*sampleData(i)); // F28_01560: Normalize is the module's own (F18_00EAA), this half only
            else for (int h : sampleHalves(i)) // while scaling runs on both halves (F28_059C6 twice)
                if (auto* d = sampleData(h)) sampleedit::scaleGain(*d, percent);
            sampleEdited(i);
        });
        break;
    case ST::kRemoveEnvelope:
        ask("Envelope Removal", [](juce::AlertWindow& w) {
            w.addTextEditor("response", "50", "Response time (1-1000 ms):");
        }, [this, i](juce::AlertWindow& w) {
            if (!sampleData(i)) return;
            const int response = juce::jlimit(1, 1000, w.getTextEditorContents("response").getIntValue());
            for (int h : sampleHalves(i)) // both halves (F28_05A64 runs for each)
                if (auto* d = sampleData(h)) sampleedit::removeEnvelope(*d, patch.modules[(size_t) h - 1].sampleRate, response);
            sampleEdited(i);
        });
        break;
    case ST::kExtendLoop: {
        auto* d = sampleData(i);
        if (!d) break;
        Module& mm = patch.modules[(size_t) i - 1];
        if (mm.sampA < 0 || mm.sampB <= mm.sampA) { alert("Place both loop markers first."); break; }
        if (mm.length <= mm.sampB) { alert("The loop is repeated from the loop end to the module's Length: set a Length past the loop end in Module Info first."); break; }
        for (int h : sampleHalves(i)) { // F28_01FF0 extends both halves
            Module& hm = patch.modules[(size_t) h - 1];
            if (auto* hd = sampleData(h); hd && hm.sampA >= 0 && hm.sampB > hm.sampA && hm.length > hm.sampB)
                sampleedit::extendLoop(*hd, hm.length, hm.sampA, hm.sampB);
        }
        sampleEdited(i);
        break; }
    case ST::kCrossfade: {
        const int32_t ls = isSample ? m.sampA : m.out[3], le = isSample ? m.sampB : m.out[4];
        if (ls < 0 || le <= ls) { alert("Place both loop markers first."); break; }
        const auto* d = isSample ? sampleData(i) : &result.left;
        const int32_t size = d ? (int32_t) d->size() : 0;
        const int32_t maxLen = juce::jmin(le - ls, size - le, ls); // the three ranges the manual lists (F37_0008C)
        const Module& mm = patch.modules[(size_t) i - 1];
        const int32_t current = !isSample && mm.out[1] > 0 && mm.out[1] <= maxLen ? mm.out[1] : maxLen;
        const bool linear0 = isSample || mm.out[2] != 0;
        ask("Crossfade Looping", [isSample, current, linear0, maxLen, toggle](juce::AlertWindow& w) {
            if (!isSample) toggle(w, "Crossfade loop the output", true);
            w.addComboBox("type", { "Linear", "Equal Power" }, "Crossfade type:");
            w.getComboBoxComponent("type")->setSelectedItemIndex(linear0 ? 0 : 1);
            w.addTextEditor("length", juce::String(current), "Crossfade length (8 to " + juce::String(maxLen) + " samples):");
        }, [this, i, isSample, maxLen](juce::AlertWindow& w) {
            const int32_t len = w.getTextEditorContents("length").getIntValue();
            const bool linear = w.getComboBoxComponent("type")->getSelectedItemIndex() == 0;
            const bool on = isSample || static_cast<juce::ToggleButton*>(w.getCustomComponent(0))->getToggleState();
            if (on && len < 8) { juce::AlertWindow::showMessageBoxAsync(juce::MessageBoxIconType::WarningIcon, "TurboSynth", "The Crossfade Length must be at least 8 samples."); return; }
            if (on && len > maxLen) { juce::AlertWindow::showMessageBoxAsync(juce::MessageBoxIconType::WarningIcon, "TurboSynth", "The Crossfade Length is too large."); return; }
            Module& mm = patch.modules[(size_t) i - 1];
            if (isSample) { // an action on the sample's own sound
                bool done = false;
                for (int h : sampleHalves(i)) // both halves of a stereo sample share the loop
                    if (auto* dd = sampleData(h); dd && crossfadeLoop(*dd, len, mm.sampA, mm.sampB, linear)) done = true;
                if (done) sampleEdited(i);
            } else { // a mode: the output crossfades whatever reaches it
                mm.out[0] = on ? 1 : 0;
                mm.out[1] = len;
                mm.out[2] = linear ? 1 : 0;
                refreshModuleWindows();
                changed();
            }
        });
        break; }
    default: break;
    }
}

void MainComponent::openLoopWindow()
{
    const int i = patchView.selected;
    if (i < 1 || i > (int) patch.modules.size()) return;
    const Module& m = patch.modules[(size_t) i - 1];
    const int32_t ls = m.type == kSample ? m.sampA : m.out[3], le = m.type == kSample ? m.sampB : m.out[4];
    if (ls < 0 || le <= ls) {
        juce::AlertWindow::showMessageBoxAsync(juce::MessageBoxIconType::WarningIcon, "TurboSynth", "Place both loop markers first.");
        return;
    }
    if (m.type == kSample) sampleData(i);
    if (!loopWindow) {
        loopWindow = std::make_unique<LoopWindow>("Loop Window");
        loopWindow->view.onChanged = [this] { if (loopWindow) setLoop(loopWindow->view.loopStart, loopWindow->view.loopEnd); };
        loopWindow->view.onPlay = [this] { playLoop(); };
        loopWindow->view.onInfo = [this] {
            if (!loopWindow) return;
            const auto& v = loopWindow->view;
            const int i = patchView.selected;
            const int rate = i >= 1 && i <= (int) patch.modules.size() && patch.modules[(size_t) i - 1].sampleRate > 0 ? patch.modules[(size_t) i - 1].sampleRate : 44100;
            juce::AlertWindow::showMessageBoxAsync(juce::MessageBoxIconType::NoIcon, "Loop Window",
                "Loop start: " + juce::String(v.loopStart) + "\nLoop end: " + juce::String(v.loopEnd) + "\nLoop length: " + juce::String(v.loopEnd - v.loopStart)
                + " samples\nLoop frequency: " + juce::String((double) rate / (double) juce::jmax(1, v.loopEnd - v.loopStart), 2) + " Hz");
        };
        loopWindow->setLookAndFeel(&lnf);
        loopWindow->centreAroundComponent(this, loopWindow->getWidth(), loopWindow->getHeight());
    }
    loopWindow->setVisible(true);
    loopWindow->toFront(true);
    syncLoopWindow();
}

void MainComponent::syncLoopWindow()
{
    if (!loopWindow || !loopWindow->isVisible()) return;
    const int i = patchView.selected;
    const Module* m = i >= 1 && i <= (int) patch.modules.size() ? &patch.modules[(size_t) i - 1] : nullptr;
    const bool isSample = m && m->type == kSample, isOut = m && (m->type == kOutput || m->type == kOutputR);
    if (!isSample && !isOut) { loopWindow->setVisible(false); return; }
    auto& v = loopWindow->view;
    v.data = isSample ? m->embedded : (m->type == kOutputR && !result.right.empty() ? result.right : result.left);
    v.loopStart = isSample ? m->sampA : m->out[3];
    v.loopEnd = isSample ? m->sampB : m->out[4];
    // F28_01380: "Loop Window", with L or R and the name for a linked stereo half.
    const int ch = isSample && samplePartner(patch, i) ? m->sampC[2] : 0;
    loopWindow->setName(ch == 1 ? "Loop Window L " + juce::String(m->name) : ch == 2 ? "Loop Window R " + juce::String(m->name) : juce::String("Loop Window"));
    v.repaint();
}

void MainComponent::playLoop()
{
    // The sound up to the loop end, then the loop repeated, about four seconds in all.
    const int i = patchView.selected;
    if (i < 1 || i > (int) patch.modules.size()) return;
    const Module& m = patch.modules[(size_t) i - 1];
    const bool isSample = m.type == kSample;
    const std::vector<int16_t>& src = isSample ? m.embedded : result.left;
    const int32_t ls = isSample ? m.sampA : m.out[3], le = isSample ? m.sampB : m.out[4];
    const int rate = isSample ? m.sampleRate : result.sampleRate;
    if (ls < 0 || le <= ls || le > (int32_t) src.size()) return;
    std::vector<int16_t> out(src.begin(), src.begin() + le);
    while ((int) out.size() < le + rate * 4) out.insert(out.end(), src.begin() + ls, src.begin() + le);
    playSamples(out, {}, rate);
}

void MainComponent::playSamples(const std::vector<int16_t>& left, const std::vector<int16_t>& right, int rate)
{
    stop();
    if (left.empty()) return;
    const bool stereo = !right.empty();
    playBuffer.setSize(2, (int) left.size());
    for (int n = 0; n < playBuffer.getNumSamples(); ++n) {
        const float l = left[(size_t) n] / 32768.0f;
        playBuffer.setSample(0, n, l);
        playBuffer.setSample(1, n, stereo && n < (int) right.size() ? right[(size_t) n] / 32768.0f : l);
    }
    memorySource = std::make_unique<juce::MemoryAudioSource>(playBuffer, false, false);
    transport.setSource(memorySource.get(), 0, nullptr, rate > 0 ? rate : 44100, 2);
    transport.setPosition(0.0);
    transport.start();
}

void MainComponent::saveLoopAsWaveform()
{
    const int i = patchView.selected;
    if (i < 1 || i > (int) patch.modules.size()) return;
    const Module& m = patch.modules[(size_t) i - 1];
    const bool isSample = m.type == kSample;
    const std::vector<int16_t>& src = isSample ? m.embedded : result.left;
    const int32_t ls = isSample ? m.sampA : m.out[3], le = isSample ? m.sampB : m.out[4];
    if (ls < 0 || le <= ls || le > (int32_t) src.size()) {
        juce::AlertWindow::showMessageBoxAsync(juce::MessageBoxIconType::WarningIcon, "TurboSynth", "Place both loop markers first.");
        return;
    }
    Waveform w;
    w.name = m.name + " loop";
    const auto wave = sampleedit::loopAsWaveform(src, ls, le);
    std::copy(wave.begin(), wave.end(), w.samples.begin());
    saveWaveformAs(w);
}

void MainComponent::showStereoPreferences()
{
    // The original's "Creating Stereo" dialog (DITL 10040), the parts that apply here.
    auto* w = new juce::AlertWindow("Stereo Preferences", {}, juce::MessageBoxIconType::NoIcon, this);
    w->addComboBox("doc", { "Mono document", "Stereo document" }, "Open a new document as a:");
    w->getComboBoxComponent("doc")->setSelectedItemIndex(params.newDocStereo ? 1 : 0);
    w->addComboBox("match", { "Pad shorter length channel", "Truncate longer length channel" }, "Matching channel lengths:");
    w->getComboBoxComponent("match")->setSelectedItemIndex(params.padStereo ? 0 : 1);
    w->addComboBox("rate", { "Use highest", "Use lowest", "Use left channel's", "Use right channel's" }, "Sample rate:");
    w->getComboBoxComponent("rate")->setSelectedItemIndex(params.stereoRate - 15);
    w->addComboBox("open", { "Open both channels", "Open specified channel" }, "Opening stereo sound files:");
    w->getComboBoxComponent("open")->setSelectedItemIndex(params.openBoth ? 0 : 1);
    w->addComboBox("scale", { "Scale", "Don't scale" }, "Stereo to mono:");
    w->getComboBoxComponent("scale")->setSelectedItemIndex(params.monoScale ? 0 : 1);
    w->addComboBox("empty", { "Mono", "Stereo" }, "Empty Sample module:");
    w->getComboBoxComponent("empty")->setSelectedItemIndex(params.emptyStereo ? 1 : 0);
    w->addButton("OK", 1, juce::KeyPress(juce::KeyPress::returnKey));
    w->addButton("Cancel", 0, juce::KeyPress(juce::KeyPress::escapeKey));
    juce::Component::SafePointer<MainComponent> self(this);
    w->enterModalState(true, juce::ModalCallbackFunction::create([self, w](int r) {
        if (!self || r != 1) return;
        auto& p = self->params;
        p.newDocStereo = w->getComboBoxComponent("doc")->getSelectedItemIndex() == 1;
        p.padStereo = w->getComboBoxComponent("match")->getSelectedItemIndex() == 0;
        p.stereoRate = 15 + juce::jmax(0, w->getComboBoxComponent("rate")->getSelectedItemIndex());
        p.openBoth = w->getComboBoxComponent("open")->getSelectedItemIndex() == 0;
        auto* s = self->settings.getUserSettings();
        s->setValue("stNewDocStereo", p.newDocStereo); s->setValue("stPadStereo", p.padStereo); s->setValue("stRate", p.stereoRate);
        p.monoScale = w->getComboBoxComponent("scale")->getSelectedItemIndex() == 0;
        p.emptyStereo = w->getComboBoxComponent("empty")->getSelectedItemIndex() == 1;
        s->setValue("stOpenBoth", p.openBoth);
        s->setValue("stMonoScale", p.monoScale); s->setValue("stEmptyStereo", p.emptyStereo);
        self->render(); // the stereo sound may change
    }), true);
}
