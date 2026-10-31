#include "TableView.h"
#include "ClassicLook.h"
#include "TableOps.h"

#include <random>

std::vector<float> TableView::clipboard;

TableView::TableView(int tableId) : id(tableId)
{
    const auto& spec = pulsar::tableSpec(id);
    viewMin = spec.viewMin;
    viewMax = spec.viewMax;
    setWantsKeyboardFocus(true);
}

juce::Rectangle<float> TableView::plotArea() const
{
    // The content area of the little window, below the range line.
    return getLocalBounds().toFloat().reduced(6.0f).withTrimmedTop((float) classic::kTitleHeight + 12.0f).withTrimmedBottom(1.0f);
}

void TableView::paint(juce::Graphics& g)
{
    // Each table is a small Classic window; the selected one has the pinstriped
    // (active) title bar, as the front window does.
    const auto& th = classic::theme();
    const auto r = getLocalBounds().withTrimmedRight(1).withTrimmedBottom(1);
    const auto inside = classic::drawWindow(g, r, pulsar::tableName(id), selected);
    g.setColour(th.ink);
    g.setFont(classic::smallFont(9.0f));
    g.drawText(juce::String(viewMin, 1) + ".." + juce::String(viewMax, 1), inside.reduced(5, 0).withHeight(13),
               juce::Justification::centredRight);

    const auto plot = plotArea();
    for (int i = 1; i < 8; ++i) {
        const int x = (int) (plot.getX() + plot.getWidth() * i / 8.0f);
        for (int y = (int) plot.getY(); y < (int) plot.getBottom(); y += 3) g.fillRect(x, y, 1, 1);
    }
    if (viewMin < 0.0f && viewMax > 0.0f) {
        const float y0 = plot.getBottom() - plot.getHeight() * (0.0f - viewMin) / (viewMax - viewMin);
        for (int x = (int) plot.getX(); x < (int) plot.getRight(); x += 2) g.fillRect(x, (int) y0, 1, 1);
    }

    auto* t = table ? table() : nullptr;
    if (t == nullptr || t->empty()) return;
    juce::Path path;
    const int w = juce::jmax(2, (int) plot.getWidth());
    for (int x = 0; x < w; ++x) {
        const size_t i = (size_t) ((double) x / (w - 1) * (t->size() - 1));
        const float v = juce::jlimit(viewMin, viewMax, (*t)[i]);
        const float y = plot.getBottom() - plot.getHeight() * (v - viewMin) / (viewMax - viewMin);
        if (x == 0) path.startNewSubPath(plot.getX() + x, y); else path.lineTo(plot.getX() + x, y);
    }
    g.setColour(th.ink);
    g.strokePath(path, juce::PathStrokeType(1.0f));

    // Play position, for the tables the control oscillators read.
    const double pos = position ? position() : -1.0;
    if (pos >= 0.0 && id != pulsar::Pulsaret && id != pulsar::PulseEnv)
        g.drawVerticalLine((int) (plot.getX() + plot.getWidth() * (float) pos), plot.getY(), plot.getBottom());
}

void TableView::mouseDown(const juce::MouseEvent& e)
{
    if (onSelected) onSelected(*this);
    grabKeyboardFocus();
    if (e.mods.isPopupMenu()) { showMenu(); return; }
    lastIndex = -1;
    drawTo(e.position);
}

void TableView::mouseDrag(const juce::MouseEvent& e)
{
    if (!e.mods.isPopupMenu()) drawTo(e.position);
}

void TableView::mouseUp(const juce::MouseEvent& e)
{
    if (!e.mods.isPopupMenu() && onEdited) onEdited(true);
    lastIndex = -1;
}

void TableView::drawTo(juce::Point<float> p)
{
    auto* t = table ? table() : nullptr;
    if (t == nullptr || t->empty()) return;
    const auto plot = plotArea();
    const int n = (int) t->size();
    const int index = juce::jlimit(0, n - 1, (int) ((p.x - plot.getX()) / plot.getWidth() * (n - 1) + 0.5f));
    const float v = juce::jlimit(viewMin, viewMax, viewMin + (plot.getBottom() - p.y) / plot.getHeight() * (viewMax - viewMin));
    if (lastIndex < 0) (*t)[(size_t) index] = v;
    else {
        // Fill every point between the previous and current mouse positions.
        const int a = juce::jmin(lastIndex, index), b = juce::jmax(lastIndex, index);
        for (int i = a; i <= b; ++i) {
            const float f = b == a ? 1.0f : (float) (i - lastIndex) / (float) (index - lastIndex);
            (*t)[(size_t) i] = lastValue + (v - lastValue) * f;
        }
    }
    lastIndex = index;
    lastValue = v;
    repaint();
    if (onEdited) onEdited(false);
}

void TableView::apply(const std::function<void(std::vector<float>&)>& op)
{
    auto* t = table ? table() : nullptr;
    if (t == nullptr) return;
    op(*t);
    repaint();
    if (onEdited) onEdited(true);
}

