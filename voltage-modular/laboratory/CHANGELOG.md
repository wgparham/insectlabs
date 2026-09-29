# Laboratory release notes

## Signal Processor 1.0.0 — 2026-09-29

First canonical release, approved by the user after a successful Designer build and host run.

- Two independent mono stages with matching defaults: PROC, Gain +1, Offset 0, Ext Lvl +1.
- Symmetric -3 to +3 gain; bipolar PROC with lighter character for negative gain.
- Unipolar VCA with rounded response and 3 ms attack / 30 ms release.
- Exact post-character offset, +5 V external gain-control reference, and restrained 2x character processing.
- Colorbox-standard direct bypass, consistent descriptive control IDs, and tooltips for all controls and jacks.
- Preserved Designer section identifiers and corrected final user-code boundaries.

Validation: both source forms compile against the Voltage Modular SDK targeting Java 17;
25 DSP checks and 123 callback checks pass. Project/source agreement, hashes, and named
Designer section boundaries are checked. The user confirmed the final project builds and runs beautifully.
No numerical CPU benchmark was recorded.

These are source archives, not prebuilt installable modules. Start future edits from a copy of the release.
