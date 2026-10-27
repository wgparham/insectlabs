# swCXI Developer Notes - 1.0.0

The canonical Teletape Workshop pair is [swCXI.java](versions/1.0.0/swCXI.java) and [swCXI.vmod](versions/1.0.0/swCXI.vmod), with the matching swCXI hero panel. Package/class remain `com.insectlabs.swcxi.swcxi`; the legacy folder name is `sw111`. See the [user manual](versions/1.0.0/USER-MANUAL.md) and [release review](versions/1.0.0/REVIEW.md).

## Design and signal flow

Ten stage inputs feed Common Output through a convex combination of the mux gains. Common Input feeds a single row output through the exclusive fade gain. If both common jacks are unplugged, that row receives its matching stage input instead. If only Common Output is plugged, row outputs are silent. There is no clipping, coloration, noise, summing gain boost or oversampling. Unpatched and non-finite input values become 0 V.

`Initialize()` builds the stage-jack arrays and sets the stage lamp. The core constructor establishes defaults; initialization does not reset a restored selection. `ProcessSample()` runs `core.update()`, reads only the stage inputs for which `needsInput()` is true, calls `mix()`, and writes all eleven outputs every sample. `Notify()` handles host Reset. The lamp setter updates LEDs only when selection changes. `GetTooltipText()` describes SEL as a selected stage in manual mode or an active stage count when either address input is patched. Numeric knob values are unchanged, so native typed editing needs no inverse conversion.

## Coefficients and state

Voltage processing is fixed at 48 kHz. Mux fades last 48 samples. The exclusive path uses two 24-sample smoothstep ramps with the end of fade-out and start of fade-in sharing one sample, giving a 47-sample handoff and no all-zero handoff frame. The scalar smoothstep is `t*t*(3-2*t)`. Retargeting starts the mux fade from current weights; rapid address changes may briefly mix multiple previous inputs at Common Output. The row path remains exclusive even under retargeting.

Clock and reset use Schmitt thresholds of 3.0 V high and 1.8 V low. CV addressing has no hysteresis: wrap by 5 V using floor, divide the cycle into the knob-selected number of bins, and quantize with floor. Negative CV wraps backward. The knob spans 1-10, clockwise increasing, default 1; its numeric and text metadata now agree. These settings were explicitly confirmed during review.

With neither clock nor address CV, the knob chooses the stage. With either patched, the knob chooses the available count. Clock alone increments modulo that count. Clock plus address CV samples the CV only on a rising edge. Unclocked address CV changes selection immediately. A smaller active count clamps an out-of-range current selection immediately. Reset overrides selection and persists until the next valid selection event. State is one byte holding the zero-based selected stage; invalid stored bytes clamp to the available ten-stage range.

## Bypass and resumption

`ProcessBypassedSample()` reads only the selected stage input and Common Input, then calls direct `bypass()` and writes every output. It reads no knob, selection CV, clock CV or reset CV. Selection, mux weights, exclusive fade progress and trigger histories stay frozen; only output buffers and the resume marker change. The first active update snaps route gains to the direct bypass selection and primes current trigger levels without a synthetic clock edge. High reset is still respected. Live manual/CV control resumes through the documented short fade.

## Real-time constraints and validation

Audio callbacks allocate no objects and do no string formatting. Input-read reduction is retained from v0.1.2, while every output is written every sample. Voltage cables use two alternating sample slots; suppressing repeated writes produced alternate stale/missing samples in v0.1.1 and must not be reintroduced. Event-only LED writes in the audio callback are an intentional indicator exception; no native GUI timing or CPU benchmark is claimed.

Both source forms compile against the installed Voltage SDK with Java 17 target, all lint warnings treated as errors. The core suite passes 60,109 checks. The constructor-free SDK callback suite passes 1,151,109 assertions, including exact equality with the supplied v0.1.2 active callbacks over 50,000 varied samples, all-sample output writes, both cable slots, direct bypass, frozen histories, no bypass control reads, resume and mode-sensitive tooltips. Constructor-free test doubles replace native jack/knob/LED calls; this is not a fresh native Designer load or audition. The user's supplied winner and explicit cleanup/promotion authorization are the approval basis; the exact cleaned archive has not received a separate native-host check.

The public [SequentialSwitchCore](../../../switch-primitives/src/com/insectlabs/switches/SequentialSwitchCore.java) is the exact normalized core, with package/class wrapper changes only. Release validation checks this parity. The exact supplied pair and its development history remain in research; obsolete working copies are removed after archive validation.

## Maintenance checklist

Repeat routing and metadata checks after changing jack direction, common-port normalization, connection-sensitive modes, knob range/default/direction, CV binning or wrap rules. Re-run callback parity and cable-slot checks after optimizing input reads or output writes. Re-run clock, reset, state and bypass/resume checks after changing lifecycle or persistence code. Validate fade duration and exclusivity after changing ramp coefficients or retargeting. Preserve control UUIDs, panel geometry, artwork and package/class identity. Synchronize both source forms, primitive, documentation and hashes after any approved edit.