bool TableView::keyPressed(const juce::KeyPress& key)
{
    // Shortcuts from the original Alter menu.
    using namespace pulsar::ops;
    const auto c = key.getTextCharacter();
    if (c == '0') { apply([](auto& t) { zero(t); }); return true; }
    if (c == 'n') { apply([](auto& t) { normalize(t); }); return true; }
    if (c == 'r') { apply([](auto& t) { reverse(t); }); return true; }
    if (c == 'i') { apply([](auto& t) { invert(t); }); return true; }
    if (c == 'm' && !clipboard.empty()) {
        apply([](auto& t) { for (size_t i = 0; i < t.size() && i < clipboard.size(); ++i) t[i] += clipboard[i]; });
        return true;
    }
    if (c == 'M' && !clipboard.empty()) {
        apply([](auto& t) { for (size_t i = 0; i < t.size() && i < clipboard.size(); ++i) t[i] *= clipboard[i]; });
        return true;
    }
    return false;
}

void TableView::askNumbers(const juce::String& title, const juce::StringArray& labels, const juce::StringArray& defaults,
                           std::function<void(const juce::StringArray&)> done)
{
    auto* w = new juce::AlertWindow(title, {}, juce::MessageBoxIconType::NoIcon, this);
    for (int i = 0; i < labels.size(); ++i) w->addTextEditor("f" + juce::String(i), defaults[i], labels[i]);
    w->addButton("OK", 1, juce::KeyPress(juce::KeyPress::returnKey));
    w->addButton("Cancel", 0, juce::KeyPress(juce::KeyPress::escapeKey));
    w->enterModalState(true, juce::ModalCallbackFunction::create([w, labels, done](int result) {
        if (result == 1) {
            juce::StringArray values;
            for (int i = 0; i < labels.size(); ++i) values.add(w->getTextEditorContents("f" + juce::String(i)));
            done(values);
        }
    }), true);
}

