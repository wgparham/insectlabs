# c2-34 Balanced Modulator 1.0.0 — canonical release

User-approved mono balanced modulator with BAL and UNBAL operating regions and a restrained, adjustable dirty zone. The BAL / UNBAL control moves continuously between carrier-suppressed four-quadrant modulation and a unipolar VCA. The center travel deliberately introduces a small amount of carrier or modulator feedthrough.

- [Designer project](c234.vmod) · [Java source](c234.java) · [Panel artwork](c234_hero.png)
- [User manual](USER-MANUAL.md) · [Review and validation record](REVIEW.md)
- [SHA-256 manifest](SHA256.json) · [Validation entry point](tests/validate.py)

Defaults: UNBAL/VCA mode and full Amplitude. X is the signal/carrier input, Y is the modulator/control input, and Z is the processed output. At +5 V on Y, both end modes reach unity for X; negative Y controls bipolar modulation in BAL and closes the VCA in UNBAL. Host bypass passes X directly to Z and ignores Y.

Build from this matched 1.0.0 project/source pair. The module is mono and processes at native sample rate; it adds no oversampling or separate transformer coloration.
