// ManualBuild: turn the scanned TurboSynth manual into an editable source.
//
//   ManualBuild figures <scan-pngs> <ocr-tsv> <out-dir>
//       find figure boxes on every scan -> <out-dir>/figures.tsv
//   ManualBuild draft <scan-pngs> <ocr-tsv> <out-dir> [overrides.tsv] [toc.txt]
//       crop the figures (1-bit), rebuild each book page's text and write
//       <out-dir>/draft.txt plus <out-dir>/figures/*.png
//
// overrides.tsv lines: "<scan> clear" removes a scan's detected figures;
// "<scan> <x0> <y0> <x1> <y1>" adds a figure box (scan pixels).
// toc.txt lists the contents' headings, one per line; side headings that the
// scan cut off on the left are completed from it.
// C# 5 (the compiler that ships with Windows).
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Text.RegularExpressions;

class Line { public int X0, Y0, X1, Y1, Half; public string Text; public bool InFigure; }
class Box { public int X0, Y0, X1, Y1; public List<Box> Parts = new List<Box>(); public string File; public bool Manual; }

static class Program
{
    static byte[] Gray(Bitmap bmp)
    {
        var d = bmp.LockBits(new Rectangle(0, 0, bmp.Width, bmp.Height), ImageLockMode.ReadOnly, PixelFormat.Format24bppRgb);
        var raw = new byte[d.Stride * bmp.Height];
        Marshal.Copy(d.Scan0, raw, 0, raw.Length);
        bmp.UnlockBits(d);
        var g = new byte[bmp.Width * bmp.Height];
        for (int y = 0; y < bmp.Height; ++y) {
            int row = y * d.Stride, o = y * bmp.Width;
            for (int x = 0; x < bmp.Width; ++x) g[o + x] = (byte) ((raw[row + x * 3] + raw[row + x * 3 + 1] * 2 + raw[row + x * 3 + 2]) / 4);
        }
        return g;
    }

    static List<Line> ReadLines(string tsv, out int w, out int h)
    {
        var rows = File.ReadAllLines(tsv, Encoding.UTF8);
        var size = rows[0].Split(' ');
        w = int.Parse(size[1]); h = int.Parse(size[2]);
        var lines = new List<Line>();
        foreach (var r in rows.Skip(1)) {
            var c = r.Split('\t');
            if (c.Length < 6 || c[5].Trim().Length == 0) continue;
            lines.Add(new Line { X0 = int.Parse(c[0]), Y0 = int.Parse(c[1]), X1 = int.Parse(c[2]), Y1 = int.Parse(c[3]), Half = int.Parse(c[4]), Text = c[5] });
        }
        return lines;
    }

