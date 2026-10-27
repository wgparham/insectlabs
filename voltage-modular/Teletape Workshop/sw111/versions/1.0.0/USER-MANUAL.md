# swCXI Sequential Switch 1.0.0

swCXI combines a ten-input selector and a ten-output distributor. Both follow the same selected stage, shown by its green lamp. It carries audio, CV and gates at unity gain once a transition finishes.

## Routing

- Each left Stage Input can reach the bottom Common Output when selected.
- The top Common Input reaches the selected right Stage Output.
- Both common routes can operate at once.
- When both common jacks are unplugged, the selected left input passes to its matching right output. Other rows stay silent.
- Patching Common Output without Common Input silences the row outputs. Unpatched signal inputs supply 0 V.

## SEL, CV and clock

SEL has ten discrete positions, increasing clockwise from 1 to 10, and defaults to 1.

| Connections | Operation |
| --- | --- |
| Select CV and Clock both unplugged | SEL chooses the active stage directly. |
| Select CV only | SEL sets the number of available stages. CV selects among them immediately. |
| Clock only | SEL sets sequence length. Each rising edge advances one stage and wraps after that length. |
| Select CV and Clock | SEL sets the available stage count. Each rising clock edge samples Select CV and chooses its stage. A steady CV selects the same stage on repeated clocks. |

The CV cycle repeats every 5 V in both directions. With ten stages, 0 to less than 0.5 V selects stage 1, 0.5 to less than 1 V selects stage 2, and 4.5 to less than 5 V selects stage 10. Exactly 5 V wraps to stage 1. A small negative voltage wraps to the last available stage. With fewer stages, the whole 5 V cycle is divided evenly across the available range.

Clock triggers at 3 V and rearms below 1.8 V. Negative pulses do not advance it. A high clock already present at startup or when processing resumes is primed without generating a new step. Reducing SEL below the current stage moves to the highest remaining stage immediately, including in clocked CV mode.

## Reset and saved state

RST triggers at 3 V, rearms below 1.8 V and takes priority over clock and selection. It holds stage 1 while high. After release, stage 1 remains until the next clock edge, a new manual knob position, or a new unclocked CV address. Clock-only operation resumes by advancing to stage 2, subject to the active stage count.

The current stage is stored with presets and variations. Knob values and cable connections use Voltage Modular's native storage. Fade progress and trigger histories are re-primed on restoration.

## Switching character and host bypass

Common Output crossfades between input routes over 48 samples (1 ms). The row outputs fade the previous route down before bringing the next route up, so two row outputs are never active together. Their handoff takes 47 samples (about 0.98 ms). The lamp indicates the requested stage immediately. These short fades also soften fast CV and gate edges.

Host bypass freezes the selected stage and passes its signal routes directly, without fades or control/CV reads. Inactive row outputs remain zero. Returning to active processing adopts current controls, primes trigger levels and discards the interrupted fade so that an old clock edge or ramp is not replayed.
