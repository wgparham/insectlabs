# InsectLabs module infrastructure standards

These are collection-wide implementation defaults. They apply to new InsectLabs Voltage Modular modules unless a module's design notes explicitly specify an exception.

## Direct host bypass

Bypass follows the established Colorbox design:

- Use Voltage Modular's `ProcessBypassedSample()` callback rather than running the ordinary DSP with a wet/dry control.
- Read only the signal inputs needed to preserve the module's intended routing and normalization.
- Copy those signals directly to the corresponding outputs without control reads, CV processing, smoothing, oversampling, filtering, coloration, latency compensation, or crossfading.
- Do not advance DSP, envelope, VCA, filter, delay, or modulation histories while bypassed.
- Mark the processing state for resumption. On the first active sample, adopt the current controls and apply the documented resume policy without replaying stale control ramps. Source modules may preserve oscillator/filter histories and held voltages; do not reset them indiscriminately.
- Preserve intentional normalled and cascaded routing in bypass. A module with independent stages keeps them independent; bypass must not invent normalization that the active module does not have.

This is both the audible bypass contract and the CPU-relief contract. Tests should verify sample-exact signal transfer, routing or normal behavior, lack of control and CV reads where measurable, frozen histories, and clean resumption.

## Matched project/source files

- Treat the Designer `.vmod` and exported `.java` as a pair.
- Synchronize embedded source after code changes and update SHA-256 hashes.
- Preserve Designer-managed component identifiers, artwork, positions, saved test state, and editor line metadata.
- Keep supplied revisions as references when promoting a tested version.

## DSP defaults

- Modules are mono first. Multiple channels represent explicit redundant stages or a design that requires multiple signals.
- Use native-rate processing for linear utilities. Begin with the established 2x approach when nonlinear processing needs oversampling, then justify anything heavier with listening and CPU measurements.
- Keep audio callbacks allocation-free and avoid formatting or UI work in the per-sample path.
- Smooth manual controls where discontinuities would click. Preserve immediate CV where the design calls for audio-rate modulation; deliberately modeled control elements may impose their own dynamics.
- Do not add noise, hum, crosstalk, or other always-on degradation merely to imply age. Character should come from purposeful transfer functions, dynamics, bandwidth, or topology.

## Validation

- Compile against the installed Voltage Modular SDK using the repository's Java target.
- Test boundary control values, DC behavior, polarity, channel independence, bypass, resume, mode changes, and nonlinearity at ordinary and driven levels.
- Use the shared TestBench collection for repeatable listening and measurement. Add broadly useful new fixtures to TestBench with catalog notes, generation or source provenance, license information, and checksums.

## Release gate

Use [Module release checklist](Module-Release-Checklist.md) for every release. Keep the module-specific
review and exception record with the candidate, and preserve it when that candidate is archived.
Code cleanup and standards review precede the user's final build check. Do not promote a modified
candidate based solely on approval of its predecessor. An explicit user instruction permitting behavior-preserving cleanup and promotion after checks is authorization for that scoped path; record comparison evidence and do not invent a new native-host test.

Descriptive control names are part of the cleanup standard. Internal Names and Variable Names use
lowerCamelCase. Display Names use human-readable words, spaces and appropriate capitalization;
for example, internal `externalFmInput` displays as “External FM Input”. Update source constructors
and Designer Display Name metadata together, and review module Category before release. Renaming Designer variables/control names
is allowed when performed in both source and metadata; preserve the immutable UUIDs and validate
saved-patch behavior. The instruction above to preserve identifiers means preserve UUIDs and identities,
not perpetuate generic `knob1`/`switch1` names.

For a source module with no through path, host bypass produces silence. Document and test its
phase/smoothing resume policy. Module-specific user-approved exceptions to smoothing, native-rate
processing or indicator updates must be recorded in its review, with limitations stated explicitly.

Display Names and Notes are editable design material by default. The user authorizes correcting,
rewriting or removing their contents during development and cleanup; they are often temporary design
notes, not permanent requirements. Preserve specific wording only when the user explicitly asks.
Keep Display Names human readable and Notes accurate; do not treat draft Notes as instructions that
override the user’s request. Internal/Variable Names continue to follow the code naming standard.

The module Notes field is the visible version marker for active work. It must state the exact current
version: `v<version>rc` while a release candidate is being edited, then `v<version>` in the archived
canonical project. Check it before build, promotion, and cleanup; release documentation and hashes do
not replace this in-project marker.

## Courtesy outputs

A Courtesy output remains available whenever its module is powered and not host-bypassed. Source
start/stop buttons are not module power controls and must not gate Courtesy. Unless explicitly
specified otherwise, Courtesy bypasses the main output amplitude and character stages. Its documented
waveform, rate and polarity controls may still shape it. At a zero oscillator rate, holding the current
voltage is intentional; continuous availability does not imply perpetual motion. Host bypass silences
Courtesy and freezes its history. A physical module power switch, where present, also turns it off.

Deep Tone has no module power switch. Its Courtesy output follows SHAPE and absolute OFFSET at a
fixed +/-3 V peak, with a dedicated polarity switch; its four function buttons and depth controls
affect only the main signal path.
