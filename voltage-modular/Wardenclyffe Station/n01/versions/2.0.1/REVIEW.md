# n01 v2.0.1 — release review

Baseline: canonical v2.0.0. Scope: trigger indicator and manual-press handling only. User tested external and manual illumination, reported rapid-press lockout, then verified the correction and approved canonization on 2026-10-11.

The 100 ms indicator follows accepted S&H events. Removed release-dependent manual latch; AutoRepeat remains disabled. Exported Java is byte-identical to the tested development file. Designer Notes are stripped except v2.0.1; working Notes are retained in developer documentation. Class, package, panel and routing are preserved. Previous archives are untouched.

Validation: PASS — both source forms compile with installed SDK / Java 17 / strict lint / warnings as errors; VMOD binary round-trip and source match; manual/external flash, 100 ms expiry, hysteresis, held gate, release, bypass/resume, invalid input, reset and 500 presses without release all pass. Source and artwork match the approved working files. Native-host CPU is unmeasured. Git publication is not performed in this promotion.

The repository-wide checker remains blocked by the previously reported missing tracked `6121198a/versions/1.0.0/SHA256.json`; this release is validated independently. The verified development folder is removed after promotion.
