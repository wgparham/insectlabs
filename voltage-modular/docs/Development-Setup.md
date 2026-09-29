# Development setup and design decisions

Collection-wide bypass, source-pair, DSP, and validation conventions are recorded in [Module Infrastructure Standards](Module-Infrastructure-Standards.md).

Checked 28 September 2026.

## Repository and workspace

- Canonical remote: https://github.com/wgparham/insectlabs
- Local checkout: `C:/Users/wgparham/Dropbox/git/insectlabs`.
- Colorbox: voltage-modular/colorbox, retaining its immutable version archives.
- Collection roadmap and utility drafts: voltage-modular/docs.
- Series One working directory: voltage-modular/laboratory (working name, not a final product title).
- Supplied manuals: workspace Resources, outside the Git checkout. References to Resources in design notes refer to this workspace directory. Resources/README.md indexes local manuals and official development links.
- Old workspace document paths contain navigation links rather than duplicate drafts.

Design documents are versioned with the Laboratory collection. Signal Processor 1.0.0 is the first approved release. Keep SDK binaries and third-party manual files external to the source repository; record their locations and sources here.

## Available tools and baseline checks

- Installed SDK: C:/ProgramData/Voltage/voltage.jar.
- Installed Java/Javac: JDK 27. Use the existing Colorbox Java 17 target for baseline builds.
- Bundled Python: C:/Users/wgparham/.cache/codex-runtimes/codex-primary-runtime/dependencies/python/python.exe.
- Designer projects and matching Java exports are available for RGB 4.0.3, CMYK 1.0.3, and HSB 1.0.2.

Ran colorbox/tools/validate.py with the installed SDK. All structural, embedded/exported-source compilation, and simulated regression checks passed. RGB matched 1,152,000 output samples; CMYK 1,536,000; HSB 768,000. This confirms the baseline toolchain and harness, not a new host audition or CPU benchmark. The first sandboxed compilation failed to write a class file; the approved unrestricted rerun passed.

Follow Colorbox STANDARDS.md for matching project/source pairs, Designer user regions, no allocation/UI work in audio callbacks, direct bypass, and release validation. Reuse proven infrastructure without treating Colorbox's individual effect transfer functions as universal circuit models.

## Series One philosophy: confirmed

Mono-only signal paths. Dual mono/stereo use requires duplicate stages. Manual controls dominate; essential modulation signals remain part of such instruments as the Balanced Modulator. CV becomes common in the radiophonic series. WWI/WWII-era equipment and its reuse in early studios guide the design, without requiring literal historical replicas.

Fader|Distr A and Fader|Distr B are separate modules, with matching surfaces and no CV or law switch. Both retain simultaneous FADER and DISTR functions under one BIAS control. Processor offset CV is excluded. Gain CV/VCA capability remains the identified exception under consideration.

Minimize CPU. Direct bypass performs only required routing and state bookkeeping, skipping DSP and processing-control reads. Start with Colorbox's 2x oversampling where necessary; native-rate linear utilities need none solely because they belong to this series. Actual CPU cost must be measured in the host.

## Modulation reference: +5 V working default; +10 V deferred

User decision: retain +5 V for now and consider +10 V only on individual CV inputs that warrant it. The following discussion is retained as background, not an active implementation requirement.

Possible interpretation if revisited: +10 V is the full-scale reference for explicitly marked continuous modulation inputs in Series One. Return to +5 V for Series Two. Do not automatically double nominal audio output, pitch scaling, gate thresholds, or every offset range.

Benefits: a deliberate operating convention, more attenuation margin for hot control sources, and a meaningful role for the Signal Processor as an inter-series level adapter.

Costs: at equal depth settings a +5 V signal produces half the change; 10 V sources can overdrive or clamp 5 V destinations. Mixed-series patches need scaling. This does not improve floating-point precision or inherently produce tube/transformer warmth.

For proposed VCA gain g = amount * CV / R (with manual gain zero), R=10 V and amount=1 gives unity at 10 V and 0.5 gain at 5 V, approximately -6.02 dB. Amount=2 restores unity at 5 V. Conversely scale 0-10 V down by 0.5 for a 0-5 V destination. Bipolar +/-10 V is a 20 V peak-to-peak signal; it is distinct from both 0-10 V CV and +/-5 V audio.

Recommendation: keep audio nominally compatible with Voltage Modular and express the heavy character through modeled gain stages, saturation, bandwidth, and input sensitivity. Treat a 10 V modulation standard as an intentional interface choice, not a sound-quality upgrade. Preserve ordinary pitch scaling where pitch tracking is offered and specify trigger thresholds independently.

## Official development references

- [Theory of operation and nominal signal levels](https://docs.cherryaudio.com/voltage-module-designer/theory-of-operation)
- [Callbacks and module variables](https://docs.cherryaudio.com/voltage-module-designer/module-functions-and-variables)
- [Audio jack API](https://docs.cherryaudio.com/voltage-module-designer/audio-input-and-output-jacks)

The theory page documents nominal +/-5 V audio and warns that other modules may clip larger signals. It also contains older Java 8 wording; the local Colorbox build has been verified with its Java 17 target against the installed SDK. Build success does not replace Designer/host validation.
