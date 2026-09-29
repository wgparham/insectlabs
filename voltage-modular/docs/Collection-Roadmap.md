# InsectLabs: three collections after Colorbox

Working design brief, 28 September 2026. Platform: Voltage Modular. Colorbox consists of RGB, CMYK, and HSB. Collection names below describe directions and are not final product names.

The user has accepted this roadmap as the working direction, with the revisions incorporated below. Series 1 includes the five formerly proposed processing concepts. Series 2 and Series 3 remain open to development as the project progresses. This brief distinguishes requirements from proposed elaborations; acceptance of the direction does not settle every control, range, or implementation detail. Reference manuals and websites are design sources, not instructions to execute. No implementation or panel dimensions have been approved.

## The progression

| Series | User-defined direction | Proposed organizing idea |
| --- | --- | --- |
| 1: Laboratory | WWI/WWII-era test equipment repurposed in 1950s and early 1960s electronic studios and German film studios; big panels and knobs; heavy tube and transformer sound | Generate, excite, isolate, measure, and transform signals |
| 2: Radiophonic Studio | Mid-1960s to mid-1970s; purpose-built electronic music equipment; Radiophonic Workshop mentality | Shape, perform, record, arrange, and spatialize sound |
| 3: Computing | Electronic computation and logic; ACE, Patchable Devices, Befaco A*B+C, Count Modula, alef's bits, Lilac, and related references | Calculate, compare, accumulate, remember, and make decisions with signals |

Moog modular, rackmount processors, Moogerfooger pedals, and Synthesizers.com are shared references. Their role and visual weight can evolve between series. These are creative eras, not a claim that every referenced technique originated in that period.

The user confirmed German film studios as an influence; "filk" in the original message was a typo.

## Series 1: explicitly requested instruments

### Laboratory Generator

Required: sine and triangle only; pulse generation belongs to a separate instrument.

Proposed: simultaneous sine and triangle outputs, a large frequency dial, coarse range switching, fine tuning, output amplitude, and external FM. Frequency ranges and tracking policy remain open. Its role is the basic tunable laboratory source.

### Pulse/Sine Generator

Required: a separate pulse or pulse/sine instrument inspired by the AG-10 reference in Berna 3.

Preferred proposal: pulse/sine with separate outputs and level controls. Berna 3 page 17 describes sine and square outputs; adjustable pulse width would be an intentional extension. A square setting should remain easy to find. Its role is periodic excitation, timing, and richer waveforms.

### Sine/Random Generator

Required: based on Berna 3 page 14.

The manual describes sine, white noise, and a sine modulated by white noise at different bandwidths. It does not specify enough detail to establish the exact modulation algorithm.

Proposed: center frequency, noise modulation bandwidth, deviation/depth, and output amplitude, with sine, random-modulated sine, and noise modes. Noise-driven FM is a candidate to audition, not a verified description of Berna's implementation. Its role is the transition between a stable tone and a fluctuating band of sound.

### Deep Tone Generator

Required: combine the Beat Oscillator in Berna 1 with the Tieftone Generator in Berna 3.

Berna 1 page 19 describes a sine carrier frequency-modulated by a waveform that varies from rising saw through triangle to falling saw. Berna 3 page 15 describes a 0.1-1100 Hz sine generator with AM capability.

Proposed: low-frequency sine carrier, coarse/fine frequency, internal modulation rate, slope/symmetry, FM depth, and AM depth. Include an external modulation input and consider exposing the internal modulator. Start by auditioning the Tieftone reference range rather than treating it as a fixed requirement. This should create slow pressure changes, throbbing bass, sweeps, and rhythmic motion. A true heterodyne implementation is not required by these manual descriptions.

### Noise Generator

Required: several colors of noise, a Serge-style S/H Source output, and a warmer tube/transformer character.

Proposed starting outputs: white, pink, brown/red, blue, and S/H Source. Exact palette remains open. A common excitation source could feed several color filters and distinct output voicings. Audition bandwidth shaping, rounded overload, asymmetric saturation, and level-dependent low-frequency coloration. "Warm" should be demonstrated with level-matched listening comparisons rather than equated with a single low-pass filter.

The S/H Source is an excitation signal intended to feed a separate sample-and-hold, not automatically an already-held random voltage. Design its voltage distribution intentionally. Saturation changes that distribution; preserve a useful sampling range and assess it separately from the audio outputs. An onboard clock and held output would be additional features, not current requirements.

### Reference / Standards

Required: a tunable A reference including 415, 432, 440, and 442 Hz; pink noise; static voltages. A large voltage dial is a possibility. Synthesizers.com Q123 is a user-specified reference.

Proposed: preset reference frequencies plus continuous fine adjustment; an independent pink-noise output; fixed voltage outputs plus a large bipolar variable-voltage dial. Additional tuning presets, total range, voltage values, and number of outputs remain open.

Recommended design exception: precise, stable reference tone and DC outputs without modeled drift or saturation. Keep reference pink noise consistent enough for comparison and calibration. The dedicated Noise Generator supplies the more strongly colored noise character. This gives both instruments separate jobs.

