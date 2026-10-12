import java.awt.*;

/** GUI-only renderer. Never advances or changes the recording transport. */
public final class WireTransportCanvas {
    private static final Color BACKGROUND = new Color(35, 31, 32);
    private static final Color SILVER = new Color(193, 192, 177);
    private static final Color DIM = new Color(111, 111, 101);
    private static final Color AMBER = new Color(227, 166, 75);
    private static final Color RED = new Color(219, 107, 83);
    private static final Color GREEN = new Color(158, 184, 141);
    private static final Font SMALL = new Font("Monospaced", Font.PLAIN, 7);
    private static final Font STATUS = new Font("Monospaced", Font.BOLD, 9);
    private static final BasicStroke WIRE = new BasicStroke(.85f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    private java.awt.image.BufferedImage frame;
    private java.awt.image.BufferedImage backplate;
    private java.awt.image.BufferedImage flange;
    private boolean phaseKnown;
    private double lastPhase;
    private double visualPhase;
    private boolean wasBroken;
    private long breakStarted;

    public void paint(Graphics2D target, int width, int height, double phase, double speed,
            double position, double seconds, boolean powered, boolean broken,
            boolean running, boolean recording, boolean armed, boolean bypassed,
            long repairs, double scar, long now) {
        if (width <= 0 || height <= 0) return;
        if (frame == null || frame.getWidth() != width || frame.getHeight() != height) {
            makeImages(width, height);
        }
        if (!phaseKnown) {
            phaseKnown = true;
            lastPhase = phase;
            visualPhase = phase * .5;
        } else {
            double delta = Math.atan2(Math.sin(phase - lastPhase), Math.cos(phase - lastPhase));
            // A slower visual ratio prevents the three spokes from appearing to
            // reverse at high speeds on the existing 30 Hz GUI timer.
            visualPhase += delta * .5;
            visualPhase = Math.IEEEremainder(visualPhase, Math.PI * 2);
            lastPhase = phase;
        }
        if (broken && !wasBroken) breakStarted = now;
        wasBroken = broken;
        double age = broken ? Math.max(0, (now - breakStarted) * 1e-9) : 0;
        Graphics2D g = frame.createGraphics();
        try {
            g.drawImage(backplate, 0, 0, null);
            g.scale(width / 301.0, height / 150.0);
            quality(g);
            drawWire(g, broken, age, visualPhase, Math.abs(speed), powered && !bypassed, scar);
            drawReel(g, 60, 57, visualPhase);
            drawReel(g, 241, 57, visualPhase + .65);
            // Playback head and its fixed guides sit in front of the wire.
            g.setColor(new Color(12, 12, 12));
            g.fillRoundRect(140, 53, 22, 20, 4, 4);
            g.setColor(new Color(105, 107, 102));
            g.fillRoundRect(141, 52, 20, 18, 3, 3);
            g.setColor(SILVER);
            g.drawLine(143, 53, 158, 53);
            g.setColor(new Color(48, 49, 46));
            g.fillRect(149, 55, 3, 12);
            g.setColor(recording && powered && !bypassed ? RED : DIM);
            g.fillRect(146, 69, 8, 1);
            drawStatus(g, speed, position, seconds, powered, broken, running,
                    recording, armed, bypassed, repairs);
        } finally {
            g.dispose();
        }
        // Present one finished image instead of exposing intermediate clears.
        java.awt.geom.AffineTransform saved = target.getTransform();
        try {
            target.setTransform(new java.awt.geom.AffineTransform());
            target.drawImage(frame, 0, 0, null);
        } finally {
            target.setTransform(saved);
        }
    }

    private static void quality(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    }

    private void makeImages(int width, int height) {
        frame = new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_RGB);
        backplate = new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_RGB);
        Graphics2D g = backplate.createGraphics();
        try {
            g.setColor(BACKGROUND);
            g.fillRect(0, 0, width, height);
            g.scale(width / 301.0, height / 150.0);
            quality(g);
            g.setPaint(new GradientPaint(0, 5, new Color(43, 40, 40), 0, 111, BACKGROUND));
            g.fillRoundRect(2, 3, 297, 106, 8, 8);
            g.setColor(new Color(51, 48, 46));
            g.drawRoundRect(2, 3, 297, 106, 8, 8);
            for (int x : new int[] {9, 292}) {
                for (int y : new int[] {10, 102}) {
                    g.setColor(new Color(19, 19, 18));
                    g.fillOval(x - 2, y - 2, 4, 4);
                    g.setColor(new Color(77, 76, 70));
                    g.drawLine(x - 1, y, x + 1, y);
                }
            }
            for (int x : new int[] {60, 241}) {
                g.setColor(new Color(19, 18, 18));
                g.fillOval(x - 44, 15, 88, 88);
                g.setColor(new Color(81, 80, 73));
                g.fillOval(x - 34, 23, 68, 68);
                for (int r = 15; r <= 33; r++) {
                    g.setColor((r & 1) == 0 ? new Color(123, 121, 109) : new Color(78, 78, 70));
                    g.setStroke(new BasicStroke(.5f));
                    g.drawOval(x - r, 57 - r, r * 2, r * 2);
                }
            }
            g.setStroke(new BasicStroke(1));
            for (int x : new int[] {116, 185}) {
                g.setColor(new Color(11, 12, 11));
                g.fillOval(x - 4, 62, 8, 8);
                g.setColor(new Color(126, 127, 115));
                g.drawOval(x - 3, 62, 6, 6);
                g.setColor(new Color(62, 62, 57));
                g.fillOval(x - 1, 64, 2, 2);
            }
            g.setFont(SMALL);
            g.setColor(DIM);
            centered(g, "SUPPLY", 60, 105);
            centered(g, "TAKE-UP", 241, 105);
            centered(g, "HEAD", 150, 82);
            g.setColor(new Color(76, 69, 59));
            g.drawLine(9, 114, 292, 114);
        } finally {
            g.dispose();
        }
        int size = Math.max(90, (int) Math.ceil(90 * Math.max(width / 301.0, height / 150.0)));
        flange = new java.awt.image.BufferedImage(size, size, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        g = flange.createGraphics();
        try {
            g.scale(size / 90.0, size / 90.0);
            g.translate(45, 45);
            quality(g);
            java.awt.geom.Area metal = new java.awt.geom.Area(new java.awt.geom.Ellipse2D.Double(-42, -42, 84, 84));
            for (int i = 0; i < 3; i++) {
                java.awt.Shape hole = new java.awt.geom.RoundRectangle2D.Double(12, -7, 23, 14, 12, 12);
                metal.subtract(new java.awt.geom.Area(java.awt.geom.AffineTransform.getRotateInstance(i * Math.PI * 2 / 3).createTransformedShape(hole)));
            }
            g.setPaint(new GradientPaint(-28, -38, new Color(196, 194, 182), 29, 34, new Color(101, 104, 99)));
            g.fill(metal);
            g.setClip(metal);
            g.setStroke(new BasicStroke(.35f));
            for (int y = -40; y < 42; y += 2) {
                g.setColor(new Color(225, 225, 211, 26));
                g.drawLine(-42, y, 42, y - 5);
            }
            g.setClip(null);
            g.setStroke(new BasicStroke(.8f));
            g.setColor(new Color(227, 226, 206));
            g.draw(metal);
            g.setColor(new Color(69, 73, 68));
            g.drawOval(-39, -39, 78, 78);
            g.setColor(new Color(231, 219, 170));
            g.fillRoundRect(-2, -40, 4, 3, 1, 1);
        } finally {
            g.dispose();
        }
    }

