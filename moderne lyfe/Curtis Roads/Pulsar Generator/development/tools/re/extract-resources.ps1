param([string]$File, [string]$OutDir)
# Parse a MacBinary file's resource fork; list resources and dump each to OutDir.
$b = [IO.File]::ReadAllBytes($File)
function U32($a,$o){ ([uint32]$a[$o] -shl 24) -bor ([uint32]$a[$o+1] -shl 16) -bor ([uint32]$a[$o+2] -shl 8) -bor $a[$o+3] }
function U16($a,$o){ ([uint32]$a[$o] -shl 8) -bor $a[$o+1] }
$dlen = U32 $b 83
$rstart = 128 + [int]([math]::Ceiling($dlen/128)*128)
$r = $b[$rstart..($b.Length-1)]
$dataOff = U32 $r 0; $mapOff = U32 $r 4
$typeListOff = $mapOff + (U16 $r ($mapOff+24))
$nameListOff = $mapOff + (U16 $r ($mapOff+26))
$nTypes = (U16 $r $typeListOff) + 1
New-Item -ItemType Directory -Force $OutDir | Out-Null
for ($t=0; $t -lt $nTypes; $t++) {
  $e = $typeListOff + 2 + $t*8
  $type = [Text.Encoding]::ASCII.GetString($r,$e,4)
  $n = (U16 $r ($e+4)) + 1
  $refOff = $typeListOff + (U16 $r ($e+6))
  for ($i=0; $i -lt $n; $i++) {
    $ro = $refOff + $i*12
    $id = [int16](U16 $r $ro)
    $nmo = U16 $r ($ro+2)
    $name = ''
    if ($nmo -ne 0xFFFF) { $p = $nameListOff + $nmo; $name = [Text.Encoding]::GetEncoding(10000).GetString($r,$p+1,$r[$p]) }
    $doff = ((U32 $r ($ro+4)) -band 0xFFFFFF) + $dataOff
    $len = U32 $r $doff
    $safe = ($type -replace '[^A-Za-z0-9#]','_')
    [IO.File]::WriteAllBytes((Join-Path $OutDir "$safe`_$id.bin"), $r[($doff+4)..($doff+3+$len)])
    "{0}`t{1}`t{2}`t{3}" -f $type,$id,$len,$name
  }
}
