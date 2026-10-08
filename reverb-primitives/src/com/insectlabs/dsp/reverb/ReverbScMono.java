package com.insectlabs.dsp.reverb;

/**
 * Mono eight-line scattering reverb extracted from the LM-21 Mk III prototype.
 * Delay constants, modulation/seeds, interpolation and scattering follow the
 * Sean Costello / Csound ReverbSC family; this is the InsectLabs mono adaptation.
 * Extraction preserves 48 kHz DSP arithmetic; see README.md and DEVELOPER-NOTES.md.
 */
public final class ReverbScMono
{
    private static final double DEFAULT_SR = 44100.0;
    private static final double OUTPUT_GAIN_MONO = 0.175; // 0.35 * 1/2
    private static final double JUNCTION_SCALE = 0.25;
    private static final double MAX_PITCH_MOD = 2.25;

    private static final double[] BASE_DELAY = {
        2473.0 / DEFAULT_SR,
        2767.0 / DEFAULT_SR,
        3217.0 / DEFAULT_SR,
        3557.0 / DEFAULT_SR,
        3907.0 / DEFAULT_SR,
        4127.0 / DEFAULT_SR,
        2143.0 / DEFAULT_SR,
        1933.0 / DEFAULT_SR
    };

    private static final double[] DELAY_VAR = {
        0.0010, 0.0011, 0.0017, 0.0006,
        0.0010, 0.0011, 0.0017, 0.0006
    };

    private static final double[] RAND_FREQ = {
        3.100, 3.500, 1.110, 3.973,
        2.341, 1.897, 0.891, 3.221
    };

    private static final int[] SEED = {
        1966, 29491, 22937, 9830,
        20643, 22937, 29491, 14417
    };

    private final DelayLine[] lines = new DelayLine[8];

    private final double sampleRate;

    public ReverbScMono()
    {
        this(48000.0);
    }

    public ReverbScMono(double sampleRate)
    {
        if (!Double.isFinite(sampleRate) || sampleRate < 8000.0 || sampleRate > 192000.0)
            throw new IllegalArgumentException("Sample rate must be 8000–192000 Hz");
        this.sampleRate = sampleRate;
        for ( int n = 0; n < 8; n++ )
        {
            int size = (int)Math.ceil(
                (BASE_DELAY[n] + DELAY_VAR[n] * MAX_PITCH_MOD * 1.125)
                * sampleRate + 16.5
            );

            lines[n] = new DelayLine( size, SEED[n] );
            initializeLine( lines[n], n, 0.40 );
        }
    }

    /** Clear the tail and restore deterministic initial modulation state. */
    public void reset()
    {
        for (int n = 0; n < lines.length; n++)
        {
            java.util.Arrays.fill(lines[n].buffer, 0.0);
            initializeLine(lines[n], n, 0.40);
        }
    }

    /** Process one sample. Caller supplies finite, bounded input and valid parameters.
     * feedback: [0,1); dampFact: [0,1]; pitchMod: [0,2.25]; age: [0,1].
     * Compute/smooth parameters outside this primitive. No audio-path allocations.
     */
    public double process( double input,
                    double feedback,
                    double dampFact,
                    double pitchMod,
                    double age )
    {
        double junction = 0.0;
        for ( int n = 0; n < 8; n++ )
            junction += lines[n].filterState;

        junction *= JUNCTION_SCALE;
        double drive = junction + input;

        double sum = 0.0;

        for ( int n = 0; n < 8; n++ )
        {
            DelayLine line = lines[n];

            line.buffer[line.writePos] = drive - line.filterState;
            line.writePos++;
            if ( line.writePos >= line.buffer.length )
                line.writePos = 0;

            double v = readCubic( line );

            line.readPos += line.readInc;
            while ( line.readPos >= line.buffer.length )
                line.readPos -= line.buffer.length;
            while ( line.readPos < 0.0 )
                line.readPos += line.buffer.length;

            v *= feedback;

            // Exact one-pole feedback damping form used by ReverbSC.
            v = (line.filterState - v) * dampFact + v;

            // The normal region remains essentially ReverbSC.  Only the
            // extended PERSPECTIVE region introduces a gentle BBD-ish
            // "generation loss": increased rounding inside recirculation.
            if ( age > 0.000001 )
            {
                double satAmount = 0.16 * age * age;
                double sat = 6.0 * Math.tanh( v / 6.0 );
                v += satAmount * (sat - v);
            }

            line.filterState = v;
            sum += v;

            line.randomCount--;
            if ( line.randomCount <= 0 )
                nextRandomSegment( line, n, pitchMod );
        }

        return sum * OUTPUT_GAIN_MONO;
    }

    private static double readCubic( DelayLine line )
    {
        int size = line.buffer.length;
        int i0 = (int)Math.floor( line.readPos );
        double frac = line.readPos - i0;

        int im1 = i0 - 1;
        if ( im1 < 0 ) im1 += size;

        int i1 = i0 + 1;
        if ( i1 >= size ) i1 -= size;

        int i2 = i1 + 1;
        if ( i2 >= size ) i2 -= size;

        double vm1 = line.buffer[im1];
        double v0 = line.buffer[i0];
        double v1 = line.buffer[i1];
        double v2 = line.buffer[i2];

        // ReverbSC's original cubic interpolation polynomial.
        double a2 = (frac * frac - 1.0) / 6.0;
        double a1 = (frac + 1.0) * 0.5;
        double am1 = a1 - 1.0;
        double a0 = 3.0 * a2;

        a1 -= a0;
        am1 -= a2;
        a0 -= frac;

        return (am1 * vm1 + a0 * v0 + a1 * v1 + a2 * v2) * frac + v0;
    }

    private void initializeLine( DelayLine line, int n, double pitchMod )
    {
        line.writePos = 0;
        line.seed = SEED[n];
        line.filterState = 0.0;

        double initialDelay =
            BASE_DELAY[n] + (line.seed * DELAY_VAR[n] / 32768.0) * pitchMod;

        line.readPos = line.buffer.length - initialDelay * sampleRate;
        while ( line.readPos < 0.0 )
            line.readPos += line.buffer.length;
        while ( line.readPos >= line.buffer.length )
            line.readPos -= line.buffer.length;

        nextRandomSegment( line, n, pitchMod );
    }

    private void nextRandomSegment( DelayLine line, int n, double pitchMod )
    {
        if ( line.seed < 0 )
            line.seed += 0x10000;

        line.seed = (line.seed * 15625 + 1) & 0xFFFF;

        if ( line.seed >= 0x8000 )
            line.seed -= 0x10000;

        line.randomCount = (int)(sampleRate / RAND_FREQ[n] + 0.5);
        if ( line.randomCount < 1 )
            line.randomCount = 1;

        double previousDelay = line.writePos - line.readPos;
        while ( previousDelay < 0.0 )
            previousDelay += line.buffer.length;
        while ( previousDelay >= line.buffer.length )
            previousDelay -= line.buffer.length;
        previousDelay /= sampleRate;

        double nextDelay =
            BASE_DELAY[n] + (line.seed * DELAY_VAR[n] / 32768.0) * pitchMod;

        line.readInc =
            ((previousDelay - nextDelay) * sampleRate / line.randomCount) + 1.0;
    }

    private static final class DelayLine
    {
        final double[] buffer;
        int writePos;
        double readPos;
        double readInc;
        int seed;
        int randomCount;
        double filterState;

        DelayLine( int size, int initialSeed )
        {
            buffer = new double[size];
            seed = initialSeed;
        }
    }
}
