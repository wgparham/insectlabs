# LM-21 Mk III Matrix Mixer

**Canonical release: v1.0.1**, user approved on 2026-10-08 after correcting the panel text, recompiling and testing. LM-21 is Wardenclyffe Station's 4×4 mono matrix mixer and Artificial Acoustic Distance Generator, with bipolar crosspoints, independent 984-inspired row character, pre-character MIX drive up to +12 dB, independent additive reverb and shared 0.5–30 second PERSPECTIVE. Its 5.5-second point is at 60% travel. FULL MIX averages A–D before gentle saturation.

Power has a 13.6-second warmup and 2.1-second cooldown. MULT stays live. Host bypass maps I–IV directly to A–D, silences FULL MIX and freezes processing/power histories. Active DSP remained sample-identical during cleanup. Native-host CPU profiling remains unmeasured.

- [Canonical files and release record](versions/1.0.1/README.md)
- [User manual](USER-MANUAL.md) and [developer notes](DEVELOPER-NOTES.md)
- [Canonical Designer project](versions/1.0.1/lm-21_mk3.vmod) and [Java export](versions/1.0.1/lm-21_mk3.java)

The historical prototype remains in the immutable release. Reusable DSP is extracted into [reverb-primitives](../../../reverb-primitives/README.md). The separate [minimixer](../minimixer/README.md) has its own development folder. The retired LM-21 development folder has been removed; make a new working copy from the archive when a future revision is needed.
