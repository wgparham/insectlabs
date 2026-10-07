# n01 Noise Source User Manual

**Insect Laboratories · Voltage Modular · canonical release 1.0.0**

Manual draft prepared 4 October 2026.

![n01 panel](n01_hero.png)

n01 is a manual noise and random-voltage source. It provides independent broadband WHITE noise, a RED/BLUE-controlled colored SPECTRA output, continuous SLOW RANDOM voltage, a continuous ramp-shaped S&H SOURCE, and a manually or externally triggered STEPPED output. There is no pink-noise jack and no internal sample clock.

This guide describes [release 1.0.0](). Open `n01.vmod` in Voltage Module Designer to build the module.

## Controls and outputs

| Control or jack | Range / default | Operation |
| --- | --- | --- |
| WHITE | Fixed broadband noise output | Broad white-like sound, slightly darker than SIN/RND’s noise core. It is independent of every panel control. |
| RED | 0–100%; starts at 50% | Adds a warmer, low-frequency component to SPECTRA and gently slows S&H SOURCE. |
| BLUE | 0–100%; starts at 50% | Adds a brighter broadband component to SPECTRA and gently speeds S&H SOURCE. |
| SPECTRA | Colored noise output | Outputs the RED/BLUE mixture with restrained compression. Both controls at zero approach silence after smoothing. No automatic loudness matching occurs as the spectrum changes. |
| RATE | Effective bandwidth 0.05–20 Hz; midpoint about 1 Hz | Sets SLOW RANDOM’s two-pole bandwidth. Clockwise is faster. It also gently speeds S&H SOURCE. This is not a periodic clock. |
| LEVEL | 0–100%; starts at 50% | Sets SLOW RANDOM amplitude and slightly increases S&H SOURCE timing variation. |
| SLOW RANDOM | Continuous voltage output, bounded to ±5 V | A bandwidth-compensated random signal derived from SPECTRA. Its lamps indicate positive/negative voltage and magnitude but are not calibrated meters. |
| S&H SOURCE | Continuous voltage output | A rising ramp with nominal ±5 V span. Its rate varies between approximately 20 and 120 Hz and is influenced by all four knobs. |
| TRIGGER IN | Voltage input | Captures the current S&H SOURCE on each rising edge; fires at +1 V and rearms at +0.1 V or below. An unpatched input is zero. |
| TRIGGER button | Momentary manual trigger | Captures one sample per press, including when an external trigger is connected. Releasing the button does not sample. |
| STEPPED | Held voltage output | Holds the most recently sampled S&H SOURCE value. It starts at 0 V and changes only on a trigger or button press. |

## How the random and sampling sections relate

WHITE is a dedicated noise core. SPECTRA is a separate, colored mixture shaped by RED and BLUE. SLOW RANDOM filters SPECTRA into a continuous control signal. The separate S&H SOURCE is a continuously moving ramp with noise-modulated timing; it is the voltage to sample. STEPPED does not create its own clock or continuous motion—it captures the current S&H SOURCE voltage on an event and holds that value. A trigger samples the ramp; it does not restart or reset the ramp phase.

RATE, RED and BLUE gently influence the mean speed of S&H SOURCE; LEVEL changes its cycle-to-cycle timing variation. Adjusting a knob does not reset the ramp phase. An already-held STEPPED value is unaffected by those knobs until the next sample event.

Holding TRIGGER IN high does not repeat. It must fall to +0.1 V or below and rise to +1 V again. If button and input events arrive during the same audio sample, one capture is made.

## Example patches

**Compare noise voices.** Listen to WHITE and SPECTRA at separate mixer inputs. Turn RED and BLUE to shift the colored output’s balance; WHITE remains unchanged.

**Use continuous random voltage.** Patch SLOW RANDOM to a modulation destination and adjust RATE for its bandwidth and LEVEL for its amplitude.

**Sample a changing voltage.** Patch S&H SOURCE into a modulation destination for continuous variation, or leave it as the internal source to STEPPED and trigger STEPPED from a gate or the front-panel button. A slower external gate makes the held steps easier to follow.

**Add movement to the sample source.** Change RATE, RED, BLUE and LEVEL while listening to S&H SOURCE. Each influences its timing slightly; none acts as a dedicated clock-rate knob for the STEPPED output.

## State and bypass

n01 has no panel power switch; its continuous sources run while the module is active. Saved patches preserve the held STEPPED voltage, while the evolving noise and ramp sequences restart as live sources. Reset requests STEPPED to return to zero.

Host bypass zeros all outputs, skips control and trigger reads, and freezes processing histories. Manual presses during bypass are discarded. An already-high external trigger does not create a new sample on resume until it falls and rises again. Bypass is immediate and is not declicked.

The WHITE and SPECTRA outputs are sound sources, not precision flat-spectrum calibration noise. For source details and validation limits, see the [n01 1.0.0 release record](README.md).
