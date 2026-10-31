// The Parameter Manager's rate and length rules, as the modules' change
// handlers (CODE 23, selector F18_00E70) apply them: a module's new length
// reaches the modules it feeds (message 10), which may take it, scale it
// (Dynamic length) or ignore it; a new rate travels on when Inherit sample
// rate is on (message 9). docs/RE-NOTES.md has the details.
#pragma once

#include "Patch.h"

namespace turbosynth::rules {

struct Options {
    bool inheritRate = false;    // Parameter Manager item 8  (-$4FB)
    bool inheritLength = false;  // item 14 (-$4FD)
    bool dynamicLength = false;  // item 15 (-$4FE)
};

// The length of module `index` (1-based) was set (message 5): tell the modules it feeds.
void lengthChanged(Patch& patch, int index, const Options& o);
// The input length reaching `index` changed (message 10).
void inputLengthChanged(Patch& patch, int index, int32_t inputLength, const Options& o);
// The rate of `index` was set (message 4): with Inherit sample rate, it travels on.
void rateChanged(Patch& patch, int index, const Options& o);
// A cord from `from` to `to` was made (F16_047C2): message 11, then 10 and 9 as the options say.
void connected(Patch& patch, int from, int to, const Options& o);
// A parameter of a Delay, Stretcher, Pitch Shifter or Diffuser changed: its
// window resends the input length when Dynamic length is on and it has one input.
void parametersChanged(Patch& patch, int index, const Options& o);

} // namespace turbosynth::rules
