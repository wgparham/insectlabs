# Series One: utility instruments

Design draft 0.3 | Voltage Modular | 28 September 2026

This draft develops the accepted collection roadmap. Module roles are agreed; channel counts, ranges, normalizations, controls, and behaviors below are recommendations for the first prototype. They are not claims about Voltage Modular defaults or exact recreations of reference hardware. The Signal Processor now has a wired Designer development project and matching source; SDK and callback tests pass, with Designer/host testing next.

## Shared direction

Treat these as substantial bench instruments: large primary controls, readable scales, clear signal paths, and enough space to operate them comfortably. Suggested finish: warm gray painted metal, cream scale plates, black knobs, and restrained amber indicators. Final dimensions and assets should be checked against the canonical Colorbox projects in ../colorbox before panel production.

The following are proposed local conventions for these five modules, to verify against the platform and existing Colorbox conventions before coding:

- Mono-only signal paths and jacks. Two redundant stages may be patched as dual mono or a stereo pair; there is no hidden stereo or polyphonic engine. A panner distributes one mono source across two outputs.
- Preserve DC and signal polarity. Unpatched ordinary signal inputs contribute zero unless a normalization is stated.
- Label gain as a ratio and offset in volts. Provide clear zero and unity positions and precise numeric entry where the platform supports it.
- Manual operation is the Series One default, reflecting WWI/WWII-era test equipment repurposed in later studios. Add CV only when it is essential to an instrument's function or explicitly requested. Review the earlier external-FM proposal for Laboratory Generator under this rule.
- Use +5 V as the working modulation reference (R = 5 V). The user has deferred the +10 V idea; revisit only for particular inputs that warrant it, not as a collection-wide change. This remains separate from audio amplitude, pitch tracking, and trigger thresholds.
- Smooth mouse-operated continuous controls over a short interval, initially 5 ms for auditioning. External modulation should retain its bandwidth rather than inherit that smoothing automatically.
- Preserve exact utility behavior in the initial prototypes. Develop tube/transformer coloration separately, then audition it on audio paths without unintentionally altering offsets, control voltages, or gate levels.
- Save control positions and latched switch selections with patches. Transient mouse presses must not become saved held states.

## 1. Signal Processor

### Recommended form

Two independent identical mono channels. Each provides manual gain, inversion, amplification, offset, and optional voltage-controlled amplitude. Duplicate stages permit paired processing without a dedicated stereo architecture.

Each channel has:

| Control or connector | Proposed behavior |
| --- | --- |
| GAIN | Large bipolar dial, -2 to +2; center is silence before offset; +1 and -1 clearly marked |
| OFFSET | -5 to +5 V, added after gain |
| GAIN CV | External voltage controlling gain |
| CV AMOUNT | Bipolar amount, -2 to +2 gain units per +R V |
| PROCESS / VCA | PROCESS allows negative gain; VCA prevents negative gain |
| IN / OUT | Audio or DC signal input and output |
| Output indicator | Positive/negative indication; useful for static CV as well as audio |

No offset CV: explicitly excluded by the user. Proposed: no input normalization between channels and no summed output.

### Behavior

For each channel, calculate effective gain from the manual setting plus the scaled gain CV. In PROCESS mode limit that gain to -2 through +2; in VCA mode limit it to 0 through +2. Then multiply the input by effective gain and add OFFSET.

These limits apply to gain, not automatically to output voltage. Output headroom and any limiting must be defined during platform integration; do not silently clip the result to +/-5 V.

Initial settings from the supplied mockup: GAIN +1, OFFSET 0, CV AMOUNT 0 on both channels; LEFT defaults to PROCESSOR, RIGHT defaults to VCA. The opposite selector directions are intentional. See MOCKUP-INTEGRATION.md for the side-specific numeric mapping.

For envelope-controlled amplitude: select VCA, set GAIN to 0, OFFSET to 0, and CV AMOUNT to +1. A 0-to-5 V envelope then requests gain from 0 to unity. To invert a signal under CV, use PROCESS mode.

