# FMC Four Channel Modulator — User Manual

FMC offers four independent lanes. Each lane can act as a BF-2-style flanger, TR-2-style tremolo, DM-2-style analog delay or DD-2-style digital delay. Every lane has its own input, output, voice selector, four shared parameter knobs, Hold switch and Rate CV input. MIX combines the four lane outputs.

## Voice selector

The four positions run from top to bottom as BF (flanger), TR (tremolo), DM (analog delay) and DD (digital delay). Factory defaults are different across the four lanes: BF, TR, DM, DD. A voice change fades into the new voice over approximately 5 ms; delay contents for the newly selected voice are cleared.

## A–D parameter rows

The four knob rows are shared controls. Their meanings change with the selected voice. Hovering a knob shows the current parameter and unit. Typed values use those displayed units.

| Voice | A | B | C | D |
| --- | --- | --- | --- | --- |
| BF flanger | Rate, 0.0625–10 Hz | Sweep depth | Manual center delay, 1–13 ms | Feedback/resonance |
| TR tremolo | Coupled rate/depth macro | Wave shape: triangle through sine to square | Depth ceiling | Rate span |
| DM analog delay | Time, 20 ms–2.4 s | Intensity/feedback | Dry/wet | Grit: modeled compander and BBD hiss |
| DD digital delay | Time, 12.5 ms–6.4 s | Feedback | Dry/wet | Resolution, 16 toward 8 bits |

The delay mix controls are dry at the counter-clockwise end, both dry and wet at full level around the midpoint, and wet only at the clockwise end. Feedback and loop controls may approach self-oscillation; reduce the control or input level if the repeats build too strongly.

## Rate CV, Hold, outputs

- Rate CV affects BF flanger and TR tremolo rate. ±5 V corresponds to ±1 normalized CV; positive voltage raises rate. The delay voices ignore this input.
- Hold affects DM and DD voices: it mutes new input and attempts to sustain the repeats already in the delay loop. BF and TR do not use Hold.
- Each lane output carries that lane's processed signal. MIX is one half of the sum of all four lane outputs; it is a summing output and may need a downstream level adjustment.
- During host bypass, each input goes directly to its matching output and MIX carries half the sum of the raw inputs. DSP histories are reset when processing resumes.

## Voice character

BF creates moving comb filtering from a short modulated delay. TR varies amplitude with a selectable, continuously shaped waveform. DM darkens repeats as its modeled BBD clock slows, with optional grain/noise character. DD makes repeat timing and resolution audible at longer delays and lower bit settings. These voices take their identities from familiar pedal circuits while using software-specific ranges and control combinations.
