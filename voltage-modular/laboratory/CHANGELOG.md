# Laboratory release notes

## Following 1.0.0

First canonical release after user approval. Shared driven amplifier and audio tap, detector-only 20–200 Hz filter, AUTO/MANUAL attack, two-stage program-dependent release, three-second envelope delay, linear balance, ±5 V envelopes and independent gate LEDs. Finalization preserves approved DSP and defaults. [Review](following/versions/1.0.0/REVIEW.md) and [manual](following/versions/1.0.0/USER-MANUAL.md).

## Selective Service 1.0.0 — 2026-10-05

First canonical release after user testing. The selective band-pass and complementary band-reject paths use manual frequency, fine, width and slope controls, input GAIN and MAIN-only AMP. POWER OFF silences MAIN and passes raw input through C's ceiling; host BYPASS is the approved exact splitter. See [release review](selectiveService/versions/1.0.0/REVIEW.md).

## SN-16u 1.0.0 — 2026-10-03

First canonical release of the manual-first universal test bench. Includes the approved tuning and fixed-voltage references, three pitch standards, sweep, courtesy tones, noise/impulse sources, precise HPF/LPF paths and measurement functions. The comprehensive user manual is included with the release. SDK/export checks and 4,032,101 callback/numerical checks pass; TestBench v1.2 verifies 58 WAVs. See [release review](sn-16u/versions/1.0.0/REVIEW.md).

## n01 Noise Source 1.0.0 — 2026-10-01

First canonical release. Approved dark WHITE/SPECTRA voice, SLOW RANDOM, continuous 20-120 Hz S&H SOURCE with gentle knob coupling, and manually/externally triggered STEPPED output. Source span remains +/-5 V; no dedicated PINK output or internal STEPPED clock. Cleanup is output-identical to the approved DSP; SDK and callback checks pass. See [review](n01/versions/1.0.0/REVIEW.md).

## Deep Tone Generator 1.0.0 — 2026-10-01

First canonical release, approved after the final +/-65 Hz OFFSET audition. C4 tuning, independent source buttons, Beat mix/linear FM, 0-200% Swell and fixed-level Courtesy. Fifth-power OFFSET taper; 1/6/2 ms Beat/Swell/Courtesy reset smoothing; approved compression and direct host bypass. Released Java is unchanged from the approved export. See [release review](deeptone/versions/1.0.0/REVIEW.md).

## r195L: SIN/RND generator 1.1.0 — 2026-09-30

First canonical release, approved after the final Designer build, save/reload and listening test.

- Logarithmic C4-initialized sine source with ±200-cent fine tuning, white noise, filtered noise, moderated random FM and 0.01 V / 0.1 V / 1 V / 10 V output ranges.
- MOD IN provides linear FM in SIN and MOD; RND/RD2 remain direct noise outputs.
- In FLT with MOD IN patched, the oscillator becomes a filtered-audio path. MOD LEVEL is a ±200% bipolar input attenuverter/amplifier; RND MOD blends white noise before filtering; AMP/RANGE remain shared output scaling.
- Restrained high-level compression, 8 ms physical power fade, direct silent host bypass and frozen source state.

Validation: Java 17 SDK compilation of both source forms; paired-source/project/default checks; and 329,622 callback/DSP assertions pass. The user confirmed final behavior and sound.

See [release review](sinrnd/versions/1.1.0/REVIEW.md).

## Function 1.0.0 — 2026-09-30

First canonical release, approved after the user's final Designer build, save/reload and listening test.

- Separate hand-built sine and square oscillator boards, sharing manual tuning but retaining independent phase and slow drift.
- Calibrated output-frequency display with X1 / X10 / X100 / X1K / X10K ranges; X10 initializes at C4 and high ranges use the full dial to the 23.76 kHz ceiling.
- Separate 0.1 V / 1 V / 10 V output ranges, variable square width, restrained high-level compression and 8 ms physical power fade.
- Direct source-module bypass: silent output and frozen state.

See [release review](function/versions/1.0.0/REVIEW.md).

## Generator 1.0.2 — 2026-09-30

Approved canonical maintenance release after the user’s successful final build and runtime test.

- Added an 8 ms physical POWER-switch fade for both main and reference outputs.
- Power-down stops oscillator processing only after the fade reaches silence; power-up resumes through the same short fade.
- Retains the Colorbox-style hard host bypass and all approved 1.0.1 oscillator, FM, output-stage and panel behavior.

See [release review](generator/versions/1.0.2/REVIEW.md). 1.0.1 remains an immutable previous release.

## Generator 1.0.1 — 2026-09-30

Approved canonical maintenance release after the user’s final build/listening check.

- Added 10 ms DUTY CYCLE smoothing; other DSP remains unchanged for equivalent duty input.
- Renamed internal/variable identifiers, expanded helpers and removed empty notification scaffolding.
- Human-readable Display Names, corrected Designer notes and Oscillators category.
- Correct tooltip precision and typed Hz/volt/percentage conversion using the SDK edit path.
- Both source forms compile; the regression suite passes 11,000,045 checks over one million samples.
- Added the shared release checklist and recorded module-specific infrastructure exceptions.

