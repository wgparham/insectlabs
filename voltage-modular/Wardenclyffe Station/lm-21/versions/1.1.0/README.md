# LM-21 Mk III — canonical v1.1.0

Canonized at the user’s request on 2026-10-10. This release adds a dry LINK submix input to FULL MIX and a 2D mode that sends LINK through channel D’s shared reverb network. The wet return appears at D and in FULL MIX; LINK dry remains outside the matrix.

The source pair, current hero panel, user manual, developer notes, release review, validator and SHA-256 manifest are archived together.

[User manual](USER-MANUAL.md) · [Developer notes](DEVELOPER-NOTES.md) · [Review](REVIEW.md) · [Java source](lm21mark3.java) · [Designer project](lm21mark3.vmod) · [Hero artwork](lm-21_hero.png) · [Validator](tests/validate.py)

Both source forms compile with Java 17 targeting Java 17 and warnings as errors; automated callback and routing checks pass. The user reports this exact pair was compiled, run, and audio tested successfully. Host CPU profiling is not recorded.
