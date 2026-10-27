# swCXI Development History

The earlier 16-way bidirectional MUX/DEMUX was scrapped because the host uses dedicated input/output jacks. The accepted replacement pairs ten inputs and ten outputs with separate common jacks, one shared selected stage and selected-row pass-through when both commons are unplugged.

Manual selection, rising-edge sequencing and 5 V cyclic CV addressing were agreed during design. Negative CV wraps backward. Clock plus Select CV samples the address only on the rising clock edge. Later v0.1.2 also made unclocked Select CV use the knob-selected number of available stages. Short declick fades deliberately soften audio, CV and gates. The detailed developer change notes are preserved in [v0.1.1](CHANGES_v0.1.1.md) and [v0.1.2](CHANGES_v0.1.2.md).

The [supplied v0.1.2 pair](supplied-v0.1.2/) is the exact pre-cleanup reference. Its executable sources agree after ignoring Designer comments. The project Notes said v0.2.0, while filenames named v0.1.2, and the knob property range was reversed relative to both executable sources. During release review the user explicitly chose CCW 1 / CW 10 / default 1. Canonical metadata is reconciled to that choice.

The 1.0.0 cleanup preserves active DSP samples exactly, corrects host bypass infrastructure, removes obsolete notification cases and externalizes working control Notes. Native GUI testing of the cleaned archive was not repeated; promotion followed the user's explicit test/cleanup/promotion authorization.
