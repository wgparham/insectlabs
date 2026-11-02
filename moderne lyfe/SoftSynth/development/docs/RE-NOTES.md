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

1. Standalone Model 1 app, and everything outside the render path: SmartSynth, time slices, the editors, the sampler transfers.
2. Extend the whole-program run in Hatari, see below.

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
- Amplitude has 40 stages and pitch 15. The file holds 42 and 17 records; the two extra in each may be the end points.
- D/A cartridges are ST Sound Digitizer and ST Replay. Which is D/A 1 is unknown.
- All MIDI is on channel 1, with no Thru (START, v2.1). START expected a later version to add channel selection.
- Preview replays without resynthesis unless a parameter changed. Time slice edits interpolate to the adjacent timbre events.
- Selecting a sampler sets a default sample rate.
- Color and mono monitors both work. Only mono has been run here.
- Maximum length 37.5 seconds (SOS, "seems"). The rate it assumes is not stated. The RSC length mask is 4000000.

**Guide points that don't fit this binary:**

- Its FM checklist says one or more modulators per carrier. The file format allows one, as its own earlier text says.
- Its sampler table omits the SP-1200 and includes names this ST version lacks (Emulator II, Multisampler, Prophet 2002).
- It reports "phase locking" from the v2.1 announcement. This binary has no such string, and every active partial gets a random start phase. The announcement may mean the Mac version; it has not been read.
- SOS's "Sustain" has no matching label here. "Secondary level" is a possible match (inferred).

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

Not run: SmartSynth Generate, any edit, any sampler transfer, MIDI, colour mode, a real TOS, a floppy boot, or a real ST. The waveform patch and the stretched patch are test edits, not musical sounds; `TBWERD1.PRM` is still the only real patch.

Build notes: use a native make (`C:\perl\c\bin\gmake.exe`, "MinGW Makefiles") with MSYS2 mingw32 gcc, because MSYS make sends GCC's temp files to `C:\WINDOWS`. Copy `SDL2.dll`, `libgcc_s_dw2-1.dll` and `libwinpthread-1.dll` next to the exe. `hatari-windows-control.patch` adds the command file, applied to a separate copy of the source. Set `SDL_VIDEODRIVER=dummy` to run with no window. Put the patch in the test drive after boot, since `hat.py start` copies only the program files.
