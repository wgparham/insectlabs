# CED development workspace

The active working pair is in [`module/`](module/): `ced.java` and `ced.vmod`. The canonized, immutable release is [`../versions/1.0.0/`](../versions/1.0.0/). Run `tests/validate.py --notes v1.0.0rc` while editing a release candidate; the archived copy validates the canonical `v1.0.0` marker.

The shared DSP source now lives in [`dsp-primitives`](../../../../dsp-primitives/src/com/insectlabs/dsp/chorus/ChorusCore.java); CED keeps an embedded copy in the Designer project. Repeatable wiring/DSP tests and local SDK stubs are in `tests/` and `stubs/`. Preserve the exact release archive when making future changes; the current module file itself is not edited in place after canonization.
