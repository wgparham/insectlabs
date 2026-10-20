# CXL development and maintenance

Status: **1.0.0 is canonized; publication is deferred**. The development pair mirrors the archive. New DSP or behavior changes require a new candidate version. Labels and Designer notes were cleaned while preserving UUIDs, panel geometry, artwork and test state. See [build record](BUILD-CHECK.json) and the canonical [release review](../versions/1.0.0/REVIEW.md).

Headless DSP checks cover delay timing, pre-delay endpoints, all-pass energy, converter grain, tank stability, a tonal decay sweep, a high-rate exciter comparison and a rough CPU benchmark. The user approved the 8.5-bit audition and CXL behavior in Designer on 2026-10-18. Test results and limits are recorded in [DSP test report](tests/DSP-TEST-REPORT.md) and [machine-readable results](tests/DSP-RESULTS.json); those focused tests do not replace native listening.

## Files and starting point

- `cxl.vmod` is the integrated Designer project; `cxl.java` is its matched export.
- The panel baseline is preserved under [research/panel-baseline-2026-10-17](../research/panel-baseline-2026-10-17/).
- The pre-cleanup approved audition pair and earlier 8-bit/9-bit comparisons are preserved under [backups](backups/).
- Historical evidence, source PDFs and explicit software departures are described in [CXL research](../RESEARCH.md) and [references](../references/).
- The module uses no external Java library or new primitive dependency.

For an initial listen, patch either input and use the left output for mono. Turn MIX fully clockwise on a send. GAIN and AMP are unity at noon. DECAY at noon targets two seconds in 12-bit mode and about 9.49 seconds in 8-bit mode. Pre-delay at noon is about 14.84 ms. The dry path remains full bandwidth.

## Controls

All six knobs retain their supplied normalized 0-1 ranges and noon defaults. Converted tooltip values support typed entry in the units below.

| Control | Mapping / entry |
| --- | --- |
| PRE-DELAY | 0-250 ms; `251^position - 1` milliseconds. True zero at CCW. Enter ms. Delay changes glide the read head and can pitch-shift newly entering audio. |
| DAMP | 0-100%; CW reduces the treble decay relative to the nominal midband duration. Enter percent. |
| DECAY | Logarithmic 0.8-5 s in 12-bit mode, 3-30 s in the softened 8-bit-character mode. The extended mode uses an 8.5-bit effective converter step as the current audition setting. Enter seconds. The overlap is 3-5 s. |
| MIX | Linear 0-100% wet. Enter percent. No constant-power boost at center. |
| GAIN | `4 * position^2`, 0-4x, unity at noon. Enter gain multiplier. Drives the wet excitation stage only. |
| AMP | `2 * position`, 0-2x, unity at noon. Enter gain multiplier. Scales the final dry/wet result. |
| Character switch | UP/state 1: 12-bit plate. DOWN/state 0: 8-bit-flavored extended plate with a softened 8.5-bit effective step. Supplied initial position remains UP. User audition accepted this direction. |

With the DECAY knob left in the same position, switching modes usually changes duration substantially. To compare color alone, type the same 3-5 s value into DECAY after selecting each mode. Modes share the same tank; changing mode preserves the tail and smoothly changes losses/converter grain over approximately 25 ms.

## Routing

- Either input alone feeds both excitation lanes at the same nominal level. Right-only input works as mono too.
- Both inputs connected retain separate stereo excitations. They are not first averaged into a single driver signal. Both excitations enter **one cross-coupled tank**, not two independent reverbs.
- Both outputs connected: left and right pickup signals, mixed with their corresponding dry inputs.
- Left output alone: arithmetic average of the two complete mixed outputs. Averaging preserves unity for a common mono dry signal. A decorrelated wet field can be lower in RMS than either individual pickup; no hidden loudness normalization is applied.
- Right output alone: right pickup and its corresponding dry signal, as requested.

## DSP design

The tank starts from the published Dattorro Fig. 1 family: four input all-passes per excitation lane, two modulated early tank all-passes, two fixed late tank all-passes, four long delays and a distributed signed seven-tap output for each pickup. Reference lengths and tap ages scale from 29761 Hz to the native 48000 Hz callback. Capacity and nominal delay are separate, including room for modulation/interpolation. Initial diffuser coefficients are 0.75/0.625, early tank coefficients -0.7 and late coefficients 0.5. Fixed late diffusion is a prototype voicing choice.

This is an original implementation of a published topology with deliberate CXL departures, not copied Lexicon firmware. Separate stereo input diffusion, the loss model, pickup gain and electronics are CXL choices. Output tap coefficient is 0.45, versus the paper's 0.6. Frequency-dependent losses are concentrated at the two tank damping stages rather than distributed among four separate scalar decay multipliers. Left/right feedback samples are captured from the same frame before either branch updates, avoiding branch-order dependence.

