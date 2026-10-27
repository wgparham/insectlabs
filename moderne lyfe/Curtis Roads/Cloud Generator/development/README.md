# Cloud Generator

A port of **Cloud Generator Model 1** (Curtis Roads and John Alexander, 1996), an interactive granular synthesis application originally for 68K/PowerPC Mac OS, to Windows, macOS and Linux.

There is no surviving source. The port reimplements the program from:

- `Cloud Generator 68K nfpu`: the original application (MacBinary). Its resource fork contains the 68K code segments (`RA.main`, `RA.aiff`, `sine table`, `gauss envelope`, ...) and the original dialogs and artwork.
- `CloudGenManual`: the original DOCMaker manual (MacBinary).

The synthesis engine follows the disassembled 68K code step by step: grain scheduling, interpolation, the Gaussian and wavetable construction, the oscillator, mixing, granulation and the text format, including the original's quirks. [docs/ALGORITHM.md](docs/ALGORITHM.md) describes the recovered algorithm. The original used 80-bit SANE arithmetic; the port computes those intermediates in 64-bit double, so output is identical on every platform but can differ from a 68K Mac by ±1 LSB in rare cases. The few points the disassembly cannot settle are marked `RE:` in the source.

## Layout

| Path | Contents |
| --- | --- |
| `src/engine/` | Synthesis engine: portable C++17 with no dependencies |
| `src/app/` | GUI application (JUCE) |
| `src/cli/` | `cloudgen-cli`: render clouds from the command line |
| `tools/re/` | Reverse-engineering tools: resource extractor, 68K disassembler driver (uses [Musashi](https://github.com/kstenerud/Musashi)), SANE lifter |
| `docs/` | Recovered algorithm |

## Building

Requirements: CMake 3.22+, a C++17 compiler (Visual Studio 2022 on Windows, Xcode on macOS, GCC/Clang on Linux), and Git. CMake downloads JUCE automatically.

```bash
cmake -B build
cmake --build build --config Release
```

To build only the engine and CLI (no JUCE download), pass `-DCLOUDGEN_BUILD_GUI=OFF`.

GitHub Actions (`.github/workflows/build.yml`) builds all three platforms on every push. macOS releases need signing and notarization with an Apple Developer account before they can be distributed widely.

## Licensing

JUCE is used under its AGPLv3 / commercial dual licence. Decide which applies before distributing builds.
