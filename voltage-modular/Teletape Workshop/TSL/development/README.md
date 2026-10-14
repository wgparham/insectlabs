> Historical development record. Current canon is [TSL 1.0.0](../versions/1.0.0/README.md), class `tsl`. Earlier alt manifests and descriptions below are superseded.

# TSL — Triple Slew Limiter Development

This is the active development folder for the Teletape Workshop Triple Slew Limiter (TSL).

## Earlier baseline pair (`tsl.java` / `tsl.vmod`)

- An initial three-channel Voltage Modular skeleton is present in `tsl.java` and `tsl.vmod`, with a matching `tsl_hero.png` mockup.
- The mockup shows one voltage input, one voltage output, and one `Voltage 1–3` control per channel, plus three V.C. input jacks. The archived rack-unit photograph corroborates these labels, the numbered 0–10 control scales, the Voltage In/Out and V.C. Input groups, and a power indicator.
- The EMS Synthi 100 brochure describes the TSL reference behavior: a unity-gain follower whose maximum slew is controlled by voltage, with an exponential slew-control response and no output-level control. It illustrates using keyboard velocity to control pitch-glide rate. The published range is 1 ms–10 s, but no voltage excursion is specified for converting that range to V/s.
- The Synthi 100 TSL schematic shows a manual control labeled `POT SLEW RATE` and a separate `I.C. SLEW RATE CONTROL` input. The user reports that the standalone rack units used circuits taken directly from the Synthi 100; an exact standalone revision has not been independently compared.
- A first DSP pass now implements three independent, bidirectional fixed-rate limiters. Each output follows its input without gain change or overshoot; an unpatched input reads as 0 V. Host bypass passes the inputs directly to their outputs.
- Baseline restored for testing: three independent symmetric fixed-rate slew limiters. Normalized knobs run from 0.0 = 0.05 V/s (fully CCW, longest glide) to 1.0 = 1000 V/s (fully CW, fastest response), exponentially. There is no CCW off/pass-through position. V.C. is +1 V doubling / −1 V halving of rate, clamped to 0.05–1000 V/s. The first DSP behavior was reconstructed from the recorded initial implementation; no original saved pair was found. Current panel edits, normalized knob ranges, and lamp wiring were preserved.
- The panel power lamp lights when any of the three rate knobs is above zero and turns off when all three are at zero. It follows the controls independently of host bypass.
- Java and Designer metadata use 0.0–1.0 for all three knobs. DSP uses the normalized position directly, so clockwise increases rate and reduces glide. This intentionally restores the original test direction.
- Slewing audio can strongly reduce amplitude when the waveform changes faster than the rate limit. Steady DC eventually reaches the input at unity gain. For pitch glide, patch sequencer pitch CV through TSL into oscillator pitch input. Fully CCW is very slow: at 0.05 V/s, a 5 V step takes 100 seconds. Fully CW remains a finite 1000 V/s limiter, not true audio bypass.
- The test behavior keeps each channel symmetric for rising and falling signals. This separates the TSL from Wardenclyffe's sh.7437, which divides positive and negative slew into distinct paths and adds patchable contour/pulse feedback.
- Rate limiting uses the native 48 kHz sample interval and preserves each channel's state between samples. The first processed sample initializes each channel at its current input to avoid an artificial startup ramp.
- The restored active pair compiled against the installed Voltage Modular SDK on 2026-10-13 and its embedded/exported sources are synchronized. Direct checks passed for original knob direction and endpoint rates, CV doubling/halving, rate-limited steps, and no overshoot. The previously compiled tslBlank pair remains the unwired comparison scaffold. Host audition of this restored baseline is pending; publication is unverified.

## Unwired front-panel pair

`tslBlank.java` and `tslBlank.vmod` retain the current panel, controls, skin and layout with empty initialization/sample/bypass user-code regions and no DSP fields or helpers. The blank uses class `tslBlank` in package `com.insectlabs.tsl`, a distinct Designer test UUID, and the display name “Triple Slew Limiter Blank”. Its knobs, jacks and lamp are unwired. It is a comparison scaffold, not a release.

## Research references

The [EMS slew-limiter research packet](../references/EMS-Slew-Limiter-Research.pdf) consolidates Synthi 100 and Sequencer 256 evidence, schematic scans, prototype choices at the time of compilation, source hashes, and unresolved questions (36 pages, compiled 2026-10-13). Its prototype notes describe a prior comparison setting; use this development record for the restored baseline behavior.

Shared EMS material is catalogued in [../../references/README.md](../../references/README.md).

