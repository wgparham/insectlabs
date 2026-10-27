# Teletape Workshop

Teletape Workshop is the new name for the planned Radiophonic series. It follows the completed Wardenclyffe Station collection and shifts toward electronic-music studio equipment rooted in the mid-1960s through mid-1970s. RVG, TSL, OFB, b08, CXL, SVF, TRM, ZOID, N02, OSC, CED, FPA, HGD, FMC, SN93T, DCA and SW3 are canonized. The complete series inventory is not yet locked.

## Accepted directions to explore

- More common CV control, including voltage-controlled switching.
- Modern manual switches and routers for studio performance.
- Studio contour and filtering tools, including a possible active Baxandall design distinct from Wardenclyffe’s passive 21-24eq.
- A frequency shifter with separate UP and DOWN outputs.
- Additional sample-and-hold capacity beyond the track-and-hold already present on TSL channel 3, and tape/delay/reverb instruments.
- Further random, sample-and-hold and performance instruments informed by studio workflows.

[N02 Dual Noise Generator](n02/README.md) is canonical at v1.0.0, with an EMS-inspired analog voice and a clocked digital source that automatically selects 7/15/31-bit sequences with SPEED. [OSC Primary Oscillator](OSC/README.md) is canonical at v1.0.0, with shaped waveform outputs, dedicated V/oct pitch and through-zero linear FM. [SW3 Voltage Controlled Switch](sw3/README.md) is canonical at v1.0.0, providing independent 2-to-1 and 1-to-2 gate-controlled signal routes. The next proposed concept is a 16-way MUX/DEMUX router; its panel and signal-flow brief remain subject to design review.

[CXL Algorithmic Reverberation](CXL/README.md) is canonical at v1.0.0, with an EMT/Dattorro-inspired shared plate tank and user-approved 8.5-bit extended converter character. Publication remains deferred.

[CED Chorus Ensemble Device](CED/README.md) is canonical at v1.0.0, combining four Dimension-style ensemble programs with a CE-1-style chorus/vibrato voice. Its mode combination laws and tonal controls are original workflow extensions; publication remains deferred.

These are design directions, not a committed product list. Keep the general CV reference at +5 V unless a future module brief establishes a specific reason to use another scale.

## Canonical modules

