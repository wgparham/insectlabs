/* macdis: disassemble classic Mac 68K CODE resources (CodeWarrior output).
 * Usage: macdis <dir-with-CODE_n.bin> <outdir>
 * Writes one listing per segment, resolving MacsBug symbols, jump-table
 * calls, PC-relative calls, A-traps and SANE opwords. */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include "m68k.h"

#define MAXSEG 32
#define MAXSYM 4096

static unsigned char *seg[MAXSEG];
static long seglen[MAXSEG];
static unsigned char *cur; static long curlen;

typedef struct { int seg; long off; char name[96]; } Sym;
static Sym syms[MAXSYM]; static int nsyms;

unsigned int m68k_read_disassembler_8(unsigned int a){ return a<(unsigned)curlen?cur[a]:0; }
unsigned int m68k_read_disassembler_16(unsigned int a){ return (m68k_read_disassembler_8(a)<<8)|m68k_read_disassembler_8(a+1); }
unsigned int m68k_read_disassembler_32(unsigned int a){ return (m68k_read_disassembler_16(a)<<16)|m68k_read_disassembler_16(a+2); }

static unsigned rd16(unsigned char*p,long o){ return (p[o]<<8)|p[o+1]; }
static unsigned rd32(unsigned char*p,long o){ return (rd16(p,o)<<16)|rd16(p,o+2); }

static unsigned char *load(const char*path,long*len){
  FILE*f=fopen(path,"rb"); if(!f) return NULL;
  fseek(f,0,SEEK_END); *len=ftell(f); fseek(f,0,SEEK_SET);
  unsigned char*b=malloc(*len+8); memset(b,0,*len+8); fread(b,1,*len,f); fclose(f); return b;
}

/* Return length of MacsBug symbol at o (after an RTS/JMP), filling name; 0 if none. */
static int macsbug(unsigned char*p,long len,long o,char*name){
  if(o>=len) return 0;
  int b=p[o], n, start;
  if(b==0x80){ n=p[o+1]; start=o+2; }
  else if(b>0x80 && b<0xA0){ n=b-0x80; start=o+1; }
  else return 0;
  if(n<2 || start+n>len) return 0;
  for(int i=0;i<n;i++){ int c=p[start+i]; if(!(isalnum(c)||c=='_'||c=='.'||c=='%'||c==':'||c=='$'||c=='~')) return 0; }
  memcpy(name,p+start,n); name[n]=0;
  long e=start+n; if(e&1) e++;
  e+=2; /* literal-constant byte count */
  e+=rd16(p,e-2); if(e&1) e++;
  return (int)(e-o);
}

static const char *symat(int s,long off){
  for(int i=0;i<nsyms;i++) if(syms[i].seg==s && syms[i].off==off) return syms[i].name;
  return NULL;
}

/* Collect function symbols: a function starts at seg start(4) or after the previous symbol block. */
static void collect(int s){
  unsigned char*p=seg[s]; long len=seglen[s]; long fstart=4;
  for(long o=4;o+2<=len;o+=2){
    unsigned w=rd16(p,o);
    /* Stripped build: name each LINK A6 (or JT entry point) F<seg>_<offset>. */
    if(w==0x4E56 && nsyms<MAXSYM && !symat(s,o)){ syms[nsyms].seg=s; syms[nsyms].off=o; sprintf(syms[nsyms].name,"F%d_%05lX",s,o); nsyms++; }
    if(w==0x4E75||w==0x4ED0||w==0x4E74){
      long q=o+(w==0x4E74?4:2); char nm[96];
      int l=macsbug(p,len,q,nm);
      if(l && nsyms<MAXSYM){ syms[nsyms].seg=s; syms[nsyms].off=fstart; strcpy(syms[nsyms].name,nm); nsyms++; fstart=q+l; o=fstart-2; }
    }
  }
}

