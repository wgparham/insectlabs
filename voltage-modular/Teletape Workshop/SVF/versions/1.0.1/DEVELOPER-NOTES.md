# SVF developer notes — 1.0.1

Teletape Workshop; Java class `svf`, package `com.insectlabs.svf`; Filters category. Canon is `svf.java`, `svf.vmod`, and `svf_hero.png` in this folder. The editable working pair is in `../../development/`. The product name reflects three simultaneously exposed state-variable taps, while the documented source is specifically the Synthi 100 HPF board 34/01 / Hilton drawing 32/01.

## DSP

Two independent two-integrator cores run at 96 kHz behind 2× interpolation. Each native sample drives two 96 kHz phases; each of the six outputs has its own decimator. The established 19-tap half-band FIRs have finite rejection/ripple and nine-native-sample nominal delay. A serial connection remains inside the 96 kHz processing loop and feeds core 2 from core 1's selected, amplified and limited tap. Parallel mode feeds each core from the raw first input before its channel level; a patched IN 2 takes precedence over either normalized feed. All six outputs run in every mode.

The core equations at unity normalized input are h=−0.27u−l−d·b−0.04b³, db/dt=ω·S(h), dl/dt=ω·S(b), with S(x)=6.4·tanh(x/6.4). `d=1.8(1−r)²−(2+0.27)/101`. The linear denominator is s²+dωs+ω²; the three numerators are −0.27s², −0.27ωs and −0.27ω². The response control can make damping negative and permit self-oscillation. On entering negative damping from near silence a 1 µV perturbation seeds the core; no continuous noise is added.

The nonlinear state update uses an implicit-midpoint/trapezoidal formulation with prewarping `g=tan(πf/96000)`, analytic Newton derivative, at most ten iterations, and a correction threshold of 1e−11 V. Outputs use a shared gain of 1+10k/3.3k and symmetric 10 V fourth-order soft knee. Manual frequency is exponential 5–15000 Hz; full-depth external CV shifts two octaves per volt and final cutoff is clamped to 5–20000 Hz. Frequency is cached when unchanged. Normalized controls smooth over 5 ms; frequency smoothing operates in log position. CV is read once per native sample and held for its two internal phases.

## Circuit grounding and model limits

The shared [Synthi 100 filter research](../../../references/Synthi-100-Filters/INITIAL-RESEARCH.md) records the scan, board identities and evidentiary limits. On drawing 32/01, A3 is the HIGH summer; A5/C2/T4 and A6/C3/T5 form the BAND and LOW integrator stages. C2 and C3 are 330 pF. R14 returns LOW; T3 and the 15k path set variable BAND feedback, opposed by a smaller positive path. The output stage equivalent is 4.0303 gain. These values ground the core topology and gain relationships.

The OTA tanh scale, cubic loop loss, resonance-to-damping curve, oscillation seed and fourth-order output knee are chosen software voicing. They are not device-level 741, 3080, 2N5163 or 2N588 models, measurements, or a component-exact reconstruction. The 5–15 kHz manual range and 0.5 V/octave CV follow the supplied Synthi 100 panel/brochure evidence; user-facing CV polarity and range are explicit implementation choices. The separate Synthi 100 LPF card 34/00 has a different identity and its component drawing was not found in the reviewed scan. No claim is made that every Synthi 100 revision used this exact filter board.

The extracted SDK-independent `dsp-primitives/.../EmsSvf96.java` contains only the reusable two-integrator core. The Voltage Modular project retains an embedded copy to remain self-contained; a bit-exact 160000-step parity check covers modulated frequency/response and resets. The primitive checks finite cutoff preconditions; the embedded core gets bounded module controls.

## State, safety and standards notes

Native controls and switches persist; DSP histories do not. Reset and preset/variation restoration queue audio-thread history clearing. Bypass passes raw channel inputs to their three corresponding outputs, skips control/CV reads and freezes DSP; resume clears histories and adopts current settings. Nonfinite inputs become zero and extreme finite input is bounded before the core. Invalid or excessive core states reset. The callback uses preallocated filters and arrays, with no per-sample allocation, lock, GUI update or I/O. Native host CPU remains unbenchmarked; bounded Newton iteration and `tanh` are the dominant added costs.

Internal control identifiers retain legacy uppercase suffixes such as `knobFrequency1`, and the existing HalfBand19 coefficient formatting is preserved for byte/arithmetic continuity. These are deliberate inherited exceptions to naming/format conventions; display names and tooltips are user-facing. Generated `super.Destroy()` appears exactly once.

User confirmed the module's sound and approved canon on 2026-10-15. This is a modeling decision record, not a claim of exact hardware reproduction. See `REVIEW.md` and `VALIDATION.txt` for release evidence and remaining independently unmeasured host metrics.


## 1.0.1 panel maintenance

Adds the static location label and adjusts TTW branding placement/skin references. Filter DSP, saved control values, routing, jack behavior and existing component UUIDs are unchanged. The user reports native compile/runtime success.
