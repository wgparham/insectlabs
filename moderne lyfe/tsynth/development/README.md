# TurboSynth

A port of **Turbosynth SC 2.22** (Digidesign, 1988–1995), the modular sample-synthesis program for the classic Macintosh, to Windows, macOS and Linux as a standalone application.

You patch modules together to build a sound: oscillators, samples, noise, envelopes, filters, modulators, waveshapers, mixers, delays, resonators, pitch shifters, stretchers and the diffuser. TurboSynth then computes the whole result, which you can play or export. The port reads and writes the original documents. All 29 example patches from the 2.0 and 2.22 disks open and render.

- **The synthesis follows the original program.** It was recovered from the 68000 code of Turbosynth SC 2.22, including its fixed-point arithmetic, quirks and per-module normalization. The Diffuser, which originally needed a Digidesign DSP card, follows the card's 56000 program. [docs/RE-NOTES.md](docs/RE-NOTES.md) is the recovery record.
- **Checked against the original.** Running the original program in an emulator, 45 of the 48 example patches render identically to it, sample for sample. The other three use the Diffuser, which the original can't run without the DSP card.
- **The interface is Classic Mac inspired.** It uses the original icons, pinstriped windows and a 1-bit look. **View > Dark Mode** switches to light ink on charcoal for night work, and the choice is remembered.

## Using it

| Action | How |
| --- | --- |
| Open a patch | File > Open, or pass the document on the command line |
| Add a module | Click its icon in the palette, then click in the patch |
| Connect | Choose the patch-cord tool and drag from one module to another, or Shift-drag with the arrow |
| Remove | Use the eraser tool on a module or a cord, or select a module and press Delete |
| Edit | Double-click a module (or click it with the Info tool) to open its window: settings, breakpoint envelope or waveform |
| Presets and modifiers | The tool palette beside the envelope, waveform or waveshaper editor, with the original icons: each preset icon holds four presets, one per quarter. Hover for names. The waveshaper also has the pencil and the eraser (puts back the straight line) |
| Stereo | Sound > Stereo Output switches the output jack between mono and the L/R pair |
| Stereo samples | Loading a stereo file into a Sample module (with Stereo Preferences set to "Open both channels") makes a linked pair: the L and R halves sit side by side, select together, and share edits, tools and loop points. Each half has its own cords, so you can process the two channels differently. Edit > Convert Stereo Sample to Dual Mono unlinks them |
| Harmonic mode | In the Oscillator, the two top palette icons switch between the waveform and its first 64 harmonics. Drag a harmonic with the arrow, or draw a line with the rake (Alt: only harmonics above the line; Alt+Shift: all of them). Harmonic presets and modifiers have their own icons |
| Time line | The Oscillator's waveforms sit on a time line: click one to edit it, drag it to move it in time. **Save** writes the current waveform to a file; the disk quarter of the last preset icon loads one |
| Edit menu | Undo, Cut, Copy, Paste, Clear, Duplicate and Convert to Sample (freezes a module's sound into a Sample module and disconnects its inputs), with the original shortcuts (Ctrl or Cmd + Z, X, C, V, B, D) |
| Options menu | Auto Preview (play after every edit, Ctrl+T); the Parameter Manager (Ctrl+Y): default rate, length and frequency, Inherit sample rate, Convert sample modules to default, Inherit length, Dynamic length (the original's formulas for the Delay, Stretchers and Pitch Shifter) and Monitor loop at output; Stereo Preferences: new documents as stereo, opening both channels of a stereo file or one you choose, matching channel lengths and the stereo sample rate |
| Tooltips | Hover over a palette tool, module or cord to see its name |
| Sample window | Open a Sample module: its window shows the sound beside the palette. Drag to select; Cut, Copy, Paste, Clear, Reverse (Ctrl+R) and Silence (Ctrl+E) then work on the selection. The palette has the original tools: load, sample rate conversion, extend loop, gain scaling (or normalize), envelope removal, crossfade looping, loop points to selection, the loop-marker pair and the Loop window |
| Loop markers | Pick the start or end marker in the palette and click in the sound, or drag a marker. A stereo output pair, or a linked stereo sample, shares its markers |
| Loop window | Shows the loop end (left) against the loop start (right). Click a half to move that point, use the arrows to step one sample or the Auto arrows to jump to the best match for the other point in that direction, the Info icon lists the loop points; zoom with the four arrow icons, and hear the loop with the speaker |
| Output crossfade | Select the output and use the crossfade icon: a mode, like the original, that crossfades whatever reaches the output. File > Save Loop as Waveform makes an oscillator waveform from the loop |
| Hear one module | Click it with the speaker tool, or use Hear in its window |
| Play / stop | The Play and Stop buttons, or the space bar |
| Export | File > Export Sound (16-bit WAV or AIFF) |

Sample modules play the sound stored in the document. As in the original, a Sample module whose "Save sound data with document" box is cleared stores no sound and reopens empty. **Load Sound...** in Module Info stores a WAV, AIFF or Sound Designer file in the patch.

## Command line

```bash
tsynth-cli patch out.wav [info] [module=N]
```

`info` lists the modules and connections. `module=N` writes one module's output instead of the patch's.

## Building

Requirements: CMake 3.22+, a C++17 compiler, and Git. CMake downloads JUCE for the app.

```bash
cmake -B build
cmake --build build --config Release
```

Pass `-DTSYNTH_BUILD_APP=OFF` to build only the engine and the command-line tool. The `turbosynth` GitHub Actions workflow (`.github/workflows/turbosynth.yml` at the repository root) builds all three platforms.

## Layout

| Path | Contents |
| --- | --- |
| `src/engine/` | Document reader/writer and synthesis engine: portable C++17, no dependencies |
| `src/app/` | The JUCE application and its Classic Mac look |
| `src/cli/` | `tsynth-cli` |
| `tests/` | Round-trip and module-statistics checks |
| `tools/re/` | Recovery tools: HFS extractor, resource lister, 68K and DSP56000 disassemblers |
| `docs/` | Recovery notes |

## Licensing

JUCE is used under its AGPLv3 / commercial dual licence. Decide which applies before distributing builds. The rights to TurboSynth itself were cleared by whoever commissioned this port.
