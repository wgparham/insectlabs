# LM-21 Mk III Matrix Mixer — Artificial Acoustic Distance Generator

**Canonical user manual — approved 2026-10-08**  
**Release version:** `v1.0.1`  
**Format:** Mono, audio-rate matrix mixer  
**Panel width:** 6.4 inches

The LM-21 Mk III is a four-input, four-output mono matrix mixer with a separate signal path and ambience engine on each output row. It is imagined as a custom studio instrument built at WDR or RAI from repaired and repurposed equipment: a central routing desk with broad tone controls, transistor-like overload, and an artificial acoustic-distance effect. It is deliberately more expansive than a period-faithful utility mixer. The separate **minimixer** is intended to fill that simpler role.

This manual describes the approved v1.0.1 signal behavior. Functional build and listening tests passed; native-host CPU profiling remains unmeasured.

## Signal-flow overview

Each of the four inputs (I–IV) feeds four independently controlled crosspoints. Every output row (A–D) sums its four crosspoints without automatic normalization, then passes through its own MIX drive, BASS and TREBLE shaping, asymmetric character stage, and independent reverb engine. The row's DIST. control adds reverb to its dry signal. All four row outputs are also averaged to the FULL MIX courtesy output and passed through a separate gentle saturation stage.

```text
I ─┬─ A crosspoints ─ MIX drive ─ BASS/TREBLE ─ character ─┬─ A output
II ┤                                                       └─ A reverb return (DIST.)
III┤
IV ┘     [same independent signal flow for rows B, C, and D]

A, B, C, D ─ average ─ gentle saturation ─ FULL MIX courtesy output
MULT IN ─ buffered copies ─ MULT OUT 1, 2, 3
```

The matrix and all four rows are mono. There is no stereo linking, panning, or CV control in this design.

## Controls and connections

### Inputs I–IV

Connect four mono audio sources. Each input is available to all four output rows through the crosspoint matrix. An unconnected audio input contributes silence.

### Matrix crosspoints

The sixteen A–D by I–IV controls set how much of each input enters each output row. The current control range is bipolar. Centered at zero, a crosspoint is off; turning it toward the positive side adds the input normally, while turning it toward the negative side adds it with inverted polarity. The rows are summed directly, so several open crosspoints can build level and drive the downstream stages harder.

### BASS and TREBLE

Each output row has its own BASS and TREBLE control. Noon is the nominal center and retains the planned 984-like circuit coloration; it is not intended as a perfectly transparent setting. The first DSP approximation uses broad low- and high-shelf shaping, with a range of approximately −12 dB to +12 dB. The shelf corners are approximately 250 Hz for BASS and 2.5 kHz for TREBLE. These are the approved digital approximation values in v1.0.1.

### MIX A–D

Each row's MIX control sets its input drive from mute to +12 dB. It acts **before** the row's tone and nonlinear character stages, so raising it changes both output level and how strongly the row is driven. The taper places unity gain at the midpoint. A setting of zero mutes new signal into that row's dry and reverb paths; any stored reverb tail can continue to decay.

### DIST. A–D

Each DIST. control adds that row's reverb return to the dry signal. It ranges from no added ambience to the full internal return. It is an additive send/return amount, not a dry/wet crossfade, so the dry signal remains present as DIST. is raised. Each row has a completely independent reverb engine; changing or exciting one row does not intentionally feed the other row's reverb network.

### PERSPECTIVE

PERSPECTIVE is shared by all four reverb engines and sets decay length and the character of the simulated acoustic space. Its range is approximately 0.5–30 seconds. The first 60% of the knob travel spans 0.5–5.5 seconds; the final 40% stretches from 5.5 seconds to 30 seconds. At the long end, the tail progressively darkens, wanders slightly in pitch, and gains a small amount of recirculation compression. This deliberately makes extreme decay times feel aged and unstable rather than pristine.

The displayed tooltip reports the approximate decay time. Reverb decay is a digital model, not a measurement of a physical room or plate.

