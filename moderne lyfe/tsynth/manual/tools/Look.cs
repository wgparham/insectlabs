// Look: draw the detected figure boxes and a coordinate grid over a scan,
// to check figure detection and pick override boxes.
//   Look <scan.png> <figures.tsv> <overrides.tsv> <out.png> [x0 y0 x1 y1]
using System;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
using System.Linq;

static class Look
{
    static void Main(string[] a)
    {
        string name = Path.GetFileNameWithoutExtension(a[0]);
        using (var src = new Bitmap(a[0])) {
            var crop = a.Length >= 8 ? Rectangle.FromLTRB(int.Parse(a[4]), int.Parse(a[5]), int.Parse(a[6]), int.Parse(a[7])) : new Rectangle(0, 0, src.Width, src.Height);
            double scale = Math.Min(1.0, 1600.0 / Math.Max(crop.Width, crop.Height));
            using (var bmp = new Bitmap((int) (crop.Width * scale), (int) (crop.Height * scale)))
            using (var g = Graphics.FromImage(bmp)) {
                g.DrawImage(src, new Rectangle(0, 0, bmp.Width, bmp.Height), crop, GraphicsUnit.Pixel);
                Func<int, float> X = x => (float) ((x - crop.X) * scale), Y = y => (float) ((y - crop.Y) * scale);
                using (var grid = new Pen(Color.FromArgb(90, 0, 120, 255)))
                using (var font = new Font("Arial", 9))
                    for (int v = 0; v < Math.Max(src.Width, src.Height); v += 100) {
                        if (v >= crop.Left && v <= crop.Right) { g.DrawLine(grid, X(v), 0, X(v), bmp.Height); if (v % 500 == 0) g.DrawString(v.ToString(), font, Brushes.Blue, X(v) + 1, 1); }
                        if (v >= crop.Top && v <= crop.Bottom) { g.DrawLine(grid, 0, Y(v), bmp.Width, Y(v)); if (v % 500 == 0) g.DrawString(v.ToString(), font, Brushes.Blue, 1, Y(v) + 1); }
                    }
                Action<string, Color> boxes = (file, c) => {
                    if (!File.Exists(file)) return;
                    using (var pen = new Pen(c, 2))
                        foreach (var r in File.ReadAllLines(file)) {
                            var f = r.Split(new[] { ' ', '\t' }, StringSplitOptions.RemoveEmptyEntries);
                            if (f.Length < 5 || f[0] != name) continue;
                            int x0 = int.Parse(f[1]), y0 = int.Parse(f[2]), x1 = int.Parse(f[3]), y1 = int.Parse(f[4]);
                            g.DrawRectangle(pen, X(x0), Y(y0), X(x1) - X(x0), Y(y1) - Y(y0));
                        }
                };
                boxes(a[1], Color.Red);
                boxes(a[2], Color.LimeGreen);
                bmp.Save(a[3], ImageFormat.Png);
            }
        }
    }
}
