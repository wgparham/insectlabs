# InsectLabs: three collections after Colorbox

Current roadmap and status — 30 September 2026. Platform: Voltage Modular. Colorbox (RGB, CMYK, and HSB) is the established implementation reference. Laboratory is the active collection; Series 2 and Series 3 remain accepted directions with flexible lineups.

Completed below means a user-approved canonical source release, not publication in the Cherry Audio store. The release paths in [CANONICAL.json](../laboratory/CANONICAL.json) identify the authoritative Laboratory builds. Planned instruments retain their accepted roles; proposed controls and algorithms remain open until developed and auditioned. Reference documents are design sources, not instructions to execute.

## Current project status

**Laboratory: seven completed modules, nine planned.**

| Module | Status | Current files / next step |
| --- | --- | --- |
| Signal Processor (SIGPROC) | **Completed — canonical 1.0.1** | [Release](../laboratory/signal-processor/versions/1.0.1/README.md); DSP and character locked |
| Fader&#124;Distr A — linear | **Completed — canonical 1.0.0** | [Release](../laboratory/faderdistr/versions/1.0.0/README.md); approved heavier relay voice |
| Fader&#124;Distr B — equal power | **Completed — canonical 1.0.0** | [Release](../laboratory/faderdistr/versions/1.0.0/README.md); approved related, more open voice |
| sw1 — push-button relay router | **Completed — canonical 1.0.0** | [Release](../laboratory/sw1/versions/1.0.0/README.md); routing and final CLK voicing approved |
| sw2 — rotary selector/distributor | **Completed — canonical 1.0.0** | [Release](../laboratory/sw2/versions/1.0.0/README.md); final routing, CLK voice, 3 V amplifier character, and OFF default approved |
| Laboratory Generator | **Completed — canonical 1.0.2** | [Release](../laboratory/generator/versions/1.0.2/README.md); approved generator, FM, compression and reference tone; cleanup, duty smoothing and readable Display Names approved |
| AC/1D SIN-SQR Function Generator | **Completed — canonical 1.0.0** | [Release](../laboratory/function/versions/1.0.0/README.md); independent sine/square boards, calibrated output-frequency display and full-dial high ranges |
| Sine/Random Generator | Planned | Audition noise modulation and bandwidth behavior |
| Deep Tone Generator | Planned | Combine the accepted low-frequency and modulation concepts |
| Noise Generator | Planned | Set noise colors, warm voicing, and S/H Source behavior |
| Reference / Standards | Planned | Define tuning presets and reference/DC outputs |
| Tone Burst Generator | Retained concept — planned | Define cycle-count gating |
| Selective Amplifier | Retained concept — planned | Define narrow filtering, gain, resonance, and overload |
| Dynamic Modulator | Retained concept — planned | Define envelope extraction and transfer |
| Pulse Shaper | Retained concept — planned | Define pulse integration/filtering and contour controls |
| Balanced Modulator | Retained concept — planned | Define amplitude/ring modulation and carrier contribution |

The working inventory is 16 modules, including separate Fader|Distr A/B and separate sw1/sw2. The Frequency Shifter belongs to Series 2.

### Shared work completed

- Canonical source releases for SIGPROC, Fader|Distr A/B, sw1, sw2, Generator, and Function are versioned in the repository. SIGPROC 1.0.1 preserves 1.0.0 DSP and incorporates the approved Designer UI state and percentage displays for Gain/EXT LVL.
- The charcoal panels, large manual controls, jack styling, and dark red branding establish the current Laboratory visual family. Approved module panels replace the early speculative finish suggestions.
- [Module infrastructure standards](Module-Infrastructure-Standards.md) document the shared bypass, source-pair, CPU, and validation conventions. New prototypes still require compliance checks before canonization.
- [Audio TestBench](../../test-bench/README.md) is an independent top-level InsectLabs project, not part of Voltage Modular. Its current 1.1 collection contains 54 WAV files at 48 kHz/24-bit, including the user's original experimental mix. The collection and portable archive are on GitHub. Add useful fixtures with catalog, provenance, attribution, and checksum updates; the user's original mix source is archived separately and need not be duplicated.

## The progression