OFFSET is deliberately post-gain: setting gain to zero leaves the offset voltage at OUT. At zero input, this module can therefore serve as an adjustable DC source. The Reference / Standards module still provides dedicated reference outputs without occupying a processor channel.

### Panel emphasis and patch trials

Use two repeated vertical channel strips with GAIN above OFFSET, smaller CV AMOUNT nearby, and connectors below. Provide a visible PROCESS/VCA switch for each channel.

Trials: invert an envelope; reduce a bipolar modulation signal; shift its center; amplify a quiet audio source; control a tone with an envelope; use one channel to condition the other's gain CV. Check exact zero/unity/inversion and confirm that offset survives zero gain.

## 2. Fader|Distr: two separate modules

Required: Fader|Distr A and Fader|Distr B. Identical control and jack layouts, with a clear variant label. A uses a fixed linear law; B uses fixed equal-power law. Neither has CV inputs, a CV amount control, or a law switch. Both perform simultaneous parallel distribution and crossfading from one large manual BIAS dial.

| Connector/control | Behavior |
| --- | --- |
| BIAS | X / 1 at minimum, balanced at center, Y / 2 at maximum |
| S | One mono DISTR signal source |
| 1 / 2 | Complementary outputs of S |
| X / Y | Two independent mono FADER inputs |
| Z | Crossfaded sum of X and Y |

All inputs independently default to zero. There is no input normalization or stereo-input processing. Initial BIAS is center.

With dial position p between zero and one, Linear uses weights 1-p and p; Equal Power uses cos(pi*p/2) and sin(pi*p/2). Apply the same weights to the panner and crossfader. At center each Linear path is 0.5, each Equal Power path approximately 0.707. Equal Power can boost correlated identical inputs at the midpoint; Linear preserves their level and also provides straightforward DC interpolation.

CPU plan: use native-rate processing for these linear signal paths. Cache weights while BIAS is stationary. On movement, update the target weights and perform a short, bounded smoothing transition; a simple coefficient interpolation briefly departs from exact equal-power behavior and should be auditioned. Do not evaluate trigonometry continuously at rest. Two instantiated modules incur two sets of host callbacks and routing work, but their arithmetic is small. Measure actual host cost before making numerical performance claims.

Panel: central oversized BIAS dial with X/1 and Y/2 markings. Separate FADER X/Y/Z and DISTR S/1/2 groups beneath it. Keep both variants geometrically identical so placing them side by side is predictable.

Trials: distribute one tone, fade two unrelated sources, fade identical sources, interpolate two reference voltages, operate both functions together, and check endpoint isolation and movement noise. No oversampling is proposed for these functions alone.

## 3. Push-Button Router

### Recommended form

Two independent A/B source selectors with large paired buttons. Each channel has A IN, B IN, and OUT. This supplies quick comparisons, manual articulation, and alternate control paths.

Per-channel controls:

- A and B buttons with an unambiguous selected-state indicator.
- LATCH / MOMENTARY switch.

Shared control: DIRECT / SOFT transition switch.

In LATCH mode, pressing A or B selects that source until another selection. In MOMENTARY mode, A is the resting source and holding B selects B; holding A forces A. If both buttons are held, A takes precedence. Releasing both returns to A. Entering MOMENTARY starts at A; returning to LATCH starts latched at A. These explicit rules avoid ambiguous behavior during mode changes or overlapping presses.

An unpatched selected input is zero, making the other button a manual mute or gate interruption. OUT does not retain the last voltage when its selected source is unpatched. Add no CV switching inputs in Series One.

DIRECT changes the selection immediately, preserving discrete gate behavior while allowing audio clicks. SOFT performs a short linear transition, initially 5 ms; this temporarily combines sources and is intended for audio. Do not use it where exact gate timing is required. A new selection during a transition starts from the current mixture rather than jumping back to an endpoint.

Initial settings: both channels select A, LATCH mode, DIRECT transitions. Patch recall restores latched choices; MOMENTARY always resumes at rest.

Trials: compare two tones; manually insert noise bursts; alternate two modulation sources; interrupt a gate with an unpatched input; audition rapid changes in both transition modes.

## 4. Rotary/Toggle Router

