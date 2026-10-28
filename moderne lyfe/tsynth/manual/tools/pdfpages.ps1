# Render the pages of a PDF to PNGs with the Windows PDF renderer, then tile
# them into contact sheets for checking.
#   pdfpages.ps1 <file.pdf> <outdir> [width] [perSheet]
param([string]$Pdf, [string]$Out, [int]$Width = 850, [int]$PerSheet = 6)

Add-Type -AssemblyName System.Runtime.WindowsRuntime
Add-Type -AssemblyName System.Drawing
$null = [Windows.Data.Pdf.PdfDocument, Windows.Data.Pdf, ContentType = WindowsRuntime]
$null = [Windows.Storage.StorageFile, Windows.Storage, ContentType = WindowsRuntime]
$null = [Windows.Storage.Streams.InMemoryRandomAccessStream, Windows.Storage.Streams, ContentType = WindowsRuntime]
$asTask = [System.WindowsRuntimeSystemExtensions].GetMethods() | Where-Object { $_.Name -eq 'AsTask' -and $_.GetParameters().Count -eq 1 -and $_.GetParameters()[0].ParameterType.Name -eq 'IAsyncOperation`1' } | Select-Object -First 1
$asAction = [System.WindowsRuntimeSystemExtensions].GetMethods() | Where-Object { $_.Name -eq 'AsTask' -and $_.GetParameters().Count -eq 1 -and $_.GetParameters()[0].ParameterType.Name -eq 'IAsyncAction' } | Select-Object -First 1
function Await($op, [type]$t) { $task = $asTask.MakeGenericMethod($t).Invoke($null, @($op)); $task.Wait(); $task.Result }
function AwaitAction($op) { $asAction.Invoke($null, @($op)).Wait() }

New-Item -ItemType Directory -Force $Out | Out-Null
$file = Await ([Windows.Storage.StorageFile]::GetFileFromPathAsync((Resolve-Path $Pdf).Path)) ([Windows.Storage.StorageFile])
$doc = Await ([Windows.Data.Pdf.PdfDocument]::LoadFromFileAsync($file)) ([Windows.Data.Pdf.PdfDocument])
$count = $doc.PageCount
for ($i = 0; $i -lt $count; $i++) {
    $page = $doc.GetPage($i)
    $opt = New-Object Windows.Data.Pdf.PdfPageRenderOptions
    $opt.DestinationWidth = $Width
    $stream = New-Object Windows.Storage.Streams.InMemoryRandomAccessStream
    AwaitAction ($page.RenderToStreamAsync($stream, $opt))
    $net = [System.IO.WindowsRuntimeStreamExtensions]::AsStreamForRead($stream.GetInputStreamAt(0))
    $img = [System.Drawing.Image]::FromStream($net)
    $img.Save((Join-Path $Out ('page{0:000}.png' -f ($i + 1))), [System.Drawing.Imaging.ImageFormat]::Png)
    $img.Dispose(); $net.Dispose(); $page.Dispose()
}
# contact sheets: $PerSheet pages in a row-major grid of 3 columns
$cols = 3
for ($s = 0; $s * $PerSheet -lt $count; $s++) {
    $pages = @(); for ($k = 0; $k -lt $PerSheet -and $s * $PerSheet + $k -lt $count; $k++) { $pages += [System.Drawing.Image]::FromFile((Join-Path $Out ('page{0:000}.png' -f ($s * $PerSheet + $k + 1)))) }
    $w = $pages[0].Width; $h = $pages[0].Height; $rows = [math]::Ceiling($pages.Count / $cols)
    $sheet = New-Object System.Drawing.Bitmap ($cols * $w + ($cols - 1) * 10), ($rows * $h + ($rows - 1) * 10)
    $g = [System.Drawing.Graphics]::FromImage($sheet); $g.Clear([System.Drawing.Color]::Gray)
    for ($k = 0; $k -lt $pages.Count; $k++) { $g.DrawImage($pages[$k], ($k % $cols) * ($w + 10), [math]::Floor($k / $cols) * ($h + 10), $w, $h); $pages[$k].Dispose() }
    $g.Dispose(); $sheet.Save((Join-Path $Out ('sheet{0:00}.png' -f ($s + 1))), [System.Drawing.Imaging.ImageFormat]::Png); $sheet.Dispose()
}
"$count pages"
