# Colorbox code conventions

Apply these conventions to editable user code; let Voltage Module Designer manage its generated scaffold and control metadata.

- Four spaces per indentation level, no tabs; class-member indentation follows Designer's existing `indent class = false` setting. Use same-line braces. User regions were normalized using google-java-format 1.36.1 in AOSP mode; generated regions were preserved.
- Use lowerCamelCase for private helpers, fields, Internal Names and Variable Names; UPPER_SNAKE_CASE
  for constants. Display Names are human readable, with spaces and appropriate capitalization.
  Retain SDK callback/API capitalization. Apply Display Names consistently to source constructors
  and Designer metadata; existing immutable release archives remain unchanged.
- Keep initialization, audio processing, direct bypass, tooltips, and DSP helpers in their designated Designer user regions. Tooltips run outside the per-sample processing path.
- Read control/jack values as required by each design, and preserve established smoothing. Avoid allocations, formatting, or other UI work in the audio callbacks.
- Direct bypass retains each module's routing, skips DSP and knob reads, and resets stale DSP histories once processing resumes. Do not introduce crossfades or latency compensation that keeps bypassed DSP running.
- Keep module-specific DSP self-contained. Do not consolidate filters or alter coefficients merely to make implementations look alike. Preserve each module's tested transfer functions and normalization rules.
- HSB topology value 1 is STANDARD/up; value 0 is CUSTOM/down. Default to STANDARD. Any saved-state semantic change requires explicit release notes.
- Treat `.vmod` embedded source and exported `.java` as a matched pair. Preserve editor line metadata when modifying a project outside Designer; verify artwork and control metadata.
- Keep immutable version folders, a canonical version index, reference snapshots, hashes, release notes, and repeatable validation. Bump patch versions for behavior-preserving cleanups and small corrections.

The existing repository GPL-3.0 license applies. Voltage Modular's SDK is an external build dependency and is not included.

Colorbox resets its input-dependent histories on resume. New source instruments may preserve phase, filters and held values under their documented resume policy; apply the shared infrastructure standard rather than blindly resetting every state.

## Applying these conventions to new modules

Use descriptive names before final approval. Existing Colorbox identifiers remain stable; generic
prototype identifiers in new modules should be replaced consistently in source and Designer metadata
while preserving UUIDs. Follow the shared [release checklist](../docs/Module-Release-Checklist.md),
including its user build gate or an explicitly authorized, scoped cleanup/promotion path with comparison evidence. Do not infer approval for audible changes from permission for cleanup.

Display Names and Notes are editable design material by default. The user authorizes correcting,
rewriting or removing their contents during development and cleanup; they are often temporary design
notes, not permanent requirements. Preserve specific wording only when the user explicitly asks.
Keep Display Names human readable and Notes accurate; do not treat draft Notes as instructions that
override the user’s request. Internal/Variable Names continue to follow the code naming standard.
