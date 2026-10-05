# Selective Service — v1.0.0

**Canonical release approved 2026-10-05.** The user confirmed the module works and locked this version after testing.

Mono selective amplifier with matched band-pass MAIN and band-reject C outputs. GAIN (−24 to +24 dB) precedes both filters; AMP (−24 to +36 dB) controls MAIN only. MAIN has a ±20 V knee and ±24 V ceiling; C has a ±16 V knee and ±20 V ceiling. FREQUENCY covers 20 Hz–18 kHz, FINE ±2 Hz, WIDTH 2–100 Hz and SLOPE 1–12 poles per skirt. Defaults: OFF, 1 kHz, 2 Hz, four poles, gains at 0 dB.

The archive includes the approved panel, matched source pair, [user manual](USER-MANUAL.md), [design notes](DESIGN.md), [review](REVIEW.md), [validation evidence](VALIDATION.md), and repeatable checks in `tests/`. Run `python tests/validate.py` with Java 17+ and `C:/ProgramData/Voltage/voltage.jar`.

This archive is authoritative. Make later changes in a new development candidate.
