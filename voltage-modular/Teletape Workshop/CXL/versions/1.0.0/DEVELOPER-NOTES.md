# CXL developer notes — 1.0.0

This version snapshot is the canonical implementation record. The module-root [developer notes](../../DEVELOPER-NOTES.md) are maintained for current handoff. The exact source pair is `cxl.java` / `cxl.vmod` in this folder; class `cxl`, package `com.insectlabs.cxl`.

## Intent and controls

CXL is a dark plate-style algorithmic reverb inspired by EMT 140 behavior and Dattorro's published cross-coupled tank family. It does not claim component-exact EMT behavior or Lexicon firmware reproduction. Detailed source evidence is catalogued in [CXL research](../../RESEARCH.md), with the original documents in [`../../references/`](../../references/).

DECAY is normalized 0–1, default noon: 0.8–5 s in UP/12-bit mode and 3–30 s in DOWN/extended mode, nominal at 500 Hz. DAMP is 0–100%, clockwise shortens treble decay. PRE-DELAY is `251^position - 1` ms from true zero to 250 ms. MIX is linear dry-to-wet, with wet-only at full clockwise. GAIN maps as `4*position^2` and shapes only the wet excitation; AMP maps `2*position` and scales the final result. All knobs default noon. The extended mode uses divisor 180 (approximately 8.5-bit effective step) with signal-following TPDF dither, preserving the user-approved grain/clarity balance. The switch defaults UP.

Either single input feeds both sides at the same nominal level. Two inputs remain separate as they enter one shared stereo tank. Two outputs provide the stereo pickups; left output alone averages both complete paths, and right output alone returns the right pickup. Host bypass performs direct normalled routing and freezes all effect histories. Resume preserves the tail and fades wet back over 5 ms. Reset/preset recall clear transient history; tails are not serialized.

## DSP and maintenance map

`ProcessSample` smooths continuous gain/mix/pre-delay and mode settings, runs wet-only `Exciter`/`SteppedConverter` lanes and pre-delay, advances `PlateTank`, then applies pickup filters, output converters and final mix. `PlateTank` uses four input all-passes per lane, modulated early all-passes, fixed late all-passes, four delay sections and distributed signed stereo taps. Its delays scale from 29,761 Hz to 48 kHz. Quadrature tank modulation is 0.23 Hz with approximately ±8 samples. A shelf-based feedback loss models frequency-dependent decay with the nominal duration anchored at 500 Hz.

The wet exciter includes 45 Hz high-pass, 1.8 kHz presence shaping, 9.5 kHz bandwidth, 2/80 ms envelope compression and gentle headroom. Pickup filtering adds a low shelf near 140 Hz and a one-pole low-pass near 6.8 kHz. Converters use signal-relative binary ranges and TPDF dither: divisor 2047 in UP, 180 in DOWN. Zero input remains exact zero. The tank remains floating point; no section is oversampled. This converter and voicing are software design decisions rather than verified circuit reconstructions.

Callbacks allocate no objects per sample. A headless `PlateTank.process` benchmark measured 439.5 ns/sample, approximately 2.11% of one desktop CPU core at 48 kHz; full module and host costs are not measured. JDK 27/Java 17-target compilation, source pair agreement, VMOD round-trip and the archived DSP suite passed. The user approved the sound/behavior in Designer on 2026-10-18. See `VALIDATION.txt` and `tests/DSP-TEST-REPORT.md`; these tests do not establish standardized full-band RT60, native host CPU or isolated alias energy.

Revalidate tank timing/decay, converter grain and tonal artifacts, mode transition, stereo and mono routing, bypass/resume, and user preset behavior after changes to the algorithm, state or controls. Change the module Notes marker and archive a new version rather than editing this release.
