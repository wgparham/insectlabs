# InsectLabs DSP primitives

SDK-independent reusable audio building blocks, preserved when module development makes them useful. Released modules retain their own approved embedded copies.

## HalfBand19

[Source](src/com/insectlabs/dsp/filters/HalfBand19.java): the established Colorbox 19-tap linear-phase half-band FIR, reused by RM1010's first 2× DSP pass. Six multiplications per filter tick; preallocated doubled ring buffer; no process-time allocation. Nine filter-rate samples of group delay. For 2× interpolation, process the source sample and then zero, scaling both outputs by two. For decimation, process both phases and retain the second result. Use separate filter instances for each direction/channel. Reset clears histories.

Provenance: unchanged coefficients/arithmetic from Colorbox RGB 4.0.2, extracted from the RM1010 embedded copy on 2026-10-08. The repository license applies. This is not a perfect brick-wall anti-alias filter; preserve the approved performance/CPU compromise when reusing it.

## CubicHermite

[Source](src/com/insectlabs/dsp/interpolation/CubicHermite.java): a stateless, allocation-free four-point cubic interpolator using Catmull-Rom tangent estimates and Horner evaluation. It interpolates between the middle samples, `s1` at fraction 0 and `s2` at fraction 1. Derived from the abandoned user-provided `testCode/cubicHermite.java` sketch and archived with the Model 62 release tests as provenance.

This is a low-cost interpolation primitive, not an anti-aliasing resampler. For pitch reduction or playback-rate conversion, pair it with a suitable band-limit filter. Model 62 uses a windowed-sinc table instead; it does not use this primitive in its approved audio path.

## EmsSvf96

[Source](src/com/insectlabs/dsp/filters/EmsSvf96.java): reusable 96 kHz two-integrator nonlinear SVF core extracted from Teletape Workshop SVF 1.0.0. It returns simultaneous circuit-scaled HIGH, BAND and LOW taps. The caller supplies samples at 96 kHz and owns input gain, routing and resampling. Call `configure(frequencyHz, response)` with finite `0 < frequencyHz < 48000`; response is clamped to 0–1. Call `process(input)` for each sample, then read the three taps. `reset()` clears integrator state, oscillation-seed state and taps. Invalid frequency throws `IllegalArgumentException`; nonfinite input is converted to zero.

The arithmetic is bit-identical to the approved embedded core. The `tanh` OTA law, response mapping, amplitude-dependent loop loss, oscillation seed and output stage are software voicing choices, not a component-level reconstruction or measured EMS behavior. Use this primitive when that specific nonlinear response is wanted; a general linear SVF should use a simpler module-specific core.

## GaloisLfsr

[Source](src/com/insectlabs/dsp/noise/GaloisLfsr.java): allocation-free right-shifting XOR Galois step extracted from N02 1.0.0. `maskForBits` supports 7, 15 and 31 bits; `advance` consumes a caller-owned nonzero state and feedback mask. Read its least-significant bit for binary output. The caller owns clocking, seed choice, output levels, reconstruction and all state persistence. Zero is an absorbing state and is not automatically repaired. Unsupported widths throw during configuration. This is a deterministic musical PRNG, not a cryptographic random source. Original InsectLabs implementation; repository license applies.

The [N02 release checks](../voltage-modular/Teletape%20Workshop/n02/versions/1.0.0/tests/validate.py) compare this step bit-for-bit with the approved embedded code, exhaustively verify 7/15-bit periods and verify the 31-bit period algebraically. Released N02 remains self-contained and does not import this copy.

## HybridWaveforms48

[Source](src/com/insectlabs/dsp/oscillators/HybridWaveforms48.java): exact extraction of OSC 1.0.0's band-limited waveform families, triangle/ramp construction, pulse shaping, imperfect/rectified sine and signed FM integration helper. This preserves that instrument's particular voicing and conservative 48 kHz host-bandwidth policy. It is not a general transistor simulation or an arbitrary-sample-rate oscillator.

Call `selectBandwidth` before evaluating waveforms; each caller owns its selection state. Supply finite bounded phase/shape/rate values. The caller owns phase advance, gain, CV interpolation, corner-velocity allowance, oversampling and output decimation. Static table construction allocates roughly 5.4 MiB per loaded class and must happen outside audio callbacks. The [OSC release harness](../voltage-modular/Teletape%20Workshop/OSC/versions/1.0.0/tests/validate.py) checks exact waveform/helper parity against the embedded implementation. Released OSC remains self-contained.

## ChorusCore

[Source](src/com/insectlabs/dsp/chorus/ChorusCore.java): SDK-independent, allocation-free shared DSP extracted from CED 1.0.0. It contains two explicit voices: four-program Dimension-style stereo chorus and a mono CE-1-style chorus/vibrato path. The caller owns host control mapping, ±5 V normalization, input/output routing and lifecycle. Internally it supplies smoothed controls/program selection, delay lines, LFOs, optional noise and Dimension compander. The CE-style transfer model and combination laws are software choices, not component-accurate Roland emulations. Use the CED developer notes and its source references before changing behavior; released CED retains a self-contained embedded copy.

The [CED archived checks](../voltage-modular/Teletape%20Workshop/CED/versions/1.0.0/tests/validate.py) compile both source forms against the Voltage SDK, compare the module callback with this core through stubs, and run numeric tests. The `ChorusCoreTest` also renders a 24-bit TestBench audition demo.

## Validation commands

Run `python tests/validate.py` with Java 17. It checks HalfBand19 impulse/symmetry/gain/reset/independence, CubicHermite endpoints/constant/linear interpolation, EmsSvf96 finite and bounded processing, reset, high-response stress and API preconditions, and ChorusCore's delay, blend, nonfinite and mode limits. The separate Teletape SVF release harness compares the extracted primitive bit-for-bit with its embedded source.
