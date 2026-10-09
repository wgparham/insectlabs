# Developer notes — 1947B SIN/RND Generator / Filter

**Canonical version:** `2.0.0`<br>
**Collection:** Wardenclyffe Station<br>
**Designer export:** `nineteenfortyseven.java`<br>
**Designer project:** `nineteenfortyseven.vmod`<br>
**Validation record:** REVIEW.md<br>

## Role and design contract

SIN/RND is a mono oscillator and noise source with five labeled modes. It provides a sine oscillator, direct white and filtered noise, noise FM, and a filtered audio path that turns MOD IN into a sound input when FLT is selected.

Treat the current manual and this version's `.vmod`/`.java` pair as the behavioral authority. The archive version is historical; make any new design changes in a separate development copy and promote only after the user's build and audition.

## Source map

- Canonical source folder: `1947b/versions/2.0.0`.
- User manual: [`USER-MANUAL.md`](versions/2.0.0/USER-MANUAL.md).
- Release review and integrity files remain in the canonical version folder. Do not edit archived `.vmod`, `.java`, panel image, or checksum files in place.
- Designer component inventory: AnalogVUMeter: 1, AudioJack: 2, Knob: 8, LED: 1, Label: 25, Switch: 1. Use stable component IDs in the `.vmod` as the UI-to-code contract; display text is user-facing and may be edited independently.

## DSP architecture

The active per-sample callback is `ProcessSample()` in `nineteenfortyseven.java`. It delegates module-specific work to: clamp, modeIndex, amplitudeRange, updateTargets, updatePowerEnvelope, thermalFrequencyScale, whiteNoise, compressionResidual.

The source declares `SAMPLE_RATE = 48000.0`. Rate-sensitive coefficients and buffers are derived from this value unless the relevant helper explicitly states otherwise. Confirm host-rate behavior before changing it.

Implementation notes present in the DSP source:

- Voltage Modular processes audio at 48 kHz. All per-sample state is allocation-free.
- A broad low band opens into a narrower high band; this is an audition voicing.
- Up to 1.5% flat when cold, settling to calibrated pitch as the simulated circuitry warms.
- Unity below 5 V, continuous slope at the knee; high-range compression only.

Per-sample flow comments:

- FLT with a connected MOD IN is an audio filter. RND MOD blends white noise before the existing filter, while MOD LEVEL is its input attenuverter.
- Cable presence is authoritative, even with silence or a zero attenuverter.
- Two evaluations of the nonlinear residual; the clean path stays sample exact. Lightweight 2x midpoint approximation, not a full bandlimited oversampler.
- Log-like meter response so all four amplitude ranges produce useful visible movement.
- Output-stage overload indication.
- Lamp shows ordinary signal activity as well as overload. It retains a faint filament glow when powered but idle.

## Key DSP constants

These are read directly from the canonical source; edit the Java and embedded Designer source as one pair.

| Constant | Current source value |
| --- | --- |
| `SAMPLE_RATE` | `48000.0` |
| `CONTROL_SMOOTH` | `1.0 - Math.exp(-1.0 / (0.010 * SAMPLE_RATE))` |
| `METER_ATTACK` | `1.0 - Math.exp(-1.0 / (0.015 * SAMPLE_RATE))` |
| `METER_RELEASE` | `1.0 - Math.exp(-1.0 / (0.180 * SAMPLE_RATE))` |
| `POWER_WARMUP_STEP` | `1.0 / (2.8 * SAMPLE_RATE)` |
| `POWER_COOLDOWN_STEP` | `1.0 / (1.5 * SAMPLE_RATE)` |
| `LAMP_ATTACK` | `1.0 - Math.exp(-1.0 / (0.008 * SAMPLE_RATE))` |
| `LAMP_RELEASE` | `1.0 - Math.exp(-1.0 / (0.140 * SAMPLE_RATE))` |

## Bypass behavior

Relevant operations in the canonical `ProcessBypassedSample()` callback:

```java
resumePending = true;
meterDisplay = 0.0;
lampDisplay = 0.0;
mainOutput.SetValue(0.0);
```

Check bypass after any DSP edit: direct-signal modules should retain the established abrupt host bypass; generators and courtesy outputs should follow the collection convention for silence, state freeze, and active mult paths.

## Real-time and state-management notes

- `ProcessSample()` is called once per audio sample. Avoid allocation, file access, GUI updates, or unbounded loops in this callback.
- Keep persistent filter, oscillator, detector, random, counter, and delay state in fields or dedicated DSP classes; initialize/reinitialize it outside the hot callback where the current design allows.
- Smooth controls only where the audible behavior requires it. Do not smooth intentionally abrupt logic/clock edges or test-equipment behaviors without design approval.
- If this module has two or more independent paths, preserve their intended state independence and verify no accidental shared phase, detector, feedback, or random state.
- CPU cost has not been newly benchmarked by this notes pass. For filters, oversampling, convolution, delay networks, or high-rate event handling, compare host load with one and multiple instances before changing architecture.

## Change and verification checklist

1. Make edits in a development copy. Preserve the canonical files and their archived version folders.
2. Confirm control IDs, jack ordering, defaults, display names, notes/version text, and component ranges against the panel and user manual.
3. Check the `.vmod`'s embedded Java against the exported `.java` after edits; do not leave a mismatched pair.
4. Build in Voltage Module Designer and run the behavioral checks in the user manual/review record, including disconnected-jack, boundary, mode, and bypass behavior.
5. Listen for zippering, clicks, DC drift, clipping, instability, and expected character at low/nominal/hot levels. Profile CPU when DSP cost changes.
6. Update this note, user manual, README, release Notes/version, review record, and integrity manifest when canonical behavior changes.
7. Add useful audio fixtures to the standalone TestBench project and keep that project independent of the Voltage Modular source tree.

## Validation provenance

This note documents the canonical `2.0.0` source archive. The validation record and `SHA256.json` are the release-time references for test history and file integrity. This documentation pass does not rebuild or retest the canonized DSP and does not alter source files.
