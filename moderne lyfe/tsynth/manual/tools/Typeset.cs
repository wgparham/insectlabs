// Typeset: turn the edited manual source into a print-ready HTML file
// (printed to PDF with Edge).
//   Typeset <source/manual.txt> <out.html>
//
// Source format:
//   @part Title {img=x.png} start a part (title page, optional picture)
//   # Title                chapter (new page)
//   ## Heading             side heading
//   ### Heading            heading inside the text column
//   - item                 bullet
//   1. item                numbered item
//   > text                 note / tip (boxed)
//   ![cap](figures/x.png) {w=NNN ppi=NNN}   figure, NNN = width in scan pixels;
//                          small uncaptioned figures (icons) sit in the side column
//   @menu Title | Item ^K | ~Dimmed | √Checked | ---   a Macintosh menu;
//                          with a single item, a menu pick in the side column
//   @include file.html     raw HTML from a file next to the source
//   @front ... @endfront   front matter (before the contents)
//   @page ...              original page marker (ignored)
//   blank line             paragraph break
// Inline: **bold**, *italic*.
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Text.RegularExpressions;

static class Typeset
{
    static string Inline(string s)
    {
        s = WebUtility.HtmlEncode(s);
        s = Regex.Replace(s, @"\*\*(.+?)\*\*", "<b>$1</b>");
        s = Regex.Replace(s, @"(?<![\w*])\*(?!\s)(.+?)(?<!\s)\*(?![\w*])", "<i>$1</i>");
        s = s.Replace("--", "&#8211;").Replace("-&gt;", "&#8594;");
        s = Regex.Replace(s, @"(\w) - (\w)", "$1 &#8211; $2");
        return s;
    }

    static string Slug(string s) { return Regex.Replace(s.ToLowerInvariant(), @"[^a-z0-9]+", "-").Trim('-'); }

    static string Menu(string spec)
    {
        var parts = spec.Split('|').Select(p => p.Trim()).ToList();
        var items = parts.Skip(1).ToList();
        var sb = new StringBuilder();
        if (items.Count == 1) {
            sb.AppendFormat("<div class=\"menupick\"><span class=\"mtitle\">{0}</span><span class=\"mitem\">{1}</span></div>\n", Inline(parts[0]), Inline(items[0]));
            return sb.ToString();
        }
        sb.AppendFormat("<div class=\"menu\"><div class=\"mtitle\">{0}</div><table>", Inline(parts[0]));
        foreach (var it in items) {
            if (it == "---") { sb.Append("<tr class=\"sep\"><td colspan=\"3\"></td></tr>"); continue; }
            string t = it, cls = "", mark = "", key = "";
            if (t.StartsWith("~")) { cls = " class=\"dim\""; t = t.Substring(1); }
            if (t.StartsWith("√")) { mark = "&#10003;"; t = t.Substring(1); }
            var k = Regex.Match(t, @"\s\^(\S)$");
            if (k.Success) { key = "&#8984;" + k.Groups[1].Value; t = t.Substring(0, k.Index); }
            sb.AppendFormat("<tr{0}><td class=\"mk\">{1}</td><td>{2}</td><td class=\"key\">{3}</td></tr>", cls, mark, Inline(t), key);
        }
        sb.Append("</table></div>\n");
        return sb.ToString();
    }