See [release review](generator/versions/1.0.1/REVIEW.md). 1.0.0 remains an immutable previous release.

## Generator 1.0.0 — 2026-09-30

First canonical release following user approval of the final build and sound.

- Four-band sine/variable-triangle generator with smoothed frequency and amplitude.
- Approved output compression, eased in over 5–7 V with the driven character retained.
- Off / Int/4 / Int / Ext modulation; internal triangle rate 0.05–50 Hz.
- Independent pure 1 kHz reference; up to dedicated output, middle off, down to main mix.
- Averaged meter; power-off and host bypass silence outputs and freeze oscillator state.
- Approved Designer controls and artwork retained; exported and embedded source matched.

See [release behavior and validation](generator/versions/1.0.0/README.md).

## sw2 1.0.0 — 2026-09-29

First canonical release, approved by the user after successful Designer builds, routing tests,
and listening tests.

- Manual shared OFF/1–4 selector: SOURCE 1–4 to X and Y to the matching destination; the two routing banks remain independent.
- Position 0 initializes/resets to OFF.
- Always-on restrained line-amplifier character with 2x midpoint conditioning, an open 14 kHz voice, and gentle asymmetric compression above ±3 V.
- Direct CLK routing up; CLK down adds a related-but-distinct 2.2 kHz contact filter plus a 2 ms relay transition.
- Direct Colorbox-style host bypass retains the cached route and performs no DSP/state updates.
- Rapid CLK selector changes retarget continuously from the current routing mix.

Validation: embedded source matches Java export; checksums, Java 17 Voltage SDK compilation,
control/tooltips and Designer anchor checks, and 192125 routing/DC/transition/bypass regression
checks pass. The user confirmed final build/load, sound, behavior, and OFF default.

## Signal Processor 1.0.1 — 2026-09-29

Canonical maintenance release for the approved Designer UI state.

- Retains the current panel skin.
- Enables percentage display on both Gain and EXT LVL controls.
- Preserves 1.0.0 DSP, gain/CV ranges, PROC/VCA behavior, timing, routing, and direct bypass exactly.

Validation: synchronized Designer project/source, release hashes, and Java 17 Voltage SDK compilation pass.

## sw1 1.0.0 — 2026-09-29

First canonical release, approved by the user after successful Designer builds, routing tests,
and listening tests.

- Manual 2x2 relay routing: normal I1-to-O1/I2-to-O2 and alternate swapped paths.
- Patch-programmable use as a selector, distributor, swapper, mute, manual flip-flop, or manual
  gate source, without CV control.
- Toggle and gate button modes, with complementary +5 V output when no inputs are connected.
- Optional CLK mode: 1.8 kHz contact filtering plus 2 ms relay settling for click reduction and
  restrained vintage darkening; direct routing with CLK up.
- Direct Colorbox-style bypass: I1 to O1 and I2 to O2.

Validation: embedded source matches Java export; checksums and Java 17 Voltage SDK compilation
pass. The user confirmed the final module's physical controls, patch functions, switching,
indicator behavior, and CLK voicing.

## Fader|Distr A and B 1.0.0 — 2026-09-29

First canonical releases, approved by the user after successful Designer builds and listening
tests. Both are mono-first manual utility modules with one BIAS control operating simultaneous
X/Y-to-Z crossfading and S-to-1/2 distribution.

- A is fixed linear law and has the heavier, woollier relay voice.
- B is fixed equal-power law and uses a related but more open later-model relay calibration.
- Both use native-rate routing, a 5 ms manual movement transition, and direct Colorbox-style
  bypass: S to 1/2 and X to Z.
- A selected endpoint remains level-conscious without a separate gain stage; equal-power B can
  raise correlated material at center by design.

Validation: shared core regression suite has 49 checks; both Designer/exported source pairs,
checksums, tooltips, and Java 17 SDK compilations pass. The user confirmed both final modules.

## Signal Processor 1.0.0 — 2026-09-29

First canonical release, approved by the user after a successful Designer build and host run.

- Two independent mono stages with matching defaults: PROC, Gain +1, Offset 0, Ext Lvl +1.
- Symmetric -3 to +3 gain; bipolar PROC with lighter character for negative gain.
- Unipolar VCA with rounded response and 3 ms attack / 30 ms release.
- Exact post-character offset, +5 V external gain-control reference, and restrained 2x character processing.
- Colorbox-standard direct bypass, consistent descriptive control IDs, and tooltips for all controls and jacks.
- Preserved Designer section identifiers and corrected final user-code boundaries.

Validation: both source forms compile against the Voltage Modular SDK targeting Java 17;
25 DSP checks and 123 callback checks pass. Project/source agreement, hashes, and named
Designer section boundaries are checked. The user confirmed the final project builds and runs beautifully.
No numerical CPU benchmark was recorded.

These are source archives, not prebuilt installable modules. Start future edits from a copy of the release.
