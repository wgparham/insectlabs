# CXL Algorithmic Reverberation — User Manual

CXL is a dark, modulated algorithmic plate reverb with one shared stereo tank. Its 12-bit and extended-character modes have different nominal decay ranges. The extended mode keeps audible converter grain while sustaining a continuous tail. It is an original software instrument inspired by plate and vintage digital reverb workflows, not an exact EMT or Lexicon reproduction.

## Controls

All six knobs have a normalized 0–1 sweep and default to noon.

| Control | Operation |
| --- | --- |
| PRE-DELAY | 0–250 ms. Fully counterclockwise is true zero; the sweep is exponential. Changing delay can create a brief pitch movement in the newly fed signal. |
| DAMP | 0–100%. Clockwise shortens the high-frequency decay relative to the nominal 500 Hz decay. |
| DECAY | Clockwise increases the nominal 500 Hz decay. UP/12-bit spans 0.8–5 s. DOWN/extended spans 3–30 s. The ranges overlap at 3–5 s. Bass can ring longer than the indicated midband value. |
| MIX | 0–100% wet, linear. Counterclockwise is dry; clockwise is wet-only. There is no center boost. |
| GAIN | 0–4× wet-input drive, with unity at noon. This changes excitation compression and saturation; it does not raise the dry input. |
| AMP | 0–2× final output amplitude, with unity at noon. It scales the dry/wet mix. |
| CHARACTER switch | UP selects the 12-bit character and 0.8–5 s decay range. DOWN selects the 8-bit-labeled extended character, softened to an 8.5-bit effective converter step, and 3–30 s range. Switching preserves the current tail and transitions smoothly. |

For a color comparison, set DECAY to the same typed value between 3 and 5 seconds in both switch positions. At the same knob position, switching modes changes the displayed decay range as well as the converter character.

## Connections and routing

- Either input can be used by itself. A single connected input feeds the complete tank at the same nominal level in either jack.
- With both inputs connected, they enter separate sides of one cross-coupled tank, preserving stereo differences.
- With both outputs connected, use the left and right pickups for stereo.
- The left output alone folds the complete left and right output paths to a mono average. The right output alone provides the right pickup; it also works with either single input.
- Host bypass routes the input signal directly to the outputs using the same mono/stereo normalization. It skips the effect and ignores MIX, GAIN and AMP. The tank holds while bypassed and resumes with its existing tail.

## Suggested starting points

- For a send/return, turn MIX fully clockwise and leave AMP at noon.
- GAIN and AMP at noon are unity. Increase GAIN for a denser, more compressed wet onset; use AMP to level the complete result.
- Try PRE-DELAY around noon for roughly 14.8 ms, then adjust by ear.
- For very short decays, note that the minimum 0.8 s setting measured faster than its nominal 500 Hz value in the headless test. The test method and limits are in the release report.

Reset or preset/variation recall clears the transient reverb history. Control values are saved by the host; the current tail is not serialized into a preset.
