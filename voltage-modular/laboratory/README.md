# Laboratory collection

Series One: mono-first, predominantly manual instruments inspired by early laboratory equipment.

| Module | Status | Files |
| --- | --- | --- |
| Signal Processor | Canonical 1.0.1 | [Release](signal-processor/versions/1.0.1/README.md) |
| Fader&#124;Distr A | Canonical 1.0.0, linear law | [Release](faderdistr/versions/1.0.0/README.md) |
| Fader&#124;Distr B | Canonical 1.0.0, equal-power law | [Release](faderdistr/versions/1.0.0/README.md) |
| sw1 | Canonical 1.0.0, manual relay router | [Release](sw1/versions/1.0.0/README.md) |
| sw2 | Canonical 1.0.0, manual rotary selector/distributor | [Release](sw2/versions/1.0.0/README.md) |
| Generator | Canonical 1.0.2, sine/variable-triangle source with FM and 1 kHz reference | [Release](generator/versions/1.0.2/README.md) |
| Function | Canonical 1.0.0, independent sine/square laboratory source | [Release](function/versions/1.0.0/README.md) |
| r195L: SIN/RND generator | Canonical 1.1.0, sine/noise source and filtered input path | [Release](sinrnd/versions/1.1.0/README.md) |
| Deep Tone Generator | Canonical 1.0.0, sine/beat source, deep AM and continuous Courtesy | [Release](deeptone/versions/1.0.0/README.md) |
| n01 Noise Source | Canonical 1.0.0, dark noise and random-voltage source | [Release](n01/versions/1.0.0/README.md) |
| SN-16u | Canonical 1.0.0, universal test bench | [Release](sn-16u/versions/1.0.0/README.md) |

[CANONICAL.json](CANONICAL.json) identifies releases without duplicating source pairs.

Next module: [Tone Burst Generator](../docs/Series-One-Pending-Modules.md#tone-burst-generator). SN-16u is now canonical; the accepted future inventory contains five retained roles.

- [Release notes](CHANGELOG.md)
- [Collection roadmap](../docs/Collection-Roadmap.md)
- [Utility specification](../docs/Series-One-Utilities.md)
- [Infrastructure standards](../docs/Module-Infrastructure-Standards.md)
- [Development setup](../docs/Development-Setup.md)
- [TestBench audio collection](../../test-bench/README.md)

Follow [Colorbox conventions](../colorbox/STANDARDS.md). The modulation reference remains +5 V;
+10 V is deferred for input-specific consideration. Manuals and SDK binaries remain in the external workspace.

Release preparation follows the [module release checklist](../docs/Module-Release-Checklist.md).
The current inventory is eleven canonical modules and five accepted planned roles. See the [documentation index](../docs/README.md), [pending briefs](../docs/Series-One-Pending-Modules.md) and [optional gap proposals](../docs/Future-Module-Proposals.md). Each release retains its own approval and validation evidence.
