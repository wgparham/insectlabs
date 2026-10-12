# InsectLabs Audio TestBench

The Audio TestBench is a standalone InsectLabs project for module development, audio/musical
experimentation, and repeatable listening or measurement. The full TestBench collection is versioned
here. The current v1.5.1 collection contains 62 WAV files at 48 kHz/24-bit, source recordings with
license evidence, and a measured catalog, attribution, manifest, and verification results.

- `v1` contains the ready-to-patch collection and its listening guide.
- `sources/ibm-freesound` preserves the source recordings and license evidence used for the recorded fixtures.
- `InsectLabs-TestBench-v1.zip` remains the portable v1.1 snapshot (54 WAVs); its SHA-256 digest is in the adjacent `.sha256` file. The current v1.5 WAVs and metadata are checked in individually. Rebuilding the 241 MB archive requires Git LFS tooling, which was unavailable during this release.
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
