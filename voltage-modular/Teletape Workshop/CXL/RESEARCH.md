# CXL: dark plate design research

Date: 2026-10-18. Status: research and design rationale for canonical CXL 1.0.0. This is an inspired software plate reverb, not a circuit-level EMT or Lexicon recreation.

## Intent and decisions

The user wanted a dark plate with an early Lexicon/used EMT character, rather than a component-exact reproduction. The early Designer scaffold and scratch document were design leads; the implementation decisions below record how the release developed.

During this discussion the user selected:

- The extended mode should have **audible grain but a continuous tail**. Listening rejected full 8-bit dither as too noisy and a 9-bit effective step as too clean. Canonical 1.0.0 uses a 180-level divisor, an approximately 8.5-bit effective step between those trials.
- Stereo wet input should use **separate injections into one shared tank**, retaining input differences. This is a software extension to the historical summed-driver arrangement.


## Source inventory and evidence

### EMT 140 service collection

[Supplied EMT 140 collection](references/emt140.pdf), 20 PDF pages. PDF page numbers below are one-based; several original drawings are landscape/rotated.

- Pages 3-8: setup, transport and installation. Page 5 describes the handwheel from 0.8 closed to 5.5 seconds open, a roughly 1/8-inch minimum clearance, and explicitly says the absorber must not touch the vibrating plate. This is damping through proximity, not mechanically clamping the plate.
- Page 9, original operating page 2: decay calibrated at **500 Hz**, nominal continuous range **0.8-5 seconds**. The long setting has a low-frequency rise in decay time and falls to about **1.5 seconds at 10 kHz**. Short settings are described as having nearly flat decay-time response.
- Page 10: stationary sine tones are unsuitable for level adjustment because small frequency changes produce large changes through the resonances. The procedure calls for warble tones and slow metering. This is evidence of modal response, not random pitch modulation.
- Page 12, drawings **X140-7 / X140-6A**, dated 25 March 1966: decay curves for nominal **1, 2 and 4.5 seconds at 500 Hz**. The longest curve is roughly 10 seconds near 100 Hz; the short curve is below 1 second there. Values away from labeled points are visual estimates. The lower graph is a tolerance envelope of total amplitude response at the two-second setting, not an individual device's exact measured EQ.
- Page 13: separate driver and output EQ curves. The driver rises approximately **18 dB by 15 kHz**, relative to its low-frequency baseline. Playback rises about **10 dB around 80 Hz**, relative to its dip near 1.5 kHz, with a smaller high-frequency rise. These are electronic-section curves; do not multiply them into the desired final wet spectrum without accounting for the plate and transducers.
- Pages 14-18: send/return, remote control, motor/relay and installation wiring. The studio connection diagram puts optional filtering in the reverb send, separately from dry program audio.
- Page 19: **V54/S** stereo tube amplifier: E81L driver with input/output transformers, and two pickup chains using **EF804S, EF804S and E80CC**. RC equalization and feedback are visible. The mere presence of tubes does not establish a particular clipping curve, asymmetry or distortion amount.
- Page 20: **EMT 162** solid-state stereo amplifier: two input stages combine into one excitation path, followed by two separate playback paths. Identifiable parts include **BC107, BC177, 2N4030/4033, 2N4921, 2N4918, 2N3053/3055 and microA709C** devices. This is a different electronics implementation, not evidence that the mechanical plate itself changed into a different algorithm. No detailed compressor threshold, ratio or timing has been extracted from this scan.

### EMT 140 TS, April 1971

[Supplied manufacturer brochure](references/EMT_140_ts_1971.pdf), eight pages. This is a product brochure with a block diagram, not a complete second service manual.

- Page 2: describes approximately **5 seconds at 500 Hz** without additional damping and about **1.5 seconds at 10 kHz**, with longer low-frequency decay. It also describes strong local frequency-response peaks and valleys.
- Pages 3-4: a moving-coil driver excites a suspended steel sheet; two ceramic contact pickups are at different distances from the driver. Bending vibrations produce reflections whose density grows with time. The nearby porous damping plate changes decay.
- Page 6: two inputs are summed to an M signal to drive the plate; stereo is obtained from the two pickups. The brochure's description of the outputs as entirely uncorrelated is a manufacturer characterization, not a guarantee of zero correlation at every frequency or instant.
- Page 7: the **EMT 162** combines two inputs before the driver and includes a **compressor intended to prevent drive overload**. This supports a driver-dynamics model; it does not support intentionally distorting every pass through an artificial feedback tank. The compressor's numerical settings remain unknown.
- Page 8: technical table specifies **1-4 seconds at 500 Hz**. Retain this alongside the brochure's approximate five-second undamped example and the earlier service ranges; do not merge all of them into one supposedly universal calibration.

