# InsectLabs module infrastructure standards

These are collection-wide implementation defaults. They apply to new InsectLabs Voltage Modular modules unless a module's design notes explicitly specify an exception.

## Direct host bypass

Bypass follows the established Colorbox design:

- Use Voltage Modular's `ProcessBypassedSample()` callback rather than running the ordinary DSP with a wet/dry control.
- Read only the signal inputs needed to preserve the module's intended routing and normalization.
- Copy those signals directly to the corresponding outputs without control reads, CV processing, smoothing, oversampling, filtering, coloration, latency compensation, or crossfading.
- Do not advance DSP, envelope, VCA, filter, delay, or modulation histories while bypassed.
- Mark the processing state for resumption. On the first active sample, adopt the current controls and initialize input-dependent histories without replaying stale ramps.
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
