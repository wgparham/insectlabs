# FPA design research

This is the retained design brief and schematic-reading record for FPA. The module borrows workflow and tonal ideas from several pedal and studio preamp circuits; it is not an electrical clone of any one unit. Tags distinguish documented details from interpretation and DSP choices.

## Evidence tags

- **DOC** — stated in a manual or schematic.
- **READ** — interpretation of a schematic; component values or behavior may need hardware confirmation.
- **MODEL** — chosen DSP approximation, not a hardware claim.
- **GUESS** — starting value selected for listening and tuning.
- **OPEN** — unresolved research question.

## Voicing sources and DSP choices

### C — CE-1-inspired boost

The nonlinear stage reuses the tested Chorus Ensemble Device soft clipper with an exponential drive range of approximately 0.7× to 10× (**MODEL**). Tone is a ±6 dB tilt around 1 kHz with 0 as the neutral center (**MODEL**).

### Re — RE-201/RE-301-inspired preamp

The RE-201 service manual documents the mic amplifier and a Baxandall tone network. The RE-301 service manual documents switched input gain steps. The DSP uses an asymmetric biased soft clip, drive of approximately 1.5× to 25×, and opposing low/high shelves around 100 Hz and 3 kHz with ±10 dB range (**READ/GUESS**). The shelf corners and nonlinear transfer are modeling choices rather than measurements of an RE preamp.

The two Roland service manuals are retained with the [SM1084 delay reference archive](../../sm1084/development/delay%20reference/). Byte-identical extra copies supplied during FPA work were removed after hash comparison.

### D — BD-2-inspired overdrive

The supplied BD-2 schematic and board images show JFET stages and an op-amp diode feedback stage. The model uses a soft-knee saturator with approximately 1× to 40× drive and a ±10 dB tone tilt around 1.2 kHz (**READ/GUESS**). The discrete JFET stage is not individually modeled.

### O — SD-1-inspired overdrive

The supplied SD-1 schematic references show an op-amp gain stage, frequency shaping, asymmetric diode clipping and a passive low-pass tone control. The model uses a roughly 720 Hz pre-emphasis stage, asymmetric soft clipping and a 1.5–8 kHz low-pass cutoff range (**READ/GUESS**). The tone knob spans −1 to +1; its midpoint is the geometric cutoff midpoint, not a flat response.

## Shared implementation decisions

- Four independent mono channels. Input and output voltage conversion uses 5 V per internal DSP unit; input sanitization clamps to ±10 V and channel output is bounded to ±10 V.
- GAIN spans 0–1 and drives each voicing's exponential stage. A per-mode calibration table targets a 1 kHz, 5 V-peak reference sine from 0 dB at minimum to +6 dB RMS at maximum. Other input levels and tone settings will not match that calibration exactly.
- TONE spans −1 to +1, with 0 as the neutral center for C, Re and D. The O voicing maps that same full sweep logarithmically across its low-pass cutoff range.
- The channel phase toggle applies polarity after nonlinear stages, with a 4 ms glide through zero.
- A 2×, 23-tap windowed-sinc oversampling path precedes nonlinear processing. The measured algorithmic delay is about 11 samples (0.23 ms at 48 kHz); it is not latency-compensated on bypass.
- The mix output is half the sum of the four processed channels. An unpatched channel contributes zero. Host bypass routes each channel input directly to its matching output and silences the mix; DSP histories reset once on resume.

## Open research

- **OPEN** — trace the RE-201 mic amplifier feedback return node and compare its measured response with the DSP approximation.
- **OPEN** — finish tracing SD-1 component values from the supplied scan; its filter corners and clipper parameters remain audition-led estimates.
- **OPEN** — confirm BD-2 values and decide whether the omitted JFET stage has an audible effect worth modeling.
- The sources and images are useful references, not proof that the module reproduces the hardware transfer curves.

## Reference catalog

- [`tumblr_ny30ly0BZj1qbg257o1_1280.jpg`](tumblr_ny30ly0BZj1qbg257o1_1280.jpg) — user-supplied SD-1 service-note page with board layout, waveforms and schematic; publication details were not supplied.
- [`boss-sd1-super-overdrive.webp`](boss-sd1-super-overdrive.webp) — SD-1 schematic image.
- [`sd1-components.webp`](sd1-components.webp) — SD-1 component reference image.
- [`boss-bd2-blues-driver-schematic.webp`](boss-bd2-blues-driver-schematic.webp) — BD-2 schematic image.
- [`bd2-board.png`](bd2-board.png) and [`bd2-pcb-component-side.jpg`](bd2-pcb-component-side.jpg) — BD-2 board photographs.
- [`OverdriveShootout.webp`](OverdriveShootout.webp) — Electric Druid’s “Classic Overdrive Shootout” comparison sheet, 15 February 2023. The source marks it CC BY-NC-SA 4.0; retain attribution and license terms if redistributed or adapted. It compares OD-1, TS-808 and SD-1 circuits and is not an SD-1-only schematic.
