# TRM 1.0.0 — DSP design record

## Product intent

Three independent, gain-staged ring-modulator lanes provide four selectable colors. EMS Synthi 100 schematics and construction practices informed the staged signal path, but the selector is a creative fictional diode/transformer palette. It does not claim to reproduce undocumented EMS component options. Keep the user-approved sound and output levels intact unless a future release is deliberately auditioned.

## Signal flow

Each lane reads one Carrier and one Modulator input, multiplies each by its own smoothed 0–2 gain, tracks the maximum absolute gain-staged input, applies the selected nonlinear ring core, then sends the result through a 2× HalfBand19 interpolation/decimation path. The embedded implementation reuses the established [shared primitive](../../../../../dsp-primitives/src/com/insectlabs/dsp/filters/HalfBand19.java); the VMOD retains a self-contained copy. A bit-for-bit parity check runs against the primitive. No output level control, automatic makeup gain or final limiter is present. Each lane has its own FIR histories, input envelope and selector crossfade state.

The controls smooth over 5 ms. The envelope follower uses 8 ms attack and 120 ms release, and its value is normalized against 5 V before shaping each mode and scaling the input activity lamp. Selector changes crossfade between voices over about 5 ms at the 2× rate. FIR coefficient values are embedded in the source; the interpolation/decimation filtering has finite rejection and delay. CPU use was not benchmarked on the target host.

## Four voicings

- **T** — biased transformer-like tanh transfer with envelope-dependent headroom, plus 3.5% carrier leakage. The leakage is an intentional tradeoff: a zero Modulator does not fully silence this voice.
- **G** — symmetric soft-conduction knee with threshold and output shoulder responding to the envelope.
- **S** — lower soft-conduction threshold with a tighter, brighter driven shoulder.
- **Si** — symmetric hard conduction threshold that opens with the envelope, followed by a firmer soft shoulder.

For G/S/Si, shaped Carrier and Modulator multiply and divide by 5. The T path also multiplies its shaped inputs and includes the carrier-leakage term. There is no compensating gain after these formulas, so level differences are part of the voice. These formulas are software voicings, not transistor-level models or measurements.

## State and host behavior

Input gain is sanitized to 0–2; nonfinite/unconnected audio becomes zero and large finite input is bounded before DSP. Output and lamp writes are checked for finite values. Host bypass has no dry route: it silences outputs and lamps, skips reads and freezes DSP histories. On the first active sample after bypass, FIR histories reset and current gain and selector values are adopted. The three initial selector states remain intentionally different: lane 1 S/state 2, lane 2 G/state 1, lane 3 Si/state 3.

## Maintenance checks

After changes to mode equations, repeat `tests/validate.py` and listen to each lane at low and high input levels, including selector changes. Changes to gain smoothing, envelope coefficients, resampling, bypass or resume require callback/boundary tests and a fresh native-host listen. Changes to the panel project require rechecking control identities, initial switch states, image references and Java/VMOD agreement. Revalidate SHA-256 after any archive edit.
