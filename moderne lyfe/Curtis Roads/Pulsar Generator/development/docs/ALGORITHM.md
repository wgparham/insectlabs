# Pulsar Generator 2001: recovered design

Pulsar Generator (Curtis Roads and Alberto de Campo, 2001) is a SuperCollider 2 standalone application for classic Mac OS. This document records how its program was recovered and how it works. The port in `src/` follows it.

## Recovering the source

The application file `PulsarGenerator  2001` is MacBinary:

- **Data fork:** a PowerPC PEF executable, the SuperCollider 2 runtime (language interpreter and native unit generators).
- **Resource fork:** menus, dialogs and icons.

The program itself is SuperCollider 2 source code, stored in `Default.lib` beside the application.

The runtime decompresses the library at launch. `compileCompressedLib` calls `expand`, `input_code` and `decode_string`, which are Mark Nelson's 1989 LZW routines unchanged:

- a 32-byte header
- 12-bit codes, packed most-significant bit first
- 0–255 are literals, the dictionary grows from 256 and stops at 4094, and 4095 ends the stream

**The catch:** the runtime reads `Default.lib` through a text-mode stream, which swaps CR (0x0D) and LF (0x0A) bytes. The stored bytes must be swapped back before decoding; otherwise the decoder drifts into garbage after the first few hundred bytes. The `.wt` and `.set` data files are binary as stored and must *not* be swapped (`PulsaretLib/sine.wt` matches a computed sine to float precision).

`tools/re/sc2lib.cpp` decodes the library. `reference/Default.lib.sc` is the result, converted to UTF-8 with LF line endings: 371 KB, 387 classes. Besides the SC2 class library, it holds Pulsar Generator's own classes:

| Class | Role |
| --- | --- |
| `PulsarEngine4` | synthesis |
| `Pulsaret` | pulsaret waveform × envelope |
| `OscXFadeBank` | control oscillators that scan the tables |
| `PG` | application |
| `TablesBank`, `SettingsLib` | table sets, banks, play-control settings |
| `PGControlPanel`, `TablesPanel`, `PGPrefsPanel`, ... | windows |

The SC2 unit generators those classes use are native PowerPC code in the runtime. `tools/re/ppcdis.cpp` disassembles them, locating each function through its CodeWarrior traceback table. The behaviour below that is attributed to a unit generator comes from that disassembly.

## Signal flow (`PG.run`)

```
OscXFadeBank (11 control tables) ──► PulsarEngine4 ──► [ring mod] ──► LeakDC × master ──► reverse ──► out
```

- **Control tables:** `fundFreq`, `pulseMask`, and `formFreq`, `pan`, `amp` for each of the three trains.
- **Ring modulation:** an optional looping sound file (FX_RingMod window): `out × direct + Pause.ar({ sound × out }, wet)`, with `wet = blend × 0.3` and `direct = 1 − blend × 0.1`.
  - Whether the ring modulator is in the graph is decided at Start: a sound picked during a run is not heard until the next one.
  - `Pause` ramps its level from the previous block's value across the block. When the level is 0 in this block and the last, it skips its graph, so with Dry/Wet at 0 the sound file stops advancing.
- **DC blocking:** `LeakDC.ar(out, 0.999, masterVol)` computes `y = x − x₁ + 0.999·y₁`, scaled by the master volume (0–3).
- **Channel swap:** `.reverse` exchanges left and right. This is kept.
- **Channels:** always stereo. `PG.setPrefs` forces `numChannels = 2`, so the `PanAz` code paths are never used.

## Control oscillators (`OscXFadeBank`)

- **Scanning:** each table (2048 points) is read by `Osc.kr` with frequency 0 and phase from a `Phasor`. The phasor makes one pass through the tables per *train duration* (0.1–100 s).
- **Rates:** `Osc.kr` reads each table once per control block (64 samples), at the phasor's value at the block's first sample, interpolating linearly between table points and wrapping from the end to the start.
- **Reaching the engine:** each value passes through `K2A.ar`, which ramps linearly from the previous block's value to this block's, arriving at the last sample. It starts from its first input, so a newly started table set has no ramp up from zero.
- **Reading the controls:** everything downstream reads the *first* sample of that ramp. `UGen.poll` (`_UGenPoll`) returns sample 0 of the current output buffer, and `Impulse` reads its frequency input once per block, also at sample 0. So every control the engine uses is the previous block's value plus 1/64 of the step to this block's: in effect a block (1.45 ms at 44.1 kHz) late.
- **Transport:** Freeze sets the scan rate to 0. Backwards reverses it, and the original keeps the position continuous.
- **Stopping:** `synthHasStopped` returns the cycle display to 0 and clears the phase offset, so every run starts at the beginning of the tables. With Loop on it also releases Freeze and Backwards.
- **Transport buttons:** `[F]` and `<-` do nothing while Loop is off. Changing Train Duration releases Freeze, including when a settings change sets it.
- **Loop off:** a check every 1/30 s stops the synth when the phase wraps. It only tests for the phase going *down*, so Backwards with Loop off stops at the first check. This is kept.
- **Start-up:** the bank triggers its tables 3 ms after the synth starts, so the first pulse sees all controls at zero and is silent.
- **Switching sets:** with a run in progress, the two sets' control values crossfade linearly over the fade time.

