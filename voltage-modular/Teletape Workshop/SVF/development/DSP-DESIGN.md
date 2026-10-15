# SVF 1.0.1 — approved DSP design

Approved 2026-10-15. DSP unchanged from 1.0.0 and canonized in `../versions/1.0.1/`; publication remains deferred. Based on the documented Synthi 100 high-pass board 34/01 / Hilton drawing 32/01, exposing its three labeled output links. This does not establish the circuit revision used in every Synthi 100 or reproduce the separate 34/00 LPF.

## Circuit interpretation

Source: [shared Synthi 100 scan](../../references/ems_synthi_100_sch.pdf), PDF page 13; HPF wiring pages 37–38. The A3 summer produces HIGH. A5/C2 followed by T4 produces BAND; A6/C3 followed by T5 produces LOW. C2/C3 are each 330 pF. R14 returns LOW to the inverting summer. T3 and the 15k path return BAND with variable negative feedback, opposed by the smaller positive-input path through R17/R16. This lets response move from damping toward oscillation. The physical FET resistance-versus-control curve and calibration are unknown.

R11=100k and R15=27k give input summer gain magnitude 0.27. R14=27k gives unity LOW feedback relative to R15. A4 has R25=10k and R26=3.3k: noninverting gain 1+10/3.3=4.030303. All three outputs use that same equivalent output amplifier. No peak normalization or automatic resonance gain compensation is added. Nominal HP high-frequency and LP DC gain are -1.08818 at unity manual input gain; polarity is preserved. BAND has its natural phase and resonance-dependent peak.

The ideal normalized equations are h=-0.27*u-l-d*b, db/dt=omega*h, dl/dt=omega*b. This is a quadratic state-variable system. Linear transfer denominator is s^2+d*omega*s+omega^2, with numerators -0.27*s^2, -0.27*omega*s and -0.27*omega^2 for HP/BP/LP. This core is not an 18 dB/octave tracking filter.

## Deliberate software modeling choices

- Manual frequency is exponential 5–15000 Hz, matching the supplied panel photograph. External CV is +2 octaves/V (0.5 V/oct) at full CV amount; positive raises cutoff. Overall frequency is clamped 5–20000 Hz. External jack sensitivity is a chosen translation of historical control practice, not a measured rack-unit calibration.
- RESPONSE r gives d=1.8*(1-r)^2-(2+0.27)/101. The 1.8 corresponds to 27k/15k maximum feedback; the small positive term estimates the R17/R16 path. This is a controllable approximation to the unknown FET/control calibration, not an exact FET model. At response 0, Q is approximately 0.563; near 80%, Q is approximately 20; near 88.8%, damping crosses zero.
- OTA integrator drive uses S(v)=6.4*tanh(v/6.4). The 6.4 V scale is motivated by the 220/(27000+220) input divider and a roughly 52 mV differential-pair scale. Temperature, actual device curves, output resistance and FET voltage offsets are omitted.
- Additional loop loss gives h=-0.27*u-l-d*b-0.04*b^3. This explicit amplitude-dependent damping is a musical approximation to overload/loss and stabilizes self-oscillation. The coefficient is not extracted from a transistor model or measured hardware. It belongs inside the loop, so drive and resonance interact. Change it only as a fresh listening trial.
- Each equivalent A4 output has a symmetric 10 V fourth-order soft knee. That is a selected headroom model for +/-12 V powered circuitry, not a measured 741 output swing. No injected hiss, hum, drift or channel tolerances.
- Once on entry into negative damping from near silence, a 1 microvolt internal BAND perturbation seeds oscillation. There is no continuous noise source. Oscillation buildup is naturally slow at very low cutoff; do not add an instant oscillator that bypasses the filter dynamics.

## Integration and resampling

Both cores run at 96 kHz, using an implicit-midpoint nonlinear solve. Let g=tan(pi*f/96000), b0/l0 be previous states, B/L the midpoint states. Solve B=b0+g*S(h), L=l0+g*S(B), h=-0.27*u-L-d*B-0.04*B^3. Newton iteration uses the analytic scalar derivative, up to ten bounded iterations with early convergence at 1e-11 V correction. States then become 2*B-b0 and 2*L-l0. This prewarped trapezoidal form preserves the expected linear response and avoids a delayed-feedback resonance hack. The stress test independently checks equation residuals.

