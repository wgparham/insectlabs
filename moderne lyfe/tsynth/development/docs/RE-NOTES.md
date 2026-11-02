# TurboSynth recovery notes (working file)

Target: Turbosynth SC 2.22 (Digidesign, 1995), a 68K MacApp / MPW Object Pascal application. These notes are the working record. `ALGORITHM.md` will be the finished write-up.

## Source files

| Archive | Contents |
| --- | --- |
| `Turbosynth_SCtm_2.22.img_.sit` | Raw classic HFS volume: app, Read Me, HyperCard "Turbo Reference", TS Files |
| `Turbosynth_2.0_.sit` | 2.0 and 2.21x3 apps, plus the 1988 example files. Its forks are AppleDouble |
| `turbosynth.cpt_.hqx` | 1988 demo app and the same example files |

The tools are in `tools/re/`: `hfsx` (HFS extractor), `rsrcx` (resource lister), `macdis` (68K disassembler, 64 segments), `annot.awk` (jump-table call names) and `rdsum.awk` (reader summary).

## Program structure

- **Code segments are named by module:** AOsc 39, AEnv 40, AShaper 41, AMod 42, AMix 43, ADelay 44, ACompand 45, APShift 46, ANoise 47, ADiffuse 48, ATimbre 38, ASamp 28, ACreate 29, ALoop 35, AOutput 37, AInitBlock 36, AClearBlock 34, GReadFile 9, GWriteFile 10, %_MethTables 18.
- **Jump table:** CODE 0. A call `jsr ($X,A5)` targets the entry at X−2. FSRead is JT `$190` (calls `$192`); FSWrite is `$198`.
- **Method dispatch:** MPW Object Pascal. A call to the selector stub (`F18_0001C`) is followed by an inline table: a count word, `0000 0000`, then (class word, JT offset) pairs. The class number is the high byte of the class word.
- **CLASINFO records (CODE 18):** words `0000 CCxx yy..`. CC is the class number, and the object size is `xxyy` (low byte of word 2 and high byte of word 3).

| Class | Number | Size |
| --- | --- | --- |
| TBLOCK | AC | 12A |
| TOUTPUT | B0 | 15E |
| TSAMP | B2 | 158 |
| TOSC | B4 | 140 |
| TENV | B6 | 138 |
| TAMP | B8 | 138 |
| TFILTER | BA | 138 |
| TSI | BC | 138 |
| TPENV | BE | 138 |
| TSHAPER | C0 | 334 |
| TMOD | C2 | 13C |
| TMIX | C4 | 172 |
| TDELAY | C6 | 13C |
| TTIME | C8 | 13C |
| TRES | CA | 13C |
| TCOMPAND | CC | 13A |
| TEXPANDER | CE | 13A |
| TCOMPRESS | D0 | 13A |
| TPSHIFT | D2 | 138 |
| TNOISE | D4 | 13A |
| TDIFFUSE | D6 | 138 |

- **DSP card programs:** the `SACD` resources, numbered by type code. 5 Osc, 6 Samp, 7 Amp, 8 Filter, 11 Shaper, 12 Mix, 13 Delay, 14 PShift, 101 MixTwo, 102 Pass, 103 Play, 104 Timbre, 105 Stereo play, 106 stereo samp, 110 Diffuser.

## Document format (TRBD data fork), from GReadFile

Big-endian throughout.

1. **Print record:** 120 bytes (TPrint, `iPrVersion` 3).
2. **Header:**
   - version `int16`: 1 = TurboSynth 2.0 files, 5 = 2.22.
   - if 2 < version < 5: 1 byte. If version == 1: 3 × `int32`, read into a local and dropped (F9_00E40).
3. **Module count:** `int16`.
4. **Each module:** type `int16`, then the base record, then the type record.
5. **Connection count:** `int16`, then (from `int16`, to `int16`) per connection.

**Base record** (F9_0038C), with object offsets:

| Object offset | Field |
| --- | --- |
| +4 | rect, 4 × `int16` (icon: top, left, bottom, right) |
| +C | kind `int16` (2000 osc, 2001 samp, 3000..3012 processors, 7000/7001/7002 outputs) |
| — | (version ≤ 4: 1 extra byte) |
| +10 | name, Str255. All 256 bytes are stored, padding is uninitialised memory |
| +110 | sample rate `int32` |
| +115 | flag, 1 byte |
| +11A | length `int32` (samples) |

**Type records:**

| Type | Module | Kind | Record after the base |
| --- | --- | --- | --- |
| 100 / 101 | Output (L) / Output R | 7000–7002 | version 1: skip 2 bytes, then +12E `int32`, +132 `int32`. Otherwise +146 byte, +148 `int32`, +14C byte, +12E `int32`, +132 `int32` |
| 5 | Oscillator | 2000 | +12A `int32`; count `int16`; count × waveform (554 bytes: Str31 name, +32E `int16`, +330 rect, 256 × `int16` samples). The same 554 bytes are the `.WVFM` file format |
| 6 | Sample | 2001 | +12E `int32`, +132 `int32`; version > 3: +154, +152, +156 `int16`; +13E flag byte; if the flag is set, length × `int16` of embedded sample data |
| 7, 8, 9, 19 | Amp Env, Filter, Spectral Inverter, Pitch Env | 3000, 3001, 3002, 3011 | version > 1: +136 `int16`; count `int16`; count × point (`int16`, `int16`, 8 bytes) |
| 10 | Modulator | 3003 | 5 × `int16` (+132..+13A) |
| 11 | Waveshaper | 3004 | 257 × `int16` |
| 12 | Mixer | 3005 | 32 × `int16` |
| 13 | Delay | 3006 | 5 × `int16` |
| 14 | Pitch Shifter | 3007 | 3 × `int16` |
| 15 | Stretcher | 3008 | 4 × `int16` |
| 16 | Resonator | 3009 | 5 × `int16` |
| 17, 18 | Compander / Noise (unconfirmed) | — | 4 × `int16` |
| 20 | Diffuser | 3012 | 3 × `int16` |

All 29 example documents parse with this layout.

**After the connections** (F9 at 17A6): an output whose +146 flag is clear takes its first input's rate (+110), length (+11A) and buffer. This applies to the first module (always the Output) and, in a stereo document, to Output R. Version-1 outputs are stored with rate and length 0, and +146/+148/+14C keep the initializer's 0, 0, 1 (CODE 36), so they get their source's rate and length here.

**Saving** writes version 5 in the same layout, whatever version was read.
- **Byte-level check (2026-10-30).**
  - All 13 version-5 example documents, read and written by the port, come back byte for byte. That needs the port to keep the 120-byte print record and every name field whole. Str255 and Str31 fields are stored complete, and the bytes after the string are whatever memory held. They are part of the document, so the port keeps them and writes the current name over them.
  - In the emulator, the original opened and saved Vibrato Example (version 5) and three version-1 documents: AM Example #2, Stretch Example #1 and Cheez-Verb. The port's save of the same input is identical for Vibrato Example. For the version-1 documents it is identical except for 7 bytes of the print record (bytes 1 and 13–23). Simple Reverb Model (version 5) shows the same 7 bytes. The original rewrites a print record that does not match the emulator's printer (these had `iPrVersion` 3; Vibrato Example's already matched), always to the same values. This is the Print Manager, so it depends on the printer, and the port leaves the record as read.
  - Not reproducible: the print record of a new document (the port writes a blank one with `iPrVersion` 3) and the leftover bytes in the name fields of new modules (the port writes zeros).

## DSP kernels (ACreate, CODE 29)

Small, call-free Pascal routines, interleaved with large setup routines. They use Q15 arithmetic: `muls`, add, add (×2), `swap` (that is, (a·b) >> 15, truncated). For example:
- `F29_00004`: mix into a buffer, `out = (out·g0 + in·g1) >> 15`.
- `F29_0006C`: clear a buffer.
- `F29_000E4`: the crossfade-loop kernel (see "Loops and the Sample window" below).

## Method tables

`tools/re/methtab` decodes the selector stubs. The compute method is selector `F18_00EB8` (the default TBLOCK one is `F29_00164`):

| Class | Function | Class | Function |
| --- | --- | --- | --- |
| TOSC | F29_0046A | TTIME | F29_02232 |
| TSAMP | F29_00A66 | TRES | F29_024D8 |
| TAMP | F29_00AD6 | TEXPANDER | F29_027A6 |
| TFILTER | F29_00E80 | TCOMPRESS | F29_02A66 |
| TSI | F29_012D2 | TPSHIFT | F29_02D12 |
| TSHAPER | F29_0174E | TPENV | F29_030F8 |
| TMOD | F29_01934 | TNOISE | F29_03540 |
| TMIX | F29_01DAA | TDIFFUSE | F29_03854 |

TDELAY isn't listed, so it inherits a compute method (to be found).

## Rendering model

- **Pull model:** each module renders its entire buffer: 16-bit samples, handle `+116`, length `+11A`, rate `+110`. Inputs are the list at `+11E`, in connection order. `+114` is the "computed" flag. TBLOCK's default compute (`F29_00164`) first computes every input that isn't computed yet (mode 2).
- **Compute mode** (`($c,A6)`): 2 = compute only. Other modes also preview the result (`F23_0104E`, playback only).
- **Normalize:** `F29_001BA`, TBLOCK method `F18_00EAA`. It runs only when the module's base flag `+115` is set. It finds min and max (`F23_008B8`); peak = max(|min|, max), with min −32768 meaning peak = 7FFF. If 0 < peak < 7FFF, every sample becomes `((x<<16)>>1 − x) / peak`, i.e. x·32767/peak (`divs`, truncating; `F29_000A2`).
- **Connections:** 1-based module indices, `from > to`. A module's inputs keep the connection order.
- **DSP card:** with the card flag `(-$4E9,A5)` set, modules run the card program instead. The port follows the host (68K) path.
- **Long arithmetic:** F1_020CA / F1_020FE are the runtime's 32-bit multiply and divide (to confirm).
- **Fixed-point traps:** A869 = FixRatio, A868 = FixMul, A86B = LoWord.

## Oscillator (TOSC, compute F29_0046A, kernel F29_00970)

- **Frequency:** `+12A`, Fixed (16.16).
- **Waveforms:** the list at `+132`. Each has samples at `+26` (256 × `int16`) and a time `+32E` (ms).
- **Ordering:** the waves are sorted by time with a bubble pass, and a wave with the same time as its predecessor gets +10 ms. Before use, sample[256] (`+226`) is set to sample[0] as the guard point.
- **Segments:** one per neighbouring pair (k, k+1), 20 bytes each: next, slope, end, waveA, waveB.
  - Δ = (t[k+1] − t[k]) for k > 1; for the first segment Δ = t[2]. Δsamples = Δ·sr/1000.
  - num = 7FFF. While Δsamples > 7FFF: Δsamples /= 2, num /= 2. slope = FixRatio(num, LoWord(Δsamples)).
  - end = t[k+1]·sr/1000, an absolute sample index.
  - Last segment: slope 0, waveA = waveB = last wave, end = length.
  - With a single wave there is just that last segment. With no waves the buffer is cleared.
- **Phase increment:** sc = 2000h (100h on the DSP card), s = sr. While s > 7FFF: s /= 2, sc /= 2. inc = FixMul(freq, FixRatio(sc, LoWord(s))).
- **Kernel:**
  - Each wave is expanded to 4096 `int16` entries: for i = 0..255, d = (w[i+1] − w[i]) / 16, truncated toward zero; output v, v+d, ... 16 values, using 16-bit adds.
  - Per sample: idx = ((phase >> 16) & 1FFE) / 2. out = (int16)(((gA_hi·A[idx] + gB_hi·B[idx]) >> 16) << 1). Then phase += inc; gA −= slope; gB += slope.
  - gA starts at 7FFF0000 and gB at 0, and both reset at every segment. At a segment's end the tables rotate (A ← B, B ← the next wave). The phase is continuous.
  - Segments whose end has already passed are skipped.
- **Normalization:** the oscillator never normalizes.

## Amplitude envelope (TAMP, compute F29_00AD6, kernel F29_00E08)

- **Breakpoints:** the list at `+132`. Each has level `+2` (0–100) and time `+4` (ms).
- **Segments** (16 bytes: gain, slope, end, next):
  - gain = FixRatio(level[k]·147h, 1)
  - slope = FixRatio(Δlevel·147h, Δsamples), halving both while Δsamples > 7FFF. Δsamples = (t[k+1] − t[k])·sr/1000.
  - end = t[k+1]·sr/1000
  - Last segment: gain = level[last], slope 0, end = length.
- **Input:** the first input's buffer and length. With no input it uses its own buffer with length 0, which gives silence.
- **Kernel:** out[i] = (int16)((gain_hi·in[i]·2) >> 16), then gain += slope. Past the input's end, out = 0.
- **Normalization:** afterwards, if `+115` is set.

## Filter (TFILTER, compute F29_00E80, kernel F29_011C2)

- **Breakpoints:** level `+2` (0–100) and time `+4` (ms), as for the envelope.
- **Segments:** c = FixRatio(103 − level[k], 2). The slope: d = c[k+1] − c[k] (Fixed). Δs = (t[k+1] − t[k])·sr/1000; while Δs > 7FFF, Δs /= 2 and d /= 2 (both long divides). slope = FixDiv(d, FixRatio(LoWord(Δs), 1)) (A84D = FixDiv). end = t[k+1]·sr/1000.
- **Last segment:** c = FixRatio(103 − level[last], 2), slope 0, end = length.
- **Kernel:** a forward-looking variable-width boxcar.
  - Setup: N = hi(c). S = Σ in[0..N−1], S1 = S + in[N], cnt = N, nxt = in[N]. head → in[N+1], tail → in[0].
  - Per output sample, while head < inputEnd:
    1. a = S / cnt (`divs`). out = a.
    2. b = S1 / (cnt + 1). out += (int16)(((b − a)·(lo(c) >> 1)·2) >> 16), using 16-bit `muls`.
    3. Slide: x = in[tail++]; S −= x; S1 −= x; S += nxt; nxt = in[head++]; S1 += nxt.
    4. c += slope; n = hi(c).
       - If cnt > n: S1 −= nxt; head −= 2; nxt = in[head++]; S −= nxt; cnt−−.
       - If cnt < n: S += nxt; nxt = in[head++]; S1 += nxt; cnt++.
  - At the end of a segment, load the next segment's c and slope. N is not reset; it adapts by ±1 per sample.
  - Once head reaches the input's end (head ≥ end, tested before each output), the rest of the output is zeros.
- **Normalization:** afterwards, if `+115` is set.

## Sample (TSAMP, compute F29_00A66)

The compute method only previews. The buffer is the loaded sound: data embedded in the document, or nothing. With +13E ("Save sound data with document", DITL 10005, on for new modules) cleared, the writer (CODE 10) stores no samples and the reader (F9 at 083C) sets the length to 0. No link to a sound file is kept: reopened, the module is empty and double-clicking it asks for a file (checked in the emulator, 2026-10-30).

## Spectral Inverter (TSI, compute F29_012D2, kernel F29_016A6)

- **Breakpoints:** level `+2` and time `+4` (ms).
- **Gain:** g(level) = 7FFF − level·FFFE/100, using long multiply and divide. Level 0 gives 7FFF, 100 gives −7FFF.
- **Segments:**
  - gain = FixRatio(g[k], 1)
  - d = g[k+1] − g[k] (long). Δs = (t[k+1] − t[k])·sr/1000.
  - While d > 7FFF, d < −8000 or Δs > 7FFF: d /= 2, Δs /= 2.
  - slope = FixRatio(d, LoWord(Δs)). end = t[k+1]·sr/1000.
  - Last segment: g[last], slope 0, end = length.
