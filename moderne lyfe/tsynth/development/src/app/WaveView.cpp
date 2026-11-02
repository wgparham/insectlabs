#include "WaveView.h"
#include "ClassicLook.h"

#include <algorithm>
#include <cmath>

void WaveView::setSamples(const std::vector<int16_t>& left, const std::vector<int16_t>& right, int sampleRate, const juce::String& text)
{
    l = left; r = right; rate = sampleRate > 0 ? sampleRate : 44100; caption = text; playPos = -1.0;
    cache = {};
    repaint();
}

void WaveView::buildCache()
{
    // Everything except the play cursor, drawn once per sound, size and theme.
    const auto& t = classic::theme();
    cache = juce::Image(juce::Image::ARGB, juce::jmax(1, getWidth()), juce::jmax(1, getHeight()), true);
    juce::Graphics g(cache);
    g.fillAll(t.paper);
    auto area = getLocalBounds().reduced(4);
    g.setColour(t.ink);
    g.setFont(classic::smallFont(10.0f));
    const auto info = area.removeFromTop(13);
    g.drawText(caption, info, juce::Justification::centredLeft);
    if (!l.empty())
        g.drawText(juce::String((double) l.size() / rate, 3) + " s   " + juce::String(rate) + " Hz   " + juce::String((int) l.size()) + " samples",
                   info, juce::Justification::centredRight);
    g.drawRect(area, 1);
    if (l.empty()) return;
    const int channels = r.empty() ? 1 : 2;
    for (int c = 0; c < channels; ++c) {
        const auto& v = c == 0 ? l : r;
        auto lane = area.reduced(1);
        lane = lane.withHeight(lane.getHeight() / channels).translated(0, c * (lane.getHeight() / channels));
        g.setColour(t.gray);
        g.drawHorizontalLine(lane.getCentreY(), (float) lane.getX(), (float) lane.getRight());
        g.setColour(t.trace);
        const double per = (double) v.size() / juce::jmax(1, lane.getWidth());
        for (int x = 0; x < lane.getWidth(); ++x) {
            const size_t a = (size_t) (x * per), b = juce::jmin(v.size(), (size_t) ((x + 1) * per) + 1);
            int lo = 32767, hi = -32768;
            for (size_t i = a; i < b; ++i) { lo = juce::jmin(lo, (int) v[i]); hi = juce::jmax(hi, (int) v[i]); }
            const float y1 = (float) lane.getCentreY() - (float) hi / 32768.0f * (float) lane.getHeight() * 0.5f;
            const float y2 = (float) lane.getCentreY() - (float) lo / 32768.0f * (float) lane.getHeight() * 0.5f;
            g.drawVerticalLine(lane.getX() + x, y1, juce::jmax(y1 + 1.0f, y2));
        }
    }
}

void WaveView::paint(juce::Graphics& g)
{
    if (!cache.isValid() || cache.getWidth() != getWidth() || cache.getHeight() != getHeight()) buildCache();
    g.drawImageAt(cache, 0, 0);
    if (playPos >= 0.0 && !l.empty()) {
        const auto area = getLocalBounds().reduced(4).withTrimmedTop(13);
        const float x = (float) area.getX() + (float) (playPos * rate / (double) l.size()) * (float) area.getWidth();
        g.setColour(classic::theme().ink);
        g.fillRect(x, (float) area.getY(), 2.0f, (float) area.getHeight());
    }
    if (!editable || l.empty()) return;
    const auto& t = classic::theme();
    const auto area = waveArea().toFloat();
    if (hasSelection()) { // inverted, as in the original
        const float x0 = xOf(selStart), x1 = juce::jmax(x0 + 1.0f, xOf(selEnd));
        g.saveState();
        g.reduceClipRegion(juce::Rectangle<float>(x0, area.getY(), x1 - x0, area.getHeight()).toNearestInt());
        g.setColour(t.ink.withAlpha(0.35f));
        g.fillRect(area);
        g.restoreState();
    }
    // Loop markers: dotted lines with the start (right-pointing) and end (left-pointing) flags.
    auto marker = [&](int32_t s, bool start) {
        if (s < 0) return;
        const float x = xOf(s);
        const float dash[] = { 2.0f, 2.0f };
        g.setColour(t.ink);
        g.drawDashedLine({ x, area.getY(), x, area.getBottom() }, dash, 2, 1.0f);
        juce::Path p;
        const float y = area.getBottom() - 10.0f, d = start ? 9.0f : -9.0f;
        p.addTriangle(x, y, x, y + 10.0f, x + d, y + 5.0f);
        g.setColour(t.paper); g.fillPath(p);
        g.setColour(t.ink); g.strokePath(p, juce::PathStrokeType(1.0f));
    };
    marker(loopStart, true);
    marker(loopEnd, false);
}