## Pulses (`PulsarEngine4.asUgen`)

- **Pulse clock:** `Impulse.ar(|fundFreq| min maxFundFreq)` triggers a `TSpawn`.
  - `maxFundFreq` depends on the number of active trains: 3000, 3000, 2400 or 2000 Hz for 0–3 trains (Preferences).
  - `Impulse` keeps a phase that starts at 1, so the first sample fires. Its frequency is read once per block.
  - `TSpawn` fires on an upward zero crossing and starts the spawned voices at that sample offset within the block, so pulses are sample-accurate.
- **Masking:** each pulse's play/skip decision is made *one pulse ahead*:
  - `pulseMask.coin`: plays with probability equal to the mask value.
  - With Burst Ratio on, also `and: burstLoop.next`. Because `and:` short-circuits, the burst pattern only advances on pulses that won the coin.
  - The burst loop starts at position −2 and SC's `%` is a true modulo, so its first answer is the last step of the pattern.
- **Each played pulse:**
  1. `thisSpawn.releaseAll`. Normal release only affects envelopes sustaining at their release node (the release envelope), which start their cutoff. The predict envelope ignores it.
  2. For each active train (on, with volume > 0), one pulsaret:
     - **`Osc1`** plays the combined table (`pulsaret × pulseEnv`, element by element) once:
       - length `n = round(sr / max(|formFreq|, 1))` samples
       - phase step `(2048 − 1) / n`, with linear interpolation
       - then **holds the last table value** until released
     - **`mul`** is `amp × volume × 0.1`. A negative `amp` inverts the pulsaret.
     - **`Pan2`** applies index `trunc(1024·pan + 1024)` (clamped to 0–2048) into a quarter-sine table: left gain `sin((2048 − i)/2048 · π/2)`, right gain `sin(i/2048 · π/2)`.
  3. An envelope over the mix, with cutoff time `0.002 × 0.6915^edge` s (edge 0–10, at most 0.02 s):
     - **Release envelope** `[1, 1, 0]` with release node 1, if `fund < 20` Hz or the next pulse will not play: it sustains until the next played pulse releases it, then ramps to zero over the cutoff time.
     - **Predict envelope**, otherwise: it holds for `min(1/fund, 1)` s, then ramps to zero over the cutoff time.
     - The source has a third case, "no envelope when the lowest formant ≥ the fundamental". `lowestForm` starts at 0 and only takes minimums, though, so it never rises above 0 and the branch is unreachable for positive fundamentals. Envelopes always apply.

During a set crossfade the pulsaret also crossfades. Each new pulse plays `new × fadeIn + old × (1 − fadeIn)`, with `fadeIn` taken at the pulse.

## Play controls and settings

A `.set` file stores 256 settings beside the tables:

| Slot | Control |
| --- | --- |
| 1–3 | train on/off |
| 11–13 | train volume, 0–10 |
| 21 | burst ratio on/off |
| 22 | burst on count |
| 23 | burst off count |
| 31 | train duration, seconds |
| 41 | edge, 0–10 |

Unused slots hold −99. While stopped, choosing a set always applies its settings. While playing, with *Link Play Controls* on, switching sets morphs the controls over the fade time (`SettingsLib.xFadeTo`):

- volumes, train duration and edge glide in `(fadeTime.round(0.1) / 0.1 − 1).max(1)` steps, one every 0.1 s; the switches and the exact target values are set right after the last step, 0.1 s before the fade time
- burst counts step through whole numbers spread over the fade time, or, if that would be faster than every 0.1 s, in 0.1 s steps of fractional size; each lands on its target at the fade time
- a train switching on shows on at once and its volume fades up from 0; one switching off fades its volume to 0, then switches off. The burst switch is paired the same way with the burst *off* count (`makeStartAndTargetLevels`)
- a fade time of 0.1 s or less applies the new settings at once

*Randomize Controls* morphs to a random state the same way. Each slot is drawn as follows:
- switches: random on or off
- volumes and edge: uniform 0–10
- burst on count: `50.linrand`
- burst off count: random below the new on count
- train duration: `10 + 10.bilinrand`

