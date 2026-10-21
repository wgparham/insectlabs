# HGD 1.0.0 — User Manual

HGD contains four independent high-gain processing lanes. Each lane has an input, GAIN and TONE controls, a voice-specific III control, a four-position voice selector, and an output. The MIX output combines the lane outputs.

## Per-lane controls and jacks

- **Input**: mono audio input. An unpatched input produces silence at its lane output.
- **GAIN**: sets the voice's drive character from its minimum setting to its maximum setting. It is not a mute control; the minimum setting still processes the input. Output compensation keeps level changes gradual as drive changes.
- **TONE**: bipolar sweep from −1 to +1 with 0 centered. The direction and filter shape depend on the selected voice.
- **III**: adjusts the selected voice's third parameter. Its meaning changes with the voice; Designer's tooltip reports the active unit and range. Typed values use that displayed unit.
- **Voice selector**: selects DS, FZ, HM or MT. The shipped positions start lane 1 on DS, lane 2 on FZ, lane 3 on HM and lane 4 on MT. Moving a selector crossfades the lane over approximately 5 ms.
- **Output**: the processed lane signal, with no separate output level control.

## Voice controls

- **DS**: the III control is Push, approximately 17–35 dB of modeled first-stage gain. TONE blends the lower and upper filtered response.
- **FZ**: the III control moves from Modern through Vintage to Maestro-style bias starvation. At the high end, starvation adds a dead zone and gating. TONE moves between darker and brighter tilt, with a Modern-mode mid bump.
- **HM**: the III control moves the midrange from a deep scoop through the as-built reference to a filled-in setting. TONE shifts the low/high weighting.
- **MT**: the III control sweeps the mid-frequency center from 250 Hz to 4 kHz. TONE moves the low and high shelves in opposite directions.

The control models and ranges are software interpretations informed by the archived circuit references. They do not claim exact reproduction of particular production revisions.

## Outputs, mix and bypass

Each output is independent. MIX is half the sum of the four processed lanes; unpatched lanes contribute silence. It is a summing output, not a separately limited or normalized master.

Voltage Modular host bypass routes each input directly to its corresponding lane output and silences MIX. DSP state is cleared once when processing resumes.

## Suggested use

Feed separate sources into the four lanes and choose a different voice for each. Use GAIN to push a signal into its voice, TONE to shape the resulting spectrum, and III to adjust that voice's distinctive behavior. Take individual outputs when processing lanes separately, or use MIX for a combined signal. Add a downstream level control if the combined signal needs attenuation.

Cherry Audio publication remains deferred until the Teletape Workshop lineup is complete.
