# Burstgen Tone Burst Generator — User Manual

**Version 1.0.0 · Voltage Modular · Insect Laboratories Series One**

![Burstgen front panel](versions/1.0.0/burstgen_hero.png)

## Purpose

Burstgen alternates two live input signals through independently counted gates. A clock tick advances the active count; when that count reaches zero, the other nonzero stage opens. Use it to make repeatable tone bursts, alternate sections of audio, or move between different sources in time. Inputs keep running while closed, so a reopened input resumes from its current playback position.

It is a mono utility with two independent signal inputs, two corresponding outputs and a combined gated output. It does not generate a tone. Patch the test tone or program material you want to gate.

## Signal connections

| Connection | Function |
| --- | --- |
| **X Input** | Signal or voltage for the X gate |
| **Y Input** | Signal or voltage for the Y gate |
| **X Output** | X input passed unchanged in PASS, or gated in GATE |
| **Y Output** | Y input passed unchanged in PASS, or gated in GATE |
| **Z Output** | Sum of X and Y after their gates; always follows the counted gates |
| **External Clock Input** | Rising-edge clock for counting when CLOCK SOURCE is set to EXT |
| **C Clock Courtesy** | Free-running 0 to +5 V internal square clock |
| **R Ramp Courtesy** | Rising 0 to +5 V internal ramp; its reset edge marks the internal timing tick |

Unpatched audio inputs contribute zero. X Output and Y Output are separate. Their PASS switches do not affect Z. In PASS, an individual output can carry its source continuously while Z still follows the burst sequence.

## Setting a burst

Each SET display is set by four decimal knobs beneath it: thousands, hundreds, tens and ones. Set the X and Y values from 0000 to 9999. Each REMAIN display shows the active tick count left for that side.

Press **ENGAGE** to start. The first nonzero side starts immediately; subsequent changes occur on clock ticks. A count of N holds that side open for N counting ticks. Counts are ticks, not seconds: at 1 Hz, two ticks take approximately two seconds, depending on the free-running clock phase at start.

- With **LOOP** selected, X and Y repeat continuously.
- With **1 SHOT** selected, Burstgen runs one full X-then-Y cycle and returns to idle.
- Changing from LOOP to 1 SHOT while running lets the current side finish, then stops.
- A zero count skips that side. If both counts are zero, the sequencer remains idle.
- ENGAGE while running pauses immediately and preserves the current side/count. Press again to resume.
- RESET while paused restores both REMAIN displays to their SET values and prepares X to start.
- RESET while running reloads the active side's SET value without changing sides.

## Clock controls

**CLOCK SOURCE** selects INT or EXT. **CLOCK RANGE** sets the free-running internal clock. **INTERNAL CLOCK** tunes logarithmically within each range.

| Range | Frequency |
| --- | ---: |
| LO | 0.005–5 Hz |
| MID | 1–200 Hz |
| HI | 20–3,000 Hz |

The **RATIO** knob has eleven positions:

| Position | Ratio | Position | Ratio |
| ---: | ---: | ---: | ---: |
| 1 | ÷8 | 7 | ×1.618 |
| 2 | ÷4 | 8 | ×2 |
| 3 | ÷2 | 9 | ×3 |
| 4 | ÷1.5 | 10 | ×5 |
| 5 | ÷1.333 (exact 4/3 division) | 11 | ×7 |
| 6 | ×1, pass-through |  |  |

With **EXT** selected on the Ratio Destination switch, RATIO changes external clock counting only. With **BOTH**, it also changes the internal counter clock and R. C always remains at the original CLOCK/RANGE frequency. Internal ratio changes are direct; they do not need external edges to be acquired.

### External clock details

The external clock advances on rising threshold crossings, returning below its low threshold before it rearms. Multiplication uses the measured time between clock edges to place additional ticks evenly within that interval. A multiplication first needs two rising edges to measure the period. When the incoming clock stops, at most six predicted ticks may still occur at ×7; then the active gate and remaining count hold. Divisions use repeating input-edge patterns for fractional ratios.

At a 48 kHz host rate, the tested 2.5 kHz external source can reach 17.5 kHz at ×7. In BOTH with the internal clock at 3 kHz and ×7, internal counting and R reach 21 kHz. These settings are near the practical timing limit: 21 kHz leaves about 2.3 audio samples per tick. The clock and ramp retain their edge behavior, while audio gates become very short.

## Courtesy outputs

C is the machine's steady heartbeat: a 50% duty square wave at the unmodified internal CLOCK/RANGE rate. R is a rising ramp whose falling reset occurs on the internal timing edge. In EXT, R follows the base internal clock. In BOTH, R follows the ratio-adjusted internal rate. Neither output follows the external clock input. They continue while the burst sequencer is idle or paused; host bypass silences them.

At ×1, C's falling edge and R's reset coincide. In BOTH at other ratios, C remains steady while R follows the adjusted internal timing. If the counter source is internal, each active count transition aligns with R's reset edge. In EXT, the external clock may advance the gates separately from the internal Courtesy signals.

## PASS, GATE, and edge treatment

Each **PASS/GATE** switch controls only its side output. Up is PASS; down is GATE. X initializes to GATE and Y to PASS. Z always sums X and Y through the gates regardless of these switches.

Gate transitions use a short, adaptive de-click fade, no longer than 0.5 ms at 48 kHz. The fade gets shorter as the clock speeds up and is disabled at effective rates of 1 kHz and above so it does not smooth fast amplitude modulation. Gating happens at native audio rate; there is no intentional tone coloration, compression or level control.

## Bypass and levels

Host bypass copies X to X Output and Y to Y Output directly, sums X+Y at Z, and silences C and R. The sequencer and oscillator phases do not advance while bypassed. On return, the gates adopt the saved running state without replaying a stale fade; external clock edges are reacquired.

The audio path is unity gain when open. Z can exceed either source if X and Y overlap because its output is their sum. Keep this in mind when patching the output into a sensitive destination. There is no limiter.

## Patch examples

**Repeated tone burst:** Set X to 2, Y to 0, select INT/MID and choose a slow rate. Set LOOP and press ENGAGE. Patch a sine source into X and listen at Z. X opens for two internal ticks; the zero-count Y stage is skipped and the sequence repeats.

**Alternating program sections:** Patch one audio source to X and another to Y. Set equal counts, select an external clock, choose ×1, and engage LOOP. Z alternates the two live sources. Use the individual outputs in GATE when you also need isolated gate signals.

**Continuous source plus bursts:** Set one side's O switch to PASS. Its O jack stays continuous while Z follows the gated X/Y cycle. PASS does not force that signal into Z.

**Separate heartbeat and slower modulation:** Select BOTH and choose a division. C continues to output the original square heartbeat; R follows the divided internal timing. The two outputs provide related but distinct timing signals.

## Defaults and limitations

New instances initialize to zero counts, LOOP, internal MID clock, ratio ×1, Ratio Destination EXT, X in GATE, and Y in PASS. Set nonzero counts before pressing ENGAGE. Saved patches preserve their saved control states.

The timing and Courtesy calculations assume a 48 kHz sample rate, matching the Voltage Modular session used for this release. External clock multiplication is event based and may produce a bounded tail at stop. Fractional external ratios have repeating tick spacing. The module has no audio generator, count quantization to musical bars, phase-reset input, internal audio limiter, or separate gated clock output.
