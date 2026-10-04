# InsectLabs

InsectLabs develops Voltage Modular instruments and a reusable audio TestBench. The active work is the mono-first Laboratory collection: large manual instruments with restrained vintage weight, inspired by early test equipment and electronic-music studios.

## Projects

| Project | Current state |
| --- | --- |
| [Colorbox](voltage-modular/colorbox/README.md) | Canonical RGB 4.0.3, CMYK 1.0.3, and HSB 1.0.2 source archives |
| [Laboratory](voltage-modular/laboratory/README.md) | Twelve canonical modules; Selective Amplifier is next |
| [Audio TestBench](test-bench/README.md) | Collection v1.2: 58 checked-in WAV files at 48 kHz/24-bit; portable ZIP remains the v1.1 snapshot |

The [collection roadmap](voltage-modular/docs/Collection-Roadmap.md) records the accepted three-series direction. All twelve currently developed Laboratory modules are canonical. The Selective Amplifier is next; four accepted Series-One roles remain planned.

## Repository layout

- `voltage-modular/colorbox`: immutable Colorbox releases and shared source-pair tooling.
- `voltage-modular/laboratory`: canonical Laboratory releases, tests, references, and collection tools.
- `voltage-modular/docs`: collection planning and implementation standards.
- `test-bench`: standalone audio fixtures, provenance, generation/import tools, and its portable archive.

Release folders are immutable. Copy a release into a new module-specific development location before starting a future revision. Generated build files, editor backups, caches, SDKs, manuals, and personal working archives stay outside Git.

[Repository checks and validation commands](tools/README.md) describe the integrity audit and module test entry points.
