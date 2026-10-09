# Developer notes — sn-16u Universal Test Bench

**Canonical version:** `2.0.0`<br>
**Collection:** Wardenclyffe Station<br>
**Designer export:** `sn16u.java`<br>
**Designer project:** `sn16u.vmod`<br>
**Validation record:** REVIEW.md<br>

## Role and design contract

sn-16u is a manual laboratory test instrument for checking and calibrating pitch voltage, reference frequencies, audio level, frequency, and simple filtering. Reference signals are clean and stable; the instrument does not add the vintage coloration used by other Laboratory modules.

Treat the current manual and this version's `.vmod`/`.java` pair as the behavioral authority. The archive version is historical; make any new design changes in a separate development copy and promote only after the user's build and audition.

## Source map

- Canonical source folder: `sn-16u/versions/2.0.0`.
- User manual: [`USER-MANUAL.md`](versions/2.0.0/USER-MANUAL.md).
- Release review and integrity files remain in the canonical version folder. Do not edit archived `.vmod`, `.java`, panel image, or checksum files in place.
- Designer component inventory: AudioJack: 37, Button: 1, DigitalCounter: 6, Knob: 6, Label: 99, Switch: 7. Use stable component IDs in the `.vmod` as the UI-to-code contract; display text is user-facing and may be edited independently.

## DSP architecture

The active per-sample callback is `ProcessSample()` in `sn16u.java`. It delegates module-specific work to: finite, clamp, readInput, nextPhase, fineCents, cutoffHz, convertPitch, pitchHz, updateControls, beginSweep, processSweep, resetMeasurements, silenceSources, refreshDisplays.

Persistent DSP helper/state classes: `OnePole`, `WindowMeter`, `FrequencyMeter`, `NoiseSource`.

The source declares `SAMPLE_RATE = 48000.0`. Rate-sensitive coefficients and buffers are derived from this value unless the relevant helper explicitly states otherwise. Confirm host-rate behavior before changing it.

Implementation notes present in the DSP source:

- VM pitch convention: C0 = 0 V. User's Hz/V convention: C1 = 1 V.
- Endpoint fades do not alter the 42-second frequency trajectory.
- Change only the visual state; never feed sweep status back into the momentary trigger.
- GUI work stays off the audio path. Native counters handle the decimal point.
- Twenty 50 ms blocks: fixed memory and constant sample work, one-second observation window.
- Meter protection only; signal-through paths are not clipped.
- Rising 1 mV crossing, rearmed below 0 V; works with bipolar waves and positive pulse trains.
- Paul Kellett's refined pinking filter; see development README for attribution and limits.

## Key DSP constants

These are read directly from the canonical source; edit the Java and embedded Designer source as one pair.

| Constant | Current source value |
| --- | --- |
| `SAMPLE_RATE` | `48000.0` |
| `TWO_PI` | `2.0 * Math.PI` |
| `F_SHARP_RATIO` | `Math.pow(2.0, -0.25)` |
| `C0_HZ` | `16.351597831287414` |
| `CONTROL_SMOOTH` | `1.0 - Math.exp(-1.0 / (0.005 * SAMPLE_RATE))` |
| `SWEEP_SAMPLES` | `42 * 48000` |
| `SWEEP_START_HZ` | `0.01` |
| `SWEEP_END_HZ` | `SAMPLE_RATE * 0.495` |
| `SWEEP_RATIO` | `Math.pow(SWEEP_END_HZ / SWEEP_START_HZ, 1.0 / (SWEEP_SAMPLES - 1))` |
| `SWEEP_STEP` | `(SWEEP_END_HZ - SWEEP_START_HZ) / (SWEEP_SAMPLES - 1)` |

## Bypass behavior

Relevant operations in the canonical `ProcessBypassedSample()` callback:

```java
bypassed = true;
sweepRequested = false;
silenceSources();
double sum = readInput(addAInput) + readInput(addBInput);
positiveDcOutput.SetValue(sum); negativeDcOutput.SetValue(-sum);
highPassOutput.SetValue(readInput(highPassInput));
lowPassOutput.SetValue(readInput(lowPassInput));
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
