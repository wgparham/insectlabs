# Cloud Generator Model 1: recovered algorithm

Reconstructed from the 68K application `Cloud Generator 68K nfpu` (CodeWarrior, 1996). The build has no symbols. Addresses below are offsets into the CODE resources. The tools in `tools/re/` regenerate the listings:

1. `extract-resources.ps1` unpacks the MacBinary resource fork.
2. `macdis.c` (with the Musashi disassembler) disassembles it, resolving jump-table calls, A-traps and SANE opwords.
3. `lift.ps1` collapses each SANE `pea/pea/move.w #op/_FP68K` sequence into one pseudo-C line.

The "nfpu" build does all floating-point work through SANE (`_FP68K`) in 80-bit extended precision. Most values are stored back into 32-bit `float` globals and locals between operations. The port keeps every float store, truncation and integer conversion, and computes the 80-bit intermediates in `double`.

## Code map

| Segment | Name | Contents |
| --- | --- | --- |
| CODE 2 | RA.main | Console UI, waveform buffers, field parsing, `interp` (F2_0167E) |
| CODE 3 | RA.aiff | Cloud generator (F3_00C48, 15.8 KB), oscillator (F3_054A0), AIFF I/O |
| CODE 4 | sine table | Copies the float waveform buffer into the int16 oscillator table |
| CODE 5 | gauss envelope | Builds the int16 Gaussian grain envelope |
| CODE 8 | ANSI | Metrowerks Standard Library (`rand` = F8_0543E, `calloc`, stdio, math) |
| CODE 9 | TableDraw | Waveform editor |

## Console values

Text fields are parsed with `atof` and stored as `float`:

- start, duration
- density initial/final
- amplitude initial/final (%)
- high bandlimit initial/final, low bandlimit initial/final
- grain duration initial/final

The stereo potentiometers are 0–100 controls (value 50 at creation), displayed as −100…+100. Each is stored as `value/100`, or as the sentinel 999 when its Random box is checked.

When Random is checked for grain duration, the fields are relabelled **Maximum** (first) and **Minimum** (second). Each grain then sets both duration values to `Minimum + r·Maximum`, where `r = rand()·(1/32767)`. This assignment persists for later grains.

## Tables

**Gaussian envelope** (CODE 5), 513 entries, `i = 0…512`, `x = i·6/512 − 3`:

```
gauss[i] = (int16) trunc( exp(−x²/2) / √6.28318 · 2.506 · 32767 )      (80-bit SANE)
```

The constants are the literals 6.28318 (under a square root, F8_046C) and 2.506, so the peak is 32758 (0.99975 of full scale), and gauss[0] = 363. An earlier reading took the first constant as 3.14159 used as a divisor, giving a peak of 26137; rendering in an emulator showed the original peaks at 32758 (verified 2026-10-30 with a square-wave grain, which reproduces the original exactly).

**Waveforms** (CODE 2) fill a 512-point float buffer `W`:

| Waveform | W[0] | W[i], i = 1…511 |
| --- | --- | --- |
| Sine | 0 | `sin(2π·i/512)·32767` |
| Square | 32767 | +32767 for i < 256, −32767 otherwise |
| Saw | 16383.5 | `k/512·65535 − 32767.5`, with k = i+256 if ≤ 512, else i−256 |
| Import | | first 512 left-channel samples of the chosen AIFF |
| Editor | | drawn |

**Oscillator table** (CODE 4): `T[i] = (int16) trunc(W[i])` for i = 0…510 only, so `T[511]` stays 0. The table is followed in memory by the Gaussian table, so the oscillator's read of `T[512]` returns `gauss[0]` = 363.

**Oscillator** (F3_054A0): for phase `φ ∈ [0, 512)`, `i = ⌊φ⌋`, `f = φ − i`:

```
d = (int16)(T[i+1] − T[i])        16-bit subtraction, can wrap
out = (int16) trunc(d·f + T[i])
```

The phase, `f` and the interpolation are SANE extended (64-bit significand). The port computes them with a software extended type (`sane::Extended`), because in double the result often lands on the other side of an integer and changes the truncated sample. At a sawtooth's jump `d·f + T[i]` exceeds 32767 and the conversion overflows (see the next section).

## Interpolation (F2_0167E)

```
interp(start, denom, t, a, b) = (float)( a + ((t − start)/denom)·(b − a) )
```

Callers pass different denominators:

| Parameter | denom |
| --- | --- |
| density | `(float)(end − start)`, i.e. the duration |
| pan with one Random box | duration |
| high/low bandlimit, amplitude, grain duration, pan with both boxes fixed | **end** = start + duration |

With a nonzero start time, the parameters that use **end** never reach their final values.

## Output buffer

```
end    = (float)(start + duration)
frames = trunc(end · 44100)
```

The buffer is `frames` stereo int16 frames, zeroed. Every mix writes `buf = (int16) trunc(existing + contribution)`. SANE returns −32768 for an out-of-range integer conversion, so overloads wrap to −32768; they do not clip. The whole mix expression is evaluated in extended, with `env/32767` rounded first. In the Basilisk II emulator an out-of-range conversion keeps the low 16 bits of the integer instead, because its `FMOVE.W` goes through int32 (`fpu_ieee.cpp`). That is an emulator artefact: a real 68881/68040 saturates, and software SANE returns −32768. The port keeps SANE's −32768; `CloudParams::wrapIntegerOverflow` reproduces the emulator for verification.

## Per-grain parameters

The same steps run for both cloud types, at grain time `t`. Random numbers are drawn in this order:

