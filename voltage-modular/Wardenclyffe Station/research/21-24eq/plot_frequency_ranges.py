from __future__ import annotations

import sys
from pathlib import Path

import numpy as np
from PIL import Image, ImageDraw, ImageFont

from model_passive_network import BASE, DARK, SHIFT_TEST, db, response


OUT = Path(__file__).with_name("response-ranges.png")
FREQ = np.geomspace(10, 20_000, 900)
RANGES = {
    "POSITION 1 · REFERENCE": BASE,
    "POSITION 2 · DARK": DARK,
    "POSITION 3 · EXTRA DARK": SHIFT_TEST,
}
CURVES = {
    "Both controls centered": (0.5, 0.5, "#d8d0bb"),
    "Bass boost": (0.0, 0.5, "#d3a34a"),
    "Bass cut": (1.0, 0.5, "#936e29"),
    "Treble boost": (0.5, 0.0, "#77b7c7"),
    "Treble cut": (0.5, 1.0, "#47717b"),
}
WIDTH, HEIGHT = 1800, 1980
BG, PANEL, GRID = "#171819", "#1e2021", "#414243"
TEXT, MUTED = "#eee9df", "#aaa59c"


def font(size: int) -> ImageFont.FreeTypeFont | ImageFont.ImageFont:
    path = r"C:\Windows\Fonts\bahnschrift.ttf"
    return ImageFont.truetype(path, size) if Path(path).exists() else ImageFont.load_default()


def main() -> None:
    image = Image.new("RGB", (WIDTH, HEIGHT), BG)
    draw = ImageDraw.Draw(image)
    draw.text((70, 38), "21-24eq  /  PASSIVE THREE-POSITION RESPONSE", font=font(44), fill=TEXT)
    draw.text((72, 98), "NODAL CIRCUIT MODEL  ·  1 kΩ SOURCE  ·  100 kΩ OUTPUT LOAD  ·  NO MAKE-UP GAIN", font=font(21), fill=MUTED)
    f_min, f_max, db_min, db_max = 10.0, 20_000.0, -45.0, 0.0
    x0, panel_w, panel_h = 74, 1652, 530
    y0s = (164, 730, 1296)
    for panel_index, (title, caps) in enumerate(RANGES.items()):
        x, y = x0, y0s[panel_index]
        draw.rounded_rectangle((x, y, x + panel_w, y + panel_h), radius=12, fill=PANEL, outline="#383a3b", width=2)
        draw.text((x + 28, y + 20), title, font=font(26), fill=TEXT)
        left, top, plot_w, plot_h = x + 72, y + 74, panel_w - 100, panel_h - 152

        def xy(freq: float, level: float) -> tuple[float, float]:
            xf = (np.log10(freq) - np.log10(f_min)) / (np.log10(f_max) - np.log10(f_min))
            yf = (db_max - level) / (db_max - db_min)
            return left + xf * plot_w, top + yf * plot_h

        for level in (0, -1, -3, -10, -20, -30, -40):
            gy = xy(f_min, level)[1]
            draw.line((left, gy, left + plot_w, gy), fill=GRID, width=2 if level == -20 else 1)
            draw.text((x + 14, gy - 9), f"{level:g}", font=font(15), fill=MUTED)
        for hz in (10, 20, 50, 100, 200, 500, 1_000, 2_000, 5_000, 10_000, 20_000):
            gx = xy(hz, -20)[0]
            draw.line((gx, top, gx, top + plot_h), fill=GRID, width=1)
            draw.text((gx, top + plot_h + 8), f"{hz/1000:g}k" if hz >= 1_000 else str(hz), font=font(14), fill=MUTED, anchor="mt")
        for name, (bass, treble, color) in CURVES.items():
            values = np.clip(db(response(FREQ, caps, bass, treble)), db_min, db_max)
            points = [xy(float(f), float(level)) for f, level in zip(FREQ, values)]
            draw.line(points, fill=color, width=4 if name == "Both controls centered" else 3)
        legend_y = y + panel_h - 42
        for idx, (name, (_, _, color)) in enumerate(CURVES.items()):
            lx = x + 30 + idx * 300
            draw.line((lx, legend_y + 10, lx + 23, legend_y + 10), fill=color, width=3)
            label = name.replace("Both controls centered", "Center")
            draw.text((lx + 29, legend_y), label, font=font(14), fill=TEXT)

    draw.text((76, 1860), "Knobs: counterclockwise = cut; clockwise = boost.", font=font(20), fill=TEXT)
    draw.text((76, 1900), "FREQ switch progression: reference > dark > extra dark.", font=font(20), fill=MUTED)
    draw.text((76, 1940), "Calculated response only; input coupling, overload color, component tolerance, and host-level behavior are not included.", font=font(20), fill="#d9a36d")
    image.save(OUT, optimize=True)
    print(OUT)


if __name__ == "__main__":
    main()
