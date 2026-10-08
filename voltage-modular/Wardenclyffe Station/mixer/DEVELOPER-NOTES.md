# LM-21 Mk III — DSP and maintenance notes

**Status:** `v1.0.1`, automated validation passed; the user recompiled, tested and approved the corrected panel on 2026-10-08.  
**Pair:** `versions/1.0.1/lm-21_mk3.java` and `versions/1.0.1/lm-21_mk3.vmod`. The Java source is also embedded in the Designer file; this archived pair is immutable; copy it into a new development folder before editing.  
**Prototype:** `versions/1.0.1/references/lm-21_mk3_reverbsc_prototype.java.txt` is retained as historical DSP reference.

## Intent and boundaries

LM-21 is a custom mono studio matrix mixer for personal use, imagined as an expansive WDR/RAI-style instrument assembled from repaired equipment. It is not meant to be a faithful 984 or CP3 clone. The 984 informs the matrix/EQ/output-row topology and residual noon coloration; the CP3 informs analog mixer character and practical studio utility. The simpler, period-faithful minimixer is a separate design.

Keep the module mono-first and audio-only. Do not add CV or stereo behavior unless the design brief changes. The four independent row reverbs are intentional. Retain the 4×4 bipolar matrix, intentional gain build-up, saturation interaction, additive ambience, and useful degradation at extreme PERSPECTIVE settings.

## File responsibilities

- `versions/1.0.1/lm-21_mk3.vmod`: immutable approved Designer panel, control definitions and embedded source. Notes carry the release version.
- `versions/1.0.1/lm-21_mk3.java`: immutable canonical Java export, synchronized with the archived Designer source.
- `versions/1.0.1/references/lm-21_mk3_reverbsc_prototype.java.txt`: pre-integration experiment; compare only when investigating earlier behavior.
- `README.md`: module scope and concise status.
- `USER-MANUAL.md`: operator-facing reference; update whenever control behavior changes.
- `DEVELOPER-NOTES.md`: DSP equations, architecture, and validation plan.

## Control state and exact mappings

**Matrix:** `matrixTarget[row][column]` receives each crosspoint's bipolar `VoltageKnob` value. A row computes `Σ input[column] × matrixSmooth[row][column]`; there is no automatic row normalization. The sixteen coefficients are smoothed with a one-pole coefficient corresponding to approximately 10 ms.

**MIX:** raw knob position `k` is clamped to `[0, 1]` and mapped to linear gain `4kÂ²`. Thus zero is mute, noon is unity, and full travel is 4× / +12.04 dB. The target gain is smoothed over approximately 10 ms and is applied before EQ and the row saturation. A residual reverb tail can continue after the MIX input is lowered because it is stored in the reverb network.

**BASS / TREBLE:** normalized knob position `k` is mapped to `g = 10^(24(k - 0.5)/20)`, giving approximately −12 dB, 0 dB, and +12 dB at minimum, midpoint, and maximum. BASS uses a one-pole low-pass at 250 Hz as the low band. TREBLE uses a one-pole low-pass at 2.5 kHz to split low/high bands and applies gain to the high band. This is an economical broad-shelf approximation, not a replica of a Baxandall or transistor network.

**DIST.:** normalized position is a linear wet-return multiplier from 0 to 1. Wet is added to dry, not crossfaded. `WET_RETURN_GAIN` is currently 1.0.

**PERSPECTIVE:** one shared control updates the reverb engines' feedback, damping, pitch modulation, and age values. The RT60 map is exponential: positions 0–0.60 cover 0.5–5.5 seconds; 0.60–1.0 covers 5.5–30 seconds. Above 60% position, an eased age value darkens the damping cutoff from 10.5 kHz toward 3.2 kHz, increases modulation depth from 0.4 to 2.0, and enables restrained nonlinear loss in the feedback loop. The tooltip displays seconds. These are intentional first-pass values for listening tests.

## Per-sample path

