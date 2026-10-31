#include "LoopWindow.h"
#include "ClassicLook.h"
#include "SampleEdit.h"

LoopWindow::LoopWindow(const juce::String& title)
    : DocumentWindow(title, classic::theme().paper, DocumentWindow::closeButton)
{
    setUsingNativeTitleBar(true);
    setContentNonOwned(&view, false);
    view.setSize(560, 300);
    setResizable(true, false);
    setResizeLimits(360, 220, 1600, 900);
    setContentComponentSize(560, 300);
}

juce::Rectangle<float> LoopView::arrows(bool endPoint) const
{
    // The step-and-Auto control (icons 5600 / 5601) under each half.
    const auto p = plot();
    const float cx = endPoint ? p.getX() + p.getWidth() * 0.25f : p.getX() + p.getWidth() * 0.75f;
    return { cx - 34.0f, p.getBottom() - 34.0f, 68.0f, 32.0f };
}

LoopView::Hit LoopView::hitAt(juce::Point<float> pos, int* direction) const
{
    for (int slot = 0; slot < 6; ++slot)
        if (iconRect(slot).contains(pos)) return (Hit) (Info + slot);
    for (bool endPoint : { true, false }) {
        const auto r = arrows(endPoint);
        if (!r.contains(pos)) continue;
        *direction = pos.x < r.getCentreX() ? -1 : 1;
        const bool automatic = pos.y > r.getY() + r.getHeight() * 0.45f; // the AUTO box is the lower part
        return endPoint ? (automatic ? EndAuto : EndStep) : (automatic ? StartAuto : StartStep);
    }
    return None;
}

void LoopView::paint(juce::Graphics& g)
{
    const auto& t = classic::theme();
    g.fillAll(t.paper);
    const int icons[] = { 1002, 1003, 8000, 8001, 8002, 8003 }; // Info, speaker, zoom
    for (int slot = 0; slot < 6; ++slot) {
        classic::drawIcon(g, icons[slot], iconRect(slot), pressed == Info + slot);
        g.setColour(t.ink);
        g.drawRect(iconRect(slot), 1.0f);
    }
    g.setColour(t.ink);
    g.drawVerticalLine((int) kPalette - 1, 0.0f, (float) getHeight());

    const auto p = plot();
    g.drawHorizontalLine((int) p.getCentreY(), p.getX(), p.getRight());
    const float dash[] = { 2.0f, 3.0f };
    g.setColour(t.ink);
    g.drawDashedLine({ p.getCentreX(), p.getY(), p.getCentreX(), p.getBottom() }, dash, 2, 1.0f);
    if (data.empty()) return;
    // Left half: the samples before the loop end; right half: from the loop start.
    juce::Path path;
    const int n = 2 * span;
    for (int i = 0; i < n; ++i) {
        const int32_t s = i < span ? loopEnd - span + i : loopStart + (i - span);
        const int16_t v = s >= 0 && s < (int32_t) data.size() ? data[(size_t) s] : 0;
        const float x = p.getX() + p.getWidth() * ((float) i + 0.5f) / (float) n;
        const float y = juce::jlimit(p.getY(), p.getBottom(), p.getCentreY() - (float) v / 32768.0f * vzoom * p.getHeight() * 0.5f);
        if (i == 0) path.startNewSubPath(x, y); else path.lineTo(x, y);
    }
    g.strokePath(path, juce::PathStrokeType(1.0f));
    if (span <= 64) // individual samples once zoomed in
        for (int i = 0; i < n; ++i) {
            const int32_t s = i < span ? loopEnd - span + i : loopStart + (i - span);
            const int16_t v = s >= 0 && s < (int32_t) data.size() ? data[(size_t) s] : 0;
            const float x = p.getX() + p.getWidth() * ((float) i + 0.5f) / (float) n;
            const float y = juce::jlimit(p.getY(), p.getBottom(), p.getCentreY() - (float) v / 32768.0f * vzoom * p.getHeight() * 0.5f);
            g.fillRect(x - 1.5f, y - 1.5f, 3.0f, 3.0f);
        }
    for (bool endPoint : { true, false }) {
        const bool down = pressed == (endPoint ? EndStep : StartStep) || pressed == (endPoint ? EndAuto : StartAuto);
        const bool autoDown = pressed == (endPoint ? EndAuto : StartAuto);
        const auto r = arrows(endPoint);
        // 5602/5603 show a pressed arrow, 5604/5605 a pressed AUTO.
        const int left = down && pressedDir < 0 ? (autoDown ? 5604 : 5602) : 5600;
        const int right = down && pressedDir > 0 ? (autoDown ? 5605 : 5603) : 5601;
        classic::drawIcon(g, left, r.withWidth(34.0f));
        classic::drawIcon(g, right, r.withTrimmedLeft(34.0f));
    }
}

void LoopView::move(bool endPoint, int direction, bool automatic)
{
    int32_t& point = endPoint ? loopEnd : loopStart;
    const int32_t next = automatic ? turbosynth::sampleedit::autoLoopPoint(data, loopStart, loopEnd, endPoint, direction) : point + direction;
    point = juce::jlimit<int32_t>(0, (int32_t) data.size() - 1, next);
    if (loopStart >= loopEnd) point = endPoint ? loopStart + 1 : loopEnd - 1;
    repaint();
    if (onChanged) onChanged();
}

void LoopView::mouseDown(const juce::MouseEvent& e)
{
    int dir = 0;
    pressed = hitAt(e.position, &dir);
    pressedDir = dir;
    switch (pressed) {
    case ZoomInV: vzoom = juce::jmin(32.0f, vzoom * 2.0f); break;
    case ZoomOutV: vzoom = juce::jmax(0.25f, vzoom * 0.5f); break;
    case ZoomInH: span = juce::jmax(8, span / 2); break;
    case ZoomOutH: span = juce::jmin(16384, span * 2); break;
    case Play: if (onPlay) onPlay(); break;
    case Info: if (onInfo) onInfo(); break;
    case EndStep: move(true, dir, false); break;
    case EndAuto: move(true, dir, true); break;
    case StartStep: move(false, dir, false); break;
    case StartAuto: move(false, dir, true); break;
    case None: {
        // Click in a half: that sample becomes the loop end (left) or start (right).
        const auto p = plot();
        if (!p.contains(e.position) || data.empty()) break;
        const int i = juce::jlimit(0, 2 * span - 1, (int) ((e.position.x - p.getX()) / p.getWidth() * (float) (2 * span)));
        if (i < span) loopEnd = juce::jlimit<int32_t>(loopStart + 1, (int32_t) data.size(), loopEnd - span + i + 1);
        else loopStart = juce::jlimit<int32_t>(0, loopEnd - 1, loopStart + (i - span));
        if (onChanged) onChanged();
        break; }
    }
    repaint();
}

void LoopView::mouseUp(const juce::MouseEvent&)
{
    pressed = None;
    repaint();
}

void LoopView::mouseMove(const juce::MouseEvent& e)
{
    int dir = 0;
    static const char* const tips[] = { "", "Loop points and frequency", "Hear the loop", "Taller", "Shorter", "Fewer samples (zoom in)", "More samples (zoom out)",
                                        "Move the loop end one sample", "Move the loop end to the best-matching sample in that direction",
                                        "Move the loop start one sample", "Move the loop start to the best-matching sample in that direction" };
    setTooltip(tips[hitAt(e.position, &dir)]);
}
