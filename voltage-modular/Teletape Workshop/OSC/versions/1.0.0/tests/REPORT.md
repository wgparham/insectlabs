# OSC 1.0.0 - release measurements

2026-10-17. SDK, source-pair, metadata, exact DSP cleanup parity, reusable primitive parity, functional and spectral checks pass. The user approved native operation/sound and authorized promotion. No separate native audition of identifier/metadata cleanup or native CPU benchmark is claimed.

## Static spectra

Coherent 32768-sample FFT after 2048 warmup samples; sum of all non-DC/non-harmonic power relative to intended harmonic power. Drift and shape-to-pitch coupling disabled only to make the tones coherent. Actual voiced waveform functions and actual 4x/two-stage output decimators are exercised.

| Output | Worst non-harmonic power |
| --- | ---: |
| sine | -158.22 dB |
| triangle | -132.68 dB |
| pulse | -131.44 dB |

Eight coherent frequencies about 45 Hz-18 kHz, five shape positions and three outputs give 120 output cases. FM is zero. Intentional harmonics and DC are excluded from alias power. Static rejection matches the approved baseline.

## Modulated spectra

Actual 4x output versus a 32x evaluation of the same waveform model and signed linear-FM phase integral, filtered to host Nyquist with the actual two-stage FIR response retained in-band. Native CV samples have identical linear interpolation in both. Crop 8192 host samples at each end and apply a Hann window. Total reference-error power includes aliasing and modulation/interpolation differences; it is not a pure alias-power measurement.

| Carrier / modulator | Linear FM peak | Shape CV peak | Sine error | Triangle error | Pulse error |
| --- | ---: | ---: | ---: | ---: | ---: |
| 1000 / 200 Hz | +/-0.1 V | +/-0 V | -161.77 dB | -162.45 dB | -155.98 dB |
| 4000 / 2000 Hz | +/-0.5 V | +/-0 V | -136.22 dB | -112.42 dB | -102.89 dB |
| 1000 / 2000 Hz | +/-0 V | +/-2 V | -77.17 dB | -56.04 dB | -60.80 dB |
| 4000 / 3000 Hz | +/-0.25 V | +/-1 V | -73.48 dB | -68.32 dB | -66.68 dB |
| 8000 / 6000 Hz | +/-0.5 V | +/-2 V | -58.82 dB | -51.89 dB | -49.24 dB |
| 1000 / 200 Hz | +/-7.5 V | +/-0 V | -161.99 dB | -137.72 dB | -119.34 dB |
| 4000 / 2000 Hz | +/-7.5 V | +/-0 V | -117.86 dB | -78.34 dB | -57.79 dB |
| 4000 / 3000 Hz | +/-7.5 V | +/-1 V | -73.68 dB | -64.77 dB | -57.98 dB |

The last three scenarios cross zero. Worst through-zero total reference error is about -58 dB; the severe 8 kHz/6 kHz combined case reaches about -49 dB. These are total-error measurements, not pure alias power, THD or proof of arbitrary-modulation alias freedom. Reference and production share waveform functions. Hard-clipped level/shape CV and numerical overload beyond the matrix are not fully characterized.

Regression budgets: static -100 dB, first two FM cases -80 dB, fast-shape cases 2/3 -50 dB, severe case 4 -45 dB, through-zero cases -55 dB. All pass.

## CPU and function evidence

Warmed headless Java; controls/jacks are stubs. Results exclude native SDK calls, GUI, scheduling and table initialization. Eight instances are processed serially. These are processing-cost measurements, not native host CPU readings.

```text
PASS: endpoints, typed entry, bounds, DC, mixing, CV routes, invalid recovery, bypass/resume and drift; separate V/oct and through-zero linear FM
PASS: reusable waveform/helper exact parity
BENCH: eight voices, 5 simulated seconds each: 6.2861 wall seconds; 157.1523 ms/voice/audio-second; checksum 5007108.880
BENCH SINGLE WAVE: eight voices, 5 simulated seconds each: 4.2244 wall seconds; 105.6106 ms/voice/audio-second; checksum 1714308.880
```

The separate signed-trajectory test uses current-thread CPU time for forward-only and reversing modulation. It does not compare feature-disabled binaries:

```text
CPU FM peak 2.5 V (forward only): 150.391 ms/voice/audio-second; checksum 1988083.884
CPU FM peak 7.5 V (through zero): 147.461 ms/voice/audio-second; checksum 1971986.423
CPU FM peak 2.5 V (forward only): 150.391 ms/voice/audio-second; checksum 2030109.552
CPU FM peak 7.5 V (through zero): 166.016 ms/voice/audio-second; checksum 1994856.362
```

CPU varies across runs/trajectories; through-zero requires no additional oscillator or oversampling stage. Preserve measured limits instead of promising zero overhead. All-wave headless cost is around 157 ms/voice/audio-second; sine-only is around 106 ms in this release-recording run.

## Reproduction and integrity

Run tests/validate.py from this archive with bundled Python/NumPy and the installed JDK/SDK. Default runs write captures/results under ignored tmp/osc-checks and do not alter the release. The packaging-only --record-results option deliberately refreshes archived metrics before final hashing; do not use it on an immutable release.

The harness extracts actual canonical user regions. It verifies pair/UUID/geometry/defaults, source/embedded SDK builds, signed pitch/FM behavior, zero/reverse handling, finite recovery, DC, mix, CV routing, bypass/resume, exact primitive output and the approved cleanup checksum. tests/CLEANUP-PARITY.json records 240000 frames and 960000 output samples with exact approved-versus-release agreement.

Canonical Java SHA-256: `d61836dfa93982f14f2c07b1c39a622d4d7a0565804e6588509e08a91f0cff35`

See REVIEW.md for scoped native-host limitations and approval hashes.
