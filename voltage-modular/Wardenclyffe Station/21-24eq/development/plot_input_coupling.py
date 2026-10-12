from __future__ import annotations

from pathlib import Path

import numpy as np
from PIL import Image, ImageDraw, ImageFont

from model_passive_network import BASE, db, response


OUT = Path(__file__).with_name("input-coupling.png")
WIDTH, HEIGHT = 1500, 900
BG, PANEL, GRID = "#171819", "#1e2021", "#414243"
TEXT, MUTED = "#eee9df", "#aaa59c"
FREQ = np.geomspace(5, 2_000, 800)
CASES = (("No added coupling", 0.0, "#d8d0bb"),
         ("220 nF candidate", 220e-9, "#d3a34a"),
         ("100 nF comparison", 100e-9, "#77b7c7"))


def font(size: int) -> ImageFont.FreeTypeFont | ImageFont.ImageFont:
    path = r"C:\Windows\Fonts\bahnschrift.ttf"
    return ImageFont.truetype(path, size) if Path(path).exists() else ImageFont.load_default()


def main() -> None:
    image = Image.new("RGB", (WIDTH, HEIGHT), BG)
    draw = ImageDraw.Draw(image)
    draw.text((64, 34), "21-24eq  /  INPUT COUPLING CHECK", font=font(40), fill=TEXT)
    draw.text((66, 88), "REFERENCE CAPS · CONTROLS CENTERED · 1 kΩ SOURCE · 100 kΩ OUTPUT LOAD", font=font(19), fill=MUTED)
    draw.rounded_rectangle((62, 140, 1438, 805), radius=12, fill=PANEL, outline="#383a3b", width=2)
    left, top, plot_w, plot_h = 130, 185, 1255, 520
    f_min, f_max, db_min, db_max = 5, 2_000, -35, -5

    def xy(hz: float, level: float) -> tuple[float, float]:
        x = (np.log10(hz) - np.log10(f_min)) / (np.log10(f_max) - np.log10(f_min))
        y = (db_max - level) / (db_max - db_min)
        return left + x * plot_w, top + y * plot_h

    for level in (-5, -10, -15, -20, -25, -30, -35):
        y = xy(f_min, level)[1]
        draw.line((left, y, left + plot_w, y), fill=GRID, width=2 if level == -20 else 1)
        draw.text((76, y - 10), str(level), font=font(16), fill=MUTED)
    for hz in (5, 10, 20, 30, 50, 100, 200, 500, 1_000, 2_000):
        x = xy(hz, -20)[0]
        draw.line((x, top, x, top + plot_h), fill=GRID, width=1)
        draw.text((x, top + plot_h + 10), f"{hz/1000:g}k" if hz >= 1_000 else str(hz), font=font(15), fill=MUTED, anchor="mt")
    for label, coupling, color in CASES:
        values = db(response(FREQ, BASE, 0.5, 0.5, input_coupling=coupling))
        points = [xy(float(hz), float(level)) for hz, level in zip(FREQ, values)]
        draw.line(points, fill=color, width=4)
    for index, (label, _, color) in enumerate(CASES):
        x = 148 + index * 365
        draw.line((x, 758, x + 36, 758), fill=color, width=4)
        draw.text((x + 48, 746), label, font=font(20), fill=TEXT)
    draw.text((65, 840), "220 nF adds a gentle sub-bass rolloff under this load model; 100 nF is more pronounced. Candidate only.", font=font(19), fill="#d9a36d")
    image.save(OUT, optimize=True)
    print(OUT)


if __name__ == "__main__":
    main()
