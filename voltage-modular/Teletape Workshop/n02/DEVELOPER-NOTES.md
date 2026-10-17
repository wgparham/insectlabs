# N02 developer notes — 1.0.0

Identity: class `n02`, package `com.insectlabs.n02`, Source category, 1.6-inch panel. Canonical pair and hero are under `versions/1.0.0/`. The user approved the working instrument on 2026-10-16 and authorized final checks, housekeeping, canonization and Git push. See [manual](versions/1.0.0/USER-MANUAL.md), [DSP design and sources](versions/1.0.0/DSP-DESIGN.md), and [review](versions/1.0.0/REVIEW.md).

## Design contract and source map

Two independent voices remain available at their dedicated outputs; the blend follows the user's D-left/A-right labels and uses both source LEVEL settings. DIGITAL uses 7/15/31-bit binary Galois LFSRs selected by SPEED. ANALOG uses a separate six-uniform SplitMix64 innovation, linear bandwidth shaping, and complementary colour filtering. EMS diagrams inform the block structure and tone model; their complete loaded circuits are not claimed as reproduced. The rejected manual 15/23/31 switch and earlier candidates are superseded; useful rationale is retained in DSP-DESIGN and the shared Research Compendium.

`processNoiseSample` sanitizes controls, caches frequency/blend conversions, smooths manual targets, selects the hysteretic word band, processes both voices and writes three outputs. `digitalSample` advances every LFSR at fractional clock edges on a 192 kHz grid and reconstructs with a 127-tap FIR. `analogInnovation` produces bounded centered stochastic samples. `initializeReconstruction` creates coefficients once. `GetTooltipText` and `EditComponentValue` present Hz and percentages with matching inverses.

## Control contract

Native SPEED and COLOUR ranges are -1..+1, default 0. SPEED maps exponentially to 20–48,000 Hz, while colour maps directly to dark/neutral/bright. Native LEVEL ranges are 0..1, default 0. Native MIX is -1..+1, default -1; digital share is `(1 - MIX) / 2`. Typed MIX is percent digital and maps back to `1 - 2 * percent/100`. The serialized `*ValueString` fields match the generated knob constructors and are checked in validation. Native UUIDs, skins, geometry, artwork, saved test state and defaults were retained from the latest user save.

Nominal automatic-length divisions are equal thirds of logarithmic SPEED travel, around 267.77 and 3585.12 Hz. Hysteresis is 1% full travel either side, and sequence weights crossfade over 240 host samples. Midpoint selects 15 bits. The analog innovation has unit RMS before filtering; the digital raw sequence is +/-1 V. Colour endpoints boost a branch by up to two, so output RMS/peaks vary by setting. There is no hidden limiting or gain compensation.

## State, safety and bypass

Mutable DSP history belongs to the audio callback. `AtomicBoolean adoptPending` coalesces recall/resume requests; the active word index is published through a volatile field for the tooltip. Inputs are manual only; nonfinite control values receive bounded fallbacks. Nonzero LFSR seeds avoid the zero trap. All three sequences advance even when not selected. Streams run even when LEVEL is zero. Analog seed initialization is per instance; digital initial seeds are fixed. Native controls are persisted by the host, while exact noise/filter histories are not serialized.

Host bypass writes zero to all outputs, skips controls and DSP, freezes histories and requests one adoption on return. Recall retains histories and adopts controls. Native reset changes controls without rewinding sources. `super.Destroy()` executes once. There is no physical power model or lamp.

## Cost and reuse

The audio callback allocates no objects. There are four internal digital ticks and one 127-tap decimating convolution per host sample, plus linear colour/bandwidth filters and six uniform innovations. Reconstruction latency is 63/192000 seconds (0.328 ms). No native CPU percentage or scheduling benchmark has been measured. The stateless LFSR step and supported feedback masks were copied into DSP primitives; this release retains its self-contained embedded step, and the harness verifies exact primitive parity. The model-specific reconstruction, gain and colour paths stay with N02. No test audio was generated or needed for this release, so no TestBench fixture was added.

## Validation and maintenance

The release harness compiles exported and embedded Java using Java 17 bytecode with the installed SDK, `-Xlint:all -Werror`, verifies lossless VMOD serialization, source agreement, exact Notes, unique UUIDs, generated/serialized knob ranges and archive hashes, and exercises actual user-code regions. It checks exhaustive 7/15-bit periods, algebraic 31-bit period, hysteresis, parameter extremes, FIR response, all source outputs, full MIX sweep and typed inverses, rapid transitions, bypass freeze, recall and invalid controls. It also checks the extracted primitive. The final MIX repair received automated checks; a new native GUI/audition session after housekeeping is not claimed.

For a revision, create a new candidate and preserve this archive. Recheck the physical knob direction, typed entry, word boundaries, reconstruction response, source bounds and bypass/recall behavior, then audition in Designer/Voltage Modular. Changes to source distributions, tone response or clock reconstruction are audible design changes requiring new approval.
