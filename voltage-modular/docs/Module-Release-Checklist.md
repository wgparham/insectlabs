# Module release checklist

Use this checklist for every InsectLabs Voltage Modular release. Keep a completed, module-specific
copy in the active development folder as `REVIEW.md`, then retain it with the approved release.
Link evidence or record a specific reason for each exception. A build that compiles is not a completed
standards review. Approval of sound does not imply approval of a subsequently modified source pair.

## 1. Establish the candidate

- [ ] Identify the current canonical version, source pair and hashes. Preserve that archive unchanged.
- [ ] Read the latest user-supplied `.vmod`, Java export and artwork. Check for changes made during review.
- [ ] Work in `development/`. Record the proposed version and distinguish it from the canonical release.
- [ ] Set the Designer module Notes field to the exact current version before development proceeds.
  Use `v<version>rc` for a release candidate and the released `v<version>` after promotion. Validate
  the Notes field against the intended archive version before each build, release, and cleanup.
- [ ] Consult [Colorbox conventions](../colorbox/STANDARDS.md) and
  [infrastructure standards](Module-Infrastructure-Standards.md). Identify applicable exceptions.
- [ ] Record what may change: cleanup, UI corrections, DSP changes, or a combination.

## 2. Code and Designer review

- [ ] Give controls, jacks, indicators, labels, state fields and helpers descriptive lowerCamelCase names.
  Use UPPER_SNAKE_CASE constants and retain SDK callback capitalization.
- [ ] Keep Internal Names and Variable Names in lowerCamelCase; **Display Names are human readable**,
  with spaces and appropriate capitalization (for example, `dutyCycleKnob` → “Duty Cycle”). Apply
  this to controls, jacks, indicators and labels in both Designer metadata and source constructors.
  Check the resulting native tooltips and automation labels; do not expose code identifiers as Display Names.
- [ ] Expand compressed helpers; use consistent four-space indentation and readable control flow.
  Remove unused notification cases, dead scaffolding and stale comments without changing Designer regions.
- [ ] If control names change, update Java and Designer control metadata together. Preserve UUIDs,
  package/class identity, artwork, positions, ranges, defaults, saved test state and unrelated metadata.
- [ ] Check the module Category against its purpose in both Designer and Java (for example, Generator → Oscillators).
- [ ] Check physical switch directions, position numbers, defaults and jack routing against the panel. Check numeric and text/serialized default fields together; test calibrated startup values after a real Designer export.
- [ ] Check notes inside Designer as well as external docs; remove obsolete draft behavior descriptions.
  Display Names and Notes may be rewritten or erased as needed without separate permission. They
  are working design material unless the user explicitly marks particular contents for preservation.
- [ ] Give every functional control and jack a useful tooltip. Verify low-frequency precision, units,
  mode-dependent meaning, pre/post-processing levels and reference switch destinations.
- [ ] If tooltips display converted units, implement and test inverse conversion for typed edits through
  the SDK edit path. Check finite inputs, limits and mode changes; preserve SDK undo behavior.

## 3. Infrastructure and DSP review

- [ ] Verify mono/independent-stage routing, normalization and intended channel independence.
- [ ] Verify host bypass skips control/CV/DSP work, preserves intended routing and freezes histories.
  For generators, document silent bypass. State and test the intended resume behavior.
- [ ] Verify power, initialization, reset, saved-patch restoration and mode transitions.
- [ ] Review generated lifecycle code after a real Designer export. Ensure each base callback (especially `super.Destroy()`) executes exactly once; do not duplicate a generated base call inside a user region.
- [ ] Review manual smoothing separately from external/audio-rate CV. Do not add smoothing where the
  user intentionally accepted immediate control behavior.
- [ ] Check sample-rate assumptions, bounds, silent/unpatched inputs, extreme settings, polarity and DC.
- [ ] Review per-sample allocations, expensive operations and meter/UI updates. State actual CPU evidence;
  compilation or arithmetic counts do not constitute a benchmark.
