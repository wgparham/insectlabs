#include "Patch.h"

#include <algorithm>
#include <cmath>
#include <filesystem>
#include <fstream>

namespace turbosynth {

namespace {

struct Reader {
    const std::vector<uint8_t>& d;
    size_t o = 0;
    bool ok = true;
    bool need(size_t n) { if (o + n > d.size()) ok = false; return ok; }
    int16_t i16() { if (!need(2)) return 0; const int16_t v = (int16_t) (d[o] << 8 | d[o + 1]); o += 2; return v; }
    int32_t i32() { const uint16_t a = (uint16_t) i16(), b = (uint16_t) i16(); return (int32_t) ((uint32_t) a << 16 | b); }
    uint8_t u8() { if (!need(1)) return 0; return d[o++]; }
    std::string pstr(size_t field) // Pascal string stored in a fixed-size field
    {
        if (!need(field)) return {};
        const size_t n = std::min<size_t>(d[o], field - 1);
        std::string s((const char*) &d[o + 1], n);
        o += field;
        return s;
    }
    void skip(size_t n) { if (need(n)) o += n; }
};

} // namespace

const char* moduleTypeName(int type)
{
    switch (type) {
    case kOscillator: return "Oscillator"; case kSample: return "Sample"; case kAmpEnv: return "Amplitude Envelope";
    case kFilter: return "Filter"; case kSpectralInverter: return "Spectral Inverter"; case kModulator: return "Modulator";
    case kWaveshaper: return "Waveshaper"; case kMixer: return "Mixer"; case kDelay: return "Delay";
    case kPitchShifter: return "Pitch Shifter"; case kStretchExpand: return "Stretcher (expand)";
    case kResonator: return "Resonator"; case kNoise: return "Noise"; case kStretchCompress: return "Stretcher (compress)";
    case kPitchEnv: return "Pitch Envelope"; case kDiffuser: return "Diffuser"; case kOutput: return "Output";
    case kOutputR: return "Output R";
    default: return "Unknown";
    }
}

bool readPatch(const std::vector<uint8_t>& data, Patch& patch, std::string* error)
{
    Reader r{ data };
    r.skip(120); // print record
    patch = Patch{};
    patch.version = r.i16();
    const int ver = patch.version;
    if (ver > 2 && ver < 5) r.skip(1);
    if (ver == 1) r.skip(12);
    const int count = r.i16();
    if (!r.ok || count < 0 || count > 1000) { if (error) *error = "Not a TurboSynth document."; return false; }
    for (int m = 0; m < count && r.ok; ++m) {
        Module mod;
        mod.type = r.i16();
        for (auto& v : mod.rect) v = r.i16();
        mod.kind = r.i16();
        if (ver <= 4) r.skip(1);
        mod.name = r.pstr(256);
        mod.sampleRate = r.i32();
        mod.normalize = r.u8() != 0; // +115
        mod.length = r.i32();
        switch (mod.type) {
        case kOutput: case kOutputR:
            if (ver == 1) { r.skip(2); mod.out[3] = r.i32(); mod.out[4] = r.i32(); }
            else { mod.out[0] = r.u8(); mod.out[1] = r.i32(); mod.out[2] = r.u8(); mod.out[3] = r.i32(); mod.out[4] = r.i32(); }
            break;
        case kOscillator: {
            mod.oscFreq = r.i32();
            const int n = r.i16();
            for (int i = 0; i < n && r.ok; ++i) {
                Waveform w;
                w.name = r.pstr(32);
                w.timeMs = r.i16();
                for (auto& v : w.rect) v = r.i16();
                for (int k = 0; k < 256; ++k) w.samples[(size_t) k] = r.i16();
                mod.waveforms.push_back(w);
            }
            break; }
        case kSample: {
            mod.sampA = r.i32(); mod.sampB = r.i32();
            if (ver > 3) for (auto& v : mod.sampC) v = r.i16();
            if (r.u8()) {
                mod.embedded.resize((size_t) std::max(0, mod.length));
                for (auto& v : mod.embedded) v = r.i16();
            }
            break; }
        case kAmpEnv: case kFilter: case kSpectralInverter: case kPitchEnv: {
            if (ver > 1) mod.envMode = r.i16();
            const int n = r.i16();
            for (int i = 0; i < n && r.ok && n < 10000; ++i) {
                EnvPoint pt; pt.level = r.i16(); pt.timeMs = r.i16();
                for (auto& v : pt.extra) v = r.i16();
                mod.points.push_back(pt);
            }
            break; }
        case kWaveshaper: for (auto& v : mod.shaper) v = r.i16(); break;
        case kMixer: for (auto& v : mod.mixGains) v = r.i16(); break;
        case kModulator: case kDelay: case kResonator: for (int i = 0; i < 5; ++i) mod.p[(size_t) i] = r.i16(); break;
        case kStretchExpand: case kStretchCompress: case kNoise: for (int i = 0; i < 4; ++i) mod.p[(size_t) i] = r.i16(); break;
        case kPitchShifter: case kDiffuser: for (int i = 0; i < 3; ++i) mod.p[(size_t) i] = r.i16(); break;
        default:
            if (error) *error = "Unknown module type " + std::to_string(mod.type);
            return false;
        }
        patch.modules.push_back(std::move(mod));
    }
    const int nc = r.i16();
    for (int i = 0; i < nc && r.ok; ++i) { Connection c; c.from = r.i16(); c.to = r.i16(); patch.connections.push_back(c); }
    if (!r.ok) { if (error) *error = "The document is truncated."; return false; }
    return true;
}

bool loadPatch(const std::string& path, Patch& out, std::string* error)
{
    std::ifstream f(std::filesystem::u8path(path), std::ios::binary);
    if (!f) { if (error) *error = "Cannot open " + path; return false; }
    std::vector<uint8_t> d(std::istreambuf_iterator<char>(f), {});
    return readPatch(d, out, error);
}

bool loadSoundDesigner(const std::string& path, std::vector<int16_t>& samples, std::string* error)
{
    std::ifstream f(std::filesystem::u8path(path), std::ios::binary);
    if (!f) { if (error) *error = "Cannot open " + path; return false; }
    std::vector<uint8_t> d(std::istreambuf_iterator<char>(f), {});
    if (d.size() < 2) { if (error) *error = "Empty sound file"; return false; }
    const size_t header = (size_t) (d[0] << 8 | d[1]);
    if (header < 2 || header > d.size()) { if (error) *error = "Not a Sound Designer file"; return false; }
    samples.clear();
    for (size_t i = header; i + 1 < d.size(); i += 2) samples.push_back((int16_t) (d[i] << 8 | d[i + 1]));
    return true;
}

} // namespace turbosynth

