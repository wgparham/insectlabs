# SN93T development history

These notes preserve the design and test decisions from the 0.1.x development sequence. They are historical; use the canonical 1.0.0 developer notes and review for current behavior and tested limits.

---

# sn93t v0.1.1 (from your v0.1.0)

Base: your Designer-saved sn93t.vmod (mtime 2026-10-25 ~06:55 local). Only the user-code regions were changed; panel/controls untouched.
Status: headless checks passed (core 143, module wiring 46, stand-in SDK, Java 8 -Xlint:all). NOT compiled against the real SDK. NOT tested in Designer.

## Changes (all non-cosmetic)
1. TnrCore.setRefA4 accepted only 400 < f < 480 (old guard). Now 380 <= f <= 480. This was the A4 knob bug: the core ignored 380-400 and 480 and kept its last value, so the display header disagreed with the knob/tooltip.
2. A4 mapping in the module: Hz = 380 + round(100 * knob fraction); works for a 380..480 Hz knob, a 0..100 index knob or a 0..1 knob.
3. tick() (33 ms GUI timer) now calls applyAll(), so every control is re-read ~30x/s (covers a knob still ramping when its notification fired).
4. EditComponentValue: typed A4 Hz and tone-level dB are converted to the knob's own range (A4 branch is a no-op for a 380..480 knob).
5. Source encoding: the cent sign in the display code is now valid UTF-8 (previously a lone 0xA2 byte).
6. Build: first trailing "pre-user" line number for EditComponentValue now sits on the last line of the user code, as in a Designer-saved file.
Version note: sn93t / v0.1.1 (module notes field).

## Not changed
No smoothing yet (SmoothValue for tone level/sweep is the next step, as v0.1.2).


---

# sn93t v0.1.3 (from your v0.1.0)

Base: your Designer-saved sn93t.vmod (mtime 2026-10-25 ~06:55 local). Only the user-code regions were changed; panel/controls untouched.
Status: headless checks passed (core 143, module wiring 46, stand-in SDK, Java 8 -Xlint:all). NOT compiled against the real SDK. NOT tested in Designer.

## Changes (all non-cosmetic)
1. TnrCore.setRefA4 accepted only 400 < f < 480 (old guard). Now 380 <= f <= 480. This was the A4 knob bug: the core ignored 380-400 and 480 and kept its last value, so the display header disagreed with the knob/tooltip.
2. A4 mapping in the module: Hz = 380 + round(100 * knob fraction); works for a 380..480 Hz knob, a 0..100 index knob or a 0..1 knob.
3. tick() (33 ms GUI timer) now calls applyAll(), so every control is re-read ~30x/s (covers a knob still ramping when its notification fired).
4. EditComponentValue: typed A4 Hz and tone-level dB are converted to the knob's own range (A4 branch is a no-op for a 380..480 knob).
5. Source encoding: the cent sign in the display code is now valid UTF-8 (previously a lone 0xA2 byte).
6. Build: first trailing "pre-user" line number for EditComponentValue now sits on the last line of the user code, as in a Designer-saved file.
Version note: sn93t / v0.1.3 (module notes field).

## Not changed
No smoothing yet (SmoothValue for tone level/sweep is the next step, as v0.1.4).


---

# sn93t v0.1.5 (from your v0.1.4)

Base: your v0.1.4 sn93t_fixed.vmod (mtime 2026-10-25 ~08:33 local). Only the embedded tuner core changed; no panel or module-wiring changes.
Status: headless checks passed (core 206, module wiring 46, stand-in SDK, Java 8 -Xlint:all). NOT compiled against the real SDK. NOT tested in Designer.

## Temperament findings
You asked which Werckmeister III is right, the knob preset or your .tun. Compared note by note (C4..B4, A4 = 440):
* Every interval was identical. The preset sat a constant 11.730 c below the .tun on every note.
* Cause: the preset pinned the key's ROOT (C) at its 12-TET pitch, which left A4 at 437.03 Hz. Your .tun pins A4 = 440.000 Hz. For a tuner whose reference knob is "A4", the .tun convention is right and the preset was wrong.

