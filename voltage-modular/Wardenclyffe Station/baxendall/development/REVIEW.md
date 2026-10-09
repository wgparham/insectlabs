# 21-24eq v1.0.1 release review

Date: 2026-10-09. User instruction: integrate the updated Java source into the Designer project, test it, and bump to v1.0.1 if it passes. The updated source and project passed the repeatable automated checks described below and were archived as v1.0.1. This is the first canonized archive; the earlier 1.0.0 candidate remained development-only.

## Passed checks

- Passed: Designer `.vmod` parser/encoder round-trip and embedded/exported source agreement after synchronization.
- Passed: both source forms compile for Java 17 against the installed Voltage SDK with `javac --release 17 -Werror`.
- Passed: repeatable headless checks execute the actual extracted sample callback and DSP core. They cover Bass/Treble polarity and cross-band response, all three switch profiles, exact dry bypass, reset-on-resume with immediate control adoption, silence on an unpatched input, nonfinite control handling, and symmetrical drive/ceiling bounds. Run `python tests/validate.py`.
- Passed: switch skin, profile order, initial Reference state, control UUIDs, source/Designer display names, and serialized label text agree. Old button callback and range-state references are absent.
- Passed: panel response chart updated for all three switch positions.

## Limitations and exceptions

- The installed `javac 27` fails with `-Xlint:all` while writing nested classes. The same error reproduces with a minimal unrelated Java nested-class source; compilation with `-Werror` and the required Java 17 target succeeds. Full `-Xlint:all -Werror` coverage is therefore unavailable in this environment.
- No native Voltage Module Designer/Voltage Modular load, switch interaction, save/reload, listening, CPU, allocation, automation, or undo session was run here. The headless controls are SDK mocks and do not replace host checks.
- This is a passive-voiced DSP approximation, not a component-exact Baxandall implementation. The response chart is a nodal model, not a measurement.

## Release contents

The immutable v1.0.1 folder contains the exact synchronized project/source pair, hero artwork, manual, developer notes, review, design record, release notes, repeatable validation, and SHA-256 manifest. No prior 1.0.0 release was canonized or overwritten.
