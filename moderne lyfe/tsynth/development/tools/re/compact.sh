#!/bin/sh
# compact.sh listing.a.s FROM TO : print functions FROM..TO without hex bytes
awk -v a="$2" -v b="$3" '$0 ~ "^"a":" {p=1} $0 ~ "^"b":" {p=0} p' "$1" | cut -c1-6,24- | sed -E 's/  +/ /g; s/ ; -> / ; /' | grep -v "^ *$"
