# Optional gap-fillers and later-series ideas

Proposal review: 1 October 2026. **None of the additions below is an approved module.** The accepted Laboratory inventory remains ten completed plus six planned. These suggestions come from comparing current capabilities and the retained briefs; they are not historical user decisions.

## Laboratory priorities

| Priority | Proposal | Gap and recommended scope |
| --- | --- | --- |
| 1 | Summing / Mixing Amplifier | Three or four mono inputs with independent manual levels and one sum output. Fader|Distr has complementary weights rather than independent channel levels; SIGPROC processes separate stages. A dedicated mixer adds repeatable balance, headroom and useful overload indication even where host patching already sums signals. |
| 2 | Measuring Amplifier / Level and Polarity Meter | An instrument for checking audio level, DC offset and polarity. Start with a large slow meter, manual ranges and AC/DC selection. Decide RMS/peak/average behavior explicitly and label it honestly. A unity through path or a monitoring-only input would avoid unintended coloration. |
| 3 | Band-Limiting Amplifier | Manual low and high cut for preparing audio, noise and feedback paths. Consider a broad mode in the already-planned Selective Amplifier first; add a separate box only if independent high/low limits justify it. |

For the mixer, keep the first design simple: no CV, stereo bus or built-in EQ. Restrained overload could give it a distinct Laboratory line-stage voice. An inverted sum is an optional useful extra, not a required expansion. Preserve DC use unless the brief deliberately calls for an audio-only path.

The meter complements Reference / Standards: one generates known signals, the other observes them. Do not burden the reference source with an analyzer before its core panel is settled. Avoid promising laboratory measurement accuracy until calibrated and tested.

## Fill these gaps within accepted concepts first

- **Envelope extraction:** Dynamic Modulator is already the intended home. Consider exposing its envelope so it can control SIGPROC or other instruments.
- **Slew/integration:** Pulse Shaper could smooth stepped voltages as well as make pulse contours. This would make n01 more useful without adding a second random generator.
- **Trigger conditioning:** a threshold with hysteresis could turn arbitrary slow/random/audio signals into events. Evaluate it in Pulse Shaper or Dynamic Modulator. n01 already conditions its own trigger input, but does not expose a general trigger extractor.
- **Narrow spectral isolation:** Selective Amplifier already fills this gap. A broad option may also cover much of the band-limiting proposal.
- **Multiplication:** Balanced Modulator already has a place; avoid adding another ring modulator under a different name.

## Radiophonic possibilities

**EMS-inspired Random Voltage Generator:** explore two random-voltage channels, held or gliding motion, mean rate and timing variance, with an event output and manual reselection/inhibit if the panel supports them. The [user-provided EMS reference](https://amsynths.co.uk/2026/08/11/ems-random-voltage-generator/) combines sampled voltages with variable timing. Its exact circuitry, channel numbering and ranges are not requirements. This would develop the user's late n01 inspiration as purpose-built studio equipment.

**External sample-and-hold / track-and-hold:** n01 STEPPED samples its own ramp only. Sampling arbitrary patched audio/CV is a real capability gap, and fits the accepted Series Two S&H direction. Decide whether this belongs in the random-voltage instrument or a small independent sampler.

**Studio noise processor:** CV-controlled spectral movement, bands and perhaps bursts could take the multiple-voice EMW and noise references further. Keep n01's approved manual noise voice intact; this is a later processor proposal rather than a revision to n01.

Frequency shifting with separate UP/DOWN outputs, voltage-controlled switches, modern manual routers, studio contour/filter tools and tape/delay/reverb remain the existing Radiophonic direction. These ideas do not commit the whole list or a development order.

## Computing boundary

Keep arithmetic, logic, accumulators, explicit state and coordinated event processing in Series Three. The requested Patchable Devices Window Generator inspiration is a multi-envelope/stage-event idea; a voltage-window comparator is a different possible instrument. The Voltage Sequencer remains a candidate for programmable voltage/state selection. Avoid adding incidental coloration to exact arithmetic without an explicit design reason.

## Suggested decision

Complete Reference / Standards and retain the five processing concepts. If expanding Laboratory, discuss the Summing Amplifier first, then the meter. Try to solve the remaining smaller gaps within those existing briefs before increasing module count.
