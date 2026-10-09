# InsectLabs Audio Test Bench — v1.4

60 listening and measurement files for the InsectLabs Voltage Modular collections. All delivery files are **48 kHz, 24-bit PCM WAV**, with mono sources prioritized and explicit stereo routing fixtures. Typical lengths are 4–24 seconds, plus two 42-second sn-16u sweeps, with one complete original experimental-music mix for extended audition. No Voltage Modular patch or special player is required.

## Start here

1. **Basic gain and polarity:** `01-calibration/sine_00440Hz_minus12dBFS.wav` and `sine_01000Hz_minus12dBFS.wav`.
2. **Signal Processor listening:** `07-recordings/guitar_recording_mono.wav` or `piano_recording_mono.wav`. Start at unity gain, zero offset, and compare input/output levels. The piano's source does not identify whether the instrument is acoustic or digital.
3. **Independent stages:** `07-recordings/recorded_guitar_left_piano_right.wav`. Split the player’s left and right outputs into the two mono stages. The two performances are independent, not a tempo-matched duet.
4. **Pan/Fade:** compare `04-stereo/dual_mono_correlated.wav` with `independent_pink_noise.wav`. Identical sources and independent sources behave differently when summed: equal-power crossfading is not constant-amplitude crossfading of identical signals.
5. **Musical audition:** `05-musical/stereo_full_music_mix.wav`, an original synthesized instrumental mix; or `after_drinking_at_emalines_original_mix.wav`, a complete user-owned experimental-music mix for extended listening. The other folder 05 performances are synthesized, including files whose short filenames omit “synthetic.”
6. **Real ambience:** `07-recordings/rain_recording_stereo.wav` and `birds2_recording_stereo.wav`. Folder 06 contains separately labeled procedural outdoor-style textures.

`all-files.m3u8` is a relative-path playlist. `CATALOG.md` describes every file; `catalog.csv` and `manifest.json` provide measurements, loop behavior and checksums. `ATTRIBUTION.md` contains source credits and redistribution terms.

## Contents

| Folder | Files | Contents |
|---|---:|---|
| 01-calibration | 21 | Sines at 20, 50, 60, 100, 220, 415, 432, 440, 442, 1000, 3000, 8000 and 12000 Hz; silence; beating and IMD pairs; multitone; 20 Hz–18 kHz sweep; sn-16u linear/exponential 0.01 Hz–23.76 kHz sweeps; close-spaced 1 kHz selectivity probe |
| 02-dynamics | 5 | Level staircase, amplitude modulation, gated bursts, impulses, sine plus DC |
| 03-noise | 9 | White/pink/brown/blue noise, modulated pink noise, random envelope noise, randomly frequency-modulated sine; sn-16u pink/blue captures |
| 04-stereo | 7 | Left/right identification, correlated dual mono, opposite polarity, alternating channels, independent noise |
| 05-musical | 7 | Original synthesized guitar-like and piano-like performances, split-channel pairs, duo, full instrumental mix and complete user-owned experimental-music mix |
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

The delivered set passes automated checks (the current count is recorded in `QA.json`): WAV format and duration, checksums, peak measurements, sample clipping, calibrated sine values/frequencies, exact stereo cancellation, channel isolation, channel swaps, and faded recording endpoints. See `QA.json`. The original 54-file set was user-approved in Voltage Modular. The four new sn-16u captures await user playback; Model 62 fixture playback is also pending.

Generated audio in folders 01–06 is dedicated under CC0 1.0. Recordings retain their individual CC0 or CC BY 4.0 terms. **Keep ATTRIBUTION.md with redistributed copies**, particularly piano/rain files and piano-containing channel pairs. The collection is not covered by one blanket CC0 license.

The repository contains the delivery WAVs, portable archive, source downloads where redistribution
permits, and generation/import/verification tools. The TestBench is its own top-level InsectLabs
project and is versioned on GitHub. Keep scripts, catalogs, attribution, checksums, and test notes
with each delivery. The original user-provided composition source is archived separately; only the
approved converted delivery mix and provenance belong here.

## sn-16u capture levels

The four sn-16u v0.1.2rc fixtures preserve relative module voltage levels using 0.1 full scale per volt. The sweeps have nominal -6.0206 dBFS peaks and retain their original 5 ms fades; the noise excerpts discard one settling second and add 50 ms endpoint fades. Neither noise excerpt is peak/loudness normalized. Pink and blue share the same random sequence. These are DSP callback captures, not native host recordings, and they are not seamless loops. The player's voltage scaling still determines their actual playback voltage.

## Narrow-band selectivity probe (1.3.0)

`01-calibration/selectivity_1k_neighbours_8s.wav` combines equal-amplitude tones at 990, 999,
1000, 1001 and 1010 Hz. The **combined** signal peaks at −12 dBFS; each individual tone is lower
(see the manifest). It is an exact repeated one-second period in an eight-second mono file,
with no fades or dither. Loop continuously for narrow/high-order filters to settle.

At a 1 kHz center and 2 Hz bandwidth, the center tone tests unity bandpass and the notch null,
the ±1 Hz tones test the transition edges, and the ±10 Hz tones test rejection/preservation.
Use sufficient FFT resolution to separate 1 Hz spacing. Finite filter slopes overlap at the
edges. This analytic fixture is numerically verified; native host playback is pending.

## Model 62 head-spacing impulses (1.4.0)

`02-dynamics/model62_head_spacing_impulses_150Hz_minus18dBFS.wav` has three stereo pulse pairs;
the left-channel pulse leads the right by 54, 108 and 13.5 ms, corresponding to a 33 mm head gap at
1×, 1/2× and 4× speed. Each 150 Hz Hann-windowed pulse is 10 ms long and peaks at −18 dBFS.
It is a one-shot, not a seamless loop. Use it to check short echo/head timing, transient response,
stereo offset and transport-speed relationships. Numerical format, level and pulse placement checks
pass; native host playback is pending. Rebuild with `python test-bench/tools/add_model62_head_impulses.py --output test-bench/v1`.
