# Build "Turbosynth Manual.pdf" from source/manual.txt.
#   build.ps1            typeset and print (uses the figures already in source/figures)
#   build.ps1 -Figures   first re-crop the figures from the page scans (needs the
#                        renders and OCR lines made from turbosynth_manual.pdf;
#                        pass their folders with -Scans and -Ocr)
param([switch]$Figures, [string]$Scans, [string]$Ocr)
$ErrorActionPreference = 'Stop'
$root = $PSScriptRoot; $tools = "$root\tools"; $src = "$root\source"
$csc = "C:\Windows\Microsoft.NET\Framework64\v4.0.30319\csc.exe"
foreach ($t in 'ManualBuild', 'Trim', 'Typeset', 'Look') {
    if (-not (Test-Path "$tools\$t.exe") -or (Get-Item "$tools\$t.cs").LastWriteTime -gt (Get-Item "$tools\$t.exe").LastWriteTime) {
        & $csc /nologo /o "/out:$tools\$t.exe" -r:System.Drawing.dll "$tools\$t.cs"
    }
}
if ($Figures) {
    & "$tools\ManualBuild.exe" draft $Scans $Ocr $src "$src\figure-overrides.tsv" "$src\toc.txt"
    & "$tools\Trim.exe" "$src\figures" | Out-Null
}
& "$tools\Typeset.exe" "$src\manual.txt" "$src\manual.html"
$edge = "${env:ProgramFiles(x86)}\Microsoft\Edge\Application\msedge.exe"
if (-not (Test-Path $edge)) { $edge = "$env:ProgramFiles\Microsoft\Edge\Application\msedge.exe" }
$profile = Join-Path $env:TEMP "turbosynth-manual-edge"
$out = "$root\Turbosynth Manual.pdf"
Start-Process -Wait -FilePath $edge -ArgumentList "--headless=new", "--disable-gpu", "--no-pdf-header-footer",
    "--user-data-dir=`"$profile`"", "--print-to-pdf=`"$out`"", "`"file:///$($src.Replace('\', '/'))/manual.html`""
"written $out"