int32_t WaveView::sampleAt(float x) const
{
    const auto a = waveArea().toFloat();
    return juce::jlimit<int32_t>(0, (int32_t) l.size(), (int32_t) std::lround((x - a.getX()) / juce::jmax(1.0f, a.getWidth()) * (float) l.size()));
}

float WaveView::xOf(int32_t s) const
{
    const auto a = waveArea().toFloat();
    return a.getX() + a.getWidth() * (float) s / (float) juce::jmax<size_t>(1, l.size());
}

void WaveView::mouseDown(const juce::MouseEvent& e)
{
    dragging = 0;
    if (!editable || l.empty()) return;
    const int32_t s = sampleAt(e.position.x);
    if (tool == Tool::PlaceStart || tool == Tool::PlaceEnd) {
        (tool == Tool::PlaceStart ? loopStart : loopEnd) = s;
        dragging = tool == Tool::PlaceStart ? 1 : 2;
        tool = Tool::Select;
        repaint();
        return;
    }
    // Grab a marker near the pointer, else start a selection.
    const bool nearStart = loopStart >= 0 && std::abs(xOf(loopStart) - e.position.x) < 5.0f;
    const bool nearEnd = loopEnd >= 0 && std::abs(xOf(loopEnd) - e.position.x) < 5.0f;
    if (nearStart || nearEnd) { dragging = nearStart ? 1 : 2; return; }
    if (!selectable) return;
    dragging = 3;
    anchor = s;
    selStart = selEnd = s;
    repaint();
}

void WaveView::mouseDrag(const juce::MouseEvent& e)
{
    const int32_t s = sampleAt(e.position.x);
    if (dragging == 1) loopStart = s;
    else if (dragging == 2) loopEnd = s;
    else if (dragging == 3) { selStart = juce::jmin(anchor, s); selEnd = juce::jmax(anchor, s); }
    else return;
    repaint();
}

void WaveView::mouseUp(const juce::MouseEvent&)
{
    if (dragging == 1 || dragging == 2) {
        if (loopStart >= 0 && loopEnd >= 0 && loopEnd < loopStart) std::swap(loopStart, loopEnd);
        if (onLoopChanged) onLoopChanged();
    } else if (dragging == 3 && onSelectionChanged) onSelectionChanged();
    dragging = 0;
    repaint();
}

// ---------------------------------------------------------------------------
void TableEditor::paint(juce::Graphics& g)
{
    const auto& t = classic::theme();
    g.fillAll(t.paper);
    g.setColour(t.ink);
    g.drawRect(getLocalBounds(), 1);
    g.setColour(t.gray);
    g.drawHorizontalLine(getHeight() / 2, 1.0f, (float) getWidth() - 1);
    if (values.empty()) return;
    juce::Path p;
    for (size_t i = 0; i < values.size(); ++i) {
        const float x = 1.0f + (float) i / (float) (values.size() - 1) * ((float) getWidth() - 2.0f);
        const float y = (float) getHeight() * 0.5f - (float) values[i] / 32768.0f * ((float) getHeight() * 0.5f - 2.0f);
        if (i == 0) p.startNewSubPath(x, y); else p.lineTo(x, y);
    }
    g.setColour(t.ink);
    g.strokePath(p, juce::PathStrokeType(1.5f));
}

