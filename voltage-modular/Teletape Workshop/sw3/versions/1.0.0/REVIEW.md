# SW3 1.0.0 Release Review

## Release record

- Module: SW3 Voltage Controlled Switch
- Baseline: user-supplied v0.1.7 Java/Designer pair and panel artwork
- Candidate/canonical version: 1.0.0
- Scope: preserve DSP and panel behavior; set the Designer Notes version marker, archive the approved pair, document behavior, and remove superseded working revisions after validation
- User authorization: canonize v0.1.7 as 1.0.0 if checks pass; discard superseded development versions
- DSP changes: none

## Review results

- Code identity, package, switch category and 1.6-inch width: passed
- Source pair and Designer serialization: passed by `tests/validate.py`
- Java exported and embedded source compile: passed against `C:\ProgramData\Voltage\voltage.jar`, Java 17 target, all lint warnings treated as errors
- Core behavior: passed 508 SDK-independent checks for FADE endpoints, inverse/monotonic mapping, Schmitt thresholds, momentary and latched switching, 5 ms minimum gate pulse, X/Y route separation and normalization, finite values, state save/restore, hard/faded switching, bypass and resume
- Primitive copy: passed exact normalized source parity against the module's `Sw3Core`
- UUID uniqueness and Designer source-line anchors: passed
- User-supplied artwork: retained unchanged
- Repository-wide check: SW3-specific checks pass; the repository checker reports pre-existing unrelated local mismatches in FPA, the blank template and SN93T, plus an SM1084 missing image and an SN93T broken link. Those files are outside this release and were left untouched.
- DSP parity against a prior canonical release: not applicable; this is a new module and the DSP was not modified during promotion
- User's Designer/Voltage Modular audition: the set was supplied as the working set; this review did not launch the native applications or independently repeat that listening check
- CPU benchmark: not run; this scalar routing core has no audio-thread allocation, and no timing result is claimed

## Reviewed behavior and limits

At 48 kHz, external gates rise at 3.0 V and release below 1.8 V. Button and gate signals are OR-combined for gate output and XOR-combined for momentary route selection; latch mode toggles on rising edges. Gate outputs are 10 V during an active gate/button and are extended to at least 5 ms after a rising edge. FADE is exponential from 0 to 200 ms. In active operation, unpatched X1 and Y In use their reference trimmers, while unpatched X2 is 0 V.

Host bypass follows primary direct routes: connected X1 to X Out and connected Y In to Y Out 1, with Y Out 2 and gate outputs at 0 V. Unpatched primary inputs also produce 0 V during bypass instead of reading the reference trimmers. Stored latch states survive bypass; gates and fade ramp state are re-primed on resume. This is intentional and covered by tests.

## Release artifacts

- Source pair: `sw3.java`, `sw3.vmod`
- Artwork: `sw3_hero.png`
- User instructions: `USER-MANUAL.md`
- Developer handoff: `DEVELOPER-NOTES.md`
- Automated validation: `tests/validate.py`, `tests/Sw3CoreTest.java`
- Shared reusable core: `switch-primitives/src/com/insectlabs/switches/Sw3Core.java`
- Canonical release commit: `4cc1ac5`; pushed to `origin/main` successfully.
- Cleanup: development archive verified; superseded source revisions and backups removed after the final checks
