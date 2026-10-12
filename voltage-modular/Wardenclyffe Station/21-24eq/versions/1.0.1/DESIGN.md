# 21-24eq — design brief (working draft)

## Purpose

21-24eq is a compact, mono, two-band frequency corrector for program audio and synthesizer patches. Its identity is a passive James-type bass/treble network with broad, dark 1940s-50s broadcast and recording character. The active Baxandall design is a better fit for the more modern EQ planned for Radiophonic. This is not a general-purpose filter or a small copy of the sn-16u test bench.

## Confirmed direction

- Keep the supplied 1.6-wide panel concept: one input, one output, Bass and Treble controls, and the three-position FREQ switch. Each position moves the tone controls darker than the last.
- Bass and Treble are independent, broad controls. Their center positions are the passive network's natural mid-control response, including its insertion loss and tonal contour; do not add compensation to force a flat response. Counterclockwise cuts and clockwise boosts.
- FREQ selects one of three increasingly dark shelf-frequency pairs.
- Tooltips describe control direction, passive midpoint behavior, switch profiles, and mono signal routing.
- Hard hits should engage deliberate soft saturation followed by a high safety ceiling; this architecture is confirmed, while exact threshold and curve remain to be designed.
- Passive-network insertion loss is acceptable. Do not automatically make up the loss; output level is part of the instrument's behavior.
- A small amount of source-coupling and loaded-network rolloff is desirable. The response does not need to be digitally transparent.
- The user accepts passive insertion loss; do not compensate it with automatic make-up gain.
- The ordinary operating range should stay controlled, with warmth and compression appearing as the signal is driven.
- Use bandwidth and saturation to establish the vintage voice. Do not add constant noise or hum just to imply old hardware.
- Host bypass should be a direct input-to-output path with no DSP, smoothing, or history advancement, following collection standards.

## Recommended signal path

`Input → high-threshold driver/color stage → passive two-band tone network → peak ceiling → Output`

Model the passive network's transfer response, including its substantial mid-band insertion loss, broad boost/cut curves, source impedance, and expected output loading. A nodal model of the supplied wiring at 1 kΩ source impedance and 100 kΩ output load gives about -20 dB through the mid-band with both controls centered. With one control centered, the opposite extremes reach roughly -3 to -4 dB at the relevant low or high edge, while full cut reaches about -40 dB at 20 Hz or -38 dB at 10 kHz. These are first-pass calculated values, not measurements of the module. Keep the loss instead of adding automatic make-up gain. Smooth control changes so the effective network does not click.

The passive R/C network itself is linear and cannot saturate. Model the high-threshold warm driver ahead of the network, then keep the final peak ceiling after it. This lets hard input peaks acquire character before passive loss while the R/C network still determines the tone. The ceiling should remain inactive at ordinary levels. Exact transfer curve, threshold, and release behavior remain open.

## Passive-network starting point

Use the supplied passive network as the first component-level candidate: 100 kΩ Bass and Treble pots; R1=10 kΩ, R2=1 kΩ, R3=10 kΩ; C1=22 nF, C2=220 nF, C3=2.2 nF, C4=22 nF. Its reference analysis reports approximately -20 dB through the mid-band. The circuit expects a low-impedance source and high-impedance following stage. The Voltage Modular version will emulate this loaded transfer response at a stated nominal source/load rather than pretend that patch cables create physical resistor loading.

Modeling assumption: 1 kΩ source impedance and 100 kΩ load. A series 220 nF input-coupling candidate adds roughly 1.7 dB attenuation at 20 Hz and 3 dB at 10 Hz under this model, with little effect above 100 Hz. The 100 nF comparison is more pronounced. Keep 220 nF as the first test value for the slight jack-in rolloff; the actual response depends on source and network impedance. Do not add make-up gain to restore passive insertion loss.

## Decisions still open

