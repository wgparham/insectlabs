import struct,sys
d=open(sys.argv[1],'rb').read()
M=3141592621; MI=pow(M,-1,1<<32); W=(1<<32)-1
pairs=set(d[i:i+4] for i in range(0,len(d)-4,2))
def nxt(s): return (s*M+1)&W
for base in (8192,):
  # a,b so that s0=a*v+b after N back-steps
  a,b=1,0
  for n in range(1,base+200):
    a=(a*MI)&W; b=((b-1)*MI)&W  # s_prev=(s-1)*MI
    if n<base: continue
    for off in range(0,0x20000,2):
      v=struct.unpack_from('>I',d,off)[0]
      s0=(a*v+b)&W
      s=nxt(s0); w0=(s>>8)&0xFFFF; s=nxt(s); w1=(s>>8)&0xFFFF
      if struct.pack('>HH',w0,w1) in pairs:
        # confirm with more words
        p=d.find(struct.pack('>HH',w0,w1)); 
        s=s0; ok=True; seq=b''
        for k in range(8192):
          s=nxt(s); seq+=struct.pack('>H',(s>>8)&0xFFFF)
        at=d.find(seq[:64])
        if at>=0:
          print(f'state at {off:#x} value {v:#010x} steps {n} seed {s0} noise table at {at:#x} full match {d[at:at+16384]==seq}')
