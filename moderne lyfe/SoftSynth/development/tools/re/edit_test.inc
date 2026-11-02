// ---- --edit-test: src/engine/Edit.cpp against the 68K routines ----------------------
//
// Runs one long random sequence of edits on both sides, starting from a patch, and
// compares the whole patch, the harmonic list and the Random state after every step.
// Included by refrender.cpp. Stubs for the GEM calls are in host_instr_hook, under
// editMode. The dialogs' code runs for real, with the stubs answering it.

static uint32_t editA4 = 0;

// Pushes `args` so that the first is at 8(A6), and runs the routine. Sizes are in
// bytes: 2 or 4.
struct Arg { uint32_t v; int size; };
static uint32_t callArgs(uint32_t textOffset, std::initializer_list<Arg> args)
{
    uint32_t s = kEditStack;
    std::vector<Arg> a(args);
    for (auto it = a.rbegin(); it != a.rend(); ++it) {
        s -= (uint32_t) it->size;
        if (it->size == 2) wr16(s, (uint16_t) it->v); else wr32(s, it->v);
    }
    s -= 4;
    wr32(s, kStop);
    m68k_set_reg(M68K_REG_SP, s);
    m68k_set_reg(M68K_REG_PC, kText + textOffset);
    runUntilStop();
    return m68k_get_reg(nullptr, M68K_REG_D0);
}

static void putPatch(const softsynth::Patch& p)
{
    std::memcpy(&mem[::kPartials], p.partials.data(), softsynth::kPartialBytes);
    std::memcpy(&mem[editA4 - 0x7BC], p.routing.data(), 0x40);
    wr32(editA4 - 0x778, (uint32_t) p.length);
    wr32(editA4 - 0x774, (uint32_t) p.rate);
    wr32(editA4 - 0x770, (uint32_t) p.frequency);
}

static void putSlices(const softsynth::SliceList& h)
{
    wr16(editA4 - 0xEE0, (uint16_t) h.count);
    for (uint32_t i = 0; i < 42; ++i) {
        wr16(editA4 - 0xEDE + i * 4, (uint16_t) h.y[i]);
        wr16(editA4 - 0xEDC + i * 4, (uint16_t) h.x[i]);
    }
}

static int checkState(const softsynth::Patch& p, const softsynth::SliceList& h, const char* what, int step)
{
    int bad = 0;
    for (size_t i = 0; i < softsynth::kPartialBytes && bad < 6; ++i) {
        if (mem[::kPartials + i] != p.partials[i]) {
            std::printf("step %d %s: P%zu +%#zx: original %02x, port %02x\n", step, what, i / softsynth::kPartialSize + 1, i % softsynth::kPartialSize, mem[::kPartials + i], p.partials[i]);
            ++bad;
        }
    }
    for (size_t i = 0; i < 0x40 && bad < 6; ++i) {
        if (mem[editA4 - 0x7BC + i] != p.routing[i]) {
            std::printf("step %d %s: routing byte %zu: original %02x, port %02x\n", step, what, i, mem[editA4 - 0x7BC + i], p.routing[i]);
            ++bad;
        }
    }
    if ((int16_t) rd16(editA4 - 0xEE0) != h.count) {
        std::printf("step %d %s: slice count: original %d, port %d\n", step, what, (int16_t) rd16(editA4 - 0xEE0), h.count);
        ++bad;
    }
    for (uint32_t i = 0; i < 42 && bad < 6; ++i) {
        if ((int16_t) rd16(editA4 - 0xEDE + i * 4) != h.y[i] || (int16_t) rd16(editA4 - 0xEDC + i * 4) != h.x[i]) {
            std::printf("step %d %s: slice %u: original y %d x %d, port y %d x %d\n", step, what, i, (int16_t) rd16(editA4 - 0xEDE + i * 4), (int16_t) rd16(editA4 - 0xEDC + i * 4), h.y[i], h.x[i]);
            ++bad;
        }
    }
    if ((int32_t) rd32(editA4 - 0x774) != p.rate || (int32_t) rd32(editA4 - 0x778) != p.length || (int32_t) rd32(editA4 - 0x770) != p.frequency) {
        std::printf("step %d %s: rate, length or frequency differs\n", step, what);
        ++bad;
    }
    return bad;
}

static int below(int n) { return (int) (rng() % (uint32_t) n); }

