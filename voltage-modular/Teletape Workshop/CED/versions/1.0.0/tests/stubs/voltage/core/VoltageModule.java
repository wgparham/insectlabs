package voltage.core;
/** STUB of the lifecycle surface used by the template; semantics are my reading of the docs. */
public abstract class VoltageModule {
    public enum ModuleType { ModuleType_Source, ModuleType_Effect }
    protected boolean canBeBypassed;
    protected VoltageModule(long moduleID, VoltageObjects objects, String name, ModuleType type, double width) { }
    protected void AddComponent(VoltageComponent c) { }
    protected void SetSkin(String id) { }
    public void Initialize() { }
    public void Destroy() { }
    public boolean Notify(VoltageComponent component, ModuleNotifications notification, double doubleValue,
                          long longValue, int x, int y, Object object) { return false; }
    public void ProcessSample() { }
    public void ProcessBypassedSample() { }
    public String GetTooltipText(VoltageComponent component) { return ""; }
    /** Assumption encoded in this stub: the base implementation applies newValue to a knob. */
    public void EditComponentValue(VoltageComponent component, double newValue, String newText) {
        if (component instanceof voltage.controllers.VoltageKnob) {
            ((voltage.controllers.VoltageKnob) component).SetValue(newValue);
        }
    }
    public void OnUndoRedo(String undoType, double newValue, Object optionalObject) { }
    public byte[] GetStateInformation() { return null; }
    public void SetStateInformation(byte[] stateInfo) { }
    public byte[] GetStateInformationForVariations() { return null; }
    public void SetStateInformationForVariations(byte[] stateInfo) { }
}
