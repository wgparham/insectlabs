# InsectLabs Audio TestBench

The Audio TestBench is a standalone InsectLabs project for module development, audio/musical
experimentation, and repeatable listening or measurement. The full TestBench collection is versioned
here. The current v1.9.0 collection contains 66 WAV files at 48 kHz/24-bit, source recordings with
license evidence, and a measured catalog, attribution, manifest, and verification results.

- `v1` contains the ready-to-patch collection and its listening guide.
- `sources/` preserves source recordings and license evidence used for the recorded fixtures, grouped by source collection.
- `InsectLabs-TestBench-v1.zip` remains the portable v1.1 snapshot (54 WAVs); its SHA-256 digest is in the adjacent `.sha256` file. The current v1.8 WAVs and metadata are checked in individually. Rebuilding the 241 MB archive requires Git LFS tooling, which was unavailable during this release.
- `archive-metadata` preserves the original delivery metadata.

Third-party recording licenses are separate from this repository's code license. Keep the credits with the WAV distribution. Do not apply the generated-audio CC0 dedication to the sourced recordings. The original user-provided composition source is archived separately; this repository contains only its approved delivery mix and provenance.

## Verify the delivered collection

Run `python test-bench/tools/verify.py test-bench/v1` from the repository root with NumPy installed. This checks the delivered WAV files and updates their QA report. Large assets use Git LFS; run `git lfs pull` after cloning if they are still pointer files.

## Rebuild in a separate destination

Requires Python 3 and NumPy. The commands below produce the generated and third-party subset in disposable local storage. They do not reproduce the complete delivery: the original composition source is archived separately. To rebuild that fixture, use `import_user_composition.py --source <original-stereo-16-bit-WAV> --output <build-folder>` after the recording import. Keep the delivered collection intact while rebuilding.

```powershell
python test-bench/tools/generate.py --output C:/InsectLabs-Build/test-bench
python test-bench/tools/download_sources.py --output test-bench/sources/ibm-freesound
python test-bench/tools/import_recordings.py --output C:/InsectLabs-Build/test-bench --sources test-bench/sources/ibm-freesound
python test-bench/tools/verify.py C:/InsectLabs-Build/test-bench
```

Generation uses seed 19710510. The manifest records NumPy's version; floating-point or FFT implementation changes across environments may alter the least significant bits. SHA-256 hashes identify this particular delivered build.

The user has tested and approved the original 54-file collection in Voltage Modular. Version 1.2 adds four numerically verified SN-16u captures; native host playback of those four fixtures remains unverified. Add useful new test fixtures as modules develop, updating the catalog, credits, manifest, and verification results together.

## Guitar riff loop added in 1.8.0

`v1/07-recordings/slide_the_e_sandman_aceinet_mono.wav` is a 29.5-second sourced guitar phrase for testing distortion pedals, overdrives, fuzz, preamps, gates and compression. The original recording is dual mono, delivered as mono at 48 kHz/24-bit with a 10 ms circular wrap crossfade and -12 dBFS peak for pedal headroom. The complete phrase is kept at its recorded timing; the loop is not tempo-locked. The source WAV and license record are preserved under `sources/guitar-loops/`.

`v1/07-recordings/slip_the_e_to_g_aceinet_mono.wav` is a 24.262-second loop cut supplied from aceinet's Freesound recording. Its source page lists CC0 1.0. The supplied file is dual mono at 48 kHz/16-bit; it is delivered as mono 48 kHz/24-bit with a 10 ms circular wrap crossfade and -12 dBFS peak. It is not tempo-locked. The supplied loop cut is archived unchanged under `sources/guitar-loops/`; the Freesound listing describes a longer 44-second original.

## Clean metal guitar input loop added in 1.7.0

`v1/05-musical/synthetic_clean_death_metal_guitar_drop_d_180bpm.wav` is an original, mono, clean direct-style guitar model for distortion pedals, overdrives, fuzz, gates, preamps and compression. It is a deterministic Karplus-Strong plucked-string performance in Drop D, 180 BPM, eight bars of 4/4, with palm-muted sixteenth-note chugs and power-chord accents. It contains no amplifier model or effects, peaks at -12 dBFS for input headroom, and loops as an exact musical cycle with note tails wrapped at the boundary. This is synthesized audio, not a recording of a guitarist. It is dedicated under CC0 1.0; generator, exact loop duration, measured levels and checksum are recorded in the manifest.

