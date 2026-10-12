# Release notes — 21-24eq v1.0.1

First canonized Wardenclyffe Station release of the frequency corrector. This build synchronizes the three-position switch and passive-voiced DSP, corrects the control direction to counterclockwise cut / clockwise boost, adds reset-on-resume behavior after bypass and preset/variation loads, bounds nonfinite controls, and documents the control profiles in tooltips.

An earlier 1.0.0 development candidate was not canonized or archived. See [REVIEW.md](REVIEW.md) for validation and known limitations.


## Published identity correction

Cherry Audio required a Java class rename after a server-side publication error. The active published class is `tttf` in package `com.insectlabs.tttf`; the user has rechecked this set and confirmed it is the correct current v1.0.1 release, tested and published. The user confirms this set was tested and published. DSP, panel behavior and design are unchanged.
