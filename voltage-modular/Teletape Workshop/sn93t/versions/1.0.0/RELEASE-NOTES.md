# SN93T 1.0.0 release notes

First canonical release, promoted from the v0.1.10 release candidate. The release preserves the tuned panel, pitch/CV behavior and CAL outlier handling. The correlation-peak threshold was adjusted from 0.90 to 0.80 after synthetic boundary tests revealed octave-low readings from approximately 2.15 kHz through the 2.2 kHz detector ceiling at the original setting. The lower threshold passed 17 boundary points, harmonic-rich 440 Hz inputs, calibration and signal-loss checks.

This is an instrument-inspired tuner implementation, not a hardware clone. The 48 kHz detector pipeline, displayed temperaments and calibration conventions are software choices documented in the developer notes.