| Product | Current version | Role | Manual |
| --- | ---: | --- | --- |
| [RVG Random Voltage Generator](RVG/versions/2.0.1/) | 2.0.1 | Shaped dual stepped random voltage with variable event timing, manual/external selection and selectable hold drift | [User manual](RVG/versions/2.0.1/USER-MANUAL.md) |
| [b08 Blanking Panel](b08/versions/1.0.0/) | 1.0.0 | 8 HP visual blanking panel; no custom DSP | [User manual](b08/versions/1.0.0/USER-MANUAL.md) |
| [CED Chorus Ensemble Device](CED/versions/1.0.0/) | 1.0.0 | Four-program stereo ensemble and CE-1-style chorus/vibrato | [User manual](CED/versions/1.0.0/USER-MANUAL.md) |
| [CXL Algorithmic Reverberation](CXL/versions/1.0.0/) | 1.0.0 | Dark cross-coupled plate-style reverb with 12-bit and grainy extended modes | [User manual](CXL/versions/1.0.0/USER-MANUAL.md) |
| [DCA Dual Controlled Amplifier](DCA/versions/1.0.0/) | 1.0.0 | Two CV-controlled amplifier channels with IND/SERIAL/ST modes, bias, shape, stereo pan and mono fold-down | [User manual](DCA/versions/1.0.0/USER-MANUAL.md) |
| [TSL Triple Slew Limiter](TSL/versions/1.0.0/) | 1.0.0 | Two CV slew channels and one slew/track-and-hold channel | [User manual](TSL/versions/1.0.0/USER-MANUAL.md) |
| [OFB 8 Octave Filter Bank](OFB/versions/1.0.0/) | 1.0.0 | Eight fixed bands, level CV, clean taps and warm mix | [User manual](OFB/versions/1.0.0/USER-MANUAL.md) |
| [SVF Dual State Variable Filter](SVF/versions/1.0.1/) | 1.0.1 | Dual Synthi 100-derived core with simultaneous HIGH/BAND/LOW outputs and selectable routing | [User manual](SVF/versions/1.0.1/USER-MANUAL.md) |
| [TRM Triple Ring Modulator](TRM/versions/1.0.0/) | 1.0.0 | Three independent gain-staged ring-modulator lanes with four distinct voices | [User manual](TRM/versions/1.0.0/USER-MANUAL.md) |
| [ZOID Trapezoid Generator](ZOID/versions/1.0.0/) | 1.0.0 | One four-stage envelope drives two segment-rotated control outputs and a logarithmic VCA | [User manual](ZOID/versions/1.0.0/USER-MANUAL.md) |
| [N02 Dual Noise Generator](n02/versions/1.0.0/) | 1.0.0 | Independent analog/digital noise, automatic 7/15/31-bit sequence lengths, colour and level controls, and equal-power blend | [User manual](n02/versions/1.0.0/USER-MANUAL.md) |
| [OSC Primary Oscillator](OSC/versions/1.0.0/) | 1.0.0 | Shared-phase shaped sine, triangle/ramp and pulse, V/oct pitch and through-zero linear FM | [User manual](OSC/versions/1.0.0/USER-MANUAL.md) |
| [FPA Four Pre-Amps](FPA/versions/1.0.0/) | 1.0.0 | Four independent preamp/overdrive voices with individual outputs and a summed mix | [User manual](FPA/versions/1.0.0/USER-MANUAL.md) |
| [HGD High Gain Device](HGD/versions/1.0.0/) | 1.0.0 | Four independent high-gain voices with individual outputs and a summed mix | [User manual](HGD/versions/1.0.0/USER-MANUAL.md) |
| [FMC Four Channel Modulator](FMC/versions/1.0.0/) | 1.0.0 | Four selectable flanger, tremolo, analog-delay and digital-delay lanes with individual outputs and a half-sum mix | [User manual](FMC/versions/1.0.0/USER-MANUAL.md) |
| [SN93T Tuner](sn93t/versions/1.1.0/) | 1.1.0 | Monophonic tuner with custom temperaments, tone, pitch CV and VCO calibration | [User manual](sn93t/versions/1.1.0/USER-MANUAL.md) |
| [SW3 Voltage Controlled Switch](sw3/versions/1.0.0/) | 1.0.0 | Independent X 2-to-1 and Y 1-to-2 routes with gate/latch control and variable fade | [User manual](sw3/versions/1.0.0/USER-MANUAL.md) |

The RVG's [developer notes](RVG/DEVELOPER-NOTES.md), [release review](RVG/versions/2.0.0/REVIEW.md), and [shared references](references/README.md) record implementation, validation and research. The RVG models EMS workflow and feel; it is not an exact circuit reconstruction. The b08's [developer notes](b08/DEVELOPER-NOTES.md) and [release review](b08/versions/1.0.0/REVIEW.md) document its artwork-only panel and validation. FPA adds four independent preamp/overdrive voices inspired by CE-1, RE-201/301, BD-2 and SD-1 circuits; see its [design research](FPA/references/FPA-Design-Research.md) and [developer notes](FPA/DEVELOPER-NOTES.md).

DCA preserves its [development brief history](DCA/research/Design-Brief-History.md) and [iteration record](DCA/research/Development-History.md). Shared [design resources](designResources/README.md) inventory the series artwork and font license. EMS schematics, manuals and broader circuit references are catalogued in the [reference index](references/README.md).

For current project decisions and optional ideas, see [Voltage Modular documentation](../docs/README.md), [Future Module Proposals](../docs/Future-Module-Proposals.md), and [Cherry Audio Notes](Cherry-Audio-Notes.md).

## Publication plan

Cherry Audio publication is deferred until the complete module list is locked and every module on that list is canonized. The user will publish the canonical collection in one session. Git synchronization continues during development. See [publication plan](PUBLICATION-PLAN.md).


See the [collection changelog](CHANGELOG.md) for the latest closeout.
