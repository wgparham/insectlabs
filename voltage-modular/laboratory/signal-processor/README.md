# Signal Processor

The Signal Processor is the first Series One Laboratory module. It contains two independent mono stages inspired by early laboratory amplifiers and control equipment. Each stage provides bipolar gain, post-character offset, an external gain-control input with level control, and a PROC/VCA mode selector.

The canonical release is [Signal Processor 1.0.1](versions/1.0.1/README.md), approved by the user after a successful Designer build and host run. [Character notes](versions/1.0.0/CHARACTER-REVISION.md) document the locked sound and mode behavior.

## Current design

- Both stages default to PROC and use matching selector direction.
- Gain is symmetric from -3 to +3. Positive PROC gain uses full character; negative gain provides a cleaner inversion direction.
- PROC is bipolar and permits immediate external modulation.
- VCA is unipolar, rounded below unity, and uses a deliberately pronounced vactrol response.
- OFFSET remains an exact post-character static voltage.
- Restrained 2x character processing provides weight when driven without duplicating Colorbox coloration.
- Direct host bypass follows the shared [module infrastructure standards](../../docs/Module-Infrastructure-Standards.md).

The module is mono first: its two stages are redundant independent processors, not a stereo-linked design.

## Validation

From the repository root:

```powershell
python voltage-modular/laboratory/tools/validate_signal_processor_character.py
```

The current candidate passes 25 focused DSP tests, matched-project/source and checksum checks, control/mode mapping checks, direct-bypass checks, and complete tooltip coverage. Both embedded and exported sources compile against the installed Voltage Modular SDK with the Java 17 target and all warnings treated as errors.

The user confirmed the final Designer build and host run. See the [release notes](../CHANGELOG.md).
Historical mockups and the original clean-core experiment are preserved under `references`; they are not current build inputs.
