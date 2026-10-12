# LM-21 Mk III v1.1.0 release review

Date: 2026-10-10. Baseline: canonical v1.0.1. Candidate/release: v1.1.0.

## Scope

Add a mono LINK submix input and 2D switch to the user-supplied panel. LINK is AC-coupled and adds dry at unity to FULL MIX, outside the four-by-four matrix. In 2D, LINK is sent into the existing channel D reverb after D's matrix, MIX, tone and character processing. DIST. D scales the send and the shared wet return; the wet LINK component is present at output D and enters FULL MIX through D's normal quarter-weight row contribution. No new reverb network is allocated. Existing matrix DSP, tone controls, power envelope and host bypass routing are otherwise unchanged.

The user confirmed on 2026-10-11 that this exact pair compiled, ran, and passed audio testing. Host CPU profiling remains unmeasured.

## Validation results

- **Source/Designer synchronization and project integrity:** passed. Embedded and exported source agree; the Designer project round-trips and line anchors validate. Module Notes are `v1.1.0`; all control working Notes were transferred into the manuals and cleared.
- **SDK compilation:** passed for exported and embedded Java with Java 27 targeting Java 17, `-Xlint:all -Werror`, and `C:/ProgramData/Voltage/voltage.jar`.
- **Automated callback validation:** passed. The harness covers 32 signed matrix routes, row independence, control edits, presets, power, bypass/resume, FULL MIX, MULT, nonfinite/unpatched inputs, short reverb response at three PERSPECTIVE points, and LINK dry/2D/DISTANCE routing.
- **Release hashes:** recorded in `SHA256.json` after all final changes.

The retained validator uses mocked SDK controls and does not emulate Designer, saved-patch automation/undo, GUI rendering or real-time scheduling. The v1.0.1 archive retains its three 30-second reverb tests; v1.1.0 shortens these repeated simulations to five seconds for practical runtime.

## Approval and remaining measurement

The user authorized canonization on 2026-10-10 and confirmed on 2026-10-11 that this exact pair was compiled, run, and audio tested successfully. Saved-patch/automation/undo checks and host CPU profiling are not recorded; no host performance claim is made.
## Archived files

The release contains the Designer project, matching Java export, current panel hero, user manual, developer notes, this review, validator and SHA-256 manifest. The previous canonical v1.0.1 archive remains unchanged.
