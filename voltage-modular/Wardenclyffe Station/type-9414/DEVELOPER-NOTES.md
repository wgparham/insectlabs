# Developer notes — Type 9414 Frequency Analyzer

**Canonical version:** `2.0.0`<br>
**Collection:** Wardenclyffe Station<br>
**Designer export:** `type9414.java`<br>
**Designer project:** `type9414.vmod`<br>
**Validation record:** REVIEW.md<br>

## Role and design contract

Type 9414 Frequency Analyzer is a mono selective amplifier. MAIN isolates a tunable band of frequencies; C rejects the same band. Input gain feeds both filters, while AMP sets only MAIN's output level. The clean filter core and gently limited output stages suit isolating partials, finding resonances, removing a narrow tone, or extracting ringing textures from complex sound.

Treat the current manual and this version's `.vmod`/`.java` pair as the behavioral authority. The archive version is historical; make any new design changes in a separate development copy and promote only after the user's build and audition.

## Source map

- Canonical source folder: `type-9414/versions/2.0.0`.
- User manual: [`USER-MANUAL.md`](versions/2.0.0/USER-MANUAL.md).
- Release review and integrity files remain in the canonical version folder. Do not edit archived `.vmod`, `.java`, panel image, or checksum files in place.
- Designer component inventory: AudioJack: 5, Knob: 4, LED: 2, Label: 15, Slider: 2, Switch: 1. Use stable component IDs in the `.vmod` as the UI-to-code contract; display text is user-facing and may be edited independently.

## DSP architecture

The active per-sample callback is `ProcessSample()` in `type9414.java`. It delegates module-specific work to: readInput, clamp, amplitudeDb, frequencyHz, widthHz, shown, updateIndicators, reset, smooth, process, ceiling, setOrder, clear, clearIfNeeded, configure, processBandpass, processBandreject.

Persistent DSP helper/state classes: `SelectiveCore`, `FilterBank`, `Section`.

The source declares `SAMPLE_RATE = 48000.0`. Rate-sensitive coefficients and buffers are derived from this value unless the relevant helper explicitly states otherwise. Confirm host-rate behavior before changing it.

Implementation notes present in the DSP source:

- Selective Service v1.0.0: canonical release, 48 kHz VM engine.
- Expensive control mappings run only when their source control changes.
- Prevent pathological upstream numeric values from poisoning filter state.
- CV is intentionally unsmoothed and read at the full sample rate.
- OFF is a passive S-through-C path. The ceiling remains; GAIN does not.

## Key DSP constants

These are read directly from the canonical source; edit the Java and embedded Designer source as one pair.

| Constant | Current source value |
| --- | --- |
| `SAMPLE_RATE` | `48000.0` |
| `MIN_GAIN_DB` | `-24.0` |
| `MAX_GAIN_DB` | `24.0` |
| `MIN_AMP_DB` | `-24.0` |
| `MAX_AMP_DB` | `36.0` |
| `MAIN_KNEE_VOLTS` | `20.0` |
| `MAIN_CEILING_VOLTS` | `24.0` |
| `COURTESY_KNEE_VOLTS` | `16.0` |
| `COURTESY_CEILING_VOLTS` | `20.0` |

## Bypass behavior

Relevant operations in the canonical `ProcessBypassedSample()` callback:

```java
double dry = readInput(signalInput);
mainOutput.SetValue(dry);
courtesyOutput.SetValue(dry);
resumePending = true;
bypassed = true;
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
