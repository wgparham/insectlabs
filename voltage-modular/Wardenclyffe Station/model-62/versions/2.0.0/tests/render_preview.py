"""Render and exercise the actual Model 62 GUI-only transport class without a host."""
from pathlib import Path
import argparse,subprocess,tempfile
p=argparse.ArgumentParser();p.add_argument('--output',type=Path,required=True);a=p.parse_args()
s=(Path(__file__).resolve().parent.parent/'model62.java').read_text(encoding='utf-8-sig')
eye_start=s.index('    static final class MagicEyeDisplay');eye_end=s.index('    /** GUI-only renderer.',eye_start)
start=s.index('    static final class TransportDisplay');end=s.index('    /** Model 62 v1.1.0:',start)
renderer=s[eye_start:eye_end]+s[start:end]
wrapper='''import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
public class CanvasPreview {
'''+renderer+'''
    static BufferedImage render(TransportDisplay d,int w,int h,double phase,double speed,
            boolean power,boolean broken,boolean run,boolean record,boolean armed,boolean bypass,long now) {
        BufferedImage out=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
        Graphics2D g=out.createGraphics();
        double timeSpeed=speed==0?1:speed;
        try {d.paint(g,w,h,phase,speed,.37,7,timeSpeed,power,broken,run,record,armed,bypass,3,0,now);}
        finally {g.dispose();}
        return out;
    }
    static BufferedImage renderEye(MagicEyeDisplay eye,int w,int h,double level,boolean active) {
        BufferedImage out=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g=out.createGraphics();
        try {eye.paint(g,w,h,level,active);} finally {g.dispose();}
        return out;
    }
    static void same(BufferedImage a,BufferedImage b) {
        for(int y=0;y<a.getHeight();y++)for(int x=0;x<a.getWidth();x++)
            if(a.getRGB(x,y)!=b.getRGB(x,y))throw new AssertionError("Stopped canvas changed");
    }
    public static void main(String[] args)throws Exception {
        File folder=new File(args[0]);folder.mkdirs();
        String[] times={TransportDisplay.formatTime(7),TransportDisplay.formatTime(14),
                TransportDisplay.formatTime(3.5),TransportDisplay.formatTime(3600),
                TransportDisplay.formatTiming(.37,7,.5),TransportDisplay.formatTiming(.37,7,2),
                TransportDisplay.formatTiming(.37,7,0)};
        String[] expected={"00:07.0","00:14.0","00:03.5","01:00:00",
                "POS 00:05.2  LEN 00:14.0","POS 00:01.3  LEN 00:03.5","POS --:--.-  LEN ∞"};
        if(!java.util.Arrays.equals(times,expected))throw new AssertionError("Varispeed time display: "+java.util.Arrays.toString(times));
        if(!TransportDisplay.formatTime(59.99).equals("01:00.0"))throw new AssertionError("Minute rollover");
        TransportDisplay stopped=new TransportDisplay();
        BufferedImage first=render(stopped,560,270,0,0,true,false,false,false,false,false,0);
        same(first,render(stopped,560,270,0,0,true,false,false,false,false,false,1000000000L));
        TransportDisplay d=new TransportDisplay();
        render(d,280,135,6.2,1,true,false,true,false,false,false,0);
        double angle=d.visualPhase;
        render(d,280,135,.1,1,true,false,true,false,false,false,33000000L);
        if(!(Math.atan2(Math.sin(d.visualPhase-angle),Math.cos(d.visualPhase-angle))>0))throw new AssertionError("Forward phase wrap");
        angle=d.visualPhase;
        render(d,280,135,6.2,-1,true,false,true,false,false,false,66000000L);
        if(!(Math.atan2(Math.sin(d.visualPhase-angle),Math.cos(d.visualPhase-angle))<0))throw new AssertionError("Reverse phase wrap");
        render(d,560,270,6.2,0,false,true,false,false,false,false,1000000000L);
        render(d,280,135,6.2,0,false,true,false,false,false,false,3000000000L);
        String[] titles={"STOPPED","FORWARD / PLAY","REVERSE","RECORDING","WIRE BROKEN","POWER OFF"};
        BufferedImage sheet=new BufferedImage(1120,900,BufferedImage.TYPE_INT_RGB);
        Graphics2D g=sheet.createGraphics();g.setColor(new Color(20,20,20));g.fillRect(0,0,1120,900);
        for(int i=0;i<6;i++) {
            TransportDisplay display=new TransportDisplay();
            double speed=i==0||i>=4?0:i==2?-1:1;
            boolean power=i!=5,broken=i==4,run=i>=1&&i<=3,record=i==3;
            render(display,560,270,.8,speed,power,broken,run,record,false,false,0);
            BufferedImage image=render(display,560,270,.8,speed,power,broken,run,record,false,false,1200000000L);
            ImageIO.write(image,"png",new File(folder,"transport-"+i+".png"));
            int x=(i%2)*560,y=(i/2)*300;
            g.setColor(new Color(195,190,175));g.setFont(new Font("Monospaced",Font.PLAIN,13));g.drawString(titles[i],x+16,y+21);
            g.drawImage(image,x,y+30,null);
        }
        g.dispose();ImageIO.write(sheet,"png",new File(folder,"model62-transport-preview.png"));
        BufferedImage eyeSheet=new BufferedImage(700,220,BufferedImage.TYPE_INT_ARGB);
        Graphics2D eg=eyeSheet.createGraphics();
        eg.setColor(new Color(20,20,20));eg.fillRect(0,0,700,220);
        String[] eyeTitles={"IDLE","LOW","MID","HIGH","POWER OFF"};
        double[] levels={0,.25,.8,2,2};
        for(int i=0;i<5;i++) {
            eg.setColor(new Color(195,190,175));eg.setFont(new Font("Monospaced",Font.PLAIN,12));
            eg.drawString(eyeTitles[i],i*140+20,28);
            eg.drawImage(renderEye(new MagicEyeDisplay(),105,105,levels[i],i<4),i*140+18,48,null);
        }
        eg.dispose();ImageIO.write(eyeSheet,"png",new File(folder,"magic-eye-preview.png"));
        System.out.println("PASS: stable transport; bidirectional wrap; resize; breaks/off; speed-scaled timing and formatting; five eye states; six transport previews.");
    }
}
'''
a.output.mkdir(parents=True,exist_ok=True)
with tempfile.TemporaryDirectory(prefix='model62-canvas-') as tmp:
 tmp=Path(tmp);f=tmp/'CanvasPreview.java';f.write_text(wrapper, encoding='utf-8')
 subprocess.run(['javac','--release','17','-Xlint:all','-Werror','-d',str(tmp),str(f)],check=True)
 subprocess.run(['java','-Djava.awt.headless=true','-cp',str(tmp),'CanvasPreview',str(a.output.resolve())],check=True)
