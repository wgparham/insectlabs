# Pass 1: jumptable.txt "A5+$XXXX JT[n] -> CODE s:+$off Fs_xxxxx"; pass 2: annotate listings.
FNR==NR { e=strtonum("0x" substr($1,5)); jt[e]=$NF; next }
{
  if (match($0, /\(\$([0-9a-f]+),A5\)/, m)) {
    v=strtonum("0x" m[1]);
    if ((v-2) in jt && $0 ~ /jsr|jmp|pea|lea/) { printf "%-90s ; -> %s\n", $0, jt[v-2]; next }
  }
  print
}
