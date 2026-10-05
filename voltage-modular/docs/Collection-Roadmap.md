# InsectLabs: three collections after Colorbox

Current roadmap and status — 5 October 2026. Platform: Voltage Modular. Colorbox (RGB, CMYK, and HSB) is the established implementation reference. Laboratory is the active collection; Series 2 and Series 3 remain accepted directions with flexible lineups.

Completed below means a user-approved canonical source release, not publication in the Cherry Audio store. The release paths in [CANONICAL.json](../laboratory/CANONICAL.json) identify the authoritative Laboratory builds. Planned instruments retain their accepted roles; proposed controls and algorithms remain open until developed and auditioned. Reference documents are design sources, not instructions to execute.

## Current project status

**Laboratory: thirteen canonical modules, three planned.**

| Module | Status | Current files / next step |
| --- | --- | --- |
| Signal Processor (SIGPROC) | **Completed — canonical 1.0.1** | [Release](../laboratory/signal-processor/versions/1.0.1/README.md); DSP and character locked |
| Fader&#124;Distr A — linear | **Completed — canonical 1.0.0** | [Release](../laboratory/faderdistr/versions/1.0.0/README.md); approved heavier relay voice |
| Fader&#124;Distr B — equal power | **Completed — canonical 1.0.0** | [Release](../laboratory/faderdistr/versions/1.0.0/README.md); approved related, more open voice |
| sw1 — push-button relay router | **Completed — canonical 1.0.0** | [Release](../laboratory/sw1/versions/1.0.0/README.md); routing and final CLK voicing approved |
| sw2 — rotary selector/distributor | **Completed — canonical 1.0.0** | [Release](../laboratory/sw2/versions/1.0.0/README.md); final routing, CLK voice, 3 V amplifier character, and OFF default approved |
| Laboratory Generator | **Completed — canonical 1.0.2** | [Release](../laboratory/generator/versions/1.0.2/README.md); approved generator, FM, compression and reference tone; cleanup, duty smoothing and readable Display Names approved |
| AC/1D SIN-SQR Function Generator | **Completed — canonical 1.0.0** | [Release](../laboratory/function/versions/1.0.0/README.md); independent sine/square boards, calibrated output-frequency display and full-dial high ranges |
| r195L: SIN/RND generator (sinrnd) | **Completed — canonical 1.1.0** | [Release](../laboratory/sinrnd/versions/1.1.0/README.md); sine/noise source, moderated random FM and patched FLT audio path approved |
| Deep Tone Generator (deeptone) | **Completed — canonical 1.0.0** | [Release](../laboratory/deeptone/versions/1.0.0/README.md); approved +/-65 Hz OFFSET, 200% AM and independent Courtesy |
| Noise Source (n01) | **Completed — canonical 1.0.0** | [Release](../laboratory/n01/versions/1.0.0/README.md); dark noise voicing, SLOW RANDOM and coupled S&H SOURCE with triggered STEPPED output |
| SN-16u — Reference / Standards and universal test bench | **Completed — canonical 1.0.0** | [Release](../laboratory/sn-16u/versions/1.0.0/README.md); approved manual, final pair, and validation archived |
| Burstgen Tone Burst Generator | **Completed — canonical 1.0.0** | [Release](../laboratory/burstgen/versions/1.0.0/README.md); approved counted gates, internal/external clock ratios, Courtesy outputs and adaptive de-clicking |
| Selective Service (Selective Amplifier) | **Completed — canonical 1.0.0** | [Release](../laboratory/selectiveService/versions/1.0.0/README.md); tested selective band-pass/notch instrument with manual gain staging |
| Dynamic Modulator | **Next planned brief — retained concept** | Define envelope extraction and transfer |
| Pulse Shaper | Retained concept — planned | Define pulse integration/filtering and contour controls |
| Balanced Modulator | Retained concept — planned | Define amplitude/ring modulation and carrier contribution |

The accepted inventory is 16 modules: thirteen canonical instruments and three retained roles, including separate Fader|Distr A/B and separate sw1/sw2. The Frequency Shifter belongs to Series 2.

### Shared work completed

