# swCXI v0.1.2 (built on your Designer save of swCXI_v0.1.1.vmod; notes already v0.1.2)

Status: headless checks passed; not yet tested in Designer / Voltage Modular.

## Fixes
1. **Outputs written every sample again (reverts the v0.1.1 write-only-on-change).**
   Cause [READ, voltage.jar `VoltageAudioCable`]: every cable has two value slots. On each sample the
   sending module writes one slot and the receiving module reads the other; they swap every sample.
   A value written only once lands in one slot, so the reader alternates between it and whatever the
   other slot held. That gave: no voltage until the knob moved, nothing on a freshly patched Common Out,
   and 23.8784722 V = 24 V x smoothstep(23/24), the second-to-last sample of the 24-sample route fade,
   left in the other slot. The 10 ms refresh (480 samples, even) always hit the same slot, so it never helped.
   Removed: `lastOut`, `forceOutputs`, refresh countdown, `putOutput`, Jack_Connected handling.
   Kept: only routed stage inputs are read (reads have no side effects).
2. **Select CV without Clock now maps its 5 V cycle across the knob's stage count** (was always 10).
   Same wrap both directions. Knob at 1 with CV patched = always stage 1.
   Reset hold in unclocked CV mode compares against the same remapped bins.

## Tests
- `SwitchCoreTest_v0.1.2.java`: your test with the four unclocked-CV cases moved from knob 1 to knob 10
  (they assumed the old 10-stage mapping) plus two remap assertions. 109 pass.
- `validate.py` line 96: change "v0.1.0" to "v0.1.2".
- Compiles against voltage.jar with --release 17 -Xlint:all -Werror.
- Module stub test now models the two-slot cable; it reproduces your v0.1.1 symptoms (24 V / 0 V on alternate
  slots, empty Common Out) and passes on v0.1.2 (14/14).