### Digital plate reference

[Dattorro, 1997, Effect Design Part 1](references/Dattorro-1997-EffectDesign-Part1.pdf), JAES 45(9), pp. 660-684. Public mirror retrieved 2026-10-17: <https://freeverb3-vst.sourceforge.io/doc/EffectDesignPart1.pdf>. The author-hosted Stanford link was blocked to the web reader. The archived file is the published paper, not third-party implementation code.

Reverb details: Fig. 1 on PDF page 3, Table 1 on page 4 and Table 2 on page 6. The reference rate is **29761 Hz**. Four input diffusers feed a cross-coupled tank containing four more diffusers and four long delays. Signed, distributed output taps and controlled delay modulation are important. The paper distinguishes input bandwidth from filtering inside the feedback loop. This is a useful published plate-class design, not the Lexicon 224's source code.

### Lexicon workflow and conversion

[User-supplied Medium article](https://medium.com/@kafedrazvuk/how-the-lexicon-224-reverb-works-e5c3180cdf32), Olexandr Pasichnik, 8 April 2026. Primarily a guide to the UAD emulation and its programs/controls, not a circuit analysis. Useful leads include separate treble decay, diffusion and modulation; its statements about firmware, historical code or converter behavior require primary corroboration.

[Universal Audio's licensed 224 product description](https://www.uaudio.com/uad-plugins/reverbs/lexicon-224.html) explicitly describes **12-bit gain-stepping converters**. A uniform fixed-step 12-bit quantizer is therefore insufficient to claim faithful converter reproduction. No original Lexicon converter circuit has been verified in this research pass. The proposed 8-bit extended mode is a CXL design choice, not a verified EMT or Lexicon feature.

## Scratch-code assessment

The notes contain useful ingredients, but should not be copied directly into the module:

1. **Modulated delay addressing is wrong for its stated purpose.** For an N-sample circular buffer, reading at `writeIndex` before writing supplies the N-sample-old value. Reading at `writeIndex - 6`, however, supplies the six-sample-old value, not N+6. The modulated all-pass therefore collapses from its nominal long delay to a few samples. Use separate capacity and nominal delay, with space for the complete interpolation/modulation excursion.
2. The example has only two tank all-passes and two long delays. It omits the second diffuser and final delay in each arm of the published network. The constants, including 1816 and 4253, and the listed 48 kHz matrix do not reproduce Fig. 1. Different lengths may sound useful, but are a new design requiring audition.
3. The two-output sum of all-pass output and damped delay is not the paper's distributed, signed tap matrix. The topology of the stereo outputs deserves as much attention as the loop lengths, especially when folding down to mono.
4. The proposed pre-filters are configured in `setupDSP()` but never used in the shown `processSample()`. Their names do not establish a functioning vintage bandwidth stage. Bit depth alone also does not establish bandwidth.
5. A maximum feedback coefficient of 0.98 is not a decay-time specification. Loop duration, diffuser group delay, feedback placements and frequency-dependent losses affect the result. Nominal RT60 conversion needs measurement across decay/damping settings.
6. The static scratch example hardcodes 48000 Hz and uses incompatible placeholder names/callback casing. The later setup example scales lengths, but rounding everything up to the next prime is not proof against ringing and alters any carefully chosen tap relationships.
7. The initial Java export and embedded VMOD were unwired. The current source pair contains the canonical DSP; export/embedded differences are Designer's generated user-region comments and anchors, not competing algorithms.

## Proposed audition architecture

This section is engineering judgment, not a hardware finding.

1. Preserve full-bandwidth dry audio independently of the wet path.
2. Wet input gain drives a shaped excitation stage with restrained overload compression. Use the EMT curves to guide the drive/pickup relationship; do not paste the full driver treble boost onto an arbitrary tank without compensation.
3. Use separate diffused left/right injection into one coupled plate tank when both inputs are patched. Either input alone excites the complete plate at the same mono level. Avoid simply summing antiphase stereo sources to silence before diffusion.
4. Start from the complete published digital plate structure, with intentional departures documented. Choose high onset density, distributed stereo pickups and subtle motion. Do not make obvious chorusing the only way to obtain a smooth decay.
5. Anchor DECAY near 500 Hz and deliberately change the bass/mid decay relationship as the virtual absorber opens. DAMP adds adjustable high-frequency loss *inside* the tank, allowing an audible onset whose tail becomes darker. Input bandwidth and output pickup EQ serve different purposes.
6. Audition a normal plate range near **0.8-5 seconds** and an extended range tentatively **5-30 seconds**. Those are proposed CXL ranges, not all-variant EMT specifications. The extended range may be changed after listening.
7. Keep the first core at floating-point precision; model the converters around the wet path, with level-dependent gain/companding considered for sustained low-level detail. Choose quantization/dither/noise shaping deliberately. Fixed-step output rounding can abruptly extinguish a quiet tail; quantizing every feedback write can instead cause persistent tones, hiss or stuck decay. Neither meets the selected continuous-grain target automatically. Feedback precision reduction is a separate audition decision.
8. Proposed panel: **DECAY, DAMP, PREDELAY, DRIVE, MIX, OUTPUT**, plus the existing two-position plate/extended-character switch. PREDELAY is the most useful addition to the existing rough controls; width and diffusion can initially be voiced internally. Independent DAMP is a software extension to a single mechanical damper.
9. Begin with a native-rate tank. If a nonlinear driver needs oversampling, apply it locally, accounting for filter latency. Oversampling the entire delay network is not automatically useful. If reduced internal sample rate is auditioned, use intentional antialias/reconstruction filtering rather than casual sample dropping. Longer decay does not inherently demand more delay memory; linking length to lower bit depth is a chosen musical consequence.

Existing `reverb-primitives/ReverbScMono` is an eight-line scattering core from the LM-21 prototype. It remains useful as a comparator or utility, but is not the proposed plate tank and should not be substituted merely to reuse code.

## Canonical implementation and limits

The canonical source uses a Dattorro Fig. 1-family cross-coupled stereo tank at 48 kHz with four input diffusers per lane, two modulated early tank all-passes, two fixed late all-passes, four delay sections and distributed signed stereo pickup taps. It is a new CXL voicing, not EMT or Lexicon firmware. Detailed implementation and maintenance notes are in [developer notes](DEVELOPER-NOTES.md); operation is in the [user manual](versions/1.0.0/USER-MANUAL.md).

The extended mode was auditioned at full 8-bit, softened 9-bit and the selected 8.5-bit effective step. The user approved the midpoint on 2026-10-18. The converter-only tone test measured its 6.8 kHz pickup-filtered error at 0.000585 RMS, between 0.000824 for 8-bit and 0.000407 for 9-bit. Tank decay fit, CPU and exciter alias-proxy limitations are recorded in the [DSP test report](versions/1.0.0/tests/DSP-TEST-REPORT.md). These local headless tests do not certify standardized full-band RT60, native host CPU use or isolated alias energy.

The first proposal to oversample the nonlinear driver was not selected. The 8x-rate comparison is only an alias proxy; native-rate DSP was accepted by listening. Publication remains deferred under the [series publication plan](../PUBLICATION-PLAN.md).

The pair was preserved during inspection. Baseline SHA-256:

```text
cxl.java cf25cd076aa1686549810090cf61f84c798767a9684f5b96eaa439e954242890
cxl.vmod 25b30d06a48fdc22b1d62fcca77f7769dc8f3732125e520892d22ace0eec1abc
emt140.pdf 495edd8a1a8e588d1506c71997c0cd0e9ea836c9c1afe6be445fc8e16f688b92
EMT_140_ts_1971.pdf b709ea405733931829fd77a988d102f9a4cf9615e9719de56266d0117245e25f
Notes Towards A CXL.md 8af6e3b128ceea80691e4e358931a2830b864eb33d4f55789f1e4877d7f4d1ea
Dattorro-1997-EffectDesign-Part1.pdf 5e25cb45f7ea62f7f10ea401a4fd9e6514fdc13b996f0fa0e3933aeaefc77b83
```

These hashes identify the inspected material, not release certification. Original sources and notes retain their authorship; archiving the paper does not relicense it as InsectLabs code.

## Canonical release record

The panel carries PRE-DELAY, DAMP, DECAY, MIX, GAIN, AMP, a 12-bit/extended-character switch and stereo/mono jacks. The canonical version is [CXL 1.0.0](versions/1.0.0/README.md); the native user audition approved the extended-mode grain and module behavior. The [release review](versions/1.0.0/REVIEW.md) distinguishes that user confirmation from the limited automated checks. `Notes Towards A CXL.md` is retained under `research/` as historical scratch material, not executable code.
