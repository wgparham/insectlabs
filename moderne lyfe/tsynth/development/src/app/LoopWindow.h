// The Loop window: the end of the loop on the left and its start on the
// right, meeting at the dotted centre line, as in the original. Click a half
// to move that loop point there; the arrows under each half move it one
// sample, the Auto arrows to the best match for the other point in that direction.
#pragma once

#include <juce_gui_basics/juce_gui_basics.h>

#include <vector>

class LoopView : public juce::Component, public juce::SettableTooltipClient
{
public:
    std::vector<int16_t> data;
    int32_t loopStart = 0, loopEnd = 0;
    std::function<void()> onChanged; // loop points moved
    std::function<void()> onPlay;    // hear the loop

    void paint(juce::Graphics&) override;
    void mouseDown(const juce::MouseEvent&) override;
    void mouseUp(const juce::MouseEvent&) override;
    void mouseMove(const juce::MouseEvent&) override;

private:
    enum Hit { None, ZoomInV, ZoomOutV, ZoomInH, ZoomOutH, Play, EndStep, EndAuto, StartStep, StartAuto };
    juce::Rectangle<float> plot() const { return getLocalBounds().toFloat().reduced(6.0f).withTrimmedTop(40.0f).withTrimmedBottom(40.0f); }
    juce::Rectangle<float> iconRect(int slot) const { return { 6.0f + 34.0f * (float) slot, 4.0f, 32.0f, 32.0f }; }
    juce::Rectangle<float> arrows(bool endPoint) const;
    Hit hitAt(juce::Point<float>, int* direction) const;
    void move(bool endPoint, int direction, bool automatic);
    int span = 128;     // samples shown in each half
    float vzoom = 1.0f; // display amplitude
    Hit pressed = None;
    int pressedDir = 0;
};

class LoopWindow : public juce::DocumentWindow
{
public:
    explicit LoopWindow(const juce::String& title);
    LoopView view;
    void closeButtonPressed() override { setVisible(false); }
};
