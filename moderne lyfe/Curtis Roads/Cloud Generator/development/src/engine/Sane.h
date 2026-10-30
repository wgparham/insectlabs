// Helpers reproducing the SANE / CodeWarrior-runtime behaviour the original
// depends on: truncating conversions to integers (which return the most
// negative value on overflow, so the original's "clipping" wraps to -32768),
// SANE's 80-bit extended arithmetic where its rounding is audible, and the
// MSL rand().
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

// SANE extended: 64-bit significand, round to nearest even. Only finite
// values are handled; the engine never produces infinities or NaNs here.
// Software arithmetic, so the result is the same with every compiler and CPU
// (MSVC and ARM have no 80-bit long double).
class Extended {
public:
    Extended() = default;
    Extended(double d)
    {
        if (d == 0.0) return;
        int e;
        const double m = std::frexp(std::fabs(d), &e); // [0.5, 1)
        neg = d < 0.0;
        man = (uint64_t) std::ldexp(m, 64);
        exp = e - 1;
    }

    double toDouble() const { return man == 0 ? 0.0 : (neg ? -1.0 : 1.0) * std::ldexp((double) man, exp - 63); }

    // FOTTI: rounds toward zero to an integral value.
    Extended trunc() const
    {
        if (man == 0 || exp >= 63) return *this;
        if (exp < 0) return {};
        Extended r = *this;
        r.man &= ~((uint64_t(1) << (63 - exp)) - 1);
        return r;
    }

    // An integral value in int64 range (after trunc()).
    int64_t toInt64() const
    {
        if (man == 0 || exp < 0) return 0;
        const uint64_t m = exp >= 63 ? man : man >> (63 - exp);
        return neg ? -(int64_t) m : (int64_t) m;
    }

    friend Extended operator-(Extended a) { if (a.man) a.neg = !a.neg; return a; }

    friend Extended operator+(const Extended& a, const Extended& b)
    {
        if (a.man == 0) return b;
        if (b.man == 0) return a;
        const bool aBigger = a.exp != b.exp ? a.exp > b.exp : a.man >= b.man;
        const Extended& x = aBigger ? a : b;
        const Extended& y = aBigger ? b : a;
        // 128-bit working significands, x.man:0 and y.man:0 shifted right; the
        // bits shifted out of y are kept as a sticky bit in the lowest place.
        U128 X{ x.man, 0 }, Y{ y.man, 0 };
        Y = shiftRightSticky(Y, x.exp - y.exp);
        int e = x.exp; // value = significand * 2^(e - 127)
        if (x.neg == y.neg) {
            U128 s = add(X, Y);
            if (s.carry) {
                s = shiftRightSticky(s, 1);
                s.hi |= uint64_t(1) << 63;
                ++e;
            }
            return pack(x.neg, e, s);
        }
        const U128 d = sub(X, Y);
        if (d.hi == 0 && d.lo == 0) return {};
        return pack(x.neg, e, d);
    }

    friend Extended operator-(const Extended& a, const Extended& b) { return a + -b; }

    friend Extended operator*(const Extended& a, const Extended& b)
    {
        if (a.man == 0 || b.man == 0) return {};
        return pack(a.neg != b.neg, a.exp + b.exp + 1, mul(a.man, b.man));
    }

    friend Extended operator/(const Extended& a, const Extended& b)
    {
        if (a.man == 0) return {};
        // 128 quotient bits of a.man * 2^127 / b.man by long division.
        U128 q{};
        uint64_t rem = a.man;
        bool carry = false;
        for (int i = 0; i < 128; ++i) {
            const bool bit = carry || rem >= b.man;
            if (bit) rem -= b.man;
            q.hi = q.hi << 1 | q.lo >> 63;
            q.lo = q.lo << 1 | (bit ? 1 : 0);
            carry = (rem >> 63) != 0;
            rem <<= 1;
        }
        if (rem != 0 || carry) q.lo |= 1; // sticky; q has at least 127 significant bits
        return pack(a.neg != b.neg, a.exp - b.exp, q);
    }

