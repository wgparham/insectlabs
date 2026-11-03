// The break point graph for one partial: its amplitude envelope or its frequency contour.
// As in TurboSynth's breakpoint editor: press a point to move it, or press anywhere else
// to add one between its neighbours. Dotted gray lines follow the mouse from the new
// place to the neighbouring points, and the change is made on release. Right-click a
// point to remove it. The records are edited through the same routines the original uses
// (Edit.h).
#pragma once

#include "Edit.h"
#include "Patch.h"

#include <juce_gui_basics/juce_gui_basics.h>

#include <functional>

class BreakPointGraph : public juce::Component
{
public:
    enum class Mode { Amplitude, Frequency };

    // `notify` receives a line for the status bar, when there is one to give.
    BreakPointGraph(softsynth::Patch& patch, std::function<void(const juce::String&)> notify);

    void setPartial(int partial);
    void setMode(Mode mode);

    void paint(juce::Graphics&) override;
    void mouseDown(const juce::MouseEvent&) override;
    void mouseDrag(const juce::MouseEvent&) override;
    void mouseUp(const juce::MouseEvent&) override;

private:
    enum class Track { None, Add, Move };

    softsynth::Graph graph() const;
    int lastRecord() const;
    int capacity() const;
    int recordX(int n) const;
    int recordY(int n) const;
    void setRecord(int n, int y, int x);
    void moveCursor(juce::Point<int> at);

    int yMin() const;
    int yMax() const;
    int xMax() const;
    juce::Rectangle<int> plotArea() const;
    float xToPx(int x) const;
    float yToPx(int y) const;
    int pxToX(int px) const;
    int pxToY(int py) const;
    int nearestRecord(juce::Point<int> at) const;
    void drawGhostLines(juce::Graphics&) const;

    softsynth::Patch& patch;
    std::function<void(const juce::String&)> notify;
    int partial = 0;
    Mode mode = Mode::Amplitude;

    // The gesture in progress. Records are not touched until release.
    Track track = Track::None;
    int trackIndex = 0;   // the point being moved, or the record a new one goes before
    int prevRecord = 0;   // the neighbours the ghost lines run to
    int nextRecord = 0;
    int cursorX = 0, cursorY = 0;
};