- [ ] For nonlinear DSP, record native-rate/oversampling choice and its aliasing/CPU tradeoff. Preserve
  approved coefficients and arithmetic order during cleanup; propose audible changes separately.
- [ ] Document deliberate exceptions explicitly instead of silently declaring blanket compliance.

## 4. Automated validation

- [ ] Synchronize embedded and exported source; verify Designer anchors and artifact round-trip integrity.
- [ ] Compile **both** source forms with the installed Voltage SDK, Java 17 target and warnings as errors.
- [ ] Verify control metadata and artwork against the approved panel; changes must be intentional.
- [ ] Run relevant routing, signal, boundary, transition, bypass/resume and tooltip checks.
- [ ] For behavior-preserving cleanup, compare actual old/new DSP callbacks sample-for-sample across
  representative controls, modes, connections and transitions. State any intentional differences.
- [ ] Keep repeatable validation scripts in the module's `tests/` directory and report their limitations.
- [ ] Update artifact hashes after the last change. Verify again if any source/project/artwork changes.
- [ ] Add broadly reusable audio fixtures to TestBench only when needed, with provenance and checksums.

## 5. User build and listening gate

- [ ] Deliver the exact candidate `.vmod`/`.java` pair with paths and a concise change/test summary.
- [ ] User confirms the cleaned candidate builds, loads and runs in Designer/Voltage Modular.
- [ ] User checks affected controls, tooltips, switches, saving/reloading and short listening tests.
- [ ] Record approval for this exact candidate. If either side changes it afterward, revalidate and identify
  whether another user check is needed. Do not promote an untested cleanup using approval of its predecessor.

If the user explicitly authorizes cleanup followed by promotion after successful checks (as for n01), record that authorization and its scope. For behavior-preserving cleanup, retain automated parity evidence and clearly state whether the exact cleaned pair received a separate native-host check. This exception is not permission to make new audible changes under an earlier approval.

## 6. Canonical archive and publication

- [ ] Before final release validation, transfer useful working Notes into external documentation, then remove all Notes except the exact module version and text explicitly designated to stay. Recheck source-pair integrity after metadata cleanup.
- [ ] Only after the candidate is approved, create its immutable `versions/<version>/` folder containing
  the source pair, artwork, README/release notes, review record and SHA-256 manifest.
- [ ] Use a patch increment for cleanup/small corrections; do not overwrite the previous canonical version.
- [ ] Update `CANONICAL.json`, collection README/changelog, roadmap, setup docs and affected project indexes.
  Check module counts, next-work statements, links and Markdown tables.
- [ ] Inspect the complete Git diff and status; do not include unrelated user work, SDKs, caches or backups.
- [ ] Run `python tools/check_repository.py` after staging the proposed archive. Run its tests from the final archive path before removing working copies.
- [ ] Stop on any failed validation, commit or push command; do not continue a command sequence after a failure. Preserve canonical Java bytes, including Designer line endings; distinguish CRLF warnings from functional errors.
- [ ] Commit and push within the user's authorized scope; verify the remote result and clean local status.

## 7. Cleanup and handoff

- [ ] Compare archived files and hashes before removing duplicate development copies.
- [ ] Retain source, artwork, validation, approval evidence and unique references. Remove obsolete backups,
  generated classes and disposable staging files only after verifying their final destinations.
- [ ] Resolve deletion targets under the intended workspace; inspect links/reparse points and process locks.
  Never bypass a lock or deletion refusal with an unsafe alternate shell.
- [ ] Do not delete an active Designer project or claim all local cleanup succeeded when files remain locked.
- [ ] Report canonical version, GitHub location, validation outcome, repository status, any cleanup remainder,
  and the next proposed module. Do not start its implementation without the next brief.

## Review record header

Record: module; baseline version/hashes; candidate version/hashes; scope; code/metadata changes;
DSP comparison result; SDK results; reviewed exceptions; outstanding user checks; approval date;
release path; commit/push result; cleanup status. Use `pending`, `passed`, or `not applicable — reason`
honestly for each gate. A development review may be complete while promotion remains pending.