    friend bool operator<(const Extended& a, const Extended& b)
    {
        if (a.man == 0 && b.man == 0) return false;
        if (a.neg != b.neg) return a.neg;
        const bool magLess = a.man == 0 || (b.man != 0 && (a.exp != b.exp ? a.exp < b.exp : a.man < b.man));
        const bool magEqual = a.exp == b.exp && a.man == b.man;
        return a.neg ? !magLess && !magEqual : magLess;
    }
    friend bool operator>=(const Extended& a, const Extended& b) { return !(a < b); }

private:
    struct U128 { uint64_t hi = 0, lo = 0; bool carry = false; };

    static U128 shiftRightSticky(U128 v, int n)
    {
        if (n <= 0) return v;
        if (n >= 128) return { 0, (v.hi | v.lo) ? uint64_t(1) : 0 };
        uint64_t lost;
        if (n >= 64) {
            lost = v.lo | (n > 64 ? v.hi << (128 - n) : 0);
            v.lo = n == 64 ? v.hi : v.hi >> (n - 64);
            v.hi = 0;
        } else {
            lost = v.lo << (64 - n);
            v.lo = v.lo >> n | v.hi << (64 - n);
            v.hi >>= n;
        }
        if (lost) v.lo |= 1;
        return v;
    }

    static U128 add(U128 a, U128 b)
    {
        U128 r;
        r.lo = a.lo + b.lo;
        const uint64_t c = r.lo < a.lo ? 1 : 0;
        r.hi = a.hi + b.hi + c;
        r.carry = r.hi < a.hi || (c && r.hi == a.hi);
        return r;
    }

    static U128 sub(U128 a, U128 b) // a >= b
    {
        U128 r;
        r.lo = a.lo - b.lo;
        r.hi = a.hi - b.hi - (a.lo < b.lo ? 1 : 0);
        return r;
    }

    static U128 mul(uint64_t a, uint64_t b)
    {
        const uint64_t a0 = (uint32_t) a, a1 = a >> 32, b0 = (uint32_t) b, b1 = b >> 32;
        const uint64_t p00 = a0 * b0, p01 = a0 * b1, p10 = a1 * b0, p11 = a1 * b1;
        const uint64_t mid = (p00 >> 32) + (uint32_t) p01 + (uint32_t) p10;
        U128 r;
        r.lo = (mid << 32) | (uint32_t) p00;
        r.hi = p11 + (p01 >> 32) + (p10 >> 32) + (mid >> 32);
        return r;
    }

    // Rounds significand * 2^(e - 127) to 64 bits.
    static Extended pack(bool neg, int e, U128 s)
    {
        while (!(s.hi >> 63)) {
            s.hi = s.hi << 1 | s.lo >> 63;
            s.lo <<= 1;
            --e;
        }
        const uint64_t half = uint64_t(1) << 63;
        if (s.lo > half || (s.lo == half && (s.hi & 1))) {
            if (++s.hi == 0) {
                s.hi = half;
                ++e;
            }
        }
        Extended r;
        r.neg = neg;
        r.exp = e;
        r.man = s.hi;
        return r;
    }

    bool neg = false;
    int exp = 0;      // value = man * 2^(exp - 63)
    uint64_t man = 0; // 0, or normalised with the top bit set
};

// FOTTI followed by FOX2Z to a 16-bit integer, from an extended. With
// wrapOverflow the low 16 bits of the integer are kept instead, which is what
// the Basilisk II emulator's FMOVE.W does (used to compare with renders made
// there).
inline int16_t toInt16(const Extended& x, bool wrapOverflow)
{
    const int64_t t = x.trunc().toInt64();
    if (wrapOverflow) return (int16_t) (uint16_t) (uint64_t) t;
    if (t < -32768 || t > 32767) return INT16_MIN;
    return (int16_t) t;
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
