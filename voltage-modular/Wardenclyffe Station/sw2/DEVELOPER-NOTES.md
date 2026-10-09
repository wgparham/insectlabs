# Developer notes — SW2 Switch / Distributer

**Canonical version:** `2.0.0`<br>
**Collection:** Wardenclyffe Station<br>
**Designer export:** `sw2.java`<br>
**Designer project:** `sw2.vmod`<br>
**Validation record:** REVIEW.md<br>

## Role and design contract

SW2 is a manual rotary selector/distributor with two independent routing banks and one shared five-position ROUTE control. The upper bank selects one of four source inputs for X. The lower bank sends a common input Y to one of four destination outputs.

Treat the current manual and this version's `.vmod`/`.java` pair as the behavioral authority. The archive version is historical; make any new design changes in a separate development copy and promote only after the user's build and audition.

## Source map

- Canonical source folder: `sw2/versions/2.0.0`.
- User manual: [`USER-MANUAL.md`](versions/2.0.0/USER-MANUAL.md).
- Release review and integrity files remain in the canonical version folder. Do not edit archived `.vmod`, `.java`, panel image, or checksum files in place.
- Designer component inventory: AudioJack: 10, Knob: 1, Label: 16, Switch: 1. Use stable component IDs in the `.vmod` as the UI-to-code contract; display text is user-facing and may be edited independently.

## DSP architecture

The active per-sample callback is `ProcessSample()` in `sw2.java`. It delegates module-specific work to: readInput, selectedPosition, clickFilterEnabled, resetRouting, processRouting, processBypassedRouting, updateRouteTransition, sourceAt, filterSelected, filterDestination, amplifierCurve, processAmplifier, resetFilterHistories.

The source declares `SAMPLE_RATE = 48000.0`. Rate-sensitive coefficients and buffers are derived from this value unless the relevant helper explicitly states otherwise. Confirm host-rate behavior before changing it.

Implementation notes present in the DSP source:

- Slightly more open rotary-contact voice than sw1; unity DC and settling stay intact.
- Two identical amplifier stages: X after source selection, Y before distribution. 2x midpoint processing follows SIGPROC; coefficients are computed only once.
- OFF has no amplifier residue once the routing transition finishes.

## Key DSP constants

These are read directly from the canonical source; edit the Java and embedded Designer source as one pair.

| Constant | Current source value |
| --- | --- |
| `SAMPLE_RATE` | `48000.0` |
| `CLICK_FILTER_CUTOFF_HZ` | `2200.0` |
| `RELAY_SETTLE_SAMPLES` | `1024` |
| `AMPLIFIER_KNEE_VOLTS` | `3.0` |
| `AMPLIFIER_CUTOFF_HZ` | `14000.0` |
| `AMPLIFIER_KNEE_WIDTH` | `4.0` |
| `AMPLIFIER_POSITIVE_AMOUNT` | `0.30` |
| `AMPLIFIER_NEGATIVE_AMOUNT` | `0.33` |

## Bypass behavior

Relevant operations in the canonical `ProcessBypassedSample()` callback:

```java
processBypassedRouting(
cachedPosition,
cachedPosition == 1 ? readInput(sourceInput1) : 0.0, cachedPosition == 2 ? readInput(sourceInput2) : 0.0,
cachedPosition == 3 ? readInput(sourceInput3) : 0.0, cachedPosition == 4 ? readInput(sourceInput4) : 0.0,
cachedPosition == 0 ? 0.0 : readInput(commonInput));
selectedOutputJack.SetValue(selectedOutput);
destinationOutput1.SetValue(destinationOutputs[0]);
destinationOutput2.SetValue(destinationOutputs[1]);
destinationOutput3.SetValue(destinationOutputs[2]);
destinationOutput4.SetValue(destinationOutputs[3]);
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
