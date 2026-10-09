# Model 62 Wire Player Recorder — User Manual

**Version 1.0.0 · Wardenclyffe Station · Insect Laboratories**

Model 62 is a mono wire recorder/player for Voltage Modular. It treats a short virtual wire as a physical medium: the machine has a motor, transport inertia, separate record and playback heads, speed-dependent pitch, gradual loss, accumulated recording noise, wow/flutter, and persistent splice marks. Use it as a recorder, varispeed loop, overdubber, or imperfect short-delay instrument.

## Panel controls and connections

| Control / jack | Function |
| --- | --- |
| **POWER** switch and lamp | Turns the machine on or off. Startup is stopped. Power-off coasts down and preserves the wire recording. The lamp indicates power. |
| **SPOOL** | Selects 1, 1.618, 4, 7, 16, or 48 seconds at normal speed. Selecting a spool installs a fresh wire and erases the current recording and splice history. Changing spools while the transport is moving also breaks the new wire; press SPLICE to repair it. |
| **PLAY** button / **PLAY STOP GATE** | Toggles transport. The gate responds to a rising edge; it must fall low before another edge can toggle. PLAY and REC buttons illuminate while active. |
| **REC** button / **RECORD GATE** | Arms recording. Recording occurs only while powered PLAY is running. The gate enables recording while high; the REC button latches the manual arm. |
| **L / E** switch | Selects **Locked** or **Elastic** recording. Locked writes forward at 1× regardless of playback speed or direction. Elastic writes along the moving playback head and follows its direction and speed; it stops writing at zero speed. |
| **SPEED** knob | Sets varispeed from reverse through stop to forward. Normal range is −2× to +2×; the **×2 / ×4** range switch selects the wider ±4× range. Startup defaults to +1× in normal range. |
| **VARISPEED CV** | Additive control for transport speed. A ±5 V signal spans the selected speed range. Total speed is limited to the selected range. |
| **GAIN** | Sets the recording and MONITOR input gain, approximately 50–250%, with unity at noon. |
| **MEMORY** knob / **MEMORY CV** | Controls the fraction of previous wire content retained at each written location. The knob spans 0–100%; a patched 0–5 V CV replaces the knob. Lower values erase old material faster during overdubbing. |
| **AMPLITUDE** | Sets playback output level from silence to 200%; default is 100%. |
| **SIGNAL IN** | Mono recording input. The signal is gain-staged, then sent to the MONITOR output and recording path. |
| **SIGNAL OUT** | Playback from the virtual wire, including the recorder’s medium and transport character. |
| **MONITOR** | Live input after GAIN, before wire coloration. It is independent of PLAY and REC and is useful for checking the incoming signal. |
| **BREAK ALARM** | Held +5 V while the wire is broken and the machine is powered. It returns to 0 V on repair, power-off, or system BYPASS. |
| **SPLICE** button | Manually repairs a broken wire, leaves transport stopped, flashes briefly, and creates a persistent splice scar. It does not erase the recording. |
| **Magic eye and spool display** | The eye follows input level while recording is armed by REC or RECORD GATE and SIGNAL IN is connected; otherwise it follows playback. The spool display shows transport state and accumulated repairs. |

## Basic operation

1. Select a spool, patch SIGNAL IN and SIGNAL OUT, then turn POWER on. The machine starts stopped.
2. Press PLAY. Set SPEED and the range switch for the desired direction and pitch.
3. Press REC to arm the recorder, or use RECORD GATE. Incoming audio is written only while PLAY runs. Press REC again to disarm.
4. Use LOCKED mode when recording should move forward at a constant 1× rate while playback is independently varispeeded. Use ELASTIC mode when the record head should follow the playback transport.
5. For overdubbing, raise MEMORY to preserve old material. Lower it to replace old material more quickly. The wire’s cumulative frequency loss and saturation remain even at full retention.
6. If tension breaks the wire, transport stops and BREAK ALARM rises. Press SPLICE to repair it; the repaired wire retains its existing audio plus a small audible knot. Press PLAY to restart.

## Recording, transport, and medium behavior

The virtual wire is mono, sampled internally at 24 kHz and stored as signed 16-bit audio. The Voltage Modular adapter processes at the collection’s fixed 48 kHz rate. Spool durations are specified at 1×; varispeed changes their playback duration and pitch. The fixed virtual head spacing is 33 mm, giving about 54.1 ms between heads at 1×, 108.3 ms at 0.5×, and 27.1 ms at 2×. These are creative head-layout choices rather than claims about ordinary wire recorders.

In LOCKED mode, the writer advances forward at 1× while the playback head may move independently. In ELASTIC mode, writing follows signed playback motion; reversed playback reverses head order, so new material may take nearly a loop to reach the playback head. A stopped ELASTIC transport does not repeatedly write into one sample. Every crossed wire sample is written, including during fast and reverse motion.

The motor accelerates and coasts rather than changing speed instantaneously. Abrupt speed demand can exceed the tension limit and snap the wire. Sustained high speed is allowed. New spool selection always clears the recording and repairs. A tension break or manual splice does not clear audio. Multiple splice marks persist through overdubbing, patch save/recall, and power cycles; overlapping marks deepen within a bounded local dropout.

Recording adds a very quiet noise component at the record head, so it becomes part of the medium and can accumulate through repeated overdubs. Playback has no separate noise bed. The recording path gently darkens and compresses the signal, while MONITOR remains pre-medium. Playback includes a 55 Hz high-pass, a 6.2 kHz two-pole low-pass, speed-aware band-limited resampling, and small speed-related wow/flutter.

## Save, recall, bypass, and power

Patch data includes the wire audio, selected spool, read/write positions, repair count and locations, and a broken-wire fault. Recall stops and disarms the transport; the saved medium is preserved. Controls and power return according to the host patch. Corrupt custom state is rejected and logged rather than silently replacing the current medium.

Physical POWER is a controlled stop: it disarms recording, coasts down, and keeps the wire. Host **BYPASS** is the collection-wide abrupt path: it sends the input directly to SIGNAL OUT and MONITOR, drops BREAK ALARM, and freezes the recorder’s processing and histories. BYPASS can click or thump; it is not the same as powering down the instrument.

## Suggested patches

- **Short slap:** use the 1 or 1.618 second spool, record a phrase, then stop recording and let the playback head run.
- **Long evolving loop:** choose 16 or 48 seconds, use ELASTIC mode, and overdub with partial MEMORY retention.
- **Reverse texture:** record in LOCKED mode, then reverse playback; switch to ELASTIC to hear how the writing direction changes.
- **Mechanical gesture:** change speed quickly or change spool length while moving, then repair with SPLICE and restart.
- **Input check:** monitor the post-GAIN signal without PLAY or REC. This MONITOR path is not the playback output.

For implementation, test coverage, and release details, see [Developer Notes](DEVELOPER-NOTES.md) and [Release Review](REVIEW.md).
