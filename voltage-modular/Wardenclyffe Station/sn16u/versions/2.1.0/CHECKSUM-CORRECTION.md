# Archival checksum correction â€” 2026-10-14

Commit 182832ca567ee2f706f1a7a3be2353f541526a14 (2026-10-12) updated this release's skin/resources and hero image without refreshing its manifest. All three current files match Git HEAD byte-for-byte. The previous manifest hashes match the files immediately before that commit. All executable user regions are unchanged; exported Java differs only in skin ID and whitespace. Control UUID identities are preserved. SN16u additionally changed an image-resource UUID and Designer test state.

At the user's request, refreshed only the Java, VMOD and hero checksum entries and added this provenance record. No source, project, artwork or DSP changes; no new native test or publication claim. This is an archival integrity correction, not a new module version.

Hashed Markdown and test scripts were normalized to Git-standard LF during final staging, and their manifest entries refreshed. Text content is unchanged; this prevents working-copy versus committed-byte discrepancies.
