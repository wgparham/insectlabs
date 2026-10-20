# Preserved CED design brief (historical)

This is the original design-phase brief retained for research provenance. Its open items and early test status are historical; the canonical behavior and current evidence are documented in [CED 1.0.0 developer notes](../versions/1.0.0/DEVELOPER-NOTES.md) and [review](../versions/1.0.0/REVIEW.md).

# Module brief: Dimension / CE-1 style chorus (working title)

Personal-use project. No Roland branding or trade dress. Status: **design + DSP core, headless-checked only.**

Evidence tags: **[DOC]** service-notes text/table, **[READ]** my reading of a schematic annotation, **[MODEL]** modeling choice, **[GUESS]** placeholder to tune by ear, **[OPEN]** unresolved.

```text
Module / collection:            InsectLabs / (new)
Public name / class / package:  TBD / TBD / com.insectlabs.<tbd> / Category: Effect (verify enum in Designer)
Baseline version:               none (new module; template = blank.java / blank.vmod)
Candidate version:              0.1 design
Scope:                          DSP design change (new module)
```

## Design intent and approved character
A versatile chorus machine with two flavors: a two-channel Dimension-style stereo widener and a CE-1-style mono chorus/vibrato. Faithful behavior is the default; combination laws and CV are labeled extensions, not hardware claims.

## Hardware sources, evidence, unresolved inferences
Sources: Roland SDD-320 Service Notes (Oct 30 1979, 2nd printing Nov 7 1983), pages 1-5. Roland CE-1 schematic ET-10C (151-010C), one drawing; revision/date not shown on the scan.

| Item | Value | Tag |
| --- | --- | --- |
| Dimension modes | OFF, 1, 2, 3, 4; interlock push switch SUE-52 | [DOC] |
| Combined presses | not documented in the notes | [OPEN] |
| Sweep, mode 1 / 2 / 3 / 4 | 8-12 / 5-10 / 6-9 / 6-9 ms (40 Hz square, MONO input) | [DOC] |
| Mode 4 extra "c" pulse | "increases only MODE in 4"; cause unknown; S4-b grounds a net D (otherwise 56k to -14 V) | [DOC] + [OPEN] |
| LFO period, modes 1,2 / 3,4 | 4 s / 2 s (0.25 / 0.5 Hz) | [READ] |
| LFO triangle amplitude, 1,2 / 3,4 | 6.8 / 4.4 Vp-p | [READ] |
| LFO topology | µPC4558 square + TL082 integrator triangle; second TL082 half is a unity inverter (opposite-phase LFO) | [READ] |
| Per-channel chain | NE570N compressor, MN3007 BBD, MN3101 clock driver, NE570N expander | [DOC] |
| Level / S/N / gain | +4 dBm nominal, S/N > 95 dB A-wtd, 0 dB with mode off | [DOC] |
| BBD stage counts | MN3007 = 1024, MN3002 = 512 | from memory, verify vs datasheet |
| CE-1 chain | preamp (~50x, A 8 mVp-p to C 4 Vp-p) -> 3-section RC -> MN3002 -> RC ladder; no compander | [READ] |
| CE-1 controls | Rate 100k C, Intensity 10k B, Depth 60k B, chorus/vibrato switch | [READ] |
| CE-1 delay range, bandwidth, outputs | not resolved from the drawing | [OPEN] |
| Delay shape ~ 1/f_clk | clock-controlled BBD, a..b endpoints exact | [MODEL] |

## Signal flow and normalization
Voltage audio is +/-5 V doubles at 48 kHz. Core units: 1.0 = 5 V. Unpatched inputs read zero (handbook 7.1); the module layer may normalize R from L (Mono input). Dimension: stereo in/out, per-channel compress (optional) -> pre-LP -> delay -> noise -> post-LP -> expand (optional) -> mix. Left uses the LFO, right the inverted LFO. CE-1: mono in (L), preamp soft clip, delay, chorus (0.5 dry + 0.5 wet) or vibrato (wet only), duplicated to both outputs.

