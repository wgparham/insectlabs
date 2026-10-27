# InsectLabs switch primitives

SDK-independent state and routing code extracted from Teletape Workshop modules. These blocks are small reusable models for switch utilities; they are not transistor-level hardware simulations.

## Sw3Core

[`Sw3Core.java`](src/com/insectlabs/switches/Sw3Core.java) is the exact SDK-independent dual switch core used by SW3 1.0.0. It includes the 3.0/1.8 V Schmitt gate state, manual/CV edge combination, momentary or latched routing, 10 V gate output with a 5 ms minimum pulse at 48 kHz, dual 2-to-1 and 1-to-2 signal routes, ±10 V reference normalization, exponential 0–200 ms fade, state save/restore, and a bypass/resume helper.

The public `process` methods operate in volts, with the `fadeMs` overload taking 0–200 ms. Each caller owns its state object. The default fade is 0.25 ms. `bypass` routes connected primary inputs only and outputs silence for unpatched primaries; it does not use reference-trim values. A subsequent `process` call re-reads active gates without creating a synthetic edge. The fixed 48 kHz rate is appropriate to this Voltage Modular module and must be revisited before reuse at another processing rate.

The core is useful as a starting point for dual switch utilities. It is not a general N-way multiplexer or a guarantee of click-free switching at the hard-switch setting. The [SW3 release validation](../voltage-modular/Teletape%20Workshop/sw3/versions/1.0.0/tests/validate.py) compiles both module source forms, checks exact primitive parity, and runs the core's routing, control, state, pulse, fade, bypass and resume tests.
