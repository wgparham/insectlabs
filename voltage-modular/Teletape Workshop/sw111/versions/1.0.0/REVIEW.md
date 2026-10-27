# swCXI 1.0.0 Release Review

- Module: swCXI Sequential Switch, Teletape Workshop, folder `sw111`
- Baseline: exact supplied v0.1.2 pair and hero, preserved with hashes in `research/supplied-v0.1.2`
- Version: first canonical release, 1.0.0
- Authorization: user requested canonization after tests, code cleanup, harvesting, housekeeping and Git push on 2026-10-27. User separately confirmed CCW 1 / CW 10 / default 1.
- Native host status: supplied as the user's winning candidate; no independent native reload or audition of the cleaned pair was performed. The explicit cleanup-then-promotion authorization covers this path.

## Code and metadata

Passed: package/class and Switches category; one call to base Destroy; descriptive stage-jack/lamp display names; mode-aware knob tooltip; inactive notification scaffold removed; compressed guards expanded; allocation-free sample paths. All control UUIDs, variables, positions, sizes, skins and supplied hero bytes are retained. The supplied Designer knob metadata (10/1/default 10) disagreed with executable source (1/10/default 1). Metadata was corrected to the explicitly chosen executable settings. The source forms agree after normalization. Designer binary round-trip, unique/ordered read-only anchors, ranges/defaults and empty control Notes pass. Module Notes are the exact canonical version marker.

## DSP and infrastructure

Passed: 60,109 core assertions and 1,151,109 SDK module-callback/cable assertions. Actual active output callbacks and selection state match v0.1.2 sample-for-sample across 50,000 deterministic samples covering manual/CV/clock modes, resets, variable stage counts, connection changes, DC/polarity and rapid transitions. Every output is written every sample, and both modeled cable slots carry settled DC. Mux and exclusive ramp arithmetic and the no-silent-handoff fix are retained.

Intentional infrastructure correction: bypass now reads only routed signal inputs, produces direct unity routes, freezes selection/clock/fade histories and primes current levels when active processing resumes. It skips all control/CV reads and does not replay an interrupted fade or synthesize a clock edge. The dedicated callback tests pass. The baseline's bypass read clock/reset CV and rewrote fading histories on every sample; those operations were removed under the authorized standards cleanup.

Passed: exported and embedded sources compile against `C:/ProgramData/Voltage/voltage.jar`, Java 17 target, `-Xlint:all -Werror`. The SDK-independent primitive is checked against the archived core. The constructor-free callback harness uses real SDK types with test I/O overrides, not the native GUI. No CPU timing or fresh listening result is claimed. LED updates only on stage changes are a documented indicator exception.

## Archive and housekeeping

The canonical pair, panel, user/developer docs, tests, review, validation output and hash manifest are retained in this version directory. Useful development change notes and the exact supplied reference pair remain under research. The shared SequentialSwitchCore primitive is harvested. Earlier working revisions and backups are redundant and removed only after final archive checks. Collection indexes, current module count and the obsolete 16-way-router proposal are updated; publication remains deferred. Unrelated local work, including the pending FPA comparison, is outside this release.

The staged repository snapshot audit inspected 1,466 files, 106 manifests, 897 hashed files, 82 source pairs and 1,358 local links. It reported eight pre-existing hash errors confined to unchanged CXL development backups; no errors concern this release or its primitive. No CXL files are staged in the release. The unmodified error report and scoped result are preserved in research/Staged-Repository-Check.json at the module root. Local FPA edits and comparison material also remain outside the commit. Release-specific manifests are checked against both working-copy bytes and actual staged blobs. This review's hashes are listed in SHA256.json; the Git commit containing this archive identifies its release commit. The push result is reported at handoff.