void TableEditor::draw(juce::Point<float> pos)
{
    if (values.empty()) return;
    const int i = juce::jlimit(0, (int) values.size() - 1, (int) std::round((pos.x - 1.0f) / ((float) getWidth() - 2.0f) * (float) (values.size() - 1)));
    const int v = juce::jlimit(-32768, 32767, (int) std::round(((float) getHeight() * 0.5f - pos.y) / ((float) getHeight() * 0.5f - 2.0f) * 32768.0f));
    if (erasing && eraseValue) {
        for (int k = juce::jmin(last < 0 ? i : last, i); k <= juce::jmax(last < 0 ? i : last, i); ++k) values[(size_t) k] = eraseValue(k);
    } else if (last < 0) values[(size_t) i] = (int16_t) v;
    else {
        const int a = juce::jmin(last, i), b = juce::jmax(last, i);
        for (int k = a; k <= b; ++k) {
            const float f = b == a ? 1.0f : (float) (k - last) / (float) (i - last);
            values[(size_t) k] = (int16_t) juce::jlimit(-32768, 32767, (int) std::round(lastValue + (v - lastValue) * f));
        }
    }
    last = i;
    lastValue = v;
    repaint();
}

// ---------------------------------------------------------------------------
namespace {
// CURS 1001, the original's eraser, hot spot (7, 8).
juce::MouseCursor eraserCursor()
{
    static const uint16_t data[16] = { 0x0000, 0x0000, 0x0000, 0x0000, 0x0111, 0x0223, 0x0447, 0x088F, 0x111E, 0x223C, 0x4478, 0xAAF0, 0xDDE0, 0xAAC0, 0xFF80, 0x0000 };
    static const uint16_t mask[16] = { 0x0000, 0x0000, 0x0000, 0x01FF, 0x03FF, 0x07FF, 0x0FFF, 0x1FFF, 0x3FFF, 0x7FFE, 0xFFFC, 0xFFF8, 0xFFF0, 0xFFE0, 0xFFC0, 0xFF80 };
    juce::Image im(juce::Image::ARGB, 16, 16, true);
    for (int y = 0; y < 16; ++y)
        for (int x = 0; x < 16; ++x)
            if (mask[y] & (0x8000 >> x))
                im.setPixelAt(x, y, data[y] & (0x8000 >> x) ? juce::Colours::black : juce::Colours::white);
    return juce::MouseCursor(im, 7, 8);
}

// A one-bit canvas for what QuickDraw draws with patterns and in XOR.
class Bits
{
public:
    Bits(juce::Image& im, juce::Colour inkIn, juce::Colour paperIn, int originX)
        : pixels(im, juce::Image::BitmapData::readWrite), ink(inkIn), paper(paperIn), origin(originX), w(im.getWidth()), h(im.getHeight()) {}
    void set(int x, int y, bool on = true) { x -= origin; if (x >= 0 && x < w && y >= 0 && y < h) pixels.setPixelColour(x, y, on ? ink : paper); }
    void invert(int x, int y)
    {
        x -= origin;
        if (x >= 0 && x < w && y >= 0 && y < h) pixels.setPixelColour(x, y, pixels.getPixelColour(x, y) == ink ? paper : ink);
    }
    template <class Plot>
    void line(int x0, int y0, int x1, int y1, Plot plot) const
    {
        if (juce::jmax(x0, x1) < origin || juce::jmin(x0, x1) >= origin + w) return;
        const int dx = std::abs(x1 - x0), dy = -std::abs(y1 - y0), sx = x0 < x1 ? 1 : -1, sy = y0 < y1 ? 1 : -1;
        int err = dx + dy;
        for (;;) {
            plot(x0, y0);
            if (x0 == x1 && y0 == y1) break;
            const int e2 = 2 * err;
            if (e2 >= dy) { err += dy; x0 += sx; }
            if (e2 <= dx) { err += dx; y0 += sy; }
        }
    }
private:
    juce::Image::BitmapData pixels;
    juce::Colour ink, paper;
    int origin, w, h;
};
bool gray(int x, int y) { return ((x + y) & 1) == 0; }                                // qd.gray
bool ltGray(int x, int y) { return (y & 1) == 0 ? (x & 3) == 0 : (x & 3) == 2; }      // qd.ltGray

// The point's rectangle (F40_00C2C), grown by `by`, as PtInRect tests it.
bool inPoint(const BreakpointEditor::Point& p, juce::Point<int> at, int by)
{
    const int h = p.timeMs, v = 100 - p.level;
    return at.x >= h - 1 - by && at.x < h + 2 + by && at.y >= v - 1 - by && at.y < v + 2 + by;
}
constexpr int kPlotHeight = 102, kRuler = 103, kScrollBar = 16;
}

BreakpointEditor::BreakpointEditor()
{
    scroll.setAutoHide(false);
    scroll.addListener(this);
    addAndMakeVisible(scroll);
}

