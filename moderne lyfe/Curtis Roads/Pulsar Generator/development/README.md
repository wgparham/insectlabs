# Pulsar Generator

A port of **Pulsar Generator** (Curtis Roads and Alberto de Campo, 2001), a pulsar synthesis instrument originally written in SuperCollider 2 for classic Mac OS. It now runs on Windows, macOS and Linux as a standalone application and as a plugin: VST3, AU on macOS, and LV2 on Linux.

The original's SuperCollider source was recovered from the compressed class library inside the application, and the engine follows it. The behaviour of the SuperCollider 2 unit generators it uses was taken from the PowerPC runtime. [docs/ALGORITHM.md](docs/ALGORITHM.md) describes the recovery and the synthesis in detail.

It loads the original files directly: `.set` table sets, `.bank` banks, and `.wt` wavetables. The original distribution's DemoTables, ExampleTables and El Rey 6 banks are in the parent folder.

## Layout

| Path | Contents |
| --- | --- |
| `src/engine/` | Synthesis engine, file formats and table operations: portable C++17, no dependencies |
| `src/plugin/` | Standalone app and plugin (JUCE) |
| `src/cli/` | `pulsargen-cli`: render a `.set` offline |
| `reference/Default.lib.sc` | The recovered SuperCollider 2 source |
| `tools/re/` | Recovery tools: library decompressor, PowerPC disassembler, resource extractor |
| `docs/` | Recovered design |

## Building

Requirements: CMake 3.22+, a C++17 compiler, and Git. CMake downloads JUCE automatically.

```bash
cmake -B build
cmake --build build --config Release
```

Pass `-DPULSARGEN_BUILD_PLUGIN=OFF` to build only the engine and CLI.

The `pulsar-generator` GitHub Actions workflow, at the repository root in `.github/workflows/pulsar-generator.yml`, builds all platforms whenever this folder changes.

## Licensing

JUCE is used under its AGPLv3 / commercial dual licence. Decide which applies before distributing builds.