- **Step:** `+136` == 1 → step 80h; otherwise 40h.
- **Kernel:** a byte phase p, starting at 0, with p += step after each sample.
  - p == 0: out = in.
  - p == 80h: out = (gh·in·2) >> 16, where gh = hi(gain).
  - Otherwise: m = (sext(gh) + 7FFF) >> 1; out = (m·in·2) >> 16 (`muls` on the low word).
  - Then gain += slope. Past the input's end, out = 0.
- **Normalization:** afterwards, if `+115` is set.

## Waveshaper (TSHAPER, compute F29_0174E, kernel F29_018C0)

- **Table:** at `+132`, 257 × `int16` from the file. Before computing, entry 256 is overwritten with entry 255 (`+332` ← `+330`).
- **Input:** the first input, or its own buffer with length 0.
- **Kernel:**
  - x = in; i = ((x ^ 8000h) >> 7) & 1FEh (a byte offset, so the index is (x + 32768) >> 8); f = (x & FFh) << 7.
  - out = T[i] + (int16)((((int16)(T[i+1] − T[i]))·f·2) >> 16). The subtraction and addition are 16-bit and wrap.
  - Past the input's end, out = 0.
- **Normalization:** afterwards, if `+115` is set.

## Modulator (TMOD, compute F29_01934, kernels F29_01BC0 / F29_01C4C / F29_01CEA)

- **Parameters:** `+132` depth, `+134` mix (0–100), `+136` mode (1, 2, 3), `+138` the input number of the modulator, `+13A` the input number of the carrier. Input numbers are 1-based positions in the input list.
- **With no inputs:** both sources are its own buffer with length 0.
- **Gains:** dry = (100 − mix)·7FFF/100 and wet = mix·7FFF/100 (long multiply and divide).
- **Kernel arguments:** depth, then the modulator buffer and length, the carrier buffer and length, and the output.
- **Mode 1, AM** (F29_01BC0). d = p132·7FFF/100.
  - While both last: m = (int16)(((mod·d·2) >> 16) + (7FFF − d)) (16-bit add); out = (m·car·2) >> 16.
  - Modulator ended: out = (car·(7FFF − d)·2) >> 16.
  - Carrier ended: out = 0.
- **Mode 2, delay modulation** (F29_01C4C). d = p132 (raw).
  - The carrier pointer p starts at car + 20 samples and stops at carEnd − 30 samples.
  - Per sample: v = mod·d (32-bit); off = hi(v) (signed); f = lo(v) (unsigned). c0 = car[p + off], c1 = car[p + off + 1], D = c1 − c0.
  - If D ≥ 0: out = c0 + ((D·f) >> 16) (`mulu`). If D < 0: out = c0 − ((−D·f) >> 16). Then p++.
  - Modulator ended: copy car[p++]. Carrier at its stop point: out = 0.
- **Mode 3, rate modulation** (F29_01CEA). d = p132·30 (16-bit).
  - The carrier base is car + 2 samples, with the limit carEnd − 2 samples. Position P = 0 (16.16).
  - Per sample: P += (int32)(int16)hi(mod·d), the signed high word added into P's fraction. i = P >> 16 (signed); f = lo(P). Interpolate car[base + i] and car[base + i + 1] as in mode 2. Then P += 10000h.
  - On signed overflow of P: base += 32768 samples, P &= 7FFFFFFF.
  - Before each read it tests the previous read position (not the new one) against the limit, so one more sample is interpolated; zeros from then on. An overflow when adding the modulation moves the base and writes no sample for that step. Modulator ended: copy the carrier from the last read position until it equals the limit.
- **Dry blend:** after the kernel, if mix < 100: out = (car·dry + out·wet)·2 >> 16 (F29_00004; the carrier is the `+13A` input). This blend runs only up to the carrier's length.
- **Normalization:** afterwards, if `+115` is set.

## Mixer (TMIX, compute F29_01DAA, kernel F29_02122)

- **Gains:** the sliders s[i] at `+130 + 2i` (i = 1..32), applied to inputs in list order. T = Σ s[i] over the connected inputs.
- **No inputs, or T == 0:** one record with gain 0 on its own buffer, length 0, which gives silence.
- **Per input:**
  - If count == 1, s[i] == T or T < 100: g = LoWord(FixRatio(s[i], 200)).
  - Otherwise: g = LoWord(FixRatio(s[i], 2·T)).
  - **Quirk:** s = 100 gives 8000h, which the signed `muls` treats as −32768, so the input comes out inverted.
- **Kernel:** out[j] = (int16)(((Σ over inputs with j < len: g·x[j]) · 2) >> 16), with a 32-bit sum.
- **DSP card only:** a sample-rate-dependent count (8, 7, 6, 5 ... at thresholds 5973h, 609Eh, 7336h, 7F49h, A180h). The host path ignores it.
- **Normalization:** afterwards, if `+115` is set.

## Other kernels

- F29_021A8: out = clip16(in1·g·2 >> 16 + in2), with a zero-filled lead-in. Its only callers are the Delay (F29_02232) and the Resonator (F29_024D8).

## Delay (compute F29_02232; the method table's class C8, my "TTIME" label, is really the Delay; kernel F29_021A8)

- **Parameters:** `+132` coarse, `+134` fine, `+136` feedback %, `+138` polarity (2 = inverted), `+13A` mix %.
- **Delay length:** D = (coarse·100 + fine)·sr / 10000 samples (long multiply and divide). D == 0 becomes 1, and D > length becomes length.
- **Input:** the first input, or its own buffer with length 0.
- **Gains:** fb = p136·7FFF/100, negated (16-bit) if polarity == 2. dry = (100 − mix)·7FFF/100, wet = mix·7FFF/100.
- **Kernel F29_021A8:**
  - out[0..D−1] = 0.
  - For j ≥ D while the input lasts: v = ((out[j−D]·fb·2) >> 16) + in[j−D], as a 16-bit add. On overflow, write 8000h if the wrapped result is non-negative and 7FFFh if it's negative (that is, saturate).
  - After the input ends: out[j] = (out[j−D]·fb·2) >> 16.
- **Blend:** if mix < 100: out = (in·dry + out·wet)·2 >> 16 over min(length, input length) (F29_00004).
- **Normalization:** afterwards, if `+115` is set.

## Resonator (compute F29_024D8, method-table class CA; kernel F29_021A8, the Delay's)

- **Parameters:** `+132` coarse, `+134` fine, `+136` feedback %, `+138` mode (2 = odd harmonics), `+13A` mix %.
- **Pitch:** FixRatio(coarse·100 + fine, 10), in Hz (Fixed); a result of 0 becomes 1 (raw).
- **Delay:** D = FixDiv(sr, pitch), with sr passed as a plain long, so the result is samples. If mode == 2, D /= 2. D == 0 becomes 1; D > length becomes length.
- **Gains:** fb = p136·7FFF/100, negated if mode == 2. dry and wet from p13A, as for the Delay.
- **Then exactly as the Delay:** F29_021A8 with fb, then the blend if mix < 100, then normalization if `+115` is set.
- **Open question:** the exact rounding of FixRatio, FixMul and FixDiv (Toolbox). It's needed for bit-exactness.

## Stretcher, expanding (compute F29_027A6, class CE "TEXPANDER"; kernel F29_029BA). Type 15, 4 words

- **Parameters:** `+132` coarse, `+134` fine, `+136` start (×10 ms), `+138` amount (1–100).
- **Pitch:** FixRatio(coarse·100 + fine, 10). P = FixDiv(sr, pitch) samples; if the pitch is 0, P = sr.
- **Lengths:**
  - adv = P·4·(101 − amount)/100 (long multiply, then divide)
  - S = start·10·sr/1000, at least P
  - slope = 7FFF0000 / P (long divide)
- **Kernel** (in, inLen, out, outLen):
  1. Copy in → out until in reaches in + S.
  2. Loop:
     - B = A − P (one period back); gA = 7FFF0000, gB = 0.
     - While B < boundary: out = (int16)(((hi(gA)·in[A++] + hi(gB)·in[B++]) << 1) >> 16); gA −= slope; gB += slope.
     - Then A = B; boundary += adv.
     - Copy in[A++] → out until boundary.
  3. Stop at outLen. As soon as the read position reaches the input's end (in the crossfade or the copy), zeros to the end.
- **Effect:** each cycle inserts one period. The output is longer than the input by (adv + P)/adv.
- **Normalization:** afterwards, if `+115` is set.

## Stretcher, compressing (compute F29_02A66, class D0 "TCOMPRESS"; kernel F29_02C72)

- **Parameters:** as for the expander. P = FixDiv(sr, FixRatio(coarse·100 + fine, 10)), or sr if the pitch is 0.
- **Lengths:** L = P·(101 − amount)/10 (crossfade length). S = start·10·sr/1000. slope = 7FFF0000 / L.
- **Kernel** (arguments L, P, slope, S, outLen, out, inLen, in; confirmed):
  1. Copy in → out until in + S. It is a do-while: at least one sample is copied even when S is 0 (verified in the emulator).
  2. Loop:
     - B = A + P; end = A + L; gA = 7FFF0000, gB = 0.
     - While A < end: out = (hi(gA)·in[A++] + hi(gB)·in[B++]) << 1 >> 16; gA −= slope; gB += slope.
     - Then A = B.
  3. Stop at outLen. Zeros once B (before each crossfade sample) or A (in the first copy) reaches the input's end.
- **Effect:** the output is shorter by about L/(L + P).

## Formerly open (2026-10-29)

All the items once listed here are settled: the class of each module type, the Toolbox and long arithmetic (see "Arithmetic"), every module's kernel, and the Output's behaviour. Every module except the Diffuser is verified against the original in an emulator (see "Verification against the original"). Sample modules whose sound is not stored come back empty, in the original and in the port (see "Sample").

## Pitch Shifter (TPSHIFT, compute F29_02D12, kernel F29_02FB6). Type 14, 3 words

- **Parameters:** `+132` semitones (signed), `+134` fine (0–100, a fraction of a semitone), `+136` mix %.
- **Input:** only if exactly one input is connected (the count == 1). Otherwise its own buffer with length 0.
- **Ratio:** r = FixRatio(1,1). For k = 1..semis, r = FixMul(r, FixRatio(10595, 10000)). For negative semis, multiply by FixRatio(10000, 10595) |semis| times.
- **Fine tune:** u = FixRatio(10000, 10595); d = r − FixMul(r, u); rate = r + d·fine/100 (long multiply and divide).
- **Kernel** (out, outLen, in, inLen, rate):
  - Position P = 0 (16.16); limit = in + inLen − 3 samples.
  - Per sample: i = P >> 16 (signed); f = lo(P). D = in[i+1] − in[i]. If D ≥ 0: out = in[i] + ((D·f) >> 16) (`mulu`); else out = in[i] − ((−D·f) >> 16). Then P += rate.
  - On signed overflow: base += 32768 samples, P &= 7FFFFFFF.
  - The limit is tested against the previous read's address (D2, 0 before the first read), as in the Modulator's mode 3, so one more sample is interpolated once the limit is passed; zeros from then on. (Found by the stress patches, 2026-10-30.)
- **Blend:** if mix < 100: out = (in·dry + out·wet)·2 >> 16, with dry = (100 − mix)·7FFF/100 and wet = mix·7FFF/100, over the input's length.
- **Normalization:** afterwards, if `+115` is set.

## Ratio table F29_03044 (index 0..11 → Fixed)

The values: 0.5, 0.66667, 0.79459, 0.94379, 0.98499, 1.0, 1.01500, 1.05951, 1.25945, 1.5, 2.0, 2.0. Hex: 8000, AAAB, CB29, F19C, FC28, 10000, 103D7, 10F3B, 14295, 18000, 20000, 20000 (each shifted left 8 bits in the code). Index 11 is also the default. Probably used by the Pitch Env.

## Pitch Env (TPENV, compute F29_030F8, kernel F29_03488). Type 19

- **Breakpoints:** level `+2` (0–100) and time `+4` (ms).
- **Rate(level):** i = level / 10, r = level mod 10 (`divs`). rate = T[i] + (T[i+1] − T[i])·r/10 (long multiply and divide), with T the table F29_03044 in 8.24 form (8000h<<8 = 0.5, ... 1000000h = 1.0 at index 5). Level 50 is unity.
- **Segments:** start = rate(level[k]); slope = (rate(level[k+1]) − start) / Δs (long divide), Δs = (t[k+1] − t[k])·sr/1000; end = t[k+1]·sr/1000. Last segment: rate(level[last]), slope 0, end = length.
- **Kernel** (out, outLen, in, inLen, segments):
  - If inLen ≤ 4: zeros.
  - Position P = 0 (8.24); limit = in + inLen − 4 samples.
  - Per sample: Q = P >> 8 (arithmetic); i = hi(Q); f = lo(Q). Interpolate in[i] and in[i+1] (unsigned f, sign-split as in the pitch shifter). Then rate += slope; P += rate.
  - On signed overflow of P: base += 128 samples, P &= 7FFFFFFF.
  - A new segment reloads rate and slope. Before each read the previous read position is tested against the limit (an overflow resets it to the new base); zeros from then on.
- **Normalization:** afterwards, if `+115` is set.

## Noise (TNOISE, compute F29_03540, kernel F29_037C0). 4 words at +12A

- **Parameters:** `+12A` coarse (×10 Hz), `+12C` fine (0.1 Hz), `+12E` amplitude randomness (0–100), `+130` frequency randomness (0–100).
- **No inputs, no normalization.** It writes its own buffer, of length `+11A`.
- **Random generator:** QuickDraw randSeed `(-$1F2,A5)` is set to 12345 at every compute. Toolbox Random: seed = seed·16807 mod 7FFFFFFF; result = low word, with 8000h becoming 0. Verified in the emulator (see "Verification against the original").
- **Table:** `TSWT` resource 1, "Sine Wave": 256 × `int16`, stored as `tswt_sine.bin`.
- **Frequency:** freq = FixRatio(p12A·10, 1) + FixRatio(p12C, 10).
- **Increment:** sc = 200h, s = sr; while s > 7FFF: s /= 2, sc /= 2. inc = FixMul(freq, FixRatio(sc, LoWord(s))).
- **Amplitude mask A:**
  - p12E == 0: 0
  - 1..49: (p12E·FFFFh/100) | FFh
  - 50..100: (p12E·FFFFh/100) | FFFh
  - Out of range: undefined in the original
- **Frequency mask F:** p130 == 0 → 0. Then bands of 6: 1..6 → 1, 7..12 → 3, 13..18 → 7, ... 85..90 → 7FFFh, and 91..100 → FFFFh (each band doubles the mask plus 1).
- **Kernel:**
  - M = inc | inc>>1 | ... | inc>>21 (all bits below the top bit of inc).
  - Per sample:
    - r = Random(); s = T[((phase >> 16) & 1FEh) / 2].
    - out = s − (int16)((((int16)(r & A))·s·2) >> 16) (16-bit subtract).
    - j = ((int32)(int16)(r & F) << 5) & M, negated if r < 0.
    - phase += inc; phase += j.

## Diffuser (TDIFFUSE). Type 20, 3 words. Host code F29_03854; card driver F29_03AA4; DSP program `SACD` 110