void BreakpointEditor::refresh()
{
    setMouseCursor(erasing ? eraserCursor() : juce::MouseCursor::NormalCursor);
    resized();
    repaint();
}

void BreakpointEditor::resized()
{
    scroll.setBounds(1, getHeight() - kScrollBar - 1, juce::jmax(0, getWidth() - 2), kScrollBar);
    scroll.setRangeLimits(0.0, (double) juce::jmax(durationMs, getWidth() - 2));
    scroll.setCurrentRange(scroll.getCurrentRangeStart(), (double) juce::jmax(1, getWidth() - 2));
}

juce::Point<int> BreakpointEditor::viewPoint(juce::Point<float> pos) const
{
    return { (int) std::floor(pos.x) - 1 + (int) scroll.getCurrentRangeStart(), (int) std::floor(pos.y) - 1 };
}

// F40_01B72, after MacApp keeps the mouse in the view.
juce::Point<int> BreakpointEditor::constrain(juce::Point<int> p) const
{
    int h = juce::jlimit(0, durationMs, p.x);
    if (trackIndex == 0) h = 0;
    else {
        h = juce::jmax(h, prev.x + 1);
        h = juce::jmin(h, toEnd ? durationMs : next.x - 1);
    }
    return { h, juce::jlimit(0, 100, p.y) };
}

void BreakpointEditor::paint(juce::Graphics& g)
{
    const auto& t = classic::theme();
    g.fillAll(t.paper);
    const int left = (int) scroll.getCurrentRangeStart(), width = juce::jmax(1, getWidth() - 2);
    juce::Image im(juce::Image::ARGB, width, kPlotHeight, false);
    im.clear(im.getBounds(), t.paper);
    {
        Bits bits(im, t.ink, t.paper, left); // writes back when it goes out of scope
        const auto solid = [&bits](int x, int y) { bits.set(x, y); };
        if (pitchScale) // F40_01290: eleven lines ten pixels apart; the middle one is darker
            for (int k = 0; k <= 10; ++k)
                bits.line(left, k * 10, left + width, k * 10, [&bits, k](int x, int y) { if (k == 5 ? gray(x, y) : ltGray(x, y)) bits.set(x, y); });
        int h = 0, v = pitchScale ? 50 : 100;
        for (const auto& p : points) { bits.line(h, v, p.timeMs, 100 - p.level, solid); h = p.timeMs; v = 100 - p.level; }
        if (!points.empty()) bits.line(h, v, durationMs, v, solid);
        for (int k = 0; k < (int) points.size(); ++k) {
            const int ph = points[(size_t) k].timeMs, pv = 100 - points[(size_t) k].level;
            const bool erase = track == Track::Erase && k == trackIndex;
            for (int y = -1; y <= 1; ++y)
                for (int x = -1; x <= 1; ++x)
                    bits.set(ph + x, pv + y, (x != 0 || y != 0) && (!erase || gray(ph + x, pv + y)));
        }
        if (track == Track::Add || track == Track::Move) { // F40_01AD2: drawn in gray, in XOR
            const auto ghost = [&bits](int x, int y) { if (gray(x, y)) bits.invert(x, y); };
            bits.line(prev.x, prev.y, cursor.x, cursor.y, ghost);
            bits.line(cursor.x, cursor.y, toEnd ? durationMs + 1 : next.x, toEnd ? cursor.y : next.y, ghost);
        }
    }
    g.setImageResamplingQuality(juce::Graphics::lowResamplingQuality);
    g.drawImageAt(im, 1, 1);
    g.saveState();
    g.reduceClipRegion(1, 1, width, juce::jmax(0, getHeight() - kScrollBar - 3));
    g.setOrigin(1 - left, 1);
    g.setColour(t.ink);
    g.setFont(classic::smallFont(9.0f));
    if (pitchScale) {
        static const char* const marks[11] = { "+12", "+7", "+4", "+1", nullptr, nullptr, nullptr, "-1", "-4", "-7", "-12" };
        for (int k = 0; k <= 10; ++k)
            if (marks[k]) g.drawSingleLineText(marks[k], 0, k * 10 + 4);
    }
    // F23_00246: a tick every 50 ms and a label every 100 ms.
    for (int k = juce::jmax(0, left / 50 - 1); k * 50 <= left + width; ++k) {
        g.fillRect(k * 50, kRuler, 1, 3);
        if ((k & 1) == 0) g.drawSingleLineText(juce::String(k * 50 / 1000.0, 3) + " sec", k * 50, kRuler + 12);
    }
    if (showType) { // F40_01290: the mark goes beside the current type; any other value shows none
        constexpr int kBaseline = 140, kA = 37, kB = 55, kMark = 6;
        g.drawSingleLineText("Type:", 2, kBaseline);
        g.drawSingleLineText("A", kA, kBaseline);
        g.drawSingleLineText("B", kB, kBaseline);
        if (type == 1 || type == 2)
            g.fillEllipse((float) ((type == 1 ? kA : kB) - kMark), (float) kBaseline - 5.0f, 5.0f, 5.0f);
    }
    g.restoreState();
    g.setColour(t.ink);
    g.drawRect(getLocalBounds(), 1);
}

