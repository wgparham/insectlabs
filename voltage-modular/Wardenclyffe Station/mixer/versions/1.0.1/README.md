# LM-21 Mk III — canonical v1.0.1

User approved the corrected panel and confirmed successful native-host build/run tests on 2026-10-08. This immutable archive contains the approved source pair, supplied hero artwork, operating manual, developer notes, release review, repeatable tests and SHA-256 manifest.

[User manual](USER-MANUAL.md) · [Developer notes](DEVELOPER-NOTES.md) · [Review](REVIEW.md) · [Java source](lm-21_mk3.java) · [Designer project](lm-21_mk3.vmod) · [Tests](tests/validate.py)

Run the validator with Python from any directory. For baseline comparison, pass --baseline pointing to tests/baseline/pre-cleanup.java.txt. Both source forms compile against the external installed Voltage Modular SDK. Tests use mocked SDK controls and actual extracted DSP/callback bodies; they do not replace native-host audition or CPU profiling. Host CPU and dedicated automation/undo profiling remain unmeasured.
