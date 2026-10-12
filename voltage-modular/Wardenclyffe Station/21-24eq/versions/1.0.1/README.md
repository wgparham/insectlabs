# 21-24eq Frequency Corrector v1.0.1

First canonized archive for the Wardenclyffe Station frequency corrector. The matching Designer project and exported Java are authoritative. The published identity is class `tttf` in package `com.insectlabs.tttf`. Cherry Audio required the class rename after a server-side publication error. The user has rechecked this set and confirmed it is the correct current v1.0.1 release, tested and published.

- [User manual](USER-MANUAL.md)
- [Developer notes](DEVELOPER-NOTES.md)
- [Release review](REVIEW.md)
- [Release notes](RELEASE-NOTES.md)
- [Designer project](tttf.vmod) and [Java source](tttf.java)
- [Panel artwork](tttf_hero.png)
- [Repeatable validation](tests/validate.py)
- [SHA-256 manifest](SHA256.json)

Run `python tests/validate.py` with Java 17-compatible `javac` and the installed Voltage SDK at `C:\ProgramData\Voltage\voltage.jar`. The review records the installed Java 27 lint limitation and host-audition status.
