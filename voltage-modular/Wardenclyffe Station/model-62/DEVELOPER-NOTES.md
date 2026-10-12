# Model 62 v2.0.0 â€” Developer Notes

Collection: Wardenclyffe Station. The exact release pair is `model62.vmod` / `model62.java`, class `model62`, package `com.insectlabs.model62`. The Designer Notes field contains `v2.0.0`. The exact candidate was approved and promoted on 2026-10-11. See the [User Manual](USER-MANUAL.md) for operation and [Release Review](REVIEW.md) for validation evidence.

## Design constraints and signal flow

Model 62 is mono, 24 kHz medium first, with sample-rate conversion at the 48 kHz Voltage Modular host rate. The medium is a fixed maximum-size signed-short wire array with a parallel byte scar map. Its maximum spool is 48 seconds; the six selectable lengths are 1, 1.618, 4, 7, 16, and 48 seconds. Two virtual heads are separated by 33 mm. Keep the difference between host samples and wire samples explicit in any transport changes.

The adapter reads and bounds connected inputs, synchronizes controls, calls `WireCore.tick`, and routes monitor, playback, and alarm outputs. `ProcessBypassedSample` is intentionally a direct split path and does not advance the core. `Notify` owns transport toggles, gates, power/mode changes and restoration; `finishRestore` stops/disarms without clearing medium. `drawDisplays` runs on the 33 ms GUI timer, not every audio sample. `GetStateInformation` and the variation callbacks use the validated wire serializer.

`WireCore.tick` converts requested speed through a 120 ms motor response, detects tension stress, updates wow/flutter and low-rate drift, advances reading/writing, and applies controlled fades. The latched Position 3 button defaults ON (High Fidelity) and selects War-Worn mode when OFF. War-Worn mode retains all baseline behavior in High Fidelity; its alternate write path adds drive and 2.5Ã— record noise, while playback adds subtle hiss, another restrained drive stage, 2Ã— wow and 4Ã— flutter depth. Modulation remains proportional to transport speed. Mode selection is an explicit momentary-button latch stored in custom patch state; wire wear already recorded is not removed when returning to High Fidelity. The record path filters for Nyquist, follows either the independent 1Ã— LOCKED writer or the signed ELASTIC transport, and writes every crossed wire cell. Medium retention/noise, bounded rational drive, quantization, and the 12 V recording ceiling occur at the write head. MONITOR is taken after input gain before medium processing. Playback applies physical head spacing, speed-aware 24-tap/256-phase windowed-sinc resampling across 16 cutoff bands, a 55 Hz high-pass and a two-pole 6.2 kHz low-pass, then output gain. A static lookup table is shared per class.

## Persistent medium and splice marks

One full 48-second signed-short wire uses 2,304,000 bytes; the byte scar map uses 1,152,000 bytes; page epochs and metadata add about 72 kB per instance. The shared resampler table is about 0.79 MB per loaded class. Save data stores the active spool only and serializes scars sparsely with format versioning and CRC32. Page epochs make spool replacement logically empty without clearing the full array on an audio callback. Snapshot copying is page-based to avoid holding the core lock across a multi-megabyte copy. It remains synchronized shared-state code, not a formal lock-free real-time guarantee.

A break records a persistent scar location. Splice stamps a localized mark in the byte map; the map survives overdubs and serialization. Playback treatment for v1.0.0 is `raw * (1 - .90 * scar) + .035 * scar + .006 * scar * noise()`, followed by the existing bounded output stage. It is intentionally subtle: a light localized reduction, transient and irregular contact noise; less prominent than the previous candidate. Preserve its coefficients unless a new listening pass is approved. Damage can overlap and is bounded. Spool changes create a new medium; changing the spool while moving creates a break on that new medium.

## Controls, gates, power and bypass

- SPEED is normalized âˆ’2..+2; the range switch selects Â±2Ã— or Â±4Ã—. CV adds Â±5 V over that range. Default is +1Ã— normal.
- MEMORY knob spans 0â€“1; patched 0â€“5 V replaces it. Input gain is 50â€“250%, unity at noon; output level is 0â€“200%.
- PLAY STOP gate toggles on a rising edge (2.5 V high, 1 V low). RECORD gate is level-sensitive; recording requires powered PLAY.
- LOCKED writes at +1Ã— independently. ELASTIC follows signed speed and freezes at zero.
- Power-off disarms and coasts down but keeps audio and scars. System BYPASS copies connected input directly to main and monitor, outputs zero alarm, skips control reads and all DSP, and freezes state. Resume rejects phantom gate edges.
- The magic eye tracks input after gain while recording is armed and input is patched; otherwise it tracks output. v1.0.0 retains the v0.1.6 15% target reduction.

