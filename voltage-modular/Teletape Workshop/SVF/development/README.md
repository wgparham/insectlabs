# SVF development — working pair for 1.0.0

The matching working pair is `svf.java` and `svf.vmod`; the approved panel image is `svf_hero.png`. These bytes match [the canonical release](../versions/1.0.0/README.md). Retain this directory as the editable Designer source location.

- [DSP derivation, control mapping and explicit modeling choices](DSP-DESIGN.md)
- [Validation results](VALIDATION.txt)
- [Repeatable checks](tests/validate.py)
- [Working-file hashes](SHA256.json)
- [Shared Synthi filter evidence](../../references/Synthi-100-Filters/INITIAL-RESEARCH.md)

Frequency knobs span 5–15000 Hz, with fully clockwise at 15000 Hz. Turn them down to hear low-frequency material. Input knobs are 0–200% (50% is unity); CV amount and response are 0–100%. Full CV amount is 0.5 V/octave. Response approaches oscillation near the top of its range.

Mode switch: up SERIES, middle independent, down PARALLEL. In SERIES, the second core receives the selected HIGH/BAND/LOW output from core 1; the route switch is up HIGH, middle BAND, down LOW. A cable at IN 2 overrides that internal feed. All six taps remain live in every mode.
