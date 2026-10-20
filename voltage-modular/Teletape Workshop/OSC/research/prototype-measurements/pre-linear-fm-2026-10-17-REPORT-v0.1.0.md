# OSC v0.1.0rc - audition measurements

Date: 2026-10-17. SDK and headless checks passed. Native Designer runtime, listening approval and native CPU measurement remain pending. This is not a canon/release sign-off.

## Working build

- Fixed 4x synthesis; band-limited waveform banks; 31-tap then 159-tap FIR decimation.
- Both exported and embedded Java compile against the installed Voltage Modular SDK, Java 17 target, strict warnings.
- Lossless VMOD encoding, embedded/exported source agreement, unique/preserved UUIDs, panel geometry/skins, C4 default metadata and SHA-256 manifest pass.
- Functional checks cover ranges, typed entry, output bounds, DC, exact linear mix, CV destination routing, nonfinite recovery, bounded drift and frozen/silent bypass with control adoption on resume.

## Static aliasing

Coherent 32768-sample FFT after 2048 warmup samples; sum of all non-DC/non-harmonic power relative to intended harmonic power. Drift and shape-to-pitch coupling disabled only to make the tones coherent. Actual voiced waveform functions and actual 4x/two-stage output decimators are exercised.

| Output | Worst measured non-harmonic energy |
| --- | ---: |
| sine | -158.22 dB |
| triangle | -132.68 dB |
| pulse | -131.44 dB |

120 output cases: eight coherent pitches (about 45.4 Hz to 18 kHz), five shared shape positions (-1, -0.5, 0, +0.5, +1), three outputs. Sine harmonic residuals, pulse DC and triangle/ramp harmonics are intentional and excluded from alias power. The extreme pulse positions are 1%/99%. No window is required for these coherent captures. The static regression target is -100 dB.

## Modulated comparison

Actual 4x output versus a 32x evaluation of the same waveform model and exact exponential-FM phase integral, filtered to host Nyquist with the actual two-stage FIR response retained in-band. Native CV samples have identical linear interpolation in both. Crop 8192 host samples at each end and apply a Hann window. Total reference-error power includes aliasing and modulation/interpolation differences; it is not a pure alias-power measurement.

| Carrier / modulation | FM depth | Shape CV peak | Sine error | Triangle error | Pulse error |
| --- | ---: | ---: | ---: | ---: | ---: |
| 1000 / 200 Hz | +/-0.1 oct | +/-0 V | -161.64 dB | -160.68 dB | -146.44 dB |
| 4000 / 2000 Hz | +/-0.5 oct | +/-0 V | -122.03 dB | -104.30 dB | -94.78 dB |
| 1000 / 2000 Hz | +/-0 oct | +/-2 V | -77.17 dB | -56.04 dB | -60.80 dB |
| 4000 / 3000 Hz | +/-0.25 oct | +/-1 V | -73.36 dB | -67.34 dB | -63.96 dB |
| 8000 / 6000 Hz | +/-0.5 oct | +/-2 V | -58.85 dB | -51.88 dB | -49.23 dB |

These figures describe total error, not THD and not pure alias power. Deep FM remains below -94 dB in this matrix. Fast shape modulation is more demanding, with approximately -56 dB worst error in the 1 kHz carrier / 2 kHz shape case. The deliberately severe 8 kHz carrier / 6 kHz combined case reaches approximately -49 dB. Do not present this as universally alias-free operation.

The regression budgets are -80 dB for the two FM cases, -50 dB for the two fast-shape/combined cases, and -45 dB for the extreme combined case. These are candidate test budgets, not historical specifications or a substitute for listening. Output level CV is functionally checked, but arbitrary audio-rate level clipping and shape CV beyond this matrix are not spectrally characterized.

## CPU and comparisons

Warmed, serial headless Java benchmark on this machine. Eight instances, five simulated audio seconds per instance; controls and jacks are stubs. This excludes SDK/native calls, GUI, host scheduling and table initialization. Wall time is an estimate of processing cost, not a native CPU meter reading.

```text
PASS: endpoints, typed entry, bounds, DC, mixing, CV routes, invalid recovery, bypass/resume and drift
BENCH: eight voices, 5 simulated seconds each: 5.8576 wall seconds; 146.4403 ms/voice/audio-second; checksum 5007108.880
BENCH SINGLE WAVE: eight voices, 5 simulated seconds each: 3.8551 wall seconds; 96.3781 ms/voice/audio-second; checksum 1714308.880
```

The final run is approximately 146 ms per voice per audio second with all three waves active (about 14.6% of one core in this headless wall-time comparison), and 96 ms with only sine active (about 9.6%). The silent paths retain history flushing but skip needless zero-history filter sums. Native CPU may differ.

The measured 2x prototype (historical reference: `../../comparisons/2x-2026-10-17/`) is retained. It took about 87 ms per voice/audio-second with all waves active, but its fast-shape and extreme-combined errors were worse. The 4x candidate also includes smoother bandwidth interpolation; the comparison is of complete candidates, not an isolated claim about oversampling factor alone.

The untouched supplied mockup is retained separately under comparisons. Neither comparison is canon.

## Reproduce

Run `development/tests/validate.py` with the bundled Python (NumPy required) from the repository. It compiles both sources, extracts current Java user regions into a Java harness, writes temporary captures under `tmp/osc-checks`, and regenerates `results.json`. It requires the installed JDK and Voltage SDK paths listed in the script.

Java SHA-256: `a661d18b272e0837282a5c726193c34423d5bf6b1aadfadcdebc6e599b29116c`

The waveform evaluator is shared between production and reference; this tests sampling/rate behavior, not independent correctness against an EMS recording. No measured hardware oscillator has been used to calibrate the character amounts.
