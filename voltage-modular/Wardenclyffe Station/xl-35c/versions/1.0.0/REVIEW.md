# XL-35c Low Pass Filters v1.0.0 — Release Review

## Scope and authorization

The user supplied the final XL-35c pair, designated it canonized, and requested 1.0.0 packaging and publication. The release cleanup preserves approved panel geometry, frequency tables, DSP, voicing, defaults, and routing. It replaces generic Designer names and Display Names, sets Category to Filters, confirms Notes as `v1.0.0`, synchronizes the source pair, and creates production folders and archives.

## Automated checks

- **PASS** — Java 17 target compile against the installed Voltage SDK with `-Xlint:all -Werror`.
- **PASS** — `.vmod` binary round-trip and embedded/exported source agreement.
- **PASS** — Filters category, version Notes, readable Display Names, control defaults, UUID preservation, and panel geometry preservation.
- **PASS** — Headless tests: all 16 cutoff positions meet the -3 dB target within 0.02 amplitude; DC behavior, band rejection, finite output, upper-stage polarity/drive, and one-time bypass-resume reset pass.
- **PASS** — SHA-256 manifest and repository integrity/link checks.
- **Not measured** — Native-host CPU profile or fresh panel rendering. User designated the supplied version canonized; no separate native-host check for metadata cleanup is claimed.

## Archive and cleanup

The canonical archive is `versions/1.0.0/`; it contains the source pair, artwork, manuals, developer notes, review, tests, and SHA-256 manifest. The earlier XL-39/filterEQ working drafts are removed from the active tree after the final pair and panel art were preserved here. Unrelated repository changes were excluded from the release.
