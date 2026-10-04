# 1A-30 Deep Tone Generator / Modulator User Manual

**Insect Laboratories · Voltage Modular · canonical release 1.0.0**

Manual draft prepared 4 October 2026.

![Deep Tone panel](versions/1.0.0/deeptone_hero.png)

Deep Tone combines a sine oscillator, an independently started Beat oscillator, an external audio input, shaped amplitude movement, and an always-on Courtesy waveform. TONE, BEAT, EXTERNAL and SWELL are function buttons; they start or add functions but do not power the machine down. Main AMPLITUDE initializes at silence.

This guide describes [release 1.0.0](versions/1.0.0/). Open `deeptone.vmod` in Voltage Module Designer to build the module.

## Frequency and waveform controls

| Control | Range / default | Function |
| --- | --- | --- |
| WHOLE | 0–10, stepped; default 2 | Whole-number portion of the Tone frequency setting. |
| FRACTION | 0–1, continuous; default 0.616255653 | Fractional portion. It is continuous; the display is only a rounded/truncated indication. |
| Multiplier | ×1, ×10 or ×100; default ×100 | Multiplies WHOLE + FRACTION. The nominal range is 0–1,100 Hz. |
| Frequency counters | Two displays | Show the whole value and first two decimal digits before multiplication. At the C4 default they show 2 and 61. The tooltip shows exact base and multiplied frequencies. |
| OFFSET | −65 to +65 Hz | Sets the difference frequency for Beat, and the movement rate for SWELL and COURTESY. The knob uses a slow center taper for fine low-rate adjustments. |
| SHAPE | Rising ramp → triangle → falling saw | Sets the Beat/Swell/Courtesy waveform shape. The left edge rises, center is triangle, and right edge falls. |
| BEAT DEPTH | 0–100% | Controls Beat mix level or FM depth, depending on the mode switch. |
| AMPLITUDE | 0–15 nominal peak volts; starts at 0 | Sets the main output level. Compression begins above approximately 3 V and approaches soft rails. |

WHOLE and FRACTION set the summed pre-multiplier frequency. At startup the setting is C4: 2.616255653 × 100 = 261.6255653 Hz. A zero Tone frequency stops and holds the Tone phase rather than inserting a hidden minimum frequency.

OFFSET moves Beat relative to Tone: Beat runs at Tone + OFFSET, limited to zero if the requested rate would be negative. SWELL and COURTESY use the absolute OFFSET rate, so their movement rate follows the magnitude of the setting in either direction. At zero OFFSET, these phase-based outputs hold their present voltage.

## Function buttons and mode switch

| Control | Behavior |
| --- | --- |
| TONE | Starts/stops the main sine oscillator. Stopping fades it out and holds its phase for the next start. |
| BEAT | Starts/stops the second oscillator. |
| EXTERNAL | Adds the EXTERNAL input to the main mix when engaged. Its input is normalized from a nominal 5 V. |
| SWELL | Applies non-inverting amplitude modulation at the absolute OFFSET rate, using SHAPE. |
| Beat Mix / FM | Down selects MIX; up selects FM. In MIX, BEAT DEPTH adds the shaped Beat waveform. In FM, it applies up to 100% linear FM to Tone; Beat is not also mixed. Tone and Beat must be started to hear this FM path. |
| Courtesy polarity | Up is normal polarity; down is inverted. |

The buttons latch independently, initialize disengaged and retain their states in presets/variations. SWELL does not require BEAT to be engaged. AMP MOD DEPTH spans 0–200%: 100% can reach silence at the envelope minimum; above 100%, the output remains silent for a greater portion of each cycle. This is non-inverting modulation, not ring modulation.

## COURTESY output

COURTESY is the SHAPE waveform at a fixed ±3 V peak before polarity inversion. It follows the absolute OFFSET rate and is independent of TONE, BEAT, EXTERNAL, SWELL, the FM/MIX selector, both depth controls, and main AMPLITUDE. It remains active while the main output is silent because the buttons are stopped. At zero rate it holds a voltage. Host BYPASS silences COURTESY.

COURTESY is a patchable source, not a copy of the main audio output. The user-approved bypass behavior passes the EXTERNAL input sample-for-sample at MAIN OUT only when EXTERNAL is engaged; otherwise MAIN OUT is silent. Bypass stops control, modulation, filter, and gain processing.

## Example patches

**Blend tone and beat.** Start TONE and BEAT, select MIX, and raise BEAT DEPTH. Change OFFSET to set the difference and SHAPE to change the Beat waveform.

**Use a changing pitch.** Start TONE and BEAT, select FM, and raise BEAT DEPTH. In this mode the Beat oscillator modulates Tone instead of being separately mixed.

**Create deep rhythmic dips.** Engage SWELL and set DEPTH below, at, or above 100%. Shape and rate follow SHAPE and the absolute OFFSET setting.

**Patch the Courtesy source.** Use COURTESY for a steady-level shaped waveform independent of the main mix, even when the main output is silent.

For exact numeric behavior, validation scope and release approval, see the [Deep Tone 1.0.0 release record](versions/1.0.0/README.md).
