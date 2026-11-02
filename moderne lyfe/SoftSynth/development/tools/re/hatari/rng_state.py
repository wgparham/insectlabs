import struct,sys
d=open(sys.argv[1],'rb').read(); n=int(sys.argv[2]); M=3141592621; MI=pow(M,-1,1<<32); W=(1<<32)-1
v=struct.unpack_from('>I',d,0x25CC)[0]; s=v
for _ in range(n): s=((s-1)*MI)&W
x=s; seq=b''
for _ in range(8192):
    x=(x*M+1)&W; seq+=struct.pack('>H',(x>>8)&0xFFFF)
print(s, 'table_ok' if d.find(seq)>=0 else 'TABLE_MISMATCH')
