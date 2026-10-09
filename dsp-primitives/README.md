# InsectLabs DSP primitives

SDK-independent reusable audio building blocks, preserved when module development makes them useful. Released modules retain their own approved embedded copies.

## HalfBand19

[Source](src/com/insectlabs/dsp/filters/HalfBand19.java): the established Colorbox 19-tap linear-phase half-band FIR, reused by RM1010's first 2× DSP pass. Six multiplications per filter tick; preallocated doubled ring buffer; no process-time allocation. Nine filter-rate samples of group delay. For 2× interpolation, process the source sample and then zero, scaling both outputs by two. For decimation, process both phases and retain the second result. Use separate filter instances for each direction/channel. Reset clears histories.

Provenance: unchanged coefficients/arithmetic from Colorbox RGB 4.0.2, extracted from the RM1010 embedded copy on 2026-10-08. The repository license applies. This is not a perfect brick-wall anti-alias filter; preserve the approved performance/CPU compromise when reusing it.

## CubicHermite

[Source](src/com/insectlabs/dsp/interpolation/CubicHermite.java): a stateless, allocation-free four-point cubic interpolator using Catmull-Rom tangent estimates and Horner evaluation. It interpolates between the middle samples, `s1` at fraction 0 and `s2` at fraction 1. Derived from the abandoned user-provided `testCode/cubicHermite.java` sketch and archived with the Model 62 release tests as provenance.

This is a low-cost interpolation primitive, not an anti-aliasing resampler. For pitch reduction or playback-rate conversion, pair it with a suitable band-limit filter. Model 62 uses a windowed-sinc table instead; it does not use this primitive in its approved audio path.

## Validation

Run `python tests/validate.py` with Java 17 for half-band impulse/symmetry, gain, reset and independence checks plus cubic interpolation endpoints, constants, and linear ramps.
