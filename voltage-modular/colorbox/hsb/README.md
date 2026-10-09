# hsb

[Complete user manual](USER-MANUAL.md)

**Two-stage tone and fuzz processor · v1.0.2**

hsb combines two independent processors, each following HUE → SATURATION → BRILLIANCE. From gentle thickening to dense fuzz and unruly distortion, its colors emerge from three simple controls working together.

**HUE** tilts the tone before distortion, moving between bass-heavy warmth and brighter emphasis. **SATURATION** adds drive, compression, and fuzz. **BRILLIANCE** shapes the result, moving from smooth, softened highs through a neutral center to pronounced presence and air.

Each processor has its own **STANDARD / CUSTOM switch**. Up selects STANDARD: thick, sustained, Muff-inspired fuzz without the traditional tone stack. Down selects CUSTOM: aggressive, input-sensitive scramble fuzz inspired by the Soda Meiser, with increasingly unruly textures as drive rises.

Individual inputs and outputs support mono, stereo, and dual-mono patches. The lower input receives the upper input when unpatched; the lower output sums both processors when the upper output is unpatched.

**Bypass prioritizes low CPU use.** Switching it on or off can produce pops and clicks because processing stops immediately without a crossfade. Use an external mute when you need a quiet transition—or embrace the rough edges as part of its DIY electronic character.

[Current source files and Designer project](versions/1.0.2/)

See [developer notes](DEVELOPER-NOTES.md) for the source map, DSP implementation details, bypass contract, and maintenance checklist.
