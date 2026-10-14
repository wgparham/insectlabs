# Teletape Workshop shared references

References useful across multiple Teletape Workshop instruments belong here. Keep module-specific references with their module and link shared material rather than duplicating it.

## EMS manuals and schematics

- [Synthi 100 educational handbook](ems_synthi100_sm.pdf) — user's reference drop, linked to the [Vasulka archive source](https://www.vasulka.org/archive/RightsIntrvwInstitMediaPolicies/IntrvwInstitKaldron/Amherst,MassEMS/SynthiEducHandbook.pdf). A 12-page scanned PDF; its encryption permits printing but restricts copying and modification. It is general EMS/Synthi context, not an RVG schematic.
- [Synthi 100 schematic](ems_synthi_100_sch.pdf) — 63-page service schematic PDF.
- [Synthi 100 Computer Synthi document](EMS%2520%2520Synthi%2520100%2520Computer%2520Synthi.pdf) — four-page PDF retained under its supplied filename.
- [Synthi AKS service manual](emsa_synthi-aks_sm.pdf) — 88-page reference PDF.
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

## TSL research notes

- [Notes Towards a Triple Slew Limiter](NotesTowardsAtsl) — user-assembled forum and web-search fragments. Treat component, topology and performance statements as unverified leads unless corroborated by primary documentation.

The reference set offers examples of EMS product documentation, signal routing and hardware construction. It does not establish the RVG's component-level circuit or prove that any other EMS schematic was used in its design.

## DSP design sketch

- [Software Slew Implementation Block](%23%20Software%20Slew%20Implementation%20Block.py) — short Python-like step-limiter pseudocode. It is Teletape design research, not production code or a tested primitive. Reusable implementations should be extracted only when a module needs them and its units and behavior are specified.

## Integrity

- [SHA-256 manifest](SHA256.json) records retained binary and code reference bytes; it excludes this README and the manifest itself.
- Keep shared sources here so later Teletape modules can link to them without copying them into module folders.
