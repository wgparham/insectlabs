# FPA — Four Pre-Amps

FPA combines four independent mono preamp and overdrive channels with a summed output. Its four voicings draw on the character and workflows of several analog circuits; they are not exact hardware clones.

## Per-channel controls

- **GAIN**: sets the drive and calibrated output level for the selected voicing. The range is 0–100%; the factory panel starts at 0%.
- **TONE**: ranges from −100% to +100%, with 0% at the neutral center for C, Re and D. O is a low-pass voice: its center setting is the midpoint of the cutoff sweep rather than a flat response.
- **Voicing switch**: selects C, Re, D or O. On the shipped defaults, channels 1–4 start at C, Re, D and O respectively.
- **Phase toggle**: reverses polarity for that channel. The change glides through zero over a few milliseconds.

Each channel has its own audio input and output. The **MIX** output is one half of the sum of the four processed channel outputs; an unpatched input contributes zero. The channel outputs remain independent.

## Levels and bypass

The DSP uses 5 V as its normal full-scale reference and clamps each processed channel to ±10 V. Tone and gain are voiced controls, so the GAIN knob is not a simple linear volume control. The gain calibration targets a 1 kHz, 5 V-peak sine; other levels and tone settings can produce different measured loudness.

Host bypass sends each channel input directly to its matching output and silences the MIX output. Processing state is reset once when the host returns to active processing. There is no separate FPA power control.
