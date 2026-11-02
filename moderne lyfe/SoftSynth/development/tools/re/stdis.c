/* stdis: linear disassembly of a GEMDOS .PRG text segment. Usage: stdis file.prg > out.txt */
#include <stdio.h>
#include <stdlib.h>
#include "m68k.h"
static unsigned char *t; static unsigned long tl;
unsigned int m68k_read_disassembler_8(unsigned int a){ return a<tl?t[a]:0; }
unsigned int m68k_read_disassembler_16(unsigned int a){ return (m68k_read_disassembler_8(a)<<8)|m68k_read_disassembler_8(a+1); }
unsigned int m68k_read_disassembler_32(unsigned int a){ return (m68k_read_disassembler_16(a)<<16)|m68k_read_disassembler_16(a+2); }
int main(int c,char**v){
  FILE*f=fopen(v[1],"rb"); fseek(f,0,2); long n=ftell(f); rewind(f);
  unsigned char*p=malloc(n); fread(p,1,n,f);
  tl=(p[2]<<24)|(p[3]<<16)|(p[4]<<8)|p[5]; t=p+28;
  unsigned a=0; char b[128];
  while(a<tl){
    unsigned len=m68k_disassemble(b,a,M68K_CPU_TYPE_68000);
    if(len==0||len>10)len=2;
    printf("%06X  ",a);
    for(unsigned i=0;i<6;i+=2) if(i<len) printf("%04X ",m68k_read_disassembler_16(a+i)); else printf("     ");
    printf(" %s\n",b);
    a+=len;
  }
  return 0;
}
