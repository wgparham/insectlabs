// TurboSynth documents ("TRBD" files): the module network and every
// module's settings, read the way GReadFile (CODE 9) reads them.
#pragma once

#include <array>
#include <cstdint>
#include <string>
#include <vector>

namespace turbosynth {

// Module type codes as stored in documents (the DSP card programs use the
// same numbers).
enum ModuleType : int16_t {
    kOscillator = 5, kSample = 6, kAmpEnv = 7, kFilter = 8, kSpectralInverter = 9,
    kModulator = 10, kWaveshaper = 11, kMixer = 12, kDelay = 13, kPitchShifter = 14,
    kStretchExpand = 15, kResonator = 16, kNoise = 17, kStretchCompress = 18,
    kPitchEnv = 19, kDiffuser = 20, kOutput = 100, kOutputR = 101,
};

// One oscillator waveform: 256 points at a time position (also .WVFM files).
// Name fields are fixed-size Pascal strings. The original writes the whole
// field, so the bytes after the string (whatever its memory held) are kept
// from the document and written back under the current name.
template <size_t N> using NameField = std::array<uint8_t, N>;

struct Waveform {
    std::string name;
    NameField<32> nameField{};
    int16_t timeMs = 0;              // +32E
    std::array<int16_t, 4> rect{};   // +330
    std::array<int16_t, 257> samples{}; // [256] is the interpolation guard
};

struct EnvPoint { int16_t level = 0, timeMs = 0; std::array<int16_t, 4> extra{}; };

struct Module {
    int16_t type = 0;
    std::array<int16_t, 4> rect{};   // icon position
    int16_t kind = 0;                // 2000 osc, 2001 samp, 3000..3012, 7000..7002
    std::string name;
    NameField<256> nameField{};
    int32_t sampleRate = 44100;      // +110
    bool normalize = false;          // +115
    int32_t length = 0;              // +11A, samples

    std::array<int16_t, 5> p{};      // 3..5 word records: +132.. (noise: +12A..)
    int16_t envMode = 0;             // envelope family +136
    std::vector<EnvPoint> points;
    int32_t oscFreq = 0;             // Fixed, +12A
    std::vector<Waveform> waveforms;
    std::array<int16_t, 257> shaper{};
    std::array<int16_t, 32> mixGains{};
    // Sample module
    int32_t sampA = 0, sampB = 0;    // +12E, +132
    std::array<int16_t, 3> sampC{};  // +154 id, +152 partner's id, +156 channel (1 left, 2 right)
    std::vector<int16_t> embedded;   // the sound
    // +13E "Save sound data with document" (on for new modules). When off the
    // document holds no samples, and the module reads back empty (length 0):
    // the original keeps no link to a sound file.
    bool saveSound = true;
    // Output module
    std::array<int32_t, 5> out{};
};

struct Connection { int16_t from = 0, to = 0; }; // 1-based module numbers

struct Patch {
    int16_t version = 0;
    std::vector<uint8_t> printRecord; // the 120-byte TPrint, written back as read
    std::vector<Module> modules;
    std::vector<Connection> connections;
};

bool readPatch(const std::vector<uint8_t>& data, Patch& out, std::string* error);
bool loadPatch(const std::string& path, Patch& out, std::string* error);
// Writes a version-5 document (the 2.22 format).
std::vector<uint8_t> writePatch(const Patch& patch);
bool savePatch(const std::string& path, const Patch& patch, std::string* error);
const char* moduleTypeName(int type);
// A new module with the defaults the editor uses.
Module makeModule(int type, int32_t sampleRate, int32_t length);
// Removes module index (1-based) and renumbers the connections.
void removeModule(Patch& patch, int index);
// A stereo document has two output jacks: Output L (type 100, icon 7001) and
// Output R (type 101, icon 7002) side by side; a mono one has Output (7000).
bool isStereo(const Patch& patch);
void setStereo(Patch& patch, bool stereo);

// Linked stereo Sample modules. Each Sample module has an id (+154, F28_01278:
// one more than the nearest earlier Sample module's id); the halves of a pair
// hold each other's id (+152) and their channel (+156). The left shows icon
// 2004 and the right 2003, side by side like the stereo output.
int sampleId(Patch& patch, int index);            // assigns one if it has none
int samplePartner(const Patch& patch, int index); // the other half, or 0
// After reading a document (F9_010A2 / F28_01040): ids, pair links and icons.
void linkSamples(Patch& patch);
// "Open both channels" (F28_00D8A / F28_00EE0): adds a right half 32 px to the
// right of `index` (moving `index` 32 px left if it would pass maxRight) and
// links the two. Returns the right half's index.
int makeStereoSample(Patch& patch, int index, int maxRight);
// Links two Sample modules as left and right halves (F28_01040), giving
// either an id first if it has none.
void linkSamplePair(Patch& patch, int left, int right);
// Convert Stereo Sample to Dual Mono (F28_00532 / F28_0130A): both halves
// become ordinary Sample modules.
void unlinkSample(Patch& patch, int index);

// Sound Designer I file: header size in the first word, then big-endian samples.
bool loadSoundDesigner(const std::string& path, std::vector<int16_t>& samples, std::string* error);
// A waveform file (.WVFM): the same 554-byte record as in documents.
bool loadWaveform(const std::string& path, Waveform& out, std::string* error);
bool saveWaveform(const std::string& path, const Waveform& w, std::string* error);

} // namespace turbosynth
