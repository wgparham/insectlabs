# FMC 1.0.0 release review

## Authorization and candidate

The user requested that this FMC set be canonized if it passed the closeout tests, followed by reference/development cleanup and a Git push. The candidate archives the four-lane panel and the synchronized `fmc.java` / `fmc.vmod` pair. It includes the source-level fixes to the SDK typed-edit callback and the host-bypass MIX path described in the release notes.

## Checks

- Exported Java and embedded Java compile against the installed Voltage SDK with Java 17 target, `-Xlint:all,-options -Werror`.
- VMOD parsing and encoding are byte-identical; exported/embedded Java agrees after newline normalization; class/package, version Notes, panel dimensions, 59-control UUIDs and identities, selector defaults, and Designer anchors pass.
- Headless DSP tests exercise voice-specific output, BF/TR and DM/DD parameter mappings, delay timing, DM HOLD, hostile inputs/parameters, finite-output ceilings, channel isolation and deterministic behavior.
- Stub-wiring tests exercise default voice assignment, selector/knob/CV/Hold notifications, single-call typed edits, active output and MIX behavior, direct bypass, bypass MIX and clean resume.
- A headless JDK 27 microbenchmark exercised four active voices. Four validation runs measured 1.13–1.55 microseconds per four-channel frame. This is a local microbenchmark, not a host CPU percentage or real-time performance guarantee.
- Release tests run against the files in this directory, not only the development copy. Release files are covered by `SHA256.json`.

## Limits

- The exact post-cleanup VMOD was not reopened and exercised in the native Designer/Voltage Modular UI during this closeout. The SDK compile, value-tree round-trip and automated wiring simulation pass; visual tooltip rendering, native patch save/reload and listening after the metadata cleanup remain a native-host check.
- The core is constructed at 48 kHz; other host sample rates are not validated. The four lanes allocate about 16.3 MiB of double-buffer storage at initialization. The benchmark has no target-host CPU/latency interpretation.
- Several pedal-inspired transfer functions and extended time ranges are models or estimates. FMC is not a component-accurate recreation. Reference provenance and reuse limits are recorded in the reference index.
- The repository-wide audit checked 1,254 local Markdown links, 77 source/VMOD pairs and 52 canonical module records. FMC's pair, links and manifest pass. Its only reported failures are the known SHA-256 mismatches for the separately edited FPA 1.0.0 Java and VMOD files; they are outside FMC and are excluded from this commit.

Cherry Audio publication remains deferred until the complete Teletape Workshop lineup is locked.
