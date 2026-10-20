# CXL 1.0.0 release review

## Record

- **Baseline:** user-approved extended-character audition, 8.5-bit effective converter step / divisor 180, DECAY 3–30 s; Dattorro-family tank and supplied panel.
- **Candidate:** CXL 1.0.0, class `cxl`, package `com.insectlabs.cxl`.
- **Scope:** first canonical archive. DSP coefficients, signal flow, panel text and geometry remain as auditioned. Canon cleanup standardizes decorative label/image identifiers and display names, sets module Notes to `v1.0.0`, removes working Notes from controls, and archives research/test material. Component UUIDs and saved Designer state are preserved.
- **Approval:** user accepted the 8.5-bit result and authorized canonization on 2026-10-18. The audio DSP was unchanged after that approval. Native audition details are limited to the user's confirmation; no separate native-host measurement log was supplied.
- **Archive:** `versions/1.0.0/`; see `SHA256.json` for immutable content hashes.

## Checks

| Gate | Result | Evidence / limits |
| --- | --- | --- |
| Source and Designer project | Passed | Java 17-target full compilation with installed SDK and warnings as errors; embedded source parity after normalizing Designer anchors; parse and binary round-trip. |
| Panel/metadata | Passed | UUIDs, positions, artwork, on-panel strings, knob/jack/switch ranges and saved test state retained. Descriptive label names changed without changing layout or behavior. Module Notes is `v1.0.0`. |
| DSP checks | Passed | Delay, pre-delay, all-pass energy, finite/stability sweep, converter error comparison, raw 500 Hz decay fit and CPU benchmark. Full results and methods in `tests/DSP-TEST-REPORT.md` / `VALIDATION.txt`. |
| Native sound approval | Passed | User approved the audible 8.5-bit midpoint and CXL operation. This approval predates only metadata/identifier cleanup; no DSP changes followed. |
| Bypass and lifecycle | Documented / limited | Source path directly routes inputs and freezes effect histories; resume preserves tail and fades wet in. Reset/recall clear transient state. Harness does not emulate Designer automation, preset migration or host scheduling. |
| CPU and aliasing | Qualified | `PlateTank.process` benchmark is not whole-module host CPU. 8× exciter comparison is an alias proxy, not isolated alias energy. No oversampling is used. |
| Research and rights | Passed | Supplied EMT manuals/brochure, Dattorro paper and design scratch retained under CXL. Attribution/technical conclusions are in `../../RESEARCH.md`; no external implementation code was copied. |
| Publication | Deferred | Cherry Audio publication remains held until the full Teletape Workshop lineup is locked, as directed by the user. |

## Exceptions and follow-up

The project uses a floating-point Dattorro-family tank rather than a transistor/capacitor model. The 8-bit panel label does not describe a literal fixed 8-bit quantizer: the approved extended mode uses divisor 180 to balance grain and sustained-tone usability. The shortest configured decay fit was 0.467 s for 0.8 s at 500 Hz; longer test points were closer to nominal. Treat UI decay values as nominal settings and retain the measurements' stated limits.

No unresolved release blocker remains for the approved design. Future changes to tank topology, control mapping, converter step, stereo fold-down, bypass/resume or persistence require a new candidate and renewed tests/listening.
