package voltage.controllers;
import voltage.core.*;
/** STUB: GetValue/SetValue as documented. SetValue fires Knob_Changed like a user move (assumption). */
public class VoltageKnob extends VoltageComponent {
    private double value;
    public VoltageKnob(String n, VoltageModule o, double initial) { super(n, o); value = initial; }
    public double GetValue() { return value; }
    public void SetValue(double v) {
        value = v;
        owner.Notify(this, ModuleNotifications.Knob_Changed, v, 0L, 0, 0, null);
    }
}
