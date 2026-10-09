from __future__ import annotations

import json
from pathlib import Path

from PIL import Image, ImageDraw, ImageFont


REPO = Path(__file__).resolve().parents[1]
COLLECTION = REPO / "voltage-modular" / "Wardenclyffe Station"
INDEX = COLLECTION / "CANONICAL.json"
OUTPUT = COLLECTION / "Lineup-2026-10-09.png"

GROUPS = [
    (
        "GENERATORS & MODULATORS",
        ["burstgen", "sinrnd", "generator", "deeptone", "n01", "function"],
    ),
    (
        "PROCESSING & SWITCHING",
        ["following", "c2-34", "signal-processor", "sherlock", "sw1", "sw2"],
    ),
    (
        "ROUTING & INSTRUMENTATION",
        ["faderdistr-a", "faderdistr-b", "sn-16u", "selectiveService", "lm-21", "rm1010"],
    ),
    ("RECORDING & PLAYBACK", ["model-62"]),
]

WIDTH = 3840
MARGIN = 88
GAP = 22
TOP = 190
CARD_W = (WIDTH - 2 * MARGIN - 5 * GAP) // 6
CARD_H = 520
ROW_GAP = 62
IMAGE_BOX = (CARD_W - 36, 392)
FOOTER = 100


def load_font(size: int, bold: bool = False) -> ImageFont.FreeTypeFont | ImageFont.ImageFont:
    candidates = (
        [r"C:\Windows\Fonts\bahnschrift.ttf", r"C:\Windows\Fonts\arialbd.ttf"]
        if bold
        else [r"C:\Windows\Fonts\bahnschrift.ttf", r"C:\Windows\Fonts\arial.ttf"]
    )
    for candidate in candidates:
        if Path(candidate).exists():
            return ImageFont.truetype(candidate, size)
    return ImageFont.load_default()


def main() -> None:
    index = json.loads(INDEX.read_text(encoding="utf-8"))
    title_font = load_font(54, bold=True)
    subtitle_font = load_font(25)
    section_font = load_font(22, bold=True)
    name_font = load_font(19, bold=True)
    version_font = load_font(17)

    height = TOP + len(GROUPS) * CARD_H + (len(GROUPS) - 1) * ROW_GAP + FOOTER
    canvas = Image.new("RGB", (WIDTH, height), "#141516")
    draw = ImageDraw.Draw(canvas)

    draw.text((MARGIN, 42), "INSECT LABORATORIES  /  WARDENCLYFFE STATION", font=title_font, fill="#eee9df")
    draw.text((MARGIN, 112), "19 CANONIZED INSTRUMENTS  ·  9 OCTOBER 2026", font=subtitle_font, fill="#aaa59c")
    draw.line((MARGIN, 164, WIDTH - MARGIN, 164), fill="#514b40", width=2)

    for row, (group_name, keys) in enumerate(GROUPS):
        row_y = TOP + row * (CARD_H + ROW_GAP)
        draw.text((MARGIN, row_y - 38), group_name, font=section_font, fill="#c8a95f")
        for column, key in enumerate(keys):
            entry = index[key]
            x_offset = (6 - len(keys)) * (CARD_W + GAP) // 2
            x = MARGIN + x_offset + column * (CARD_W + GAP)
            y = row_y
            draw.rounded_rectangle(
                (x, y, x + CARD_W, y + CARD_H),
                radius=10,
                fill="#1b1c1d",
                outline="#373532",
                width=2,
            )

            hero_files = sorted((COLLECTION / entry["path"]).glob("*hero.png"))
            if len(hero_files) != 1:
                raise ValueError(f"Expected one hero panel for {key}, found {hero_files}")
            with Image.open(hero_files[0]) as source:
                panel = source.convert("RGBA")
            panel.thumbnail(IMAGE_BOX, Image.Resampling.LANCZOS)
            px = x + (CARD_W - panel.width) // 2
            py = y + 16 + (IMAGE_BOX[1] - panel.height) // 2
            canvas.paste(panel, (px, py), panel)

            words = entry["name"].split()
            lines: list[str] = []
            line = ""
            for word in words:
                candidate = f"{line} {word}".strip()
                if line and draw.textbbox((0, 0), candidate, font=name_font)[2] > CARD_W - 28:
                    lines.append(line)
                    line = word
                else:
                    line = candidate
            if line:
                lines.append(line)
            if len(lines) > 2:
                raise ValueError(f"Product name does not fit on two lines: {entry['name']}")
            label_y = y + CARD_H - (78 if len(lines) == 2 else 59)
            draw.multiline_text(
                (x + CARD_W / 2, label_y),
                "\n".join(lines),
                font=name_font,
                fill="#ece8df",
                anchor="mt",
                align="center",
                spacing=2,
            )
            draw.text(
                (x + CARD_W / 2, y + CARD_H - 34),
                f"CANONICAL  v{entry['version']}",
                font=version_font,
                fill="#aaa59c",
                anchor="mt",
            )

    draw.text(
        (MARGIN, height - 53),
        "VOLTAGE MODULAR  ·  MONO-FIRST ELECTRONIC INSTRUMENTS",
        font=version_font,
        fill="#8b877f",
    )
    canvas.save(OUTPUT, optimize=True)
    print(f"Created {OUTPUT} ({canvas.width} × {canvas.height})")


if __name__ == "__main__":
    main()
