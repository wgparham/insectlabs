// Helpers reproducing the SANE / CodeWarrior-runtime behaviour the original
// depends on: truncating conversions to integers (which return the most
// negative value on overflow, so the original's "clipping" wraps to -32768)
// and the MSL rand().
#pragma once

#include <cmath>
#include <cstdint>

namespace cloudgen::sane {

// FOTTI followed by FOX2Z to a 16-bit integer.
inline int16_t toInt16(double x)
{
    const double t = std::trunc(x);
    if (!(t >= -32768.0 && t <= 32767.0)) return INT16_MIN;
    return (int16_t) t;
}

inline int32_t toInt32(double x)
{
    const double t = std::trunc(x);
    if (!(t >= -2147483648.0 && t <= 2147483647.0)) return INT32_MIN;
    return (int32_t) t;
}

// Metrowerks Standard Library rand()/srand().
class Rand {
public:
    explicit Rand(uint16_t seed) : next(seed) {}
    int16_t operator()()
    {
        next = next * 1103515245u + 12345u;
        return (int16_t) ((next >> 16) & 0x7FFF);
    }
private:
    uint32_t next;
};

} // namespace cloudgen::sane