- **Parameters:** `+132` coarse, `+134` fine, `+136` amount (%).
- **Delay:** D = FixRound(FixMul(FixDiv(sr, 10000), FixRatio(coarse·100 + fine, 10))) samples (A840 = FixRound), clamped to length.
- **Gain:** g16 = amount·7FBCh/100 (long multiply and divide).
- **Lines:** three delay lines of D samples each in one block (NewPtr(D·6), L2 at +D, L3 at +2D), all zero at the start, with one shared circular index that wraps before use. With D = 0 the block is empty and all three lines are the same cell just past it, so each stage's write feeds the next stage; the original starts that cell with whatever memory held, the port with 0.
- **Without a card:** BlockMove input → output (input length; the rest of the buffer is untouched). The port uses the card algorithm (the user's decision).
- **Host protocol** (56001 host port, `tools/re/dsp56dis`): host command 93h (vector P:$26 → $6A). Parameter words: G = g16<<8, N = length (24 bits), flag. Per sample i (0..N−1), with k = i mod D:
  - send x (input or 0); send L1[k], receive L1[k]; send L2[k], receive L2[k]; send L3[k], receive out[i], receive L3[k].
  - 16-bit values travel in the low 16 bits of a 24-bit word. The DSP shifts them up (mpy by 80h, take a0), so x = s<<8.
- **DSP arithmetic:** 24-bit fractions. A = 56-bit accumulator. mpy gives A = 2·a·b. "move a,reg" takes A1 with saturation and no rounding.
  - Stage 1: X = x<<8, Dv = L1<<8. W1 = sat24((2·G·X + Dv·2^24) >> 24). S1 = sat24((X·2^24 − 2·G·W1) >> 24). Send S1 (stored to L1).
  - Stage 2: input W1, delay L2. W2 = sat24((2·G·W1 + (L2<<8)·2^24) >> 24). S2 = sat24((W1·2^24 − 2·G·W2) >> 24). Send S2 (stored to L2).
  - Stage 3: input W2, delay L3. W3 = sat24((2·G·W2 + (L3<<8)·2^24) >> 24). Send W3 (the output). S3 = sat24((W2·2^24 − 2·G·W3) >> 24). Send S3 (stored to L3).
  - **Sending Y:** mpy by 008000h, then A1 = Y >> 8 (arithmetic); the host keeps the low 16 bits, so the value is (int16)(Y >> 8).
  - **Precision:** W1 and W2 carry 8 extra bits between stages. Line values and the output are truncated to 16 bits.
- **Transfer function:** each stage is (g + z^−D)/(1 + g·z^−D), and the three stages are cascaded.
- **Normalization:** afterwards, if `+115` is set.
- **Cross-checked (2026-10-29):** the host code (F29_03854, F29_03AA4) was re-read against the port step by step, and the DSP program was run on an independent DSP56001 emulator (the core of Hatari, the Atari Falcon emulator) with the host port driven exactly as F29_03AA4 drives the card. Its output equals the port's for all ten Diffusers in Bright Hall 01, Non-Linear Reverb and Simple Reverb Model (up to 154,605 samples each) and for edge cases: amount 100 with a full-scale input (saturation), delays of 0, 1 and 4410, and output longer than the input. That run found one fix, the shared cell for D = 0. The harness (Hatari's GPL DSP core plus a driver) is kept outside the repository, in `C:UserswgparhamMacEmudsptest`.
- **Host code run on a 68K emulator (2026-10-30).** TurboSynth's own CODE 29 runs on the Musashi 68K core: F29_03854, with F29_00164, F29_0006C and the card loop F29_03AA4. The card's host-port registers (base + 3 + 4·n) are wired to the Hatari DSP56001 core running SACD 110.
  - Only calls that leave CODE 29 are stubbed: FixRatio/FixDiv/FixMul/FixRound, long multiply and divide, NewPtr (returning junk-filled memory), input lookup, program load and notifications.
  - The result equals the port's for all ten example Diffusers, and for these edge cases: delay 0, delay 1, amount 100, delay 0 with amount 100, an output 20,000 samples longer than its input, and a delay clamped to the length. The stack comes back balanced.
  - So the whole Diffuser path is now checked by execution, not just by reading: host setup, host protocol and DSP program. Only the card hardware itself is assumed.
  - Size coarse above 327 overflows the original's `muls.w` (coarse·100 in 16 bits) and gives a negative delay. The port wraps the same way, but what the original then does (NewPtr of a negative size) is undefined.
  - The harness is `harness68k.cpp` in `C:\Users\wgparham\MacEmu\dsptest`, outside the repository, with Musashi in `MacEmu\musashi`.

## Resolved

- **Types:** 15 = Stretcher, expanding (TEXPANDER); 17 = Noise (TNOISE, 4 words at `+12A`); 18 = Stretcher, compressing (TCOMPRESS). This comes from the CODE 9 allocation offsets ($157C, $15B4, $1584) placed in CLASINFO order: $154C Delay(13), $1554 Res(16), $157C Expander(15), $1584 Compress(18), $158C PShift(14), $15AC PEnv(19), $15B4 Noise(17), $15D4 Diffuse(20).
- **Rates:** every module keeps its own sample rate and length. Buffers pass between modules unconverted (a 30 kHz sample can feed a 31.25 kHz envelope); a module's rate only scales its own timing.
- **Output** (types 100/101, kinds 7000/7001/7002): has no compute method of its own (TBLOCK default), so its result is its input's buffer. Type 100 is mono "Output" or "Output L"; 101 is "Output R".
- **Sound Designer I (SFIL/XFER):** header size in the first word (0538h = 1336); data length in bytes at 94h and B8h; then big-endian `int16` samples. The rest of the header is mostly uninitialised memory. The port takes the rate and length from the patch's module.
- **Sample modules without embedded data** come back empty (length 0), as in the original. The port no longer looks for sound files by name.

## Arithmetic (verified in the emulator except where noted)

- **FixRatio(n, d) truncates** toward zero: FixRatio(4096, 22050) = 12173, not 12174. Verified on the oscillator phase increment, which only matches the original with the truncated value, and on falling envelope slopes (negative numerators).
- **FixMul(a, b) rounds** to nearest: the oscillator increment FixMul(3624140, 12173) = 673166.75 must be 673167.
- **FixDiv(a, b) rounds** to nearest: truncating it breaks the Filter, Stretcher and Resonator renders.
- FixRound(f) = (f + 8000h) >> 16 (not exercised separately).
- Long multiply and divide (F1_020CA, F1_020FE): C semantics, truncating toward zero.
- Random: seed = seed·16807 mod 7FFFFFFF; return the low word, with 8000h becoming 0. Verified with seven single-Noise test patches (pure tone; amplitude randomness 20 and 75; frequency randomness 10, 50 and 95; both at 40), which match the original exactly. The 8000h case occurs at draw 14294 from seed 12345, inside the masks of two of them.

## Verification against the original (2026-10-29)