Established HalfBand19 FIR interpolation and per-output decimation are embedded in the module. The serial feed is selected from the first core's amplified/limited tap inside the 96 kHz loop; there is no intermediate downsampling/upsampling. Parallel takes the raw first audio source before either channel's level control. All six output decimators use the same phase and DC normalization. FIR nominal group delay is nine native samples (0.1875 ms), in addition to each filter's frequency-dependent phase.

This short FIR has finite rejection/ripple. Its image path is observable in cascaded HP routing; the validation reference evaluates both baseband and interpolation-image paths explicitly. It is not an alias-free resampler. CPU and native host load have not been benchmarked.

## Controls and routing

Eight manual controls smooth over 5 ms; frequency smoothing is in log-frequency position. CV is read each 48 kHz callback and held for the two internal phases, with no additional CV smoothing. Frequency coefficient calculation is cached when the effective frequency is unchanged. Input gain is 0–2, unity at 50%; zero means no external drive, but self-oscillation can still sound. CV amount is 0–100%; RESPONSE is 0–100%. Existing ranges/defaults/panel geometry/UUIDs/skins are preserved: input gain starts at unity, CV and response at zero, both frequency knobs at 15000 Hz. Turn frequency down to audition LP/BP with low-pitched audio. Typed frequency entry is Hz; other knobs accept percent.

Native state 2/up is SERIES, state 1/middle independent, state 0/down PARALLEL. The series source selector is native 2/up HIGH, 1/middle BAND, 0/down LOW. Existing switch defaults are preserved: independent and HIGH. The user-approved panel marks SERIES up, independent middle and PARALLEL down; the series route is HIGH up, BAND middle and LOW down. Patching IN 2 overrides either normalization. IN 2's level always acts on its selected input; IN 1's level acts only on core 1, so parallel levels are independent. All six outputs always remain available.

## State, bypass and safety

Native knob/switch state persists; DSP history does not. Reset, preset/variation completion and custom restore callbacks queue an AtomicBoolean request. The audio callback clears integrators/FIRs, adopts controls and allows fresh oscillation startup on its next configuration. Bypass freezes DSP and smoothing, reads no knobs/switches/CV, and passes raw channel inputs to each corresponding set of three outputs. If IN 2 is unpatched, bypass uses the last active coupling mode: coupled modes pass raw IN 1 to all second-channel taps; independent mode gives silence. Switching while bypassed takes effect on resume. Resume clears all histories and adopts controls, avoiding old ringing.

Nonfinite signals become zero; finite signals/CV are numerically bounded to +/-10000 V before stateful processing. Controls clamp to 0–1; invalid switch values use fallback positions. Invalid or >64 V internal states clear the affected core. These bounds are numerical protection outside the musical operating range, not a claim about hardware voltage limits. Extreme finite input is guaranteed finite recovery, not exact analog overload behavior. No per-sample allocations, GUI updates, I/O or locks; all output arrays and FIR buffers are allocated outside callbacks. Existing HalfBand19 formatting is retained to preserve established primitive arithmetic. Generated super.Destroy remains exactly once.

## Validation and release limits

tests/validate.py compiles exported and embedded source against the installed SDK with Java 17 target, all lint and warnings as errors. It extracts actual core/callbacks for independent complex HP/BP/LP transfer tests at six frequencies, full serial polyphase response for each source selection, parallel equivalence, patched-input override, DC gain/rejection, nonlinear solver stress, self-oscillation growth/bounds, malformed-input/state recovery, bypass freeze/no controls/resume, reset, taper/CV calibration and typed-entry inversion.

Headless checks establish the equations, routing, bounded recovery, source pair and build behavior listed in the versioned `VALIDATION.txt`. The user auditioned and approved the instrument. Native CPU profiling, automated GUI interaction/undo/recall checks, and exact hardware comparison remain unmeasured. The Mk1 recreation uses a different documented frequency-control approach; this release is based on the available 34/01 drawing and is not claimed to reproduce that board or every Synthi 100 revision.
