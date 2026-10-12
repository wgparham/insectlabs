# Model 62 Wire Player Recorder â€” User Manual

**Version 2.0.0 Â· Wardenclyffe Station Â· Insect Laboratories**

Model 62 is a mono wire recorder/player for Voltage Modular. It treats a short virtual wire as a physical medium: the machine has a motor, transport inertia, separate record and playback heads, speed-dependent pitch, gradual loss, accumulated recording noise, wow/flutter, and persistent splice marks. Use it as a recorder, varispeed loop, overdubber, or imperfect short-delay instrument.

## Panel controls and connections

| Control / jack | Function |
| --- | --- |
| **POWER** switch and lamp | Turns the machine on or off. Startup is stopped. Power-off coasts down and preserves the wire recording. The lamp indicates power. |
| **SPOOL** | Selects 1, 1.618, 4, 7, 16, or 48 seconds at normal speed. Selecting a spool installs a fresh wire and erases the current recording and splice history. Changing spools while the transport is moving also breaks the new wire; press SPLICE to repair it. |
| **PLAY** button / **PLAY STOP GATE** | Toggles transport. The gate responds to a rising edge; it must fall low before another edge can toggle. PLAY stays illuminated during playback and motor coast; REC stays illuminated while armed or recording. |
| **REC** button / **RECORD GATE** | Arms recording. Recording occurs only while powered PLAY is running. The gate enables recording while high; the REC button latches the manual arm. |
| **L / E** switch | Selects **Locked** or **Elastic** recording. Locked writes forward at 1Ã— regardless of playback speed or direction. Elastic writes along the moving playback head and follows its direction and speed; it stops writing at zero speed. |
| **SPEED** knob | Sets varispeed from reverse through stop to forward. Normal range is âˆ’2Ã— to +2Ã—; the **Ã—2 / Ã—4** range switch selects the wider Â±4Ã— range. Startup defaults to +1Ã— in normal range. |
| **VARISPEED CV** | Additive control for transport speed. A Â±5 V signal spans the selected speed range. Total speed is limited to the selected range. |
| **GAIN** | Sets the recording and MONITOR input gain, approximately 50â€“250%, with unity at noon. |
| **MEMORY** knob / **MEMORY CV** | Controls the fraction of previous wire content retained at each written location. The knob spans 0â€“100%; a patched 0â€“5 V CV replaces the knob. Lower values erase old material faster during overdubbing. |
| **AMPLITUDE** | Sets playback output level from silence to 200%; default is 100%. |
| **SIGNAL IN** | Mono recording input. The signal is gain-staged, then sent to the MONITOR output and recording path. |
| **SIGNAL OUT** | Playback from the virtual wire, including the recorderâ€™s medium and transport character. |
| **MONITOR** | Live input after GAIN, before wire coloration. It is independent of PLAY and REC and is useful for checking the incoming signal. |
| **BREAK ALARM** | Held +5 V while the wire is broken and the machine is powered. It returns to 0 V on repair, power-off, or system BYPASS. |
| **3rd / High Fidelity Mode** button | ON (default) keeps the current cleaner voicing. OFF selects War-Worn mode: stronger speed-linked wow/flutter, a little playback hiss, and additional drive/saturation. New recordings acquire the extra noise and drive; this wear stays on the wire when returning to High Fidelity. The mode also changes playback of existing recordings. |
| **SPLICE** button | Manually repairs a broken wire, leaves transport stopped, flashes briefly, and creates a persistent splice scar. It does not erase the recording. |
| **Magic eye and spool display** | The eye follows input level while recording is armed by REC or RECORD GATE and SIGNAL IN is connected; otherwise it follows playback. The shaded green magic eye follows the existing level signal. The spool display shows transport state, accumulated repairs, current loop position and total loop duration at varispeed. |

## Basic operation

1. Select a spool, patch SIGNAL IN and SIGNAL OUT, then turn POWER on. The machine starts stopped.
2. Press PLAY. Set SPEED and the range switch for the desired direction and pitch.
3. Press REC to arm the recorder, or use RECORD GATE. Incoming audio is written only while PLAY runs. Press REC again to disarm.
4. Use LOCKED mode when recording should move forward at a constant 1Ã— rate while playback is independently varispeeded. Use ELASTIC mode when the record head should follow the playback transport.
5. For overdubbing, raise MEMORY to preserve old material. Lower it to replace old material more quickly. The wireâ€™s cumulative frequency loss and saturation remain even at full retention.
6. If tension breaks the wire, transport stops and BREAK ALARM rises. Press SPLICE to repair it; the repaired wire retains its existing audio plus a small audible knot. Press PLAY to restart.

