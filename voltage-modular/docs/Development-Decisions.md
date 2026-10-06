# Development decisions and documentation clarifications

Reviewed against the supplied conversation and repository on 1 October 2026. This is a current decision record, not a new release or a claim of fresh host testing. Canonical indexes identify the authoritative source pairs; archived release evidence remains unchanged.

## Collection-wide decisions

- Platform is Voltage Modular. Colorbox supplies infrastructure conventions; the three following series develop their own sound and interface roles.
- Laboratory is mono-first and manual-first: WWI/WWII equipment repurposed in early electronic and German film studios. Multiple paths do not imply stereo. Functional modulation inputs are allowed; widespread CV control belongs to Radiophonic.
- Global +10 V modulation scaling was deferred. The default working reference remains +5 V, separately from audio amplitude, DC ranges, gates and pitch.
- Family resemblance does not require identical coloration. Fader|Distr A/B and sw1/sw2 deliberately differ. Faders have more audible weight than typical Laboratory utilities, below Colorbox intensity. Do not impose one generic aging stage on all modules.
- Host bypass retains the Colorbox low-work contract. Physical power declicking is a separate feature; Generator's approved power fade does not change hard host bypass.
- Internal/Variable Names use established code standards. Display Names are human readable. Notes and Display Names may be edited unless the user expressly preserves wording; Notes carry the exact version, with rc for candidates.
- Courtesy means available whenever on and not bypassed. Function-start buttons do not turn the instrument off. Courtesy can hold a voltage at zero frequency and follows its documented waveform controls, independent of the main amplifier.
- TestBench is a top-level general audio project. Add reusable fixtures with provenance, catalog and checksums; real recordings are preferred where available and synthesized substitutes must be labeled. The user's original music source is already archived elsewhere.

## Decisions that supersede early drafts

| Instrument | Current decision |
| --- | --- |
| SIGPROC | Both stages start in PROC; GAIN is -3 to +3. Negative gain is cleaner than the deliberately driven positive extreme. No offset CV. VCA has its distinct rounded/vactrol response. |
| Fader&#124;Distr | Replaces the stereo-oriented Panfade framing. A is linear, B equal-power; simultaneous fade/distribution, manual BIAS, no CV or law selector. |
| sw1/sw2 | CLK is engaged down; default up/direct. User-approved filtering and short transitions are pragmatic declicking, not a component-exact Moog reconstruction. sw2 starts at 0/OFF. |
| Generator | SIN/variable TRI; internal FM positions Off / Int/4 / Int / Ext. Internal modulator spans 0.05-50 Hz. Frequency, duty and amplitude smoothing and the 8 ms power fade are approved. |
| Function | Independent, unsynchronized sine/square boards. Display shows actual output frequency, not the original pre-multiplier value; upper ranges use full knob travel. Default C4 at X10. |
| SIN/RND | SIN default, C4, 1 V initial range, fine +/-200 cents; MOD LEVEL +/-2. Patched FLT is an audio-filter path with optional white noise blended before filtering. RND/RD2 ignore MOD IN. |
| Deep Tone | Stepped WHOLE, continuous FRACTION; OFFSET +/-65 Hz retains fifth-power taper. Swell follows absolute OFFSET, with 200% depth. Courtesy remains independent and less smoothed than Swell. |
| n01 | No PINK output; approved darker WHITE/SPECTRA. SOURCE is a continuous ramp with slow, gently knob-coupled timing; STEPPED samples it on external/manual events only. |

## Noise and random-source distinctions

n01's RATE describes continuous random-motion bandwidth, not a clock for STEPPED. Its SOURCE rate is bounded at 20-120 Hz; RATE, RED and BLUE influence mean speed and LEVEL influences timing variation. The ramp amplitude remains fixed. A held value changes only at a capture event. These distinctions must survive future tooltip or manual rewrites.

n01 uses a different random core/amplitude distribution from SIN/RND, plus deliberate bandwidth voicing. Changing a PRNG alone does not guarantee that listeners can distinguish two white-noise sources; the approved spectral treatment is part of the audible identity. A broad distribution of sampled voltages is not the same property as a 1/f spectrum. WHITE here is a musical broadband source, not a precision flat calibration signal.

The EMS random-voltage reference is retained for later Radiophonic exploration. Dual random channels and variable event timing are not pending fixes to the completed n01.

## Source and historical-document clarifications

- SIN/RND's approved internal random FM uses a moderated exponential excursion, while external FM in SIN/MOD is linear. Earlier requests to return to linear FM preceded the final approved moderation. Its two-stage voiced filter includes a residual/subtraction path; do not relabel it a conventional low-pass without response analysis. This records existing code, not a DSP change.
- SIGPROC's historical 1.0.0 README has a damaged multiplication glyph. The PROC gain expression is `GAIN + EXT LVL * (EXT INPUT / 5)`, subject to the documented limits/character path. Version 1.0.1 preserves the DSP. Early mockup defaults and names are superseded.
- Older archive build commands may place classes inside the checkout. Current policy is `C:/InsectLabs-Build/<module>`; follow current setup/tool documentation. Archives remain intact for provenance.
- Colorbox's dated validation report records automated comparisons and baseline audition, with native testing of that cleanup not separately recorded there. Canonical designation is not new evidence of a host test.
- Arithmetic inspection supports lightweight design, not a measured CPU percentage. Midpoint 2x conditioning is not automatically a complete anti-aliasing oversampler. A fundamental below Nyquist does not guarantee alias-free nonlinear harmonics.

## Audit boundary

This refresh updates living Markdown guides, module indexes and historical-reference labels. Versioned sources, projects, images, hashes and release reviews remain immutable. The repository audit checks tracked links, canonical targets, hashes and source agreement; it does not replace listening or native Designer checks. Proposals are explicitly separated from accepted plans. No new panel, DSP behavior or module release is approved by this documentation pass.

## Following — canonical 1.0.0

The Dynamic Modulator role is realized as Following. SIGNAL THROUGH is tapped after shared gain and coloration; it is not a raw active splitter. FILTER is detector-only, 20–200 Hz. DELAY shifts the entire envelope rather than extending release. AUTO/MANUAL share a two-stage program-dependent release. BALANCE affects the positive/inverted envelope only; both gate outputs and delayed courtesy remain independent. Gate LEDs track actual levels without pulse stretching. Final defaults are OFF, AUTO, unity amplitude, minimum filter/attack/delay, immediate balance and 5 V threshold. Host bypass alone provides raw audio through; CV is silent.
