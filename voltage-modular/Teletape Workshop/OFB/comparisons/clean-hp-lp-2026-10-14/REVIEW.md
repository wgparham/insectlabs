# OFB schematic-topology audition — v0.0.3

Created 2026-10-14 at the user's request. The user subsequently auditioned this implementation, confirmed better sound/function and explicitly selected it as the OFB tuning codebase. Not canonized or published. Previous code attempts, safety comparison and integration backups were removed with user authorization. The latest Designer export has matching Java/embedded source and Notes v0.0.3; hashes were refreshed without modifying that export. The unique schematic research and current tests remain.

## New DSP

One buffered finite-guarded input feeds eight independent cascades of second-order high-pass and low-pass sections. Sixteen total biquads at48kHz. Natural frequencies and section Q are calculated from nominal component values on drawing82/11; each section Q=sqrt(10)/2. Each natural frequency is bilinear-prewarped independently. Nominal rounded components and the 2kHz HP/LP cutoff offset are preserved. This ideal model omits741 finite bandwidth/slew/overload, tolerances and output-capacitor/load behavior. No artificial input tanh or separate3.16228 master gain.

Raw band outputs precede the mix controls. Main signs are[-,+,-,+,-,+,-,+] for63 through8000Hz, matching the two-summer topology. Nominal10k mix-input resistor weights are used; the board's18k low-band calibration is deliberately not adopted for this initial page1 nominal comparison.

Mix knobs are normalized0–1 and default full. Approximate log law a=(100^position-1)/99; this is chosen, not a measured vintage pot curve. Loaded gain=a/(1+.5*a*(1-a)), modeling5k pot feeding10k virtual-ground input. CCW is exactly zero, CW unity, midpoint approximately8.73%. Tooltips show knob percent and target loaded attenuation. Typed entry remains knob percent. Targets are cached while knobs stay unchanged; gains smooth5ms.

Reset/preset/variation completion and bypass return clear histories and adopt gains on next active sample. Bypass itself directly passes finite input to main output, zeros taps and freezes histories/smoothing, reading no controls. Native control states persist; transient filter history is not saved. Atomic flags transfer requests to the audio callback. Nonfinite input/gain/history recovery is retained. Audio callbacks allocate no objects; coefficients are computed only during initialization. There is no resampler or nonlinear coloration stage; CPU/host meter unmeasured.

## Validation

Both exported and embedded source compile against installed voltage.jar with --release17 -Xlint:all -Werror. tests/validate.py dynamically extracts the actual current core/callbacks and generated fields into substitutes. Independent transfer calculations use analog HP/LP equations and prewarped frequencies, not the biquad coefficient formulas. Ten two-second sine tests checked complex gain of all eight taps plus the alternating-polarity sum, with2e-7 absolute tolerance. Tests passed63,125,250,500,707,1000,2000,4000,8000,12000Hz. Representative all-full main gains:500Hz9.411dB,707Hz5.541dB,1000Hz9.385dB. Additional checks passed DC rejection, log law endpoints/monotonicity, input scaling1x/5x without clipping, tap independence from mix controls, mute after smoothing, bypass direct/frozen/no control reads, reset/resume and invalid-state recovery.

The user confirmed native functional operation and listening preference for this topology. The latest Designer export remains synchronized; its re-exported bytes differ from the prior tested files, and hashes have been refreshed. Native CPU/automation/undo/recall detail remains unmeasured. This is the selected development codebase; future audible edits require fresh listening checks. All-full does not imply unity broadband gain.

## Next decisions

The user chose this topology; subsequent work will fine-tune its voicing. Board calibration (low-band18k), actual pot curve, high-band digital/analog response mapping and any supported741 overload model remain tunable. Oversampling is not added to this linear prototype, and earlier single-tanh oversampling timing is not a cost estimate for it. See ../references/SCHEMATIC-ANALYSIS.md for derivation/provenance.
