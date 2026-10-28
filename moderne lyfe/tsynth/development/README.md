# TurboSynth

A port of **Turbosynth SC 2.22** (Digidesign, 1988–1995), the modular sample-synthesis program for the classic Macintosh, to Windows, macOS and Linux as a standalone application.

You patch modules together to build a sound: oscillators, samples, noise, envelopes, filters, modulators, waveshapers, mixers, delays, resonators, pitch shifters, stretchers and the diffuser. TurboSynth then computes the whole result, which you can play or export. The port reads and writes the original documents. All 29 example patches from the 2.0 and 2.22 disks open and render.

- **The synthesis follows the original program.** It was recovered from the 68000 code of Turbosynth SC 2.22, including its fixed-point arithmetic, quirks and per-module normalization. The Diffuser, which originally needed a Digidesign DSP card, follows the card's 56000 program. [docs/RE-NOTES.md](docs/RE-NOTES.md) is the recovery record.
- **The interface is Classic Mac inspired.** It uses the original icons, pinstriped windows and a 1-bit look. **View > Dark Mode** switches to light ink on charcoal for night work, and the choice is remembered.

## Using it

| Action | How |
| --- | --- |
| Open a patch | File > Open, or pass the document on the command line |
| Add a module | Click its icon in the palette, then click in the patch |
| Connect | Choose the patch-cord tool and drag from one module to another, or Shift-drag with the arrow |
| Remove | Use the eraser tool on a module or a cord, or select a module and press Delete |
| Edit | Select a module; Module Info shows its settings, breakpoint envelope or waveform |
| Hear one module | Double-click it, or use Hear in Module Info |
| Play / stop | The Play and Stop buttons, or the space bar |
| Export | File > Export Sound (16-bit WAV or AIFF) |

Sample modules use the sound stored in the document. If there isn't one, they look for a Sound Designer file named after the module, next to the patch or in a "Sound files" folder there. **Load Sound...** in Module Info stores a WAV, AIFF or Sound Designer file in the patch.

## Command line

```bash
tsynth-cli patch out.wav [sounds=folder] [info] [module=N]
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
