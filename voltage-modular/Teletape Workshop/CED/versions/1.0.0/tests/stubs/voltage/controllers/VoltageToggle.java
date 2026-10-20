package voltage.controllers;
import voltage.core.*;
/** STUB: SetValue is an ASSUMPTION (documented for VoltageButton). Records calls; can re-notify. */
public class VoltageToggle extends VoltageComponent {
    public boolean notifyOnSetValue = true;
    public int setValueCalls = 0;
    private double value;
    public VoltageToggle(String n, VoltageModule o) { super(n, o); }
    public double GetValue() { return value; }
    public void SetValue(double v) {
        setValueCalls++;
        value = v;
        if (notifyOnSetValue) owner.Notify(this, ModuleNotifications.Button_Changed, v, 0L, 0, 0, null);
    }
    /** Test helper: a user click. */
    public void userClick(boolean down) {
        value = down ? 1.0 : 0.0;
        owner.Notify(this, ModuleNotifications.Button_Changed, value, 0L, 0, 0, null);
    }
}
