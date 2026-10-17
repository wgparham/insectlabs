# N02 1.0.0 — automatic sequence length

## Digital voice

Three right-shifting XOR Galois LFSRs share a continuously variable clock. Feedback masks are `0x60` (7 bits), `0x6000` (15 bits), and `0x48000000` (31 bits). Nonzero startup states avoid the XOR generator's zero trap. Each clock advances all three banks, including inactive banks. Their raw output bits map to -1 V and +1 V; word length changes sequence duration, not amplitude resolution. The selected bitstream transitions over 240 host samples. A new selection during a transition starts from the current weights, preserving their sum of one.

These masks correspond to the 7/6, 15/14 and 31/28 primitive polynomial terms. The [Xilinx XAPP052 table](https://docs.amd.com/api/khub/documents/F9osNcU4E9VyJU6VdodOdA/content) is a later tap reference; its illustrated XNOR/Fibonacci form should not be copied into this XOR/Galois implementation without translating the convention. Maximal-length shift-register methods themselves predate that note; a [1971 musical-instrument patent](https://patents.google.com/patent/US3629482) describes their musical use. N02 is not a recreation of a particular sound chip.

The clock spans 20–48,000 Hz exponentially. SPEED selects sequence length automatically over equal thirds of its logarithmic travel:

| Nominal region | Clock | Sequence | Approximate repeat range |
| --- | --- | --- | --- |
| Low third | 20–267.77 Hz | 7 bits / 127 steps | 6.35–0.474 seconds |
| Middle third | 267.77–3585.12 Hz | 15 bits / 32,767 steps | 122.37–9.14 seconds |
| High third | 3585.12–48,000 Hz | 31 bits / 2,147,483,647 steps | 6.93 days–12.43 hours |

These are nominal divisions, not hard discontinuities. Selection follows the smoothed clock with hysteresis equivalent to 1% full knob travel on each side. Rising transitions occur around 289.45 Hz and 3875.31 Hz; falling transitions occur around 247.72 Hz and 3316.67 Hz. Within a hysteresis band the current sequence remains selected. The SPEED tooltip reports the active length so the repeat interval can be calculated as `(2^bits - 1) / clockHz`. At initialization, recall or bypass return, the current native SPEED setting selects its nominal band directly; the midpoint is approximately 980 Hz with 15 bits.

The predictable repeating click pattern at slow SPEED is an intentional consequence and benefit of the simpler panel. The former word-length switch is removed. All three banks still advance on every clock; changing regions does not reseed or restart a sequence. Filtered output can sound less obviously periodic, but the underlying sequence remains deterministic. Fixed startup seeds make repeatable comparisons possible; recalling presets does not rewind them.

Clock phase runs at an internal 192 kHz rate, independent of integer host-sample intervals. Each internal sample averages the held bit values across any fractional clock edge. A normalized 127-tap Blackman-windowed sinc low-pass at 18 kHz reconstructs and decimates to the 48 kHz host. The FIR contributes 63 internal samples (0.328 ms) group delay. Its amplitude response at 24 kHz is checked below -60 dB, and its 10 kHz response above 0.99. This finite filter reduces aliasing; it is not an assertion of mathematically perfect band limitation. No CPU percentage has been measured.

A 5 Hz DC blocker precedes the digital colour network. Lower clock rates deliberately produce chatter and held pulse textures, with different bandwidth and loudness; there is no automatic level normalization across SPEED settings.

## Analog voice

Local circuit evidence is in [Synthi 100 schematics](../../../references/ems_synthi_100_sch.pdf), PDF page 20, drawing EMS 32/07, and [Synthi AKS service manual](../../../references/emsa_synthi-aks_sm.pdf), PDF pages 61–62. These show noise-source, amplifier and tone-network blocks. The AKS service notes specify a selected wideband noise source, reject lumpy/crackly devices, and describe a roughly 3 V peak-to-peak service adjustment. That test reading does not establish a universal bound for an arbitrary noise waveform.

In the approved model, six centered uniform innovations from a separate SplitMix64 stream approximate a Gaussian amplitude distribution. Their sum is scaled to unit variance before filtering. This is an implementation choice for a noise-like analog excitation, not a simulation of semiconductor physics. A linear 16 kHz Butterworth low-pass and 20 Hz DC blocker establish finite bandwidth. There is no added hum, drift, clipping or generic saturation.

The colour network on each voice uses complementary low/high branches around approximately 1.254 kHz, informed by 2.7 kOhm and 47 nF values in drawing 32/07. The implemented transfer is `x + colour * (x - 2 * lowpass(x))`: neutral is the incoming signal, dark is twice the low-pass branch, and bright is twice its complementary high-pass branch. This simplifies the complete loaded EMS passive network and does not reproduce its exact pot taper, gain or response. The digital side deliberately uses the same colour interface for comparison. The user approved this response; future fuller circuit models require a new scoped revision.

## Gain, blend and state

The analog innovation has nominal 1 V RMS before bandwidth/colour shaping; the digital raw source is +/-1 V. These are model gain choices, not a historical EMS output calibration. Filtering changes RMS and peaks, and the colour extremes can boost a branch by up to two. No hard audio limiter is inserted. LEVEL scales each conditioned source, then BLEND uses cosine/sine gains for approximately constant power with uncorrelated, equally loud sources. It is a crossfade rather than a sum-level control.

Controls are sanitized and bounded before use. Continuous controls smooth over approximately 5 ms; expensive clock and blend conversions are cached until their targets change. The audio callback allocates no objects. Initialization builds filter coefficients and seeds the analog stream per instance. Bypass freezes all DSP histories and outputs zero; return adopts current controls. Preset/variation recall retains transient noise/filter state and adopts native controls. Native reset changes controls without reseeding the streams. Noise streams are not custom serialized.

## Validation and listening

Run `tests/validate.py --build-root C:/InsectLabs-Build/n02-validation` with the bundled Python runtime. The script compiles exported and embedded Java with the installed SDK and Java 17 target, checks VMOD round-trip/source agreement, and exercises actual DSP user regions using control/jack substitutes. The substitutes do not establish native GUI correctness.

Passed on 2026-10-16: exhaustive 7/15-bit sequence periods; algebraic 31-bit period; clock mapping and automatic-band hysteresis; reconstruction-filter response; low/middle/high clock and colour-endpoint combinations; finite bounded outputs; initial silence; the full bipolar MIX sweep following the D/A panel labels; typed Hz/colour/level/blend inverses; primitive parity; blend endpoints/center; rapidly interrupted word changes; bypass silence/history freeze; recall continuity; and invalid-control fallbacks.

The user approved the working instrument and supplied its final hero on 2026-10-16. Release housekeeping corrected the native -1..+1 MIX mapping to follow the D-left/A-right labels, along with its tooltip and typed inverse. Automated checks cover that correction; a separate native audition of the housekeeping change was not performed. CPU profiling and saved-patch migration remain unmeasured. The Kawai random-coefficient wavetable method remains a Series Three research lead.

## Final MIX mapping

Native MIX spans -1..+1 with default -1. The digital share is `(1 - MIX) / 2`: CCW/D is digital, center is an equal-power blend, CW/A is analog. Typed entries specify percent digital and invert to `1 - 2 * percent/100`. This corrects stale prototype assumptions after the user's final panel edit; the independent voices and all other DSP arithmetic are unchanged.
