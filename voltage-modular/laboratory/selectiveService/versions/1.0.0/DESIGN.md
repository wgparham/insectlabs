# Selective Service — canonical v1.0.0

Status: **Canonical v1.0.0**, approved by the user after successful build and testing.
The working pair is `development/selective_service.vmod` and `development/selective_service.java`.
Class/package identity and control UUIDs are preserved. The visible panel remains the supplied mockup;
the C jack is restored to the requested 22 x 22 size. Internal names are descriptive lowerCamelCase,
Display Names are human readable, and Designer Notes contain only `v1.0.0`.

## Agreed controls and startup

| Control | Behavior | Default |
|---|---|---|
| FREQUENCY | Logarithmic 20 Hz–18 kHz center frequency | 1 kHz |
| FINE | Absolute ±2 Hz, added after V/Oct transposition | 0 Hz |
| WIDTH | Logarithmic 2–100 Hz full −3 dB bandwidth | 2 Hz |
| SLOPE | Stepped 1–12 poles **per skirt** | 4 |
| GAIN | Clean input gain, −24 to +24 dB | 0 dB at noon |
| AMP | MAIN-only output gain, −24 to +36 dB | 0 dB at noon |
| POWER | 10 ms panel on/off transition | OFF |
| V/Oct | 1 V/oct relative to FREQUENCY | No cable: zero transposition |
| FM | Bipolar linear FM, 100 Hz/V | No cable: zero deviation |

The resulting center is `clamp(FREQUENCY × 2^(V/Oct) + FINE + 100 × FM, 20, 18000)`.
V/Oct and FM are simultaneous, unsmoothed, audio-rate inputs. Manual frequency, fine,
width and gain changes have 5 ms one-pole smoothing. Tooltip entry uses Hz or dB, even
where the underlying control uses a normalized position. Slope changes crossfade banks
over 10 ms; a newly selected very narrow filter still needs its natural settling time.

## Signal paths

- Powered MAIN: S → clean GAIN → bandpass → AMP → soft ceiling → power fade.
- Powered C: S → clean GAIN → matched band-reject → separate soft ceiling. AMP has no effect.
- POWER OFF: MAIN silent; C passes raw S through its ceiling. Input GAIN is inactive on this
  passive through path. C transitions between that path and the active notch over 10 ms.
- Host BYPASS: exact unity S → **both MAIN and C**, including hot signals. No control/CV reads,
  gain, filtering, ceilings, fades, or DSP-history advancement. This is the user's explicit
  exception to the usual Courtesy-output silence convention: an expensive splitter.
- Resume from host bypass: current controls are adopted immediately. Same-order filter state
  is retained; changing slope while bypassed clears the newly configured filter state. No stale
  control or power ramp is replayed. This can be abrupt, as requested for host bypass.

MAIN's ceiling is transparent through ±20 V, smoothly approaches ±24 V above that knee.
C is transparent through ±16 V and approaches ±20 V. These are native-rate soft safety
ceilings retained from the tested first pass, not an oversampled distortion stage. No noise,
drift, hum or intentional pre-filter saturation is added. Hot high-frequency signals
and very strong FM should be included in listening tests; assess whether the ceiling
needs the series' 2x processing before release.

SIG shows the post-AMP, post-ceiling MAIN level. OL indicates when MAIN's ceiling is engaged;
it can hold during sustained limiting. C does not drive either indicator. Both are dark when
the module is fully off or host-bypassed. Audio envelopes feed a 20 Hz GUI timer.

## Filter definition and implementation

The clean core uses matched Butterworth bandpass and bandstop transformations, with separate
signal histories and shared coefficients. The notch has a zero at the selected center. Outside
the notch, its settled response tends to unity. Band edges have finite transition skirts:
MAIN and C overlap through those transitions, rather than constituting an impossible ideal
brick-wall split. Their **squared magnitudes** are complementary before gains and ceilings;
their audio signals are not promised to sum to the dry input with unchanged phase.

SLOPE is the prototype order, and specifies the rolloff on each side. A band transformation
doubles total pole count: the default four-pole skirt uses eight poles per complete filter.
WIDTH remains the same measured −3 dB width at every slope. The response is logarithmically
symmetric in the prewarped domain; at very low center frequencies with wide bandwidths, the
two edges are not simply `center ± WIDTH/2`.

Each conjugate-pole pair is processed with a topology-preserving state-variable section.
The frequency mapping preserves the digital center and full bandwidth at the 48 kHz Voltage
Modular engine rate. Coefficients update every sample when modulation requires it and are cached
at static settings. Arrays and filter banks allocate at initialization, not in audio callbacks.
At full power-off the filter processing stops. Reset/preset notifications schedule state clearing
on the audio thread. Extremely narrow bands ring and settle slowly; strong rapid tuning can pump
stored filter energy into the ceilings. This behavior needs listening evaluation, not just limits.

The transform equations were checked against the primary
[SciPy bandpass transformation documentation](https://docs.scipy.org/doc/scipy/reference/generated/scipy.signal.lp2bp_zpk.html)
and [bandstop transformation documentation](https://docs.scipy.org/doc/scipy/reference/generated/scipy.signal.lp2bs_zpk.html).
Implementation is original Java, not copied Bogaudio source. Bogaudio VCF, the Berna selective
instruments, and vintage indicating amplifiers remain design references.

## Build and test handoff

Run `python tests/validate.py` from this module folder using a working Python interpreter, Java 17+
compiler and `C:/ProgramData/Voltage/voltage.jar`. The validator compiles both source forms, checks
pair agreement and defaults, and exercises the actual callbacks with a host stub. It measures
the filter with settled sine probes, including the 2 Hz width, all slope orders, range endpoints,
both band edges, center nulls, and out-of-band rejection. It also tests gain-path independence,
power timing, FM summation and clamps, tooltip entry, reset/resume and exact bypass.

The lightweight benchmark is a standalone JVM core measurement, not a Voltage Modular CPU-meter
reading. Native Designer UI, saved-patch behavior and listening remain the user's next checks.

Suggested first listening test:

1. Insert a fresh instance: OFF, 1 kHz, WIDTH minimum, SLOPE 4, GAIN/AMP at noon.
2. Patch a 1 kHz sine into S. OFF gives silence at MAIN and the tone at C. Power on and allow
   the narrow filter to settle: MAIN carries the tone, C rejects it.
3. Try noise or music, sweep FREQUENCY/WIDTH, then change SLOPE. AMP affects only MAIN;
   GAIN affects both active paths. Listen to the hot end of both output ceilings.
4. Patch V/Oct and FM together, including audio-rate signals. Compare musical modulation with
   extreme excursions and listen for objectionable bursts or long recovery.
5. Host bypass gives identical unprocessed S at both outputs regardless of every panel control.

The user tested and approved the expanded GAIN/AMP ranges, ceilings, labels and complete module behavior. FM remains 100 Hz/V. These behaviors are canonical. See REVIEW.md.
