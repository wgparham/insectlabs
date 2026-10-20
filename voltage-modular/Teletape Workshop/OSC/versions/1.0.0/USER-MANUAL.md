# OSC 1.0.0 - Primary Oscillator

A lively EMS-inspired hybrid oscillator with simultaneous shaped sine, triangle/ramp and pulse outputs, a direct mix, dedicated pitch input and through-zero linear FM. It is a creative instrument rather than an exact EMS circuit clone.

## Frequency and modulation

- VCO/up: FREQUENCY spans 1 Hz-15 kHz. Default is C4, 261.625565 Hz.
- LFO/down: FREQUENCY spans 0.05-500 Hz. All waveform outputs preserve DC.
- V/o: +1 V raises pitch by one octave; -1 V lowers it by one octave.
- FM: DC-coupled through-zero linear FM. +5 V adds 100% of the tuned rate, -5 V stops phase advance, and -10 V reverses at the tuned rate. Depth follows the frequency knob and V/o input. Use an external attenuator for shallower modulation.
- Passing through zero preserves phase. Stopping is not muting: the outputs can hold a DC voltage. The numerical signed rate limit is +/-18 kHz.

## Waveforms

Each row has SHAPE, its own CV input and LEVEL. Levels affect both the corresponding separate output and MIX. All levels default to zero; raise one to hear audio.

- SIN: imperfect sine at noon. CCW progressively retains the negative half; CW the positive half. Shape changes harmonic balance, level and DC.
- TRI: rising ramp at CCW, triangle at noon, falling ramp at CW. The extreme ramps retain finite return slopes.
- SQR: positive pulse width spans 1%-99%, with 50% at noon. Narrow pulse settings naturally change AC energy and average voltage.

The CV destination switch is SHAPE/up, SHAPE + LEVEL/middle, LEVEL/down. Default is LEVEL/down. Each CV jack affects its own row. +5 V adds one full control sweep; summed controls stop at their endpoints. Bipolar CV can move either way from the knob setting.

## Outputs and character

Separate outputs are nominal +/-5 V at full level for symmetric waves. MIX is their direct sum with no loudness normalization, saturation or output attenuator; it can exceed +/-5 V. Shared phase allows cancellations. Shape-dependent dips, offsets and other interactions are part of the approved instrument.

All waveforms share subtle slow pitch movement and a small sine-shape/pitch interaction. Waveform bandwidth narrows toward higher pitches and under fast corner movement to reduce digital artifacts. Four-times synthesis and two-stage filtering add about 0.901 ms of common latency. Finite stress tests do not guarantee artifact-free operation for every extreme external modulation.

Type frequency in Hz, levels in percent, sine/triangle shape in signed percent and pulse width in duty percent. Host bypass silences the module and freezes processing. Resume adopts current controls, clears output-filter histories and preserves phase. Native controls save normally; phase and drift histories are not serialized.

Approved and canonized 2026-10-17. Cherry Audio publication is deferred until the complete Teletape Workshop collection is locked and canonized.
