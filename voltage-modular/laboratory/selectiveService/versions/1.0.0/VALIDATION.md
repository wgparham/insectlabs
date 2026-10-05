# Selective Service v1.0.0 — validation

2026-10-05. **PASS: 768,209 DSP/callback assertions** and a **120,000-sample bit-exact cleanup comparison**.

Both exported Java and embedded Designer source compile with Java target 17, all warnings enabled
and treated as errors, against the installed Voltage Modular SDK. The Designer binary round-trips
and embedded/exported sources agree. Updated ranges/defaults, stepped slope and lifecycle calls pass.
Every supplied control property other than the two gain ranges and working Notes is preserved,
including positions, dimensions, skins, labels, UUIDs and switch defaults. Hero artwork is unchanged.

Measured tests cover filter center unity/notch null, full bandwidth, skirts, all slope orders and
frequency endpoints; exact bypass routing without control/CV reads; frozen bypass histories;
reset/resume; manual smoothing; 10 ms power transitions; simultaneous V/Oct/FM; tooltip conversions;
C independence from AMP; and the new gain/ceiling endpoints. An actual 1 kHz probe verifies the
combined +60 dB boost and −48 dB cut below the output knee.

The cleanup comparison starts from the user-updated Java with only the requested gain/headroom
changes applied. Cleanup is sample-identical for both outputs and both indicator envelopes over
120,000 samples with varying controls, slopes, power states and modulation. This demonstrates
cleanup parity, not parity with the previous lower-gain voicing.

Ordinary 1 V pitch / 5 V audio-rate FM remained controlled. The deliberate extreme stress test
(±100 V FM, pitch/slope/width changes, +24 dB input gain) remained finite and output-bounded at
±24/±20 V. Pre-ceiling resonant energy reached approximately 4.29 million in that pathological
test. Output limiting contains it; the model is not claimed to remain transparent under extreme
retuning. This is an existing high-Q modulation characteristic, not a cleanup change.

Standalone core benchmark, 10 seconds of audio in one warmed local JVM:

| Slope | Static tuning | Audio-rate FM |
|---|---:|---:|
| 4 | 45 ms | 228 ms |
| 12 | 100 ms | 575 ms |

These exclude wrapper/jack/UI overhead and are not native-host CPU readings. Slope crossfades
briefly run two banks. The nonlinear safety ceilings remain native-rate, preserving the previously
tested topology; no oversampling or unrelated coloration has been added.

The user completed the final Designer build/load, listening, and save/reload check and reported that all tests pass. The approved canonical source is archived with the final version marker.
