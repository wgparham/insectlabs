# DCA — Dual Controlled Amplifier

DCA is a two-channel amplifier with three signal-flow modes. Each channel has an input, level control, bias, response shape, CV input and mono output. The DUAL outputs add stereo positioning or a mono sum; the LINK inputs feed those DUAL outputs at unity.

## Controls and connections

| Control or jack | Function |
| --- | --- |
| IN A / IN B | Audio input for each channel. An unpatched input is silent in IND mode. |
| LEVEL | Sets 0–4× gain at the internal +5 V CV normal. A +5 V external CV sweeps the full slider range. |
| CV | Replaces the +5 V normal when patched. Negative CV is clipped to zero; gain is limited to 4×. |
| BIAS | Adds a bipolar offset from −5 V to +5 V before amplification. Center is zero. |
| SHAPE | Selects the gain curve below unity: LIN at the low end, EXP at center, LOG at the high end. Above unity all curves continue linearly. |
| IN A / IN B mono outputs | The individual channel outputs, before stereo pan. |
| DUAL L / DUAL R | Stereo outputs or a mono mix when DUAL R is unpatched. |
| LINK L / LINK R | Unity inputs summed into DUAL only. When DUAL R is unpatched, both links are included in DUAL L's mono sum. |
| IND / SERIAL / ST | Selects independent channels, A-to-B serial routing, or stereo operation. |

## Modes

- **IND:** A and B operate independently. If IN B is unpatched, channel B is silent. Each channel's PAN control positions it in DUAL L/R.
- **SERIAL:** When IN B is unpatched, channel A's mono output feeds channel B. Patching IN B breaks that normal. Each channel retains its own LEVEL, CV, BIAS and SHAPE.
- **ST:** Channel A's input is copied to B while IN B is unpatched; B keeps its own BIAS, while A's LEVEL, CV and SHAPE control both sides. Patching IN B breaks the copy and provides separate left and right inputs. PAN A is a balance control: center sends both sides at full level, and moving toward either side fades the opposite side.

When DUAL R is unpatched, DUAL L carries a unity mono sum of both channel outputs and both LINK inputs, without pan. In ST with the A-to-B normal, the duplicate signal is counted once in this mono sum.

## Gain and character

The internal CV normal is +5 V. With CV unpatched, the LEVEL slider sets gain directly from 0× to 4×. With CV patched, gain is the LEVEL setting multiplied by the positive CV relative to 5 V, limited to 4×. BIAS is added to the incoming signal before this gain. The nonlinear output stage adds a mild level-dependent coloration and compresses strongly above 10 V toward an approximately 11 V ceiling.

## Bypass

Voltage Modular bypasses the gain and coloration stages. The mono outputs pass their corresponding input signals directly, while serial/stereo normaling and DUAL routing follow the mode and pan state retained by the module.
