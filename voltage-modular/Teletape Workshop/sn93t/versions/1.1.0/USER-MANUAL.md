# SN93T — User Manual

## Signal and tuning

Patch a monophonic pitched signal into **IN**. **THRU** passes the input audio; **MUTE** silences THRU while the tuner continues to analyze the input. The display reports the detected note or tuning degree, measured frequency, deviation from the nearest tuning step and signal status. **HOLD** freezes the displayed reading and pitch outputs. When the input disappears, the last reading is retained briefly before the display reports no signal.

The **A4** knob sets the reference from 380 to 480 Hz. **KEY** chooses the root for the supported periodic temperaments. **TEMPERAMENT** selects 12-TET, Werckmeister III, quarter-comma meantone, 19-EDO, 24-EDO, 53-EDO, Bohlen–Pierce, 5-limit just, Pythagorean, or a loaded custom tuning. **RESPONSE** trades faster tracking for a steadier display.

## Outputs

- **TONE** provides the selected reference pitch. Set **TONE SOURCE** to FOLLOW to track the detected note or MANUAL to use the TONE NOTE and TONE OCTAVE controls. **TONE** enables or disables the output; **LEVEL** sets its level in dB, with 0 dB corresponding to 5 V peak.
- **V/O** outputs pitch CV. Its calibration is 1 V per octave; the chosen convention maps C4 to 0 V when A4 is 440 Hz.
- **CENTS** outputs the signed pitch deviation around the nearest tuning step, scaled to ±5 V for ±50 cents.
- **GATE** indicates a valid detected signal.
- **CAL** outputs the temporary calibration CV during a calibration pass.

TONE, CENTS, V/O and CAL outputs are expressed directly in volts: 0 dB TONE is 5 V peak, ±50 cents is ±5 V, and V/O/CAL use 1 jack unit per volt.

## Loading tunings

Press **LOAD** and choose an AnaMark `.tun` or Scala `.scl` file. A `.tun` may also be dropped onto the module. The loaded tuning is stored with the patch. A keyboard-like 12-note-per-octave `.tun` uses note names; non-octave and other non-12-note tables use numbered entries. For numbered tunings, TONE OCTAVE selects one of eight pages of 16 entries and TONE NOTE selects a note within that page.

## Calibrating a VCO

Patch **CAL** to the target VCO's 1 V/oct input and patch that oscillator's audio output back to **IN**. Press **CAL** to run the sweep. The module measures a sequence of CV steps, ignores readings outside its current detector range, and drops an isolated step when the fitted response is more than 30 cents away and at least four usable steps remain. At least three in-range measurements are required. The final report shows the fitted tracking error and residual. Calibration quality depends on a stable oscillator and a clean input signal.