// Shared colours: laboratory test equipment, in the same family as the
// Cloud Generator port (dark chassis, silkscreen legends, phosphor screens).
// The original's pale-green panels survive as the trace colour.
#pragma once

#include <juce_gui_basics/juce_gui_basics.h>

namespace look {
const juce::Colour panel  { 0xff2a2d2b };  // chassis
const juce::Colour bezel  { 0xff383c39 };
const juce::Colour legend { 0xffd8d2c0 };  // silkscreen lettering
const juce::Colour accent { 0xffe0a040 };  // amber indicator
const juce::Colour record { 0xffc03028 };  // the original's recRed, brightened
const juce::Colour screen { 0xff0b130e };
const juce::Colour trace  { 0xffcdfacd };  // PG's bAndKGreen as phosphor
const juce::Colour grid   { 0xff1c3022 };
}
