# Developer notes — n01 Noise Generator

**Canonical version:** `2.0.0`<br>
**Collection:** Wardenclyffe Station<br>
**Designer export:** `n01.java`<br>
**Designer project:** `n01.vmod`<br>
**Validation record:** REVIEW.md<br>

## Role and design contract

n01 is a manual noise and random-voltage source. It provides independent broadband WHITE noise, a RED/BLUE-controlled colored SPECTRA output, continuous SLOW RANDOM voltage, a continuous ramp-shaped S&H SOURCE, and a manually or externally triggered STEPPED output. There is no pink-noise jack and no internal sample clock.

Treat the current manual and this version's `.vmod`/`.java` pair as the behavioral authority. The archive version is historical; make any new design changes in a separate development copy and promote only after the user's build and audition.

## Source map

- Canonical source folder: `n01/versions/2.0.0`.
- User manual: [`USER-MANUAL.md`](versions/2.0.0/USER-MANUAL.md).
- Release review and integrity files remain in the canonical version folder. Do not edit archived `.vmod`, `.java`, panel image, or checksum files in place.
- Designer component inventory: AudioJack: 6, Button: 1, Knob: 4, LED: 2, Label: 14. Use stable component IDs in the `.vmod` as the UI-to-code contract; display text is user-facing and may be edited independently.

## DSP architecture

The active per-sample callback is `ProcessSample()` in `n01.java`. It delegates module-specific work to: clamp, coefficient, rateHz, smooth, scramble, noiseSample, rampRandom, warmLimit, updateLamps.

The source declares `SAMPLE_RATE = 48000.0`. Rate-sensitive coefficients and buffers are derived from this value unless the relevant helper explicitly states otherwise. Confirm host-rate behavior before changing it.

Implementation notes present in the DSP source:

- SplitMix64 finalizer; two halves of each word form one triangular sample. The two instances use separate states; no allocations or trigonometry here.
- Gentle symmetric compression, zero preserving and strictly bounded.

Per-sample flow comments:

- RMS compensation for two low-pass poles, independent of LEVEL.
- Broad warm component plus independently adjustable brighter content.
- Continuous filtered noise: RATE is a bandwidth, never an S&H clock.
- Knobs gently pull a continuous ramp; LEVEL changes timing variation only.

## Key DSP constants

These are read directly from the canonical source; edit the Java and embedded Designer source as one pair.

| Constant | Current source value |
| --- | --- |
| `SAMPLE_RATE` | `48000.0` |
| `CONTROL_SMOOTH` | `1.0 - Math.exp(-1.0 / (0.010 * SAMPLE_RATE))` |
| `WHITE_COEFFICIENT` | `coefficient(4200.0)` |
| `RED_COEFFICIENT` | `coefficient(70.0)` |
| `MID_COEFFICIENT` | `coefficient(250.0)` |
| `UPPER_COEFFICIENT` | `coefficient(1000.0)` |
| `BLUE_COEFFICIENT` | `coefficient(3200.0)` |
| `SEED_STEP` | `0x9e3779b97f4a7c15L` |

## Bypass behavior

Relevant operations in the canonical `ProcessBypassedSample()` callback:

```java
bypassActive = true;
resumePending = true;
suppressTriggerOnResume = true;
manualRequests.set(0);
randomMeter = 0.0;
whiteOutput.SetValue(0.0);
spectraOutput.SetValue(0.0);
slowRandomOutput.SetValue(0.0);
sampleSourceOutput.SetValue(0.0);
steppedOutput.SetValue(0.0);
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
