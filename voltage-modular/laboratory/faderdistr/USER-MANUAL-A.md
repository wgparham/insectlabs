# Fader|Distr A User Manual

**Insect Laboratories · Voltage Modular · canonical release 1.0.0**

Manual draft prepared 4 October 2026.

![Fader|Distr A panel](versions/1.0.0/faderdistrA_hero.png)

Fader|Distr A combines a manual linear crossfader and a signal distributor. One BIAS knob moves both functions at once. There are no CV inputs, separate level controls, or pan-law switch.

## Connections

| Section | Jack | Function |
| --- | --- | --- |
| FADER | X, Y | The two mono sources being crossfaded. |
| FADER | Z | The weighted mix of X and Y. |
| DISTR | S | The mono signal to distribute. |
| DISTR | 1, 2 | Complementary weighted versions of S. |

FADER and DISTR are simultaneous but otherwise independent: X and Y do not enter the distributor, and S does not enter the crossfader. The module has no automatic input normaling; patch each signal explicitly.

## BIAS and linear law

BIAS travels between the left-side labels **X / 1** and **Y / 2**. At the left endpoint, Z follows X and output 1 follows S at approximately unity level; output 2 is at its opposite endpoint. At the right endpoint, Z follows Y and output 2 follows S. The transition is linear. At the center, the FADER weights X and Y at 0.5 each, and the DISTR balances S between outputs 1 and 2.

The level-conscious endpoints avoid a separate gain stage. A centered crossfade can be quieter than either input by itself when X and Y are uncorrelated; matching source levels helps make the sweep predictable. The distributor’s complementary outputs change together as BIAS moves.

Fader|Distr A has the heavier, woollier relay voice of the pair. Its coloration is intended to be audible as a gentle thickening over ordinary use, while the module remains a simple fade/distribution utility.

## Example patches

**Crossfade two sources.** Patch them to X and Y, then take Z. Move BIAS from X toward Y.

**Distribute one source.** Patch S and listen to 1 and 2. Use BIAS to move the signal between destinations. The same movement also changes the FADER weights, even if X and Y are not patched.

**Use both sections together.** Crossfade two sounds at X/Y/Z while independently distributing a third sound from S to 1/2. One BIAS control synchronizes the movement of both sections.

## Bypass

Host bypass makes the dry routes: X passes to Z, while S passes to both outputs 1 and 2. It is a direct CPU-saving path and does not crossfade the bypass transition. Use an external mute when click-free switching is important.

The fixed law and voicing distinguish A from [Fader|Distr B](USER-MANUAL-B.md). Both variants are archived in [release 1.0.0](versions/1.0.0/README.md).
