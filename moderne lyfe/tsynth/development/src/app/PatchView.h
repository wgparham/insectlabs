// The patch window's content: a tool palette (arrow, patch cord, eraser and
// the module icons, all TurboSynth's own) and the patch itself, modules drawn
// as their original icons at their stored positions.
#pragma once

#include "Patch.h"

#include <juce_gui_basics/juce_gui_basics.h>

class PatchView : public juce::Component, public juce::TooltipClient
{
public:
    turbosynth::Patch* patch = nullptr;
    int selected = 0; // 1-based module, 0 = none

    std::function<void(int)> onSelect;
    std::function<void()> onChanged;    // structure or positions changed
    std::function<void(int)> onPreview; // hear one module
    std::function<void(turbosynth::Module&)> onNewModule; // a module about to be placed (defaults)
    std::function<void(int, int)> onConnected;            // a new cord, from and to (1-based)

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
    enum class Tool { Arrow, Cord, Eraser, Place };
    struct PaletteItem { int icon; int type; juce::String tip; };
    std::vector<PaletteItem> palette() const;
    static constexpr int kPaletteWidth = 82;
    juce::Rectangle<int> paletteArea() const { return getLocalBounds().removeFromLeft(kPaletteWidth); }
    juce::Rectangle<float> paletteCell(int i) const;
    juce::Rectangle<float> iconBounds(int index) const;
    juce::Rectangle<float> labelBounds(int index) const;
    int moduleAt(juce::Point<float>) const;
    int cordAt(juce::Point<float>) const; // index into connections, or -1
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
    juce::Point<float> dragStart, dragNow;
    std::array<int16_t, 4> rectAtStart{}, partnerRectAtStart{};
};