    private void drawReel(Graphics2D g, double x, double y, double angle) {
        java.awt.geom.AffineTransform transform = g.getTransform();
        g.translate(x, y);
        g.rotate(angle);
        g.drawImage(flange, -45, -45, 90, 90, null);
        g.setTransform(transform);
        g.setPaint(new GradientPaint((float) x - 8, (float) y - 8, new Color(210, 208, 189),
                (float) x + 8, (float) y + 8, new Color(74, 78, 72)));
        g.fillOval((int) x - 10, (int) y - 10, 20, 20);
        g.setColor(new Color(32, 35, 31));
        g.fillOval((int) x - 4, (int) y - 4, 8, 8);
        g.setColor(new Color(192, 191, 167));
        g.drawLine((int) x - 2, (int) y, (int) x + 2, (int) y);
    }

    private static void drawWire(Graphics2D g, boolean broken, double age, double phase,
            double speed, boolean active, double scar) {
        g.setStroke(WIRE);
        g.setColor(active ? SILVER : new Color(138, 137, 124));
        if (broken) {
            double settle = 1 - Math.exp(-age * 5);
            double bounce = Math.sin(age * 16) * Math.exp(-age * 5) * 5;
            java.awt.geom.Path2D left = new java.awt.geom.Path2D.Double();
            left.moveTo(78, 30);
            left.curveTo(102, 48, 107, 74, 126, 68 + 28 * settle + bounce);
            java.awt.geom.Path2D right = new java.awt.geom.Path2D.Double();
            right.moveTo(223, 30);
            right.curveTo(203, 48, 197, 77, 175, 69 + 27 * settle - bounce);
            g.draw(left);
            g.draw(right);
            g.setColor(AMBER);
            g.fillOval(124, (int) (66 + 28 * settle + bounce), 3, 3);
            g.fillOval(173, (int) (67 + 27 * settle - bounce), 3, 3);
        } else {
            java.awt.geom.Path2D wire = new java.awt.geom.Path2D.Double();
            wire.moveTo(78, 30);
            wire.lineTo(116, 62);
            wire.lineTo(150, 61);
            wire.lineTo(185, 62);
            wire.lineTo(223, 30);
            g.draw(wire);
            if (active && speed > .01) {
                double travel = phase / (Math.PI * 2);
                travel -= Math.floor(travel);
                double x = 121 + travel * 59;
                g.setColor(new Color(235, 231, 211));
                g.draw(new java.awt.geom.Line2D.Double(x, 61.8, x + 2, 61.8));
            }
            if (scar > .02) {
                g.setColor(AMBER);
                g.drawOval(146, 59, 7, 4);
            }
        }
    }