1. Smooth all sixteen matrix coefficients, four bass gains, four treble gains, four MIX gains, four DIST. amounts, and shared reverb controls.
2. Apply approximately 3.3 Hz first-order high-pass coupling to the four input signals. This removes DC while retaining audio-band content.
3. For each row, sum four input contributions using its bipolar matrix coefficients, without normalization.
4. Apply row MIX gain, nominal 1.25× stage gain, broad bass shelf, broad treble shelf, and asymmetric soft saturation.
5. Feed that characterized row signal to its independent reverb network. Add the wet return scaled by that row's DIST. control.
6. Apply approximately 3.3 Hz output coupling and write the row to A, B, C, or D.
7. Average the four processed row outputs (×0.25), apply a separate gentle asymmetric saturation, and write the FULL MIX courtesy output.
8. Copy MULT IN to MULT OUT 1–3 as buffered digital multiples.

The row character function is currently `7 × tanh(x/7)` for nonnegative signals and `6.2 × tanh(x/6.2)` for negative signals. The FULL MIX courtesy function uses `15 × tanh(x/15)` for nonnegative input and `14.5 × tanh(x/14.5)` for negative input. Both are lightweight approximations, not circuit solvers.

## Reverb engine

There are four state-independent mono engines. Each adapts an eight-line scattering feedback delay network from the ReverbSC family. Each line uses a delay buffer, cubic interpolation, slowly changing delay length, feedback state, and low-pass damping. The eight line outputs are mixed through a scattering junction. In this module the returned wet field is mono.

PERSPECTIVE maps the desired RT60 to feedback using a mean line delay of approximately 68.38 ms: `feedback = 10^((-3 × meanDelay) / RT60)`. The feedback damping coefficient is derived from the target cutoff. The long-tail age term gradually increases pitch wander and introduces mild nonlinear loss in recirculation. This helps the 5.5–30 second region sound older and less pristine.

The implementation uses four networks × eight delay lines = 32 delay lines. At the assumed 48 kHz rate, each network holds about 26,000 double-precision delay samples; all four require roughly 0.8 MiB for delay samples before Java array/object overhead. Each audio sample reads four neighboring values per delay line for cubic interpolation, in addition to feedback and filtering. CPU percentage has not yet been measured; profile it in Voltage Modular with all four reverbs active and with several instances loaded.

## Bypass behavior

Host bypass is intentionally abrupt per the series convention. While bypassed:

- I → A, II → B, III → C, IV → D, with no matrix, tone, saturation, or reverb processing.
- FULL MIX is set to silence.
- MULT IN remains copied to MULT OUT 1–3.

Host bypass reads no panel controls and does not advance the power envelope, filters, delays, modulation or smoothing histories. It only transfers the required audio and marks `resumePending`. The first active sample clears coupling, EQ and reverb histories. Targets continue to follow normal UI notifications; the existing control smoothing and frozen power envelope then resume.

## Panel power

`powerSwitch >= 0.5` is ON. `powerGain` starts at zero, rises by `1 / (13.6 × 48000)` per sample while ON, and falls by `1 / (2.1 × 48000)` while OFF. This approved equipment-style envelope multiplies A–D before their FULL MIX average. Once OFF and below `POWER_CUTOFF = 1e-4`, the outputs become zero and processing state is cleared once; subsequent OFF callbacks skip matrix/EQ/reverb work. MULT remains live. The lamp follows the envelope with checks every 480 samples and an approximately 0.002 display-change threshold.

Do not shorten these approved power transitions or smooth host bypass as part of maintenance.

## Sample-rate assumption

This candidate uses a fixed 48 kHz constant for filter coefficients, control smoothing, PERSPECTIVE damping, and reverb delay allocation. Confirm Voltage Modular's supported engine rates and module callback conventions before canonical release. If a runtime sample rate can vary, pass it into the DSP state, recompute all coefficients once during initialization (not per sample), and size the reverb buffers and delay rates from that value. Never merely change the constant without regenerating all state.

## Build and listening checklist