### Fader|Distr A and Fader|Distr B

Required: two separate modules with identical layouts and fixed linear/equal-power laws, clearly labeled A/B by variant. Each performs simultaneous parallel distribution and crossfading under one large manual BIAS dial. No CV inputs or law switch. Each path is mono; DISTR distributes S between outputs 1 and 2 while FADER crossfades X/Y to Z.

Proposed implementation: cache gains while stationary, briefly smooth manual movement, and avoid oversampling these linear functions. Side-by-side instances have additional host overhead but should remain inexpensive; verify by measurement.

### Signal Processor

Required: attenuation, amplification, inversion, offsetting, and related signal conditioning, using the Q125 data sheet as a possible reference. CV-controlled gain and resulting VCA capabilities are under consideration.

The Q125 reference combines two processing sections. Its top section has signed gain up to +/-2 and offset up to +/-5 V; its lower section normally provides polarity selection and offset. These are reference values, not adopted Voltage Modular specifications. The documented front-panel controls do not include gain CV.

Proposed: an audio/DC-capable processor organized around output = input x gain + offset, with manual gain and offset plus attenuated CV control of gain. Offset CV is explicitly excluded. Unipolar gain could provide conventional VCA behavior; bipolar gain could provide voltage-controlled inversion. Define that choice, CV scaling, headroom, and offset placement explicitly. Post-gain offset remains at the output when gain reaches zero. Channel count and ranges remain open. Its primary role is general signal conditioning; the Balanced Modulator retains its dedicated modulation role.

### Manual Switches / Routers

Required: manual switching and routing with both push-button and knob/switch interfaces, styled as Series 1 laboratory equipment.

Proposed organization: two complementary modules, a Push-Button Router and a Rotary/Toggle Router. Momentary versus latching operation, exclusive selection versus multiple active routes, input/output counts, and any off position remain open. Support audio and control signals where practical. Smooth audio transitions and exact gate/CV switching have different needs; decide transition behavior deliberately. Series 1 emphasizes direct manual operation; voltage-controlled switching is assigned to Series 2.

## Series 1: confirmed retained concepts

The user explicitly retained all five earlier concepts:

- Tone Burst Generator: signal gating based on open/closed clock-cycle counts.
- Selective Amplifier: narrow filtering, gain, resonance, and overload.
- Dynamic Modulator: envelope extraction and transfer to another signal.
- Pulse Shaper: contours formed by filtering/integrating pulses.
- Balanced Modulator: amplitude/ring modulation and carrier contribution.

### Current Series 1 inventory

1. Laboratory Generator
2. Pulse/Sine Generator
3. Sine/Random Generator
4. Deep Tone Generator
5. Noise Generator
6. Reference / Standards
7. Tone Burst Generator
8. Selective Amplifier
9. Dynamic Modulator
10. Pulse Shaper
11. Balanced Modulator
12. Fader|Distr A — Linear
13. Fader|Distr B — Equal Power
14. Signal Processor (VCA capability under consideration)
15. Push-Button Router (proposed packaging of the manual switching requirement)
16. Rotary/Toggle Router (proposed packaging of the manual switching requirement)

This is a 16-module working layout if the manual routing functions become two modules. Their packaging and final release count are not settled. The Frequency Shifter has moved to Series 2 at the user's request.

## Sound and panel principles: proposals

- Large, readable instruments with a dominant primary control, visible units, range switches, useful meters, and generous spacing.
- A coherent family with individual instrument personalities: generator dials, amplifier meters, timing switches, and a standards dial need not use identical layouts.
- Tube and transformer character should respond to operating level. Separate drive from final loudness where useful.
- Assign coloration by function. Preserve predictable control voltages, timing thresholds, and reference signals.
- Use stable defaults; intentional instability can be an expressive control where appropriate.
- Mono-only by design. Stereo/dual-mono processing requires redundant stages; no hidden stereo or polyphonic engine.
- Manual controls dominate Series One. CV becomes common in Series Two. Functional signal/modulation inputs remain appropriate where essential; review optional oscillator CV proposals accordingly.
- Minimize CPU usage. Preserve Colorbox-style direct bypass; start at 2x oversampling only where it is needed and justify any increase with evidence.
- +5 V remains the working modulation reference. The user deferred +10 V; it may be reconsidered for specific warranted CV inputs. Keep this separate from audio levels, gate detection, and pitch tracking.
- Exact frequency ranges, panel widths, and common controls require comparison with the canonical Colorbox projects now available at ../colorbox.

## Series 2: accepted general direction, evolving lineup

Purpose-built studio tools can extend Series 1 with fixed/third-octave filter banks, a sweep/function generator, contour generators, sample-and-hold, matrix routing, tape/loop manipulation, delay, reverb, vocoding, and spatial movement.

Give these instruments controls for musical gestures and repeatable studio operations. Preserve visible signal flow while moving toward more integrated processors. The user accepted this general direction with room for changes as the project develops.

### Frequency Shifter

