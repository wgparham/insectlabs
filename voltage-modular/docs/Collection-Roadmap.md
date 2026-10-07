# Wardenclyffe Station roadmap

Wardenclyffe Station is the completed first collection in Insect Laboratories’ Voltage Modular line. Canonical release means the repository’s authoritative source pair, panel and documentation; it does not imply store publication. The user confirmed the current 2.x candidates were built and auditioned and authorized promotion after this standards and packaging review. The exact user-host test was on the predecessor candidate; this pass preserves approved DSP and records its validation separately.

## Current collection

All sixteen current products are canonized in [Wardenclyffe Station](../Wardenclyffe%20Station/README.md). Every product folder contains its active 2.x release and every prior canonical 1.x archive.

| Product | Current | 1.x archives | Role | Manual |
| --- | ---: | ---: | --- | --- |
| [1919/ln Tone Burst Generator](../Wardenclyffe%20Station/1919-ln/versions/2.0.1/) | 2.0.1 | 1.0.0 | Counted dual audio gates with internal/external clocking | [Manual](../Wardenclyffe%20Station/1919-ln/versions/2.0.1/USER-MANUAL.md) |
| [1947B SIN/RND Generator / Filter](../Wardenclyffe%20Station/1947b/versions/2.0.0/) | 2.0.0 | 1.1.0 | Sine, noise, random FM and patched-audio filtering | [Manual](../Wardenclyffe%20Station/1947b/versions/2.0.0/USER-MANUAL.md) |
| [1958 Dual Waveform Generator](../Wardenclyffe%20Station/1958/versions/2.0.0/) | 2.0.0 | 1.0.0, 1.0.1, 1.0.2 | Accurate test sine/triangle source with FM and 1 kHz reference | [Manual](../Wardenclyffe%20Station/1958/versions/2.0.0/USER-MANUAL.md) |
| [1998/4 Dynamic Modulator](../Wardenclyffe%20Station/1998-4/versions/2.0.0/) | 2.0.0 | 1.0.0 | Driven amplifier, envelope/gate extraction and delay | [Manual](../Wardenclyffe%20Station/1998-4/versions/2.0.0/USER-MANUAL.md) |
| [1A-30 Deep Tone Generator / Modulator](../Wardenclyffe%20Station/1a-30/versions/2.0.0/) | 2.0.0 | 1.0.0 | Deep sine, beat, FM and amplitude movement | [Manual](../Wardenclyffe%20Station/1a-30/versions/2.0.0/USER-MANUAL.md) |
| [6121198a Fader / Distributer A — Linear](../Wardenclyffe%20Station/6121198a/versions/2.0.0/) | 2.0.0 | 1.0.0 | Linear crossfade and signal distribution | [Manual](../Wardenclyffe%20Station/6121198a/versions/2.0.0/USER-MANUAL.md) |
| [6121198b Fader / Distributer B — Equal Power](../Wardenclyffe%20Station/6121198b/versions/2.0.0/) | 2.0.0 | 1.0.0 | Equal-power crossfade and signal distribution | [Manual](../Wardenclyffe%20Station/6121198b/versions/2.0.0/USER-MANUAL.md) |
| [AC/1D SQR/SIN Function Generator](../Wardenclyffe%20Station/ac-1d/versions/2.1.2/) | 2.1.2 | 1.0.0 | Independent sine and square test oscillators | [Manual](../Wardenclyffe%20Station/ac-1d/versions/2.1.2/USER-MANUAL.md) |
| [c2-34 Balanced Modulator](../Wardenclyffe%20Station/c2-34/versions/2.0.0/) | 2.0.0 | 1.0.0 | Carrier-suppressed modulation and unipolar VCA | [Manual](../Wardenclyffe%20Station/c2-34/versions/2.0.0/USER-MANUAL.md) |
| [n01 Noise Generator](../Wardenclyffe%20Station/n01/versions/2.0.0/) | 2.0.0 | 1.0.0 | Dark noise, random voltage and sample/hold source | [Manual](../Wardenclyffe%20Station/n01/versions/2.0.0/USER-MANUAL.md) |
| [sh.7437 Dual Slew Processor](../Wardenclyffe%20Station/sh-7437/versions/2.0.0/) | 2.0.0 | 1.0.0 | Independent positive and negative slew limiting | [Manual](../Wardenclyffe%20Station/sh-7437/versions/2.0.0/USER-MANUAL.md) |
| [sn-16u Universal Test Bench](../Wardenclyffe%20Station/sn-16u/versions/2.0.0/) | 2.0.0 | 1.0.0 | Pitch standards, clean references, meters, sweep and filters | [Manual](../Wardenclyffe%20Station/sn-16u/versions/2.0.0/USER-MANUAL.md) |
| [SW1 Relay Switch](../Wardenclyffe%20Station/sw1/versions/2.0.0/) | 2.0.0 | 1.0.0 | Patch-programmable two-input/two-output relay switch | [Manual](../Wardenclyffe%20Station/sw1/versions/2.0.0/USER-MANUAL.md) |
| [SW2 Switch / Distributer](../Wardenclyffe%20Station/sw2/versions/2.0.0/) | 2.0.0 | 1.0.0 | Manual input selector and output distributor | [Manual](../Wardenclyffe%20Station/sw2/versions/2.0.0/USER-MANUAL.md) |
| [Type 9414 Frequency Analyzer](../Wardenclyffe%20Station/type-9414/versions/2.0.0/) | 2.0.0 | 1.0.0 | Selective band-pass and matched band-reject instrument | [Manual](../Wardenclyffe%20Station/type-9414/versions/2.0.0/USER-MANUAL.md) |
| [Type 23 Signal Processor](../Wardenclyffe%20Station/type-23/versions/2.0.0/) | 2.0.0 | 1.0.0, 1.0.1 | Dual mono processor/VCA with independent stages | [Manual](../Wardenclyffe%20Station/type-23/versions/2.0.0/USER-MANUAL.md) |