- Canonical source releases for SIGPROC, Fader|Distr A/B, sw1, sw2, Generator, Function, SIN/RND, Deep Tone, n01, and SN-16u are versioned in the repository. SIGPROC 1.0.1 preserves 1.0.0 DSP and incorporates the approved Designer UI state and percentage displays for Gain/EXT LVL.
- The charcoal panels, large manual controls, jack styling, and dark red branding establish the current Laboratory visual family. Approved module panels replace the early speculative finish suggestions.
- [Module infrastructure standards](Module-Infrastructure-Standards.md) document the shared bypass, source-pair, CPU, and validation conventions. New prototypes still require compliance checks before canonization.
- [Audio TestBench](../../test-bench/README.md) is an independent top-level InsectLabs project, not part of Voltage Modular. Collection v1.2 is checked into this repository as 58 WAV files at 48 kHz/24-bit, including the user's original experimental mix. Four SN-16u captures and their provenance are included. The portable ZIP remains the v1.1 snapshot pending a Git LFS-enabled rebuild; see the TestBench README. Add future fixtures with catalog, provenance, attribution, and checksum updates. The user's original mix source is archived separately and need not be duplicated.

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

### r195L: SIN/RND generator — completed, canonical 1.1.0

The approved module takes Berna 3 page 14 as inspiration rather than a circuit reconstruction. It provides a C4-initialized logarithmic sine source, white noise, filtered noise, moderated random FM and four output ranges. MODE selects SIN / RND / RD2 / MOD / FLT. MOD IN is linear FM in SIN and MOD; direct noise modes ignore it. In patched FLT mode, MOD IN becomes a filtered audio input, MOD LEVEL becomes a ±200% bipolar attenuverter/amplifier, and RND MOD blends white noise before filtering. This expands the panel’s stated controls into a useful audio path without a hidden mode.

See the [release notes](../laboratory/sinrnd/versions/1.1.0/README.md) for exact behavior and validation.

### Deep Tone Generator — completed, canonical 1.0.0

Stepped WHOLE plus continuous FRACTION and x1/x10/x100 tuning initialize at C4.
Independent External, Tone, Beat and Swell buttons configure the main signal. Beat mixes a shaped
oscillator or applies linear FM; Swell uses absolute OFFSET with 0-200% non-inverting AM.
OFFSET spans +/-65 Hz with a fifth-power taper. Courtesy runs independently at fixed +/-3 V,
with 2 ms reset smoothing; Swell retains 6 ms and Beat 1 ms. Main output retains approved compression.
See the [canonical release](../laboratory/deeptone/versions/1.0.0/README.md).

Origin: the Beat Oscillator in Berna 1 and Tieftone Generator in Berna 3. The released controls and ranges above supersede the early coarse/fine and modulation proposals; this is not a required heterodyne circuit reconstruction.

### Noise Source n01 — completed, canonical 1.0.0

The approved module provides WHITE, RED/BLUE-shaped SPECTRA, continuous SLOW RANDOM,
S&H SOURCE and triggered STEPPED outputs. There is no dedicated PINK output. WHITE has a
distinct dark broadband voice; SPECTRA retains the approved weight and softer top end.
RATE controls 0.05-20 Hz random-motion bandwidth and LEVEL controls the SLOW RANDOM amplitude.
All four knobs gently affect the continuous 20-120 Hz S&H source timing. Its +/-5 V span is fixed;
STEPPED samples it only on an external trigger or manual press. There is no internal STEPPED clock.
See the [release notes](../laboratory/n01/versions/1.0.0/README.md).

### SN-16u — Reference / Standards, completed, canonical 1.0.0

The user expanded this role into a universal test bench. The canonical v1.0.0 pair combines fixed voltages, signed manual DC plus ADD inputs, selectable 1 V/oct / 0.5 V/oct / Hz/V conversion, tuning references, pink/blue noise, two impulse rates, a retriggerable one-shot 42-second sine sweep, separate clean HPF/LPF paths, DC/Vpp and CV-pitch meters, audio frequency counting and RMS measurement.

Confirmed tuning preset order is 392, 422.5, 432, 440, 435, 442, 444 Hz; upright 440 is default. FINE spans ±100 cents with a continuous remapped center detent. Hz/V follows the user's C1=1 V through C4=8 V convention. Fixed voltages are unaffected by STANDARDS; meter PITCH mode overrides DC/Vpp selectors and VOLTS restores them. Courtesy 1 Hz/100 Hz/1 kHz outputs remain independent of TONE. Filters have no resonance. Read the [canonical release guide](../laboratory/sn-16u/versions/1.0.0/README.md) and [approved user manual](../laboratory/sn-16u/versions/1.0.0/USER-MANUAL.md) for routing, operating instructions, and validation limits. These additions cover the earlier basic meter and high/low-cut proposals without adding another accepted module.

