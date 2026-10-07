# r195L: SIN/RND generator 1.1.0

Canonical first release, approved 2026-09-30 after Designer build, control, save/reload and listening tests.

`r195L: SIN/RND generator` is a mono laboratory source inspired by the Sine/Random Generator in Berna 3, page 14. The final instrument treats that reference as a starting point rather than a circuit reconstruction: it provides a stable sine, white and filtered noise, moderated random FM, and a patchable filtered-audio path.

FREQUENCY is logarithmic from 50 Hz to 18 kHz and initializes at C4 (261.6256 Hz); FINE spans ±200 cents. The five-position MODE control is SIN, RND, RD2 filtered noise, MOD white-noise FM and FLT filtered-noise FM. RND and RD2 are direct noise outputs. MOD IN supplies linear FM in SIN and MOD, while MOD LEVEL spans −200% to +200%.

With MOD IN patched in FLT, the oscillator is silenced and the jack becomes a filter input. MOD LEVEL is its bipolar attenuverter/amplifier; RND MOD blends white noise before filtering; AMP and RANGE apply the shared 0.01 V / 0.1 V / 1 V / 10 V output scaling. This path blends on connection changes to avoid hard output discontinuities. The filter is a deliberately voiced two-stage network, not a claimed reconstruction of the Berna circuit.

The source is linear through 5 V and then develops the approved restrained compression, with lightweight midpoint conditioning at higher levels. The power switch fades over 8 ms and stops state afterward. Host bypass is a silent source bypass that freezes state. The meter is averaged; its power lamp has a fast compression response. No host CPU benchmark has been recorded.

## Included

- `sinrnd.vmod` — canonical Designer project, marked `v1.1.0` in Notes.
- `sinrnd.java` — matching exported source.
- `sinrnd_hero.png` — approved panel image.
- `SHA256.json` — SHA-256 manifest for the source pair and panel image.
- `tests/validate.py` — repeatable source-pair and DSP callback validation.
- `REVIEW.md` — final code review, intentional exceptions and release evidence.
