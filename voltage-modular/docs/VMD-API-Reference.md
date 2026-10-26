# Voltage Module Designer API notes

Selected API facts for InsectLabs module development. This is a concise reference, not a complete SDK definition. The full user-captured Designer Library reference is archived as [`VMD_API_Reference_1.md`](../../VMD_API_Reference_1.md). Signatures marked **[Designer tooltip]** were transcribed from that reference; use Designer's generated source and current official documentation when context or overloads matter.

## Loading audio files

`VoltageSound.LoadFromFile(String filePath)` is a native method that returns `boolean`. Its Designer tooltip says it loads OGG, MP3, WAV, and AIFF files. The method belongs to `VoltageSound`; it loads audio and is not a general-purpose text or arbitrary-file reader.

For a user-selected file, the Designer reference also lists:

```java
native String ShowOpenFileDialog(
    String dialogBoxTitle,
    String initialDirectory,
    String fileTypesToShow)
```

The dialog returns the selected path. Check `VoltageSound.LoadFromFile`'s boolean result before treating the audio as loaded. The Designer API reference also lists file-drop registration and `File_Dropped` notifications; consult its control/event notes for that workflow.

The captured file-drop workflow is `RegisterFileTypesForFileDrop("tun;scl")`, using semicolon-separated extensions. `File_Dropped` supplies the dropped path as a `String` in `Notify()`'s `object` argument. Change the extensions to match the formats the module accepts.

## Preserving module-specific state

Cherry Audio's [Module Functions and Variables](https://docs.cherryaudio.com/voltage-module-designer/module-functions-and-variables) page documents `GetStateInformation()` and `SetStateInformation(byte[])` for state beyond the control and cable state Voltage saves automatically. The page recommends `java.util.Properties` with byte-array streams as one way to encode custom state. File paths or loaded-sample identity may need this module-specific state handling if they must survive preset save/load.

## Provenance and limits

- The `VoltageSound.LoadFromFile` tooltip and file-dialog signatures are Designer tooltip captures supplied by the user on 2026-10-22.
- The lifecycle and state-persistence guidance is from Cherry Audio's official documentation linked above.
- These notes do not establish threading rules, supported path permissions, or whether a particular sound object loads synchronously; verify those behaviors in Designer before relying on them in a module.
