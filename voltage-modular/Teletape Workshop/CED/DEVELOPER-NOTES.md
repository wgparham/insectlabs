# CED developer notes — 1.0.0

This is the canonical implementation record for the `ced` class in `com.insectlabs.ced`. The matched pair is `ced.java` / `ced.vmod`; the panel file is `ced_hero.png`. See the [user manual](versions/1.0.0/USER-MANUAL.md) and [review](versions/1.0.0/REVIEW.md).

## Implementation map

`ProcessSample` handles a pending bypass resume, reads finite audio/CV, normalizes an unpatched right input to left, converts Voltage's ±5 V range to the core's ±1 range, processes the shared `ChorusCore`, and converts the result back. `ProcessBypassedSample` copies the input paths directly and sets the one-shot resume flag. `Notify` routes knob, toggle, and switch changes. `Initialize` reads current knob/switch values into the core. Custom tooltip/edit conversion exists for the logarithmic Rate, CE-1 Rate, CE-1 Drive, and dB Noise controls.

The core is extracted without arithmetic changes to [`dsp-primitives` ChorusCore](../../../dsp-primitives/src/com/insectlabs/dsp/chorus/ChorusCore.java). The module intentionally keeps a self-contained embedded copy for Voltage Designer. Dimension processing uses four triangle LFOs, the documented per-program delay ranges and opposing stereo sweeps, 0.5 ms minimum delay, one shared delay line per channel, two cascaded one-pole filters before and after each delay, optional deterministic noise and compander, and smoothed program engagement. Combination modes use one averaged LFO, summed LFO offsets, or parallel taps from each active program. The single CE-1 path uses a modeled preamp/soft clip, a triangle-driven delay centered at 3.8 ms, rate/intensity smoothing and selectable chorus/vibrato mix.

All core inputs are normalized (1.0 = 5 V). The process path has no per-sample allocations. Fixed arrays and delay/filter state are allocated with the module/core; output mode changes reset wet histories, while host bypass freezes them and the first active sample clears them once. LFO phase is not reset by core reset. The noise source is deterministic and shared across delay processing.

## Designer controls and maintenance

The four 1–4 controls are persistent toggles. EXC enforces a single active program in code; CMP enables the Dimension compander. The bottom ENS/CHO switch selects the mode; SUM/AVG/PAR selects program combination; M/S selects the Dimension input sum; CHORUS/VIBE selects the CE-style dry/wet mode. Dimensions are 1.6 HP. The mode switch defaults to ENS/Dimension, all program buttons default off, and continuous controls use their serialized VMOD defaults.

Keep Designer control UUIDs, saved state, panel geometry, skin, and all 15 generated user-region anchors intact. When editing DSP, update the embedded source and the portable primitive only after reviewing the behavioral difference. Recheck the serialized VMOD round-trip, release Notes marker, and SHA-256 inventory. The archived controls and names were checked for unique UUIDs and lowerCamelCase internal names; human-facing display names use readable words.

## Validation and performance

The exact Java export and VMOD-embedded source both compile against the installed Voltage SDK with `--release 17`, all lint warnings as errors. VMOD value-tree round-trip and 49 control metadata checks pass. `versions/1.0.0/tests/ModuleWiringTest.java` runs the module against local SDK stubs and compares it sample-for-sample with the reusable core. `versions/1.0.0/tests/ChorusCoreTest.java` checks delay sweeps, periods, stereo movement, delay centroids, bounds, compander behavior, exact dry, nonfinite input handling, and CE-1 limits. It also creates the 24-bit TestBench audition render.

Two local headless runs measured the worst tested combination at approximately 1.0–1.7 μs/sample (4.8–8.2% of one 48 kHz core budget). JVM timings vary and do not represent a Voltage runtime or whole-module benchmark. Stub tests validate module control wiring and signal flow but do not replace a native Designer load, saved-patch recall, GUI automation, or user listening pass. See `versions/1.0.0/VALIDATION.txt` and `versions/1.0.0/tests/`.

Future edits should retest the input normalization and bypass routing, switch/button state transitions, all three combine laws, multi-button smoothing, noise/compander interaction, mode clear behavior, typed tooltip inversion, control persistence, and the embedded/portable core parity. Hardware-derived values and software guesses are separated in [design research](research/Design-Research.md).
