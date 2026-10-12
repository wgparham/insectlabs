// Shared GUI renderer; no audio callback calls these methods.
public final class SevenSegmentCanvas {
    private static final java.awt.Color FACE = new java.awt.Color(12, 17, 15);
    private static final java.awt.Color LIT = new java.awt.Color(192, 208, 197);
    private static final java.awt.Color UNLIT = new java.awt.Color(27, 37, 31);
    private static final java.awt.Color CAPTION = new java.awt.Color(131, 151, 139);
    private static final java.awt.Font UNIT_FONT = new java.awt.Font(java.awt.Font.MONOSPACED,
            java.awt.Font.PLAIN, 6);
    private static final int[] DIGITS = {119, 36, 93, 109, 46, 107, 123, 37, 127, 111};
    private boolean kilo;
    private String mode = "";
    private String lastNumber, lastUnit;
    private int lastWidth = -1, lastHeight = -1;

    private static final class Reading {
        final String number, unit;
        Reading(String number, String unit) { this.number = number; this.unit = unit; }
    }

    private Reading reading(double value, String baseUnit, String context,
            int preferredDecimals, boolean allowScale) {
        String nextMode = baseUnit + ":" + context;
        if (!nextMode.equals(mode)) { mode = nextMode; kilo = false; }
        if (Double.isFinite(value) && allowScale) {
            double magnitude = Math.abs(value);
            if (!kilo && magnitude >= 1100.0) kilo = true;
            else if (kilo && magnitude <= 900.0) kilo = false;
        } else if (!allowScale) {
            kilo = false;
        }
        String unit = (kilo ? "k" : "") + baseUnit
                + (context.isEmpty() ? "" : " " + context);
        if (Double.isNaN(value)) return new Reading("Err", unit);
        double scaled = kilo ? value / 1000.0 : value;
        if (!Double.isFinite(scaled) || Math.abs(scaled) > 99999.0) {
            return new Reading("OL", unit);
        }
        int precision = kilo ? Math.max(3, preferredDecimals) : preferredDecimals;
        for (int decimals = precision; decimals >= 0; decimals--) {
            double factor = Math.pow(10.0, decimals);
            double rounded = Math.round(scaled * factor) / factor;
            if (rounded == 0.0) rounded = 0.0; // Suppress negative zero.
            String number = String.format(java.util.Locale.ROOT, "% ." + decimals + "f", rounded).trim();
            int digits = 0;
            for (int i = 0; i < number.length(); i++) {
                if (Character.isDigit(number.charAt(i))) digits++;
            }
            if (digits <= 5) return new Reading(number, unit);
        }
        return new Reading("OL", unit);
    }

    public void update(voltage.core.VoltageCanvas canvas, double value, String unit, String context,
            int decimals, boolean allowScale) {
        Reading reading = reading(value, unit, context, decimals, allowScale);
        int width = canvas.GetBitmapWidth(), height = canvas.GetBitmapHeight();
        if (width <= 0 || height <= 0) return;
        if (reading.number.equals(lastNumber) && reading.unit.equals(lastUnit)
                && width == lastWidth && height == lastHeight) return;
        java.awt.Graphics2D graphics = canvas.GetGraphics();
        if (graphics == null) return;
        try {
            draw(graphics, width, height, reading.number, reading.unit);
        } finally {
            graphics.dispose();
        }
        canvas.Invalidate();
        lastNumber = reading.number;
        lastUnit = reading.unit;
        lastWidth = width;
        lastHeight = height;
    }

    private static void draw(java.awt.Graphics2D g, int width, int height,
            String number, String unit) {
        g.setTransform(new java.awt.geom.AffineTransform());
        g.setColor(FACE);
        g.fillRect(0, 0, width, height);
        g.scale(width / 60.0, height / 30.0);
        g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(new java.awt.Color(55, 68, 59));
        g.draw(new java.awt.geom.Rectangle2D.Double(0.5, 0.5, 59.0, 29.0));
        double textWidth = 0.0;
        for (int i = 0; i < number.length(); i++) {
            textWidth += number.charAt(i) == '.' ? 2.8 : 10.0;
        }
        double scale = Math.min(1.0, 55.0 / Math.max(1.0, textWidth));
        java.awt.geom.AffineTransform logical = g.getTransform();
        g.translate((60.0 - textWidth * scale) / 2.0, (22.0 - 16.5 * scale) / 2.0);
        g.scale(scale, scale);
        double x = 0.0;
        for (int i = 0; i < number.length(); i++) {
            char c = number.charAt(i);
            if (c == '.') {
                g.setColor(LIT);
                g.fill(new java.awt.geom.Ellipse2D.Double(x, 14.7, 1.8, 1.8));
                x += 2.8;
            } else {
                int mask = c >= '0' && c <= '9' ? DIGITS[c - '0']
                        : c == '-' ? 8 : c == 'O' ? 119 : c == 'L' ? 82
                        : c == 'E' ? 91 : c == 'r' ? 24 : 0;
                drawDigit(g, x, mask);
                x += 10.0;
            }
        }
        g.setTransform(logical);
        g.setFont(UNIT_FONT);
        g.setColor(CAPTION);
        int unitWidth = g.getFontMetrics().stringWidth(unit);
        g.drawString(unit, (60.0f - unitWidth) / 2.0f, 27.0f);
    }

    private static void drawDigit(java.awt.Graphics2D g, double x, int mask) {
        for (int segment = 0; segment < 7; segment++) {
            g.setColor((mask & (1 << segment)) == 0 ? UNLIT : LIT);
            double sx, sy, sw, sh;
            if (segment == 0 || segment == 3 || segment == 6) {
                sx = x + 1.5; sy = segment == 0 ? 0.0 : segment == 3 ? 7.25 : 14.5;
                sw = 5.5; sh = 1.5;
            } else {
                sx = x + (segment == 1 || segment == 4 ? 0.0 : 7.0);
                sy = segment == 1 || segment == 2 ? 1.5 : 8.75;
                sw = 1.5; sh = 5.75;
            }
            g.fill(new java.awt.geom.RoundRectangle2D.Double(sx, sy, sw, sh, 0.6, 0.6));
        }
    }
}
