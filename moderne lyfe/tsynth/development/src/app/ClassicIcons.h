// TurboSynth's original 1-bit icons (ICON resources), drawn in the theme's ink.
#pragma once

#include <cstdint>

namespace classic {
struct IconEntry { int id; uint8_t bits[128]; };
extern const IconEntry kIcons[];
extern const int kIconCount;
} // namespace classic
