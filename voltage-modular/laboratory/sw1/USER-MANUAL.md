# sw1 User Manual

**Insect Laboratories · Voltage Modular · canonical release 1.0.0**

Manual draft prepared 4 October 2026.

![sw1 panel](versions/1.0.0/sw1_hero.png)

sw1 is a manual 2×2 relay router. It has two inputs and two outputs. The normal position connects I1 to O1 and I2 to O2; the alternate position swaps those paths. The T/G control changes how the illuminated relay button selects the alternate position.

## Connections and routing

| Input | Normal position | Alternate position |
| --- | --- | --- |
| I1 | O1 | O2 |
| I2 | O2 | O1 |

When one input is patched, its signal moves between the two output jacks as the relay changes position. With both inputs patched, the module swaps both signals. The unselected audio route is closed; the module is a switch, not a crossfader. The output lamps indicate the selected routing state.

When no audio inputs are patched, the two outputs provide complementary +5 V manual toggle/gate signals. This makes sw1 useful as a manual logic source as well as an audio router.

## Controls

| Control | Function |
| --- | --- |
| Relay button | Changes or holds the alternate routing according to T/G. |
| T/G | **T** selects toggle mode: each press changes state. **G** selects gate mode: the alternate routing is active while the button is held and returns to normal on release. |
| CLK | Up is direct routing. Down engages the click filter and short relay-settling transition. |

The down CLK position uses the approved approximately 1.8 kHz contact filter with a 2 ms settling transition. It reduces switching clicks and darkens the sound slightly; it is intentionally not a transparent, click-free crossfade. CLK starts in the unfiltered/up position.

## Example patches

**Select one destination.** Patch a source to I1 and patch O1/O2 to the two destinations. In toggle mode, press the button to move the source between them.

**Swap two sources.** Patch sources to I1 and I2, then take O1 and O2. Toggle between the straight and crossed assignments.

**Make a manual gate pair.** Leave I1 and I2 empty and patch O1/O2 to gate inputs. Use toggle mode for a latched choice or gate mode for a momentary choice.

## Bypass

Host bypass forces the normal routing: I1 passes to O1 and I2 to O2, regardless of the relay selection. It skips the CLK filter and is immediate. Use a downstream mute if a quiet bypass transition is required.

For validation and release history, see the [sw1 1.0.0 release record](versions/1.0.0/README.md).
