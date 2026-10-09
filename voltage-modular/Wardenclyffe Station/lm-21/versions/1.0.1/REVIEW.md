# LM-21 Mk III release-candidate review

Date: 2026-10-08. Candidate: **v1.0.1rc**, promoted from the user working Notes marker v1.0.1a. No earlier LM-21 canonical archive exists in this module folder. Scope authorized by the user: test, clean/review, then promote the pair to RC when checks pass. Canonical release/publication is pending.

## Changes

- Descriptive control names and human-readable Display Names in Java and Designer; immutable UUIDs, panel geometry, artwork, ranges/defaults and saved test state preserved.
- Added functional matrix/jack tooltip guidance and corrected stale notes/documentation.
- Direct host bypass now freezes the power envelope and avoids power-control reads. This is the sole intentional processing-behavior correction; active audio equations and approved power timing are unchanged.
- Notes version is v1.0.1rc; working control notes moved into module documents and cleared.

## Evidence

- Exported and embedded source parity: passed.
- Complete ValueTree parse/byte round-trip and remapped Designer line anchors: passed.
- Both source forms: Java 17, installed Voltage SDK, all compiler warnings treated as errors: passed.
- Actual callback/DSP tests: passed, including all 16 routes in both polarities, control notifications, typed edits, preset restore, bypass/resume, power, MULT, FULL MIX, hot inputs, and three 30-second reverb trials.
- Active baseline/candidate output digest: identical, 14811806090894522753. Baseline Java SHA-256: daebf9c1d3aa799621a50ef730c3f0f61a68c4e3222992013cf7059bd2f4b6e5.
- Audio fixtures: none rendered; no new TestBench files required.

## Pending and exceptions

Native-host testing of this exact cleaned pair, saved-patch/automation/undo verification, skin rendering and host CPU profiling remain pending. Earlier user reports approve the sound, but are not represented as a native-host test of this RC. The repeatable tests mock SDK controls and execute the actual extracted source bodies. Native-rate saturation, fixed 48 kHz operation, throttled lamp updates from the audio callback and retained generated notification scaffolding are documented exceptions.

Developer notes and manual updated. No canonical archive, commit or push is part of this RC-only task. Unrelated minimixer work and the historical prototype remain untouched. Candidate artifact hashes are recorded in SHA256.json. The exact pre-cleanup source is retained in tests/baseline/pre-cleanup.java.txt for repeatable parity comparisons.

## Canonical approval — 2026-10-08

The user corrected a small front-panel text error, recompiled and tested the latest pair, confirmed everything works, and approved canonization. The release is v1.0.1. The archived pair retains that correction; only version Notes/comment cleanup follows approval. Final archived-source SDK, callback and integrity validation is required before publication. Native-host CPU and dedicated automation/undo profiling remain unmeasured.

Final archive validation: passed both warning-clean SDK builds, source/project integrity, functional callback tests and baseline sample parity. User native-host approval applies to the corrected working pair; subsequent changes only finalize version markers and documentation.
