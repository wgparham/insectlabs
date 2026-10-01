# Deep Tone 1.0.0 release review

Approved 2026-10-01 after the user tested the final +/-65 Hz OFFSET candidate and declared it canonical. First canonical release; preceding auditions were v0.1.xrc.

## Scope and evidence

- Released Java is byte-for-byte identical to the latest approved export. Designer changes are limited to the module-level Notes version/release record; controls, defaults, UUIDs, editor anchors, embedded code and artwork are preserved. No DSP cleanup or retuning is included after approval.
- Matched embedded/exported source and lossless project round-trip are checked. Both source forms compile against the installed Voltage SDK targeting Java 17 with warnings as errors. The archived callback validator covers C4, split display/carry, tuning, typed edits/limits, independent latches, undo/state restore, switching, main levels/compression, FM, 200% non-inverting AM, Courtesy independence/polarity/zero hold, reset smoothing, extrema and direct bypass/frozen histories.
- Controls and jack tooltips describe units, routes and scaling. OFFSET remains fifth-power +/-65 Hz. AMP MOD remains 0-200%; Beat Depth remains 0-100%. All function buttons and main amplitude initialize off/zero; continuous Courtesy is intentional.
- Bypass reads only the enabled External path, copies it directly to main, silences Courtesy and freezes histories. Resume adopts controls, retains phases and clears previousRaw. The active external path rejects non-finite samples and clamps extreme inputs; bypass deliberately remains a direct copy.
- Audio processing is allocation-free. UI updates run at 20 Hz. No native host CPU benchmark was performed. The fixed sample-rate assumption is 48 kHz. Main character uses two midpoint/sample evaluations and averaging, not a complete bandlimited oversampling chain. High-frequency waveform edges and nonlinearities may still alias.

## Recorded standards exceptions

The approved prototype lived at the module root rather than development/. It is now archived under versions/1.0.0. Compact one-line UI/helper branches and descriptive numbered label identifiers remain as tested; frequencyDisplay_1 retains its Designer suffix. No post-approval style refactor or identifier rename is claimed. Display Names remain human readable. Destroy retains the tested Designer scaffold order (one super.Destroy call, followed by timer stop); changing that order is deferred to a future reviewed candidate. These are explicit exceptions, not a claim of complete stylistic conformity.

The user reported successful testing and approval, but did not enumerate every native GUI/save-reload checklist action in the final message. Automated state and control tests provide additional evidence without claiming a separate native-host test by the assistant.

## Archive

Source pair, current hero, documentation, validator and SHA-256 manifest are retained together. SHA256.json identifies release artifacts; APPROVED-BASELINE.json records pre-promotion artifact hashes. Canonical index, roadmap, collection changelog and setup/project indexes identify this release. Repository checks and archived-path validation are required before commit and push.
