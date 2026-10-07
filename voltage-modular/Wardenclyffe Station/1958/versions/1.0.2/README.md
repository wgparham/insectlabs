# Generator 1.0.2

Canonical release approved on 2026-09-30 after the final Designer build, run and listening check.

This maintenance release adds a short physical power-switch transition. Switching POWER on or off uses an 8 ms linear fade. On power-down the oscillators stop once the fade reaches silence; on power-up they resume from their held state through the same fade. The host bypass remains the established direct, hard silence/freeze path.

The module retains the approved 1.0.1 oscillator, FM, output compression, reference tone, duty smoothing, panel, category and control behavior. The Designer Notes field identifies this release as `v1.0.2`.

## Included files

- `generator.vmod` — canonical Voltage Modular Designer project.
- `generator.java` — matching exported DSP source.
- `generator_hero.png` — approved panel image.
- `SHA256.json` — SHA-256 manifest for the release files.

## Validation

The user confirmed the final 1.0.2 build and runtime test. Source/project parity, release metadata and hashes were checked during promotion. The release source also received an independent review of the new 8 ms power-gain path and retains the prior hard host-bypass implementation.

[Previous release: 1.0.1](../1.0.1/README.md)
