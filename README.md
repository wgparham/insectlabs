# InsectLabs

InsectLabs develops Voltage Modular instruments and a reusable audio TestBench.

| Project | Current state |
| --- | --- |
| [Colorbox](voltage-modular/colorbox/README.md) | RGB, CMYK and HSB canonical releases |
| [Wardenclyffe Station](voltage-modular/Wardenclyffe%20Station/README.md) | Sixteen canonized 2.x mono-first modules, each with its 1.x archive; one final mixer planned |
| [Audio TestBench](test-bench/README.md) | Standalone reusable audio fixture collection |

The [Voltage Modular roadmap](voltage-modular/docs/Collection-Roadmap.md) tracks the finished Wardenclyffe collection, the final planned mixer and the flexible Radiophonic and computation series.

## Repository layout

- `voltage-modular/colorbox`: Colorbox modules and shared source-pair tooling.
- `voltage-modular/Wardenclyffe Station`: the complete postwar laboratory collection, release archives, manuals and review records.
- `voltage-modular/docs`: product roadmap, decisions, infrastructure standards and release checklist.
- `test-bench`: standalone audio fixtures, provenance, generation/import tools and its portable archive.
- `tools`: repository integrity audit and shared maintenance utilities.

Released source pairs are versioned and immutable. Create a development copy for a future revision. Keep generated classes, SDKs, caches, editor backups and personal reference archives outside Git.
