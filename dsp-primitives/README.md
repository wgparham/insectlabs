# InsectLabs DSP primitives

SDK-independent reusable audio building blocks, preserved when module development makes them useful. Released modules retain their own approved embedded copies.

## HalfBand19

[Source](src/com/insectlabs/dsp/filters/HalfBand19.java): the established Colorbox 19-tap linear-phase half-band FIR, reused by RM1010's first 2× DSP pass. Six multiplications per filter tick; preallocated doubled ring buffer; no process-time allocation. Nine filter-rate samples of group delay. For 2× interpolation, process the source sample and then zero, scaling both outputs by two. For decimation, process both phases and retain the second result. Use separate filter instances for each direction/channel. Reset clears histories.

Provenance: unchanged coefficients/arithmetic from Colorbox RGB 4.0.2, extracted from the RM1010 embedded copy on 2026-10-08. The repository license applies. This is not a perfect brick-wall anti-alias filter; preserve the approved performance/CPU compromise when reusing it.

Run `python tests/validate.py` with Java 17 for impulse/symmetry, gain, reset and independence checks. The existing Colorbox/RM1010 module tests exercise this exact filter in their signal paths. Additional reusable DSP belongs here with provenance, documentation and meaningful tests; the separate reverb-primitives project contains the reverb engine.