## Controls (proposed; ranges are core ranges)
| Control | Range / default | Notes |
| --- | --- | --- |
| Mode | Dimension / CE-1 | switch |
| Buttons 1-4 | toggles, default off | combine per Combine Law |
| Combine Law | Average / Summed LFOs / Parallel taps, default Summed LFOs | only matters with 2+ buttons |
| Exclusive | on/off | module-layer behavior (needs SDK check); Group ID is design-time only |
| Mix | 0-1, default 1; 0 = exact dry | [MODEL] 1 = equal dry/wet |
| Rate scale | 0.05-4, default 1 | CV candidate |
| Depth scale | 0-2, default 1 | CV candidate; 0 = fixed center delay |
| Input Mono | on/off | [DOC] input mode exists |
| BBD noise | 0-0.05, default 0 | off by default (handbook 2) |
| Compander | on/off, default off | only audible with noise |
| CE-1 Rate / Intensity / Vibrato / Drive | 0.05-12 Hz (1) / 0-1 (0.5) / off / 0.1-10 (1.5) | [GUESS] |

CV laws, gate thresholds, and unpatched CV behavior are **not yet defined**; decide before the Designer pass.

## State, bypass, resume
- Init/reset: histories cleared, button weights adopt the current mask, LFO phases preserved (free-running), noise seed preserved.
- Mode change: wet-path history cleared once, bandwidth swapped.
- Button changes: weights smoothed (~20 ms) [MODEL]; released-all fades wet to dry.
- Preset/variation recall: native control states restored by Voltage; no custom state needed in v0.1.
- Host bypass: direct input to output per channel, no DSP, histories frozen; resume policy to be written once bypass is wired (not in the core yet).
- Latency: none beyond the delay; filters add ~0.9 samples of group delay at DC.

## Nonlinearity, numerics
Soft clip is a rational tanh-like function, exact at |x| >= 3 [MODEL]. Parameter setters clamp with explicit fallbacks. Nonfinite inputs read as zero. No oversampling in v0.1; evaluate aliasing from the CE-1 preamp by listening.

## Validation status (handbook section 19 vocabulary)
- **Headless checks passed, core** (`ChorusCoreTest`): sweep endpoints and period per button, L/R opposite directions, impulse delay and unit conversion, combo bounds and finiteness under all three laws, compander unity, exact dry at Mix 0, NaN/Inf and bad-parameter handling, CE-1 range.
- **Headless checks passed, wiring against STUB SDK classes** (`ModuleWiringTest`): module output equals a reference core through `Notify`/`ProcessSample`, rate/depth CV mapping, Exclusive logic (both SetValue-notify behaviors), bypass routing and resume, tooltip/typed-entry round trips, CE-1 switch, NaN bursts. Stubs are my reading of the docs, so this proves my logic, not the SDK.
- **Language-level check only:** compiled with `-source 8 -target 8` and `-Xlint:all`, no warnings. Not compiled with `--release 17` or against the real Voltage SDK, so **SDK compiled is not claimed**.
- **Template integrity:** all 30 `//[user-...]` marker lines in `dimensionChorus.java` are identical to `blank.java`; only the eight user regions, the class/package rename, and a TEST-ONLY field block differ.
- **Not done:** native Designer/Voltage build, panel, recall, undo, listening, CPU in Voltage, `.vmod` synchronization (no Designer project exists yet for the new controls).
- Indicative cost: about 215 ns/sample (about 1% of one core) in the worst-case configuration on a headless JVM here; not a Voltage measurement.
- Test limits: the compander's -30 dB is set by my floor choice, not hardware. The opposite-polarity test checks motion, not sound. Impulse-delay tests run at depth 0 only.

## Open items
1. Combined-button behavior of the real unit (only the interlock switch is documented).
2. Mode 4's extra pulse: trace net D to its destination.
3. CE-1 delay range, bandwidth, and Mono/Stereo output mix.
4. Whether the JFET stages (Q9/Q10) linearize the 1/f clock response.
5. Java target: Cherry's docs say Java 8 SE; the handbook says `--release 17`.
6. Designer SDK semantics for `VoltageToggle.SetValue` and `EditComponentValue` (see DesignerControlSpec.md).

Files and sha256 (this candidate): `ChorusCore.java` 92f6863da734acf65a32881b0d2cbb23cd98818c5a24ffcf208c78c9a0ae1010; `ChorusCoreTest.java` 5f263dbd819c70ee1f3f63bc2078b5d7713e3c69ee70bb321ca3861f81eb10e4; `ModuleWiringTest.java` a80e2a560fa37790fc4cd0605717d63e4af5561fbd6af9c56aacc522ddeae042; `assemble.py` and `dimensionChorus.java` are in the bundle (hash after your last edit). Control names and ranges: see `DesignerControlSpec.md`. Publication status: none. Cleanup: nothing committed or pushed.