- **Setup:** Basilisk II (kanjitalk755/macemu, built with MSYS2 MinGW32, JIT off) with a Quadra 650 ROM and System 7.5.3 runs the original Turbosynth SC 2.22 from the extracted HFS volume. A host folder is mapped as a drive the Mac sees (Basilisk's ExtFS; Finder type/creator live in `.finf\<name>`, so documents get TRBD/TRBS). For each patch the original's File > Save Output As writes a Sound Designer II file (data fork: big-endian 16-bit samples, interleaved for stereo) straight to the host, and a small tool compares it sample for sample with `tsynth-cli`. Renders are deterministic: the same patch saved twice gives identical files.
- **Result:** all 45 example patches that can run without a Digidesign card match the original exactly, including the four stereo patches and the output crossfade. Bright Hall 01, Non-Linear Reverb and Simple Reverb Model use the Diffuser, which the original refuses without the DSP card ("A Digidesign DSP Card is required to use the Diffuser"), so the Diffuser is still checked only against its DSP program. Seven test patches with a single Noise module (none of the examples uses one) also match exactly, Seven test patches with a compressing Stretcher (also unused by the examples) match too, after one fix (its first copy always takes a sample), so every module except the Diffuser is now verified.
- **Method for a mismatch:** a probe patch connects one module straight to the Output (written by the port as a version-5 document); rendering probes in both programs shows the first module that differs.
- **What the comparison corrected:**
  - FixRatio truncates (above). It set every oscillator's phase increment off by a few units.
  - Modulator mode 3 (F29_01CEA) tests the stop condition against the previous read position, before computing the new one, so it outputs one more interpolated sample; an overflow when adding the modulation moves the base and writes no sample for that step; after the modulator ends, the carrier is copied until the read position equals the limit.
  - The Filter (F29_011C2) stops when head ≥ input end, one sample earlier than the port did.
  - The Pitch Envelope (F29_03488) also tests the previous read position before each read; an overflow resets it to the new base.
  - The expanding Stretcher (F30_010BC / F29_029BA) fills zeros as soon as the read position reaches the input's end, in the crossfade or the copy; it does not restart from the crossfade's other position.
  - The compressing Stretcher (F29_02C72) copies at least one sample before its first crossfade, even with a start of 0.
  - **Version-1 documents** (the TS Files patches): the reader (F9_00BCC) stores the Stretcher amount as 100 − amount; and the envelope reader (F9_00856) does not read `+136`, so the module keeps its initial value 1 (CODE 36 initializers). For the Spectral Inverter that selects step 80h instead of 40h.
- **Stress patches (2026-10-30):** 15 patches built to reach the kernels' rare paths, rendered in the original and compared sample for sample:
  - Match: the oscillator's 16-bit interpolation wrap (a table alternating ±full scale, a hashed table at 9973 Hz, a full-scale saw); the Delay and Resonator at 99 % feedback, both signs, over full-scale input (the saturation rule); the Modulator's modes 1 and 3 at full depth over 100,000 samples (read position passing 32768); the Pitch Shifter an octave down; the Pitch Envelope 0 → 100 → 0 over 100,000 samples; the Amplitude Envelope and Filter at full level on a full-scale square.
  - Fixed: the Pitch Shifter an octave up wrote one sample too few at the end (above).
  - Not reproducible: the Modulator's mode 2 at depth 32767 reads up to ±16384 samples away from the carrier position, outside its buffer; the original then reads whatever memory lies there. Every sample read inside the buffer matches.
  - All 45 earlier example references and the Noise and compressing-Stretcher probes still match.

## Engine status (2026-10-28)

- **Code:** `src/engine` (Patch, Engine, MacMath, SineTable) and `src/cli` (`tsynth-cli patch out.wav [info] [module=N]`). It builds warning-free with MSVC /W4.
- **Example patches:** all 29 render (2.22 TS Files, Best of Disk 1 and 2, Sample Files), with no missing sound files.
- **Checks** (`tests/modstats.cpp`):
  - The Analog Synth Model oscillators measure 110, 55, 55 and 1.5 Hz, matching the stored Fixed frequencies.
  - Every module type gives non-silent output without runaway DC, except where a patch's own feedback settings saturate the 16-bit delay kernel (Cheez-Verb's 75% delays, More Big's 97% resonator), which is expected.
- **Verified (2026-10-29):** see "Verification against the original": 45 of the 48 example patches render identically to the original in an emulator; the three Diffuser patches need the DSP card.
- **Port decisions:**
  - Outputs are the input of type 100 (left/mono) and 101 (right).
  - A version-1 output with rate 0 takes its source's rate.
  - Sample modules have no sound-file lookup; a sound is either stored in the document or absent.
- **Next:** the JUCE standalone app (patch view, module editors, render/play/export), CI workflow, README/ALGORITHM docs, then Model 2.

## App status (2026-10-28)

- **Look:** a JUCE standalone in `src/app` with a Classic Mac look (the user asked for one "closer to its original look", with a dark mode). Light is black on white with a dither desktop and pinstriped windows; View > Dark Mode is the same drawing in warm light ink on charcoal, saved in `TurboSynth.settings`.
- **Icons:** the original ICON resources are embedded in `src/app/ClassicIcons.cpp`, generated from the 2.22 resource fork. Module icons are numbered by kind: 2000 osc, 2001 sample, 2002 noise, 3000–3012 processors (3008 expanding stretcher, 3010 compressing stretcher), 7000–7002 outputs. Tool icons: 1000 arrow, 1001 eraser, 1003 speaker, 1005 patch cord, 1006 pencil, 1007 rake (Harmonic window). 7001 and 7002 are the left and right halves of the stereo jack (each lacks one border), and 2004 and 2003 the left and right halves of a linked stereo sample.
- **Patch window:** the original's palette, three to a row: arrow, patch cord, eraser; Mixer, Info, speaker; then the module types in the original's order (checked in the emulator 2026-10-30). Place a module by clicking its icon, then the patch. The patch cord connects, the eraser removes, the speaker tool hears a module, and the Info tool or a double-click opens it (an empty Sample module asks for a sound, F28_00628). Without a DSP card the original shows the Diffuser crossed out (icon 3013); the port computes it, so it shows 3012. The view fits large patches.
- **Windows (2026-10-30):** as in the original, a desktop holds the patch window and one window per opened module (double-click, or the Info tool). Windows move by the title bar, resize by the grow box and close by the close box; the front one has the pinstriped title and is the module the menus act on. A module window holds its settings: numeric fields, a breakpoint editor (envelope family), drawable tables (oscillator waves, shaper) and the tool palette.
- **Sound:** a Sample or Output window shows its sound beside its palette, with Play and Stop and the play cursor; one live sound view moves to whichever of those windows is in front, and the others keep a picture of theirs. File > Export Sound writes 16-bit WAV or AIFF.
- **Snapshots:** `TurboSynth [patch] --snapshot out.png [--dark] [--select N]` renders the window off screen (for docs and tests).
- **Tests:**
  - Real-click on Windows: selecting, placing a module from the palette, connecting, Dark Mode from the menu, Play.
  - Engine round trip (`tests/roundtrip.cpp`): 29/29.
- **Not yet done:** Model 2, an ALGORITHM.md write-up, Mac/Linux runs, a DAW-style keyboard preview.

## Example patches (2026-10-28)

- **Complete set:** the full 2.22 "TS Files" set is 48 patches plus 16 sound files and 20 waveforms, 84 files in all. It's collected in `moderne lyfe/tsynth/patches` (outside this folder).
- **Seven files:** Additive Example #1, Resonance Example #5, Stretch Example #1/#3/#4, Amplitude Envelope Demo and Spectral Inverter Demo could not be written by hfsx or unar on this machine. They came from the 1988 Compact Pro archive instead, with sizes identical to the 2.22 catalog. Earlier checks used only the 29 that had extracted.
- **Results:** all 48 render and round-trip.
- **CLI fix:** `tsynth-cli` now reads its arguments as UTF-8 on Windows, so "π Mutant" opens.

## Fixes after the first test round (2026-10-28)

- **Normalize (F29_001BA) was wrong in the port.**
  - The original computes n = ((x zero-extended) << 16, then `asr.l #1`) − x. The shift is arithmetic, so n = x·32767 for negative x too.
  - The port used a logical shift, so every negative sample became garbage, and any module with Normalize on sounded like noise (reported on Cheez-Verb).
  - Fixed. Most of the "saturating by design" flags in `tests/modstats` came from this and are gone. What remains: two very high-feedback resonators, and DC from asymmetric waveforms.
- **App speed:**
  - The dither and the 1-bit icons were drawn pixel by pixel, roughly half a million fillRect calls per repaint. The dither is now a cached tiled image. Icons are cached vector paths built from pixel runs (the user suggested vectors), so they scale smoothly with the window.
  - The waveform is drawn once into an image, and the play cursor repaints only while playing.
  - Rendering runs on a worker thread; the newest request wins, the Sound window shows "(rendering...)", and Play waits for the result.
  - Idle CPU is about 1% of one core. Real clicks register within 250 ms. Drag-move and Shift-drag connect work, checked with input-level mouse moves (SetCursorPos-only drags never reach JUCE).

## Editor presets and modifiers (2026-10-28)

The module windows' palettes, recovered from the window code and ported in `src/engine/Presets.cpp`. Each palette sits two icons to a row. A preset icon (1010–1012, 1110–1112, 3100/3101, 3400/3401) holds four tools, one per quarter; the tool codes run across the top halves of a whole row of preset icons, then the bottom halves (two icons to a row, three in the Waveform/Harmonic window; verified from the palette rectangles, see "Palette click mapping" below). Checked by plotting every result against its icon.

- **Waveshaper (CODE 41).** Dispatch at F41_01CF4 on the tool code (+1C), 5..22. The table is at +132.
  - Presets 5–12: straight line (F41_00004), zigzag (0003C), fold (00202), V (00294), parabola (00320), cubic x³/16 − 768x (00386), random walk (00426), flat (005D8).
  - The random walk burns TickCount mod 8 Randoms, walks up by HiWord(slope) + 256 a step, turns at the edges, re-aims 1 time in 32, then applies one random modifier (Random mod 8 → 68A, 6C8, 7E0, 896, 708, 770, 96E, 9E6).
  - Modifiers 13–22: mirror right half onto left (0068A), left onto right (006C8), flip left half (00608), flip right half (00648), square law (007E0), 2v − square law (00896), bow down (00708), bow up (00770), add Random/32 (0096E), smooth (009E6: in place, 3/5/7-point, ends held). 7E0–770 smooth afterwards.
  - The bows square v + 0x8000 with the 32-bit LMUL, which wraps above 46341; dividing by 65536 and keeping 16 bits makes the wrap vanish, so the port reproduces it as is.
  - The window's eraser re-draws the straight line (i − 128) << 8 where it is dragged.
- **Waveforms (CODE 38, used by the Oscillator window CODE 39 through the jump table).** Dispatch at F38_03F70 (presets, codes 7–18) and F38_0406E (modifiers in waveform mode, codes 19–36; the flag at +33A picks it; harmonic mode has its own 15-entry table at F38_041A6, see below).
  - Drawing tools in waveform mode: the pencil (icon 1006, tool 2) and the eraser (1001, tool 3), beside the mode icon. The tracker (F38_03058, F38_031CC) stores 0x7FFF − (y << 8) for the pencil and 0 for the eraser at the point under the mouse, then fills the points between it and the previous one by linear interpolation, so the eraser clears what it passes over. The port has both.
  - A preset is a harmonic recipe: 65 records of (level, phase) at +22A, phase 0..255 of a cycle (0x80 = inverted). F38_00CEC clears them; F38_0098C sums them into a 32-bit buffer with the TSWT sine table (F38_04A54: buf += q15(sine[(phase + n·k) & 255] · level)), finds the peak (04ABC) and, if it is over 0x7FFF, scales by LoWord(FixDiv(0x7FF8, peak)); otherwise by 0xFFFF (04AF0: hi·scale + ext((lo·scale) >> 16), low word kept). A sum under 100% therefore stays unnormalized, as the manual says.
  - Recipes: sine (00D28); triangle, odd k at 0x6872/k² with alternating phase (00D5E); saw, 0x7FFF/k (00DEA); square, odd k (00E3E); k = 1 or even at 0x70A2/k (00E9A); k ≡ 1 mod 4 at 0x5C8C/k, alternating (00F06); k ≡ 1 mod 3 (00F94); formant, the fundamental plus k 20–25 at 0x7FFF divided by 3, 2, 1, 1, 2, 3 (00FF8); Fibonacci harmonics (01086) and primes plus 1 (010F4), both Pascal sets stored as bitmaps just before each routine; octaves (01162). Code 18 is the disk: load a waveform file.
  - Modifiers: ×10/9 on the positive half (011C4), negative half (01232), both (012A0); fade in ×(i+245)/500 (0130A); fade out ×(500−i)/500 (0135E); ×9/10 (013B2); +2000 (013FE); −2000 (0144C); normalize to 0x7FFF (0149A; the original divides by zero on silence, the port skips); resample with a falling step from the left (0155E), from the right (015E0), both (01678), interpolating with F38_00C5A; rotate 4 samples left (01692) and right (016EE); invert (0174C); add Random/32 (01796); smooth using the guard point (01806); smooth wrapping round the cycle (01968).
- **Envelopes (CODE 40).** Dispatch at F40_02056, codes 5–18. Times are in ms against the module's length: dur = LMUL(length, 1000) / rate.
  - Presets: ADSR (0,0)(100,30)(50,100)(50,dur−100)(0,dur) with each point only if it fits (F40_000C0); decay (00270); rise and fall (00334); rise (0044A); 45/55 alternating every 201 ms (00510); 50/100 every 61 ms (005B0); flat 50 (00650); flat 100 (006AA).
  - Modifiers: levels ×9/10 (00704); (level+1)×10/9 up to 100 (0075E); times from the second point ×9/10, kept increasing (007CC; 9·time is a 16-bit `muls.w`, so it wraps past 3640 ms, as in the original); times ×10/9 + 1, then points past the end are dropped (00852, 0005A); levels toward 50 (008C4); away from 50, applied twice (00928).
  - F40_00C2C keeps a handle rectangle with each point: SetRect(time−1, 99−level, time+2, 102−level), stored in the point's extra words. The port fills it whenever it creates or edits points.

- **Checked in the emulator (2026-10-30).** Each tool was applied in the original TurboSynth and the document saved after every step. The port then replayed the step on the previous saved state and compared the stored tables.
  - Waveshaper: all 8 presets and 10 modifiers identical.
  - Waveform: all 11 recipes and 18 modifiers identical.
  - Harmonic mode: all 15 modifiers identical.
  - Envelope: all 8 presets and 6 modifiers identical, including the handle rectangles, and including Expand time applied four times in a row so points fall off the end.
  - The random tools match when `randSeed` starts at 1 (its value after InitGraf) and carries over from tool to tool: random walk (TickCount mod 8 was 0), Randomize, Add noise and harmonic Randomize.
  - One fix: the waveform modifiers no longer set the guard point `[256]`. The original leaves it alone; the engine sets it before rendering, so only the saved bytes changed.
## Stereo output (2026-10-28)

- **In the documents:** a stereo document has Output L (type 100, kind 7001) and Output R (type 101, kind 7002), R exactly 32 px right of L, so the two half icons make one jack. A mono document has Output (type 100, kind 7000). Checked on Early Reflections and Rotating Speaker.
- **In the port:** Sound > Stereo Output switches between the two (`setStereo` in `Patch.cpp`); going back to mono drops Output R and its cords. The halves move and highlight together, and neither can be erased, matching the original's "the output jack cannot be erased". The palette no longer offers loose output modules.
- **Stereo Preferences** are ported; see "Stereo Preferences" below.

## Harmonic mode (2026-10-28)

- **Analysis (F38_007AC):** run when the Harmonic window needs harmonics and the flag at +228 says they are stale. For k = 0..64 it correlates the 256 samples with the TSWT sine twice, from phase 0 and from phase 64 (a quarter cycle), with F38_049E6: sum of (sample · sine) >> 6, saturating to 0x7FFF0000 / 0x80000000, result the high word (−1 becomes 0).
  - Level = HiWord(FracSqrt(s² + c²) << 1), with the FracSqrt result clamped to 0x3FFFFFFF.
  - Phase is a linear stand-in for the arctangent, one quadrant at a time: (c·64)/(s+c); 64 + (−s·64)/(c−s); 128 + (c·64)/(c+s); 192 + (s·64)/(s−c).
  - Resynthesis is F38_0098C (see the waveform presets). A preset wave analyzed and resynthesized comes back within a few steps; a normalized wave's levels come back scaled with it.
- **Harmonic presets:** the same twelve recipes as the waveform presets (the icons 1110–1112 draw them as drawbars). In Harmonic mode they replace the harmonics, then resynthesize.
- **Harmonic modifiers (F38_041A6, codes 19–33), on levels only:** −0x200, floor 0 (01A82); +0x200 for levels above 8, ceiling 0x7FFF (01AD6); normalize to 0x7FFF (01B3E); ×(k+64)/128 (01C18); ×(128−k)/128 below 33 and ×(k+64)/128 above (01C72); ×(128−k)/128 (01D10); ×(129−k)/64 (01D6A); ×(k+64)/64 below 33, ×(129−k)/64 above (01DD2); ×(k+64)/64 (01E6C), each capped at 0x7FFF; shift down, h1 += h2 (01ED4); shift up (01F40); add Random/256, below 64 becomes 0 (01F7C); spacing halved (01FEA); spacing doubled, odd places from k/2, even ones cleared (0206C); spacing ×3/2 by averaging (020CE).
- **Editing (F38_03A34 tracking, F38_03B32 apply):** the Harmonic window is 256 pixels tall and each harmonic 4 pixels wide (x 1..256 → harmonic x/4 + 1). A point at y (clamped to at most 255) is level (256 − y)·128, so levels step by 128 and the lowest a drag can set is 128. The bars are drawn absolute, top at 256 − level/128 (F38_02B30); there is no relative display. The rake orders its two points by x and sets each harmonic k between them to l1 + (k − h1)·(l2 − l1)/(h2 − h1) (long multiply and divide) if the harmonic is above 1, or with Option only if it is above the line (`+2AC`), or with Option+Shift always (`+2AD`). With both points on one harmonic it takes the second point's level (the comparison there uses a stale register in the original; the port compares with that level). The port does all of this, except that the original's y = 0 (level 32768, which wraps negative) is clamped to y = 1.

## Group 2 in the port (2026-10-28)

- **Oscillator time line:** the waveforms sit at their times on a 50 ms scale and are dragged in time; on release they are re-sorted by time. Save writes the 554-byte waveform file.
- **Edit menu:** Undo (40 steps, one per edit), Cut, Copy, Paste, Clear, Duplicate and Convert to Sample (the module's computed sound becomes a Sample module's stored sound, its input cords go, its output cords stay), with the original shortcuts.
- **Options menu:** Auto Preview (⌘T), the Parameter Manager (⌘Y) and Stereo Preferences, saved in the settings. The defaults (rate, length, frequency) apply to new documents and newly placed modules. The rate and length rules are traced; see "Parameter Manager rules" below.
- **Keyboard:** the window passes keys no control takes to the shortcuts, and Ctrl+letter arriving as a control character is mapped back.

## Loops and the Sample window (2026-10-28)

- **Loop fields:** TBLOCK modules with a sound window keep the loop start at `+12E` and the loop end at `+132` (−1 = none; set by F36_00004). The Output adds crossfade looping: `+146` on (byte), `+148` length, `+14C` type (byte: 1 Linear, 0 Equal Power, from the dialog's radio items 5/6 in F37_0008C). These are the Output's five stored values; the Sample module's `+12E`/`+132` are its loop.
- **Output crossfade is a mode (F23_01DF2):** when the Output is computed with `+146` set, and `length − loopEnd ≥ xfLength` and `xfLength ≤ loopStart`, it copies its input's sound into its own buffer and crossfades it; otherwise it stays a view of the input. The port now does this in the engine: FM Synthesis Example has it on for both outputs, and its render changed accordingly.
- **Crossfade (F6_00C36, kernel F29_000E4):** q = length / 4; four quarters centred on the loop points, posA from loopStart − 2q, posB from loopEnd − 2q. Per sample a' = (a·gA + b·gB)·2 >> 16 and b' = (a·gB + b·gA)·2 >> 16 with gA, gB the high words of 16.16 accumulators starting at 0x7FFF0000 and 0; each sample gA −= slope, gB += slope. Slopes per quarter, divided by q: Linear 0x20000000, 0x20000000, −0x20000000, −0x20000000; Equal Power 0x30000000, 0x10000000, −0x10000000, −0x30000000. So both regions meet at an equal blend exactly at the loop points. The dialog limits the length to min(loopEnd − loopStart, length − loopEnd, loopStart) and at least 8.
- **Sample Rate Conversion (F28_01B6E dialog 0x2718, F28_01870, kernel F28_0594A):** ratio = FixDiv(old, new); new length = FixDiv(length, ratio). The kernel steps a 16.16 position by the ratio and interpolates a + (b−a)·frac >> 16 (toward a when b < a). Downsampling works in place; upsampling first moves the sound to the end of the larger buffer. The loop points (and `+140`/`+144`) become FixDiv(point, ratio). Rates 100–100000.
- **Gain Scaling (F28_01560, dialog 0x2717):** Normalize runs the module's normalize (F18_00EAA) on this module only, not a stereo partner; otherwise gain = FixRatio(percent, 100), 1–2000%, applied by F28_059C6: x·integer part + (x·(fraction >> 1))·2 >> 16, clipped.
- **Envelope Removal (after F28_01B6E, dialog 0x271C, default 50 ms):** blocks = length·(1000 / response) / rate (the 1000/response truncates first), block length = rate·response / 1000. For each block F6_00D7C finds the peak and the index after it; the gain there is FixRatio(30000, peak) (or the previous gain for silence), and F28_05A64 ramps the gain linearly from the previous peak to this one, clipping. The last gain holds to the end.
- **Edit commands (CODE 16, MacApp command objects with Do/Undo):** Reverse F16_01B10 (F28_05A28 both ways); Silence F16_01D1E (F29_0006C clears the selection); Cut F16_00B2C removes the selection, leaves the insertion point at its start, and if the loop end is now at or past the end clears both loop markers; Paste F16_00FE0 / F16_013D8 inserts the clipboard (replacing a selection) and selects what was pasted; Clear F16_018B6 removes the selection and leaves the markers alone. The selection is `+140`/`+144`. Loop markers do not move with the edits. The port follows these.
- **Extend Loop (F28_01FF0, rect `+78`):** the info box's Length sets the buffer size first; the command then fills from the loop end to the end of the buffer with copies of the loop (BlockMove from the loop start each time), overwriting what was there. The port resizes to the module's Length and does the same.
- **Loop points to selection (rect `+98`):** selection = loop start .. loop end, for a stereo partner too.
- **Save Loop As Waveform (F25_002B6):** step = FixRatio(loop length as a word, 256); the oscillator's resampler F39_02870 makes 256 points (a + ((b−a)·(frac >> 1))·2 >> 16 with 16-bit differences; the last point reads the sample at the loop end); then the module normalize (F23_008B8 + F29_000A2). The file it writes is 0x2A bytes of whatever the buffer held (not a name and time; in the emulator they matched nothing in the sound) followed by the 512 bytes of samples. The port resamples and normalizes the same way but writes a proper name. When it loads a waveform file it takes the name from the file name, since such headers are junk (19 of the 20 original files in TS Files' Waveforms folder).
- **Palette click mapping (verified, CODE 38/40/41):** each tool has a rectangle at `+38 + 8·code` in the window object, set with SetRect. Waveshaper and envelope palettes are 64 px wide: codes 1–4 are 32 px icons, the preset codes are 16 px quarters across the top halves of the two preset icons, then the bottom halves, then 32 px modifiers two to a row. The Waveform/Harmonic window palette is 96 px wide (three icons): row 1 waveform mode (1008), pencil (1006) or arrow (1000), eraser (1001) or rake (1007); row 2 harmonic mode (1009), info, speaker; row 3 the three preset icons, codes 7–12 across their top halves and 13–18 across the bottom halves; then the modifiers three to a row. The port's earlier two-wide waveform palette had some waveform presets in the wrong quarters; it now uses the original layout. The waveform window's eraser is not ported (its action was not traced).
- **Loop window layout (checked in the emulator 2026-10-30):** titled "Loop Window" ("Loop Window L"/"R" plus the name for a linked half). A two-column palette on the left holds Info, the speaker and the four zoom icons, and the two halves fill the rest, split by the dotted line, each with its step and Auto arrows at the bottom. The port matches; its Info icon lists the loop points and frequency. Still the port's own: the zoom steps, playing the loop for about four seconds, and refusing to open without both loop markers (the original opens with empty halves). Direct sampling (Sound Accelerator + AD In) is not offered.
- **Loop window Auto arrows (CODE 35 F35_00EE4, search F6_00DDA):** each arrow scans a whole range one sample at a time: loop end ← from the end to loopStart + 1 (1 without a start), loop end → to length − 1, loop start ← to 0, loop start → to loopEnd − 1 (length − 2 without an end). At each position p, with d the direction, score = |s[p] − s[r−d]| + |s[p+d] − s[r]| + |s[p+2d] − s[r+d]| where r is the other loop point; the lowest score wins, a tie going to the later position, and the new point is p + d (the middle of the three). So it always moves, and it goes to the best match in the range however far away. The port follows this exactly (it replaced an earlier search of the port's own that stopped at the first close match nearby).

- **Checked in the emulator (2026-10-30).** Each step was done in the original on Simple Reverb Model's Finger Snap and the document saved; the port replayed it on the previous saved state.
  - Identical:
    - Sample Rate Conversion down (44100→32000) and up (32000→48000)
    - Gain 37 % and 250 % (clipping)
    - Normalize
    - Envelope Removal at 20 ms
    - Reverse of everything and of a selection
    - Silence of a selection
    - Extend Loop
    - five Auto arrow presses in the Loop window, in all four directions
    - Save Loop As Waveform's 256 points
  - Upsampling: the last sample differs. The original interpolates with whatever follows the buffer (587 here, where the sound ends in zeros); the port uses 0.
  - Save Loop As Waveform's 0x2A-byte header held 21 values found nowhere in the sound. It is whatever the buffer held, not necessarily the sound's first samples.
  - Where the tools are in the original:
    - Sample window palette: Sample Rate Conversion and Extend Loop are the fourth row, Gain Change and Envelope Removal the fifth.
    - The Loop window is the magnifier icon at the bottom right.
    - Edit menu shortcuts: Reverse ⌘R, Silence ⌘E.
    - Save Loop As Waveform is in the File menu.

## Parameter Manager rules (2026-10-28)

- **Settings (DITL 10009, CODE 6 F6_0132C; loaded in CODE 3):** `-$4F2` default rate, `-$4F6` default length, `-$4FA` default frequency; check boxes Inherit sample rate (item 8, `-$4FB`), Convert sample modules to default (9, `-$4FC`), Inherit length (14, `-$4FD`), Dynamic length (15, `-$4FE`) and Monitor loop at output (20, `-$4FF`).
- **Messages (the change selector F18_00E70; TBLOCK handler F23_014F4):** 4 sets the rate and, with Inherit rate, sends 9 to the modules fed; 9 sets the rate and passes it on unconditionally; 5 sets the length (capped by memory) and always sends 10 with the new length to the modules fed; 10 ("input length") sets the length only with Inherit length; 11/12 (input added/removed).
- **Classes overriding message 10:**
  - TDELAY F23_02794: for a Delay (type 13) with Dynamic length, L + (feedback / 5) · delay, delay = (100·coarse + fine)·rate / 10000 samples; otherwise L (so the Resonator always takes its input's length).
  - TCOMPAND F23_02828: expanding Stretcher with Dynamic length L + FixMul(L, FixRatio(25, 101 − amount)); compressing Stretcher with Dynamic length and amount > 80 L·(110 − amount)/40; otherwise L.
  - TPSHIFT F23_028C8: with Dynamic length max(L, L·(−semitones)/6); otherwise L.
  - TDIFFUSE F23_02936: the same formula applied to the Size coarse parameter, so in practice L.
  - TOUTPUT F23_018F4: takes L.
  - TMIX F23_02612: with Inherit length, the longest of its inputs (F23_025B8), also re-measured when inputs are added or removed.
  - TENV F23_02346: with Inherit length, takes L and drops breakpoints past the new end (F40_0005A).
  - These classes then set their length with message 5, which passes it on. So a Delay, Resonator, Stretcher, Pitch Shifter, Diffuser or Output follows a change of its input's length whether or not Inherit length is on.
- **Connections (F16_047C2):** message 11 to the new destination, then 10 with the source's length if Inherit length is on, then 9 with the source's rate if Inherit rate is on.
- **Parameter edits:** the Delay window (CODE 44, only for type 13), the Stretcher (45), Pitch Shifter (46) and Diffuser (48) windows send 10 with their input's length after an edit when Dynamic length is on and the module has exactly one input.
- **Monitor loop at output:** when the output is previewed, the loop points go to the playback routine and the loop repeats.
- **The port** (`src/engine/Rules.cpp`) applies all of this after edits in Module Info, new cords and loaded sounds; it replaced the port's earlier "compute and trim the tail" rule. Monitor loop at output repeats the output's loop for about 20 seconds after Play.

- **Checked in the emulator (2026-10-30)** on Simple Reverb Model (Sample → Diffuser → Delay → Mixer → Output). The Sample module's Length or Rate was changed in its Info box under different Parameter Manager settings, and every module's length and rate compared with the port's rules. All identical:
  - all on: Length 8000, Rate 22050
  - all off: Length 12000, Rate 32000
  - Dynamic length only: Length 9000 (the Delay's dynamic length 27516)
  - Inherit length only: Length 7000 (the Mixer takes its longest input)
  - Inherit rate only: Rate 48000
  - Not checked: the rules on making a cord.

## Stereo Preferences (2026-10-28)

- **Dialog (DITL 10040, CODE 6 F6_0178E):** new document Mono/Stereo (items 4/5, a field of the application object); open both channels / a specified channel of a stereo file (7/8, `-$518`); matching channel lengths pad/truncate (12/13, `-$513`); sample rate highest / lowest / left's / right's (15–18, `-$516` holds the item number); stereo to mono scale / don't (20/21, `-$517`); direct sampling mono/stereo (23/24, `-$519`); empty Sample module mono/stereo (26/27, `-$51A`).
- **Stereo sound (F23_02DDC, F23_02F6E):** the rate is chosen from the two outputs' sources per `-$516` (or the one output that has a source), and the length is the longer channel padded (pad) or the shorter (truncate, which also clears loop points past it).
- **The port:** new documents as stereo, pad/truncate and the rate choice (`RenderOptions`), open both channels (linked pairs, below) or a specified channel, chosen when the file opens, the stereo-to-mono scale and empty stereo Sample modules (below). Not ported: direct sampling.
- **Stereo to mono (`-$517`, DFLB 7, default Scale).** Save Output As writes a stereo document's sound to a mono file through F27_01BCC. It runs the Mixer's kernel (F29_02122) over the two outputs with gains 0x3FFF each (Scale) or 0x7FFF each (Don't scale, which can wrap): q15(g·L + g·R), over the padded or truncated length. Playback folds the same way (F23_02F6E) only on hardware without stereo, which the port never uses. In the port, exporting a stereo sound offers a mono file (`foldToMono`).
- **Empty Sample module (`-$51A`, DFLB 11, default Mono).** F28_00628 asks for a sound when an empty Sample module is opened. If that is cancelled on a module with no sound and no partner, and the preference is Stereo, the module becomes an empty linked pair (F28_00D8A) and the right half's window opens. The port does the same when Load Sound is cancelled.

## Linked stereo Sample modules (2026-10-28)

- **Fields:** `+14E` partner handle, `+152` partner's id, `+154` own id, `+156` channel (1 left, 2 right, 0 mono). Documents store `+154`, `+152`, `+156` (the port's `sampC`).
- **The id is not a list position.** F28_01278 gives a Sample module whose `+154` is 0 the id of the nearest earlier Sample module that has one, plus 1 (or 1 if there is none). The partner is the Sample module whose id equals `+152`, so deleting modules needs no renumbering.
- **Icons (F28_011D8):** channel 1 → kind 2004, channel 2 → 2003, otherwise 2001. 2004 lacks its right border and 2003 its left one, so the pair reads as one L|R icon, like the stereo output jack.
- **Reading a document (F9_010A2, F28_01040):** each left half (`+156` = 1, `+152` ≠ 0) finds the module with that id. The partner takes the left's id in `+152` and channel 2. A Sample module left without a partner becomes mono (kind 2001).
- **Opening a stereo file with "Open both channels" (`-$518`; F28_00D8A, F28_00EE0):**
  - The module becomes the left half.
  - A new Sample module is created as the right half, 32 px to the right. If that would pass the window's right edge, the left half moves 32 px left first.
  - The right half gets the left's name and loop points.
  - The two exchange ids, and both are selected.
  - F28_00B90 loads channel 1 into the left and channel 2 into the right, converting the rate for both when "Convert sample modules" is on.
- **Opening a mono file, or one channel, into a linked half** unlinks the pair first (F28_006E2 → F28_00532).
- **Convert Stereo Sample to Dual Mono (Edit menu, F28_00532 → F28_0130A on each half):** clears `+152` and `+156`, sets kind 2001 and drops the partner pointer. The modules keep their places and ids.
- **What applies to both halves:**
  - Clicking one selects both, and dragging keeps the left half 32 px left of the right (F16_024C4).
  - Cut, Copy, Paste, Clear, Reverse and Silence (F16_00B2C, F16_00FE0/F16_013D8, F16_018B6, F16_01B10, F16_01D1E) repeat the edit on the partner, over the partner's own selection, which follows this one.
  - Copy takes both channels (F16_008A0).
  - Rate conversion (F28_01B6E; F28_01870 converts both), Gain Scaling's percentage (F28_01560, two F28_059C6 calls), Envelope Removal (F28_05A64 per half), Extend Loop (F28_01FF0) and Crossfade Looping (F28_02124 area: F6_00C36 for each half, with this half's loop points) run on both halves. Gain Scaling's Normalize does not: it normalizes this half only.
  - Loop points are copied to the partner.
  - F28_01380 titles the Loop windows "Loop Window L" / "Loop Window R" plus the name, for both halves.
  - Hearing a linked half plays both channels as stereo (F29_00A66 → F29_00234 with the channel).
- **Checked in the emulator (2026-10-30):** a stereo AIFF opened with "Open both channels" into a new Sample module, next to an existing mono one. The port's `makeStereoSample` gives the same ids (2 and 3 after the mono module's 1), partner ids, channels, icon kinds (2004/2003) and positions (the right half 32 px below). Gain Change at 50 % from the L half's window changed both halves exactly as the port does.
- **Deleting one half** unlinks the other (F16_03A66). In the port, Clear on a selected pair deletes both halves; the eraser deletes only the one it touches.
- **Paste (CODE 16 ~F16_025xx):** pasted Sample modules get new ids (F16_02458 / F16_023EC, which also points a partner's `+152` at the new id). Two pasted halves that find each other by id, with equal lengths, are linked again (F16_02314 → F28_01040); otherwise they go mono. Their positions are then lined up 32 px apart (F16_024C4). In the port, Copy and Duplicate of a selected pair carry both halves, and Paste makes a new linked pair; one half pasted alone is mono.

## Menus and commands (traced 2026-10-31)

- **Command numbers (`mntb` 0):** File: 10 New, 20 Open, 31 Close, 30 Save, 32 Save As, 33 Save a Copy In, 2000 Save Output (⌘U), 2006 Save Output As, 2001 Save Loop As Waveform, 2002 Save Waveform, 2005 Get Info (⌘I), 176–178 Page Setup / Print One / Print, 36 Quit. Edit: 101 Undo, 103–106 Cut / Copy / Paste / Clear, 3010 Select All, 3000 Duplicate, 3002 Reverse, 3003 Silence, 3001 Convert to Sample (⌘H), 3005 Convert to Envelope (⌘J), 3006 Convert to Oscillator (⌘K), 3004 Send Loop to Output (⌘L), 3007 Convert Stereo Sample to Dual Mono, 35 Show Clipboard. Options: 6003 Auto Preview, 6001 Auto Update SampleCell, 6004 Parameter Manager, 6008 Stereo Preferences, 6009 Playback Options, 6005 Hardware Setup, 6006 / 6007 Stereo / Mono Document. The numbers 3000–3012 are also the processor icon kinds, so searches for them find both.
- **Patch window dispatch (F15_00DCE):** Get Info → F8_0161A; Duplicate → F16_0215C; Convert to Sample → ALRT 233 ("You won't be able to Undo this"), then F16_03E8A; Convert to Envelope / Oscillator → dialogs 10014 / 10015, then F16_03E8A; Dual Mono; Select All. Module windows send Get Info to F18_00F0E (the module's Info box).
- **Enabling (F23_035D4):** Get Info always; Cut, Copy, Clear and Duplicate with a selected module that is not an Output; Convert to Sample with a selected module that is neither an Output nor a Sample and has been computed (`+114`); Convert to Envelope and Oscillator with a selected Sample module that has no stereo partner (`+152` = 0); Dual Mono with a selected linked half; Select All while some module is unselected. In the Sample window (F28_0476E), Send Loop to Output needs both loop markers.
- **Convert to Envelope (dialog 10014, F6_011B0):** Response Time, default 50 ms, accepted 20–1000 (F24_00152; out of range the dialog stays). The new module (F36_003B2) is an Amplitude Envelope (type 7, icon 3000, `+136` = 1) with the Sample's rate, length, name and position. Blocks = length · (1000 / ms) / rate, the division truncating first; block length rate · ms / 1000. Each block's peak (F6_00D7C: |x|, −32768 as 32767, ties to the later sample) gives level = peak · 100 / 32767 at time = (index after the peak) · 1000 / rate ms, 0 for the first block. A point is kept when its time is after the last kept point's and its level differs from both of the last two kept levels.
- **Convert to Oscillator (dialog 10015, F6_00F62):** "Extract a waveform every" ms, default 50, 20–1000; the fundamental, default FixDiv(rate, loop length) with a loop or the Parameter Manager's default frequency without, accepted 20 Hz to rate / 2; "Track frequency changes", off. The new module (F36_001C8) is an Oscillator at that frequency with the Sample's rate, length, name and position. P = rate / FixRound(freq) samples; window min(P, 200); search span (P·3 as a word) / 2. Every interval (time = i · interval ms, while time · rate / 1000 ≤ length − rate / 30, at least once): the cycle start is the position in [t, t + span] whose window + 1 samples best match those at the previous cycle's end (F6_01154: least summed |difference|, ties to the later position; the first match is against the sound's start); the end is start + P, or with tracking the best match for the start's window in [start + 2P/3, … + span]; then end − 2 is refined over 5 positions on the single first sample. The cycle is resampled to 256 points by F39_02870 (step FixRatio(cycle bytes, 512)) without normalizing, named "Waveform", with `+32E` its time and the rect (time, 0, time + 32, 32). The original reads past the buffer near the end; the port reads zeros there.
- **Send Loop to Output (Sample window, CODE 28):** messages 14 (loop start) and 15 (loop end), then 13. TBLOCK passes 14 and 15 on to the modules it feeds (F23_014F4); TOUTPUT (F23_018F4) stores them as its loop start `+12E` and end `+132` and redraws.
- **In the port (2026-10-31):** Edit has Convert to Sample (now with the confirmation), Convert to Envelope, Convert to Oscillator and Send Loop to Output with the original's shortcuts and enabling; File has Get Info (⌘I), which opens the module's window. `sampleedit::extractEnvelope` and `extractWaveforms` follow F6_011B0 and F6_00F62. The port keeps its own Undo for the conversions, where the original could not undo them.
- **Not ported:** Save a Copy In, Save Waveform from the menu (the Oscillator window has it), Show Clipboard, printing, Auto Update SampleCell, Playback Options and Hardware Setup.

## Cord rules (traced 2026-10-31, not yet compared with the port)

- **F16_045B6** checks a new cord in this order: no destination, a cord that already exists (F16_04546) or source = destination is refused silently; a cord from an Output (type 100/101) shows "The output can only terminate the signal path."; a destination whose input count equals its limit shows "No more inputs available."; a destination that already reaches the source (F16_0456C) shows "Feedback paths are not allowed." (all through F6_00BF4).
- **Input limits (selector F18_00E52):** Oscillator, Sample and Noise 0; Modulator 2; Mixer 32 (0x20); every other block 1 (TBLOCK).
- **The port (2026-10-31):** had no cord checks at all (any module could be dragged onto any other, including Samples and Oscillators, cycles and extra inputs). `PatchView::cordRefusal` now applies the four rules above and shows the original's messages. Drag-connecting is the only place cords are made; loaded documents are not checked.
- **Future Model 2 option (the user's request, 2026-10-31):** the "Feedback paths are not allowed." refusal is a candidate for a selectable Model 2 setting that permits feedback loops. The scheme is undecided; the user wants to think about it. Model 1 keeps the original's refusal.

## Dialog inventory (2026-10-31, items from the DITL resources; limits not yet traced)

- **Module Info boxes:** Oscillator 10000 (Name, Length in samples, Frequency in Hz, Sample Rate); envelope family 10001 (Name, Time in seconds); Mixer 10002 (Name, Length, Sample Rate, Normalize); Output 10003 (Name, Length, Sample Rate, Loop Frequency); Sample 10005 (Name, Length, Sample Rate, Loop Frequency, "Save sound data with document"). The port's Info fields cover the same values, but the envelope family shows Length and Sample Rate instead of Time in seconds, and Output and Sample show the loop frequency as text rather than an editable field. Field limits are not traced.
- **Others:** 10006 loop start and end; 10007 gain change; 10008 sample rate conversion; 10009 Parameter Manager; 10012 envelope removal; 10013 crossfade looping (type, "Maximum Length", "Crossfade Length"); 10016 dual-mono confirmation; 10025 "Waiting for signal to reach threshold ..." (direct sampling); 10030 stereo file channel choice; 10040 Stereo Preferences; 10050 Playback Options (Digidesign DSP Card, SampleCell, Sound Manager).

## Info box limits (traced in part, 2026-10-31)

- **Oscillator Info (F39_004EC, dialog 10000):** each field is checked through F24_00152 (a non-number gives "Only numbers ... in this field"). Length: minimum 32 (moveq #$20), maximum min(2147483, a bound from the sample rate and 32000 ms). Sample Rate: 100 to 100000, its lower bound also tied to the length. The exact bounds use Toolbox trap calls (A84D, A868) that were not resolved, so the port keeps its own upper bounds. Changing the length sends message 5 and, if it changed, message 2; changing the rate sends 4.
- **In the port:** the Info fields now accept rates 100 to 100000 (they were 1000 to 96000) and lengths from 32 (they were from 1). The other module windows (envelope Time field, Mixer, Output, Sample) were not traced.

## Waveform time dialog (DITL 10001, F38_00492, traced 2026-10-31)

- DITL 10001 ("Piano 100ms", Time: x.xxx Seconds) belongs to the Oscillator's waveform, not to the envelope family. F38_00492 opens it with the waveform's time `+32E` (ms) shown as seconds to three places, and on OK checks the fractional part of the seconds (ms mod 1000) against two thresholds, 100 and 10, showing text 0x7A6 when a check fails. The exact accepted ranges and the alert text need one more step to confirm.
- The envelope family's Info box was not found: the dialog constants 10000–10016 that the port's ModulePanel copies were checked, and no envelope Info dialog was located among CODE 36–40.

## Envelope Info box, located (2026-10-31, partly traced)

- The envelope family's Info dialog is **DITL 10001's opener F16_041D6**, which calls the window helper F15_0060E with 10001 (the ID the Oscillator's waveform time shares; see above). Its caller is in CODE 15 around 017E0, the Info-opening routine for module windows. F16_041D6 stores the module in the dialog record and copies the default `-$75C` block; the field checks are not in it.
- Still to trace: the field handler for this dialog (where the Time field is validated), and which module types reach 017E0.
- Also confirmed: F16_0287C opens 10000 (Oscillator), F16_04390 opens 10002 (Mixer), and 10003 and 10005 are opened from their own functions. The Oscillator's limits already applied come from F39_004EC.
- **Info dispatch (CODE 15, about 01A00–01F60):** one routine opens each module's Info box and chooses between F16_0287C (10000 Oscillator), F16_041D6 (10001 envelope family), F16_04390 (10002 Mixer), with 10003 and 10005 handled in the same routine by module type (type tests with 0x64/0x65/0x6 for Output and the Sample modules). The field checks for 10001 are inside this routine, after the dialog is shown; not yet separated out.
- **Limits for the envelope Time field (not found):** the range checks that go through F24_00152 in CODE 15 are only at 00FF4 and 1004E, which belong to other tools. The envelope field's own check is not in CODE 15, CODE 36 or CODE 40 by a constant search. Conclusion so far: its check is not in the Info routine itself, and DITL 10001 is shared with the waveform time (see above), so the envelope's limit is probably set by the waveform path's rules. Needs a runtime test in the emulator to settle.
- **Emulator attempt (2026-10-31), inconclusive:** TurboSynth runs in Basilisk II (Turbosynth disk). An envelope placed from the palette opens an editor window ("Amp Env-1") on double-click, with breakpoints and time ruler, not a Time field. Get Info (⌘I) and the Info tool did not open a box for the selected envelope. The envelope's Info dialog was not reached, so its Time limits are still unmeasured.
- **Envelope Info box, seen in the emulator (2026-10-31, user's screen):** the box for "Amp Env-1" has Name, Length (44100 samples), Sample Rate (44100 Hertz) and a Normalize check box, with Cancel and OK. It has no Time field, so the envelope family's Info matches the port's Name / Length / Sample Rate / Normalize fields. The port labels the check box "Normalize output"; the original says "Normalize". Limits for Length and Sample Rate are not yet measured.
- **Envelope Length, measured 2026-10-31:** entering 10 samples and pressing OK was accepted with no alert; the box closed and the envelope's curve collapsed to a sliver. So the original's minimum for an envelope's Length is 10 or lower, not 32 (the Oscillator's limit). Not yet measured: the lower limit below 10 and the upper limit.
- **Envelope Length upper limit (2026-10-31):** 1,000,000 samples accepted; 1,500,000, 2,000,000 and 99,999,999 refused with "Inappropriate values abound! Don't panic. Please make sure all values are reasonable." The limit is between 1,000,000 and 1,500,000 (not pinned down further). The lower limit is 10 or less.
- **Side effect:** the test changed Amp Env-1's length (to 10, then 1,000,000, then 1,500,000 refused, then back to 44100). Shortening to 10 samples dropped the envelope's breakpoints past the end, so Amp Env-1's curve is now gone and must be redrawn in the original.
- **Correction (2026-10-31, checked by hand):** the envelope Length accepts 0 samples, not just 10. The minimum is 0. The port's Length minimum is now 0 for envelope-family and other modules, 32 for the Oscillator. The upper limit is between 1,000,000 and 1,500,000 (the port uses 1,000,000 for now, so it does not refuse anything the original accepts in that gap; this needs the exact value).
- **Envelope Length upper limit: 1,411,200 samples** (the user's check; 32 seconds at 44,100 Hz). The port's maximum for envelope-family modules is now 1,411,200. The Oscillator's upper limit is still unmeasured; the port keeps 2,147,483 for it.
- **Rule, from the user's checks (2026-10-31): the length limit depends on the sample rate.** 1,411,200 samples is refused below 44,100 Hz and accepted at 44,100 Hz; 1,400,000 is accepted at 43,750 Hz. Both fit length ≤ 32 × rate, i.e. a 32-second cap: 32 × 44,100 = 1,411,200 and 32 × 43,750 = 1,400,000. The port now caps envelope-family lengths at 32 × the module's current sample rate (and 2,147,483). The Oscillator's cap is unmeasured. Changing the rate does not re-clamp an existing length; the field only checks when it is edited.
- **Results from the user's checks (2026-10-31):** (1) Lowering the sample rate below the length's cap is refused in the original: Length 1,411,200 at 44,100 Hz, then Sample Rate 40,000 gives a refusal. So the cap is enforced on the rate change, not only on the length. (2) An Oscillator Length of 2,000,000 at 44,100 Hz is refused (the user's single "refusal" covers the Oscillator test). The Oscillator's cap is still unmeasured, so the 32-second rule is not confirmed for it. (3) The port accepted the same sequence because it did not check rate changes; it now refuses a rate change that would leave the length over 32 × the new rate, with the original's alert text.

## Field limits, Mixer / Output / Sample Info (in progress, 2026-10-31)
- **Mixer Info opener:** F16_04390 (CODE 16) only creates dialog 10002 and stores the module; it does not check fields. It is called from CODE 15 at 01BD8 (the Info dispatch). The field checks must be in the dialog's item handler, which is not yet identified.
- **Oscillator field check:** F39_004EC (CODE 39) reads the Length and Sample Rate items, calls F24_00152 for numeric checks and the bounds routines. Length minimum 32, maximum from the sample rate and 32000 ms (see the Oscillator Info entry above). The same routine's pattern (items 0x12E, 0x132, 0x11A, 0x12A, 0x110 on the module object) is the likely template for the Mixer, Output and Sample boxes; to be confirmed.
- **Next:** find the handler for dialog 10002 (and 10003, 10005) by the item offsets, then compare its bounds with the Oscillator's. Until then the port keeps its own bounds for these boxes.

## Field limits, traced (2026-10-31): the bounds routine and each module type
- **The range check:** F24_00152(result, value, lo, hi) (CODE 24) sets result = lo ≤ value ≤ hi; otherwise it shows the alert "Inappropriate values abound!  Don't panic.  Please make sure all values are reasonable." (text at 0018C). The port's alert text is the same.
- **Helpers:** F1_002DE(a, b) = max(a, b); F1_00302(a, b) = min(a, b). Traps: A84D = FixDiv, A868 = FixMul, A869 = FixRatio.
- **Block modules (TBLOCK, F23_011C0; also the envelope family and the Mixer by the same type table):**
  - Length: lo 0, hi = min(2147483, FixMul(FixDiv(rate, 1000), 32000)) = min(2147483, 32 × rate). Checked at CODE 23 01324.
  - Sample Rate: lo = max(FixMul(FixDiv(length, 32000), 1000), 100) = max(length / 32, 100), hi 100000 (0x186A0). Checked at CODE 23 013EC.
  - The port matches this: envelope Length 0..min(1,411,200 for the envelope, 32 × rate), rate ≥ length / 32 via the accepts check, rate 100..100000. The Mixer uses the same box type; not yet checked in the emulator.
- **Sample (F28_02396, selector @00F0A TSAMP):** the same rules as the block modules. Length: lo 0, hi min(2147483, 32 × rate) (CODE 28 02558); Sample Rate: lo max(length / 32, 100), hi 100000 (0272A). When a field is not yet read back (value 0), the routine uses the module's stored value.
- **Oscillator (F39_004EC):** Length lo 32; hi as above; the rate rule as above (see the Oscillator entry).
- **Output (F37_00492, selector @00F0A TOUTPUT):** no range check anywhere in the routine (no F24_00152 call). Its Length and Sample Rate in the Info box are not range-checked, and the loop frequency is computed from the loop points. The port does not show Output Length/Rate fields at all; a difference to address when the Output box is traced in the emulator.
- **Oscillator's 1,411,200 at 40,000 Hz:** refused by the same rule (32 × 40,000 = 1,280,000).
- **Still to trace:** the crossfade looping dialog, the Output and Playback Options dialogs, the Output box's behavior when a field is edited (does the original write back an unchecked value?).
- **Crossfade looping dialog (10013, F28_02124, CODE 28):** Maximum Length (item 8) = min(loopEnd − loopStart, length − loopEnd, loopStart) for the sound's loop points (the same value is also set as the default of Crossfade Length, item 11). Crossfade Length (item 11) is checked on OK: below 8 is refused with "The Crossfade Length must be at least 8." (text at 02364, read from the disassembly); above the maximum, "The Crossfade Length is too large." (02340). Both alerts come from the same alert routine as the Info boxes. The port's dialog: limits as above; the alert wording is not verified against the emulator.
- **Playback Options (10050):** hardware choices only (Digidesign DSP Card, SampleCell, Sound Manager). They do not change the sound, so the port leaves them out. Not traced field by field.

## Playback Options (10050, F6_0178E, CODE 6 at 01A80), traced 2026-10-31
- Items 4, 5 and 6 are a radio group (the three playback routes). Item 4 (the DSP card route) is only allowed when the global flags at −$4EE and −$4E9 (DSP card present, and the second flag) are set; otherwise the choice falls back to item 6 (the Sound Manager route).
- Item 5 is refused when F3_0120C says the card path is unavailable, and falls back to 6 in the same way.
- The chosen route is stored as a word in the global −$526 (only for the routes 1 to 3 in the switch; the mapping of those codes to names is not yet confirmed).
- F24_000CA sets the radio group (it loops over the group, calling GetControlValue, and sets the one on the chosen item). It is a radio helper, not a number-field check. These are hardware choices with no effect on the sound, so the port does not carry them.
- **Still open:** confirm the −$526 code-to-route mapping and F3_0120C's test.

## Options menu states (F23_02A48, CODE 23), traced 2026-10-31
This routine sets the check marks and enabled state of the Options items from globals:
- **Auto Update SampleCell (6001):** checked from the global −$4EC.
- **Auto Preview (6003):** checked from the global −$4EB.
- **Hardware Setup (6005):** enabled only when the DSP card flag (−$4EE) is set and the global −$4EA is clear (the item's enable is −$4EA inverted, AND −$4EE).
- **Stereo Preferences (6008) and Playback Options (6009):** enabled when −$4EA is clear.
- **Show Clipboard (35):** enabled/disabled through the same routine (the flag is not yet traced).
- The routine ends by calling F23_00BD8 with two globals at −$6D0 and −$4E0 (the saved preferences).
- **Still open:** the meaning of −$4EA (probably "no document" or "stereo state"), the −$4EB and −$4EC flags' mapping to Auto Preview / SampleCell, and whether Hardware Setup's dialog has any field limits.

## Stereo Preferences (10040, F6_0178E, CODE 6 at 017C2), traced 2026-10-31
Item labels from DITL 10040 (extracted resource, scratchpad rsrc/sc222). The OK path stores each group into a global:
- **Open A New Document As A: Mono / Stereo** (items 4, 5): the new-document stereo choice. Port: newDocStereo.
- **Opening A Stereo Soundfile: Open both channels / Open specified channel** (items 7, 8): flag −$9 → global −$518. Port: openBoth.
- **Matching Channel Lengths: Pad shorter / Truncate longer** (items 12, 13): D6 → −$513. Port: padStereo.
- **Sample Rate: Use highest / lowest / left / right** (items 15–18): D5 (a word, 15–18) → −$516. Port: stereoRate.
- **Stereo To Mono: Scale / Don't scale** (items 20, 21): D4 → −$517. Port: monoScale.
- **Direct Sampling: Mono / Stereo** (items 23, 24): flag −$8 → −$519. Port: not ported (no direct sampling).
- **Empty Sample Module: Mono / Stereo** (items 26, 27): flag −$7 → −$51A. Port: emptyStereo.
The dialog's OK also writes the new-document choice into the document's record at +9.

## The Options flag −$4EA (partly traced)
- Set to 1 in F28_02D28 (CODE 28 at 02D58) when the module is a Sample (type 6) and the two arguments are (0 and non-zero). Cleared in F28_0280E (02826), F28_02DA0 (02DA0) and F3_0120C (CODE 3 at 01332). F3_0120C tests whether the SampleCell extension is present (its string is "SampleCell").
- So −$4EA is a Sample-related editing state, not a document-type flag. Its exact meaning (the Sample is being edited with a SampleCell or direct-sampling link) is still a guess. Confirm by reading F28_02D28's callers.
- Hardware Setup (6005) and Stereo / Playback (6008, 6009) are enabled only when −$4EA is clear.
- **Callers of the −$4EA setter (F28_02D28):** no direct JSR or PC-relative call to 02D28 exists in the disassembly. It is reached through the method dispatch (a selector/jump table for the Sample module's messages), so its callers need that table. Not finished; the flag's meaning stays a guess until it is.
- **Port (2026-10-31):** Stereo Preferences now has the Direct Sampling Mono / Stereo choice (stored as stDirectStereo, not used since direct sampling is not ported).
- **Method dispatch search for F28_02D28 (2026-10-31):** ts_methods.txt has 12 selector groups; the Sample entries are F28_01752, F28_02396, F28_004C6, F28_05910 (message 156) and F28_0008A. F28_02D28 is in none of them. No instruction in any CODE segment refers to its address (no JSR, BSR, JMP, or word 0x2D28 in CODE 0 or the jump table). So it is reached by a computed route (a message table in data, or a MacApp-style command object), not by a static call. Caller trace not possible statically; needs a runtime check in the emulator.
- **Runtime check suggested:** in the emulator, with the Options menu open, note the enabled state of Hardware Setup, Stereo Preferences and Playback Options with no module selected, then with a Sample module selected, then after opening a Sample's window. The change points show which action sets −$4EA.
- **Emulator check of the Options menu (2026-10-31, user's screens):** in all five states (no selection; Amp Env selected; a Sample selected; a Sample window open, showing "Clarinet"; after closing it) Hardware Setup was greyed out, and Stereo Preferences and Playback Options were enabled. So −$4EA stayed clear in every state. Selecting or opening a Sample is not enough to set it. Hardware Setup is greyed because the DSP card flag is clear (no card in this emulator), which matches F23_02A48.
- **Next trigger to test:** a Sample action that uses the Sound Manager or direct sampling path (for example Load Sound on an empty Sample, or Direct Sampling if the menu offers it).

## Manual check: Playback Options and Hardware Setup (2026-10-31)
- The SC 2.2 addendum (manual/source/manual.txt, Section 2, "Playback Options", page 12 scan p071) confirms the user's guess: **Hardware Setup** configures the Digidesign DSP card (Pro Tools, Sound Tools II, Audiomedia II): which card and slot, and whether the digital or analog input is used. It is enabled only with a DSP card, which matches the greyed item in the emulator.
- **Playback Options** chooses one of three routes: a Digidesign DSP card, SampleCell, or Sound Manager. The manual says to pick the DSP card route and then configure it in Hardware Setup.
- The manual's Stereo Preferences text (page 13–14, p073–p074) matches the port's dialog: new-document mono or stereo; open both channels or the specified channel; direct sampling mono or stereo; empty Sample module mono or stereo; pad or truncate matching lengths; stereo to mono scale; sample rate highest, lowest, left or right.
- The manual says the Diffuser needs a Digidesign card (SampleCell or a DSP card), which matches the existing port behaviour.
- The manual's Sound Driver / Sound Accelerator text (Section 1) describes the SE-era playback options, not SC 2.2.
- The corrected manual (tsynth/manual/Turbosynth Manual.pdf) has a text layer. Its Playback Options, Hardware Setup and Stereo Preferences text is the same as the source the earlier check used, so the notes above stand.

## Document Info Box (dialog 10004, F8_0161A, CODE 8), traced 2026-10-31
The manual (What's New, p.16 and the Info Box text) describes this box: Samples used, Samples available, Compact Memory and Release Clipboard buttons, and a memory indicator in the tool palette. The box is opened by File > Get Info (command 2005) from the patch window (F15_00DCE → F8_0161A). The port's Get Info opens the selected module instead, so the document box is missing.
- **Contents:** items 5 and 8 show memory. The routine calls FreeMem (trap A01C) and HGetVol (the free bytes on the volume) and converts them to samples (1 sample = 2 bytes), with the document's own use from object +0x88 (F2_008B4).
- **Buttons:** a switch on the hit item: one branch (value 2) disposes the clipboard handle (global −$6E2, via F5_0056A) and recomputes the memory; another (value 3) calls F5_0056A and F5_03660 on a copy of the document record, then recomputes. The manual says these are Release Clipboard and Compact Memory. The exact Compact Memory call (a Toolbox compaction routine) is not yet confirmed.
- **Memory indicator:** the manual says the tool palette's info icon fills from the bottom as memory is used, and shows "Lo!" when memory is very low. Not yet traced in the code.
- **Show Clipboard:** the manual places it in the Edit menu, so the enabling of item 35 in F23_02A48 is about the Edit menu, not Options. Resolves the earlier open item.
- **Parameter Manager:** the manual lists its options (Inherit sample rate from input, Convert sample modules to default, Inherit length from input, Dynamic length based on parameters, Monitor Loop at Output). The port has these.
- **Crossfade:** the manual's three-range limit (start to loop start, loop start to loop end, loop end to the end) matches the traced dialog.
- **Port:** the document Info Box, Compact Memory, Release Clipboard and the memory indicator are not implemented.

## Memory indicator and Compact Memory (traced 2026-10-31)
- **Trap names:** the disassembly labels A04C as "HGETVOL", but by the Toolbox numbering A04C is **CompactMem** (A04D is PurgeMem, A01C is FreeMem, A11E is NewPtr). So the Info Box's call with 0x800000 (8 MB) is a CompactMem request; the box then shows the memory left. Treat the "HGETVOL" label in CODE 1 (011B0) as a guess that needs fixing.
- **Compact Memory:** in F8_0161A, the branch for the second button calls F5_0056A (clipboard handle release, global −$6E2) and then CompactMem(0x800000), and redraws the counts. The other button calls F5_0056A and F5_03660 instead, without compacting. Which item number is which is not yet confirmed (the branch values 2 and 3 map to the two buttons).
- **Memory indicator (F23_004C6, CODE 23):** each pass calls FreeMem (A01C), subtracts a reserve computed by F2_02BA (not yet identified), and divides the used memory by the total (global −$522) in SANE. The fraction is multiplied by 10 (the SANE constant at 056C, value 10.0), rounded to an integer, and clamped to 0–9. The level is stored in the global −$51E, and the icon redraws when it changes. The "Lo!" text was not found as ASCII in the code, so its trigger is still open.
- **Port:** the port has none of this (no memory indicator, no Compact Memory, no Release Clipboard button).

## Resolved (2026-10-31): Info Box buttons, Lo!, and the free-memory figure
- **Buttons (confirmed from DITL 10004, labels in order):** item 2 is **Compact Memory** (branch F8_0161A 016E4: it flushes the clipboard handle, calls CompactMem(0x800000), then redraws) and item 3 is **Release Clipboard** (branch 01720: flushes the clipboard, then redraws the counts without compacting).
- **The "reserve" is not a reserve.** The helper labelled F2_02BA is really F1_020FE, a 32-bit signed divide (CODE 1 at 0214A). The indicator divides FreeMem by 2 (bytes to samples: 1 sample = 2 bytes). So "free" is in samples and "used" is the total (global −$522) minus that.
- **Level and Lo!:** level = round(10 × used / total), capped at 9 (F23_004C6). The level selects icon ID 9150 + level (F23_038EC): ICON_9150–9159 are the ten memory-fill frames. Icon 9159 (level 9) has "Lo!" drawn in its top-right corner, so Lo! is simply the top level, shown when memory is about 85% or more used.
- **Corrections to earlier entries:** the "HGETVOL" label in CODE 1 is CompactMem (A04C); the "reserve" note above should read "FreeMem ÷ 2 (samples)".
- **Still not traced:** the exact rounding mode of the level (SANE FOX2Z, default round-to-nearest assumed), and what sets the global −$522 total (not yet traced).

## Startup: the memory total and the playback route (CODE 3, about 01306–01360), traced 2026-10-31
- **The memory total (−$522) is set once, at startup:** FreeMem ÷ 2 (F1_020FE divides by 2), i.e. the free memory in samples when TurboSynth launches. So the memory indicator measures memory used since launch, not against the machine's total RAM. The level is round(10 × (total − current free samples) / total), capped at 9. The level global (−$51E) is cleared at this point.
- **The playback route word (−$526):** at startup it is set to 6 (Sound Manager). If the SampleCell check (F3_0120C) fails and the route is 5, it falls back to 6. So the route codes are: 4 = Digidesign DSP card, 5 = SampleCell, 6 = Sound Manager. This matches the three radio items of Playback Options (items 4, 5 and 6). The earlier note saying codes 1–3 were unclear is superseded.
- **The clear flags:** −$4EB and −$4EA are cleared here too (before the total is read). So −$4EA is a per-session state set during editing, and it is reset at startup. This supports the idea that it tracks an editing state, not a hardware setting.

## Hardware Setup (command 6005): the dialog is Digidesign's, not TurboSynth's (2026-10-31)
- TurboSynth does not dispatch command 6005 itself (its only references are the enable logic in F23_02A48). The dialog is DITL 4663 in the extracted resources, which is Digidesign's DSP driver dialog. Its items: Select Card Type, Cards To Use (Cards 1–6), Ch 1,2 Input (analog or digital), Recalibrate Inputs, Sample Rate, Sync Mode, Digital Format, Output Resolution, Track Mapping, Other Options, PROFile, and Bridge I/O / Disk I/O / Peripheral settings.
- So the fields belong to the Digidesign driver and are not limits set in TurboSynth. The port does not need them. The only TurboSynth-side clue is the DSP error in CODE 6: "DSP Card not avaiable- check the Hardware Setup window." (sic: the original's spelling).
- **Playback Options labels confirmed** (DITL 10050): Digidesign DSP Card (item 4), SampleCell (item 5), Sound Manager (item 6). This matches the route codes at startup.

## Parameter Manager (10009, F6_0132C, CODE 6), traced 2026-10-31
Labels (DITL 10009): Sample Rate Default, Length Default, Frequency Default (440.0000 Hz), and four check boxes: Inherit sample rate from input, Convert sample modules to default, Inherit length from input, Dynamic length based on parameters, and Monitor loop at output.
- **Sample Rate Default:** checked against lo = max(FixDiv(length default, 1000)·32000 ... i.e. length default ÷ 32, 100) and hi = 100000 (CODE 6 at 014D6). Same rule as the module fields.
- **Length Default:** lo 0, hi = min(2147483, 32 × sample rate default) (CODE 6 at 01568). Same rule as the modules.
- **Frequency Default:** lo 0, hi = the Parameter Manager's default sample rate ÷ 2, i.e. the Nyquist limit (CODE 6 at 015E6). The default sample rate is the global −$4F2, which new modules copy (CODE 36 at 0002C copies it into the module's rate at +0x110).
- **Port:** the port's Parameter Manager has the four check boxes and the three defaults. It does not check the frequency against the Nyquist limit, and its frequency limit is fixed rather than tied to the default rate. Not yet changed.

## Port changes (2026-10-31)
- **Parameter Manager:** rate 100 to 100000; length 0 to 32 × rate (refused with the original's alert otherwise); frequency 0 to half the default rate (the Nyquist limit).
- **File > Get Info (⌘I)** now opens the document Info box (the original's dialog 10004) instead of the selected module's window. The module windows still open from the Info tool and by double-clicking.
- **Document Info box** (src/app/DocumentInfo.cpp): Memory used and free in samples, the ten-level indicator (Lo! at level 9), and the two buttons.
- **Compact Memory** frees the saved undo states; the original compacts the Mac heap, which has no direct equivalent here. Undo history is lost, so it is a deviation.
- **Release Clipboard** clears the module and sound clipboards, as in the original.
- **Memory budget:** the original's total is free memory at launch on the Mac. The port uses a fixed 8 MB of 16-bit samples (4,194,304 samples) as a stand-in, so the indicator's levels are based on that budget and not the user's machine.
- **Not built:** the memory fill on the palette's info icon (the box shows the indicator instead); Hardware Setup (a Digidesign dialog, not part of TurboSynth).
- **Memory budget (2026-10-31):** Model 1 uses a 32 MB stand-in (16,777,216 samples) for the Info box's total. Taking the real system RAM is planned as a Model 2 option.

## Next-steps round (2026-10-31)
- **Crossfade alert wording confirmed:** the code resource strings are "The Crossfade Length must be at least 8 samples." and "The Crossfade Length is too large." The port's alerts match exactly.
- **Palette Info icon:** the port now draws the memory level icon (9150 + level) in place of the Info tool icon, and it repaints with each edit. Built.
- **−4EA setter callers:** no reference to F28_02D28 exists in the code or in any resource header, so it is reached indirectly. A static trace can't find its callers; a breakpoint trace in the emulator is needed.
- **Output box write-back:** unresolved. F37_00492 does no range check, but whether an edited value is stored has not been traced. Needs an emulator test.
- **Click tests (item 1):** not done by me. Keyboard shortcuts and menu popups did not reach the app from the test script, so the Info box, Compact Memory, Release Clipboard, Parameter Manager, and frequency ceiling need a manual click test.

## Show Clipboard (item 35) enable state (traced 2026-11-01)
- **Menu resource:** MENU 3 (Edit) lists Show Clipboard as the last item (19). Its enable flags are all set, so it starts enabled.
- **Enable routine:** F23_02A48 (CODE 23, A5 jump table entry JT[846] at A5+$1A90) writes the menu state for the Options and Edit items. It first calls F5_0072C (JT[180]), which enables item 35 with flag 1 (along with item 36, Quit). Then it writes item 35 again: `move.w #$23`, `clr.w`, `jsr $6DA`, which passes flag 0. That write comes last, so the item ends up disabled.
- **Helper:** F5_01DB6 (JT[215]) takes the item and a flag byte. A non-zero flag calls the EnableItem trap (A939); zero calls DisableItem (A93A).
- **Result:** Show Clipboard is disabled whenever F23_02A48 runs, and nothing in the code sets it back to enabled. This matches the emulator: it stays greyed out in every case tried.
- **Runtime caller (confirmed 2026-11-02, MAME Quadra 950):** there is no direct call to JT[846]. It is reached through `jsr $17FA(A5)` (JT slot at A5+$17F8, CODE 18 offset $13FC), which leaves the stack unchanged, so it acts as a method dispatch. The two call sites are F5_01298, passing the object in global −$752, and F5_0047A, passing the next object from field +2 of the current one (in F5_00462, which F5_0072C calls first). This looks like MacApp's menu setup walking its target chain, with F23_02A48 as the application-level override. The MacApp names are inferred, not read from the binary.
- **Trace result:** the JT[846] slot was hit 5 times during launch and two scripted Cmd-A presses. The first hit is at the unloaded stub, so it is probably counted twice by the segment load, which leaves 4 calls. That matches 4 calls to JT[180] and 4 writes of item 35 with flag 0, all from return address F23_02ABE. Edit menu enableFlags bit 19 (Show Clipboard) read 1 before the first menu setup and 0 at every later sample (every 5 frames). F5_0072C's enable at F5_00756 uses a direct call, so the jump table breakpoint can't see it; that part remains static evidence.
- **Other enables:** a byte search of all CODE resources found no other `move.w #$23` followed by the enable helper. This wouldn't catch an item number passed in another form.
- **Edit title after Cmd-A (user's check and scripted run, 2026-11-02):** in a new document holding only the Output, Cmd-A selects the Output and the Edit title goes grey (enableFlags 00000000). Clicking it opens the menu with every item grey, and the title stays grey (the user's report and the scripted run agree). It greys because nothing is enabled: the Output can't be cut or copied, and Select All disables itself once everything is selected. With a Mixer as well, Cmd-A leaves Undo, Cut, Copy, Clear and Duplicate enabled and the title black. After Cmd-A and a click in empty space, Select All is enabled and the title black. Port difference: the port's menu titles never grey, and Select All from the patch window isn't in the port yet.
- **Manual versus SC 2.22:** the reconstructed manual says of this item, "Displays the contents of the clipboard", and its Edit menu figure doesn't mark it disabled. SC 2.22 disables it every time, and the user found no state that enables it. The port follows the program. Whether an earlier version enabled it hasn't been checked.
- **Port:** the port shows Show Clipboard greyed out in Edit, which matches.

## Parameter Manager frequency limit (confirmed 2026-11-01)
- CODE 6 at 015CC–015E6: the default sample rate (global −$4F2) is divided by 2 (F1_020FE, via A5 $2BA), then the value goes through the range check F1_01B2A, which refuses a frequency above that Nyquist limit. This matches the port's `jlimit(0, rate / 2, …)` in the Parameter Manager OK handler.

## Mixer Info box (partial, 2026-11-01)
- F16_04390 (CODE 16) creates the dialog and stores the module (already known). It does not check fields.
- F16_043F0 (CODE 16) is a drawing routine (MoveTo and LineTo calls), not a field check.
- The Mixer's field check is still not located. Until it is, the port's Mixer uses the envelope box's rules, which the notes say the Mixer shares. Confirm in the emulator.

## Mixer window layout (manual figure p033_b, 2026-11-01)
- The manual's Mixer window shows one column per connected input: the module's name, a vertical slider, and the level as a percentage under it (for example "81%", "22%", "66%"). A scroll bar along the bottom reaches the rest when there are more inputs than fit. The window's title is "Mixer-1".
- The port's Mixer (ModulePanel.cpp, kMixer) shows only text fields labelled "Input N level", 0 to 100. It has no sliders, no module names and no scroll bar. This is a layout difference, not a limit difference.
- The figure is a degraded scan, so the exact slider geometry should be checked in the emulator when the manual images are recovered.

## Cord rules compared with the port (2026-11-01)
- The port's PatchView::cordRefusal matches F16_045B6 in order: duplicate cords and the output message, the input limits (Oscillator, Sample and Noise 0; Modulator 2; Mixer 32; others 1), and the feedback check (a path from the destination back to the source). The port's feedback check walks upstream from the source, which finds the same loops as the original's forward check.
- **Fixed:** a cord from a module to itself is now refused silently, as the original does. The port previously let it through the feedback check.

## Range-check callers (2026-11-01, for the Mixer field search)
- The range check is A5 $1B2A (JT[865], F24_00152). It has 15 call sites: CODE 6 (3, Parameter Manager), CODE 15 (2, Convert to Oscillator: 20–1000 ms and 20 Hz to rate ÷ 2), CODE 23 (2), CODE 28 (4, Sample), CODE 35 (2), CODE 38 (1), CODE 39 (3).
- The CODE 23 call at $1324 checks a value against $7D00 (32000) and a limit from the module; the call at $13EC checks against $186A0 (100000, the rate maximum). These are the nearest to a generic module-box check. Which module types reach them is not yet traced, and the Mixer is one candidate.
- The Mixer's Info box field checks are therefore not yet identified. The next step is the type dispatch leading to CODE 23 at $1290–$1330.

## Mixer field limits: method-table route (2026-11-01, partly traced)
- ts_methods.txt maps each method selector to its per-type routine. For TMIX the selectors are @00D94 (F9_00AB4), @00DD2 (F10_01490), @00E10 (F10_01470), @00E4E (F16_0213E), @00E70 (F23_02612), @00EB8 (F29_01DAA), @00F3E (F43_002CE) and @0188A (F43_00004).
- @00E4E (F16_0213E) sets the Mixer's input limit to 32. This is the cord rule already compared with the port.
- @00E70 (F23_02612) is a switch on a code value in D4 (5, 1, 4, 6 and others), with a per-type branch for each module type, the Mixer included. It is the likely home of the Mixer's Info field checks. The code values are not yet mapped to meanings, so the checks themselves are not yet identified.
- Faster route: test the Mixer's Info box in the emulator (enter a Length or rate past the envelope's limits and see whether it is refused, and at what value).

## F23_02612 (Mixer branch of @00E70), read 2026-11-01
- The routine switches on the message code in D4: codes 5 and 10 (at $2674 and $268C), 1 (at $2644), 6, 7 and 8 (at $26C4), and anything else goes to F23_014F4.
- Code 1 reads the Mixer's input list (module field +$12A) and passes the entries to $862.
- Code 10 calls F23_025B8 and then F23_00E4C. F23_00E4C is not a Mixer limit: it doubles a sample count into bytes and compares it with the buffer size (+$116 and +$11A), so it is the memory-fit check.
- No check against a 0–100 range appears in this routine. The manual's sliders are 0–100%, and the port's Mixer fields use that range. The limit is probably enforced by the slider, not a check.
- Result: the Mixer's input levels are 0–100% (manual figure p033_b), matching the port. The Mixer's Length and Sample rate checks are not in this routine, so they still use the shared box rules.

## Mixer Length and Sample rate (2026-11-01)
- The other Mixer method routines are record-sizing and allocation: F10_01470 (base size 64 bytes plus F10_00A02) and F10_01490 (allocates the record). F29_01DAA is the Mixer's handle-locking routine. None of them checks a field.
- So the Mixer's Length and Sample rate limits come from the shared Info box (the same box type as the envelope). The port already applies the envelope rules to the Mixer: Length up to 32 × rate and rate 100 to 100000.
- Measured in the emulator 2026-11-02 (see the next section).

## Mixer Info box, measured (2026-11-02)
Run on SC 2.22 in MAME (Quadra 950, System 7.5.3), scripted with `MacEmu\tools\mame-scripts\drive.lua`, on a Mixer with no inputs. The box is opened with Cmd-I from the Mixer's window. The Info tool in the palette opens the document's memory box instead. "Stored" means the value was still there when the box was reopened.

| Length | Sample Rate | Result |
|---|---|---|
| 0 | 44100 | accepted, stored |
| empty | 44100 | accepted, stored as 0 |
| 1411200 | 44100 | accepted, stored |
| 1411201 | 44100 | "Inappropriate values abound!" |
| 1000 | 99 | "Inappropriate values abound!" |
| 1000 | 100 | accepted |
| 1000 | 100000 | accepted, stored |
| 1000 | 100001 | "Inappropriate values abound!" |
| 2147483 | 100000 | accepted, stored |
| 2147484 | 100000 | "Inappropriate values abound!" |
| 2147483 | 44100 | "Inappropriate values abound!" |
| 1411200 | 44099 | "Inappropriate values abound!" |
| -1, 12a, 1.5 | 44100 | "Enter only numbers to this field." |

- This confirms the shared rule above for the Mixer: Length 0 to min(2147483, 32 × rate), and rate from max(length / 32, 100) to 100000. So 1,411,200 is 32 × 44,100, not a fixed cap. The Mixer accepts up to 2,147,483 at 100,000 Hz.
- After a refusal the box stays open with the entered text, and OK can be pressed again after correcting it. Length 1411200 and above leaves the watch cursor up for a while after OK.
- The memory check wasn't exercised: 2,147,483 samples fitted in the 2,814,080 free in this session.
- **Port:** the limits already matched, because the Length field's `hiNow` overrides its fixed maximum. The difference was that the port clamped an out-of-range value silently. Its Length and Sample Rate fields (ModulePanel.cpp) now refuse with the original's two alerts and put the old value back, whereas the original leaves the box open with the entered text. This applies to every module with those fields, on the strength of the same range check in the code (F23_011C0, F28_02396, F39_004EC). Only the Mixer's was run here. The "Enter only numbers" string is in CODE 6, 23, 28 and 39. The fixed 1,411,200 maximum, which had no effect, was changed to 2,147,483.

## Select All and group selection, measured (2026-11-02)
Run on SC 2.22 in MAME (Quadra 950, System 7.5.3), scripted with `MacEmu\tools\mame-scripts\drive.lua`, on new documents.

**Code:**
- Patch window: F15_00DCE at $1236 walks the modules and sets each one's selected flag (`+0E`, callback F15_00DB6), between two F18_0120C highlight calls.
- Oscillator window: F39_01398 at $141A walks the waveforms and sets each one's flag (`+338`, callback F39_01380), then sends message 13. F39_012D2 enables the item when some waveform is unselected and there is at least one.
- Waveform Duplicate: F39_0242C finds the leftmost left edge (`+332`) and rightmost right edge (`+336`) of the selected icons, and F39_02482 shifts each copy by that span, adding it to the time (`+32E`) and offsetting the rect. The copies are selected and the originals are not.

**Patch window, seen on screen:**
- Cmd-A selects every module, Output included. Select All then greys, and Cut, Copy, Clear and Duplicate stay enabled.
- A plain click selects one module. A click on a member of a group keeps the group. Shift-click adds or removes one. A click in empty space deselects.
- A drag from empty space draws a box that selects anything it touches, even partly. With Shift it toggles what it touches.
- Dragging a member moves the whole group, Output included.
- Duplicate and Paste place copies 32 px right and 32 px down, and copy no cords, even between copied modules. The copies become the selection.
- A selected Output is never copied. After Duplicate it stays selected; after Paste it doesn't.
- Clear and Cut remove the selected modules and their cords. A selected Output stays and stays selected.
- Undo restores the modules, cords and selection. One exception: after Cmd-A, Duplicate and Undo, the Output came back unselected.
- The Tab key cycles Arrow, Patch Cord, Eraser. Shift doesn't connect modules in the original.

**Oscillator window, seen on screen:**
- A new Oscillator has no waveforms, and Select All is grey until one is placed.
- Cmd-A selects every waveform. With waveforms selected, Clear and Duplicate are enabled; Cut, Copy and Paste stay grey.
- Shift-click and the selection box work as in the patch window, the box touching counts, and dragging moves the whole group.
- Duplicate of waveforms at x 231 and 331 put copies at 363 and 463: a shift of 132, the span of the two 32-wide icons. Scale here is 1 px to 1 ms.
- Clear can remove every waveform. Undo restores them, selected.

**In the port:** `PatchView` and `WaveTimeline` now hold a selection set and behave as above. Cut, Copy, Paste, Clear and Duplicate work on the whole group, the paste offset is 32 (it was 24, which nothing had measured), and Undo restores the selection. Tab is added. The port's own Shift-drag cord shortcut is gone, because Shift now extends the selection as in the original. The Edit menu now works on the modules whenever the patch window is in front, even with a Sample selected; it used to act on the Sample's sound.

**Empty Oscillators in the port (user's decision, 2026-11-02):** a placed Oscillator now starts with no waveforms, as in the original, and renders silence. With no waveform selected, a palette preset or loaded file goes on the time line at the next click and stays unselected, as in the original's Oscillator window. With one selected, the preset changes that waveform, because the port has one window where the original has separate Oscillator and Waveform windows. The + button still adds a waveform, a sine at 0 ms when there are none. Waveform rects now follow Move and Duplicate, and an empty Oscillator saved and reloaded correctly in the port (not tried in the original). A temporary 20-check test (since removed) passed.

**Checked in the port:** a temporary 38-check test (since removed) drove the real MainComponent through fabricated mouse events and the menu commands, and all passed. Real mouse input and a stereo Sample pair in a group were not run.

**Differences left in the port:**
- After Cmd-A, Duplicate and Undo, the Output comes back selected.
- The patch window's title is "Untitled", where the original says "Untitled-1".
- With a group, the Convert commands act on the first selected module that qualifies, as the original's menu test finds it (F2_0095E). Running them on a group in the original wasn't tested.

**Not tested in the original:** whether a second Paste lands further along, Select All in other module windows, and Shift-drag of a group.

## Module names and the new document (2026-11-02)
- **Names (CODE 16, table at $35FE):** Osc-, Samp-, Noise-, Amp Env-, Filter-, Spectral Inv-, Delay-, Stretcher- (expanding, type 15), Time Comp- (compressing, type 18), Pitch Shifter-, Pitch Env-, Modulator-, Resonator-, Diffuse-, Waveshaper-, Mixer-. Osc-1, Mixer-1 and Amp Env-1 were also seen in the emulator; the rest come from the code.
- **Number (F16_02BAA, F16_02B60, F16_02B20):** the lowest n from 1 for which no module in the document has that exact name (F1_0239C is case-sensitive). So a cleared or renamed module's number comes back. The port had counted the modules of the same type.
- **New document (F23_02C24):** only the Output, at SetRect(64, 192, 96, 224). In stereo the right jack is at left 96, 32 px beside it, which `setStereo` already does.
- **Port:** `nextModuleName` follows this, and a new document matches. It is silent until something is patched, as in the original. The patch view now caps its zoom at 1.5, its default, where it could reach 2.5, which would have made a lone Output very large. Small patches therefore show at 1.5, and the view still shrinks as a patch outgrows the window. The old starter patch fitted at about 1.1, so icons in a new document look larger than they did. This also limits how far a small saved patch enlarges. A temporary 27-check test (since removed) passed.

## Memory indicator "Lo!" (resolved 2026-11-01)
- "Lo!" is not a text string in the resources or the code, so the earlier search found nothing. The indicator's ten levels are the icon resources ICON 9150 to 9159 (extracted as ts/rsrc/sc222/ICON_9150.bin to ICON_9159.bin). Level 9 (ICON 9159) is the "Lo!" cell, so the label is part of that bitmap.
- The port matches: its palette and Info box draw level 9 with "Lo!".
