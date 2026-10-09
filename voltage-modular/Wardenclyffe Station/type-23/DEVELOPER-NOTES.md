# Developer notes — Type 23 Signal Processor

**Canonical version:** `2.0.0`<br>
**Collection:** Wardenclyffe Station<br>
**Designer export:** `type23.java`<br>
**Designer project:** `type23.vmod`<br>
**Validation record:** REVIEW.md<br>

## Role and design contract

The Type 23 Signal Processor is a dual, mono-first utility. Its left and right stages are independent: each has its own audio input and output, gain, offset, external control input, external level control, and PROC/VCA selector. Use the stages as separate processors or as a dual-mono pair; there is no stereo link between them.

Treat the current manual and this version's `.vmod`/`.java` pair as the behavioral authority. The archive version is historical; make any new design changes in a separate development copy and promote only after the user's build and audition.

## Source map

- Canonical source folder: `type-23/versions/2.0.0`.
- User manual: [`USER-MANUAL.md`](versions/2.0.0/USER-MANUAL.md).
- Release review and integrity files remain in the canonical version folder. Do not edit archived `.vmod`, `.java`, panel image, or checksum files in place.
- Designer component inventory: AudioJack: 6, Knob: 8, Label: 18. Use stable component IDs in the `.vmod` as the UI-to-code contract; display text is user-facing and may be edited independently.

## DSP architecture

The active per-sample callback is `ProcessSample()` in `type23.java`. It delegates module-specific work to: readInput, tooltipValue, gainTooltip, modeTooltip, setLeftControls, setRightControls, process, processBypassed, getLeftOutput, getRightOutput, setControls, snap, roundedVcaGain, processCharacter, processCharacterStep, characterCurve, fullCharacterCurve, setTarget, next, finiteOrZero, clamp.

Persistent DSP helper/state classes: `SignalProcessorDsp`, `ProcessorStage`, `ControlRamp`.

The source declares `SAMPLE_RATE = 48000.0`. Rate-sensitive coefficients and buffers are derived from this value unless the relevant helper explicitly states otherwise. Confirm host-rate behavior before changing it.

Oversampling-related source constants: `OVERSAMPLE_FACTOR = 2.0`. Check the interpolation/decimation routines and bypass path together when changing oversampling.

Implementation notes present in the DSP source:

- Signal Processor 1.0.0 - insect laboratories. Two independent mono stages; 2x character processing and direct host bypass.

Per-sample flow comments:

- Control reads stay on the audio thread, following the Colorbox baseline.

## Key DSP constants

These are read directly from the canonical source; edit the Java and embedded Designer source as one pair.

| Constant | Current source value |
| --- | --- |
| `SAMPLE_RATE` | `48000.0` |
| `CV_REFERENCE_VOLTS` | `5.0` |
| `MANUAL_SMOOTH_SECONDS` | `0.005` |
| `VACTROL_ATTACK_SECONDS` | `0.003` |
| `VACTROL_RELEASE_SECONDS` | `0.030` |
| `OVERSAMPLE_FACTOR` | `2.0` |
| `NEGATIVE_PROC_CHARACTER` | `0.35` |
| `CHARACTER_KNEE_VOLTS` | `3.5` |
| `CHARACTER_DRIVE_START_VOLTS` | `3.0` |
| `CHARACTER_DRIVE_SPAN_VOLTS` | `6.0` |
| `CLEAN_CUTOFF_HZ` | `30000.0` |
| `DRIVEN_CUTOFF_HZ` | `16000.0` |
| `POSITIVE_COMPRESSION` | `0.10` |
| `NEGATIVE_COMPRESSION` | `0.08` |

## Bypass behavior

Relevant operations in the canonical `ProcessBypassedSample()` callback:

```java
signalProcessor.processBypassed(readInput(leftInput), readInput(rightInput));
leftOutput.SetValue(signalProcessor.getLeftOutput());
rightOutput.SetValue(signalProcessor.getRightOutput());
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