## Changes (non-cosmetic)
1. Named 12-note temperaments (12-TET, Werckmeister III, 1/4-comma meantone, Pythagorean, 5-limit just) now pin the note called A to the A4 reference in every key. Werckmeister III key C now matches your Werckmeister_III.tun to 0.000 c on all 12 notes (E4 = 330.000 Hz, C4 = 263.404 Hz at A4 = 440). A4 is exactly the reference for all 12 keys x 5 named tunings (60 checks).
   Not changed: 19/24/53-EDO and Bohlen-Pierce (root-anchored: A is not a degree of those scales), and loaded .scl files (intervals only, root-anchored).
2. Pythagorean was wrong: the chain of fifths ran C..F, so the wolf fifth fell between F and C and F was 23.46 c sharp (521.5 vs 498.045). Now the wolf is between G# and Eb (F = 498.045, G# = 815.64).
3. Audit of every preset against independently known cent values: Pythagorean, 1/4-comma meantone, 5-limit just, Werckmeister III, 19-EDO, 53-EDO, 24-EDO, Bohlen-Pierce all match to < 0.001 c (meantone 0.0004 c, rounding of the published values).

## Your other .tun files
Partch 43 and Carlos Alpha both parse (128 entries). Partch: 49.000 Hz -> 49.000, one octave = 43 steps (98.000 Hz), 81/80 step lands at 49.6125 Hz. Carlos: 78.0 c steps.

## Noticed, not changed
* For .tun tables that are not 12 notes per octave (Partch, Carlos) the display shows MIDI note names (e.g. "G2" for Partch step 43). Those names are meaningless there; showing the step number would be better. Proposed for v0.1.6 if you want it.

Version note: sn93t / v0.1.5.


---

# sn93t v0.1.6 (from your v0.1.5, which tested well)

Base: your v0.1.4 vmod with the v0.1.5 core changes carried forward (nothing in your panel/controls touched).
Status: headless checks passed (core 211, module wiring 46, stand-in SDK, Java 8 -Xlint:all). NOT compiled against the real SDK. NOT tested in Designer.

