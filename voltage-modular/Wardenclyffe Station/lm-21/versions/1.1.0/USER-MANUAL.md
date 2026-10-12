# LM-21 Mk III Matrix Mixer — Artificial Acoustic Distance Generator

**Canonical user manual — v1.1.0, 2026-10-10**
**Release version:** `v1.1.0`
**Format:** Mono, audio-rate matrix mixer
**Panel width:** 6.4 inches

The LM-21 Mk III is a four-input, four-output mono matrix mixer with a separate signal path and ambience engine on each output row. It is imagined as a custom studio instrument built at WDR or RAI from repaired and repurposed equipment: a central routing desk with broad tone controls, transistor-like overload, and an artificial acoustic-distance effect. It is deliberately more expansive than a period-faithful utility mixer. RM1010 fills the compact mixing role; the earlier minimixer proposal was superseded and is not a separate module.

This manual describes LM-21 v1.1.0. Automated source, project-integrity, and routing checks passed. The user confirmed that this exact pair compiled, ran, and passed audio testing; host CPU profiling is not recorded.

## Signal-flow overview

Each of the four inputs (I–IV) feeds four independently controlled crosspoints. Every output row (A–D) sums its four crosspoints without automatic normalization, then passes through its own MIX drive, BASS and TREBLE shaping, asymmetric character stage, and independent reverb engine. The row's DIST. control adds reverb to its dry signal. The four row outputs are averaged with the dry LINK submix at the FULL MIX courtesy output, then passed through separate gentle saturation. In 2D mode, LINK also feeds channel D’s reverb; that wet return is heard at D and in FULL MIX.

```text
I ─┬─ A crosspoints ─ MIX drive ─ BASS/TREBLE ─ character ─┬─ A output
II ┤                                                       └─ A reverb return (DIST.)
III┤
IV ┘     [same independent signal flow for rows B, C, and D]

A, B, C, D ─ average ─┐
LINK ───── dry ────────┴─ gentle saturation ─ FULL MIX
LINK ──[2D up]── D reverb ── D output and FULL MIX
MULT IN ─ buffered copies ─ MULT OUT 1, 2, 3
```

The matrix, LINK submix and all four rows are mono. LINK is a submix feed, not stereo linking; there is no panning or CV control.

## Controls and connections

### Inputs I–IV

Connect four mono audio sources. Each input is available to all four output rows through the crosspoint matrix. An unconnected audio input contributes silence.

### LINK input and 2D switch

LINK adds an AC-coupled mono submix directly to FULL MIX. It does not enter the crosspoint matrix or add dry signal to outputs A–D. With the 2D switch down (default), LINK stays dry. With 2D up, LINK also feeds the existing channel D reverb network after the D row’s matrix, MIX, tone and character stages. This shared wet return appears at output D and contributes to FULL MIX through the normal A–D average; only LINK’s dry component is added directly to FULL MIX.

DIST. D scales the LINK send into the shared reverb and the wet return, as well as controlling the normal D reverb return. Thus the LINK reverb level rises more strongly than linearly as DIST. D is raised. The D reverb tail is shared with row D.

### Matrix crosspoints

The sixteen A–D by I–IV controls set how much of each input enters each output row. The current control range is bipolar. Centered at zero, a crosspoint is off; turning it toward the positive side adds the input normally, while turning it toward the negative side adds it with inverted polarity. The rows are summed directly, so several open crosspoints can build level and drive the downstream stages harder.

### BASS and TREBLE

Each output row has its own BASS and TREBLE control. Noon is the nominal center and retains the planned 984-like circuit coloration; it is not intended as a perfectly transparent setting. The first DSP approximation uses broad low- and high-shelf shaping, with a range of approximately −12 dB to +12 dB. The shelf corners are approximately 250 Hz for BASS and 2.5 kHz for TREBLE. These are the digital approximation values retained from v1.0.1.

### MIX A–D

Each row's MIX control sets its input drive from mute to +12 dB. It acts **before** the row's tone and nonlinear character stages, so raising it changes both output level and how strongly the row is driven. The taper places unity gain at the midpoint. A setting of zero mutes new signal into that row's dry and reverb paths; any stored reverb tail can continue to decay.

### DIST. A–D

Each DIST. control adds that row's reverb return to the dry signal. It ranges from no added ambience to the full internal return. It is an additive send/return amount, not a dry/wet crossfade, so the dry signal remains present as DIST. is raised. Each row has a completely independent reverb engine; changing or exciting one row does not intentionally feed the other row's reverb network.

### PERSPECTIVE

PERSPECTIVE is shared by all four reverb engines and sets decay length and the character of the simulated acoustic space. Its range is approximately 0.5–30 seconds. The first 60% of the knob travel spans 0.5–5.5 seconds; the final 40% stretches from 5.5 seconds to 30 seconds. At the long end, the tail progressively darkens, wanders slightly in pitch, and gains a small amount of recirculation compression. This deliberately makes extreme decay times feel aged and unstable rather than pristine.

