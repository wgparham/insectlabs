from pathlib import Path
import sys,re,subprocess,tempfile
repo=next(p for p in Path(__file__).resolve().parents if (p/'voltage-modular/colorbox/tools').is_dir());sys.path.insert(0,str(repo/'voltage-modular/colorbox/tools'));sys.dont_write_bytecode=True
from value_tree import Reader,props,encode
from support import normalized
p=Path(__file__).resolve().parent.parent;s=(p/'sn16u.java').read_text(encoding='utf-8-sig');raw=(p/'sn16u.vmod').read_bytes();r=Reader(raw);tr=r.tree();assert r.p==len(raw) and encode(tr)==raw
embedded=props(next(n for n in tr[2] if n[0]=='module code'))['MODULE.SOURCE'];assert normalized(s)==normalized(embedded)
cs=next(n for n in tr[2] if n[0]=='controls')[2];canvases={props(c)['variable name']:props(c) for c in cs if props(c)['Control Type']==19};assert set(canvases)=={'voltageCanvas','tuningCanvas','frequencyCanvas','amplitudeCanvas','meterACanvas','meterBCanvas'}
assert not any(props(c)['Control Type']==21 for c in cs)
for name,c in canvases.items():
 m=re.search(name+r' = new VoltageCanvas\( "([^"]+)", "([^"]+)"',s);assert m and m[1]==name and m[2]==c['display name']
helper=s[s.index('    // Shared GUI renderer;'):s.index('    private final BenchDisplay voltageReadout')]
stub='''import java.awt.*;import java.awt.image.BufferedImage;
public class CanvasChecks {
 static class VoltageCanvas {
  BufferedImage image=new BufferedImage(120,60,BufferedImage.TYPE_INT_ARGB);int invalidations;boolean unavailable;
  int GetBitmapWidth(){return image.getWidth();}int GetBitmapHeight(){return image.getHeight();}
  Graphics2D GetGraphics(){return unavailable?null:image.createGraphics();}void Invalidate(){invalidations++;}
 }
'''+helper+'''
 static void check(boolean ok,String label){if(!ok)throw new AssertionError(label);}
 static void expect(BenchDisplay d,double value,String unit,String context,int decimals,boolean scale,String number,String caption){BenchDisplay.Reading r=d.reading(value,unit,context,decimals,scale);check(r.number.equals(number)&&r.unit.equals(caption),value+" -> "+r.number+" "+r.unit+" expected "+number+" "+caption);}
 public static void main(String[] args)throws Exception{
  BenchDisplay d=new BenchDisplay();expect(d,0,"V","OUT",2,true,"0.00","V OUT");expect(d,-12.345,"V","DC",3,true,"-12.345","V DC");expect(d,-.0001,"V","DC",3,true,"0.000","V DC");expect(d,440,"Hz","A",2,false,"440.00","Hz A");expect(d,261.625,"Hz","CV",3,true,"261.63","Hz CV");expect(d,.05,"Hz","",3,true,"0.050","Hz");
  expect(d,1200,"Hz","",3,true,"1.200","kHz");expect(d,1000,"Hz","",3,true,"1.000","kHz");expect(d,901,"Hz","",3,true,"0.901","kHz");expect(d,900,"Hz","",3,true,"900.00","Hz");expect(d,1000,"Hz","",3,true,"1000.0","Hz");expect(d,1100,"Hz","",3,true,"1.100","kHz");
  expect(d,3.5355,"V","RMS",3,true,"3.536","V RMS");expect(d,Double.NaN,"V","DC",3,true,"Err","V DC");expect(d,1e12,"V","DC",3,true,"OL","kV DC");expect(d,5,"V","PP",3,true,"5.000","V PP");
  VoltageCanvas c=new VoltageCanvas();d.update(c,-12.34,"V","DC",3,true);check(c.invalidations==1,"first draw");d.update(c,-12.34,"V","DC",3,true);check(c.invalidations==1,"unchanged draw cached");c.image=new BufferedImage(60,30,BufferedImage.TYPE_INT_ARGB);d.update(c,-12.34,"V","DC",3,true);check(c.invalidations==2,"resized bitmap redraw");d.update(c,-12.34,"V","PP",3,true);check(c.invalidations==3,"mode caption redraw");
  VoltageCanvas pending=new VoltageCanvas();pending.unavailable=true;BenchDisplay waiting=new BenchDisplay();waiting.update(pending,440,"Hz","A",2,false);check(pending.invalidations==0,"unavailable bitmap safe");pending.unavailable=false;waiting.update(pending,440,"Hz","A",2,false);check(pending.invalidations==1,"first bitmap retried");
  BufferedImage preview=new BufferedImage(420,192,BufferedImage.TYPE_INT_ARGB);Graphics2D g=preview.createGraphics();g.setColor(new Color(35,35,35));g.fillRect(0,0,420,192);g.setFont(new Font(Font.MONOSPACED,Font.PLAIN,11));
  double[] values={-12.34,440,12000,3.5355,-.125,261.625};String[] units={"V","Hz","Hz","V","V","Hz"};String[] contexts={"OUT","A","","RMS","DC","CV"};String[] names={"VOLTAGE","TUNING","FREQUENCY","AMPLITUDE","METER A / DC","METER B / PITCH"};
  for(int i=0;i<6;i++){VoltageCanvas panel=new VoltageCanvas();BenchDisplay display=new BenchDisplay();display.update(panel,values[i],units[i],contexts[i],i<2?2:3,i!=1);int x=10+(i%3)*140,y=22+(i/3)*94;g.setColor(Color.LIGHT_GRAY);g.drawString(names[i],x,y-7);g.drawImage(panel.image,x,y,null);}
  g.dispose();javax.imageio.ImageIO.write(preview,"png",new java.io.File(args[0]));
  System.out.println("PASS all display formats, signs/rounding, units/modes, scale hysteresis, error/overrange, high-density rendering, caching, resize and unavailable-canvas recovery");
 }
}
'''
with tempfile.TemporaryDirectory(prefix='sn16u-canvas-') as temp:
 temp=Path(temp)
 for label,source in [('exported',s),('embedded',embedded)]:
  folder=temp/label;folder.mkdir();f=folder/'sn16u.java';f.write_text(source,encoding='utf-8');subprocess.run(['javac','--release','17','-Xlint:all','-Werror','-encoding','utf-8','-cp','C:/ProgramData/Voltage/voltage.jar','-d',str(folder),str(f)],check=True)
 f=temp/'CanvasChecks.java';f.write_text(stub,encoding='utf-8');subprocess.run(['javac','--release','17','-d',str(temp),str(f)],check=True)
 subprocess.run(['java','-Djava.awt.headless=true','-cp',str(temp),'CanvasChecks',sys.argv[1] if len(sys.argv)>1 else str(temp/'preview.png')],check=True)
print('PASS SDK compilation of both source forms and six-canvas metadata/synchronization')