1. Build the current `.vmod` in Voltage Module Designer and resolve any API/compiler diagnostics.
2. Verify all 16 crosspoint controls address the intended rows and inputs. Confirm zero, positive, and inverted settings with a single test tone.
3. Check I–IV map to their intended A–D outputs in host bypass; confirm FULL MIX goes silent and MULT IN still reaches all three multiple outputs.
4. Test each row alone at MIX mute, noon, and maximum. Check unity at noon and +12 dB maximum before character; listen for useful drive and absence of zipper noise.
5. Sweep BASS and TREBLE independently at low and hot input levels. Verify noon coloration, broad low/high action, and no unexpected oscillation or DC build-up.
6. Excite one row's reverb at a time and confirm no cross-row leakage. Sweep DIST. and PERSPECTIVE, especially the 5.5-second transition and extreme long tail. Check feedback remains stable and controls do not zipper.
7. Compare the FULL MIX courtesy output against the arithmetic A–D average. Confirm its added saturation is gentle and does not unexpectedly clip.
8. Profile CPU and memory with one instance, several instances, and all four reverbs receiving signal. Record measurements in this file after testing.
9. Update this file, `USER-MANUAL.md`, README, roadmap, Designer Notes/version, and changelog whenever behavior or released version changes.

## Candidate review and limitations

The cleanup renamed control variables/Internal Names consistently and gave Display Names human-readable labels, retaining every Designer UUID, control range/default, skin and panel geometry. It completed functional jack/matrix tooltips, corrected stale documentation, and removed control reads and power-envelope advancement from host bypass. All native-rate saturation coefficients and arithmetic order are unchanged.

Both source forms compile with `--release 17 -Xlint:all -Werror` against the installed Voltage SDK. The retained validator extracts actual DSP and callback bodies into a headless test class, using value/connection mocks for SDK controls. It tests 32 signed crosspoint routes, notification mappings, typed EQ/MIX/DIST./PERSPECTIVE edits, preset restoration, row/reverb independence, averaging, MULT, frozen-history bypass, resume, power timing, 1,001 taper round-trips, unpatched/nonfinite inputs, and 30 seconds of reverb response at each of three PERSPECTIVE settings. Active output digests match the pre-cleanup baseline exactly: `14811806090894522753`.

Run `python versions/1.0.1/tests/validate.py` from any directory. Use `--baseline versions/1.0.1/tests/baseline/pre-cleanup.java.txt` to repeat the recorded baseline comparison, or supply another saved source path. The harness reads/extracts code at test time; it does not duplicate the audio algorithm. It does not emulate Designer, native control events, saved-patch storage, automation/undo integration, skin rendering or real-time scheduling.

- Native-host build/run tests and canonical approval were confirmed on 2026-10-08; dedicated automation/undo tests remain unmeasured.
- 48 kHz is the documented callback assumption, consistent with the SDK-generated 48,000-callback comment. Other processing rates are not supported by this fixed-rate DSP without adaptation.
- Native-rate nonlinear stages trade CPU against alias suppression; no oversampling was added during cleanup.
- UI meter setters remain throttled within the audio callback as a deliberate implementation exception.
- Generated empty notification cases remain as Designer scaffolding; they do no DSP work.
- Input sanitation replaces nonfinite values with zero and limits finite values to ±1,000,000 V, including the otherwise direct bypass and MULT paths.
- Native-host CPU and allocation profiling remain unmeasured. Offline test wall time is not a Voltage Modular CPU percentage.

## Design provenance

The module is for personal use. Its reverb prototype follows the ReverbSC family of scattering-network design and is retained with source lineage in the code comments. It is an adaptation for this mono instrument, not a claim of a component-accurate physical reverb or a commercial product.

## Local organization after release

The retired development folder has been removed after archive verification. Minimixer and its rLogo asset are in the sibling minimixer/development folder. A standalone reverb engine, parameter helpers and validation are kept in the root reverb-primitives project; the archived prototype and released module remain unchanged.
