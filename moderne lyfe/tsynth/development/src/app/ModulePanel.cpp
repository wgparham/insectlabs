#include "ModulePanel.h"
#include "ClassicLook.h"
#include "PatchView.h"
#include "Presets.h"

using namespace turbosynth;

turbosynth::Module* ModulePanel::module() const
{
    if (!patch || index < 1 || index > (int) patch->modules.size()) return nullptr;
    return &patch->modules[(size_t) index - 1];
}

void ModulePanel::edited() { if (onEdited) onEdited(); }

void ModulePanel::addField(const juce::String& name, int lo, int hi, std::function<int()> get, std::function<void(int)> set)
{
    auto f = std::make_unique<Field>();
    f->label = std::make_unique<juce::Label>(juce::String(), name);
    f->label->setFont(classic::uiFont(12.0f, false));
    f->edit = std::make_unique<juce::TextEditor>();
    f->edit->setFont(classic::uiFont(12.0f, false));
    f->edit->setInputRestrictions(7, "-0123456789");
    f->edit->setText(juce::String(get()), false);
    f->get = std::move(get); f->set = std::move(set); f->lo = lo; f->hi = hi;
    Field* raw = f.get();
    f->edit->onReturnKey = [this, raw] { commit(*raw); };
    f->edit->onFocusLost = [this, raw] { commit(*raw); };
    addAndMakeVisible(*f->label);
    addAndMakeVisible(*f->edit);
    fields.push_back(std::move(f));
}

void ModulePanel::commit(Field& f)
{
    const int v = juce::jlimit(f.lo, f.hi, f.edit->getText().getIntValue());
    f.edit->setText(juce::String(v), false);
    if (v != f.get()) { f.set(v); edited(); }
}

