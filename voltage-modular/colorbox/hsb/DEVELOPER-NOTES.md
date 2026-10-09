# Developer notes — hsb

**Canonical version:** `1.0.2`<br>
**Collection:** Colorbox<br>
**Designer export:** `hsb_1-0-2.java`<br>
**Designer project:** `hsb_1-0-2.vmod`<br>
**Validation record:** [collection VALIDATION.md](../VALIDATION.md)<br>

## Role and design contract

hsb combines two independent processors, each following HUE → SATURATION → BRILLIANCE. From gentle thickening to dense fuzz and unruly distortion, its colors emerge from three simple controls working together.

Treat the current manual and this version's `.vmod`/`.java` pair as the behavioral authority. The archive version is historical; make any new design changes in a separate development copy and promote only after the user's build and audition.

## Source map

- Canonical source folder: `hsb/versions/1.0.2`.
- User manual: [`USER-MANUAL.md`](USER-MANUAL.md).
- Release review and integrity files remain in the canonical version folder. Do not edit archived `.vmod`, `.java`, panel image, or checksum files in place.
- Designer component inventory: AudioJack: 4, Knob: 6, Label: 2, Switch: 2. Use stable component IDs in the `.vmod` as the UI-to-code contract; display text is user-facing and may be edited independently.

## DSP architecture

The active per-sample callback is `ProcessSample()` in `hsb_1-0-2.java`. It delegates module-specific work to: readHsbControls, processHsb, process, reset, approach, safe, unit, setControls, coefficients, makeFir.

Persistent DSP helper/state classes: `HsbDsp`, `Stage`, `Tilt`, `Feedback`, `Lowpass`, `Dc`, `Fir`.

Oversampling-related source constants: `OS = 2`. Check the interpolation/decimation routines and bypass path together when changing oversampling.

Implementation notes present in the DSP source:

- HSB 1.0.2 - insect laboratories. Colorbox: 2x processing; direct host bypass preserves routing and skips DSP. HSB 1.0.2 - insect laboratories. Locked DSP, 2x oversampling. Mono-first parallel normals, not a serial audio cascade.
- Direct bypass is for CPU relief: do not read knobs or advance any DSP.
- Seed current panel targets and clear stale histories once on re-entry.
- Linear 5 ms ramps have exact endpoints. Both channels keep running.
- Preserve v0.1 through 70%; add up to 9 dB per cell above that. Smoothstep has zero slope at the junction and maximum.
- Two asymmetric gain stages, AC coupling and signal-dependent bias. Rectification increasingly replaces the interstage feed: octave/intermodulation.
- Phase zero yields the documented integer 32-sample FIR delay.
- Same filter duration and 21 kHz cutoff at either experimental rate. 4x: 129 taps at 192 kHz; 2x: 65 taps at 96 kHz. Pair delay: 32 host samples.

## Key DSP constants

These are read directly from the canonical source; edit the Java and embedded Designer source as one pair.

| Constant | Current source value |
| --- | --- |
| `OS` | `2` |
| `RATE` | `SAMPLE_RATE * OS` |
| `ENV_ATTACK` | `1 - Math.exp(-1 / (.002 * RATE))` |
| `ENV_RELEASE` | `1 - Math.exp(-1 / (.045 * RATE))` |
| `COUPLING` | `1 - Math.exp(-2 * Math.PI * 18 / RATE)` |

## Bypass behavior

Relevant operations in the canonical `ProcessBypassedSample()` callback:

```java
processHsb(true);
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

This note documents the canonical `1.0.2` source archive. The validation record and `SHA256.json` are the release-time references for test history and file integrity. This documentation pass does not rebuild or retest the canonized DSP and does not alter source files.
