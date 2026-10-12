# RVG v1.8.0 release review

Date: 2026-10-12. User approval: the user confirmed the three-state compensation mode worked, tuned FACTORY/BLOWN droop to preference, and authorized canonization after checks pass. This is the first canonized RVG archive; no earlier release is overwritten.

## Scope

- Preserved the current 1.6-inch panel, public name/class/package identity, Designer UUIDs, switch defaults, tuned droop rates and audio behavior.
- Set the archived Designer Notes field to `v1.8.0` and cleared temporary control Notes after transferring the mode mapping to documentation. The saved compensation state remains 1 (middle/FACTORY); Inhibit remains 0 (Free Run).
- Added functional control/jack tooltips, removed unused notification cases, and stopped the GUI timer on destruction. These are packaging/maintenance changes, not changes to the approved random-voltage DSP.
- Moved the general Synthi handbook to `Teletape Workshop/references/`; retained RVG-specific links in `RVG/references/`.
- No DSP primitive was extracted and no test audio was generated for TestBench.

## Validation

- PASS: Designer project binary round-trip, embedded/exported source agreement, control identities, source display names, module/version metadata, defaults and cleared temporary Notes.
- PASS: exported and embedded sources compiled with `javac --release 17 -Werror` against the installed Voltage SDK.
- PASS: repeatable headless checks cover the three state mapping and one-second droop in both polarities, zero clamp, initial FACTORY state, startup selection and trigger duration, manual/external selection, gate re-arm thresholds, Inhibit, bypass silence/history freeze/resume, tooltip content, timer shutdown and one-time base destruction. Run `python versions/1.8.0/tests/validate.py`.
- PASS: release SHA-256 manifest and all Teletape Workshop local Markdown links were verified after the final archive was staged.
- USER HOST CHECK: the user built, loaded and auditioned the current tuned DSP in Voltage Modular, confirmed that FACTORY mode worked after the discrete switch-state mapping fix, and approved the instrument's sound. Tooltips, timer cleanup, version-note cleanup and packaging were completed after that listening approval; no new DSP calculation was introduced.
- NOT MEASURED: native-host CPU. Save/reload behavior was not separately exercised during this closeout. The simulation is fixed at 48 kHz; host sample-rate adaptation is not implemented.
- LIMITATION: the EMS RVG schematic and its measured droop values remain unknown. This is a workflow/response model, not a circuit-exact recreation.

## Archive and publication

Canonical archive: `versions/1.8.0/`. SHA-256 manifest covers the release artifacts except itself. Release commit `14d43d2fbefc2facba6f12c6d25e01cb369789f1` was pushed to `origin/main`; remote verification matched the local commit. The repository-wide checker confirmed the Teletape/RVG archive, links, manifests, and source pairs but exited nonzero for unrelated Wardenclyffe issues: 21-24eq and n01/sn16u manifest mismatches, missing 21-24eq and b08 release files, b08 canonical-index issues, and Wardenclyffe README links to those missing files. These unrelated paths were left untouched.
