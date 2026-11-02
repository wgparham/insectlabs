// Everything the original does to a patch outside synthesis: SmartSynth, the
// break point editors, time slices, copy and clear, FM patching and New.
// docs/RE-NOTES.md has the details; tools/re/refrender.cpp --edit-test compares
// each routine here with the original's 68K code.
#pragma once

#include "Engine.h"
#include "Patch.h"

#include <array>
#include <cstdint>

namespace softsynth {

// x of the record that ends a list.
constexpr int kEndX = 10000;
// Most amp, frequency and time slice segments the editors allow (F00CAE8).
constexpr int kMaxAmpSegments = 40, kMaxFreqSegments = 15, kMaxSlices = 40;
// A break point level, or a timbre event's fader, runs to this. A partial's fader
// runs to kFaderMax.
constexpr int kLevelMax = 103, kFaderMax = 63;

enum class Graph { Amp = 1, Freq = 2, Harmonics = 3, TimeSlices = 4 };

// The harmonic mix curve the time slice screen edits, at -0xEE0(A4) in the original.
struct SliceList {
    int16_t count = 0;
    std::array<int16_t, 42> y{}, x{};
};

// Record fields, by partial.
int16_t ampX(const Patch&, int partial, int index);
int16_t ampY(const Patch&, int partial, int index);
int16_t freqX(const Patch&, int partial, int index);
int16_t freqY(const Patch&, int partial, int index);
void setAmpPoint(Patch&, int partial, int index, int y, int x);
void setFreqPoint(Patch&, int partial, int index, int y, int x);
int16_t lastAmp(const Patch&, int partial);
int16_t lastFreq(const Patch&, int partial);

// ---- SmartSynth ---------------------------------------------------------------

// SmartSynth > Set up. 0 is a heading switched off; otherwise the option, in the
// dialog's order, from 1. The defaults are the startup code's (0x8A0).
struct SmartSynthSettings {
    int harmonicSeries = 1;    // All, Odd, Even
    int partialDetuning = 0;   // Slight, Medium, Extreme
    int attackRate = 1;        // Fast, Medium, Slow
    int decayRate = 1;         // Fast, Medium, Slow
    int harmonicRange = 2;     // High, Medium, Low
    int doubling = 0;          // Slight, Medium, Extreme
    int frequencyAttack = 0;   // Bend, Bite, Blip
    int secondaryLevel = 1;    // High, Medium, Low
    int harmonicFilter = 2;    // Slight, Medium, Extreme
    int frequencyMovement = 0; // Slight, Medium, Extreme
    int percussion = 0;        // Dink, Bump, Click
    int fade = 1;              // Equal rate, Highs first, Lows first
};

// The Set up dialog's rules (F003E10), by group in the order above. Turning a
// heading on selects its first option. Frequency attack and Frequency movement
// switch each other off.
class SmartSynthDialog {
public:
    SmartSynthSettings settings;
    bool isHeadingOn(int group) const;
    void clickHeading(int group);
    void clickOption(int group, int option); // 1..3; ignored while its heading is off
};

// F003FD4. It edits the patch in hand: anything a rule doesn't touch stays, so the
// result depends on what was there.
void smartSynthGenerate(Patch&, const SmartSynthSettings&, XbiosRandom&);

// ---- File > New ---------------------------------------------------------------

enum class Sampler {
    AkaiS612, AkaiS700, AkaiS900, CasioFZ1, EmuEmax, EmuSP1200, EnsoniqMirage, KorgDSS1,
    OberheimDPX1, RolandMKS100, RolandS10, RolandS220, RolandS50, SequentialP2000, MmaSds
};

// F00216A and F000A0C. The rate follows the selected sampler, except the DPX-1, which
// has no entry and keeps the patch's. Unused records keep their bytes, as they do in
// the original, which reuses its patch memory: pass the patch in hand.
void newPatch(Patch&, Sampler);
void initPartials(Patch&);

// ---- Break points -------------------------------------------------------------

// F00C9C8: finds the place for x in records 1 to last + 1. True when a record already
// sits at x. `index` is that record, or the one to insert before, or 0 past the end.
bool findPoint(const Patch&, const SliceList&, Graph, int partial, int x, int16_t& index);

// F00CAE8. Moves records up to open `index`, shifting x and y only. When the graph is
// full and `force` is off, returns false and sets index to 0, which is the original's
// "you have used all the break points" alert. `force` drops the last record instead.
// Graph::TimeSlices inserts into every partial at x `sliceX`, with y from the partial's
// own curve. Graph::Freq ignores `force`.
bool insertPoint(Patch&, SliceList&, Graph, int partial, int16_t& index, bool force, int sliceX = kEndX);

// F00D296. Graph::TimeSlices acts on every partial.
void removePoint(Patch&, SliceList&, Graph, int partial, int index);

// F00DD8E, F00DECA: the curve's y at x, through the original's float library. With no
// record at or past x they return last + 2, as the original's do.
int16_t ampAt(const Patch&, int partial, int x);
int16_t harmonicAt(const SliceList&, int x);

// ---- Time slices --------------------------------------------------------------

// F00D43E, on entering the time slice screen: partial data to timbre events.
void partialsToSlices(Patch&, SliceList&);
// F00D89A, on leaving it. False when a partial's list was full (the alert again).
bool slicesToPartials(Patch&, SliceList&);
// The end of a timbre event's fader drag (0x15A4): sets y at `sliceX`, if the partial
// has a record there.
void setSliceLevel(Patch&, int partial, int sliceX, int level);
// The end of F00DBFE: moves timbre event `index` to x in every partial.
void moveSlice(Patch&, int index, int x);

// ---- Edit > Copy and Paste, Clear ---------------------------------------------

struct PartialFields {
    bool ampEnvelope = true, frequencyContour = true, waveType = false, frequencyRatio = false, faderLevel = false;
};

// F00E1F6, F00E43A. Numbers are as typed, from 1, and are checked as the original
// checks them: a bad "from" or range start means partial 1, a bad range end is pulled
// into range. A blank "through" field reads as 0.
void copyPartials(Patch&, const PartialFields&, int from, int first, int last);
void clearPartials(Patch&, const PartialFields&, int first, int last);
// F00E67C, F00E7C2: the same for timbre events. A bad "from" or start means record 0;
// a bad end means the last event.
void copyTimbre(Patch&, int from, int first, int last);
void clearTimbre(Patch&, int first, int last);

// ---- Edit > FM Patching (F0027E0) ---------------------------------------------

bool isModulator(const Patch&, int partial); // F009736
void fmSet(Patch&, int modulator, int target);
void fmClear(Patch&, int modulator, int target);
void fmClearAll(Patch&);
void fmToggleMix(Patch&, int partial);

} // namespace softsynth
