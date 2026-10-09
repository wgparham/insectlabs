# InsectLabs reverb primitives

Reusable Java audio DSP extracted from the LM-21 Mk III ReverbSC prototype. This is an SDK-independent code project, not a finished Voltage Modular module. It provides one mono eight-line scattering reverb and inexpensive control-rate parameter conversions.

- [ReverbScMono](src/com/insectlabs/dsp/reverb/ReverbScMono.java): allocation-free sample processing, configurable 8–192 kHz construction, deterministic reset.
- [ReverbParameters](src/com/insectlabs/dsp/reverb/ReverbParameters.java): nominal RT60 feedback and cutoff/damping conversions.
- [Developer notes](DEVELOPER-NOTES.md) and [repeatable tests](tests/validate.py).
- [Original prototype](../voltage-modular/Wardenclyffe%20Station/lm-21/versions/1.0.1/references/lm-21_mk3_reverbsc_prototype.java.txt).

```java
ReverbScMono reverb = new ReverbScMono(48000.0);
double feedback = ReverbParameters.feedbackForRt60(3.0);
double damping = ReverbParameters.dampingForCutoff(10500.0, 48000.0);
// In the audio callback, add or mix this return as appropriate:
double wet = reverb.process(input, feedback, damping, 0.40, 0.0);
```

Compute parameters outside the audio loop; smooth changing parameters in the caller. Instantiate an independent engine for each audio path. Calling reset clears the tail immediately and belongs at an intentional transition, not every sample. The feedback formula is a nominal decay mapping; damping, modulation and nonlinear aging change the effective decay.

Run `python tests/validate.py` with Java 17 available. The validator compares the extracted engine sample-for-sample with the archived original at 48 kHz, then checks reset, engine independence, parameter conversions and operation at 44.1, 48 and 96 kHz.

The source lineage follows the Sean Costello/Csound ReverbSC family through the personal-use LM-21 prototype. This extraction retains that lineage; it does not independently establish upstream licensing rights. The repository license covers InsectLabs' own contributions.
