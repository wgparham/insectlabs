#include "Patch.h"

#include <cstdio>
#include <cstring>

namespace softsynth {

std::string Patch::name() const
{
    const size_t n = nameField[0] & 0x3F;
    return std::string((const char*) nameField.data() + 1, n);
}

bool parsePatch(const std::vector<uint8_t>& data, Patch& out, std::string* error)
{
    // The original's checks (F003116), and its one message for all of them.
    bool ok = data.size() == kFileSize;
    Patch p;
    if (ok) {
        p.length = (int32_t) readBe32(&data[0x77C0]);
        p.rate = (int32_t) readBe32(&data[0x77C4]);
        p.frequency = (int32_t) readBe32(&data[0x77C8]);
        ok = p.frequency >= 0x10000 && p.rate >= 100 && p.rate <= 100000 && p.length >= 64 && p.length <= 4000000;
    }
    if (!ok) {
        if (error) *error = "Bad parameter file";
        return false;
    }
    p.partials.assign(data.begin(), data.begin() + (std::ptrdiff_t) kPartialBytes);
    std::memcpy(p.nameField.data(), &data[0x7780], p.nameField.size());
    std::memcpy(p.routing.data(), &data[0x77CC], p.routing.size());
    out = std::move(p);
    return true;
}

std::vector<uint8_t> writePatch(const Patch& patch)
{
    std::vector<uint8_t> d(kFileSize, 0);
    std::memcpy(d.data(), patch.partials.data(), kPartialBytes);
    std::memcpy(&d[0x7780], patch.nameField.data(), patch.nameField.size());
    writeBe32(&d[0x77C0], (uint32_t) patch.length);
    writeBe32(&d[0x77C4], (uint32_t) patch.rate);
    writeBe32(&d[0x77C8], (uint32_t) patch.frequency);
    std::memcpy(&d[0x77CC], patch.routing.data(), patch.routing.size());
    return d;
}

bool loadPatch(const std::string& path, Patch& out, std::string* error)
{
    FILE* f = std::fopen(path.c_str(), "rb");
    if (!f) {
        if (error) *error = "Can't open " + path;
        return false;
    }
    std::vector<uint8_t> d;
    int c;
    while ((c = std::fgetc(f)) != EOF) d.push_back((uint8_t) c);
    std::fclose(f);
    return parsePatch(d, out, error);
}

bool savePatch(const std::string& path, const Patch& patch, std::string* error)
{
    FILE* f = std::fopen(path.c_str(), "wb");
    if (!f) {
        if (error) *error = "Can't write " + path;
        return false;
    }
    const auto d = writePatch(patch);
    const bool ok = std::fwrite(d.data(), 1, d.size(), f) == d.size();
    std::fclose(f);
    if (!ok && error) *error = "Can't write " + path;
    return ok;
}

} // namespace softsynth
