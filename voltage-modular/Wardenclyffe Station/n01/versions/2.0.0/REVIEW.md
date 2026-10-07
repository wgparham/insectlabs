# Release review — n01 Noise Generator 2.0.0

- Prior archive: 1.0.0 (retained unchanged).
- Candidate source pair: approved 2.x RC, promoted to 2.0.0 after user confirmation that the candidate had been built and auditioned.
- Scope: release packaging, pair filenames matched to the public Java class, descriptive internal identifiers where the panel had generated placeholders, human-readable Display Names, module Notes set to `v2.0.0`, useful missing tooltips added, and user manual refreshed.
- Audible DSP and tested panel behavior: preserved from the approved candidate.
- SDK compilation: passed: 16/16 Java source files compile against Voltage SDK using Java 17 target.
- Designer round trip and embedded-source parity: passed: all 16 `.vmod` files round-trip and embedded Java matches the standalone source.
- Native host test after metadata cleanup: not repeated; user approved the exact candidate behavior before this behavior-preserving cleanup.
- Release files and hashes: passed: SHA-256 manifest generated for all release files.
