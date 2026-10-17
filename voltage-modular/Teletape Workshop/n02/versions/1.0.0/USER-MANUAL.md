# N02 Dual Noise Generator — 1.0.0

N02 offers two independent noise voices and an equal-power blend. DIGITAL is a clocked repeating binary sequence. ANALOG is a broadband source with a smoother amplitude distribution and an EMS-inspired tone network. Each dedicated OUT remains available while BLEND combines the two after their LEVEL controls.

| Control | Range and operation |
| --- | --- |
| Digital SPEED | 20 Hz fully CCW to 48 kHz fully CW, exponential. Center is approximately 980 Hz. The tooltip reports clock Hz and the active sequence length. Type values in Hz. |
| COLOUR, both voices | D/dark fully CCW, neutral at center, B/bright fully CW. Tooltip and typed values use -100% to +100%. |
| LEVEL, both voices | 0–100%; defaults to silence. Controls the dedicated output and that source's contribution to BLEND. |
| MIX | D/digital fully CCW, equal-power blend at center, A/analog fully CW. Defaults fully CCW. Tooltip and typed entry use percent digital: 100% digital at D, 0% at A. |

SPEED automatically selects 7 bits in its low third, 15 bits in the middle third, and 31 bits in its high third. Nominal boundaries are approximately 268 Hz and 3.59 kHz. Small hysteresis regions keep a steady setting near a boundary from switching repeatedly; the tooltip identifies the active length.

The slow 7-bit voice repeats a 127-step pattern: at 20 Hz its cycle is 6.35 seconds. The 15-bit voice has 32,767 steps, and the 31-bit voice has 2,147,483,647 steps. At a fixed clock, repeat time is the step count divided by clock Hz. Changing SPEED changes both playback rate and, across the regions, pattern length. Colour filtering changes timbre without changing the feedback taps. Different speeds/colours can have different loudness; LEVEL provides manual adjustment.

Continuous manual controls smooth over approximately 5 ms. Sequence changes also transition over 5 ms; all three sequences continue running, without restarting on a region change. There is no sample/hold input, external clock, CV input, limiter or automatic loudness compensation.

Host bypass silences all outputs and freezes histories. Returning adopts current controls and preserves the streams. Preset/variation recall changes native controls without rewinding the noise. Exact random/filter histories are not saved. The analog and digital paths have slightly different filtering delays; the sources are independent rather than phase-matched.

Inspired by EMS source/amplifier/colour workflows and early digital feedback-register methods. This is a musical model, not a measured circuit reconstruction or a calibrated noise test instrument. Canonical and user-approved; Cherry Audio publication awaits the complete Teletape Workshop release.
