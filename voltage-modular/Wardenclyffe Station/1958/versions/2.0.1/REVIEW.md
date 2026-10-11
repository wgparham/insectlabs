# Release review — 1958 Dual Waveform Generator 2.0.1

- Prior canonical release: 2.0.0; preserved unchanged. Earlier 1.x archives remain intact.
- Candidate Notes: `v2.0.1rc`; final archived Notes: `v2.0.1`.
- Scope: panel/artwork refresh; internal modulation extended from 0.05–50 Hz to 0.05–100 Hz; mixed 1 kHz reference reduced and routed before AMPLITUDE so the control scales both signals.
- User build, runtime, and listening test: passed for the exact 2.0.1 RC source pair; user confirmed it compiles, runs, and sounds great on 11 October 2026.
- Java 17 target compilation against the installed Voltage SDK: passed for exported and embedded source with warnings treated as errors.
- Designer serialization round-trip: passed. Embedded source matches the exported Java after final version Notes update.
- Boundary checks: passed for internal modulation rate mapping (0.05–100 Hz), reference-mix coefficient and pre-amplitude routing, module category, release Notes, and documented power timing.
- Panel/source review: candidate artwork and serialized control layout are retained together. The user performed the native panel build and runtime check.
- Full repository checker: not available in this checkout; no `tools/check_repository.py` was found. Git status was reviewed; unrelated user work is present and excluded from this release.
- Full CPU benchmark: not run; the DSP change adds no new per-sample algorithm beyond the existing sine mix and oscillator path.
- Applied whitespace-only cleanup to the exported and embedded Java after preserving the exact RC pair; DSP behavior is unchanged.
- Applied whitespace-only cleanup to exported and embedded Java; `APPROVED-RC-SOURCE.json` records the original candidate hashes. DSP behavior is unchanged.
- Integrity manifest: generated after all release files were finalized.

This release is a patch-level update to the 2.x product. The prior canonical archive is not modified.
