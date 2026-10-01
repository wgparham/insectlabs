# Series One utility instruments

This document is the current utility status and implementation reference. The earlier design draft
was superseded by the approved releases and the [Collection Roadmap](Collection-Roadmap.md).

## Completed instruments

| Module | Release | Role |
| --- | --- | --- |
| [Signal Processor](../laboratory/signal-processor/versions/1.0.1/README.md) | 1.0.1 | Two independent mono PROC/VCA stages with bipolar gain, post-character offset, and gain CV |
| [Fader&#124;Distr A/B](../laboratory/faderdistr/versions/1.0.0/README.md) | 1.0.0 | Simultaneous X/Y-to-Z fader and S-to-1/2 distributor; A is linear, B equal-power |
| [sw1](../laboratory/sw1/versions/1.0.0/README.md) | 1.0.0 | Manual 2x2 relay router with toggle/gate behavior and optional CLK contact filtering |
| [sw2](../laboratory/sw2/versions/1.0.0/README.md) | 1.0.0 | Shared OFF/1–4 source selector and destination distributor with optional CLK contact filtering |

These four designs comprise five modules (Fader|Distr A and B are separate). They are manual, mono-first instruments. Their release notes, checksums, and tests define
their final behavior; this page does not supersede them.

## Shared collection behavior

- Ordinary unpatched signal inputs contribute zero unless a release documents a normalization.
- Fader|Distr is the only intentional simultaneous crossfade/distribution design. It has no hidden
  stereo architecture and no CV law selection.
- sw1 and sw2 use manual selection. sw2 position 0 initializes and resets to OFF.
- Direct host bypass follows [Module Infrastructure Standards](Module-Infrastructure-Standards.md).
  It is dry routing, not a wet/dry blend; it skips character/filter processing and freezes history.
- Character is assigned by purpose. SIGPROC supplies gain-dependent drive and a distinct VCA response; Fader|Distr
  supplies relay weight; sw1 supplies direct/optional contact treatment; sw2 supplies an open
  line-amplifier weight plus a distinct optional contact voice.
- Series One uses +5 V as its working modulation reference. Do not infer a collection-wide +10 V
  convention from a single future interface decision.

## Remaining utility direction

The completed manual routers meet the current Series One switching requirement. Future Series One
utilities are the retained Tone Burst Generator, Selective Amplifier, Dynamic Modulator, Pulse
Shaper, and Balanced Modulator. Voltage-controlled switches and more performance-oriented routing
belong to Series Two unless the roadmap is deliberately revised.

See the roadmap for the full source-generator and reference-instrument sequence. New utility work
must begin from the current [infrastructure standards](Module-Infrastructure-Standards.md) and use
TestBench for repeatable checks.

## Capabilities available beyond the utility panels

Generator includes a pure 1 kHz reference, Deep Tone has independently available Courtesy, SIN/RND accepts external audio through its FLT path, and n01 provides continuous and event-sampled random voltages. n01 STEPPED samples its own source, not arbitrary external audio. These existing functions should inform future briefs without being mistaken for a complete standards source, general-purpose filter or external sample-and-hold.

See [pending briefs](Series-One-Pending-Modules.md) for the six accepted unfinished roles, including Reference / Standards. Independent summing and measurement are the strongest optional utility gaps; [gap proposals](Future-Module-Proposals.md) explains their scope. These suggestions have not expanded the accepted inventory.
