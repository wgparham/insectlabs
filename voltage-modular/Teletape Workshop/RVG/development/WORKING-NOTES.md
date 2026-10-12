# RVG Development Notes

**Status:** Canonical RVG v1.8.0. Development source is paired with the immutable release archive at `../versions/1.8.0/`. Keep this note aligned with the current user-approved behavior.

## Evidence used

Published RVG descriptions support two independently varying stepped outputs, a nominal 0.2–20 selections-per-second mean-rate range, rate CV at 0.5 V/octave, a +4 V trigger per selection, inhibit and reselect controls, and a rectangular probability distribution. Secondary descriptions and the surviving board photo suggest a third sample-and-hold value influences the clock rate. No RVG schematic has been found, so the internal arrangement remains a hypothesis.

## Current behavior hypothesis

- The free-running clock is a phase accumulator. The Mean Rate knob maps exponentially from 0.2 to 20 Hz; connected Mean CV multiplies that rate at two octaves per volt.
- Every selection samples two independent uniform bipolar values, held at up to ±2.5 V and scaled by the V1 and V2 Level knobs.
- A third uniform held value modulates the next clock rate through the Variance control. At maximum Variance, the rate multiplier spans a hypothesized 128:1 range, centered to preserve the Mean Rate as an arithmetic average over events.
- The current panel switch mapping treats its initial value as RUN. Inhibit stops clock phase while preserving held outputs. Re-Select and a rising edge at Select Input immediately take a new selection; the input triggers above 1 V and re-arms below 0.1 V.
- The three-position silver switch selects hold behavior and defaults to FACTORY (middle, state 1). COMPENSATED (up, state 2) has no droop. FACTORY applies a 15 mV/s linear leak toward zero. BLOWN CIRCUIT (down, state 0) applies a 115 mV/s leak toward zero. Both leak modes affect held outputs continuously, including while inhibited; compensation mode does not alter the clock or trigger behavior.
- Each selection raises Trig Out to +4 V for 10 ms. The New Selection lamp follows a 100 ms pulse.
- A selection occurs at startup so the module has useful held voltages before its first clock edge.

The third held value, exact timing distribution, startup selection, select-input edge thresholds, pulse widths, switch polarity, and both droop rates are implementation choices made in the absence of an RVG schematic or verified hardware behavior. The selected rates are the user-approved musical response; they are not measurements of an EMS RVG. The linear drift is intentionally bounded at zero and is a simplification of leakage, not a component-level model. The model does not add continuous noise or unrelated component-aging faults.

## Next review

Build and load the project in Voltage Modular, then check startup, inhibit/resume, manual and external selection, voltage range and independence, CV rate scaling, maximum Variance, trigger level/width, and behavior at the panel limits. Adjust the timing feel against the available demonstrations before treating this model as a specification.
