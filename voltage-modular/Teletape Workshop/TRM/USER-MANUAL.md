# TRM 1.0.0 — User Manual

TRM contains three independent ring-modulator lanes. Each lane multiplies a Carrier signal and a Modulator signal after applying separate input gain controls. A four-position switch selects a nonlinear voice. The output is the circuit response with no output attenuator or automatic level compensation.

## Per-lane controls and jacks

- **Carrier knob**: input gain from 0× to 2×; 1× is unity.
- **Modulator knob**: input gain from 0× to 2×; 1× is unity.
- **Carrier input (A)** and **Modulator input (B)**: audio-rate inputs for that lane.
- **Diode selector**: choose T, G, S or Si. The three lanes start differently: lane 1 S, lane 2 G and lane 3 Si.
- **Lamp**: follows the level of the larger of the two gain-staged inputs. It has a short attack and a longer release, so it fades after the input stops.
- **Output**: the unattenuated result of the selected ring-modulator voice. Use a downstream utility module if you need to adjust level.

## Voice character

- **T** adds asymmetric transformer-like saturation and a small amount of Carrier leakage. With no Modulator signal, a small Carrier component remains.
- **G** has a softer conduction knee and gives up more level at quiet input levels.
- **S** begins conduction at a lower level and gets a tighter, brighter shoulder when driven.
- **Si** has a firmer low-level threshold that opens as the input level rises.

All four positions retain a ring-modulated character, with differences in threshold, saturation and level. The switch labels describe creative software voices; they are not claims about the exact parts fitted to historical EMS equipment.

## Patching and behavior

Patch audio to one lane's Carrier and Modulator inputs and take its output. Turn either input gain down to 0× to mute that input. Higher gain can drive the nonlinear response; output level is not normalized, so the four voices may produce different levels.

The lamp responds to either input after gain staging. Switching voices crossfades over about 5 ms to reduce clicks. Voltage Modular bypass silences the outputs and lamps; it does not pass dry audio. The module resets its short DSP filter history when processing resumes.

Approved 2026-10-15. Cherry Audio publication is deferred until the complete Teletape Workshop lineup is canonized.
