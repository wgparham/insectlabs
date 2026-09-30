# AC/1D SIN-SQR Function Generator 1.0.0

Canonical first release, approved 2026-09-30 after the final Designer build, load, save/reload, control and listening check.

`function` is the Series One Pulse/Sine Generator: a deliberately worn, hand-built laboratory source inspired by the Heathkit AG-10 family. It has independent sine and square oscillator boards in one chassis, sharing manual tuning but not phase. The two outputs use separate amplitude controls and 0.1 V / 1 V / 10 V ranges.

The shared display reports calibrated output frequency after its X1 / X10 / X100 / X1K / X10K multiplier. X10 initializes at C4. The range selector covers 0.1–100 Hz, 1–1000 Hz, 10–10000 Hz, 100–23760 Hz and 1000–23760 Hz, with the high ranges spread over the complete frequency dial. The 23.76 kHz ceiling keeps the generated signal below Nyquist at Voltage Modular's 48 kHz rate.

Both boards retain approved slow independent drift. Square width spans 5–95%; the sine has mild harmonic imperfection; hot ranges develop the approved restrained output compression. POWER uses an 8 ms fade, while host bypass is a silent source bypass that freezes state.

## Included

- `function.vmod` — canonical Designer project, marked `v1.0.0` in Notes.
- `function.java` — matching exported source.
- `function_hero.png` — approved panel image.
- `SHA256.json` — SHA-256 manifest for the source pair and panel image.
- `tests/` — repeatable source-pair and DSP callback validation.
- `REVIEW.md` — final code review, exceptions and release evidence.
