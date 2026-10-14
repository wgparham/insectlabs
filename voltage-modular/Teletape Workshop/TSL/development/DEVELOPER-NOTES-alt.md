> Historical development record. Current canon is [TSL 1.0.0](../versions/1.0.0/README.md), class `tsl`. Earlier alt manifests and descriptions below are superseded.

# TSL alternate candidate developer notes

Current candidate: **v0.2.0rc**, Teletape Workshop. Pair: `tsl_alt.java` (historical filename), `tsl_alt.vmod` (historical filename). [Checksums](SHA256-alt.json) include the hero image. Development only; not canonized or published.

## Signal flow and controls

Three independent symmetric fixed-rate limiters run at 48 kHz. Outputs advance toward input by at most rate / 48000 per sample, without overshoot. Active unpatched inputs target zero; nonfinite input becomes zero; active input values are bounded to +/-1,000,000 V for numerical protection.

Normalized knobs default to zero. Rate = 1000 * (0.05 / 1000)^position V/s: CCW fastest, CW most glide. No off position. Channels 1/2 subtract CV / 5 from knob position and clamp to 0–1. Positive CV speeds response; 5 V offsets full knob travel. This CV calibration is a design choice, not an established EMS specification. Tooltips show manual rate and 10 V traversal time; typed values are V/s and clamp to the rate range.

Channel 3 tracks through its slew limiter when T&H is unpatched or above 1 V. Connected at or below 1 V holds. There is no gate hysteresis or hold droop. The lamp is always lit. Initial output histories start at zero. Reset/preset/variation completion adopts current inputs on the next active sample, including channel 3 while held. Held voltages are not serialized.

## Bypass and maintenance

Direct host bypass reads only signal inputs, copies values unchanged to outputs, freezes histories and requests resumption. First active sample adopts current inputs for all three histories, including held channel 3. AtomicBoolean getAndSet avoids losing concurrent resume requests. Audio callbacks own voltage histories.

ProcessSample contains the three limiters; baseRateForKnob/knobForRate define forward/inverse mapping; readInput guards active inputs. No allocation, formatting or UI updates occur per sample. Exponential calculations remain per sample for immediate CV response. Manual controls change slope immediately without separate smoothing. CPU use is not benchmarked.

## Review — 2026-10-13

Fixed bypass history advancement, made resume requests atomic, removed the leftover Blank product name, corrected misleading control display names, set Notes to v0.2.0rc and qualified hardware-equivalence comments. Existing slew and T&H behavior was retained. Metadata edits were limited to product/control display names and module Notes; source synchronization updated embedded code/editor anchors. A full before/after binary metadata comparison could not be completed because the temporary backup was no longer accessible across sandbox calls.

Full source compiled against installed voltage.jar with JDK 27 --release 17 -Xlint:all without diagnostics. A temporary harness using extracted actual callbacks/helpers and minimal jack/knob substitutes passed rate endpoints, inverse entry, polarity, settling/no overshoot, independent channels, gate threshold/hold, CV endpoints, bypass direct transfer/frozen histories/no control reads, resume and nonfinite recovery. Embedded/exported normalized source agreement passed through the synchronization tool. File checksums verified.

Designer audition remains pending: check typed values, saved patches, reset/variation behavior, bypass transitions and T&H cable insertion/removal. These command-line checks do not constitute user-tested or published status. Repeat relevant checks after rate, gate, recall or bypass edits.


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
