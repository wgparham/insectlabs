# Fader|Distr

The next Laboratory family. Fader|Distr A is the locked linear-law working pair:
`faderdistrA.java`, `faderdistrA.vmod`, and `faderdistrA_hero.png` in `development`.

Fader|Distr A and B use matching panels and a single large BIAS knob. A has the fixed linear
law; B will use fixed equal-power law. There is no CV input or law switch.

BIAS controls two independent mono functions at once: the FADER crossfades X and Y to Z, while
the DISTR distributes S between 1 and 2. At −1, X and 1 are selected; 0 is balanced; +1 selects
Y and 2. Direct bypass follows the shared standard: S goes unchanged to both 1 and 2, and X goes
unchanged to Z.

The native-rate shared prototype is in `prototype`, with regression coverage in `tests`.
Fader|Distr A's matched Designer/source pair has passed source-pair integrity checks, SDK
compilation, and the initial Designer build/load check. It remains a development baseline until
the complete DSP and host test pass are approved.

The `panfade` directory is retained temporarily because the active Designer project is open from
this location. Its product identity is Fader|Distr; rename the working directory after Designer
is closed.

See the [utility specification](../../docs/Series-One-Utilities.md) and
[infrastructure standards](../../docs/Module-Infrastructure-Standards.md).
