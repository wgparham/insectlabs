# sw2 amplifier-character audition

## Current audition: 3 V knee

The user requested 3 V as the lower bookend against the previous 5 V calibration. Only the knee changed: tone bandwidth, compression amounts/asymmetry, 2x processing, CLK voicing, and relay timing are unchanged. The supplied panel update and official `com.insectlabs.sw2.sw2` identity are preserved.

The static linear region is now +/-3 V. +5 V becomes +4.88 V; -5 V becomes -4.868 V. +10 V becomes +8.416923 V; -10 V becomes -8.258615 V. CV above the knee is intentionally compressed as well as audio. Direct host bypass remains unshaped. Both source forms compile and the revised routing/DC/compression checks pass. Listening approval is pending.

The measurements below describe the earlier 5 V audition, not the new calibration.

## Earlier 5 V audition

29 September 2026. Functional routing and the 2.2 kHz CLK revision were approved by the user. This new amplifier voicing is a working audition, not a canonical release.

Two matching stages serve X after source selection and Y before destination distribution. Both operate with CLK up or down. A fixed 14 kHz one-pole at 96 kHz follows midpoint interpolation and smooth, slightly asymmetric compression. This is the lightweight 2x approach used by SIGPROC, not a brick-wall oversampling filter. The CLK contact filter and 96-sample settling time remain unchanged.

The static curve is exactly linear through +/-5 V, with continuous first and second derivatives at the knee. Above it, positive and negative peaks compress gently: +10 V becomes +9.0854 V; -10 V becomes -8.9939 V. Small audio gains its character from bandwidth rounding rather than saturation. Settled CV in the linear region is preserved; edges are rounded, and larger CV is compressed. Asymmetry can generate a small signal-dependent mean shift on driven audio; there is no injected DC bias or drift.

The faders remain the more colored utilities: their shaping begins at much lower levels, and Fader|Distr B uses an 11.5-to-8 kHz tone range. sw2 uses a more open fixed stage, without a compressor detector, pumping, random variations, or extra panel controls. It allocates no objects in the audio callback and evaluates no per-sample exponentials. Host CPU has not been measured. Bypass does not run the amplifier stages; resumption initializes them from current signals.

Validation:

- Exported Java and embedded Designer source compile with Java 17 against the Voltage SDK, warnings treated as errors. The earlier sandbox-generated-class write problem did not recur when compilation ran with authorized filesystem access.
- The extracted actual DSP passes routing/OFF, matching-bank/isolation, monotonic compression, DC, and bypass/resume checks. Run `python voltage-modular/laboratory/sw2/tests/verify_character.py` from the repository.
- A coherent-tone numerical model at 48 kHz measured nonharmonic folded energy relative to the fundamental: at 10 V peak, approximately -96 dBc at 997 Hz and -49 dBc at 7001 Hz; at 20 V peak, approximately -78 and -38 dBc respectively. Through 5 V peak the curve is linear. This lightweight design does not eliminate aliasing under hot high-frequency drive; these are model measurements, not a host capture or listening approval.

Audition with the TestBench music mix, clean piano/guitar, and tone/level fixtures: listen first at ordinary levels, then boost into the knee using SIGPROC. Compare CLK up/down and host bypass, and check OFF and routing again with driven signals. Broad infrastructure cleanup remains part of pre-release work.