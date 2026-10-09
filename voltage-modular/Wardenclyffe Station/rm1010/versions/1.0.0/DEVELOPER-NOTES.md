# RM1010 v1.0.0 developer notes

## Agreed signal path

Inputs I–IIII → 16 Hz AC coupling → GAIN → ±10 V tanh channel stage → 900 Hz tilt → VOLUME → mute envelope. Each channel feeds its pair submix and, scaled by PROCESS, the independent send bus. 3 is I+II; 7 is III+IIII. Each submix has gentle saturation, and those characterized signals feed the final 10 bus together with RETURN and LINK. S sums the post-volume sends, without averaging. RETURN does not feed S or the submix taps. Unpatched RETURN is silent.

Each bus uses `x / sqrt(1 + (x/12)^2)`, asymptotically ±12 V. These are rounded stage limits, not hard post-output clamps. Output coupling and FIR transients can briefly exceed nominal rails. Channel drive uses `10*tanh(x*gain/10)`. Low levels approach unity; the high range can be strongly distorted. No hum, random drift or artificial crosstalk is added in this pass.

Gain is `1 + (maximum-1)*knob²`, with maximum 2, 5 or 100. VOLUME and PROCESS are linear 0–1. Tilt uses a bilinear first-order shelving transfer with opposing ±6 dB endpoints and unity magnitude at the 900 Hz pivot. Positive BALANCE brightens, negative darkens. Coefficients and controls are smoothed over approximately 10 ms at 96 kHz. Exponentials/powers for parameters run in notification/control updates, not per audio sample.

## Mute, indicators and bypass

Range 0 mutes dry/send using the smoothed mute envelope. The gain/tilt stage continues running for metering. LEDs tap the saturated input stage before BALANCE/VOLUME, with 5 ms attack and 120 ms release, scaled by 1/5 and capped at full brightness. Mute retains the last active gain range for this monitoring; initialization in mute uses LOW. This is the approved monitoring behavior. The remembered range is runtime state; a muted channel restored in a newly created instance monitors LOW until an active range is selected.

LED setters are throttled to 100 Hz. System bypass turns off the LEDs once on entry, then does not read knobs/switches, process filters/nonlinearity, advance smoothing or repeatedly update meters: it sends raw I+II to 3, III+IIII to 7, all four plus RETURN/LINK to 10, and zero to S. Resume adopts current controls, clears stale filters/envelopes, and snaps smoothing once. Preset/variation/reset/randomization notifications refresh targets and request the same state clearing. Native automation/undo behavior remains a host check.

## Oversampling and cost

48 kHz host callbacks are processed at 2× internally. Six input interpolators and four output decimators reuse Colorbox's exact 19-tap half-band coefficients. Inputs are zero-inserted and scaled by two; output takes phase 1. Nominal combined FIR group delay is about nine host samples; bypass has no latency compensation. RETURN/LINK use the same interpolation to remain aligned with direct internal paths. There is no effects-loop latency compensation.

All arrays/filter states are preallocated. The audio path does no allocation, string formatting or parameter exponentiation. The FIRs require six multiplications per tick, about 120 multiplications per host sample across the filters, in addition to ordinary DSP. This is an arithmetic description, not a measured host CPU percentage. The saturation math and FIRs need native-host profiling. Default ranges/positions, artwork, UUIDs and geometry are unchanged; only the balance3 typo and uppercase LED variable identifiers were normalized in Java and Designer.

Output coupling at about 3.3 Hz removes incidental DC on 3/7/10/S. Audio sanitation treats unconnected/nonfinite jack values as zero; active processing limits internal finite inputs to ±1,000,000 V for numerical stability. The input helper also bounds bypass input values to ±1,000,000 V, while bypass otherwise transfers ordinary voltages directly.

## Validation and reusable code

The retained tests compile both exported and embedded sources for Java 17 against the installed Voltage SDK with all warnings treated as errors. They verify project/source agreement, routing, pair isolation, post-volume send behavior, mute with active metering, RETURN/LINK isolation, gain range/taper, tilt direction and pivot magnitude, typed unit inverses, preset resume, bypass/frozen histories and driven signal stability. Tests execute actual extracted callback/core bodies with mocked SDK controls; they do not replace the user's native-host build/listening check.

The unchanged half-band filter is retained in dsp-primitives with provenance and standalone validation. RM1010 keeps its own embedded copy. The approved mixer core remains self-contained to preserve its sound.

References: user-supplied Moffenmix schematic and quick-start guide in development. The schematic informs gain-before-volume and amplifier overload. The user's confirmed routing, gains and mute behavior take precedence over the reference hardware. Alice 28-series is an additional desk-design reference; its detailed circuit documentation has not been inspected.

## Canonical review

Release v1.0.0, 2026-10-08. The user authorized canonization after code review, behavior-preserving cleanup and successful checks. Both source forms compile with Java 17 / all warnings as errors; actual callbacks/core pass routing, transition, boundary and typed-edit tests. Cleanup audio digest matches the approved baseline exactly: 15974080979552226371. UUIDs, geometry, artwork, ranges, initial controls and saved test state were preserved. Descriptive decorative names and human-readable Display Names were normalized; module/control Notes were stripped except v1.0.0. Source uses 48 kHz host timing, 2× native-coloration DSP and throttled audio-thread meter setters. No native-host CPU/automation/undo benchmark or separate post-cleanup audition is claimed.
