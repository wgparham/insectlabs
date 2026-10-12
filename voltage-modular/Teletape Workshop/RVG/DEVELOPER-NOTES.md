# RVG Random Voltage Generator — Developer Notes

Canonical v1.8.0. Public identity: RVG Random Voltage Generator. Java identity: class `rvg`, package `com.insectlabs.rvg`. The authoritative pair is [`versions/1.8.0/rvg.vmod`](versions/1.8.0/rvg.vmod) and [`versions/1.8.0/rvg.java`](versions/1.8.0/rvg.java). See the [user manual](USER-MANUAL.md), [release review](versions/1.8.0/REVIEW.md), [research notes](RESEARCH-NOTES.md), and [RVG references](references/README.md).

## Signal flow and behavior

`ProcessSample()` runs at the module's 48 kHz design assumption. A phase accumulator drives selections from 0.2 to 20 Hz. Mean Rate maps its normalized knob value exponentially across that range. Mean CV scales the base rate at two octaves per volt, then clamps it back to 0.2–20 Hz. Variance applies a normalized exponential random multiplier to the next event interval; at maximum setting, multipliers span a 128:1 ratio with an arithmetic mean of one. The final instantaneous rate is bounded to 0.01–200 Hz.

`sampleAndHold()` uses a xorshift64 generator with a `System.nanoTime()` seed. It captures three independent values at each selection: two uniform bipolar voltages up to ±2.5 V and one uniform value used by Variance. V1 and V2 Level scale their respective held voltages from 0 to full range; their saved defaults are zero. An initial selection occurs at startup. Re-Select and a re-armed Select input take an immediate new selection and reset the internal clock phase.

The three-position switch reports discrete states 0–2: state 2/up is COMPENSATED (zero droop), state 1/middle is FACTORY, state 0/down is BLOWN CIRCUIT. The saved initial state is 1, so FACTORY is the startup mode. The user-approved rates are 0.015 V/s and 0.115 V/s toward zero. `droopTowardZero()` bounds the drift at zero and never crosses polarity. These are chosen musical rates, not measurements or component-level claims about EMS hardware. Droop continues during Inhibit and pauses during host bypass.

`switchInhibit` stops the internal clock and resets phase. Manual and external selections remain active while inhibited. Select triggers at or above 1 V and rearms at or below 0.1 V. Trig Out is +4 V for 480 samples (10 ms); the selection lamp is active for 4,800 samples (100 ms) and is presented by the 25 ms GUI timer.

## Source map, lifecycle and bypass

- `Initialize()` starts the GUI timer, queues the initial selection, and reads the saved hold-mode switch state.
- `Notify()` handles Re-Select, compensation switch changes, GUI lamp updates and Reset. The switch value is rounded and clamped to state 0–2.
- `ProcessSample()` handles gate detection, selection, held-voltage droop, clock/variance timing and the three outputs.
- `ProcessBypassedSample()` silences all outputs, clears pending selection/trigger/light state and marks the gate for safe re-arming. It does not advance held voltages or clock phase.
- On the first active sample after bypass, the Select input is sampled to establish its re-arm state. Held voltages and clock phase are preserved; queued manual selections are discarded during bypass.
- `Destroy()` stops the GUI timer and calls the base implementation exactly once.

This is a source module without a through path, so host bypass produces silence. Audio outputs resume from the preserved held values; leaky modes resume drifting. The source includes no custom saved state: Voltage Modular persists the ordinary controls, including the three-position switch.

## Real-time and validation notes

The sample callback allocates no objects. When Variance is active it evaluates exponentials and a hyperbolic sine per sample, and Mean Rate uses exponential scaling; CPU has not been benchmarked. No sample-rate adaptation is implemented, so clock, pulse and droop timing follow the 48 kHz constant rather than a queried host rate.

The release test compiles both source forms with the installed Voltage SDK and exercises switch-state mapping, hold rates, selection/gate behavior, triggers, output bounds, bypass/resume and tooltip content using headless SDK mocks. This does not replace a native Designer/Voltage host build. The user built and auditioned the DSP/settings before release packaging and approved the result; the final source-only additions are tooltips and notification/timer cleanup. Native save/reload and CPU measurement were not repeated for packaging.

## Focused maintenance checklist

For changes to switch encoding, repeat the three-position 0/1/2 mapping, initialization and notification checks. For DSP changes, repeat the controlled one-second droop checks in both polarities, independent channel sampling, rate/CV bounds, Variance limits, external gate thresholds, selection pulses and inhibit. For lifecycle changes, repeat bypass silence, frozen histories, rearm and one-time timer shutdown. Rebuild the Designer pair with the installed SDK, run `python versions/1.8.0/tests/validate.py`, and update the review and checksums. Never present the model as an RVG schematic reconstruction without new primary evidence.
