# SVF 1.0.1 — User manual

SVF contains two independent filter cores. Each takes one audio input and provides simultaneous HIGH, BAND and LOW outputs. The core follows the two-integrator state-variable topology documented on the Synthi 100 HPF sheet. The three outputs let you use it as a high-pass, band-pass, low-pass or combined filter voice.

## Per-channel controls

- **IN** sets input level from silence to twice the input; 50% is unity.
- **CV** sets cutoff modulation depth. At full depth, +0.5 V raises cutoff by one octave and −0.5 V lowers it by one octave. CV is summed with the manual frequency setting and bounded by the available range.
- **FREQ** sweeps exponentially from 5 Hz to 15 kHz. The default is fully clockwise at 15 kHz; turn it down for low-pitched material.
- **RESP** raises resonance. At high settings the filter can self-oscillate; input level at zero does not silence self-oscillation.

Connect audio to **IN 1** and take a signal from **HIGH 1**, **BAND 1**, or **LOW 1**. The second channel is independent by default and works the same way.

## Core routing

The center three-position mode switch is **SERIES** up, **independent** in the middle, and **PARALLEL** down. In SERIES, core 2 processes core 1's selected output. The center route switch chooses HIGH up, BAND middle or LOW down as the signal sent into core 2. Core 1's own outputs remain available. In PARALLEL, both cores receive the first input unless IN 2 is patched. A cable at IN 2 overrides internal normalization in either routed mode; the second channel's IN control sets its level.

All six outputs stay active in each mode. For two unrelated filters, leave the mode switch in the middle. For a two-stage filter, select SERIES and choose the first stage's output with the route switch.

## Notes

Manual controls smooth over approximately 5 ms. Voltage Modular stores control and switch positions; transient filter history resets on preset/variation restore and when DSP resumes from bypass. Host bypass sends input directly to the matching channel outputs and freezes the filter. The series reference folder documents the source schematics and the limits of the historical evidence.

Approved 2026-10-15. Publication is deferred until the full Teletape Workshop lineup is locked and canonized.
