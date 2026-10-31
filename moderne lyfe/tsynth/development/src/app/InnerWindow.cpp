#include "InnerWindow.h"
#include "ClassicLook.h"

namespace {
constexpr int kGrow = 15; // the grow box at the bottom right
constexpr int kShadow = 1;
}

InnerWindow::InnerWindow(const juce::String& t, juce::Component& c, bool canClose)
    : title(t), content(c), closable(canClose)
{
    addAndMakeVisible(content);
    content.addMouseListener(this, true); // a click anywhere inside brings the window forward
}

InnerWindow::~InnerWindow()
{
    content.removeMouseListener(this);
    removeChildComponent(&content);
}

void InnerWindow::activate()
{
    toFront(false);
    if (!active && onActivate) onActivate();
    setActive(true);
}

juce::Rectangle<int> InnerWindow::closeBox() const
{
    return { 9, classic::kTitleHeight / 2 - 5, 11, 11 };
}

juce::Rectangle<int> InnerWindow::growBox() const
{
    const auto f = getLocalBounds().withTrimmedRight(kShadow).withTrimmedBottom(kShadow);
    return { f.getRight() - kGrow, f.getBottom() - kGrow, kGrow, kGrow };
}

void InnerWindow::paint(juce::Graphics& g)
{
    classic::drawWindow(g, getLocalBounds().withTrimmedRight(kShadow).withTrimmedBottom(kShadow), title, active);
    if (!closable && active) { // no close box: cover the one drawWindow puts there with pinstripes
        const auto& t = classic::theme();
        const auto b = closeBox().expanded(2, 0);
        g.setColour(t.paper);
        g.fillRect(b);
        g.setColour(t.ink);
        for (int y = 4; y < classic::kTitleHeight - 3; y += 2) g.drawHorizontalLine(y, (float) b.getX(), (float) b.getRight());
    }
}

void InnerWindow::paintOverChildren(juce::Graphics& g)
{
    if (!active) return;
    const auto& t = classic::theme();
    const auto b = growBox();
    g.setColour(t.paper);
    g.fillRect(b);
    g.setColour(t.ink);
    g.drawRect(b.withTrimmedLeft(0), 1);
    g.drawRect(juce::Rectangle<int>(b.getX() + 4, b.getY() + 4, 7, 7), 1);
    g.drawRect(juce::Rectangle<int>(b.getX() + 2, b.getY() + 2, 6, 6), 1);
}

void InnerWindow::resized()
{
    content.setBounds(getLocalBounds().withTrimmedRight(kShadow).withTrimmedBottom(kShadow).reduced(1).withTrimmedTop(classic::kTitleHeight));
}

void InnerWindow::mouseDown(const juce::MouseEvent& e)
{
    activate();
    drag = Drag::None;
    boundsAtStart = getBounds();
    start = mouseInParent();
    const auto p = e.getEventRelativeTo(this).getPosition();
    if (growBox().contains(p)) drag = Drag::Grow; // drawn over the content, so its clicks may come from there
    else if (e.eventComponent != this) return;    // a click in the content: only activation
    else if (closable && closeBox().expanded(2).contains(p)) drag = Drag::Close;
    else if (p.y < classic::kTitleHeight) drag = Drag::Move;
}

juce::Point<int> InnerWindow::mouseInParent() const
{
    // The mouse itself, not the event: once the pointer leaves the window the
    // events are no longer measured against it, so the window would jump.
    const auto screen = juce::Desktop::getInstance().getMainMouseSource().getScreenPosition().roundToInt();
    auto* parent = getParentComponent();
    return parent ? parent->getLocalPoint(nullptr, screen) : screen;
}

void InnerWindow::mouseDrag(const juce::MouseEvent&)
{
    if (drag != Drag::Move && drag != Drag::Grow) return;
    const auto d = mouseInParent() - start;
    if (drag == Drag::Move) {
        auto b = boundsAtStart.translated(d.x, d.y);
        if (auto* parent = getParentComponent()) // the title bar stays reachable
            b.setPosition(juce::jlimit(40 - b.getWidth(), parent->getWidth() - 40, b.getX()),
                          juce::jlimit(0, parent->getHeight() - classic::kTitleHeight, b.getY()));
        setBounds(b);
    } else {
        setSize(juce::jmax(200, boundsAtStart.getWidth() + d.x), juce::jmax(120, boundsAtStart.getHeight() + d.y));
    }
}

void InnerWindow::mouseUp(const juce::MouseEvent& e)
{
    if (drag == Drag::Close && closeBox().expanded(2).contains(e.getEventRelativeTo(this).getPosition()) && onClose) {
        drag = Drag::None;
        onClose(); // may delete this window
        return;
    }
    drag = Drag::None;
}
