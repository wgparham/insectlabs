> Historical candidate record. Final approval: OFB 1.0.0, 2026-10-14. See REVIEW.md; earlier pending-audition statements are superseded.

# OFB warm-mixer comparison â€” v0.0.4rc

User requested gentle circuit-like saturation/compression on the eight-band mix on 2026-10-14. Clean selected HP/LP codebase is preserved at ../comparisons/clean-hp-lp-2026-10-14 with exact source/project/hero and verified hashes. No panel control is added. Current class remains ofb; open comparison builds separately. Not canonized or published.

## Voicing

The phase-correct linear band sum feeds a symmetric fourth-order soft knee: y=x/(1+(x/12)^4)^(1/4). Its small-signal gain is1, with no makeup gain, DC offset, envelope pump, noise or drift. Compression at mixer input5V is about0.7%; at12V about15.9%; at24V about50.8%. Asymptotic magnitude12V. This headroom/transfer is a chosen musical model, not a measured741 law or confirmed output swing. A reciprocal form above the knee avoids overflow. Finite/nonfinite mix protection before interpolation keeps FIR histories bounded, with +/-1,000,000V numerical protection outside ordinary audio.

Band HP/LP sections, component-derived tunings, Q, mix phase, log pots and gain smoothing are unchanged. Shaping acts on the aggregate eight-band sum, so strong/overlapping bands share headroom. The direct band outputs remain clean; they are delayed9 native samples to align with the main path. No separate tanh is introduced at the input or in individual bands.

## Oversampling and timing

Established dsp-primitives HalfBand19 coefficients/arithmetic are embedded as a private static class so Designer needs no external Java dependency. Two FIR calls interpolate the native sum to96kHz, two shaped values feed the decimator, and its even phase is emitted at48kHz. Small-signal DC normalization corrects the primitive's finite image leakage without makeup gain. Main linear resampling response has19-tap FIR ripple/finite stopband rejection; it is not a transparent infinite-order resampler.

Combined symmetric FIR nominal group delay is18 samples at96kHz=9 samples at48kHz (~.1875ms). Each direct tap gets an equal9-sample ring delay. Main and tap phases are aligned in the linear regime; main magnitude includes resampler response, taps do not. Bypass remains immediate direct input to main, taps silent; it freezes all filter/FIR/delay/smoothing histories. First active sample after reset/recall/bypass return clears all those histories and adopts current controls. Native controls persist, transient state is unsaved. Per-sample shaping is bounded/allocation-free. Actual native CPU has not been benchmarked; the prior tanh benchmark is not an exact cost prediction for this knee.

## Checks

Both source forms compile using --release17 -Xlint:all -Werror. Current tests extract the actual callbacks/core. Small-signal complex responses pass at63/125/250/500/707/1000/2000/4000/8000/12000Hz: raw taps equal the ideal digital HP/LP response with9-sample delay, main equals the alternating sum through the independently evaluated resampler. Shaping checks pass small-signal unity, odd symmetry, monotonicity, bounded helper output, gentle ordinary-level attenuation, driven compression and extreme finite values. Direct taps retain1x/5x input scaling, regardless of mix controls. DC/mute, bypass/frozen/no control reads, reset/resume and invalid-state/gain recovery pass. Latest numerical guard was checked with Double.MAX_VALUE input followed by zero, verifying finite output recovery.

One alias probe uses a24V-peak9kHz sine directly into the warm stage and measures the3kHz folded component after settling: native shape0.634085V vs2x0.012525V (~34dB reduction). It establishes improvement for that probe, not universal alias rejection. No native host or listening result is claimed yet. Panel geometry/art/UUIDs and all non-code metadata are unchanged except Notesv0.0.4rc. Current/backup hashes and project serialization/source agreement verified.

## Audition

Compare the clean backup and working warm variant at matched perceived levels. Start with ordinary input and moderate mix settings, then increase input/all-band levels. Listen for retained transient/band clarity and restrained shared-headroom rounding. Soft-knee headroom is MIX_HEADROOM_VOLTS=12.0 for tuning. Changing it is a new listening trial. If warmth is excessive, increase headroom; if only overload is affected, reduce it cautiously. Current native96kHz oversampled stage has a small alignment delay; all nonlinear sound is confined to main OUT.