static const char *trapname(unsigned w){
  static const struct{unsigned w;const char*n;} t[]={
    {0xA9EB,"_FP68K (SANE)"},{0xA9EC,"_Elems68K (SANE)"},{0xA9F0,"_LoadSeg"},{0xA9F1,"_UnloadSeg"},
    {0xA9EE,"_DecStr68K"},{0xA9ED,"_Pack5"},{0xA122,"_NewHandle"},{0xA11E,"_NewPtr"},{0xA01F,"_DisposePtr"},
    {0xA023,"_DisposeHandle"},{0xA029,"_HLock"},{0xA02A,"_HUnlock"},{0xA9A0,"_GetResource"},{0xA9A3,"_ReleaseResource"},
    {0xA000,"_Open"},{0xA001,"_Close"},{0xA002,"_Read"},{0xA003,"_Write"},{0xA008,"_Create"},{0xA009,"_Delete"},
    {0xA00A,"_OpenRF"},{0xA011,"_GetEOF"},{0xA012,"_SetEOF"},{0xA044,"_SetFPos"},{0xA018,"_GetFPos"},{0xA260,"_HFSDispatch"},
    {0xA9EA,"_Pack3 (StdFile)"},{0xA9E7,"_Pack2"},{0xA9E9,"_Pack4"},{0xA9B9,"_GetCursor"},{0xA851,"_SetCursor"},
    {0xA975,"_TickCount"},{0xA861,"_Random"},{0xA9C8,"_SysBeep"},{0xA9F4,"_ExitToShell"},{0xA992,"_DetachResource"},
    {0xA9A1,"_GetNamedResource"},{0xA9AA,"_ChangedResource"},{0xA9AB,"_AddResource"},{0xA9B0,"_WriteResource"},
    {0xA97C,"_GetDialogItem"},{0xA97D,"_SetDialogItemText"},{0xA990,"_GetDialogItemText"},{0xA97E,"_SelectDialogItemText"},
    {0xA963,"_GetControlValue"},{0xA964,"_SetControlValue"},{0xA986,"_Alert"},{0xA97F,"_ModalDialog"},
    {0xA97B,"_GetNewDialog"},{0xA983,"_DisposeDialog"},{0xA9D4,"_GetMHandle"},{0xA9B3,"_EnableItem"},
    {0xA893,"_MoveTo"},{0xA891,"_LineTo"},{0xA8A1,"_FrameRect"},{0xA8A3,"_EraseRect"},{0xA8A2,"_PaintRect"},
    {0xA873,"_SetPort"},{0xA874,"_GetPort"},{0xA85E,"_ShowCursor"},{0xA852,"_HideCursor"},{0xA9FF,"_Debugger"},
    {0xA9DD,"_InsMenuItem"},{0xA988,"_StopAlert"},{0xA985,"_NoteAlert"},{0xA9C6,"_SecondsToDate"},
    {0xA9BA,"_GetString"},{0xA906,"_NumToString(Pack7)"},{0xA9EE,"_Pack7"},{0xA9C9,"_SysError"},
    {0xA865,"_GetPixel"},{0xA8A5,"_FillRect"},{0xA8B6,"_FrameOval"},{0xA8B8,"_InvertOval"},{0xA8B9,"_FillOval"},
    {0xA8B7,"_PaintOval"},{0xA8C0,"_FrameRoundRect"},{0xA884,"_DrawString"},{0xA883,"_DrawChar"},{0xA88C,"_StringWidth"},
    {0xA887,"_TextFont"},{0xA88A,"_TextSize"},{0xA888,"_TextFace"},{0xA889,"_TextMode"},{0xA89E,"_PenPat"},
    {0xA898,"_PenNormal"},{0xA89B,"_PenSize"},{0xA89C,"_PenMode"},{0xA9C0,"_DrawPicture?"},{0xA8F6,"_DrawPicture"},
    {0xA971,"_GetNextEvent"},{0xA860,"_WaitNextEvent"},{0xA974,"_Button"},{0xA9AF,"_ResError"},{0xA81A,"_Gestalt?"},
    {0xA1AD,"_Gestalt"},{0xA02E,"_BlockMove"},{0xA9A2,"_LoadResource"},{0xA025,"_GetHandleSize"},{0xA024,"_SetHandleSize"},
    {0xA9C7,"_Date2Secs"},{0xA03B,"_Delay"},{0xA198,"_HWPriv"},{0xA0BD,"_FlushCodeCache?"},
    {0,NULL}};
  for(int i=0;t[i].n;i++) if(t[i].w==w) return t[i].n;
  return NULL;
}

