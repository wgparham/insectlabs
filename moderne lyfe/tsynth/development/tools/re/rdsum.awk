# Summarise reader code: track the byte-count variable at (-$4,A6) or (-$6,A6) and print READ size->dest.
function strip(s){ sub(/^ *[0-9A-F]+ +([0-9A-F]{4} )+ */,"",s); gsub(/  +/," ",s); return s }
/^F[0-9]+_/ { print ""; print $0; d0=""; next }
{
  s=strip($0); raw=s
  if (s ~ /^moveq #/ || s ~ /^move.l #/ || s ~ /^move.w #/) { split(s,a,/[ ,]+/); val=a[2]; if (s ~ /D0 *$/) d0=val }
  if (s ~ /^asl.w #1, D0/) d0=d0"*2"
  if (s ~ /^move.l D0, \(-\$[46],A6\)/) { size=d0; next }
  if (s ~ /^move.l #.*\(-\$[46],A6\)/) { split(s,a,/[ ,]+/); size=a[2]; next }
  if (s ~ /^pea \(/ ) { lastpea=s }
  if (s ~ /F1_01372/) { printf "  READ %-8s -> %s\n", size, lastpea; next }
  if (s ~ /jsr|jmp \(A0\)|^b[a-z][a-z] |^dbra|^cmp|^tst|_H(Un)?[Ll]ock|^rts/) {
    if (s ~ /F2_00146|_HLock|_HUnlock|jmp \(A0\)|^rts/) next
    print "  " s
  }
}
