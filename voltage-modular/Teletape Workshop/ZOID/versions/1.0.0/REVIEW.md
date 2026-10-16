# ZOID 1.0.0 — release review

User approved the working instrument and authorized canonization on 2026-10-16. The final pair is the streamlined shared-segment build, with Java class, package and Designer identity `zoid` / `com.insectlabs.zoid`. The front panel artwork supplied for this build is archived byte-for-byte as `zoid_hero.png`.

Closeout changes: normalized all filenames to `zoid`, set Designer Notes to `v1.0.0`, corrected stale EOA/EOC tooltip text from 1 ms to the implemented 5 ms, and synchronized the Designer embedded source. No DSP behavior changed beyond those documentation and identity updates; the stage timing, 5 ms pulse implementation, and controls reflect the user-approved build.

Validation in `tests/validate.py` compiles exported and embedded Java against the installed Voltage SDK with Java 17 target, all lint warnings treated as errors. It also checks exact Designer serialization round-trip, source parity, control UUID uniqueness, stage timing/CV curves, typed time, shared-phase behavior, retriggers, VCA, pulse width and tooltip agreement, modes, bypass/resume, and recall. Results are recorded in `VALIDATION.txt`; file hashes are in `SHA256.json`.

The user reports that the pair works in Designer/Voltage Modular. This closeout reran automated compilation and behavioral checks; it did not repeat a GUI interaction audit, saved-patch migration check, native CPU benchmark or new listening session. Publication remains deferred under the series publication plan.

Folder audit: the Synthi 100 manual stays in the shared Teletape references collection. Useful design history from the AKS-style and early shared-segment trials is summarized in the developer notes; superseded source snapshots and Designer backups were removed. The specialized coupled trapezoid state machine is not extracted as a shared primitive, and no test audio was generated for this build.
