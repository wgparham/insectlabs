# Fader|Distr B User Manual

**Insect Laboratories · Voltage Modular · canonical release 1.0.0**


![Fader|Distr B panel](faderdistrB_hero.png)

Fader|Distr B combines a manual equal-power crossfader and a signal distributor. One BIAS knob moves both functions at once. There are no CV inputs, separate level controls, or pan-law switch.

## Connections

| Section | Jack | Function |
| --- | --- | --- |
| FADER | X, Y | The two mono sources being crossfaded. |
| FADER | Z | The weighted mix of X and Y. |
| DISTR | S | The mono signal to distribute. |
| DISTR | 1, 2 | Complementary weighted versions of S. |

FADER and DISTR are simultaneous but otherwise independent: X and Y do not enter the distributor, and S does not enter the crossfader. The module has no automatic input normaling; patch each signal explicitly.

## BIAS and equal-power law

BIAS travels between the left-side labels **X / 1** and **Y / 2**. At the left endpoint, Z follows X and output 1 follows S at approximately unity level; output 2 is at its opposite endpoint. At the right endpoint, Z follows Y and output 2 follows S. At the center, each side has approximately 0.707 weight. The same equal-power law is used for the distributor, which moves S between outputs 1 and 2.

The endpoint levels are level-conscious and there is no separate gain stage. Around center, the equal-power FADER may sum correlated X and Y louder than either source alone. That is normal for this law; check headroom with strongly related or identical signals.

Fader|Distr B shares the family’s relay-fader character with A, with a more open tone range, a later compression knee and a different asymmetric balance. The variant’s equal-power law is fixed; it is not selected by a panel switch.

## Example patches

**Crossfade two sources.** Patch them to X and Y, then take Z. Move BIAS from X toward Y. The center preserves perceived loudness more evenly with typical uncorrelated material than a linear law.

**Distribute one source.** Patch S and listen to 1 and 2. Use BIAS to move the signal between destinations. The same movement also changes the FADER weights, even if X and Y are not patched.

**Use both sections together.** Crossfade two sounds at X/Y/Z while independently distributing a third sound from S to 1/2. One BIAS control synchronizes the movement of both sections.

## Bypass

Host bypass makes the dry routes: X passes to Z, while S passes to both outputs 1 and 2. It is a direct CPU-saving path and does not crossfade the bypass transition. Use an external mute when click-free switching is important.

The fixed law and voicing distinguish B from [Fader|Distr A](../../../6121198a/versions/1.0.0/USER-MANUAL.md). Both variants are archived in [release 1.0.0](README.md).
