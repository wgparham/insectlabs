# Reverb primitive maintenance notes

## Origin and extraction

Source: the immutable LM-21 Mk III v1.0.1 prototype snapshot linked in README. Extracted 2026-10-08. The original nesting depended only on the outer SAMPLE_RATE constant; no Voltage Modular SDK objects are required inside the engine. The original mono average/output gain, eight delay constants, signed random generator seeds/rates, scattering junction, cubic interpolation, feedback low-pass and optional age saturation are preserved.

Changes: public standalone class/API, per-instance sample rate (default 48 kHz), deterministic reset, and separate control-rate parameter conversions. Two sample-rate-dependent static helpers became instance methods. At 48 kHz the sample path must remain bit-identical to the original. The canonical LM-21 module is unchanged and continues using its own embedded engine.

## Caller contract

`process(input, feedback, dampFact, pitchMod, age)` expects finite input and parameters: feedback >= 0 and < 1; dampFact 0–1; pitchMod 0–2.25; age 0–1. Caller handles input conditioning, clipping, wet/dry mix and parameter smoothing. Per-sample guards are omitted intentionally; violating the contract can corrupt state. Constructor accepts finite 8–192 kHz rates and allocates all buffers once. Reconstruct the engine to change sample rate.

The parameter helper uses the original approximate 68.38 ms mean delay for nominal RT60, and the original damping formula. Age adds asymmetric-generation-independent tanh loss in recirculation; zero age preserves the normal prototype path. It is not a convolution reverb or component simulation. Each engine owns all buffers/modulation state and has no shared mutable DSP state.

## Tests and limitations

Tests dynamically extract the reference from the immutable prototype; the test does not rewrite its algorithm. They compile both the public primitive and reference with Java 17 and warnings as errors, test sample parity over multiple parameter sets, verify reset returns silence and deterministic behavior, check independent instances, and test additional sample rates. No audio fixture is rendered by these checks. Native-host CPU use and subjective behavior at other sample rates remain unmeasured. Do not substitute this primitive into an approved module without separately validating that module.
