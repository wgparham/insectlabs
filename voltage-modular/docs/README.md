# Voltage Modular documentation

Current documentation review: 6 October 2026. Laboratory has **fifteen canonical modules and one accepted planned role**. Sherlock 1.0.0 is canonical. Balanced Modulator is the remaining planned brief. Colorbox has three canonical modules. Canonical means the repository's authoritative source release; it does not mean store publication or imply testing beyond each release's recorded evidence.

| Document | Purpose |
| --- | --- |
| [Collection roadmap](Collection-Roadmap.md) | Current status, accepted inventory, series directions and design references |
| [Series One utilities](Series-One-Utilities.md) | Completed utility capabilities and remaining roles |
| [Pending instrument briefs](Series-One-Pending-Modules.md) | Completed Burstgen, Selective Service, Following and Sherlock releases, plus one accepted future role |
| [Future module proposals](Future-Module-Proposals.md) | Optional gap-fillers, kept separate from the committed inventory |
| [Development decisions](Development-Decisions.md) | Chat decisions, superseded drafts and current documentation clarifications |
| [Infrastructure standards](Module-Infrastructure-Standards.md) | Naming, bypass, DSP, source pairs and Courtesy behavior |
| [Release checklist](Module-Release-Checklist.md) | Review, validation, user approval, archive and cleanup gates |
| [Development setup](Development-Setup.md) | Local paths, SDK, external compiler output and workflow |
| [Validation tools](../../tools/README.md) | Repeatable checks and their limits |
| [Colorbox user manual](manuals/Colorbox-Collection-User-Manual.md) | Collection overview and shared patching guide for RGB, CMYK and HSB |
| [Laboratory user manual](manuals/Laboratory-Collection-User-Manual.md) | Collection overview and index for fifteen canonical modules |
| [Signal Processor manual](../laboratory/signal-processor/USER-MANUAL.md) | Complete operating guide for Signal Processor 1.0.1 |
| [Fader\|Distr A manual](../laboratory/faderdistr/USER-MANUAL-A.md) | Complete operating guide for Fader\|Distr A 1.0.0 |
| [Fader\|Distr B manual](../laboratory/faderdistr/USER-MANUAL-B.md) | Complete operating guide for Fader\|Distr B 1.0.0 |
| [sw1 manual](../laboratory/sw1/USER-MANUAL.md) | Complete operating guide for sw1 1.0.0 |
| [sw2 manual](../laboratory/sw2/USER-MANUAL.md) | Complete operating guide for sw2 1.0.0 |
| [Generator manual](../laboratory/generator/USER-MANUAL.md) | Complete operating guide for Generator 1.0.2 |
| [Function manual](../laboratory/function/USER-MANUAL.md) | Complete operating guide for Function 1.0.0 |
| [SIN/RND manual](../laboratory/sinrnd/USER-MANUAL.md) | Complete operating guide for SIN/RND 1.1.0 |
| [Deep Tone manual](../laboratory/deeptone/USER-MANUAL.md) | Complete operating guide for Deep Tone 1.0.0 |
| [Selective Service manual](../laboratory/selectiveService/versions/1.0.0/USER-MANUAL.md) | Complete operating guide for Selective Service 1.0.0 |
| [n01 manual](../laboratory/n01/USER-MANUAL.md) | Complete operating guide for n01 1.0.0 |
| [SN-16u approved manual](../laboratory/sn-16u/versions/1.0.0/USER-MANUAL.md) | User-approved operating guide for SN-16u 1.0.0 |
| [RGB user manual](../colorbox/rgb/USER-MANUAL.md) | Complete operating guide for RGB 4.0.3 |
| [CMYK user manual](../colorbox/cmyk/USER-MANUAL.md) | Complete operating guide for CMYK 1.0.3 |
| [HSB user manual](../colorbox/hsb/USER-MANUAL.md) | Complete operating guide for HSB 1.0.2 |


Use [Laboratory CANONICAL.json](../laboratory/CANONICAL.json) and [Colorbox CANONICAL.json](../colorbox/CANONICAL.json) to find current sources. Read archived release notes for exact behavior and evidence. New work belongs in development; do not modify an archive to correct current documentation. Third-party reference manuals and SDK binaries stay in external Resources. Collection overview manuals are maintained in docs/manuals; detailed module manuals live with their module documentation, except the approved SN-16u manual, which remains inside its versioned release. [TestBench](../../test-bench/README.md) is its own general audio project.
