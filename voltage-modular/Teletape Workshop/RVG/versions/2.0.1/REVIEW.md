# RVG 2.0.1 release review

User supplied the updated panel pair and confirmed compilation and runtime on 2026-10-15. Scope is limited to panel metadata/branding and one new fixed label. DSP callbacks and DSP helper tokens are identical to canonical 2.0.0; all existing Designer control UUIDs are preserved and one new label control is added.

## Checks

- Exported and embedded sources agree; VMOD round-trip and all current release hashes pass.
- Both source forms compile against the installed Voltage SDK with Java 17 target, `-Xlint:all -Werror`.
- Existing control identities and saved switch positions are preserved; `labelLocation` is the only added Designer control.
- Existing automated RVG callback, state, trigger, droop, bypass/resume and lifecycle tests pass on the exact 2.0.1 archive.
- DSP regions are token-identical to v2.0.0. The change is panel-only, so no renewed listening was requested or performed by the user after compiling/running.

No user-facing DSP change, new hardware claim, primitive, or audio fixture. CPU and GUI automation are not measured. Cherry Audio publication remains deferred until the complete Teletape lineup is canonized.

The existing hero PNG is retained unchanged; it does not depict the new runtime text label. The user-supplied Designer project is the authoritative updated panel source.
