# Developer notes — SW1 Switch

**Canonical version:** `2.0.0`<br>
**Collection:** Wardenclyffe Station<br>
**Designer export:** `sw1.java`<br>
**Designer project:** `sw1.vmod`<br>
**Validation record:** REVIEW.md<br>

## Role and design contract

SW1 is a manual 2×2 relay router. It has two inputs and two outputs. The normal position connects I1 to O1 and I2 to O2; the alternate position swaps those paths. The T/G control changes how the illuminated relay button selects the alternate position.

Treat the current manual and this version's `.vmod`/`.java` pair as the behavioral authority. The archive version is historical; make any new design changes in a separate development copy and promote only after the user's build and audition.

## Source map

- Canonical source folder: `sw1/versions/2.0.0`.
- User manual: [`USER-MANUAL.md`](versions/2.0.0/USER-MANUAL.md).
- Release review and integrity files remain in the canonical version folder. Do not edit archived `.vmod`, `.java`, panel image, or checksum files in place.
- Designer component inventory: AudioJack: 4, Button: 1, Knob: 1, LED: 4, Label: 8, Switch: 1. Use stable component IDs in the `.vmod` as the UI-to-code contract; display text is user-facing and may be edited independently.

## DSP architecture

The active per-sample callback is `ProcessSample()` in `sw1.java`. It delegates module-specific work to: readInput, isGateMode, updateIndicators, setButtonPressed, setGateMode, reset, restore, isAlternate, process, processBypassed, getOutputTop, getOutputBottom.

Persistent DSP helper/state classes: `Switch1Core`, `OnePole`, `RelayContact`.

The source declares `SAMPLE_RATE = 48000.0`. Rate-sensitive coefficients and buffers are derived from this value unless the relevant helper explicitly states otherwise. Confirm host-rate behavior before changing it.

Implementation notes present in the DSP source:

- Gentle 6 dB/octave contact conditioning: attenuates relay-edge hash without dulling audio.
- A short, fixed relay settling time removes the route-change discontinuity.

## Key DSP constants

These are read directly from the canonical source; edit the Java and embedded Designer source as one pair.

| Constant | Current source value |
| --- | --- |
| `SAMPLE_RATE` | `48000.0` |
| `GATE_VOLTS` | `5.0` |
| `CLICK_FILTER_CUTOFF_HZ` | `1800.0` |
| `RELAY_SETTLE_SAMPLES` | `96` |

## Bypass behavior

Relevant operations in the canonical `ProcessBypassedSample()` callback:

```java
switchCore.processBypassed(readInput(inputTop), readInput(inputBottom));
outputTop.SetValue(switchCore.getOutputTop());
outputBottom.SetValue(switchCore.getOutputBottom());
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
