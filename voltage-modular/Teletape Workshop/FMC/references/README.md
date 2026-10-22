# FMC reference inventory

These references informed the four voices in the Four Channel Modulator. The pedal schematics, board photographs and product photographs are historical/technical research; original source and reuse rights were not recorded with several image files, so do not package those images in a published module or external publication without establishing provenance.

| File | Subject | Use and evidence |
| --- | --- | --- |
| [bf2-schematic.webp](bf2-schematic.webp), [bf2-board.png](bf2-board.png), [bf2-pedal.jpg](bf2-pedal.jpg) | BOSS BF-2 flanger | Circuit organization and control relationships; the DSP is a musical model, not a component-level reproduction. |
| [tr2-schematic.webp](tr2-schematic.webp), [tr2-board.jpg](tr2-board.jpg), [tr2-pedal.jpg](tr2-pedal.jpg) | BOSS TR-2 tremolo | LFO and amplitude-modulation inspiration; the combined macro/range controls are original workflow choices. |
| [dm2-schematic.webp](dm2-schematic.webp), [dm2-board.webp](dm2-board.webp), [dm2-pedal.webp](dm2-pedal.webp) | BOSS DM-2 analog delay | BBD, clock, filtering and compander reference. The model extends the nominal delay range and adds an optional grit control. |
| [dd2-schematic.webp](dd2-schematic.webp), [dd2-pedal.jpg](dd2-pedal.jpg) | BOSS DD-2 digital delay | Digital delay architecture and circuit reference; variable-rate memory, bit-depth control and extended time are modeling choices. |
| [amethyst-dm-2-documentation.pdf](amethyst-dm-2-documentation.pdf) | Aion FX Amethyst DM-2 project documentation, v1.2.0 | Build documentation for a modern DM-2-derived project. See its included license/use statement before reuse. It is not an EMS or BOSS service document. |

## Development material disposition

The old `development/reference` directory also contained CED, FPA and HGD scratch sources/tests, a Dimension-style chorus demo and an early CED brief. Those are not FMC design inputs: the shared chorus implementation is archived in `dsp-primitives`, and the owning module histories and tests remain under CED, FPA and HGD. They were removed from this module's working folder after confirming those owner archives; no FMC release depends on them. The stale standalone `FmcCore.java` was also removed because the current tested core is nested in the canonical `fmc.java` and extracted by the repeatable test harness.