| Series | User-defined direction | Proposed organizing idea |
| --- | --- | --- |
| 1: Laboratory | WWI/WWII-era test equipment repurposed in 1950s and early 1960s electronic studios and German film studios; big panels and knobs; heavy tube and transformer sound | Generate, excite, isolate, measure, and transform signals |
| 2: Radiophonic Studio | Mid-1960s to mid-1970s; purpose-built electronic music equipment; Radiophonic Workshop mentality | Shape, perform, record, arrange, and spatialize sound |
| 3: Computing | Electronic computation and logic; ACE, Patchable Devices, Befaco A*B+C, Count Modula, alef's bits, Lilac, and related references | Calculate, compare, accumulate, remember, and make decisions with signals |

Moog modular, rackmount processors, Moogerfooger pedals, and Synthesizers.com are shared references. Their role and visual weight can evolve between series. These are creative eras, not a claim that every referenced technique originated in that period.

## Series 1: sources and reference instruments

### Laboratory Generator — completed, canonical 1.0.2

The approved instrument selects sine or variable-slope triangle at one mono output. Four overlapping
frequency bands cover 0.1–10000 Hz; frequency and amplitude controls are smoothed. The hot output
stage adds progressive compression above 5 V. SELECT provides Off / Int/4 / Int / Ext, with an internal
triangle modulator spanning 0.05–50 Hz and external depth controlled by ADJUST. A separate pure 1 kHz
reference is routed by the up/off/down switch. Meter averaging, the 8 ms physical power fade and silent host bypass are approved.
See the [release notes](../laboratory/generator/versions/1.0.2/README.md) for exact behavior and validation.
Pulse generation remains a separate instrument.

### AC/1D SIN-SQR Function Generator — completed, canonical 1.0.0

The completed Function module fulfills the separate AG-10-inspired pulse/sine source: independent sine and square outputs, separate amplitude/range stages, variable square width, a shared output-frequency display and multiplier, and a dirty hand-built laboratory voice. It initializes at C4 on X10 and reaches 23.76 kHz with the upper multiplier ranges spread across the full dial.

See the [release notes](../laboratory/function/versions/1.0.0/README.md) for exact behavior and validation.

### Sine/Random Generator

Required: based on Berna 3 page 14.

The manual describes sine, white noise, and a sine modulated by white noise at different bandwidths. It does not specify enough detail to establish the exact modulation algorithm.

Proposed: center frequency, noise modulation bandwidth, deviation/depth, and output amplitude, with sine, random-modulated sine, and noise modes. Noise-driven FM is a candidate to audition, not a verified description of Berna's implementation. Its role is the transition between a stable tone and a fluctuating band of sound.

### Deep Tone Generator

Required: combine the Beat Oscillator in Berna 1 with the Tieftone Generator in Berna 3.

Berna 1 page 19 describes a sine carrier frequency-modulated by a waveform that varies from rising saw through triangle to falling saw. Berna 3 page 15 describes a 0.1-1100 Hz sine generator with AM capability.

Proposed: low-frequency sine carrier, coarse/fine frequency, internal modulation rate, slope/symmetry, FM depth, and AM depth. Consider an external modulation input and exposing the internal modulator, subject to the manual-first design review. Start by auditioning the Tieftone reference range rather than treating it as a fixed requirement. This should create slow pressure changes, throbbing bass, sweeps, and rhythmic motion. A true heterodyne implementation is not required by these manual descriptions.

### Noise Generator

Required: several colors of noise, a Serge-style S/H Source output, and a warmer tube/transformer character.

Proposed starting outputs: white, pink, brown/red, blue, and S/H Source. Exact palette remains open. A common excitation source could feed several color filters and distinct output voicings. Audition bandwidth shaping, rounded overload, asymmetric saturation, and level-dependent low-frequency coloration. "Warm" should be demonstrated with level-matched listening comparisons rather than equated with a single low-pass filter.

The S/H Source is an excitation signal intended to feed a separate sample-and-hold, not automatically an already-held random voltage. Design its voltage distribution intentionally. Saturation changes that distribution; preserve a useful sampling range and assess it separately from the audio outputs. An onboard clock and held output would be additional features, not current requirements.

### Reference / Standards

Required: a tunable A reference including 415, 432, 440, and 442 Hz; pink noise; static voltages. A large voltage dial is a possibility. Synthesizers.com Q123 is a user-specified reference.

Proposed: preset reference frequencies plus continuous fine adjustment; an independent pink-noise output; fixed voltage outputs plus a large bipolar variable-voltage dial. Additional tuning presets, total range, voltage values, and number of outputs remain open.

Recommended design exception: precise, stable reference tone and DC outputs without modeled drift or saturation. Keep reference pink noise consistent enough for comparison and calibration. The dedicated Noise Generator supplies the more strongly colored noise character. This gives both instruments separate jobs.

