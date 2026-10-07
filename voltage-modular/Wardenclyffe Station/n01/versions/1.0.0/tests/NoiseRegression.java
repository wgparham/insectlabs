public class NoiseRegression {
    public static void main(String[] args) {
        NoiseChecks current = NoiseChecks.unit();
        ApprovedChecks approved = ApprovedChecks.unit();
        java.util.Random sequence = new java.util.Random(71029);
        int comparisons = 0;
        for (int sample = 0; sample < 480000; sample++) {
            if (sample % 701 == 0) {
                current.redKnob.value = approved.redKnob.value = sequence.nextDouble();
                current.blueKnob.value = approved.blueKnob.value = sequence.nextDouble();
                current.randomRateKnob.value = approved.randomRateKnob.value = sequence.nextDouble();
                current.randomLevelKnob.value = approved.randomLevelKnob.value = sequence.nextDouble();
                current.triggerInput.connected = approved.triggerInput.connected = sequence.nextBoolean();
            }
            current.triggerInput.value = approved.triggerInput.value = sample % 113 < 12 ? 5 : 0;
            if (sample % 773 == 0) { current.press(); approved.press(); }
            if (sample % 773 == 18) { current.release(); approved.release(); }
            if (sample % 10001 == 0) {
                current.restore(approved.state());
                approved.restore(current.state());
            }
            if (sample % 13001 < 35) { current.bypass(); approved.bypass(); }
            else { current.process(); approved.process(); }
            double[] actual = {current.whiteOutput.value, current.spectraOutput.value,
                    current.slowRandomOutput.value, current.sampleSourceOutput.value, current.steppedOutput.value};
            double[] expected = {approved.whiteOutput.value, approved.spectraOutput.value,
                    approved.slowRandomOutput.value, approved.sampleSourceOutput.value, approved.steppedOutput.value};
            for (int output = 0; output < actual.length; output++) {
                if (Double.doubleToLongBits(actual[output]) != Double.doubleToLongBits(expected[output])) {
                    throw new AssertionError("Cleanup changed output " + output + " at sample " + sample);
                }
                comparisons++;
            }
        }
        System.out.println(comparisons + " bit-exact output comparisons passed against approved callbacks.");
    }
}
