# Model 62 v2.0.0 release review

Scope: GUI-only reel, wire-transport and magic-eye canvas update with read-only snapshots of existing transport values. Previous canonical release: v1.1.0. Audio/DSP callbacks, saved state, controls, panel geometry, artwork and audio behavior remain unchanged. Designer Notes: v2.0.0.

Approved: the user confirmed the exact v2.0.0 candidate after native build, run and visual testing on 2026-10-11.

Passed: both embedded and exported Java compile for Java 17 against the installed Voltage SDK, with all warnings treated as errors; Designer project round-trip and embedded/exported source match; Notes and control metadata; existing core tests for sample-rate conversion, delays, reverse, power, compression, breaks, scars, persistence, corruption rejection and gate restore; actual-callback checks for routing, bypass freeze, monitor, buttons, mode lamps, reset and saved/legacy restore; renderer checks for stopped/forward/reverse states, resizing, broken wire, power-off, varispeed-scaled position and duration, minute/hour formatting, and magic-eye response. All six rendered previews were reviewed.

CPU scope: canvas work runs on the existing 33 ms GUI timer. Static flanges and backplate are cached per bitmap size. No audio-callback work was added. Native host CPU profiling was not performed; the user’s native build and listening check passed.

The footer reports loop position and duration at effective transport speed, including speed changes, and while stopped uses the selected speed. The eye follows the existing meter signal. The transport image retains the existing panel artwork and behavior.

Release archive: `versions/2.0.0/`. Previous 1.0.0 and 1.1.0 archives are preserved.
