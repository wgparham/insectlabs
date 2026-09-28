# Colorbox code conventions

Apply these conventions to editable user code; let Voltage Module Designer manage its generated scaffold and control metadata.

- Four spaces per indentation level, no tabs; class-member indentation follows Designer's existing `indent class = false` setting. Use same-line braces. User regions were normalized using google-java-format 1.36.1 in AOSP mode; generated regions were preserved.
- Use lowerCamelCase for private helpers and fields, UPPER_SNAKE_CASE for constants. Retain the SDK's callback/API capitalization and established control identifiers.
- Keep initialization, audio processing, direct bypass, tooltips, and DSP helpers in their designated Designer user regions. Tooltips run outside the per-sample processing path.
- Read control/jack values as required by each design, and preserve established smoothing. Avoid allocations, formatting, or other UI work in the audio callbacks.
- Direct bypass retains each module's routing, skips DSP and knob reads, and resets stale DSP histories once processing resumes. Do not introduce crossfades or latency compensation that keeps bypassed DSP running.
- Keep module-specific DSP self-contained. Do not consolidate filters or alter coefficients merely to make implementations look alike. Preserve each module's tested transfer functions and normalization rules.
- HSB topology value 1 is STANDARD/up; value 0 is CUSTOM/down. Default to STANDARD. Any saved-state semantic change requires explicit release notes.
- Treat `.vmod` embedded source and exported `.java` as a matched pair. Preserve editor line metadata when modifying a project outside Designer; verify artwork and control metadata.
- Keep immutable version folders, a canonical version index, reference snapshots, hashes, release notes, and repeatable validation. Bump patch versions for behavior-preserving cleanups and small corrections.

The existing repository GPL-3.0 license applies. Voltage Modular's SDK is an external build dependency and is not included.
