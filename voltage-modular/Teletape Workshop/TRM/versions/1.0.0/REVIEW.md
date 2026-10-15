# TRM 1.0.0 — release review

User approval: on 2026-10-15 the user confirmed that all four modes sound and respond as ring modulators with distinctive voices, and authorized canonization and cleanup. The final promotion changes Designer Notes from the release-candidate marker to `v1.0.0` and adds documentation; DSP is unchanged after audition.

## Checks

- Exported and embedded Java compile against the installed Voltage SDK with Java 17 target, `-Xlint:all -Werror`.
- Exported and embedded source agree after normalization; the VMOD round-trips byte-identically and its Notes field is `v1.0.0`.
- The repeatable harness executes actual DSP methods and callbacks, covering mode distinction, polarity where the selected conduction model is symmetric, envelope-dependent voicing, transformer leakage, lane independence, default selector states, zero gain, lamp response, bypass/resume, and finite stressed output.
- Source pair, panel image, documentation and test files are covered by the release hash manifest.

## Scope and limits

The mode palette is an explicitly creative software interpretation; it is not a circuit reconstruction or a claim about historical EMS component variants. Host CPU profiling, native UI/tooltip automation and a separate post-documentation Designer session were not performed. The user had already compiled, run and auditioned the exact DSP and panel in Voltage Modular before this documentation-only promotion. No new test audio was needed.

No unpublished scratch implementation was promoted. Original `notesTowardsAtrm.md` remains with the development folder as history. Generated `.bak` files may be removed after verifying archive pair identity. Cherry Audio publication remains deferred until the full series is canonized.
