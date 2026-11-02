import struct,sys
d=bytearray(open(sys.argv[1],'rb').read())
struct.pack_into('>i',d,0x77C0,int(sys.argv[3]))
mx=0
for i in range(32):
    base=i*956
    for off,n in ((0xA,42),(0x2AC,17)):
        for r in range(n):
            p=base+off+r*16+14; x=struct.unpack_from('>h',d,p)[0]
            if x!=10000 and x>0:
                x2=x*50; mx=max(mx,x2); struct.pack_into('>h',d,p,x2)
open(sys.argv[2],'wb').write(d)
print('active',sum(1 for i in range(32) if struct.unpack_from('>h',d,i*956+2)[0]!=0),'max x',mx)
