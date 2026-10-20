package voltage.core;
/** STUB: not the Voltage SDK. */
public abstract class VoltageComponent {
    public final String internalName;
    public final VoltageModule owner;
    protected VoltageComponent(String internalName, VoltageModule owner) { this.internalName = internalName; this.owner = owner; }
    public void SetWantsMouseNotifications(boolean b) { }
    public void SetPosition(int x, int y) { }
    public void SetSize(int w, int h) { }
}
