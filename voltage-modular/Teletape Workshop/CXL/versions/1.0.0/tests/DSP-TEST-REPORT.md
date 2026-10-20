# CXL headless DSP test report

Run: 2026-10-18, JDK 27 on Windows, extracted DSP classes compiled with `-Xlint:all -Werror`; 48 kHz native-rate model. The repeatable runner is [validate_dsp.py](validate_dsp.py), and machine-readable results are [DSP-RESULTS.json](DSP-RESULTS.json). It reads the current `cxl.java` DSP classes and leaves the Designer working pair untouched.

## Results

- Delay line integer tap timing passed. Pre-delay at zero, one sample, half a sample, and the 250 ms endpoint (12,000 samples) passed exact timing/interpolation checks.
- A fixed all-pass stage preserved unit impulse energy to within `1e-8` over 12,000 samples.
- Tank state remained finite and bounded for 4 seconds at six combinations spanning 0.8-30 s and both damping endpoints.
- The 500 Hz, 2-second drive/release fit measured these tank decay times at DAMP 50%:

| Configured duration | Measured duration | Difference |
| ---: | ---: | ---: |
| 0.8 s | 0.467 s | -42% |
| 3 s | 3.055 s | +1.8% |
| 5 s | 4.695 s | -6.1% |
| 30 s | 35.078 s | +16.9% |

Measurements regress the raw tank's stereo RMS from -5 to -24 dB, with 10 ms windows. The short setting crosses the fit range quickly; the 30 s setting only has a 12 s captured release. These are a repeatable tonal decay check, not a full-band standardized RT60 measurement. The short endpoint is a meaningful discrepancy to compare with the native audition and likely tune if its response feels too abrupt.

- At a constant 0.25 input, the relative 12-bit converter produced 0.000122 RMS error and the 8-bit converter 0.001969 RMS error. Mean errors stayed below 0.000002; a separate check confirmed exact digital zero for silence.
- To address the listening report, a 1 kHz / 0.25-level tone was run through two converter stages and the modeled 6.8 kHz pickup low-pass. Full 8-bit dither measured 0.000824 RMS error; the prior softened 9-bit effective step measured 0.000407 (about 6.1 dB lower). The new 8.5-bit midpoint, using a 180-level divisor, measures 0.000585: about 3.0 dB less error than full 8-bit, but about 3.1 dB more grain than the 9-bit setting. That puts it between the two audition endpoints numerically. Keeping the input at 8-bit and raising only the output to 12-bit measured 0.000591 (about 2.9 dB lower). Removing output dither raised the strongest harmonic spur from -77.8 to -70.0 dBc. First-order noise shaping reduced error below 6 kHz but raised the total post-pickup error slightly (0.000849), so it was not selected. The current audition spin uses the 8.5-bit effective step only in the extended-time mode; the 12-bit mode and decay ranges are unchanged. This is a focused converter cascade test, not a full plate performance measurement.
- Java headless `PlateTank.process` benchmarks varied from 326 to 457 ns/sample; the archived run measured 439.5 ns/sample (2.11%), or roughly 1.7-2.2% of one desktop CPU core at 48 kHz. This excludes the outer `ProcessSample`, Designer host, and other modules; it is a rough local benchmark, not a host CPU guarantee.
- A driven nonlinear-exciter comparison against the same equations at 8x sample rate, low-pass filtered at 20.5 kHz and decimated, measured native/reference RMS error of -30.4 dB at 1 kHz, -13.6 dB at 5 kHz, -8.4 dB at 9 kHz, -6.0 dB at 12 kHz, and -3.1 dB at 17 kHz (input amplitude 2x). This is an alias proxy that also captures differences from retuning the discrete-time filters and envelope at each rate. It flags increasing top-octave sensitivity; it does not isolate alias energy or establish an oversampling requirement. The converter's intended grain is separate from this driver comparison.

## Limits

This suite exercises the current tank, all-pass, delay, converter, and exciter classes directly. It does not emulate Designer's audio callback or establish native module latency, full patch routing, spectral noise floor, or certified RT60 across frequency. The user confirmed that the 8.5-bit audition and current behavior work in Designer on 2026-10-18. Detailed independent measurements of automation/undo, saved-patch migration, full-band decay, native host CPU and isolated alias energy are not available. The native audition approved the tonal/behavioral design; headless metrics remain focused engineering checks rather than a substitute for a standardized lab test.
