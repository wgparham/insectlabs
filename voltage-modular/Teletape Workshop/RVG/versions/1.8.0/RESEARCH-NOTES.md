# RVG design research and decisions

The RVG is approached as a playable reconstruction of workflow and response, not as an exact electrical copy. The available references describe the instrument and show a surviving board, but no verified RVG schematic has been located. The schematic topology and exact component values therefore remain unknown.

## Working behavioral model

- Two independent sample-and-hold outputs are supported by published RVG descriptions. Their internal random values are independently sampled across a bipolar range.
- A third random value influences timing through Variance. This is a plausible interpretation of descriptions and demonstrations, not a confirmed RVG circuit fact.
- A phase-accumulator clock, exponential Mean Rate control, 0.5 V/octave Mean CV, manual and external reselection, inhibit, and +4 V event trigger provide a practical patching workflow.
- The silver switch models hold quality. Its states are 2/up COMPENSATED, 1/middle FACTORY, and 0/down BLOWN CIRCUIT. The approved patch saves state 1 as its startup default.
- The user's final tuning is 15 mV/s toward zero in FACTORY and 115 mV/s toward zero in BLOWN CIRCUIT. These rates are not measurements of an EMS unit. The model clamps at zero and does not attempt to emulate a specific leakage path.

## Limits and next-work boundaries

The exact RVG timing distribution, third-channel circuit, sample-and-hold topology, noise source, hold capacitor, leakage mechanism, and output stages remain unknown. A service or education document for the Synthi 100 and repair notes for other EMS equipment can inform the broader design ethos, but they are not RVG schematics. Keep conclusions tied to the linked sources and mark extrapolations as hypotheses.

No general DSP primitive was extracted from RVG: its random clock and hold behavior is module-specific. No useful test audio was generated during this release; existing TestBench fixtures are sufficient for repeatable input patches. Add a new fixture only if later measurement or listening work needs one.

See [module-specific references](../../references/README.md) and the [shared Teletape Workshop reference archive](../../../references/README.md).
