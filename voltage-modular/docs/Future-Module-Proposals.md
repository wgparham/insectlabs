# Optional gap-fillers and later-series ideas

Proposal review: 9 October 2026. The LM-21 Mk III and simpler RM1010 minimixer are canonized. XL-35h and XL-35c add the matched high-pass / low-pass filter pair. Other additions below remain optional proposals. All twenty-one Wardenclyffe products are canonized. These suggestions come from comparing current capabilities and the completed briefs; they are not historical user decisions.

## Completed Wardenclyffe module and optional gap-fillers

| Priority | Proposal | Gap and recommended scope |
| --- | --- | --- |
| Completed | LM-21 Mk III Matrix Mixer / Artificial Acoustic Distance Generator | A bespoke 4×4 mono audio matrix with four independently processed output rows, bipolar crosspoints, bass/treble tone shaping, per-row level and ambience, shared reverb perspective, and a full-mix output. This intentionally expands the original compact-mixer brief into a custom WDR/RAI-style studio centerpiece. |
| 2 | Measuring Amplifier / Level and Polarity Meter | An instrument for checking audio level, DC offset and polarity. Start with a large slow meter, manual ranges and AC/DC selection. Decide RMS/peak/average behavior explicitly and label it honestly. A unity through path or a monitoring-only input would avoid unintended coloration. |
| 3 | Band-Limiting Amplifier | Manual low and high cut for preparing audio, noise and feedback paths. Consider a broad mode in the canonical Type 9414 Frequency Analyzer first; add a separate box only if independent high/low limits justify it. |

LM-21 Mk III is canonical at v1.0.1, and RM1010 is canonical at v1.0.0. The minimixer remains a separate, simpler and more period-faithful design; it does not inherit LM-21 features by default.

sn-16u 1.0.0 provides DC/Vpp, CV pitch, audio Hz and RMS measurement, along with independent clean HPF/LPF paths. The meter and band-limiting proposals above are substantially covered by that release, not separate approved modules. Revisit only if native testing exposes a remaining need. Avoid promising laboratory measurement accuracy beyond tested limits.

## Fill these gaps within accepted concepts first

- **Envelope extraction:** 1998/4 now provides positive/inverted and delayed envelopes for Type 23 Signal Processor or other instruments.
- **Slew/integration:** sh.7437 now smooths stepped voltages and shapes pulse contours with independently patchable positive and negative slews. This complements n01 without adding a second random generator.
- **Trigger conditioning:** a threshold with hysteresis could turn arbitrary slow/random/audio signals into events. 1998/4 now exposes immediate and delayed envelope threshold gates with hysteresis. sh.7437 also provides START/SUSTAIN conditioning and low-level pulse comparators. n01 already conditions its own trigger input, but does not expose a general trigger extractor.
- **Narrow spectral isolation:** Type 9414 Frequency Analyzer already fills this gap. A broad option may also cover much of the band-limiting proposal.
- **Multiplication:** c2-34 Balanced Modulator already has a place; avoid adding another ring modulator under a different name.

## Radiophonic possibilities

**EMS-inspired Random Voltage Generator:** explore two random-voltage channels, held or gliding motion, mean rate and timing variance, with an event output and manual reselection/inhibit if the panel supports them. The [user-provided EMS reference](https://amsynths.co.uk/2026/08/11/ems-random-voltage-generator/) combines sampled voltages with variable timing. Its exact circuitry, channel numbering and ranges are not requirements. This would develop the user's late n01 inspiration as purpose-built studio equipment.

**External sample-and-hold / track-and-hold:** n01 STEPPED samples its own ramp only. Sampling arbitrary patched audio/CV is a real capability gap, and fits the accepted Series Two S&H direction. Decide whether this belongs in the random-voltage instrument or a small independent sampler.

**Studio noise processor:** CV-controlled spectral movement, bands and perhaps bursts could take the multiple-voice EMW and noise references further. Keep n01's approved manual noise voice intact; this is a later processor proposal rather than a revision to n01.

Frequency shifting with separate UP/DOWN outputs, voltage-controlled switches, modern manual routers, studio contour/filter tools and tape/delay/reverb remain the existing Radiophonic direction. These ideas do not commit the whole list or a development order.

## Computing boundary

Keep arithmetic, logic, accumulators, explicit state and coordinated event processing in Series Three. The requested Patchable Devices Window Generator inspiration is a multi-envelope/stage-event idea; a voltage-window comparator is a different possible instrument. The Voltage Sequencer remains a candidate for programmable voltage/state selection. Avoid adding incidental coloration to exact arithmetic without an explicit design reason.

## Status

LM-21 Mk III and RM1010 are completed Wardenclyffe modules. The XL-35 filter pair is canonized. No optional gap-filler below is a locked brief. The meter and band-limiting ideas remain optional: sn-16u already covers much of that territory. The Radiophonic and computation ideas remain flexible later-series references, not a locked inventory.
