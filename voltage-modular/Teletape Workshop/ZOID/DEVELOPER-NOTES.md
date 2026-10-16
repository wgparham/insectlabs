# ZOID developer notes — 1.0.0

Collection: Teletape Workshop. Canonical files: `zoid.java`, `zoid.vmod` and `zoid_hero.png` in `versions/1.0.0/`. Class `zoid`; package `com.insectlabs.zoid`. See the [user manual](versions/1.0.0/USER-MANUAL.md), [release review](versions/1.0.0/REVIEW.md) and [validation record](versions/1.0.0/VALIDATION.txt).

## Design source and lineage

The primary reference is the “Three Voltage Controlled Trapezoid Generators with Integral Envelope Shapers” description in the shared [EMS Synthi 100 Computer Synthi manual](../references/EMS%2520%2520Synthi%2520100%2520Computer%2520Synthi.pdf). It describes four programmable segments from 2 ms to 20 s, two related control outputs, multiple trigger modes, exponential time-CV behavior, and a logarithmic envelope shaper. The user supplied the manual passage during design. This implementation takes those behaviors as inspiration; it does not claim exact electrical calibration or circuit reconstruction.

The first audition used shorter AKS-inspired times and a second independently triggered trapezoid after EOA. The user liked that relationship but chose one ZOID module and continued with the Synthi 100's shared-segment interpretation. A later iteration explored global Speed and a zero-time Delay bypass. Both were removed: the final four stages use the same range and mapping. The second output is a one-segment rotation of the first on the same state machine, not an independent delayed generator. A separate CV delay remains only a future utility idea, outside this module.

## Panel and signal behavior

- Delay, Attack, On, Decay knobs: normalized 0–1, default 0.5; 2 ms–20 s; exponential. CCW is the minimum, CW the maximum.
- Four stage CV inputs: +5 V adds one full manual sweep in position space. The sum is clamped to 0–1. External CV is read per sample; manual stage settings are smoothed with a 5 ms one-pole response.
- Mode: five positions, default Threshold: Free Run, Hold-On, Single Cycle, Multi Cycle, Threshold.
- Trigger In: Single Cycle uses positive-going zero crossings; gate modes use a 1.0 V rising / 0.8 V falling Schmitt threshold; Threshold mode uses the selected threshold.
- Signal In: feeds the primary-envelope VCA and the Threshold comparator. Threshold mode compares the higher of Signal In and Trigger In against the threshold with 25 mV hysteresis.
- Threshold Level: 0–5 V, default 5 V. Signal Level: 0–200%, default 100%.
- Trapezoid I and II level controls: -1 to +1, default center-zero. Outputs are `5*(2*phase-1)*signedLevel`, bounded by the controls to ±5 V.
- EOA/EOC: +5 V pulses, 240 samples = 5 ms at 48 kHz. Green/white/red lamps track primary Attack/On/Decay with short visual hold; GUI updates every 25 ms.
- Manual trigger: momentary, no auto-repeat; provides held-gate behavior in Hold-On and Multi Cycle.

Mode behavior: Hold-On runs its On timer while the gate remains high; when On expires it waits at the peak for release before Decay. Multi Cycle repeats while high and completes the current cycle after release. Single Cycle does not use gate duration as a hold. Threshold triggers once per rising crossing. Mode changes and recall reset transient phase state and re-arm comparators at current levels.

Host bypass routes Signal In directly to Signal Out, silences generated outputs/lamps and freezes the envelope. Resume preserves phase/progress while adopting current controls and clearing stale pulses.

## DSP map

`ProcessSample` reads controls and inputs, smooths the eight manual values, resolves mode triggers/gates, updates the shared `Timing` record, advances `SharedTrapezoid`, and writes both envelopes, the VCA, pulse outputs and lamp state. `SharedTrapezoid.step` advances a single DELAY/ATTACK/ON/DECAY clock. For normalized progress p, output pairs are `(0,1-p)`, `(p,0)`, `(1,p)`, `(1-p,1)`. Stage transitions generate primary EOA/EOC events. Retriggering in Attack continues; Delay restarts; On restarts its timer; a Decay retrigger returns to Attack from the current primary voltage, which can resynchronize Phase II discontinuously.

`stageTime` adds CV to normalized position and clamps, then looks up the exponential time. `timeForPosition` maps p to `0.002*exp(log(10000)*p)` seconds; the midpoint is 200 ms. `EditComponentValue` implements the inverse mapping for typed seconds. `vcaGain` maps the primary phase exponentially from 0.001 (-60 dB) to unity; Signal Level scales 0–200%. The -60 dB floor is intentional; the low phase is not a hard mute.

`Notify` transfers trigger-button events and services the 25 ms UI timer. `ProcessBypassedSample` copies Signal In directly to Signal Out and holds envelope state. Recall, initialization and mode changes clear event counters and transient phase state. Non-finite inputs are sanitized. The audio path allocates no objects; the VCA exponent is evaluated each sample. There is no oversampling, noise injection, saturation or intentional drift. CPU cost has not been benchmarked.

## Validation and maintenance

`tests/validate.py` builds the exported and embedded source with Java 17 target, `-Xlint:all -Werror`; round-trips the Designer binary; verifies source parity, identity, hashes and unique control UUIDs; then tests actual callback/core regions with headless controls. It covers range endpoints/taper, CV and typed-time mapping, unequal stage lengths, output polarity, retriggers, held gates, VCA values, 5 ms pulses and tooltip text, all modes, bypass/resume and recall. This does not emulate native UI, patch automation/undo, saved-patch migration or CPU scheduling. See `REVIEW.md` for the closeout limits.

After changes to timing, event handling, retriggering, outputs, VCA or state/lifecycle behavior, rerun `tests/validate.py`, compile both Designer and exported Java, check manual/tooltip consistency, and audition all modes and boundaries. Create a new version candidate instead of editing this archive.
