# RVG Random Voltage Generator — User Manual

RVG produces two independent random stepped voltages. Every selection chooses a new value for V1 and V2 and a timing value for the next event. Mean Rate sets the average pace; Variance makes the intervals irregular. Patch the voltage outputs to pitch, filter, or other CV inputs, and use Trig Out to coordinate other events.

## Controls and connections

| Control or jack | Operation |
| --- | --- |
| **Mean Rate** | Sets the internal event rate from 0.2 to 20 selections per second. The knob is exponential. |
| **Variance** | Adds random timing variation. At zero, events follow the Mean Rate steadily. Higher settings spread the next interval over a wider range while preserving the average rate. |
| **V1 Level / V2 Level** | Scales each independent bipolar random output from silent to a maximum of ±2.5 V. Both controls start at zero. |
| **Re-Select** | Takes a new selection immediately, independent of the internal clock. |
| **Inhibit / Free Run** | Inhibit stops the internal clock and resets its phase. Manual and external selections continue to work. Free Run resumes clocking from a fresh interval. The default is Free Run. |
| **Compensate switch** | Selects hold behavior: up is COMPENSATED with no droop; middle is FACTORY with a gentle leak; down is BLOWN CIRCUIT with a stronger leak. The default is the middle FACTORY position. Both droop modes pull held voltages toward zero and stop at zero. |
| **VC Mean** | Rate CV input. A +0.5 V change doubles the base rate; a -0.5 V change halves it. The base rate remains within 0.2–20 Hz before Variance is applied. |
| **Select** | Rising gate input. A crossing of 1 V takes a selection; the input rearms after falling below 0.1 V. |
| **V1 / V2** | Independent stepped bipolar outputs. The level controls scale their held voltages. |
| **Trig** | A +4 V pulse lasting 10 ms for each selection, including manual and external selections. |
| **New Selection light** | Lights for 100 ms after a selection. |

The default output levels are zero, so raise V1 Level and/or V2 Level to hear or use the held voltages. RVG takes an initial selection at startup; Trig Out and the New Selection light also indicate that event.

## Choosing a hold character

The silver switch defaults to FACTORY in the center. FACTORY drifts toward zero at 15 mV/s. COMPENSATED stops that drift. BLOWN CIRCUIT drifts toward zero at 115 mV/s. These are tuned musical rates for this implementation, not measured values for an EMS unit. For example, a five-second hold changes by about 75 mV in FACTORY or 575 mV in BLOWN CIRCUIT, unless the stored value reaches zero first.

## Patch ideas

- Patch V1 and V2 to separate oscillator pitch or filter controls for related but independent motion.
- Use a slow Mean Rate and moderate Variance for irregular changes with breathing room.
- Patch Trig Out to an envelope, sequencer advance, or another event input.
- Use Select for manual clocking, or feed it gates from another module while Inhibit is engaged.
- Try BLOWN CIRCUIT for held notes that drift audibly between selections; switch to COMPENSATED when stable values are needed.

RVG is an EMS-inspired behavioral model rather than an exact schematic reconstruction. The published reference material and unresolved design hypotheses are recorded in [Research Notes](RESEARCH-NOTES.md) and [RVG References](../../references/README.md) and the [shared Synthi handbook](../../../references/README.md).
