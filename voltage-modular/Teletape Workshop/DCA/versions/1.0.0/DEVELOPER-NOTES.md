# DCA 1.0.0 developer notes

## Signal path

The 48 kHz module adapter reads audio/CV jacks, maps the 0..1 BIAS knobs to ±5 V, maps the level sliders directly to 0..4, and passes the control/routing state to the framework-free `DcaCore`. The core applies a half-wave CV response and 10 ms one-pole smoothing to LEVEL, SHAPE, BIAS and PAN. CV itself is not smoothed. An unpatched CV is +5 V.

For control amount `x` below unity, the response parameter maps to a generalized exponential gain curve. LIN is linear; EXP uses a ten-to-one exponential mapping; LOG spans a nominal 60 dB below unity. Unity is invariant across the sweep, and above unity each curve continues linearly through 4×. The nonlinear amount grows from zero at silence to its maximum at 4×. BIAS offsets the input before gain.

The soft stage is odd-symmetric. Below 10 V it uses a gain-scaled cubic term; above 10 V it transitions smoothly to a tanh curve approaching 11 V. Deterministic xorshift noise is added only to an active connected channel, except the ST special case where an unpatched A side remains audible as the noise floor when only IN B is connected. No noise is generated at zero gain.

## 2× nonlinear path

The input is interpolated and the nonlinear residual is processed through the 19-tap half-band FIR at twice the host rate. The clean signal is delayed by nine host samples to align with the FIR path; this avoids filtering the linear component. The coefficients and processing are embedded in the module to keep the release standalone. The same reusable FIR is recorded in [`dsp-primitives`](../../../../../dsp-primitives/README.md#halfband19).

This is a practical oversampled waveshaper path, not an ideal alias-free converter. The FIR has finite rejection, the host-rate and smoothing assumptions are 48 kHz, and the model is a software voice rather than a transistor-level Synthi reconstruction.

## Routing and lifecycle

- IND has no normal from A to B.
- SERIAL normals A's post-channel output to B only while IN B is unpatched.
- ST normals IN A to both channels while IN B is unpatched; B retains its own bias. IN B alone also activates the A amplifier noise floor.
- Link inputs are summed at unity into DUAL only. If DUAL R is unpatched, DUAL L carries a unity mono mixdown; pan is ignored and the ST duplicate is counted once.
- Bypass resets channel DSP state and passes raw audio through the last selected mode/pan routing, without noise, gain, CV or coloration.

The Java class stores no extra preset state; Designer persists the controls and switch. The release `tests/validate.py` compiles both Java copies against the Voltage SDK, checks Designer serialization/source/control identity, and exercises the extracted core with deterministic checks.
