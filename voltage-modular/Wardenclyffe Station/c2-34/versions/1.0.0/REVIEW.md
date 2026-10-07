# c2-34 Balanced Modulator 1.0.0 — release review

## Approval and scope

The user confirmed that the v0.0.2n dirty-zone adjustment works well and authorized canonization. The approved sound uses the wider mid-travel feedthrough range with 6% maximum carrier and modulator leakage. This is the first canonical version; there was no previous release archive.

The release cleanup renames generated controls while preserving their Designer UUIDs, sets human-readable Display Names, adds user-facing jack/control tooltips, removes the unused empty Notify switch, clears temporary Notes, and synchronizes the embedded source. The module's `Notes` field is exactly `v1.0.0`. No DSP coefficients or processing equations changed after listening approval.

## Design and infrastructure review

- Mono routing is X signal/carrier and Y modulator/control to Z output; no normalization or second channel is introduced.
- BAL is a carrier-suppressed four-quadrant product. UNBAL is a unipolar VCA. The continuous knob uses smoothed endpoint transitions and the approved center dirty zone.
- Each carrier/modulator feedthrough term is capped at 6%; in the center overlap the aligned terms can sum to 12% of their respective full-scale inputs.
- The Amplitude knob is a linear 0–1 post-stage control with 5 ms manual smoothing.
- Host BYPASS routes X directly to Z, ignores Y and controls, and marks DSP state for current-control resumption. The transition is intentionally abrupt.
- Audio processing runs at native sample rate, allocates no objects in the sample callback, and uses no oversampling or added transformer/saturation stage. No numeric CPU benchmark was recorded.
- Output can contain DC from Y; this is a user-approved circuit artifact. No hard output clamp is added.
- Category is Processor. The decorative, non-editable Mode Curve is Designer-only art and has no Java control variable or audio role.

## Validation

- The exported Java and embedded Designer source match after normalization; the Designer project also round-trips byte-for-byte through the repository parser.
- Both source forms compile against the installed Voltage SDK with `javac --release 17 -Xlint:unchecked -Werror`.
- Full `-Xlint:all` compilation was attempted, but the installed JDK 27 reports a class-file emission error while writing the nested `BalancedModulatorDsp` class. The narrower unchecked-warning check passes with warnings as errors; full lint is not claimed.
- `tests/validate.py` runs 14 assertions against the actual DSP core for both exported and embedded classes, covering VCA range/polarity, balanced multiplication, carrier suppression, 6% feedthrough limits, endpoint cleanliness, and the 5 ms control ramp.
- Static checks verify the version Notes, human-readable Display Names, direct bypass route, no control/DSP reads in bypass, source pairing, and release hashes.
- The user tested the approved DSP before behavior-preserving ID/tooltip cleanup. The final pair compiled and passed automated validation; a separate post-cleanup Designer save/reload test was not performed here.
- No audio test fixture was needed.

## Release state

Archive: `voltage-modular/Wardenclyffe Station/c2-34/versions/1.0.0`.

The c2-34 archive is staged and its focused validation passes. The repository-wide audit was run after staging and reported only two hash mismatches in the already-canonical SIN/RND 1.1.0 Java/Designer pair, which has separate unstaged local edits. Those files were left untouched. Commit and push are held until the whole-series review reconciles that existing state; do not mix those edits into this c2-34 release.
