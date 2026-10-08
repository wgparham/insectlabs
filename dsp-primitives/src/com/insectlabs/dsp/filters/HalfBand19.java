package com.insectlabs.dsp.filters;

/** Established Colorbox 19-tap half-band FIR; coefficients/arithmetic preserved. */
public final class HalfBand19
{
    private static final double C0 =
         0.021277660466073;

    private static final double C2 =
        -0.027992303277934;

    private static final double C4 =
         0.049537687283950;

    private static final double C6 =
        -0.095789590456413;

    private static final double C8 =
         0.308510413777763;

    private static final double C9 =
         0.488912264413122;


    // A doubled ring buffer avoids array shifting and modulo.

    private final double[] buffer =
        new double[38];

    private int position = 0;
    private boolean dirty = false;


    public double process( double input )
    {
        position--;

        if( position < 0 )
        {
            position = 18;
        }

        buffer[position] = input;
        buffer[position + 19] = input;

        dirty = true;


        return
            C0 *
                (buffer[position] +
                 buffer[position + 18])
            +
            C2 *
                (buffer[position + 2] +
                 buffer[position + 16])
            +
            C4 *
                (buffer[position + 4] +
                 buffer[position + 14])
            +
            C6 *
                (buffer[position + 6] +
                 buffer[position + 12])
            +
            C8 *
                (buffer[position + 8] +
                 buffer[position + 10])
            +
            C9 *
                buffer[position + 9];
    }


    public void reset()
    {
        // Avoid repeatedly clearing an already-empty filter.

        if( !dirty )
        {
            return;
        }

        for( int i = 0; i < 38; i++ )
        {
            buffer[i] = 0.0;
        }

        position = 0;
        dirty = false;
    }
}
