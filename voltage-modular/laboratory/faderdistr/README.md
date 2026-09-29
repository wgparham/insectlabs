# Fader|Distr

Fader|Distr A and B 1.0.0 are canonical in [versions/1.0.0](versions/1.0.0/README.md). Their
locked working pairs remain in `development`:
`faderdistrA.java` / `faderdistrA.vmod` and `faderdistrB.java` / `faderdistrB.vmod`, each with
its matching hero image and SHA-256 manifest.

Fader|Distr A and B use matching panels and a single large BIAS knob. A has the fixed linear
law; B uses fixed equal-power law. There is no CV input or law switch.

BIAS controls two independent mono functions at once: the FADER crossfades X and Y to Z, while
the DISTR distributes S between 1 and 2. At −1, X and 1 are selected; 0 is balanced; +1 selects
Y and 2. Direct bypass follows the shared standard: S goes unchanged to both 1 and 2, and X goes
unchanged to Z.

The native-rate A prototype is in `prototype`, with regression coverage in `tests`. Both matched
Designer/source pairs have passed source-pair integrity checks, SDK compilation, and Designer
build/load listening tests. They remain locked development baselines until a numbered release is
prepared.

Fader|Distr A uses a fixed, low-CPU relay voice ahead of its linear routing: a gentle asymmetric
contact/transformer knee and one tone state per input. Fully selected ordinary-level signals stay
at unity; stronger material becomes heavier and woollier without a gain stage. Fader|Distr B keeps
the same family intent with a later knee, more open tone range, and its own asymmetry calibration.


See the [utility specification](../../docs/Series-One-Utilities.md) and
[infrastructure standards](../../docs/Module-Infrastructure-Standards.md).
