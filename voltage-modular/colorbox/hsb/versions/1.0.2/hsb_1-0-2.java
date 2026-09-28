package com.insectlabs.hsb;


import voltage.controllers.*;
import voltage.core.*;
import voltage.core.Jack.JackType;
import voltage.sources.*;
import voltage.utility.*;
import voltage.processors.*;
import voltage.effects.*;
import java.awt.*;

//[user-imports]   Add your own imports here

//[/user-imports]


public class HSB extends VoltageModule
//[user-inheritance]

//[/user-inheritance]
{

@SuppressWarnings("this-escape") 
public HSB( long moduleID, VoltageObjects voltageObjects )
{
    super( moduleID, voltageObjects, "hsb", ModuleType.ModuleType_Processor, 0.8 );

    InitializeControls();


    canBeBypassed = true;
    SetSkin( "090be42f7f2346c78920a459dc5b01f4" );
}

void InitializeControls()
{

    topIn = new VoltageAudioJack( "topIn", "Top input", this, JackType.JackType_AudioInput );
    AddComponent( topIn );
    topIn.SetWantsMouseNotifications( false );
    topIn.SetPosition( 2, 20 );
    topIn.SetSize( 25, 25 );
    topIn.SetSkin( "Jack Round 25px" );

    topOut = new VoltageAudioJack( "topOut", "Top output (breaks mix normal)", this, JackType.JackType_AudioOutput );
    AddComponent( topOut );
    topOut.SetWantsMouseNotifications( false );
    topOut.SetPosition( 30, 140 );
    topOut.SetSize( 25, 25 );
    topOut.SetSkin( "Mini Jack 25px" );

    bottomIn = new VoltageAudioJack( "bottomIn", "Bottom input (normal: top input)", this, JackType.JackType_AudioInput );
    AddComponent( bottomIn );
    bottomIn.SetWantsMouseNotifications( false );
    bottomIn.SetPosition( 2, 180 );
    bottomIn.SetSize( 25, 25 );
    bottomIn.SetSkin( "Jack Round 25px" );

    bottomOut = new VoltageAudioJack( "bottomOut", "Bottom output / normalled mix", this, JackType.JackType_AudioOutput );
    AddComponent( bottomOut );
    bottomOut.SetWantsMouseNotifications( false );
    bottomOut.SetPosition( 30, 300 );
    bottomOut.SetSize( 25, 25 );
    bottomOut.SetSkin( "Mini Jack 25px" );

    topHue = new VoltageKnob( "topHue", "Top HUE", this, 0.0, 1.0, 0.5 );
    AddComponent( topHue );
    topHue.SetWantsMouseNotifications( false );
    topHue.SetPosition( 16, 50 );
    topHue.SetSize( 25, 25 );
    topHue.SetSkin( "Plastic Maroon" );
    topHue.SetRange( 0.0, 1.0, 0.5, false, 0 );
    topHue.SetKnobParams( 215, 145 );
    topHue.DisplayValueInPercent( false );
    topHue.SetKnobAdjustsRing( true );

    topSaturation = new VoltageKnob( "topSaturation", "Top SATURATION", this, 0.0, 1.0, 0.0 );
    AddComponent( topSaturation );
    topSaturation.SetWantsMouseNotifications( false );
    topSaturation.SetPosition( 16, 80 );
    topSaturation.SetSize( 25, 25 );
    topSaturation.SetSkin( "Plastic Orange" );
    topSaturation.SetRange( 0.0, 1.0, 0.0, false, 0 );
    topSaturation.SetKnobParams( 215, 145 );
    topSaturation.DisplayValueInPercent( false );
    topSaturation.SetKnobAdjustsRing( true );

    topBrilliance = new VoltageKnob( "topBrilliance", "Top BRILLIANCE", this, 0.0, 1.0, 0.5 );
    AddComponent( topBrilliance );
    topBrilliance.SetWantsMouseNotifications( false );
    topBrilliance.SetPosition( 16, 110 );
    topBrilliance.SetSize( 25, 25 );
    topBrilliance.SetSkin( "Plastic White" );
    topBrilliance.SetRange( 0.0, 1.0, 0.5, false, 0 );
    topBrilliance.SetKnobParams( 215, 145 );
    topBrilliance.DisplayValueInPercent( false );
    topBrilliance.SetKnobAdjustsRing( true );

    bottomHue = new VoltageKnob( "bottomHue", "Bottom HUE", this, 0.0, 1.0, 0.5 );
    AddComponent( bottomHue );
    bottomHue.SetWantsMouseNotifications( false );
    bottomHue.SetPosition( 16, 210 );
    bottomHue.SetSize( 25, 25 );
    bottomHue.SetSkin( "Plastic Maroon" );
    bottomHue.SetRange( 0.0, 1.0, 0.5, false, 0 );
    bottomHue.SetKnobParams( 215, 145 );
    bottomHue.DisplayValueInPercent( false );
    bottomHue.SetKnobAdjustsRing( true );

    bottomSaturation = new VoltageKnob( "bottomSaturation", "Bottom SATURATION", this, 0.0, 1.0, 0.0 );
    AddComponent( bottomSaturation );
    bottomSaturation.SetWantsMouseNotifications( false );
    bottomSaturation.SetPosition( 16, 240 );
    bottomSaturation.SetSize( 25, 25 );
    bottomSaturation.SetSkin( "Plastic Orange" );
    bottomSaturation.SetRange( 0.0, 1.0, 0.0, false, 0 );
    bottomSaturation.SetKnobParams( 215, 145 );
    bottomSaturation.DisplayValueInPercent( false );
    bottomSaturation.SetKnobAdjustsRing( true );

    bottomBrilliance = new VoltageKnob( "bottomBrilliance", "Bottom BRILLIANCE", this, 0.0, 1.0, 0.5 );
    AddComponent( bottomBrilliance );
    bottomBrilliance.SetWantsMouseNotifications( false );
    bottomBrilliance.SetPosition( 16, 270 );
    bottomBrilliance.SetSize( 25, 25 );
    bottomBrilliance.SetSkin( "Plastic White" );
    bottomBrilliance.SetRange( 0.0, 1.0, 0.5, false, 0 );
    bottomBrilliance.SetKnobParams( 215, 145 );
    bottomBrilliance.DisplayValueInPercent( false );
    bottomBrilliance.SetKnobAdjustsRing( true );

    textInsect = new VoltageLabel( "textInsect", "insect laboratories", this, "insect" );
    AddComponent( textInsect );
    textInsect.SetWantsMouseNotifications( false );
    textInsect.SetPosition( 0, 335 );
    textInsect.SetSize( 57, 23 );
    textInsect.SetEditable( false, false );
    textInsect.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
    textInsect.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
    textInsect.SetColor( new Color( 147, 0, 0, 255 ) );
    textInsect.SetBkColor( new Color( 35, 35, 35, 0 ) );
    textInsect.SetBorderColor( new Color( 85, 0, 0, 255 ) );
    textInsect.SetBorderSize( 4 );
    textInsect.SetMultiLineEdit( false );
    textInsect.SetIsNumberEditor( false );
    textInsect.SetNumberEditorRange( 0, 100 );
    textInsect.SetNumberEditorInterval( 1 );
    textInsect.SetNumberEditorUsesMouseWheel( false );
    textInsect.SetHasCustomTextHoverColor( false );
    textInsect.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
    textInsect.SetFont( "Courier New", 13, true, false );

    textCatalog = new VoltageLabel( "textCatalog", "hsb", this, "hsb" );
    AddComponent( textCatalog );
    textCatalog.SetWantsMouseNotifications( false );
    textCatalog.SetPosition( 3, 1 );
    textCatalog.SetSize( 23, 13 );
    textCatalog.SetEditable( false, false );
    textCatalog.SetJustificationFlags( VoltageLabel.Justification.Left );
    textCatalog.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
    textCatalog.SetColor( new Color( 147, 0, 0, 255 ) );
    textCatalog.SetBkColor( new Color( 35, 35, 35, 0 ) );
    textCatalog.SetBorderColor( new Color( 35, 35, 35, 0 ) );
    textCatalog.SetBorderSize( 1 );
    textCatalog.SetMultiLineEdit( false );
    textCatalog.SetIsNumberEditor( false );
    textCatalog.SetNumberEditorRange( 0, 100 );
    textCatalog.SetNumberEditorInterval( 1 );
    textCatalog.SetNumberEditorUsesMouseWheel( false );
    textCatalog.SetHasCustomTextHoverColor( false );
    textCatalog.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
    textCatalog.SetFont( "Courier New", 13, true, false );

    topTopology = new VoltageSwitch( "topTopology", "Top STANDARD / CUSTOM", this, 1 );
    AddComponent( topTopology );
    topTopology.SetWantsMouseNotifications( false );
    topTopology.SetPosition( 45, 85 );
    topTopology.SetSize( 7, 15 );
    topTopology.SetSkin( "2-State Slide Black" );

    bottomTopology = new VoltageSwitch( "bottomTopology", "Bottom STANDARD / CUSTOM", this, 1 );
    AddComponent( bottomTopology );
    bottomTopology.SetWantsMouseNotifications( false );
    bottomTopology.SetPosition( 5, 245 );
    bottomTopology.SetSize( 7, 15 );
    bottomTopology.SetSkin( "2-State Slide Black" );
}



//-------------------------------------------------------------------------------
//  public void Initialize()

//  Initialize will get called shortly after your module's constructor runs. You can use it to
//  do any initialization that the auto-generated code doesn't handle.
//-------------------------------------------------------------------------------
@Override
public void Initialize()
{
    //[user-Initialize]   Add your own initialization code here
    hsb = new HsbDsp();
    wasBypassed = false;
    readHsbControls();
    //[/user-Initialize]
}


//-------------------------------------------------------------------------------
//  public void Destroy()

//  Destroy will get called just before your module gets deleted. You can use it to perform any
//  cleanup that's not handled automatically by Java.
//-------------------------------------------------------------------------------
@Override
public void Destroy()
{
    super.Destroy();
    //[user-Destroy]   Add your own module-getting-deleted code here

    //[/user-Destroy]
}


//-------------------------------------------------------------------------------
//  public boolean Notify( VoltageComponent component, ModuleNotifications notification, double doubleValue, long longValue, int x, int y, Object object )

//  Notify will get called when various events occur - control values changing, timers firing, etc.
//-------------------------------------------------------------------------------
@Override
public boolean Notify( VoltageComponent component, ModuleNotifications notification, double doubleValue, long longValue, int x, int y, Object object )
{
    //[user-Notify]   Add your own notification handling code between this line and the notify-close comment
    // notify-close comment
    switch (notification) {
        case Knob_Changed: // doubleValue is the new VoltageKnob value
            {
            }
            break;

        case Slider_Changed: // doubleValue is the new slider value
            {
            }
            break;

        case Button_Changed: // doubleValue is the new button/toggle button value
            {
            }
            break;

        case Switch_Changed: // doubleValue is the new switch value
            {
            }
            break;

        case Jack_Connected: // longValue is the new cable ID
            {
            }
            break;

        case Jack_Disconnected: // All cables have been disconnected from this jack
            {
            }
            break;

        case GUI_Update_Timer: // Called every 50ms (by default) if turned on
            {
            }
            break;

        case Object_MouseMove: // called when mouse is over an object that receives mouse
                               // notifications. 'object' parameter is a VoltageMouseKeyFlags
                               // object.
            {
            }
            break;

        case Object_MouseLeave: // called when mouse leaves an object that receives mouse
                                // notifications. 'object' parameter is a VoltageMouseKeyFlags
                                // object.
            {
            }
            break;

        case Object_LeftButtonDown: // called when user left-clicks on an object that receives
                                    // mouse notifications. 'object' parameter is a
                                    // VoltageMouseKeyFlags object.
            {
            }
            break;

        case Object_LeftButtonUp: // called when user releases left mouse button on an object
                                  // that receives mouse notifications. 'object' parameter is a
                                  // VoltageMouseKeyFlags object.
            {
            }
            break;

        case Object_RightButtonDown: // called when user releases right mouse button on an
                                     // object that receives mouse notifications. 'object'
                                     // parameter is a VoltageMouseKeyFlags object.
            {
            }
            break;

        case Object_RightButtonUp: // called when user right-clicks on an object that receives
                                   // mouse notifications
            {
            }
            break;

        case Object_LeftButtonDoubleClick: // called when user left-button double-clicks on an
                                           // object that receives mouse notifications
            {
            }
            break;

        // Less common notifications:

        case Named_Timer: // object contains a String with the name of the timer that has fired
            {
            }
            break;

        case Canvas_Painting: // About to paint canvas.  object is a java.awt.Rectangle with
                              // painting boundaries
            {
            }
            break;

        case Canvas_Painted: // Canvas painting is complete
            {
            }
            break;

        case Control_DragStart: // A user has started dragging on a control that has been marked
                                // as draggable
            {
            }
            break;

        case Control_DragOn: // This control has been dragged over during a drag operation.
                             // object contains the dragged object
            {
            }
            break;

        case Control_DragOff: // This control has been dragged over during a drag operation.
                              // object contains the dragged object
            {
            }
            break;

        case Control_DragEnd: // A user has ended their drag on a control that has been marked
                              // as draggable
            {
            }
            break;

        case Label_Changed: // The text of an editable text control has changed
            {
            }
            break;

        case SoundPlayback_Start: // A sound has begun playback
            {
            }
            break;

        case SoundPlayback_End: // A sound has ended playback
            {
            }
            break;

        case Scrollbar_Position: // longValue is the new scrollbar position
            {
            }
            break;

        case PolyVoices_Changed: // longValue is the new number of poly voices
            {
            }
            break;

        case File_Dropped: // 'object' is a String containing the file path
            {
            }
            break;

        case Preset_Loading_Start: // called when preset loading begins
            {
            }
            break;

        case Preset_Loading_Finish: // called when preset loading finishes
            {
            }
            break;

        case Variation_Loading_Start: // sent when a variation is about to load
            {
            }
            break;

        case Variation_Loading_Finish: // sent when a variation has just finished loading
            {
            }
            break;

        case Tempo_Changed: // doubleValue is the new tempo
            {
            }
            break;

        case Randomized: // called when the module's controls get randomized
            {
            }
            break;

        case VariationListChanged: // sent when a variation gets added, deleted, or renamed, or
                                   // the variations list gets reordered
            {
            }
            break;

        case Key_Press: // sent when module has keyboard focus and a key is pressed; object is a
                        // VoltageKeyPressInfo object
            {
            }
            break;

        case Reset: // sent when the module has been reset to default settings
            {
            }
            break;

        case Keyboard_NoteOn: // sent when a note has been pressed on a VoltageKeyboard object.
                              // longValue is the note value ( 0-127 )
            {
            }
            break;

        case Keyboard_NoteOff: // sent when a note has been released on a VoltageKeyboard
                               // object. longValue is the note value ( 0-127 )
            {
            }
            break;

        case Curve_Changed: // sent when user has edited a curve's value. 'object' will be a
                            // VoltageCurve.CurveChangeNotification object.
            {
            }
            break;
    }

    return false;
    //[/user-Notify]
}


//-------------------------------------------------------------------------------
//  public void ProcessSample()

//  ProcessSample is called once per sample. Usually it's where you read
//  from input jacks, process audio, and write it to your output jacks.
//  Since ProcesssSample gets called 48,000 times per second, offload CPU-intensive operations
//  to other threads when possible and avoid calling native functions.
//-------------------------------------------------------------------------------
@Override
public void ProcessSample()
{
    //[user-ProcessSample]   Add your own process-sampling code here
    processHsb(false);
    //[/user-ProcessSample]
}


//-------------------------------------------------------------------------------
//  public void ProcessBypassedSample()

//  ProcessBypassedSample gets called instead of ProcessSample when you've checked the "Can Be Bypassed" box
//  and a user has bypassed your module. If your module processes data from input jacks and then sends it to
//  output jack(s), make ProcessBypassedSample just send the data from the input jacks to the output jacks without
//  processing it.
//-------------------------------------------------------------------------------
@Override
public void ProcessBypassedSample()
{
    //[user-ProcessBypassedSample]   Add your own code here
    processHsb(true);
    //[/user-ProcessBypassedSample]
}


//-------------------------------------------------------------------------------
//  public String GetTooltipText( VoltageComponent component )

//  Gets called when a tooltip is about to display for a control. Override it if
//  you want to change what the tooltip displays - if you want a knob to work in logarithmic fashion,
//  for instance, you can translate the knob's current value to a log-based string and display it here.
//-------------------------------------------------------------------------------
@Override
public String GetTooltipText( VoltageComponent component )
{
    //[user-GetTooltipText]   Add your own code here
    if (component == topTopology || component == bottomTopology)
        return ((component == topTopology ? topTopology : bottomTopology).GetValue() < 0.5)
                ? "CUSTOM - scramble fuzz"
                : "STANDARD - dual-feedback fuzz";
    return super.GetTooltipText(component);
    //[/user-GetTooltipText]
}


//-------------------------------------------------------------------------------
//  public void EditComponentValue( VoltageComponent component, double newValue, String newText )

//  Gets called after a user clicks on a tooltip and types in a new value for a control. Override this if
//  you've changed the default tooltip display (translating a linear value to logarithmic, for instance)
//  in GetTooltipText().
//-------------------------------------------------------------------------------
@Override
public void EditComponentValue( VoltageComponent component, double newValue, String newText )
{
    //[user-EditComponentValue]   Add your own code here

    //[/user-EditComponentValue]
    super.EditComponentValue( component, newValue, newText );
}


//-------------------------------------------------------------------------------
//  public void OnUndoRedo( String undoType, double newValue, Object optionalObject )

//  If you've created custom undo events via calls to CreateUndoEvent, you'll need to
//  process them in this function when they get triggered by undo/redo actions.
//-------------------------------------------------------------------------------
@Override
public void OnUndoRedo( String undoType, double newValue, Object optionalObject )
{
    //[user-OnUndoRedo]   Add your own code here

    //[/user-OnUndoRedo]
}


//-------------------------------------------------------------------------------
//  public byte[] GetStateInformation()

//  Gets called when the module's state gets saved, typically when the user saves a preset with
//  this module in it. Voltage Modular will automatically save the states of knobs, sliders, etc.,
//  but if you have any custom state information you need to save, return it from this function.
//-------------------------------------------------------------------------------
@Override
public byte[] GetStateInformation()
{
    //[user-GetStateInformation]   Add your own code here

    return null;
    //[/user-GetStateInformation]
}


//-------------------------------------------------------------------------------
//  public void SetStateInformation(byte[] stateInfo)

//  Gets called when this module's state is getting restored, typically when a user opens a preset with
//  this module in it. The stateInfo parameter will contain whatever custom data you stored in GetStateInformation().
//-------------------------------------------------------------------------------
@Override
public void SetStateInformation(byte[] stateInfo)
{
    //[user-SetStateInformation]   Add your own code here

    //[/user-SetStateInformation]
}


//-------------------------------------------------------------------------------
//  public byte[] GetStateInformationForVariations()

//  Gets called when a user saves a variation with this module in it.
//  Voltage Modular will automatically save the states of knobs, sliders, etc.,
//  but if you have any custom state information you need to save, return it from this function.
//-------------------------------------------------------------------------------
@Override
public byte[] GetStateInformationForVariations()
{
    //[user-GetStateInformationForVariations]   Add your own code here

    return GetStateInformation();
    //[/user-GetStateInformationForVariations]
}


//-------------------------------------------------------------------------------
//  public void SetStateInformationForVariations(byte[] stateInfo)

//  Gets called when a user loads a variation with this module in it.
//  The stateInfo parameter will contain whatever custom data you stored in GetStateInformationForVariations().
//-------------------------------------------------------------------------------
@Override
public void SetStateInformationForVariations(byte[] stateInfo)
{
    //[user-SetStateInformationForVariations]   Add your own code here
    SetStateInformation(stateInfo);

    //[/user-SetStateInformationForVariations]
}


// Auto-generated variables
private VoltageSwitch bottomTopology;
private VoltageSwitch topTopology;
private VoltageLabel textCatalog;
private VoltageLabel textInsect;
private VoltageKnob bottomBrilliance;
private VoltageKnob bottomSaturation;
private VoltageKnob bottomHue;
private VoltageKnob topBrilliance;
private VoltageKnob topSaturation;
private VoltageKnob topHue;
private VoltageAudioJack bottomOut;
private VoltageAudioJack bottomIn;
private VoltageAudioJack topOut;
private VoltageAudioJack topIn;


//[user-code-and-variables]    Add your own variables and functions here
// HSB 1.0.2 - insect laboratories.
// Colorbox: 2x processing; direct host bypass preserves routing and skips DSP.
// HSB 1.0.2 - insect laboratories. Locked DSP, 2x oversampling.
// Mono-first parallel normals, not a serial audio cascade.
private HsbDsp hsb;
private boolean wasBypassed = false;

private void readHsbControls() {
    hsb.top.setControls(
            2.0 * topHue.GetValue() - 1.0,
            topSaturation.GetValue(),
            2.0 * topBrilliance.GetValue() - 1.0,
            topTopology.GetValue() < 0.5);
    hsb.bottom.setControls(
            2.0 * bottomHue.GetValue() - 1.0,
            bottomSaturation.GetValue(),
            2.0 * bottomBrilliance.GetValue() - 1.0,
            bottomTopology.GetValue() < 0.5);
}

private void processHsb(boolean bypassed) {
    // Direct bypass is for CPU relief: do not read knobs or advance any DSP.
    if (bypassed) {
        double top = topIn.IsConnected() ? topIn.GetValue() : 0.0;
        double bottom = bottomIn.IsConnected() ? bottomIn.GetValue() : top;
        topOut.SetValue(top);
        bottomOut.SetValue(bottom + (topOut.IsConnected() ? 0.0 : top));
        wasBypassed = true;
        return;
    }

    readHsbControls();
    if (wasBypassed) {
        // Seed current panel targets and clear stale histories once on re-entry.
        hsb.reset();
        wasBypassed = false;
    }
    hsb.process(
            topIn.IsConnected() ? topIn.GetValue() : 0.0,
            bottomIn.GetValue(),
            bottomIn.IsConnected(),
            topOut.IsConnected(),
            false);
    topOut.SetValue(hsb.out1);
    bottomOut.SetValue(hsb.out2);
}

/** HSB 0.4 tone audition; accepted v0.2 fuzz. Java 8+, single audio thread. */
private static final class HsbDsp {
    public static final double SAMPLE_RATE = 48000.0;
    public static final int LATENCY = 32;
    private static final int OS = 2;
    private static final double RATE = SAMPLE_RATE * OS;
    private static final double[] FIR = makeFir();
    public final Stage top = new Stage();
    public final Stage bottom = new Stage();
    public double out1, out2;
    private double normalMix = 1, bypassMix;
    private boolean started;

    /** Jack values in volts; absent input 1 must be supplied as zero. */
    public void process(
            double in1,
            double in2,
            boolean input2Patched,
            boolean output1Patched,
            boolean bypassed) {
        double mixTarget = output1Patched ? 0 : 1;
        if (!started) {
            normalMix = mixTarget;
            bypassMix = bypassed ? 1 : 0;
            started = true;
        }
        // Linear 5 ms ramps have exact endpoints. Both channels keep running.
        normalMix = approach(normalMix, mixTarget, 1.0 / 240);
        bypassMix = approach(bypassMix, bypassed ? 1 : 0, 1.0 / 240);
        double a = top.process(safe(in1), bypassMix);
        double b = bottom.process(safe(input2Patched ? in2 : in1), bypassMix);
        out1 = a;
        out2 = b + normalMix * a;
    }

    public void reset() {
        top.reset();
        bottom.reset();
        started = false;
        out1 = out2 = 0;
    }

    private static double approach(double x, double y, double step) {
        return x + Math.max(-step, Math.min(step, y - x));
    }

    private static double safe(double x) {
        return Double.isFinite(x) ? Math.max(-1000, Math.min(1000, x)) : 0;
    }

    private static double unit(double x) {
        return Math.max(0, Math.min(1, safe(x)));
    }

    public static final class Stage {
        private final Fir up = new Fir(), down = new Fir();
        private final double[] dry = new double[LATENCY];
        private int dryPos;
        private double hTarget, sTarget, bTarget, mTarget;
        private double h, s, b, mode;
        private boolean initialized;
        private final Tilt tilt = new Tilt();
        private final Feedback first = new Feedback(), second = new Feedback();
        private final Dc standardDc = new Dc(), customDc = new Dc();
        private final Lowpass smooth1 = new Lowpass(), smooth2 = new Lowpass();
        private final Lowpass air = new Lowpass();
        private double envelope, customCoupling;
        private double drive = 1, wet, chaos, tail, makeup = 1, cut, boost, lowBlend;
        private int controlTick;
        private static final double ENV_ATTACK = 1 - Math.exp(-1 / (.002 * RATE));
        private static final double ENV_RELEASE = 1 - Math.exp(-1 / (.045 * RATE));
        private static final double COUPLING = 1 - Math.exp(-2 * Math.PI * 18 / RATE);

        /** HUE and BRILLIANCE: -1..1. SATURATION: 0..1. true selects CUSTOM. */
        public void setControls(
                double hue, double saturation, double brilliance, boolean custom) {
            hTarget = 2 * unit((hue + 1) * .5) - 1;
            sTarget = unit(saturation);
            bTarget = 2 * unit((brilliance + 1) * .5) - 1;
            mTarget = custom ? 1 : 0;
            if (!initialized) {
                h = hTarget;
                s = sTarget;
                b = bTarget;
                mode = mTarget;
                initialized = true;
                coefficients();
            }
        }

        private void coefficients() {
            tilt.configure(h);
            // Preserve v0.1 through 70%; add up to 9 dB per cell above that.
            // Smoothstep has zero slope at the junction and maximum.
            tail = Math.max(0, (s - .7) / .3);
            tail = tail * tail * (3 - 2 * tail);
            drive = Math.pow(10, 1.45 * s * s + .45 * tail);
            wet = Math.min(1, s / .35);
            wet = wet * wet * (3 - 2 * wet);
            chaos = Math.max(0, (s - .3) / .7);
            chaos *= chaos;
            makeup = 1 / Math.pow(drive, .36);
            lowBlend = Math.max(0, -b);
            cut = 22000 * Math.pow(4500.0 / 22000, lowBlend);
            smooth1.configure(cut);
            smooth2.configure(cut);
            air.configure(5000);
            boost = Math.pow(10, Math.max(0, b) * 12 / 20) - 1;
        }

        private double process(double volts, double bypass) {
            if (!initialized) setControls(0, 0, 0, false);
            h = approach(h, hTarget, 2.0 / 960);
            b = approach(b, bTarget, 2.0 / 960);
            s = approach(s, sTarget, 1.0 / 960);
            mode = approach(mode, mTarget, 1.0 / 480);
            if ((controlTick++ & 15) == 0) coefficients();
            double delayed = dry[dryPos];
            dry[dryPos] = volts;
            dryPos = (dryPos + 1) % LATENCY;
            double result = 0;
            for (int i = 0; i < OS; i++) {
                double x = tilt.process(up.process(i == 0 ? volts / 5 * OS : 0));
                double std = second.process(first.process(x, drive), drive) * makeup;
                std = standardDc.process(std);

                // Two asymmetric gain stages, AC coupling and signal-dependent bias.
                // Rectification increasingly replaces the interstage feed:
                // octave/intermodulation.
                double abs = Math.abs(x);
                envelope += (abs > envelope ? ENV_ATTACK : ENV_RELEASE) * (abs - envelope);
                double bias = chaos * (.12 + .65 * envelope);
                double a = Math.tanh(drive * x + bias) - Math.tanh(bias);
                double inter = (1 - (.85 + .15 * tail) * chaos) * a + 1.5 * chaos * Math.abs(a);
                customCoupling += COUPLING * (inter - customCoupling);
                double shifted = inter - customCoupling - chaos * (.35 + .30 * tail) * envelope;
                double custom = Math.tanh(drive * shifted);
                custom = customDc.process(custom) * (1 + .35 * s);

                double fuzz = std + mode * (custom - std);
                double y = x + wet * (fuzz - x);
                double lp = smooth2.process(smooth1.process(y));
                double airLow = air.process(y);
                y += lowBlend * (lp - y) + boost * (y - airLow);
                double filtered = down.process(y);
                // Phase zero yields the documented integer 32-sample FIR delay.
                if (i == 0) result = 5 * filtered;
            }
            return result + bypass * (delayed - result);
        }

        public void reset() {
            up.reset();
            down.reset();
            java.util.Arrays.fill(dry, 0);
            dryPos = 0;
            tilt.reset();
            first.reset();
            second.reset();
            standardDc.reset();
            customDc.reset();
            smooth1.reset();
            smooth2.reset();
            air.reset();
            envelope = customCoupling = 0;
            controlTick = 0;
            h = hTarget;
            s = sTarget;
            b = bTarget;
            mode = mTarget;
            coefficients();
        }
    }

    /**
     * Bilinear first-order reciprocal shelf: exactly unity at 800 Hz and h=0. Analog
     * H(s)=(g*s+w)/(s+g*w), giving DC=1/g, HF=g.
     */
    private static final class Tilt {
        double b0 = 1, b1, a1, x1, y1;

        void configure(double hue) {
            double g = Math.pow(10, 10 * hue / 20);
            double w = Math.tan(Math.PI * 800 / RATE);
            double den = 1 + g * w;
            b0 = (g + w) / den;
            b1 = (w - g) / den;
            a1 = (g * w - 1) / den;
        }

        double process(double x) {
            double y = b0 * x + b1 * x1 - a1 * y1;
            x1 = x;
            y1 = y;
            return y;
        }

        void reset() {
            x1 = y1 = 0;
        }
    }

    /**
     * Reduced nonlinear negative-feedback cell, not a transistor/diode emulation. Solve (1+k)y
     * + 0.5*y^3 = drive*x + k*previousLowpass using bounded Newton work. Feedback memory
     * provides a mild frequency-dependent clipping response.
     */
    private static final class Feedback {
        double memory;
        final double alpha = 1 - Math.exp(-2 * Math.PI * 6500 / RATE);

        double process(double x, double gain) {
            double rhs = gain * x + .25 * memory;
            double y = Math.cbrt(2 * rhs);
            for (int i = 0; i < 8; i++)
                y -= (1.25 * y + .5 * y * y * y - rhs) / (1.25 + 1.5 * y * y);
            memory += alpha * (y - memory);
            return y;
        }

        void reset() {
            memory = 0;
        }
    }

    private static final class Lowpass {
        double a, b, x1, y1;

        void configure(double hz) {
            double k = Math.tan(Math.PI * hz / RATE);
            b = k / (1 + k);
            a = (k - 1) / (k + 1);
        }

        double process(double x) {
            double y = b * (x + x1) - a * y1;
            x1 = x;
            y1 = y;
            return y;
        }

        void reset() {
            x1 = y1 = 0;
        }
    }

    private static final class Dc {
        final double pole = Math.exp(-2 * Math.PI * 8 / RATE);
        double x1, y1;

        double process(double x) {
            double y = (1 + pole) * .5 * (x - x1) + pole * y1;
            x1 = x;
            y1 = y;
            return y;
        }

        void reset() {
            x1 = y1 = 0;
        }
    }

    private static final class Fir {
        final double[] delay = new double[FIR.length];
        int p;

        double process(double x) {
            delay[p] = x;
            double y = 0;
            int j = p;
            for (int i = 0; i < FIR.length; i++) {
                y += FIR[i] * delay[j];
                if (--j < 0) j = FIR.length - 1;
            }
            if (++p == FIR.length) p = 0;
            return y;
        }

        void reset() {
            java.util.Arrays.fill(delay, 0);
            p = 0;
        }
    }

    private static double[] makeFir() {
        // Same filter duration and 21 kHz cutoff at either experimental rate.
        // 4x: 129 taps at 192 kHz; 2x: 65 taps at 96 kHz. Pair delay: 32 host samples.
        int half = 16 * OS;
        double[] h = new double[2 * half + 1];
        double sum = 0, fc = 21000.0 / RATE;
        for (int i = 0; i < h.length; i++) {
            int n = i - half;
            double sinc = n == 0 ? 2 * fc : Math.sin(2 * Math.PI * fc * n) / (Math.PI * n);
            double window =
                    .42
                            - .5 * Math.cos(2 * Math.PI * i / (2 * half))
                            + .08 * Math.cos(4 * Math.PI * i / (2 * half));
            h[i] = sinc * window;
            sum += h[i];
        }
        for (int i = 0; i < h.length; i++) h[i] /= sum;
        return h;
    }
}
//[/user-code-and-variables]
}

 