void BreakpointEditor::mouseDown(const juce::MouseEvent& e)
{
    track = Track::None;
    if (e.mods.isPopupMenu()) return;
    const auto at = viewPoint(e.position);
    // F40_01674: the Type comes first, with either tool.
    if (showType && at.x >= 30 && at.x < 70 && at.y >= 130 && at.y < 145) {
        type = (at.x - 30) / 20 + 1;
        repaint();
        if (onType) onType(type);
        return;
    }
    // F40_01506: a point's own rectangle first; then, from the left, the first point the mouse is
    // near or short of, which is the point a new one goes before.
    const int count = (int) points.size();
    int index = count;
    bool on = false;
    for (int k = 0; k < count && !on; ++k)
        if (inPoint(points[(size_t) k], at, 0)) { index = k; on = true; }
    for (int k = 0; k < count && !on; ++k) {
        if (inPoint(points[(size_t) k], at, 4)) { index = k; on = true; }
        else if (at.x < points[(size_t) k].timeMs - 5) { index = k; break; }
    }
    if (erasing) { if (on && index > 0) track = Track::Erase; } // the first point stays
    else if (on) track = Track::Move;
    else if (!spaceIsLow || !spaceIsLow()) track = Track::Add;
    if (track == Track::None) return;
    // F40_018D2: the neighbours the gray lines run to.
    trackIndex = index;
    const auto toView = [](const Point& p) { return juce::Point<int>(p.timeMs, 100 - p.level); };
    prev = count > 0 ? toView(points[(size_t) juce::jmax(0, index - 1)]) : at;
    const int following = track == Track::Add ? index : index + 1;
    toEnd = following >= count;
    next = toEnd ? juce::Point<int>(durationMs, 0) : toView(points[(size_t) following]);
    cursor = constrain(at);
    beginDragAutoRepeat(50);
    repaint();
}

void BreakpointEditor::mouseDrag(const juce::MouseEvent& e)
{
    if (track == Track::None) return;
    const int inner = getWidth() - 2;
    if (e.x < 1) scroll.setCurrentRangeStart(scroll.getCurrentRangeStart() + juce::jmax(-16, e.x - 1));
    else if (e.x > inner) scroll.setCurrentRangeStart(scroll.getCurrentRangeStart() + juce::jmin(16, e.x - inner));
    cursor = constrain(viewPoint(e.position));
    repaint();
}

void BreakpointEditor::mouseUp(const juce::MouseEvent& e)
{
    if (track == Track::None) return;
    cursor = constrain(viewPoint(e.position));
    const Point p { 100 - cursor.y, cursor.x };
    if (track == Track::Add) points.insert(points.begin() + trackIndex, p);
    else if (track == Track::Move) points[(size_t) trackIndex] = p;
    else points.erase(points.begin() + trackIndex);
    track = Track::None;
    repaint();
    if (onEdited) onEdited();
}

// ---------------------------------------------------------------------------
void ToolPalette::addPresetRow(std::initializer_list<int> icons, int first)
{
    while (items.size() % (size_t) columns) items.push_back({});
    const int n = (int) icons.size();
    int j = 0;
    for (int id : icons) { items.push_back({ id, { first + 2 * j, first + 2 * j + 1, first + 2 * n + 2 * j, first + 2 * n + 2 * j + 1 }, true }); ++j; }
}

