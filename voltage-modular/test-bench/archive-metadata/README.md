# InsectLabs Audio Test Bench — v1.0

53 listening and measurement files for the InsectLabs Voltage Modular collections. All delivery files are **48 kHz, 24-bit PCM WAV**, with mono sources prioritized and explicit stereo routing fixtures. Typical lengths are 4–24 seconds. No Voltage Modular patch or special player is required.

## Start here

1. **Basic gain and polarity:** `01-calibration/sine_00440Hz_minus12dBFS.wav` and `sine_01000Hz_minus12dBFS.wav`.
2. **Signal Processor listening:** `07-recordings/guitar_recording_mono.wav` or `piano_recording_mono.wav`. Start at unity gain, zero offset, and compare input/output levels. The piano's source does not identify whether the instrument is acoustic or digital.
3. **Independent stages:** `07-recordings/recorded_guitar_left_piano_right.wav`. Split the player’s left and right outputs into the two mono stages. The two performances are independent, not a tempo-matched duet.
4. **Fader|Distr:** compare `04-stereo/dual_mono_correlated.wav` with `independent_pink_noise.wav`. Identical sources and independent sources behave differently when summed: equal-power crossfading is not constant-amplitude crossfading of identical signals.
5. **Musical audition:** `05-musical/stereo_full_music_mix.wav`, an original synthesized instrumental mix. All folder 05 performances are synthesized, including files whose short filenames omit “synthetic.”
6. **Real ambience:** `07-recordings/rain_recording_stereo.wav` and `birds2_recording_stereo.wav`. Folder 06 contains separately labeled procedural outdoor-style textures.

`all-files.m3u8` is a relative-path playlist. `CATALOG.md` describes every file; `catalog.csv` and `manifest.json` provide measurements, loop behavior and checksums. `ATTRIBUTION.md` contains source credits and redistribution terms.

## Contents

| Folder | Files | Contents |
|---|---:|---|
| 01-calibration | 18 | Sines at 20, 50, 60, 100, 220, 415, 432, 440, 442, 1000, 3000, 8000 and 12000 Hz; silence; beating and IMD pairs; multitone; 20 Hz–18 kHz sweep |
| 02-dynamics | 5 | Level staircase, amplitude modulation, gated bursts, impulses, sine plus DC |
| 03-noise | 7 | White/pink/brown/blue noise, modulated pink noise, random envelope noise, randomly frequency-modulated sine |
| 04-stereo | 7 | Left/right identification, correlated dual mono, opposite polarity, alternating channels, independent noise |
| 05-musical | 6 | Original synthesized guitar-like and piano-like performances, split-channel pairs, duo and full instrumental mix |
| 06-ambience | 2 | Synthesized garden and stream textures |
| 07-recordings | 8 | Sourced guitar and piano, birds and rain, mono instrument versions and split-channel pairs |

## Playback and level conventions

Use unity playback gain and disable automatic normalization, fades and time stretching when measuring. Match the host to 48 kHz for the baseline test; other host rates introduce an additional playback-resampling variable. Stereo files test routing or two independent stages; they do not imply that the Series One processors are stereo internally.

The calibration sines have -12 dBFS sample peaks (approximately -15.01 dBFS RMS). Most generated test signals use -12 dBFS peaks; instrument and ambience sources peak at -9 dBFS, and synthesized mixes at -6 dBFS. Consult the manifest for exact per-channel values. Noise colors are peak matched, **not RMS or loudness matched**. This leaves headroom for gain tests but does not guarantee that every processor setting will avoid clipping.

WAV amplitude is a fraction of full scale, not an absolute voltage. Verify the player's voltage scaling with a meter before using these as CV references. If its mapping is ±5 V at full scale, a -12 dBFS sine is approximately ±1.256 V; this is conditional on that mapping. The DC fixture contains +0.1 full scale plus a 100 Hz sine of 0.15 full-scale amplitude. Some playback paths remove DC; use native CV sources for authoritative offset/VCA control tests.

The single-sample impulses and gated bursts deliberately contain sharp transitions. Continuous high-frequency tones are measurement fixtures; start playback at a comfortable monitor level.

## Loops and recording provenance

The analytic tones, FFT noise and procedural sources use periodic constructions. A nonzero difference between the last and first sample is normal for a sampled periodic waveform; it is not by itself a loop error. Sweeps, staircases and imported recording excerpts have intentional restarts, documented individually. Recording excerpts have 50 ms endpoint fades and are **not beat-synchronous or seamless musical loops**.

The real-source files were obtained from a pinned public IBM sample mirror with links back to their Freesound authors. Guitar is a recorded jazz performance; its original processing chain is unspecified. Piano is a sourced performance with unspecified instrument technology. No new reverb, distortion or other effects were added. Birds originated as MP3. The mirror supplies 16-bit WAVs; delivering derived files at 24 bits does not recover lost source precision. These recordings are listening/routing material, not distortion or high-frequency calibration standards.

## Validation and archiving

The delivered set passes 255 automated checks: WAV format and duration, checksums, peak measurements, sample clipping, calibrated sine values/frequencies, exact stereo cancellation, channel isolation, channel swaps, and faded recording endpoints. See `QA.json`. Subjective listening and playback inside Voltage Modular remain to be done.

Generated audio in folders 01–06 is dedicated under CC0 1.0. Recordings retain their individual CC0 or CC BY 4.0 terms. **Keep ATTRIBUTION.md with redistributed copies**, particularly piano/rain files and piano-containing channel pairs. The collection is not covered by one blanket CC0 license.

The repository contains generation/import/verification tools and a small archival manifest. WAVs, original source downloads and the review ZIP remain in `Resources/TestBench` for now. After audition and annotation, a versioned GitHub Release asset is a practical home for the audio ZIP; keep scripts, catalogs, attribution, checksums and test notes in Git. Nothing has been published by this task.