The displayed tooltip reports the approximate decay time. Reverb decay is a digital model, not a measurement of a physical room or plate.

### Output A–D

Each jack outputs its mono row after MIX drive, tone shaping, nonlinear character, and its additive DIST. return. In 2D mode, D also carries the LINK signal’s wet reverb return; LINK dry remains outside the row outputs.

### FULL MIX courtesy output

FULL MIX averages the processed A–D outputs, adds the dry LINK input, then passes the result through a dedicated gentle saturation stage. Averaging controls the automatic level increase that would otherwise come from summing four full-level rows. The separate saturation adds a small amount of density. FULL MIX has no level control.

### MULT IN and MULT OUT 1–3

MULT IN is buffered to three copies at MULT OUT 1–3. This is a digital ideal-multiple convenience path and does not pass through the matrix or the row character stages.

### POWER switch and lamp

Turning POWER on starts a 13.6-second linear warmup of the A–D outputs and dry LINK contribution. Turning it off applies a 2.1-second cooldown, then silences A–D and FULL MIX and clears the stored reverb and filter state. The indicator follows this envelope; its visual update is throttled to approximately 100 Hz. MULT remains live throughout. These long equipment-style power transitions are separate from host bypass.

## Practical patches

**One source to one output:** Patch a source to I. Set A/I positive and leave the other crosspoints at zero. Take the result from A.

**Distribute one source to several independent treatments:** Patch a source to I and open I's crosspoint on two or more rows. Set BASS, TREBLE, MIX, and DIST. independently on each row.

**Combine sources:** Patch sources to I–IV and open the desired crosspoints in one row. Because the row is not normalized, use MIX to set the operating level and drive.

**Polarity comparison:** Set a crosspoint positive on one row and negative on another to compare normal and inverted versions of the same source.

**Shared monitor feed:** Use FULL MIX for a level-managed combination of A–D and the LINK submix, with its additional gentle saturation.

**Daisy-chained submix:** Connect another LM-21 FULL MIX or a submixer output to LINK. It joins this module’s FULL MIX without entering its matrix. Raise DIST. D and move 2D up to add the shared D reverb.

## Bypass

Host system bypass is abrupt, following the Laboratory series convention. In bypass, inputs I–IV map directly to outputs A–D respectively; FULL MIX is silent, including LINK. The MULT IN-to-MULT OUT path remains active. All processing and power histories freeze during bypass; returning to active processing clears stale filter and reverb state and continues the power envelope from its frozen value. Bypass is not a transparent one-to-one routing mode for every matrix connection: it provides a predictable straight-through mapping for maintenance and comparison.

## DSP behavior and implementation notes

- Panel controls are smoothed to reduce zipper noise while moving controls.
- The four matrix inputs and LINK use very-low-frequency AC coupling, approximately 3.3 Hz, to suppress DC while retaining the audio band.
- LINK dry is summed after the A–D average and follows POWER; its 2D send enters channel D’s reverb network. The wet signal is returned at D, then appears in FULL MIX at the normal quarter-weight row average. DIST. D scales both the LINK send and shared wet return.
- Centered BASS/TREBLE shaping uses broad shelves. The current model approximates the intended 984-like response and is not a transistor-level circuit simulation.
- The character stages use asymmetric soft saturation rather than a component-by-component circuit solver.
- The reverb uses four independent mono eight-delay-line scattering networks. LINK shares D’s network in 2D mode; no fifth reverb network is allocated. PERSPECTIVE controls feedback, damping, modulation, and long-tail aging behavior.
- The current delay network allocates memory and performs interpolation for each of its 32 delay lines. CPU use must be profiled in Voltage Modular on the user's machine before the design is finalized.
- The implementation currently assumes a 48 kHz processing rate. Confirm the host's rate behavior before release or operation at other sample rates.

## Current verification status

Both exported and embedded source forms compiled with `--release 17 -Xlint:all -Werror` against the installed Voltage SDK. The automated harness exercises actual processing and control callbacks with mocked SDK controls: all 32 signed matrix routes, row/reverb independence, FULL MIX, MULT, power, bypass/resume, typed edits, preset restoration, five-second reverb stability, and LINK dry/2D/DISTANCE routing. The earlier v1.0.1 archive retains the three 30-second reverb trials.

The user authorized canonization on 2026-10-10 and confirmed on 2026-10-11 that this exact pair compiled, ran, and passed audio testing. Saved-patch/automation/undo checks and host CPU profiling are not recorded. The earlier v1.0.1 user approval remains documented in its immutable archive.

## Reference character

The 984 informs the four-by-four matrix and row EQ/output concept. The CP3 informs practical studio routing and analog mixer character. LM-21 is a custom composite instrument inspired by those references; it does not claim to be a circuit-accurate replica of either mixer.
