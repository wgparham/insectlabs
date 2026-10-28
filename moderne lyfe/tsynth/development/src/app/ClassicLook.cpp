#include "ClassicLook.h"
#include "ClassicIcons.h"

namespace classic {

namespace {

Theme makeTheme(bool dark)
{
    Theme t;
    t.dark = dark;
    if (!dark) {
        t.paper = juce::Colours::white;
        t.ink = juce::Colours::black;
        t.gray = juce::Colour(0xff808080);
        t.desktopA = juce::Colours::white;
        t.desktopB = juce::Colours::black;
        t.trace = juce::Colours::black;
        t.accent = juce::Colours::black;
    } else {
        t.paper = juce::Colour(0xff1d1d1b);
        t.ink = juce::Colour(0xffe6e2d6);   // warm, like a phosphor-free night mode
        t.gray = juce::Colour(0xff6b6a66);
        t.desktopA = juce::Colour(0xff2b2b29);
        t.desktopB = juce::Colour(0xff3a3a37);
        t.trace = juce::Colour(0xffe6e2d6);
        t.accent = juce::Colour(0xffe6e2d6);
    }
    return t;
}

Theme current = makeTheme(false);

const IconEntry* findIcon(int id)
{
    for (int i = 0; i < kIconCount; ++i) if (kIcons[i].id == id) return &kIcons[i];
    return nullptr;
}

juce::String pickFont(std::initializer_list<const char*> names)
{
    const auto all = juce::Font::findAllTypefaceNames();
    for (const char* n : names) if (all.contains(n)) return n;
    return juce::Font::getDefaultSansSerifFontName();
}

} // namespace

const Theme& theme() { return current; }
void setDark(bool dark) { current = makeTheme(dark); }

juce::Font uiFont(float size, bool bold)
{
    static const juce::String name = pickFont({ "ChicagoFLF", "Chicago", "Charcoal", "Geneva", "Microsoft Sans Serif", "Tahoma" });
    return juce::Font(juce::FontOptions(name, size, bold ? juce::Font::bold : juce::Font::plain));
}

juce::Font smallFont(float size)
{
    static const juce::String name = pickFont({ "Geneva", "Microsoft Sans Serif", "Tahoma", "Verdana" });
    return juce::Font(juce::FontOptions(name, size, juce::Font::plain));
}

bool hasIcon(int id) { return findIcon(id) != nullptr; }

void drawIcon(juce::Graphics& g, int id, juce::Rectangle<float> r, bool inverted)
{
    const auto* icon = findIcon(id);
    if (!icon) return;
    const float px = juce::jmax(1.0f, std::floor(juce::jmin(r.getWidth(), r.getHeight()) / 32.0f));
    const float x0 = std::round(r.getCentreX() - 16.0f * px), y0 = std::round(r.getCentreY() - 16.0f * px);
    if (inverted) { g.setColour(current.ink); g.fillRect(x0, y0, 32.0f * px, 32.0f * px); }
    g.setColour(inverted ? current.paper : current.ink);
    for (int y = 0; y < 32; ++y)
        for (int x = 0; x < 32; ++x)
            if (icon->bits[y * 4 + x / 8] & (0x80 >> (x % 8)))
                g.fillRect(x0 + (float) x * px, y0 + (float) y * px, px, px);
}

void fillDesktop(juce::Graphics& g, juce::Rectangle<int> r)
{
    g.setColour(current.desktopA);
    g.fillRect(r);
    g.setColour(current.desktopB);
    for (int y = r.getY(); y < r.getBottom(); ++y)
        for (int x = r.getX() + ((y & 1) ? 1 : 0); x < r.getRight(); x += 2)
            g.fillRect(x, y, 1, 1);
}

juce::Rectangle<int> drawWindow(juce::Graphics& g, juce::Rectangle<int> f, const juce::String& title, bool active)
{
    g.setColour(current.ink);
    g.fillRect(f.translated(1, 1)); // drop shadow
    g.setColour(current.paper);
    g.fillRect(f);
    g.setColour(current.ink);
    g.drawRect(f, 1);
    auto bar = f.reduced(1).removeFromTop(kTitleHeight - 1);
    g.drawHorizontalLine(f.getY() + kTitleHeight, (float) f.getX(), (float) f.getRight());
    if (active)
        for (int y = bar.getY() + 3; y < bar.getBottom() - 2; y += 2)
            g.drawHorizontalLine(y, (float) bar.getX() + 1, (float) bar.getRight() - 1);
    // Close box on the left, as in System 6/7.
    const juce::Rectangle<int> close(bar.getX() + 8, bar.getCentreY() - 5, 11, 11);
    if (active) { g.setColour(current.paper); g.fillRect(close.expanded(2, 0)); g.setColour(current.ink); g.drawRect(close, 1); }
    const auto font = uiFont(12.0f);
    const int tw = juce::GlyphArrangement::getStringWidthInt(font, title) + 16;
    const juce::Rectangle<int> label(bar.getCentreX() - tw / 2, bar.getY(), tw, bar.getHeight());
    g.setColour(current.paper);
    g.fillRect(label);
    g.setColour(current.ink);
    g.setFont(font);
    g.drawText(title, label, juce::Justification::centred);
    return f.reduced(1).withTrimmedTop(kTitleHeight);
}

// ---------------------------------------------------------------------------
LookAndFeel::LookAndFeel() { refresh(); }

void LookAndFeel::refresh()
{
    const auto& t = current;
    setColour(juce::ResizableWindow::backgroundColourId, t.paper);
    setColour(juce::Label::textColourId, t.ink);
    setColour(juce::TextButton::textColourOffId, t.ink);
    setColour(juce::TextButton::textColourOnId, t.paper);
    setColour(juce::ToggleButton::textColourId, t.ink);
    setColour(juce::TextEditor::textColourId, t.ink);
    setColour(juce::TextEditor::backgroundColourId, t.paper);
    setColour(juce::TextEditor::highlightColourId, t.ink);
    setColour(juce::TextEditor::highlightedTextColourId, t.paper);
    setColour(juce::CaretComponent::caretColourId, t.ink);
    setColour(juce::ComboBox::textColourId, t.ink);
    setColour(juce::ComboBox::backgroundColourId, t.paper);
    setColour(juce::PopupMenu::textColourId, t.ink);
    setColour(juce::PopupMenu::backgroundColourId, t.paper);
    setColour(juce::PopupMenu::highlightedBackgroundColourId, t.ink);
    setColour(juce::PopupMenu::highlightedTextColourId, t.paper);
    setColour(juce::Slider::textBoxTextColourId, t.ink);
    setColour(juce::Slider::textBoxBackgroundColourId, t.paper);
    setColour(juce::Slider::textBoxOutlineColourId, t.ink);
    setColour(juce::ListBox::backgroundColourId, t.paper);
    setColour(juce::AlertWindow::backgroundColourId, t.paper);
    setColour(juce::AlertWindow::textColourId, t.ink);
    setColour(juce::AlertWindow::outlineColourId, t.ink);
    setColour(juce::DocumentWindow::textColourId, t.ink);
    setColour(juce::ScrollBar::thumbColourId, t.paper);
    setColour(juce::ScrollBar::trackColourId, t.gray);
}

void LookAndFeel::drawButtonBackground(juce::Graphics& g, juce::Button& b, const juce::Colour&, bool, bool down)
{
    const auto r = b.getLocalBounds().toFloat().reduced(1.0f);
    const bool lit = down || b.getToggleState();
    g.setColour(lit ? current.ink : current.paper);
    g.fillRoundedRectangle(r, 6.0f);
    g.setColour(current.ink);
    g.drawRoundedRectangle(r, 6.0f, 1.0f);
    if (b.isEnabled() == false) { g.setColour(current.paper.withAlpha(0.5f)); g.fillRoundedRectangle(r, 6.0f); }
}

void LookAndFeel::drawButtonText(juce::Graphics& g, juce::TextButton& b, bool, bool down)
{
    g.setColour(down || b.getToggleState() ? current.paper : current.ink);
    if (!b.isEnabled()) g.setColour(current.gray);
    g.setFont(uiFont(12.0f));
    g.drawText(b.getButtonText(), b.getLocalBounds(), juce::Justification::centred);
}

void LookAndFeel::drawToggleButton(juce::Graphics& g, juce::ToggleButton& b, bool, bool down)
{
    const juce::Rectangle<float> box(2.0f, (float) b.getHeight() / 2.0f - 6.0f, 12.0f, 12.0f);
    g.setColour(down ? current.ink : current.paper);
    g.fillRect(box);
    g.setColour(current.ink);
    g.drawRect(box, 1.0f);
    if (b.getToggleState()) {
        g.drawLine(box.getX(), box.getY(), box.getRight(), box.getBottom(), 1.0f);
        g.drawLine(box.getRight(), box.getY(), box.getX(), box.getBottom(), 1.0f);
    }
    g.setFont(uiFont(12.0f));
    g.drawText(b.getButtonText(), b.getLocalBounds().withTrimmedLeft(20), juce::Justification::centredLeft);
}

void LookAndFeel::drawLinearSlider(juce::Graphics& g, int x, int y, int w, int h, float pos, float, float,
                                   juce::Slider::SliderStyle, juce::Slider&)
{
    // A classic scroll bar: dithered gray track with a white thumb box.
    const juce::Rectangle<int> track(x, y + h / 2 - 7, w, 15);
    g.setColour(current.paper);
    g.fillRect(track);
    g.setColour(current.ink);
    for (int yy = track.getY() + 1; yy < track.getBottom() - 1; ++yy)
        for (int xx = track.getX() + 1 + ((yy & 1) ? 1 : 0); xx < track.getRight() - 1; xx += 2)
            g.fillRect(xx, yy, 1, 1);
    g.drawRect(track, 1);
    const juce::Rectangle<int> thumb((int) pos - 7, track.getY(), 15, track.getHeight());
    g.setColour(current.paper);
    g.fillRect(thumb);
    g.setColour(current.ink);
    g.drawRect(thumb, 1);
}

void LookAndFeel::drawComboBox(juce::Graphics& g, int w, int h, bool, int, int, int, int, juce::ComboBox&)
{
    const juce::Rectangle<int> r(0, 0, w - 1, h - 1);
    g.setColour(current.ink);
    g.fillRect(r.translated(1, 1));
    g.setColour(current.paper);
    g.fillRect(r);
    g.setColour(current.ink);
    g.drawRect(r, 1);
    juce::Path tri;
    const float cx = (float) w - 12.0f, cy = (float) h / 2.0f;
    tri.addTriangle(cx - 5, cy - 2, cx + 5, cy - 2, cx, cy + 3);
    g.fillPath(tri);
}

void LookAndFeel::drawPopupMenuBackground(juce::Graphics& g, int w, int h)
{
    g.fillAll(current.paper);
    g.setColour(current.ink);
    g.drawRect(0, 0, w, h, 1);
}

void LookAndFeel::drawPopupMenuItem(juce::Graphics& g, const juce::Rectangle<int>& area, bool isSeparator, bool isActive,
                                    bool isHighlighted, bool isTicked, bool, const juce::String& text,
                                    const juce::String& shortcut, const juce::Drawable*, const juce::Colour*)
{
    if (isSeparator) {
        g.setColour(current.ink);
        for (int x = area.getX() + 1; x < area.getRight() - 1; x += 2) g.fillRect(x, area.getCentreY(), 1, 1);
        return;
    }
    const bool hi = isHighlighted && isActive;
    if (hi) { g.setColour(current.ink); g.fillRect(area); }
    g.setColour(hi ? current.paper : (isActive ? current.ink : current.gray));
    g.setFont(uiFont(12.0f));
    auto r = area.reduced(4, 0);
    if (isTicked) g.drawText(juce::String::charToString((juce::juce_wchar) 0x2713), r.removeFromLeft(14), juce::Justification::centred);
    else r.removeFromLeft(14);
    g.drawText(text, r, juce::Justification::centredLeft);
    if (shortcut.isNotEmpty()) g.drawText(shortcut, r, juce::Justification::centredRight);
}

void LookAndFeel::drawMenuBarBackground(juce::Graphics& g, int w, int h, bool, juce::MenuBarComponent&)
{
    g.fillAll(current.paper);
    g.setColour(current.ink);
    g.drawHorizontalLine(h - 1, 0.0f, (float) w);
}

void LookAndFeel::drawMenuBarItem(juce::Graphics& g, int w, int h, int, const juce::String& text, bool over, bool open,
                                  bool, juce::MenuBarComponent&)
{
    if (open || over) { g.setColour(current.ink); g.fillRect(0, 0, w, h - 1); }
    g.setColour(open || over ? current.paper : current.ink);
    g.setFont(uiFont(12.0f));
    g.drawText(text, 0, 0, w, h - 1, juce::Justification::centred);
}

void LookAndFeel::fillTextEditorBackground(juce::Graphics& g, int, int, juce::TextEditor&) { g.fillAll(current.paper); }
void LookAndFeel::drawTextEditorOutline(juce::Graphics& g, int w, int h, juce::TextEditor&)
{
    g.setColour(current.ink);
    g.drawRect(0, 0, w, h, 1);
}

void LookAndFeel::drawScrollbar(juce::Graphics& g, juce::ScrollBar&, int x, int y, int w, int h, bool vertical,
                                int thumbStart, int thumbSize, bool, bool)
{
    g.setColour(current.paper);
    g.fillRect(x, y, w, h);
    g.setColour(current.ink);
    for (int yy = y; yy < y + h; ++yy)
        for (int xx = x + ((yy & 1) ? 1 : 0); xx < x + w; xx += 2) g.fillRect(xx, yy, 1, 1);
    g.drawRect(x, y, w, h, 1);
    const juce::Rectangle<int> thumb = vertical ? juce::Rectangle<int>(x, thumbStart, w, thumbSize) : juce::Rectangle<int>(thumbStart, y, thumbSize, h);
    g.setColour(current.paper);
    g.fillRect(thumb);
    g.setColour(current.ink);
    g.drawRect(thumb, 1);
}

} // namespace classic
