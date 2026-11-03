// Softsynth parameter files (.PRM), read the way F003116 reads them.
// docs/RE-NOTES.md has the format.
#pragma once

#include <array>
#include <cstdint>
#include <string>
#include <vector>

namespace softsynth {

constexpr int kPartials = 32;
constexpr size_t kPartialSize = 956;
constexpr size_t kPartialBytes = kPartials * kPartialSize;
constexpr size_t kFileSize = 0x780C;

// Offsets within a partial.
constexpr size_t kScale = 0x000, kWave = 0x001, kFader = 0x002, kRatio = 0x004;
constexpr size_t kLastAmp = 0x008, kAmpRecords = 0x00A, kLastFreq = 0x2AA, kFreqRecords = 0x2AC;
// Offsets within a break point record.
constexpr size_t kRecTime = 0, kRecValue = 4, kRecSlope = 8, kRecY = 12, kRecX = 14, kRecSize = 16;
constexpr int kAmpRecordCount = 42, kFreqRecordCount = 17;

inline uint32_t readBe32(const uint8_t* p) { return (uint32_t) p[0] << 24 | p[1] << 16 | p[2] << 8 | p[3]; }
inline int16_t readBe16(const uint8_t* p) { return (int16_t) (p[0] << 8 | p[1]); }
inline void writeBe32(uint8_t* p, uint32_t v) { p[0] = (uint8_t) (v >> 24); p[1] = (uint8_t) (v >> 16); p[2] = (uint8_t) (v >> 8); p[3] = (uint8_t) v; }
inline void writeBe16(uint8_t* p, uint16_t v) { p[0] = (uint8_t) (v >> 8); p[1] = (uint8_t) v; }

struct Patch {
    // The partials stay as the file's bytes. The kernels walk break point records
    // by address and can read past the ones in use, so the layout is the behaviour.
    std::vector<uint8_t> partials = std::vector<uint8_t>(kPartialBytes, 0);
    std::array<uint8_t, 0x40> nameField{}; // Pascal string; bytes after it are kept
    int32_t length = 0, rate = 0;          // samples, Hz
    int32_t frequency = 0;                 // 16.16 Hz
    std::array<uint8_t, 0x40> routing{};   // per partial: in the mix, modulator (1-based)

    uint8_t* partial(int i) { return partials.data() + (size_t) i * kPartialSize; }
    const uint8_t* partial(int i) const { return partials.data() + (size_t) i * kPartialSize; }
    int fader(int i) const { return readBe16(partial(i) + kFader); }
    int inMix(int i) const { return (int8_t) routing[(size_t) i * 2]; }
    int modulator(int i) const { return (int8_t) routing[(size_t) i * 2 + 1]; }
    std::string name() const;
};

bool parsePatch(const std::vector<uint8_t>& data, Patch& out, std::string* error);
std::vector<uint8_t> writePatch(const Patch& patch);
bool loadPatch(const std::string& path, Patch& out, std::string* error);
bool savePatch(const std::string& path, const Patch& patch, std::string* error);

} // namespace softsynth
