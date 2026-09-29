# Supplied Signal Processor mockup

Current reference: references/mockup-2026-09-28-rev2. All three supplied files are archived byte-for-byte with SHA-256 checksums. The first snapshot remains preserved separately. Original workspace files are unchanged.

## User-confirmed defaults

LEFT defaults to PROCESSOR. RIGHT defaults to VCA. The opposite selector angle settings are intentional and must be preserved.

Both selectors have numeric default 0. Their meaning differs because their physical directions are reversed:

| Panel role | Left ID / mapping | Right ID / mapping |
| --- | --- | --- |
| GAIN | knob1, -2 to +2, default +1 | knob4, -2 to +2, default +1 |
| OFFSET | knob2, -5 to +5 V, default 0 | knob6, -5 to +5 V, default 0 |
| EXT LVL | knob3, -2 to +2 per +5 V, default 0 | knob7, -2 to +2 per +5 V, default 0 |
| PROC / VCA | knob5: 0 = PROC, 1 = VCA | knob8: 0 = VCA, 1 = PROC |
| INPUT | inputJack1 | inputJack4 |
| OUTPUT | outputJack1 | outputJack2 |
| EXT INPUT | inputJack3 | inputJack6 |

Adapter predicates: left VCA when knob5 >= 0.5; right VCA when knob8 < 0.5. Tooltips, processing, initialization, reset, patch loading, and tests must all use those same mappings. Do not change either selector's angles merely to make its numeric interpretation match the other.

## Verified intake

- Project identity is com.insectlabs.sigproc.SIGPROC.
- Embedded and exported source agree using Colorbox's source comparison.
- Binary project round-trips byte-for-byte through the existing project tools.
- Both output ports are now actual audio outputs in project metadata and Java.
- Gain, Offset, and Ext Lvl ranges and source defaults now match the planned processor controls.
- Left selector angles remain 335/25; right remains 25/335. Both are two-position controls.
- Processing and bypass callbacks are still empty: this is not yet a functional processor.
- Saved Designer test values still hold 0.5 for Gain, Offset, and Ext Lvl on both channels. Those are distinct from the new constructor defaults (+1, 0, 0). They are saved test positions, not evidence that the declared defaults failed. Synchronize the working build's test state to defaults if a clean startup demonstration is desired; preserve the archived original.

VCA mode means the gain cannot become negative. With the supplied Gain +1 and Ext Lvl 0 defaults, the right channel initially passes its input at unity; it does not start as a closed envelope-controlled VCA. Keep those supplied defaults. To use an envelope, set Gain 0 and Ext Lvl +1 and patch 0-5 V to EXT INPUT.

## Visual and integration baseline

Preserve the supplied charcoal panel, metal knobs, divider, footer, control positions, and skins. The updated centered title is part of the current baseline. No extra output indicators are required.

Use the tested core's top channel for the left strip and bottom channel for the right strip. Preserve existing IDs and UUIDs. No normalization between channels and no offset CV. Explain Gain in multiples, Offset in volts, and Ext Lvl in gain contribution per +5 V through tooltips and numeric entry.

## Integration checklist (now completed through automated validation in development/)

1. Create a separate working project/source pair from revision 2, preserving module identity and artwork.
2. Embed the tested core in Designer user regions and supply controls through an audio-thread-safe integration.
3. Implement the side-specific mode predicates above and add tests for both default and alternate positions.
4. Wire both active and direct-bypass callbacks. Bypass reads only signal inputs and writes their own outputs, without control/CV reads or smoothing advancement.
5. Verify project/source agreement, editor anchors, unchanged artwork/layout, SDK compilation, correct defaults, numeric entry, and independent routing.
6. Open/build in Designer, confirm pointer orientation, and audition in Voltage Modular including patch save/load.

No Designer open/build, host audition, or functional integration is claimed by this intake review.

The separate development pair now implements and verifies steps 1-5. See development/README.md for results and the remaining Designer/host checks. This archived intake description still describes the supplied mockup before DSP integration.
