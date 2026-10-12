# sn46 v1.0.0 — release review

Status: canonical, approved by the user on 2026-10-11 after the review. The user supplied the refreshed hero image. Baseline file hashes and original panel invariants are retained in tests/REVIEW-BASELINE.json.

Scope: code/metadata cleanup, useful tooltips and precise custom-state validation. The user confirmed F Sharp should remain below A. Preserved class/package, UUIDs, panel layout/resources, voltage ranges/defaults, source math, routing and bypass. Changed public metadata to sn46, corrected manufacturer metadata and human-readable control names, fixed the outputPulsws typo, removed dead notifications and unused previousPink, and constrained custom state to valid five-byte mode data. No tone or noise coefficient changes.

- PASS: exported and embedded Java compile, installed Voltage SDK / Java 17 / Xlint all / warnings as errors.
- PASS: binary round-trip, synchronized source, metadata/constructor identities and source-anchor bounds.
- PASS: art resources, skins, test-mode state, geometry and control UUID inventory match the supplied panel.
- PASS: unchanged audio/bypass calculation bodies after identifier substitution.
- PASS: DC limits/polarity/fixed references, nonfinite input bounds, display precision and zero padding.
- PASS: sine frequency/amplitude, lower F Sharp ratio, impulse height/schedule and finite live noise.
- PASS: A432 settling, manual toggle/release, 500 presses without releases, undo/redo, reset, saved/variation mode and rejected malformed state.
- PASS: silent bypass, history freeze, no bypass control reads, resume and single base destruction/timer shutdown.
- APPROVED: user authorized canonization; refreshed hero uses sn46. Native saved-patch recall was not separately reported; automated recall checks pass.
- UNMEASURED: native-host CPU; no native benchmark claim.

Canonical archive: versions/1.0.0. Designer working Notes have been moved into external documentation; only the release version remains. Final archive validation is recorded below. Git publication is not performed by this promotion. The wider repository checker has the separately reported missing 6121198a archive checksum file; this release is checked independently.

Final archive validation: PASS — both strict SDK compilations and all behavioral tests. The latest approved Designer export keeps the same geometry and refreshed sn46 hero; the prior baseline is retained separately. Active source and archive copies are byte-identical after promotion.
