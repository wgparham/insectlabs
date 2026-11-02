// The floating point library in Softsynth 2.1 (0x5D64..0x6230), ported routine
// for routine. Values have the layout of an IEEE double, but every result is
// rounded to a 24-bit mantissa, and add, divide and float-to-long each have
// their own rounding behaviour, so native float and double do not match it.
// tools/re/refrender.cpp --fp-test compares this with the 68K code.
#pragma once

#include <cstdint>

namespace softsynth::stfloat {

struct Value {
    uint32_t hi = 0, lo = 0;
};

namespace detail {

// The operand unpack repeated at the top of each routine. A zero exponent is
// left for the caller to handle.
inline uint32_t mantissa(const Value& v)
{
    return ((v.hi << 11) | 0x80000000u) | ((v.lo >> 16) & 0xFFFFu) >> 5;
}

inline uint16_t exponent(const Value& v)
{
    return (uint16_t) ((v.hi >> 20) & 0x7FF);
}

// 0x5FC2: normalize, round to even at bit 8, pack. The sign comes from `sign`.
inline Value pack(uint32_t m, uint16_t e, uint32_t sign)
{
    if ((m & 0xFFFFFF00u) == 0)
        return {};

    bool carry;
    do {
        --e;
        carry = (m & 0x80000000u) != 0;
        m <<= 1;
    } while (!carry);
    e = (uint16_t) (e + 2);

    while ((m & 0x100u) && (m & 0x2FFu)) {
        const uint32_t before = m;
        m += 0x100u;
        if (m >= before)
            break;
        m >>= 1;
        ++e;
    }

    m &= 0xFFFFFE00u;
    const uint32_t low = m & 0xFFFFu;
    m >>= 12;
    const uint16_t top = (uint16_t) (((m >> 16) & 0xFu) | (uint16_t) ((e & 0x7FF) << 4) | (sign & 0x8000u));
    return { (uint32_t) top << 16 | (m & 0xFFFFu), (uint32_t) (uint16_t) (low << 4) << 16 };
}

inline uint32_t signWord(const Value& v) { return v.hi >> 16; }

} // namespace detail

// 0x61FE
inline Value fromLong(int32_t x)
{
    if (x == 0)
        return {};
    uint32_t m = x > 0 ? (uint32_t) x : 0u - (uint32_t) x;
    uint16_t e = 0x41D;
    if ((m & 0xFFFFFF00u) == 0) {
        m <<= 8;
        e = (uint16_t) (e - 8);
    }
    return detail::pack(m, e, (uint32_t) x >> 16);
}

// 0x61B6: truncates toward zero.
inline int32_t toLong(const Value& v)
{
    uint32_t m = detail::mantissa(v);
    const uint16_t e = detail::exponent(v);
    if (e == 0) {
        m = 0;
    } else {
        const unsigned count = (unsigned) (uint16_t) (0x41E - e) & 63u;
        m = count >= 32 ? 0 : m >> count;
    }
    if (v.hi & 0x80000000u)
        m = 0u - m;
    return (int32_t) m;
}

// 0x613E
inline uint32_t toFloatBits(const Value& v)
{
    if (v.hi == 0 && v.lo == 0)
        return 0;
    const uint32_t shifted = (v.hi << 3) | (v.lo >> 29);
    return (shifted + 0x40000000u) | (v.hi & 0x80000000u);
}

// 0x617C
inline Value fromFloatBits(uint32_t f)
{
    if (f == 0)
        return {};
    const uint32_t lo = f << 29;
    const uint32_t hi = (uint32_t) ((int32_t) f >> 3);
    return { (hi & 0x8FFFFFFFu) + 0x38000000u, lo };
}

// 0x5E7C: 32 x 32, with a sticky bit for the low half.
inline Value mul(const Value& a, const Value& b)
{
    const uint16_t ea = detail::exponent(a), eb = detail::exponent(b);
    if (ea == 0 || eb == 0)
        return {};
    const uint64_t p = (uint64_t) detail::mantissa(a) * detail::mantissa(b);
    uint32_t m = (uint32_t) (p >> 32);
    if ((uint32_t) p != 0)
        m |= 2u;
    return detail::pack(m, (uint16_t) (ea - 0x3FF + eb), detail::signWord(a) ^ detail::signWord(b));
}

// 0x5DFE: a 32-step quotient with no sticky bit. Dividing by zero gives zero.
inline Value div(const Value& a, const Value& b)
{
    const uint16_t ea = detail::exponent(a), eb = detail::exponent(b);
    if (ea == 0 || eb == 0)
        return {};
    uint64_t num = (uint64_t) detail::mantissa(a) << 32;
    uint64_t den = (uint64_t) detail::mantissa(b) << 32;
    uint32_t q = 0;
    for (int i = 0; i < 32; ++i) {
        q <<= 1;
        if (den <= num) {
            q |= 1u;
            num -= den;
        }
        den >>= 1;
    }
    return detail::pack(q, (uint16_t) (ea - eb + 0x3FE), detail::signWord(a) ^ detail::signWord(b));
}

// 0x5F28: the smaller operand is aligned by truncating, with no sticky bit.
inline Value add(const Value& a, const Value& b)
{
    uint16_t ea = detail::exponent(a), eb = detail::exponent(b);
    uint32_t ma = ea ? detail::mantissa(a) >> 1 : 0;
    uint32_t mb = eb ? detail::mantissa(b) >> 1 : 0;
    const uint32_t signA = detail::signWord(a), signB = detail::signWord(b);
    uint32_t resultSign = signA;
    const int16_t diff = (int16_t) (ea - eb);
    if (diff <= 0 && (diff != 0 || (int32_t) ma < (int32_t) mb)) {
        const uint16_t e = ea;
        ea = eb;
        eb = e;
        const uint32_t m = ma;
        ma = mb;
        mb = m;
        resultSign = signB;
    }
    uint32_t result = ma;
    if (mb != 0) {
        const int16_t shift = (int16_t) (ea - eb);
        if (shift <= 31) {
            // A negative count can't arise from normal values; the shift register is mod 64.
            const unsigned count = (unsigned) (uint16_t) shift & 63u;
            uint32_t aligned = shift == 0 ? mb : (count >= 32 ? 0 : mb >> count);
            if ((signB ^ signA) & 0x8000u)
                aligned = 0u - aligned;
            result += aligned;
        }
    }
    return detail::pack(result, ea, resultSign);
}

// 0x5F0E
inline Value sub(const Value& a, const Value& b)
{
    Value negated = b;
    negated.hi ^= 0x80000000u;
    return add(a, negated);
}

// 0x5DBE: negative, zero or positive, as the flags it leaves.
inline int compare(const Value& a, const Value& b)
{
    auto key = [](const Value& v) {
        uint32_t hi = v.hi, lo = v.lo;
        if ((int32_t) hi < 0) {
            hi = ~hi | 0x80000000u;
            lo = ~lo;
        }
        return (int64_t) (((uint64_t) hi << 32) | lo);
    };
    const int64_t ka = key(a), kb = key(b);
    return ka < kb ? -1 : ka > kb ? 1 : 0;
}

} // namespace softsynth::stfloat
