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
- [Eight-octave filter-bank interior photograph](EMS-8OctaveFilterBank-Inside.webp) — EMS hardware construction reference.

The reference set offers examples of EMS product documentation, signal routing and hardware construction. It does not establish the RVG's component-level circuit or prove that any other EMS schematic was used in its design.

## DSP design sketch

- [Software Slew Implementation Block](%23%20Software%20Slew%20Implementation%20Block.py) — short Python-like step-limiter pseudocode. It is Teletape design research, not production code or a tested primitive. Reusable implementations should be extracted only when a module needs them and its units and behavior are specified.

## Integrity

- [SHA-256 manifest](SHA256.json) records retained binary and code reference bytes; it excludes this README and the manifest itself.
- Keep shared sources here so later Teletape modules can link to them without copying them into module folders.
