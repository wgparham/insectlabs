# RM1010 Mixing Amplifier — user manual

**Canonical v1.0.0 · Voltage Modular · Wardenclyffe Station**

RM1010 is a four-channel mono mixing amplifier with independent channel drive, tonal balance, volume and effects-send controls. It combines three uses: a compact four-channel mixer, two independent two-channel submixers, and an amplifier/distortion instrument. Its numerical output labels describe their input groups: 3 combines channels I and II; 7 combines III and IIII; 10 combines both groups plus the effects return and LINK.

## Quick start

1. Connect mono audio to input I.
2. Set that row's range slider to L.
3. Raise VOLUME and take audio from output 3 or 10. The supplied panel retains zero VOLUME defaults, so a newly initialized channel can be silent until raised.
4. Raise GAIN to push the channel stage. Lower VOLUME to keep the driven signal at a practical mixing level.
5. Turn BALANCE left for darker/bassier sound or right for brighter/thinner sound. Noon is neutral.
6. Raise PROCESS, connect S to an external processor, and return its output to R if an effects loop is wanted.

RM1010 has no separate panel power switch or master volume control. System BYPASS follows the collection's immediate bypass convention.

## Signal flow

```text
I, II     -> individual channel stages -> gentle pair sum -> 3 --+
III, IIII -> individual channel stages -> gentle pair sum -> 7 --+-> final stage -> 10
R --------------------------------------------------------------+
LINK -----------------------------------------------------------+

Each channel: 16 Hz input coupling -> GAIN/drive -> BALANCE -> VOLUME/mute
Each channel's post-volume copy -> PROCESS -> common send stage -> S
```

Connecting a cable to 3 or 7 does not disconnect its signal from 10. Every channel and output is mono. The pair taps can be used as two separate signal paths, including a manually arranged dual-channel patch, without turning BALANCE into a stereo panner.

## Channel controls

### GAIN and the 0 / L / M / H range slider

GAIN controls the input amplifier before tonal shaping and VOLUME. The slider selects:

| Position | Behavior |
| --- | --- |
| 0 | Mute the channel's dry and PROCESS contributions; input-stage metering remains active |
| L | Input gain from unity to 2× |
| M | Input gain from unity to 5× |
| H | Input gain from unity to 100× |

The GAIN taper is progressive: unity plus `(range maximum − unity) × knob²`. Noon therefore gives 1.25× in L, 2× in M, and 25.75× in H. The wide H range is intended to drive the amplifier strongly; it is not a promise of 100× clean output voltage. Channel saturation rounds toward approximately ±10 V before BALANCE and VOLUME.

A muted channel continues monitoring its input stage using the last active gain range. If an instance initializes muted, that monitoring begins in LOW. The remembered range is runtime monitoring state, not an extra panel setting retained separately in patches. Mute transitions are smoothed, so a tiny settling interval is expected.

### BALANCE

BALANCE adjusts tone around a 900 Hz pivot. Left increasingly emphasizes low frequencies and reduces highs; right does the opposite. At the ends the opposing low/high gains approach ±6 dB. Noon is neutral, and the underlying filter maintains approximately unity magnitude at the pivot. This is tonal balance, not stereo position. It follows the channel's drive stage, so it shapes the already-characterized signal.

### VOLUME

VOLUME attenuates the post-BALANCE signal from silence to unity. It adds no boost. Use GAIN for drive and VOLUME to place that driven signal in the mix. Both the dry contribution and PROCESS feed follow this control.

### PROCESS

PROCESS sends a copy of the post-BALANCE, post-VOLUME channel to S, from zero to unity. It does not remove dry signal from the channel's normal submix. Four sends are summed without averaging, then passed through the gentle send-bus stage. Several strong sends can drive that bus more than one alone.

### Channel LEDs

Each LED measures the saturated input stage, before BALANCE and VOLUME. Thus a muted or turned-down channel can still show signal activity. The indicators use a short attack and longer release rather than flashing sample-by-sample. They are useful operating indicators, not calibrated VU/peak meters. BYPASS extinguishes them once on entry; active metering resumes after bypass.

