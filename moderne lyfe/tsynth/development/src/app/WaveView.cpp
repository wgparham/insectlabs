#include "WaveView.h"
#include "ClassicLook.h"

#include <algorithm>

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
    if (last < 0) values[(size_t) i] = (int16_t) v;
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
juce::Point<float> BreakpointEditor::toScreen(const Point& p) const
{
    const auto r = plot();
    return { r.getX() + (float) p.timeMs / (float) juce::jmax(1, durationMs) * r.getWidth(),
             r.getBottom() - (float) p.level / 100.0f * r.getHeight() };
}

int BreakpointEditor::hit(juce::Point<float> pos) const
{
    for (int i = 0; i < (int) points.size(); ++i)
        if (toScreen(points[(size_t) i]).getDistanceFrom(pos) < 6.0f) return i;
    return -1;
}

void BreakpointEditor::paint(juce::Graphics& g)
{
    const auto& t = classic::theme();
    g.fillAll(t.paper);
    g.setColour(t.ink);
    g.drawRect(getLocalBounds(), 1);
    const auto r = plot();
    g.setColour(t.gray);
    for (int i = 1; i < 4; ++i) g.drawHorizontalLine((int) (r.getY() + r.getHeight() * (float) i / 4.0f), r.getX(), r.getRight());
    g.setColour(t.ink);
    juce::Path p;
    for (size_t i = 0; i < points.size(); ++i) {
        const auto s = toScreen(points[i]);
        if (i == 0) p.startNewSubPath(s); else p.lineTo(s);
    }
    if (!points.empty()) p.lineTo(r.getRight(), toScreen(points.back()).y);
    g.strokePath(p, juce::PathStrokeType(1.0f));
    for (const auto& pt : points) {
        const auto s = toScreen(pt);
        g.setColour(t.paper); g.fillRect(juce::Rectangle<float>(7, 7).withCentre(s));
        g.setColour(t.ink); g.drawRect(juce::Rectangle<float>(7, 7).withCentre(s), 1.0f);
    }
}

void BreakpointEditor::mouseDown(const juce::MouseEvent& e)
{
    dragged = hit(e.position);
    if (e.mods.isPopupMenu() && dragged >= 0 && points.size() > 1) {
        points.erase(points.begin() + dragged);
        dragged = -1;
        repaint();
        if (onEdited) onEdited();
    }
}

void BreakpointEditor::mouseDrag(const juce::MouseEvent& e)
{
    if (dragged < 0) return;
    const auto r = plot();
    auto& pt = points[(size_t) dragged];
    pt.level = juce::jlimit(0, 100, (int) std::round((r.getBottom() - e.position.y) / r.getHeight() * 100.0f));
    int tms = (int) std::round((e.position.x - r.getX()) / r.getWidth() * (float) durationMs);
    const int lo = dragged > 0 ? points[(size_t) dragged - 1].timeMs : 0;
    const int hi = dragged + 1 < (int) points.size() ? points[(size_t) dragged + 1].timeMs : durationMs;
    pt.timeMs = dragged == 0 ? 0 : juce::jlimit(lo, hi, tms);
    repaint();
}

void BreakpointEditor::mouseDoubleClick(const juce::MouseEvent& e)
{
    const auto r = plot();
    Point p { juce::jlimit(0, 100, (int) std::round((r.getBottom() - e.position.y) / r.getHeight() * 100.0f)),
              juce::jlimit(0, durationMs, (int) std::round((e.position.x - r.getX()) / r.getWidth() * (float) durationMs)) };
    auto it = std::find_if(points.begin(), points.end(), [&](const Point& q) { return q.timeMs > p.timeMs; });
    points.insert(it, p);
    repaint();
    if (onEdited) onEdited();
}
