# Teletape Workshop shared references

References useful across multiple Teletape Workshop instruments belong here. Keep module-specific references with their module and link shared material rather than duplicating it.

## EMS manuals and schematics

- [CXL plate reverb research and sources](../CXL/RESEARCH.md) — EMT 140 documentation, the Dattorro plate-network paper and CXL implementation limits. Source PDFs are archived under CXL so module-specific research stays with its series module.

- [Synthi 100 service index and excerpts](ems_synthi100_sm.pdf) — 12-page service document with a drawing index and circuit excerpts. Earlier identified here as the educational handbook in error; it is not the [Vasulka educational handbook](https://www.vasulka.org/archive/RightsIntrvwInstitMediaPolicies/IntrvwInstitKaldron/Amherst,MassEMS/SynthiEducHandbook.pdf).
- [Synthi 100 schematic](ems_synthi_100_sch.pdf) — 63-page service schematic PDF.
- [Synthi 100 VCA and amplifier design reference](Synthi-100-VCA-Amplifier-Design-Reference.pdf) — nine-page research packet with selected brochure, service-manual index and schematic pages for the quad log modulator and related amplifiers.
- [Synthi 100 Computer Synthi document](EMS%2520%2520Synthi%2520100%2520Computer%2520Synthi.pdf) — four-page PDF retained under its supplied filename.
- [Synthi AKS service manual](emsa_synthi-aks_sm.pdf) — 88-page reference PDF.
- [OSC oscillator research](../OSC/RESEARCH.md) — locations of the 100/AKS oscillator drawings, calibration evidence, thermal mechanisms and proposed hybrid DSP consequences.
- [Synthi 256 sequencer service manual](ems_synthi_sequencer_256_sm.pdf) — 25-page reference PDF.
- [Vocoder 2000 service manual](ems_vocoder-2000_sm.pdf) — 22-page reference PDF.
- [Three-channel slew schematic](3xSlew_Schematics.pdf) — one-page schematic reference for a separate slew design.
- [Eight-octave filter-bank schematics](EMS-8-Octave-Filter-Bank-Schematics.pdf) — additional EMS circuit reference; useful for studying recurring implementation patterns, but not a TSL schematic.
- [Eight-octave filter-bank interior photograph](EMS-8OctaveFilterBank-Inside.webp) — EMS hardware construction reference.
- [EMS rack-gear brokerage-sale cover image](EMS-Rack-Gear-Brokerage-Sale-110521-Cover-2.webp) — visual/context reference; not circuit documentation.

## Berna software manuals: RAI/WDR research guides

- [Berna 1.0 User Manual (2009)](Berna%201.0%20User%20Manual%20.pdf) — creator-authored reconstruction guide. Its preface says the software is a study/creative tool, not vintage-sound emulation; it cites sources for Lietti's oscillator ranges and several RAI devices.
- [Berna 2 manual (2014)](berna%202%20manual.pdf) — later guide with device workflows and a bibliography.
- [Berna 3 manual (v3.0, 2021)](Berna3Manual.pdf) — later reconstruction with a suggested-reading list, RAI/WDR discography, and per-device historical inspirations.

These manuals describe software reconstructions, not original RAI/WDR service documents. Use them to locate device names, operating ideas, and bibliographic leads; corroborate hardware and circuit claims with the cited historical sources before treating them as primary evidence.

- [Synthi 100 oscillator and sequencer panels](synthi-100-oscillator-and-sequencer-panels.jpg) — supplied photograph showing the oscillator bank, noise/random voltage controls, sequencer outputs and associated controls. Its original source and publication details were not supplied; use as visual context, not as circuit documentation.

## Synthi 100 filter evidence

- [Synthi 100 filter research packet](Synthi-100-Filters/INITIAL-RESEARCH.md) — separates the documented 34/01 HPF/three-tap core from the distinct, incompletely documented 34/00 LPF board. It includes the user-supplied Digitana Mk1 photos/demos and front-panel photograph, hashes, unresolved revision history and limits on what component photos establish.

## TSL research notes

- [Notes Towards a Triple Slew Limiter](NotesTowardsAtsl) — user-assembled forum and web-search fragments. Treat component, topology and performance statements as unverified leads unless corroborated by primary documentation.

The reference set offers examples of EMS product documentation, signal routing and hardware construction. It does not establish the RVG's component-level circuit or prove that any other EMS schematic was used in its design.

## DSP design sketch

- [Software Slew Implementation Block](%23%20Software%20Slew%20Implementation%20Block.py) — short Python-like step-limiter pseudocode. It is Teletape design research, not production code or a tested primitive. Reusable implementations should be extracted only when a module needs them and its units and behavior are specified.

## Integrity

- [SHA-256 manifest](SHA256.json) records retained binary and code reference bytes; it excludes this README and the manifest itself.
- Keep shared sources here so later Teletape modules can link to them without copying them into module folders.

