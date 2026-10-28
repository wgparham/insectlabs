// Sound display with a play cursor, and two editors used by the module
// panel: a drawable table and a breakpoint envelope.
#pragma once

#include <juce_gui_basics/juce_gui_basics.h>

#include <vector>

class WaveView : public juce::Component
{
public:
    void setSamples(const std::vector<int16_t>& left, const std::vector<int16_t>& right, int sampleRate, const juce::String& caption);
    void setPlayPosition(double seconds) { if (seconds != playPos) { playPos = seconds; repaint(); } }
    void themeChanged() { cache = {}; repaint(); }
    void resized() override { cache = {}; }
    void paint(juce::Graphics&) override;
private:
    std::vector<int16_t> l, r;
    int rate = 44100;
    juce::String caption;
    double playPos = -1.0;
    juce::Image cache; // the waveform, drawn once per sound and size
    void buildCache();
};

// Draw-with-the-mouse table of 16-bit values (oscillator waves, shaper curve).
class TableEditor : public juce::Component
{
public:
    std::vector<int16_t> values;
    std::function<void()> onEdited;
    void paint(juce::Graphics&) override;
    void mouseDown(const juce::MouseEvent& e) override { last = -1; draw(e.position); }
    void mouseDrag(const juce::MouseEvent& e) override { draw(e.position); }
    void mouseUp(const juce::MouseEvent&) override { if (onEdited) onEdited(); }
private:
    void draw(juce::Point<float>);
    int last = -1;
    int lastValue = 0;
};

// Envelope breakpoints, level 0..100 against time in ms. Drag points;
// double-click adds one, right-click removes one.
class BreakpointEditor : public juce::Component
{
public:
    struct Point { int level, timeMs; };
    std::vector<Point> points;
    int durationMs = 1000;
    std::function<void()> onEdited;
    void paint(juce::Graphics&) override;
    void mouseDown(const juce::MouseEvent&) override;
    void mouseDrag(const juce::MouseEvent&) override;
    void mouseUp(const juce::MouseEvent&) override { if (dragged >= 0 && onEdited) onEdited(); dragged = -1; }
    void mouseDoubleClick(const juce::MouseEvent&) override;
private:
    juce::Rectangle<float> plot() const { return getLocalBounds().toFloat().reduced(6.0f, 8.0f); }
    juce::Point<float> toScreen(const Point&) const;
    int hit(juce::Point<float>) const;
    int dragged = -1;
};
