// The document's Info box, the original's dialog 10004 (File > Get Info): the
// samples used and free, the memory indicator, and the Compact Memory and
// Release Clipboard buttons. The indicator has ten levels (0 to 9); level 9 is
// the original's "Lo!" icon.
#pragma once

#include <juce_gui_basics/juce_gui_basics.h>

#include <cstdint>
#include <functional>

class DocumentInfo : public juce::Component
{
public:
    // Model 1 stand-in for the Mac's free memory at launch: 32 MB of 16-bit samples (16,777,216 samples).
    static constexpr int64_t kModel1Budget = 32LL * 1024 * 1024 / 2;

    // The original's message when the memory cannot take a new module, paste or longer length.
    static constexpr const char* kMemoryMessage = "Memory cannot be allocated for some or all modules.  Perhaps you can reduce usage elsewhere.";

    // The original's message while memory is low (ALRT 115), and how often it can come back: 7200 ticks.
    static constexpr const char* kLowMessage = "Memory space is low.  Some commands may be disabled.";
    static constexpr int kLowAlertSeconds = 120;

    // A waveform takes memory that "samples used" doesn't count: 100,112 samples for 200, measured in the emulator.
    static constexpr int64_t kWaveformCost = 500;

    // Memory is low with less than MacApp's reserve free: the mem! resources 0 and 1000 add up to 44,096 bytes.
    // The emulator agrees, with 29,840 samples free not low and 13,656 low.
    static constexpr int64_t kLowReserve = 44096 / 2;

    // The samples the modules use: the sum of their lengths.
    template <class Modules>
    static int64_t samplesUsed(const Modules& modules)
    {
        int64_t used = 0;
        for (const auto& m : modules) used += m.length;
        return used;
    }

    // The samples left in the budget, after the modules and their waveforms.
    template <class Modules>
    static int64_t samplesFree(const Modules& modules)
    {
        int64_t waves = 0;
        for (const auto& m : modules) waves += (int64_t) m.waveforms.size();
        return kModel1Budget - samplesUsed(modules) - waves * kWaveformCost;
    }

    template <class Modules>
    static bool spaceIsLow(const Modules& modules) { return samplesFree(modules) < kLowReserve; }

    DocumentInfo();

    // used and free are in samples (1 sample = 2 bytes), as the original shows them.
    void setCounts(int64_t used, int64_t free);

    // The original's indicator level for a use of used out of total: round(10 × used / total), at most 9.
    static int levelOf(int64_t used, int64_t total);

    std::function<void()> onCompact, onReleaseClipboard;

    void paint(juce::Graphics&) override;
    void resized() override;

private:
    int64_t used = 0;
    juce::Label usedLabel, freeLabel;
    juce::TextButton compact { "Compact Memory" }, release { "Release Clipboard" };
};
