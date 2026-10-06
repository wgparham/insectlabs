# Function 1.0.1a — development candidate

This is a development panel revision. **Canonical remains 1.0.0** in `../versions/1.0.0/`.
The Designer Notes field is set to `v1.0.1a`. The current candidate adds panel identification
labels, revises title/branding placement, and adjusts the power indicator. The DSP, bypass,
tooltip, and state-handling user-code sections are unchanged from 1.0.0.

The updated Designer export moved `super.Destroy()` before `StopGuiUpdateTimer()`; review that
lifecycle order during development testing. Build and host testing of this panel revision are
pending. The checked-in panel PNG remains the 1.0.0 image until a new hero image is supplied.

Run `tests/validate_callbacks.py` for SDK compilation and callback checks.
