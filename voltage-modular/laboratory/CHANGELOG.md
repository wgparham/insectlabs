# Laboratory release notes

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
