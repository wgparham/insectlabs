# b16 v2.0.0 Release Review

Date: 2026-10-12. User confirmed successful Voltage Modular compile, run and basic panel checks and upload through Designer to the Cherry Audio developer account.

## Scope and provenance

- Canonized the supplied 16 HP b16 blanking panel as the Wardenclyffe successor to the retired b08.
- No Java, Designer metadata, artwork or user-facing behavior was changed. The supplied Java and VMOD files were copied unchanged into the versioned archive; pair hashes match.
- The old b16/1.0.0 folder is an exact byte-for-byte duplicate of the retired Wardenclyffe b08 v1.0.0 archive in Git history. It was retained after the automated safety reviewer rejected its deletion; this is a cleanup remainder, not an earlier b16 release.
- No DSP primitive, audio fixture or TestBench asset applies.

## Validation

- PASS: Designer project parses and round-trips byte-for-byte.
- PASS: embedded and exported Java match after repository line-ending normalization.
- PASS: exported and embedded sources compile against the installed Voltage SDK using Java 17 and warnings as errors.
- PASS: automated checks confirm `blank16`, class `b16`, package `com.insectlabs.b16`, 3.2-inch width, three images, two labels, no jacks/functional controls and an empty custom `ProcessSample()` body.
- USER HOST CHECK: successful compile, run and basic panel checks in Voltage Modular, as confirmed by the user.
- PUBLICATION: uploaded through Designer to the user's Cherry Audio developer account, as confirmed by the user.
- NOT APPLICABLE: audio/DSP, bypass routing, signal fixtures or CPU/audio measurements; the panel has no signal path or custom DSP.

## Archive and cleanup

Canonical archive: `versions/2.0.0/`. The user-supplied pair at `2.0.0/` remains in place for the existing Designer workflow and matches the archive. Generated `.bak` files were removed after verifying the current pair against the archive. The Designer Notes field is preserved exactly as published (`2.0.0` followed by the panel description). The duplicated legacy folder remains for a later cleanup decision.
