# Validation — 2026-09-28

Passed with JDK 27 targeting Java 17 (`javac --release 17 -Xlint:all`) and the locally installed `C:/ProgramData/Voltage/voltage.jar` SDK:

- Exported and embedded source for all three modules compiled without warnings or errors.
- Baseline uploaded `.java` and `.vmod` executable source agree. Updated pairs agree as well (Designer may reorder uninitialized control field declarations).
- DSP token comparison confirms no audio-code changes beyond private identifier renaming, explicit false initialization, and HSB's requested switch inversion. Formatter preserves executable tokens.
- Project binary serialization round-trips, editor read-only line positions remain attached to their original generated lines, and all panel controls, art resources, skins, and saved test state remain byte-identical to the supplied projects.
- RGB: 1,152,000 output samples match the baseline bit-for-bit.
- CMYK: 1,536,000 output samples match the baseline bit-for-bit.
- HSB: 768,000 output samples match with old/new switch values paired to select the same circuits.
- Regression uses 128 connection patterns, changing controls, both switch states, audio up to 12 V, start-bypassed, bypass during processing, and return from bypass. No knob or switch reads occur while bypassed. Every RGB/CMYK processing control and jack has a tooltip; DC switch and HSB topology text follows the switch state.

The baseline was auditioned by the user. This release has not been opened in Designer or auditioned in Voltage Modular. The harness tests Java processing with simulated jacks/controls, not native UI behavior. A final Designer open/build and short host check should verify tooltip appearance, HSB up/down orientation, and saved-patch behavior. No claim of a CPU benchmark is made.

## Repeat the checks

Requires Python 3, a JDK with Java 17 target support, and the Voltage Modular SDK. From any directory:

```powershell
python path/to/voltage-modular/colorbox/tools/validate.py --sdk C:/ProgramData/Voltage/voltage.jar
```

The tool validates hashes and project/source agreement, compiles both source forms, generates the comparison harness directly from the archived versions, and runs the audio/tooltip checks. It writes only to the ignored `colorbox/build/` directory. Without `--sdk`, structural and simulated audio checks still run; real SDK compilation is skipped explicitly.
