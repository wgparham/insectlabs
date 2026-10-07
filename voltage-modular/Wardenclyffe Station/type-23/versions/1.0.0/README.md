# Signal Processor 1.0.0

Canonical source release, approved 2026-09-29. Open `signal_proc.vmod` in Voltage Module Designer.
`signal_proc.java` is the matching source export; `sigproc_hero.png` is the panel image.
`SHA256.json` records all three file hashes. The user confirmed the final Designer build and host run.

## Behavior

- Two independent mono stages; no cross-channel normalization or stereo coupling.
- Both selectors use numeric 0 = PROC and 1 = VCA. Both stages default to PROC.
- Both Gain controls cover -3 through +3. OFFSET covers -5 through +5 V; EXT LVL covers -2 through +2.
- Requested gain is `GAIN + EXT LVL Ã— EXT INPUT / 5 V`.
- PROC is bipolar and responds immediately to external modulation. Positive gain uses full character; negative gain uses 35 percent character for cleaner inversion.
- VCA is unipolar, uses a rounded law below unity, and retains the linear +1 to +3 push range. Its approximate time constants are 3 ms attack and 30 ms release.
- Manual Gain, Offset, and Ext Lvl changes smooth over 5 ms. OFFSET is added after character processing and remains exact at zero signal gain.
- The signal path uses a low-cost 2x character stage. It is linear through 3.5 V, then adds mild asymmetric compression and drive-dependent high-frequency rounding.
- Unpatched inputs are 0 V. The channels have no internal routing between them.
- Direct host bypass follows the Colorbox standard: copy each signal input to its corresponding output, skip control/CV reads and DSP, freeze histories, and resume from current settings without stale ramps.

All controls and jacks have dynamic or descriptive tooltips. The mode and gain tooltips identify the current PROC/VCA behavior.

## Validation

From the repository root:

```powershell
python voltage-modular/Wardenclyffe Station/tools/validate_signal_processor_character.py
python voltage-modular/Wardenclyffe Station/tools/validate_signal_processor_project.py --sdk C:/ProgramData/Voltage/voltage.jar
```

See the [release notes](../../../CHANGELOG.md). Future edits belong in a development copy, leaving this release intact.
