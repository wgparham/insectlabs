# Wardenclyffe Station — Collection Guide

**Voltage Modular · Insect Laboratories · 9 October 2026**

Wardenclyffe Station is a mono-first electronic-music laboratory built from imagined postwar test equipment. The twenty-two canonized instruments span tone and noise sources, reference standards, signal conditioning, switching, modulation, slew shaping, selectable filtering, and passive-voiced frequency correction. Individual manuals describe each front panel and its exact release behavior.

## Module directory

| Product | Version | Manual |
| --- | ---: | --- |
| 1919/ln Tone Burst Generator | 2.0.1 | [1919/ln Tone Burst Generator](1919-ln/versions/2.0.1/USER-MANUAL.md) |
| 1947B SIN/RND Generator / Filter | 2.0.0 | [1947B SIN/RND Generator / Filter](1947b/versions/2.0.0/USER-MANUAL.md) |
| 1958 Dual Waveform Generator | 2.0.0 | [1958 Dual Waveform Generator](1958/versions/2.0.0/USER-MANUAL.md) |
| 1998/4 Dynamic Modulator | 2.0.0 | [1998/4 Dynamic Modulator](1998-4/versions/2.0.0/USER-MANUAL.md) |
| 1A-30 Deep Tone Generator / Modulator | 2.0.0 | [1A-30 Deep Tone Generator / Modulator](1a-30/versions/2.0.0/USER-MANUAL.md) |
| 6121198a Fader / Distributer A — Linear | 2.0.0 | [6121198a Fader / Distributer A — Linear](6121198a/versions/2.0.0/USER-MANUAL.md) |
| 6121198b Fader / Distributer B — Equal Power | 2.0.0 | [6121198b Fader / Distributer B — Equal Power](6121198b/versions/2.0.0/USER-MANUAL.md) |
| AC/1D SQR/SIN Function Generator | 2.1.2 | [AC/1D SQR/SIN Function Generator](ac-1d/versions/2.1.2/USER-MANUAL.md) |
| c2-34 Balanced Modulator | 2.0.0 | [c2-34 Balanced Modulator](c2-34/versions/2.0.0/USER-MANUAL.md) |
| n01 Noise Generator | 2.0.0 | [n01 Noise Generator](n01/versions/2.0.0/USER-MANUAL.md) |
| sh.7437 Dual Slew Processor | 2.0.0 | [sh.7437 Dual Slew Processor](sh-7437/versions/2.0.0/USER-MANUAL.md) |
| sn-16u Universal Test Bench | 2.0.0 | [sn-16u Universal Test Bench](sn-16u/versions/2.0.0/USER-MANUAL.md) |
| SW1 Relay Switch | 2.0.0 | [SW1 Relay Switch](sw1/versions/2.0.0/USER-MANUAL.md) |
| SW2 Switch / Distributer | 2.0.0 | [SW2 Switch / Distributer](sw2/versions/2.0.0/USER-MANUAL.md) |
| Type 9414 Frequency Analyzer | 2.0.0 | [Type 9414 Frequency Analyzer](type-9414/versions/2.0.0/USER-MANUAL.md) |
| Type 23 Signal Processor | 2.0.0 | [Type 23 Signal Processor](type-23/versions/2.0.0/USER-MANUAL.md) |
| LM-21 Mk III Matrix Mixer | 1.0.1 | [LM-21 manual](lm-21/versions/1.0.1/USER-MANUAL.md) |
| RM1010 Mixing Amplifier | 1.0.0 | [RM1010 manual](rm1010/versions/1.0.0/USER-MANUAL.md) |
| Model 62 Wire Player Recorder | 1.0.0 | [Model 62 manual](model-62/versions/1.0.0/USER-MANUAL.md) |
| XL-35h High-Pass Filters | 1.0.1 | [XL-35h manual](xl-35h/versions/1.0.1/USER-MANUAL.md) |
| XL-35c Low-Pass Filters | 1.0.1 | [XL-35c manual](xl-35c/versions/1.0.1/USER-MANUAL.md) |
| 21-24eq Frequency Corrector | 1.0.1 | [21-24eq manual](baxendall/versions/1.0.1/USER-MANUAL.md) |


## Shared operating conventions

- The modules are mono-first. Any two signal paths exist because the instrument has two explicit stages or duties; treat them as independent unless the manual shows a shared control or sum.
- Ordinary unpatched inputs contribute 0 V unless a manual documents another normalization.
- Direct host BYPASS is an abrupt patch-through or silence path. It skips the module’s filters, character and smoothing and freezes its DSP histories. A physical power switch may have its own controlled warm-up or fade.
- +5 V is the collection’s working modulation reference unless a connector’s manual says otherwise. Do not assume that audio ceilings, fixed laboratory voltages, pitch standards and gate thresholds all use the same scale.
- Courtesy outputs follow their own manual. They can remain active when the main function is stopped; host BYPASS silences them.
- Module character varies by instrument. Reference oscillators and measurement paths are clean; drive and relay voicing appear only where the individual design calls for them.

For exact ranges, defaults, power behavior and connection examples, use the linked module manual for the installed version. The final mono summing amplifier concepts are represented by canonical LM-21 and RM1010. Model 62 extends the collection with a wire recorder; 21-24eq adds three progressively darker passive-voiced EQ profiles. SN-46 remains the final planned Wardenclyffe module in development.
