from __future__ import annotations

from pathlib import Path

import numpy as np
from PIL import Image, ImageDraw, ImageFont


OUT = Path(__file__).with_name("response-candidates.png")
WIDTH, HEIGHT = 1800, 1120
BG, PANEL, GRID = "#171819", "#1e2021", "#414243"
TEXT, MUTED = "#eee9df", "#aaa59c"
COLORS = {
    "Level response": "#d8d0bb",
    "Bass boost": "#d3a34a",
    "Bass cut": "#936e29",
    "Treble boost": "#77b7c7",
    "Treble cut": "#47717b",
}

# Approximate points read from the supplied passive-tone-control response figure.
# They communicate the network's insertion loss and broad control shape; they are
# not measurements of 21-24eq and should be replaced by a circuit-derived model.
KNOTS_HZ = np.array([10, 20, 50, 100, 200, 500, 1_000, 2_000, 5_000, 10_000, 20_000], dtype=float)
CURVES_DB = {
    "Level response": [-20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20],
    "Bass boost": [-2, -2, -3, -5, -10, -17, -20, -20, -20, -20, -20],
    "Bass cut": [-40, -38, -36, -32, -27, -22, -20, -20, -20, -20, -20],
    "Treble boost": [-20, -20, -20, -20, -20, -20, -20, -17, -10, -5, -2],
    "Treble cut": [-20, -20, -20, -20, -20, -20, -20, -22, -30, -37, -43],
}


def load_font(size: int, bold: bool = False) -> ImageFont.FreeTypeFont | ImageFont.ImageFont:
    path = r"C:\Windows\Fonts\bahnschrift.ttf"
    if Path(path).exists():
        return ImageFont.truetype(path, size)
    return ImageFont.load_default()


def main() -> None:
    image = Image.new("RGB", (WIDTH, HEIGHT), BG)
    draw = ImageDraw.Draw(image)
    title = load_font(46, True)
    subtitle = load_font(22)
    label = load_font(21, True)
    tick = load_font(18)
    draw.text((72, 42), "21-24eq  /  PASSIVE TONE-CONTROL REFERENCE", font=title, fill=TEXT)
    draw.text((74, 105), "QUALITATIVE REDRAW  ·  APPROX. MID-BAND INSERTION LOSS  ·  NOT A 21-24eq MEASUREMENT", font=subtitle, fill=MUTED)

    x, y, w, h = 78, 190, 1644, 710
    draw.rounded_rectangle((x, y, x + w, y + h), radius=12, fill=PANEL, outline="#383a3b", width=2)
    left, top, plot_w, plot_h = x + 92, y + 55, w - 130, h - 125
    f_min, f_max = 10.0, 20_000.0
    db_min, db_max = -45.0, 0.0
    frequencies = np.geomspace(f_min, f_max, 900)
    x_knots = np.log10(KNOTS_HZ)

    def xy(freq: float, db: float) -> tuple[float, float]:
        xf = (np.log10(freq) - np.log10(f_min)) / (np.log10(f_max) - np.log10(f_min))
        yf = (db_max - db) / (db_max - db_min)
        return left + xf * plot_w, top + yf * plot_h

    for db in (0, -1, -3, -10, -20, -30, -40):
        gy = xy(f_min, db)[1]
        draw.line((left, gy, left + plot_w, gy), fill=GRID, width=2 if db == -20 else 1)
        draw.text((x + 22, gy - 10), f"{db:g}", font=tick, fill=MUTED)
    for freq in (10, 20, 50, 100, 200, 500, 1_000, 2_000, 5_000, 10_000, 20_000):
        gx = xy(freq, -20)[0]
        draw.line((gx, top, gx, top + plot_h), fill=GRID, width=1)
        tick_label = f"{freq / 1000:g}k" if freq >= 1000 else f"{freq:g}"
        draw.text((gx, top + plot_h + 10), tick_label, font=tick, fill=MUTED, anchor="mt")

    for name, samples in CURVES_DB.items():
        values = np.interp(np.log10(frequencies), x_knots, np.asarray(samples, dtype=float))
        points = [xy(float(f), float(v)) for f, v in zip(frequencies, values)]
        color = COLORS[name]
        if name in ("Bass cut", "Treble cut"):
            for i in range(0, len(points) - 1, 14):
                draw.line(points[i : i + 8], fill=color, width=3)
        else:
            draw.line(points, fill=color, width=4 if name == "Level response" else 3)

    legend_y = y + h - 35
    legend_xs = (x + 34, x + 338, x + 650, x + 957, x + 1265)
    for (name, color), legend_x in zip(COLORS.items(), legend_xs):
        draw.line((legend_x, legend_y + 10, legend_x + 28, legend_y + 10), fill=color, width=3)
        draw.text((legend_x + 36, legend_y), name, font=tick, fill=TEXT)

    draw.text((78, 947), "Source figure describes roughly -20 dB mid-band loss; full boost approaches -1 to -3 dB at its band edge.", font=subtitle, fill=TEXT)
    draw.text((78, 990), "A passive network's exact response depends on component values, source impedance, and output loading.", font=subtitle, fill=MUTED)
    draw.text((78, 1034), "No make-up gain is included here. The FREQ ranges and final network values remain to be designed.", font=subtitle, fill="#d9a36d")
    image.save(OUT, optimize=True)
    print(OUT)


if __name__ == "__main__":
    main()
