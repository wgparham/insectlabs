# InsectLabs Audio Test Bench

Local review collection: `Resources/TestBench/v1` beneath the outer InsectLabs workspace, outside this Git clone. It contains 53 WAV files at 48 kHz/24-bit, a listening guide, measured catalog, attribution and verification results. `Resources/TestBench/InsectLabs-TestBench-v1.zip` is the portable review copy.

The `archive-metadata` directory preserves the delivery manifest, credits and guide without checking the audio binaries into Git. Third-party recording licenses are separate from the repository's code license.

## Rebuild

Requires Python 3 and NumPy. Run from the outer InsectLabs workspace. Generation rewrites the catalog with the generated subset; run the import step afterward to restore the full catalog. Preserve README.md when rebuilding.

```powershell
python repository/voltage-modular/test-bench/tools/generate.py --output Resources/TestBench/v1
python repository/voltage-modular/test-bench/tools/download_sources.py --output Resources/TestBench/sources/ibm-freesound
python repository/voltage-modular/test-bench/tools/import_recordings.py --output Resources/TestBench/v1 --sources Resources/TestBench/sources/ibm-freesound
python repository/voltage-modular/test-bench/tools/verify.py Resources/TestBench/v1
```

Generation uses seed 19710510. The manifest records NumPy's version; floating-point or FFT implementation changes across environments may alter the least significant bits. SHA-256 hashes identify this particular delivered build.

Keep the credits with the WAV distribution. Do not apply the generated-audio CC0 dedication to the sourced recordings. Review the collection by ear and in Voltage Modular before promoting it to a release.