namespace turbosynth {

namespace {
struct Writer {
    std::vector<uint8_t> d;
    void i16(int v) { d.push_back((uint8_t) ((v >> 8) & 0xFF)); d.push_back((uint8_t) (v & 0xFF)); }
    void i32(int32_t v) { i16((int16_t) (v >> 16)); i16((int16_t) v); }
    void u8(int v) { d.push_back((uint8_t) v); }
    void pstr(const std::string& s, size_t field)
    {
        const size_t n = std::min(s.size(), field - 1);
        u8((int) n);
        d.insert(d.end(), s.begin(), s.begin() + (long) n);
        d.insert(d.end(), field - 1 - n, 0);
    }
};
} // namespace

std::vector<uint8_t> writePatch(const Patch& patch)
{
    Writer w;
    w.d.assign(120, 0); // print record
    w.d[1] = 3;         // iPrVersion
    w.i16(5);
    w.i16((int) patch.modules.size());
    for (const auto& m : patch.modules) {
        w.i16(m.type);
        for (int v : m.rect) w.i16(v);
        w.i16(m.kind);
        w.pstr(m.name, 256);
        w.i32(m.sampleRate);
        w.u8(m.normalize ? 1 : 0);
        w.i32(m.length);
        switch (m.type) {
        case kOutput: case kOutputR: w.u8(m.out[0]); w.i32(m.out[1]); w.u8(m.out[2]); w.i32(m.out[3]); w.i32(m.out[4]); break;
        case kOscillator:
            w.i32(m.oscFreq);
            w.i16((int) m.waveforms.size());
            for (const auto& wf : m.waveforms) {
                w.pstr(wf.name, 32); w.i16(wf.timeMs);
                for (int v : wf.rect) w.i16(v);
                for (int k = 0; k < 256; ++k) w.i16(wf.samples[(size_t) k]);
            }
            break;
        case kSample:
            w.i32(m.sampA); w.i32(m.sampB);
            for (int v : m.sampC) w.i16(v);
            w.u8(m.embedded.empty() ? 0 : 1);
            if (!m.embedded.empty())
                for (int32_t i = 0; i < m.length; ++i) w.i16(i < (int32_t) m.embedded.size() ? m.embedded[(size_t) i] : 0);
            break;
        case kAmpEnv: case kFilter: case kSpectralInverter: case kPitchEnv:
            w.i16(m.envMode);
            w.i16((int) m.points.size());
            for (const auto& p : m.points) { w.i16(p.level); w.i16(p.timeMs); for (int v : p.extra) w.i16(v); }
            break;
        case kWaveshaper: for (int v : m.shaper) w.i16(v); break;
        case kMixer: for (int v : m.mixGains) w.i16(v); break;
        case kModulator: case kDelay: case kResonator: for (int i = 0; i < 5; ++i) w.i16(m.p[(size_t) i]); break;
        case kStretchExpand: case kStretchCompress: case kNoise: for (int i = 0; i < 4; ++i) w.i16(m.p[(size_t) i]); break;
        case kPitchShifter: case kDiffuser: for (int i = 0; i < 3; ++i) w.i16(m.p[(size_t) i]); break;
        default: break;
        }
    }
    w.i16((int) patch.connections.size());
    for (const auto& c : patch.connections) { w.i16(c.from); w.i16(c.to); }
    return w.d;
}

bool savePatch(const std::string& path, const Patch& patch, std::string* error)
{
    const auto d = writePatch(patch);
    std::ofstream f(std::filesystem::u8path(path), std::ios::binary);
    if (!f) { if (error) *error = "Cannot write " + path; return false; }
    f.write((const char*) d.data(), (std::streamsize) d.size());
    return (bool) f;
}

} // namespace turbosynth

