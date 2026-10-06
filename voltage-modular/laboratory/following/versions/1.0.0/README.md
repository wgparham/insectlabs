# Following — v1.0.0

Canonical first release of Dynamic Modulator Model 1998/4, approved after user testing.

Mono envelope/gate extraction with a shared driven input amplifier, post-gain audio tap,
detector-only 20–200 Hz high-pass, AUTO/MANUAL attack, program-dependent two-stage release,
true 0–3 s envelope delay, linear BALANCE, positive/inverted CV and independent gate lamps.

- [Designer project](following_2.vmod) and [Java export](following_2.java)
- [Comprehensive user manual](USER-MANUAL.md)
- [Release review and validation](REVIEW.md)
- [Control identity map](CONTROL-MAP.md)
- [Repeatable tests](tests/validate.py)
- [Artifact hashes](SHA256.json)

Power initially loads OFF, attack in AUTO, AMPLITUDE at unity, FILTER/ATTACK/DELAY at
minimum, BALANCE at immediate only, and THRESHOLD at 5 V. Turn power up and lower
THRESHOLD when testing gates with ordinary sources.

Finalization changes only label identifiers/Display Names, tooltip coverage, formatting
and Notes cleanup. Approved DSP arithmetic, routing, timing, voicing, artwork and defaults
are preserved. This archive is immutable; future edits belong in a new development revision.
