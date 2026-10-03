# InsectLabs Audio TestBench

The Audio TestBench is a standalone InsectLabs project for module development, audio/musical
experimentation, and repeatable listening or measurement. The full TestBench collection is versioned
here. The current v1.2 collection contains 58 WAV files at 48 kHz/24-bit, source recordings with
license evidence, and a measured catalog, attribution, manifest, and verification results.

- `v1` contains the ready-to-patch collection and its listening guide.
- `sources/ibm-freesound` preserves the source recordings and license evidence used for the recorded fixtures.
- `InsectLabs-TestBench-v1.zip` remains the portable v1.1 snapshot (54 WAVs); its SHA-256 digest is in the adjacent `.sha256` file. The current v1.2 WAVs and metadata are checked in individually. Rebuilding the 241 MB archive requires Git LFS tooling, which was unavailable during this release.
- `archive-metadata` preserves the original delivery metadata.

Third-party recording licenses are separate from this repository's code license. Keep the credits with the WAV distribution. Do not apply the generated-audio CC0 dedication to the sourced recordings. The original user-provided composition source is archived separately; this repository contains only its approved delivery mix and provenance.

## Verify the delivered collection

Run `python test-bench/tools/verify.py test-bench/v1` from the repository root with NumPy installed. This checks the delivered WAV files and updates their QA report. Large assets use Git LFS; run `git lfs pull` after cloning if they are still pointer files.

## Rebuild in a separate destination

Requires Python 3 and NumPy. The commands below produce the generated and third-party subset in disposable local storage. They do not reproduce the complete 58-file delivery: the original composition source is archived separately. To rebuild that fixture, use `import_user_composition.py --source <original-stereo-16-bit-WAV> --output <build-folder>` after the recording import. Keep the delivered collection intact while rebuilding.

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
