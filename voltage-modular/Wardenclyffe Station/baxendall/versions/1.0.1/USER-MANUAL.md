# 21-24eq Frequency Corrector — User Manual

**Version 1.0.1 · Wardenclyffe Station · Insect Laboratories**

21-24eq is a mono, passive-voiced bass and treble corrector. Its broad controls and three frequency profiles are intended for shaping program audio and synthesizer patches. The design keeps passive-style insertion loss and a non-flat midpoint response; it does not add makeup gain.

## Controls and connections

| Control / jack | Function |
| --- | --- |
| **TREBLE** | Counterclockwise cuts high frequencies; clockwise boosts them. Center retains the passive-voiced contour and loss. |
| **BASS** | Counterclockwise cuts low frequencies; clockwise boosts them. Center retains the passive-voiced contour and loss. |
| **FREQUENCY PROFILE** | Three positions select Reference, Dark, and Extra Dark, progressively lowering both shelf corners. The switch tooltip shows the selected profile and its nominal corner frequencies. |
| **INPUT** | Mono audio input. An unpatched input contributes silence. |
| **OUTPUT** | Mono tone-corrected signal. The passive-voiced model has substantial insertion loss by design. |

| Profile | Bass shelf | Treble shelf |
| --- | ---: | ---: |
| Reference | 70 Hz | 7 kHz |
| Dark | 35 Hz | 3.5 kHz |
| Extra Dark | 17.5 Hz | 1.75 kHz |

## Using the module

Patch a mono signal to INPUT and take OUTPUT to the next stage. Start with both tone knobs centered, then turn Bass or Treble clockwise for boost and counterclockwise for cut. Change FREQUENCY PROFILE to move the broad tone controls lower in the spectrum. Because the center retains the modeled passive contour and insertion loss, it is not a flat or unity-gain setting.

The modeled input stage begins to soften signals driven above approximately 6 V. A high output safety stage begins limiting near 20 V. These stages are symmetrical and add character when the input is driven hard; they are not calibrated limiters.

## Bypass and saved patches

Host bypass passes the input directly to the output and freezes the tone-processing history. When processing resumes, the module clears its filter history and immediately adopts the current knobs and frequency profile. Preset and variation loads also request a clean DSP start. The host saves the knob and switch positions as ordinary module controls.

## Character and accuracy

21-24eq is a DSP approximation of a passive Baxandall-style tone response, not a component-exact circuit model. Its fixed insertion loss, broad shelves, low-frequency rolloff, and high-threshold overload behavior form the intended voice. For implementation and validation details, see [Developer Notes](DEVELOPER-NOTES.md) and [Release Review](REVIEW.md).
