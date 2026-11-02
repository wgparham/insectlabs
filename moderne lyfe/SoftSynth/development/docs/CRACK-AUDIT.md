# What the crack did to Softsynth 2.1

This cracked copy may be the only surviving 2.x binary, so the crack needs to be understood as part of the record. The surviving files are fingerprinted in `SoftSynth/SHA256SUMS`. Do not edit them: work on copies. Addresses are offsets into the text segment.

Status words below: **confirmed** means shown by the bytes or a test; **inferred** means the best reading, not proven.

## The original protection (key disk)

What remains of it, in `0x4DD6` and the tail from `0x4F36` to `0x509A`:

1. `F004EC0` reads `$4A6` (the number of floppy drives) in supervisor mode. **Confirmed.** It has no remaining caller, which was probably in the overwritten code (**inferred**).
2. `F004DD6` makes up to 3 attempts to read one sector with Floprd: a random track from 0 to 79 (`0x4D70`), side 0, sector 10, into a buffer at -$3EC8(A4). Errors -2, -6 and -8 end the attempts. A read passes only with error 0 or **-4 (CRC error)**, and with the three bytes at buffer offset 0xA0 summing to 0x55 mod 256. It returns the word at offset 0xA0 as the XOR key, or -1. **Confirmed.** Accepting a CRC error suggests a deliberately damaged sector, which ordinary disk copying wouldn't reproduce (**inferred**).
3. The tail reads track 78, side 0, sector 7 with Floprd (`0x4F88`), returning 0x9999 on error. If the key is -1 it returns 0x1111, after trying the other drive at `0x4F38`. Each of 0x100 words is XORed with the key, which increases by 0x2345 per word. A byte sum of the 0x200-byte result must be 0xDD, or it returns 0x8888. **Confirmed.**
4. It calls the decrypted buffer as code (`jsr (A0)` at `0x5052`), passing -$40CC(A4), the drive, 3, 0, 0x4F and -$40D2(A4). Afterwards it wipes the buffer with a pattern from 0xABCD adding 0x6543. **Confirmed.**
5. The decrypted code lived only on the key disk. **It is not in this binary and is lost.**
6. Main at `0x4AC` combines the key-disk result words at -$40D2 and -$40D0 and the byte at -$40CE, and adds the result to -$4F2(A4). **Confirmed** for the data flow.

-$4F2 is the main loop's exit flag: `0x9EC` loops while it is 0, and Quit at `0x1CFA` and `0x2056` sets it. So a failed key disk made the program quit straight away. **Inferred** from that flow.

## What the crackers changed (The M.C.A.)

| Where | Change | Status |
|---|---|---|
| `0x4EF4..0x4F35` | 66 bytes of the key-disk routine overwritten by their own code, which is why the tail at `0x4F36` starts mid-function. | Confirmed |
| `0x4EF4` | New routine: writes 01 02 03 04 05 to -$40CC and returns 0. Main calls it at `0x412`. | Confirmed |
| `0x4D4` | `clr.l D0` just before the add to the exit flag, so the check always passes. It likely replaced a `sub.w D3,D0` (also 2 bytes). | Clear confirmed; replacement inferred |
| `0x4B6..0x4C2` | The immediates 0x102, 0x304 and 5 in main match the bytes their stub writes. They may be patched from references to -$40CC. | Inferred |
| `0x3BD6` | `jsr $4F08` replacing a 4-byte `movea.l -$1E(A4),A0` (which the routine then performs). Same instruction appears unpatched at `0x3BA0`, `0x3BB2` and `0x3BC4`. | Confirmed |
| `0x4F08` | Tamper check, see below. | Confirmed |
| `SOFTSYN.RSC` 0x511 | " Cracked by The M.C.A.  ", followed by a blank 21-character line. What these lines first said is unknown. | Credit confirmed; earlier text unknown |

**Possibly theirs:** the NOPs at `0x410`, `0x4DA`, `0x8C4` and `0x9D8`, the only four in the code. They could also be from the original build.

### Their tamper check

`0x4F08` sums 52 words at A5+0x2DE00 and compares with 0x915F. On a mismatch it jumps to start+0x24 of that memory.

- The window at offset 0x4D8 of `SOFTSYN.RSC` sums to exactly 0x915F, and covers "oftsynth", the version, the copyright and the "Cracked by" credit.
- 0x2DE00 − 0x4D8 = 0x2D928, which is exactly where the program's memory ends after its Mshrink (text + data + BSS + 0x2100 − 0x100). So they assumed the resource file loads directly after the program.
- On a mismatch the program executes About-box text at file offset 0x4FC as code, which would normally crash it.

This fires from `F003B90`, a dialog helper. **Consequences:**

- **Editing the credit breaks the program** (expected from the code, not run).
- **It depends on where TOS puts the resource file.** On a TOS or memory setup where it loads elsewhere, dialogs could crash even on an untouched copy. Not yet run on Hatari or real hardware, so this is a risk to test. Any test needing the check bypassed should patch a copy.

## What is lost

- The 66 overwritten bytes (the head of the key-disk routine).
- The key disk's sector contents and the code that ran from them.
- Whatever the credit lines replaced.

None of these are part of synthesis, as far as the code shows.

## Does the crack reach the sound?

No evidence that it does, on these grounds:

- **Protection memory is isolated.** Every A4 reference from -$40D2 to -$3E26 was listed. They are only in main's check, the key-disk code, and the setup at `0x8CC4`. None is in the render path.
- **The only exit from the check is the quit flag.** It has no other readers than the main loop.
- **The crack's code is reached from two places only:** `0x412` and `0x3BD6`. No other call, jump table entry or branch goes there.
- **Indirect calls are accounted for:** the only `jsr`/`jmp (An)` in code are the key-disk call, the float dispatcher, and the kernels' waveform dispatch.
- **Traps are accounted for:** Floprd has no other callers than the key-disk code.

**What this doesn't prove.** A small patch elsewhere that looks like ordinary code would not show up in this audit. Crackers had no reason to touch synthesis, but only an independent reference would settle it: a clean copy, or sound files made with an uncracked 2.x.

Related, not from the crack: the kernel at `0xBDA6` stores its start sample inside the code at `0xBE48`, which is self-modifying code by the original authors.
