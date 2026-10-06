# Laboratory: pending instrument briefs

Updated 6 October 2026. Fifteen modules are canonical and one accepted role remains planned. Future roles below are working briefs, not approved panels or DSP specifications. The [roadmap](Collection-Roadmap.md) owns status; [new proposals](Future-Module-Proposals.md) are separate from this inventory. Manual-first, mono-first operation and current infrastructure standards apply.

## SN-16u / Reference / Standards — completed, canonical 1.0.0

The expanded test bench combines precision fixed/manual voltages, tuning references, standards conversion, two measurement meters, frequency counting, RMS measurement, pink/blue noise, impulse sources, separate clean HPF/LPF paths, and a retriggerable 42-second sine sweep. The user approved the comprehensive manual and confirmed the final build and behavior. See the [canonical release](../laboratory/sn-16u/versions/1.0.0/README.md).

## Burstgen Tone Burst Generator — completed, canonical 1.0.0

Burstgen alternates two external signals through independent counted gates. It has LOOP and one-cycle operation, internal or external clocking, eleven ratios, three internal ranges, independent PASS/GATE side outputs, a summed gated output, and independent internal square and ramp Courtesy outputs. The approved manual documents count behavior, ratio routing, bypass and the bounded prediction tail for multiplied external clocks. The user confirmed the tested build and Courtesy edge timing. See the [canonical release and full manual](../laboratory/burstgen/versions/1.0.0/README.md).

## Selective Service / Selective Amplifier — completed, canonical 1.0.0

**Completed role:** mono selective amplifier with matched band-pass MAIN and complementary band-reject C outputs; clean input GAIN, MAIN-only AMP, manual frequency/width/slope, V/Oct and FM, indicators, power-off behavior and exact splitter bypass. The user approved canonical 1.0.0 after successful build and testing. See the [release and manual](../laboratory/selectiveService/versions/1.0.0/README.md).

**Canonical behavior:** center frequency spans 20 Hz–18 kHz with ±2 Hz fine adjustment; bandwidth is 2–100 Hz; slope is 1–12 poles per skirt. GAIN spans ±24 dB before both filters; AMP spans −24/+36 dB after MAIN only. MAIN is transparent through ±20 V and approaches ±24 V; C is transparent through ±16 V and approaches ±20 V. POWER OFF silences MAIN and sends raw S through C’s ceiling. Host BYPASS sends exact raw S to both outputs, by explicit design.

**Release evidence:** the canonical archive includes the matched source pair, approved panel, user manual, review, checksums and callback validation.

## Dynamic Modulator / Following — completed, canonical 1.0.0

Following extracts envelopes and sustained gates for use with SIGPROC or another external
VCA. Its shared input gain/character stage feeds SIGNAL THROUGH and a detector-only
20–200 Hz high-pass. It provides AUTO/MANUAL attack, two-stage program-dependent release,
true 0–3 s envelope delay, linear balance, positive/inverted CV, and independent gate lamps.
It has no built-in carrier input or audio gain element for applying the envelope to another
source. See the [canonical release and manual](../laboratory/following/versions/1.0.0/README.md).

## Pulse Shaper / Sherlock — completed, canonical 1.0.0

**Status:** canonical 1.0.0 after user build and listening approval.
See the [canonical module](../laboratory/sherlock/README.md) and
[user manual](../laboratory/sherlock/versions/1.0.0/USER-MANUAL.md).

Independent positive and negative linear slew limiters; no internal normal. Patch a cable
for either cascade order. Positive START/SUSTAIN generates a full rise/reset or held contour.
Pulse outputs permit external feedback cycling. Generated positive peak is 5/10 V; pulse
gates stay 0/+5 V. Ordinary signal paths preserve bipolar levels.

Each RATE knob spans 100 us–4 s per 5 V in FAST and 100 ms–4000 s in SLOW, an exact ×1000
time change. Positive defaults FAST near C4; Negative defaults SLOW near C−1 in their
feedback loops. Both VC attenuverters start at zero. CV doubles rate per volt
at full positive gain within the selected range. Native-rate processing, direct independent
bypass, descriptive identifiers and human-readable Display Names are in place.

The approved sound and ranges are locked; the release retains checks and approval evidence.

## Balanced Modulator — retained

**Accepted role:** amplitude/ring modulation with controllable carrier contribution.

**Candidate design:** two signal inputs, manual input levels/balance, product output and adjustable carrier leakage. Laboratory sources already provide multiple useful carriers; an internal oscillator is not automatically needed.

**Decide:** four-quadrant multiplication scale, carrier suppression, DC coupling, overload, output trim and aliasing treatment. Distinguish bipolar multiplication from SIGPROC's unipolar VCA. Begin with the lowest-cost adequate implementation; assess 2x processing only where audible nonlinear behavior warrants it.

## Shared decisions before implementation

For each brief, settle ranges, default positions, normalized/unpatched behavior, mono routing, host bypass, saved state, smoothing and voltage units before committing DSP. Keep the final panel/source pair synchronized, with readable Display Names and the exact candidate version in Notes. Approved source voicing remains locked while new instruments are developed.
