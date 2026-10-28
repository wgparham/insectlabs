#include "PatchView.h"
#include "ClassicLook.h"

#include <algorithm>
#include <map>

using namespace turbosynth;

int PatchView::iconFor(const Module& m)
{
    if (classic::hasIcon(m.kind)) return m.kind;
    switch (m.type) {
    case kOscillator: return 2000; case kSample: return 2001; case kNoise: return 2002; case kAmpEnv: return 3000;
    case kFilter: return 3001; case kSpectralInverter: return 3002; case kModulator: return 3003; case kWaveshaper: return 3004;
    case kMixer: return 3005; case kDelay: return 3006; case kPitchShifter: return 3007; case kStretchExpand: return 3008;
    case kResonator: return 3009; case kStretchCompress: return 3010; case kPitchEnv: return 3011; case kDiffuser: return 3012;
    case kOutput: return 7000; case kOutputR: return 7002;
    default: return 1002;
    }
}

std::vector<PatchView::PaletteItem> PatchView::palette() const
{
    return {
        { 1000, -1, "Arrow: select and move" }, { 1005, -2, "Patch cord: drag from a module to another" }, { 1007, -3, "Eraser: remove a module or cord" },
        { 2000, kOscillator, "Oscillator" }, { 2001, kSample, "Sample" }, { 2002, kNoise, "Noise" },
        { 3000, kAmpEnv, "Amplitude Envelope" }, { 3001, kFilter, "Filter" }, { 3002, kSpectralInverter, "Spectral Inverter" },
        { 3011, kPitchEnv, "Pitch Envelope" }, { 3003, kModulator, "Modulator" }, { 3004, kWaveshaper, "Waveshaper" },
        { 3005, kMixer, "Mixer" }, { 3006, kDelay, "Delay" }, { 3009, kResonator, "Resonator" },
        { 3007, kPitchShifter, "Pitch Shifter" }, { 3008, kStretchExpand, "Stretcher (expand)" },
        { 3010, kStretchCompress, "Stretcher (compress)" }, { 3012, kDiffuser, "Diffuser" },
        { 7000, kOutput, "Output (or Output L)" }, { 7002, kOutputR, "Output R" },
    };
}

juce::Rectangle<float> PatchView::paletteCell(int i) const
{
    // Two columns, tools first, as in the original palette.
    const auto a = paletteArea();
    const float w = (float) (a.getWidth() - 1) / 2.0f, h = 38.0f;
    return { (float) a.getX() + w * (float) (i % 2), (float) a.getY() + h * (float) (i / 2), w, h };
}

juce::Point<float> PatchView::toPatch(juce::Point<float> p) const { return (p - origin) / scale; }

juce::Rectangle<float> PatchView::iconBounds(int index) const
{
    const auto& m = patch->modules[(size_t) index - 1];
    const float px = 32.0f * std::floor(juce::jmax(1.0f, scale));
    return juce::Rectangle<float>(px, px).withCentre(origin + juce::Point<float>((float) m.rect[1] + 16.0f, (float) m.rect[0] + 16.0f) * scale);
}

juce::Rectangle<float> PatchView::labelBounds(int index) const
{
    const auto r = iconBounds(index);
    return { r.getCentreX() - 60.0f, r.getBottom() + 1.0f, 120.0f, 13.0f };
}

int PatchView::moduleAt(juce::Point<float> p) const
{
    if (!patch) return 0;
    for (int i = (int) patch->modules.size(); i >= 1; --i)
        if (iconBounds(i).contains(p) || labelBounds(i).reduced(30.0f, 0.0f).contains(p)) return i;
    return 0;
}

int PatchView::cordAt(juce::Point<float> p) const
{
    if (!patch) return -1;
    for (int i = (int) patch->connections.size() - 1; i >= 0; --i) {
        const auto& c = patch->connections[(size_t) i];
        if (c.from < 1 || c.to < 1 || c.from > (int) patch->modules.size() || c.to > (int) patch->modules.size()) continue;
        juce::Line<float> l(iconBounds(c.from).getCentre(), iconBounds(c.to).getCentre());
        juce::Point<float> nearest;
        if (l.getDistanceFromPoint(p, nearest) < 5.0f) return i;
    }
    return -1;
}

