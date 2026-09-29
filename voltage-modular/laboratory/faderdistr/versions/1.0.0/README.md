# Fader|Distr A and B 1.0.0

Canonical source release, approved 2026-09-29. Open `faderdistrA.vmod` or `faderdistrB.vmod` in
Voltage Module Designer. Their matching Java exports and hero images are included alongside them.
`SHA256.json` records hashes for all six release assets.

## Variants

- **Fader|Distr A** has a fixed linear law. At the BIAS center, each output has a 0.5 weight.
  Its relay voice is the heavier first model: a lower knee and woollier driven tone.
- **Fader|Distr B** has a fixed equal-power law. At center, each output has an approximately
  0.707 weight. It retains the family character with a later knee, more open tone range, and a
  different asymmetric compression balance.

Both are mono-first manual utility modules. BIAS performs two independent functions in parallel:
X/Y crossfade to Z and S distribution to 1/2. The selected endpoint is level-conscious with no
separate gain stage. Each uses native-rate routing, a short manual-control transition, and three
low-cost relay voices. Direct host bypass copies S to 1 and 2 and X to Z.

## Validation

From the repository root:

```powershell
python voltage-modular/laboratory/tools/validate_faderdistr_core.py
python voltage-modular/laboratory/tools/validate_faderdistr_a.py --sdk C:/ProgramData/Voltage/voltage.jar
python voltage-modular/laboratory/tools/validate_faderdistr_b.py --sdk C:/ProgramData/Voltage/voltage.jar
```

The user confirmed both Designer builds and listening tests. Future edits belong in a development
copy, leaving this release intact.