void ModulePanel::show(Patch* p, int i)
{
    patch = p; index = i;
    fields.clear();
    hasEnvelope = hasTable = hasWaves = hasTools = hasHarm = false;
    table.erasing = false;
    table.eraseValue = nullptr;
    removeAllChildren();
    auto* m = module();
    if (!m) { repaint(); return; }

    nameEdit.setFont(classic::uiFont(12.0f));
    nameEdit.setText(m->name, false);
    nameEdit.applyColourToAllText(classic::theme().ink); // text keeps the colour it was typed in; follow the theme
    nameEdit.onReturnKey = nameEdit.onFocusLost = [this] {
        if (auto* mm = module(); mm && mm->name != nameEdit.getText().toStdString()) { mm->name = nameEdit.getText().toStdString(); edited(); }
    };
    addAndMakeVisible(nameEdit);
    hear.onClick = [this] { if (onPreview) onPreview(index); };
    addAndMakeVisible(hear);

    const bool isOutput = m->type == kOutput || m->type == kOutputR;
    if (!isOutput) {
        addField("Sample rate (Hz)", 1000, 96000, [m] { return m->sampleRate; }, [m](int v) { m->sampleRate = v; });
        addField("Length (samples)", 1, 2147483, [m] { return m->length; }, [m](int v) { m->length = v; });
        if (m->type == kSample) { // the Sample info dialog's checkbox (DITL 10005), +13E
            normalize.setButtonText("Save sound data with document");
            normalize.setToggleState(m->saveSound, juce::dontSendNotification);
            normalize.onClick = [this] { if (auto* mm = module()) { mm->saveSound = normalize.getToggleState(); edited(); } };
            addAndMakeVisible(normalize);
        } else if (m->type != kOscillator && m->type != kNoise) {
            normalize.setButtonText("Normalize output");
            normalize.setToggleState(m->normalize, juce::dontSendNotification);
            normalize.onClick = [this] { if (auto* mm = module()) { mm->normalize = normalize.getToggleState(); edited(); } };
            addAndMakeVisible(normalize);
        }
    }
    auto word = [&](const juce::String& name, int k, int lo, int hi) {
        addField(name, lo, hi, [m, k] { return (int) m->p[(size_t) k]; }, [m, k](int v) { m->p[(size_t) k] = (int16_t) v; });
    };
    switch (m->type) {
    case kOscillator: {
        addField("Frequency (Hz)", 0, 20000, [m] { return (int) std::lround(m->oscFreq / 65536.0); }, [m](int v) { m->oscFreq = v << 16; });
        hasWaves = true;
        wave = juce::jlimit(0, juce::jmax(0, (int) m->waveforms.size() - 1), wave);
        // The time line: one box per waveform at its time.
        timeline.durationMs = (int) juce::jlimit<int64_t>(1, 32767, (int64_t) m->length * 1000 / juce::jmax(1, m->sampleRate));
        timeline.waves.clear();
        for (const auto& w : m->waveforms) timeline.waves.push_back({ w.timeMs, &w.samples, juce::String(w.name) });
        timeline.selected = wave;
        timeline.onSelect = [this](int i) {
            wave = i;
            juce::Component::SafePointer<ModulePanel> self(this);
            juce::MessageManager::callAsync([self] { if (self) self->show(self->patch, self->index); });
        };
        timeline.onMoved = [this](int i, int ms) {
            auto* mm = module();
            if (!mm || i >= (int) mm->waveforms.size()) return;
            mm->waveforms[(size_t) i].timeMs = (int16_t) ms;
            const Waveform moved = mm->waveforms[(size_t) i];
            std::stable_sort(mm->waveforms.begin(), mm->waveforms.end(), [](const Waveform& a, const Waveform& b) { return a.timeMs < b.timeMs; });
            for (int k = 0; k < (int) mm->waveforms.size(); ++k)
                if (mm->waveforms[(size_t) k].timeMs == moved.timeMs && mm->waveforms[(size_t) k].name == moved.name) { wave = k; break; }
            harmIndex = -1;
            edited();
            juce::Component::SafePointer<ModulePanel> self(this);
            juce::MessageManager::callAsync([self] { if (self) self->show(self->patch, self->index); });
        };
        addAndMakeVisible(timeline);
        if (!m->waveforms.empty()) {
            addField("Wave time (ms)", 0, 32767, [this, m] { return (int) m->waveforms[(size_t) wave].timeMs; },
                     [this, m](int v) { m->waveforms[(size_t) wave].timeMs = (int16_t) v; });
            if (harmonicMode) {
                // The Harmonic window works on the analysis of the current waveform.
                if (harmIndex != index || harmWave != wave) { harm = presets::analyze(m->waveforms[(size_t) wave]); harmIndex = index; harmWave = wave; }
                hasHarm = true;
                for (int k = 0; k < 64; ++k) harmEditor.levels[(size_t) k] = harm[(size_t) k + 1].level;
                harmEditor.onEdited = [this] {
                    auto* mm = module();
                    if (!mm || wave >= (int) mm->waveforms.size()) return;
                    for (int k = 0; k < 64; ++k) harm[(size_t) k + 1].level = harmEditor.levels[(size_t) k];
                    presets::synthesize(harm, mm->waveforms[(size_t) wave]);
                    refreshWave();
                };
                addAndMakeVisible(harmEditor);
            } else {
                hasTable = true;
                table.values.assign(m->waveforms[(size_t) wave].samples.begin(), m->waveforms[(size_t) wave].samples.begin() + 256);
                table.onEdited = [this] {
                    if (auto* mm = module(); mm && wave < (int) mm->waveforms.size()) {
                        std::copy(table.values.begin(), table.values.end(), mm->waveforms[(size_t) wave].samples.begin());
                        mm->waveforms[(size_t) wave].samples[256] = mm->waveforms[(size_t) wave].samples[0];
                    }
                    harmIndex = -1;
                    timeline.repaint();
                    edited();
                };
                addAndMakeVisible(table);
            }
            saveWave.onClick = [this] {
                if (auto* mm = module(); mm && wave < (int) mm->waveforms.size() && onSaveWaveform) onSaveWaveform(mm->waveforms[(size_t) wave]);
            };
            saveWave.setTooltip("Save this waveform to a file");
            addAndMakeVisible(saveWave);
        }
        addWave.onClick = [this] {
            if (auto* mm = module()) {
                Waveform w = mm->waveforms.empty() ? makeModule(kOscillator, 44100, 1).waveforms[0] : mm->waveforms[(size_t) wave];
                w.timeMs = (int16_t) juce::jmin(32767, w.timeMs + 100);
                mm->waveforms.insert(mm->waveforms.begin() + (mm->waveforms.empty() ? 0 : wave + 1), w);
                wave = mm->waveforms.empty() ? 0 : wave + 1;
                edited(); show(patch, index);
            }
        };
        removeWave.onClick = [this] {
            if (auto* mm = module(); mm && mm->waveforms.size() > 1) { mm->waveforms.erase(mm->waveforms.begin() + wave); wave = juce::jmax(0, wave - 1); edited(); show(patch, index); }
        };
        addAndMakeVisible(addWave);
        addAndMakeVisible(removeWave);
        break; }
    case kSample:
        // The Sample window's tools are in the palette; the sound itself is edited beside them.
        break;
    case kAmpEnv: case kFilter: case kSpectralInverter: case kPitchEnv: {
        if (m->type == kSpectralInverter)
            addField("Pattern (1 = every other sample)", 0, 1, [m] { return (int) m->envMode; }, [m](int v) { m->envMode = (int16_t) v; });
        hasEnvelope = true;
        envelope.points.clear();
        for (const auto& pt : m->points) envelope.points.push_back({ pt.level, pt.timeMs });
        envelope.durationMs = (int) juce::jmax<int64_t>(1, (int64_t) m->length * 1000 / juce::jmax(1, m->sampleRate));
        envelope.onEdited = [this] {
            if (auto* mm = module()) {
                mm->points.clear();
                for (const auto& pt : envelope.points) {
                    EnvPoint p { (int16_t) pt.level, (int16_t) pt.timeMs, {} };
                    presets::setPointRect(p);
                    mm->points.push_back(p);
                }
                edited();
            }
        };
        addAndMakeVisible(envelope);
        break; }
    case kModulator:
        word("Depth", 0, 0, 100); word("Mix (% wet)", 1, 0, 100); word("Mode: 1 AM, 2 delay, 3 rate", 2, 1, 3);
        word("Modulator input #", 3, 1, 32); word("Carrier input #", 4, 1, 32);
        break;
    case kWaveshaper:
        hasTable = true;
        table.values.assign(m->shaper.begin(), m->shaper.begin() + 256); // [256] is the engine's guard entry
        table.eraseValue = presets::shaperIdentity;
        table.onEdited = [this] { if (auto* mm = module()) { std::copy(table.values.begin(), table.values.end(), mm->shaper.begin()); edited(); } };
        addAndMakeVisible(table);
        break;
    case kMixer: {
        int n = 0;
        for (const auto& c : patch->connections) if (c.to == index) ++n;
        for (int k = 0; k < juce::jmax(1, juce::jmin(n, 32)); ++k)
            addField("Input " + juce::String(k + 1) + " level", 0, 100, [m, k] { return (int) m->mixGains[(size_t) k]; },
                     [m, k](int v) { m->mixGains[(size_t) k] = (int16_t) v; });
        break; }
    case kDelay:
        word("Delay coarse (x10 ms)", 0, 0, 999); word("Delay fine (x0.1 ms)", 1, 0, 99);
        word("Feedback (%)", 2, 0, 100); word("Polarity (2 = inverted)", 3, 1, 2); word("Mix (% wet)", 4, 0, 100);
        break;
    case kResonator:
        word("Pitch coarse (x10 Hz)", 0, 0, 999); word("Pitch fine (x0.1 Hz)", 1, 0, 99);
        word("Feedback (%)", 2, 0, 100); word("Harmonics (2 = odd only)", 3, 1, 2); word("Mix (% wet)", 4, 0, 100);
        break;
    case kPitchShifter:
        word("Semitones", 0, -48, 48); word("Fine (% of semitone)", 1, 0, 100); word("Mix (% wet)", 2, 0, 100);
        break;
    case kStretchExpand: case kStretchCompress:
        word("Pitch coarse (x10 Hz)", 0, 0, 999); word("Pitch fine (x0.1 Hz)", 1, 0, 99);
        word("Start (x10 ms)", 2, 0, 9999); word("Amount", 3, 1, 100);
        break;
    case kNoise:
        word("Frequency coarse (x10 Hz)", 0, 0, 2000); word("Frequency fine (x0.1 Hz)", 1, 0, 99);
        word("Amplitude randomness", 2, 0, 100); word("Frequency randomness", 3, 0, 100);
        break;
    case kDiffuser:
        word("Size coarse", 0, 0, 999); word("Size fine", 1, 0, 99); word("Amount (%)", 2, 0, 100);
        break;
    default: break;
    }
    if (m->type == kSample || isOutput) {
        // What the original's info boxes show: the loop and its frequency, and for the output the crossfade mode.
        const int32_t ls = m->type == kSample ? m->sampA : m->out[3], le = m->type == kSample ? m->sampB : m->out[4];
        juce::String text;
        if (ls >= 0 && le > ls) {
            const int rate = m->sampleRate > 0 ? m->sampleRate : 44100;
            text << "Loop: " << (int) ls << " to " << (int) le << "\nLoop frequency: " << juce::String((double) rate / (double) (le - ls), 2) << " Hz\n";
        } else text << "No loop markers\n";
        if (isOutput)
            text << "Crossfade looping: " << (m->out[0] ? (m->out[2] ? "Linear, " : "Equal Power, ") + juce::String(m->out[1]) + " samples" : juce::String("off")) << "\n";
        text << "Drag in the sound to select; the markers set the loop.";
        soundInfo.setText(text, juce::dontSendNotification);
        soundInfo.setFont(classic::smallFont(11.0f));
        soundInfo.setJustificationType(juce::Justification::topLeft);
        soundInfo.setColour(juce::Label::textColourId, classic::theme().ink);
        addAndMakeVisible(soundInfo);
    }
    if (m->type == kWaveshaper || hasEnvelope || (m->type == kOscillator && !m->waveforms.empty()) || m->type == kSample || isOutput) setupTools(*m);
    resized();
    repaint();
}