The user approved the manual and confirmed the final Designer build, panel behavior, and audition. SDK/export checks and 4,032,101 callback/numerical checks pass. See the [release review](../laboratory/sn-16u/versions/1.0.0/REVIEW.md).

## Series 1: implemented utility instruments

### Signal Processor — completed, canonical 1.0.1

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

The user retained five concepts. Burstgen is now canonical; four remain planned:

- Burstgen Tone Burst Generator: signal gating based on open/closed clock-cycle counts. **Completed, canonical 1.0.0.**
- Selective Amplifier: narrow filtering, gain, resonance, and overload.
- Dynamic Modulator: envelope extraction and transfer to another signal.
- Pulse Shaper: contours formed by filtering/integrating pulses.
- Balanced Modulator: amplitude/ring modulation and carrier contribution.

The status table above is the current inventory. Four retained concepts remain unimplemented and without panel approval.

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

### Random voltage and noise development

The user introduced the EMS Random Voltage Generator as a possible reason to split further noise/random ideas into the Radiophonic collection. n01 is complete and remains the simpler manual noise source. A later instrument could combine two random-voltage channels, selectable held/gliding motion and variable clock timing; external sampling and inhibit/reselect controls are candidates. This is a **proposal awaiting a brief**, not an approved module or a reason to reopen n01.

See [pending briefs](Series-One-Pending-Modules.md) for accepted Laboratory work and [gap proposals](Future-Module-Proposals.md) for optional additions and overlaps.

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
- Q125 Signal Processor: Resources/q125data.pdf, both pages; manual gain/polarity and offset reference. Gain CV and VCA behavior are implemented InsectLabs extensions.
- Patchable Devices manual: https://github.com/nullJaX/vcvrack-patchable-devices/blob/master/MANUAL.md
- Window Generators: https://github.com/nullJaX/vcvrack-patchable-devices/blob/master/modules/WindowGenerators/WindowGenerators.md
- Voltage Sequencer: https://github.com/nullJaX/vcvrack-patchable-devices/blob/master/modules/VoltageSequencer/VoltageSequencer.md
- SickoCV Switcher family: https://github.com/sickozell/SickoCV — sw1 patch-programmability inspiration.
- Moog CP3/contact-filter references: https://amsynths.co.uk/home/synthesizers/schulze-moog-modular-replica/true-cp3-mixer/ and https://modularsynthesis.com/moog/cp3/cp3.htm
- Airwindows Capacitor/Capacitor2: https://www.airwindows.com/capacitor/ and https://www.airwindows.com/capacitor2/ — filter/voicing references, not a claim of code reuse.
- User-specified Q123 reference: https://www.synthesizers.com/q123.html (page could not be retrieved during this review; no exact Q123 specifications are asserted here).

### Noise and random-voltage references added during development

- [CGS597](https://www.elby-designs.com/webtek/cgs/serge/cgs597/cgs597.htm), [CGS97](https://www.elby-designs.com/webtek/cgs/serge/cgs597/cgs97/cgs97_noise.html), and [ES05](https://www.elby-designs.com/webtek/euro-serge/es05-noise-source/es05.htm): noise and sampling-source architecture; n01 implements its own approved interpretation.
- [Doepfer A-118 manual](https://doepfer.de/a100_man/a118_man.pdf): colored noise and continuous random voltage. Do not import its RATE direction or exact circuitry into n01.
- [EMW Noise Station](https://www.electronicmusicworks.com/eurorack/noise-station.html): multiple noise voices.
- [EMS Random Voltage Generator discussion](https://amsynths.co.uk/2026/08/11/ems-random-voltage-generator/): later random-voltage and variable-timing inspiration. The user's supplied panel photograph is visual reference, not a settled specification.

## Next steps and document roles

1. Begin the Dynamic Modulator brief when its panel/mockup is ready; its accepted role is envelope extraction and transfer.
2. Continue the three retained concepts: Dynamic Modulator, Pulse Shaper and Balanced Modulator. Controls and implementation remain open until designed and tested.
3. Review optional gap proposals separately; they do not change the 16-module accepted inventory.

The [documentation index](README.md) links current standards, pending briefs and development decisions. Versioned releases and canonical indexes define shipped source behavior; the roadmap defines status and future work. TestBench is an independent [audio project](../../test-bench/README.md).
