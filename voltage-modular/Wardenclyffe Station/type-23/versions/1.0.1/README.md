# Signal Processor 1.0.1

Canonical source release, approved 2026-09-29. Open `signal_proc.vmod` in Voltage Module Designer.
`signal_proc.java` is the matching source export; `sigproc_hero.png` is the panel image.
`SHA256.json` records all three file hashes.

This maintenance release preserves the approved 1.0.0 DSP, control ranges, routing, mode behavior,
Colorbox-style bypass, tooltips, and two independent mono stages. It updates the saved Designer UI
state: the current panel skin is retained and Gain plus EXT LVL display their values as percentages.
No audio, CV, timing, or bypass behavior changed.

## Validation

The historical release project/source pair is synchronized, its hashes pass, and its Java source compiles with
the Java 17 Voltage Modular SDK. Future edits belong in a development copy, leaving this release intact.
