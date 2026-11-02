// The patch window's content: a tool palette (arrow, patch cord, eraser and
// the module icons, all TurboSynth's own) and the patch itself, modules drawn
// as their original icons at their stored positions.
#pragma once

#include "Patch.h"

#include <juce_gui_basics/juce_gui_basics.h>

#include <set>
#include <vector>

class PatchView : public juce::Component, public juce::TooltipClient
{
public:
    turbosynth::Patch* patch = nullptr;
    int selected = 0; // 1-based module, 0 = none; in a group, the one the single-module commands act on
    std::set<int> alsoSelected; // the rest of a group: the original keeps a selected flag on each module (+0E)

    void select(int index) { selected = index; alsoSelected.clear(); }
    bool isSelected(int index) const;
    std::vector<int> selection() const; // every selected module in order, both halves of a stereo pair
    void setSelection(const std::vector<int>&);
    void selectAll(); // Edit > Select All (F15_00DCE)
    void nextTool();  // the Tab key: Arrow, Patch Cord, Eraser

    std::function<void(int)> onSelect;
    std::function<void()> onChanged;    // structure or positions changed
    std::function<void(int)> onPreview; // hear one module
    int memoryLevel() const; // the palette's Info icon: the memory level, 0 to 9 (see DocumentInfo)
    std::function<void(int)> onOpen;    // open a module (Info tool or double-click)
    std::function<void(int)> onRemoving; // a module is about to be erased
    std::function<void(turbosynth::Module&)> onNewModule; // a module about to be placed (defaults)
    std::function<void(int, int)> onConnected;            // a new cord, from and to (1-based)
    std::function<void(const char*)> onRefused;           // a cord the original refuses, with its message

    void paint(juce::Graphics&) override;
    void mouseDown(const juce::MouseEvent&) override;
    void mouseDrag(const juce::MouseEvent&) override;
    void mouseUp(const juce::MouseEvent&) override;
    void mouseDoubleClick(const juce::MouseEvent&) override;
    // Palette tools and modules name themselves on hover.
    juce::String getTooltip() override;

    static int iconFor(const turbosynth::Module&);
    // The other half of a stereo output jack or stereo sample (0 if none), and whether a module is an output.
    int partnerOf(int index) const;
    bool isOutput(int index) const;
    // The right edge of the visible patch area, in patch coordinates.
    int visibleRight() const { return (int) toPatch({ (float) getWidth(), 0.0f }).x; }

private:
    enum class Tool { Arrow, Cord, Eraser, Info, Hear, Place };
    struct PaletteItem { int icon; int type; juce::String tip; };
    std::vector<PaletteItem> palette() const;
    static constexpr int kPaletteWidth = 100; // three 33 px cells
    juce::Rectangle<int> paletteArea() const { return getLocalBounds().removeFromLeft(kPaletteWidth); }
    juce::Rectangle<float> paletteCell(int i) const;
    juce::Rectangle<float> iconBounds(int index) const;
    juce::Rectangle<float> labelBounds(int index) const;
    int moduleAt(juce::Point<float>) const;
    int cordAt(juce::Point<float>) const; // index into connections, or -1
    const char* cordRefusal(int from, int to) const; // null = allowed, "" = ignored, else the message
    juce::Point<float> toPatch(juce::Point<float>) const;
    void fit(); // choose scale and origin so the whole patch shows
    void showModuleMenu(int index);
    void place(int type, juce::Point<float> at);

    Tool tool = Tool::Arrow;
    int placeType = 0;
    float scale = 1.5f;
    juce::Point<float> origin { 100.0f, 10.0f };
    int dragging = 0;
    bool connecting = false;
    bool selecting = false; // dragging a selection box from empty space
    bool boxToggles = false; // with Shift the box toggles what it touches
    juce::Point<float> dragStart, dragNow;
    struct Held { int index; std::array<int16_t, 4> rect; };
    std::vector<Held> held; // the group being moved, where each was at the start
    void toggleSelected(int index);
};