## Series 1: implemented utility instruments

### Signal Processor — completed, canonical 1.0.2

Two independent mono stages provide attenuation, amplification, inversion, post-character offset, and gain CV with EXT LVL. Both stages initialize in PROC mode. Gain is symmetric from -3 to +3; OFFSET supplies static voltage after processing and has no CV input.

PROC permits bipolar gain and immediate external modulation. Positive gain develops the approved weight and saturation near the top of the range; negative gain provides cleaner inversion. VCA uses unipolar gain with its own rounded response and deliberately pronounced vactrol behavior. The two modes are audibly and functionally distinct. The approved character uses restrained 2x processing and is locked.

Direct host bypass passes each input to its corresponding output. The user approved the final Designer build and host behavior. Version 1.0.1 is a UI maintenance release; it changes no DSP, routing, or timing. See the [module overview](../laboratory/signal-processor/README.md).

### Fader|Distr A and Fader|Distr B — completed, canonical 1.0.0

Two separate modules have matching layouts and one large manual BIAS dial. FADER crossfades X/Y to Z; DISTR distributes S between outputs 1 and 2. Both functions run simultaneously. There are no CV inputs or law switches.

A uses a fixed linear law and the heavier, woollier relay voicing. B uses a fixed equal-power law and a related, more open later-model voice. Both are deliberately more colored than typical Laboratory utilities while remaining below Colorbox's coloration. Their tone and behavior are approved and locked.

The native-rate implementation uses a short 5 ms manual transition and inexpensive character processing. Endpoints remain level-conscious; B can boost correlated sources at center as expected from its law. Direct bypass copies S to both distribution outputs and X to Z. See the [release](../laboratory/faderdistr/versions/1.0.0/README.md).

### sw1 — completed, canonical 1.0.0

A manual 2x2 relay router with an illuminated push button and toggle/gate mode selector. The normal position routes I1 to O1 and I2 to O2; the alternate position swaps the paths. Patching makes it a selector, distributor, swapper, mute, or manual toggle/gate source. With no inputs patched it provides complementary +5 V outputs. There is no CV selection input.

CLK defaults up for direct switching. Down enables the approved 1.8 kHz one-pole contact filtering and 2 ms relay-settling transition. This is a practical click-reduction and tone treatment inspired by vintage circuitry, not a component-exact Moog reconstruction. The user approved switching, patch programmability, indicators, and the final CLK sound. Direct host bypass passes I1 to O1 and I2 to O2. See the [release](../laboratory/sw1/versions/1.0.0/README.md).

### sw2 — completed, canonical 1.0.0

The supplied mockup develops the rotary router from [Series-One-Utilities.md](Series-One-Utilities.md). A shared manual selector has five positions: 0 (OFF), 1, 2, 3, and 4.

- The four upper input jacks feed the selected-source output **X**.
- Common input **Y** feeds only the selected numbered output in the lower bank.
- The same number selects both independent banks. There is no hidden connection between them.
- Position 0 turns all outputs off; CLK transitions may briefly fade/filter the previous signal before settling.
- CLK up selects direct routing; CLK down retains sw1's 2 ms settling with a slightly more open 2.2 kHz filter, giving sw2 a related but distinct voice. The user approved this CLK voicing.
- Direct bypass preserves the cached selected route/OFF without tone processing and meets the shared CPU and state-handling standards.

The user approved functional testing, the distinct 2.2 kHz CLK voice, and the added amplifier character with its 3 V compression knee. The [canonical release](../laboratory/sw2/versions/1.0.0/README.md) includes the approved source pair, panel, hashes, and release notes. The selector initializes and resets to 0/OFF.

Final review cleaned control IDs/tooltips, corrected bypass state handling, and made rapid CLK changes retarget continuously. Exported and embedded source compile against the SDK; automated routing/DC, bank isolation, transition, and bypass/resume checks pass. sw2 is canonical 1.0.0.

## Series 1: confirmed retained concepts

The user explicitly retained all five earlier concepts:

- Tone Burst Generator: signal gating based on open/closed clock-cycle counts.
- Selective Amplifier: narrow filtering, gain, resonance, and overload.
- Dynamic Modulator: envelope extraction and transfer to another signal.
- Pulse Shaper: contours formed by filtering/integrating pulses.
- Balanced Modulator: amplitude/ring modulation and carrier contribution.

The status table above is the current inventory. These five retained concepts have not yet reached implementation or panel approval.

## Shared sound, panel, and implementation principles