void PatchView::paint(juce::Graphics& g)
{
    const auto& t = classic::theme();
    g.fillAll(t.paper);

    // Tool palette.
    const auto items = palette();
    for (int i = 0; i < (int) items.size(); ++i) {
        const auto cell = paletteCell(i);
        const bool on = (items[(size_t) i].type == -1 && tool == Tool::Arrow) || (items[(size_t) i].type == -2 && tool == Tool::Cord)
                     || (items[(size_t) i].type == -3 && tool == Tool::Eraser) || (tool == Tool::Place && items[(size_t) i].type == placeType);
        if (on) { g.setColour(t.ink); g.fillRect(cell); }
        classic::drawIcon(g, items[(size_t) i].icon, cell.reduced(2.0f), on);
        g.setColour(t.ink);
        g.drawRect(cell, 0.5f);
    }
    g.setColour(t.ink);
    g.drawVerticalLine(paletteArea().getRight() - 1, 0.0f, (float) getHeight());
    if (!patch) return;

    fit();
    g.reduceClipRegion(getLocalBounds().withTrimmedLeft(kPaletteWidth));
    // Patch cords.
    for (const auto& c : patch->connections) {
        if (c.from < 1 || c.to < 1 || c.from > (int) patch->modules.size() || c.to > (int) patch->modules.size()) continue;
        const auto a = iconBounds(c.from).getCentre(), b = iconBounds(c.to).getCentre();
        juce::Line<float> line(a, b);
        const float cut = iconBounds(c.to).getWidth() * 0.62f;
        g.setColour(t.ink);
        g.drawArrow({ a, line.getPointAlongLine(juce::jmax(0.0f, line.getLength() - cut)) }, 1.0f, 8.0f, 8.0f);
    }
    if (connecting && dragging) {
        g.setColour(t.ink);
        const float dash[] = { 3.0f, 3.0f };
        g.drawDashedLine({ iconBounds(dragging).getCentre(), dragNow }, dash, 2, 1.0f);
    }
    // Modules: icon plus name, inverted when selected (as in the Finder).
    for (int i = 1; i <= (int) patch->modules.size(); ++i) {
        const auto& m = patch->modules[(size_t) i - 1];
        const bool sel = i == selected;
        classic::drawIcon(g, iconFor(m), iconBounds(i), sel);
        const auto font = classic::smallFont(10.0f);
        const int tw = juce::GlyphArrangement::getStringWidthInt(font, m.name) + 4;
        const auto lb = labelBounds(i).withSizeKeepingCentre((float) juce::jmin(tw, 120), 13.0f);
        g.setColour(sel ? t.ink : t.paper);
        g.fillRect(lb);
        g.setColour(sel ? t.paper : t.ink);
        g.setFont(font);
        g.drawText(m.name, lb, juce::Justification::centred, true);
    }
}

void PatchView::mouseDown(const juce::MouseEvent& e)
{
    if (paletteArea().contains(e.getPosition())) {
        const auto items = palette();
        for (int i = 0; i < (int) items.size(); ++i)
            if (paletteCell(i).contains(e.position)) {
                const int type = items[(size_t) i].type;
                tool = type == -1 ? Tool::Arrow : type == -2 ? Tool::Cord : type == -3 ? Tool::Eraser : Tool::Place;
                placeType = type;
                setMouseCursor(tool == Tool::Arrow ? juce::MouseCursor::NormalCursor : juce::MouseCursor::CrosshairCursor);
            }
        repaint();
        return;
    }
    if (!patch) return;
    const int hit = moduleAt(e.position);
    if (e.mods.isPopupMenu()) {
        if (hit) { selected = hit; if (onSelect) onSelect(hit); showModuleMenu(hit); }
        repaint();
        return;
    }
    if (tool == Tool::Place) {
        place(placeType, e.position);
        tool = Tool::Arrow;
        setMouseCursor(juce::MouseCursor::NormalCursor);
        return;
    }
    if (tool == Tool::Eraser) {
        if (hit) { removeModule(*patch, hit); selected = 0; if (onSelect) onSelect(0); if (onChanged) onChanged(); }
        else if (const int c = cordAt(e.position); c >= 0) { patch->connections.erase(patch->connections.begin() + c); if (onChanged) onChanged(); }
        repaint();
        return;
    }
    selected = hit;
    if (onSelect) onSelect(hit);
    dragging = hit;
    connecting = hit && (tool == Tool::Cord || e.mods.isShiftDown());
    dragStart = dragNow = e.position;
    if (hit) rectAtStart = patch->modules[(size_t) hit - 1].rect;
    repaint();
}