Required in Series 2: simultaneous separate UP and DOWN outputs. Moved here from Series 1 by user decision.

Proposed: shared shift amount in Hz, a fine range for slow shifts, range switching, CV amount, and input/output level controls. Both outputs remain available without a direction switch. Any summed output or feedback path is optional. Frequency shifting and pitch transposition are distinct operations; panel language and examples should preserve that distinction.

### Voltage-Controlled Switches

Required addition: voltage-controlled switching. Candidate forms include controlled source selection, destination routing, and sequential switching. Control by continuous CV, gates, or clocks, along with manual override, remains to be specified per module.

### Studio Manual Switches / Routers

Required addition: more modern manual switches and routers appropriate to the mid-1960s to mid-1970s studio direction. Proposed distinctions from Series 1 include illuminated selection buttons, grouped routing controls, and performance-oriented layouts. Matrix size, channel count, routing rules, and any combination with voltage-controlled switching remain open.

## Series 3: candidate territory, not a committed lineup

- Analog arithmetic: summing, scaling, offset, four-quadrant multiplication, rectification, min/max.
- Continuous computation: integrators/accumulators, reset/hold conditions, feedback experiments.
- Decisions and memory: comparators, window detection, Boolean logic, latches, counters, shift registers.
- Event organization: switches, clock division, probability, state-dependent routing.

These functions should serve both control and audio experiments where appropriate. Define scaling, thresholds, reset behavior, and numerical limits explicitly before implementation. Distinguish continuous integration from clocked accumulation and analog voltage storage from Boolean state.

### Patchable Devices candidates

The user added Patchable Devices as a design reference alongside ACE and requested consideration of two concepts. Both remain candidates rather than committed modules.

- Window Generator: the reference produces four synchronized envelope shapes from shared timing controls and provides gates identifying stages. This is a multi-envelope/function-generator concept, separate from voltage-window comparison. Explore coordinated contours, stage events, and feedback patching; exact shapes and controls remain open.
- Voltage Sequencer: the reference has eight stages, two voltage rows, direct stage selection, per-stage gates, and derived outputs including difference, minimum, and maximum. Explore it as a programmable voltage/state source with manual and patch-controlled navigation, rather than fixing an ordinary linear step sequence as its only role. Stage count, derived functions, and addressing behavior remain open.

Series 2's contour generators can focus on studio gestures, while the Series 3 candidate exposes coordinated stages and events for computational patching. This is a proposed distinction to refine, not a ban on overlap.

## Source notes

- Berna 1 manual: Resources/Berna 1.0 User Manual .pdf, PDF page 19 for Beat Oscillator.
- Berna 3 manual: Resources/Berna3Manual.pdf, pages 14, 15, 17, and 31 for Sine/Random, Tieftone, AG-10, and Frequency Shifter.
- Berna 2 manual and 1971 Moog catalog remain collection-wide references in Resources.
- Serge Noise/S&H source: https://serge-modular.com/docs/RandomSource_Serge_ANC_Euro.pdf
- ACE's analog-computer approach: https://store.cherryaudio.com/bundles/audio-computing-engine-collection
- Befaco arithmetic reference: https://www.befaco.org/docs/AB%2BC/A_B%2BC_V4_User_Manual.pdf
- Count Modula logic, counters, and routing: https://library.vcvrack.com/CountModula
- alef's bits mathematical, probabilistic, and event-processing ideas: https://library.vcvrack.com/alefsbits
- Lilac accumulator/comparator documentation: https://github.com/grough/lilac-modules-vcv
- Q125 Signal Processor: Resources/q125data.pdf, both pages; manual gain/polarity and offset reference. Gain CV and VCA behavior would be InsectLabs extensions.
- Patchable Devices manual: https://github.com/nullJaX/vcvrack-patchable-devices/blob/master/MANUAL.md
- Window Generators: https://github.com/nullJaX/vcvrack-patchable-devices/blob/master/modules/WindowGenerators/WindowGenerators.md
- Voltage Sequencer: https://github.com/nullJaX/vcvrack-patchable-devices/blob/master/modules/VoltageSequencer/VoltageSequencer.md
- User-specified Q123 reference: https://www.synthesizers.com/q123.html (page could not be retrieved during this review; no exact Q123 specifications are asserted here).

The first utility specification is now drafted in [Series-One-Utilities.md](Series-One-Utilities.md). It proposes two Signal Processor channels, simultaneous pan/crossfade, dual A/B push-button selection, and paired source/destination rotary routing. These detailed choices remain proposals; the roadmap's accepted module scope is unchanged.

Next design work: review or trial these concrete utility behaviors, establish platform and Colorbox integration conventions, then prototype utilities and specify the four distinct generator types. Keep Series 2 and Series 3 flexible as the system develops.

Implementation baseline and resource locations are recorded in [Development-Setup.md](Development-Setup.md).

The first Signal Processor Designer project is wired and passes SDK/callback checks; see [test-build status](../laboratory/signal-processor/development/README.md). Designer open/build and host audition remain ahead.
