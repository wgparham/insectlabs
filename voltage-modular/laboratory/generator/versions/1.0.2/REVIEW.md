# Release review — Generator 1.0.2

## Scope

- Added a physical POWER control fade using a 384-sample (8 ms at 48 kHz) linear gain ramp.
- Kept the existing host bypass behavior: immediate silent outputs and no DSP/state updates.
- Updated the Designer Notes field from release-candidate `v1.0.2rc` to release `v1.0.2`.

## Review result

The new gain state is initialized and reset safely, fades both the main and reference outputs, and stops source processing after a completed power-down fade. No frequency, waveform, FM, compression, duty-cycle, meter, routing or reference-tone behavior was changed outside the power transition.

The user built, loaded and passed the final release candidate before promotion.
