# Teletape Workshop

Teletape Workshop is the new name for the planned Radiophonic series. It follows the completed Wardenclyffe Station collection and shifts toward electronic-music studio equipment rooted in the mid-1960s through mid-1970s. RVG, TSL, OFB, b08, SVF, TRM and ZOID are canonized. The complete series inventory is not yet locked.

## Accepted directions to explore

- More common CV control, including voltage-controlled switching.
- Modern manual switches and routers for studio performance.
- Studio contour and filtering tools, including a possible active Baxandall design distinct from Wardenclyffe’s passive 21-24eq.
- A frequency shifter with separate UP and DOWN outputs.
- External sample-and-hold or track-and-hold, and tape/delay/reverb instruments.
- Further random, sample-and-hold and performance instruments informed by studio workflows.

These are design directions, not a committed product list. Keep the general CV reference at +5 V unless a future module brief establishes a specific reason to use another scale.

## Canonical modules

| Product | Current version | Role | Manual |
| --- | ---: | --- | --- |
| [RVG Random Voltage Generator](RVG/versions/2.0.1/) | 2.0.1 | Shaped dual stepped random voltage with variable event timing, manual/external selection and selectable hold drift | [User manual](RVG/versions/2.0.1/USER-MANUAL.md) |
| [b08 Blanking Panel](b08/versions/1.0.0/) | 1.0.0 | 8 HP visual blanking panel; no custom DSP | [User manual](b08/versions/1.0.0/USER-MANUAL.md) |
| [TSL Triple Slew Limiter](TSL/versions/1.0.0/) | 1.0.0 | Two CV slew channels and one slew/track-and-hold channel | [User manual](TSL/versions/1.0.0/USER-MANUAL.md) |
| [OFB 8 Octave Filter Bank](OFB/versions/1.0.0/) | 1.0.0 | Eight fixed bands, level CV, clean taps and warm mix | [User manual](OFB/versions/1.0.0/USER-MANUAL.md) |
| [SVF Dual State Variable Filter](SVF/versions/1.0.1/) | 1.0.1 | Dual Synthi 100-derived core with simultaneous HIGH/BAND/LOW outputs and selectable routing | [User manual](SVF/versions/1.0.1/USER-MANUAL.md) |
| [TRM Triple Ring Modulator](TRM/versions/1.0.0/) | 1.0.0 | Three independent gain-staged ring-modulator lanes with four distinct voices | [User manual](TRM/versions/1.0.0/USER-MANUAL.md) |
| [ZOID Trapezoid Generator](ZOID/versions/1.0.0/) | 1.0.0 | One four-stage envelope drives two segment-rotated control outputs and a logarithmic VCA | [User manual](ZOID/versions/1.0.0/USER-MANUAL.md) |

The RVG's [developer notes](RVG/DEVELOPER-NOTES.md), [release review](RVG/versions/2.0.0/REVIEW.md), and [shared references](references/README.md) record implementation, validation and research. The RVG models EMS workflow and feel; it is not an exact circuit reconstruction. The b08's [developer notes](b08/DEVELOPER-NOTES.md) and [release review](b08/versions/1.0.0/REVIEW.md) document its artwork-only panel and validation.

Shared [design resources](designResources/README.md) inventory the series artwork and font license. EMS schematics, manuals and broader circuit references are catalogued in the [reference index](references/README.md).

For current project decisions and optional ideas, see [Voltage Modular documentation](../docs/README.md), [Future Module Proposals](../docs/Future-Module-Proposals.md), and [Cherry Audio Notes](Cherry-Audio-Notes.md).

## Publication plan

Cherry Audio publication is deferred until the complete module list is locked and every module on that list is canonized. The user will publish the canonical collection in one session. Git synchronization continues during development. See [publication plan](PUBLICATION-PLAN.md).