- Large, readable instruments with a dominant primary control, visible units, range switches, useful meters, and generous spacing.
- A coherent family with individual instrument personalities: generator dials, amplifier meters, timing switches, and a standards dial need not use identical layouts.
- Tube and transformer character should respond to operating level. Separate drive from final loudness where useful.
- Assign coloration by function. Preserve predictable control voltages, timing thresholds, and reference signals.
- Use stable defaults; intentional instability can be an expressive control where appropriate.
- Mono-only by design. Stereo/dual-mono processing requires redundant stages; no hidden stereo or polyphonic engine.
- Manual controls dominate Series One. CV becomes common in Series Two. Functional signal/modulation inputs remain appropriate where essential; review optional oscillator CV proposals accordingly.
- Minimize CPU usage. Preserve Colorbox-style direct bypass; start at 2x oversampling only where it is needed and justify any increase with evidence.
- +5 V remains the working modulation reference. The user deferred +10 V; it may be reconsidered for specific warranted CV inputs. Keep this separate from audio levels, gate detection, and pitch tracking.
- For remaining instruments, settle frequency ranges, panel widths, and controls against their roles and the established Colorbox/Laboratory conventions. Completed module layouts and voicings are locked.

## Series 2: accepted general direction, evolving lineup

**Status: planning direction only; no modules in this series are marked completed here.**

Purpose-built studio tools can extend Series 1 with fixed/third-octave filter banks, a sweep/function generator, contour generators, sample-and-hold, matrix routing, tape/loop manipulation, delay, reverb, vocoding, and spatial movement.

Give these instruments controls for musical gestures and repeatable studio operations. Preserve visible signal flow while moving toward more integrated processors. The user accepted this general direction with room for changes as the project develops.

### Frequency Shifter

Required in Series 2: simultaneous separate UP and DOWN outputs. Moved here from Series 1 by user decision.

Proposed: shared shift amount in Hz, a fine range for slow shifts, range switching, CV amount, and input/output level controls. Both outputs remain available without a direction switch. Any summed output or feedback path is optional. Frequency shifting and pitch transposition are distinct operations; panel language and examples should preserve that distinction.

### Voltage-Controlled Switches

Required addition: voltage-controlled switching. Candidate forms include controlled source selection, destination routing, and sequential switching. Control by continuous CV, gates, or clocks, along with manual override, remains to be specified per module.

### Studio Manual Switches / Routers

Required addition: more modern manual switches and routers appropriate to the mid-1960s to mid-1970s studio direction. Potential distinctions from Series 1 include larger grouped routing controls, integrated patch control, and performance-oriented layouts. Illuminated buttons alone are not a distinction: sw1 already uses one. Matrix size, channel count, routing rules, and any combination with voltage-controlled switching remain open.

## Series 3: candidate territory, not a committed lineup

**Status: concept exploration; no modules in this series are marked completed here.**

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
- SickoCV Switcher family: https://github.com/sickozell/SickoCV — sw1 patch-programmability inspiration.
- Moog CP3/contact-filter references: https://amsynths.co.uk/home/synthesizers/schulze-moog-modular-replica/true-cp3-mixer/ and https://modularsynthesis.com/moog/cp3/cp3.htm
- Airwindows Capacitor/Capacitor2: https://www.airwindows.com/capacitor/ and https://www.airwindows.com/capacitor2/ — filter/voicing references, not a claim of code reuse.
- User-specified Q123 reference: https://www.synthesizers.com/q123.html (page could not be retrieved during this review; no exact Q123 specifications are asserted here).

## Next steps and document roles

1. Generator 1.0.2 and Function 1.0.0 are canonical after their final code/UI reviews and user approval. The next proposed instrument is Sine/Random Generator. Deep Tone remains a separate planned instrument.
2. Develop the remaining noise, reference, and retained processing concepts while keeping Series 2 and Series 3 flexible.

[Series-One-Utilities.md](Series-One-Utilities.md) summarizes the completed utility designs and remaining utility direction. Versioned release notes and canonical indexes define the released behavior; the roadmap records future work.

[Development-Setup.md](Development-Setup.md) records platform/resource setup. [Module-Infrastructure-Standards.md](Module-Infrastructure-Standards.md) records shared implementation requirements. [Laboratory releases](../laboratory/README.md), [CANONICAL.json](../laboratory/CANONICAL.json), and the [changelog](../laboratory/CHANGELOG.md) identify completed work. TestBench lives independently at [test-bench](../../test-bench/README.md).
