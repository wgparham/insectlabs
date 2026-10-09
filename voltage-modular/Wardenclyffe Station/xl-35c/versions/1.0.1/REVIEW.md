# XL-35c Low Pass Filters v1.0.1 — Release Review

## Scope

The current Java sources were supplied as the authoritative implementation and integrated into their Designer projects. This release preserves the approved panel geometry, filter response, cutoff tables, voicing, defaults, and routing. It resets filter histories on module initialization, preset/variation load, explicit reset, and bypass resume. Numeric frequency entry snaps to the nearest labeled step and the tooltips explain this behavior. Designer Notes are `v1.0.1`. The v1.0.0 archive remains unchanged.

## Checks

- **PASS** — Java 17 target compile against the Voltage SDK with `-Xlint:all -Werror`.
- **PASS** — Embedded/exported source agreement and binary VMOD round-trip.
- **PASS** — Module Notes version, filters category, control identifiers, cutoff defaults, and panel geometry.
- **PASS** — Headless response tests for all 16 cutoff positions, DC response, out-of-band rejection, finite output, upper-stage voicing, typed cutoff snapping, and filter-core reset behavior.
- **PASS** — Archive SHA-256 manifest and repository-wide consistency/link checks.
- **Not measured** — Runtime delivery of lifecycle reset events in the native host, fresh panel rendering, patch save/reload, and host CPU profile.

## Archive

The immutable release snapshot is `versions/1.0.1/`. The prior `versions/1.0.0/` snapshot is retained for rollback and comparison.