void TableView::showMenu()
{
    using namespace pulsar::ops;
    juce::PopupMenu alter, fill, file, menu;
    alter.addItem("Zero  (0)", [this] { apply([](auto& t) { zero(t); }); });
    alter.addItem("Normalize  (n)", [this] { apply([](auto& t) { normalize(t); }); });
    alter.addItem("Reverse  (r)", [this] { apply([](auto& t) { reverse(t); }); });
    alter.addItem("Invert  (i)", [this] { apply([](auto& t) { invert(t); }); });
    alter.addItem("Absolute Value", [this] { apply([](auto& t) { absolute(t); }); });
    alter.addItem("Square", [this] { apply([](auto& t) { square(t); }); });
    alter.addItem("Cube", [this] { apply([](auto& t) { cube(t); }); });
    alter.addItem("Distort", [this] { apply([](auto& t) { distort(t); }); });
    // The original greys these out in table windows (they act on a SignalView selection).
    alter.addItem("Fade In", false, false, [] {});
    alter.addItem("Fade Out", false, false, [] {});
    alter.addItem("Clip Negative", [this] { apply([](auto& t) { clipNegative(t); }); });
    alter.addItem("Smooth Table", [this] { apply([](auto& t) { smooth(t); }); });
    alter.addItem("Scale...", [this] {
        askNumbers("Scale", { "Factor (blank to scale to limits)", "Minimum", "Maximum" }, { "1", "", "" }, [this](const juce::StringArray& v) {
            if (v[0].isNotEmpty()) apply([f = v[0].getFloatValue()](auto& t) { scale(t, f); });
            else apply([lo = v[1].getFloatValue(), hi = v[2].getFloatValue()](auto& t) { scaleTo(t, lo, hi); });
        });
    });
    alter.addSeparator();
    alter.addItem("Hanning Window", [this] { apply([](auto& t) { hanningWindow(t); }); });
    alter.addItem("Parzen Window", [this] { apply([](auto& t) { parzenWindow(t); }); });
    alter.addItem("Welch Window", [this] { apply([](auto& t) { welchWindow(t); }); });
    alter.addItem("Comb Table...", [this] {
        askNumbers("Comb Table", { "Tooth spacing", "Odd comb (1) or even (0)" }, { "2", "0" }, [this](const juce::StringArray& v) {
            apply([s = v[0].getFloatValue(), odd = v[1].getIntValue() != 0](auto& t) { comb(t, s, odd); });
        });
    });

    fill.addItem("Constant...", [this] {
        askNumbers("Fill With Constant", { "Value" }, { juce::String(pulsar::tableSpec(id).defaultValue) }, [this](const juce::StringArray& v) {
            apply([x = v[0].getFloatValue()](auto& t) { fillConstant(t, x); });
        });
    });
    fill.addItem("Line...", [this] {
        askNumbers("Fill With Line", { "Start", "End" }, { juce::String(viewMin), juce::String(viewMax) }, [this](const juce::StringArray& v) {
            apply([a = v[0].getFloatValue(), b = v[1].getFloatValue()](auto& t) { fillLine(t, a, b); });
        });
    });
    fill.addItem("Fourier...", [this] {
        askNumbers("Fourier", { "Harmonic amplitudes", "Phases (radians)" }, { "1 0.5 0.33 0.25", "" }, [this](const juce::StringArray& v) {
            std::vector<float> a, p;
            for (auto& s : juce::StringArray::fromTokens(v[0], " ,", "")) if (s.isNotEmpty()) a.push_back(s.getFloatValue());
            for (auto& s : juce::StringArray::fromTokens(v[1], " ,", "")) if (s.isNotEmpty()) p.push_back(s.getFloatValue());
            apply([a, p](auto& t) { fourierFill(t, a, p); });
        });
    });
    fill.addItem("Chebyshev...", [this] {
        askNumbers("Chebyshev", { "Polynomial amplitudes" }, { "1 0 0.5" }, [this](const juce::StringArray& v) {
            std::vector<float> a;
            for (auto& s : juce::StringArray::fromTokens(v[0], " ,", "")) if (s.isNotEmpty()) a.push_back(s.getFloatValue());
            apply([a](auto& t) { chebyshevFill(t, a); });
        });
    });
    fill.addItem("Phase Modulation...", [this] {
        askNumbers("Phase Modulation", { "Carrier", "Modulator", "Index" }, { "1", "2", "3" }, [this](const juce::StringArray& v) {
            apply([c = v[0].getFloatValue(), m = v[1].getFloatValue(), i = v[2].getFloatValue()](auto& t) { phaseModFill(t, c, m, i); });
        });
    });
    fill.addItem("Buzz...", [this] {
        askNumbers("Buzz", { "Lowest harmonic", "Number of harmonics", "Series coefficient", "Bilateral spectrum (1 or 0)" }, { "1", "8", "0.9", "0" },
                   [this](const juce::StringArray& v) {
            apply([lo = v[0].getIntValue(), n = v[1].getIntValue(), c = v[2].getFloatValue(), bi = v[3].getIntValue() != 0](auto& t) { buzzFill(t, lo, n, c, bi); });
        });
    });
    juce::PopupMenu grain;
    auto grainItem = [this, &grain](const char* name, GrainShape shape) {
        grain.addItem(name, [this, shape] {
            askNumbers("Grain Envelope", { "Grain duration (%)" }, { "50" }, [this, shape](const juce::StringArray& v) {
                apply([shape, d = v[0].getFloatValue()](auto& t) { grainEnvelopeFill(t, shape, d); });
            });
        });
    };
    grainItem("Hanning", GrainShape::Hanning);
    grainItem("Welch", GrainShape::Welch);
    grainItem("Parzen", GrainShape::Parzen);
    fill.addSubMenu("Grain Envelope", grain);
    fill.addItem("Noise", [this] { apply([](auto& t) { std::mt19937 r{ (uint32_t) juce::Time::getMillisecondCounter() }; noiseFill(t, r, false); }); });

    // The original refuses table loading and saving while the synth runs.
    const bool running = position && position() >= 0.0;
    file.addItem("Load Table (.wt)...", !running, false, [this] {
        chooser = std::make_unique<juce::FileChooser>("Load table", juce::File(), "*.wt");
        chooser->launchAsync(juce::FileBrowserComponent::openMode | juce::FileBrowserComponent::canSelectFiles, [this](const juce::FileChooser& fc) {
            if (fc.getResult() == juce::File()) return;
            std::vector<float> loaded;
            if (pulsar::loadWavetable(fc.getResult().getFullPathName().toStdString(), loaded))
                apply([loaded](auto& t) { t = loaded; });
        });
    });
    file.addItem("Save Table (.wt)...", !running, false, [this] {
        chooser = std::make_unique<juce::FileChooser>("Save table", juce::File(), "*.wt");
        chooser->launchAsync(juce::FileBrowserComponent::saveMode | juce::FileBrowserComponent::warnAboutOverwriting, [this](const juce::FileChooser& fc) {
            if (fc.getResult() == juce::File() || table == nullptr) return;
            pulsar::saveWavetable(fc.getResult().withFileExtension("wt").getFullPathName().toStdString(), *table());
        });
    });

    menu.addSectionHeader(pulsar::tableName(id));
    menu.addSubMenu("Alter", alter);
    menu.addSubMenu("Fill", fill);
    menu.addSeparator();
    menu.addItem("Copy", [this] { if (table) clipboard = *table(); });
    menu.addItem("Paste", !clipboard.empty(), false, [this] { apply([](auto& t) { t = clipboard; t.resize(pulsar::kTableSize, 0.0f); }); });
    menu.addItem("Add Clipboard  (m)", !clipboard.empty(), false, [this] { keyPressed(juce::KeyPress('m')); });
    menu.addItem("Multiply Clipboard  (M)", !clipboard.empty(), false, [this] { keyPressed(juce::KeyPress('M')); });
    menu.addSeparator();
    menu.addItem("Set View Limits...", [this] {
        askNumbers("View Limits", { "Minimum", "Maximum" }, { juce::String(viewMin), juce::String(viewMax) }, [this](const juce::StringArray& v) {
            const float lo = v[0].getFloatValue(), hi = v[1].getFloatValue();
            if (hi > lo) { viewMin = lo; viewMax = hi; repaint(); }
        });
    });
    menu.addSubMenu("File", file);
    menu.showMenuAsync(juce::PopupMenu::Options().withTargetComponent(this));
}
