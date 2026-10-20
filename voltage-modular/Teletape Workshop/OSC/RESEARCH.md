# OSC: oscillator circuit research

Research date: 2026-10-17. OSC 1.0.0 is canonical; obsolete mockup/comparison pairs were removed after research and metrics were retained. The current panel combines the Synthi 100's sine/ramp, triangle/square and low-frequency generator roles. The historical observations below remain separate from the software choices documented in [DSP design](versions/1.0.0/DSP-DESIGN.md).

## Primary sources and exact locations

- [Synthi 100 circuit collection](../references/ems_synthi_100_sch.pdf), PDF page 18: EMS 32/06 sheet 1 of 3, issue G, ramp/triangle oscillator. PDF page 19: sheet 2 of 3, issue G, sine/ramp oscillator. Read the revision annotations as part of each drawing.
- [AKS service manual](../references/emsa_synthi-aks_sm.pdf), PDF pages 55-60: alternating oscillator drawings and calibration/fault notes; printed service pages 22, 24 and 26 are PDF pages 56, 58 and 60.
- [Synthi 100 specification transcription](../references/EMS%2520%2520Synthi%2520100%2520Computer%2520Synthi.pdf), PDF pages 1-2. This retained four-page document contains obvious transcription errors, including the sine distortion figure and frequency units. Do not silently repair these into verified specifications.

The 12-page file named `ems_synthi100_sm.pdf` contains a service drawing index and excerpts, not the educational handbook previously claimed in the shared reference index. Its index identifies 32/06 as the oscillator/meter circuit and separate module wiring sheets for the oscillator groups.

## What the drawings establish

### Repeated core, different shaping

Both 100 oscillator drawings use a matched-transistor exponential frequency converter, a capacitor integrator and a hysteretic switching stage. They are discrete transistor circuits: the repeated 2N5172, BC169C, 2N4288 and ME9002 parts do not constitute a generic 741 oscillator. The drawings show 2N2916 matched pairs; revision notes explicitly record replacement of earlier BC169C pairs.

Sheet 1 labels its stages: anti-log frequency control summer, integrator, hysteretic switch, rectifier, ramp-output offset corrector, DC shifter and square-wave output. Triangle/ramp shaping and square shaping have distinct connections. Sheet 2 uses a related timing structure followed by transistor and diode shaping, with a separate sine-shape connection. The AKS drawing explicitly labels this last section a triangle-to-sine shaper.

The sheet-1 population table changes the timing capacitor from 10 nF in one group to 1 uF in another, alongside resistor changes. Low-frequency operation is a change in circuit time constants, not a digital divider. Do not infer the total frequency ratio from capacitance alone. The specification describes sine/ramp audio, triangle/square audio and slower waveform-generator groups; these are not three unrelated oscillator principles.

### Imperfections documented by EMS

- AKS oscillator 1: adjust sine shape before frequency because the adjustment affects frequency. PR17 balances a small spike at the sine peak; the service illustration accepts a residual triangular spike. The ramp shape is not adjustable. Sine and ramp are approximately 1.5 V peak-to-peak in this procedure.
- AKS oscillator 2: pulse approximately 2 V peak-to-peak, ramp approximately 3.5 V peak-to-peak. The notes explicitly state oscillator 2/3 waveforms are not symmetrical about zero volts. Matched-pair mismatch is given as a cause of curved voltage/frequency tracking.
- AKS oscillator 3: triangle/ramp approximately 6 V peak-to-peak and pulse approximately 5 V peak-to-peak. Shape runs from rising ramp through triangle to falling ramp, with symmetry near the middle. These are AKS service values, not proposed OSC output levels or universal 100 values.
- The oscillator 3 fault notes describe parasitic oscillation and breakthrough into other oscillators, cured by changing C70 from 330 pF to 1 nF. This is a repair target, not a desirable normal operating feature.
- The 100 specification claims approximately 1% ramp-linearity departure and approximately 5% triangle rise/fall symmetry error. It also claims typical session stability within 2 parts per thousand (about 3.46 cents), versus better than 2% month-to-month. These are published claims, not drift waveforms or measurements of surviving instruments. They argue against obligatory large, fast pitch wobble.

