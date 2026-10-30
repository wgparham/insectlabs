# Model 2

Model 2 is Cloud Generator with the original program's quirks corrected. It runs in the same app and engine as Model 1.

- **Model selector:** in the header, choose **Model 1 (original)** or **Model 2 (corrected)**.
- **Individual fixes:** **FIXES...** switches each correction on or off. Any mix other than the two presets shows as "Model 2 (custom fixes)".
- **A/B comparison:** with **A/B vs Model 1** ticked, Generate renders the same settings twice from the same random seed: once as Model 1 (A) and once with the current fixes (B). The **A** and **B** buttons switch the plot, playback and export between them.
- **Output options:** **Output** offers other sample rates (with any fix on) and 24-bit or 32-bit float export (with the floating-point mix).
- **CLI:** `cloudgen-cli` takes `model=2`, `rate=…`, and `+name` / `-name` to switch single fixes after the preset.

With every fix off the engine is Model 1, bit for bit. This is checked against a snapshot of the Model 1 engine over 48 settings: both cloud types, all four waveforms, overloads, random durations, every panning combination, text mode and all three granulation orders.

[ALGORITHM.md](ALGORITHM.md) explains each quirk in detail.

## Fixes in the Model 2 preset

### Bugs

| Fix | Model 1 | Model 2 |
| --- | --- | --- |
| `interpolateOverDuration` | Bandlimits, amplitude, grain duration and fixed panning interpolate over the cloud's *end time*, so with a start time they never reach their final values | All parameters interpolate over the duration |
| `coherentPanning` | With one Random box ticked the other setting is ignored: grains narrow to hard left, or widen from it | The random end is a fresh position per grain; grains move between it and the fixed setting |
| `grainDurationRange` | Random duration = Minimum + r·Maximum, and the value carries over to later grains | Random duration between Minimum and Maximum, drawn per grain |
| `wavetableWrap` | Table entry 511 is never filled (0), and the oscillator reads past the end into the envelope table (290) | All 512 entries are used and the oscillator wraps to entry 0 |
| `oscillatorNoWrap` | The difference between neighbouring entries is a 16-bit subtraction; at square and saw edges it wraps and the output can jump to −32768 | Interpolation in floating point |
| `sawtoothFirstPoint` | The saw's first point is 16383.5 instead of 0 | On the ramp |
| `safeGranulation` | Positions are in 16-bit samples, so an odd position swaps left and right. Random selection can read past the end of the file. Grains that would overrun are moved to the file's start | Whole stereo frames (with sub-frame interpolation), always inside the file |
| `resampleSource` | Source files play at 44.1 kHz whatever their real rate, so they come out at the wrong pitch and speed | Read at their own rate with linear interpolation |
| `dropUnusedRandom` | One random number per grain is drawn and thrown away | Not drawn. This only changes which random numbers later parameters get |

### Signal quality

| Fix | Model 1 | Model 2 |
| --- | --- | --- |
| `floatMix` | Each grain is added into the 16-bit buffer and truncated. Overloads become −32768 (SANE's integer overflow value), a harsh wraparound | Mixed in double precision, rounded and hard-clipped once at the end. The unclipped mix is kept for 24-bit and float export |
| `smoothEnvelope` | 512-step Gaussian table, which steps audibly on long grains | Continuous Gaussian, same shape and peak |
| `keepCloudTail` | The file ends exactly at start + duration, cutting off the last grains. Synchronous grains that would overrun are dropped entirely | The file is extended by the longest grain so every grain finishes |

Other output sample rates (48, 88.2, 96 kHz) are available whenever any fix is on.

## Optional, not in the preset

These change the character of the sound rather than fix errors.

| Option | Effect |
| --- | --- |
| `fullScaleEnvelope` | The original's Gaussian peaks at 32758 (its 2.506 and 6.28318 literals do not quite cancel). This makes it peak at exactly 32767, a difference of 0.002 dB |
| `logFrequency` | Frequencies are spread evenly in pitch between the bandlimits instead of evenly in Hz. With linear spread a 100 Hz–10 kHz cloud clusters in the top octaves; with log spread its median is near 1 kHz |

## Measured differences

These were measured with the engine on identical settings and seeds. Two Model 1 entries are calculated rather than measured: resampling (a 22.05 kHz file read at 44.1 kHz) and the linear median.

| Fix | Setting | Model 1 | Model 2 |
| --- | --- | --- | --- |
| interpolateOverDuration | start 4 s, amplitude 10→90 % | last grain at 50 % | last grain at 90 % |
| coherentPanning | initial Random, final hard right | mean pan 0.04 (left) over the last 0.5 s | 0.96 (right) |
| grainDurationRange | Maximum 0.05, Minimum 0.01 | up to 0.059 s | 0.010–0.050 s |
| floatMix | amplitude 500 %, density 200 | 76,122 overloaded samples flip sign | none; all clip to ±32767 |
| wavetable fixes | slow saw grain against the ideal ramp | worst deviation 3266 (int16 units) | 0.2 |
| resampleSource | 441 Hz tone recorded at 22.05 kHz | an octave up (882 Hz) | 441 Hz |
| logFrequency | 100 Hz–10 kHz | median ≈5 kHz | median 979 Hz |

## Notes

- Model 2 keeps the original's text output format, so text files from both models can be compared line by line.
- A/B mode always renders A at 44.1 kHz, as the original did.
