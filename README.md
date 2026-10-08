# InsectLabs

InsectLabs develops Voltage Modular instruments and a reusable audio TestBench.

| Project | Current state |
| --- | --- |
| [Colorbox](voltage-modular/colorbox/README.md) | RGB, CMYK and HSB canonical releases |
| [Wardenclyffe Station](voltage-modular/Wardenclyffe%20Station/README.md) | Eighteen canonical mono-first modules, including LM-21 and RM1010; earlier 1.x archives retained |
| [DSP primitives](dsp-primitives/README.md) | Reusable SDK-independent audio filters and building blocks |
| [Reverb primitives](reverb-primitives/README.md) | SDK-independent reusable Java mono reverb and parameter helpers |
| [Minimixer](voltage-modular/Wardenclyffe%20Station/minimixer/README.md) | RM1010 Mixing Amplifier canonical v1.0.0 |
| [Audio TestBench](test-bench/README.md) | Standalone reusable audio fixture collection |

The [Voltage Modular roadmap](voltage-modular/docs/Collection-Roadmap.md) tracks the finished Wardenclyffe collection, separate minimixer work and the flexible Radiophonic and computation series.

## Repository layout

- `voltage-modular/colorbox`: Colorbox modules and shared source-pair tooling.
- `voltage-modular/Wardenclyffe Station`: the complete postwar laboratory collection, release archives, manuals and review records.
- `voltage-modular/docs`: product roadmap, decisions, infrastructure standards and release checklist.
- `reverb-primitives`: reusable standalone DSP extracted from the LM-21 prototype.
- `test-bench`: standalone audio fixtures, provenance, generation/import tools and its portable archive.
- `tools`: repository integrity audit and shared maintenance utilities.

Released source pairs are versioned and immutable. Create a development copy for a future revision. Keep generated classes, SDKs, caches, editor backups and personal reference archives outside Git.
