# sw2 User Manual

**Insect Laboratories · Voltage Modular · canonical release 1.0.0**

Manual draft prepared 4 October 2026.

![sw2 panel](versions/1.0.0/sw2_hero.png)

sw2 is a manual rotary selector/distributor with two independent routing banks and one shared five-position ROUTE control. The upper bank selects one of four source inputs for X. The lower bank sends a common input Y to one of four destination outputs.

## Routing

| ROUTE position | Upper bank | Lower bank |
| ---: | --- | --- |
| 0 / OFF | No source is connected to X. | Y is connected to no destination. |
| 1 | SOURCE 1 → X | Y → DESTINATION 1 |
| 2 | SOURCE 2 → X | Y → DESTINATION 2 |
| 3 | SOURCE 3 → X | Y → DESTINATION 3 |
| 4 | SOURCE 4 → X | Y → DESTINATION 4 |

Both banks follow the same knob position, but they do not feed each other. You can use either bank alone or use both at once. The ROUTE control is manual; sw2 has no CV input. Position 0 is the startup and reset position.

## CLK and signal character

**CLK up** selects direct relay routing with the module’s always-on line-amplifier character. **CLK down** adds the approved sw2 contact filter, centered around 2.2 kHz, and an approximately 2 ms route transition. The filter setting darkens transients and makes switching less abrupt. The two amplifier stages add restrained compression beginning around ±3 V and gentle weight; they are not intended as high-gain distortion.

This is a manually selected router, not a crossfade. During a change in CLK-down mode, the short transition may let a small amount of both adjacent routes pass. Use that brief overlap as part of the switch’s behavior.

## Example patches

**Choose one of four sources.** Patch up to four signals to SOURCE 1–4, turn ROUTE to the desired number, and take the selected signal from X.

**Send one signal to one of four destinations.** Patch the source to Y, patch destinations to DESTINATION 1–4, and use ROUTE to choose the destination.

**Use both banks together.** Select a source for X while routing Y to the destination with the same number. Their paths remain independent even though the control is shared.

## Bypass

Host bypass preserves the selected dry route, while skipping the contact filter and character processing. It also freezes DSP histories; on resume, histories initialize from the current signals. This bypass does not crossfade. Use a downstream mute when silent switching is needed.

For validation and release history, see the [sw2 1.0.0 release record](versions/1.0.0/README.md).
