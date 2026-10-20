# CED design research and evidence

CED is a personal workflow instrument combining Dimension-style ensemble programs and a CE-1-inspired chorus/vibrato voice. It uses no Roland trademarks or trade dress as a claim of affiliation and is not an exact hardware reconstruction.

## Source-backed observations

- The Roland SDD-320 service-note material records OFF and four numbered programs and gives the nominal delay sweep endpoints used by the DSP tests: program 1, 8–12 ms; 2, 5–10 ms; 3, 6–9 ms; 4, 6–9 ms.
- The source notes/readings identify a NE570 compressor, MN3007 delay line, MN3101 clock driver, and NE570 expander in the Dimension signal chain. The CED compander/noise controls are optional software interpretations of that chain.
- Schematic annotations were read as 4 s LFO periods for programs 1–2 and 2 s for 3–4, with an inverted modulation direction between stereo channels. These are marked as schematic readings in source comments, not manufacturing specifications.
- The CE-1 schematic is used as a circuit workflow reference. CED's CE-style range, bandwidth and saturation amount are modeled settings rather than verified service specifications.

## Deliberate software choices

The module exposes combinations of multiple Dimension buttons although combined operation is not documented in the service material. SUM, AVG and PAR are distinct performance laws; Rate/Depth CV, exclusive selection, added BBD noise and mono-sum input are workflow extensions. The CE-style path uses a softened preamp model and an adjustable delay rather than a transistor/BBD component simulation. Consult the versioned developer notes for exact mappings and the manual for operation.

The original service-note scan and schematic references remain in the shared Teletape [`references`](../../references/README.md) collection where available. CED-specific research claims must retain their `[DOC]`, `[READ]`, `[MODEL]` or `[GUESS]` distinction when updated.

Preserved project history: [design brief](Design-Brief-History.md) and [early control specification](Designer-Control-Spec-History.md). These are archival background; the 1.0.0 release documents are authoritative for the shipped behavior.