// A patch the editors could have made: ordered x values, an end record, leftovers past it.
static void randomPatch(softsynth::Patch& p)
{
    using namespace softsynth;
    for (size_t i = 0; i < softsynth::kPartialBytes; ++i) p.partials[i] = (uint8_t) rng();
    // Files can hold any level, and large ones overflow the original's word arithmetic.
    const bool wild = below(6) == 0;
    for (int i = 0; i < softsynth::kPartials; ++i) {
        p.partial(i)[kScale] = (uint8_t) (1 + below(2));
        p.partial(i)[kWave] = (uint8_t) (1 + below(5));
        writeBe16(p.partial(i) + kFader, (uint16_t) (below(3) == 0 ? 0 : below(64)));
        writeBe32(p.partial(i) + kRatio, (uint32_t) (1 + below(16)) << 16);
        const int na = below(4) == 0 ? below(41) : below(8);
        writeBe16(p.partial(i) + kLastAmp, (uint16_t) na);
        int x = 0;
        setAmpPoint(p, i, 0, 0, 0);
        for (int n = 1; n <= na; ++n) {
            x += 1 + below(60);
            setAmpPoint(p, i, n, wild ? (int16_t) rng() : below(104), x);
        }
        setAmpPoint(p, i, na + 1, 0, kEndX);
        const int nf = below(16);
        writeBe16(p.partial(i) + kLastFreq, (uint16_t) nf);
        x = 0;
        setFreqPoint(p, i, 0, 0, 0);
        for (int n = 1; n <= nf; ++n) {
            x += 1 + below(60);
            setFreqPoint(p, i, n, below(101) - 50, x);
        }
        setFreqPoint(p, i, nf + 1, 0, kEndX);
        p.routing[(size_t) i * 2] = (uint8_t) below(2);
        p.routing[(size_t) i * 2 + 1] = (uint8_t) (below(3) == 0 ? 1 + below(32) : 0);
    }
    p.length = 64 + below(400000);
    p.rate = 8000 + below(40000);
    p.frequency = (1 + below(4000)) << 16;
}

static softsynth::SmartSynthSettings randomSettings()
{
    softsynth::SmartSynthSettings s;
    s.harmonicSeries = below(4); s.partialDetuning = below(4); s.attackRate = below(4); s.decayRate = below(4);
    s.harmonicRange = below(4); s.doubling = below(4); s.frequencyAttack = below(4); s.secondaryLevel = below(4);
    s.harmonicFilter = below(4); s.frequencyMovement = below(4); s.percussion = below(4); s.fade = below(4);
    if (s.frequencyAttack != 0) s.frequencyMovement = 0; // the dialog can't set both
    return s;
}

static void settingsArray(const softsynth::SmartSynthSettings& s, int* v)
{
    const int a[12] = { s.harmonicSeries, s.partialDetuning, s.attackRate, s.decayRate, s.harmonicRange, s.doubling,
                        s.frequencyAttack, s.secondaryLevel, s.harmonicFilter, s.frequencyMovement, s.percussion, s.fade };
    std::memcpy(v, a, sizeof a);
}

static void putSettings(const softsynth::SmartSynthSettings& s)
{
    int v[12];
    settingsArray(s, v);
    for (uint32_t i = 0; i < 12; ++i) wr16(editA4 - 0x8E0 + i * 2, (uint16_t) v[i]);
    // The object tree F003E10 keeps in step with them.
    for (uint32_t g = 0; g < 12; ++g) {
        const uint32_t heading = 3 + g * 4;
        wr16(kTree + heading * 24 + 10, v[g] != 0 ? 1 : 0);
        for (uint32_t o = 1; o <= 3; ++o)
            wr16(kTree + (heading + o) * 24 + 10, (uint16_t) (v[g] == 0 ? 8 : v[g] == (int) o ? 4 : 0));
    }
}

