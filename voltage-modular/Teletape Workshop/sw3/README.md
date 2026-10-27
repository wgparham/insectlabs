# SW3 Voltage Controlled Switch

SW3 is a two-path utility: X selects one of two inputs for a common output, while Y routes one input to one of two outputs. It is canonized at **1.0.0**.

- [User manual](versions/1.0.0/USER-MANUAL.md)
- [Developer notes](DEVELOPER-NOTES.md)
- [Release review and validation](versions/1.0.0/REVIEW.md)
- [Canonical source pair and artwork](versions/1.0.0/)

The versioned archive is the source of truth. Its validation harness compiles the exported Java and embedded Designer source against the installed Voltage SDK, checks the project round-trip and hashes, and exercises the switching core.
