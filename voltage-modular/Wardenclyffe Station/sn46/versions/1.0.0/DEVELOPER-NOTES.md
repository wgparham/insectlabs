# sn46 — Developer notes

Canonical v1.0.0. Public identity: sn46 - standards. Java identity: class `standards`, package `com.insectlabs.standards`; preserve this identity when uploading. The disk export is `standards.java`; validation copies it to `standards.java` because javac requires the public class name as its filename. Category: Utility. Manufacturer metadata: Insect Laboratories.

## Signal flow and implementation

At 48 kHz, ProcessSample reads and bounds Voltage (0–12) and Fine Voltage (0–1), adds them, and publishes opposite polarities. It writes four fixed DC outputs independently. Four sine phase accumulators generate ±5 V peak A, lower F Sharp, 1 Hz and 100 Hz. A follows a 5 ms one-pole smoothing coefficient toward 440 or 432 Hz; F Sharp uses `2^(-3/12)` times the smoothed A frequency. The lower pitch is explicitly confirmed by the user.

Pulses uses a wrapping 12-bit sample counter: +5 V only at count zero, otherwise 0 V. The interval is 4096 host samples. Pink noise uses xorshift64 and six recursive filter states plus a delayed seventh term; the existing coefficients and scale are preserved. It is uncalibrated, and no claim of precise spectral/RMS accuracy is made. The unused previousPink field was removed without changing signal calculations.

ProcessBypassedSample zeros all twelve outputs, updates the bypass flag, and leaves histories and control reads untouched. All processing is native rate, with no oversampling or intended nonlinear audio stage. There are no per-sample object allocations. Four sine evaluations and the existing noise filter dominate the simple source workload; native-host CPU was not measured.

## GUI, controls and persistence

The 25 ms GUI timer publishes rounded voltage hundredths to VoltageDigitalCounter. A static EnumSet of segment flags overrides leading digits as zeros only when their leading status changes. The decimal LED is permanently lit. Display rounding never alters the DC output. GUI and formatting work stays outside ProcessSample.

The 432 button uses a native momentary control with AutoRepeat disabled and an explicit volatile mode latch. Each press toggles once and creates an undo node; release is not required for rearming. `UpdateGUIValue` renders the latch without changing the control's logical value or notifying an action, and the GUI timer reconciles it. This also supports rapid presses and reset/undo/recall. Follow the documented regular-button lessons rather than adding a release-dependent down latch.

Custom state is exactly five bytes: ASCII SN46 followed by 0 (A440) or 1 (A432). Both patches and variations use it. Bad signatures, lengths and flags are ignored without state replacement. Only this mode is serialized; host controls use normal host persistence. Reset restores mode false and does not reset oscillator/noise phases. Destroy calls the base lifecycle once and stops the GUI timer once.

Control Variable/Internal names were made descriptive lowerCamelCase, with UUIDs and layout preserved. Display Names are human readable. Corrected outputPulsws to outputPulses. Tooltips describe voltage units, fine scaling, lower F Sharp, output peaks, impulse timing and noise limits. The fine knob no longer displays a percentage. Module Notes identify v1.0.0; working notes are retained in WORKING-NOTES.md.

## Review and maintenance

Both Java source forms compile against the installed SDK with Java 17 compatibility, strict lint and warnings as errors. The headless tests cover bounds, polarity, DC constants, display rounding/leading digits, measured A/pulse timing, F Sharp ratio, noise finiteness, button/undo/reset/custom state, rapid presses, bypass freeze and lifecycle shutdown. Metadata validation checks synchronization, Designer binary round-trip, control/display identity, region anchors and preserved art/layout. The audio and bypass callback bodies were compared with the supplied source after identifier substitution; their calculations are unchanged.

The supplied pair was already built/run by the user. The user approved canonization on 2026-10-11 and supplied the refreshed sn46 hero image. Native saved-patch recall was not separately reported; its automated checks pass. No test audio was rendered by this review. Retain the tests as reusable reference-source and GUI-counter validation examples.
