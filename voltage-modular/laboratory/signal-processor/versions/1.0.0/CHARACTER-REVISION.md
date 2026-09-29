# Signal Processor character revision

Working pair:

- `signal_proc.vmod`
- `signal_proc.java`
- `SHA256.json`

Both stages default to PROC and use matching selector orientation. Both Gain knobs cover -3 to +3. The Java source and the source embedded in the Designer project are synchronized.

## PROC

PROC is the fast, DC-coupled laboratory amplifier. Its requested gain is:

`GAIN + EXT LVL × EXT INPUT / 5 V`

The result is limited to -3 through +3. Negative gain inverts. External modulation remains unsmoothed, while changes to the three manual controls retain the existing 5 ms ramp. OFFSET is added after the character stage, so zero-input static voltages remain exact and are not compressed.

Positive PROC gain uses the full character stage. Negative PROC gain retains 35 percent of that transfer, creating a cleaner inversion direction while preserving a trace of the same voicing. This makes the knob mechanically symmetric but sonically directional. VCA always uses the full character stage.

## VCA

VCA uses the same requested-gain expression but limits it to 0 through +3. A negative total closes the VCA and never inverts it. Between zero and unity, the control follows `g²(2-g)`: it is rounder and quieter than the PROC law while reaching exact unity at 1. Above unity it becomes linear, retaining the +1 to +3 push range.

The VCA control element opens with an approximately 3 ms time constant and closes with an approximately 30 ms time constant. This response applies to external modulation as well as the settled manual target. Switching modes or returning from host bypass initializes from the current controls rather than replaying stale state.

## Output character

The gained signal passes through a low-cost 2x stage before OFFSET is added. It remains linear through 3.5 V. Above that knee it develops gentle asymmetric compression: the positive half uses slightly more compression than the negative half. A drive-dependent one-pole stage at the 2x rate moves from approximately 30 kHz at ordinary levels toward 16 kHz under heavy drive. This makes high-frequency overload rounder without imposing a conspicuous dark voicing at normal levels.

At a steady +6 V before OFFSET, the character stage produces approximately +5.5 V. At -6 V it produces approximately -5.583 V. This is intended as restrained output-stage weight rather than a Colorbox-style effect.

Direct host bypass follows the Colorbox infrastructure rule: it copies each signal input to its corresponding output, does not read controls or CV, and freezes VCA and character histories. Processing resumes from current controls without replaying stale state.

## Validation and listening

Run the focused core regression from the repository root:

```powershell
python voltage-modular/laboratory/tools/validate_signal_processor_character.py
```

The current revision passes 25 focused checks covering the symmetric PROC range, cleaner negative-gain drive, exact post-character offset, bipolar PROC CV, rounded/unipolar VCA law, attack/release timing, driven asymmetry, high-frequency rounding, channel independence, exact bypass, and clean resume.

For the host audition, compare PROC and VCA with a sustained clean piano or guitar file, then the -12 dBFS 1 kHz tone and level staircase from TestBench. At Gain +1 the modes should differ mostly in VCA motion, not static level. Push Gain toward +3 or use a hotter input to hear the output stage. Check that Offset alone still produces the requested static voltage. Confirm the two selector directions and direct bypass once more in the host.
