// Table sets: the 13 wavetables and 256 play-control settings that make up a
// Pulsar Generator "set", and the original file formats (.set, .wt, .bank).
#pragma once

#include <array>
#include <string>
#include <vector>

namespace pulsar {

constexpr int kTableSize = 2048;

// Order of the tables in a .set file (TablesBank.tableNames).
enum TableId {
    FundFreq, PulseMask, Pulsaret, PulseEnv,
    FormFreq1, FormFreq2, FormFreq3,
    Pan1, Pan2, Pan3,
    Amp1, Amp2, Amp3,
    kNumTables
};

const char* tableName(int id); // "fundFreq", "pulseMask", ...

// Display range and default of each table view (PG.allTableViewSpecs).
struct TableSpec { float viewMin, viewMax, limitMin, limitMax, step, defaultValue; };
const TableSpec& tableSpec(int id);

// Play-control settings slots (SettingsLib); unused slots hold -99.
enum SettingSlot {
    kTrain1On = 1, kTrain2On = 2, kTrain3On = 3,
    kTrain1Level = 11, kTrain2Level = 12, kTrain3Level = 13, // 0..10
    kBurstOn = 21, kBurstOns = 22, kBurstOffs = 23,
    kTrainDuration = 31, // seconds
    kEdge = 41,          // 0..10
    kNumSettings = 256
};
using Settings = std::array<float, kNumSettings>;
Settings defaultSettings();

struct TableSet {
    std::string name;
    std::array<std::vector<float>, kNumTables> tables; // each kTableSize samples
    Settings settings;

    static TableSet defaults(); // TablesBank.defaultTables
    // pulsaret * pulseEnv, sample by sample (Pulsaret.updateComboSig)
    std::vector<float> comboSignal() const;
};

// .set: raw big-endian float32; 256 zeros, 256 settings, then the 13 tables
// of 2048 samples in TableId order (TablesBank.readTablesSignal).
bool loadSet(const std::string& path, TableSet& out, std::string* error = nullptr);
bool saveSet(const std::string& path, const TableSet& set, std::string* error = nullptr);
// The same format in memory (used for plugin state).
std::vector<unsigned char> encodeSet(const TableSet& set);
bool decodeSet(const unsigned char* data, size_t size, TableSet& out);

// .wt: one table, raw big-endian float32, 2048 samples.
bool loadWavetable(const std::string& path, std::vector<float>& out, std::string* error = nullptr);
bool saveWavetable(const std::string& path, const std::vector<float>& table, std::string* error = nullptr);

// .bank: a text file listing .set paths, one per line, in classic Mac
// notation (":Folder:Name.set", relative to the application folder).
// Returns host paths resolved against baseFolder (or the bank's folder when
// a listed file is not found there).
std::vector<std::string> loadBank(const std::string& path, const std::string& baseFolder, std::string* error = nullptr);
bool saveBank(const std::string& path, const std::vector<std::string>& setPaths, std::string* error = nullptr);

} // namespace pulsar
