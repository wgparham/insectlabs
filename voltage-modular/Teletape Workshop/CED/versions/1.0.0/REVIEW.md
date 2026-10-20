# CED 1.0.0 release review

## Candidate and disposition

- **Release:** first canonical CED Chorus Ensemble Device, class `ced`, package `com.insectlabs.ced`.
- **Candidate basis:** user-supplied final working pair and panel. Cleanup renamed internal decorative label identifiers and display names only; panel text, control UUIDs, placement, skin, ranges and DSP are preserved. VMOD Notes is `v1.0.0`.
- **Release gate:** automated checks below passed. No native Designer/Voltage session or user listening record was available during this closeout; the stub run is explicitly not represented as a native host run.

## Checks

| Area | Result | Evidence and limits |
| --- | --- | --- |
| Java and VMOD | Pass | Both source forms compile against the installed Voltage SDK with Java 17 target and `-Werror`; VMOD binary tree round-trips byte-for-byte. |
| Pair and metadata | Pass | 49 controls, unique UUIDs, lowerCamelCase identifiers, 15 synchronized user regions; class/package and version Notes agree. |
| Wiring | Pass, stubbed host | W1–W7 verify dry/normalled paths, program and CV behavior, exclusive toggles in notifying and silent cases, bypass/resume, tooltip inversion and CE-1 routing. Local stubs cannot prove SDK notification or serialization behavior. |
| DSP | Pass, headless | T1–T7 check program sweeps/periods, opposite channel motion, delay centroids, combine laws and bounds, compander behavior, dry/null handling, nonfinite recovery, and CE-1 sweep bounds. |
| CPU | Measured, limited | Worst tested core path: ~1126 ns/sample, 5.41% of a 48 kHz core budget on this headless JVM. Not a host benchmark. |
| Native build/listening | Pending | No Designer/Voltage load or listening comparison was performed for this archive. |
| Publication | Deferred | Cherry Audio publication remains held until the full Teletape lineup is locked, per project direction. |

All code and DSP test output is available in [`tests/`](tests/); the bundled validation summary is `VALIDATION.txt`. The existing user-supplied panel preview is archived as `ced_hero.png`.

## Exceptions and future changes

Dimension service-note sweeps and periods are treated as source-backed targets; combinations and CE-1 delay/bandwidth character are modeling decisions. The software noise and compander are optional character controls. Before any DSP, control map, switch orientation, state persistence, bypass or SDK lifecycle change, repeat the full archived validation and check the exact pair in Designer.