    static List<Box> FindFigures(byte[] g, int w, int h, List<Line> lines, bool landscape)
    {
        var dark = new bool[w * h];
        for (int i = 0; i < dark.Length; ++i) dark[i] = g[i] < 100;
        foreach (var l in lines)
            for (int y = Math.Max(0, l.Y0 - 8); y < Math.Min(h, l.Y1 + 8); ++y)
                for (int x = Math.Max(0, l.X0 - 8); x < Math.Min(w, l.X1 + 8); ++x) dark[y * w + x] = false;
        // Binding: columns dark over much of the height. Cleared row by row (cache friendly).
        var colCount = new int[w];
        for (int y = 0; y < h; y += 2) { int o = y * w; for (int x = 0; x < w; ++x) if (g[o + x] < 100) colCount[x]++; }
        var binding = new bool[w];
        for (int x = 0; x < w; ++x) {
            bool spiralZone = landscape && x > w * 0.40 && x < w * 0.60;
            if ((spiralZone && colCount[x] > h * 0.02) || colCount[x] > h * 0.125)
                for (int xx = Math.Max(0, x - 30); xx < Math.Min(w, x + 30); ++xx) binding[xx] = true;
        }
        for (int y = 0; y < h; ++y) { int o = y * w; for (int x = 0; x < w; ++x) if (binding[x]) dark[o + x] = false; }

        // Frames: windows, dialogs, menus and icons have long straight borders;
        // text never has a continuous dark run this long. Found on the
        // unmasked scan so frames around text-filled dialogs still count.
        const int minH = 120, minV = 100;
        for (int y = 0; y < h; ++y) {
            int o = y * w, run = 0;
            for (int x = 0; x <= w; ++x) {
                bool d = x < w && g[o + x] < 110 && !binding[x];
                if (d) { ++run; continue; }
                if (run >= minH && run < w * 0.4) for (int k = x - run; k < x; ++k) dark[o + k] = true;
                run = 0;
            }
        }
        for (int x = 0; x < w; ++x) {
            if (binding[x]) continue;
            int run = 0;
            for (int y = 0; y <= h; ++y) {
                bool d = y < h && g[y * w + x] < 110;
                if (d) { ++run; continue; }
                if (run >= minV && run < h * 0.5) for (int k = y - run; k < y; ++k) dark[k * w + x] = true;
                run = 0;
            }
        }

        const int cell = 8, grow = 3;
        int cw = w / cell, ch = h / cell;
        var cellDark = new int[cw * ch];
        for (int y = 0; y < ch * cell; ++y) { int o = y * w, co = (y / cell) * cw; for (int x = 0; x < cw * cell; ++x) if (dark[o + x]) cellDark[co + x / cell]++; }
        var on = new bool[cw * ch];
        for (int cy = 0; cy < ch; ++cy)
            for (int cx = 0; cx < cw; ++cx) {
                if (cellDark[cy * cw + cx] < 2) continue;
                for (int dy = -grow; dy <= grow; ++dy)
                    for (int dx = -grow; dx <= grow; ++dx) {
                        int yy = cy + dy, xx = cx + dx;
                        if (yy >= 0 && yy < ch && xx >= 0 && xx < cw) on[yy * cw + xx] = true;
                    }
            }
        var seen = new bool[cw * ch];
        var figs = new List<Box>();
        var stack = new Stack<int>();
        for (int start = 0; start < on.Length; ++start) {
            if (!on[start] || seen[start]) continue;
            int x0 = int.MaxValue, y0 = int.MaxValue, x1 = 0, y1 = 0, ink = 0;
            stack.Push(start); seen[start] = true;
            while (stack.Count > 0) {
                int c = stack.Pop(), cx = c % cw, cy = c / cw;
                ink += cellDark[c];
                x0 = Math.Min(x0, cx); y0 = Math.Min(y0, cy); x1 = Math.Max(x1, cx); y1 = Math.Max(y1, cy);
                if (cx > 0 && on[c - 1] && !seen[c - 1]) { seen[c - 1] = true; stack.Push(c - 1); }
                if (cx < cw - 1 && on[c + 1] && !seen[c + 1]) { seen[c + 1] = true; stack.Push(c + 1); }
                if (cy > 0 && on[c - cw] && !seen[c - cw]) { seen[c - cw] = true; stack.Push(c - cw); }
                if (cy < ch - 1 && on[c + cw] && !seen[c + cw]) { seen[c + cw] = true; stack.Push(c + cw); }
            }
            var f = new Box { X0 = (x0 + grow) * cell, Y0 = (y0 + grow) * cell, X1 = (x1 - grow + 1) * cell, Y1 = (y1 - grow + 1) * cell };
            int fw = f.X1 - f.X0, fh = f.Y1 - f.Y0;
            if (fw < 110 || fh < 70 || ink < 500) continue;
            if (fh > h * 0.75 || fw > w * 0.6) continue;
            if (f.X0 < w * 0.01 || f.X1 > w * 0.99) continue;
            f.Parts.Add(new Box { X0 = f.X0, Y0 = f.Y0, X1 = f.X1, Y1 = f.Y1 });
            figs.Add(f);
        }
        for (bool merged = true; merged;) {
            merged = false;
            for (int i = 0; i < figs.Count && !merged; ++i)
                for (int j = i + 1; j < figs.Count && !merged; ++j) {
                    var a = figs[i]; var b = figs[j];
                    if (a.X0 - 20 <= b.X1 && b.X0 - 20 <= a.X1 && a.Y0 - 20 <= b.Y1 && b.Y0 - 20 <= a.Y1) {
                        a.X0 = Math.Min(a.X0, b.X0); a.Y0 = Math.Min(a.Y0, b.Y0); a.X1 = Math.Max(a.X1, b.X1); a.Y1 = Math.Max(a.Y1, b.Y1);
                        a.Parts.AddRange(b.Parts); figs.RemoveAt(j); merged = true;
                    }
                }
        }
        return figs;
    }