void ToolPalette::addPresetIcon(int icon, int first)
{
    while (items.size() % (size_t) columns) items.push_back({});
    items.push_back({ icon, { first, first + 1, first + 2, first + 3 }, true });
    while (items.size() % (size_t) columns) items.push_back({});
}

void ToolPalette::addTools(std::initializer_list<int> icons, int first)
{
    for (int id : icons) items.push_back({ id, { first++, -1, -1, -1 }, false });
}

float ToolPalette::cell() const
{
    return juce::jmax(8.0f, juce::jmin((float) getWidth() / (float) columns, (float) getHeight() / (float) juce::jmax(1, rows())));
}

juce::Rectangle<float> ToolPalette::itemBounds(int i) const
{
    const float s = cell();
    return { s * (float) (i % columns), s * (float) (i / columns), s, s };
}

int ToolPalette::codeAt(juce::Point<float> p, juce::Rectangle<float>* where) const
{
    for (int i = 0; i < (int) items.size(); ++i) {
        const auto r = itemBounds(i);
        if (!items[(size_t) i].icon || !r.contains(p)) continue;
        if (!items[(size_t) i].quarters) { if (where) *where = r; return items[(size_t) i].codes[0]; }
        const int q = (p.x >= r.getCentreX() ? 1 : 0) + (p.y >= r.getCentreY() ? 2 : 0);
        if (where) *where = r.withSize(r.getWidth() / 2.0f, r.getHeight() / 2.0f)
                              .translated(q % 2 ? r.getWidth() / 2.0f : 0.0f, q / 2 ? r.getHeight() / 2.0f : 0.0f);
        return items[(size_t) i].codes[(size_t) q];
    }
    return -1;
}

void ToolPalette::paint(juce::Graphics& g)
{
    const auto& t = classic::theme();
    g.fillAll(t.paper);
    for (int i = 0; i < (int) items.size(); ++i) {
        const auto& it = items[(size_t) i];
        if (!it.icon) continue;
        const auto r = itemBounds(i);
        const bool on = !it.quarters && (std::find(active.begin(), active.end(), it.codes[0]) != active.end() || it.codes[0] == pressed);
        if (on) { g.setColour(t.ink); g.fillRect(r); }
        classic::drawIcon(g, it.icon, r, on);
        if (it.quarters)
            for (int q = 0; q < 4; ++q)
                if (it.codes[(size_t) q] == pressed) { // show the pressed quarter inverted
                    const auto qr = r.withSize(r.getWidth() / 2.0f, r.getHeight() / 2.0f)
                                     .translated(q % 2 ? r.getWidth() / 2.0f : 0.0f, q / 2 ? r.getHeight() / 2.0f : 0.0f);
                    g.saveState();
                    g.reduceClipRegion(qr.toNearestInt());
                    g.setColour(t.ink); g.fillRect(r);
                    classic::drawIcon(g, it.icon, r, true);
                    g.restoreState();
                }
    }
}

void ToolPalette::mouseMove(const juce::MouseEvent& e)
{
    const int code = codeAt(e.position);
    setTooltip(code >= 0 && tips.contains(code) ? tips[code] : juce::String());
}

void ToolPalette::mouseDown(const juce::MouseEvent& e)
{
    pressed = codeAt(e.position);
    repaint();
    if (pressed >= 0 && onTool) onTool(pressed);
}

// ---------------------------------------------------------------------------
float HarmonicEditor::barX(int k) const
{
    const auto r = plot();
    return r.getX() + r.getWidth() * (float) k / 64.0f;
}

int HarmonicEditor::barAt(float x) const
{
    const auto r = plot();
    return juce::jlimit(0, 63, (int) ((x - r.getX()) / r.getWidth() * 64.0f));
}

int HarmonicEditor::levelAt(float y) const
{
    // The window is 256 pixels tall: a point at y (1..255 from the top) is level (256 - y) * 128.
    const auto r = plot();
    const int py = juce::jlimit(1, 255, (int) std::lround((y - r.getY()) / r.getHeight() * 256.0f));
    return (256 - py) * 128;
}

