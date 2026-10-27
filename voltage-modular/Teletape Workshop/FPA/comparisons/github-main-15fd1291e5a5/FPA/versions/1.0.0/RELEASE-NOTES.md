# Release notes — FPA 1.0.0

First canonical release of the four-channel FPA preamp/overdrive module. The four independent voicings are inspired by the Boss CE-1, Roland RE-201/RE-301, Boss BD-2 and Boss SD-1, with per-channel gain, tone, polarity and voicing controls, individual outputs and a summed MIX output.

During final inspection, the DSP tone clamp was corrected to match the existing −1..+1 panel range. C, Re and D now use 0 as the neutral tone center. O maps the same signed sweep over its existing 1.5–8 kHz low-pass range. Designer display names for the voicing and channel labels were made descriptive, and a Tone label typo was corrected; their visible layout is unchanged. No other DSP transfer, gain, bypass, routing, or control behavior was changed in this release.

The user confirmed that the candidate pair loaded, compiled and ran in Designer before the signed-tone correction, then explicitly authorized canonization if this scoped change passed the code tests. The exact corrected pair passes strict SDK compilation, binary project/source checks and the headless DSP tests listed in `REVIEW.md`. A separate native Designer audition of the corrected tone response is not recorded.
