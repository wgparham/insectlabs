package com.insectlabs.dsp.interpolation;

/**
 * Allocation-free four-point cubic Hermite (Catmull-Rom tangent) interpolator.
 * The fraction is normally in [0, 1], between s1 and s2. This is not a
 * band-limited resampler; use a suitable anti-alias filter for rate reduction.
 */
public final class CubicHermite {
    private CubicHermite() {
    }

    public static double interpolate(double s0, double s1, double s2, double s3, double fraction) {
        double c1 = 0.5 * (s2 - s0);
        double c2 = s0 - 2.5 * s1 + 2.0 * s2 - 0.5 * s3;
        double c3 = 0.5 * (s3 - s0) + 1.5 * (s1 - s2);
        return ((c3 * fraction + c2) * fraction + c1) * fraction + s1;
    }
}
