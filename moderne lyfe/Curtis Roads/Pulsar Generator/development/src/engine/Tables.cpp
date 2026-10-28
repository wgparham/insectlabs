#include "Tables.h"

#include <cmath>
#include <cstdint>
#include <cstdio>
#include <cstring>
#include <filesystem>

namespace fs = std::filesystem;

namespace pulsar {

namespace {

const char* const kNames[kNumTables] = {
    "fundFreq", "pulseMask", "pulsaret", "pulseEnv",
    "formFreq1", "formFreq2", "formFreq3",
    "pan1", "pan2", "pan3",
    "amp1", "amp2", "amp3"
};

const TableSpec kSpecs[kNumTables] = {
    { 0, 200, 0, 2000, 10, 18 },     // fundFreq
    { 0, 1, 0, 1, 0.1f, 1 },         // pulseMask
    { -1, 1, -1, 1, 0.1f, 0 },       // pulsaret
    { -1, 1, -1, 1, 0.1f, 1 },       // pulseEnv
    { 0, 2000, 0, 20000, 10, 400 },  // formFreq1
    { 0, 2000, 0, 20000, 10, 400 },  // formFreq2
    { 0, 2000, 0, 20000, 10, 400 },  // formFreq3
    { -1, 1, -1, 1, 0.1f, 0 },       // pan1
    { -1, 1, -1, 1, 0.1f, 0 },       // pan2
    { -1, 1, -1, 1, 0.1f, 0 },       // pan3
    { -1, 1, -1, 1, 0.1f, 0.5f },    // amp1
    { -1, 1, -1, 1, 0.1f, 0.5f },    // amp2
    { -1, 1, -1, 1, 0.1f, 0.5f },    // amp3
};

void setError(std::string* e, const std::string& msg) { if (e) *e = msg; }

bool readFile(const std::string& path, std::vector<unsigned char>& out)
{
    FILE* f = std::fopen(fs::u8path(path).string().c_str(), "rb");
    if (!f) return false;
    out.clear();
    unsigned char buf[65536];
    size_t n;
    while ((n = std::fread(buf, 1, sizeof buf, f)) > 0) out.insert(out.end(), buf, buf + n);
    std::fclose(f);
    return true;
}

bool writeFile(const std::string& path, const std::vector<unsigned char>& data)
{
    FILE* f = std::fopen(fs::u8path(path).string().c_str(), "wb");
    if (!f) return false;
    const bool ok = std::fwrite(data.data(), 1, data.size(), f) == data.size();
    return std::fclose(f) == 0 && ok;
}

// Classic Mac files copied off old disks are often MacBinary-wrapped;
// return the data fork in that case.
void unwrapMacBinary(std::vector<unsigned char>& d)
{
    if (d.size() < 128 || d[0] != 0 || d[1] == 0 || d[1] > 63 || d[74] != 0 || d[82] != 0) return;
    for (int i = 65; i < 73; ++i) if (d[i] < 32 || d[i] > 126) return;
    const uint32_t dataLen = (uint32_t) d[83] << 24 | (uint32_t) d[84] << 16 | (uint32_t) d[85] << 8 | d[86];
    if (128 + (size_t) dataLen > d.size()) return;
    d = std::vector<unsigned char>(d.begin() + 128, d.begin() + 128 + dataLen);
}

float readBE(const unsigned char* p)
{
    const uint32_t v = (uint32_t) p[0] << 24 | (uint32_t) p[1] << 16 | (uint32_t) p[2] << 8 | p[3];
    float f;
    std::memcpy(&f, &v, 4);
    return f;
}

void writeBE(std::vector<unsigned char>& out, float f)
{
    uint32_t v;
    std::memcpy(&v, &f, 4);
    out.push_back((unsigned char) (v >> 24));
    out.push_back((unsigned char) (v >> 16));
    out.push_back((unsigned char) (v >> 8));
    out.push_back((unsigned char) v);
}

std::vector<float> filled(float v) { return std::vector<float>(kTableSize, v); }

} // namespace

const char* tableName(int id) { return id >= 0 && id < kNumTables ? kNames[id] : ""; }
const TableSpec& tableSpec(int id) { return kSpecs[id]; }

Settings defaultSettings()
{
    Settings s;
    s.fill(-99.0f);
    const float pairs[][2] = { { 1, 1 }, { 2, 1 }, { 3, 1 }, { 11, 3 }, { 12, 3 }, { 13, 3 },
                               { 21, 0 }, { 22, 2 }, { 23, 1 }, { 31, 10 }, { 41, 5 }, { 51, 0 } };
    for (const auto& p : pairs) s[(size_t) p[0]] = p[1];
    return s;
}

TableSet TableSet::defaults()
{
    TableSet t;
    t.name = "Default Tables";
    t.tables[FundFreq] = filled(10);
    t.tables[PulseMask] = filled(1);
    t.tables[Pulsaret].resize(kTableSize); // Wavetable.sineFill(2048, [1])
    for (int i = 0; i < kTableSize; ++i)
        t.tables[Pulsaret][(size_t) i] = (float) std::sin(2.0 * 3.14159265358979323846 * i / kTableSize);
    t.tables[PulseEnv] = filled(1);
    t.tables[FormFreq1] = filled(80);
    t.tables[FormFreq2] = filled(600);
    t.tables[FormFreq3] = filled(1200);
    t.tables[Pan1] = t.tables[Pan2] = t.tables[Pan3] = filled(0);
    t.tables[Amp1] = t.tables[Amp2] = t.tables[Amp3] = filled(0.5f);
    t.settings = defaultSettings();
    return t;
}

std::vector<float> TableSet::comboSignal() const
{
    std::vector<float> c(kTableSize);
    for (size_t i = 0; i < c.size(); ++i)
        c[i] = tables[Pulsaret][i] * tables[PulseEnv][i];
    return c;
}

bool decodeSet(const unsigned char* data, size_t size, TableSet& out)
{
    const size_t needed = (512 + (size_t) kNumTables * kTableSize) * 4;
    if (size < needed) return false;
    TableSet t;
    t.name = out.name;
    for (int i = 0; i < kNumSettings; ++i)
        t.settings[(size_t) i] = readBE(data + (size_t) (256 + i) * 4);
    for (int k = 0; k < kNumTables; ++k) {
        t.tables[(size_t) k].resize(kTableSize);
        const size_t base = (512 + (size_t) k * kTableSize) * 4;
        for (int i = 0; i < kTableSize; ++i)
            t.tables[(size_t) k][(size_t) i] = readBE(data + base + (size_t) i * 4);
    }
    out = std::move(t);
    return true;
}

std::vector<unsigned char> encodeSet(const TableSet& set)
{
    std::vector<unsigned char> d;
    d.reserve((512 + (size_t) kNumTables * kTableSize) * 4);
    for (int i = 0; i < 256; ++i) writeBE(d, 0.0f);
    for (float v : set.settings) writeBE(d, v);
    for (const auto& table : set.tables)
        for (int i = 0; i < kTableSize; ++i) writeBE(d, i < (int) table.size() ? table[(size_t) i] : 0.0f);
    return d;
}

bool loadSet(const std::string& path, TableSet& out, std::string* error)
{
    std::vector<unsigned char> d;
    if (!readFile(path, d)) { setError(error, "Cannot open " + path); return false; }
    unwrapMacBinary(d);
    TableSet t;
    t.name = fs::u8path(path).stem().u8string();
    if (!decodeSet(d.data(), d.size(), t)) { setError(error, path + " is not a Pulsar Generator .set file"); return false; }
    out = std::move(t);
    return true;
}

bool saveSet(const std::string& path, const TableSet& set, std::string* error)
{
    if (!writeFile(path, encodeSet(set))) { setError(error, "Cannot write " + path); return false; }
    return true;
}

bool loadWavetable(const std::string& path, std::vector<float>& out, std::string* error)
{
    std::vector<unsigned char> d;
    if (!readFile(path, d)) { setError(error, "Cannot open " + path); return false; }
    unwrapMacBinary(d);
    if (d.size() < (size_t) kTableSize * 4) { setError(error, path + " is not a 2048-point wavetable"); return false; }
    out.resize(kTableSize);
    for (int i = 0; i < kTableSize; ++i) out[(size_t) i] = readBE(&d[(size_t) i * 4]);
    return true;
}

bool saveWavetable(const std::string& path, const std::vector<float>& table, std::string* error)
{
    std::vector<unsigned char> d;
    for (int i = 0; i < kTableSize; ++i) writeBE(d, i < (int) table.size() ? table[(size_t) i] : 0.0f);
    if (!writeFile(path, d)) { setError(error, "Cannot write " + path); return false; }
    return true;
}

std::vector<std::string> loadBank(const std::string& path, const std::string& baseFolder, std::string* error)
{
    std::vector<std::string> result;
    std::vector<unsigned char> d;
    if (!readFile(path, d)) { setError(error, "Cannot open " + path); return result; }
    unwrapMacBinary(d);
    const fs::path bankDir = fs::u8path(path).parent_path();
    const fs::path base = baseFolder.empty() ? bankDir : fs::u8path(baseFolder);

    std::string line;
    auto flush = [&] {
        while (!line.empty() && (line.back() == ' ' || line.back() == '\t')) line.pop_back();
        if (line.empty()) return;
        // Mac path components; the last is the file name. Names are MacRoman,
        // but set names in practice are ASCII.
        std::vector<std::string> parts;
        std::string part;
        for (char c : line) {
            if (c == ':') { parts.push_back(part); part.clear(); }
            else part += c;
        }
        parts.push_back(part);
        const std::string fileName = parts.back();
        fs::path candidate = bankDir / fs::u8path(fileName);
        if (!fs::exists(candidate) && line[0] == ':') { // relative to the application folder
            candidate = base;
            for (size_t i = 1; i < parts.size(); ++i) candidate /= fs::u8path(parts[i]);
        }
        if (fs::exists(candidate)) result.push_back(candidate.u8string());
        line.clear();
    };
    for (unsigned char c : d) {
        if (c == '\r' || c == '\n') flush();
        else line += (char) c;
    }
    flush();
    if (result.empty()) setError(error, "No table sets from " + path + " were found");
    return result;
}

bool saveBank(const std::string& path, const std::vector<std::string>& setPaths, std::string* error)
{
    // Written relative to the bank's folder so banks can be moved together.
    std::vector<unsigned char> d;
    const fs::path bankDir = fs::u8path(path).parent_path();
    for (const auto& p : setPaths) {
        std::string rel = fs::u8path(p).lexically_relative(bankDir).u8string();
        if (rel.empty()) rel = fs::u8path(p).filename().u8string();
        std::string mac = ":";
        for (char c : rel) mac += (c == '/' || c == '\\') ? ':' : c;
        d.insert(d.end(), mac.begin(), mac.end());
        d.push_back('\r');
    }
    if (!writeFile(path, d)) { setError(error, "Cannot write " + path); return false; }
    return true;
}

} // namespace pulsar