namespace turbosynth {

Module makeModule(int type, int32_t sampleRate, int32_t length)
{
    Module m;
    m.type = (int16_t) type;
    m.sampleRate = sampleRate;
    m.length = length;
    m.rect = { 0, 0, 32, 32 };
    const int durMs = (int) ((int64_t) length * 1000 / std::max(1, sampleRate));
    auto env = [&](int a, int b) { m.points = { EnvPoint{ (int16_t) a, 0, {} }, EnvPoint{ (int16_t) b, (int16_t) std::min(durMs, 32767), {} } }; };
    switch (type) {
    case kOscillator: {
        m.kind = 2000;
        m.oscFreq = 440 << 16;
        Waveform w; w.name = "Sine";
        for (int i = 0; i < 256; ++i) w.samples[(size_t) i] = (int16_t) std::lround(32767.0 * std::sin(6.283185307179586 * i / 256.0));
        m.waveforms.push_back(w);
        break; }
    case kSample: m.kind = 2001; break;
    case kAmpEnv: m.kind = 3000; env(100, 0); m.normalize = false; break;
    case kFilter: m.kind = 3001; env(100, 0); break;
    case kSpectralInverter: m.kind = 3002; env(0, 100); m.envMode = 1; break;
    case kModulator: m.kind = 3003; m.p = { 50, 100, 1, 1, 2 }; break;
    case kWaveshaper: m.kind = 3004; for (int i = 0; i <= 256; ++i) m.shaper[(size_t) i] = (int16_t) std::clamp(i * 256 - 32768, -32768, 32767); break;
    case kMixer: m.kind = 3005; m.mixGains.fill(100); break;
    case kDelay: m.kind = 3006; m.p = { 2, 50, 50, 1, 50 }; break;
    case kPitchShifter: m.kind = 3007; m.p = { 7, 0, 100, 0, 0 }; break;
    case kStretchExpand: m.kind = 3008; m.p = { 1, 0, 1, 50, 0 }; break;
    case kStretchCompress: m.kind = 3010; m.p = { 1, 0, 1, 50, 0 }; break;
    case kResonator: m.kind = 3009; m.p = { 4, 40, 90, 1, 100 }; break;
    case kNoise: m.kind = 2002; m.p = { 44, 0, 50, 50, 0 }; break;
    case kPitchEnv: m.kind = 3011; env(50, 50); break;
    case kDiffuser: m.kind = 3012; m.p = { 3, 0, 60, 0, 0 }; break;
    case kOutput: m.kind = 7000; m.name = "Output"; break;
    case kOutputR: m.kind = 7002; m.name = "Output R"; break;
    default: break;
    }
    if (m.name.empty()) m.name = moduleTypeName(type);
    return m;
}

void removeModule(Patch& patch, int index)
{
    if (index < 1 || index > (int) patch.modules.size()) return;
    patch.modules.erase(patch.modules.begin() + (index - 1));
    std::vector<Connection> kept;
    for (auto c : patch.connections) {
        if (c.from == index || c.to == index) continue;
        if (c.from > index) --c.from;
        if (c.to > index) --c.to;
        kept.push_back(c);
    }
    patch.connections = kept;
}

} // namespace turbosynth