## File formats

- **`.set`:** big-endian float32. 256 zeros, 256 settings, then 13 tables of 2048 points in the order `fundFreq, pulseMask, pulsaret, pulseEnv, formFreq1–3, pan1–3, amp1–3`. 108,544 bytes in all.
- **`.wt`:** one table, 2048 big-endian float32.
- **`.bank`:** text, one set path per line in classic Mac notation.
  - Paths may be relative (`:DemoTables f:Beat_01.set`) or absolute paths from the authors' machines.
  - The port resolves each entry by file name in the bank's folder first, then along the relative path.
  - Files that are still MacBinary-wrapped are unwrapped on reading.

## Table tools

Each operation converts the table from SC's wavetable format to a signal, applies a primitive, and converts back. The conversion is exact.

| Item | Original behaviour |
| --- | --- |
| Normalize, Reverse, Invert, Absolute Value, Square, Cube, Clip Negative, Zero | As named; Normalize scales the peak to 1 |
| Distort | `x / (1 + |x|)`, then Normalize |
| Smooth | Four passes of a 7-point Hann kernel, `0.125·(1 + cos(πk/4))` for k = −3..3, wrapping around the table. Only power-of-two sizes are smoothed |
| Hanning Window | `0.5 − 0.5·cos(2π·i/N)`, dividing by N, not N − 1 |
| Welch Window | A half sine, `sin(π·i/N)`, not the textbook parabola |
| Parzen Window | A triangle rising by `0.5/N` per point from both ends, so it peaks at 0.25, not 1 |
| Comb | `0.5·(x[i] ± x[i + d])`, wrapping, with `d = trunc(N / spacing)` (N/2 if that is 1 or less). The odd comb adds; the even comb subtracts |
| Fade In, Fade Out, Add Clipboard, Mult Clipboard | Greyed out in table windows. The help says to use the `m` and `M` keys for the clipboard |
| Phase Modulation | `sin(2π·c·i/N + index·sin(2π·m·i/N))`, not normalized |
| Buzz | The closed-form sum `Σ rᵏ·cos((low + k)·θ)`, normalized, with θ starting at π, so the pulse peaks mid-table. *Bilateral Spectrum* instead multiplies the symmetric `Σ_{k=−n..n} r^|k|·cos(kθ)` by `sin(low·θ)` |
| Grain Envelope | Holds 1 over *Grain Duration* % of the table (default 50); the rest is split between a rising ramp at the start and a falling one at the end: Hanning `0.5 − 0.5·cos(π·p)`, Welch `sin(π/2·p)`, Parzen `p` |
| Noise | Uniform in −1..1. The help's shift-key ±1 variant is not wired up for tables |

Constants were read from the unpacked PEF data section through the TOC (main transition vector at data + 0x97F0, TOC at data + 0x8000). The section is pattern-initialized: opcode 3 stores its common block once, with custom blocks copied between repeats; opcode 4 does the same with a common block of zeros that is not stored.

## Tables Bank

- **Size:** a bank holds 18 entries, two of them the reserved current and default sets, so 16 sets. *Add Current* refuses when it is full. Loading a bank has no limit.
- **Loading a bank:** clears the bank and adds each set under its file name. The current tables stay as they are.
- **Loading a set:** adds it to the bank and makes it current.
- **While the synth runs:** loading and saving sets, banks and single tables is refused, and so is picking the ring-mod sound file.
- **Switching sets during a run:** copying the set runs through `PG.doTasks`, one task every 0.03 s (14 table copies, the library update, then `newCurrentTables`), so the crossfade begins 0.45 s after the click. Until it has finished, a new switch is refused ("Wait..."). *Randomize Controls* holds the same lock for the fade time.

## Differences from the original

Model 1 follows the original, quirks included. [MODEL2.md](MODEL2.md) describes the optional corrections. Apart from those:

- **Recording:** the standalone records 24-bit AIFF or WAV. The original recorded 16-bit.
- **Randomness:** the coin flips and random controls use a modern generator, so masking patterns are statistically the same but not the same sequence. The original's sequence was never reproducible either.
- **Table editing tools:** the Fill and Alter menus are native: `WavetableCommand` (menu commands 5501–5505 and 5600–5620) and the `ZPyr...Dialog` classes. The port follows their PowerPC code, quirks included. The extras the port adds are the Fill menu's Constant and Line, and Fourier and Chebyshev as separate number-entry dialogs instead of the original's drawn Harmonics window.
- **Not ported:** the original's help windows, demo-version limits and "Pulsar credits" counter. A plain pulsar count is shown instead.
