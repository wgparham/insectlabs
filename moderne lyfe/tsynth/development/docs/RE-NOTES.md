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
   - if 2 < version < 5: 1 byte. If version == 1: 3 × `int32`.
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

## DSP kernels (ACreate, CODE 29)

Small, call-free Pascal routines, interleaved with large setup routines. They use Q15 arithmetic: `muls`, add, add (×2), `swap` (that is, (a·b) >> 15, truncated). For example:
- `F29_00004`: mix into a buffer, `out = (out·g0 + in·g1) >> 15`.
- `F29_0006C`: clear a buffer.
- `F29_000E4`: a stereo rotation with a moving 16.16 coefficient.

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
  - Once head reaches the input's end, the rest of the output is zeros.
- **Normalization:** afterwards, if `+115` is set.

## Sample (TSAMP, compute F29_00A66)

The compute method only previews. The buffer is the loaded sound: data embedded in the document, or the sound file, which is loaded elsewhere (ASamp/ASoundFile, to be read).

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
  - Stops (zeros) once base + i reaches the limit. Modulator ended: copy the carrier from the last read position.
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

- F29_021A8: out = clip16(in1·g·2 >> 16 + in2), with a zero-filled lead-in. Its user is not identified yet.

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
  3. Stop at outLen. Past the input's end, zeros.
- **Effect:** each cycle inserts one period. The output is longer than the input by (adv + P)/adv.
- **Normalization:** afterwards, if `+115` is set.

## Stretcher, compressing (compute F29_02A66, class D0 "TCOMPRESS"; kernel F29_02C72)

- **Parameters:** as for the expander. P = FixDiv(sr, FixRatio(coarse·100 + fine, 10)), or sr if the pitch is 0.
- **Lengths:** L = P·(101 − amount)/10 (crossfade length). S = start·10·sr/1000. slope = 7FFF0000 / L.
- **Kernel** (argument order to confirm against the pushes: L, P, slope, S, outLen, out, inLen, in):
  1. Copy in → out until in + S.
  2. Loop:
     - B = A + P; end = A + L; gA = 7FFF0000, gB = 0.
     - While A < end: out = (hi(gA)·in[A++] + hi(gB)·in[B++]) << 1 >> 16; gA −= slope; gB += slope.
     - Then A = B.
  3. Stop at outLen. Zeros once B (or A) passes the input's end.
- **Effect:** the output is shorter by about L/(L + P).

## Still open

- Types 15/17/18 against the classes TEXPANDER, TCOMPRESS and TNOISE. The CODE 9 allocations use A5 class-info offsets `$157C` (15), `$15B4` (17) and `$1584` (18), all size 13A. They need resolving: through the A5 init data, or the module UI code in CODE 45 (ACompand) and CODE 47 (ANoise).
- Exact FixRatio, FixMul and FixDiv semantics (rounding).
- F1_020CA and F1_020FE (long multiply and divide).
- Remaining modules: Pitch Shifter (F29_02D12, kernels F29_02FB6 / F29_03044), Pitch Env (F29_030F8, kernel F29_03488), Noise (F29_03540, kernel F29_037C0), Diffuser host (F29_03854, kernel F29_03AA4) and the Diffuser DSP program (`SACD` 110, 56000 code).
- The sample module's sound-file loading (CODE 27 ASoundFile, CODE 28 ASamp): the SFIL (Sound Designer) format, and how the file is located.
- The Output module: what it does with its input (writing a file, sample rate) — CODE 37 AOutput. Also the Output L/R pair (types 100/101).

## Pitch Shifter (TPSHIFT, compute F29_02D12, kernel F29_02FB6). Type 14, 3 words

- **Parameters:** `+132` semitones (signed), `+134` fine (0–100, a fraction of a semitone), `+136` mix %.
- **Input:** only if exactly one input is connected (the count == 1). Otherwise its own buffer with length 0.
- **Ratio:** r = FixRatio(1,1). For k = 1..semis, r = FixMul(r, FixRatio(10595, 10000)). For negative semis, multiply by FixRatio(10000, 10595) |semis| times.
- **Fine tune:** u = FixRatio(10000, 10595); d = r − FixMul(r, u); rate = r + d·fine/100 (long multiply and divide).
- **Kernel** (out, outLen, in, inLen, rate):
  - Position P = 0 (16.16); limit = in + inLen − 3 samples.
  - Per sample: i = P >> 16 (signed); f = lo(P). D = in[i+1] − in[i]. If D ≥ 0: out = in[i] + ((D·f) >> 16) (`mulu`); else out = in[i] − ((−D·f) >> 16). Then P += rate.
  - On signed overflow: base += 32768 samples, P &= 7FFFFFFF.
  - Once in + i ≥ limit, zeros.
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
  - A new segment reloads rate and slope. Once in + i ≥ limit, zeros.
- **Normalization:** afterwards, if `+115` is set.

