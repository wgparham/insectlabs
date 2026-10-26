# SN93T 1.1.0 change record
Jack values in Voltage are volts (author's voltmeter: hot triangle 5.33 V, 3-wave mix 11.0 V; CAL 1.0 per octave; Cherry user guide gives CV levels in plain volts, 5 V gate, +-5 V pitch bend). Three outputs still used the old volts/5 scale:
- TONE output: TONE LEVEL 0 dB is now a 5 V peak (was 1 V peak). At the same knob position the tone is 14 dB louder.
- Cents CV: +-50 c is now +-5 V (was +-1 V).
- THRU unchanged (still passes the input exactly).
- Detection sensitivity unchanged: the detector keeps its internal x5 input scale (named DETECTOR_GAIN), so the no-signal threshold is the same 0.4 mV real that you tested. The "0.002 V RMS" in the notes is the internal figure.
- Pitch CV and CAL CV unchanged from v1.0.0 (already jack = volts).
Signal routing and voltage constants are the only DSP behavior changes; control geometry, panel layout, A4 steps (200) and analysis core are unchanged from v1.0.0. The Designer save also refreshed the module-level skin ID and TTW logo resource ID; the candidate was kept as supplied after the author verified it in Designer. Notes read v1.1.0.
Checks: wiring 52/52 with the tone and cents tests moved to the new scale (tone peak 5.0 at 0 dB, cents +2.0 at +20 c); core 216/216. The author confirmed the complete pair loads, compiles, runs and passes functional checks in Designer.
