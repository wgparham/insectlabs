use strict; use warnings;
my ($prg,$dis,$out)=@ARGV;
open my $f,'<:raw',$prg or die; local $/; my $p=<$f>; close $f; $/="\n";
my ($tl,$dl,$bl)=unpack 'x2 N N N',$p; my $text=substr $p,28,$tl; my $data=substr $p,28+$tl,$dl;
sub str { my $a=shift; return undef if $a<0||$a>=$dl; my $e=index $data,"\0",$a; my $s=substr $data,$a,$e-$a; return ($s=~/^[\x20-\x7e\r\n]{2,}$/)?$s:undef }
my @jt; my $o=0; while(substr($text,$o,2) eq "\x4e\xf9"){push @jt,unpack 'N',substr $text,$o+2,4;$o+=6}
open my $d,'<',$dis or die; open my $w,'>',$out or die;
my $fn='?'; my (%calls,%strs,@funcs);
while(<$d>){ chomp; my $l=$_; my ($a)=/^([0-9A-F]{6})/; my $ia=hex $a;
  if(/link\s+A6/){$fn=$a;push @funcs,$a}
  if(/\(([-\$0-9a-f]+),A4\)/){ my $v=$1; my $n=$v=~/^-/?-hex(substr $v,2):hex(substr $v,1); if($n>=0){my $s=str($n); if(defined $s){$l.="   ; \"$s\""; push @{$strs{$s}},"$a in F$fn"}} }
  if(/jsr\s+\(\$([0-9a-f]+),A5\)/){ my $t=hex $1; my $i=$t/6; if($i==int $i && $i<@jt){$l.=sprintf '   ; -> F%06X',$jt[$i]; $calls{sprintf '%06X',$jt[$i]}++}}
  if(/jsr\s+\(\$[0-9a-f]+,PC\); \(\$([0-9a-f]+)\)/){ $calls{sprintf '%06X',hex $1}++ }
  print $w "$l\n"; }
print "functions $#funcs+1\n";
for my $k (grep {/Synth|break point|time slice|Normaliz|peak|Illegal|Bad param|Overwrite|medium|Converting|Disk error|Saving sound/} sort keys %strs){ print "  \"$k\" : @{$strs{$k}}\n" }
print "top callees: "; my @t=sort{$calls{$b}<=>$calls{$a}}keys %calls; print join(' ',map{"$_:$calls{$_}"}@t[0..24]),"\n";
# NOP runs: crack patches often show as NOP sleds
my @w=unpack 'n*',$text; my $i=0;
my $nops=grep{$_==0x4e71}@w; print "NOP words total $nops; runs>=2: ";
while($i<@w){ if($w[$i]==0x4e71){my $j=$i;$j++ while $j<@w&&$w[$j]==0x4e71; printf '%#x(%d) ',$i*2,$j-$i if $j-$i>=2;$i=$j}else{$i++} } print "\n";
# code vs table bounds
my @lk=grep{$w[$_]==0x4e56}0..$#w; my ($prev)=(0); for(@lk){ printf "gap between LINKs: %#x .. %#x (%d bytes)\n",$prev*2,$_*2,($_-$prev)*2 if ($_-$prev)*2>3000; $prev=$_ }
printf "last link %#x, text end %#x\n",$lk[-1]*2,$tl;
