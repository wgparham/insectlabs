# HSB User Manual

**Insect Laboratories · Voltage Modular · canonical release 1.0.2**

Manual draft prepared 4 October 2026.

![HSB panel](versions/1.0.2/hsb_hero.png)

HSB is a mono-first pair of parallel tone-and-fuzz processors. Each path runs HUE → SATURATION → BRILLIANCE. The upper and lower processors have separate controls and can process the same source or two different sources. The lower output sums both processed paths until the upper output is patched.

This manual describes the archived [HSB 1.0.2 release](versions/1.0.2/). Open its `.vmod` project in Voltage Module Designer to build the module; the archive contains source files, not a prebuilt plug-in.

## Signal path and normalled routing

```text
TOP IN ──→ Top HUE → SATURATION → BRILLIANCE ──→ TOP OUT
   │                                                    │
   └─(if BOTTOM IN is empty)→ Bottom HUE → SATURATION → BRILLIANCE → BOTTOM OUT
                                                        ↑
                 TOP path is added here while TOP OUT is unpatched
```

The processors are parallel, not a serial top-to-bottom cascade. The normalled connections are:

| Jack | Normal behavior |
| --- | --- |
| TOP IN | The upper source input. If empty, the upper path receives silence. |
| TOP OUT | Carries only the processed upper path. When this output is unpatched, the upper signal is also added to BOTTOM OUT. |
| BOTTOM IN | When empty, receives TOP IN. Patching it gives the lower processor a separate source. |
| BOTTOM OUT | Carries the lower processed signal, plus the upper processed signal while TOP OUT is unpatched. |

The addition to BOTTOM OUT is smoothed over a short transition when TOP OUT is connected or disconnected. Because it sums two signals, BOTTOM OUT can be louder than either path by itself. Patch TOP OUT to separate the outputs; this breaks the upper-to-lower output mix normal while leaving TOP OUT available as its own signal.

## Controls

The upper and lower processors have matching controls. Each starts with HUE and BRILLIANCE centered, SATURATION at minimum, and its mode switch up in STANDARD.

| Control | Range | Default | Function |
| --- | ---: | ---: | --- |
| HUE | 0–1 | 0.5 | Tilts the tone before fuzz, from bass-weighted warmth toward brighter emphasis. Center is the neutral setting. |
| SATURATION | 0–1 | 0 | Increases drive, compression and fuzz from minimum to maximum. |
| BRILLIANCE | 0–1 | 0.5 | Shapes the post-fuzz result from softened highs through neutral to more presence and air. Center is neutral. |
| STANDARD / CUSTOM | Two positions | STANDARD / up | Selects the processor’s fuzz topology. Up is STANDARD; down is CUSTOM. |

**STANDARD** is the thick, sustaining dual-feedback fuzz voice, inspired by Muff-family circuits and without a conventional tone stack. **CUSTOM** is the more aggressive, input-sensitive scramble-fuzz voice, inspired by the Soda-Meiser. The two settings are intentionally distinct; use SATURATION to bring either voice forward.

## Starting points

**One processed source.** Patch to TOP IN, leave BOTTOM IN empty, and listen to BOTTOM OUT. This lets you hear both independently adjusted paths mixed together. Reduce SATURATION on one path or set its controls to complement the other.

**Two independent sources.** Patch one source to TOP IN and another to BOTTOM IN. Patch TOP OUT to keep the upper and lower outputs separate, then take TOP OUT and BOTTOM OUT individually.

**Compare the two modes.** Keep HUE, SATURATION, BRILLIANCE and input level steady, then flip one processor between STANDARD and CUSTOM. Repeat with the other processor to blend the two characters at BOTTOM OUT.

**Shape before and after fuzz.** Start with HUE and BRILLIANCE centered. Move HUE first to alter the tone feeding the fuzz, then use BRILLIANCE to shape the processed result. Add SATURATION last so you can distinguish tone shaping from increased drive.

## Bypass and saved patches

Host bypass routes the inputs directly to the outputs while retaining the module’s normalled input and output behavior. It stops the DSP rather than crossfading, so switching may produce a pop or click. Use a downstream mute if a quiet bypass transition is important.

HSB 1.0.2 establishes **switch up = STANDARD** and **switch down = CUSTOM**. This mapping changed from release 1.0.1. When opening a patch made with 1.0.1, check both switches and flip either one if needed to retain the intended fuzz voice.

## Troubleshooting

- **No sound:** patch a source to TOP IN or BOTTOM IN. TOP IN is silent when empty; BOTTOM IN receives TOP IN only when its own input is empty.
- **The lower output is louder than expected:** BOTTOM OUT sums the upper path while TOP OUT is unpatched. Patch TOP OUT to separate the paths.
- **The two inputs seem linked:** an empty BOTTOM IN intentionally follows TOP IN. Patch BOTTOM IN to give it an independent source.
- **A switch selects the opposite voice from an older patch:** 1.0.2 uses up = STANDARD and down = CUSTOM. Check and flip the switch as needed.
- **A click occurs when bypass changes:** bypass is direct. Mute the downstream signal or switch while the source is quiet.

## Release information

The canonical project is HSB **1.0.2**. This manual describes that version. The [Colorbox collection guide](../../docs/manuals/Colorbox-Collection-User-Manual.md) covers how the three modules fit together; the HSB [release notes](../CHANGELOG.md), [standards](../STANDARDS.md) and [validation record](../VALIDATION.md) document source and maintenance details.
