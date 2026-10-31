// Sound display with a play cursor, and two editors used by the module
// panel: a drawable table and a breakpoint envelope.
#pragma once

#include <juce_gui_basics/juce_gui_basics.h>

#include <array>
#include <vector>

class WaveView : public juce::Component
{
public:
    void setSamples(const std::vector<int16_t>& left, const std::vector<int16_t>& right, int sampleRate, const juce::String& caption);
    void setPlayPosition(double seconds) { if (seconds != playPos) { playPos = seconds; repaint(); } }
    void themeChanged() { cache = {}; repaint(); }
    void resized() override { cache = {}; }
    void paint(juce::Graphics&) override;

    // Sample and Output windows: loop markers (sample indices, -1 = none) and,
    // when selectable, a selection [selStart, selEnd) dragged with the mouse.
    enum class Tool { Select, PlaceStart, PlaceEnd };
    bool editable = false, selectable = false;
    Tool tool = Tool::Select;
    int32_t loopStart = -1, loopEnd = -1, selStart = -1, selEnd = -1;
    std::function<void()> onLoopChanged, onSelectionChanged;
    bool hasSelection() const { return selectable && selStart >= 0 && selEnd > selStart; }
    void mouseDown(const juce::MouseEvent&) override;
    void mouseDrag(const juce::MouseEvent&) override;
    void mouseUp(const juce::MouseEvent&) override;
private:
    juce::Rectangle<int> waveArea() const { return getLocalBounds().reduced(4).withTrimmedTop(13); }
    int32_t sampleAt(float x) const;
    float xOf(int32_t sample) const;
    int dragging = 0; // 1 loop start, 2 loop end, 3 selection
    int32_t anchor = 0;
    std::vector<int16_t> l, r;
    int rate = 44100;
    juce::String caption;
    double playPos = -1.0;
    juce::Image cache; // the waveform, drawn once per sound and size
    void buildCache();
};

// Draw-with-the-mouse table of 16-bit values (oscillator waves, shaper curve).
// With erasing set, dragging puts back eraseValue(i) instead of drawing.
class TableEditor : public juce::Component
{
public:
    std::vector<int16_t> values;
    std::function<void()> onEdited;
    bool erasing = false;
    std::function<int16_t(int)> eraseValue;
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

// The Harmonic window: harmonics 1..64 as bars, 0..100% (level 0..0x7FFF).
// The arrow drags one bar; the rake draws a line, and on release the
// harmonics under it that are above zero move to it (Alt: only those above
// the line; Alt+Shift: all of them, zeros included), as the manual describes.
class HarmonicEditor : public juce::Component
{
public:
    std::array<int16_t, 64> levels {};
    bool rake = false;
    std::function<void()> onEdited;
    void paint(juce::Graphics&) override;
    void mouseDown(const juce::MouseEvent&) override;
    void mouseDrag(const juce::MouseEvent&) override;
    void mouseUp(const juce::MouseEvent&) override;
private:
    juce::Rectangle<float> plot() const { return getLocalBounds().toFloat().reduced(4.0f, 6.0f).withTrimmedBottom(10.0f); }
    int barAt(float x) const;
    int levelAt(float y) const;
    float barX(int k) const; // left edge of harmonic k (0-based)
    bool raking = false;
    juce::Point<float> rakeFrom, rakeTo;
};

// The Oscillator's time line: each waveform sits at its time; click one to
// edit it, drag it to move it in time.
class WaveTimeline : public juce::Component, public juce::TooltipClient
{
public:
    struct Wave { int timeMs; const std::array<int16_t, 257>* samples; juce::String name; };
    std::vector<Wave> waves;
    int durationMs = 1000, selected = 0;
    std::function<void(int)> onSelect;          // index
    std::function<void(int, int)> onMoved;       // index, new time (ms), on release
    void paint(juce::Graphics&) override;
    void mouseDown(const juce::MouseEvent&) override;
    void mouseDrag(const juce::MouseEvent&) override;
    void mouseUp(const juce::MouseEvent&) override;
    juce::String getTooltip() override;
private:
    juce::Rectangle<float> boxFor(int i) const;
    float xFor(int ms) const;
    int msAt(float x) const;
    int hit(juce::Point<float>) const;
    int dragging = -1, dragTime = 0;
    float grabDx = 0.0f;
};

// An editor's tool palette, as in the original module windows: icons two to
// a row. A preset icon holds four tools, one in each quarter; tool codes run
// row by row across the quarters of a pair of icons.
class ToolPalette : public juce::Component, public juce::SettableTooltipClient
{
public:
    std::function<void(int)> onTool;
    std::vector<int> active; // mode tools shown pressed (pencil, eraser, waveform or harmonic mode)

    void clear() { items.clear(); tips.clear(); active.clear(); columns = 2; repaint(); }
    // Two preset icons side by side, codes first..first+7.
    // Preset icons side by side on one row; the tools run across the top
    // halves of the whole row, then the bottom halves (first .. first + 4n - 1).
    void addPresetRow(std::initializer_list<int> icons, int first);
    void addPresetPair(int iconL, int iconR, int first) { addPresetRow({ iconL, iconR }, first); }
    void addBlank() { items.push_back({}); }
    void remap(int item, int code) { if (item >= 0 && item < (int) items.size()) items[(size_t) item].codes[0] = code; }
    int columns = 2; // icons per row: 2, or 3 in the Oscillator's window
    // One preset icon on its own row, codes first..first+3.
    void addPresetIcon(int icon, int first);
    // An icon split down the middle into two tools (the loop-marker pair).
    void addSplitIcon(int icon, int leftCode, int rightCode) { items.push_back({ icon, { leftCode, rightCode, leftCode, rightCode }, true }); }
    // Whole-icon tools, two to a row, codes first, first+1, ...
    void addTools(std::initializer_list<int> icons, int first);
    void setTip(int code, const juce::String& tip) { tips.set(code, tip); }
    int rows() const { return (int) (items.size() + (size_t) columns - 1) / columns; }

    void paint(juce::Graphics&) override;
    void mouseMove(const juce::MouseEvent&) override;
    void mouseDown(const juce::MouseEvent&) override;
    void mouseUp(const juce::MouseEvent&) override { pressed = -1; repaint(); }

private:
    struct Item { int icon = 0; std::array<int, 4> codes { -1, -1, -1, -1 }; bool quarters = false; }; // TL TR BL BR
    std::vector<Item> items; // laid out two to a row; icon 0 leaves the slot empty
    juce::HashMap<int, juce::String> tips;
    int pressed = -1;
    float cell() const;
    juce::Rectangle<float> itemBounds(int i) const;
    int codeAt(juce::Point<float>, juce::Rectangle<float>* where = nullptr) const;
};
