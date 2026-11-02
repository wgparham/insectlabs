# Softsynth 2.1 (Atari ST): reverse-engineering notes

Digidesign Softsynth 2.1, 1988, adapted for the ST by Rainer Brodersen. The copy we have is cracked ("Cracked by The M.C.A." in the About tree of `SOFTSYN.RSC`). Addresses are offsets into the text segment of `SOFTSYNT.PRG`, with no load base.

## Tools (`tools/re/`)

- `stdis.c`: linear disassembly of a `.PRG` text segment. Build with Musashi's `m68kdasm.c` (`MacEmu\musashi`).
- `annot.pl`: adds strings and jump-table targets to that listing, and prints a summary.
- `prmdump.py`: dumps a `.PRM` and checks its stored break points against the formulas below.

## Executable

- Text 0x25D5E, data 0x1274, BSS 0x4956, no symbols. 145 relocations, all in the jump table at text 0.
- A5 = text base: `jsr d(A5)` jumps to the table entry at offset d. A4 = data base: positive offsets are initialised data and strings, negative offsets are BSS.
- Trap wrappers: BIOS 0x6618, XBIOS 0x6634, GEMDOS 0x6650.
- Long multiply is 0x6400 (`jsr $198(A5)`). Long divide is `jsr $1D4(A5)`.

## Floating point

Values are 8 bytes with the layout of an IEEE double, but **every result is rounded to a 24-bit mantissa**, so it behaves like single precision in a double's clothing. The accumulator is at -$4780(A4). `src/engine/StFloat.h` is a routine-for-routine port.

- 0x602C (`jsr $D8(A5)`) takes an opword. The high byte is the operand type: 0x2000 word, 0x2800 long, 0x1000 float. The low byte indexes the table at -$47A4(A4): 0 add, 1 subtract, 2 multiply, 3 divide, 4 compare, 5 and 6 are the pack routines.
- 0x60DE long to double, 0x60EE double to long, 0x60FE float to double, 0x610E double to float, each on the accumulator.

| Routine | Address | Behaviour |
|---|---|---|
| Pack | 0x5FC2 | Normalizes, rounds to even at bit 8. A 32-bit working value with nothing above bit 7 becomes zero. |
| Multiply | 0x5E7C | 32×32, with a sticky bit for the low half. |
| Divide | 0x5DFE | 32-step quotient with no sticky bit, so a remainder can read as an exact tie. Divide by zero gives zero. |
| Add, subtract | 0x5F28, 0x5F0E | Aligns the smaller operand by truncating, with no sticky bit. An exponent gap above 31 ignores it. |
| Double to long | 0x61B6 | Truncates toward zero. |
| Compare | 0x5DBE | 64-bit signed compare after flipping negatives. |

The break-point stage uses this library. The sample kernels are integer only.

**Check:** `refrender --fp-test` runs each routine on Musashi against the port with random operands, including unusual ones. 4.5 million operations, 0 differences. Six deliberately broken copies of the port were all caught, so the test can fail. Native `float` differs from the original, but rarely on ordinary values: the "div gains sticky" variant differed on about 0.1% of divides.

## `.PRM` file (0x780C bytes, all big-endian)

Loader: F003116. It rejects frequency < 1.0 Hz, rate outside 100..100000, and length outside 64..4000000.

| Offset | Size | Contents |
|---|---|---|
| 0x0000 | 32 × 956 | Partials |
| 0x7780 | 0x40 | Pascal name (length masked with 0x3F; bytes after the string are left over) |
| 0x77C0 | 4 | Length, samples |
| 0x77C4 | 4 | Sample rate, Hz |
| 0x77C8 | 4 | Frequency, 16.16 Hz |
| 0x77CC | 32 × 2 | FM routing: byte 0 = in the output mix, byte 1 = modulator partial (1-based, 0 = none) |

Partial (956 = 0xA + 42×16 + 2 + 17×16):

| Offset | Size | Contents |
|---|---|---|
| +0x000 | 1 | Frequency contour scale: 1 divides y by 2500, otherwise by 100 |
| +0x001 | 1 | Waveform, see below |
| +0x002 | 2 | Fader; 0 = partial off |
| +0x004 | 4 | Frequency ratio, 16.16 |
| +0x008 | 2 | Index of the last amp segment |
| +0x00A | 42 × 16 | Amp break points |
| +0x2AA | 2 | Index of the last frequency segment |
| +0x2AC | 17 × 16 | Frequency break points |

