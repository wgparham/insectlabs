# b08 v1.0.0 Release Review

Date: 2026-10-12. The user authorized testing and canonization at will, then confirmed that b08 is tested but not published.

## Scope and provenance

- Canonicalized the supplied 8 HP artwork-only blanking panel without changing its Java or Designer metadata.
- The working `.java` and `.vmod` were copied unchanged into this archive; hashes were compared before cleanup. The source contains an empty generated `ProcessSample()` body and no custom DSP.
- Preserved the supplied panel hero image. Series-level artwork, the font and its original license remain under `../designResources/`; shared EMS materials and the unimplemented slew pseudocode remain in `../../references/`.
- No audio fixture or reusable DSP primitive is applicable.

## Validation

- PASS: Designer binary parses and round-trips byte-for-byte.
- PASS: embedded Java and exported Java agree after the repository's line-ending normalization.
- PASS: exported and embedded source compile against the installed Voltage SDK using `javac --release 17 -Werror`.
- PASS: release checks verify `blank08` / class `b08`, 1.6-inch width, the expected two image components and one label, no functional controls or jacks, and an empty custom process body.
- USER HOST CHECK: the user confirmed the module compiled, ran and passed basic panel checks in Voltage Modular.
- PUBLICATION: not published to the Cherry Audio developer account.
- NOT APPLICABLE: audio/DSP behavior, bypass routing, signal fixtures, and CPU/audio measurements; the module has no signal path or custom DSP.

## Archive and cleanup

The versioned archive is the canonical source of record. The user-supplied Designer working pair remains at `../../1.0.0/` for continuity. Generated `.bak` files were removed only after confirming the working pair matches the archive. This release was canonized in the repository; account publication is a separate, still-pending action.
