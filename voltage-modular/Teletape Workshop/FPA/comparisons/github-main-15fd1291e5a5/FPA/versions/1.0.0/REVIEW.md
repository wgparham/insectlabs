# FPA 1.0.0 release review

Record: FPA — Four Pre-Amps; Teletape Workshop; canonical module, no prior released baseline. The repair restores the four documented per-channel polarity toggles to the Designer control tree and reconnects Tone DSP controls to the right-column Tone knobs. The signed Tone interpretation (−1..+1, center 0) and DSP algorithm are unchanged.

## Evidence and checks

- **PASS** — exported `fpa.java` and embedded `.vmod` source compile against the installed Voltage SDK using `javac --release 17 -Xlint:all -Werror`.
- **PASS** — Designer binary round-trip, embedded/exported source parity, class/package identity, Notes `v1.0.0`, unique control UUIDs, four signed Tone metadata ranges/defaults, four default-normal polarity toggles and protected-line index bounds.
- **PASS** — exact nested `PreampCore` extracted from the release source passes `PreampCoreTest`: calibration within 0.5 dB at 11 gain points for each voicing, gain monotonicity, output ceiling, zero input, NaN/Infinity handling, phase polarity, signed Tone sweep, C/Re/D neutral center, O geometric cutoff midpoint, signed clamp bounds, mix scaling and channel independence.
- **PASS** — user confirmed Designer load/compile/run on the earlier candidate. After restoring the missing phase toggle controls, exported and embedded sources compile under the installed SDK and automated checks pass; a fresh native Designer load/listen check after this repair is still needed.
- **LIMITATION** — the repaired pair has not yet been reopened in Designer for a visual check, saved-patch/undo check or listening test. No DSP algorithm change was made in this repair.
- **LIMITATION** — sample-rate coefficients are fixed at 48 kHz. Alternate host sample rates, alias rejection under external measurement and native Voltage CPU usage are unverified. The timing output in the test is only a rough headless JVM estimate.
- **LIMITATION** — user-provided reference material includes an Electric Druid schematic comparison sheet marked CC BY-NC-SA 4.0; attribution/license terms are retained in the reference catalog.

## Promotion and cleanup

Promotion to v1.0.0 is authorized by the user's scoped instruction, conditional on the passing automated checks above. The active module Notes field is exactly `v1.0.0`. Reference assets and design research are retained under `FPA/references/`; duplicate Roland manuals were verified byte-identical to copies already stored in the SM1084 delay reference archive. Invalid preliminary wiring-test scaffolding and duplicate core/staging files are excluded from the release.

Final source-pair and artwork SHA-256 values are in [`SHA256.json`](SHA256.json). Automated validation is repeatable with [`tests/validate.py`](tests/validate.py). Cherry Audio publication is deferred under the collection plan.
