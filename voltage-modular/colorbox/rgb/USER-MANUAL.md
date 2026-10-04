# RGB User Manual

**Insect Laboratories · Voltage Modular · canonical release 4.0.3**

Manual draft prepared 4 October 2026.

![RGB panel](versions/4.0.3/rgb_hero.png)

RGB is a mono-first, three-stage saturation processor. Its RED, GREEN and BLUE stages have different responses, and normalled connections let them operate as one cascade or as separate processors. Each stage has an input, output and DRIVE control. The DC FILTER switch controls DC blocking after every stage.

This manual describes the archived [RGB 4.0.3 release](versions/4.0.3/). Open its `.vmod` project in Voltage Module Designer to build the module; the archive contains source files, not a prebuilt plug-in.

## Signal path

```text
RED IN → [RED] → RED OUT ──(if GREEN IN is empty)──→ [GREEN] → GREEN OUT ──(if BLUE IN is empty)──→ [BLUE] → BLUE OUT
```

Each output is a tap of its own stage. The cable-normalled route carries that stage’s output to the next stage only while the next stage’s input is unpatched. Patching an input replaces its normalled source; it does not disconnect the stage’s output jack.

| Jack | When unpatched | When patched |
| --- | --- | --- |
| RED IN | RED receives silence. | The source enters the first stage. |
| RED OUT | Its signal feeds GREEN IN if that input is empty. | Provides the RED-stage output; the internal feed to GREEN remains available if GREEN IN is empty. |
| GREEN IN | Receives RED OUT. | The patched source replaces RED’s normalled feed. |
| GREEN OUT | Its signal feeds BLUE IN if that input is empty. | Provides the GREEN-stage output. |
| BLUE IN | Receives GREEN OUT. | The patched source replaces GREEN’s normalled feed. |
| BLUE OUT | No further internal stage follows it. | Provides the final BLUE-stage output. |

The stages are not linked as stereo channels. For two independent mono paths, patch separate sources into different stage inputs and take their outputs. A stereo setup can use two RGB modules or two independently patched stages, subject to the normalled routing above.

## Controls

| Control | Range | Default | Function |
| --- | ---: | ---: | --- |
| RED DRIVE | 1–10 | 1 | Sets the firmest, more aggressive soft-saturation stage. |
| GREEN DRIVE | 1–10 | 1 | Sets the rounder, more gradual saturation stage. |
| BLUE DRIVE | 1–10 | 1 | Sets an increasingly asymmetric saturation with a gritty edge. |
| DC FILTER | ON / OFF | ON | Enables or disables DC blocking after all three stages. |

The drive values are stage controls, not input/output gain meters. Raising drive changes harmonic content and perceived loudness; use your host or a separate level control to match levels when comparing settings.

## Starting points

**Run the full chain.** Patch your source to RED IN and listen at BLUE OUT. RED OUT and GREEN OUT let you compare the intermediate stages without changing the normalled cascade.

**Use one stage by itself.** Patch the source directly to GREEN IN or BLUE IN and take that stage’s output. An unpatched RED IN is silent, so a source entering GREEN or BLUE does not need to pass through earlier stages.

**Build two independent paths.** Patch one source to RED IN and another to GREEN IN, then take RED OUT and GREEN OUT. The GREEN input cable replaces the normal feed from RED, so the paths remain separate.

**Compare stage character.** Keep the source and input level steady. RED gives a firmer edge, GREEN a rounder thickening, and BLUE the most uneven, gritty saturation. Increase drive gradually through the cascade to hear the stages accumulate.

## DC FILTER and bypass

With DC FILTER on, a low-frequency DC blocker follows each saturation stage. Its corner is approximately 3.8 Hz, so it removes offset while also affecting content very close to subsonic frequencies. With the switch off, stage offset is retained and can change how later saturation stages respond. The switch starts ON.

Host bypass is designed to save CPU by stopping the DSP while preserving the module’s normalled signal routing. It is an immediate direct bypass, not a click-free crossfade, and switching can produce a pop or click. A downstream mute can help when silent switching is required. This is separate from the DC FILTER switch, which changes the processed signal while the module is active.

## Troubleshooting

- **No output:** check that a source is patched to RED IN, GREEN IN or BLUE IN. RED IN is silent when empty.
- **A later stage does not receive the previous one:** check whether its input jack is patched; a cable replaces the normalled source.
- **A stage sounds less saturated than expected:** raise its DRIVE control and check the level arriving at that stage. The next stage receives the previous stage’s processed output, not the original source.
- **Low-frequency content changes:** compare DC FILTER ON and OFF. The enabled blocker affects frequencies near its very low corner as well as steady offset.
- **A click occurs when bypass changes:** use a downstream mute or change bypass while the signal is quiet.

## Release information

The canonical project is RGB **4.0.3**. This manual describes that version. The [Colorbox collection guide](../../docs/manuals/Colorbox-Collection-User-Manual.md) covers how the three modules fit together; the RGB [release notes](../CHANGELOG.md), [standards](../STANDARDS.md) and [validation record](../VALIDATION.md) document source and maintenance details.
