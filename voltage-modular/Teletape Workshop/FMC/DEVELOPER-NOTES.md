# FMC developer notes — 1.0.0

FMC is a four-channel, four-voice modulation and delay instrument. The release source pair and panel are [`fmc.java`](versions/1.0.0/fmc.java), [`fmc.vmod`](versions/1.0.0/fmc.vmod), and [`fmc_hero.png`](versions/1.0.0/fmc_hero.png). See the [user manual](versions/1.0.0/USER-MANUAL.md), [research](research/Design-Research.md), [references](references/README.md), [review](versions/1.0.0/REVIEW.md), and [validation record](versions/1.0.0/VALIDATION.txt).

## Control and signal behavior

Each lane has an audio input/output, four shared A–D knobs, a four-way voice selector, a Hold toggle and a rate-CV input. Default selector positions are channel 1 BF-2, channel 2 TR-2, channel 3 DM-2 and channel 4 DD-2. All A–D controls span 0–1. Their current units and typed-value conversion depend on the selected voice; tooltip and `EditComponentValue()` code must be updated together if a voice mapping changes.

- BF-2: A rate (0.0625–10 Hz), B depth, C center delay (1–13 ms), D feedback/resonance.
- TR-2: A coupled rate/depth macro, B waveform interpolation, C depth ceiling, D rate span.
- DM-2: A delay time (20 ms–2.4 s), B feedback/intensity, C dry/wet, D grit/compander/hiss amount.
- DD-2: A delay time (12.5 ms–6.4 s), B feedback, C dry/wet, D quantizer resolution (16 toward 8 bits).

Rate CV is interpreted as ±1 normalized position for ±5 V. It modulates BF-2 and TR-2 rate only. Hold suppresses new input and sustains delay-loop repeats for DM-2/DD-2; BF-2/TR-2 ignore it. MIX is half the sum of the four channel outputs. During host bypass the four channel routes are direct and MIX remains the half-sum of the raw inputs; the core resets its histories on resume.

## Implementation map

- `Initialize()` connects module controls and looks up current values; `Notify()` updates UI changes; `ProcessSample()` handles patched and retained-tail channels.
- `ProcessBypassedSample()` routes direct audio without touching DSP controls/CV and preserves MIX behavior.
- The nested `FmcCore` contains four independent per-channel state machines for the flanger, tremolo, analog delay and digital delay. It uses ring-buffer interpolation for delays, feedback filtering/limits, delay-time smoothing, and bounded input/control sanitization.
- Delay buffers and state arrays allocate at initialization; there are no per-sample object or buffer allocations. Each channel owns four 131072-sample double arrays plus the BF ring and detector history (about 4.1 MiB per lane, 16.3 MiB total for four lanes, plus smaller state).
- The core is constructed at 48 kHz. Four headless JDK 27 microbenchmark runs measured 1.13–1.55 μs per frame across four active voices. This is not target-host CPU, latency or real-time qualification.

## Modeling boundaries

The voice names and circuit references indicate inspiration and workflow. The DM-2 extended delay range, DD-2 memory/resampling, CV laws, Hold compensation, feedback response, noise and several filters are software models. The code comments mark estimates. Do not describe FMC as a circuit-exact pedal emulation. Source provenance and limitations for the references are recorded in [the reference inventory](references/README.md).

## Maintenance

When changing shared knob meanings, validate both tooltip units and typed-entry inverse mappings. Repeat each voice response, delay timing, feedback/hold stability, finite-output stress, lane isolation, selector/Hold/CV notification wiring, direct bypass and MIX tests. Rebuild the exported Java and embedded VMOD together, verify the exact versioned pair and regenerate its SHA-256 manifest after all edits.
