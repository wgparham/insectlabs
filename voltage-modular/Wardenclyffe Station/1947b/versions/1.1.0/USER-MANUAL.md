# r195L: SIN/RND Generator User Manual

**Insect Laboratories · Voltage Modular · canonical release 1.1.0**

Manual draft prepared 4 October 2026.

![SIN/RND panel](sinrnd_hero.png)

SIN/RND is a mono oscillator and noise source with five labeled modes. It provides a sine oscillator, direct white and filtered noise, noise FM, and a filtered audio path that turns MOD IN into a sound input when FLT is selected.

This guide describes [release 1.1.0](). Open `sinrnd.vmod` in Voltage Module Designer to build the module.

## Controls

| Control or jack | Range / default | Operation |
| --- | --- | --- |
| MODE | SIN, RND, RD2, MOD, FLT; starts at SIN | Selects the function described below. |
| FREQUENCY | 50 Hz–18 kHz; starts at C4 | Sets the oscillator pitch on a logarithmic control. |
| FINE | ±200 cents; centered | Fine tunes the oscillator up or down by up to two semitones. |
| AMP RANGE | 0.01 V, 0.1 V, 1 V or 10 V peak; starts at 1 V | Sets the available output range. |
| AMPLITUDE | Silence to selected range; starts at silence | Sets output level within AMP RANGE. |
| MOD IN | External modulation or FLT audio input | Its function depends on MODE. |
| MOD LEVEL | −200% to +200%; centered | Bipolar FM amount in SIN/MOD; input attenuverter/amplifier in patched FLT. |
| RND MOD | 0–100% | Sets internal random FM depth in MOD; blends white noise into patched FLT audio before filtering. |
| FILTER | Low/wide to high/narrow | Changes the noise filter used by RD2, filtered modulation, and the FLT audio path. |
| POWER | Panel switch | Fades the oscillator/noise output over 8 ms; off stops its live state after fade-out. |

## MODE behavior

| MODE | Main output | MOD IN behavior |
| --- | --- | --- |
| SIN | Sine oscillator. | External signal linearly frequency-modulates the sine when patched. |
| RND | Direct white noise. | Not used in this mode. |
| RD2 | Direct filtered white noise. | Not used in this mode. |
| MOD | Sine with internal white-noise FM. | A patched source takes over as external linear FM. |
| FLT, MOD IN empty | Sine with filtered-noise FM. | Unpatched, so filtered internal noise drives modulation. |
| FLT, MOD IN patched | Filtered audio input; sine is silenced. | Becomes an audio input to the noise filter. MOD LEVEL sets input gain/polarity; RND MOD blends in noise before filtering. |

In SIN and MOD, MOD LEVEL adjusts the external FM depth. Its ±200% range provides a strong range of control while remaining a bipolar attenuverter. In patched FLT, the same control scales incoming audio up to 2× at full positive setting. AMPLITUDE and AMP RANGE continue to set the final output level and range in FLT, just as they do in the other modes.

The FILTER control changes the character of noise and filtered audio; it is intentionally voiced rather than a claim to reproduce a specific historical filter circuit. The module’s sine and noise sources are designed to sound different from other Laboratory generators.

## Example patches

**Tune a sine.** Select SIN, use FREQUENCY for coarse tuning and FINE for a musical offset. Patch MOD IN only if you want external FM.

**Use either noise output.** Select RND for white noise or RD2 for filtered white noise at the main output. Adjust AMP RANGE and AMPLITUDE for level; FILTER affects RD2.

**Create random pitch movement.** Select MOD and raise RND MOD to add white-noise FM. Patch a modulator to MOD IN to replace the internal noise modulation with an external source.

**Filter an external sound.** Select FLT, patch audio to MOD IN, adjust MOD LEVEL, and sweep FILTER. Raise RND MOD to mix noise into the audio before filtering. The oscillator is silent in this patched FLT configuration.

## Power, bypass and release

The physical POWER switch uses an 8 ms fade; host bypass is a separate direct source mute that freezes state. Bypass is not click-free. The panel meter is averaged, and its indicator responds quickly when compression engages; neither is a calibrated meter.

For validation and release evidence, see the [SIN/RND 1.1.0 release record](README.md).
