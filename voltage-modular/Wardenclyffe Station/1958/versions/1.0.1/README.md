# Generator 1.0.1

Canonical source release, approved 2026-09-30 after Designer builds and listening tests.
Open `generator.vmod` in Voltage Module Designer. The matching Java export, approved panel image,
and SHA-256 manifest are included. The source pair is preserved exactly as approved after the final Designer save.

## Operation

- Mono selected sine or variable-slope triangle output. DUTY CYCLE covers 8–92% rise time,
  with a symmetric triangle in the middle and ramp/saw-like shapes at the extremes. DUTY CYCLE
  now has 10 ms smoothing; its initial/saved value is adopted directly.
- Frequency bands: 0.1–10, 1–100, 10–1000, and 100–10000 Hz. Manual tuning has 10 ms smoothing before FM.
- AMPLITUDE requests 0–10 V peak, with 10 ms smoothing. Output is linear through 5 V,
  compression eases in over 5–7 V, and the approved driven curve remains intact above 7 V.
  Actual maximum output is about 7.70 V peak, after compression.
- FM SELECT: Off / Int/4 / Int / Ext. Int/4 is one quarter of the full internal frequency deviation.
  ADJUST controls internal triangle rate over 0.05–50 Hz, or external input attenuation in Ext.
  The internal oscillator has bounded ±2.5% slow rate variation. Nominal full internal FM is ±18%
  of carrier frequency; Int/4 is ±4.5%. At full external attenuation setting, ±5 V produces ±18% deviation.
- DUTY CYCLE has no effect on SIN. FM is linear; instantaneous frequency is limited to 0.01–18000 Hz.
- Reference switch up: pure 1000 Hz sine at the dedicated jack, 5 V peak. Middle: off.
  Down: reference mixed at 2.5 V peak into the main output and passed through the output stage.
- POWER off and host bypass silence both outputs and freeze oscillator state. The panel power
  switch is independent of the host bypass setting.
- Meter: averaged main-output magnitude, 150 ms averaging and 100 display updates per second.
  A 5 V peak sine sits near mid-scale; this is a level indication, not a calibrated measurement.

## Validation

The user approved sound, controls, amplitude/frequency smoothing, compression, Int/4 and the
0.05–50 Hz modulator range. The release validator compiles exported and embedded source against
the installed Voltage SDK with Java 17, verifies hashes and source agreement, and tests the actual
DSP bodies in a harness with simulated controls/jacks. Native Designer UI behavior is covered
by the user's build and listening tests; no CPU benchmark is claimed.

Run from the repository root:

```powershell
python voltage-modular/Wardenclyffe Station/1958/tests/validate_cleanup.py --repo . --candidate voltage-modular/Wardenclyffe Station/1958/versions/1.0.1
```

The accepted voicing uses a stable main oscillator, internal-modulator drift, and output-stage
compression. No additional main-oscillator drift, noise, hum or oversampling was introduced at release.
Internal/variable names are descriptive lowerCamelCase; Display Names are human readable.
Designer UUIDs and module identity are retained. Category is Oscillators. Typed tooltip values
use displayed Hz, volts or percentages. The [release review](REVIEW.md) records validation and exceptions.