    // Text whose centre lies inside one of a figure's detected shapes belongs to it.
    static void Absorb(List<Box> figs, List<Line> lines)
    {
        foreach (var f in figs)
            foreach (var l in lines) {
                int mx = (l.X0 + l.X1) / 2, my = (l.Y0 + l.Y1) / 2;
                var parts = f.Parts.Count > 0 ? f.Parts : new List<Box> { f };
                if (parts.Any(p => mx > p.X0 - 15 && mx < p.X1 + 15 && my > p.Y0 - 15 && my < p.Y1 + 15)) {
                    l.InFigure = true;
                    f.X0 = Math.Min(f.X0, l.X0); f.Y0 = Math.Min(f.Y0, l.Y0); f.X1 = Math.Max(f.X1, l.X1); f.Y1 = Math.Max(f.Y1, l.Y1);
                }
            }
    }

    static void SaveFigure(byte[] g, int w, int h, Box f, string path)
    {
        int pad = 10;
        int x0 = Math.Max(0, f.X0 - pad), y0 = Math.Max(0, f.Y0 - pad), x1 = Math.Min(w, f.X1 + pad), y1 = Math.Min(h, f.Y1 + pad);
        using (var bmp = new Bitmap(x1 - x0, y1 - y0, PixelFormat.Format24bppRgb)) {
            var d = bmp.LockBits(new Rectangle(0, 0, bmp.Width, bmp.Height), ImageLockMode.WriteOnly, PixelFormat.Format24bppRgb);
            var raw = new byte[d.Stride * bmp.Height];
            for (int y = 0; y < bmp.Height; ++y)
                for (int x = 0; x < bmp.Width; ++x) {
                    byte v = g[(y + y0) * w + x + x0] < 150 ? (byte) 0 : (byte) 255;
                    int o = y * d.Stride + x * 3; raw[o] = raw[o + 1] = raw[o + 2] = v;
                }
            Marshal.Copy(raw, 0, d.Scan0, raw.Length);
            bmp.UnlockBits(d);
            bmp.Save(path, ImageFormat.Png);
        }
    }

    static readonly Regex PageNumber = new Regex(@"^\s*\d{1,3}\s*$");
    static readonly Regex Footer = new Regex(@"(?-i:(ection|ction|tion)\s*[\dIl](\s|-|$))|^\s*Introduction\s*$|Table of Contents|^\s*Appendix|^\s*Index\s*$|Manual Addendum|^\s*Digidesign\s*$|^\s*\d{1,3}\s+Digidesign", RegexOptions.IgnoreCase);
    static readonly Regex Bullet = new Regex(@"^(•|·|\*|e\s|o\s)\s*");

    // OCR slips that recur in this scan.
    static string Clean(string s)
    {
        s = Regex.Replace(s, @"\bMIDl\b", "MIDI");
        s = Regex.Replace(s, @"\bMacIs\b", "Mac's");
        s = Regex.Replace(s, @"\b1 6-bit\b", "16-bit");
        s = Regex.Replace(s, @"\b1 2-bit\b", "12-bit");
        s = Regex.Replace(s, @"\b(\d) (\d)(?=\s*(kHz|Hz|bit|K\b))", "$1$2");
        s = s.Replace("couldnt", "couldn't");
        return s;
    }

    // Complete a side heading the scan cut off on the left, from the contents.
    static string Complete(string head, List<string> toc)
    {
        string h = head.Trim();
        if (toc.Count == 0 || h.Length < 3) return h;
        string best = null; double bestScore = 0;
        foreach (var t in toc) {
            if (t.Length < h.Length) continue;
            string tail = t.Substring(t.Length - Math.Min(t.Length, h.Length + 3));
            double score = Similar(h.ToLowerInvariant(), tail.ToLowerInvariant());
            if (t.ToLowerInvariant().EndsWith(h.ToLowerInvariant())) score = 1.0 + h.Length / 100.0;
            if (score > bestScore) { bestScore = score; best = t; }
        }
        return bestScore >= 0.72 ? best : h;
    }

    // Edit-distance similarity of a to the best-matching end of b (free prefix in b).
    static double Similar(string a, string b)
    {
        int[,] d = new int[a.Length + 1, b.Length + 1];
        for (int i = 0; i <= a.Length; ++i) d[i, 0] = i;
        for (int j = 0; j <= b.Length; ++j) d[0, j] = 0;
        for (int i = 1; i <= a.Length; ++i)
            for (int j = 1; j <= b.Length; ++j)
                d[i, j] = Math.Min(Math.Min(d[i - 1, j] + 1, d[i, j - 1] + 1), d[i - 1, j - 1] + (a[i - 1] == b[j - 1] ? 0 : 1));
        return 1.0 - (double) d[a.Length, b.Length] / Math.Max(1, a.Length);
    }