### Recommended form

A four-position selector with OFF, controlling two separate routing banks together:

- SOURCE bank: IN 1-4 feed one SELECTED OUT.
- DESTINATION bank: one COMMON IN feeds OUT 1-4, with all unselected outputs at zero.

The large selector chooses the same numbered position in both banks. They can be patched independently or used as a coordinated pair. Each bank has fixed-direction ports; this is not a bidirectional hardware jack emulation. There is no hidden connection between the banks.

Use a five-position dial labeled OFF, 1, 2, 3, 4 and a DIRECT / SOFT toggle with the same transition meaning as the Push-Button Router. In OFF, all outputs are zero. During a soft destination change, the old and new destinations briefly receive complementary gains; OFF fades to or from zero. Direct mode selects only the final requested position rather than emitting intermediate steps from a mouse drag.

Initial setting: OFF, DIRECT. Restore selector position on patch recall. No CV selection, clock stepping, or programmable memory in this Series One draft.

Panel: large selector above clearly separated SOURCE and DESTINATION banks; align port numbers to make the paired routing apparent.

Trials: compare four oscillators; choose one of four reference voltages; route a signal to one of four processors; couple source selection to destination selection; verify OFF and absence of leakage on unselected paths.

## Prototype order and completion criteria

1. Signal Processor: settle gain/CV scaling, DC handling, output headroom, and zero/unity behavior. It becomes a useful tool for evaluating the other modules.
2. Both Fader|Distr variants: validate their fixed gain laws, independent inputs, manual motion, and usable control travel.
3. Both routers: share a transition implementation while preserving their distinct manual interactions. Verify saved selections, momentary release, OFF behavior, and audio versus gate operation.
4. Laboratory Generator: establish the generator tuning controls and stable sine/triangle foundation; then develop Pulse/Sine, Sine/Random, and Deep Tone against it.
5. Character studies: audition several level-dependent audio voicings using the established sources and utilities. Keep level-matched comparisons and document which behavior belongs to each instrument.

Canonical Colorbox is now available at ../colorbox. The installed SDK is C:/ProgramData/Voltage/voltage.jar; use the existing validation tooling and matched Designer/exported-source workflow. See Development-Setup.md. Resolve panel proportions, signal conventions, and patch-state handling against these references before coding.

Before calling a prototype complete, check its defining signal relationships and patch-state behavior, then perform listening trials at ordinary and extreme settings. No final sound or usability claims should be made from this document alone.

## Main choices to revisit after review or patch trials

- Two identical Signal Processor channels versus a simpler single channel.
- PROCESS/VCA gain behavior and any future input-specific modulation scaling. Offset CV is excluded.
- Manual smoothing feel for both fixed-law Fader|Distr modules; shared BIAS and simultaneous operation are retained.
- Two A/B button selectors versus a larger button routing bank.
- Paired source/destination rotary routing versus separate selectors.

The recommendations above provide a concrete starting point; they do not expand the accepted collection beyond the utility family in the revised roadmap.

## CPU, bypass, and oversampling requirements

Follow Colorbox's direct host-bypass approach: minimal input/output routing, no processing-control reads, no advancing filters or oversampling, and no bypass crossfade that keeps DSP running. Reset stale processing histories once on resumption where necessary. Bypass still incurs host and jack-access work; it is not literally zero CPU.

Recommended bypass routing, pending panel implementation: each Processor IN passes unchanged to its OUT; button selectors use their cached selected source; rotary routing retains its cached selection/OFF; both Fader|Distr variants copy S to 1 and 2 and X to Z (Y is ignored). These are explicit proposals because multi-output instruments have no unique universal dry path. Cache routing choices outside the per-sample bypass path.

Use Colorbox's 2x processing as the first oversampling candidate only for stages that need it. Pure gain, offset, routing, and manual panning stay at native rate. Nonlinear audio coloration and demanding oscillator/modulator cases require specific aliasing and CPU evaluation. Do not increase the factor unless evidence warrants it.

Prototype implementation, checks, and remaining Designer work: [Signal Processor](../laboratory/signal-processor/README.md).