static const char *saneop(unsigned trap,unsigned op){
  static char b[64];
  if(trap==0xA9EB){
    static const char*even[]={"ADD","SUB","MUL","DIV","CMP","CPX","REM","Z2X(src->ext)","X2Z(ext->dst)","SQRT","RINT","TTI(trunc)","SCALB","LOGB","CLASS","?1E"};
    static const char*odd[]={"SETENV","GETENV","SETHV","GETHV","D2B","B2D","NEG","ABS","CPYSGN","NEXT","SETXCP","PROCENTRY","PROCEXIT","TESTXCP","?1D","?1F"};
    unsigned fmt=(op>>8)&0x38, o=op&0x1F;
    const char*f= fmt==0?"ext":fmt==0x08?"dbl":fmt==0x10?"sgl":fmt==0x18?"?":fmt==0x20?"int16":fmt==0x28?"int32":fmt==0x30?"comp":"?";
    sprintf(b,"FO%s.%s",(o&1)?odd[o/2]:even[o/2],f); return b;
  }
  static const struct{unsigned o;const char*n;} e[]={{0,"LNX"},{2,"LOG2X"},{4,"LN1X"},{6,"LOG21X"},{8,"EXPX"},{0xA,"EXP2X"},
    {0xC,"EXP1X"},{0xE,"EXP21X"},{0x8010,"XPWRI"},{0x8012,"XPWRY"},{0xC014,"COMPOUND"},{0xC016,"ANNUITY"},{0x18,"SINX"},
    {0x1A,"COSX"},{0x1C,"TANX"},{0x1E,"ATANX"},{0x20,"RANDX"},{0,NULL}};
  for(int i=0;e[i].n;i++) if(e[i].o==op && (i==0?op==0:1)) { sprintf(b,"FO%s",e[i].n); return b; }
  sprintf(b,"elems op $%04X",op); return b;
}

static void jtname(unsigned char*jt,long jtlen,int d16,char*out){
  long e=(d16-32); /* JT starts at A5+32 */
  out[0]=0;
  if(d16<32 || e%8 || e+8>jtlen-16) return;
  unsigned off=rd16(jt,16+e), s=rd16(jt,16+e+4);
  const char*n=s<MAXSEG?symat(s,off+4):NULL;
  sprintf(out,"JT[%ld] -> CODE %u:+$%X %s",e/8,s,off+4,n?n:"");
}