    private static void drawStatus(Graphics2D g, double speed, double position, double seconds,
            boolean powered, boolean broken, boolean running, boolean recording,
            boolean armed, boolean bypassed, long repairs) {
        String status;
        Color color;
        if (bypassed) { status = "BYPASS"; color = DIM; }
        else if (broken) { status = "WIRE BROKEN / SPLICE"; color = AMBER; }
        else if (!powered) { status = "POWER OFF"; color = DIM; }
        else if (recording) { status = "RECORDING"; color = RED; }
        else if (!running && Math.abs(speed) > .01) { status = "COASTING"; color = SILVER; }
        else if (running && speed < -.01) { status = "REVERSE"; color = GREEN; }
        else if (running) { status = "PLAY"; color = GREEN; }
        else if (armed) { status = "REC ARMED"; color = RED; }
        else { status = "STOPPED"; color = SILVER; }
        g.setColor(color);
        g.fillOval(10, 121, 4, 4);
        g.setFont(STATUS);
        g.drawString(status, 20, 126);
        g.setFont(SMALL);
        String speedText = String.format(java.util.Locale.ROOT, "%+.2fx", speed);
        right(g, speedText, 291, 126);
        g.setColor(DIM);
        g.drawString("SPLICES " + repairs, 10, 142);
        String timing = String.format(java.util.Locale.ROOT, "%.2f / %.3g s", position * seconds, seconds);
        right(g, timing, 291, 142);
        g.setColor(new Color(66, 61, 53));
        g.fillRect(10, 131, 281, 2);
        g.setColor(broken ? AMBER : powered && !bypassed ? SILVER : DIM);
        int at = 10 + (int) Math.round(Math.max(0, Math.min(1, position)) * 280);
        g.fillRect(at, 130, 1, 4);
    }

    private static void centered(Graphics2D g, String text, int center, int y) {
        g.drawString(text, center - g.getFontMetrics().stringWidth(text) / 2, y);
    }

    private static void right(Graphics2D g, String text, int x, int y) {
        g.drawString(text, x - g.getFontMetrics().stringWidth(text), y);
    }
}
