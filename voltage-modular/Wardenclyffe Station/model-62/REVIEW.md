# Model 62 v1.0.0 — Release Review

## Scope and identity

This release locks the final splice-scar reduction and the Model 62 naming. The final scar playback transform is `raw * (1 - .90 * scar) + .035 * scar + .006 * scar * noise()`. The magic-eye target remains reduced by 15% from the prior candidate. No panel geometry, routing, transport, controls, button lights, or other DSP changed during this final pass. Designer labels and image IDs were normalized to descriptive names; visible panel text and positions are unchanged.

The project identity is `model62` in `com.insectlabs.model62`; Designer Notes are exactly `v1.0.0`. The working and archived `.vmod` / `.java` pairs were synchronized after the metadata and source cleanup.

## Automated release checks

- **PASS** — both exported and embedded Java compile against the installed Voltage Modular SDK with Java 17, `-Xlint:all`, and warnings treated as errors.
- **PASS** — Designer binary parses and round-trips; embedded/exported Java agree; source anchor line entries are in range.
- **PASS** — control variable names, user-facing display names, monitor jack type, and release Notes are consistent.
- **PASS** — headless core and adapter checks cover the 33 mm head delay at 44.1/48/96 kHz, cell coverage at forward/reverse speeds, LOCKED and ELASTIC writing, stop/zero-speed behavior, power persistence, break/repair, multiple scars, state save/restore and corruption rejection, all spool bounds, wow/flutter speed relation, high-speed alias rejection, monitor routing, bypass freeze/direct pass, resume, typed edits, and patch restore.
- **PASS** — headless core throughput: 10 seconds of active audio processed in 0.281 seconds on this machine. This is not a native Voltage Modular CPU benchmark.
- **PASS** — SHA-256 manifest was generated from the release contents.

## Host check and limitations

The latest subtle scar adjustment has not been loaded and auditioned in the user's native Voltage Modular session. The user explicitly authorized canonization if the pre-publishing checks pass; the automated checks above pass. The supplied hero is the approved panel reference and was preserved; no claim is made that it is a fresh screenshot of the final DSP. Native UI rendering and native-host CPU profiling remain unmeasured.

## Archive and maintenance

The immutable release is `versions/1.0.0/`. Its source pair, hero, user manual, developer notes, tests, review record, and hash manifest are self-contained. The abandoned sketch folder and stale `wireRecorder` pair were removed after preserving the validated Model 62 test harness and the reusable cubic interpolation primitive in `dsp-primitives`.
