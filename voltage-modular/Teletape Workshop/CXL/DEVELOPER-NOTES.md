# CXL developer notes — 1.0.0

Collection: Teletape Workshop. Canonical files are `cxl.java`, `cxl.vmod` and `cxl_hero.png` in [`versions/1.0.0/`](versions/1.0.0/). Class `cxl`, package `com.insectlabs.cxl`. See the [user manual](versions/1.0.0/USER-MANUAL.md), [release review](versions/1.0.0/REVIEW.md), [validation](versions/1.0.0/VALIDATION.txt), and [research packet](RESEARCH.md).

## Design intent and lineage

CXL is a dark plate-style algorithmic reverb inspired by EMT 140 behavior and the cross-coupled tank topology published by Jon Dattorro. It does not emulate an EMT plate, an EMT amplifier, or Lexicon firmware bit-for-bit. The source documents, design decisions and explicit uncertainties are in [RESEARCH.md](RESEARCH.md); scratch code is preserved in [`research/Notes Towards A CXL.md`](research/Notes%20Towards%20A%20CXL.md).

The user chose separate left/right injections into one shared tank, even though the EMT documents describe summing inputs to one mechanical driver. The user's purpose is to retain stereo input differences. The extended voice keeps an 8-bit panel label and longer decay range, but its effective converter step is softened to a 180-level divisor (approximately 8.5-bit) after listening rejected full 8-bit as too noisy and the prior 9-bit step as too clean.

## Controls and routing

- DECAY: normalized 0–1, default 0.5. Exponential 0.8–5 seconds UP, 3–30 seconds DOWN, nominal at 500 Hz. The overlap supports same-time color comparisons. At noon the targets are 2 s and about 9.49 s.
- PRE-DELAY: normalized 0–1, default 0.5; `251^position - 1` ms (0–250 ms), true zero at CCW. Changing its smoothed read position creates tape-like pitch movement in the newly fed signal.
- DAMP: normalized 0–1, default 0.5; CW shortens treble decay. At noon the nominal high-frequency ratio is geometric between 55% and 8% of the midband duration.
- GAIN: `4 * position^2`, 0–4×, default 1× at noon. Affects the wet excitation only.
- MIX: linear 0–100% wet, default 50%, no center boost; CW is wet-only.
- AMP: `2 * position`, 0–2×, default 1× at noon; scales the final dry/wet result.
- CHARACTER: two-position switch, UP/state 1 by default selects the nominal 12-bit step and 0.8–5 s range; DOWN/state 0 selects the softened 8-bit-character step and 3–30 s range. Mode transitions retain tank history and smooth losses/converter step over about 25 ms.
- Inputs: either input alone is copied to both excitation lanes at the same nominal level. With both patched, each lane retains its own signal before entering the shared cross-coupled tank.
- Outputs: two pickups are available in stereo. If only the left output is connected, it averages the complete left and right output paths. The right output alone returns the right pickup.

## Signal path and source map

`ProcessSample` polls control targets every 64 frames; input drive, mix, final gain, pre-delay, mode interpolation and the 5 ms wet-entry fade are smoothed. It resolves normalled inputs, runs separate wet `Exciter` stages and `SteppedConverter`s, applies the two pre-delay lines, advances one `PlateTank`, then applies pickup tone, output converters, mix and output amplitude.

`PlateTank` follows the Dattorro Fig. 1 family at 48 kHz: four input all-passes on each lane, modulated early all-passes, fixed late all-passes, four long/final delay sections and distributed signed pickup taps. Delay lengths and tap ages scale from the paper's 29,761 Hz reference. Early tank modulation is quadrature at 0.23 Hz with about ±8 samples of excursion. The two branches use previous-frame feedback samples to avoid lane-order bias.

Decay and DAMP set frequency-dependent feedback loss. A low/high shelf is placed within the tank and its pole is solved so the nominal loss at 500 Hz follows DECAY. Long bass decay can rise to 1.9× nominal; the high-frequency target falls from 55% to 8% as DAMP turns clockwise. These are software voicing choices, not measured EMT calibration. The raw 500 Hz DAMP-50 fit in the test report is 0.467/3.055/4.695/35.078 s for 0.8/3/5/30 s configured values; see the short-endpoint and capture-window caveats there.

`Exciter` applies 45 Hz rumble removal, modest 1.8 kHz presence shaping, 9.5 kHz bandwidth, an envelope compressor (2 ms attack, 80 ms release) and restrained headroom. This shaping is wet-only. Pickup shaping adds a low shelf near 140 Hz and a one-pole low-pass near 6.8 kHz. No artificial mains hum, noise floor or age drift is added.

`SteppedConverter` applies signal-following binary gain ranges with TPDF dither at wet input and pickup outputs. UP uses inverse divisor 2047; DOWN uses 180. Silence returns exact zero. The tank remains floating point. This creates continuous signal-dependent grain; it is not evidence of a particular EMT/Lexicon ADC/DAC circuit. No DSP stage is oversampled. The 8× nonlinear-exciter comparison is an alias proxy with filter/envelope rate differences, not an isolated alias measurement.

`ProcessBypassedSample` copies the normalled input routing directly to outputs. It does not read controls or advance filters, random state, tank or smoothing histories and does not apply MIX, GAIN or AMP. Resume preserves the frozen tail, adopts current controls, and fades the wet contribution in over 5 ms. Reset and preset/variation recall clear transient histories; those tails are not serialized.

## Runtime, validation and maintenance

The audio callback allocates no per-sample arrays or objects. Delay buffers and DSP state are constructed once. `PlateTank.process` measured 439.5 ns/sample (2.11% of one desktop CPU core at 48 kHz) in the archived JDK 27 headless run; this excludes the full module callback and host scheduling. Do not treat it as an instance-count guarantee.

The release pair compiled with JDK 27 targeting Java 17, `-Xlint:all -Werror` and the installed Voltage SDK. `tests/validate_dsp.py` checks actual extracted DSP classes for delay timing, all-pass energy, tank stability, converter comparisons, tonal decay fit, a high-rate exciter proxy and a rough CPU benchmark. The user confirmed the 8.5-bit audition and module behavior in Designer on 2026-10-18. The test harness does not certify full-band RT60, native automation/undo, host CPU, or isolated alias energy. See [validation](versions/1.0.0/VALIDATION.txt) for exact scope.

After changes to converters, decay/damping, tank lengths/feedback, routing, mode transitions, bypass or state recovery, rerun the archived tests, compile both source forms, validate VMOD round-trip and re-audition stereo, mono fold-down, the minimum decay, bright driven input, mode changes, bypass/resume and saved-state recall. Preserve the current archive and develop a new version candidate.
