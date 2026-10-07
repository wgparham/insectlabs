# SN-16u universal test bench — 1.0.0

Canonical Laboratory release. SN-16u combines precision references, conversion, measurement, filtering, and a one-shot sweep in a modified test-equipment workbench. The approved panel is manual-first; its reference and measurement paths are designed for clean, predictable operation.

Open [sn16u.vmod](sn16u.vmod) with its matching [Java export](sn16u.java). The module's internal class is `sn16u`. [USER-MANUAL.md](USER-MANUAL.md) is the approved operating guide; [CONTROL-MAP.md](CONTROL-MAP.md) records the retained panel identities and positions. The hero image is the final panel art.

## Main functions

- Fixed precision voltage references and a manual whole/fraction voltage source with polarity and two summing/conversion inputs.
- Selectable 1 V/oct, 0.5 V/oct, or user-defined Hz/V conversion; seven tunable A references; clean A4, C4, and F#4 outputs; independent 1 Hz, 100 Hz, and 1 kHz courtesy sines.
- Pink and blue noise, fast and slow impulse outputs, and a retriggerable 42-second linear or exponential sine sweep.
- Independent nonresonant high-pass and low-pass paths, two voltage/pitch meters, an audio frequency counter, and an RMS meter.

## Verification

Both exported and embedded source forms compile with the Voltage Modular SDK using Java 17 compatibility and warnings as errors. The release validator reports 4,032,101 callback/numerical checks passed, including pair round-trip, metadata, startup, output, routing, conversion, meter, bypass, and sweep checks. The release does not claim a native-host CPU benchmark. The user approved the manual and confirmed the final panel, audio behavior, and test workflow.

Run `python "voltage-modular/Wardenclyffe Station/sn-16u/versions/1.0.0/tests/validate.py"` from the repository root to repeat the SDK and callback checks.
