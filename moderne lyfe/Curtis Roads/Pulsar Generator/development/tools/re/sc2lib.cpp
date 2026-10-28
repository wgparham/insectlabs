// sc2lib: decompress a SuperCollider 2 compressed class library (e.g. Default.lib).
//
//   sc2lib Default.lib out.sc
//
// Format, from the SC2 runtime (compileCompressedLib -> expand, input_code,
// decode_string: Mark Nelson's 1989 LZW): a 32-byte header, then 12-bit codes
// packed most-significant bit first. Codes 0-255 are literals, the
// dictionary grows from 256 and stops at 4094, and 4095 ends the stream.
//
// The runtime reads the file through a text-mode stream, which swaps CR (0x0D)
// and LF (0x0A) bytes, so the stored bytes must be swapped back before
// decoding. Without the swap the output drifts into garbage.
//
// The decoded text uses CR line endings and MacRoman characters; this tool
// writes it unchanged.

#include <cstdio>
#include <string>
#include <vector>

int main(int argc, char** argv)
{
    if (argc < 3) {
        std::fprintf(stderr, "usage: sc2lib in.lib out.sc\n");
        return 1;
    }
    FILE* f = std::fopen(argv[1], "rb");
    if (!f) { std::fprintf(stderr, "cannot open %s\n", argv[1]); return 1; }
    std::vector<unsigned char> d;
    for (int c; (c = std::fgetc(f)) != EOF;)
        d.push_back(c == 0x0D ? 0x0A : c == 0x0A ? 0x0D : (unsigned char) c);
    std::fclose(f);

    std::vector<std::string> dict(256);
    for (int i = 0; i < 256; ++i) dict[i] = std::string(1, (char) i);

    auto at = [&](size_t i) -> unsigned { return i < d.size() ? d[i] : 0u; };
    std::string out, prev;
    size_t bit = 32 * 8;
    while (bit + 12 <= d.size() * 8) {
        const size_t byte = bit / 8;
        const unsigned code = ((at(byte) << 16 | at(byte + 1) << 8 | at(byte + 2)) >> (12 - bit % 8)) & 0xFFF;
        bit += 12;
        if (code == 0xFFF) break;
        std::string cur;
        if (code < dict.size()) cur = dict[code];
        else if (code == dict.size() && !prev.empty()) cur = prev + prev[0];
        else { std::fprintf(stderr, "invalid code %u at bit %zu\n", code, bit - 12); return 1; }
        out += cur;
        if (!prev.empty() && dict.size() <= 4094) dict.push_back(prev + cur[0]);
        prev = cur;
    }

    FILE* o = std::fopen(argv[2], "wb");
    if (!o) { std::fprintf(stderr, "cannot write %s\n", argv[2]); return 1; }
    std::fwrite(out.data(), 1, out.size(), o);
    std::fclose(o);
    std::printf("%zu bytes\n", out.size());
    return 0;
}
