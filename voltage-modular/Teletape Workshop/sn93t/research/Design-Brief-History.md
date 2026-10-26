# Historical design brief

This is the v0.1.10 design-phase brief retained for provenance. Some status/open-item entries predate the final candidate and the 1.0.0 checks. Current behavior and validation status are in [`versions/1.0.0/DEVELOPER-NOTES.md`](../versions/1.0.0/DEVELOPER-NOTES.md) and [`versions/1.0.0/REVIEW.md`](../versions/1.0.0/REVIEW.md).
# sn93t (tuner core "tnr"): module brief, v0.1.10

## Status
- **SDK compiled and run in Designer** by the author (v0.1.8 loads, compiles, runs). My own checks are headless only: core 216, module wiring 51, against stand-in SDK classes, Java 8 `-Xlint:all` clean.
- Author-reported working: A4 reference knob (380 to 480 Hz, every step), all built-in temperaments, `.tun` loading (Werckmeister III, Harry Partch 43, Wendy Carlos Alpha), tone generator incl. 8-page mode, display legibility, detection on every signal tried within the detector range.
- **Not yet confirmed by a real patch: CAL** (see Open). Release candidate (`v0.1.9rc`) waits on that.
- Decision (author): no smoothing in this module. It adds CPU for no benefit here.

## What the module does
- Detector: McLeod NSDF (coarse pass 12 kHz, refine 48 kHz), median stabiliser (3/5/7/9 readings via RESPONSE), HOLD, mapper with 6 c hysteresis, 250 ms signal-loss hold, level threshold 0.002 V RMS, range edges AUTO 30 to 2200 Hz, LOW 30 to 140, MID 70 to 700, HIGH 250 to 2200.
- Outputs: THRU (MUTE silences it), TONE, 1 V/oct pitch CV (0 V = C4 at the A4 reference), cents CV (+-50 c = +-5 V), gate, CAL CV. Audio, tone and cents-CV jack values are volts / 5; **pitch CV and CAL CV are jack = volts (1.0 = 1 octave), [MEASURED] v0.1.9**.
- Controls: KEY (C to B), TEMP (10 steps), A4 (380 to 480 Hz, 100 steps, value is Hz), RESPONSE, RANGE slide (0 HIGH, 1 MID, 2 LOW, 3 AUTO), TONE SOURCE (up follow, down manual), TONE NOTE / TONE OCT / TONE LEVEL (-40 to 0 dB, 0 dB = 5 V peak), CAL SPAN (2 to 9 octaves), buttons HOLD, MUTE, TONE, CAL, LOAD. `.tun` / `.scl` also load by drag and drop. A loaded custom tuning is saved with the preset.

## TEMP knob (values 1 to 10)
1 12-TET, 2 Werckmeister III, 3 1/4-comma meantone, 4 19-EDO, 5 24-EDO quarter-tone, 6 53-EDO, 7 Bohlen-Pierce (13 equal steps per 3:1), 8 5-limit just, 9 Pythagorean, 10 CUSTOM (.tun / .scl).

### Anchoring rule (fixed in v0.1.5)
- Named 12-note temperaments (12-TET, Werckmeister III, meantone, Pythagorean, just): the note called **A is pinned to the A4 reference** in every key. Werckmeister III key C matches the author's `.tun` to 0.000 c (E4 = 330.000 Hz, C4 = 263.404 Hz at A4 = 440).
- 19/24/53-EDO and Bohlen-Pierce: root-anchored (A is not a degree of those scales). Loaded `.scl`: root-anchored (intervals only).
- `.tun`: absolute (BaseFreq + cents), scaled by the A4 reference relative to 440.
- Pythagorean (fixed in v0.1.5): wolf fifth between G# and Eb; F = 498.045 c above C.
- Every preset was audited against independently known cent values: all within 0.001 c.

### `.tun` display and tone (v0.1.6, v0.1.7)
- A `.tun` that looks like a 12-notes-per-octave keyboard tuning (note n+12 about 1200 c above note n, for 90% of notes) shows MIDI note names and uses the KEY / TONE OCT / TONE NOTE scheme.
- Any other `.tun` is **numbered**: display shows the file's note number (big "43", label "#43"). Manual tone: TONE OCT 0 to 7 picks a page of 16 notes, TONE NOTE picks one of 16; 8 x 16 = all 128 notes; KEY unused.
- 24-EDO labels use a trailing "+" for the quarter-sharp (D#+4 = neutral third). [MODEL] 24-EDO is a grid for Arabic music, not a claim of authenticity. 53-EDO shows degree numbers 0 to 52. Bohlen-Pierce "octave" counts tritaves.

## Measured (synthetic signals, headless)
- Clean tones 30.9 Hz to 2.1 kHz: worst error 0.10 c. Guitar-like and clarinet-like: under 0.01 c. Noise (20 dB SNR): +0.09 c. Note change followed in 200 ms; +-20 c vibrato does not flip the note.
- CAL recovers a simulated VCO slope error of +4.8 c/oct as +4.6, and -9.6 as -9.5.
- Cost: analysis about 1 ms per 25 ms hop on the worker thread; push about 184 ns/sample.
These are exact synthetic tones; real instruments will do worse, especially with inharmonicity (piano, low bass strings).

## Open
- **CAL on a real VCO** [MEASURED]: a second VCO gave -960.16 c/oct, resid 0.05 c, proving 1.0 jack unit = 1 octave. CAL CV and pitch CV were rescaled in v0.1.9. Needs one re-run: expect near 0 c/oct on that VCO. Wide spans: steps above the detector range are now dropped (v0.1.10); untested on the real VCO.
- Cents CV (+-50 c = +-5 V sent as +-1.0) and audio scale (volts/5) may need the same jack = volts treatment. [OPEN]
- 0 V = C4 pitch-CV convention and +-50 c = +-5 V cents-CV scale are my choices, not documented facts. [OPEN]
- `ShowOpenFileDialog` cancel value and exact filter syntax (the dialog opens and files load; the cancel path is untested). [OPEN]
- Stale status text: status messages in the bottom strip are still the small font.

## Designer file handling: what went wrong in the first delivery and the rules now
1. The code blob in a `.vmod` is UTF-8 bytes. A non-ASCII character in my code (the cent sign) was written as one invalid byte and broke the compile. The build now encodes as UTF-8 and checks the whole blob is valid UTF-8 before writing.
2. Each region's trailing generated lines carry line numbers; the first one must sit on the last line of the user code. A region with two of them (EditComponentValue) was left pointing at the wrong line.
3. Always build on the author's **latest** Designer-saved `.vmod`, never an older copy (skins and panel edits live in it); diff first, change only the user-code regions, and never overwrite the author's file (new file name, new version).
4. Version note (module Notes field) is bumped on every non-cosmetic change: v0.1.0 first testable, `rc` suffix for a release candidate, v1.0.0 after the author's tests.
5. The author's `.vmod` is the deliverable; my `.java` export is for reading and diffing only.

## Version history (author's convention)
- 0.1.0 author baseline. 0.1.3 A4 knob fixed (core rejected 380 to 400 and 480; encoding and line-label fixes). 0.1.5 A pinned to the A4 reference for named temperaments, Pythagorean wolf moved. 0.1.6 numbered `.tun` labels, display name fitting, dead-code removal. 0.1.7 8-page tone for numbered `.tun`. 0.1.8 legible tone readout. 0.1.9 pitch CV and CAL CV rescaled to jack = volts (from measured CAL; 2 octaves -0.67 c/oct, resid 0.21). 0.1.10 CAL drops out-of-range/outlier steps (author's wide-span runs failed above 2200 Hz).