void HarmonicEditor::paint(juce::Graphics& g)
{
    const auto& t = classic::theme();
    g.fillAll(t.paper);
    g.setColour(t.ink);
    g.drawRect(getLocalBounds(), 1);
    const auto r = plot();
    g.setColour(t.gray);
    for (int i = 1; i < 4; ++i) g.drawHorizontalLine((int) (r.getY() + r.getHeight() * (float) i / 4.0f), r.getX(), r.getRight());
    g.setColour(t.ink);
    for (int k = 0; k < 64; ++k) {
        const float h = r.getHeight() * (float) juce::jmax(0, (int) levels[(size_t) k]) / 32767.0f;
        const float x0 = barX(k), w = juce::jmax(1.0f, barX(k + 1) - x0 - 1.0f);
        if (h > 0.0f) g.fillRect(x0, r.getBottom() - h, w, h);
    }
    g.setFont(classic::smallFont(9.0f));
    for (int k : { 1, 16, 32, 48, 64 })
        g.drawText(juce::String(k), juce::Rectangle<float>(barX(k - 1) - 6.0f, r.getBottom() + 1.0f, 20.0f, 9.0f), juce::Justification::centredLeft);
    if (raking) {
        const float dash[] = { 3.0f, 2.0f };
        g.drawDashedLine({ rakeFrom, rakeTo }, dash, 2, 1.0f);
    }
}

void HarmonicEditor::mouseDown(const juce::MouseEvent& e)
{
    if (rake) { raking = true; rakeFrom = rakeTo = e.position; repaint(); return; }
    mouseDrag(e);
}

void HarmonicEditor::mouseDrag(const juce::MouseEvent& e)
{
    if (raking) { rakeTo = e.position; repaint(); return; }
    levels[(size_t) barAt(e.position.x)] = (int16_t) levelAt(e.position.y);
    repaint();
}

void HarmonicEditor::mouseUp(const juce::MouseEvent& e)
{
    if (raking) {
        raking = false;
        const bool aboveOnly = e.mods.isAltDown() && !e.mods.isShiftDown(), all = e.mods.isAltDown() && e.mods.isShiftDown();
        // F38_03A34 / F38_03B32: the line runs from the left point's harmonic and
        // level to the right one's, interpolated per harmonic with the long
        // multiply and divide. A harmonic moves to it if it is above 1, or with
        // Alt only if it is above the line, or with Alt+Shift always.
        auto a = rakeFrom, b = rakeTo;
        if (a.x > b.x) std::swap(a, b);
        const int h1 = barAt(a.x), h2 = barAt(b.x), l1 = levelAt(a.y), l2 = levelAt(b.y);
        for (int k = h1; k <= h2; ++k) {
            const int16_t line = (int16_t) (h1 == h2 ? l2 : (int) (((int64_t) (k - h1) * (l2 - l1)) / (h2 - h1)) + l1);
            auto& v = levels[(size_t) k];
            if (all || (aboveOnly ? line < v : v > 1)) v = line;
        }
        repaint();
    }
    if (onEdited) onEdited();
}

// ---------------------------------------------------------------------------
float WaveTimeline::xFor(int ms) const
{
    const float w = (float) getWidth() - 34.0f;
    return 2.0f + w * (float) juce::jlimit(0, durationMs, ms) / (float) juce::jmax(1, durationMs);
}

int WaveTimeline::msAt(float x) const
{
    const float w = (float) getWidth() - 34.0f;
    return juce::jlimit(0, juce::jmin(32767, durationMs), (int) std::round((x - 2.0f) / w * (float) durationMs));
}

juce::Rectangle<float> WaveTimeline::boxFor(int i) const
{
    const int ms = moving && selection.count(i) ? juce::jlimit(0, juce::jmin(32767, durationMs), waves[(size_t) i].timeMs + moveMs) : waves[(size_t) i].timeMs;
    return { xFor(ms), 2.0f, 30.0f, (float) getHeight() - 14.0f };
}

int WaveTimeline::hit(juce::Point<float> p) const
{
    for (int i = (int) waves.size() - 1; i >= 0; --i) if (boxFor(i).contains(p)) return i;
    return -1;
}

