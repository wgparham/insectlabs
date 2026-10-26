# SN93T 1.1.0 release review

## Scope

Promoted from the author-tested Designer candidate. Version 1.0.0 remains preserved unchanged. This update corrects external jack scaling for TONE and Cents CV while retaining detector sensitivity and the pitch/CAL mappings. The control UUIDs, geometry, panel layout, and analysis core are unchanged. Designer refreshed the module-level skin ID and TTW logo resource ID; those IDs are preserved from the user-tested candidate.

## Evidence and checks

- **PASS** — author confirmed the pair loads, compiles, runs, and functions in Voltage Module Designer.
- **PASS** — exported Java and embedded VMOD source are equivalent; the Designer binary round-trips byte-for-byte; module class/package/Notes and 103 unique control UUIDs are valid; protected line anchors are sorted and in range; detector, THRU, TONE, CENTS, pitch-CV and CAL scaling remain connected to their expected maps; panel control positions and sizes match v1.0.0.
- **PASS** — candidate Java compiles against the installed Voltage SDK with Java 17, all lint warnings treated as errors.
- **PASS** — deterministic tuner tests: 17 pitch points from 31 Hz to 2.2 kHz; four harmonic mixtures; silence/no-signal/HOLD behavior; tone endpoint/frequency; NaN/Infinity handling; three AnaMark tuning files; Scala scale parsing; and a synthetic CAL sweep.
- **PASS** — tone output reaches 5.0 V peak at 0 dB control and 440 Hz; the v1.0.0 core test suite passes unchanged. User-provided change record reports 52/52 wiring checks and 216/216 core checks.

## Limits

The automated signal tests do not replace the author's native Designer audition, nor do they establish broad real-instrument tracking, alternate sample-rate behavior, or native-host CPU use. Publication remains deferred under the Teletape collection plan.
