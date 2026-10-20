# OSC candidate DSP design

Version: 1.0.0. Host engine 48 kHz; oscillator path 192 kHz (4x). This is an EMS-inspired software model, not a literal transistor solver.

User direction, 2026-10-17: lively behavior takes priority over uniform control. Shape-dependent volume dips, phase cancellations and other useful consequences are acceptable. There is no RMS/loudness normalization, cancellation correction or automatic gain control. Antialiasing must not be judged by eliminating intentional harmonics or flattening the mix response.

## Synthesis and bandwidth

One continuously running phase drives sine, triangle/ramp and pulse paths. The separate V/oct input is exponential 1 V/octave. Linear FM multiplies that tuned frequency by `1 + FMvolts/5`. It is DC coupled, sensitivity follows tuning, -5 V stops phase advance, and more negative voltage reverses direction. This scaling is an audition choice, not a measured EMS Hz/V specification. No phase reset, reflection or output polarity switch occurs at zero.

Phase advances by the analytic integral of exponential pitch times linearly changing FM over each subinterval. A small-exponent series avoids cancellation, and constant base pitch has a fast exact path. Native pitch, FM and waveform CV are linearly interpolated at 4x. Bandwidth selection uses absolute signed rate, including reverse operation; a finite denominator handles zero. Manual controls use 5 ms smoothing; range changes glide within selected limits. These choices do not claim a measured EMS CV amplifier response.

The first 2x polynomial edge-correction probe was rejected for insufficient static rejection at narrow widths/high pitches. The subsequent 2x table implementation passed static tests, but deep audio-rate shape modulation justified testing 4x. The working candidate uses the latter architecture.

Three related Fourier table families hold a rising saw, its periodic integral and an absolute sine. Each family has 14 bandwidth banks, 32768 samples per bank and cubic interpolation. Static initialization builds these once per loaded module class, outside audio callbacks. Their float storage is approximately 5.25 MiB plus a 128 KiB sine table; native class-loader sharing is host dependent. Scratch arrays exist only during initialization.

Each bank has a power-of-two harmonic ceiling, with a raised-cosine taper over its upper half. Playback crossfades adjacent conservative banks using a smoothstep weight. Selected bank support stays below 0.4 times the host rate (19.2 kHz). This deliberately rolls off high harmonics as pitch rises; it is not an attempt to preserve an infinitely sharp waveform at Nyquist. It also imposes a finite harmonic ceiling at very low pitches. Neither the brightness taper nor table size is a historical EMS specification.

Triangle/ramp is the scaled difference of two shifted integrated-saw lookups. Pulse is the difference of shifted saws plus its duty-dependent DC term. The finite shape endpoints are 1% and 99% for both timing asymmetry and pulse width. When shape moves, bandwidth selection includes the estimated extra corner velocity. The velocity estimate interpolates across native frames; sudden bandwidth-bank changes must not become a new source of aliasing.

Sine uses a sine table with small second/third harmonic residuals, then blends a band-limited absolute sine to progressively suppress either half-cycle. A smooth gain normalization avoids a new kink at the center of audio-rate shape modulation. DC, asymmetry and shape-dependent amplitude are intentional. There is no generic output tanh or simulated noise floor.

## Resampling and outputs

Two FIR stages per waveform decimate 192 -> 96 -> 48 kHz. The first is a 31-tap Kaiser-windowed half-band filter, beta 8.6. The final stage is a symmetric 159-tap Kaiser-windowed sinc, 22 kHz cutoff at 96 kHz, beta 8.6, normalized for DC unity. Both exploit coefficient symmetry, and calculate dot products only on retained samples. This is a local quality refinement, not a modification to the approved HalfBand19 primitive or any released module.

Nominal FIR group delay is 15/192000 + 79/96000 seconds, approximately 0.901 ms. All separate outputs have identical delay; MIX sums them after decimation. Outputs are nominal +/-5 V at full level for symmetric waves. Pulse duty and sine rectification preserve intentional DC; MIX can exceed +/-5 V.

## Character and state

Two slow smoothing stages follow a bounded pseudorandom pitch target, updated once per second and seeded per instance. Target depth is 1.2 cents; actual motion is smoother and usually smaller. Sine shape contributes up to 0.35 cents of common pitch shift. Both are explicit voicing choices, not measured EMS coefficients. They are disabled in coherent spectral tests only, so intentional movement is not misclassified as aliasing.

No phase/drift history is serialized. Native controls persist through Designer. Recall/reset callbacks queue atomic adoption for the audio callback; phase is retained, controls are adopted and FIRs cleared. Bypass emits silence, reads no controls/CV and freezes histories. Resume follows the same adoption/filter-clear policy, preserving phase. There is no mandatory warm-up delay.

Nonfinite controls use bounded fallbacks; nonfinite CV becomes zero. Pitch CV is bounded to +/-16 V; linear FM and waveform CV to +/-20 V. The tuned base rate is bounded to 0.0001 Hz-18 kHz, control sums to their physical endpoints, and signed effective rate/integrated step to +/-18 kHz. Zero is allowed after modulation. At numerical overload, the step limit is not an exact continuous-time limiter model. These are numerical/software limits, not claimed historical voltage specifications. Audio callbacks contain no allocation, file I/O, UI calls, locks or table generation. Tooltip formatting and typed-entry conversion stay outside rendering.

## Measurement limits

Static FFT tests measure non-harmonic energy. Modulated tests compare the actual candidate against a 32x evaluation of the same waveform model, with final Nyquist exclusion and the candidate's in-band FIR response. That is total model/reference error, not a pure alias estimate and not proof of similarity to a hardware recording. The finite modulation matrix cannot guarantee alias-free operation for every external signal. Native listening and host CPU remain separate checks.
