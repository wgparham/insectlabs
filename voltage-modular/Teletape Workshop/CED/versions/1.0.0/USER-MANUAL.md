# CED Chorus Ensemble Device — User Manual

CED combines two chorus workflows. ENS selects four slow, complementary stereo ensemble programs. CHO selects a mono CE-1-style chorus or vibrato voice. The shared DSP makes the Dimension delays, noise, pre/post filtering, and optional compander; these are modeled behaviors, not component-exact hardware emulations.

## Connections

- **In L / In R:** stereo inputs. In ENS, an unpatched right input follows left. In mono input mode, the two inputs are averaged and feed both sides. In CHO, the left input is the mono source.
- **Out L / Out R:** stereo ensemble outputs. CHO duplicates its mono output to both jacks.
- **Rate CV / Depth CV:** affect the ENS controls. Rate CV is exponential at 0.4 octave per volt; depth CV adds 0.2 sweep-scale units per volt. Unpatched inputs are neutral. At ±5 V, rate changes by ±2 octaves and depth by ±1.0, within the core limits.

## ENS controls

- **1–4:** engage Dimension-style programs. Buttons can combine. With all buttons up, the signal is dry.
- **EXC:** exclusive selection; when enabled, pressing a program releases the others. The most recently pressed remains selected.
- **CMP:** enable the modeled compressor/expander pair around the delay. It is most apparent with BBD noise enabled.
- **NOISE:** adds shaped, repeatable noise to the delay output; zero is off, maximum is approximately −26 dB relative to a 5 V peak reference.
- **Rate:** scales the selected programs' LFO rates from 0.05× to 4×, logarithmically; default is 1×.
- **Depth:** scales the delay sweep around each program's center, from 0 to 2×; default is 1×.
- **SUM / AVG / PAR switch:** selects how two or more programs combine: summed modulation, averaged program, or parallel delay taps. With one program selected the choice does not add another voice.
- **M / S switch:** selects mono-sum or stereo ENS input. In mono-sum, the average of L and R feeds both sides.
- **MIX:** scales the ensemble wet contribution. At zero the output is exact dry; with an ENS program active, the wet return is blended at half scale at the full setting.

The four programs use nominal delay sweeps of 8–12, 5–10, 6–9, and 6–9 ms. Programs 1–2 run at 0.25 Hz and programs 3–4 at 0.5 Hz before Rate scaling. Their left and right modulation directions oppose one another.

## CHO controls

- **Rate:** logarithmic 0.05–12 Hz CE-style LFO rate; default 1 Hz.
- **Intensity:** scales the delay sweep from zero to a 2.4 ms half-range around a modeled 3.8 ms center.
- **Drive:** logarithmic 0.1×–10× input gain before the soft clip; default 1.5×.
- **CHORUS / VIBE:** Chorus mixes dry and delayed signals equally; Vibe is wet-only vibrato.

Noise also reaches the delayed voice in CHO. The ENS compander control and ENS rate/depth CV do not change the CHO voice. MIX applies to ENS; CHO's chorus/vibrato modes set its dry/wet behavior.

## Operation and limits

Use ENS for stereo width, gentle motion, or combinations of the four delay programs. Use CHO for the more direct, mono chorus/vibrato tone. The mode switch clears the wet history when changing modes. Host bypass routes the inputs directly to the outputs and freezes DSP; the effect clears its stale delay history once on return. Presets save controls, not delay tails.

The module runs at 48 kHz internally. Its filtering, BBD noise, compander, delay interpolation and controls are software models. The documented Dimension delay/period values are sourced from service material and schematic readings; the CE-1 center/range and some tonal details are modeling choices.

For a source-rendered listening reference, see the [CED TestBench demo](../../../../../test-bench/v1/08-module-demos/CED_dimension_ce1_chorus_demo.wav). It is a set of short sections, not a seamless loop or a native Voltage Modular capture.
