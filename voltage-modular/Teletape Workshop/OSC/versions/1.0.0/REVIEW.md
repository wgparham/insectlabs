# OSC 1.0.0 - release review

Approval: 2026-10-17. The user said the instrument sounds great, confirmed the through-zero addition, and explicitly authorized canonization. Their latest Designer export already identified `v1.0.0` with a canon note. Comparison to the previous tested source found no user-DSP changes: generated ordering and skin metadata changed and are preserved.

Scope: local canonization and housekeeping. No audible redesign, publication or push. Controls, jacks, ranges/defaults, UUIDs, geometry, skins, assets and saved test material are preserved. Internal identifiers are made descriptive lowerCamelCase; output tooltips explain levels/DC/mix; compact callback/reset statements are expanded; all draft Notes move into documentation and Notes becomes exactly `v1.0.0`. Class/package/Category stay unchanged.

The approved and cleaned DSP were compared for 240000 deterministic frames / four outputs with varied audio-rate pitch, through-zero FM, three shape CVs, ranges, destinations, manual controls, drift, recall and bypass/resume. The output-bit checksum is identical; see tests/CLEANUP-PARITY.json. Final source/embedded SDK builds, metadata/source-pair checks, primitive parity, functional tests and spectral regression budgets pass. The final archive-path run is retained in VALIDATION.txt.

Scoped native-host exception: the user approved the working sound and authorized promotion; no separate native audition is claimed for the behavior-preserving identifier/tooltip/metadata cleanup. Native CPU, exhaustive GUI/save-reload tests and a fresh native re-export after cleanup are not claimed. The latest supplied native export was inspected; generated lifecycle calls and Designer anchors are validated headlessly. Oversampling, finite spectral limits and measured CPU scope are documented, not blanket claims of universal alias freedom.

Folder audit: shared original EMS documents remain under Teletape Workshop/references, with oscillator research retained at the module root. Unique prototype metrics and design history move to research/prototype-measurements. Reusable waveform/integration code moves to DSP primitives with exact parity checks; no useful retained audio needs TestBench migration. Superseded comparison pairs and redundant development copies/backups are removed only after final archive validation and hash checks. Development then points to canon.

Publication remains deferred. Unrelated blank-template, Sectronix, CXL and PVC work is excluded. No Git push is performed by this task.

Approved baseline SHA-256:

- `osc.java`: `3ab318734416e4fd859e27e99a9a1e1956b80e7d8b21eac7944b4e70dd635209`
- `osc.vmod`: `3a1e027a82b79c46fadf48e2619cc4c6259d4aaa5513996cf5dfb610807fdb58`
- `osc_hero.png`: `bb71ca6b0757f24bdf1b348d7d0845b6c4de2a04f0e4acb21ad9f61ab703cb87`

Cleanup result: all obsolete comparison/development files and Designer backups were removed after hash verification. Windows refused deletion of empty cloud directories (comparisons/2x-2026-10-17 and development/tests); other empty comparison directories may remain under that parent. No obsolete executable files remain, and empty directories are not Git artifacts. Locks were not bypassed.
