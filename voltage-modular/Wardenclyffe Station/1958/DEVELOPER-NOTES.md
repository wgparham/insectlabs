# Developer notes — 1958 Dual Waveform Generator

**Canonical version:** `2.0.0`<br>
**Collection:** Wardenclyffe Station<br>
**Designer export:** `nineteenfiftyeight.java`<br>
**Designer project:** `nineteenfiftyeight.vmod`<br>
**Validation record:** REVIEW.md<br>

## Role and design contract

The 1958 Dual Waveform Generator is a precise sine and variable-triangle test oscillator with manual frequency bands, internal or external FM, a separate 1 kHz reference, and a physical POWER switch. Its sound is intended to stay clear and useful for measurement-style patches, with restrained drift and compression at high output levels.

Treat the current manual and this version's `.vmod`/`.java` pair as the behavioral authority. The archive version is historical; make any new design changes in a separate development copy and promote only after the user's build and audition.

## Source map

- Canonical source folder: `1958/versions/2.0.0`.
- User manual: [`USER-MANUAL.md`](versions/2.0.0/USER-MANUAL.md).
- Release review and integrity files remain in the canonical version folder. Do not edit archived `.vmod`, `.java`, panel image, or checksum files in place.
- Designer component inventory: AnalogVUMeter: 1, AudioJack: 3, Knob: 7, LED: 1, Label: 27, Switch: 2. Use stable component IDs in the `.vmod` as the UI-to-code contract; display text is user-facing and may be edited independently.

## DSP architecture

The active per-sample callback is `ProcessSample()` in `nineteenfiftyeight.java`. It delegates module-specific work to: smoothMainFrequency, smoothDutyCycle, updatePowerGain, internalDepthScale, internalModulation, internalModulationRateHz, updateOutputMeter, resetGenerator, isPowered, mainFrequencyHz, scaleMultiplier, selectedWaveformMode, selectedModulationMode, selectedReferenceDestination, selectedWaveform, sine, triangle, variableTriangle, advancePhase, outputStage, frequencyScaleText, dutyLabel, modulationLabel, formatFrequency, formatOneDecimal, controlValueFromDisplay, readInput, clamp.

The source declares `SAMPLE_RATE = 48000.0`. Rate-sensitive coefficients and buffers are derived from this value unless the relevant helper explicitly states otherwise. Confirm host-rate behavior before changing it.

Implementation notes present in the DSP source:

- Control smoothing
- Vintage vacuum-tube / thermal emulation
- Mechanical VU meter ballistics
- Oscillator state
- Control smoothing
- Power / simulated filament
- Panel lamp follows the simulated filament state.
- Control interpretation
- As the simulated circuitry heats up, the cold oscillator begins slightly flat and settles toward its calibrated frequency.
- Output-stage saturation/compression
- Display helpers
- Typed-value conversion

## Key DSP constants

These are read directly from the canonical source; edit the Java and embedded Designer source as one pair.

| Constant | Current source value |
| --- | --- |
| `SAMPLE_RATE` | `48000.0` |
| `TWO_PI` | `Math.PI * 2.0` |
| `MINIMUM_FREQUENCY_HZ` | `0.01` |
| `MAXIMUM_FREQUENCY_HZ` | `18000.0` |
| `REFERENCE_FREQUENCY_HZ` | `1000.0` |
| `REFERENCE_OUTPUT_VOLTS` | `3.0` |
| `REFERENCE_MIX_VOLTS` | `1.0` |
| `MAXIMUM_OUTPUT_VOLTS` | `10.0` |
| `FM_INPUT_REFERENCE_VOLTS` | `5.0` |
| `INTERNAL_FM_DEPTH` | `0.18` |
| `INTERNAL_FM_DRIFT_HZ` | `0.018` |
| `WAVEFORM_SINE` | `1` |
| `WAVEFORM_TRIANGLE` | `2` |
| `MODULATION_OFF` | `1` |
| `MODULATION_INTERNAL_QUARTER` | `2` |
| `MODULATION_INTERNAL` | `3` |
| `MODULATION_EXTERNAL` | `4` |
| `REFERENCE_JACK` | `2` |
| `REFERENCE_OFF` | `1` |
| `REFERENCE_MIX` | `0` |
| `AMPLITUDE_SMOOTH_COEFFICIENT` | `1.0 - Math.exp(-1.0 / (SAMPLE_RATE * 0.010))` |
| `FREQUENCY_SMOOTH_COEFFICIENT` | `1.0 - Math.exp(-1.0 / (SAMPLE_RATE * 0.010))` |
| `DUTY_CYCLE_SMOOTH_COEFFICIENT` | `1.0 - Math.exp(-1.0 / (SAMPLE_RATE * 0.010))` |
| `POWER_WARMUP_STEP` | `1.0 / (SAMPLE_RATE * 1.8)` |
| `POWER_COOLDOWN_STEP` | `1.0 / (SAMPLE_RATE * 0.4)` |
| `NEEDLE_ATTACK_SPEED` | `0.04` |
| `NEEDLE_DECAY_SPEED` | `0.008` |

## Bypass behavior

Relevant operations in the canonical `ProcessBypassedSample()` callback:

```java
referenceOutput.SetValue(0.0);
mainOutput.SetValue(0.0);
outputMeter.SetValue(0.0);
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
