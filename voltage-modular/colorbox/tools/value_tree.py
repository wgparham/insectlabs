import struct
def ci(n):
    a=abs(n); raw=a.to_bytes((a.bit_length()+7)//8,'little');return bytes([len(raw)|(128 if n<0 else 0)])+raw
class Reader:
    def __init__(self,b): self.b=b;self.p=0
    def integer(self):
        n=self.b[self.p];self.p+=1;v=int.from_bytes(self.b[self.p:self.p+(n&127)],'little');self.p+=n&127;return -v if n&128 else v
    def string(self):
        e=self.b.index(0,self.p);v=self.b[self.p:e].decode();self.p=e+1;return v
    def tree(self):
        t=self.string();props=[]
        for _ in range(self.integer()):
            k=self.string();n=self.integer();v=self.b[self.p:self.p+n];self.p+=n;props.append([k,v])
        return [t,props,[self.tree() for _ in range(self.integer())]]
def encode(t):
    return t[0].encode()+b'\0'+ci(len(t[1]))+b''.join(k.encode()+b'\0'+ci(len(v))+v for k,v in t[1])+ci(len(t[2]))+b''.join(map(encode,t[2]))
def value(v):
    if not v:return None
    if v[0]==5:return v[1:].rstrip(b'\0').decode()
    if v[0]==4:return struct.unpack('<d',v[1:])[0]
    if v[0] in (1,6):return int.from_bytes(v[1:],'little',signed=True)
    if v[0] in (2,3):return v[0]==2
    return '<binary %d bytes>'%len(v)
def props(t):return {k:value(v) for k,v in t[1]}
def setprop(t,k,v):
    for p in t[1]:
        if p[0]==k:
            old=value(p[1]);tag=p[1][0]
            if isinstance(v,str):p[1]=b'\5'+v.encode()+b'\0'
            elif isinstance(v,bool):p[1]=bytes([2 if v else 3])
            elif tag==4:p[1]=b'\4'+struct.pack('<d',v)
            else:p[1]=bytes([tag])+int(v).to_bytes(len(p[1])-1,'little',signed=True)
            return old
    raise KeyError(k)
