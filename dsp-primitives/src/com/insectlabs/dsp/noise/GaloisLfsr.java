package com.insectlabs.dsp.noise;

/**
 * Right-shifting XOR Galois LFSR step, extracted from N02 1.0.0.
 * Caller owns the state, clock and conversion to audio. Supply a nonzero state
 * within the selected width. Zero is an absorbing state; no automatic reseeding
 * or amplitude normalization is performed. No process-time allocation.
 */
public final class GaloisLfsr {
    private GaloisLfsr() {
    }

    public static int maskForBits(int bits) {
        return switch (bits) {
            case 7 -> 0x60;
            case 15 -> 0x6000;
            case 31 -> 0x48000000;
            default -> throw new IllegalArgumentException("Supported LFSR widths: 7, 15, 31");
        };
    }

    public static int advance(int state, int feedbackMask) {
        return (state >>> 1) ^ (-(state & 1) & feedbackMask);
    }
}
