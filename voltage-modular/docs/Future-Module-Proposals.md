# Optional gap-fillers and later-series ideas

Proposal review: 16 October 2026. LM-21 Mk III and RM1010 are canonized; RM1010 supersedes the earlier minimixer concept, and no separate minimixer module is planned. XL-35h and XL-35c add the matched high-pass / low-pass filter pair, and 21-24eq adds passive-voiced frequency correction. Twenty-four Wardenclyffe products are canonized; sn46 Standards is canonical v1.0.0 and the planned Wardenclyffe Station lineup is complete. Other additions below remain optional proposals. These suggestions come from comparing current capabilities and the completed briefs; they are not historical user decisions.

## Completed Wardenclyffe module and optional gap-fillers

| Priority | Proposal | Gap and recommended scope |
| --- | --- | --- |
| Completed | LM-21 Mk III Matrix Mixer / Artificial Acoustic Distance Generator | A bespoke 4×4 mono audio matrix with four independently processed output rows, bipolar crosspoints, bass/treble tone shaping, per-row level and ambience, shared reverb perspective, and a full-mix output. This intentionally expands the original compact-mixer brief into a custom WDR/RAI-style studio centerpiece. |
| 2 | Measuring Amplifier / Level and Polarity Meter | An instrument for checking audio level, DC offset and polarity. Start with a large slow meter, manual ranges and AC/DC selection. Decide RMS/peak/average behavior explicitly and label it honestly. A unity through path or a monitoring-only input would avoid unintended coloration. |
| 3 | Band-Limiting Amplifier | Manual low and high cut for preparing audio, noise and feedback paths. Consider a broad mode in the canonical Type 9414 Frequency Analyzer first; add a separate box only if independent high/low limits justify it. |

LM-21 Mk III is canonical at v1.1.0, and RM1010 is canonical at v1.0.0. RM1010 is Wardenclyffe Station’s compact, period-faithful mixing amplifier; it replaced the earlier minimixer proposal. It does not inherit LM-21 features by default.

sn16u 2.1.0 provides DC/Vpp, CV pitch, audio Hz and RMS measurement, along with independent clean HPF/LPF paths. The meter and band-limiting proposals above are substantially covered by that release, not separate approved modules. Revisit only if native testing exposes a remaining need. Avoid promising laboratory measurement accuracy beyond tested limits.

## Fill these gaps within accepted concepts first

- **Envelope extraction:** 1998/4 now provides positive/inverted and delayed envelopes for Type 23 Signal Processor or other instruments.
- **Slew/integration:** sh.7437 now smooths stepped voltages and shapes pulse contours with independently patchable positive and negative slews. This complements n01 without adding a second random generator.
- **Trigger conditioning:** a threshold with hysteresis could turn arbitrary slow/random/audio signals into events. 1998/4 now exposes immediate and delayed envelope threshold gates with hysteresis. sh.7437 also provides START/SUSTAIN conditioning and low-level pulse comparators. n01 already conditions its own trigger input, but does not expose a general trigger extractor.
- **Narrow spectral isolation:** Type 9414 Frequency Analyzer already fills this gap. A broad option may also cover much of the band-limiting proposal.
- **Multiplication:** c2-34 Balanced Modulator already has a place; avoid adding another ring modulator under a different name.

## Teletape Workshop possibilities (formerly Radiophonic)

Teletape Workshop now has seventeen canonized modules, including SW3 1.0.0, a dual CV/manual switch with separate 2-to-1 and 1-to-2 routes. N02 fills the dedicated audio-noise source role with independent EMS-inspired analog and clocked digital voices; its sequence lengths automatically increase from 7 to 15 to 31 bits with SPEED. The final inventory and next module brief remain open. The options below are possible additions rather than mandatory gaps.

**RVG Random Voltage Generator — canonical v2.0.1.** RVG provides two independent stepped random voltages, variable event timing, manual and external reselection, inhibit/free-run, a +4 V trigger, and compensated/factory/failure hold modes. The design models workflow and response rather than claiming a component-exact EMS circuit. The [module research notes](../Teletape%20Workshop/RVG/RESEARCH-NOTES.md) and [references](../Teletape%20Workshop/RVG/references/README.md) preserve the unresolved circuit questions.

**Additional sample-and-hold capacity:** TSL channel 3 already provides a track-and-hold function, so external T&H is not wholly absent from the series. A dedicated sampler could still add independent channels, sample-and-hold behavior, or a different workflow; treat that as optional unless testing reveals a specific need.

**Noise processing beyond N02:** CV-controlled spectral movement, bands and perhaps bursts could take the multiple-voice EMW and noise references further. This is distinct from N02's canonical role as a source; OFB and SVF already provide substantial filtering. Keep n01's approved manual noise voice intact.

Frequency shifting with separate UP/DOWN outputs, modern manual routers, a continuous studio contour/filter tool distinct from the existing OFB and SVF, and tape/delay/reverb remain possible Teletape Workshop directions. SW3 now covers a compact dual 2:1 switch utility. The next proposed concept is a separate 16-way MUX/DEMUX router: two banks of sixteen signal jacks, one position/address control with CV, route indication, and a MUX/DEMUX mode. It should preserve a clear distinction between signal routing and its gate/latch controls. The existing mockup is a starting point, not a locked design brief.

## Computing boundary

Keep arithmetic, logic, accumulators, explicit state and coordinated event processing in Series Three. The requested Patchable Devices Window Generator inspiration is a multi-envelope/stage-event idea; a voltage-window comparator is a different possible instrument. The Voltage Sequencer remains a candidate for programmable voltage/state selection. Avoid adding incidental coloration to exact arithmetic without an explicit design reason.

## Status

LM-21 Mk III and RM1010 are completed Wardenclyffe modules. The XL-35 filter pair is canonized. No optional gap-filler below is a locked brief. The meter and band-limiting ideas remain optional: sn-16u already covers much of that territory. The Teletape Workshop and computation ideas remain flexible later-series references, not a locked inventory.
