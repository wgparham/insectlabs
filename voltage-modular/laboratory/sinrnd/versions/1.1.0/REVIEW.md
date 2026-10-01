# r195L: SIN/RND generator 1.1.0 release review

## Release record

- Module: r195L: SIN/RND generator (`sinrnd`)
- Version: 1.1.0
- Approved: 2026-09-30
- Candidate Notes: `v1.1.0rc`; released Notes: `v1.1.0`
- Canonical path: `laboratory/sinrnd/versions/1.1.0/`

## Completed review

- The user approved the exact v1.1.0rc Designer build after successful control, routing, save/reload and listening tests. The canonical Notes marker is `v1.1.0`.
- The exported Java and embedded Designer source match; the project binary round-trips. The 34 current Designer components retain their UUIDs, supplied panel layout and artwork. All functional controls use lowerCamelCase names and human-readable Display Names. Category is Oscillators.
- FREQUENCY has a logarithmic 50–18,000 Hz mapping, explicit C4 default in the Java constructor and both Designer default fields, and FINE spans ±200 cents. The release validator checks both C4 representations.
- MODE is intentionally mapped SIN / RND / RD2 / MOD / FLT. RND and RD2 do not read MOD IN. MOD IN is linear FM in SIN/MOD. FLT with a connected MOD IN silences the oscillator and filters normalized input audio; MOD LEVEL is a ±200% bipolar attenuverter/amplifier and RND MOD blends white noise before filtering. AMP/RANGE retain normal shared output scaling.
- The final code review moved GUI-timer cleanup before the single generated `super.Destroy()` call. This is lifecycle-only cleanup; no audio callback or panel behavior changed.
- Both source forms compile against the Voltage Modular SDK using Java 17 target and warnings as errors. The callback suite passes 329,622 assertions, covering source parity, Designer round-trip, metadata/defaults, all modes and ranges, external-FM priority, RD2 isolation, FLT input routing/scaling/noise blend/filter control, power, bypass, smoothing, C4 and extrema.

## Deliberate design choices and limits

- Manual controls smooth over approximately 10 ms. External FM remains audio-rate/immediate. The FLT cable transition uses the same short smoothing interval.
- Internal MOD/FLT uses moderated exponential random FM with a ±1.5-octave span. External FM is linear and can request ±200% deviation at full MOD LEVEL; negative instantaneous frequency is stopped at zero and upper frequency is limited below Nyquist. Deep modulation can alias near the limit.
- The filter is the approved original voicing, with two one-pole stages and cached coefficient updates. It is neither a component-level Berna reconstruction nor a precision equalizer.
- High-level compression uses two nonlinear residual evaluations with midpoint interpolation. It is a lightweight 2x approximation, not a complete alias-free oversampling chain. The linear path is unchanged by it.
- No per-sample allocations occur. Frequency/filter exponentials are cached when controls change. The meter and lamp update at 40 Hz. These implementation observations are not a host CPU benchmark.
- Host bypass is intentionally silent for this source and freezes state. Physical power uses an 8 ms fade, then freezes oscillator/noise histories.
