# sw2 1.0.0

Canonical source release, approved 29 September 2026. Open sw2.vmod in Voltage Module Designer.
The matching Java export and hero image are included alongside it; SHA256.json records their hashes.

sw2 is a mono-first manual rotary selector/distributor. Its five-position shared rotary control
selects OFF, 1, 2, 3, or 4. SOURCE 1–4 select to X; common input Y feeds the matching numbered
destination. The banks share a position but remain electrically independent. Position 0 is OFF and
is the initialized/reset default.

CLK up uses direct relay routing with the module's always-on line-amplifier character. CLK down
adds the sw2-specific 2.2 kHz contact filter and a 96-sample / 2 ms route transition. The two
matching amplifier stages use restrained 2x midpoint conditioning and a gently asymmetric
compression knee at ±3 V: it adds weight without Colorbox-level coloration. The family voice is
related to the faders and sw1 but deliberately more open than both.

Host bypass retains the cached selected route, reads only the needed selected source/Y input, and
bypasses filtering, character processing, control reads, and state updates. Resuming initializes
the audio histories from current signals.

## Validation

From the repository root, run:

    python voltage-modular/laboratory/sw2/tests/verify_character.py

The test compiles both the release Java export and embedded Designer source with Java 17 against
the Voltage SDK. It also verifies pair equivalence, Designer anchors and descriptive control names,
routing/OFF, bank isolation, DC, compression monotonicity, rapid CLK retargeting, exact bypass,
frozen histories, and clean resume. The user confirmed the final Designer build, host behavior, CLK
voice, amplifier character, and 0/OFF initialization.
