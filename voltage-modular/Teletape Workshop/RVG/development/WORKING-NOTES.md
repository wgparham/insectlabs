# RVG 2.0.0 development integration — 2026-10-14

Candidate v2.0.0rc, not canonized. Canon remains 1.8.0. User-supplied Java was integrated into the working VMOD's complete generated scaffold, restoring control declarations and callback user regions. Originals are retained in integration-backup. Fixed unsupported GetSampleRate() to the 48 kHz host callback interval. Corrected automatic clock selections to reserve 479 remaining high samples after the first high sample is already emitted, so all trigger paths last 480 samples / 10 ms. Preserved supplied DSP otherwise, including tanh after the output level controls and third-held-value clock feedback. Updated the level tooltip to the actual tanh endpoint.

The user reports discussing the rackmount RVG with a former owner, who observed fewer very low/high voltages and attributed this to circuit compression/clipping or transistor limits. This is owner testimony relayed by the user; the mechanism has not been verified by schematic or measurement. The user explicitly chose to retain tanh for now. This implementation draws uniform bipolar samples and applies tanh after gain. Full level yields +/-2.5*tanh(1), approximately +/-1.903985 V. Compression removes the original extremes but does not produce a center-heavy density within the compressed range; equal-width bins near its ends have higher density. Do not describe the distribution or saturation mechanism as established EMS hardware behavior.

Variance multiplies the base rate by 10^((heldRateRandom-.5)*2*variance). At full variance the multiplier spans 0.1–10. It is not mean-normalized: its arithmetic expectation is about 2.15, and clipping at 0.05–500 Hz further changes statistics. Mean Rate remains 0.2–20 Hz with 0.5 V/octave CV. Compensation rates remain 0, .015 and .115 V/s. The saved switch default remains Factory/state 1.

Validation: exported and embedded source compile against installed SDK using --release 17 -Xlint:all -Werror. Actual extracted callbacks with minimal jack/control substitutes passed startup/manual/external/internal-clock 480-sample pulses, held-high gate/rearm, factory drift, output bounds, bypass silence and frozen held/clock/random state. Source agreement, binary round-trip and all non-code metadata preservation passed, except the intended Notes update. SHA256.json verified. Rebuilt malformed pre-existing embedded line endings and callback/readonly editor records; named section/control identities retained. Native Designer editing/export and audition pending.

Remaining standards review: control NaN sanitation, full functional-jack tooltips, rate coefficient caching, physical switch directions and recall policy. Bypass retains the supplied clear-pending-selection/trigger/lamp behavior. No publication/canonization/commit/push is claimed for this candidate.

## Normalized tanh comparison — 2026-10-14

At user request, tanh shaping is now normalized by 1/tanh(1), restoring +/-2.5 V endpoints for nominal held inputs. It precedes the Level controls, which now apply linear attenuation after shaping. Normalization is a static constant; no per-sample division by tanh(1). Clock, selection, hold, drift and gate behavior remain unchanged. Candidate remains v2.0.0rc. Earlier compressed-range notes are superseded for this comparison; normalization still does not create a center-heavy distribution from uniform samples.

Both source forms compile with Java 17 target and warnings as errors. Reflection checks of the compiled shaping helper passed endpoint, zero, symmetry, monotonicity and bounds across 10,001 positions. At endpoint, half level gives 1.25 V. Embedded/exported source agreement and unchanged non-code VMOD metadata verified; manifest refreshed. Native audition pending.


## Canonization — 2026-10-14

User auditioned normalized tanh, liked the result, and approved 2.0.0. Code/hero unchanged after approval; module Notes finalized as v2.0.0. Canon is now versions/2.0.0, superseding the earlier pending statements. Publication unconfirmed; 1.8.0 preserved.
