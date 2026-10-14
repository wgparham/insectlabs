# TSL developer notes — 1.0.0

Collection: Teletape Workshop. Canonical files: tsl.java, tsl.vmod, tsl_hero.png in versions/1.0.0. Class tsl; package com.insectlabs.tsl. See USER-MANUAL.md and REVIEW.md beside the release for operating behavior and validation. Root copies link from the module README.

## Implementation

ProcessSample advances three independent histories toward their input with a symmetric difference clamp: maximum change = rate / 48000. baseRateForKnob maps normalized 0–1 to 0.05 * (1000 / 0.05)^position V/s. knobForRate supplies the logarithmic inverse for typed values. Channels 1/2 add CV/5 to normalized position, clamped to 0–1. Channel 3 uses its manual knob and a >3 V gate; disconnected gate tracks. Knobs default to zero/slowest. No oversampling, extra filtering, noise, drift or crosstalk is added.

readInput maps unpatched/nonfinite values to zero and bounds active signal/CV values to +/-1,000,000 V. clamp01 falls back to zero for invalid controls. Histories begin at zero and are audio-thread owned. AtomicBoolean coalesces reset/preset/variation/resume requests. On consumption, all histories adopt input immediately, even while channel 3 holds. Native controls persist; custom held voltages do not.

Bypass copies raw signal jack values to corresponding outputs, performs no control/CV reads and freezes histories. It requests adoption on resumption. This approved source does not apply active input guards during bypass; malformed/nonfinite bypass signals propagate. This is a known standards gap retained to avoid altering the approved build, not a claim of universal compliance.

Audio callbacks allocate no objects or format strings. Exponentials are evaluated each active sample, including unchanged settings; caching remains a future optimization. Manual rate changes are immediate, with no separate smoothing. The lamp is set once in Initialize. super.Destroy executes once. CPU, automation/undo and native saved-patch migration are not benchmarked or independently validated.

## Release changes and preservation

User approved the supplied set for canonization on 2026-10-14 and explicitly requested class tsl instead of tsl_alt. Class/constructor/Designer class metadata changed together; existing UUIDs, panel, geometry, skin, saved test state, source filenames and DSP were preserved. Corrected the stale T&H tooltip from 1 V to the actual 3 V threshold. Notes remains v1.0.0. No executable DSP changes were made.

Module category remains the approved ModuleType_Source metadata; revisiting category is deferred. Signal jacks retain native display-name tooltips. These and coefficient caching/bypass safety are open maintenance items rather than completed handbook checks. Changing class identity may affect existing prototype patch references; the rename was explicitly requested.

## Maintenance

Repeat tests/validate.py after source edits. It compiles exported and embedded Java with Java 17 target, warnings as errors, validates serialization/source agreement/hashes and runs extracted actual callbacks with jack/knob substitutes. It does not emulate native UI, automation, thread scheduling or saved-patch compatibility. Any gate, rate, state, bypass or smoothing change requires focused checks and listening. Preserve version archives; create a new development candidate for future changes.
