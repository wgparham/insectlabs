# Colorbox for Voltage Modular

Canonical source projects from insect laboratories. Small mono processing stages combine through normalled routing into cascades, parallel processing, stereo, and dual mono patches.

| Module | Current version | Character |
| --- | --- | --- |
| [RGB](rgb/versions/4.0.3/) | 4.0.3 | Three saturation stages: red, green, blue |
| [CMYK](cmyk/versions/1.0.3/) | 1.0.3 | Four wavefolding stages with bipolar input levels and fold CV |
| [HSB](hsb/versions/1.0.2/) | 1.0.2 | Two HUE → SATURATION → BRILLIANCE processors |

Each release contains a Designer `.vmod`, matching exported `.java`, the supplied panel image, and SHA-256 checksums. Open the `.vmod` in Voltage Module Designer to build/test/install. Keep the project and source together. Package names and control identifiers are preserved.

HSB initializes with both switches **up = STANDARD**. Down selects CUSTOM. STANDARD retains the modified Muff-family sound without a tone stack; CUSTOM retains the approved scramble fuzz intensity. HSB remains at 2x oversampling. All three use direct host bypass to skip DSP while retaining their normalled routing.

Previous supplied versions are preserved in their own version folders. `CANONICAL.json` identifies the current versions without duplicating source files.

- [Release notes and compatibility](CHANGELOG.md)
- [Code conventions](STANDARDS.md)
- [Validation and repeatable checks](VALIDATION.md)

These are source archives. SDK compilation and automated checks pass; this cleanup has not yet been auditioned or opened in Designer. The user tested the supplied baselines in the host.
