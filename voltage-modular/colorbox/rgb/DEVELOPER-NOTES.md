# Developer notes — rgb

**Canonical version:** `4.0.3`<br>
**Collection:** Colorbox<br>
**Designer export:** `rgb_4-0-3.java`<br>
**Designer project:** `rgb_4-0-3.vmod`<br>
**Validation record:** [collection VALIDATION.md](../VALIDATION.md)<br>

## Role and design contract

rgb combines three distinct waveshaping stages, each with its own drive control. RED brings firmer, more aggressive saturation; GREEN is rounder and more gradual; BLUE becomes increasingly asymmetric as drive rises, adding an uneven, gritty edge. Together they range from gentle harmonic thickening to dense, layered distortion.

Treat the current manual and this version's `.vmod`/`.java` pair as the behavioral authority. The archive version is historical; make any new design changes in a separate development copy and promote only after the user's build and audition.

## Source map

- Canonical source folder: `rgb/versions/4.0.3`.
- User manual: [`USER-MANUAL.md`](USER-MANUAL.md).
- Release review and integrity files remain in the canonical version folder. Do not edit archived `.vmod`, `.java`, panel image, or checksum files in place.
- Designer component inventory: AudioJack: 6, Knob: 3, Label: 2, Switch: 1. Use stable component IDs in the `.vmod` as the UI-to-code contract; display text is user-facing and may be edited independently.

## DSP architecture

The active per-sample callback is `ProcessSample()` in `rgb_4-0-3.java`. It delegates module-specific work to: driveCompensation, blueShape, dcBlockRed, dcBlockGreen, dcBlockBlue, resetRedDc, resetGreenDc, resetBlueDc, process, reset, resetDsp.

Persistent DSP helper/state classes: `HalfBand19`.

Implementation notes present in the DSP source:

- RGB 4.0.3 - insect laboratories. Colorbox: 2x processing; direct host bypass preserves routing and skips DSP.
- RGB V4 - CONSTANTS
- Voltage Modular processes at 48 kHz. rgb performs nonlinear processing internally at 96 kHz.
- Approximately the same ~3.8 Hz DC-blocking corner as the original 48 kHz coefficient, adjusted for 96 kHz.
- Approximately 10 ms parameter smoothing at 48 kHz.
- Automatic output restraint. Deliberately not full level matching.
- COLOR VOICING
- RED = TANH: firmer and more aggressive.
- GREEN = ATAN: rounder and more gradual.
- BLUE = exponential: symmetric at Drive 1 and increasingly asymmetric toward 10.
- Drive runs from 1 to 10, giving a span of 9.
- PARAMETER STATE
- DC BLOCKER STATE
- DRIVE COMPENSATION

Per-sample flow comments:

- RGB V4 - TRIPLE OVERSAMPLED WAVESHAPER BLUE  = drive-dependent asymmetric exponential Voltage Modular I/O: 48 kHz Internal nonlinear processing: 96 kHz
- RESET FILTER STATE ON RETURN FROM TRUE BYPASS
- PARAMETER SMOOTHING
- Cache jack and switch states once per native sample.
- Internal 96 kHz signal pairs. A = first oversampled phase B = second oversampled phase
- GREEN IN overrides the internally normalled RED signal.
- RED is already at 96 kHz.
- BLUE - ASYMMETRIC EXPONENTIAL BLUE IN overrides the internally normalled GREEN signal.
- GREEN is already at 96 kHz.
- DRIVE-DEPENDENT BLUE ASYMMETRY positive rate = 1.00 negative rate = 1.00 positive rate = 0.80 negative rate = 1.40 Square-root progression brings the asymmetry in quickly without over-coloring the minimum setting.

## Key DSP constants

These are read directly from the canonical source; edit the Java and embedded Designer source as one pair.

| Constant | Current source value |
| --- | --- |
| `NOMINAL_VOLTAGE` | `5.0` |
| `TWO_OVER_PI` | `0.6366197723675814` |
| `DC_POLE_2X` | `0.9997499687421851` |
| `CONTROL_SMOOTH` | `0.002` |
| `DRIVE_COMPENSATION_FACTOR` | `0.10` |
| `RED_DRIVE_SCALE` | `1.25` |
| `GREEN_DRIVE_SCALE` | `0.70` |
| `BLUE_POS_RATE_MIN` | `1.00` |
| `BLUE_POS_RATE_MAX` | `0.80` |
| `BLUE_NEG_RATE_MIN` | `1.00` |
| `BLUE_NEG_RATE_MAX` | `1.40` |
| `BLUE_DRIVE_SPAN` | `9.0` |
| `C0` | `0.021277660466073` |
| `C2` | `-0.027992303277934` |
| `C4` | `0.049537687283950` |
| `C6` | `-0.095789590456413` |
| `C8` | `0.308510413777763` |
| `C9` | `0.488912264413122` |

## Bypass behavior

Relevant operations in the canonical `ProcessBypassedSample()` callback:

```java
double bypassRed = redIn.GetValue();
redOut.SetValue(bypassRed);
double bypassGreen;
if (greenIn.IsConnected()) {
bypassGreen = greenIn.GetValue();
} else {
bypassGreen = bypassRed;
}
greenOut.SetValue(bypassGreen);
double bypassBlue;
if (blueIn.IsConnected()) {
bypassBlue = blueIn.GetValue();
} else {
bypassBlue = bypassGreen;
}
blueOut.SetValue(bypassBlue);
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

This note documents the canonical `4.0.3` source archive. The validation record and `SHA256.json` are the release-time references for test history and file integrity. This documentation pass does not rebuild or retest the canonized DSP and does not alter source files.
