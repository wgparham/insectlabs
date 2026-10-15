# Synthi 100 filter research — 2026-10-14 to 2026-10-15

Status: research packet updated after SVF 1.0.0 was canonized on 2026-10-15. The earlier dated sections preserve the decision trail; the final section records the implemented direction.

## Primary sources

- [Shared Synthi 100 scan](../../references/ems_synthi_100_sch.pdf): PDF page 13, Hilton Electronic (Wareham) drawing 32/01, HIGH PASS FILTER SYNTHI 100; says circuit duplicated on PCB 34/01. Pages 37–38, wiring drawing 31/07, show dual HPF cards and separate downstream log modulators. Pages 2 and 6 contain equipment specifications.
- [Digitana/Portabellabz Mk1 recreation](https://www.synthi.co.uk/synthi-100-mk1-dual-high-pass-filter-rack-module/): manufacturer describes early Mk1 diode-string frequency control, resonance through self-oscillation, two independent filters, series/off/parallel coupling, input level and frequency CV controls. Series permits an additional external signal at input 2. Parallel routes input 1 to both filters; it does not imply summed outputs.

## Revision distinction

The page-13 circuit uses 3080 OTAs A5/A6, two 330 pF integrating capacitors C2/C3, 2N5163 FET buffers, 741 amplifiers, and a matched/glued 2N588 transistor pair. T4/T5 are selected for 2.5–3.0 V pinch-off. It provides HP/BP/LP output links. Topology is consistent with a two-integrator state-variable filter; derive the complete transfer and gain before implementation. This is not the diode-string circuit described by Digitana. Exact historical revision relationship remains unresolved.

The brochure on page 2 gives filter range 5 Hz–20 kHz and 0.5 V/octave; HP is described as complementary to LP. Page 6 lists maximum stable Q 20, LP attenuation 12 dB for the first octave and 18 dB/octave thereafter, and roughly 2 ms/octave maximum pitch-change response time; HP again refers to the complementary behavior. These broad specifications cannot be indiscriminately assigned to the different page-13 topology. In particular, do not add a third pole merely to reconcile a mixed-document set.

## Mockup and proposed direction

Two columns each have input level (silver), CV amount (green), frequency (blue), response (gold), audio input, cutoff CV input and output. Central three-state switch is named Series / Off / Paralell. Suggest SERIES / INDEPENDENT / PARALLEL labeling to distinguish coupling-off from bypass. Preserve independent outputs and externally injected input 2 in series mode. Exact coupling tap and summing gains remain unspecified by the product prose.

Recommend a low-cutoff startup (current frequency defaults are normalized 1), moderate input, zero response, independent mode. Clockwise cutoff should rise exponentially. Historical 0.5 V/oct is a candidate at full CV amount, with attenuator for practical range; external jack calibration must account for the selected revision/interface. Optional response CV would add useful capability but is not required to match the rack workflow. Additional BP/LP outputs are justified only if choosing the OTA topology.

DSP suggestion: choose circuit revision before coding. For the documented OTA network, derive the two-integrator loop and model its internal gain/limiting; evaluate nonlinear feedback oversampling and resonance startup/settling. Output-only saturation would not capture loop-dependent resonance behavior. No amount of component reuse establishes exact nonlinear voicing without additional derivation or measurement.

Pair inspection: exported and embedded code differ only by a SetStateInformation(stateInfo) delegation in the variation-state setter. Both processing regions and custom variables are empty. No functional DSP exists; reconcile the harmless skeleton discrepancy when implementation begins.

## Follow-up: Mk1 evidence and target

See [Digitana photos and demos](DIGITANA-MK1-REFERENCES.md). The reported replacement history is explicitly unverified forum/friend lore. Prior OTA-first implementation advice is superseded: first investigate the early diode-controlled HPF; retain the OTA circuit as comparative research. No DSP topology has been selected.

## Panel versus topology clarification — 2026-10-14

The supplied high-resolution synthi100_hero.jpg shows filters 1–4 labeled LOW PASS / RESONATOR / OSC and 5–8 HIGH PASS / RESONATOR / OSC, each with frequency, response and level controls. Visible frequency endpoints are 5 Hz and 15 kHz on this panel, differing from the 20 kHz brochure endpoint. The image does not establish the installed circuit revision.

Drawing 32/01 explicitly supports a state-variable core configured as a dedicated high-pass output: A3 is the summing stage/high-pass node; A5/C2 and A6/C3 are two OTA integrators buffered by T4/T5; their intermediate and final nodes feed the band-pass and low-pass links. R14 returns the final state to A3; T3 and associated resistors provide response-dependent feedback involving the intermediate state. A4 buffers/amplifies the selected output. The high-pass link is drawn connected, with BP/LP alternatives dotted. These are board configuration links, not evidence of a front-panel multimode selector.

Under the ideal small-signal two-integrator model, HP has numerator proportional to s^2 and a quadratic denominator, giving 12 dB/octave low-frequency asymptotic attenuation. Control-path capacitors and supply bypassing do not add a third tracking audio pole. Exact gains and damping require full derivation; finite device behavior can depart from this ideal. An 18 dB/octave response is not established by this drawing. The broader brochure's complementary HP/LP claim and the different diode recreation remain separate evidence.

An unchanged HPF panel is compatible with an SVF core wired to its HP output, or with replacement boards retaining the existing interface. Therefore photo labels neither prove that all units always used SVFs nor disprove internal revision changes. The claim that this OTA board was a later factory replacement remains unverified; earlier discussion must not be read as established chronology. The diode-array recreation also cannot be classified as ladder versus state-variable solely from device types or board photographs.

## LPF versus HPF board wiring — 2026-10-14

In the shared 63-page Synthi 100 scan, PDF page 36 / drawing 31/06 identifies LP filters 1–4 as the FILTER PART of board 34/00, at slots 1G/1H/1L/1M. Frequency control is pin 14, response pin 15, signal in pin 17 and signal out pin 19. An external 34/17 summing buffer combines manual frequency and control input; downstream 34/15 log modulators provide level control.

PDF pages 37–38 / drawing 31/07 instead identify dual HPF boards 34/01 at slots 1P and 1R. Each carries two filters, with separate frequency-pot and current-control inputs. PDF page 34 / board-location drawing 31/01 independently confirms this distinct board allocation. Thus the documented instrument does not simply use eight instances of the same 34/01 board with different output links.

The scan's detailed component drawings start at PDF page 12 with the ring modulator, EMS 32/00/2, followed on page 13 by the HPF 32/01. The LPF component-level sheet for board 34/00 was not found in the reviewed page inventory. The numbering suggests a missing companion sheet, but its contents cannot be inferred. The wiring establishes different assemblies and interfaces; it does not establish the LPF's transistor/diode/OTA topology or prove any Moog-style implementation. The 34/01 core's LP option is a capability of that HPF design, not evidence that factory LP filters 1–4 used it.

## AKS filter found; proposed dual SVF direction — 2026-10-14

The archived emsa_synthi-aks_sm.pdf contains a component-level filter schematic on PDF page 73, labeled FILTER P.C. BOARD B, VCS 3/ISS3/CCT5. The drawing explicitly labels its ladder filter/signal phase splitter, antilog frequency-control summer, FET feedback control and differential signal amplifier. Diodes D10–D23 and bridging capacitors C33–C36 show a diode ladder, not the conventional Moog transistor ladder. Frequency, response and level pots are shown. This is verified AKS/VCS3-family reference material; equivalence to the Synthi 100's missing 34/00 LPF circuit is not established.

User is considering combining the planned Synthi 100 HPF, subsequent LPF and later BPF roles into a dual multimode EMS-inspired SVF based on the documented 34/01 circuit. This remains design discussion, not permission to change panel or implement DSP. Exposing its documented HP/BP/LP nodes is a defensible extension of that circuit, but should not be described as a reconstruction of the separate Synthi 100 34/00 LPF or a Polivoks.

Suggested panel direction: retain two cores with frequency, response, input level and CV depth, expose simultaneous HP/BP/LP outputs per core, retain independent/parallel/series routing. Series mode needs an explicit choice of which first-channel response feeds channel 2; a selector and a selected/main output per core would make this clear, or retain fixed HP chaining as a deliberately narrower workflow. The three taps share core frequency and response. Preserve/derive relative tap gains and signs before deciding any normalization; the hardware links feed one shared output amplifier, so exposing all three requires defined equivalent buffering/scaling. Additional output reads are much cheaper than three separately processed filters, although nonlinear oversampling and feedback solving dominate the eventual CPU cost.

## Canonical SVF direction — 2026-10-15

The user selected the documented Synthi 100 HPF state-variable core as a dual filter with simultaneous HIGH, BAND and LOW outputs. The design combines the anticipated separate HPF, LPF and BPF roles in one module; it does not purport to clone the separately wired 34/00 LPF board.

Both channels have input level, exponential cutoff, CV depth and response controls. The three-position mode switch selects SERIES (up), independent (middle) or PARALLEL (down). In series, a second three-position selector sends core 1 HIGH (up), BAND (middle) or LOW (down) into core 2. A patched IN 2 overrides normalized routing. All six taps remain available. This preserves the chosen external workflow while exposing the three documented links from the 34/01 core.

The user approved the wired module's sound and canonized SVF 1.0.0. Its nonlinear OTA approximation, feedback/resonance law, saturation and solver are fully described in the versioned developer notes. Those are deliberate DSP choices, not measured historical curves. The source pair, image and release evidence are archived under `../../SVF/versions/1.0.0/`; shared original schematic, photo and demo sources remain in this research area.
