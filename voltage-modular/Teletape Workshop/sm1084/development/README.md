# SM1084 development snapshot

Uncanonized panel draft for a proposed `sm1084 - studio mixer` Voltage Modular module. The Java source compiles against the installed Voltage SDK with Java 17 strict lint flags, and its normalized source matches the Java embedded in the VMOD. The current Java contains generated control/panel setup only: there is no audio-processing implementation, and the module has not had functional DSP testing.

The supplied `.bak` files are retained in this snapshot as historical material. `sm1084.java.bak` is byte-identical to the current Java export; `sm1084.vmod.bak` differs from the current VMOD.

## Included references

The `delay reference/` folder is retained as supplied. It contains BOSS DM-1/DM-2 service notes and images, plus Roland RE-101/201 and RE-301 service manuals. The two files `BOSS DM-2.jpg` and `BOSS Dm2.jpg` have identical SHA-256 hashes; both names are retained in this snapshot.

## Integrity

[`SHA256.json`](SHA256.json) records hashes for the source pair, panel image, supplied reference files, and retained backup files. It excludes itself.
