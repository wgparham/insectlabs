# FMC design research

FMC combines four familiar pedal-inspired modulation and delay voices in four parallel lanes. The circuit documents provide design cues; the DSP is an original hybrid instrument shaped for Voltage Modular workflow, not an exact reproduction.

## Evidence and modeling choices

- **BF-2-style flanger:** The supplied schematic and board image informed a short modulated delay with feedback and a manual delay control. Rate covers 0.0625–10 Hz, corresponding to periods from 16 seconds to 100 milliseconds. Depth spans the delay sweep, C sets a 1–13 ms center delay, and D controls feedback/resonance. Feedback filtering and the selected ceiling are modeled choices.
- **TR-2-style tremolo:** The supplied schematic informed the amplitude-modulation voice. A is a macro that raises rate and depth together; B interpolates triangle, sine and square wave shapes; C sets a depth ceiling; D sets the rate span. The combined macro is a workflow choice rather than a control mapping claimed to match the pedal.
- **DM-2-style analog delay:** The circuit and the Aion FX Amethyst build documentation support a BBD-derived model with clock-linked bandwidth loss. FMC maps A to 20 ms–2.4 s, B to feedback/intensity, C to a dry/wet law and D to optional modeled compander/hiss. The 2.4-second maximum extends beyond the familiar DM-2 range; increased effective stage count preserves the approximate bandwidth of a 300 ms delay. This extension and the exact filter/compander dynamics are model choices.
- **DD-2-style digital delay:** The schematic and pedal images inform the digital-delay voice. A maps 12.5 ms–6.4 s, B controls feedback, C controls dry/wet, and D lowers the modeled word length from 16 toward 8 bits. The extended memory and continuously variable sample rate are software interpretations, not claims about a stock unit's exact memory organization.

The Aion document describes an MN3005 BBD, NE570/NE571 compander and MN3101 clock in its project. Those parts are evidence for that recreation and source design, not proof of the exact production revision represented by every reference image. The FMC DM-2 model uses those as inspiration and labels guessed constants as guesses in source comments.

## Routing and performance behavior

- Four channels run independently. Their default voices, top to bottom, are BF-2, TR-2, DM-2 and DD-2; each four-position selector can choose any voice.
- The A–D rows are shared physical controls whose meaning changes with the selected voice. Tooltips show the active units and typed-value ranges.
- Each lane has its own input and output, Hold control and rate CV input. ±5 V represents ±1 normalized CV. Rate CV affects BF-2 and TR-2 rates; it does not retune the two delay voices.
- Hold is active for the DM-2 and DD-2 voices: it mutes new input and attempts to sustain repeats. The flanger and tremolo ignore Hold.
- MIX is one half of the sum of all four lane outputs. Host bypass passes each input directly to its matching output and maintains the same half-sum law at MIX.
- The callback uses preallocated delay and modulation buffers. The four channels allocate roughly 16.3 MiB of double-buffer storage at construction, plus small state and arrays. The core is currently constructed at 48 kHz; other host sample rates have not been functionally verified.
- The archived headless microbenchmark on JDK 27 measured 1.13–1.55 microseconds per frame across four runs for four active voices on the development machine. This is not a target-host CPU percentage or a real-time guarantee.

## Confidence

Hardware topology and pedal control names are reference-backed. Exact voice transfer functions, modeled circuit nonidealities, extension of the original delay ranges, feedback/hold compensation, noise level, CV scaling and several control laws are implementation choices or estimates. FMC should be described as pedal-inspired, not as four exact pedal clones.
