# sw1 1.0.0

Canonical source release, approved 2026-09-29. Open `sw1.vmod` in Voltage Module Designer.
The matching Java export and hero image are included alongside it; `SHA256.json` records their
hashes.

`sw1` is a mono-first manual 2x2 relay router. In normal routing, I1 goes to O1 and I2 goes to
O2. The alternate relay position swaps the paths. With one connected input it behaves as a
one-to-two route selector; without inputs it provides complementary +5 V manual toggle/gate
outputs. Patch topology determines the useful role without adding CV control.

The T/G control selects toggle or momentary gate behavior. The large illuminated button operates
the relay. With CLK up, routing is direct. CLK down enables a 1.8 kHz contact filter and a 2 ms
relay-settling transition, giving substantial click reduction with a restrained vintage darkening.

Host bypass follows the collection convention: I1 passes directly to O1 and I2 directly to O2.
The user confirmed the Designer build, routing behavior, flip-flop/toggle-gate functions, swap,
and final CLK voicing.

## Validation

From the repository root:

```powershell
javac --release 17 -Xlint:all -Werror -cp C:/ProgramData/Voltage/voltage.jar -d voltage-modular/Wardenclyffe Station/build/sw1-sdk voltage-modular/Wardenclyffe Station/sw1/versions/1.0.0/sw1.java
```

The source compiles cleanly with Java 17 against the Voltage Modular SDK. The `.vmod` embedded
source is synchronized with its Java export and the supplied checksum manifest.
