# RM1010 v1.0.0 canonical review

Date: 2026-10-08. First canonical release. User authorization: "this is canonized once you do the code review, cleanup, and checks." Scope: preserve approved audio and infrastructure behavior while completing cleanup and successful automated review. No separate cleaned-pair native-host test is asserted.

- Passed: current Java/Designer source agreement and artifact round-trip.
- Passed: both source forms compiled with Java 17 / all warnings treated as errors against installed Voltage SDK.
- Passed: routing, pair/send isolation, mute/LED behavior, RETURN/LINK, gain/taper, 900 Hz tilt, typed edits, preset/resume, direct bypass/frozen histories and hot-signal checks.
- Passed: baseline/cleaned callback output digest identical, 15974080979552226371.
- Passed: immutable control UUIDs, geometry, artwork, ranges, defaults and saved test state retained. Variable names, Display Names and working Notes normalized together.
- Preserved: user-added one-time LED extinguishing on bypass and finite input bound.
- Reviewed: base Destroy call occurs once; audio callback preallocates DSP state and uses control-rate parameter conversions; no new sound coefficients/arithmetic were introduced during cleanup.
- Exceptions: fixed 48 kHz callback rate, 2× 19-tap filtering, throttled audio-thread LED setters, approximately ±12 V stage limits rather than a final clamp. Runtime muted-meter remembered range is not separately saved in patch state.
- Pending measurements: native-host CPU, allocation/automation/undo profiling. Mocked callback tests do not replace those measurements.
- Reusable code: HalfBand19 preserved independently under dsp-primitives and exercised by standalone tests. No reusable audio file was rendered by these tests.

The immutable archive contains the exact final source pair, artwork, manual, developer notes, this review, repeatable tests/baseline, and SHA-256 manifest. Canonical publication follows final archive/repository checks.

Final archive-path validation and metadata/constructor agreement: passed. Baseline digest remained 15974080979552226371.