- [Synthi 100 brochure](../../references/EMS%2520%2520Synthi%2520100%2520Computer%2520Synthi.pdf), printed p. 2, describes the published TSL behavior and performance range.
- [Studio Sound May 1975 equipment survey](https://www.worldradiohistory.com/Archive-All-Audio/Archive-Recording-Engineer/Archive-Studio-Sound/70s/Studio-Sound-1975-05.pdf), printed p. 55, independently lists three Synthi 100 slew limiters with a 1 ms–10 s range, but does not state a 0–10 dial mapping.
- [Synthi 100 service schematic](../../references/ems_synthi_100_sch.pdf#page=25), drawing EMS 32/12 Issue C, shows the TSL channel topology and control inputs. The margin note says R2 was changed to 39 kΩ, while the schematic body labels it 33 kΩ; treat this as revision ambiguity.
- [EMS rack-unit lineup photograph](../../references/EMS-Rack-Gear-Brokerage-Sale-110521-Cover-2.webp) is visual evidence for panel labels, control scales, and jack groups, not the electrical response or calibration.
- [3xSlew_Schematics.pdf](../../references/3xSlew_Schematics.pdf) is a topology example for a triple slew limiter, not confirmed to be the EMS design.
- [NotesTowardsAtsl](../../references/NotesTowardsAtsl) contains gathered research fragments. Treat its specific component, topology, and performance claims as unverified until supported by reliable sources.
- [# Software Slew Implementation Block.py](../../references/%23%20Software%20Slew%20Implementation%20Block.py) is an untested code sketch, not production DSP or a selected design. Its per-tick voltage limit needs an explicit update interval if a volts-per-second rate is used.

Use these sources to inform implementation choices while keeping unresolved numeric mappings explicit.

## Alternate DSP import

`tsl_alt.java` is integrated into `tsl_alt.vmod` using the VMOD's full Designer panel scaffold. Its class/constructor and Designer class name are `tsl_alt`; the original import is preserved in `tsl_alt.java.import-original.txt`. The supplied `GetSampleRate()` call was replaced with the host's 48000 Hz callback rate because that method is unavailable in the installed SDK. The pair compiled on 2026-10-13. Its supplied DSP uses clockwise-increasing glide, a 5 V full-span CV shift, and an always-lit lamp; Designer audition remains pending.

On 2026-10-13, the supplied tsl_alt2.java replaced the alternate pair's embedded source. The matching tsl_alt.java was refreshed from that file, and the preceding alternate export is retained as tsl_alt.java.pre-alt2.txt. All Designer panel metadata remained byte-identical; the imported source compiled successfully. Designer audition remains pending.

## Current alternate candidate — v0.2.0rc

`tsl_alt.java` / `tsl_alt.vmod` has completed code review with corrected direct bypass/resume. Channel 3 is slew plus gated track/hold; channels 1/2 retain 5 V full-span rate CV. The lamp is always lit. See [developer notes](DEVELOPER-NOTES-alt.md) for behavior, validation and outstanding Designer checks. [Checksums](SHA256-alt.json) cover the pair and hero. Baseline and blank pairs are comparison material; this candidate is not canonized.


## Latest source integration — 2026-10-14

The newly supplied Java supersedes the standards-reviewed implementation described above. Its user code was integrated into the existing full Designer scaffold under class tsl_alt. The supplied abbreviated tslBlank source and prior VMOD are preserved in integration-backup/. All non-code VMOD metadata remained byte-identical; embedded/exported source agreement and SDK compilation passed. SHA256-alt.json was refreshed.

Current imported behavior: CCW slow / CW fast, 1 V/octave rate CV, channel 3 gated track/hold, animated lamp, initialization benchmark, and custom preset code. The import retains the supplied continuous-history bypass and plain boolean resume flag. Earlier standards-review and behavioral-test claims do not apply to this new implementation. It requires a fresh standards review and native audition.

## Rate mapping correction — 2026-10-14

The files supplied for this edit contained the earlier baseRateForKnob implementation (5 V full-span CV, atomic resume and constant lamp), superseding the prior import description. Current mapping is now 0.0/CCW = 0.05 V/s and 1.0/CW = 1000 V/s, exponential. CV adds CV/5 to normalized position, preserving positive CV as faster. Typed V/s uses the matching inverse. Channel 3 T&H is unchanged.

20 seconds applies to a 1 V step at minimum rate, not a maximum duration: 5 V takes 100 seconds and 10 V takes 200 seconds. Tooltips now show both 1 V and 10 V traversal times. SDK compilation passed; reflection checks of the compiled rate/inverse helpers passed endpoints, intermediate positions and inverse round trips. A scalar integration check using the helper's minimum rate reproduced these times within one sample of floating-point accumulation. Full callback/native audition was not performed for this edit. Source synchronization passed, all non-code VMOD metadata remained byte-identical, and candidate hashes were refreshed.

## Manual control span — 2026-10-14

User requested the full manual sweep contribute 0–1 equivalent control volts instead of 0–5 V, with external CV sensitivity unchanged. rateForControls now sums normalized knob * 1 V and external CV, then divides by the existing 5 V full-rate span and clamps. All three knobs run CCW slow to CW faster: manual-only 0.05–0.3623898318 V/s (20–2.75946 seconds per volt). Channels 1/2 can reach 1000 V/s with sufficient positive CV; channel 3 retains its gate input and reduced manual range. This is internal rate control, not output amplitude limiting. Typed V/s entry clamps to the new manual range and uses the matching inverse.

SDK Java 17 compilation and compiled-helper reflection checks passed intermediate mappings, typed inverse, equality of full knob with +1 V CV at zero knob, unchanged CV sensitivity and typed-entry clamping. Source-pair synchronization and non-code metadata preservation passed; hashes refreshed. Native audition pending. This supersedes prior manual-range descriptions.


## Rollback — 2026-10-14

At user request restored the exact source/project bytes immediately preceding the 0–1 V manual-span experiment (Java SHA-256 prefix 93f514170c8b; VMOD 0bc9a90359c8). Manual range is again 0.05–1000 V/s, CCW slowest / CW fastest, with a full sweep equivalent to +5 V CV. Source-pair agreement and backup hashes verified; candidate manifest refreshed. The reverted experiment remains in integration-backup. Prior successful compilation applies to these exact restored bytes; no new host test claimed. This supersedes the manual control span entry above.
