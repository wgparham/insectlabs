# Developer notes — cmyk

**Canonical version:** `1.0.3`<br>
**Collection:** Colorbox<br>
**Designer export:** `cmyk_1-0-3.java`<br>
**Designer project:** `cmyk_1-0-3.vmod`<br>
**Validation record:** [collection VALIDATION.md](../VALIDATION.md)<br>

## Role and design contract

cmyk combines four wavefolding stages with bipolar input levels and CV control over folding depth. Two alternating folder shapes bring warm, rounded harmonics and brighter, more angular textures, ranging from subtle coloration to dense, complex distortion.

Treat the current manual and this version's `.vmod`/`.java` pair as the behavioral authority. The archive version is historical; make any new design changes in a separate development copy and promote only after the user's build and audition.

## Source map

- Canonical source folder: `cmyk/versions/1.0.3`.
- User manual: [`USER-MANUAL.md`](USER-MANUAL.md).
- Release review and integrity files remain in the canonical version folder. Do not edit archived `.vmod`, `.java`, panel image, or checksum files in place.
- Designer component inventory: AudioJack: 12, Knob: 8, Label: 2, Switch: 1. Use stable component IDs in the `.vmod` as the UI-to-code contract; display text is user-facing and may be edited independently.

## DSP architecture

The active per-sample callback is `ProcessSample()` in `cmyk_1-0-3.java`. It delegates module-specific work to: process, get, reset, smoothControl, clamp01, getDepth, folderA, folderB, dcBlockCyan, dcBlockMagenta, dcBlockYellow, dcBlockBlack, resetDsp.

Persistent DSP helper/state classes: `HalfBand19`.

Implementation notes present in the DSP source:

- CMYK 1.0.3 - insect laboratories. Colorbox: 2x processing; direct host bypass preserves routing and skips DSP. CMYK 1.0.3 - CONSTANTS
- Voltage Modular nominal signal reference.
- Manual-control smoothing. Approximately 8 ms at Voltage Modular's 48 kHz rate.
- 96 kHz DC-blocking pole. Approximately the same ~3.8 Hz corner used by rgb.
- 19-TAP HALF-BAND FIR COEFFICIENTS
- 2X HALF-BAND FILTER Used for audio/CV interpolation and physical-output The filter implementation is intentionally unchanged from CMYK 1.0.1; only naming and encapsulation have been cleaned.
- True once the filter has processed any non-reset state. Allows reset() to skip unnecessary buffer clearing.
- If the filter is already empty, there is nothing to do.
- AUDIO INPUT INTERPOLATORS
- CV INPUT INTERPOLATORS These permit audio-rate folding-depth modulation.
- PHYSICAL OUTPUT DECIMATORS Internal normalled signals remain at 96 kHz between stages.
- SMOOTHED MANUAL CONTROL STATE
- CONTROL HELPERS
- Unpatched CV is internally +5 V. Patched CV modulates around depth 0.5.

Per-sample flow comments:

- Four-stage oversampled wavefolder: MAGENTA  = Folder A YELLOW   = Folder B BLACK    = Folder A Normalled cascade: CYAN -> MAGENTA -> YELLOW -> BLACK Voltage Modular I/O: 48 kHz Internal processing: 96 kHz
- SMOOTH MANUAL CONTROLS
- Cache switch and jack connection states once per host sample.
- UPSAMPLE EXTERNAL AUDIO INPUTS
- UPSAMPLE EXTERNAL CV Unpatched CV uses the locked internal +5 V normal. We still advance the interpolation filter with zeros so stale CV history drains while the jack is disconnected.
- DC history intentionally advances even while DC BLOCK is OFF.
- MAGENTA - FOLDER A
- YELLOW - FOLDER B
- BLACK - FOLDER A
- MAGENTA - FOLDER A

## Key DSP constants

These are read directly from the canonical source; edit the Java and embedded Designer source as one pair.

| Constant | Current source value |
| --- | --- |
| `NOMINAL_VOLTAGE` | `5.0` |
| `CONTROL_SMOOTH` | `0.0026` |
| `DC_POLE_2X` | `0.9997499687421851` |
| `HB_C0` | `0.021277660466073` |
| `HB_C2` | `-0.027992303277934` |
| `HB_C4` | `0.049537687283950` |
| `HB_C6` | `-0.095789590456413` |
| `HB_C8` | `0.308510413777763` |
| `HB_C9` | `0.488912264413122` |

## Bypass behavior

Relevant operations in the canonical `ProcessBypassedSample()` callback:

```java
double cyanBypass = cyanIn.GetValue();
cyanOut.SetValue(cyanBypass);
double magentaBypass;
if (magentaIn.IsConnected()) {
magentaBypass = magentaIn.GetValue();
} else {
magentaBypass = cyanBypass;
}
magentaOut.SetValue(magentaBypass);
double yellowBypass;
if (yellowIn.IsConnected()) {
yellowBypass = yellowIn.GetValue();
} else {
yellowBypass = magentaBypass;
}
yellowOut.SetValue(yellowBypass);
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

This note documents the canonical `1.0.3` source archive. The validation record and `SHA256.json` are the release-time references for test history and file integrity. This documentation pass does not rebuild or retest the canonized DSP and does not alter source files.
