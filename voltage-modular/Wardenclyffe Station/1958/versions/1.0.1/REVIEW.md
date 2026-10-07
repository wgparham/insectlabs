# Generator 1.0.1 release review

Status: **canonical; approved by the user on 2026-09-30**.
Baseline: immutable [1.0.0](../1.0.0/README.md). Approved artifacts are in this directory;
`SHA256.json` identifies the pair and artwork. 1.0.1 is canonical; 1.0.0 is retained unchanged.

## Completed cleanup

- Renamed all Designer controls, labels and jacks to descriptive internal/variable names. Display Names
  are now human readable in source and Designer metadata; for example, “Duty Cycle” and “Main Output”. Updated embedded/exported
  source and control metadata together. The latest user-supplied project is the panel baseline, including
  its Oscillators category and regenerated skin registration. UUIDs, artwork, ranges, defaults, placement,
  saved test state and unrelated metadata are preserved against that latest baseline.
- Renamed smoothing coefficients, reference/modulation phase fields and selection helpers consistently.
- Expanded compact helper methods, simplified the empty notification switch and clarified FM routing.
- Corrected obsolete Designer notes for modulation, duty shaping and reference routing.
- Reviewed every functional tooltip. Low-frequency values now retain two decimal places; 0.05 Hz
  displays correctly. AMPLITUDE is labeled as requested pre-compression voltage.
- Added typed-value conversion: frequency in Hz within the selected band; amplitude in volts;
  triangle rise time in percent; internal rate in Hz; external attenuation in percent. Discrete controls
  show their position numbers. Non-finite typed values are ignored; SDK edit/undo path is retained.

## Intentional DSP adjustment

- Added 10 ms DUTY CYCLE smoothing before variable-triangle generation. Initial/saved duty is adopted
  directly; subsequent moves glide. It tracks while powered, including in SIN mode, and freezes during
  power-off/host bypass. Sine generation remains independent of duty. Other DSP is unchanged.

## Validation evidence

- Both exported and embedded Java compile against `C:/ProgramData/Voltage/voltage.jar` with Java 17,
  UTF-8, `-Xlint:all -Werror`.
- 1,000,000 samples of the actual callbacks match 1.0.0 when its duty input is supplied by an independently
  calculated 10 ms smoothing response. Both outputs, meter and other DSP histories agree bit-for-bit.
  Raw moving-duty output intentionally differs from 1.0.0. Startup, bounded/monotonic duty response,
  settling, frozen state in bypass/power-off, SIN independence and unit entry are checked.
  The revised suite passes 11,000,045 comparison and unit-entry checks.
- Source/project agreement, artifact hashes, editor anchor bounds, descriptive identifiers, unchanged
  artwork, control UUIDs, ranges/defaults and saved Designer metadata pass.
- Repeat from the repository root:

```powershell
python voltage-modular/Wardenclyffe Station/1958/tests/validate_cleanup.py --repo . --candidate voltage-modular/Wardenclyffe Station/1958/versions/1.0.1
```

The harness simulates controls and jacks and executes the actual Java DSP bodies. It does not simulate
native Designer rendering, native undo or saved-patch migration. The million-sample result is a regression
test, not a CPU benchmark or proof of every possible input.

## Infrastructure review and retained exceptions

- Source-module bypass has no signal-through path: it silences outputs and freezes oscillator/smoothing
  histories, with no knob or CV reads. Power-off is independent of host bypass and also freezes state.
  The accepted 1.0.0 resume policy continues the stored phase and smoothing histories. It deliberately
  differs from the generic processor instruction to snap controls/reset histories on resumption.
- Manual frequency, amplitude and now duty-cycle smoothing use 10 ms responses. Internal modulation
  rate and selector changes retain immediate behavior. External FM remains unsmoothed.
- Audio callbacks remain allocation-free. The existing native-rate waveform/output-stage design is retained;
  no oversampling or new band-limiting is introduced during cleanup. Aliasing is not quantitatively
  characterized by this pass. Existing per-sample powers/sines remain unchanged.
- Meter averaging is 150 ms with active display updates at 100 Hz. As in 1.0.0, the meter is explicitly
  zeroed during each power-off/bypass callback. This documented indicator update is the exception to
  the general instruction to avoid UI work in audio callbacks; no new CPU claim is made.
- Reset and preset callbacks retain the baseline behavior. Numeric ranges/defaults and Designer UUIDs
  are preserved. Native host behavior is covered by the user’s final approval; individual UI actions were not independently observed.
- No new audio fixture was needed; this cleanup uses deterministic generated comparison signals.

## Final approval and archive

The user confirmed: “The current files are good and canonical.” This approves the final pair,
including duty smoothing, readable Display Names and Oscillators category. A final Designer save
changed only Java skin registration plus project bookkeeping; the accepted DSP was retained.
Individual native undo/save-reload actions were not independently observed by the automated harness.

The exact approved source pair and hero image are archived here with fresh checksums. See
[Module-Release-Checklist.md](../../../../docs/Module-Release-Checklist.md) for future releases.
