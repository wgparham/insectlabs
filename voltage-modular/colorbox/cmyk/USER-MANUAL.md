# CMYK User Manual

**Insect Laboratories · Voltage Modular · canonical release 1.0.3**

Manual draft prepared 4 October 2026.

![CMYK panel](versions/1.0.3/cmyk_hero.png)

CMYK is a mono-first, four-stage wavefolder. Its normalled CYAN → MAGENTA → YELLOW → BLACK path alternates two folder shapes: CYAN and YELLOW use Folder B; MAGENTA and BLACK use Folder A. Each stage has an audio input and output, an INPUT LEVEL control, a CV input and a bipolar CV-depth/index control. A shared DC FILTER switch acts after every folder.

This manual describes the archived [CMYK 1.0.3 release](versions/1.0.3/). Open its `.vmod` project in Voltage Module Designer to build the module; the archive contains source files, not a prebuilt plug-in.

## Signal path

```text
CYAN IN → [Folder B] → CYAN OUT ──(if MAGENTA IN is empty)──→ [Folder A] → MAGENTA OUT
      ──(if YELLOW IN is empty)──→ [Folder B] → YELLOW OUT ──(if BLACK IN is empty)──→ [Folder A] → BLACK OUT
```

Each stage output taps that stage’s processed signal. An empty stage input receives the preceding stage’s output, while a patched input replaces that normalled feed.

| Audio jack | When unpatched | When patched |
| --- | --- | --- |
| CYAN IN | CYAN receives silence. | The source enters the first folder. |
| CYAN OUT | Feeds MAGENTA IN if it is empty. | Provides the CYAN-stage output. |
| MAGENTA IN | Receives CYAN OUT. | The patched source replaces the CYAN feed. |
| MAGENTA OUT | Feeds YELLOW IN if it is empty. | Provides the MAGENTA-stage output. |
| YELLOW IN | Receives MAGENTA OUT. | The patched source replaces the MAGENTA feed. |
| YELLOW OUT | Feeds BLACK IN if it is empty. | Provides the YELLOW-stage output. |
| BLACK IN | Receives YELLOW OUT. | The patched source replaces the YELLOW feed. |
| BLACK OUT | No further internal stage follows it. | Provides the final BLACK-stage output. |

## Controls

Each stage has the same three controls. The default state is all INPUT LEVEL knobs at +100%, all CV-depth/index knobs centered at 0%, and DC FILTER ON.

| Control | Range | Default | Function |
| --- | ---: | ---: | --- |
| Stage INPUT LEVEL | −100% to +100% | +100% | Sets the signal level entering that folder. Center mutes the stage input; the negative side reverses polarity. |
| Stage CV depth / INDEX | −100% to +100% | 0% | Sets the amount and polarity of the stage’s CV influence on fold position. Center means no CV-driven movement. |
| Stage CV input | Voltage input | Internally normalled to +5 V | External voltage control for fold position. When a cable is connected, it replaces the +5 V normal. |
| DC FILTER | ON / OFF | ON | Enables or disables DC blocking after each of the four folders. |

The CV-depth control is not an audio-level control or a folder bypass. At its center, the incoming CV does not move the folder away from its base position; the folder still processes audio. The CV is scaled around a nominal +5 V. With the input left unpatched, the internal +5 V normal affects fold position when you move the depth control away from center. Turning the control left reverses the CV response.

## Starting points

**Hear the complete folder chain.** Patch a source to CYAN IN and listen at BLACK OUT. Use the intermediate outputs to hear how each folder changes the material.

**Animate one stage.** Patch a slow LFO or envelope to a stage’s CV input and turn its depth/index control away from center. Use the negative side when you want the control voltage to move fold position in the opposite direction. At the center setting, CV has no effect.

**Invert or reduce a stage’s input.** Turn its INPUT LEVEL left of center to invert polarity, or center it to mute the signal before that folder. Its CV controls continue to determine fold position independently.

**Use folders independently.** Patch sources directly to different stage inputs and take their individual outputs. Each input cable breaks only that stage’s incoming normal; it does not disable the later stages or outputs.

Folders reshape peaks and generate harmonics, so the output level can differ from the input level. CMYK is a sound-shaping processor, not a calibrated level utility.

## DC FILTER and bypass

With DC FILTER on, a low-frequency DC blocker follows each folder. Its corner is approximately 3.8 Hz; it removes steady offset but also affects content very close to subsonic frequencies. With the switch off, offset is retained and can influence the following folder. The switch starts ON.

Host bypass saves CPU by stopping the DSP and preserving the normalled signal routing. It switches directly rather than crossfading, so a pop or click is possible when bypass changes. Use a downstream mute for a quiet transition if needed. The DC FILTER remains a separate, in-circuit option.

## Troubleshooting

- **No output:** patch a source to one of the audio inputs. CYAN IN is silent when empty.
- **A folder receives an unexpected source:** check whether its audio input is patched. A connected cable overrides the previous folder’s normalled feed.
- **Moving the CV-depth control seems to do nothing:** check that the knob is away from center and that the input has a voltage. The unpatched jack supplies +5 V internally, but centered depth applies no CV movement.
- **A stage is silent:** its INPUT LEVEL may be centered, which mutes the stage input.
- **A later folder has a different response:** CYAN/YELLOW use Folder B, while MAGENTA/BLACK use Folder A; their alternating characters are intentional.
- **Low-frequency content changes:** compare DC FILTER ON and OFF. The enabled blocker also affects frequencies near its low corner.

## Release information

The canonical project is CMYK **1.0.3**. This manual describes that version. The [Colorbox collection guide](../../docs/manuals/Colorbox-Collection-User-Manual.md) covers how the three modules fit together; the CMYK [release notes](../CHANGELOG.md), [standards](../STANDARDS.md) and [validation record](../VALIDATION.md) document source and maintenance details.
