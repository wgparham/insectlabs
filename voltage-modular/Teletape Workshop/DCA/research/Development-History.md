# DCA development history

This archive records the discarded pre-release iterations. The Java/VMOD snapshots and generated backups were not retained; the final implementation is archived in [versions/1.0.0](../versions/1.0.0/).


---

# dca v0.1.0 (from the author's mockup v0.0.1m)
First wired build. Panel, skin and Designer code untouched except the user regions; Notes read v0.1.0.
Everything is listed in ModuleBrief_dca.md. Checks: 33/33 headless (gain law, color, waveshaper, THD, noise, half-wave CV, smoothing, oversampling, pan, modes, module wiring). Not run in Designer.

---

# dca v0.1.3 (from the author's saved v0.1.2; panel edits and slider defaults are yours and untouched)
- DUAL L = mono mixdown (A + B + LINK L + LINK R, unity, no pan) when nothing is plugged into DUAL R.
- ST mode: only A's LEVEL, SHAPE, CV and PAN act; B no longer copies IN A.
  - IN B unpatched: PAN A is a plain pan pot for A (B not in the mix).
  - IN B patched (with or without IN A): PAN A is a balance control, A left and B right, centre = both full.
- BIAS is independent per channel in every mode, including ST.
- Amplifier noise now runs whether or not IN is patched (it was gated off when unpatched), so the "silent" side of a balance sits at the noise level set by LEVEL.
- Fixed: my v0.1.0 EditComponentValue region left out `super.EditComponentValue(...)`, so typed tooltip values would not have applied. Restored.
- Notes read v0.1.3. Checks 42/42 headless; not run in Designer.

---

# dca v0.1.4 (from your saved v0.1.2 file; panel untouched)
ST mode now works like the hardware normal:
- Only IN A: normalled to B (B has its own BIAS). PAN A is a balance control that moves both copies left or right.
- IN B patched: normal broken, A is left and B is right, PAN A is balance.
- Only IN B: A still in the mix, only its amplifier noise floor (the one case an unpatched channel makes noise).
- Nothing patched: nothing anywhere.
Noise and CPU: unpatched channels make no noise anywhere else, and a channel with nothing patched, BIAS centred and no forced noise does no processing.
Mono mixdown on DUAL L when DUAL R is empty: in ST with the normal, the two copies count once instead of doubling.
Notes read v0.1.4. Checks 47/47 headless; not run in Designer.

---

# dca v0.1.6 (headless-tested only; not yet tested in Designer)
Base: your saved vmod with note v0.1.5 (new skin, SetMidpointValue(1.0) on both faders).
1. BYPASS keeps the routing. DUAL L/R use the mode, balance/pan weights and mono-mixdown state frozen at the last processed sample (if bypassed from the start, seeded from the current mode and pan knobs). IN A/IN B still reach MONO A/B, links still sum into DUAL. No other DSP. [OPEN] relies on IsConnected() on the DUAL R output jack.
2. IND mode: IN A is normalled to IN B (copy of A through B's own LEVEL, SHAPE, CV, BIAS, PAN). Patching IN B breaks it. Splitter with independent bias.
3. SHAPE: code unchanged. Value 1.0 = LOG, 0.0 = LIN, 0.5 = EXP. With your swapped default of 1.0 the module now starts in LOG (Synthi-style), not LIN. The knob direction itself is only in the panel.
Tests: 54 headless checks pass, Java 8 -Xlint:all. Note field: v0.1.6.

---

# dca v0.1.7 (headless-tested only)
Base: your Designer-saved v0.1.6 vmod.
- IND mode: the IN A -> IN B normal from v0.1.6 is removed. Unpatched IN B is silent again (BIAS on B still acts). SERIAL and ST normals are unchanged.
- Bypass follows: in IND an unpatched IN B stays silent in bypass; SERIAL/ST normals hold.
- Note field: v0.1.7. Tests: all headless checks pass, Java 8 -Xlint:all.
