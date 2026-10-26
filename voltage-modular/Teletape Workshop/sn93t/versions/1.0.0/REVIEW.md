# SN93T 1.0.0 release review

## Scope

First canonical archive, promoted from the author's v0.1.10 release candidate. The front panel and core architecture remain as auditioned. One narrow detector correction was made after an expanded boundary test found octave-low readings above about 2.13 kHz. The McLeod peak threshold changed from 0.90 to 0.80; the change restores the first valid period peak and was rechecked against harmonic-rich tones, the entire declared upper range and calibration. No control mapping or panel layout was changed.

## Evidence and checks

- **PASS** — exported Java and VMOD-embedded Java compile against the installed Voltage SDK with `--release 17 -Xlint:all -Werror`.
- **PASS** — Designer binary parses and round-trips byte-for-byte; embedded/exported source is equivalent; class/package/Notes are canonical; all 103 control UUIDs are unique; required audio/CV jacks and five buttons are present; all 2,205 Designer protected-line anchors are sorted and within the embedded source.
- **PASS** — synthetic sine tracking at 31, 35, 65.41, 110, 220, 440, 880, 1,760, 2,100, 2,125, 2,150, 2,175, 2,180, 2,190, 2,195, 2,199 and 2,200 Hz. Maximum measured error in this set was under 0.1 cent after the threshold adjustment.
- **PASS** — four 440 Hz harmonic mixtures including a stronger second harmonic; pitch stayed within 0.01 cent in the measured runs.
- **PASS** — silence rejection, valid-signal acquisition, HOLD behavior, signal-loss timeout, fine response setting, 5 V peak tone endpoint, 440 Hz tone frequency and finite output under NaN/Infinity inputs.
- **PASS** — all three archived `.tun` files parse into 128-entry tuning maps; a valid five-note Scala `.scl` parses.
- **PASS** — seven-step synthetic VCO CAL sweep measured 0.999996 octave/volt, 0.012-cent residual and retained 6 of 7 measurements, correctly excluding the out-of-range step.
- **PASS** — the author confirmed the final canonical pair loads, compiles and runs in Voltage Module Designer on 2026-10-25. The earlier native-host gap is closed; a real-instrument listening check was not reported in this closeout.
- **LIMITATION** — synthetic tests do not establish broad real-instrument tracking, alternate sample-rate behavior or native Voltage CPU use. CAL depends on signal quality, oscillator stability and detector range.

The user authorized canonization if the checks passed. Final pair and hero hashes are recorded in `SHA256.json`. Cherry Audio publication remains deferred under the collection plan.