# Developer notes standard

Create or update developer notes for every canonized Voltage Modular build, including patch releases and behavior-preserving maintenance releases. This is part of module completion and the handoff before starting another module. A release is not fully documented until the notes correspond to the exact canonical `.vmod` and exported `.java` pair.

Keep the current notes at the module root as `DEVELOPER-NOTES.md` and link them from that module's `README.md`. Include a version-specific snapshot at `versions/<version>/DEVELOPER-NOTES.md` in each newly canonized archive. Update both copies together when promoting a release. Preserve historical version snapshots unchanged.

## Required contents

- Module identity, exact canonical version, collection, source filenames and locations, and links to the user manual and release/validation record.
- Intended behavior and important design constraints that affect future maintenance.
- Control, jack, switch, indicator, and output behavior, including ranges, units, defaults, normalization, and mode-dependent behavior where relevant.
- Source map for processing callbacks and important helpers; explain the signal flow and DSP methods at a level that makes the implementation maintainable.
- Important coefficients, scaling, state, smoothing, nonlinear processing, power and bypass behavior, and resume policy where applicable.
- Real-time and CPU-relevant design notes, including allocations or expensive operations and what has or has not been benchmarked.
- Validation evidence for this exact build, such as compilation, source-pair agreement, automated checks, and user host/audition status. State limitations and unverified items plainly.
- A focused change checklist identifying what future edits could affect behavior, and which tests should be repeated.

## Accuracy rules

Base statements on the canonical source, Designer metadata, approved user manual, release review and recorded test evidence. Do not infer undocumented hardware facts or claim measurements that were not made. Mark unresolved behavior as unknown or pending. Keep implementation notes distinct from user-facing operating instructions; link to the manual instead of duplicating it. Update notes after any canonical source or metadata change, then validate links and version identifiers before archiving.