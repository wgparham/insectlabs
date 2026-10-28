// One of the 13 table views: shows a table, edits it by drawing, and offers
// the original Alter and Fill menus on right-click.
#pragma once

#include "Tables.h"

#include <juce_gui_extra/juce_gui_extra.h>

class TableView : public juce::Component
{
public:
    explicit TableView(int tableId);

    int tableId() const { return id; }
    void setSelected(bool s) { selected = s; repaint(); }

    std::function<std::vector<float>*()> table;   // the table to show and edit
    std::function<void(bool finished)> onEdited;   // after a change
    std::function<void(TableView&)> onSelected;
    std::function<double()> position;             // 0..1 play position marker, or < 0

    void paint(juce::Graphics&) override;
    void mouseDown(const juce::MouseEvent&) override;
    void mouseDrag(const juce::MouseEvent&) override;
    void mouseUp(const juce::MouseEvent&) override;
    bool keyPressed(const juce::KeyPress&) override;

    static std::vector<float> clipboard; // shared table clipboard

private:
    juce::Rectangle<float> plotArea() const;
    void drawTo(juce::Point<float> p);
    void showMenu();
    void apply(const std::function<void(std::vector<float>&)>& op);
    void askNumbers(const juce::String& title, const juce::StringArray& labels, const juce::StringArray& defaults,
                    std::function<void(const juce::StringArray&)> done);

    int id;
    float viewMin, viewMax;
    bool selected = false;
    int lastIndex = -1;
    float lastValue = 0.0f;
    std::unique_ptr<juce::FileChooser> chooser;
};
