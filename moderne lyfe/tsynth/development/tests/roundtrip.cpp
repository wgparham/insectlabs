// Round trip: read each document, write it as version 5, read it back and
// check that both render identically.
#include "Engine.h"
#include <cstdio>
#include <filesystem>
using namespace turbosynth;
int main(int argc, char** argv)
{
    int fails = 0;
    for (int a = 1; a < argc; ++a) {
        Patch p, q; std::string err;
        if (!loadPatch(argv[a], p, &err)) { std::printf("FAIL read %s\n", argv[a]); ++fails; continue; }
        if (!readPatch(writePatch(p), q, &err)) { std::printf("FAIL reread %s: %s\n", argv[a], err.c_str()); ++fails; continue; }
        RenderOptions o;
        const auto r1 = Engine(p, o).render(), r2 = Engine(q, o).render();
        const bool same = r1.left == r2.left && r1.right == r2.right && r1.sampleRate == r2.sampleRate;
        if (!same) { std::printf("FAIL render differs: %s\n", argv[a]); ++fails; }
    }
    std::printf("%d documents, %d failures\n", argc - 1, fails);
    return fails ? 1 : 0;
}
