# XL-35c Low Pass Filters — User Manual

**Wardenclyffe Station · Canonical version 1.0.0**

XL-35c is a pair of independent, manually stepped one-pole low pass filters. Each channel has its own frequency selector, input, and output. The upper channel has gentle warmth; the lower channel is the cleaner path.

## Signal paths and controls

Connect a mono signal to either input and take the result from its matching output. The channels do not mix, normalize, or cross-connect. An unpatched input contributes zero. Both knobs select one of eight fixed cutoff points. Panel values are marked in cycles per second (CPS), equivalent to hertz. Each cutoff is nominally the -3 dB point, with an approximately 6 dB-per-octave one-pole slope. Step changes take effect immediately; a small transient can occur while audio is passing.

The upper signal passes through a restrained saturating stage after filtering. It gently rounds stronger signals. The lower signal has no added character stage. There is no resonance, gain control, CV input, or physical power switch.

## Frequency banks

### Upper selector

| Position | Cutoff | Reference cue |
|---:|---:|---|
| 1 | 5.3 Hz | infrasonic / heavy CV slew |
| 2 | 41 Hz | deep drone fundamental |
| 3 | 159 Hz | vintage boxy lo-fi region |
| 4 | 800 Hz | lower vocal formant |
| 5 | 1591 Hz | gritty mid-range bite |
| 6 | 3000 Hz | presence / ringing clank |
| 7 | 5300 Hz | harmonic taming |
| 8 | 16000 Hz | air / spectral dust |

### Lower selector

| Position | Cutoff | Reference cue |
|---:|---:|---|
| 1 | 16 Hz | sub-bass / audio-rate CV |
| 2 | 70 Hz | kick and thud fundamental |
| 3 | 100 Hz | bassline tracking / hum removal |
| 4 | 250 Hz | mud / clutter region |
| 5 | 482 Hz | classic Moog mid step |
| 6 | 1000 Hz | telephone mid focus |
| 7 | 2000 Hz | presence / intelligibility |
| 8 | 7500 Hz | sizzle / sibilance control |

**Defaults:** 16 kHz upper and 7.5 kHz lower.

## Bypass

Host BYPASS sends each input directly to its matching output without filtering or coloration. Filter histories stop advancing while bypassed. On the first active sample, the filter histories are cleared before processing resumes. BYPASS is abrupt and can produce a click or thump.

## Use

The upper section provides a slightly warmer response, while the lower section keeps a cleaner response. The steps suit repeatable spectral shaping, cleanup, and deliberate shifts between fixed cutoff points. This manual describes the XL-35c version 1.0.0.
