# Historical Designer control specification

This early implementation checklist is preserved for control mapping provenance. It predates CED 1.0.0 naming cleanup and contains native Designer questions that were not resolved by the headless stub tests. The canonical pair and current validated behavior are documented in [release developer notes](../versions/1.0.0/DEVELOPER-NOTES.md), [manual](../versions/1.0.0/USER-MANUAL.md), and [review](../versions/1.0.0/REVIEW.md). Do not treat every proposal below as a hardware fact.

# Designer control spec for `dimensionChorus.java`

Create these controls in Designer with **exactly these Variable Names**; the user-region code in `dimensionChorus.java` refers to them. The "TEST-ONLY placeholders" block in that file exists only so it compiles against stubs. Delete it once Designer generates the real declarations (a duplicate declaration would not compile).

Defaults must match the core's defaults, because Voltage only sends `Switch_Changed` at load for switches **not** at their default, and toggles are assumed up until a `Button_Changed` arrives.

## Jacks (`VoltageAudioJack`)
| Variable | Display name | Tooltip text |
| --- | --- | --- |
| `audioInLeft` | In L | Left audio input. Also the mono input. CE-1 mode uses only this input. |
| `audioInRight` | In R | Right audio input. Normalled to In L when unpatched. |
| `audioOutLeft` | Out L | Left audio output. In CE-1 mode both outputs carry the same mono signal. |
| `audioOutRight` | Out R | Right audio output. |
| `rateCvIn` | Rate CV | Multiplies LFO rate: 0 V = no change, +/-5 V = +/-2 octaves. Unpatched = 0 V. |
| `depthCvIn` | Depth CV | Adds to Depth: +/-5 V = +/-100% (Depth is limited to 0 to 200% in total). Unpatched = 0 V. |

## Toggle buttons (`VoltageToggle`), Group ID 0, not initially toggled
| Variable | Display name | Behavior |
| --- | --- | --- |
| `dimensionButton1` .. `dimensionButton4` | 1, 2, 3, 4 | Dimension-mode presets. Several can be down at once unless Exclusive is on. |
| `exclusiveToggle` | Exclusive | On: only one button stays down (the most recent press wins). Off: combinations allowed. |
| `companderToggle` | Compander | On: NE570-style compressor/expander around the delay. Only audible together with BBD noise. |

Group ID is fixed at design time, so Exclusive is implemented in code (`releaseOthers`), not with a radio group.

## Switches (`VoltageSwitch`), default position 0
| Variable | Positions | Notes |
| --- | --- | --- |
| `modeSwitch` | 0 Dimension, 1 CE-1 | Changing mode clears the wet path once. |
| `combineSwitch` | 0 Summed LFOs, 1 Average, 2 Parallel taps | Only matters when 2 or more buttons are down. |
| `inputSwitch` | 0 Stereo, 1 Mono | Mono feeds the average of L and R to both channels. |
| `vibratoSwitch` | 0 Chorus, 1 Vibrato | CE-1 only. Vibrato outputs wet only. |

Verify position numbers against the physical up/down/center operation (handbook section 8).

## Knobs (`VoltageKnob`), continuous
| Variable | Min..Max | Default | Meaning |
| --- | --- | --- | --- |
| `mixKnob` | 0..1 | 1.0 | Dry/wet. 0 = exact dry. Show as percent. |
| `rateKnob` | 0..1 | 0.6836408 | LFO rate scale, log: x0.05 to x4, default x1.00. Custom tooltip and typed entry. |
| `depthKnob` | 0..2 | 1.0 | Sweep half-range scale. 0 = fixed center delay. Show as percent. |
| `noiseKnob` | 0..1 | 0.0 | BBD noise, squared law, 0 = off, top about -26 dB re 5 V. Tooltip and typed entry in dB. |
| `ceRateKnob` | 0..1 | 0.5466027 | CE-1 LFO rate, log: 0.05 to 12 Hz, default 1 Hz. Tooltip and typed entry in Hz. |
| `ceIntensityKnob` | 0..1 | 0.5 | CE-1 sweep half-range, 0 to 2.4 ms (guess). Show as percent. |
| `ceDriveKnob` | 0..1 | 0.5880456 | CE-1 preamp drive, log: x0.1 to x10, default x1.5. Tooltip and typed entry in x. |

## Behavior decisions that need your approval (nothing here is hardware-documented)
1. R input is normalled to L when unpatched, in processing and in bypass. A mono source then feeds the stereo widener directly.
2. Rate CV is exponential (0.4 octave/V); Depth CV is linear (0.2/V). Unpatched CV is neutral.
3. Mix at 100% is an equal dry/wet blend, and OFF (no buttons) is exact dry at unity gain.
4. Button presses crossfade over about 20 ms instead of switching instantly (handbook section 10 treats selector steps as immediate unless you approve a transition).
5. Host bypass: direct routing, histories frozen, wet path cleared once on return, LFO phases preserved.

## Things that only a native Designer/Voltage run can settle
- `VoltageToggle.SetValue(0.0)` exists and behaves as assumed (used only in `releaseOthers`).
- Whether `SetValue` on a toggle sends its own `Button_Changed` (the code is safe either way; both cases are tested against stubs).
- What `newValue` means inside `EditComponentValue` for a knob: the code treats it as the typed number in the displayed unit, converts it to a knob position, and lets the base implementation apply it.
- `ModuleType` for an effect: the template says `ModuleType_Source`. Change it in Designer's module properties and check the enum name.
