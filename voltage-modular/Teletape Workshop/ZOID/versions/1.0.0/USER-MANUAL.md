# ZOID Trapezoid Generator — 1.0.0

ZOID creates a four-stage trapezoid and a second related control waveform. Both outputs share one Delay, Attack, On and Decay cycle. Phase II is rotated by one segment: it falls during Delay, stays low during Attack, rises during On, and stays high during Decay. This is a shared-cycle relationship, not a separate delayed envelope.

## Time controls

Delay, Attack, On and Decay each cover **2 ms to 20 s**. Turn clockwise for longer times. Their control curves are exponential. The four CV inputs add to the corresponding knob position; **+5 V spans one full knob sweep** toward longer time. Combined settings are bounded to the knob range.

## Trigger modes

The Mode control selects:

- **Free Run:** continuously repeats the cycle.
- **Hold-On:** a gate starts a cycle. The On stage runs while the gate is high; after On expires, the generator waits at its peak until the gate goes low, then Decay begins.
- **Single Cycle:** each positive-going zero crossing at Trigger In starts one cycle.
- **Multi Cycle:** a high gate repeats cycles; when it goes low, the current cycle finishes.
- **Threshold:** a positive crossing of Signal In or Trigger In above the selected threshold starts a cycle.

The panel trigger button starts a cycle in every mode. In Hold-On and Multi Cycle it acts as a held gate while pressed. The button does not auto-repeat.

## Outputs and indicators

**Trapezoid I** follows low, rising, high and falling phases. **Trapezoid II** follows the same stage timing, shifted by one segment. Each level control is bipolar and center-zero: clockwise gives normal polarity, counterclockwise inverts, and center is silent. At maximum level, either output spans -5 V to +5 V.

Signal In passes through a logarithmic VCA controlled by Trapezoid I. Signal Level ranges from 0% to 200%; the VCA moves from -60 dB at the low plateau to unity at the peak when Level is 100%. The low-stage gain has a small -60 dB floor rather than hard muting.

**EOA** emits +5 V for 5 ms when Trapezoid I completes Attack. **EOC** emits +5 V for 5 ms at the end of Decay. Green, white and red lamps indicate primary Attack, On and Decay activity.

Host bypass routes Signal In directly to Signal Out and silences the generated outputs. The cycle resumes from its saved phase when bypass is released.

This is an EMS-inspired instrument based on documented Synthi 100 workflows; it is not a component-level circuit reconstruction.
