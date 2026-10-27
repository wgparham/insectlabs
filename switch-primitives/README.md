# InsectLabs switch primitives

SDK-independent state and routing code extracted from Teletape Workshop modules. These blocks are small reusable models for switch utilities; they are not transistor-level hardware simulations.

## Sw3Core

[`Sw3Core.java`](src/com/insectlabs/switches/Sw3Core.java) is the exact SDK-independent dual switch core used by SW3 1.0.0. It includes the 3.0/1.8 V Schmitt gate state, manual/CV edge combination, momentary or latched routing, 10 V gate output with a 5 ms minimum pulse at 48 kHz, dual 2-to-1 and 1-to-2 signal routes, ±10 V reference normalization, exponential 0–200 ms fade, state save/restore, and a bypass/resume helper.

The public `process` methods operate in volts, with the `fadeMs` overload taking 0–200 ms. Each caller owns its state object. The default fade is 0.25 ms. `bypass` routes connected primary inputs only and outputs silence for unpatched primaries; it does not use reference-trim values. A subsequent `process` call re-reads active gates without creating a synthetic edge. The fixed 48 kHz rate is appropriate to this Voltage Modular module and must be revisited before reuse at another processing rate.

The core is useful as a starting point for dual switch utilities. It is not a general N-way multiplexer or a guarantee of click-free switching at the hard-switch setting. The [SW3 release validation](../voltage-modular/Teletape%20Workshop/sw3/versions/1.0.0/tests/validate.py) compiles both module source forms, checks exact primitive parity, and runs the core's routing, control, state, pulse, fade, bypass and resume tests.


## SequentialSwitchCore

[SequentialSwitchCore.java](src/com/insectlabs/switches/SequentialSwitchCore.java) is the exact normalized core harvested from swCXI 1.0.0. It provides ten-stage signal selection/distribution, cyclic 5 V address CV (including negative wrap), knob-selected range, rising-edge sequencing, clock-sampled CV, reset override, 48-sample mux crossfades and exclusive row fades. Both common routes may run together; with both common jacks unplugged only the selected paired row passes through. The row fade-down and fade-up share the handoff sample, so the transition takes 47 samples without a fully silent handoff frame.

The processing rate is 48 kHz and all signal/CV arguments are volts. Use `update()` before asking `needsInput()` which entries to read, then call `mix()`. `process()` combines these calls for an already available input array. Write every resulting output on every host sample; a held output value is not permission to skip cable writes. `bypass()` produces direct selected routes, freezes control/fade histories and marks the next active update for re-priming. It accepts no control CV inputs. Each caller owns a core instance; audio callbacks allocate nothing. Selection state is a one-byte zero-based index.

The [swCXI release validation](../voltage-modular/Teletape%20Workshop/sw111/versions/1.0.0/tests/validate.py) compiles both module source forms, checks exact primitive parity, tests core invariants and exercises the actual module callbacks through constructor-free SDK I/O substitutes. The primitive models signal routing and intentional short fades; it is not a transistor-level hardware model.