Break point: time (long), value (long), slope (long), y (word), x (word). x and y are the graph data. An x of 10000 marks an unused slot. Time, value and slope are recalculated at synthesis, but the file keeps the last results.

## Break points (F00B3C8)

The first record has time 1. For the amp envelope its value is 0. For each later record:

- time = x·3·rate/800 + 2, so x is in units of 3.75 ms, independent of the sound's length.
- slope of the previous record = (value − previous value) / (time − previous time).

Amp value:

- Carrier: y · fader · 0x50CB4
- Modulator (F009736: some partial's routing names it): y · fader · (fader · 0xA42). **The fader counts twice for modulators.**

Frequency value, using the float library above, in this order: base = (frequency >> 3) · ratio, passed through double-to-float-to-double around each step as the code does, then value = toLong((base·y/div + base) / rate), where the first record uses toLong(base / rate). A phase increment of 1 cycle is 2^29.

The headroom shift counts how many halvings bring the summed faders (of partials in the mix) to 64 or less.

**Check:** on `TBWERD1.PRM`, `prmdump.py` matches amp time, value and slope 43/43, and frequency time and slope 22/22. Frequency value matches only 21/22 using true doubles with rounding: P5 freq[1] computes 5109594 and stored is 5109593. The apparent "rounding" was really 24-bit precision followed by truncation. With `StFloat.h` and the same formulas, all 26 stored frequency values match, including the first records, and the original's own code on Musashi reproduces all stored break points.

## Kernels

`F00C28E` renders in chunks: 0xB3C8 break points; 0xBC2E for the partials in an FM group (lists built by 0xB2A2); 0xBD9E for the plain ones (0xB336), mixing onto the same buffer; 0xBF9E finds the peak. Normalising is a second pass over the file: x·32767/peak, truncated toward zero (F00BFCC). The file header is 0x538 bytes, the Sound Designer I size.

**Buffer bug in the original (confirmed):** 0xBC2E loops through `end` inclusive and 0xBD9E stops before it, while the caller advances by one buffer. So FM partials run one extra step per buffer, and the sound slips against the plain partials at each boundary. A chunk is (Malloc(-1) − 0x1000) / 2 samples, so the result depends on free memory, and only for sounds longer than the buffer. About 450,000 samples (10 s at 44.1 kHz) on a machine with roughly 900 KB free. Without FM, buffer size changes nothing. Model 1 needs a decision on which buffer size to imitate. The references use one chunk.

Per sample, per partial:

- Phase table index = high word of phase & 0x1FFE, a byte offset into an 8 KB table of 4096 words.
- amp += amp slope. inc += inc slope. phase += inc.
- At each time match, load the next break point.
- 0xBC2E sums into a long and shifts at the end. 0xBD9E shifts each partial before adding. These round differently.
- FM: the modulator's last output word (+4 of its state) is added to the phase high word, so this is phase modulation.

Waveforms:

| Value | Source | Notes |
|---|---|---|
| 1 | 0x16B7A | Sine |
| 2 | 0x1AB7A | Harmonic-limited square: odd harmonics at 1/k, with ripple on the flat tops. |
| 3 | 0x18B7A | Triangle: odd harmonics at 1/k². |
| 4 | 0x1CBB2 | 16384 words of noise-like data, indexed by sample number and multiplied by the sine at the partial's phase. Reviews' "filtered noise". No FM input. |
| other | Heap, 16 KB | 8-bit noise, indexed by sample number, ignoring pitch. |

## Randomness

The original is not repeatable, which affects testing:

- Each partial's starting phase = XBIOS Random << 1. Random is 24 bits and a cycle is 2^29, so phases fall in the first 1/16 of a cycle.
- The 8-bit noise table is filled from Random at startup (0x816).
- SmartSynth calls 0x4D22, a range helper over Random, from 47 sites.

Sample-for-sample comparison therefore needs Random pinned in the test harness.

## Reference renders

`tools/re/refrender.cpp` (built by `build_refrender.bat`) runs the original's 68K code on Musashi: its startup and init, its table setup, and all of F00C28E with its float library. It replaces only GEMDOS file calls, XBIOS Random, the status window and the file dialogs. Its Random is seeded with the TOS formula as recalled (seed = seed·3141592621 + 1, return bits 8..31), which has not been checked against a TOS ROM. That doesn't matter for matching, as long as the engine uses the same generator.

Order of Random calls: 8192 words for the noise table (the low word of each call), then one per active partial during the render.

`tools/re/check_reference.py` writes `tests/reference/` and runs 8 checks, all passing:

- Same seed gives an identical file; a different seed changes the audio.
- Without FM, buffer size changes nothing. With FM, a 2048-sample buffer first differs at sample 2048.
- The file header is correct, and the normalize formula predicts every sample.
- The patch's carriers stand above the gaps in the spectrum.

Files: `tbwerd1-seed1.sd`/`.wav` (normalized), `tbwerd1-seed1-raw.sd`, `tbwerd1-zero-raw.sd` (Random always 0), and `tbwerd1-wave{1..5}-{fm,plain}-raw.sd` (P5 and P8 set to that waveform, with or without FM routing).

Limits: these run on an emulated CPU, not checked against a real ST or Hatari. The main program before the render is not run, only the setup the render needs. The variants are test cases, not patches made in the program.

## Not part of a port

- Preview: 8-bit through a YM2149 lookup at 0xC190, or a cartridge port read at 0xC236 (0xFA0000 or 0xFB0000).
- Likely key-disk code: Floprd at 0x4E12 and 0x4F88. The startup code at 0x4AC computes a value but clears it before use, and may be the crack's work. Neither is confirmed.

## Next

1. Standalone Model 1 app, using the dialog and drag rules in "Editors" below.
2. The sampler transfers and MIDI Keyboard, which are not ported.
3. A real TOS ROM in Hatari. See "Zero divide" under SmartSynth.

## Engine (`src/engine/`)

`Engine.cpp` ports F00C28E, F00B3C8, F00B2A2, F00B336 and both kernels. `StFloat.h` is the float library. `WaveTables.cpp` is generated from the program by `tools/re/extract_tables.py`. `Patch.cpp` reads and writes `.PRM` files. There is no JUCE dependency. `src/cli` renders a patch from the command line, with the same options as `refrender`.

**Behaviour kept on purpose:**

- A modulator's fader counts twice.
- The lists run from the highest partial down, so a modulator above its carrier is heard one sample sooner than one below it.
- Waveform 4's plain kernel clears bit 16 of the phase on every sample. Its FM kernel works on a copy and doesn't. So the same patch differs by routing.
- Waveforms 4 and 5 take no FM input. The other value (`else` in the dispatch) is the 8-bit noise, including waveform 0 and anything above 5.
- The 8-bit noise in the plain kernel ignores its frequency contour.
- After its last break point, a kernel reads the next record's time from the file, even if it's a leftover. So unused records can change the sound, and they are kept byte for byte.
- The one-step FM buffer bug, through `RenderOptions::bufferSamples`. 0 renders in one pass.

**Differences from the original, because it would crash:**

- Equal break point times divide by zero, which bombs an ST. The engine uses a slope of 0 and warns. The fuzzer did not generate this case, so this path has run on neither side.
- A corrupt record count made the original write through its own memory. The engine stops at the end of the data and warns.
- Reading past the end of the partials returns 0. The original reads whatever memory follows.

**Verification** (compared with the original's 68K code on Musashi, as whole output files, header included):

| Test | Result |
|---|---|
| `tests/test_engine.cpp`, 14 reference renders: seeds, zero Random, normalize, all 5 waveforms with and without FM, small buffer | 14 of 14 identical, with GCC and with MSVC |
| `tools/re/fuzz.py`, 2000 random patches: odd waveform values, routing, self-modulation, leftover records, small buffers | 1893 identical, 107 rejected by both, 0 failures |
| 300,000-sample sound, one pass and two buffer sizes | Identical |
| Damaged files, 2000 | Engine finished on all of them |
| Planted bugs in copies of the engine, 10 | 9 caught. The tenth moved the 8-bit noise's phase, which nothing reads, so it can't change the output. |

**Limits:** all of this compares with the code in this cracked copy on an emulated CPU, not with a real ST or an uncracked copy. Patches from the fuzzer are random, not musical, and only one real patch exists. The render path is the only part ported so far.

## Documentation evidence

No Digidesign manual has been found. The user's *Archival User Guide Reconstruction* (November 2026, kept outside this repo because it reprints magazine images) is a secondary summary. Its links led to two ST reviews, read directly: [START, Oct 1988](https://www.atarimagazines.com/startv3n3/softsynth.php) (v2.1) and [Sound On Sound, Jul 1989](https://www.muzines.co.uk/articles/good-vibrations/5601) (says v2.0). Its other sources have not been read.

**Reviews and this binary agree:**

| Point | Reviews | Binary |
|---|---|---|
| Samplers | 15 (START); same models listed (SOS) | 15 Sampler menu entries, 14 models plus MMA SDS, including the SP-1200 |
| FM | A partial has one modulator; a modulator can drive many partials, or itself (SOS, START) | One modulator slot per partial in `.PRM` routing |
| Modulator in mix | Optional | "Include modulator in output mix" |
| Pitch range | 2% or 50%, per partial | Partial screen, Scale 2% / 50% |
| SmartSynth | 12 characteristics (START); figure matches | 12 groups in the RSC |
| Display | Four viewing angles | Four perspective entries in the RSC |
| External D/A | Two supported cartridges | "Use external D/A 1" and "2"; two cartridge ports in the preview code |
| Protection | Key disk, master in drive A | Consistent with `CRACK-AUDIT.md` |

**From the reviews, not yet confirmed in code:**

- Waveforms are sine, square, triangle, pink or band-limited noise, and white noise. Inferred mapping: wave 4 is the band-limited one, other values the white one.
- D/A cartridges are ST Sound Digitizer and ST Replay. Which is D/A 1 is unknown.
- All MIDI is on channel 1, with no Thru (START, v2.1). START expected a later version to add channel selection.
- Preview replays without resynthesis unless a parameter changed.
- Color and mono monitors both work. Only mono has been run here.

**Review claims since settled by the code:**

- Amplitude has 40 stages and pitch 15: F00CAE8 refuses a 41st and a 16th. The file's 42 and 17 records add the first record and the end record.
- Maximum length 37.5 seconds: the length field's limit is rate x 75 / 2, whatever the rate, up to 4000000. The rate field's lower limit is the same rule the other way round, and at least 100. The loader does not apply this, so a file can exceed it.
- Selecting a sampler sets a default sample rate: only partly. The Sampler menu just moves its check mark. File > New reads the selected sampler, see "Editors".
- Time slice edits interpolate: a new timbre event takes each partial's level from that partial's own curve (F00DD8E).

**Guide points that don't fit this binary:**

- Its FM checklist says one or more modulators per carrier. The file format allows one, as its own earlier text says.
- Its sampler table omits the SP-1200 and includes names this ST version lacks (Emulator II, Multisampler, Prophet 2002).
- It reports "phase locking" from the v2.1 announcement. This binary has no such string, and every active partial gets a random start phase. The announcement may mean the Mac version; it has not been read.
- SOS's "Sustain" has no matching label here. "Secondary level" is a possible match (inferred): it sets the level of amp record 2, where the decay stops.

**Leads:** the Nov 1987 Music Technology screenshot shows `A:\SYNTILAT.PRM`, "Syntillating", at 220 Hz, 31250 Hz, 60000 samples, a factory-style patch worth searching for. Reviews mention a 12-sound supplemental disk, a 3-disk library, and a free backup disk for registered owners.

## Whole-program run

Hatari 2.6.1-devel from `MacEmu\hatari`, EmuTOS 1.4 (192 KB US), ST, 1 MB, mono, GEMDOS drive, driven by `tools/re/hatari/`. The original program ran from copies, using its own menus, file selector, Synthesize command and file saving, on Hatari's cycle-exact CPU.

The seed was not chosen. EmuTOS keeps the Random state at 0x25CC; `rng_state.py` steps it back from a RAM dump. The recovered seed reproduced the whole 16 KB noise table in RAM before any audio was compared, so the audio plays no part in choosing it. This also confirms the Random formula under EmuTOS, but not under a real TOS ROM.

| Patch | Samples | Result against the engine |
|---|---|---|
| `TBWERD1.PRM`, unmodified | 20,000 | Identical at 41,336 bytes, one continuous pass (SHA-256 starts 235e14f773e49126) |
| TBWERD1 plus 5 partials, waveforms 1 to 5 | 20,000 | Identical (4836724d44c30099) |
| TBWERD1, length 300,000 | 300,000 | Identical (a692af48c99e9bec); envelopes end early, so this says nothing about buffer joins |
| TBWERD1, x values times 50, sustained FM (`stretch_patch.py`) | 800,000 | Identical at 1,601,336 bytes with `--buffer 690512` (7930fa0c51210ad7), and the harness too |

The last row is the strongest check. The GEMDOS trace shows `Fwrite` of 690,512 bytes, so that is the buffer here. One continuous pass first differs at sample 345,256, the first join, confirming the FM buffer bug in the running program. A buffer of 690,514 changes 6 bytes and 690,000 changes 1,517.

**So the original's output depends on the machine:** the buffer follows free memory, and any FM sound longer than the buffer changes with it. There is no single "correct" render of such a sound. Shorter sounds aren't affected. On this setup that means FM sounds under 345,256 samples.

Seen on this setup: normalize on, internal speaker, Akai S900 as the startup defaults. `.PRM` and `.SD` are the file selector masks. Window title format is path, then description.

Opened with no crash: partial editor, FM Patching, time slice (10 timbre events in TBWERD1), SmartSynth Set up, About, the File -> Sampler file selector, and MIDI Keyboard, which is the Record / Play / Pattern dialog. Preview ran and returned, with sound off, so nothing was heard. The About box does not display the "Cracked by The M.C.A." credit that sits in its resource tree.

Not run here: any mouse edit of a graph, any sampler transfer, MIDI, colour mode, a real TOS, a floppy boot, or a real ST. SmartSynth and the time slice conversions have since been run, see "Verification of the edits". The waveform patch and the stretched patch are test edits, not musical sounds; `TBWERD1.PRM` is still the only real patch.

Build notes: use a native make (`C:\perl\c\bin\gmake.exe`, "MinGW Makefiles") with MSYS2 mingw32 gcc, because MSYS make sends GCC's temp files to `C:\WINDOWS`. Copy `SDL2.dll`, `libgcc_s_dw2-1.dll` and `libwinpthread-1.dll` next to the exe. `hatari-windows-control.patch` adds the command file, applied to a separate copy of the source. Set `SDL_VIDEODRIVER=dummy` to run with no window. Put the patch in the test drive after boot, since `hat.py start` copies only the program files.

## SmartSynth (F003FD4)

Generate edits the patch in hand, in the fixed order below. Each rule whose heading is on rewrites what it covers and leaves everything else, so results depend on what was there, and repeated Generates build on each other. `rnd(a,b)` is F004D22: (b-a+1) x the low word of Random, high word of the product, plus a. Only partials 1 to 16 are generated. Doubling writes 17 to 32, and Percussion writes 32.

x is in units of 3.75 ms. `L` is length x 267 / rate, the sound's length in x units. `C` is the partial count from Harmonic range. i is the partial's index from 0.

| Order | Heading | Effect |
|---|---|---|
| 1 | Harmonic series | P1 fader 63. P2 to P16: All: rnd(10,63). Odd and Even: the same for that harmonic, 0 for the others. |
| 2 | Harmonic range | `C`: High rnd(12,16), Medium rnd(8,11), Low rnd(3,7), off 16. Faders from `C` up go to 0. **This step runs even when the heading is off,** so P17 to P32 are always silenced here. |
| 3 | Harmonic filter | P2 up to `C`, if the fader is on. Slight: 7 in 8 keep, rnd(15, 63-i x 40/C). Medium: 3 in 4 keep, rnd(5, 63-i x 55/C). Extreme: 1 in 2 keep, rnd(5, C x 7/(i+1)). Others go to 0. |
| 4 | Partial detuning | Ratio of harmonic h, 2 to 16, = h x (1 + rnd(-n,n)/10000), through the float library. n is 100, 200, 5000 for Slight, Medium, Extreme. P1 is 1.0. **Off leaves ratios alone**, so they are not reset to harmonics. |
| 5 | Frequency attack | Scale 50%. Bend: y -20 at x 0, -rnd(1,10) at x 2, 0 at rnd(5,10). Bite: fixed 30, 0, 20, 0, 10, 0 at x 0 to 5. Blip: P1 flat; others +50 or -50 from x 0 to rnd(2,5), then 0. |
| 6 | Frequency movement | Scale 2%. x steps of rnd(30,80) until `L`, at most 15. y alternates -m, +m. m is rnd(2,7), rnd(7,15), rnd(15,30). |
| 7 | Attack rate | Record 1 is y 103 at x: Fast rnd(0,5), Medium rnd(10,20), Slow rnd(30,80). Later records at or before it are removed. |
| 8 | Decay rate | Record 2's x = record 1's + Fast rnd(5,20), Medium rnd(20,70), Slow rnd(80,160). Creates record 2, y rnd(30,60), if missing. Later records at or before it are removed. |
| 9 | Secondary level | Record 2's y: High rnd(60,90), Medium rnd(30,50), Low rnd(5,20). Later records more than 20 away from it are set to it. |
| 10 | Fade | The last record's x = `L`. Ensures 3 segments. Equal rate and Highs first: last y 0. See below for the rest. Then any x at or before its predecessor is set to the predecessor + 17 - i. |
| 11 | Doubling | One factor for the whole patch, 1 + or - rnd/10000: rnd(12,40), rnd(50,125), rnd(150,300). P1 to P16 are copied whole to P17 to P32 at that ratio. FM routing is copied with modulators offset by 16. A modulator above 16 is cleared on both. |
| 12 | Percussion | Overwrites P32, fader 63. Dink: wave rnd(1,3), ratio rnd(2000,10000)/Hz. Bump: ratio 1/rnd(2,5), wave kept. Click: wave 4 (filtered noise), ratio rnd(800,4000)/Hz. Short envelopes. |

The Set up defaults are the startup code's at 0x8A0 and match the resource. The settings are not saved. `Save settings` writes only the sampler, normalize and D/A choice, 6 bytes, to `SOFTSYN.INF`.

Dialog rules (F003E10): turning a heading on selects its first option, so the previous choice is lost. Frequency attack and Frequency movement switch each other off. Options are disabled while their heading is off.

**Oddities kept on purpose:**

- *Lows first:* for partials below `C`, the last y = the one before / (C-i). A loop then counts from 3 to last, but its body always divides the last record's x, so that x is divided by (C-i) once per pass. It looks like a typing slip for dividing each record, as Highs first does.
- *Dink and Click ratios:* `divu` then `swap` puts the remainder, not a fraction, in the low word. So the ratio is slightly off, by an amount that depends on the patch's frequency.
- Generate does not touch waveforms, except for Percussion, or routing, except for Doubling.

**Zero divide:** Highs first divides each x from record 3 on by the partial's index, and the first index is 0. The 68000 traps. In EmuTOS 1.4 that vector (0xFC060A) is a bare `RTE`, the registers stay as they were, and the program carries on, so P1 keeps its x values and P2 is divided by 1. The port does this. **Whether a real TOS bombs here is not known.** We have run no TOS ROM. If it does, every Highs first on a real ST crashed, and that is the first thing to test with a ROM.

A malformed patch can hang the original in the Attack and Decay removal loops, where the count passes below the record being compared. The port stops after 42 removals. No patch in testing reached this.

## Editors

`src/engine/Edit.cpp`. Only x and y move in an edit. Time, value and slope stay in place and are recalculated at synthesis, so leftovers in the file don't follow their records.

| Function | Routine | Rules |
|---|---|---|
| Find, insert, remove | F00C9C8, F00CAE8, F00D296 | Amp 40 segments, frequency 15, then the "used all the break points" alert. Time slice conversion instead drops the last record. |
| Copy and Paste | F00E1F6 | Copies whichever of envelope, contour with scale, wave, ratio, fader. A bad "from" or range start means partial 1. A bad end is pulled to 32 or to the start, so a blank "through" copies to one partial. |
| Clear | F00E43A | Resets those fields: scale 2%, wave sine, ratio = partial number, fader 0. |
| FM Patching | F0027E0 | Set takes the modulator out of the mix and returns the target's old modulator to the mix if nothing else uses it. Clear only acts if that modulator is the one patched. The mix checkbox is disabled unless the partial is a modulator (F002A56). A partial can modulate itself. |
| New | F00216A, F000A0C | 440 Hz, 20000 samples, rate by sampler, below. Partials reset, unused records left as they were. |

New's rates: S612 26320. S700/X7000, S900, P-2000, MMA SDS 31250. FZ-1 36000. Emax, SP-1200 26000. Mirage 29412. DSS-1 32000. MKS-100, S-10, S-220, S-50 30000. **The DPX-1 has no entry and keeps the current rate.**

**Dialog limits (F001758):** frequency 1 to 32767 Hz, ratio 0 to 32767, rate and length as under "Review claims". Out of range offers Adjust, which clamps, or Cancel, which keeps the old value.

**Drag rules, for the app.** These are read from the code and not yet compared against mouse input:

- Graphs (F00CDAC): x = (mouse - left) / 3, and y = (base - mouse) shifted left by (2 - resolution), which is 0 on mono. Record 1 can sit on the previous record's x. Later records stay at least 1 unit after it, and every record stays at least 1 unit before the next. y stays inside the graph rectangle.
- Scroll: one page is 200 x units, 0.75 s. x on screen is offset by 200 x page.
- Left button in draw mode adds or moves. The right button, or erase mode, removes (F00C8BE: mode AND button = 1 draws).
- Faders (F0016E8): 0 to 63, or 0 to 103 for a timbre event. A left-button drag moves (3 - resolution) units per pixel, other buttons 1. A click on a partial number within 8 pixels picks that partial.
- Timbre events (F00DBFE): dragged in steps of 3 pixels, between their neighbours.

## Time slices

Entering the screen (F00D43E):

1. Each fader is multiplied into its envelope, then all faders read 63. A partial with fader 0 has its envelope cleared.
2. The harmonic curve takes every partial's x values, sorted, up to 40.
3. Every partial gets a record at each of those x values, interpolated from its own curve (F00DD8E), which goes through float with 24-bit rounding.
4. For each event, the curve's y keeps the sum of levels, and the levels are normalized to 103. Then the curve is normalized to 103.
5. Records within 6 of the line through their neighbours (F00DFE6) are removed: from the curve, and from the partials where all 32 agree.

Leaving (F00D89A) reverses this: curve x values become events if there's room, each event's levels become level x curve / sum, envelopes renormalize to 103 and faders take the rest, near-line records go per partial, and faders are scaled so the largest is 63.

**So the round trip changes a patch, even with no edit.** Levels are integers scaled several times, near-line records are thrown away, and the number of records can drop. Going in and out of the screen is not neutral, in the original or the port.

## Verification of the edits

| Test | Result |
|---|---|
| `refrender --edit-test 40000`: one random sequence on the original's 68K code and the port, comparing all partials, routing, the harmonic list and Random after each step. Includes SmartSynth, Set up, find, insert, remove, curve reads, both conversions, copy, clear, timbre copy and clear, FM, New. Dialog code runs for real against stubs answering the GEM calls. | 40,000 steps, 0 differences, 0 hangs, 35 full-graph alerts on both sides. One random patch in 6 has levels anywhere in the 16-bit range, to reach the word overflow in the scaling |
| Real program in Hatari on EmuTOS against the port, from RAM dumps with `tools/re/hatari/compare_ram.cpp`, with Random state and settings read from those dumps | Generate on 4 settings chosen in its own dialog, each on the last one's result, including Bite, Dink, Lows first and Highs first: 153, 662, 754 and 199 bytes changed, 0 differences, same Random stream |
| The same run, entering and leaving the time slice screen | 581 and 352 bytes changed, 0 differences, 5 events in the list |
| `tests/test_edit.cpp`, no emulator needed: 96 random settings on TBWERD1 and New, through Generate and both conversions | 96 of 96 match hashes from the 68K code |
| Planted bugs in copies of `Edit.cpp` | 23 tried, 21 caught. Not caught: skipping the float round trip in interpolation, which cannot change a result at normal levels and was not retried at extreme ones, and the overflow case of `divs`, which needs exactly -32768 / -1 and was never reached |

This comparison found a real error in the first port: it divided each x in Lows first, as the loop's intent suggests, instead of what the code does.

**Limits:** patches in these tests are random, or TBWERD1 and New. The real program was run on only those 4 settings and one time slice round trip. Mouse drags, the field checks in F001758, `setSliceLevel` and `moveSlice` are read from the code and not compared. Sound from edited patches was not compared here; they go through the render path checked earlier. All of it is this cracked copy, under EmuTOS rather than TOS.
