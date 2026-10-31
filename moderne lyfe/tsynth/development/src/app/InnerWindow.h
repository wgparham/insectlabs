// A window on the application's desktop, drawn like the original's System 6/7
// windows: pinstriped title bar (only on the front window), close box, grow
// box. Drag the title bar to move it; drag the grow box to resize it. Any
// click inside brings it to the front.
#pragma once

#include <juce_gui_basics/juce_gui_basics.h>

class InnerWindow : public juce::Component
{
public:
    InnerWindow(const juce::String& title, juce::Component& content, bool closable = true);
    ~InnerWindow() override;

    std::function<void()> onActivate; // brought to the front
    std::function<void()> onClose;    // close box clicked (the owner deletes or hides it)

    void setTitle(const juce::String& t) { if (t != title) { title = t; repaint(); } }
    const juce::String& getTitle() const { return title; }
    void setActive(bool a) { if (a != active) { active = a; repaint(); } }
    bool isActive() const { return active; }
    // Brings it to the front and makes it the active window.
    void activate();

    void paint(juce::Graphics&) override;
    void paintOverChildren(juce::Graphics&) override; // the grow box
    void resized() override;
    void mouseDown(const juce::MouseEvent&) override;
    void mouseDrag(const juce::MouseEvent&) override;
    void mouseUp(const juce::MouseEvent&) override;

private:
    juce::Rectangle<int> closeBox() const;
    juce::Rectangle<int> growBox() const;
    juce::String title;
    juce::Component& content;
    bool closable, active = false;
    enum class Drag { None, Move, Grow, Close } drag = Drag::None;
    juce::Rectangle<int> boundsAtStart;
    juce::Point<int> start; // the mouse at the press, in the parent's coordinates
    juce::Point<int> mouseInParent() const;
};
