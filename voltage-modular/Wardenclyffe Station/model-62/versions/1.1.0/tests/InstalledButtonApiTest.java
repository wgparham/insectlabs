import voltage.core.*;
public final class InstalledButtonApiTest {
 static final class Probe extends VoltageButton {
  double visual; int updates,notifications;
  Probe(){super("probe","Probe",null,false);}
  @Override public void UpdateGUIValue(double v){visual=v;updates++;}
  @Override protected boolean NotifyParent(VoltageModule.ModuleNotifications n,double v,long l,int x,int y,Object o){notifications++;return false;}
 }
 static void check(boolean ok){if(!ok)throw new AssertionError("Installed button API mismatch");}
 public static void main(String[] args){
  Probe p=new Probe();p.SetValueNoNotification(1,false);check(p.GetValue()==1&&p.visual==0&&p.updates==0&&p.notifications==0);
  p.SetValueNoNotification(0,true);p.SetValueNoNotification(1,true);check(p.visual==1&&p.updates==2&&p.notifications==0);
  p.SetValue(0,true);check(p.visual==0&&p.notifications==1);
  System.out.println("PASS installed VoltageButton API: GUI flag and suppressed action notifications (native panel rendering pending)");
 }
}