void WaveTimeline::paint(juce::Graphics& g)
{
    const auto& t = classic::theme();
    g.fillAll(t.paper);
    g.setColour(t.ink);
    g.drawRect(getLocalBounds(), 1);
    // The time scale, 50 ms ticks as in the original window.
    const float base = (float) getHeight() - 10.0f;
    g.drawHorizontalLine((int) base, 2.0f, (float) getWidth() - 2.0f);
    const int step = durationMs > 4000 ? 500 : durationMs > 1000 ? 100 : 50;
    g.setFont(classic::smallFont(8.0f));
    for (int ms = 0; ms <= durationMs; ms += step) {
        const float x = xFor(ms);
        const bool major = ms % (step * 10) == 0;
        g.drawVerticalLine((int) x, base, base + (major ? 4.0f : 2.0f));
        if (major) g.drawText(juce::String(ms), juce::Rectangle<float>(x + 1.0f, base + 1.0f, 40.0f, 9.0f), juce::Justification::centredLeft);
    }
    for (int i = 0; i < (int) waves.size(); ++i) {
        const auto r = boxFor(i);
        const bool sel = selection.count(i) != 0;
        g.setColour(sel ? t.ink : t.paper);
        g.fillRect(r);
        g.setColour(t.ink);
        g.drawRect(r, 1.0f);
        juce::Path p;
        const auto& s = *waves[(size_t) i].samples;
        for (int n = 0; n < 256; n += 4) {
            const float x = r.getX() + 2.0f + (r.getWidth() - 4.0f) * (float) n / 252.0f;
            const float y = r.getCentreY() - (float) s[(size_t) n] / 32768.0f * (r.getHeight() * 0.5f - 2.0f);
            if (n == 0) p.startNewSubPath(x, y); else p.lineTo(x, y);
        }
        g.setColour(sel ? t.paper : t.ink);
        g.strokePath(p, juce::PathStrokeType(1.0f));
    }
    if (selecting) {
        const auto box = juce::Rectangle<float>(dragStart, dragNow);
        const float dash[] = { 3.0f, 3.0f };
        g.setColour(t.ink);
        g.drawDashedLine({ box.getTopLeft(), box.getTopRight() }, dash, 2, 1.0f);
        g.drawDashedLine({ box.getTopRight(), box.getBottomRight() }, dash, 2, 1.0f);
        g.drawDashedLine({ box.getBottomRight(), box.getBottomLeft() }, dash, 2, 1.0f);
        g.drawDashedLine({ box.getBottomLeft(), box.getTopLeft() }, dash, 2, 1.0f);
    }
}

// As in the patch window, checked in the emulator: Shift toggles, a click on a member keeps the
// group, any other click selects that one, and a drag from empty space draws a selection box.
void WaveTimeline::mouseDown(const juce::MouseEvent& e)
{
    if (placing) {
        placing = false;
        setMouseCursor(juce::MouseCursor::NormalCursor);
        if (onPlace) onPlace(msAt(e.position.x));
        return;
    }
    const int i = hit(e.position);
    const bool shift = e.mods.isShiftDown();
    dragStart = dragNow = e.position;
    moveMs = 0;
    if (i >= 0) {
        if (shift) { if (!selection.erase(i)) selection.insert(i); }
        else if (!selection.count(i)) selection = { i };
        moving = selection.count(i) != 0;
        if (onSelect) onSelect(moving ? i : -1);
    } else {
        if (!shift) { selection.clear(); if (onSelect) onSelect(-1); }
        selecting = true;
        boxToggles = shift;
    }
    repaint();
}

void WaveTimeline::mouseDrag(const juce::MouseEvent& e)
{
    dragNow = e.position;
    const float dx = e.position.x - dragStart.x;
    if (moving) moveMs = dx >= 0.0f ? msAt(2.0f + dx) : -msAt(2.0f - dx);
    if (moving || selecting) repaint();
}

void WaveTimeline::mouseUp(const juce::MouseEvent&)
{
    if (selecting) {
        selecting = false;
        const auto box = juce::Rectangle<float>(dragStart, dragNow);
        if (!box.isEmpty()) { // a waveform the box only touches counts
            for (int i = 0; i < (int) waves.size(); ++i)
                if (boxFor(i).intersects(box)) { if (!boxToggles || !selection.erase(i)) selection.insert(i); }
            if (onSelect) onSelect(-1);
        }
    } else if (moving) {
        const int by = moveMs;
        moving = false;
        moveMs = 0;
        if (by != 0 && onMoved) onMoved(by);
    }
    repaint();
}

juce::String WaveTimeline::getTooltip()
{
    const int i = hit(getMouseXYRelative().toFloat());
    return i < 0 ? juce::String() : waves[(size_t) i].name + " at " + juce::String(waves[(size_t) i].timeMs) + " ms";
}
