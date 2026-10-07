# Sherlock 1.0.0 — release review

## Approval and scope

On 6 October 2026 the user reported the v0.1.0rc build worked, praised the ranges, and then
requested canonization. The current Designer re-export is the release baseline: it preserves
the tested DSP and incorporates a regenerated skin ID and revised drawing order.

Baseline SHA-256:
- Java: `afcd4c72ad18cfd432312a34a9ddb0228b0d036ec22b3263899d7807a30ece0c`
- VMOD: `3839c3b29190ca5f685e7a85ed151a343b470e7ec12acd7abb74c6bd4feb25e8`
- Hero: `cb53de70211ffe41e5b4eea22f604b7a2779c834486a1204a1f8ec6a5ca2c679`

Finalization only removes trailing source whitespace, strips working Notes to `v1.0.0`,
and synchronizes Designer source anchors. Java tokens and all DSP arithmetic are unchanged.
The user-authorized canonization covers this behavior-preserving metadata/formatting work.
The exact final Notes-cleaned project has not received a separate native-host audition.

## Review

- Descriptive lowerCamelCase IDs and human-readable names already applied in the tested candidate.
- UUIDs, skins/artwork, layout, ranges, defaults, saved test state, class/package and Utility category preserved.
- RATE/VC/tooltips and inverse typed edits covered; switch mapping verified against panel and Notes.
- Independent signal paths in processing and bypass; no implicit normalization.
- Direct bypass reads signal inputs only, silences pulses and freezes DSP/control histories.
- Resume starts fresh slopes with current controls; a high trigger/gate can start a new contour.
- START/SUSTAIN, busy edges, peak changes while held, polarity, DC, CV bounds and long slews reviewed.
- Native 48 kHz processing; core also checked at 96 kHz. No oversampling. High-rate hard resets can alias.
- Audio callback allocates no objects. Manual mappings/CV factors are cached; lamp writes occur on transitions.
  No native CPU percentage benchmark is claimed.
- Generated lifecycle retains exactly one `super.Destroy()`; no duplicate initialization or stale notification cases.
- Compact guard/tooltip branches retained from the user-tested source to avoid unnecessary executable changes.
- Useful working Notes transferred into the manual; no control Notes explicitly requested for retention.
- User confirmed functional build/listening. Individual native undo/save/reload tests were not separately reported.

## Validation

Run `tests/validate.py` from this archive. It compiles exported and embedded source with Java 17,
the installed SDK, selected warnings and `-Werror`; checks Designer round-trip and original metadata;
exercises signed slew, endpoints, ×1000 ranges, CV, contours, feedback and numeric boundaries;
and simulates a full 4000-second ramp at 48 kHz. Callback spies check exact bypass, indicator
states, control-read exclusion, typed edits and resumption.

The same actual callback bodies are run from the approved and final source; matching sample
trace hashes establish cleanup parity. Validation result is recorded before publication.
Full `-Xlint:all` is not claimed; see the repository's toolchain note.

No audio files are rendered by these checks; no new TestBench fixture is required.
Original mockup, approved pre-release and early user sketch are retained as references.
Unrelated Generator 2.0.0a and Sectronix work is outside this release.

## Publication

Archive: `voltage-modular/Wardenclyffe Station/sh-7437/versions/1.0.0`.
Archive validation and the staged repository audit must pass before commit/push.
Git history records publication; duplicate working files are removed only after comparison.
