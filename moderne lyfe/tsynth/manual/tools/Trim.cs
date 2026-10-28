// Trim: tidy figure crops in place. Drops thin strokes along the edges that
// are separated from the picture by white space (the page's column rules,
// slivers of neighbouring art), then trims the white border.
//   Trim <dir-or-png>...
using System;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
using System.Linq;

static class Trim
{
    // Keep the span [lo, hi) of profile indices that holds the picture: split the
    // profile at runs of at least `gap` empty entries and drop thin outer pieces.
    static void Span(int[] prof, int gap, int thin, out int lo, out int hi)
    {
        var segs = new System.Collections.Generic.List<int[]>();
        int start = -1, blank = 0;
        for (int i = 0; i <= prof.Length; ++i) {
            bool ink = i < prof.Length && prof[i] > 0;
            if (ink) { if (start < 0) start = i; blank = 0; }
            else if (start >= 0 && (++blank >= gap || i == prof.Length)) { segs.Add(new[] { start, i - blank + 1 }); start = -1; blank = 0; }
        }
        while (segs.Count > 1 && segs[0][1] - segs[0][0] <= thin) segs.RemoveAt(0);
        while (segs.Count > 1 && segs[segs.Count - 1][1] - segs[segs.Count - 1][0] <= thin) segs.RemoveAt(segs.Count - 1);
        lo = segs.Count > 0 ? segs[0][0] : 0; hi = segs.Count > 0 ? segs[segs.Count - 1][1] : prof.Length;
    }

    static void Fix(string path)
    {
        Bitmap src;
        using (var f = new Bitmap(path)) src = new Bitmap(f);
        int w = src.Width, h = src.Height;
        var dark = new bool[w, h];
        for (int y = 0; y < h; ++y) for (int x = 0; x < w; ++x) dark[x, y] = src.GetPixel(x, y).GetBrightness() < 0.5f;
        var cols = new int[w]; var rows = new int[h];
        for (int y = 0; y < h; ++y) for (int x = 0; x < w; ++x) if (dark[x, y]) { cols[x]++; rows[y]++; }
        int x0, x1, y0, y1;
        Span(cols, 5, 8, out x0, out x1);
        Span(rows, 5, 8, out y0, out y1);
        int pad = 4;
        x0 = Math.Max(0, x0 - pad); y0 = Math.Max(0, y0 - pad); x1 = Math.Min(w, x1 + pad); y1 = Math.Min(h, y1 + pad);
        if (x0 == 0 && y0 == 0 && x1 == w && y1 == h) { src.Dispose(); return; }
        using (var dst = src.Clone(new Rectangle(x0, y0, x1 - x0, y1 - y0), src.PixelFormat)) { src.Dispose(); dst.Save(path, ImageFormat.Png); }
        Console.WriteLine("{0}: {1}x{2} -> {3}x{4}", Path.GetFileName(path), w, h, x1 - x0, y1 - y0);
    }

    static void Main(string[] args)
    {
        foreach (var a in args)
            foreach (var p in Directory.Exists(a) ? Directory.GetFiles(a, "*.png") : new[] { a }) Fix(p);
    }
}
