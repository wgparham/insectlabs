# RM1010 Mixing Amplifier — canonical v1.0.0

First release approved after the user-authorized code review, behavior-preserving cleanup and successful checks. Immutable source pair: [rm1010.java](rm1010.java) / [rm1010.vmod](rm1010.vmod).

[User manual](USER-MANUAL.md) · [Developer notes](DEVELOPER-NOTES.md) · [Review record](REVIEW.md) · [Repeatable tests](tests/validate.py)

Run the validator with Python and Java 17; the installed Voltage Modular SDK is an external dependency. It compiles both source forms and compares actual callback/core samples against the retained approved baseline using mocked controls. Host CPU and dedicated automation/undo profiling remain unmeasured. No new native-host post-cleanup audition is claimed.
