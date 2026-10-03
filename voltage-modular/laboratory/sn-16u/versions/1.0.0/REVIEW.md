# SN-16u 1.0.0 release review

Approved for canonization and publication after the user reviewed the comprehensive manual, confirmed its contents, and authorized final checks, cleanup, and GitHub publication. The user had already verified the approved DSP and panel in Voltage Modular.

## Final cleanup

The release preserves the approved signal processing, controls, UUIDs, panel positions, ranges, defaults, artwork, class/package identity, and lifecycle behavior. Cleanup set the module Notes field to exactly `v1.0.0`, removed temporary control Notes, and gave 99 static panel labels human-readable display names and lowerCamelCase internal names. Label text and panel geometry are unchanged. The embedded Designer source and exported Java were resynchronized. The helper now recognizes that a human-readable Display Name may differ from a control's internal name.

No DSP algorithm, coefficient, sample ordering, or routing changed in this cleanup. This is the first canonical release; no earlier canonical SN-16u archive was replaced.

## Validation

- Java export and embedded Designer source compile against the installed Voltage SDK with `--release 17 -Xlint:all -Werror`.
- Exported/embedded pair equality, Designer serialization round-trip, Notes, metadata count, UUID uniqueness, key positions/defaults, and editor anchors pass.
- Callback suite passes **4,032,101** checks, including fixed references, pitch standards, conversions, typed edits, reference frequencies and levels, filter paths, pulse timing, meter modes, bypass/frozen state, and sweep start/retrigger/completion.
- TestBench v1.2 verification passes for 58 WAVs and 275 format, signal, checksum, and routing checks. New sweep/noise fixtures are callback captures; their source version and levels are documented in the TestBench manifest.
- User native build, load, and listening test passed on the approved candidate before this metadata-only cleanup. A separate native-host test after the readable label rename is not claimed.
- No native-host CPU benchmark or measurement-grade calibration claim is made.

## Version baseline and source hashes

This is the first release, promoted from the user's approved v1.0.0 candidate. Candidate bytes before the final naming/metadata pass are recorded in `APPROVED-BASELINE.json`. Final release file hashes are in `SHA256.json`.

## Release contents

The archive includes the matched Designer/Java pair, approved panel art, user manual, control map, release review, repeatable validator, and checksums. Temporary Designer Notes, backups, compiler outputs, and unrelated work-in-progress projects are excluded.