## Noise (TNOISE, compute F29_03540, kernel F29_037C0). 4 words at +12A

- **Parameters:** `+12A` coarse (×10 Hz), `+12C` fine (0.1 Hz), `+12E` amplitude randomness (0–100), `+130` frequency randomness (0–100).
- **No inputs, no normalization.** It writes its own buffer, of length `+11A`.
- **Random generator:** QuickDraw randSeed `(-$1F2,A5)` is set to 12345 at every compute. Toolbox Random: seed = seed·16807 mod 7FFFFFFF; result = low word, with 8000h becoming 0 (to confirm).
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
- **Lines:** three delay lines of D samples each, all zero at the start, with one shared circular index.
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

## Resolved

- **Types:** 15 = Stretcher, expanding (TEXPANDER); 17 = Noise (TNOISE, 4 words at `+12A`); 18 = Stretcher, compressing (TCOMPRESS). This comes from the CODE 9 allocation offsets ($157C, $15B4, $1584) placed in CLASINFO order: $154C Delay(13), $1554 Res(16), $157C Expander(15), $1584 Compress(18), $158C PShift(14), $15AC PEnv(19), $15B4 Noise(17), $15D4 Diffuse(20).
- **Rates:** every module keeps its own sample rate and length. Buffers pass between modules unconverted (a 30 kHz sample can feed a 31.25 kHz envelope); a module's rate only scales its own timing.
- **Output** (types 100/101, kinds 7000/7001/7002): has no compute method of its own (TBLOCK default), so its result is its input's buffer. Type 100 is mono "Output" or "Output L"; 101 is "Output R".
- **Sound Designer I (SFIL/XFER):** header size in the first word (0538h = 1336); data length in bytes at 94h and B8h; then big-endian `int16` samples. The rest of the header is mostly uninitialised memory. The port takes the rate and length from the patch's module.
- **Sample modules without embedded data:** the port looks for a file named after the module (trailing spaces trimmed) next to the patch, in "Sound files", or in a user search folder.

## Arithmetic assumptions (unverified against a real Mac)

- FixRatio(n, d) = round((n << 16) / d). FixMul(a, b) = round((a·b) >> 16). FixDiv(a, b) = round((a << 16) / b). FixRound(f) = (f + 8000h) >> 16. Rounding is to nearest, half away from zero.
- Long multiply and divide (F1_020CA, F1_020FE): C semantics, truncating toward zero.
- Random: seed = seed·16807 mod 7FFFFFFF (exact); return low word, with 8000h becoming 0.

## Engine status (2026-10-28)

- **Code:** `src/engine` (Patch, Engine, MacMath, SineTable) and `src/cli` (`tsynth-cli patch out.wav [sounds=dir] [info] [module=N]`). It builds warning-free with MSVC /W4.
- **Example patches:** all 29 render (2.22 TS Files, Best of Disk 1 and 2, Sample Files), with no missing sound files.
- **Checks** (`tests/modstats.cpp`):
  - The Analog Synth Model oscillators measure 110, 55, 55 and 1.5 Hz, matching the stored Fixed frequencies.
  - Every module type gives non-silent output without runaway DC, except where a patch's own feedback settings saturate the 16-bit delay kernel (Cheez-Verb's 75% delays, More Big's 97% resonator), which is expected.
- **Not yet verifiable:** there are no reference renders from the original program. The Toolbox rounding (FixRatio and friends) and Random are assumptions.
- **Port decisions:**
  - Outputs are the input of type 100 (left/mono) and 101 (right).
  - A version-1 output with rate 0 takes its source's rate.
  - Sample files are found by module name.
- **Next:** the JUCE standalone app (patch view, module editors, render/play/export), CI workflow, README/ALGORITHM docs, then Model 2.

## App status (2026-10-28)

- **Look:** a JUCE standalone in `src/app` with a Classic Mac look (the user asked for one "closer to its original look", with a dark mode). Light is black on white with a dither desktop and pinstriped windows; View > Dark Mode is the same drawing in warm light ink on charcoal, saved in `TurboSynth.settings`.
- **Icons:** the original ICON resources are embedded in `src/app/ClassicIcons.cpp`, generated from the 2.22 resource fork. Module icons are numbered by kind: 2000 osc, 2001 sample, 2002 noise, 3000–3012 processors (3008 expanding stretcher, 3010 compressing stretcher), 7000–7002 outputs. Tool icons: 1000 arrow, 1005 patch cord, 1007 eraser, 1003 speaker.
- **Patch window:** a two-column palette; place a module by clicking its icon, then the patch. Patch cord or Shift-drag connects, the eraser removes, double-click hears a module. The view fits large patches.
- **Module Info:** numeric fields per type, a breakpoint editor (envelope family), and drawable tables (oscillator waves, shaper).
- **Sound window:** waveform, Play/Stop, play cursor. File > Export Sound writes 16-bit WAV or AIFF.
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
