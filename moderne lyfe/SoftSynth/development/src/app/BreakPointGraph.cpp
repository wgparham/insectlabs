#include "BreakPointGraph.h"
#include "ClassicLook.h"

using namespace softsynth;

namespace {

// x units are 3.75 ms (RE-NOTES, break points).
constexpr double kSecondsPerX = 0.00375;
// Hit distance in pixels for an existing point.
constexpr int kHitRadius = 8;
// The graph's shown range in x units, at least this long.
constexpr int kMinimumXMax = 200;
// Frequency contour y is shown from -50 to 50, which the partial's scale maps to percent.
constexpr int kContourLimit = 50;

} // namespace

BreakPointGraph::BreakPointGraph(Patch& p, std::function<void(const juce::String&)> n)
    : patch(p), notify(std::move(n))
{
}

void BreakPointGraph::setPartial(int p)
{
    partial = p;
    track = Track::None;
    repaint();
}

void BreakPointGraph::setMode(Mode m)
{
    mode = m;
    track = Track::None;
    repaint();
}

Graph BreakPointGraph::graph() const
{
    return mode == Mode::Amplitude ? Graph::Amp : Graph::Freq;
}

int BreakPointGraph::lastRecord() const
{
    return mode == Mode::Amplitude ? lastAmp(patch, partial) : lastFreq(patch, partial);
}

int BreakPointGraph::capacity() const
{
    return mode == Mode::Amplitude ? kMaxAmpSegments : kMaxFreqSegments;
}

int BreakPointGraph::recordX(int n) const
{
    return mode == Mode::Amplitude ? ampX(patch, partial, n) : freqX(patch, partial, n);
}

int BreakPointGraph::recordY(int n) const
{
    return mode == Mode::Amplitude ? ampY(patch, partial, n) : freqY(patch, partial, n);
}

void BreakPointGraph::setRecord(int n, int y, int x)
{
    if (mode == Mode::Amplitude)
        setAmpPoint(patch, partial, n, y, x);
    else
        setFreqPoint(patch, partial, n, y, x);
}

int BreakPointGraph::yMin() const { return mode == Mode::Amplitude ? 0 : -kContourLimit; }
int BreakPointGraph::yMax() const { return mode == Mode::Amplitude ? kLevelMax : kContourLimit; }

int BreakPointGraph::xMax() const
{
    // The sound's length in x units, using the original's 267 for 800/3.
    const int length = (int) (((uint32_t) patch.length * 0x10Bu) / (uint32_t) patch.rate);
    return juce::jmax(kMinimumXMax, length);
}

juce::Rectangle<int> BreakPointGraph::plotArea() const
{
    return getLocalBounds().withTrimmedLeft(48).withTrimmedRight(12).withTrimmedTop(10).withTrimmedBottom(24);
}

float BreakPointGraph::xToPx(int x) const
{
    const auto plot = plotArea();
    return (float) plot.getX() + (float) x * (float) plot.getWidth() / (float) xMax();
}

float BreakPointGraph::yToPx(int y) const
{
    const auto plot = plotArea();
    const float fraction = (float) (y - yMin()) / (float) (yMax() - yMin());
    return (float) plot.getBottom() - fraction * (float) plot.getHeight();
}

int BreakPointGraph::pxToX(int px) const
{
    const auto plot = plotArea();
    const int x = juce::roundToInt((float) (px - plot.getX()) * (float) xMax() / (float) plot.getWidth());
    return juce::jlimit(0, xMax(), x);
}

int BreakPointGraph::pxToY(int py) const
{
    const auto plot = plotArea();
    const float fraction = (float) (plot.getBottom() - py) / (float) plot.getHeight();
    return juce::jlimit(yMin(), yMax(), juce::roundToInt((float) yMin() + fraction * (float) (yMax() - yMin())));
}

int BreakPointGraph::nearestRecord(juce::Point<int> at) const
{
    int best = 0;
    float bestDistance = (float) kHitRadius;
    for (int n = 1; n <= lastRecord(); ++n) {
        const juce::Point<float> p(xToPx(recordX(n)), yToPx(recordY(n)));
        const float d = p.getDistanceFrom(at.toFloat());
        if (d <= bestDistance) {
            bestDistance = d;
            best = n;
        }
    }
    return best;
}

void BreakPointGraph::moveCursor(juce::Point<int> at)
{
    // A point stays between its neighbours in x, one unit clear of each.
    const int lo = recordX(prevRecord) + 1;
    const int hi = juce::jmax(lo, recordX(nextRecord) - 1);
    cursorX = juce::jlimit(lo, hi, pxToX(at.x));
    cursorY = pxToY(at.y);
}

void BreakPointGraph::drawGhostLines(juce::Graphics& g) const
{
    // TurboSynth draws these in gray and XOR; a dashed gray line is the nearest here.
    const juce::Point<float> tip(xToPx(cursorX), yToPx(cursorY));
    const juce::Point<float> before(xToPx(recordX(prevRecord)), yToPx(recordY(prevRecord)));
    // The end record is off the right of the graph, so its line runs to the edge at the cursor's level.
    const bool toEnd = nextRecord > lastRecord();
    const juce::Point<float> after = toEnd ? juce::Point<float>(xToPx(xMax()), tip.y)
                                           : juce::Point<float>(xToPx(recordX(nextRecord)), yToPx(recordY(nextRecord)));

    const float dashes[] = { 2.0f, 2.0f };
    g.setColour(classic::theme().gray);
    g.drawDashedLine(juce::Line<float>(before, tip), dashes, 2, 1.0f);
    g.drawDashedLine(juce::Line<float>(tip, after), dashes, 2, 1.0f);
}

