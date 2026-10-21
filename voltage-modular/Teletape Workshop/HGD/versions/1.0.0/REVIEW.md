# HGD 1.0.0 — release review

## Authorization and candidate

On 2026-10-21 the user confirmed that the HGD working set compiles, loads and passes their functional tests, and authorized canonization, housekeeping and a Git push if the checks pass. The candidate preserves the four DSP voices and the supplied panel. Cleanup synchronized the Designer embed with the exported Java, set Designer Notes to `v1.0.0`, aligned Gain's numeric display setting across all lanes, and replaced underscore-suffixed label/guide metadata with descriptive names. DSP algorithms, coefficients, routing and defaults were not changed.

## Checks

- Exported Java and embedded Java compile against the installed Voltage SDK using Java 17, `-Xlint:all -Werror`.
- VMOD parses and re-encodes byte-identically; exported/embedded source agrees after normalization; Notes is `v1.0.0`; control UUIDs are unique; Designer line anchors remain in range.
- The headless harness exercises all four voice outputs, drive/tone/III endpoints, ±10 input limits, NaN/infinity handling, the soft output ceiling, channel independence, deterministic HM noise, and the half-sum mix law.
- The user reports native compilation, load, run and functional/panel testing of the working candidate. The exact post-cleanup VMOD was not reopened in Designer after its embed and metadata-only synchronization.
- Release files are covered by `SHA256.json`. The repository-wide audit checked 1,216 local links and the HGD manifest; it reported two hash mismatches in the separate FPA 1.0.0 source pair, which has unrelated local edits and is outside this release scope.

## Limits

No target-host CPU or latency benchmark was made. The module constructs its core for 48 kHz; other host rates are unverified. Automated tests do not replace a native UI/tooltip/preset save-reload check after the metadata cleanup. The hardware references are inspiration; the DSP is not an exact circuit recreation. Publication remains deferred.
