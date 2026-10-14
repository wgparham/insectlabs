# Development setup

## Repository layout

- Local checkout: `C:/Users/wgparham/Dropbox/git/insectlabs`
- Current module collection: [`voltage-modular/Wardenclyffe Station`](../Wardenclyffe%20Station/README.md)
- Independent reusable audio fixtures: [`test-bench`](../../test-bench/README.md)
- Voltage Module Designer SDK: `C:/ProgramData/Voltage/voltage.jar`
- Compile output belongs outside Dropbox/Git, for example `C:/InsectLabs-Build/<module>`.

Each product folder contains its current canonical release and any preserved older versions; versions vary by product. Open the pair named in the collection's `CANONICAL.json` in Voltage Module Designer; its matching `.java` is the exported source. The next collection is [Teletape Workshop](../Teletape%20Workshop/README.md), whose first canonical instrument is RVG and whose remaining module briefs remain in development.

## Toolchain and validation

Use Java 17 bytecode with the installed JDK (`javac --release 17`) and `C:/ProgramData/Voltage/voltage.jar`. Repository checks validate tracked release hashes, project round trips, embedded-source parity, canonical paths and local Markdown links; they do not replace a native Designer build, Voltage host audition or DSP tests.

Keep canonical folders immutable after release. Work in a module-specific development copy, preserve Designer UUIDs and panel state, synchronize both source forms, and review generated lifecycle callbacks. Keep audio processing allocation-free and follow [infrastructure standards](Module-Infrastructure-Standards.md). Use the [release checklist](Module-Release-Checklist.md) before canonization.

Series One is mono-first and predominantly manual. +5 V is the common modulation reference; +10 V remains a module-specific possibility, not a series-wide standard. Add reusable audio fixtures to TestBench using its documented format, provenance, catalog and checksum rules.

The Berna manuals, 1971 Moog catalog, Q125 data and other hardware/software references are maintained in the local Resources folder rather than duplicated in the repository. Collection decisions are summarized in the [roadmap](Collection-Roadmap.md).

TSL Triple Slew Limiter is [canonical v1.0.0](../Teletape%20Workshop/TSL/README.md) as of 2026-10-14. OFB Octave Filter Bank is the user-planned next EMS module; its implementation has not started here.

RVG current canon is [2.0.0](../Teletape%20Workshop/RVG/versions/2.0.0/README.md), approved 2026-10-14; the 1.8.0 archive remains preserved.
