# Repository maintenance and validation

Run repository checks from the checkout root. Keep generated compiler output outside Dropbox and Git.

## Read-only integrity audit

```powershell
python tools/check_repository.py
```

This checks tracked SHA-256 manifests, archived Designer/source pairs, canonical index targets and local Markdown file links. It tolerates Designer declaration reordering while comparing executable source. It does not compile Java or prove host behavior; stage new files before the final tracked-file audit.

Use `python tools/sync_vmod_source.py --help` to synchronize a Java export into its matching Designer project after an intentional source edit. Review the diff and refresh release hashes after using it.

## SDK and audio checks

```powershell
python voltage-modular/colorbox/tools/validate.py --sdk C:/ProgramData/Voltage/voltage.jar
python test-bench/tools/verify.py
```

Wardenclyffe release folders contain their applicable repeatable tests and review evidence. Older module-specific tests are retained with their historical 1.x releases and must not be treated as tests of the 2.x source unless the test says so. Every 2.x module was built and auditioned by the user before this packaging pass; the release records describe the automated checks performed here and the scope of the user-host test.

The repository audit verifies integrity and source agreement; it does not replace Designer build/load, UI, save/reload or listening checks. Stop on errors. For fixture format, provenance and checksums, see the [TestBench project](../test-bench/README.md).
