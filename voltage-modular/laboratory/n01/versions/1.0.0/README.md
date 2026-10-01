# n01 — Noise Source

**Canonical v1.0.0, approved 2026-10-01.** The user approved the darker voice and slower coupled S&H SOURCE, and authorized cleanup followed by canonization and push if checks passed. Matching source pair, supplied final panel, UUIDs and defaults are retained. See [release review](REVIEW.md).

## Panel behavior

| Control / output | Released behavior |
| --- | --- |
| WHITE | Always-running broadband noise, independent of every knob. Fixed output, about 1.61 V RMS in the deterministic check, bounded by +/-4.9 V. |
| RED / BLUE | Independently mix a warm low-frequency component and a brighter broadband component into SPECTRA. Both zero gives silence after smoothing. RED also gently slows S&H SOURCE; BLUE gently speeds it. No separate PINK output; the spectral balance is user-approved. |
| SPECTRA | Colored mixture with gentle symmetric compression. Approximate soft ceiling +/-11.11 V; ordinary noise levels are much lower. No automatic loudness matching while changing colors. |
| RATE | 0.05-20 Hz effective two-pole low-pass bandwidth, clockwise faster; midpoint 1 Hz. This describes random-motion bandwidth, not a periodic clock. RATE also gently speeds S&H SOURCE as it rises. |
| LEVEL | SLOW RANDOM amplitude, 0-100%; also slightly increases S&H SOURCE cycle-to-cycle speed variation. At maximum its output is bounded by +/-5 V; stochastic values do not routinely reach these rails. |
| SLOW RANDOM | Continuous, bandwidth-compensated filtered SPECTRA. RED/BLUE influence its motion and strength. Startup settles from zero. Positive/negative lamps show its polarity and magnitude. |
| S&H SOURCE | Continuous rising ramp, nominal +/-5 V, with a cycle rate bounded between 20 and 120 Hz. RATE/RED/BLUE gently affect mean speed; LEVEL affects timing variation. Knob changes do not reset phase. No external trigger is needed. This is a source to sample, not an internally clocked held output. |
| STEPPED | Samples the current S&H SOURCE on a trigger or manual press, holds exactly until another event. Initializes at 0 V. Knobs influence future samples through SOURCE timing; an already held voltage is unaffected. |
| TRIGGER input | Schmitt rising-edge detector: fires at +1 V, rearms at +0.1 V or below. Holding a gate high does not repeat. Unpatched input is zero. |
| TRIGGER button | One sample per press, including with an external trigger patched. Button release does not sample. Simultaneous events within one sample produce one capture. |

There is no internal clock for STEPPED. RATE primarily controls SLOW RANDOM, with a smaller influence on S&H SOURCE timing. There is no separate module power switch; all continuous sources run whenever the module is active. Save/reload and variations preserve the held STEPPED voltage; the continuously evolving noise and ramp sequences are not serialized. Reset requests a held value of zero.

## Voice and implementation

The WHITE core uses SplitMix64 output words whose two signed halves are combined into a triangular amplitude distribution. This differs from SIN/RND's uniform xorshift32 core. A mild fixed high-frequency attenuation provides a deliberate audible distinction: 65% direct noise plus 35% from a 4.2 kHz one-pole path, with a small level adjustment to retain similar RMS. This darkens WHITE gently. Independent instances receive distinct seeds. The output is statistically centered, not guaranteed to have exactly zero average over every short recording. It is broad white-like noise, not a precision flat-spectrum calibration output or a transistor-level avalanche model.

SPECTRA combines weighted one-pole low-pass branches at 70/250/1000/3200 Hz with increased low-mid weighting and a brighter component derived from the filtered noise rather than raw noise, then uses inexpensive rational soft compression. SLOW RANDOM filters the resulting signal through two poles with rate-dependent level compensation and a bounded output transfer. Manual controls smooth over roughly 10 ms. Coefficient exponentials and the normalization square root are recalculated only when RATE changes.

The noise-modulated ramp is a functional Serge-inspired source. Its own random stream remains separate from the audio noise core, while control positions couple its speed to the rest of the panel. Mean speed is 60 + 24*(RATE-0.5) + 8*(BLUE-RED) Hz. Each cycle has a signed random timing factor whose depth spans 30-50% with LEVEL. The resulting instantaneous speed is limited to 20-120 Hz; all knob influences use the existing 10 ms smoothing. LEVEL changes the variation around the nominal mean, so elapsed-time average speed can also shift slightly. The output voltage span stays +/-5 V. Broad voltage coverage is a distribution property; it does not imply a 1/f spectrum. It retains its hard reset and is intended primarily for sampling; if heard as audio, its rough high-frequency edges can alias. No elaborate bandlimiting or oversampling is added for this control source. Noise voicing also runs natively at 48 kHz: no periodic carrier harmonics need a dedicated oversampling chain here, though this is not an alias-free circuit simulation. No per-sample allocation or UI formatting occurs. Lamps update at 40 Hz. Runtime is lightweight by construction; no native-host CPU benchmark or listening comparison is claimed.

Host bypass writes zero to all five outputs, skips controls/trigger reads and freezes signal histories. Resume adopts current controls, resumes source/filter histories and restores the held output. Manual presses during bypass are discarded; an already-high trigger on resume does not cause a new capture until it falls and rises again. Host bypass is intentionally not declicked.

## Validation and listening

Run `python tests/validate.py`. Compiler output goes to `C:/InsectLabs-Build/n01/checks`, outside Dropbox. Both embedded/exported sources compile against the Voltage SDK targeting Java 17 with warnings as errors. Tests exercise the actual callbacks through mock controls: source parity, metadata/defaults, manual/external triggering, held voltage, hysteresis, independence, output bounds/statistics, ramp coverage, rate response, save/restore, bypass/resume, typed edits and lamps. The user confirmed the preceding build works and approved its sound and SOURCE behavior. Cleanup is checked sample-for-sample against those approved callbacks; native-host testing of the final formatting-only change has not been separately claimed.

Voice and timing are locked. Compared with the first pass, level-normalized first-difference energy decreases for both WHITE and SPECTRA; the automated check verifies spectral darkening rather than merely lower volume. This metric is not a perceptual brightness score. Added per-sample work is one smoothing update, basic source-rate arithmetic and a clamp; the existing noise filters are retuned without adding filter stages. No host CPU benchmark is claimed.

## Design references

- [CGS597](https://www.elby-designs.com/webtek/cgs/serge/cgs597/cgs597.htm) and [CGS97](https://www.elby-designs.com/webtek/cgs/serge/cgs597/cgs97/cgs97_noise.html): noise and shaped sampling-source architecture.
- [ES05](https://www.elby-designs.com/webtek/euro-serge/es05-noise-source/es05.htm): compact source/manual-trigger arrangement.
- [Doepfer A-118](https://doepfer.de/a100_man/a118_man.pdf): red/blue mixing and continuous filtered random voltage.
- [EMW Noise Station](https://www.electronicmusicworks.com/eurorack/noise-station.html): distinct noise voices.
- [EMS RVG discussion](https://amsynths.co.uk/2026/08/11/ems-random-voltage-generator/): later Radiophonic inspiration; dual random channels and clock variance are reserved for the Radiophonic direction.

These are design references, not claims of circuit reproduction. The canonical pair is archived here under `versions/1.0.0`; future changes belong in a new development candidate.
