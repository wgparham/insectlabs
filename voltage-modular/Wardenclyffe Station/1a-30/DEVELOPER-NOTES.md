# Developer notes — 1A-30 Deep Tone Generator / Modulator

**Canonical version:** `2.0.0`<br>
**Collection:** Wardenclyffe Station<br>
**Designer export:** `deeptone.java`<br>
**Designer project:** `deeptone.vmod`<br>
**Validation record:** REVIEW.md<br>

## Role and design contract

Deep Tone combines a sine oscillator, an independently started Beat oscillator, an external audio input, shaped amplitude movement, and an always-on Courtesy waveform. TONE, BEAT, EXTERNAL and SWELL are function buttons; they start or add functions but do not power the machine down. Main AMPLITUDE initializes at silence.

Treat the current manual and this version's `.vmod`/`.java` pair as the behavioral authority. The archive version is historical; make any new design changes in a separate development copy and promote only after the user's build and audition.

## Source map

- Canonical source folder: `1a-30/versions/2.0.0`.
- User manual: [`USER-MANUAL.md`](versions/2.0.0/USER-MANUAL.md).
- Release review and integrity files remain in the canonical version folder. Do not edit archived `.vmod`, `.java`, panel image, or checksum files in place.
- Designer component inventory: AudioJack: 3, Button: 4, DigitalCounter: 2, Knob: 8, Label: 60, Switch: 2. Use stable component IDs in the `.vmod` as the UI-to-code contract; display text is user-facing and may be edited independently.

## DSP architecture

The active per-sample callback is `ProcessSample()` in `deeptone.java`. It delegates module-specific work to: clamp, offsetHz, smooth, baseFrequency, multiplier, advance, edgeCorrection, shapedWave, compressionResidual, functionBit, updatePanel.

The source declares `SAMPLE_RATE = 48000.0`. Rate-sensitive coefficients and buffers are derived from this value unless the relevant helper explicitly states otherwise. Confirm host-rate behavior before changing it.

Implementation notes present in the DSP source:

- Smooth only ramp/saw resets, preserving the triangle and the rest of each slope. Total reset spans: 6 ms for Swell, 2 ms for Courtesy and 1 ms for audible Beat.
- Broaden the existing polynomial reset; cap it at a quarter of a cycle so faster settings retain a distinct ramp. No whole-signal low-pass filter.
- Carry at FRACTION=1 so the two counters still describe the summed base.

Per-sample flow comments:

- Courtesy is always active. Zero offset holds the phase and its current voltage.
- Above 100%, deepen the muted portion without reversing polarity.
- Lightweight 2x midpoint conditioning with averaging at the output. Both evaluations stay within the soft output rails, including sudden external transients.

## Key DSP constants

These are read directly from the canonical source; edit the Java and embedded Designer source as one pair.

| Constant | Current source value |
| --- | --- |
| `SAMPLE_RATE` | `48000.0` |
| `TWO_PI` | `2.0 * Math.PI` |
| `CONTROL_SMOOTH` | `1.0 - Math.exp(-1.0 / (0.010 * SAMPLE_RATE))` |
| `SWELL_EDGE_HALF_SAMPLES` | `0.003 * SAMPLE_RATE` |
| `COURTESY_EDGE_HALF_SAMPLES` | `0.001 * SAMPLE_RATE` |
| `BEAT_EDGE_HALF_SAMPLES` | `0.0005 * SAMPLE_RATE` |
| `EXTERNAL` | `1` |
| `TONE` | `2` |
| `BEAT` | `4` |
| `SWELL` | `8` |

## Bypass behavior

Relevant operations in the canonical `ProcessBypassedSample()` callback:

```java
resumePending = true;
double input = 0.0;
if ((enabledFunctions & EXTERNAL) != 0 && externalInput.IsConnected()) {
input = externalInput.GetValue();
}
mainOutput.SetValue(input);
courtesyOutput.SetValue(0.0);
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
