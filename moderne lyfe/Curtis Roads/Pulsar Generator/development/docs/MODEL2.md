# Model 2

Model 2 is Pulsar Generator with the original program's quirks corrected. It runs in the same app, plugin and engine as Model 1.

- **Model selector:** in the header, choose **Model 1 (original)** or **Model 2 (corrected)**. New instances start as Model 1.
- **Individual fixes:** **FIXES...** switches each correction on or off. Any mix other than the two presets shows as "Model 2 (custom fixes)". Each fix is also a plugin parameter (`Fix: ...`), so it can be automated and is saved with the project.
- **A/B comparison:** with any fix on, **A: MODEL 1** plays the original behaviour and **B: CURRENT** the current fixes, live, with the same tables and controls. The `a` key flips between them. The A/B choice is a parameter too (`A/B: Hear Model 1`).
- **CLI:** `pulsargen-cli` takes `model=2`, then `+name` / `-name` to switch single fixes after the preset, for example `pulsargen-cli in.set out.wav model=2 +bandLimited`.

With every fix off the engine is Model 1, bit for bit. This is checked against the previous engine over 246 renders: all 41 original table sets (DemoTables, ExampleTables, El Rey 6), each with the set's own settings, with burst masking, backwards with Loop off, at 48 kHz, with a short train and Loop off, and at 96 kHz with a set crossfade.

[ALGORITHM.md](ALGORITHM.md) explains how the original works.

## Fixes in the Model 2 preset

### Bugs

| Fix | Model 1 | Model 2 |
| --- | --- | --- |
| `noChannelSwap` | The output's final `.reverse` exchanges left and right, so pan tables play mirrored: −1 comes out on the right | Left is left |
| `loopEndBothWays` | With Loop off, the end-of-tables check only looks for the phase wrapping downward, so a backwards run stops at the first check (1/30 s) | The end is detected in both directions. A backwards run starting at the beginning of the tables plays them from the end |
| `controlsAtStart` | The control tables reach the synth 3 ms after it starts, so the first pulses play with every control at zero: silent, with pulse mask 0 | The tables are read from the first sample |
| `burstFromFirstStep` | The burst counter starts at −2, so the pattern begins on its last step | The pattern begins on its first step |

### Signal quality

| Fix | Model 1 | Model 2 |
| --- | --- | --- |
| `silenceAfterPulsaret` | `Osc1` holds the pulsaret's last table value until the envelope cuts it off. If pulsaret × envelope doesn't end at exactly 0, each pulse leaves a DC step, which LeakDC turns into a low thump | The voice falls silent when the pulsaret ends |
| `exactFormant` | The pulsaret length is rounded to whole samples, so high formants are out of tune (7 kHz plays as 7.35 kHz at 44.1 kHz) and formant sweeps move in steps | The pulsaret lasts exactly 1/formant seconds |
| `smoothControls` | The control tables are read once per 64-sample block, so every pulse within a block gets the same values and fast sweeps step | Control values are interpolated per sample, including the fundamental that drives the pulse clock |
| `rateIndependentDC` | The DC blocker's 0.999 coefficient is fixed, so its cutoff rises with the sample rate: about 7 Hz at 44.1 kHz, 15 Hz at 96 kHz | The coefficient is scaled so the cutoff stays at the 44.1 kHz value |

## Optional, not in the preset

These change the character of the sound rather than fix errors.

| Option | Effect |
| --- | --- |
| `burstEveryPulse` | The original decides each pulse with `coin and: burst`. `and:` short-circuits, so the burst pattern only advances on pulses that won the mask coin, and with a mask below 1 the on/off pattern stretches and drifts. This advances the pattern on every pulse, so the burst ratio is a fixed rhythm that the mask thins out |
| `formantEnvelopeRule` | The source drops the cutoff envelope when the lowest formant is at or above the fundamental, so short pulsarets end at the next pulse instead of being faded. `lowestForm` never resets, so the rule never fires in the original. This restores it, per pulse. Best used with `silenceAfterPulsaret`, otherwise a held DC value is cut abruptly at the next pulse |
| `raisedFundCap` | The fundamental is capped at 3000, 2400 or 2000 Hz depending on the number of active trains, a limit set for 2001 CPUs. This raises the cap to 8 kHz (at most 45 % of the sample rate) and allows 1024 voices instead of 512 |
| `bandLimited` | The pulsaret is read with linear interpolation, so a pulsaret with fine detail played at a high formant aliases. This reads it from pre-filtered, half-length copies (half-band filter, down to 8 points), chosen so no more than one table point is skipped per sample |

## Measured differences

Measured with the engine on identical settings and seeds. In each test the fixes the test isn't about are set the same for both columns.

| Fix | Setting | Model 1 | Model 2 |
| --- | --- | --- | --- |
| noChannelSwap | pan −1 (hard left) | all energy on the right | all energy on the left |
| loopEndBothWays | backwards, Loop off, 1 s train | stops after 0.07 s | plays 1.03 s |
| controlsAtStart | 1 kHz fundamental | 4 pulsars in the first 10 ms | 11 |
| burstFromFirstStep | burst 1 on / 1 off | first pulse silent | first pulse plays |
| silenceAfterPulsaret | constant pulsaret, 1 ms at 10 Hz | voice still sounding 50 ms later; tail energy 33.7 | voice ended; tail energy 0.07 |
| exactFormant | 7 kHz single-cycle sine | mid-cycle crossing at 3.00 samples | 3.17 (ideal 3.15) |
| smoothControls | pan sweep, 2205 Hz pulses | 48 of 190 pulses repeat the previous pan | 1 |
| rateIndependentDC | held step at 96 kHz | decays to 1/e in 10.4 ms | 22.7 ms (as at 44.1 kHz) |
| burstEveryPulse | burst 2 on / 1 off, mask 0.5, 30 s | plays spread over every pulse position | one position in three never plays |
| formantEnvelopeRule | formant 1 kHz ≥ fundamental 10 Hz | old pulsaret ramps out after the next pulse (2 voices) | ends at the next pulse (1 voice) |
| raisedFundCap | 6 kHz, three trains | 1993 pulsars per second | 5981 |
| bandLimited | 50-cycle pulsaret at 15 kHz (all above Nyquist) | energy 378 (aliasing) | 0 |
| bandLimited | single-cycle pulsaret at 500 Hz | energy 1057 | 1055 (in-band content unchanged) |
