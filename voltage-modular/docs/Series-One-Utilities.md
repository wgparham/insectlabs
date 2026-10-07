# Wardenclyffe Station utility instruments

This document records utility capabilities in the completed collection. Module release manuals and review records are authoritative for exact behavior.

| Product | Current version | Role |
| --- | ---: | --- |
| [Type 23 Signal Processor](../Wardenclyffe%20Station/type-23/versions/2.0.0/README.md) | 2.0.0 | Two independent mono PROC/VCA stages with bipolar gain, post-character offset and external control input |
| [6121198a Fader / Distributer A](../Wardenclyffe%20Station/6121198a/versions/2.0.0/README.md) | 2.0.0 | Linear X/Y-to-Z crossfade and S-to-1/2 distribution |
| [6121198b Fader / Distributer B](../Wardenclyffe%20Station/6121198b/versions/2.0.0/README.md) | 2.0.0 | Equal-power variant with a related, distinct relay voice |
| [SW1 Relay Switch](../Wardenclyffe%20Station/sw1/versions/2.0.0/README.md) | 2.0.0 | Patch-programmable relay switching with optional CLK contact filter |
| [SW2 Switch / Distributer](../Wardenclyffe%20Station/sw2/versions/2.0.0/README.md) | 2.0.0 | Manual source selector and output distributor with optional CLK treatment |
| [1919/ln Tone Burst Generator](../Wardenclyffe%20Station/1919-ln/versions/2.0.1/README.md) | 2.0.1 | Two counted audio gates, external/internal clocking and independent Courtesy clocks |
| [Type 9414 Frequency Analyzer](../Wardenclyffe%20Station/type-9414/versions/2.0.0/README.md) | 2.0.0 | Selective band-pass, matched band-reject and exact splitter bypass |
| [c2-34 Balanced Modulator](../Wardenclyffe%20Station/c2-34/versions/2.0.0/README.md) | 2.0.0 | Carrier-suppressed four-quadrant modulation and unipolar VCA region |

These eight functions remain mono-first and manually oriented. Later Radiophonic plans add more CV-controlled switching and performance routing. The final planned Wardenclyffe instrument is a mono summing/mixing amplifier; see [future module proposals](Future-Module-Proposals.md) for its open brief boundary.

## Shared operating conventions

- Ordinary unpatched inputs contribute zero unless the module manual documents another normalization.
- Fader/distributor controls are manual and shared across two independent functions; there is no hidden stereo architecture or CV law selection.
- SW1 and SW2 are manual switching devices. SW2 initializes and resets to OFF.
- Host BYPASS is the shared direct patch-through/silence path. It skips filters and character and can click. Physical POWER behavior is device-specific.
- Character is assigned by product: Type 23 has gain-dependent drive and vactrol response; the 6121198 pair carries the strongest relay weight; the switches have their own contact voicing.
- +5 V is the common working modulation reference. Treat any future +10 V interface as a documented module-specific exception.

For the full sixteen-product inventory, see the [roadmap](Collection-Roadmap.md) and [collection manual](../Wardenclyffe%20Station/USER-MANUAL.md).
