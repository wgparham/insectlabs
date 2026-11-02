# FPA developer notes — 1.0.0

This is the current implementation note for the canonical FPA 1.0.0 source pair in [`versions/1.0.0/`](versions/1.0.0/). The matching version snapshot is [archived here](versions/1.0.0/DEVELOPER-NOTES.md). See the [user manual](versions/1.0.0/USER-MANUAL.md), [release notes](versions/1.0.0/RELEASE-NOTES.md), [review](versions/1.0.0/REVIEW.md) and [design research](references/FPA-Design-Research.md).

## Identity and panel

- Collection: Teletape Workshop. Module display name: FPA — Four Pre-Amps. Class `fpa`, package `com.insectlabs.fpa`, Effect category.
- Canonical source pair: `fpa.java` and `fpa.vmod`; artwork: `fpa_hero.png` in `versions/1.0.0/`.
- Four independent mono lanes. Each lane has an audio input, GAIN knob, TONE knob, voicing switch, phase toggle and audio output. A fifth output sums the four processed channels.
- Gain controls span 0–1 with a factory default of 0. Tone controls span −1..+1 with a factory default of 0. Mode switch positions 0/1/2/3 map to O/D/Re/C. Factory switch defaults map channels 1–4 to C/Re/D/O. Phase toggles default to normal polarity.
- TONE at 0 is neutral for C, Re and D. O is a low-pass voice and remains filtered at center; its cutoff maps logarithmically from 1.5 kHz at −1 to 8 kHz at +1, making center the geometric cutoff midpoint.
- Each input is normalized by 5 V before DSP, sanitized and limited to ±10 V. Channel outputs are converted back to volts and bounded to ±10 V. MIX is `0.5 * (out1 + out2 + out3 + out4)`; unpatched channels contribute zero.

## DSP map and maintenance constraints

`Initialize` caches control and jack references, reads starting knob/switch values and resets the core. `Notify` forwards gain, signed tone, voicing and phase changes. `ProcessSample` processes each connected lane independently, resets a lane when newly patched, writes its output and forms MIX. `ProcessBypassedSample` directly routes each lane input to its output, silences MIX and marks processing for a one-time core reset on resume.

The nested `PreampCore` owns four independent channel states. Its cached per-voicing gain compensation tables are generated at construction by processing a 1 kHz, 5 V-peak reference through each modeled chain. Each lane sanitizes input, applies a 2× 23-tap windowed-sinc oversampling path around its pre-filter/drive/clipper, decimates, removes DC with a 12 Hz one-pole high-pass, applies the mode-specific tone curve and reference compensation, limits output to ±10 V, fades mode changes over 5 ms and applies phase polarity with a 4 ms glide. Per-sample code does not allocate objects.

The tone parameter is signed. For C/Re/D it directly scales a centered tilt/shelf law; at 0, both gains are exactly unity. O transforms tone to `[0,1]` as `(tone + 1)/2` before the existing exponential low-pass cutoff mapping. Preserve this mapping unless an approved later release changes the control behavior. The calibration reference means output loudness will vary with input level and tone; do not describe the gain knob as a linear attenuator.

The core is constructed with a fixed 48 kHz sample-rate assumption. Filter coefficients and the 2× oversampling path have only been validated at 48 kHz; behavior at alternate host sample rates remains unverified. Measured algorithmic latency is approximately 11 samples (0.23 ms at 48 kHz), with no latency compensation in bypass. Initialization computes calibration tables; audio callbacks do not perform that work. The included headless Java timing check is a rough single-process estimate and not a native Voltage CPU benchmark.

The four voicings are inspirations rather than circuit emulations: C uses the CE-1-derived soft clipper; Re uses a biased asymmetric clip and opposing shelves informed by Roland RE-201/301 references; D models BD-2-style soft diode clipping and tilt; O models SD-1-style pre-emphasis, asymmetric clipping and a passive low-pass tone response. Exact component-to-transfer-function correspondence is not claimed. The [design research record](references/FPA-Design-Research.md) tracks source evidence and open schematic questions.

## Validation and future edits

The exact archived exported and embedded sources compile against the installed Voltage SDK with Java 17 target, `-Xlint:all` and `-Werror`. Validation checks project serialization round-trip, source parity, identity, version Notes, unique control UUIDs, signed Tone metadata and Designer protected-line indices. `tests/PreampCoreTest.java` is compiled against the `PreampCore` extracted from the released module source and checks all four voices for calibration, gain monotonicity, output ceiling, silence, non-finite input handling, polarity, signed tone endpoints and bounds, center behavior, channel independence and mix scaling. See `VALIDATION.txt` and `REVIEW.md` for results and limitations.

When changing the tone law, repeat the signed endpoint and center checks and audition all four voices. For edits to voicings, repeat all gain/calibration/ceiling tests and compare the mode mapping to the panel. For changes to lifecycle or routing, test patched/unpatched inputs, direct bypass, silent MIX during bypass and clean resume in Designer. For edits to oversampling or nonlinear stages, remeasure aliasing, latency and native CPU use; the current benchmarks are not native-host evidence.
