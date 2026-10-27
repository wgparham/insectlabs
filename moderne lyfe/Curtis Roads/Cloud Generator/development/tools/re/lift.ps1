param([string]$Listing, [string]$SegBin, [string]$Out)
# Collapse SANE "pea src; pea dst; move.w #op,-(sp); _FP68K" idioms into pseudo-C.
$seg = [IO.File]::ReadAllBytes($SegBin)
$lines = Get-Content $Listing

function BE([int]$o, [int]$n) { $v = [byte[]]$seg[$o..($o+$n-1)]; [array]::Reverse($v); ,$v }
function ConstAt([int]$addr, [string]$fmt) {
    switch ($fmt) {
        'int16' { return [string][BitConverter]::ToInt16((BE $addr 2), 0) }
        'int32' { return [string][BitConverter]::ToInt32((BE $addr 4), 0) }
        'sgl'   { return ([BitConverter]::ToSingle((BE $addr 4), 0)).ToString('R') + 'f' }
        'dbl'   { return ([BitConverter]::ToDouble((BE $addr 8), 0)).ToString('R') }
        'comp'  { return [string][BitConverter]::ToInt64((BE $addr 8), 0) + 'c' }
        default {
            $e = ([int]$seg[$addr] -shl 8) -bor $seg[$addr+1]
            $m = [UInt64]0; for ($k=0; $k -lt 8; $k++) { $m = ($m -shl 8) -bor $seg[$addr+2+$k] }
            $sign = if ($e -band 0x8000) { -1 } else { 1 }
            $val = $sign * ([double]$m / [math]::Pow(2,63)) * [math]::Pow(2, ($e -band 0x7FFF) - 16383)
            return $val.ToString('R') + 'x'
        }
    }
}
function Opnd([string]$t, [string]$fmt) {
    if ($t -match '^\(-\$([0-9a-f]+),A6\)$') { return "L$($matches[1])" }
    if ($t -match '^\(\$([0-9a-f]+),A6\)$') { return "ARG$($matches[1])" }
    if ($t -match '^\$ffff([0-9a-f]{4})\.l$') { return "G$($matches[1])" }
    if ($t -match '\(\$([0-9a-f]+)\)$') { return (ConstAt ([Convert]::ToInt32($matches[1],16)) $fmt) }
    if ($t -match '^\(\$?(-?\$?[0-9a-f]+),A([0-5])\)$') { return "[A$($matches[2])+$($matches[1])]" }
    return $t
}
$ops2 = @{ 0x00='+='; 0x02='-='; 0x04='*='; 0x06='/='; 0x0C='rem='; 0x0E='=' }
$res = New-Object System.Collections.Generic.List[string]
for ($i = 0; $i -lt $lines.Count; $i++) {
    $L = $lines[$i]
    if ($L -match '^(\w+) SANE (FO\S+)' -and $i -ge 2 -and $lines[$i-1] -match 'move\.w #\$([0-9a-f]+), -\(A7\)') {
        $op = [Convert]::ToInt32($matches[1], 16)
        $code = $op -band 0x1F
        $fmtBits = ($op -shr 8) -band 0x38
        $fmt = @{0='ext';8='dbl';0x10='sgl';0x20='int16';0x28='int32';0x30='comp'}[$fmtBits]
        $addr = ($L -split ' ')[0]
        $p1 = if ($lines[$i-2] -match '^\w+ pea (.+?)\s*$') { $matches[1] } else { $null }
        $p0 = if ($lines[$i-3] -match '^\w+ pea (.+?)\s*$') { $matches[1] } else { $null }
        $isElems = $L -match 'Elems'
        $two = (-not $isElems) -and ($code -in 0,2,4,6,8,0xA,0xC,0xE,0x10)
        if ($two -and $p0 -and $p1) {
            $src = Opnd $p0 $fmt; $dst = Opnd $p1 'ext'
            $s = switch ($code) {
                0x08 { "cmp $dst, ($fmt)$src" } 0x0A { "cmpx $dst, ($fmt)$src" }
                0x10 { $d2 = Opnd $p1 $fmt; "$d2 = ($fmt)$(Opnd $p0 'ext')" }
                0x0E { "$dst = ($fmt)$src" }
                default { "$dst $($ops2[$code]) ($fmt)$src" } }
            for ($k = 0; $k -lt 3; $k++) { $res.RemoveAt($res.Count-1) }
            $res.Add("$addr    $s")
        } elseif ($p1) {
            $name = ($matches[0] -split ' ')[-1]
            $nm = ($L -split 'SANE ')[1]
            for ($k = 0; $k -lt 2; $k++) { $res.RemoveAt($res.Count-1) }
            $res.Add("$addr    $(Opnd $p1 'ext') = $nm($(Opnd $p1 'ext'))")
        } else { $res.Add($L) }
        continue
    }
    $res.Add($L)
}
$res | Set-Content -Encoding utf8 $Out

