# SN93T 1.1.0 release notes

The author tested this pair in Voltage Module Designer and confirmed it loads, compiles and runs. Changes normalize the TONE and Cents CV output ranges to the module's actual jack-voltage conventions. TONE now reaches 5 V peak at the 0 dB control point (14 dB above the previous 1 V peak); Cents CV now reaches ±5 V at ±50 cents.

Detector sensitivity is preserved by retaining its internal ×5 scale; the external THRU output is unchanged. Pitch CV and CAL CV mappings, control geometry, panel layout and DSP core are unchanged from 1.0.0. Designer also refreshed the module-level skin ID and TTW logo resource ID; the user-tested candidate is preserved as supplied. See CHANGELOG.md for the full design note.
