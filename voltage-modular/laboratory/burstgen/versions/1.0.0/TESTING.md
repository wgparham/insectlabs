# Burstgen 1.0.0 validation

The user built and auditioned the final module in Voltage Modular and confirmed expected switching, Courtesy timing, and audio behavior before canonization. The final naming, tooltip, and archive pass preserves DSP behavior.

## Checks recorded for the approved DSP

- Java 17 SDK compilation against `C:/ProgramData/Voltage/voltage.jar` succeeded for the candidate source and its Designer-embedded source.
- A 242,545-assertion DSP harness covered gate/count transitions, reset and pause/resume, ratios, one-shot/loop behavior, and boundary conditions.
- External-clock stress at 2.5 kHz and ×7 measured approximately 17.5 kHz ticks. Internal HI at ×7 reached 21 kHz. Near this limit, audio gates can be only a few samples long.
- Courtesy-edge checks verified that C remains at the unmodified internal rate and R follows the documented selected internal timing; external counting remains independent.
- Direct host bypass, adaptive fade cutoff at 1 kHz, and exact source-pair synchronization were reviewed.

The final cleaned source pair also compiles against the installed SDK with Java 17 target. The JDK 27 compiler's `-Xlint:all` mode fails while emitting the large nested DSP class, including on the previously tested candidate; ordinary SDK compilation and individual lint categories succeed. This compiler diagnostic is not treated as a successful all-lint check.

The final approved Designer build and listening test remain the evidence for host behavior. The repository integrity audit verifies source/archive consistency, manifests, canonical paths and local documentation links; it does not simulate the Voltage Modular host.