### Output A–D

Each jack outputs its independent mono matrix row after MIX drive, tone shaping, nonlinear character, and its additive DIST. return.

### FULL MIX courtesy output

FULL MIX averages the processed A–D outputs, then passes that average through a dedicated gentle saturation stage. Averaging controls the automatic level increase that would otherwise come from summing four full-level rows. The separate saturation adds a small amount of density. FULL MIX has no level control.

### MULT IN and MULT OUT 1–3

MULT IN is buffered to three copies at MULT OUT 1–3. This is a digital ideal-multiple convenience path and does not pass through the matrix or the row character stages.

### POWER switch and lamp

Turning POWER on starts a 13.6-second linear warmup of the A–D output amplitude. Turning it off applies a 2.1-second cooldown, then silences A–D and FULL MIX and clears the stored reverb and filter state. The indicator follows this envelope; its visual update is throttled to approximately 100 Hz. MULT remains live throughout. These long equipment-style power transitions are separate from host bypass.

## Practical patches

**One source to one output:** Patch a source to I. Set A/I positive and leave the other crosspoints at zero. Take the result from A.

**Distribute one source to several independent treatments:** Patch a source to I and open I's crosspoint on two or more rows. Set BASS, TREBLE, MIX, and DIST. independently on each row.

**Combine sources:** Patch sources to I–IV and open the desired crosspoints in one row. Because the row is not normalized, use MIX to set the operating level and drive.

**Polarity comparison:** Set a crosspoint positive on one row and negative on another to compare normal and inverted versions of the same source.

**Shared monitor feed:** Use FULL MIX for a level-managed combination of A–D, with its additional gentle saturation.

## Bypass

Host system bypass is abrupt, following the Laboratory series convention. In bypass, inputs I–IV map directly to outputs A–D respectively; FULL MIX is silent. The MULT IN-to-MULT OUT path remains active. All processing and power histories freeze during bypass; returning to active processing clears stale filter and reverb state and continues the power envelope from its frozen value. Bypass is not a transparent one-to-one routing mode for every matrix connection: it provides a predictable straight-through mapping for maintenance and comparison.

## DSP behavior and implementation notes

- Panel controls are smoothed to reduce zipper noise while moving controls.
- Input and row output paths use very-low-frequency AC coupling, approximately 3.3 Hz, to suppress DC while retaining the audio band.
- Centered BASS/TREBLE shaping uses broad shelves. The current model approximates the intended 984-like response and is not a transistor-level circuit simulation.
- The character stages use asymmetric soft saturation rather than a component-by-component circuit solver.
- The reverb uses four independent mono eight-delay-line scattering networks. PERSPECTIVE controls their feedback, damping, modulation, and long-tail aging behavior.
- The current delay network allocates memory and performs interpolation for each of its 32 delay lines. CPU use must be profiled in Voltage Modular on the user's machine before the design is finalized.
- The implementation currently assumes a 48 kHz processing rate. Confirm the host's rate behavior before release or operation at other sample rates.

## Current verification status

Both source forms passed Java 17 compilation against the installed Voltage Modular SDK with all warnings treated as errors. Automated tests exercise the actual processing and control callback bodies with mocked SDK controls: every matrix route and polarity, row/reverb independence, FULL MIX, MULT, power, bypass/resume, typed control edits, preset-restoration notification, and hot-signal/long-tail stability. Active audio output remained sample-for-sample identical during cleanup.

The user previously auditioned the mixer successfully. The user corrected a small panel-text mistake, recompiled and tested this pair, and approved canonization on 2026-10-08. Dedicated automation/undo stress testing remains unmeasured. Host CPU use has not been measured. Canonical release: v1.0.1.

## Reference character

The 984 informs the four-by-four matrix and row EQ/output concept. The CP3 informs practical studio routing and analog mixer character. LM-21 is a custom composite instrument inspired by those references; it does not claim to be a circuit-accurate replica of either mixer.
