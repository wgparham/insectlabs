# 1A-30 Deep Tone Generator / Modulator

**Canonical v1.0.0 — approved 2026-10-01.** The user approved the final 65 Hz OFFSET build. The released Java is byte-for-byte identical to the approved export; only the module-level Designer Notes version record changes. See [release review](REVIEW.md).

## Frequency and shape

- WHOLE is stepped 0-10; FRACTION is continuous 0-1; x1/x10/x100 multiplies their sum. The nominal range is 0-1100 Hz. Zero stops the Tone phase, holding its value rather than introducing a hidden minimum frequency.
- Defaults are WHOLE 2, FRACTION 0.616255653005986, x100: C4, 261.6255653 Hz. Both numeric and text Designer defaults are set. The two counters show the whole part and first two decimal digits of the summed pre-multiplier frequency: 2 and 61 at startup. FRACTION=1 carries into the whole display. The fraction counter represents hundredths (a displayed 3 means .03); the tooltip shows exact base and multiplied frequencies. Display truncation does not quantize tuning.
- OFFSET uses a signed fifth-power -65 to +65 Hz taper: quarter travel from center gives +/-0.0634765625 Hz, halfway gives +/-2.03125 Hz, and three-quarters gives approximately +/-15.42 Hz. This reserves more travel for slow motion. Beat runs at Tone frequency plus OFFSET, clamped at zero. Swell and Courtesy use absolute OFFSET even when that clamp prevents the Beat oscillator from reaching the requested negative frequency. Typed-Hz editing uses the inverse fifth-power mapping.
- SHAPE blends rising ramp at left, triangle at center and falling saw at right. The ramp/saw reset is softened with the existing polynomial correction widened to approximately 1 ms for audible Beat, 6 ms for Swell, and 2 ms for Courtesy. The reset is capped at one quarter of a cycle at high rates, so reset durations shorten at fast rates. This rounds the reset rather than filtering the entire mix; the centered triangle is unchanged. Changes are smoothed. Zero OFFSET holds the Courtesy/Swell phase; at the default triangle/initial phase, Courtesy holds -3 V.

## Independent function buttons

All four buttons initialize disengaged. They latch independently, retain state in presets/variations and support undo. Button lights follow the latched state. Main Amplitude initializes to silence. The instrument remains powered and Courtesy is active throughout.

1. EXTERNAL adds the input, normalized from 5 V nominal, to the mix.
2. TONE starts the sine oscillator and adds it to the mix. Stopping it fades out and holds its phase for the next start.
3. BEAT starts the second oscillator. With the small switch down, Beat DEPTH adds its shaped waveform to the mix. With the switch up, Beat DEPTH instead applies up to 100% linear FM deviation to Tone; the Beat signal is not also mixed into the output. Tone must be engaged to hear that FM.
4. SWELL applies non-inverting AM to the combined mix at the absolute OFFSET rate, using SHAPE. DEPTH spans 0-200%: 0% is unchanged; 100% reaches silence at the minimum; above 100% the envelope stays silent for more of the cycle. At 200%, the triangle envelope is silent for half the cycle. This provides deeper rhythmic contrast without gain above unity or negative gain/ring modulation. Beat Depth remains 0-100%. Swell operates independently of the Beat button/depth.

Manual tuning, shapes, gain, depths and function changes use approximately 10 ms smoothing. Buttons are functional starts, not power switches. A small input audio transient can still be audible when connecting a live cable; no special cable-insertion envelope is added.

## Main output and Courtesy

AMPLITUDE uses a curved taper from zero to a nominal 15 V peak per full-scale source. The sources sum without automatic level compensation; combinations can drive the stage harder. Compression begins above 3 V and approaches +/-14 V soft rails. A lone full-scale low-frequency source at maximum amplitude reaches approximately 11.8 V after compression. Typed Amplitude values are nominal volts. This is the approved output voicing.

The output stage uses two evaluations per sample with midpoint interpolation and simple averaging. This adds gentle high-frequency attenuation and constrains transients, but is not a complete bandlimited oversampling system. There is no injected noise, hum or drift in this release. All audio callbacks are allocation-free; UI updates occur at 20 Hz. No host CPU benchmark has been recorded.

COURTESY is the SHAPE waveform at absolute OFFSET, fixed at +/-3 V peak before optional polarity inversion. It is independent of Tone, Beat, External and Swell buttons, both depth controls, FM/Mix and main Amplitude. Up is normal polarity; down is inverted. Its waveform runs while the machine is on, including when the main output is silent. It holds a voltage at zero rate.

Host BYPASS silences Courtesy and freezes all processing histories. Following the existing direct-bypass standard, the main output passes the External input sample-for-sample when the External latch is engaged, otherwise it is silent. No controls, modulation, filtering or gain processing run in bypass. On resumption, controls are adopted and phases retained.

## Verification and listening checklist

Run `python tests/validate.py` with the installed Voltage SDK and JDK. Builds go to `C:/InsectLabs-Build/deeptone/checks` outside Dropbox. The validator compiles exported and embedded source with Java 17/warnings as errors; verifies pair integrity, metadata, C4 defaults and project round-trip; and exercises actual audio, UI notification, state and edit callbacks through test controls. Tests cover independent latches, undo/state restoration, Courtesy independence/polarity/rate/zero hold, measured C4, input levels, full-depth Swell, FM routing, bypass/freeze, typed values and extremes.

The user confirmed successful testing and canonized this build on 2026-10-01. Automated callback tests supplement that approval; they do not independently verify the native GUI or constitute a CPU benchmark. Future edits belong in a new development candidate, not in this immutable release folder.