void ModulePanel::setupTools(Module& m)
{
    // Tool codes: presets from 0, modifiers from kMod, the pencil and eraser at kMode.
    static constexpr int kMod = 100, kMode = 200;
    if (rng.seed == 1) rng.seed = (juce::Time::getMillisecondCounter() & 0x3FFFFFFF) | 1;
    tools.clear();
    hasTools = true;
    addAndMakeVisible(tools);
    switch (m.type) {
    case kOscillator: {
        // The original's Waveform/Harmonic window palette (CODE 38): three icons
        // to a row; the mode icons stacked on the left, the drawing tools beside
        // them, the three preset icons on one row, then the modifiers.
        tools.columns = 3;
        tools.setTip(kMode + 2, "Waveform mode: the wave as amplitude against time");
        tools.setTip(kMode + 3, "Harmonic mode: the first 64 harmonics");
        if (harmonicMode) {
            tools.addTools({ 1008, 1000, 1007 }, kMode + 2); // the arrow and rake get their codes below
            tools.addTools({ 1009 }, kMode + 3);
            tools.addBlank(); tools.addBlank();
        } else {
            tools.addTools({ 1008, 1006 }, kMode + 2);
            tools.addTools({ 1001 }, kMode + 8);
            tools.addTools({ 1009 }, kMode + 3);
            tools.addBlank(); tools.addBlank();
            tools.setTip(kMode + 3, "Harmonic mode: the first 64 harmonics");
        }
        if (harmonicMode) {
            // Row 1 was added as 1008, 1000, 1007 with codes kMode+2..+4; give
            // them their meanings: waveform mode, arrow (kMode+5), rake (kMode+6).
            tools.remap(1, kMode + 5);
            tools.remap(2, kMode + 6);
            tools.setTip(kMode + 5, "Arrow: drag a harmonic up or down");
            tools.setTip(kMode + 6, "Rake: draw a line; the harmonics under it move to it (Alt: only those above; Alt+Shift: all)");
            tools.active = { kMode + 3, harmEditor.rake ? kMode + 6 : kMode + 5 };
            tools.addPresetRow({ 1110, 1111, 1112 }, 0);
            tools.addTools({ 1113, 1114, 1115, 1116, 1117, 1118, 1119, 1120, 1121, 1122, 1123, 1124, 1125, 1126, 1127 }, kMod);
            for (int k = 0; k < presets::kHarmonicModifiers; ++k) tools.setTip(kMod + k, presets::harmonicModifierName(k));
        } else {
            tools.remap(1, kMode + 7);
            tools.setTip(kMode + 7, "Pencil: draw the waveform");
            tools.setTip(kMode + 8, "Eraser: set the samples it passes over to 0");
            table.eraseValue = [](int) { return (int16_t) 0; }; // F38_031CC: tool 3 stores 0
            tools.active = { kMode + 2, table.erasing ? kMode + 8 : kMode + 7 };
            tools.addPresetRow({ 1010, 1011, 1012 }, 0);
            tools.addTools({ 1013, 1014, 1015, 1016, 1017, 1018, 1019, 1020, 1021, 1022, 1023, 1024, 1025, 1026, 1027, 1028, 1029, 1030 }, kMod);
            for (int k = 0; k < presets::kWaveModifiers; ++k) tools.setTip(kMod + k, presets::waveModifierName(k));
        }
        for (int p = 0; p < presets::kWavePresets; ++p) tools.setTip(p, presets::wavePresetName(p));
        tools.setTip(presets::kWavePresets, "Load a waveform file...");  // the disk, the last quarter
        tools.onTool = [this](int code) {
            auto* mm = module();
            if (!mm || wave >= (int) mm->waveforms.size()) return;
            const int w = wave;
            if (code == kMode + 2 || code == kMode + 3) {
                harmonicMode = code == kMode + 3;
                juce::Component::SafePointer<ModulePanel> self(this);
                juce::MessageManager::callAsync([self] { if (self) self->show(self->patch, self->index); });
                return;
            }
            if (code == kMode + 7 || code == kMode + 8) {
                table.erasing = code == kMode + 8;
                tools.active = { kMode + 2, code };
                tools.repaint();
                return;
            }
            if (code >= kMode + 4) {
                if (code != kMode + 5 && code != kMode + 6) return;
                harmEditor.rake = code == kMode + 6;
                tools.active = { kMode + 3, code };
                tools.repaint();
                return;
            }
            if (code == presets::kWavePresets) {
                if (onChooseWaveform)
                    onChooseWaveform([this, w](const juce::File& f) {
                        auto* m2 = module();
                        if (!m2 || w >= (int) m2->waveforms.size()) return;
                        Waveform loaded;
                        std::string err;
                        if (!loadWaveform(f.getFullPathName().toStdString(), loaded, &err)) {
                            juce::AlertWindow::showMessageBoxAsync(juce::MessageBoxIconType::WarningIcon, "TurboSynth", err);
                            return;
                        }
                        loaded.name = f.getFileName().substring(0, 31).toStdString(); // most original files hold junk in the name field
                        loaded.timeMs = m2->waveforms[(size_t) w].timeMs; // keeps its place on the time line
                        loaded.rect = m2->waveforms[(size_t) w].rect;
                        m2->waveforms[(size_t) w] = loaded;
                        harmIndex = -1;
                        if (harmonicMode) { harm = presets::analyze(loaded); harmIndex = index; harmWave = w; }
                        refreshWave();
                    });
                return;
            }
            auto& wf = mm->waveforms[(size_t) w];
            if (harmonicMode) {
                if (code < kMod) { presets::harmonicPreset(harm, code); wf.name = presets::wavePresetName(code); }
                else presets::harmonicModifier(harm, code - kMod, rng);
                presets::synthesize(harm, wf);
            } else {
                if (code < kMod) presets::wavePreset(wf, code);
                else presets::waveModifier(wf, code - kMod, rng);
                harmIndex = -1;
            }
            refreshWave();
        };
        break; }
    case kWaveshaper:
        tools.addTools({ 1006, 1001 }, kMode);
        tools.addPresetPair(3400, 3401, 0);
        tools.addTools({ 3402, 3403, 3404, 3405, 3406, 3407, 3408, 3409, 3410, 3411 }, kMod);
        tools.setTip(kMode, "Pencil: draw the transfer function");
        tools.setTip(kMode + 1, "Eraser: put back the straight line");
        for (int p = 0; p < presets::kShaperPresets; ++p) tools.setTip(p, presets::shaperPresetName(p));
        for (int k = 0; k < presets::kShaperModifiers; ++k) tools.setTip(kMod + k, presets::shaperModifierName(k));
        tools.active = { table.erasing ? kMode + 1 : kMode };
        tools.onTool = [this](int code) {
            auto* mm = module();
            if (!mm) return;
            if (code >= kMode) { table.erasing = code == kMode + 1; tools.active = { code }; tools.repaint(); return; }
            const uint32_t ticks = (uint32_t) ((uint64_t) juce::Time::getMillisecondCounter() * 60 / 1000); // TickCount
            if (code < kMod) presets::shaperPreset(mm->shaper.data(), code, rng, ticks);
            else presets::shaperModifier(mm->shaper.data(), code - kMod, rng);
            table.values.assign(mm->shaper.begin(), mm->shaper.begin() + 256);
            table.repaint();
            edited();
        };
        break;
    case kSample: case kOutput: case kOutputR: {
        // The Sample window's palette (the Output's has the loop tools only).
        const bool sample = m.type == kSample;
        if (sample) {
            tools.addTools({ 2100, 2106, 2101, 2105, 2104, 8004, 8005 }, 0);
            const int codes[] = { kLoadSound, kRateConvert, kExtendLoop, kGainScale, kRemoveEnvelope, kCrossfade, kLoopToSelection };
            const char* tips[] = { "Load a sound file into the module", "Sample rate conversion", "Extend the loop to the module's length",
                                   "Gain scaling (or normalize)", "Envelope removal", "Crossfade looping", "Loop points to selection" };
            // addTools numbers from 0; remap to the sound-tool codes below.
            for (int k = 0; k < 7; ++k) tools.setTip(k, tips[k]);
            tools.addSplitIcon(7101, 7, 8);
            tools.addTools({ 2103 }, 9);
            tools.onTool = [this, codes](int code) {
                static const int more[] = { kStartMarker, kEndMarker, kLoopWindow };
                if (onSoundTool) onSoundTool(code < 7 ? codes[code] : more[code - 7]);
            };
        } else {
            tools.addSplitIcon(7101, 7, 8);
            tools.addTools({ 2103, 8004 }, 9);
            tools.onTool = [this](int code) {
                static const int map[] = { kStartMarker, kEndMarker, kLoopWindow, kCrossfade };
                if (onSoundTool && code >= 7 && code <= 10) onSoundTool(map[code - 7]);
            };
            tools.setTip(10, "Crossfade looping (a mode: it applies to whatever reaches the output)");
        }
        tools.setTip(7, "Loop start marker: click in the sound to place it");
        tools.setTip(8, "Loop end marker: click in the sound to place it");
        tools.setTip(9, "Loop window: line up the loop end and start");
        break; }
    default: // the breakpoint envelopes
        tools.addPresetPair(3100, 3101, 0);
        tools.addTools({ 3102, 3103, 3104, 3105, 3106, 3107 }, kMod);
        for (int p = 0; p < presets::kEnvPresets; ++p) tools.setTip(p, presets::envPresetName(p));
        for (int k = 0; k < presets::kEnvModifiers; ++k) tools.setTip(kMod + k, presets::envModifierName(k));
        tools.onTool = [this](int code) {
            auto* mm = module();
            if (!mm) return;
            if (code < kMod) presets::envPreset(*mm, code);
            else presets::envModifier(*mm, code - kMod);
            envelope.points.clear();
            for (const auto& pt : mm->points) envelope.points.push_back({ pt.level, pt.timeMs });
            envelope.repaint();
            edited();
        };
        break;
    }
}

