# Function 1.0.0 release review

## Release record

- Module: AC/1D SIN-SQR Function Generator (`function`)
- Version: 1.0.0
- Approved: 2026-09-30
- Candidate Notes: `v1.0.0rc`; released Notes: `v1.0.0`
- Canonical path: `voltage-modular/Wardenclyffe Station/ac-1d/versions/1.0.0/`

## Completed review

- User locked the tone and overall sound at v0.1. The final tuning revision changes display and dial scaling only: it reports nominal output frequency after multiplication and expands X1K/X10K across the full dial. X10 remains calibrated to C4.
- All 33 Designer controls have lowerCamelCase internal/variable names and human-readable display names. UUIDs, panel layout, control ranges/defaults, artwork, class/package identity and Oscillators category were preserved.
- The Java export and embedded Designer source match; the project binary round-trips; release hashes match.
- Both source forms compile with the Voltage SDK using Java 17 target and warnings as errors.
- Corrected the final cleanup-only destruction ordering so the GUI timer stops before exactly one base destruction call. No oscillator, tuning, panel or audio callback arithmetic changed.
- Callback tests cover C4 initialization, frequency endpoints and full-dial monotonicity, typed-Hz round trips, all amplitude/range/width boundaries, output independence, power fade/stop, hard source bypass, frozen state and clean resume.
- The user confirmed the exact final candidate builds, loads, saves/reloads, runs and sounds correct.

## Deliberate design choices

- Independent sine and square boards share the frequency target but retain distinct phase and slow drift.
- Native-rate processing is retained to preserve the approved voice and CPU profile. The square uses low-cost edge correction. The deliberate sine harmonics and output saturation can alias near the 23.76 kHz upper limit; this is documented character, not an alias-free claim.
- Host bypass is silent and freezes source state. The physical power switch has an 8 ms fade and stops the boards after fade-out.
