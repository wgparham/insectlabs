# RVG 2.0.0 release review — 2026-10-14

Baseline: immutable 1.8.0. Candidate: user-supplied normalized-tanh 2.0.0 working pair. User stated it worked and was liked, then explicitly approved canonization. Publication unconfirmed.

Code/artwork were not changed after that approval. Packaging only changes module Notes from v2.0.0rc to v2.0.0 and external docs/indexes. Supplied panel/control/skin/test state are retained. Source agreement, project round-trip, artifact hashes and both SDK source compilations are checked by tests/validate.py. Actual callback substitutes check selection, holds, timing, inhibit, gates, bypass/resume, lifecycle and normalized output/gain. Native tests remain the user's audition evidence; no independent UI/automation/undo/CPU benchmark is claimed.

Differences from 1.8.0: direct third-held-value variance feedback without mean normalization; .05–500 Hz final bound; normalized tanh before Level; uniform source draws retained; automatic pulses corrected to 480 samples. Model matches approved workflow; transistor/cell specifics and distribution are not established original-hardware claims.

Known maintenance gaps: per-sample coefficient computation, native-invalid-control sanitation and full jack tooltips. Retained established source bypass clears pending triggers/lamp/selection while freezing held/PRNG/phase. No unconditional age noise/degradation added. Versions and research remain preserved; no new audio fixture or standalone reusable primitive created. Git push was previously authorized but pending because GitHub DNS failed; this turn itself requests canonization, not a new publication operation.