void PatchView::mouseDrag(const juce::MouseEvent& e)
{
    if (!patch || !dragging) return;
    dragNow = e.position;
    if (!connecting) {
        const auto d = (e.position - dragStart) / scale;
        auto& r = patch->modules[(size_t) dragging - 1].rect;
        r[0] = (int16_t) (rectAtStart[0] + d.y); r[2] = (int16_t) (rectAtStart[2] + d.y);
        r[1] = (int16_t) (rectAtStart[1] + d.x); r[3] = (int16_t) (rectAtStart[3] + d.x);
    }
    repaint();
}

void PatchView::mouseUp(const juce::MouseEvent& e)
{
    if (!patch || !dragging) return;
    if (connecting) {
        const int to = moduleAt(e.position);
        if (to && to != dragging) { patch->connections.push_back({ (int16_t) dragging, (int16_t) to }); if (onChanged) onChanged(); }
    } else if (e.position != dragStart && onChanged) {
        onChanged();
    }
    dragging = 0;
    connecting = false;
    repaint();
}

void PatchView::mouseDoubleClick(const juce::MouseEvent& e)
{
    if (tool != Tool::Arrow) return;
    if (const int hit = moduleAt(e.position); hit && onPreview) onPreview(hit);
}

void PatchView::showModuleMenu(int index)
{
    juce::PopupMenu menu;
    menu.addItem(1, "Hear This Module");
    menu.addSeparator();
    menu.addItem(2, "Disconnect Inputs");
    menu.addItem(3, "Disconnect Outputs");
    menu.addItem(4, "Clear Module");
    menu.showMenuAsync(juce::PopupMenu::Options().withTargetComponent(this), [this, index](int r) {
        if (!patch || r == 0) return;
        auto& cs = patch->connections;
        if (r == 1) { if (onPreview) onPreview(index); return; }
        if (r == 2) cs.erase(std::remove_if(cs.begin(), cs.end(), [index](const Connection& c) { return c.to == index; }), cs.end());
        if (r == 3) cs.erase(std::remove_if(cs.begin(), cs.end(), [index](const Connection& c) { return c.from == index; }), cs.end());
        if (r == 4) { removeModule(*patch, index); selected = 0; if (onSelect) onSelect(0); }
        if (onChanged) onChanged();
        repaint();
    });
}

void PatchView::place(int type, juce::Point<float> at)
{
    if (!patch) return;
    int32_t sr = 44100, len = 44100;
    for (const auto& m : patch->modules) if (m.type == kOutput && m.sampleRate > 0) { sr = m.sampleRate; len = m.length > 0 ? m.length : sr; break; }
    auto m = makeModule(type, sr, len);
    const auto pos = toPatch(at);
    m.rect = { (int16_t) (pos.y - 16), (int16_t) (pos.x - 16), (int16_t) (pos.y + 16), (int16_t) (pos.x + 16) };
    int count = 1;
    for (const auto& o : patch->modules) if (o.type == m.type) ++count;
    if (m.type != kOutput && m.type != kOutputR) m.name += "-" + std::to_string(count);
    patch->modules.push_back(m);
    selected = (int) patch->modules.size();
    if (onSelect) onSelect(selected);
    if (onChanged) onChanged();
    repaint();
}

void PatchView::fit()
{
    if (!patch || dragging) return; // keep the view steady while dragging
    juce::Rectangle<float> all;
    bool first = true;
    for (const auto& m : patch->modules) {
        const juce::Rectangle<float> r((float) m.rect[1] - 40.0f, (float) m.rect[0], 112.0f, 48.0f); // icon plus label
        all = first ? r : all.getUnion(r);
        first = false;
    }
    if (first) return;
    const auto area = getLocalBounds().withTrimmedLeft(kPaletteWidth).reduced(16).toFloat();
    scale = juce::jlimit(0.5f, 1.5f, juce::jmin(area.getWidth() / all.getWidth(), area.getHeight() / all.getHeight()));
    // Keep the stored layout's own origin when it fits, as the original window did.
    const float ox = all.getX() * scale < 0.0f || all.getRight() * scale > area.getRight() - area.getX() ? area.getX() - all.getX() * scale : area.getX();
    const float oy = all.getY() * scale < 0.0f || all.getBottom() * scale > area.getBottom() - area.getY() ? area.getY() - all.getY() * scale : area.getY();
    origin = { ox, oy };
}
