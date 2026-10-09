# 21-24eq v1.0.1 Developer Notes

**Collection:** Wardenclyffe Station  
**Sources:** `21-24eq.vmod` and `21-24eq.java` in this archive  
**User documentation:** [USER-MANUAL.md](USER-MANUAL.md)  
**Validation record:** [REVIEW.md](REVIEW.md)

## Behavior and signal flow

The module is mono. `ProcessSample()` reads INPUT, substitutes zero for an unpatched or nonfinite input, and clamps the finite input to ±1,000,000 V. A one-pole 10 Hz high-pass precedes symmetrical soft drive. Drive is linear through 6 V, then approaches an 18 V additional asymptote through `tanh`. The signal passes through a low shelf and a high shelf, receives fixed −20 dB insertion loss, then passes through a symmetrical safety stage with a 20 V knee and 24 V asymptote.

Bass changes only the low-shelf gain target; Treble changes only the high-shelf gain target. The source knob range is −1 to +1. Each maps to the shelf's internal 0-to-1 direction so −1 is cut, 0 is the passive-voiced center, and +1 is boost. Relative to the fixed shelf contours, Bass adds up to +10 dB / −29 dB and Treble adds up to +12 dB / −24 dB. The center therefore retains +8 dB bass and +5 dB treble shelf contours before the fixed insertion loss.

`switchFrequency` has three integer positions. The DSP maps them to bass/treble corners of 70 Hz / 7 kHz, 35 Hz / 3.5 kHz, and 17.5 Hz / 1.75 kHz. Knob targets and corner transitions smooth over 5 ms. The switch default and serialized initial state are position 0 (Reference).

## State, bypass, and reset

The host stores the knob and switch state. `GetStateInformation()` returns no custom state. `Initialize()`, preset/variation load completion, and every bypassed sample request a DSP reset. The next active sample clears filter and smoothing history, then seeds the current control values and selected profile immediately. `ProcessBypassedSample()` is a direct mono transfer and does not advance the DSP core.

## Source map and real-time notes

- `InitializeControls()` declares the panel components and their ranges, defaults, positions and skins.
- `GetTooltipText()` explains control direction, passive midpoint behavior, profile corner frequencies, and jack routing.
- `ProcessSample()` is the audio callback; `readInput()`, `clamp()`, `softDrive()`, and `safetyCeiling()` provide input and nonlinear bounds.
- `PassiveToneCore.process()` implements the input high-pass, two one-pole shelf stages, control smoothing and fixed loss. `reset()` clears its histories.

The audio callback allocates no objects. It computes shelf coefficients and the input sample path at the fixed 48 kHz design rate. Native-host CPU, allocation, automation, and undo profiling have not been measured. `Math.tanh()` is used only above the drive threshold; the shelf coefficient calculations use exponentials per sample and may merit profiling before optimizing.

## Maintenance checks

After DSP edits, repeat knob-direction and cross-band checks, all three profile response checks, bypass/resume and preset/variation reset checks, nonfinite and extreme input checks, and source/project round-trip. Recheck the module Notes and switch initial state when exporting from Designer. This design is a passive-voiced approximation; do not silently flatten its insertion loss or midpoint contour.

The exact source-pair agreement, Java target compilation, headless checks, and remaining validation limits are recorded in [REVIEW.md](REVIEW.md).
