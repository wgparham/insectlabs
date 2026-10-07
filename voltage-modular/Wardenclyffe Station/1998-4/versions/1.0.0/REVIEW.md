# Following 1.0.0 — release review

## Approval and scope

The user accepted v0.2.1rc with “perfect. canonize it and start the finalization processes”.
That authorizes the behavior-preserving finalization and publication performed here.
This is the first canonical release. No new audible behavior was introduced after approval.

Approved baseline SHA-256:

- Java: `0dd20398cbebf50e73e6f43240610ab59ad00746a24a62f7c53ef8530b6ab1af`
- VMOD: `34c2d7ab97747fb6f589186004fd75e465149a349527dc62a04a88d70b360cf7`
- Hero: `52912d4db2a5c800fb414f3069b0b1e117c05fdd440ec3f5b4ec49afe9f5b5eb`

The exact cleaned pair has not received a separate native-host audition. Approval refers
to the preceding v0.2.1rc candidate; cleanup is covered by the explicit finalization
request and automated parity evidence. Save/reload and native undo were not individually
reported by the user, so this record does not assert separate tests of them.

## Completed review

- [x] Latest paired export, project and hero fingerprinted before editing.
- [x] Descriptive lowerCamelCase control, jack, indicator and label identities; human-readable
  Display Names. Generic label identifiers were the remaining naming cleanup.
- [x] Control UUIDs, ranges, numeric/text defaults, initial switch states, positions, skins,
  artwork, saved test state and class/package identity preserved. Category remains Utility.
- [x] Input, output and mode tooltip coverage reviewed; inverse edits tested at interior values,
  limits and nonfinite inputs. SDK inspection confirms `SetValue(double)` and the base edit
  callback both call `SetValue(double, true)`; typed edits retain that SDK behavior.
- [x] Lifecycle scaffold reviewed: `super.Destroy()` occurs exactly once; initialization creates
  no duplicate processor and the audio callbacks allocate no per-sample objects.
- [x] Direct bypass reads only S and performs no control reads, processing or history advancement.
- [x] Power-off, startup, AUTO/MANUAL behavior, LED edges, delay clearing and resume reviewed/tested.
- [x] Whole-envelope delay, delayed gate alignment, blend, polarity, ceilings, input extremes,
  high-pass/DC behavior and two-stage release tested on the actual core at 48/96 kHz.
- [x] Both embedded and exported source compile with Java 17 target, selected warning categories,
  and `-Werror` against the installed `voltage.jar`. Full `-Xlint:all` is not claimed (toolchain
  limitation already recorded in Development Setup).
- [x] Approved versus cleaned audio/bypass callback traces match exactly:
  `TRACE=18196480542377192430`. DSP core and audio/bypass callback
  tokens are unchanged; final whitespace was tidied. Representative traces cover changing input, gates, power and bypass.
- [x] Actual callback-body tests with host-object spies verify lamp states, independent timing,
  transition-only writes and typed-unit inverses; native lamp rendering is covered by user approval.
- [x] Designer round-trip integrity, source agreement and editor anchors validated.
- [x] Working Notes transferred to the user manual/review. All Notes stripped except `v1.0.0`.
- [x] User-approved panel and current version marker archived with hashes and reproducible tests.

## Deliberate design choices and limits

SIGNAL THROUGH is post-gain/post-character during active operation and raw input during
host bypass. Envelope/gate courtesy outputs are silent in bypass. Power-off silences all
outputs; resumption clears detector/delay history. Gate LEDs have no pulse stretching.

The audio amplifier retains its approved 2x midpoint character approach; this is not a
brick-wall antialiasing system. The host wrapper runs at 48 kHz. The three-second double
delay buffer uses about 1.15 MB per instance. Expensive control mappings are cached;
indicator writes occur only on state changes. No native CPU percentage benchmark is claimed.

Tests use synthetic numerical input and do not render audio files. No new TestBench audio
fixture was needed. All established voicing and timing coefficients remain unchanged.

## Publication and cleanup

Release destination: `voltage-modular/Wardenclyffe Station/1998-4/versions/1.0.0`.
The canonical index, collection docs, manual inventory and roadmap are updated in the same
publication. Repository audit and archived-path validation are required before commit/push.
Development duplicates are removed only after archive checks; unrelated Sectronix work is
outside this release. The associated Git commit and remote history record publication.
