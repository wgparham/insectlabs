# 1958 Dual Waveform Generator · Version 2.0.1

Canonical Wardenclyffe Station release for Voltage Modular.

This release contains the user-tested Designer project, matching Java export, updated panel artwork, user manual, review record, and integrity manifest. It preserves the 2.0.0 archive and the earlier 1.x releases.

Version 2.0.1 extends the internal modulation range to 0.05–100 Hz and lowers the 1 kHz reference mix. In mixed mode, the reference now enters before AMPLITUDE, so its level follows the main amplitude control.

Open `nineteenfiftyeight.vmod` in Voltage Module Designer. Its matching Java export is `nineteenfiftyeight.java`.

See [USER-MANUAL.md](USER-MANUAL.md) for operation and [DEVELOPER-NOTES.md](DEVELOPER-NOTES.md) for implementation guidance. [REVIEW.md](REVIEW.md) records validation and user approval; `tests/validate.py` checks the serialized pair and core range assumptions. `SHA256.json` covers every release file except itself.
