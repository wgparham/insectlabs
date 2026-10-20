# FPA 1.0.0 release review

Record: FPA — Four Pre-Amps; Teletape Workshop; new canonical module, no prior released baseline. Candidate DSP scope was limited to correcting Tone interpretation to match the existing panel/metadata range −1..+1 with center 0. Non-audible Designer display-name strings for labels were cleaned up; no panel layout, routing, bypass or artwork behavior changed.

## Evidence and checks

- **PASS** — exported `fpa.java` and embedded `.vmod` source compile against the installed Voltage SDK using `javac --release 17 -Xlint:all -Werror`.
- **PASS** — Designer binary round-trip, embedded/exported source parity, class/package identity, Notes `v1.0.0`, unique control UUIDs, four Tone metadata ranges/defaults and protected-line index bounds.
- **PASS** — exact nested `PreampCore` extracted from the release source passes `PreampCoreTest`: calibration within 0.5 dB at 11 gain points for each voicing, gain monotonicity, output ceiling, zero input, NaN/Infinity handling, phase polarity, signed Tone sweep, C/Re/D neutral center, O geometric cutoff midpoint, signed clamp bounds, mix scaling and channel independence.
- **PASS** — user confirmed Designer load/compile/run and basic functional operation on the candidate before the tone-only correction; the user explicitly authorized canonization if this narrowly scoped correction passed automated checks.
- **LIMITATION** — no separate native Designer build, saved-patch/undo check or listening test of the corrected tone response is recorded. The tone correction changes DSP interpretation from the previous clamp; only the user-authorized tone scope was changed.
- **LIMITATION** — sample-rate coefficients are fixed at 48 kHz. Alternate host sample rates, alias rejection under external measurement and native Voltage CPU usage are unverified. The timing output in the test is only a rough headless JVM estimate.
- **LIMITATION** — user-provided reference material includes an Electric Druid schematic comparison sheet marked CC BY-NC-SA 4.0; attribution/license terms are retained in the reference catalog.

## Promotion and cleanup

Promotion to v1.0.0 is authorized by the user's scoped instruction, conditional on the passing automated checks above. The active module Notes field is exactly `v1.0.0`. Reference assets and design research are retained under `FPA/references/`; duplicate Roland manuals were verified byte-identical to copies already stored in the SM1084 delay reference archive. Invalid preliminary wiring-test scaffolding and duplicate core/staging files are excluded from the release.

Final source-pair and artwork SHA-256 values are in [`SHA256.json`](SHA256.json). Automated validation is repeatable with [`tests/validate.py`](tests/validate.py). Cherry Audio publication is deferred under the collection plan.
