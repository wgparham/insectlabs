# Development setup

## Build output

Compile Java class output outside Dropbox and Git at `C:\InsectLabs-Build\<module>`. Clear the module-specific output folder before a clean build. This avoids sync/indexing contention with `javac` class-file writes while keeping source and immutable releases in the repository.


## Current repository state

- Canonical remote: https://github.com/wgparham/insectlabs
- Local checkout: `C:/Users/wgparham/Dropbox/git/insectlabs`
- Active collection: [Laboratory](../laboratory/README.md)
- Shared audio fixtures: [TestBench](../../test-bench/README.md)
- Current Laboratory releases: Signal Processor 1.0.1, Fader|Distr A/B 1.0.0, sw1 1.0.0, sw2 1.0.0, and Generator 1.0.2.
- Next proposed module: Pulse/Sine Generator; Generator is canonical 1.0.2.

The accepted direction and unbuilt inventory live in [Collection Roadmap](Collection-Roadmap.md).
[Module Infrastructure Standards](Module-Infrastructure-Standards.md) defines the shared
source-pair, bypass, DSP, and validation contract.

## Toolchain

- Voltage Module Designer SDK: `C:/ProgramData/Voltage/voltage.jar`
- Installed Java: JDK 27. Compile sources with the established Java 17 target:
  `javac --release 17 -Xlint:all -Werror`.
- Python scripts run with the available local Python runtime. The TestBench tools require NumPy.

Colorbox validation has been run against this SDK and Java 17 target. Module-specific validation
and Designer/host listening are still required for every release.

## Working rules

- A `.vmod` and its exported `.java` are a matched pair. Synchronize embedded source after a code
  change, preserve Designer control UUIDs/positions/test state, then validate both forms.
- Keep canonical releases immutable under `versions/<version>/`. Create a module-specific
  development copy only when beginning a new revision.
- Use direct host bypass as defined in the infrastructure standards. Keep audio callbacks
  allocation-free and avoid processing-control reads while bypassed.
- Linear utilities run at native rate unless sound tests justify additional processing. Use the
  established 2x approach for nonlinear stages that need it.
- Series One is mono-first and manual-first. +5 V is the working modulation reference; +10 V
  remains deferred for an individual input that specifically warrants it.
- Add useful fixtures to TestBench with catalog, provenance, attribution, manifest, and verification
  updates. Do not copy SDKs, manuals, generated classes, editor backups, caches, or personal archive
  folders into Git.

## External references

Manual PDFs remain in the external workspace `Resources` directory. The roadmap records the
specific Berna, Moog, Q125/Q123, Serge, and modular-software references. The official Voltage
Module Designer documents are linked from the roadmap and infrastructure standards.

## Current release gate

Generator 1.0.2 is canonical in `laboratory/generator/versions/1.0.2/`; its final review and
user approval are recorded in `REVIEW.md`.
Use [Module release checklist](Module-Release-Checklist.md) before archiving any subsequent module.

