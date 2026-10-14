# RVG developer notes — 2.0.0

Identity: class rvg, package com.insectlabs.rvg, Source category, Teletape Workshop. Canonical pair and hero: versions/2.0.0/rvg.java, rvg.vmod, rvg_hero.png. See the adjacent user manual, release review and tests/validate.py in the archive. Version 1.8.0 remains immutable historical canon.

## Signal flow and source map

sampleAndHold draws three independent xorshift64 uniform values: two bipolar +/-2.5 V held nodes and one 0–1 clock-control node. System.nanoTime seeds the generator; zero seed is replaced with a fixed nonzero value. ProcessSample handles safe gate rearm after bypass, manual/external reselection, linear internal droop, pulse/lamp counters, clock feedback and shaped/scaled outputs at 48 kHz.

Rate: .2 * 100^knob, multiplied by 2^(2*VC Mean), clamped .2–20 Hz. Variance multiplier: 10^((heldRateRandom-.5)*2*amount), spanning .1–10 at maximum. Final rate is bounded .05–500 Hz. It is not mean-normalized; a held rate's multiplier and interval statistics are distinct from an arithmetic rate average. Internal clock selection refreshes all three held values; manual/external selection also resets phase.

shapedVoltage(v) = 2.5*tanh(v/2.5)/tanh(1). Normalization is cached as a static constant. Level is a linear multiplier after shaping. Output endpoints are +/-2.5 V; shaping compresses uniformly drawn source values without creating a center-heavy density within the final range. Internal droop is 0/.015/.115 V/s, bounded at zero. The nonlinear transfer changes the observed output droop. No oversampling: output shaping operates on held modulation values, not an audio-through processor.

Initialize starts the 25 ms GUI timer, requests a selection and reads Factory switch default 1. Notify handles button selection via AtomicBoolean, volatile compensation mode, Reset and lamp presentation. Destroy stops the timer and calls the base callback exactly once. Native controls persist; no custom state payload or held-voltage restore. Reset requests a new selection; preset/variation custom setters are empty. Resume is audio-thread owned, not a UI-written flag.

Bypass silences outputs; freezes held voltages, PRNG and phase; discards pending requests and clears trigger/lamp activity. This intentionally preserves the established RVG 1.8.0 bypass policy. It skips controls and CV. Return samples Select to avoid a phantom high-gate trigger.

## Interface and real-time limits

Select thresholds are >=1 V trigger / <=.1 V rearm. Manual/external pulses load 480 before current-sample decrement; automatic clock pulses load 479 after emitting their first sample, giving exactly 480 high samples on every path. Lamp counter is 4800 samples. Output levels default zero; Mean Rate .25, Variance zero, Free Run state 0, Factory state 1. Physical switch directions match the supplied panel/recorded mapping; not independently re-tested during packaging.

No callback allocation or formatting. Exponentials and tanh remain per sample; CPU is unmeasured and coefficient caching is a future maintenance task. Control reads are not comprehensively sanitized for invalid native knob values; finite CV guards exist. Full jack tooltips are deferred. Immediate controls are retained as auditioned; no added smoothing. These are documented gaps, not a blanket standards-compliance claim.

## Evidence and future edits

User auditioned the normalized-tanh working pair and approved 2.0.0 canonization on 2026-10-14. Packaging changes only the module Notes from v2.0.0rc to v2.0.0; code/art remain identical. Repeatable tests compile both source forms using Java 17 target/warnings as errors and run actual extracted callbacks with SDK substitutes. See VALIDATION.txt and REVIEW.md for results/limits. No independent native UI/undo/CPU measurement or publication claim.

Future changes to sampling distribution, variance normalization, shaping/gain order, droop, clock limits, gates or bypass require a new candidate and renewed listening approval. Validate both polarity directions, event pulse paths, switch defaults, lifecycle, state and source-pair integrity. Preserve unique owner/research evidence with the series; extract a reusable primitive only when a separate need and interface emerge.
