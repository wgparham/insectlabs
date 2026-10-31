#include "DocumentInfo.h"

#include "ClassicLook.h"

#include <algorithm>

DocumentInfo::DocumentInfo()
{
    for (auto* l : { &usedLabel, &freeLabel }) {
        l->setFont(classic::uiFont(12.0f, false));
        addAndMakeVisible(*l);
    }
    compact.onClick = [this] { if (onCompact) onCompact(); };
    release.onClick = [this] { if (onReleaseClipboard) onReleaseClipboard(); };
    addAndMakeVisible(compact);
    addAndMakeVisible(release);
}

int DocumentInfo::levelOf(int64_t used, int64_t total)
{
    if (total <= 0 || used <= 0) return 0;
    const int64_t level = (20 * used + total) / (2 * total); // round(10 × used / total)
    return (int) std::min<int64_t>(9, level);
}

void DocumentInfo::setCounts(int64_t u, int64_t b)
{
    used = u;
    budget = juce::jmax<int64_t>(1, b);
    const int64_t freeSamples = juce::jmax<int64_t>(0, budget - used);
    usedLabel.setText("Memory used: " + juce::String(used) + " Samples", juce::dontSendNotification);
    freeLabel.setText("Memory free: " + juce::String(freeSamples) + " Samples", juce::dontSendNotification);
    repaint();
}

void DocumentInfo::resized()
{
    auto area = getLocalBounds().reduced(12);
    usedLabel.setBounds(area.removeFromTop(20));
    freeLabel.setBounds(area.removeFromTop(20));
    area.removeFromTop(64); // the indicator is drawn here
    auto buttons = area.removeFromBottom(28);
    compact.setBounds(buttons.removeFromLeft(140));
    buttons.removeFromLeft(12);
    release.setBounds(buttons.removeFromLeft(140));
}

void DocumentInfo::paint(juce::Graphics& g)
{
    const auto& t = classic::theme();
    g.fillAll(t.paper);
    g.setColour(t.ink);
    g.setFont(classic::uiFont(12.0f, false));

    // The indicator: ten cells that fill as memory is used. "Lo!" when the top level is reached.
    auto cells = getLocalBounds().reduced(12).withTrimmedTop(44).withHeight(40).withTrimmedBottom(0);
    const int level = levelOf(used, budget);
    const float cellW = cells.getWidth() / 10.0f;
    for (int i = 0; i < 10; ++i) {
        juce::Rectangle<float> cell(cells.getX() + i * cellW, (float) cells.getY(), cellW - 2.0f, (float) cells.getHeight());
        g.setColour(t.ink);
        g.drawRect(cell, 1.0f);
        if (i < level) g.fillRect(cell.reduced(2.0f));
    }
    if (level >= 9) {
        g.setColour(t.ink);
        g.drawText("Lo!", juce::Rectangle<int>(cells.getRight() - 40, cells.getY() - 16, 40, 14), juce::Justification::centredRight, false);
    }
}
