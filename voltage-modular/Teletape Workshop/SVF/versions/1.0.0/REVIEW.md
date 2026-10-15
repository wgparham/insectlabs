# SVF 1.0.0 — release review

User approved the sound and canonized this design on 2026-10-15. The latest Designer working pair was preserved; Java and embedded source agree after normalization and the VMOD round-trips byte-identically. Notes are `v1.0.0`. The Java class/package remain `svf` / `com.insectlabs.svf`. The archived Java, VMOD and hero match the approved working files by SHA-256.

## Checks

- Both exported and embedded Java compile against the installed Voltage SDK with Java 17 target, `-Xlint:all -Werror`.
- Independent linear HP/BP/LP transfer, polarity and gain checks; serial routes, parallel routing, independent silence and patched IN 2 override pass.
- Self-oscillation startup/bounds, low/high cutoff extremes, 20 V modulation stress, nonlinear solver residual, DC behavior, bypass/freeze/resume, reset, numerical recovery, CV/taper and typed entry checks pass.
- Extracted SDK-independent primitive is bit-identical to the embedded core across 160000 modulated steps and resets.
- Both language source forms and project metadata are covered by release hashes. The detailed output is in `VALIDATION.txt`.

The user approved listening/function in Voltage Modular. The repository checks do not measure native CPU on the user's machine or independently automate visible control interaction, preset UI, or undo behavior. No new test audio was needed: the independent equation, polyphase and module-routings tests exercise the DSP. No generated classes or backup files belong in canon.

## Housekeeping

HPF was renamed SVF because the final instrument exposes all three state-variable outputs. The former HPF development path is retired. Schematics, Digitana photos/demonstrations and the user-supplied Synthi 100 panel photo now live in the shared `references/Synthi-100-Filters/` archive. The general unwired Teletape panel is retained as a template under `designResources/blank-template/`. The abandoned unwired SVF mockup and stale `.bak` files were removed after the accepted release pair was archived and verified. Reusable core arithmetic was extracted to `dsp-primitives`; the release remains independently buildable.

No Cherry Audio publication occurred. Publication is deferred until the user locks and canonizes the whole Teletape lineup. Git push records the source release and housekeeping only.
