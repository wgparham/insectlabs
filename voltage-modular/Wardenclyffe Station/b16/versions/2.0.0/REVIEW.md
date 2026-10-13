# b16 v2.0.0 Release Review

Date: 2026-10-12. User confirmed successful Voltage Modular compile, run and basic panel checks and upload through Designer to the Cherry Audio developer account.

## Scope and provenance

- Canonized the supplied 16 HP b16 blanking panel as the Wardenclyffe successor to the retired b08.
- The user supplied the current Java, Designer project and hero image and confirmed these are the correct canon files, tested and published. The archive now mirrors the supplied pair and hero byte-for-byte. The current source and embedded source match after repository line-ending normalization.
- The `b16/versions/1.0.0/` folder intentionally preserves the exact retired Wardenclyffe 8 HP b08 v1.0.0 Java, Designer project and hero image as the predecessor archive. Wardenclyffe’s 8 HP blank is fully replaced by this 16 HP b16; the archive is retained for continuity and is not an earlier 16 HP b16 release.
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

Canonical archive: `versions/2.0.0/`. The versioned archive at `versions/2.0.0/` contains the active published pair and hero image. Generated `.bak` files were removed after verifying the current pair against the archive. The Designer Notes field is preserved exactly as published (`2.0.0` followed by the panel description). The predecessor archive remains under `b16/versions/1.0.0/` for archival continuity. The redundant `b16/2.0.0/` working copy has been deleted; `b16/versions/2.0.0/` is the sole canonical folder for the active 16 HP module.
