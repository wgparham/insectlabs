# Selective Service — release review

Date: 2026-10-05. Canonical release: **v1.0.0**, approved by the user on 2026-10-05 after successful testing.
No previous canonical release exists. Baseline is the user's latest working pair and hero,
preserved under `references/user-panel-before-1.0.0rc/`.

## Scope and results

| Gate | Status / evidence |
|---|---|
| Current user files and Notes read | Passed; artwork reviewed and original hashes recorded. |
| Requested sound changes | GAIN ±24 dB; AMP −24/+36 dB; MAIN ceiling 24 V/knee 20 V; C ceiling 20 V/knee 16 V. |
| Noon and startup values | Passed; both gains 0 dB, OFF, 1 kHz, 2 Hz width, slope 4 retained. |
| Designer controls and artwork | Passed; user positions/skins/labels/UUIDs preserved. Category remains Filters. |
| Naming and readable code | Passed; descriptive identifiers, shared headroom constants, expanded control flow, four-space user-code indentation. |
| Tooltips and typed edits | Passed; converted dB values and inverse AMP mapping match new ranges. |
| Paired source/editor integrity | Passed; embedded source synchronized with named anchors preserved; binary round-trip and SDK compilation pass. |
| Lifecycle | Passed; single base Destroy call, GUI timer stopped; reset remains audio-thread deferred. |
| Routing / power / bypass | Passed; main/notch topology retained, 10 ms panel fade; exact raw splitter host bypass. |
| Signal and boundary checks | Passed; see VALIDATION.md and tests/validate.py. |
| Cleanup parity | Passed; 120,000 bit-identical samples versus post-gain-change/pre-cleanup baseline. |
| Audio-thread allocation/UI review | Passed; no new audio-thread allocations or GUI formatting; coefficients cached at static settings. |
| CPU evidence | Local core microbenchmarks recorded; host CPU remains a user check. |
| Documentation | Current user manual, design notes, README and validation updated. |
| TestBench | The new 1 kHz neighbor-selectivity calibration fixture is cataloged and checksum-verified in TestBench v1.3; TestBench validation passes 282 checks across 59 files. |
| Final native build/listen/save/reload | Passed; user confirmed it works and locked it as canonical. |
| Canonical archive | Completed as v1.0.0. Git publication is recorded below after push. |

## Deliberate exceptions and limits

- Host BYPASS sends raw S to both outputs; C is deliberately active in bypass, per the user.
- The native-rate output ceilings retain the approved topology. They can generate aliasing when
  driven with high-frequency material; adding 2x processing now would be an additional sound change.
- Slope is poles per skirt, not total transformed bandpass pole count. Finite transition overlap is documented.
- Very narrow filters ring. Extreme rapid tuning can pump internal energy; output bounds and finite-state
  tests pass, without claiming clean behavior under that pathological stress.
- Useful design notes are in DESIGN.md and USER-MANUAL.md. Designer Notes are exactly `v1.0.0`.

## Canonical release and cleanup status

The user approved the module after successful build and testing. Promotion changed Designer Notes to `v1.0.0` and updated one source comment; no executable DSP code changed, and the full validation was rerun. The archive contains the matched pair, artwork, user manual, design record, review, validation, tests and checksums. Collection indexes and roadmaps identify the release. Duplicate development sources were removed after hash comparison; distinct editor backups and prior design references were retained under `references/`.

## Canonical artifact hashes

- `selective_service.java`: `224d1048a3a7e22cfa16a3438db78643b47395bfdb15ab62d3b41747974e5049`
- `selective_service.vmod`: `9192d03f24886fecaa8fcdb735456ca27cfcfd53cc54d39afa1d39963981aa1b`
- `selectiveService_hero.png`: `50844022ade02acdb18821d07b6c67f453ce17a0859ce76dd8abb5ed6bd239bd`


## Canonical release record

- Approved version: 1.0.0; approval received 2026-10-05.
- Canonical archive: `versions/1.0.0/`.
- Designer Notes: `v1.0.0`.
- SHA-256 values: `SHA256.json`.
- Published commit: `3b4f142ab330bfac0fbf1a8919ab2e5fd44f345e`, pushed to `origin/main` on 2026-10-05.
