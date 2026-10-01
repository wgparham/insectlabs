# InsectLabs

InsectLabs develops Voltage Modular instruments and a reusable audio TestBench. The active work is
the mono-first Laboratory collection: large manual instruments with restrained vintage weight,
inspired by early test equipment and electronic-music studios.

## Projects

| Project | Current state |
| --- | --- |
| [Colorbox](voltage-modular/colorbox/README.md) | Canonical RGB 4.0.3, CMYK 1.0.3, and HSB 1.0.2 source archives |
| [Laboratory](voltage-modular/laboratory/README.md) | Canonical Signal Processor 1.0.1, Fader&#124;Distr A/B 1.0.0, sw1 1.0.0, sw2 1.0.0, Generator 1.0.2, Function 1.0.0, SIN/RND 1.1.0, Deep Tone 1.0.0, and n01 Noise Source 1.0.0 |
| [Audio TestBench](test-bench/README.md) | Version 1.1, 54 measurement and listening WAV files at 48 kHz/24-bit |

The [collection roadmap](voltage-modular/docs/Collection-Roadmap.md) records the accepted
three-series direction. Ten Laboratory modules are canonical. Reference / Standards is next in the roadmap; its brief is still to be defined.

## Repository layout

- `voltage-modular/colorbox`: immutable Colorbox releases and shared source-pair tooling.
- `voltage-modular/laboratory`: canonical Laboratory releases, tests, references, and collection tools.
- `voltage-modular/docs`: collection planning and implementation standards.
- `test-bench`: standalone audio fixtures, provenance, generation/import tools, and its portable archive.

Release folders are immutable. Copy a release into a new module-specific development location before
starting a future revision. Generated build files, editor backups, caches, SDKs, manuals, and personal
working archives stay outside Git.

[Repository checks and validation commands](tools/README.md) describe the integrity audit and module test entry points.
