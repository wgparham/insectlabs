# HGD developer notes — 1.0.0

This is the developer-notes snapshot for the exact HGD 1.0.0 release archived in this folder. Canonical files are [hgd.java](hgd.java), [hgd.vmod](hgd.vmod), and [hgd_hero.png](hgd_hero.png). See [USER-MANUAL.md](USER-MANUAL.md), [RELEASE-NOTES.md](RELEASE-NOTES.md), [REVIEW.md](REVIEW.md), and [VALIDATION.txt](VALIDATION.txt). HGD circuit images and documents are catalogued in the [reference inventory](../../references/README.md).

## Behavior and controls

There are four independent lanes. Each lane has a mono input/output, Gain (0–1, default 0), Tone (−1..+1, default 0), a four-position voice selector, and a III control. The shipped selector positions map lanes 1–4 to DS, FZ, HM and MT respectively. Tone is centered at 0. The III ranges are 0–1 for lanes 1 and 2 and −1..+1 for lanes 3 and 4; `thirdPos()` converts either physical range to the common 0–1 DSP parameter position. `GetTooltipText()` reports the selected voice's current unit and parameter range. `EditComponentValue()` performs the inverse conversion for typed edits. Selector changes fade over 5 ms through the per-channel `setVoice()` transition.

GAIN controls modeled drive, not input attenuation. The calibration table adjusts each voice's output compensation to a target RMS that increases from 0 to +6 dB across the control. This is a model choice, not a measured guarantee at every signal, tone setting or mode.

Four lane outputs remain independent; MIX is `0.5 * sum(lane outputs)`. Unpatched inputs are silent and omitted from the sum. During host bypass, the module routes each input directly to its corresponding output and silences MIX. It marks the core for reset and clears histories on the first active sample after bypass.

## DSP map

- `Initialize()` builds the input/output/control lookup arrays, adopts current knob and switch values, then resets the core.
- `Notify()`, `handleKnobChanged()`, and `handleSwitchChanged()` transfer UI state to the selected channel.
- `ProcessSample()` skips unpatched channels, clears a channel's history when newly patched, processes each active lane, and writes the half-sum mix.
- `ProcessBypassedSample()` performs direct per-lane routing and marks DSP state for reset on resume.
- `HgdCore.Channel.process()` smooths Gain, Tone and III controls, upsamples, applies the selected nonlinear voice and tone section, downsamples, removes DC, applies per-voice output compensation and a soft ceiling.
- `Channel.nl()` contains the DS, FZ, HM and MT nonlinear models. `Channel.tone()` contains their tone-shaping responses. `update()` refreshes derived coefficients only when smoothed controls change enough to cross the threshold.
- HM adds a per-lane deterministic xorshift noise source, filtered at the oversampled rate. Seeds differ by lane. The source is intended as part of the HM voice model.

The nonlinear path uses 4× oversampling with 63-tap Blackman-windowed sinc filters (16 polyphase taps per branch). There are no per-sample object or array allocations in the module callback. The module constructs the core at 48 kHz; behavior at other host sample rates is unverified. Calibration tables are generated once per supported sample rate and shared, but startup cost, runtime CPU and latency have not been benchmarked on a target host. The four oversampled nonlinear channels are the primary CPU cost.

The voice labels refer to the source inspirations: DS-1-style, FZ-1-style, HM-2-style and MT-2-style. The code combines documented circuit elements, observed component values and modeled/guessed transfer functions. It is not a SPICE reconstruction and must not be described as an exact clone. See `hgd.java` comments and the reference inventory for confidence qualifiers.

## Maintenance checks

If changing a voice model, repeat mode-specific response, tone/III endpoints, finite-output and ceiling tests, cross-lane isolation, seeded-noise reproducibility, bypass/resume, and mix scaling. Recheck typed-value conversion if changing III ranges or tooltip units. Rebuild and validate both embedded and exported source after any code edit. Re-measure CPU, latency and alias behavior if changing oversampling or filters; no native CPU benchmark is currently recorded.
