# Audio credits and redistribution

Generated audio in folders 01–06: original procedural material, dedicated under [CC0 1.0](https://creativecommons.org/publicdomain/zero/1.0/). No third-party samples were used in those folders. Generator code follows the repository license separately.

Folder 07 contains third-party recordings. Keep these credits with all copies, including derived channel combinations. No endorsement by the source authors is implied.

## jazz_guitar

[more Jazz guitar.wav](https://freesound.org/people/Sub-d/sounds/49658/) by **Sub-d**, [CC0-1.0](https://creativecommons.org/publicdomain/zero/1.0/).

Real guitar performance. Recording chain and existing effects not established.

[Downloaded WAV mirror](https://raw.githubusercontent.com/IBM/MAX-Audio-Embedding-Generator/333258a3644677d8de12a30ff50982bcfe23138d/samples/demo_assets/jazz_guitar.wav); SHA-256 `e3e637e7bebd6145c22469e5362e3613525a9cd280ae627035e1615dbcee25a4`. IBM's sample README identifies the original sources and notes conversion to 16-bit where necessary. Source license pages checked 2026-09-28.

## piano

[Piano Melody](https://freesound.org/people/benpm/sounds/186942/) by **benpm (formerly credited as Lemoncreme)**, [CC-BY-4.0](https://creativecommons.org/licenses/by/4.0/).

Sourced piano performance; acoustic versus digital instrument is unspecified by the author.

[Downloaded WAV mirror](https://raw.githubusercontent.com/IBM/MAX-Audio-Embedding-Generator/333258a3644677d8de12a30ff50982bcfe23138d/samples/demo_assets/piano.wav); SHA-256 `2a4b1da95931608f127a6110cf68b5d3269b95de87a8fcf115b0f94d3c1db4ec`. IBM's sample README identifies the original sources and notes conversion to 16-bit where necessary. Source license pages checked 2026-09-28.

## birds2

[Bird call in spring.mp3](https://freesound.org/people/jmiddlesworth/sounds/364663/) by **jmiddlesworth**, [CC0-1.0](https://creativecommons.org/publicdomain/zero/1.0/).

Real field recording, originally lossy MP3. WAV conversion does not restore lost detail.

[Downloaded WAV mirror](https://raw.githubusercontent.com/IBM/MAX-Audio-Embedding-Generator/333258a3644677d8de12a30ff50982bcfe23138d/samples/demo_assets/birds2.wav); SHA-256 `57c092eeb30b6597a4a698889ff01aac252d5169333b5759175c0d166c068173`. IBM's sample README identifies the original sources and notes conversion to 16-bit where necessary. Source license pages checked 2026-09-28.

## rain

[Rain, Moderate, C.wav](https://freesound.org/people/InspectorJ/sounds/401275/) by **InspectorJ (www.jshaw.co.uk)**, [CC-BY-4.0](https://creativecommons.org/licenses/by/4.0/).

Real moderate-rain field recording.

[Downloaded WAV mirror](https://raw.githubusercontent.com/IBM/MAX-Audio-Embedding-Generator/333258a3644677d8de12a30ff50982bcfe23138d/samples/demo_assets/rain.wav); SHA-256 `d1a50a18aa56241134e6179f1108c3c87ab45678e9175eabe8d9a7fec4572804`. IBM's sample README identifies the original sources and notes conversion to 16-bit where necessary. Source license pages checked 2026-09-28.

## Changes made for this collection

Used the first 10 seconds of the mirrored guitar, piano and rain files, and the first 24 seconds of birds. Removed the mean; applied 50 ms endpoint fades, Fourier resampled from 44.1 to 48 kHz, reapplied endpoint fades, and adjusted stereo peak to -9 dBFS. Exported as 24-bit PCM without adding effects. This does not restore precision lost in earlier conversions. Mono versions average left and right. Split-channel files pair those mono versions; the performances are independent and not tempo matched. The piano-containing pairs include CC BY 4.0 material: retain the piano credit and license link.

## User-owned original composition
`05-musical/after_drinking_at_emalines_original_mix.wav` is an original composition and fully mixed stereo recording supplied by the project creator. Copyright remains with the composer; it is included in this TestBench collection with permission for InsectLabs development and audition. Source SHA-256: `5ee10739c9c17d05c80f041c455b65debf80fa36da97cbcfd45d5ba188b16fcb`.

## sn-16u generated captures (1.2.0)

The `sn16u_` sweeps and noise files are generated audio dedicated under CC0-1.0. They were captured from the actual sn-16u v0.1.2rc callback test harness at 48 kHz, with deterministic noise seed 19710510. The manifest records source and delivery SHA-256 values; the importer documents scaling and fades. The pink algorithm is Paul Kellett's refined [pink-noise filter](https://www.musicdsp.org/en/latest/Filters/76-pink-noise-filter.html); blue differentiates its output. This audio dedication does not change the source code's license or the licenses of other recordings.

## 1.3.0 — original narrow-band selectivity probe

`01-calibration/selectivity_1k_neighbours_8s.wav`: original analytic five-sine construction,
generated for InsectLabs by `test-bench/tools/add_selectivity_fixture.py`. No third-party samples.
Dedicated under CC0 1.0, matching the collection's other generated calibration fixtures.
The manifest records the generator hash, NumPy version, frequencies, component levels and WAV hash.

## 1.4.0 — Model 62 head-spacing impulses

`02-dynamics/model62_head_spacing_impulses_150Hz_minus18dBFS.wav` is original analytic audio,
derived from the impulse pair and generator supplied by the project creator. Its reproducible,
cleaned generator is `test-bench/tools/add_model62_head_impulses.py`; the manifest records both
supplied source hashes and the delivered file/generator hashes. No third-party samples are used.
The generated fixture is dedicated under CC0 1.0, matching the other procedural calibration audio.
