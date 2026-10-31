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
    template <size_t N> std::string name(std::array<uint8_t, N>& raw)
    {
        if (need(N)) std::copy(d.begin() + (long) o, d.begin() + (long) (o + N), raw.begin());
        return pstr(N);
    }
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
    patch = Patch{};
    if (r.need(120)) patch.printRecord.assign(data.begin(), data.begin() + 120);
    r.skip(120);
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
        mod.name = r.name(mod.nameField);
        mod.sampleRate = r.i32();
        mod.normalize = r.u8() != 0; // +115
        mod.length = r.i32();
        switch (mod.type) {
        case kOutput: case kOutputR:
            // Version 1 has no +146/+148/+14C; they keep the initializer's 0, 0, 1 (CODE 36).
            if (ver == 1) { r.skip(2); mod.out[2] = 1; mod.out[3] = r.i32(); mod.out[4] = r.i32(); }
            else { mod.out[0] = r.u8(); mod.out[1] = r.i32(); mod.out[2] = r.u8(); mod.out[3] = r.i32(); mod.out[4] = r.i32(); }
            break;
        case kOscillator: {
            mod.oscFreq = r.i32();
            const int n = r.i16();
            for (int i = 0; i < n && r.ok; ++i) {
                Waveform w;
                w.name = r.name(w.nameField);
                w.timeMs = r.i16();
                for (auto& v : w.rect) v = r.i16();
                for (int k = 0; k < 256; ++k) w.samples[(size_t) k] = r.i16();
                mod.waveforms.push_back(w);
            }
            break; }
        case kSample: {
            mod.sampA = r.i32(); mod.sampB = r.i32();
            if (ver > 3) for (auto& v : mod.sampC) v = r.i16();
            mod.saveSound = r.u8() != 0;
            if (mod.saveSound) {
                mod.embedded.resize((size_t) std::max(0, mod.length));
                for (auto& v : mod.embedded) v = r.i16();
            } else mod.length = 0; // F9 at 083C
            break; }
        case kAmpEnv: case kFilter: case kSpectralInverter: case kPitchEnv: {
            // F9_00856 reads +136 only for version > 1; older documents keep the
            // value the new module starts with, 1 (CODE 36 initializers). For the
            // Spectral Inverter that selects step 80h (verified in the emulator).
            mod.envMode = ver > 1 ? r.i16() : 1;
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
        case kStretchExpand: case kStretchCompress:
            for (int i = 0; i < 4; ++i) mod.p[(size_t) i] = r.i16();
            if (ver == 1) mod.p[3] = (int16_t) (100 - mod.p[3]); // F9_00BCC: version-1 documents store the amount inverted
            break;
        case kNoise: for (int i = 0; i < 4; ++i) mod.p[(size_t) i] = r.i16(); break;
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
    // F9 at 17A6: an output whose +146 flag is clear takes its first input's
    // rate and length (the first module, and the right output of a stereo
    // document). This is what gives version-1 outputs, stored as 0, a rate.
    for (size_t i = 0; i < patch.modules.size(); ++i) {
        auto& o = patch.modules[i];
        if ((o.type != kOutput || i != 0) && o.type != kOutputR) continue;
        if (o.out[0] != 0) continue;
        for (const auto& c : patch.connections)
            if (c.to == (int) i + 1 && c.from >= 1 && c.from <= (int) patch.modules.size()) {
                const auto& src = patch.modules[(size_t) c.from - 1];
                o.sampleRate = src.sampleRate;
                o.length = src.length;
                break;
            }
    }
    linkSamples(patch);
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


bool loadWaveform(const std::string& path, Waveform& out, std::string* error)
{
    std::ifstream f(std::filesystem::u8path(path), std::ios::binary);
    if (!f) { if (error) *error = "Cannot open " + path; return false; }
    std::vector<uint8_t> d(std::istreambuf_iterator<char>(f), {});
    if (d.size() != 554) { if (error) *error = "Not a TurboSynth waveform file"; return false; }
    Reader r { d };
    out.name = r.name(out.nameField);
    out.timeMs = r.i16();
    for (auto& v : out.rect) v = r.i16();
    for (int k = 0; k < 256; ++k) out.samples[(size_t) k] = r.i16();
    out.samples[256] = out.samples[0];
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
    // The string over the field's old bytes, as copying it into the record does.
    template <size_t N> void name(const std::string& s, const std::array<uint8_t, N>& raw)
    {
        const size_t at = d.size(), n = std::min(s.size(), N - 1);
        d.insert(d.end(), raw.begin(), raw.end());
        d[at] = (uint8_t) n;
        std::copy(s.begin(), s.begin() + (long) n, d.begin() + (long) at + 1);
    }
};
} // namespace

std::vector<uint8_t> writePatch(const Patch& patch)
{
    Writer w;
    if (patch.printRecord.size() == 120) w.d = patch.printRecord;
    else { w.d.assign(120, 0); w.d[1] = 3; } // a blank print record (iPrVersion 3)
    w.i16(5);
    w.i16((int) patch.modules.size());
    for (const auto& m : patch.modules) {
        w.i16(m.type);
        for (int v : m.rect) w.i16(v);
        w.i16(m.kind);
        w.name(m.name, m.nameField);
        w.i32(m.sampleRate);
        w.u8(m.normalize ? 1 : 0);
        w.i32(m.length);
        switch (m.type) {
        case kOutput: case kOutputR: w.u8(m.out[0]); w.i32(m.out[1]); w.u8(m.out[2]); w.i32(m.out[3]); w.i32(m.out[4]); break;
        case kOscillator:
            w.i32(m.oscFreq);
            w.i16((int) m.waveforms.size());
            for (const auto& wf : m.waveforms) {
                w.name(wf.name, wf.nameField); w.i16(wf.timeMs);
                for (int v : wf.rect) w.i16(v);
                for (int k = 0; k < 256; ++k) w.i16(wf.samples[(size_t) k]);
            }
            break;
        case kSample:
            w.i32(m.sampA); w.i32(m.sampB);
            for (int v : m.sampC) w.i16(v);
            w.u8(m.saveSound ? 1 : 0);
            if (m.saveSound)
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

bool saveWaveform(const std::string& path, const Waveform& wf, std::string* error)
{
    Writer w;
    w.name(wf.name, wf.nameField);
    w.i16(wf.timeMs);
    for (int v : wf.rect) w.i16(v);
    for (int k = 0; k < 256; ++k) w.i16(wf.samples[(size_t) k]);
    std::ofstream f(std::filesystem::u8path(path), std::ios::binary);
    if (!f) { if (error) *error = "Cannot write " + path; return false; }
    f.write((const char*) w.d.data(), (std::streamsize) w.d.size());
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
    if (samplePartner(patch, index)) unlinkSample(patch, index); // F16_03A66: the half left behind goes mono
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

bool isStereo(const Patch& patch)
{
    return std::any_of(patch.modules.begin(), patch.modules.end(), [](const Module& m) { return m.type == kOutputR; });
}

void setStereo(Patch& patch, bool stereo)
{
    auto left = std::find_if(patch.modules.begin(), patch.modules.end(), [](const Module& m) { return m.type == kOutput; });
    if (!stereo) {
        for (int i = (int) patch.modules.size(); i >= 1; --i)
            if (patch.modules[(size_t) i - 1].type == kOutputR) removeModule(patch, i);
        left = std::find_if(patch.modules.begin(), patch.modules.end(), [](const Module& m) { return m.type == kOutput; });
        if (left != patch.modules.end()) { left->kind = 7000; left->name = "Output"; }
        return;
    }
    if (isStereo(patch)) return;
    if (left == patch.modules.end()) {
        patch.modules.push_back(makeModule(kOutput, 44100, 88200));
        left = patch.modules.end() - 1;
    }
    left->kind = 7001;
    left->name = "Output L";
    Module right = makeModule(kOutputR, left->sampleRate, left->length);
    right.rect = { left->rect[0], (int16_t) (left->rect[1] + 32), left->rect[2], (int16_t) (left->rect[3] + 32) }; // the right half of the jack
    right.out = left->out;
    patch.modules.push_back(right);
}

int sampleId(Patch& patch, int index)
{
    if (index < 1 || index > (int) patch.modules.size()) return 0;
    auto& m = patch.modules[(size_t) index - 1];
    if (m.type != kSample) return 0;
    if (m.sampC[0] == 0) { // F28_01278: one more than the nearest earlier id, else 1
        int16_t id = 1;
        for (int j = index - 1; j >= 1; --j) {
            const auto& o = patch.modules[(size_t) j - 1];
            if (o.type == kSample && o.sampC[0] != 0) { id = (int16_t) (o.sampC[0] + 1); break; }
        }
        m.sampC[0] = id;
    }
    return m.sampC[0];
}

int samplePartner(const Patch& patch, int index)
{
    if (index < 1 || index > (int) patch.modules.size()) return 0;
    const auto& m = patch.modules[(size_t) index - 1];
    if (m.type != kSample || m.sampC[1] == 0 || (m.sampC[2] != 1 && m.sampC[2] != 2)) return 0;
    for (int j = 1; j <= (int) patch.modules.size(); ++j) {
        const auto& o = patch.modules[(size_t) j - 1];
        if (j != index && o.type == kSample && o.sampC[0] == m.sampC[1] && o.sampC[1] == m.sampC[0] && o.sampC[2] == 3 - m.sampC[2]) return j;
    }
    return 0;
}

namespace {
void setSampleKind(Module& m) { m.kind = m.sampC[2] == 1 ? 2004 : m.sampC[2] == 2 ? 2003 : 2001; } // F28_011D8
}

void linkSamples(Patch& patch)
{
    const int n = (int) patch.modules.size();
    for (int i = 1; i <= n; ++i) sampleId(patch, i);
    for (int i = 1; i <= n; ++i) { // F9_010A2: each left half looks for the module with its partner's id
        auto& m = patch.modules[(size_t) i - 1];
        if (m.type != kSample || m.sampC[2] != 1 || m.sampC[1] == 0) continue;
        for (int j = 1; j <= n; ++j) {
            auto& o = patch.modules[(size_t) j - 1];
            if (j == i || o.type != kSample || o.sampC[0] != m.sampC[1]) continue;
            o.sampC[1] = m.sampC[0]; // F28_01040: the partner takes this id and the other channel
            o.sampC[2] = 2;
            break;
        }
    }
    for (int i = 1; i <= n; ++i) {
        auto& m = patch.modules[(size_t) i - 1];
        if (m.type != kSample) continue;
        if (!samplePartner(patch, i)) { m.sampC[1] = 0; m.sampC[2] = 0; } // no partner: mono
        setSampleKind(m);
    }
}

int makeStereoSample(Patch& patch, int index, int maxRight)
{
    if (index < 1 || index > (int) patch.modules.size() || patch.modules[(size_t) index - 1].type != kSample) return 0;
    if (const int p = samplePartner(patch, index)) return patch.modules[(size_t) index - 1].sampC[2] == 2 ? index : p;
    sampleId(patch, index);
    Module right = patch.modules[(size_t) index - 1]; // F28_00EE0: a new Sample module with the same name and loop
    right.embedded.clear();
    right.sampC = { 0, 0, 2 };
    patch.modules.push_back(right);
    const int r = (int) patch.modules.size();
    auto& left = patch.modules[(size_t) index - 1];
    if (left.rect[3] + 32 > maxRight) { left.rect[1] = (int16_t) (left.rect[1] - 32); left.rect[3] = (int16_t) (left.rect[3] - 32); }
    auto& rm = patch.modules[(size_t) r - 1];
    rm.rect = { left.rect[0], (int16_t) (left.rect[1] + 32), left.rect[2], (int16_t) (left.rect[3] + 32) };
    linkSamplePair(patch, index, r);
    return r;
}

void linkSamplePair(Patch& patch, int left, int right)
{
    const int n = (int) patch.modules.size();
    if (left < 1 || right < 1 || left > n || right > n || left == right) return;
    sampleId(patch, left);
    sampleId(patch, right);
    auto& l = patch.modules[(size_t) left - 1];
    auto& r = patch.modules[(size_t) right - 1];
    if (l.type != kSample || r.type != kSample) return;
    l.sampC[1] = r.sampC[0]; l.sampC[2] = 1;
    r.sampC[1] = l.sampC[0]; r.sampC[2] = 2;
    setSampleKind(l);
    setSampleKind(r);
}

void unlinkSample(Patch& patch, int index)
{
    const int p = samplePartner(patch, index);
    for (int i : { index, p }) {
        if (i < 1 || i > (int) patch.modules.size()) continue;
        auto& m = patch.modules[(size_t) i - 1];
        if (m.type != kSample) continue;
        m.sampC[1] = 0; m.sampC[2] = 0;
        setSampleKind(m);
    }
}

} // namespace turbosynth
