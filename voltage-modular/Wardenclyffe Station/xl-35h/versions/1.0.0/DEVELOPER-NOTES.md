# XL-35h High Pass Filters v1.0.0 — Developer Notes

Collection: Wardenclyffe Station. Exact source pair: `XL35h.vmod` / `XL35h.java`; class `XL35h`; package `com.insectlabs.highpassfilters`. Designer Notes contain `v1.0.0`. See [User Manual](USER-MANUAL.md) and [Release Review](REVIEW.md).

## Design and signal flow

Two independent mono stages. The upper path is gently warm and the lower is cleaner. Frequencies and reference cues are in the manual. Both selectors default to their lowest step: upper 5.3 Hz, lower 16 Hz.

`ProcessSample()` reads bounded inputs, updates filter coefficients only when a selector step changes, runs both independent states, and writes outputs. `ProcessBypassedSample()` directly transfers each input to its corresponding output and freezes the filter histories. There is no custom state serialization. The first active sample after bypass adopts the current cutoff coefficients and clears stale filter histories once.

`HighPassSection` is a bilinear one-pole filter at 48 kHz. With cutoff `fc`, `g = tan(π fc / Fs)` and `a = (1−g)/(1+g)`. The high-pass uses b = 1/(1+g) and y[n] = b(x[n]−x[n−1]) + a y[n−1]. This gives a nominal -3 dB cutoff and about 6 dB/octave slope. Coefficients are recomputed only when the step changes; no manual-step smoothing is applied.

The upper output uses `warmMakeupStage(x) = x / (1 + 0.006 |x|)` after filtering. The lower output is the clean filter response. Unpatched inputs and non-finite samples resolve to zero; finite inputs are bounded before processing.

## Realtime notes and limits

The audio callback uses two scalar filter states and allocates no objects per sample. `Math.tan` is limited to a cutoff change. Input checks and the upper soft-saturation curve run each sample. CPU usage has not been benchmarked in Voltage Modular. The implementation assumes the module host's 48 kHz rate. Abrupt step changes retain state and may make a small transient.

## Validation and maintenance

`tests/FilterCoreTest.java` tests all 16 cutoff positions, DC behavior, out-of-band rejection, upper voicing, and bypass-resume reset behavior. Release records additionally cover strict SDK compilation and Designer source consistency. These tests do not render the native panel or measure host CPU. Repeat them after changing sample rate, cutoff tables, coefficient equations, state transitions, routing, or the upper-stage coefficient. Synchronize embedded and exported source after code edits and preserve control UUIDs and panel layout.
