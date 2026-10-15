# SVF 1.0.1 — release review

The user supplied this revised panel pair and confirmed it compiled and ran on 2026-10-15. Scope is limited to Designer panel metadata, TTW image/skin references and one static location label. Java DSP and routing callbacks are token-identical to canonical 1.0.0. All 44 existing controls retain their UUIDs; `labelLocation` is the sole additional Designer control.

## Checks

- Exported/embedded source agreement, VMOD byte-identical round-trip, Notes v1.0.1 and release hashes pass.
- Both Java source forms compile against the installed Voltage SDK with Java 17 target and warnings as errors.
- Existing SVF analytic, routing, resonance, nonlinear solver, state, bypass/resume, control and primitive-parity tests pass from the 1.0.1 archive.
- The release archive contains no Designer `.bak` files. Historical source and project for 1.0.0 are retained unchanged.

No new DSP audition was required because the audio-processing regions are unchanged; the user confirmed native compile/runtime. Host CPU and GUI/undo automation remain unmeasured. Cherry Audio publication is deferred pending completion of the full series.
