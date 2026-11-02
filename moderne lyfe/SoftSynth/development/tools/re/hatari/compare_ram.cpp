// compare_ram before.bin after.bin [length] [smart|to|from]: replays a Generate or a time slice
// conversion on the port from two 1 MB RAM dumps of the real program under Hatari, and reports
// any difference. [length] finds a patch whose length is not New's 20000.
// hcmp before.bin after.bin: replays a Generate on the port from two RAM dumps of the real program.
#include "Edit.h"
#include <cstdio>
#include <cstring>
#include <cstdlib>
#include <vector>
using namespace softsynth;
static std::vector<uint8_t> rd(const char* p){ std::vector<uint8_t> d(0x100000); FILE*f=fopen(p,"rb"); fread(d.data(),1,d.size(),f); fclose(f); return d; }
static uint32_t be32(const std::vector<uint8_t>&d,size_t o){return readBe32(&d[o]);}
int main(int c,char**argc_v){ char* v[5]={argc_v[0],argc_v[1],argc_v[2],c>3?argc_v[3]:nullptr,nullptr};
  auto a=rd(v[1]),b=rd(v[2]);
  // -77C(A4) partials pointer, then length, rate, frequency.
  size_t lens=0; for(size_t o=0;o+16<a.size();o+=2){ uint32_t base=be32(a,o), len=be32(a,o+4), rate=be32(a,o+8), fr=be32(a,o+12);
    if(base>0x1000&&base<0xF0000&&base%2==0&&((len==20000&&rate==31250&&fr==0x1B80000u)||(v[3]&&len==(uint32_t)atoi(v[3])))){lens=o;break;} }
  if(!lens){puts("no patch found");return 1;}
  const size_t a4=lens+0x77C, base=be32(a,lens);
  printf("A4 %#zx partials %#zx length %u rate %u freq %u\n",a4,base,be32(a,lens+4),be32(a,lens+8),be32(a,lens+12)>>16);
  Patch p; p.partials.assign(a.begin()+base,a.begin()+base+kPartialBytes); std::memcpy(p.routing.data(),&a[a4-0x7BC],0x40);
  p.length=be32(a,lens+4);p.rate=be32(a,lens+8);p.frequency=be32(a,lens+12);
  SmartSynthSettings s; int*f[12]={&s.harmonicSeries,&s.partialDetuning,&s.attackRate,&s.decayRate,&s.harmonicRange,&s.doubling,&s.frequencyAttack,&s.secondaryLevel,&s.harmonicFilter,&s.frequencyMovement,&s.percussion,&s.fade};
  printf("settings:"); for(int i=0;i<12;i++){*f[i]=readBe16(&b[a4-0x8E0+i*2]);printf(" %d",*f[i]);} puts("");
  const uint32_t st=be32(a,0x25CC),st2=be32(b,0x25CC); printf("Random state before %u after %u\n",st,st2);
  XbiosRandom r(st);
  SliceList hl; hl.count=readBe16(&a[a4-0xEE0]); for(int i=0;i<42;i++){hl.y[i]=readBe16(&a[a4-0xEDE + i*4]);hl.x[i]=readBe16(&a[a4-0xEDC+i*4]);}
  const char* mode=c>4?argc_v[4]:"smart";
  if(!strcmp(mode,"to")) partialsToSlices(p,hl); else if(!strcmp(mode,"from")) slicesToPartials(p,hl); else smartSynthGenerate(p,s,r);
  if(strcmp(mode,"smart")){ int bad=readBe16(&b[a4-0xEE0])!=hl.count; for(int i=0;i<42;i++) bad+=readBe16(&b[a4-0xEDE + i*4])!=hl.y[i]||readBe16(&b[a4-0xEDC+i*4])!=hl.x[i]; printf("slice list: %d events, %d differences\n",hl.count,bad); }
  int diffs=0,changed=0; for(size_t i=0;i<kPartialBytes;i++){ if(a[base+i]!=b[base+i])changed++; if(b[base+i]!=p.partials[i]){ if(diffs<8)printf("P%zu +%#zx real %02x port %02x\n",i/kPartialSize+1,i%kPartialSize,b[base+i],p.partials[i]); diffs++; } }
  for(int i=0;i<0x40;i++) if(b[a4-0x7BC+i]!=p.routing[i]){ if(diffs<8)printf("routing %d real %02x port %02x\n",i,b[a4-0x7BC+i],p.routing[i]); diffs++; }
  XbiosRandom probe(st2); uint32_t want=probe.next(), got=r.next();
  printf("bytes changed by Generate %d, differences from port %d, Random stream %s\n",changed,diffs,want==got?"same":"DIFFERS");
  return diffs!=0||want!=got;
}