int main(int argc,char**argv){
  if(argc<3){ fprintf(stderr,"usage: macdis dir outdir\n"); return 1; }
  char path[1024];
  for(int s=0;s<MAXSEG;s++){ sprintf(path,"%s/CODE_%d.bin",argv[1],s); seg[s]=load(path,&seglen[s]); }
  for(int s=1;s<MAXSEG;s++) if(seg[s]) collect(s);
  for(long e=0;16+e+8<=seglen[0];e+=8){
    unsigned off=rd16(seg[0],16+e)+4, sn=rd16(seg[0],16+e+4);
    if(sn<MAXSEG && seg[sn] && !symat(sn,off) && nsyms<MAXSYM){ syms[nsyms].seg=sn; syms[nsyms].off=off; sprintf(syms[nsyms].name,"F%u_%05X",sn,off); nsyms++; }
  }
  sprintf(path,"%s/symbols.txt",argv[2]); FILE*sf=fopen(path,"w");
  for(int i=0;i<nsyms;i++) fprintf(sf,"CODE %d +$%05lX %s\n",syms[i].seg,syms[i].off,syms[i].name);
  fclose(sf);
  /* jump table listing */
  sprintf(path,"%s/jumptable.txt",argv[2]); FILE*jf=fopen(path,"w");
  for(long e=0;16+e+8<=seglen[0];e+=8){ char nm[256]; jtname(seg[0],seglen[0],(int)(32+e),nm); fprintf(jf,"A5+$%04lX %s\n",32+e,nm); }
  fclose(jf);

  for(int s=1;s<MAXSEG;s++){
    if(!seg[s]) continue;
    cur=seg[s]; curlen=seglen[s];
    sprintf(path,"%s/CODE_%d.s",argv[2],s); FILE*o=fopen(path,"w");
    fprintf(o,"; CODE %d, %ld bytes, header %04X %04X\n",s,curlen,rd16(cur,0),rd16(cur,2));
    long pc=4; unsigned lastimm=0xFFFFFFFF; long lastimmpc=-10;
    while(pc+2<=curlen){
      const char*n=symat(s,pc);
      if(n) fprintf(o,"\n%s:\n",n);
      char nm[96]; unsigned prev=pc>=6?rd16(cur,pc-2):0;
      if((prev==0x4E75||prev==0x4ED0) ){ int l=macsbug(cur,curlen,pc,nm); if(l){ fprintf(o,"        ; macsbug \"%s\" (%d bytes)\n",nm,l); pc+=l; continue; } }
      char buf[256]; unsigned w=rd16(cur,pc);
      unsigned sz=m68k_disassemble(buf,pc,M68K_CPU_TYPE_68040);
      char note[300]="";
      if(w==0x4EBA||w==0x6100||w==0x4EFA||w==0x6000){ long t=pc+2+(short)rd16(cur,pc+2); const char*tn=symat(s,t); sprintf(note,"-> +$%lX %s",t,tn?tn:""); }
      else if((w&0xFF00)==0x6100 && (w&0xFF)){ long t=pc+2+(signed char)(w&0xFF); const char*tn=symat(s,t); sprintf(note,"-> +$%lX %s",t,tn?tn:""); }
      else if(w==0x4EAD||w==0x4EED){ jtname(seg[0],seglen[0],(short)rd16(cur,pc+2),note); }
      else if(w==0x4EB9||w==0x4EF9){ unsigned t=rd32(cur,pc+2);
        /* CodeWarrior relocates these against A5: target is a loaded jump-table entry's JMP. */
        if(t>=0x22 && (t-0x22)%8==0) jtname(seg[0],seglen[0],(int)(t-2),note);
        else sprintf(note,"-> abs $%X",t); }
      else if((w&0xF000)==0xA000){ const char*tn=trapname(w);
        if((w==0xA9EB||w==0xA9EC) && lastimmpc==pc-4) sprintf(note,"%s %s",tn,saneop(w,lastimm));
        else sprintf(note,"%s",tn?tn:"trap"); sz=2; sprintf(buf,"dc.w    $%04X",w); }
      if(w==0x3F3C){ lastimm=rd16(cur,pc+2); lastimmpc=pc; }
      /* flag A5-relative absolute data addresses */
      for(unsigned k=2;k+4<=sz;k+=2){ unsigned v=rd32(cur,pc+k); if(v>=0xFFFF8000u && !note[0]) sprintf(note,"A5-$%X?",0u-v); }
      fprintf(o,"%05lX  ",pc);
      for(unsigned k=0;k<6;k+=2) { if(k<sz) fprintf(o,"%04X ",rd16(cur,pc+k)); else fprintf(o,"     "); }
      fprintf(o," %-40s %s\n",buf,note);
      if(sz<2) sz=2; pc+=sz;
    }
    fclose(o);
  }
  printf("%d symbols\n",nsyms);
  return 0;
}
