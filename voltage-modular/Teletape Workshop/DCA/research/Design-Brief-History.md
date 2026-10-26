# DCA design brief history

The following notes preserve the initial v0.1.4 design brief. They document early assumptions and are not a substitute for the final behavior in [DCA 1.0.0 developer notes](../versions/1.0.0/DEVELOPER-NOTES.md).

---

# dca: dual controlled amplifier, brief, v0.1.4

## Status
- Headless only: core+module checks 47/47 against stand-in SDK, Java 8 `-Xlint:all`. NOT yet compiled or run in Designer, not heard.
- Built on the author's mockup `dca.vmod` (Notes v0.0.1m). Panel untouched; all 13 user regions replaced (the mockup's default regions contained stray boilerplate that would not compile).

## Panel facts used [READ, author's notes fields and vmod]
- LEVEL slider 0..4 per channel (Designer default A 0, B 2.67). Unpatched CV = 5 V reference, so the slider is a level/amplifier control to 4x; with CV patched it is CV depth 0..4x.
- BIAS knob 0..1 (0.5 = none): biases the incoming signal; CCW negative, CW positive. [MODEL] implemented as DC added to the audio/CV at IN, +-5 V, before the VCA.
- PAN knob 0..1 (A default 0, B default 1): affects DUAL only; MONO outputs are VCA only. Constant-power law.
- LINK L/R: no coloration, unity into DUAL L/R only.
- MODE switch states: 0 IND, 1 SERIAL, 2 ST. [OPEN] switch direction (left = 0 assumed), default state arg 1.
- SHAPE knob 0..1: 0 LIN, 0.5 EXP, 1 LOG.

## Signal path (per channel) [MODEL unless tagged]
1. sig = IN + BIAS volts.
2. CV: unpatched = 5 V; patched = max(0, CV) (half-wave). Not smoothed.
3. x = clamp(LEVEL * CV / 5, 0, 4). LEVEL, SHAPE, BIAS smoothed (one pole, 10 ms; snaps when within 1e-7).
4. g(x): x <= 1: (a^x - 1)/(a - 1), log10(a) = 2*SHAPE (to 0.5), then 1 + 4*(SHAPE - 0.5) (a = 1 LIN, 10 EXP, 1000 LOG = 60 dB, dB-linear, true zero) [DOC Synthi "ideal logarithmic", 60 dB]; x > 1: g = x (linear to 4x).
5. v = sig * g; color k(g) = 0.15 g^2 below unity, 0.15 + 0.85 sqrt(dB/12) above (1.0 at 4x). [GUESS]
6. noise (only when IN is patched): white, 1.41 mV rms * (g/4) * k. 74 dB below a 10 V peak sine. [DOC 74 dB S/N at 3 % THD, noise falls with gain, none at cutoff]
7. 2x oversampled waveshaper (author's HalfBand19, coefficients unchanged). Clean path = exact 9-sample delay (0.19 ms), flat within 0.01 dB to 20 kHz; only the waveshaper residual goes through the down-sampling half-band.
8. Waveshaper: y = u(1 - 0.12 k (u/10)^2) up to 10 V (third harmonic 3.3 % at 10 V peak, k = 1: the Synthi 3 % THD level [DOC]); beyond 10 V tanh compression to a hard ceiling of 11 V, continuous in value and slope. [author: soft limit at 10 V, last volt overload]
9. MONO out = this. DUAL L = A cos + B cos + LINK L; R = sin terms + LINK R.

## Modes (v0.1.4, author's hardware-style normal)
- IND: independent. SERIAL: IN B unpatched takes MONO A (post-VCA, pre-pan); patching IN B breaks it.
- ST: only channel A's LEVEL, SHAPE, CV jack and PAN act; BIAS stays per channel. A feeds the left side, B the right, and PAN A is a balance control (centre = both full, turning away fades the opposite side).
  - Only IN A: IN A is normalled to B (B has its own BIAS); PAN A moves both copies left or right.
  - IN B patched (with or without IN A): the normal is broken; A and B are left and right inputs.
  - Only IN B: A is still in the mix with only its amplifier noise floor at A's LEVEL (the only case an unpatched channel makes noise).
  - Nothing patched: nothing anywhere.
- Mono environment: nothing plugged into DUAL R means DUAL L carries a mono mixdown (A + B + LINK L + LINK R at unity, no pan; in ST with the normal, the two copies count once). [OPEN] IsConnected() on an output jack is assumed to work.
- A channel with IN unpatched, no forced noise and BIAS centred does no processing at all (idle). Amplifier noise runs only on patched channels, 1.41 mV rms x (g/4) x k.

## Schematic review (Synthi 100 packet) [READ]
- EMS 32/15 Quad Log Modulator: CA3046 differential pair multiplier with emitter degeneration, 2N4288 output stage; control by exponential V to I conversion, which is the "log" law. Its tanh-like input transfer supports a soft cubic/tanh character (MODEL, not transistor-modelled).
- EMS 32/16 and 32/17 hex op-amps: ML741C on +-12 V, 4k7/4k7 unity inverters and 33k summing stages. The 741 swings roughly +-10 to 11 V (consistent with the 10 V knee and 11 V ceiling) and slews about 0.5 V/us (full-power bandwidth about 8 kHz at 10 V peak). The slew limit is not in the packet (741 datasheet knowledge) and is NOT implemented: proposal for a later version, only for the last volt.
- Resistor noise of those stages (4k7) is microvolts, far below the VCA spec noise; ignored.

## Open
- Slider taper: values 0..4 are treated as the gain factor; set the slider midpoint to 1.0 in Designer to put unity at 50 % travel (author's choice).
- BIAS target and range; ST semantics; mode switch order; HF slew limit; whether noise should also play when IN is unpatched.
- Smoothing time (10 ms) and color curve are [GUESS], tune by ear.