Each tank arm applies a passive low/high shelf. The low-frequency gain, high-frequency gain and shelf pole are calculated from the nominal duration, bass persistence and DAMP setting. The pole is solved so the shelf magnitude at 500 Hz matches the nominal loss for that reference frequency. The nominal complete loop duration uses the sum of the reference delay lengths; all-pass group delay, modes, interpolation and output voicing mean an actual measured band decay may differ. This is not a claim that a single midband formula reproduces a physical plate's entire spectrum.

Bass decay rises from 1.05 to 1.9 times the nominal duration as DECAY opens through the normal plate range, remaining at that ratio in longer settings. Therefore bass can outlast the displayed maximum. DAMP maps the high-frequency decay target from 55% down to 8% of the nominal duration. This deliberately dark first voicing is adjustable after listening.

Tank modulation is approximately +/-8 samples at 0.23 Hz in quadrature. It modulates the two early all-pass delays around their full nominal lengths, not around zero. Linear interpolation adds some frequency-dependent loss; its audible effect and measured decay remain to be evaluated.

### Excitation, pickup and grain

Wet input is referenced to 5 V audio, with 45 Hz rumble removal, a modest presence emphasis, approximately 9.5 kHz input filtering, an envelope-driven compression stage (2 ms attack / 80 ms release) and soft driver headroom. These numerical settings are proposed voicing, not measured EMT calibration. Pickup tone adds a modest low shelf near 140 Hz and a final low-pass near 6.8 kHz. No noise or hum is synthesized to imply physical age.

The converters use instantaneous binary gain ranges and TPDF dither. The UP mode uses a nominal 12-bit signed mantissa; the DOWN mode retains its 8-bit character with a softened 8.5-bit effective step. Dither follows the current signal's scale and is exactly zero for silence. There is no fixed hiss source or fixed quiet-tail quantization floor. Mode transitions interpolate quantizer step size. The tank itself remains floating point; the wet excitation and pickups receive the conversion model. This is a signal-following grain model inspired by gain-stepping converters, not a verified reconstruction of the Lexicon 224's ranges, clipping, analog reconstruction or finite arithmetic. Neither mode is a fixed-full-scale bitcrusher.

Both modes run at 48 kHz. The tank and driver are not oversampled. A headless sample-rate comparison of the nonlinear driver shows increasing differences near Nyquist, but the comparison includes filter/envelope discretization changes and does not isolate alias energy. Intentional converter grain and unwanted driver aliases should be evaluated separately before choosing any local oversampling revision; see the test report for results and limitations.

The first 8-bit audition was noisier than useful on exposed oscillator tones; the later 9-bit audition was judged too clean. The current test spin uses a 180-level divisor, the geometric midpoint between 127 and 255, for an approximately 8.5-bit effective step. This returns some of the 8-bit grain while retaining more tonal clarity than full 8-bit. Converter-only measurements and their limitations are in the DSP test report. The extended-time voice keeps its original range and panel position. Re-audition sustained tones and short decays in Designer.

### Headless test findings

Integer and fractional delay behavior passed, including a true zero and the 250 ms maximum pre-delay. The fixed all-pass preserved impulse energy, and the tank stayed finite at the tested duration/damping endpoints. The raw 500 Hz tank decay measured 0.467 s for a configured 0.8 s, 3.055 s for 3 s, 4.695 s for 5 s, and 35.078 s for 30 s at DAMP 50%. The short endpoint is notably faster than its nominal target and should be judged during listening. Local Java `PlateTank.process` timing was 326-457 ns/sample, roughly 1.7-2.2% of one CPU core at 48 kHz before host overhead. These are prototype measurements, not host guarantees or certified full-band reverberation times.

## Runtime and state

- Audio callbacks allocate no arrays or objects. Buffers are constructed once. Control polling and coefficient calculations run once per 64 samples; continuous gain, mix, pre-delay and loss changes are smoothed.
- Inputs become zero when unpatched or nonfinite. Extreme finite inputs are bounded to +/-1,000,000 V before nonlinear arithmetic. Ordinary modular levels are not clipped by this numerical guard.
- Reset and preset/variation recall atomically request an audio-thread clear of transient histories. Controls are host-persisted; tails are not serialized. Initialization/recall adopts controls immediately and fades the wet entry over 5 ms.
- Host bypass directly applies the input/output normalization above, without reading controls or advancing any DSP, smoothing or random histories. It ignores MIX/GAIN/AMP. Returning from bypass preserves the frozen tank, adopts current controls and fades its wet contribution in over 5 ms. A retained pre-bypass tail can therefore return.
- Mode changes preserve the tank, filter and delay histories. They do not reset the modulation oscillator.
- Nonfinite wet output requests immediate history recovery. Tiny delay/filter histories are flushed below 1e-20 to avoid denormal buildup; that threshold is far below the intended audible decay.

## Future maintenance checks

When changing signal flow, decay/damping, converter scaling, routing or state handling, rerun the archived test harness and native Designer checks. Areas not exhaustively established by the current harness include standardized bandwise decay, mono/antiphase fold-down, overload recovery, automation/preset migration, native host CPU and isolated alias energy. Keep those limits distinct from the user's approval of the current sound and behavior.