    static void Main(string[] args)
    {
        string mode = args[0], scans = args[1], ocr = args[2], outDir = args[3];
        Directory.CreateDirectory(outDir);
        string figTsv = Path.Combine(outDir, "figures.tsv");
        if (mode == "figures") {
            var sb = new StringBuilder();
            foreach (var tsv in Directory.GetFiles(ocr, "p*.tsv").OrderBy(s => s)) {
                string name = Path.GetFileNameWithoutExtension(tsv);
                int w, h; var lines = ReadLines(tsv, out w, out h);
                List<Box> figs;
                using (var bmp = new Bitmap(Path.Combine(scans, name + ".png"))) figs = FindFigures(Gray(bmp), w, h, lines, w > h);
                foreach (var f in figs) {
                    sb.AppendFormat("{0}\t{1}\t{2}\t{3}\t{4}", name, f.X0, f.Y0, f.X1, f.Y1);
                    foreach (var p in f.Parts) sb.AppendFormat("\t{0},{1},{2},{3}", p.X0, p.Y0, p.X1, p.Y1);
                    sb.Append('\n');
                }
                Console.Write(".");
            }
            File.WriteAllText(figTsv, sb.ToString());
            Console.WriteLine(" figures.tsv written");
            return;
        }

        // ---- draft ----
        var boxes = new Dictionary<string, List<Box>>();
        foreach (var r in File.ReadAllLines(figTsv)) {
            var c = r.Split('\t'); if (c.Length < 5) continue;
            var b = new Box { X0 = int.Parse(c[1]), Y0 = int.Parse(c[2]), X1 = int.Parse(c[3]), Y1 = int.Parse(c[4]) };
            for (int k = 5; k < c.Length; ++k) { var q = c[k].Split(','); b.Parts.Add(new Box { X0 = int.Parse(q[0]), Y0 = int.Parse(q[1]), X1 = int.Parse(q[2]), Y1 = int.Parse(q[3]) }); }
            if (!boxes.ContainsKey(c[0])) boxes[c[0]] = new List<Box>();
            boxes[c[0]].Add(b);
        }
        if (args.Length > 4 && File.Exists(args[4]))
            foreach (var r in File.ReadAllLines(args[4])) {
                var c = r.Split(new[] { ' ', '\t' }, StringSplitOptions.RemoveEmptyEntries);
                if (c.Length < 2 || c[0].StartsWith("#")) continue;
                if (c[1] == "clear") { boxes[c[0]] = new List<Box>(); continue; }
                if (c.Length >= 5) {
                    if (!boxes.ContainsKey(c[0])) boxes[c[0]] = new List<Box>();
                    boxes[c[0]].Add(new Box { X0 = int.Parse(c[1]), Y0 = int.Parse(c[2]), X1 = int.Parse(c[3]), Y1 = int.Parse(c[4]), Manual = true });
                }
            }
        var toc = args.Length > 5 && File.Exists(args[5]) ? File.ReadAllLines(args[5]).Select(s => s.Trim()).Where(s => s.Length > 0).ToList() : new List<string>();
        Directory.CreateDirectory(Path.Combine(outDir, "figures"));
        foreach (var old in Directory.GetFiles(Path.Combine(outDir, "figures"), "*.png")) File.Delete(old);
        var draft = new StringBuilder();
        foreach (var tsv in Directory.GetFiles(ocr, "p*.tsv").OrderBy(s => s)) {
            string name = Path.GetFileNameWithoutExtension(tsv);
            int w, h; var lines = ReadLines(tsv, out w, out h);
            bool landscape = w > h;
            List<Box> figs; boxes.TryGetValue(name, out figs);
            if (figs == null) figs = new List<Box>();
            Absorb(figs, lines);
            if (figs.Count > 0) {
                byte[] g;
                using (var bmp = new Bitmap(Path.Combine(scans, name + ".png"))) g = Gray(bmp);
                int fi = 0; char fm = (char) 96;
                foreach (var f in figs.Where(f => !f.Manual).OrderBy(f => f.Y0).Concat(figs.Where(f => f.Manual))) { f.File = f.Manual ? string.Format("figures/{0}_{1}.png", name, ++fm) : string.Format("figures/{0}_{1}.png", name, ++fi); SaveFigure(g, w, h, f, Path.Combine(outDir, f.File)); }
            }
            for (int side = 0; side < (landscape ? 2 : 1); ++side) {
                int left = landscape ? side * w / 2 : 0, right = landscape ? (side + 1) * w / 2 : w;
                var pl = lines.Where(l => !landscape || l.Half == side).ToList();
                var pf = figs.Where(f => (f.X0 + f.X1) / 2 >= left && (f.X0 + f.X1) / 2 < right).ToList();
                string pageNo = "", footer = "";
                var body = new List<Line>();
                foreach (var l in pl.Where(l => !l.InFigure)) {
                    if (PageNumber.IsMatch(l.Text)) { if (l.Y0 > h * 0.70) pageNo = l.Text.Trim(); continue; }
                    if (l.Y0 > h * 0.80 && Footer.IsMatch(l.Text)) { footer = l.Text.Trim(); continue; }
                    body.Add(l);
                }
                if (body.Count == 0 && pf.Count == 0) continue;
                int bodyX = body.Count == 0 ? left : body.GroupBy(l => (l.X0 / 40) * 40).OrderByDescending(gp => gp.Count()).First().Key;
                draft.AppendFormat("\n@page {0} scan={1}{2} footer=\"{3}\"\n\n", pageNo, name, landscape ? (side == 0 ? "L" : "R") : "", footer);
                var heads = body.Where(l => l.X1 < bodyX - 30).OrderBy(l => l.Y0).ToList();
                var text = body.Where(l => l.X1 >= bodyX - 30).OrderBy(l => l.Y0).ToList();
                int lineH = text.Count > 0 ? text.Select(l => l.Y1 - l.Y0).OrderBy(v => v).ElementAt(text.Count / 2) : 50;
                var paras = new List<Tuple<int, string>>();
                StringBuilder para = null; int paraY = 0; Line prev = null;
                foreach (var l in text) {
                    string t = Clean(l.Text.Trim());
                    bool bullet = Bullet.IsMatch(t) && t.Length > 3;
                    bool newPara = prev == null || l.Y0 - prev.Y1 > lineH * 0.55 || bullet;
                    if (newPara) {
                        if (para != null) paras.Add(Tuple.Create(paraY, para.ToString()));
                        para = new StringBuilder(bullet ? "- " + Bullet.Replace(t, "") : t);
                        paraY = l.Y0;
                    } else {
                        string s = para.ToString();
                        bool hyphen = s.Length > 1 && (s.EndsWith("-") || s.EndsWith("—")) && char.IsLetter(s[s.Length - 2]) && t.Length > 0 && char.IsLower(t[0]);
                        if (hyphen) { para.Length -= 1; para.Append(t); } else para.Append(' ').Append(t);
                    }
                    prev = l;
                }
                if (para != null) paras.Add(Tuple.Create(paraY, para.ToString()));
                // A side heading goes before the paragraph whose first line is nearest to it.
                var items = new List<Tuple<double, string>>();
                foreach (var p in paras) items.Add(Tuple.Create((double) p.Item1, p.Item2));
                for (int i = 0; i < heads.Count;) {
                    var hb = new StringBuilder(heads[i].Text.Trim());
                    int y = heads[i].Y0, j = i + 1;
                    while (j < heads.Count && heads[j].Y0 - heads[j - 1].Y1 < 30) { hb.Append(' ').Append(heads[j].Text.Trim()); ++j; }
                    string head = Complete(Clean(hb.ToString()), toc);
                    double at = y - 0.5;
                    var near = paras.OrderBy(p => Math.Abs(p.Item1 - y)).FirstOrDefault();
                    if (near != null && Math.Abs(near.Item1 - y) < lineH * 3) at = near.Item1 - 0.5;
                    items.Add(Tuple.Create(at, "## " + head));
                    i = j;
                }
                foreach (var f in pf) items.Add(Tuple.Create((double) f.Y0, string.Format("![]({0}) {{w={1}}}", f.File, f.X1 - f.X0)));
                foreach (var it in items.OrderBy(it => it.Item1)) draft.Append(it.Item2).Append("\n\n");
            }
        }
        File.WriteAllText(Path.Combine(outDir, "draft.txt"), draft.ToString(), new UTF8Encoding(false));
        Console.WriteLine("draft written, figures: {0}", Directory.GetFiles(Path.Combine(outDir, "figures")).Length);
    }
}
