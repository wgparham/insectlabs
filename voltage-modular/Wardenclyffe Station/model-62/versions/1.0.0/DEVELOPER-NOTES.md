# Model 62 v1.0.0 — Developer Notes

Collection: Wardenclyffe Station. The exact release pair is `model62.vmod` / `model62.java`, class `model62`, package `com.insectlabs.model62`. The Designer Notes field contains only `v1.0.0`. See the [User Manual](USER-MANUAL.md) for operation and [Release Review](REVIEW.md) for validation evidence.

## Design constraints and signal flow

Model 62 is mono, 24 kHz medium first, with sample-rate conversion at the 48 kHz Voltage Modular host rate. The medium is a fixed maximum-size signed-short wire array with a parallel byte scar map. Its maximum spool is 48 seconds; the six selectable lengths are 1, 1.618, 4, 7, 16, and 48 seconds. Two virtual heads are separated by 33 mm. Keep the difference between host samples and wire samples explicit in any transport changes.

The adapter reads and bounds connected inputs, synchronizes controls, calls `WireCore.tick`, and routes monitor, playback, and alarm outputs. `ProcessBypassedSample` is intentionally a direct split path and does not advance the core. `Notify` owns transport toggles, gates, power/mode changes and restoration; `finishRestore` stops/disarms without clearing medium. `drawDisplays` runs on the 33 ms GUI timer, not every audio sample. `GetStateInformation` and the variation callbacks use the validated wire serializer.

`WireCore.tick` converts requested speed through a 120 ms motor response, detects tension stress, updates wow/flutter and low-rate drift, advances reading/writing, and applies controlled fades. The record path filters for Nyquist, follows either the independent 1× LOCKED writer or the signed ELASTIC transport, and writes every crossed wire cell. Medium retention/noise, bounded rational drive, quantization, and the 12 V recording ceiling occur at the write head. MONITOR is taken after input gain before medium processing. Playback applies physical head spacing, speed-aware 24-tap/256-phase windowed-sinc resampling across 16 cutoff bands, a 55 Hz high-pass and a two-pole 6.2 kHz low-pass, then output gain. A static lookup table is shared per class.

## Persistent medium and splice marks

One full 48-second signed-short wire uses 2,304,000 bytes; the byte scar map uses 1,152,000 bytes; page epochs and metadata add about 72 kB per instance. The shared resampler table is about 0.79 MB per loaded class. Save data stores the active spool only and serializes scars sparsely with format versioning and CRC32. Page epochs make spool replacement logically empty without clearing the full array on an audio callback. Snapshot copying is page-based to avoid holding the core lock across a multi-megabyte copy. It remains synchronized shared-state code, not a formal lock-free real-time guarantee.

A break records a persistent scar location. Splice stamps a localized mark in the byte map; the map survives overdubs and serialization. Playback treatment for v1.0.0 is `raw * (1 - .90 * scar) + .035 * scar + .006 * scar * noise()`, followed by the existing bounded output stage. It is intentionally subtle: a light localized reduction, transient and irregular contact noise; less prominent than the previous candidate. Preserve its coefficients unless a new listening pass is approved. Damage can overlap and is bounded. Spool changes create a new medium; changing the spool while moving creates a break on that new medium.

## Controls, gates, power and bypass

- SPEED is normalized −2..+2; the range switch selects ±2× or ±4×. CV adds ±5 V over that range. Default is +1× normal.
- MEMORY knob spans 0–1; patched 0–5 V replaces it. Input gain is 50–250%, unity at noon; output level is 0–200%.
- PLAY STOP gate toggles on a rising edge (2.5 V high, 1 V low). RECORD gate is level-sensitive; recording requires powered PLAY.
- LOCKED writes at +1× independently. ELASTIC follows signed speed and freezes at zero.
- Power-off disarms and coasts down but keeps audio and scars. System BYPASS copies connected input directly to main and monitor, outputs zero alarm, skips control reads and all DSP, and freezes state. Resume rejects phantom gate edges.
- The magic eye tracks input after gain while recording is armed and input is patched; otherwise it tracks output. v1.0.0 retains the v0.1.6 15% target reduction.

Audio processing allocates no per-sample objects in the steady callback. The per-sample playback interpolator performs 24 multiply-accumulate taps plus filter and transport math. The large medium arrays are allocated once per module instance. The GUI timer handles canvas drawing separately. The included headless core run processed 10 seconds of active audio in 0.281 seconds on this machine; this is not a native-host CPU measurement.

## Validation and maintenance

`tests/validate.py` checks Designer binary round-trip, source synchronization, control names and display names, line anchors, SDK compilation of both source forms with Java 17 warnings as errors, and headless core/adapter behavior. `WireTests.txt` provides the test stub. It does not verify panel appearance, Voltage Modular patch save/reopen in the native host, listening judgement, or native-host CPU. See `REVIEW.md` for this release’s actual results.

The former `testCode/` snippets include abandoned alternatives (cubic interpolation, simplistic transport physics, and illustrative splice logic). They are not runtime dependencies. The cleaned four-point cubic interpolator is separately archived in the shared DSP primitives library with tests and a note that it is not a band-limited resampler.

For future DSP edits, rerun the archive test script, compare the full source pair and Designer metadata, inspect bypass and restore behavior, and check all six spool lengths, positive/reverse/zero/high speeds, both recording modes, overlapping scars, and saved broken/repair states. Native listening remains the final sound-quality check.
