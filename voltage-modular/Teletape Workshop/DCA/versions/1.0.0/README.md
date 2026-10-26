# DCA 1.0.0 — Dual Controlled Amplifier

Canonized as Teletape Workshop release 1.0.0. The module combines two independently controlled voltage amplifiers with a serial mode, a stereo mode, per-channel bias and shape controls, and unity link inputs. Cherry Audio publication remains deferred until the complete series lineup is locked.

- [Open the Designer module](dca.vmod)
- [Matching Java export](dca.java) — class/package `dca` / `com.insectlabs.dca`
- [User manual](USER-MANUAL.md)
- [Developer notes](DEVELOPER-NOTES.md)
- [Release notes](RELEASE-NOTES.md)
- [Release review](REVIEW.md)
- [Repeatable validation](tests/validate.py)
- [Validation record](VALIDATION.txt)
- [SHA-256 manifest](SHA256.json)

The release panel is [dca_hero.png](dca_hero.png). The reusable 19-tap half-band FIR used by the nonlinear path is also catalogued in [`dsp-primitives`](../../../../../dsp-primitives/README.md#halfband19); the released module remains self-contained.
