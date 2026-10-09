# Developer notes — sh.7437 Dual Slew Processor

**Canonical version:** `2.0.0`<br>
**Collection:** Wardenclyffe Station<br>
**Designer export:** `shDot7437.java`<br>
**Designer project:** `shDot7437.vmod`<br>
**Validation record:** REVIEW.md<br>

## Role and design contract

sh.7437 combines independent positive and negative slew limiters with patchable pulse feedback. It processes DC control voltages and audio, and creates triggered or repeating contours.

Treat the current manual and this version's `.vmod`/`.java` pair as the behavioral authority. The archive version is historical; make any new design changes in a separate development copy and promote only after the user's build and audition.

## Source map

- Canonical source folder: `sh-7437/versions/2.0.0`.
- User manual: [`USER-MANUAL.md`](versions/2.0.0/USER-MANUAL.md).
- Release review and integrity files remain in the canonical version folder. Do not edit archived `.vmod`, `.java`, panel image, or checksum files in place.
- Designer component inventory: AudioJack: 11, Knob: 4, LED: 3, Label: 25, Switch: 3. Use stable component IDs in the `.vmod` as the UI-to-code contract; display text is user-facing and may be edited independently.

## DSP architecture

The active per-sample callback is `ProcessSample()` in `shDot7437.java`. It delegates module-specific work to: readInput, updatePulseIndicators.

Implementation notes present in the DSP source:

- Voltage Modular's engine runs at 48 kHz. The core accepts a rate for numerical checks. No sample-rate API from the early sketch is assumed to exist in the SDK.
- Defaults target the nearest attainable native-sample loop period with a one-sample cable delay. One sample holds the peak/reset transition; no interpolated or hidden oscillator is used.
- Busy edges are consumed, never queued. SUSTAIN alone can start a full rise.
- Raising the voltage range while held still obeys the positive slew limit.
- Emit a real reset sample before returning to ordinary input following.
- Fully independent input: no normal, crossfeed, rectification or voltage-range scaling.
- CV moves within the selected range; the FAST/SLOW factor stays exactly 1000.

## Key DSP constants

These are read directly from the canonical source; edit the Java and embedded Designer source as one pair.

| Constant | Current source value |
| --- | --- |
| `FAST_MIN_SECONDS` | `0.0001` |
| `FAST_MAX_SECONDS` | `4.0` |
| `RANGE_FACTOR` | `1000.0` |
| `LOG_SPAN` | `Math.log(40000.0)` |
| `LOG_TWO` | `Math.log(2.0)` |
| `PULSE_VOLTS` | `5.0` |
| `LOW_ON_VOLTS` | `0.001` |
| `LOW_OFF_VOLTS` | `0.002` |
| `GATE_ON_VOLTS` | `1.0` |
| `GATE_OFF_VOLTS` | `0.5` |
| `IDLE` | `0` |
| `RISING` | `1` |
| `AT_PEAK` | `2` |
| `HOLDING` | `3` |

## Bypass behavior

Relevant operations in the canonical `ProcessBypassedSample()` callback:

```java
positiveOutput.SetValue(readInput(positiveInput));
negativeOutput.SetValue(readInput(negativeInput));
positiveHighOutput.SetValue(0.0);
positiveLowOutput.SetValue(0.0);
negativePulseOutput.SetValue(0.0);
updatePulseIndicators(false, false, false);
sherlockCore.resumePending = true;
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
