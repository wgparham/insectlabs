# OSC developer notes - 1.0.0

Teletape Workshop canonical release, approved/canonized 2026-10-17. Class/package remain `osc` / `com.insectlabs.osc`; Category remains Oscillators. The canonical pair is `OSC/versions/1.0.0/osc.java` and `osc.vmod` relative to the collection.

See [user manual](USER-MANUAL.md), [release review](REVIEW.md), [DSP design](DSP-DESIGN.md) and [measurements](tests/REPORT.md).

## Signal flow and controls

One signed phase drives all three waveform paths at 192 kHz for the host's 48 kHz engine. Dedicated V/oct pitch is exponential; linear FM multiplies tuned rate by `1 + FM/5` and reverses below -5 V without resetting phase. `linearFmMean` integrates exponential pitch times changing linear FM; small-exponent and constant-base cases avoid cancellation/extra work. Absolute rate drives bandwidth selection. Canonical wrapping handles negative sub-ulp remainders rounding to 1.0.

`renderSample` adopts queued controls, smooths manual controls for 5 ms, interpolates CV at 4x, advances phase, shapes the waveforms and decimates. `selectBandwidth`, `wave`, `lookup` and the three waveform helpers implement Fourier-bank selection and cubic interpolation. Triangle uses shifted integrated saws; pulse uses shifted saws plus DC; sine has small intentional harmonic residuals and half-cycle shaping. The final waveform gains follow the three LEVEL controls; `outputMix` is the exact post-filter sum.

Fourteen 32768-sample banks per family, conservative 19.2 kHz harmonic support and finite 1%/99% shape endpoints are software choices. Two FIR stages (31-tap half-band, then 159-tap 22 kHz Kaiser sinc) give about 0.901 ms nominal common delay. Table storage is approximately 5.4 MiB per loaded module class. Slow drift target is bounded to 1.2 cents; sine shape contributes up to 0.35 cents of common pitch shift. Coefficients, arithmetic order and voicing are preserved from approval.

VCO range is 1 Hz-15 kHz; LFO range 0.05-500 Hz. Default frequency is C4 in VCO mode; levels default to zero. V/oct is 1 V/octave. Waveform CV is +5 V per full sweep; native destination states 2/up shape, 1/middle both, 0/down level. Native range 1/up is VCO, 0/down LFO. Numerical base rate is 0.0001 Hz-18 kHz; signed effective rate/step is bounded to +/-18 kHz. Pitch CV clamps to +/-16 V and other CV to +/-20 V; nonfinite values use bounded fallbacks. These are software bounds, not historical hardware specifications.

## State, real time and maintenance

Atomic adoption requests bridge recall/reset events to the audio callback. Native controls persist; phase/drift do not serialize. Bypass emits silence and freezes histories without control/CV reads. Resume/recall preserve phase, adopt controls and clear FIR histories. Silent filter paths flush their histories before skipping zero-history sums. Static tables are generated before rendering; callbacks allocate nothing and perform no I/O, GUI work or locks. Generated `super.Destroy()` remains once.

Released OSC embeds its own implementation. `HybridWaveforms48` in DSP primitives preserves the reusable waveform and integration helpers; the archived harness verifies exact parity. It does not own phase, CV interpolation or resampling. No useful retained test audio existed to migrate to TestBench; numerical fixtures and scripts are retained instead.

## Validation and change checklist

Both source forms compile against the installed SDK with Java 17 target and strict warnings. VMOD/source/metadata checks, 120 static output cases, eight modulation scenarios, zero/reverse behavior, finite recovery, mix/DC, bypass/resume and primitive parity pass. Cleanup comparison covers 240000 deterministic frames across four outputs with varied pitch/FM/shape CV, ranges, routing, drift, recall and bypass: output-bit checksum matches the approved baseline.

The user confirmed native operation and sound, then authorized canonization. Post-approval cleanup changes control identifiers/readability/tooltips and version/Notes metadata; it has automated exact DSP parity but no separate post-cleanup native-host audition. Native CPU and comprehensive save/reload stress were not separately reported. Headless timing is not a native CPU meter reading. See review for approval hashes and scoped exceptions.

Future edits must repeat pair/metadata/hash checks, both SDK builds, cleanup parity if applicable, exact primitive comparison, spectra and signed FM tests. Re-audition any change to phase integration, table generation/interpolation, bandwidth selection, control laws, drift, FIRs, output gain/DC or routing. Preserve UUIDs and immutable archives.
