# RVG Random Voltage Generator — 2.0.0

RVG selects two independent bipolar voltages and a third held random value that modulates its clock. The normalized tanh stage shapes each held voltage before its Level control, retaining the full +/-2.5 V scale at full Level. Level controls default to zero; raise them to use the outputs.

| Control / jack | Behavior |
| --- | --- |
| Mean Rate | Exponential 0.2–20 Hz; default normalized position .25, about .632 Hz. |
| Variance | Random clock multiplier from 10^(-amount) to 10^(amount); maximum 0.1–10. Zero gives steady timing. It does not preserve the average pace. |
| V1 / V2 Level | Linear attenuation after normalized tanh shaping, zero to full +/-2.5 V. |
| Re-Select | Immediate selection of all three held values and clock-phase reset. |
| Inhibit / Free Run | Up inhibits/reset internal phase; down runs. Manual/external selection works in either position. Default Free Run. |
| Compensation | Up: no droop; middle/Factory: .015 V/s; down/Failure: .115 V/s. Default Factory. |
| VC Mean | +.5 V doubles base rate, -.5 V halves it; base rate limited to .2–20 Hz. |
| Select | Triggers at or above 1 V when armed; rearms at or below .1 V. |
| V1 / V2 | Independent shaped bipolar held voltages. Unpatched outputs do not affect selection. |
| Trig | +4 V for 480 samples / 10 ms per selection. |
| New Selection lamp | 100 ms activity, presented by a 25 ms GUI timer. |

Variance sets the rate multiplier using the third held random value. Final rate is limited to .05–500 Hz; actual attainable maximum with the stated base/variance ranges is 200 Hz. These limits do not describe a mean-normalized random process. Manual/external selection resets the clock phase.

Factory and Failure pull the internal held voltages toward zero at the stated rates, even during Inhibit. Output drift passes through tanh and the Level control, so the measured output drift need not equal the internal rate. There is no added noise or temperature model.

Startup and Reset request a new selection. Held voltages/random state are not serialized; native knobs and switches are saved. Host bypass silences all outputs and freezes held voltages, random state and clock phase. It clears pending selections/pulses/lamp; return adopts the current Select gate for safe rearming. A high gate does not create an extra selection on return.

This is an EMS-inspired model. An owner report motivated the shaping experiment; component mechanism and exact original random distribution are unverified. Normalized tanh is not a center-heavy distribution from uniform random samples. See [research notes](RESEARCH-NOTES.md). Publication is unconfirmed.
