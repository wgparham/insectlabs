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

Values are 8 bytes, consistent with IEEE double. The accumulator is at -$4780(A4).

- 0x602C (`jsr $D8(A5)`) takes an opword. The high byte is the operand type: 0x2000 word, 0x2800 long, 0x1000 float. The low byte is the operation: 0 add, 2 multiply, 3 divide.
- 0x60DE long to double, 0x60EE double to long (rounds, see the check below), 0x60FE float to double, 0x610E double to float.

The break-point stage uses this library. The sample kernels are integer only.

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

Frequency value, in doubles and in this order: base = (frequency >> 3) · ratio, then value = (base·y/div + base) / rate. A phase increment of 1 cycle is 2^29.

The headroom shift counts how many halvings bring the summed faders (of partials in the mix) to 64 or less.

**Check:** on `TBWERD1.PRM`, `prmdump.py` matches amp time, value and slope 43/43, and frequency time and slope 22/22. Frequency value matches 21/22 using correctly rounded doubles: P5 freq[1] computes 5109594 and stored is 5109593. The original's own code on Musashi reproduces all stored break points, 5109593 included, so the difference is in its float library. A port needs that library's rounding, not native doubles, to match every value. Its add, multiply, divide and double-to-long routines have not been read yet.

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

1. Read the float library's add, multiply, divide and double-to-long routines (reached through the table at -$47A4).
2. C++ engine as a library with no JUCE dependency and an explicit seed, compared sample for sample with `tests/reference/`.
3. Standalone Model 1 app.
