# 1947B SIN/RND Generator-Filter — v2.0.0rc development

This is the in-progress successor to SIN/RND 1.1.0. The current 1.1.0 release remains
canonical at `../versions/1.1.0/`; this v2.0.0rc project is not a replacement release.

The candidate carries revised 1947B branding and panel labels, with the same DSP/user-code
regions as 1.1.0. Its Designer Notes field is `v2.0.0rc`. The source and project filenames
match its current internal class `nineteenfortyseven`. No new hero preview has been supplied.

GUI timer shutdown is retained before base destruction. Run `tests/validate.py` for project
round-trip, source parity, SDK compilation, and the callback/DSP checks.
