# Digitana Mk1 DHPF photo and demo references

Received from the user 2026-10-14. Photos are attributed by the user to Digitana's recreation; retain as research reference, not product artwork. Original supplied JPEG bytes are preserved.

- [Top view](Digitana-Mk1-DHPF-PCB-top.jpg)
- [Angled view](Digitana-Mk1-DHPF-PCB-angle.jpg)
- [Manufacturer description](https://www.synthi.co.uk/synthi-100-mk1-dual-high-pass-filter-rack-module/)
- [Demo Y54gpvIFWYY](https://www.youtube.com/watch?v=Y54gpvIFWYY)
- [Demo KJx7-tOSUGc](https://www.youtube.com/watch?v=KJx7-tOSUGc)

## Direct visual observations

Two repeated channel layouts are visible. Each has a dense array of glass-bodied axial diodes, three visible DIP IC packages, three trimmers, and a group of four rectangular capacitors. Similarity of layout supports treating the channels as the same nominal design. Chip markings and complete underside connectivity are not established from these views. Do not infer pole count from capacitor count or identify every IC as a 741 by its package. The photos support the manufacturer's diode-string description but do not themselves reveal how those strings connect to the signal and control paths.

## Working implications, not measured behavior

Investigate control-dependent diode impedance and signal-level-dependent behavior, response feedback and its limiting, and interactions when filters are cascaded. Determine the network before choosing a ladder or state-variable implementation. Do not infer exact slope, CV sensitivity, saturation law or self-oscillation amplitude from these photographs. Trimmers establish calibration provisions, not their individual functions.

Demo review should look for response buildup, onset and persistence of oscillation, input-level interaction, frequency movement under CV, and series versus parallel behavior. The web fetch could not access either YouTube video on this pass; no listening observations or quantitative measurements are claimed.

## Revision-history provenance

The user clarified that the four-HPF/four-LPF to four-SVF/four-Moog-style-ladder replacement story, and the customer-dislike explanation, came from forums/friends and are common lore rather than a verified source. Retain as a research lead. Do not label the archived Hilton OTA drawing as a confirmed factory successor on this evidence alone. Our target discussion remains the early dedicated HPF / Mk1 recreation workflow; the OTA drawing is comparative evidence.

## Local demo files now available

The user supplied two MP4 files, preserved with checksums in [videos](videos/README.md). Both decode successfully. Timestamped visual sampling is complete; audio has not been auditioned. The earlier web-access limitation no longer prevents local frame inspection.