static int editTest(int steps, const std::vector<uint8_t>& prm, uint32_t a4)
{
    using namespace softsynth;
    editA4 = a4;
    editMode = true;
    wr32(a4 - 0x77C, ::kPartials);
    // The zero-divide vector, as EmuTOS has it: RTE.
    wr16(kEditRte, 0x4E73);
    wr32(0x14, kEditRte);
    wr16(a4 - 0x760, 1); // partial screen, which keeps the current partial
    rngState = 0x2468ACE1;

    Patch tb;
    std::string error;
    if (!parsePatch(prm, tb, &error)) { std::printf("%s\n", error.c_str()); return 1; }
    Patch p = tb;
    SliceList h;
    putPatch(p);
    putSlices(h);

    enum { Smart, Smart2, Setup, Insert, Remove, Find, AmpAt, HarmAt, Slices, Copy, Clear, CopyT, ClearT, Fm, New, Fresh, Tb, InsertT, RemoveT, Ops };
    const char* const names[Ops] = { "SmartSynth", "SmartSynth 2", "Set up", "insert", "remove", "find", "ampAt", "harmonicAt", "slices", "copy", "clear",
                                     "copy timbre", "clear timbre", "FM", "New", "fresh", "TBWERD1", "insert event", "remove event" };
    uint64_t counts[Ops] = {};
    int hangs = 0, alerts0 = 0, failures = 0;
    bool sliceMode = false;

    for (int step = 0; step < steps && failures < 5; ++step) {
        int op = below(Ops);
        if (sliceMode && (op == Fresh || op == Tb || op == New || op == Smart || op == Smart2 || op == Copy || op == Clear)) op = Slices;
        if (!sliceMode && (op == CopyT || op == ClearT || op == InsertT || op == RemoveT)) op = Slices;
        const int partial = below(32);
        wr16(a4 - 0x8C6, (uint16_t) partial);
        ++counts[op];
        hung = false;

        switch (op) {
        case Smart: case Smart2: {
            SmartSynthSettings s = randomSettings();
            if (op == Smart2) s.fade = 2;
            putSettings(s);
            seed = rng();
            XbiosRandom r(seed);
            callArgs(0x3FD4, {});
            if (hung) break;
            smartSynthGenerate(p, s, r);
            if (r.next() != xbiosRandom()) { std::printf("step %d: Random state differs\n", step); ++failures; }
            break;
        }
        case Setup: {
            SmartSynthDialog d;
            d.settings = randomSettings();
            putSettings(d.settings);
            dialogObjects.clear();
            const int clicks = 1 + below(12);
            for (int c = 0; c < clicks; ++c) {
                const int obj = 3 + below(48);
                dialogObjects.push_back(obj);
                const int group = (obj - 3) / 4, sub = obj - (3 + group * 4);
                if (sub == 0) d.clickHeading(group);
                else d.clickOption(group, sub);
            }
            dialogObjects.push_back(0x33);
            dialogAt = 0;
            callArgs(0x3E10, {});
            int want[12];
            settingsArray(d.settings, want);
            for (uint32_t i = 0; i < 12; ++i) {
                if ((int16_t) rd16(a4 - 0x8E0 + i * 2) != want[i]) {
                    std::printf("step %d: Set up group %u: original %d, port %d\n", step, i, (int16_t) rd16(a4 - 0x8E0 + i * 2), want[i]);
                    ++failures;
                }
            }
            break;
        }
        case Insert: {
            const Graph g = (Graph) (1 + below(sliceMode ? 3 : 2));
            const int x = below(600);
            const bool force = below(2) != 0;
            // F00C9C8, then F00CAE8, then the values the drag would leave.
            int16_t at = 0;
            const bool found = findPoint(p, h, g, partial, x, at);
            wr16(kEditArg, 0x7777);
            const uint32_t d0 = callArgs(0xC9C8, { { (uint32_t) x, 2 }, { kEditArg, 4 }, { (uint32_t) g, 2 } });
            if ((uint16_t) d0 != (found ? 1 : 0) || (int16_t) rd16(kEditArg) != at) {
                std::printf("step %d: find differs: original %d at %d, port %d at %d\n", step, (uint16_t) d0, (int16_t) rd16(kEditArg), found, at);
                ++failures;
            }
            if (found || at == 0) break;
            callArgs(0xCAE8, { { (uint32_t) partial, 2 }, { kEditArg, 4 }, { (uint32_t) g, 2 }, { (uint32_t) (force ? 1 : 0), 2 } });
            const bool ok = insertPoint(p, h, g, partial, at, force);
            if ((int16_t) rd16(kEditArg) != at) { std::printf("step %d: insert index: original %d, port %d\n", step, (int16_t) rd16(kEditArg), at); ++failures; }
            if (!ok) ++alerts0;
            if (at != 0) {
                if (checkState(p, h, "insert", step)) ++failures;
                const int y = below(104);
                if (g == Graph::Amp) setAmpPoint(p, partial, at, y, x);
                else if (g == Graph::Freq) setFreqPoint(p, partial, at, y - 50, x);
                else { h.y[(size_t) at] = (int16_t) y; h.x[(size_t) at] = (int16_t) x; }
                putPatch(p);
                putSlices(h);
            }
            break;
        }
        case Remove: {
            const Graph g = (Graph) (1 + below(sliceMode ? 3 : 2));
            const int last = g == Graph::Amp ? lastAmp(p, partial) : g == Graph::Freq ? lastFreq(p, partial) : h.count;
            if (last < 1) break;
            const int at = 1 + below(last);
            callArgs(0xD296, { { (uint32_t) at, 2 }, { (uint32_t) g, 2 } });
            removePoint(p, h, g, partial, at);
            break;
        }
        case Find: {
            const int x = below(700);
            int16_t at = 0;
            const bool found = findPoint(p, h, Graph::TimeSlices, partial, x, at);
            wr16(kEditArg, 0x7777);
            const uint32_t d0 = callArgs(0xC9C8, { { (uint32_t) x, 2 }, { kEditArg, 4 }, { 4u, 2 } });
            if ((uint16_t) d0 != (found ? 1 : 0) || (int16_t) rd16(kEditArg) != at) { std::printf("step %d: event find differs\n", step); ++failures; }
            break;
        }
        case AmpAt: {
            const int x = below(5) == 0 ? below(12000) : below(700);
            const int16_t a = (int16_t) callArgs(0xDD8E, { { (uint32_t) partial, 2 }, { (uint32_t) x, 2 } });
            if (a != ampAt(p, partial, x)) { std::printf("step %d: ampAt P%d x %d: original %d, port %d\n", step, partial + 1, x, a, ampAt(p, partial, x)); ++failures; }
            break;
        }
        case HarmAt: {
            const int x = below(5) == 0 ? below(12000) : below(700);
            const int16_t a = (int16_t) callArgs(0xDECA, { { (uint32_t) x, 2 } });
            if (a != harmonicAt(h, x)) { std::printf("step %d: harmonicAt x %d: original %d, port %d\n", step, x, a, harmonicAt(h, x)); ++failures; }
            break;
        }
        case Slices:
            if (!sliceMode) {
                callArgs(0xD43E, {});
                partialsToSlices(p, h);
                sliceMode = true;
            } else {
                callArgs(0xD89A, {});
                slicesToPartials(p, h);
                sliceMode = false;
            }
            break;
        case Copy: case Clear: {
            PartialFields f;
            f.ampEnvelope = below(2) != 0; f.frequencyContour = below(2) != 0; f.waveType = below(2) != 0; f.frequencyRatio = below(2) != 0; f.faderLevel = below(2) != 0;
            for (int o = 0; o < 16; ++o) dialogSelected[o] = false;
            dialogSelected[2] = f.ampEnvelope; dialogSelected[3] = f.frequencyContour; dialogSelected[4] = f.waveType; dialogSelected[5] = f.frequencyRatio; dialogSelected[6] = f.faderLevel;
            const int a = below(36) - 1, b = below(36) - 1, c = below(36) - 1;
            dialogNumbers.clear();
            dialogAt = 0;
            if (op == Copy) {
                dialogNumbers = { a, b, c };
                dialogExit = 0x10;
                callArgs(0xE1F6, {});
                copyPartials(p, f, a, b, c);
            } else {
                dialogNumbers = { b, c };
                dialogExit = 0xE;
                callArgs(0xE43A, {});
                clearPartials(p, f, b, c);
            }
            break;
        }
        case CopyT: {
            const int a = below(46) - 1, b = below(46) - 1, c = below(46) - 1;
            dialogNumbers = { a, b, c };
            dialogExit = 0xB;
            dialogAt = 0;
            callArgs(0xE67C, {});
            copyTimbre(p, a, b, c);
            break;
        }
        case ClearT: {
            const int b = below(46) - 1, c = below(46) - 1;
            dialogNumbers = { b, c };
            dialogExit = 8;
            dialogAt = 0;
            callArgs(0xE7C2, {});
            clearTimbre(p, b, c);
            break;
        }
        case Fm: {
            const int modulator = below(32), target = below(32);
            dialogObjects = { 3, 5 };
            dialogMouse = { modulator * 18 + 1, target * 18 + 1 };
            const int clicks = 1 + below(5);
            for (int c = 0; c < clicks; ++c) {
                const int what = below(4);
                dialogObjects.push_back(what == 0 ? 7 : what == 1 ? 8 : what == 2 ? 9 : 10);
                dialogMouse.push_back(0);
                if (what == 0) fmSet(p, modulator, target);
                else if (what == 1) fmClear(p, modulator, target);
                else if (what == 2) fmClearAll(p);
                else fmToggleMix(p, modulator);
            }
            dialogObjects.push_back(6);
            dialogMouse.push_back(0);
            dialogAt = 0;
            callArgs(0x27E0, {});
            dialogMouse.clear();
            break;
        }
        case New: {
            const int s = below(15);
            wr16(a4 - 0x76A, (uint16_t) (0x3A + s));
            callArgs(0x216A, {});
            newPatch(p, (Sampler) s);
            break;
        }
        case Fresh:
            randomPatch(p);
            h = SliceList();
            putPatch(p);
            putSlices(h);
            break;
        case Tb:
            p = tb;
            putPatch(p);
            break;
        case InsertT: {
            const int x = below(600);
            int16_t at = 0;
            if (findPoint(p, h, Graph::TimeSlices, 0, x, at) || at == 0) break;
            wr16(kEditArg, (uint16_t) at);
            wr16(a4 - 0x8C4, (uint16_t) x);
            callArgs(0xCAE8, { { (uint32_t) partial, 2 }, { kEditArg, 4 }, { 4u, 2 }, { 0u, 2 } });
            insertPoint(p, h, Graph::TimeSlices, partial, at, false, x);
            if ((int16_t) rd16(kEditArg) != at) { std::printf("step %d: event index differs\n", step); ++failures; }
            break;
        }
        case RemoveT: {
            if (lastAmp(p, 0) < 1) break;
            const int at = 1 + below(lastAmp(p, 0));
            callArgs(0xD296, { { (uint32_t) at, 2 }, { 4u, 2 } });
            removePoint(p, h, Graph::TimeSlices, partial, at);
            break;
        }
        default:
            break;
        }

        if (hung) {
            // The original looped; the port stops. Start again on both sides.
            ++hangs;
            std::printf("step %d %s: the original did not finish\n", step, names[op]);
            randomPatch(p);
            h = SliceList();
            sliceMode = false;
            putPatch(p);
            putSlices(h);
            continue;
        }
        if (checkState(p, h, names[op], step)) {
            ++failures;
            std::printf("  after step %d (%s), partial %d, slice mode %d\n", step, names[op], partial, sliceMode);
        }
    }

    for (int i = 0; i < Ops; ++i) std::printf("%-14s %llu\n", names[i], (unsigned long long) counts[i]);
    std::printf("hangs %d, full-graph refusals %d, alerts in the original %d, failures %d\n", hangs, alerts0, editAlerts, failures);
    return failures != 0;
}

