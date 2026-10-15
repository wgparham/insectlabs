# EMS Octave Filter Bank schematic analysis — 2026-10-14

Source: ../../references/EMS-8-Octave-Filter-Bank-Schematics.pdf, five scanned pages. Page 1: Octave Filter, drawing82/11 Issue B, late1971. Page2: EMS33/05 PCB assembly IssueE, revision annotations through14.1.72. Pages3–5 are power supply/board/regulator documents, not additional audio-band circuitry. Interior photograph is construction context; it does not establish DSP parameters. Analysis below is derived from ideal-op-amp equations and transcribed nominal components; not measured hardware response.

## Band topology

A17 is a unity-gain input voltage follower. Each of eight paths has a unity-gain Sallen-Key second-order high-pass A1–A8 followed by a unity-gain Sallen-Key second-order low-pass A9–A16. The full band is fourth order, approximately12dB/octave on either far skirt. Existing DSP is one second-order constant-peak bandpass per band, approximately6dB/octave per analog-like skirt. Lowering its Q alone would not reproduce the circuit.

For the HP, equal series capacitors C and resistor pair R1(midpoint-to-output), R2(positive-input-to-ground): omegaH=1/(C*sqrt(R1*R2)); QH=sqrt(R2/R1)/2. Hhp(s)=s^2/(s^2+s*omegaH/QH+omegaH^2).

For the LP, equal series resistors R with feedback capacitor C3(midpoint-to-output) and grounded capacitor C4(positive-input-to-ground): omegaL=1/(R*sqrt(C3*C4)); QL=sqrt(C3/C4)/2. Hlp(s)=omegaL^2/(s^2+s*omegaL/QL+omegaL^2).

All nominal HP resistor ratios are10:1 and LP capacitor ratios10:1, giving QH=QL=sqrt(10)/2 approximately1.58114. This is each section's Q, not the Q of a single replacement bandpass. The resulting composite peak is around2.5x, with variation from cutoff offsets and nominal rounded parts.

| Label | HP resistors ohms | HP capacitors each | LP resistors each | LP feedback/ground capacitors | HP f0 / LP f0 Hz |
| --- | --- | --- | --- | --- | --- |
|63|18000/180000|47nF|56000|150nF/15nF|59.49/59.92|
|125|9100/91000|47nF|27000|150nF/15nF|117.67/124.27|
|250|4300/43000|47nF|13000|150nF/15nF|249.03/258.10|
|500|2200/22000|47nF|6800|150nF/15nF|486.74/493.42|
|1000|3300/33000|15nF|10000|47nF/4.7nF|1016.75/1070.83|
|2000|1600/16000|15nF|5600|47nF/4.7nF|2097.05/1912.20|
|4000|2700/27000|4.7nF|8200|15nF/1.5nF|3966.05/4091.81|
|8000|1300/13000|4.7nF|4300|15nF/1.5nF|8237.19/7802.98|

Printed63/125/etc are nominal channel labels, not proof of exact tunings. 2kHz path has its LP natural frequency below its HP natural frequency; retain that difference when modeling nominal components rather than imposing identical cutoff frequencies. Page2 specifies10% plastic film capacitors and shows2% resistor markings. Do not turn that into random drift/noise by default. derived-band-values.json retains calculated values and model assumptions.

## Mixer phase: essential correction

A18 is an inverting summer with R77=10k feedback. A19 is another inverting summer with R79=10k feedback, routed into A18 via R78=10k. Direct-to-A18 bands are63,250,1000,4000Hz; through-A19 bands are125,500,2000,8000Hz. Thus nominal master signs are[-,+,-,+,-,+,-,+]. Separate band outputs remain non-inverted relative to each filter output. The double inversion on alternate bands is intentional circuit structure. Page2 includes an inversion-of-phase alteration annotation, supporting the significance of this revision.

With nominal10k mix input resistors, each band's nominal summing coefficient magnitude is1. There is no separate blanket3.16228 multiplier in the shown mixer. Page1 notes R5–R12 are nominal10k adjusted on test to trim output levels. Page2 visibly lists R5 as18k and other displayed mix inputs as10k: the low band can have a different calibrated contribution. This is a schematic/PCB revision distinction, not justification to silently collapse all versions to a single gain.

All knobs are5k LOG pots on the mix feed; their individual jack taps are before the pots. Thus pre-Level band outputs in the prototype are correct. Loading through a virtual-ground mix resistor introduces modest pot-law deviation: for ideal fraction a and pot P, loaded gain is a/(1+P*a*(1-a)/Rmix). The actual audio taper is not specified beyond LOG. Use a documented chosen log law and verify workflow; do not claim exact taper from the drawing.

C75 is16uF output AC coupling; its response depends on external load, which is not supplied. Avoid inventing an exact output coupling cutoff.

## Calculated comparison

Ideal stages, nominal page1 resistors, all knobs full, no741 dynamics/loading/tolerances. Prototype comparator uses the actual48kHz biquad coefficients and existing +10dB gain at small input, where tanh is approximately linear.

At500Hz: prototype+10.36dB, schematic+9.41dB. At707.11Hz: prototype-5.71dB, schematic+5.56dB. At1000Hz: prototype+10.35dB, schematic+9.39dB. Hollow coloration is strongly explained by narrow single-band response and wrong summing polarity. Merely replacing filters but still summing all-positive is also wrong: at707Hz that model yields approximately-2.45dB. The circuit is not a perfectly flat reconstruction filter bank, but adjacent-band cancellation is much reduced by its alternate phase routing.

## Nonlinearity, components and redesign

Audio schematic labels741 op-amps; notes include vendor restrictions for A18/A19. PCB page names ML-741C for A1–A18 and shows the added A19. Audio rails are labeled+/-12V. These establish a useful topology/component family; no measured saturation law, exact output headroom, slew-rate or bandwidth was derived here.

A17 has no explicit5V soft-limiting network. Remove the arbitrary input tanh from the first circuit-informed linear prototype. Resonant HP/LP stages can raise internal levels, so later overload should be evaluated at the individual amplifier nodes, not justified solely by an input limiter. Op-amp output headroom, loading, finite bandwidth and slew are possible later models requiring supported parameters and audition. Preserve a clean analytical baseline before adding them.

Recommended next implementation: sixteen biquad sections (HP thenLP for each band), component-derived natural frequencies and Q; direct pre-pot taps; alternating-polarity mixer with nominal unity weights or a documented board-specific low-band trim; log mix controls; existing state/safety/bypass infrastructure. No arbitrary final+10dB booster or5V tanh. Start at48kHz with explicit digital mapping/prewarping and measure high-band response. This is a new design prototype; current pair has not been changed by this analysis.

The preceding oversampling benchmark covered one tanh plus eight filters and does not predict the rewritten circuit model's cost. A linear16-section model does not need oversampling to prevent generated harmonics. Higher rate can reduce analog-response warping, but must be assessed with its resampling passband, delay and CPU; later nonlinear741-node models may justify oversampling around the complete nonlinear path. Do not transplant the earlier50% cost figure onto that design.