Audio processing allocates no per-sample objects in the steady callback. The per-sample playback interpolator performs 24 multiply-accumulate taps plus filter and transport math. The large medium arrays are allocated once per module instance. The GUI timer handles canvas drawing separately. The included headless core run processed 10 seconds of active audio in 0.281 seconds on this machine; this is not a native-host CPU measurement.

## Validation and maintenance

`tests/validate.py` checks Designer binary round-trip, source synchronization, control names and display names, line anchors, SDK compilation of both source forms with Java 17 warnings as errors, and headless core/adapter behavior. `WireTests.txt` provides the test stub. It does not verify panel appearance, Voltage Modular patch save/reopen in the native host, listening judgement, or native-host CPU. See `REVIEW.md` for this releaseâ€™s actual results.

The former `testCode/` snippets include abandoned alternatives (cubic interpolation, simplistic transport physics, and illustrative splice logic). They are not runtime dependencies. The cleaned four-point cubic interpolator is separately archived in the shared DSP primitives library with tests and a note that it is not a band-limited resampler.

For future DSP edits, rerun the archive test script, compare the full source pair and Designer metadata, inspect bypass and restore behavior, and check all six spool lengths, positive/reverse/zero/high speeds, both recording modes, overlapping scars, and saved broken/repair states. Native listening remains the final sound-quality check.

## Regular 2500 button implementation — 2026-10-11

All four controls use regular momentary VoltageButtons with the standard 2500 Square Big skin: dark idle and lit pushed artwork. A press performs the action once; release only restores the indicator. `updateButtonLights()` calls `SetValueNoNotification(value, true)`: true requests the GUI update and NoNotification prevents recursive button actions. The GUI timer reconciles manual controls and external gates. PLAY follows running/coasting transport, REC follows arming/recording, SPLICE flashes for 1.2 seconds after a repair, and the mode lamp indicates High Fidelity independently of power. Mode changes remain available during bypass.

Do not derive mode from the momentary button's GetValue. Initialize and Reset explicitly choose High Fidelity. The custom state envelope is ASCII M62B, byte format version 1, byte mode (1 clean / 0 worn), followed by the unchanged WireCore payload. Legacy unwrapped wire states load in High Fidelity. Both preset and variation callbacks use this envelope; validation occurs before replacing any state. No sound-processing coefficients changed in this button correction.

`tests/InstalledButtonApiTest.java` exercises the installed SDK class with overridden GUI/notification callbacks, without creating a native panel. The headless adapter now models the GUI flag and mouse release rather than preloading a fictitious button default. Neither test proves native skin rendering: the user's Designer test is still required. Native VoltageToggle remains the preferred automatic-persistence choice for future mode controls; this explicitly latched regular-button pattern supports this panel's selected controls.

## Canonical approval

The user verified the regular 2500 button behavior in Designer and approved v1.1.0 as canonical on 2026-10-11. Working Designer Notes were moved here before release; only the module version remains in Designer Notes. The tested Java and audio DSP are unchanged by promotion.

## 2.0.0 display redesign

The GUI-only update touches the existing 33 ms timer and adds no audio callback work. `TransportDisplay` caches its backplate, flange and frame per bitmap size. Reels follow a smoothed visual phase, wire guides/head stay fixed, broken ends settle from a GUI clock, and splice marks are sampled read-only. The canvas omits SUPPLY, HEAD and TAKE-UP labels because the transport drawing already identifies those parts.

`MagicEyeDisplay` replaces the flat wedge with a shaded green phosphor fan. Its opening responds to the existing core meter value and dims when power is off or the module is bypassed. The meter source and response envelope in `WireCore` are unchanged.

The footer shows the physical playback position translated into current wall-clock time and the full loop duration at the current transport rate. While moving, timing uses the smoothed core speed, including external speed CV; while stopped, it uses the selected SPEED knob rate so the operator can preview duration. Reverse uses the absolute speed for duration while the position value follows the physical wire location. Rates at zero show an infinite loop length and no advancing time position. Durations under one hour use `mm:ss.t`; longer durations use `hh:mm:ss`. No DSP, state serialization, control metadata or panel dimensions changed.
