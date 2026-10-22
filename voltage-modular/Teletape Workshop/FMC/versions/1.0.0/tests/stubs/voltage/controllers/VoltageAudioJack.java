package voltage.controllers;
import voltage.core.*;
/** STUB: IsConnected/GetValue/SetValue as documented. */
public class VoltageAudioJack extends VoltageComponent {
    public boolean connected;
    public double value;
    public int getValueCalls;
    public VoltageAudioJack(String n, VoltageModule o) { super(n, o); }
    public boolean IsConnected() { return connected; }
    public double GetValue() { getValueCalls++; return value; }
    public void SetValue(double v) { value = v; }
}