## Change 1: step numbers instead of MIDI names for non-12-note .tun files
A .tun is treated as a 12-notes-per-octave keyboard tuning if note n+12 is about 1200 c above note n (within 30 c) for at least 90% of notes. Those keep note names (A4, C#3, ...); the Werckmeister test file does.
Anything else (Harry Partch 43, Wendy Carlos Alpha) now shows the file's own note number: big "43" on the display, "#43" in the tone label and tooltip, no octave subscript.
Core tests added: 12-note .tun keeps names; 43-per-octave and 78 c-step tables use numbers; Carlos note 20 reads "#20".

## Change 2: long names fit the display (found while rendering the above)
A long tuning name (from the file name) used to run into "A4 440.0" at the top right, and a long status message ("LOADED ...") ran into the HOLD/MUTE flags at the bottom. Both are now shortened with "..." to the room that is left.

## Change 3: clean-up of everything added while chasing the A4 knob (all of it removed)
* Diagnostic tooltip text for A4 (DIAG_A4) - removed; tooltip is "A4 = 440.0 Hz" again.
* GUI timer no longer re-applies every control every 33 ms (that was the smoothing theory; the real cause was the old 400..480 guard in the core).
* A4 maths back to one line: Hz = knob value, clamped to 380..480. (The 0..100 / 0..1 compatibility code and the 1 Hz rounding are gone; the knob is a Hz knob.)
* EditComponentValue no longer touches the A4 knob; it only converts typed dB for the tone-level knob.
* Initialize no longer calls DisplayValueInPercent(false) on knobRef/knobResponse/knobLevel: your panel code already does it for all 8 knobs.
* Scan for names declared but never used in the tuner code: only run() (Runnable), tickHop() (headless test hook) and setThresholdRms() (core setter with no panel control yet) - left in on purpose.

## Noticed, not changed
* For a non-12-note .tun the manual TONE NOTE knob still addresses 12 consecutive notes starting at KEY/OCT, so most Partch/Carlos notes cannot be reached by the knob (follow mode works for any note). A clean option: for these tables, TONE OCT picks one of 8 pages of 16 notes (8 x 16 = 128) and the TONE NOTE knob picks within the page. Say the word and I will do it as v0.1.7.
* The display still reads "KEY C" for a .tun, where KEY has no meaning.

Version note: sn93t / v0.1.6.


---

# sn93t v0.1.7 (from your v0.1.6)

Base: your Designer-saved v0.1.6 (only the skin id differed from mine; carried forward). Panel untouched.
Status: headless checks passed (core 216, module wiring 51, stand-in SDK, Java 8 -Xlint:all). NOT compiled against the real SDK. NOT tested in Designer.

## Change: 8-page manual tone for numbered .tun tables
For a .tun that is not a 12-notes-per-octave keyboard tuning (Partch 43, Carlos Alpha, ...):
* TONE OCT (0..7) now picks a PAGE of 16 notes: page p covers notes #(16p) to #(16p+15).
* The TONE NOTE knob picks one of the 16 notes on that page.
* 8 pages x 16 = all 128 notes of the .tun are reachable, each exactly once (core test).
* KEY is not used for these tables (the notes are absolute, so KEY would only shift the page).
* Tooltips: TONE OCT reads "Tone page 2: notes #32 to #47"; TONE NOTE reads "Tone note: #43  98.00 Hz".
Unchanged: the built-in temperaments and 12-note .tun files (e.g. your Werckmeister_III.tun) keep the octave / key / degree scheme.

Tests: core: pages x positions reach all 128 notes exactly once; page 2 position 11 = #43 = 98.000 Hz with any key; out-of-range rejected; 12-note .tun still gives A4 at octave 4 degree 9. Module: tooltips, key ignored, first/last page ends (#0 = 49.00 Hz, #127).

Version note: sn93t / v0.1.7.


---

# sn93t v0.1.8 (from your v0.1.7)

Base: your Designer-saved v0.1.7 (only the skin id differed from mine; carried forward). Panel untouched.
Status: headless checks passed (core 216, module wiring 51, stand-in SDK, Java 8 -Xlint:all). NOT compiled against the real SDK. NOT tested in Designer.

## Change: legibility of the tone readout on the canvas (bottom strip)
Cause: the whole readout was drawn in the smallest font (Arial Bold 8) in red on the dark blue, which smears the characters after the "#".
* The note and frequency (e.g. "#71  200.16") are now Monospaced Bold 10 (the same family as the TARGET row, which you found legible) in the bright text colour.
* The small red label ("TONE FOLLOW") stays, in the small font. When HOLD or MUTE take room, the label shortens first ("TONE FOLLOW" -> "TONE" -> nothing); the note and frequency never shrink unless even they do not fit.
Nothing else changed.

Version note: sn93t / v0.1.8.


---

# sn93t v0.1.9 (from the author's re-saved v0.1.8)
- [MEASURED] CAL on a second VCO: -960.16 c/oct, resid 0.05 c. That is a perfect 1 V/oct VCO driven with 0.2 jack units per commanded volt, so 1.0 jack unit = 1 V = 1 octave. The first VCO (-986.87) tracks about 11% flat.
- Pitch CV (1 V/oct output) and CAL CV now use jack value = volts (`CV_JACK_PER_VOLT = 1.0`). Before they were volts/5.
- Unchanged: audio in/thru/tone scale (volts/5), tone level, cents CV (+-50 c = +-5 V is still sent as +-1.0). [OPEN] whether those should also be jack = volts.
- Expected CAL result on an accurate VCO now: near 0 c/oct. Note reads v0.1.9.
- Checks: wiring test 51/51 (pitch CV and CAL simulation updated to the new scale). Built on the author's saved v0.1.8 (only skin id differs from mine).


---

# sn93t v0.1.10 (from v0.1.9rc)
CAL only. Test data (author, Test Rack Oscillator, 65.41 Hz at 0 V): breakdown between 6 and 7 octaves, where the top step passes 2200 Hz (AUTO ceiling).
- CAL ignores steps whose measured pitch is outside the current range edges (AUTO 30 to 2200 Hz).
- Then, while the worst step is more than 30 c off the fitted line and at least 4 steps remain, that step is dropped and the line refitted.
- Fewer than 3 usable steps: "CAL FAILED - NEED 3 STEPS IN RANGE".
- Result line adds "(n/N STEPS)" only when steps were dropped.
- Wiring test: new 7-octave check (52/52). Core 216/216. Pitch CV and everything else unchanged from v0.1.9.
- Notes field reads v0.1.10.