## Repair evidence and physical interpretation

[Hinton's AKS/VCS3 servicing guide](https://hinton-instruments.co.uk/ems/essential.html) distinguishes differential heating of the exponential transistor pair from ambient-temperature compensation. Poor thermal coupling and imperfect compensation can affect tuning and scale differently. Board revisions vary; this cannot establish one drift magnitude for every Synthi 100.

[Hinton's recommended modifications](https://www.hinton-instruments.co.uk/ems/recommended.html) describes unbuffered AKS/VCS3 matrix loading and sync injected into the hysteretic switch threshold. Loading explains some patch-dependent behavior; it is not automatically a property of a Voltage Modular cable. Threshold sync is also distinct from unconditional digital phase reset.

Physical inferences for a model:

- Timing-capacitor current and switching thresholds determine period. Converter errors can therefore move pitch or bend tracking without adding an audible noise floor.
- Finite switching speed and shaper capacitances can round transitions and change behavior with frequency. The drawings establish these mechanisms, but not a measured transfer curve for a particular unit.
- Diode and transistor shaping can produce asymmetric harmonics and changing amplitude/DC with shape. A generic final tanh would not reproduce all these interactions.

## Recommended hybrid for audition

1. Use one continuous timing state for the hybrid, so all three outputs share pitch drift. Independent detuning of its sine, triangle and pulse is a different instrument architecture.
2. Derive separate waveform paths: triangle/ramp skew; diode-inspired sine curvature/asymmetry; pulse threshold/duty with finite transition time. Keep their phases and polarity deliberate.
3. Make the noon sine slightly imperfect, with restrained curvature/spike character. Sweep into asymmetric shaping rather than simply hard-clipping a perfect mathematical sine. The mockup's word "rectification" still needs a precise endpoint definition; suppression of one half-cycle and full-wave rectification are different functions.
4. Retain shape-dependent amplitude and DC where musically useful. Permit a small, bounded shape-to-pitch interaction only as an explicit design choice: the AKS observation does not quantify a suitable amount or prove identical behavior in every 100 revision.
5. Start pitch movement with a slow, bounded shared process and, if wanted, gentle register-dependent tracking error. Coefficients and timescales must be listening choices until measurements exist. Do not add independent white-noise pitch jumps, simulated mechanical knob backlash, parasitic faults or mandatory warm-up waits.
6. Preserve useful low-frequency output, including DC. A blanket audio high-pass used to hide shape offsets would defeat the LFO role. Pulse duty must remain bounded and its extremes should have an intentional behavior.
7. Band-limit discontinuities and account for audio-rate shape modulation. The established 2x nonlinear path is a reasonable starting point, but oversampling alone cannot remove all oscillator aliasing. No CPU claim or implementation benchmark has been made.

## Panel observations and open choices

The supplied notes specify 1 Hz-15 kHz VCO and 0.05 Hz-500 Hz LFO knob ranges, C4 as frequency default, and SHAPE / BOTH / LEVEL routing for the three waveform CV inputs. These are the user's hybrid choices, not exact historical specifications. Separate shape controls make sense even where the original hardware shared controls.

The first audition used host-friendly 1 V/octave FM. Following user approval, OSC 1.0.0 separates V/oct pitch from DC-coupled through-zero linear FM: +/-5 V adds +/-100% of tuned rate, with reversal below -5 V. This is a user-approved extension, not an EMS circuit claim. Waveform CV retains +5 V per full control sweep, individual outputs follow their levels, and MIX remains unnormalized. Numerical voicing amounts and endpoint behavior are listed in DSP design. The historical AKS calibration notes give 0.32 V/octave; this is not a reason to make a modern pitch input incompatible with the host ecosystem. Preserve the panel unless a practical control limitation emerges during audition.
