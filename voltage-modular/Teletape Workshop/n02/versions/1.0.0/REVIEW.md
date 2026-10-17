# N02 1.0.0 — release review

The user approved the working instrument and authorized final tests, housekeeping, canonization and push on 2026-10-16. Their latest Designer save already identified the build as 1.0.0 and included the final hero with D/A blend labels. Source agreement was verified before editing.

Closeout found that native MIX had changed to -1..+1 while its DSP still used 0..1 and earlier direction text. The release maps the whole travel to digital-left/analog-right and updates typed entry and tooltip text. This is a control-wiring correction after the user's panel changes. All other DSP user regions and core arithmetic were compared with the supplied source; only that mapping, its UI conversion and a release comment changed. Root metadata, controls/UUIDs, geometry, skins, test state, assets and hero bytes were compared with the supplied set and preserved, except Notes was reduced to exactly `v1.0.0`.

The final working-pair SDK builds and DSP harness passed before packaging. Final archive-path validation and repository audit are recorded in VALIDATION.txt. Tests exercise actual code with control/jack substitutes; they do not establish native GUI semantics. The user approved the working candidate; the post-housekeeping MIX correction has automated evidence but no separate native-host audition. No CPU benchmark or saved-patch migration check is claimed. Publication remains deferred.

Folder audit: shared EMS PDFs remain under Teletape Workshop/references. The release DSP design preserves the source locations and model limits; the long-term Research Compendium retains historical and algorithmic leads, including Kawai for Series Three. The reusable Galois step was copied to DSP primitives and checked against the embedded copy. No useful test audio existed to move to TestBench. The approved archive contains the pair, final hero, manuals, developer notes, design evidence, review, tests and hashes; redundant development pair/design/tests/hero copies and .bak files are removed only after archive-path validation and hash comparison. The development directory then contains a pointer to the archive.

Baseline SHA-256 values before housekeeping:

- `n02.java`: `791c47e4b4dfe180c92f160056dec537257aa6c7016ecc0cb5609c4ac1625cec`
- `n02.vmod`: `fb1ec02722ff1fce1b3117292d6e0aef8ac02240e3343630b387b38c93b5a666`
- `n02_hero.png`: `8729e8d113a1b3324d566839983afa8905694923c4a6c9aefc2aceb203635b1f`

Release-snapshot audit passed with zero errors. Four unrelated pre-existing local files (the blank-template pair and Sectronix sx501a pair) are excluded from the commit; their index blobs were used read-only while auditing the exact staged release. The complete working tree continues to flag the two blank-template manifest mismatches until that separate work is reviewed. No unrelated file or manifest was reset, rewritten or included.
