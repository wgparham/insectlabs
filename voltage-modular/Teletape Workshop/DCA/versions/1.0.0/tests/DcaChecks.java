public final class DcaChecks {
    private static int checks;
    private DcaChecks() { }
    private static void check(boolean ok, String message) { checks++; if (!ok) throw new AssertionError(message); }
    private static void near(double a, double b, double tolerance, String message) { check(Double.isFinite(a) && Math.abs(a-b) <= tolerance, message + ": " + a + " vs " + b); }
    private static void finite(double... values) { for (double v : values) check(Double.isFinite(v), "finite output"); }

    private static void laws() {
        for (double s : new double[] {0.0, 0.5, 1.0}) {
            near(DcaCoreFixture.DcaCore.gainLaw(0, s), 0, 0, "zero gain");
            near(DcaCoreFixture.DcaCore.gainLaw(1, s), 1, 0, "unity invariant");
            near(DcaCoreFixture.DcaCore.gainLaw(4, s), 4, 0, "maximum gain");
        }
        near(DcaCoreFixture.DcaCore.gainLaw(0.5, 0.0), 0.5, 1e-12, "linear gain law");
        check(DcaCoreFixture.DcaCore.gainLaw(0.5, 0.5) < 0.5, "EXP attenuates below unity");
        check(DcaCoreFixture.DcaCore.gainLaw(0.5, 1.0) < DcaCoreFixture.DcaCore.gainLaw(0.5, 0.5), "LOG has deeper attenuation");
        near(DcaCoreFixture.DcaCore.gainLaw(9, 1), 4, 0, "gain clamp");
        near(DcaCoreFixture.DcaCore.colorAmount(0), 0, 0, "color at zero");
        near(DcaCoreFixture.DcaCore.colorAmount(1), 0.15, 1e-12, "color at unity");
        near(DcaCoreFixture.DcaCore.colorAmount(4), 1, 1e-12, "color at 4x");
        for (int i=0; i<=100; i++) {
            double g = DcaCoreFixture.DcaCore.gainLaw(i*0.04, 0.73);
            check(g >= 0 && g <= 4, "gain bounded");
            check(DcaCoreFixture.DcaCore.colorAmount(g) >= 0 && DcaCoreFixture.DcaCore.colorAmount(g) <= 1, "color bounded");
        }
    }

    private static void waveshaper() {
        for (int i=0; i<=200; i++) {
            double x = i * 0.1;
            near(DcaCoreFixture.DcaCore.shape(-x, 0.65), -DcaCoreFixture.DcaCore.shape(x, 0.65), 1e-12, "odd waveshaper");
        }
        double before = DcaCoreFixture.DcaCore.shape(10.0-1e-8, 1.0);
        double at = DcaCoreFixture.DcaCore.shape(10.0, 1.0);
        double after = DcaCoreFixture.DcaCore.shape(10.0+1e-8, 1.0);
        check(before <= at && at <= after && after-before < 1e-6, "continuous waveshaper knee");
        for (double x : new double[] {10, 11, 20, 1000}) {
            double y = DcaCoreFixture.DcaCore.shape(x, 1.0);
            check(y >= 0 && y <= 11, "positive ceiling");
            check(DcaCoreFixture.DcaCore.shape(-x, 1.0) >= -11, "negative ceiling");
        }
        near(DcaCoreFixture.DcaCore.panL(0), 1, 1e-12, "left pan endpoint");
        near(DcaCoreFixture.DcaCore.panR(0), 0, 1e-12, "left pan isolation");
        near(DcaCoreFixture.DcaCore.panL(1), 0, 1e-12, "right pan isolation");
        near(DcaCoreFixture.DcaCore.panR(1), 1, 1e-12, "right pan endpoint");
        near(DcaCoreFixture.DcaCore.panL(0.5), Math.sqrt(0.5), 1e-12, "constant power center");
    }

    private static DcaCoreFixture.DcaCore render(int mode, boolean conA, boolean conB, boolean rightOut, double panA, double panB) {
        DcaCoreFixture.DcaCore c = new DcaCoreFixture.DcaCore();
        for (int i=0; i<2000; i++) c.process(mode,
                2.0, conA, 5.0, false, 1.0, 0.0, 0.0, panA,
                -1.0, conB, 5.0, false, 1.0, 0.0, 0.0, panB,
                0.0, 0.0, rightOut);
        return c;
    }

    private static void modeAndRouting() {
        DcaCoreFixture.DcaCore ind = render(0, true, false, true, 0.0, 1.0);
        check(Math.abs(ind.outA) > 1.0, "IND channel A passes signal");
        near(ind.outB, 0, 0, "IND unpatched B stays silent");
        near(ind.outL, ind.outA, 1e-12, "hard-left A route");
        near(ind.outR, 0, 0, "hard-left A isolated");

        DcaCoreFixture.DcaCore serial = render(1, true, false, true, 0.0, 1.0);
        check(Math.abs(serial.outB) > 0.5, "SERIAL normal feeds A into B");
        near(serial.outR, serial.outB, 1e-12, "hard-right B route");

        DcaCoreFixture.DcaCore st = render(2, true, false, true, 0.5, 0.0);
        check(Math.abs(st.outB) > 1.0, "ST normal feeds A into B");
        near(st.outL, st.outA, 1e-12, "ST centered balance sends A left");
        near(st.outR, st.outB, 1e-12, "ST centered balance sends B right");
        DcaCoreFixture.DcaCore stMono = render(2, true, false, false, 0.5, 0.0);
        near(stMono.outL, stMono.outA, 0.01, "ST mono normal does not double");
        near(stMono.outR, stMono.outL, 0, "mono output mirrors left mix");

        DcaCoreFixture.DcaCore links = new DcaCoreFixture.DcaCore();
        for (int i=0; i<4; i++) links.process(0, 0,false,0,false,0,0,0,0, 0,false,0,false,0,0,0,1, 1.25,-0.75,false);
        near(links.outL, 0.5, 1e-12, "mono mix sums both unity links");
        near(links.outR, 0.5, 0, "mono link mix is mirrored");
        finite(ind.outA, ind.outB, ind.outL, ind.outR, serial.outA, serial.outB, serial.outL, serial.outR, st.outA, st.outB, st.outL, st.outR);
    }

    private static void bypassAndSanitization() {
        DcaCoreFixture.DcaCore c = render(1, true, false, true, 0.0, 1.0);
        c.bypass(2.25, true, -3.5, true, 1.0, 2.0, true);
        near(c.outA, 2.25, 0, "bypass A is direct");
        near(c.outB, -3.5, 0, "bypass B is direct when patched");
        near(c.outL, 3.25, 1e-12, "bypass routed left plus link");
        near(c.outR, -1.5, 1e-12, "bypass routed right plus link");
        DcaCoreFixture.DcaCore ind = render(0, true, false, true, 0.0, 1.0);
        ind.bypass(2.0, true, 0.0, false, 0, 0, true);
        near(ind.outB, 0, 0, "IND bypass does not invent a normal");
        DcaCoreFixture.DcaCore n = new DcaCoreFixture.DcaCore();
        n.process(0, Double.NaN,true,Double.NaN,true,Double.NaN,Double.NaN,Double.NaN,Double.NaN,
                Double.POSITIVE_INFINITY,true,Double.NaN,true,Double.NaN,Double.NaN,Double.NaN,Double.NaN,
                Double.NaN,Double.NEGATIVE_INFINITY,true);
        finite(n.outA,n.outB,n.outL,n.outR);
    }

    private static void channelAndFilter() {
        DcaCoreFixture.DcaCore.Channel a = new DcaCoreFixture.DcaCore.Channel();
        DcaCoreFixture.DcaCore.Channel b = new DcaCoreFixture.DcaCore.Channel();
        for (int i=0; i<2000; i++) {
            double ya = a.process(0.5, true, false, 5, false, 1, 0.5, 0);
            double yb = b.process(0.5, true, false, 5, false, 1, 0.5, 0);
            near(ya, yb, 0, "fixed-seed repeatability");
        }
        near(a.lastX, 1, 1e-12, "unpatched CV uses 5V reference");
        near(a.lastGain, 1, 1e-12, "unity gain point");
        DcaCoreFixture.DcaCore.Channel cv = new DcaCoreFixture.DcaCore.Channel();
        cv.process(1,true,false,-5,true,4,0,0);
        near(cv.lastX, 0, 0, "negative CV rectified to zero");
        cv.process(1,true,false,2.5,true,4,0,0);
        near(cv.lastX, 2, 0, "CV scales fader by 5V");
        DcaCoreFixture.DcaCore.Channel idle = new DcaCoreFixture.DcaCore.Channel();
        near(idle.process(0,false,false,0,false,4,0.5,0), 0, 0, "unpatched centered channel is silent");
        check(idle.isIdle(), "unpatched channel enters idle state");
        check(DcaCoreFixture.DcaCore.NOISE_RMS_V > 0, "noise floor is nonzero");
        DcaCoreFixture.DcaCore.HalfBand19 f = new DcaCoreFixture.DcaCore.HalfBand19();
        double[] impulse = new double[19];
        for (int i=0; i<19; i++) impulse[i] = f.process(i == 0 ? 1 : 0);
        double sum=0; for(double x:impulse) sum+=x;
        near(sum,1,1e-12,"half-band DC gain");
        for(int i=0;i<19;i++) near(impulse[i],impulse[18-i],0,"half-band impulse symmetry");
    }

    public static void main(String[] args) {
        laws(); waveshaper(); modeAndRouting(); bypassAndSanitization(); channelAndFilter();
        System.out.println("PASS: " + checks + " DCA math, oversampling-filter, mode/routing, bypass, finite-input, idle, and deterministic-output assertions.");
    }
}