// --edit-golden FILE: what the original's code does to known starting points, as
// hashes, for tests/test_edit.cpp, which needs no 68K emulator.
static uint64_t hashState()
{
    uint64_t h = 0xCBF29CE484222325ull;
    const auto add = [&](uint8_t b) { h = (h ^ b) * 0x100000001B3ull; };
    for (size_t i = 0; i < softsynth::kPartialBytes; ++i) add(mem[::kPartials + i]);
    for (uint32_t i = 0; i < 0x40; ++i) add(mem[editA4 - 0x7BC + i]);
    for (uint32_t i = 0; i < 2 + 42 * 4; ++i) add(mem[editA4 - 0xEE0 + i]);
    return h;
}

static int editGolden(const std::string& path, const std::vector<uint8_t>& prm, uint32_t a4)
{
    using namespace softsynth;
    editA4 = a4;
    editMode = true;
    wr32(a4 - 0x77C, ::kPartials);
    wr16(kEditRte, 0x4E73);
    wr32(0x14, kEditRte);
    rngState = 0x13579BDF;
    Patch tb;
    std::string error;
    if (!parsePatch(prm, tb, &error)) { std::printf("%s\n", error.c_str()); return 1; }
    FILE* f = std::fopen(path.c_str(), "w");
    if (!f) return 1;
    std::fprintf(f, "# base, then settings in struct order, seed, then FNV-1a hashes after Generate, after entering and after leaving the time slice screen\n");
    for (int c = 0; c < 96; ++c) {
        const SmartSynthSettings s = randomSettings();
        int v[12];
        settingsArray(s, v);
        const int base = c % 2;
        const uint32_t sd = rng();
        putPatch(tb);
        putSlices(SliceList());
        if (base == 1) {
            wr16(a4 - 0x76A, 0x3C);
            callArgs(0x216A, {});
        }
        putSettings(s);
        seed = sd;
        callArgs(0x3FD4, {});
        if (hung) { std::fclose(f); return 1; }
        const uint64_t h1 = hashState();
        callArgs(0xD43E, {});
        const uint64_t h2 = hashState();
        callArgs(0xD89A, {});
        const uint64_t h3 = hashState();
        std::fprintf(f, "%d", base);
        for (int i = 0; i < 12; ++i) std::fprintf(f, " %d", v[i]);
        std::fprintf(f, " %u %016llx %016llx %016llx\n", sd, (unsigned long long) h1, (unsigned long long) h2, (unsigned long long) h3);
    }
    std::fclose(f);
    return 0;
}