1. **FREQ profiles:** The three-position switch selects reference, dark, and extra-dark curves in that order. The reference set is 22 nF / 220 nF for bass and 2.2 nF / 22 nF for treble; dark uses 47 nF / 470 nF and 4.7 nF / 47 nF; extra-dark uses 100 nF / 1 µF and 10 nF / 100 nF. Doubling the capacitor values approximately halves the associated transition frequencies. The knob midpoint is not required to be flat; retain the circuit's native curve and loss.
2. **EQ range:** The controls should follow the passive network's boost/cut span rather than a symmetric modern ±dB promise. Derive usable control endpoints from the chosen component network.
3. **Overload behavior:** The fixed high-threshold soft-saturation stage plus high peak ceiling is the target. Initial voicing candidate: begin warmth above roughly 6 V peak, with a gradual knee that is clearly audible by 12 V; keep the output safety ceiling near the Wardenclyffe 20 V knee / 24 V limit convention. Use a symmetric, level-dependent curve first; leave asymmetry and driven bandwidth reduction for listening tests. These levels are provisional until checked against the expected patch range. No automatic make-up gain.
4. **Input/output model:** Initial assumption: low-impedance source and high-impedance load, with the input coupling and output loading represented in the transfer model. The effective source/load values still need to be selected for the Voltage Modular environment.
5. **Nomenclature/topology:** Keep 21-24eq on the passive James topology. Radiophonic can explore the later active Baxandall negative-feedback circuit as its own, more modern design.

## Response preview

`response-ranges.png` is a first-pass nodal simulation of the supplied passive topology at 1 kΩ source impedance and 100 kΩ output load. It compares the three switch positions at the knob midpoint and four control extremes. The plotted response excludes the optional input coupling, nonlinear drive stage, component tolerance, and host-level behavior, so it is a circuit model rather than a measurement. `input-coupling.png` compares the reference midpoint response with no added coupling, 220 nF, and 100 nF. `model_passive_network.py` defines all three switch profiles. `response-candidates.png` is the earlier qualitative redraw and has been superseded. The idealized unity-centered digital shelf preview is also superseded.

## First DSP draft

The exported Java source now contains a real-time approximation of the passive voice: a 10 Hz input high-pass as the DSP equivalent of the 220 nF coupling candidate, a high-threshold symmetric soft drive before the tone network, a fixed -20 dB insertion loss, and broad bass/treble shelves. The switch selects 70 Hz / 7 kHz, 35 Hz / 3.5 kHz, or 17.5 Hz / 1.75 kHz shelf corners. The bipolar knob values map counterclockwise to cut and clockwise to boost; Bass shapes only the low shelf and Treble shapes only the high shelf. The uncorrected midpoint response retains fixed +8 dB bass and +5 dB treble contours. Bass control travel adds up to +10 dB or -29 dB; Treble adds up to +12 dB or -24 dB. Controls and range transitions smooth over 5 ms. The output ceiling uses the 20 V knee / 24 V limit.

This is a first approximation of the circuit-derived response, not a component-exact digital realization. The release passed headless response/routing and source-pair checks and Java 17 SDK compilation. The installed Java 27 warning-lint path and the native Designer/host audition remain unavailable; see `REVIEW.md` for current validation status.

## Initial listening and measurement plan

- Measure frequency response for Bass and Treble at their midpoint, both endpoints, and intermediate settings; confirm the passive insertion loss and native tonal contour.
- Derive and compare two component-value sets for FREQ. Confirm they create audibly distinct ranges without abrupt jumps or unstable coefficients.
- Check the midpoint response, representative boost/cut settings, low-frequency high-amplitude content, and high-frequency tones for unexpected clipping or gain jumps.
- Compare overload onset with the stated operating range; keep ordinary use clean and make the driven response repeatable.
- Verify exact dry-through bypass, control-change smoothness, and recovery after bypass.

## Current state

The supplied Designer project and Java export establish the 1.6-wide control layout. The Designer project is synchronized with the Java export, and the pair passes source-parity, project-round-trip, and Java 17 SDK compilation checks. Headless tests cover routing, switch profiles, bypass, and recovery behavior. Listening and native Designer/host checks remain outstanding; see `REVIEW.md` for the precise validation status.
