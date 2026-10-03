# Laboratory: pending instrument briefs

Updated 3 October 2026. Eleven modules are canonical and five accepted roles remain planned. Future roles below are working briefs, not approved panels or DSP specifications. The [roadmap](Collection-Roadmap.md) owns status; [new proposals](Future-Module-Proposals.md) are separate from this inventory. Manual-first, mono-first operation and current infrastructure standards apply.

## SN-16u / Reference / Standards — completed, canonical 1.0.0

The expanded test bench combines precision fixed/manual voltages, tuning references, standards conversion, two measurement meters, frequency counting, RMS measurement, pink/blue noise, impulse sources, separate clean HPF/LPF paths, and a retriggerable 42-second sine sweep. The user approved the comprehensive manual and confirmed the final build and behavior. See the [canonical release](../laboratory/sn-16u/versions/1.0.0/README.md).

## Tone Burst Generator — next module

**Accepted role:** gate a signal according to open/closed clock-cycle counts.

**Candidate design:** signal input and gated output, manual start/stop, open/closed count controls and a simple cycle reference. Define whether counts track an internal timebase, external events or the input waveform; this has not been settled. A single-shot burst could be useful alongside repeat operation.

**Decide:** timing source/range, retrigger/reset, count-zero behavior, unpatched signal behavior and edge treatment. Phase-aware opening and a short transition have different effects; do not silently promise click-free switching at arbitrary wave phases. Avoid duplicating sw1's manual gate or expanding this into a modern sequencer.

## Selective Amplifier — retained

**Accepted role:** narrow filtering, gain, resonance and overload for isolating and emphasizing a signal region.

**Candidate design:** manual center frequency, bandwidth/selectivity, gain and overload indication. It could serve tuned-noise and resonant measurement experiments. Consider a broad bandwidth setting before creating another filter module.

**Decide:** passband topology, tuning range, resonance/self-oscillation, DC behavior and headroom. SIN/RND's external FLT path already provides a voiced filter, but is not specified as a general independent high-pass/low-pass instrument. Do not describe it as a conventional low-pass solely because it uses filter state variables.

## Dynamic Modulator — retained

**Accepted role:** extract one signal's envelope and transfer its dynamics to another.

**Candidate design:** detector input, carrier/program input, sensitivity, rise/fall response and depth. An exposed envelope output would make it useful with existing processors. Rectification and attack/release are functional operations, not optional vintage noise.

**Decide:** peak versus averaged detection, normalization, response range, depth polarity, envelope voltage reference and idle behavior. A threshold/event output is an optional way to fill a trigger-extraction gap. SIGPROC's VCA is the gain element; this instrument's new contribution is extracting and applying dynamics.

## Pulse Shaper — retained

**Accepted role:** form contours by filtering/integrating pulses.

**Candidate design:** pulse input with manual rise/fall or integration controls; consider a continuous slew mode usable with n01 STEPPED. Keep the front panel focused rather than adding a complete modern envelope generator.

**Decide:** whether it reshapes arbitrary incoming voltages or detects events and generates a fixed contour; retrigger behavior; amplitude/polarity preservation; time ranges and outputs. Hysteresis/threshold conditioning may fit here if event extraction is needed. These are suggestions, not extra accepted modules.

## Balanced Modulator — retained

**Accepted role:** amplitude/ring modulation with controllable carrier contribution.

**Candidate design:** two signal inputs, manual input levels/balance, product output and adjustable carrier leakage. Laboratory sources already provide multiple useful carriers; an internal oscillator is not automatically needed.

**Decide:** four-quadrant multiplication scale, carrier suppression, DC coupling, overload, output trim and aliasing treatment. Distinguish bipolar multiplication from SIGPROC's unipolar VCA. Begin with the lowest-cost adequate implementation; assess 2x processing only where audible nonlinear behavior warrants it.

## Shared decisions before implementation

For each brief, settle ranges, default positions, normalized/unpatched behavior, mono routing, host bypass, saved state, smoothing and voltage units before committing DSP. Keep the final panel/source pair synchronized, with readable Display Names and the exact candidate version in Notes. Approved source voicing remains locked while new instruments are developed.
