# TSL 1.0.0 release review

Approval: user supplied tsl.java/tsl.vmod/hero and stated “This is the one. canonize it” on 2026-10-14. User subsequently authorized class rename tsl_alt to tsl. This records acceptance of the supplied instrument; no separate detailed native control checklist or publication confirmation was supplied. Publication: unconfirmed.

Scope: class identity correction and T&H tooltip text only. The approved incoming pair is preserved under ../../research/approved-input-2026-10-14/ as .txt files. No DSP coefficients, arithmetic, routing, lifecycle or state policies changed. Byte comparison after those exact text substitutions verifies executable parity. Non-code VMOD metadata differs only in the explicitly requested class name. Hero is copied byte-for-byte.

Automated evidence: tests/validate.py compiles both source forms against the SDK with --release 17 -Xlint:all -Werror; checks source-pair agreement, Designer round-trip, identity/version, hashes, rate endpoints/inverse, polarity/settling, CV extremes, 3 V gate boundary, independent channels, direct bypass/frozen histories/no control reads, resume and active input guards. See VALIDATION.txt for actual output. Native host audition of the identity/tooltip correction is not independently performed.

Known gaps retained from approved source: raw bypass has no nonfinite/finite-range guard; unchanged coefficients are recomputed; input/output jacks use native name tooltips; category remains Source. No assertion of complete handbook compliance. Native CPU, undo, automation, saved-patch compatibility and separate final Designer export remain unverified. The correction does not alter sound; prototype patches referencing tsl_alt may require replacement after the requested class rename.

Housekeeping: series-specific research stays under TSL/references and TSL/research; experiments and working notes retained as research, rather than being silently discarded. No new useful audio fixture or independently reusable DSP primitive was created by this release. Shared repository changes outside TSL are not included. User authorized wrap-up and Git push on 2026-10-14.

Repository audit found unrelated existing checksum mismatches in Wardenclyffe n01 2.0.1 and sn16u 2.1.0 (Java, VMOD and hero for each). They were not modified. Two historical TSL links to removed alt filenames were corrected to plain historical labels. Repository-wide green status is therefore blocked by those six unrelated hash discrepancies.

Closeout: n01/sn16u discrepancies were traced to the committed October 12 artwork updates; manifests corrected with user authorization and provenance records. No module source changes were needed. Shared EMS/Berna research is included so the released reference links remain usable. RVG work, OFB development and tmp material are excluded from this release commit.