    static void Main(string[] args)
    {
        string dir = Path.GetDirectoryName(Path.GetFullPath(args[0]));
        var src = File.ReadAllLines(args[0], Encoding.UTF8);
        var body = new StringBuilder();
        var toc = new List<Tuple<int, string, string>>(); // level, title, id
        var used = new HashSet<string>();
        Func<string, string> id = t => { string b = Slug(t), s = b; for (int n = 2; used.Contains(s); ++n) s = b + "-" + n; used.Add(s); return s; };
        var para = new List<string>();
        string listTag = null;
        Action flush = () => {
            if (para.Count > 0) { body.Append("<p>").Append(Inline(string.Join(" ", para))).Append("</p>\n"); para.Clear(); }
        };
        Action endList = () => { if (listTag != null) { body.Append("</" + listTag + ">\n"); listTag = null; } };
        Action<string> list = tag => { flush(); if (listTag != tag) { endList(); body.Append("<" + tag + ">\n"); listTag = tag; } };
        bool chapterOpen = false;
        Action closeChapter = () => { flush(); endList(); if (chapterOpen) body.Append("</section>\n"); chapterOpen = false; };
        var front = new StringBuilder();
        StringBuilder saved = null;
        foreach (var raw in src) {
            string line = raw.TrimEnd();
            if (line == "@front") { flush(); saved = body; body = front; continue; }
            if (line == "@endfront") { flush(); endList(); body = saved; saved = null; continue; }
            if (line.StartsWith("@page")) { flush(); continue; }
            if (line.StartsWith("@part ")) {
                closeChapter();
                var pm = Regex.Match(line.Substring(6), @"^(.*?)\s*(\{img=(.+?)\})?$");
                string t = pm.Groups[1].Value.Trim(), i = id(t);
                toc.Add(Tuple.Create(0, t, i));
                body.AppendFormat("<section class=\"part\" id=\"{0}\">{2}<h1 class=\"part-title\">{1}</h1></section>\n", i, Inline(t),
                    pm.Groups[3].Success ? "<img class=\"part-img\" src=\"" + pm.Groups[3].Value + "\" alt=\"\">" : "");
                continue;
            }
            if (line.StartsWith("# ")) {
                closeChapter();
                string t = line.Substring(2).Trim(), i = id(t);
                toc.Add(Tuple.Create(1, t, i));
                body.AppendFormat("<section class=\"chapter\"><h1 id=\"{0}\">{1}</h1>\n", i, Inline(t));
                chapterOpen = true;
                continue;
            }
            if (line.StartsWith("## ")) {
                flush(); endList();
                string t = line.Substring(3).Trim(), i = id(t);
                if (saved == null) toc.Add(Tuple.Create(2, t, i));
                body.AppendFormat("<h2 id=\"{0}\">{1}</h2>\n", i, Inline(t));
                continue;
            }
            if (line.StartsWith("### ")) { flush(); endList(); body.AppendFormat("<h3>{0}</h3>\n", Inline(line.Substring(4).Trim())); continue; }
            if (line.StartsWith("@menu ")) { flush(); endList(); body.Append(Menu(line.Substring(6))); continue; }
            if (line.StartsWith("@include ")) { flush(); endList(); body.Append(File.ReadAllText(Path.Combine(dir, line.Substring(9).Trim()), Encoding.UTF8)); continue; }
            var fig = Regex.Match(line, @"^!\[(.*?)\]\((.+?)\)\s*(\{(.*)\})?");
            if (fig.Success) {
                flush(); endList();
                double wpx = 0, ppi = 433;
                var wm = Regex.Match(fig.Groups[4].Value, @"w=(\d+)"); if (wm.Success) wpx = double.Parse(wm.Groups[1].Value);
                var pm = Regex.Match(fig.Groups[4].Value, @"ppi=(\d+)"); if (pm.Success) ppi = double.Parse(pm.Groups[1].Value);
                string imgPath = Path.Combine(dir, fig.Groups[2].Value);
                if (File.Exists(imgPath)) using (var im = System.Drawing.Image.FromFile(imgPath)) wpx = im.Width; // crops are at scan scale
                string cap = fig.Groups[1].Value;
                bool icon = cap.Length == 0 && wpx > 0 && wpx / ppi <= 0.62;
                string style = wpx > 0 ? string.Format(" style=\"width:{0:0.00}in\"", Math.Min(icon ? 0.6 : 5.1, wpx / ppi * (icon ? 1.0 : 1.6))) : "";
                body.AppendFormat("<figure{4}><img src=\"{0}\"{1} alt=\"{2}\">{3}</figure>\n", fig.Groups[2].Value, style, WebUtility.HtmlEncode(cap),
                    cap.Length > 0 ? "<figcaption>" + Inline(cap) + "</figcaption>" : "", icon ? " class=\"icon\"" : "");
                continue;
            }
            if (line.StartsWith("- ")) { list("ul"); body.AppendFormat("<li>{0}</li>\n", Inline(line.Substring(2))); continue; }
            var num = Regex.Match(line, @"^\d+\.\s+(.*)$");
            if (num.Success) { list("ol"); body.AppendFormat("<li>{0}</li>\n", Inline(num.Groups[1].Value)); continue; }
            if (line.StartsWith("> ")) { flush(); endList(); body.AppendFormat("<aside>{0}</aside>\n", Inline(line.Substring(2))); continue; }
            if (line.Trim().Length == 0) { flush(); endList(); continue; }
            endList();
            para.Add(line.Trim());
        }
        closeChapter();

        var tocHtml = new StringBuilder("<nav class=\"toc\"><h1>Contents</h1>\n");
        foreach (var t in toc)
            tocHtml.AppendFormat("<a class=\"l{0}\" href=\"#{1}\">{2}</a>\n", t.Item1, t.Item3, Inline(t.Item2));
        tocHtml.Append("</nav>\n");

        string template = File.ReadAllText(Path.Combine(dir, "template.html"), Encoding.UTF8);
        File.WriteAllText(args[1], template.Replace("{{FRONT}}", front.Length > 0 ? "<div class=\"front\">" + front + "</div>" : "").Replace("{{TOC}}", tocHtml.ToString()).Replace("{{BODY}}", body.ToString()), new UTF8Encoding(false));
        Console.WriteLine("{0} headings", toc.Count);
    }
}
