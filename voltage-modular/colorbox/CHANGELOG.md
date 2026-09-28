# Release notes

## 2026-09-28 — RGB 4.0.3, CMYK 1.0.3, HSB 1.0.2

- Standardize editable Java region formatting to four spaces, same-line braces, and consistent expression layout. Preserve Designer-generated code and panel definitions.
- Align RGB helper names with CMYK: `resetDsp`, `CONTROL_SMOOTH`, input upsampler/output downsampler names, and `Dc` capitalization in private identifiers.
- Align manufacturer and copyright metadata to insect laboratories; add current version headers.
- Add RGB/CMYK knob, audio jack, CV jack (CMYK), and DC switch tooltips. Values reflect current controls. Jack text explains the input normals; CMYK CV text identifies its unpatched +5 V source.
- Correct both HSB switches and their tooltips: up (value 1) selects STANDARD; down (value 0) selects CUSTOM. The supplied up-position defaults remain unchanged.
- Preserve all DSP arithmetic, coefficients, gain ranges, control smoothing, 2x processing, direct bypass, artwork, panel geometry, control IDs, and saved test settings.

### HSB saved-patch compatibility

HSB 1.0.1 interpreted switch value 1 as CUSTOM and 0 as STANDARD. Version 1.0.2 intentionally reverses that mapping. When loading an older patch, flip each HSB topology switch if you need to restore the circuit that patch previously selected. New instances start up in STANDARD as requested. RGB and CMYK control meanings are unchanged.

### Reference snapshots

RGB 4.0.2, CMYK 1.0.2, and HSB 1.0.1 are the exact supplied files, including the user's latest panel updates. They are retained for provenance and regression comparisons; no original was overwritten.
