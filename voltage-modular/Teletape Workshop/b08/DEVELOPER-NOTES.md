# b08 Blanking Panel — Developer Notes

Canonical v1.0.0 in Teletape Workshop. Java identity is class `b08`, package `com.insectlabs.b08`; the archived pair is [`versions/1.0.0/b08.vmod`](versions/1.0.0/b08.vmod) and [`versions/1.0.0/b08.java`](versions/1.0.0/b08.java). See the [manual](versions/1.0.0/USER-MANUAL.md) and [release review](versions/1.0.0/REVIEW.md).

## Design and implementation

The panel is 1.6 inches wide (8 HP). It combines the Teletape Workshop wordmark, tape-reel artwork and the “XIII : III” label. The Designer project embeds the artwork; editable source SVGs and the font/license are in the series-level [`designResources`](../designResources/README.md) folder.

There are two `VoltageImage` components and one non-editable `VoltageLabel`. No jacks, knobs, sliders, switches, buttons or indicators are present. The generated `ProcessSample()` body is empty. The module has no audio or CV path to bypass. Its only purpose is to occupy rack space and display artwork.

## Lifecycle, validation and maintenance

The module uses generated lifecycle callbacks without custom state or timers. It allocates no DSP state and has no meaningful per-sample workload. Java 17 compilation of both the exported and embedded source, Designer project round-trip, source-pair agreement, width, component inventory and empty process body were validated for v1.0.0. The user confirmed a Voltage Modular build/run and basic panel check. Cherry Audio account publication is not recorded as complete.

For future revisions, compare the panel image in Designer, confirm the width and component inventory, keep the embedded and exported Java synchronized, compile both source forms, update the manual/review and regenerate the SHA-256 manifest. No audio fixture or DSP primitive is applicable.