1. Random grain duration, if enabled: one `rand()`.
2. `hi = interp(start, end, t, highI, highF)`, skipped when highI = highF. `lo` likewise.
3. `freq = (float)(r·(float)(hi − lo) + lo)`: one `rand()`.
4. `amp = interp(start, end, t, ampI, ampF)`.
5. One `rand()` whose result is unused. The original interpolates two uninitialized locals and draws a random number when the result has a fractional part; with stack garbage this almost always happens.
6. Pan, one `rand()`, depending on which Random boxes are checked:

   | Random box | pan |
   | --- | --- |
   | both | `r` |
   | initial only | `r·(interp(…,1,F) − interp(…,0,F))` = `r·(1−u)`: narrows to hard left, ignoring the final setting |
   | final only | `r·u`: widens from hard left, ignoring the initial setting |
   | neither | `interp(start, end, t, I, F)` |

   Here `u` is the interpolation fraction.
7. `phaseInc = freq/44100·512` (kept in extended precision).
8. `onset = trunc(t·44100)`, `length = trunc(44100·interp(start, end, t, grainI, grainF))`.
9. `amp = (float)(amp/100)`.

## Synchronous clouds

```
t = start
loop:
    if t + interp(start, end, t, grainI, grainF) > end: stop
    density = interp(start, end − start, t, densI, densF)
    grain(t)                       # steps above
    if 2·(length + onset) > 2·frames: length = onset = 0   (grain dropped)
    mix
    t = (float)((1000/density)/1000 + t)
```

## Asynchronous clouds

```
maxDensity = max(densI, densF)
repeat trunc(maxDensity · duration) times:      # int16 in the original
    t = (float)(r·duration + start)
    if (float)(interp(start, end − start, t, densI, densF)/maxDensity) < (float)r': reject
    grain(t); mix
```

Onsets are uniform over the cloud, thinned to the density curve. Grains are produced in random time order.

## Mixing

For `n = 0…length−1` while `onset+n < frames`:

```
env = gauss[ (int16) trunc(n/length · 512) ]
L = (int16) trunc( env/32767 · s · amp · (1 − pan) + L )
R = (int16) trunc( env/32767 · s · amp · pan + R )
```

Here `s` is the oscillator output. The phase is advanced after both channels and wrapped at 512.

## Granulated clouds

The source is read as raw interleaved 16-bit stereo; the sample rate is ignored. Let `total` be the number of int16 samples and `prop = trunc((float)(onset·2) / (2·frames) · total)`.

- **Random:** `pos = trunc(rand/32767 · total)`. No bounds check.
- **Statistical evolution:** repeat:

  ```
  cand = trunc(rand/32767 · total)
  dist = |cand − prop| / (cand > prop ? total − prop : prop)
  ```

  Accept `cand` unless `dist > rand/32767`. Then reset `pos` to 0 if `pos + 2·length` passes the end: async uses `≥`, sync uses `>`.
- **Deterministic progression:** `pos = prop`.

The grain copies `2·length` interleaved samples from `pos`. An odd `pos` swaps the channels. Envelope, amplitude and pan apply as for synthetic grains.

## Text output

Per grain:

```
printf("%f    %f    %f    %i     %i     %f    %f\n", onset, duration, amp, 0, 0, frequency, pan)
```

Frequency is 0 for granulated clouds. The two integer columns come from globals that are never written. The file is then re-read as floats and rewritten, skipping lines whose onset equals the previous line's.

## Random numbers

Metrowerks `rand()`: `seed = seed·1103515245 + 12345; return (seed >> 16) & 0x7FFF`. The original calls `srand((unsigned short) time())` per generation, so its outputs are not reproducible. The port takes the seed as a parameter.

## Verification against the original

Renders by the original 68K application in the Basilisk II emulator (Quadra 650 ROM, System 7.5.3), compared with the port at the recovered random seed and with `wrapIntegerOverflow` set. Each cloud lasts 1 s at 44.1 kHz. With both Output buttons on (the console allows it), one Generate writes an AIFF and a Text file from the same seed:

| Cloud | Settings | Result |
|---|---|---|
| cg1 | synchronous, sine, density 10, amp 80 %, 200–2000 Hz, 0.05 s grains, centre | identical (seed 17854) |
| cg2 | synchronous, square, 10 Hz, density 1, amp 100 %, pan 1 | identical (seed 0) |
| cg3 | asynchronous, sawtooth, density 20→5, amp 60→90 %, high 4000→500, low 100→300 Hz, grains 0.02→0.08 s, both pans random | identical (seed 10555) |
| cg4 | cg4t's settings, an earlier render | identical (seed 35533) |
| cg4t | asynchronous, sawtooth, start 0.5 s, random grain duration (Maximum 0.02, Minimum 0.08), initial pan centre, final pan random; other settings as cg3 | identical (seed 9194) |
| cg4x | the same cloud as Text output | identical text |
| cg5 | cg4t's settings, granulated from cg1, Random selection | identical except one grain that reads past the end of the source (frames 47262–49062): the original reads whatever memory follows the sound, the port reads silence |
| cg5x | the same cloud as Text output | identical text (seed 37281) |
| cg6, cg6x | cg5 with Statistical Evolution selection | identical audio and text (seed 55288) |
| cg7, cg7x | cg5 with Deterministic Progression selection | identical text (seed 63328); audio identical except two grains that read past the end of the source (frames 64193–64236 and 64502–65002) |
| cg8, cg8x | synchronous, granulated from cg1, Statistical Evolution, otherwise as cg5 | identical audio and text (seed 6885) |

Before the extended arithmetic, cg1 had 2 samples off by 1. cg3 also had 61 overflow samples at the sawtooth jump, which match only in wrap mode.
