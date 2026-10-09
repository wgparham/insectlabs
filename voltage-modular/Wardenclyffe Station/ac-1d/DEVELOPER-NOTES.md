# Developer notes — AC/1D SQR/SIN Function Generator

**Canonical version:** `2.1.2`<br>
**Collection:** Wardenclyffe Station<br>
**Designer export:** `ac1d.java`<br>
**Designer project:** `ac1d.vmod`<br>
**Validation record:** REVIEW.md<br>

## Role and design contract

Function is a deliberately worn, hand-built laboratory oscillator inspired by the Heathkit AG-10 family. It contains two separate oscillator boards: a sine source and a square source. They share manual tuning controls, but their phases are independent and they are not phase-locked.

Treat the current manual and this version's `.vmod`/`.java` pair as the behavioral authority. The archive version is historical; make any new design changes in a separate development copy and promote only after the user's build and audition.

## Source map

- Canonical source folder: `ac-1d/versions/2.1.2`.
- User manual: [`USER-MANUAL.md`](versions/2.1.2/USER-MANUAL.md).
- Release review and integrity files remain in the canonical version folder. Do not edit archived `.vmod`, `.java`, panel image, or checksum files in place.
- Designer component inventory: AudioJack: 2, DigitalCounter: 1, Knob: 7, LED: 1, Label: 28, Switch: 1. Use stable component IDs in the `.vmod` as the UI-to-code contract; display text is user-facing and may be edited independently.

## DSP architecture

The active per-sample callback is `ProcessSample()` in `ac1d.java`. It delegates module-specific work to: resetFunctionGenerator, updatePowerGain, thermalFrequencyScale, selectedMultiplier, multiplierLabel, requestedBaseFrequencyHz, usableDialTravel, baseAtPosition, positionFromBase, positionFromOutputHz, effectiveBaseFrequencyHz, requestedDutyCycle, selectedAmplitude, selectedRangeVolts, rangeLabel, advancePhase, polyBlep, bandLimitedPulse, voicedSine, outputStage, displayFrequencyHz, formatHz.

The source declares `SAMPLE_RATE = 48000.0`. Rate-sensitive coefficients and buffers are derived from this value unless the relevant helper explicitly states otherwise. Confirm host-rate behavior before changing it.

Implementation notes present in the DSP source:

- Up to 1.5% flat when cold, settling to calibrated pitch as the simulated circuitry reaches temperature.
- The custom taper keeps the existing panel's 0.4177 default exactly at C4 on X10.
- Preserve the accepted low-range taper; expand the usable part of capped ranges to a full turn.

Per-sample flow comments:

- Independent oscillator boards share tuning but never reset each other's phase.

## Key DSP constants

These are read directly from the canonical source; edit the Java and embedded Designer source as one pair.

| Constant | Current source value |
| --- | --- |
| `SAMPLE_RATE` | `48000.0` |
| `TWO_PI` | `Math.PI * 2.0` |
| `C4_HZ` | `261.625565` |
| `DEFAULT_FREQUENCY_POSITION` | `0.4177` |
| `DEFAULT_BASE_HZ` | `C4_HZ / 10.0` |
| `MINIMUM_BASE_HZ` | `0.1` |
| `MAXIMUM_BASE_HZ` | `100.0` |
| `MAXIMUM_FREQUENCY_HZ` | `23760.0` |
| `POWER_WARMUP_STEP` | `1.0 / (SAMPLE_RATE * 2.3)` |
| `POWER_COOLDOWN_STEP` | `1.0 / (SAMPLE_RATE * 1.3)` |
| `FREQUENCY_SMOOTH` | `1.0 - Math.exp(-1.0 / (SAMPLE_RATE * 0.010))` |
| `AMPLITUDE_SMOOTH` | `1.0 - Math.exp(-1.0 / (SAMPLE_RATE * 0.008))` |
| `DUTY_SMOOTH` | `1.0 - Math.exp(-1.0 / (SAMPLE_RATE * 0.006))` |

## Bypass behavior

Relevant operations in the canonical `ProcessBypassedSample()` callback:

```java
squareOutput.SetValue(0.0);
sineOutput.SetValue(0.0);
bypassed = true;
resumePending = true;
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

This note documents the canonical `2.1.2` source archive. The validation record and `SHA256.json` are the release-time references for test history and file integrity. This documentation pass does not rebuild or retest the canonized DSP and does not alter source files.
