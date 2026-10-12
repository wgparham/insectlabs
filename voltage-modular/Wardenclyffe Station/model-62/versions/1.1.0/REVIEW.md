# Model 62 v1.1.0 â€” Canonical Release Review

## Scope and behavior

This working candidate adds a latched **High Fidelity Mode** button, ON by default. ON preserves the existing v1.0.0 voice. OFF selects **War-Worn**: the input recording path adds a second restrained drive stage and 2.5Ã— record noise; playback of any spool adds subtle hiss and another saturation stage, while wow depth doubles and flutter depth increases fourfold. The modulation rates remain tied to transport speed. Added noise and drive written while War-Worn is selected remain on the wire after switching back to High Fidelity. MONITOR remains input-only after initial gain staging.

Designer Notes are `v1.1.0`; the button has a human-readable display name and notes matching the behavior. The VMOD embeds the current Java source. The original `versions/1.0.0/` tracked release pair has been restored from repository HEAD and left unchanged.

## Automated checks

- **PASS** â€” exported and embedded Java compile against the installed Voltage Modular SDK with Java 17 compatibility, strict lint, and warnings as errors.
- **PASS** â€” VMOD parses and round-trips; embedded and exported source match; Designer control names, display names, version, default button state, and source anchors agree.
- **PASS** â€” headless core and adapter tests cover the prior transport, write, save/restore, break/repair, bypass, and routing behavior, plus the new mode default, button toggling, write imprint, playback treatment, and added saturation.
- **PASS** â€” headless core throughput was 10 seconds of active audio in 0.526 seconds on this machine. This is not a native Voltage Modular CPU measurement.

## Button correction checks — 2026-10-11

- **PASS** — standard square 2500 skin has dark idle and lit pushed artwork; panel geometry is preserved.
- **PASS** — both source forms compile with Java 17 compatibility, strict lint and warnings as errors; VMOD round-trip, source synchronization, metadata and line anchors pass.
- **PASS** — installed VoltageButton SDK probe verifies GUI-update flag and non-notifying writes.
- **PASS** — release events preserve mode/PLAY/REC lamps without repeating actions; mode survives knob edits and bypass; saved-mode and legacy-wire recall, invalid-state rejection and reset pass.
- **PASS** — existing core, War-Worn, recording, routing and bypass checks pass. The WireCore DSP was unchanged by the correction.

## Native test and approval

**PASS — user confirmed all buttons work in Designer and canonized v1.1.0 on 2026-10-11.** The previously approved sound remains unchanged. Promotion removes working Designer Notes and updates release documentation only; the Java DSP is unchanged. Native-host CPU was not measured. Legacy-state and mode-save checks are automated; native save/reopen was not separately reported.

Canonical archive: `versions/1.1.0/`. Previous archive: `versions/1.0.0/`, preserved unchanged. Git publication status is recorded in the final handoff.

## Final archive check

Post-promotion strict SDK compilation and all behavioral checks passed. Root and archived source pair match byte-for-byte; v1.0.0 matches Git HEAD. Repository-wide checker is blocked by the absent tracked `6121198a/versions/1.0.0/SHA256.json`, outside this release. Changes are local; this promotion has not been committed or pushed.