## sn-16u captures added in 1.2.0

Two complete 42-second sine sweeps and 11-second pink/blue noise fixtures were captured from the v0.1.2rc callbacks; the manifest retains the exact source hash and capture levels. The original raw captures were deliberately discarded after verified conversion, so this repository preserves the finished WAVs and provenance rather than a regeneration claim. `import_sn16u_captures.py` can convert an available capture directory; pass its matching source export and version explicitly. The v1.0.0 release is the current module pair, but it is not the source revision recorded for these earlier fixtures.

## Selectivity probe added in 1.3.0

`v1/01-calibration/selectivity_1k_neighbours_8s.wav` is a periodic mono fixture with equal-amplitude
990, 999, 1000, 1001 and 1010 Hz components and a −12 dBFS composite peak. It adds closely spaced
frequency-selection and notch checks that the wider-spaced multitone does not cover. Loop it to
allow very narrow filters to settle. It is original analytic audio, not a capture of Selective Service.
Numerical spectrum/format checks pass; user playback is pending.

Rebuild or add it to an existing collection with
`python test-bench/tools/add_selectivity_fixture.py --output <collection-folder>`.
The portable ZIP remains the older v1.1 snapshot; individually versioned WAVs and metadata are current.

## Model 62 head-spacing impulses added in 1.4.0

`v1/02-dynamics/model62_head_spacing_impulses_150Hz_minus18dBFS.wav` is an analytic stereo one-shot fixture with three 150 Hz Hann-windowed pulse pairs. The left-to-right delays (54, 108 and 13.5 ms) model the 33 mm head gap at 1×, 1/2× and 4× transport speeds. The signal is fixed at −18 dBFS peak per channel. It is useful for checking short echo timing, stereo offset, transient response and speed relationships. Rebuild it with `python test-bench/tools/add_model62_head_impulses.py --output test-bench/v1`.

## Drum recordings added in 1.5.0

Two CC0 sourced drum loops have been added under `v1/07-recordings`: Hip Hop Drum Loop 17 by LoopDilla and Death Metal Drums by erich94. Both are stereo 48 kHz/24-bit PCM WAV with a linked -9 dBFS peak and 3 ms endpoint fades. Original source files, earlier HQ previews and source-page snapshots are retained in `sources/drum-loops`; conversion does not restore source precision or MP3 losses. Source loops are retained in full, but faded restarts are not a guarantee of a seamless musical boundary. See the catalog and attribution for source links and measurements.

Rebuild with `python test-bench/tools/add_drum_recordings.py --output test-bench/v1 --sources test-bench/sources/drum-loops --ffmpeg <ffmpeg.exe>` (NumPy and FFmpeg required). The portable ZIP remains the older v1.1 snapshot.

## Metal loop edit (1.5.1)

The extreme-metal WAV is now an exact 6.4-second musical cycle, taken from 3.198833–9.598833 seconds of the decoded source. Its repeated pattern supports eight 4/4 bars at 300 BPM, or four at 150 BPM half-time. A 10 ms wrap-tail blend preserves the duration and cymbal sustain across the join; no endpoint fade or silence is inserted. The original source recording is preserved. This replaces the earlier full-length metal delivery, which included a long decay tail.

## CED chorus/ensemble demo (1.6.0)

`v1/08-module-demos/CED_dimension_ce1_chorus_demo.wav` is an original 44-second stereo PCM render from CED's headless `ChorusCoreTest`. Eleven four-second segments demonstrate dry routing, each of four Dimension-style delay programs, all three program-combination laws, a four-program sum, and CE-style chorus/vibrato. Its section changes are intentional, so it is a listening reference rather than a seamless loop or a native Voltage Modular recording. The render is dedicated under CC0 1.0 and registered in the catalog/manifest with its checksum and measured levels. Automated WAV and checksum checks pass; user/native-host playback is pending.