## Shared design and engineering decisions

- Series One is mono-first and primarily manually controlled, inspired by early and postwar test equipment, electronic laboratories and studio instruments. Dual sections exist where the device has two independent stages or jobs.
- Keep code and DSP light. The shared host BYPASS is an abrupt direct transfer/silence path that skips DSP and can click; a physical POWER control can have a separate graceful warm-up or fade. Courtesy outputs follow each manual and are silenced by host BYPASS.
- Human-readable Designer Display Names are standard; internal and variable names remain descriptive lowerCamelCase. Module Notes contain the exact version marker (`v<version>`) in a release, and controls have useful tooltips.
- +5 V remains the general modulation reference. Consider +10 V only for a control whose intended instrument behavior warrants it. Restore +5 V for the Radiophonic series.
- Add useful audio fixtures to the independent [TestBench project](../../test-bench/README.md) in its published format.

## Final planned Wardenclyffe instrument

The next and final module is a mono summing/mixing amplifier. It is the only remaining planned Wardenclyffe role. The brief is not locked: begin from a compact manual mixer with independent input levels and a summed output; define input count, headroom, overload character and any polarity options with the user before DSP. No CV, stereo bus or EQ is assumed unless the new brief calls for it.

## Later collections

**Series Two — Radiophonic Studio.** The broad direction is accepted and remains open as the collection develops: equipment designed for making electronic music in mid-1960s to mid-1970s studios. Continue toward more common CV control, voltage-controlled switches, more modern manual switches/routers, studio contour and filtering tools, and the frequency shifter with separate UP and DOWN outputs. A later EMS-like random-voltage instrument and external sample/track-and-hold remain proposals; n01 stays canonical.

**Series Three — Electronic Computation.** Keep the accepted direction broad: analog arithmetic, accumulation and integration, comparison and windows, logic, latches, counters, registers and event routing. ACE, Befaco A*B+C, Count Modula, alef’s bits, Lilac and Patchable Devices are references. Window Generator and Voltage Sequencer remain concepts to explore, not locked briefs. Preserve distinctions between continuous integration and clocked accumulation, and between a voltage-window comparator and a multi-stage envelope generator.

The detailed design records and optional gap analysis are indexed in [docs](README.md). These later-series ideas do not reopen or alter Wardenclyffe releases.
