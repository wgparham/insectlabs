# Development setup

## Build output

Compile Java class output outside Dropbox and Git at `C:\InsectLabs-Build\<module>`. Clear the module-specific output folder before a clean build. This separates disposable compiler output from synced source and immutable releases. It has compiled successfully in this workflow; the earlier write failure was not conclusively diagnosed as a file lock. No antivirus exclusion is required by this convention.


## Current repository state

- Canonical remote: https://github.com/wgparham/insectlabs
- Local checkout: `C:/Users/wgparham/Dropbox/git/insectlabs`
- Active collection: [Laboratory](../laboratory/README.md)
- Shared audio fixtures: [TestBench](../../test-bench/README.md)
- Current Laboratory releases: Signal Processor 1.0.1, Fader|Distr A/B 1.0.0, sw1 1.0.0, sw2 1.0.0, Generator 1.0.2, Function 1.0.0, SIN/RND 1.1.0, Deep Tone 1.0.0, and n01 Noise Source 1.0.0.
- Next proposed module: Reference / Standards; n01 Noise Source is canonical 1.0.0.

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

## Before replacing a working pair

Read the latest Designer export and fingerprint it before editing; do not overwrite panel changes with an older candidate. Check numeric and serialized/text default fields together, especially for calibrated frequencies. Keep embedded/exported source synchronized and review generated lifecycle callbacks after export. Preserve canonical Java bytes and line endings; routine text normalization is not a release repair. See the [release checklist](Module-Release-Checklist.md).

## External references

Manual PDFs remain in the external workspace `Resources` directory. The roadmap records the
specific Berna, Moog, Q125/Q123, Serge, and modular-software references. Keep SDK reference material alongside these resources; the repository contains implementation standards and source-validation tools.

## Current release gate

n01 1.0.0 is the latest Laboratory release at this review; its [review](../laboratory/n01/versions/1.0.0/REVIEW.md) records approval, cleanup parity and validation.
The [canonical index](../laboratory/CANONICAL.json) is the authority for all current versions.
Use [Module release checklist](Module-Release-Checklist.md) before archiving any subsequent module.


## Repository checks

From the repository root, run `python tools/check_repository.py` for a read-only audit of tracked release hashes, source pairs, canonical paths and local Markdown file links. Newly added files must be staged to enter the tracked-file audit. The command does not change Designer projects, compile Java or contact the network.

For SDK and DSP checks, see [Validation commands](../../tools/README.md). Older module-specific tests may be fixed to a historical release; they are not interchangeable with tests for the newest release.
