# FMC developer notes — 1.0.0

This snapshot documents the exact FMC release pair in this directory. The canonical module-level notes are [../../DEVELOPER-NOTES.md](../../DEVELOPER-NOTES.md); design evidence and source limits are in [../../research/Design-Research.md](../../research/Design-Research.md) and [../../references/README.md](../../references/README.md).

The module contains four independent lanes and selects BF-2, TR-2, DM-2 or DD-2 behavior per lane. The exported Java and Designer-embedded Java are synchronized. All physical A–D knobs use normalized 0–1 values and voice-aware tooltips and typed-value mappings. Defaults assign the four voices in lane order (BF/TR/DM/DD). Each channel uses its own state, buffer history and deterministic noise seed.

`ProcessSample()` runs only channels that are patched or retain active delay/Hold state. `ProcessBypassedSample()` does not read voice controls or CV; it performs direct lane routing, maintains the half-sum MIX output and marks histories for reset at resume. The delay memories use 131072-element rings. Per-channel storage is about 4.1 MiB, or approximately 16.3 MiB for four lanes, before smaller state.

The callback contains no per-sample allocations. Four headless JDK 27 benchmark runs measured 1.13–1.55 μs per sample frame for four active lanes on the development machine; this measurement does not translate to a host CPU percentage. The current implementation constructs the core at 48 kHz, so behavior at other rates is not verified. There is no oversampling stage.

DSP choices and assumptions are described as models in source comments and the design research. The DM-2 style is BBD-inspired; TR/BF are modulation models; DD is a variable-rate, reduced-resolution delay. None is claimed to be a component-accurate recreation.
