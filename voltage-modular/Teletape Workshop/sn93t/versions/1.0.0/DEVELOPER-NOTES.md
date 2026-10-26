# SN93T developer notes — 1.0.0

The canonical pair is `sn93t.java` / `sn93t.vmod`, class `sn93t`, package `com.insectlabs.sn93t`; the front-panel artwork is `sn93t_hero.png`. This snapshot's Designer Notes are `sn93t` / `v1.0.0`.

## Signal path and controls

The module runs a fixed 48 kHz detector path. Input is DC blocked and level tracked, then split into a 12 kHz coarse path and a 48 kHz fine path. McLeod NSDF pitch estimation finds periodicity, median stabilization is adjustable from 3 to 9 readings, and a 6-cent hysteresis stabilizes note changes. The detector has AUTO 30–2200 Hz, LOW 30–140 Hz, MID 70–700 Hz and HIGH 250–2200 Hz ranges. The no-signal threshold is 0.002 V RMS; the display retains the last pitch for 250 ms after signal loss. No smoothing beyond the user-selected median response is used.

The A4 reference is 380–480 Hz. The key control is used for root-anchored scales. The temperament control selects the built-in and user-loaded tunings; custom `.tun` and `.scl` contents are saved with patch state. Named 12-note temperaments pin A to the selected A4 reference. EDO, Bohlen–Pierce and SCL scales are root-anchored; `.tun` files use their absolute frequency data scaled against the 440 Hz reference. Keyboard-like `.tun` files display MIDI note names; other tables are numbered.

THRU is direct input routing and MUTE affects only that jack. HOLD freezes the stable reading and pitch outputs. The built-in tone generator follows the selected tuning or can be set manually, with an 8-page by 16-note manual table for numbered tunings. Pitch CV uses 1 jack unit per volt and a 1 V/octave slope; C4 = 0 V at A4 = 440 is a project convention. Cents CV uses ±1 jack unit for ±50 cents and audio/tone use internal units of volts divided by 5. CAL drives the external VCO over its step sequence while its signal is returned to IN.

## CAL behavior

A CAL sweep takes 2–9 octave spans, with per-step settling and averaging. The 1 V/oct CV convention was measured against a VCO during development: 1 jack unit equals 1 octave, and the measured example was −960.16 cents/octave with 0.05-cent maximum residual. The 1.0.0 core test emulates a VCO with a 65.41 Hz base and 1 V/octave input, checks the fitted slope and residual, and confirms that the seventh octave above a 65.41 Hz base is rejected by the 2200 Hz AUTO ceiling. Real VCO behavior varies by range, waveform, stability and input noise.

## Validation and limits

`tests/validate.py` builds the exported Java and VMOD-embedded source against the installed Voltage SDK with Java 17 target, all lint warnings treated as errors. It validates the Designer binary round-trip, source parity, module identity, Notes, control UUIDs, key jacks/buttons, protected line anchors and SHA-256 hashes. `tests/TnrCoreTest.java` tests pitch accuracy from 31 Hz to the 2200 Hz ceiling, near-ceiling boundary frequencies, harmonic-rich tones, response settings, HOLD, signal loss, tone output, invalid-input handling, Scala parsing, three custom tuning fixtures and the seven-step calibration sweep.

The core assumes 48 kHz. Alternate sample rates, native Designer reloading during this closeout, multi-voice polyphony, broad real-instrument tracking, detector CPU under a full patch and native host CPU have not been independently measured. The headless signal tests are deterministic and do not replace a user's instrument audition. Development decisions and superseded measurements are retained in [research history](../../research/Development-History.md) and [the original brief](../../research/Design-Brief-History.md).