# InsectLabs Audio Test Bench

The full TestBench collection is versioned here. It contains 54 WAV files at 48 kHz/24-bit, a portable ZIP archive, source recordings with license evidence, and a measured catalog, attribution, manifest, and verification results.

- `v1` contains the ready-to-patch collection and its listening guide.
- `sources/ibm-freesound` preserves the source recordings and license evidence used for the recorded fixtures.
- `InsectLabs-TestBench-v1.zip` is the portable v1 review copy; its SHA-256 digest is in the adjacent `.sha256` file.
- `archive-metadata` preserves the original delivery metadata.

Third-party recording licenses are separate from this repository's code license. Keep the credits with the WAV distribution. Do not apply the generated-audio CC0 dedication to the sourced recordings.

## Rebuild

Requires Python 3 and NumPy. Run from the repository root. Generation rewrites the catalog with the generated subset; run the import step afterward to restore the full catalog. Preserve `v1/README.md` when rebuilding.

```powershell
python test-bench/tools/generate.py --output test-bench/v1
python test-bench/tools/download_sources.py --output test-bench/sources/ibm-freesound
python test-bench/tools/import_recordings.py --output test-bench/v1 --sources test-bench/sources/ibm-freesound
python test-bench/tools/verify.py test-bench/v1
```

Generation uses seed 19710510. The manifest records NumPy's version; floating-point or FFT implementation changes across environments may alter the least significant bits. SHA-256 hashes identify this particular delivered build.

The user has tested and approved this collection in Voltage Modular. Add useful new test fixtures as modules develop, updating the catalog, credits, manifest, and verification results together.
