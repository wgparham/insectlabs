# Developer notes — 1919/ln Tone Burst Generator

**Canonical version:** `2.0.1`<br>
**Collection:** Wardenclyffe Station<br>
**Designer export:** `nineteennineteen.java`<br>
**Designer project:** `nineteennineteen.vmod`<br>
**Validation record:** REVIEW.md<br>

## Role and design contract

1919/ln alternates two live input signals through independently counted gates. A clock tick advances the active count; when that count reaches zero, the other nonzero stage opens. Use it to make repeatable tone bursts, alternate sections of audio, or move between different sources in time. Inputs keep running while closed, so a reopened input resumes from its current playback position.

Treat the current manual and this version's `.vmod`/`.java` pair as the behavioral authority. The archive version is historical; make any new design changes in a separate development copy and promote only after the user's build and audition.

## Source map

- Canonical source folder: `1919-ln/versions/2.0.1`.
- User manual: [`USER-MANUAL.md`](versions/2.0.1/USER-MANUAL.md).
- Release review and integrity files remain in the canonical version folder. Do not edit archived `.vmod`, `.java`, panel image, or checksum files in place.
- Designer component inventory: AudioJack: 8, Button: 2, DigitalCounter: 4, Knob: 10, LED: 2, Label: 47, Switch: 6. Use stable component IDs in the `.vmod` as the UI-to-code contract; display text is user-facing and may be edited independently.

## DSP architecture

The active per-sample callback is `ProcessSample()` in `nineteennineteen.java`. It delegates module-specific work to: readSetCount, digitValue, updateCounterDisplay, updateActiveIndicators, internalClockPositionForHz, readAudioInput, advanceInternalClock, internalRampValue, startCycle, restartCurrentSide, advanceSequencer, selectSide, finishCycle, setGateTargets, shortestActiveCount, fadeSamples, advanceGateFades, internalFrequency, clampCount, resetRatio, dividerForPosition, multiplierForPosition, clockRatio, ratioLabel.

The source declares `SAMPLE_RATE = 48000.0`. Rate-sensitive coefficients and buffers are derived from this value unless the relevant helper explicitly states otherwise. Confirm host-rate behavior before changing it.

Implementation notes present in the DSP source:

- Keep initialization in the user-owned region: Designer regenerates the constructor.
- Activity LEDs
- Counter helpers
- Activity indicators
- Typed clock-frequency conversion
- Burst generator core
- Direct frequency scaling needs no edge acquisition, even at the slowest range.
- External clock
- Ratio mapping
- Panel ÷1.333 denotes the exact 4/3 divisor.

Per-sample flow comments:

- Latch brief activity so high-rate bursts remain visible.
- 100 Hz visual refresh; counting remains sample accurate.

## Key DSP constants

These are read directly from the canonical source; edit the Java and embedded Designer source as one pair.

| Constant | Current source value |
| --- | --- |
| `SAMPLE_RATE` | `48000.0` |
| `ACTIVE_LED_DECAY_PER_REFRESH` | `0.72` |
| `GATE_HIGH_VOLTS` | `5.0` |
| `MAX_FADE_SAMPLES` | `24` |
| `MAX_FADE_PHASE_FRACTION` | `0.04` |
| `FAST_CLOCK_FADE_CUTOFF_HZ` | `1000.0` |
| `LOW_THRESHOLD` | `0.1` |
| `HIGH_THRESHOLD` | `1.0` |

## Bypass behavior

Relevant operations in the canonical `ProcessBypassedSample()` callback:

```java
engagePresses.set(0);
resetPresses.set(0);
double dryX = readAudioInput(this.inputX);
double dryY = readAudioInput(this.inputY);
burstGenCore.processBypassed(
dryX,
dryY
);
outputZ.SetValue(dryX + dryY);
outputX.SetValue(dryX);
outputY.SetValue(dryY);
clockCourtesyOutput.SetValue(0.0);
rampCourtesyOutput.SetValue(0.0);
xActivitySeen = false;
yActivitySeen = false;
xLedDisplay = 0.0;
// … additional bypass operations omitted; see the canonical Java source for the full callback.
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

This note documents the canonical `2.0.1` source archive. The validation record and `SHA256.json` are the release-time references for test history and file integrity. This documentation pass does not rebuild or retest the canonized DSP and does not alter source files.
