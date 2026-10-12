# sn46 — Standards user manual

Version 1.0.0 · Wardenclyffe Station · Voltage Modular

sn46 supplies reference voltages, clean sine tones, brief test impulses and pink noise. It is a source-only utility with no signal inputs, pitch CV input, panel power switch or output level controls. Its sources run whenever the module is active.

## Voltage section

| Control or jack | Function |
| --- | --- |
| V | Continuous coarse voltage, 0–12 V. |
| cV | Continuous fine voltage, 0–1 V added to V. Its tooltip uses volts. |
| + | Positive combined voltage, 0 to +13 V. |
| − | Inverted combined voltage, 0 to −13 V. |
| Display | Combined magnitude rounded to 0.01 V, with leading zeros and a fixed decimal point. It is not a meter input. |
| 0 V | Fixed zero-voltage reference. |
| 3.3 V | Fixed +3.3 V reference. |
| 5 V | Fixed +5 V reference. |
| 9 V | Fixed +9 V reference. |

Both adjustable outputs run simultaneously. The signal is continuous; the two-decimal display does not quantize the voltage. For example, V at 4.25 V and cV at 0.125 V produces +4.375 V and −4.375 V; the display reads 04.38. The fixed references are independent of these controls and the tuning button. Both voltage controls initialize at zero.

## Tone and test-source section

| Control or jack | Function |
| --- | --- |
| A | Sine at 440 Hz by default, or 432 Hz when the 432 button is lit. |
| F# | Sine three semitones below A: approximately 369.994 Hz at A440, or 363.267 Hz at A432. |
| 432 button | Each press toggles the A/F# tuning standard. Release does not toggle; holding does not repeat. The setting is saved with the patch and supports undo/redo. |
| 1 Hz | Fixed 1 Hz sine, unaffected by the tuning button. |
| 100 Hz | Fixed 100 Hz sine, unaffected by the tuning button. |
| Pulses | +5 V impulses one host sample wide, repeating every 4096 samples: 11.71875 Hz at the 48 kHz host rate. Same timing as SN-16u Slow Pulses. |
| PINK | Continuous pink-noise source. It has no calibrated output level or RMS adjustment. |

All sine outputs are ±5 V peak (10 V peak-to-peak). Switching A440/A432 smooths the tuning over approximately 5 ms; it does not reset the oscillators. The pulse output is a brief impulse train, not a square-wave clock; downstream devices must be able to detect a one-sample event.

## Patch examples

Use A or F# to tune an oscillator by ear, beat frequency, or an external frequency meter. Select 432 only when the tuning context calls for it. Use 1 Hz as a steady modulation reference or 100 Hz as an audio/check signal. Use the + and − outputs as paired offsets, or combine a fixed voltage with other signals through a mixer. PINK can excite filters or provide a broad test signal; use external attenuation where required. Pulses can reveal filter or delay response and can trigger devices that accept very short edges.

## Bypass, reset and patch recall

System BYPASS immediately zeros all twelve outputs and freezes oscillator, pulse and noise histories. It performs no control reads. The voltage display shows zero during bypass; its fixed decimal point remains illuminated. Resuming continues the source histories. The 432 selection can be changed while bypassed.

Reset restores A440; the host resets the voltage knobs to their defaults. The 432 setting is stored in custom patch and variation data; ordinary knob positions are saved by the host. Oscillator phase and the live noise sequence are not serialized. Reset and tuning changes do not rewind those running source histories. Invalid custom mode data is ignored.

The references are mathematical signals within the module's 48 kHz processing model. PINK is a colored noise source rather than a calibrated measurement instrument. No deliberate vintage saturation or drift is applied to the sine references or voltage outputs.
