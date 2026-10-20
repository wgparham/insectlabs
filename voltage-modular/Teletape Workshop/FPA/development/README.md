# FPA development

This is an early, unpublished four-channel panel skeleton. It has four preamp lanes and their controls, but the audio callbacks contain no DSP yet. The Java class and Designer metadata use `fpa`.

The exported Java and VMOD-embedded source match and both compile against the installed Voltage SDK. No native Designer load, functional audio test, or user listening check has been performed. This is development work, not a canonized release.

## References

- [`reference/tumblr_ny30ly0BZj1qbg257o1_1280.jpg`](reference/tumblr_ny30ly0BZj1qbg257o1_1280.jpg) — user-supplied Boss SD-1 service-note page scan with board layout, verification waveforms and schematic. Original publication details were not supplied; treat it as a visual reference, not a verified component bill.
- [`reference/boss-sd1-super-overdrive.webp`](reference/boss-sd1-super-overdrive.webp) — closer schematic image of the Boss SD-1 Super Overdrive.
- [`reference/OverdriveShootout.webp`](reference/OverdriveShootout.webp) — Electric Druid’s “Classic Overdrive Shootout” schematic sheet comparing the Boss OD-1, Ibanez TS-808 and Boss SD-1 (15 Feb 2023), marked CC BY-NC-SA 4.0. Preserve attribution and license terms if the sheet is redistributed or adapted; this is comparative reference material, not an SD-1-only schematic.

[`SHA256.json`](SHA256.json) records the Java pair, this note, and the reference images. It excludes itself.
