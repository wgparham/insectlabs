# Repository maintenance and validation

Run commands from the repository root. All canonical archives are immutable; repairs belong in a new development candidate. Keep generated compiler output outside the synced checkout.

## Read-only integrity audit

```powershell
python tools/check_repository.py
```

Checks tracked SHA-256 manifests, all archived Designer/source pairs, canonical index targets and local Markdown file links. It tolerates Designer declaration reordering while still comparing executable tokens. It does not compile or prove host behavior. Stage new files before running the final tracked-file check.

## SDK and DSP checks

The validators below keep disposable compiler output outside the checkout: generally `C:/InsectLabs-Build`, with sw2 using an automatically cleaned system temporary directory. The SDK remains external at `C:/ProgramData/Voltage/voltage.jar`.

```powershell
python voltage-modular/colorbox/tools/validate.py --sdk C:/ProgramData/Voltage/voltage.jar
python voltage-modular/laboratory/tools/validate_signal_processor_project.py --sdk C:/ProgramData/Voltage/voltage.jar
python voltage-modular/laboratory/tools/validate_signal_processor_character.py
python voltage-modular/laboratory/tools/validate_faderdistr_a.py --sdk C:/ProgramData/Voltage/voltage.jar
python voltage-modular/laboratory/tools/validate_faderdistr_b.py --sdk C:/ProgramData/Voltage/voltage.jar
python voltage-modular/laboratory/tools/validate_faderdistr_core.py
python voltage-modular/laboratory/following/tests/validate.py
python voltage-modular/laboratory/sw2/tests/verify_character.py
python voltage-modular/laboratory/sinrnd/versions/1.1.0/tests/validate.py
python voltage-modular/laboratory/function/versions/1.0.0/tests/validate_callbacks.py
python voltage-modular/laboratory/deeptone/versions/1.0.0/tests/validate.py
python voltage-modular/laboratory/n01/versions/1.0.0/tests/validate.py
python voltage-modular/laboratory/sn-16u/versions/1.0.0/tests/validate.py
```

Fader|Distr and SIGPROC project checks resolve their release folder through the Laboratory canonical index. Colorbox compares its documented old/new release pairs. The shared Fader|Distr core test exercises the retained prototype/core; it supplements the two actual module checks. Function's suite is frozen with its release. Generator's historical cleanup comparison targets 1.0.1 against 1.0.0 and must not be presented as validation of the later power-fade release.

The repository audit verifies integrity and source agreement for all archives, including modules without a dedicated current DSP harness. It does not replace Designer build/load, UI, save/reload and listening checks. Stop on errors; do not continue to commit or push after a failed check.

For audio fixture rebuild and verification, see [TestBench](../test-bench/README.md). Its numerical verifier updates its QA report; the repository integrity audit is read-only.