## Connections

### Inputs I, II, III and IIII

The four mono channel inputs have approximately 16 Hz AC coupling before high gain. Unconnected inputs contribute silence. RM1010 is an audio mixer; it is not intended as a DC-accurate CV summing utility.

### Outputs 3 and 7

3 is the post-channel-stage sum of I and II. 7 is the equivalent sum of III and IIII. Both have gentle overload approaching ±12 V and low-frequency output coupling. Their jack connections do not change internal routing. The characterized submix signals also feed the final bus.

### Output 10

10 combines submixes 3 and 7, the effects return at R, and LINK. A separate gentle final amplifier stage provides additional interaction when these signals sum. There is no averaging or automatic gain compensation. The final stage approaches approximately ±12 V; use channel VOLUME or the external source's level to manage drive.

### S and R

S is the common effects-send output. Connect it to an external processor's input. R is a unity contribution to the final mix from the processor's output. R does not enter 3, 7 or S. An unpatched R contributes silence; it is not normalized to S. The loop is additive and has no automatic latency compensation. For external processors returning both dry and wet audio, set their mix appropriately to avoid unintended duplication of the dry signal.

### LINK

LINK is another unity contribution to 10 only. It can chain a mixer output into RM1010 or accept an additional source without channel controls. It does not feed the pair taps or PROCESS send. Unity describes its input contribution before the final bus's coupling and level-dependent saturation; a hot LINK signal can drive the final stage.

## Character, headroom and timing

The individual input stages have the strongest overload character. Pair, send and final stages use gentler rounded saturation. BALANCE can boost already-driven material, so a channel's downstream peak may exceed its nominal input-stage rail. Output coupling and FIR transients can also briefly exceed nominal stage limits; these are amplifier limits rather than hard final-output clamps.

The audio path uses the proven Colorbox-style 2× half-band filtering at an assumed 48 kHz host callback rate. It adds roughly nine host samples of FIR group delay. Manual controls are smoothed to reduce zipper noise. No artificial hum, random drift or crosstalk was added. The numerical-safety helper converts nonfinite inputs to silence and bounds finite inputs to ±1,000,000 V.

## System bypass

BYPASS is immediate and changes routing to:

- 3 = raw I + II.
- 7 = raw III + IIII.
- 10 = all four raw inputs + R + LINK.
- S = silence.

Channel ranges, mute, GAIN, VOLUME, BALANCE and PROCESS do not affect bypass routing. Processing histories freeze, and no latency compensation or crossfade is used. On resume, current controls are adopted and stale filter/envelope histories are cleared. This can produce an abrupt transition, as intended by the collection's bypass convention.

## Typed control values

GAIN tooltips show input gain in × units; typed edits use those units within the active range. BALANCE uses signed dB of tilt. VOLUME and PROCESS use percentage. Range sliders identify their selected behavior. Nonfinite numeric edits are ignored, and finite values are limited to the control's valid range.

## Suggested patches

**Two independent mixers:** Use I/II with 3 and III/IIII with 7. Leave 10 unused if a combined feed is unwanted.

**Quiet distorted layer:** Select H, drive GAIN, then lower VOLUME. Distortion is created before the attenuation.

**Parallel external coloration:** Send selected channels through PROCESS and S, return a wet-only effect at R, and listen to 10. Pair outputs remain independent of the return.

**Chained mixing:** Feed another mixer's output into LINK. It joins 10 without appearing at 3 or 7.

**Different treatment by channel:** Drive one input, leave another comparatively clean, then balance their tonal content and volumes before they sum.

## Release and references

The user authorized canonization after successful code review and behavior-preserving cleanup. Exported/embedded SDK builds, actual callback/core tests and baseline sample parity passed. A separate native-host test of the cleaned artifact is not claimed; native-host CPU and automation/undo profiling remain unmeasured.

Moffenmix informed gain-before-volume and amplifier overload; Alice 28-series was a desk-design reference. RM1010 follows its own confirmed topology and controls rather than claiming a component-accurate recreation. See [developer notes](DEVELOPER-NOTES.md) for equations, implementation choices, provenance and validation limits.