## Recording, transport, and medium behavior

The virtual wire is mono, sampled internally at 24 kHz and stored as signed 16-bit audio. The Voltage Modular adapter processes at the collectionâ€™s fixed 48 kHz rate. Spool durations are specified at 1Ã—; varispeed changes their playback duration and pitch. The fixed virtual head spacing is 33 mm, giving about 54.1 ms between heads at 1Ã—, 108.3 ms at 0.5Ã—, and 27.1 ms at 2Ã—. These are creative head-layout choices rather than claims about ordinary wire recorders.

In LOCKED mode, the writer advances forward at 1Ã— while the playback head may move independently. In ELASTIC mode, writing follows signed playback motion; reversed playback reverses head order, so new material may take nearly a loop to reach the playback head. A stopped ELASTIC transport does not repeatedly write into one sample. Every crossed wire sample is written, including during fast and reverse motion.

The motor accelerates and coasts rather than changing speed instantaneously. Abrupt speed demand can exceed the tension limit and snap the wire. Sustained high speed is allowed. New spool selection always clears the recording and repairs. A tension break or manual splice does not clear audio. Multiple splice marks persist through overdubbing, patch save/recall, and power cycles; overlapping marks deepen within a bounded local dropout.

Both modes record a very quiet noise component at the record head, so it becomes part of the medium and can accumulate through repeated overdubs. War-Worn mode increases noise and drive on new recordings and adds subtle playback hiss, stronger speed-linked wow/flutter, and saturation to any spool. The recording path gently darkens and compresses the signal, while MONITOR remains pre-medium. Playback includes a 55 Hz high-pass, a 6.2 kHz two-pole low-pass, and speed-aware band-limited resampling.

## Save, recall, bypass, and power

Patch data includes the wire audio, selected spool, read/write positions, repair count and locations, and a broken-wire fault. The High Fidelity / War-Worn button state is saved with the patch. Recall stops and disarms the transport; the saved medium is preserved. Controls and power return according to the host patch. Corrupt custom state is rejected and logged rather than silently replacing the current medium.

Physical POWER is a controlled stop: it disarms recording, coasts down, and keeps the wire. Host **BYPASS** is the collection-wide abrupt path: it sends the input directly to SIGNAL OUT and MONITOR, drops BREAK ALARM, and freezes the recorderâ€™s processing and histories. BYPASS can click or thump; it is not the same as powering down the instrument.

## Suggested patches

- **Short slap:** use the 1 or 1.618 second spool, record a phrase, then stop recording and let the playback head run.
- **Long evolving loop:** choose 16 or 48 seconds, use ELASTIC mode, and overdub with partial MEMORY retention.
- **Reverse texture:** record in LOCKED mode, then reverse playback; switch to ELASTIC to hear how the writing direction changes.
- **Mechanical gesture:** change speed quickly or change spool length while moving, then repair with SPLICE and restart.
- **Input check:** monitor the post-GAIN signal without PLAY or REC. This MONITOR path is not the playback output.

For implementation, test coverage, and release details, see [Developer Notes](DEVELOPER-NOTES.md) and [Release Review](REVIEW.md).

## 2.0.0 transport display

The updated canvas shows the supply and take-up reels, fixed head/guides, loose wire ends when broken, transport state and actual speed, playback position and total splices. REC ARMED appears when recording is armed but the transport is stopped. The animation follows transport direction and coasting; bypass freezes transport. The timing readout uses the smoothed transport speed while moving, including speed CV; while stopped it uses the selected SPEED knob rate to preview the resulting duration. It reports the physical wire position as time in the loop, so that value moves backward during reverse playback. At zero speed, total duration is shown as infinite and the time position is blank. Times under an hour use minutes, seconds and tenths; longer times use hours, minutes and seconds. The reel artwork represents the cyclic wire medium and does not indicate physical spool fullness. DSP, controls, saved state and instrument operation are unchanged.
