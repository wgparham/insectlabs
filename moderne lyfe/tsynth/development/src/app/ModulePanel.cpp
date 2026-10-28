#include "ModulePanel.h"
#include "ClassicLook.h"
#include "PatchView.h"

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
    hasEnvelope = hasTable = hasWaves = false;
    removeAllChildren();
    auto* m = module();
    if (!m) { repaint(); return; }

    nameEdit.setFont(classic::uiFont(12.0f));
    nameEdit.setText(m->name, false);
    nameEdit.applyColourToAllText(classic::theme().ink); // text keeps the colour it was typed in; follow the theme
    nameEdit.onReturnKey = nameEdit.onFocusLost = [this] { if (auto* mm = module()) { mm->name = nameEdit.getText().toStdString(); edited(); } };
    addAndMakeVisible(nameEdit);
    hear.onClick = [this] { if (onPreview) onPreview(index); };
    addAndMakeVisible(hear);

    const bool isOutput = m->type == kOutput || m->type == kOutputR;
    if (!isOutput) {
        addField("Sample rate (Hz)", 1000, 96000, [m] { return m->sampleRate; }, [m](int v) { m->sampleRate = v; });
        addField("Length (samples)", 1, 2147483, [m] { return m->length; }, [m](int v) { m->length = v; });
        if (m->type != kOscillator && m->type != kSample && m->type != kNoise) {
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
        hasWaves = hasTable = true;
        wave = juce::jlimit(0, juce::jmax(0, (int) m->waveforms.size() - 1), wave);
        waveSelect.clear(juce::dontSendNotification);
        for (int k = 0; k < (int) m->waveforms.size(); ++k)
            waveSelect.addItem(juce::String(k + 1) + ": " + m->waveforms[(size_t) k].name + " @ " + juce::String(m->waveforms[(size_t) k].timeMs) + " ms", k + 1);
        waveSelect.setSelectedId(wave + 1, juce::dontSendNotification);
        waveSelect.onChange = [this] { wave = waveSelect.getSelectedId() - 1; show(patch, index); };
        addAndMakeVisible(waveSelect);
        if (!m->waveforms.empty()) {
            addField("Wave time (ms)", 0, 32767, [this, m] { return (int) m->waveforms[(size_t) wave].timeMs; },
                     [this, m](int v) { m->waveforms[(size_t) wave].timeMs = (int16_t) v; });
            table.values.assign(m->waveforms[(size_t) wave].samples.begin(), m->waveforms[(size_t) wave].samples.begin() + 256);
            table.onEdited = [this] {
                if (auto* mm = module(); mm && wave < (int) mm->waveforms.size())
                    std::copy(table.values.begin(), table.values.end(), mm->waveforms[(size_t) wave].samples.begin());
                edited();
            };
            addAndMakeVisible(table);
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
        loadSound.onClick = [this] { if (onLoadSound) onLoadSound(index); };
        addAndMakeVisible(loadSound);
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
                for (const auto& pt : envelope.points) mm->points.push_back({ (int16_t) pt.level, (int16_t) pt.timeMs, {} });
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
        table.values.assign(m->shaper.begin(), m->shaper.end());
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
    resized();
    repaint();
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
    g.drawText(moduleTypeName(m->type), 52, 6, getWidth() - 60, 16, juce::Justification::centredLeft);
    if (m->type == kSample)
        g.drawText(m->embedded.empty() ? "Sound: from file \"" + juce::String(m->name).trim() + "\"" : "Sound: stored in the document",
                   10, 120, getWidth() - 20, 16, juce::Justification::centredLeft);
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
        auto row = r.removeFromTop(26).reduced(0, 2);
        removeWave.setBounds(row.removeFromRight(26));
        addWave.setBounds(row.removeFromRight(26));
        waveSelect.setBounds(row.withTrimmedRight(6));
    }
    r.removeFromTop(6);
    if (hasEnvelope) envelope.setBounds(r.removeFromTop(juce::jmin(220, r.getHeight() - 10)));
    if (hasTable) table.setBounds(r.removeFromTop(juce::jmin(220, r.getHeight() - 10)));
}
