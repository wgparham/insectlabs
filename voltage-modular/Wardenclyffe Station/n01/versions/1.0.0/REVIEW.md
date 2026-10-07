# n01 Noise Source 1.0.0 release review

Approved 2026-10-01. The user tested v0.1.1rc, locked the darker voicing and slower SOURCE, and explicitly authorized code cleanup, canonization if checks pass, and GitHub publication. This authorization covers promotion after verified behavior-preserving cleanup without another listening gate.

## Review and changes

- Reviewed the latest development pair and final hero, including the SLOW RANDOM panel heading and revised positions. Exported and embedded code agree. No artwork, control positions, UUIDs, ranges, defaults, module identity or category changed during cleanup. Internal names are lowerCamelCase and Display Names human readable. Module Notes now state v1.0.0.
- Expanded single-line tooltip branches and removed empty user-region spacing. Audio processing, coefficients, arithmetic order, state behavior and helper calculations are unchanged. Preserved the Designer-generated scaffold. Destroy contains exactly one base call and one timer stop in the tested Designer order; no extra lifecycle code was introduced.
- WHITE and SPECTRA retain their approved dark voice. S&H SOURCE remains a continuous +/-5 V noisy ramp within 20-120 Hz. RATE/BLUE gently speed it, RED slows it, LEVEL increases timing variation. STEPPED holds exactly and advances only on a manual press or external rising edge. LEVEL does not change SOURCE/STEPPED voltage span.
- Reviewed mono routing, knob smoothing, sampling edges, trigger hysteresis, invalid input handling, lamps, held-voltage serialization and variation forwarding, units/inverse typed edits, startup, reset, extremes and bypass/resume. Utilities category is intentional for this combined noise/control-voltage source. There is no panel power switch or through-audio input.
- Bypass silences all outputs, skips controls and trigger reads, and freezes generator/filter histories. Resume adopts controls without restarting phases; already-high gates and presses during bypass do not cause spurious samples. This source has intentionally hard host bypass and retained filter state.

## Evidence

- Both Java forms compile against the Voltage Modular SDK, Java 17 target, warnings as errors.
- The callback suite passes, including output statistics, bounds, ramp coverage, source-rate limits/directions, trigger/hold behavior, no phase resets, state restore, lamps, bypass and typed edits.
- Actual approved callbacks are preserved as a test fixture. All five outputs match bit-for-bit over 480,000 samples (2,400,000 output comparisons) with deterministic seeds, moving controls, trigger events, manual presses, state restoration and bypass transitions.
- Source-pair round-trip, editor-anchor synchronization, Designer metadata comparison and release hashes are verified. No native-host CPU benchmark is claimed. The user's successful build/listening report applies to the approved candidate; no separate host test of the formatting-only cleanup is claimed.

## Deliberate limits

Native 48 kHz noise processing is allocation-free, with cached rate coefficient calculations and 40 Hz lamp updates. Noise coloring and rational compression use native-rate processing; the rough ramp reset is intended for sampling and can alias when auditioned as audio. This is a musical circuit-inspired source, not a precision white-noise calibrator or component-level model. Continuous random sequences are not serialized; held STEPPED voltage is. Designer-generated formatting remains Designer-owned.

The release contains tests, the approved callback fixture, final artwork, notes and checksums. APPROVED-BASELINE.json records pre-cleanup hashes. The repository indexes and roadmap identify this archive. No previous canonical n01 archive exists.
