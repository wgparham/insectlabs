# Signal Processor User Manual

**Insect Laboratories · Voltage Modular · canonical release 1.0.1**

Manual draft prepared 4 October 2026.

![Signal Processor panel](versions/1.0.1/sigproc_hero.png)

The Signal Processor is a dual, mono-first utility. Its left and right stages are independent: each has its own audio input and output, gain, offset, external control input, external level control, and PROC/VCA selector. Use the stages as separate processors or as a dual-mono pair; there is no stereo link between them.

This guide describes [release 1.0.1](versions/1.0.1/), the canonical source pair. Open `signal_proc.vmod` in Voltage Module Designer to build the module.

## Signal path and controls

Each side has this path:

```text
INPUT → gain/character stage → OFFSET → OUTPUT
                 ↑
       EXT INPUT × EXT LVL
```

| Control or jack | Range / default | Operation |
| --- | --- | --- |
| INPUT | Audio input | Feeds only the matching left or right stage. |
| GAIN | −3× to +3×; default +1× | Sets the stage level and sign. In PROC, the positive range adds more driven saturation; the negative range gives a cleaner inverted response. |
| OFFSET | −5 V to +5 V; default 0 V | Adds a manual DC offset after the character stage. It has no external CV input. |
| EXT INPUT | Voltage input | Controls gain rather than adding audio to the output. A nominal +5 V corresponds to the setting on EXT LVL. |
| EXT LVL | −2× to +2×; default +1× | Sets the amount and polarity of external control. |
| PROC / VCA | Two positions; default PROC | Selects the processor response. Left is PROC; right is VCA. |
| OUTPUT | Audio output | Carries the processed signal from the matching stage. |

## PROC and VCA

**PROC** is the general signal-processing mode. The GAIN knob is bipolar, and external control responds promptly. Positive gain pushes the stage into its approved saturation character; negative gain provides an inverted path with less saturation.

**VCA** changes the gain response to a unipolar, rounded envelope. Its vactrol-inspired action has approximately 3 ms attack and 30 ms release, so it responds more slowly than PROC. Use EXT LVL to set the control sensitivity, then patch an envelope or other control signal to EXT INPUT. Positive and negative EXT LVL settings reverse the control direction.

The audio input on one side does not feed the other side. An unpatched audio input is silence; neither side is normalled to the other.

## Example patches

**Drive one sound in PROC.** Patch the source to left INPUT, set the left selector to PROC, and listen at left OUTPUT. Start with GAIN near +1 and increase it to add more saturation. Try the negative side to compare the cleaner inverted response.

**Use the right side as a VCA.** Patch audio to right INPUT and an envelope to right EXT INPUT. Select VCA, adjust EXT LVL for the desired control amount, and set GAIN for the maximum level. The envelope has the deliberately softened attack and release of this mode.

**Build a two-stage chain.** Patch left OUTPUT to right INPUT. Set each stage independently—for example, left PROC for tone and right VCA for envelope movement. The stages remain separate if you instead feed two sources and take two outputs.

## Bypass and release notes

Host bypass passes left INPUT directly to left OUTPUT and right INPUT directly to right OUTPUT. It skips the processing rather than fading between processed and dry signals, so a click or pop is possible when bypass changes. Use a downstream mute if a silent transition matters.

Release 1.0.1 is a maintenance release of the approved 1.0.0 sound and routing. It retains the tested control ranges and modes and updates saved Designer presentation state. See the [release record](versions/1.0.1/README.md) for validation details.