void ModulePanel::paint(juce::Graphics& g)
{
    const auto& t = classic::theme();
    g.fillAll(t.paper);
    auto* m = module();
    g.setColour(t.ink);
    if (!m) {
        g.setFont(classic::uiFont(12.0f, false));
        g.drawText("Select a module to see its settings.", getLocalBounds().reduced(12), juce::Justification::centredTop);
        return;
    }
    classic::drawIcon(g, PatchView::iconFor(*m), { 10.0f, 8.0f, 32.0f, 32.0f });
    g.setFont(classic::uiFont(12.0f));
    const bool pair = m->type == kSample && samplePartner(*patch, index);
    g.drawText(pair ? juce::String(m->sampC[2] == 1 ? "Stereo Sample, left" : "Stereo Sample, right") : juce::String(moduleTypeName(m->type)), 52, 6, getWidth() - 60, 16, juce::Justification::centredLeft);
    g.drawHorizontalLine(46, 6.0f, (float) getWidth() - 6.0f);
}

void ModulePanel::resized()
{
    auto r = getLocalBounds().reduced(10, 0);
    auto top = r.removeFromTop(46);
    top.removeFromTop(22);
    hear.setBounds(top.removeFromRight(56).reduced(0, 1));
    top.removeFromLeft(42);
    nameEdit.setBounds(top.reduced(4, 0).withHeight(20));
    r.removeFromTop(8);
    for (auto& f : fields) {
        auto row = r.removeFromTop(24);
        f->edit->setBounds(row.removeFromRight(80).reduced(0, 2));
        f->label->setBounds(row);
    }
    if (normalize.isVisible()) normalize.setBounds(r.removeFromTop(24));
    if (loadSound.isVisible()) { r.removeFromTop(24); loadSound.setBounds(r.removeFromTop(26).removeFromLeft(140)); }
    if (hasWaves) {
        auto row = r.removeFromTop(44).reduced(0, 2);
        auto buttons = row.removeFromRight(56);
        timeline.setBounds(row.withTrimmedRight(6));
        auto b1 = buttons.removeFromTop(20);
        removeWave.setBounds(b1.removeFromRight(26));
        addWave.setBounds(b1.removeFromLeft(26));
        buttons.removeFromTop(2);
        saveWave.setBounds(buttons.removeFromTop(18));
    }
    r.removeFromTop(6);
    // The editor, with its tool palette (two icons to a row) on the left.
    auto area = r.removeFromTop(juce::jmax(0, juce::jmin(r.getHeight() - 10, hasTools ? juce::jmax(220, tools.rows() * 30) : 220)));
    if (hasTools) {
        const int cell = juce::jmin(32, juce::jmin(area.getHeight() / juce::jmax(1, tools.rows()), area.getWidth() / (tools.columns + 4)));
        tools.setBounds(area.removeFromLeft(cell * tools.columns).withHeight(cell * tools.rows()));
        area.removeFromLeft(6);
    }
    if (hasEnvelope) envelope.setBounds(area);
    if (hasTable) table.setBounds(area);
    if (hasHarm) harmEditor.setBounds(area);
    if (soundInfo.isVisible()) soundInfo.setBounds(area.withHeight(juce::jmin(area.getHeight(), 120)));
}

void ModulePanel::refreshWave()
{
    auto* mm = module();
    if (!mm || wave >= (int) mm->waveforms.size()) return;
    const auto& wf = mm->waveforms[(size_t) wave];
    table.values.assign(wf.samples.begin(), wf.samples.begin() + 256);
    table.repaint();
    for (int k = 0; k < 64; ++k) harmEditor.levels[(size_t) k] = harm[(size_t) k + 1].level;
    harmEditor.repaint();
    if (wave < (int) timeline.waves.size()) timeline.waves[(size_t) wave].name = wf.name;
    timeline.repaint();
    edited();
}
