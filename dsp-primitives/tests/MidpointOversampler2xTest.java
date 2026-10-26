import com.insectlabs.dsp.interpolation.MidpointOversampler2x;

public final class MidpointOversampler2xTest {
    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    public static void main(String[] args) {
        int[] calls = {0};
        MidpointOversampler2x average = new MidpointOversampler2x(
                sample -> { calls[0]++; return sample * sample; },
                MidpointOversampler2x.OutputMode.AVERAGE);
        check(average.process(2.0) == 4.0 && calls[0] == 1, "initial sample seed");
        check(average.process(4.0) == 12.5 && calls[0] == 3, "midpoint and endpoint average");
        average.reset();
        check(average.process(4.0) == 16.0 && calls[0] == 4, "reset reseeds history");
        check(average.process(4.0) == 16.0 && calls[0] == 6, "constant input");

        MidpointOversampler2x second = new MidpointOversampler2x(
                sample -> sample * sample, MidpointOversampler2x.OutputMode.SECOND);
        check(second.process(2.0) == 4.0, "SECOND seed output");
        check(second.process(4.0) == 16.0, "SECOND endpoint output");

        double[] firstCalls = new double[4];
        int[] index = {0};
        MidpointOversampler2x trace = new MidpointOversampler2x(
                sample -> { firstCalls[index[0]++] = sample; return sample; },
                MidpointOversampler2x.OutputMode.SECOND);
        trace.process(2.0); trace.process(6.0);
        check(index[0] == 3 && firstCalls[0] == 2.0
                && firstCalls[1] == 4.0 && firstCalls[2] == 6.0, "2x processor order");
        System.out.println("PASS: midpoint seed/order, AVERAGE/SECOND reduction, reset, constant input and processor call count.");
    }
}
