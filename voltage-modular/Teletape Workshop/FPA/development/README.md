# FPA development

This is an early, unpublished four-channel panel skeleton. It has four preamp lanes and their controls, but the audio callbacks contain no DSP yet. The Java class and Designer metadata use `fpa`.

The exported Java and VMOD-embedded source match and both compile against the installed Voltage SDK. No native Designer load, functional audio test, or user listening check has been performed. This is development work, not a canonized release.

## Reference

[`reference/`](reference/) contains two user-supplied Boss SD-1 overdrive service-reference images: a page scan with board layout, verification waveforms and circuit schematic, and a closer schematic image. Original publication/source details were not supplied; treat them as visual circuit references, not a verified component bill or license statement.

[`SHA256.json`](SHA256.json) records the Java pair, this note, and both reference images. It excludes itself.
