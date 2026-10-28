// Classic Macintosh look (System 6/7 era, as TurboSynth looked), with a dark
// variant: the same 1-bit drawing in light ink on charcoal.
#pragma once

#include <juce_gui_basics/juce_gui_basics.h>

namespace classic {

struct Theme {
    bool dark = false;
    juce::Colour paper, ink, gray, desktopA, desktopB, trace, accent;
};

const Theme& theme();
void setDark(bool dark);

juce::Font uiFont(float size = 12.0f, bool bold = true);  // Chicago-like system font
juce::Font smallFont(float size = 10.0f);                   // Geneva-like

// One of the original 32x32 icons, scaled to fit r (pixel-crisp at whole multiples).
void drawIcon(juce::Graphics&, int id, juce::Rectangle<float> r, bool inverted = false);
bool hasIcon(int id);

// 50% dither desktop.
void fillDesktop(juce::Graphics&, juce::Rectangle<int>);
// A document window: 1-pixel frame, drop shadow, pinstriped title bar.
// Returns the content area.
juce::Rectangle<int> drawWindow(juce::Graphics&, juce::Rectangle<int> frame, const juce::String& title, bool active = true);
constexpr int kTitleHeight = 19;

class LookAndFeel : public juce::LookAndFeel_V4
{
public:
    LookAndFeel();
    void refresh(); // re-read the theme's colours

    juce::Font getTextButtonFont(juce::TextButton&, int) override { return uiFont(12.0f); }
    juce::Font getLabelFont(juce::Label&) override { return smallFont(11.0f); }
    juce::Font getComboBoxFont(juce::ComboBox&) override { return uiFont(12.0f); }
    juce::Font getPopupMenuFont() override { return uiFont(12.0f); }
    juce::Font getMenuBarFont(juce::MenuBarComponent&, int, const juce::String&) override { return uiFont(12.0f); }

    void drawButtonBackground(juce::Graphics&, juce::Button&, const juce::Colour&, bool over, bool down) override;
    void drawButtonText(juce::Graphics&, juce::TextButton&, bool over, bool down) override;
    void drawToggleButton(juce::Graphics&, juce::ToggleButton&, bool over, bool down) override;
    void drawLinearSlider(juce::Graphics&, int x, int y, int w, int h, float pos, float minPos, float maxPos,
                          juce::Slider::SliderStyle, juce::Slider&) override;
    void drawComboBox(juce::Graphics&, int w, int h, bool down, int bx, int by, int bw, int bh, juce::ComboBox&) override;
    void drawPopupMenuBackground(juce::Graphics&, int w, int h) override;
    void drawPopupMenuItem(juce::Graphics&, const juce::Rectangle<int>&, bool isSeparator, bool isActive, bool isHighlighted,
                           bool isTicked, bool hasSubMenu, const juce::String& text, const juce::String& shortcut,
                           const juce::Drawable* icon, const juce::Colour* textColour) override;
    void drawMenuBarBackground(juce::Graphics&, int w, int h, bool, juce::MenuBarComponent&) override;
    void drawMenuBarItem(juce::Graphics&, int w, int h, int index, const juce::String& text, bool over, bool open,
                         bool isMouseOverBar, juce::MenuBarComponent&) override;
    void fillTextEditorBackground(juce::Graphics&, int w, int h, juce::TextEditor&) override;
    void drawTextEditorOutline(juce::Graphics&, int w, int h, juce::TextEditor&) override;
    void drawScrollbar(juce::Graphics&, juce::ScrollBar&, int x, int y, int w, int h, bool vertical, int thumbStart,
                       int thumbSize, bool over, bool down) override;
    int getPopupMenuBorderSize() override { return 1; }
};

} // namespace classic