void BreakPointGraph::paint(juce::Graphics& g)
{
    const auto plot = plotArea();
    g.setColour(classic::theme().paper);
    g.fillRect(plot);

    // Grid: every 20 units for the envelope, every 10 for the contour.
    const int gridStep = mode == Mode::Amplitude ? 20 : 10;
    g.setColour(classic::theme().gray);
    for (int v = yMin(); v <= yMax(); v += gridStep)
        g.drawHorizontalLine(juce::roundToInt(yToPx(v)), (float) plot.getX(), (float) plot.getRight());
    g.setColour(classic::theme().gray);
    if (mode == Mode::Frequency)
        g.drawHorizontalLine(juce::roundToInt(yToPx(0)), (float) plot.getX(), (float) plot.getRight());

    // Time ticks, every quarter second.
    g.setFont(juce::FontOptions(11.0f));
    g.setColour(classic::theme().ink);
    const double ticksPerSecond = 1.0 / kSecondsPerX;
    for (int k = 0;; ++k) {
        const int x = juce::roundToInt(k * 0.25 * ticksPerSecond);
        if (x > xMax())
            break;
        const float px = xToPx(x);
        g.drawVerticalLine(juce::roundToInt(px), (float) plot.getY(), (float) plot.getBottom());
        g.drawText(juce::String(k * 0.25, 2) + " s", juce::Rectangle<float>(px - 30, (float) plot.getBottom() + 2, 60, 18), juce::Justification::centred);
    }

    // Level labels. The contour's ends are its scale, 2% or 50%.
    const juce::String scale = patch.partial(partial)[kScale] == 1 ? "2%" : "50%";
    const juce::String top = mode == Mode::Amplitude ? "100%" : "+" + scale;
    const juce::String bottom = mode == Mode::Amplitude ? "0%" : "-" + scale;
    g.drawText(top, juce::Rectangle<int>(0, plot.getY() - 6, 44, 14), juce::Justification::centredRight);
    g.drawText(bottom, juce::Rectangle<int>(0, plot.getBottom() - 8, 44, 14), juce::Justification::centredRight);

    // The curve from the start record through the last point.
    juce::Path curve;
    curve.startNewSubPath(xToPx(recordX(0)), yToPx(recordY(0)));
    for (int n = 1; n <= lastRecord(); ++n)
        curve.lineTo(xToPx(recordX(n)), yToPx(recordY(n)));
    g.setColour(classic::theme().ink);
    g.strokePath(curve, juce::PathStrokeType(1.5f));

    for (int n = 1; n <= lastRecord(); ++n) {
        const float px = xToPx(recordX(n)), py = yToPx(recordY(n));
        g.fillRect(juce::Rectangle<float>(px - 3, py - 3, 6, 6));
    }

    if (track != Track::None)
        drawGhostLines(g);

    g.setColour(classic::theme().ink);
    g.drawRect(plot);
}

void BreakPointGraph::mouseDown(const juce::MouseEvent& e)
{
    track = Track::None;
    const auto at = e.getPosition();
    const int hit = nearestRecord(at);

    if (e.mods.isPopupMenu()) {
        if (hit > 0) {
            SliceList unused;
            removePoint(patch, unused, graph(), partial, hit);
            repaint();
            notify("Removed a break point.");
        }
        return;
    }

    if (hit > 0) {
        track = Track::Move;
        trackIndex = hit;
        prevRecord = hit - 1;
        nextRecord = hit + 1;
    } else {
        // Between the neighbours: the first record past the click's x (Edit.h's findPoint).
        SliceList unused;
        int16_t index = 0;
        if (findPoint(patch, unused, graph(), partial, pxToX(at.x), index) || index == 0)
            return; // a point is already at that x, or there is no room past the end
        if (lastRecord() >= capacity()) {
            notify("Sorry, you have used all the break points available for this graph.");
            return;
        }
        track = Track::Add;
        trackIndex = index;
        prevRecord = index - 1;
        nextRecord = index;
    }

    moveCursor(at);
    repaint();
}

void BreakPointGraph::mouseDrag(const juce::MouseEvent& e)
{
    if (track == Track::None)
        return;
    moveCursor(e.getPosition());
    repaint();
}

void BreakPointGraph::mouseUp(const juce::MouseEvent& e)
{
    if (track == Track::None)
        return;
    moveCursor(e.getPosition());

    SliceList unused;
    if (track == Track::Add) {
        int16_t index = (int16_t) trackIndex;
        if (!insertPoint(patch, unused, graph(), partial, index, false)) {
            notify("Sorry, you have used all the break points available for this graph.");
        } else {
            setRecord(index, cursorY, cursorX);
        }
    } else {
        setRecord(trackIndex, cursorY, cursorX);
    }

    track = Track::None;
    repaint();
}
