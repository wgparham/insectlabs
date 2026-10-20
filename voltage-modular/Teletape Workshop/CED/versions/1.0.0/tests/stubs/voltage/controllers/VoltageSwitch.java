package voltage.controllers;
import voltage.core.*;
/** STUB: GetValue/SetValue as documented. */
public class VoltageSwitch extends VoltageComponent {
    private double value;
    public VoltageSwitch(String n, VoltageModule o, double initial) { super(n, o); value = initial; }
    public double GetValue() { return value; }
    public void SetValue(double v) {
        value = v;
        owner.Notify(this, ModuleNotifications.Switch_Changed, v, 0L, 0, 0, null);
    }
}
