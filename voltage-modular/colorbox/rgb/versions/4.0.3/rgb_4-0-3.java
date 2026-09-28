package com.insectlabs.rgb;


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


public class RGB extends VoltageModule
//[user-inheritance]

//[/user-inheritance]
{

@SuppressWarnings("this-escape") 
public RGB( long moduleID, VoltageObjects voltageObjects )
{
    super( moduleID, voltageObjects, "rgb", ModuleType.ModuleType_Processor, 0.8 );

    InitializeControls();


    canBeBypassed = true;
    SetSkin( "2f59ac59d2184eb68402ea7550256b96" );
}

void InitializeControls()
{

    redIn = new VoltageAudioJack( "redIn", "red in", this, JackType.JackType_AudioInput );
    AddComponent( redIn );
    redIn.SetWantsMouseNotifications( false );
    redIn.SetPosition( 16, 20 );
    redIn.SetSize( 25, 25 );
    redIn.SetSkin( "Jack Round 25px" );

    greenIn = new VoltageAudioJack( "greenIn", "green in", this, JackType.JackType_AudioInput );
    AddComponent( greenIn );
    greenIn.SetWantsMouseNotifications( false );
    greenIn.SetPosition( 16, 120 );
    greenIn.SetSize( 25, 25 );
    greenIn.SetSkin( "Jack Round 25px" );

    blueIn = new VoltageAudioJack( "blueIn", "blue in", this, JackType.JackType_AudioInput );
    AddComponent( blueIn );
    blueIn.SetWantsMouseNotifications( false );
    blueIn.SetPosition( 16, 220 );
    blueIn.SetSize( 25, 25 );
    blueIn.SetSkin( "Jack Round 25px" );

    redOut = new VoltageAudioJack( "redOut", "red out", this, JackType.JackType_AudioOutput );
    AddComponent( redOut );
    redOut.SetWantsMouseNotifications( false );
    redOut.SetPosition( 16, 80 );
    redOut.SetSize( 25, 25 );
    redOut.SetSkin( "Mini Jack 25px" );

    greenOut = new VoltageAudioJack( "greenOut", "green out", this, JackType.JackType_AudioOutput );
    AddComponent( greenOut );
    greenOut.SetWantsMouseNotifications( false );
    greenOut.SetPosition( 16, 180 );
    greenOut.SetSize( 25, 25 );
    greenOut.SetSkin( "Mini Jack 25px" );

    blueOut = new VoltageAudioJack( "blueOut", "blue out", this, JackType.JackType_AudioOutput );
    AddComponent( blueOut );
    blueOut.SetWantsMouseNotifications( false );
    blueOut.SetPosition( 16, 280 );
    blueOut.SetSize( 25, 25 );
    blueOut.SetSkin( "Mini Jack 25px" );

    redAmount = new VoltageKnob( "redAmount", "red drive", this, 1.0, 10.0, 1.0 );
    AddComponent( redAmount );
    redAmount.SetWantsMouseNotifications( false );
    redAmount.SetPosition( 16, 50 );
    redAmount.SetSize( 25, 25 );
    redAmount.SetSkin( "Plastic Red" );
    redAmount.SetRange( 1.0, 10.0, 1.0, false, 0 );
    redAmount.SetKnobParams( 215, 145 );
    redAmount.DisplayValueInPercent( false );
    redAmount.SetKnobAdjustsRing( true );

    greenAmount = new VoltageKnob( "greenAmount", "green drive", this, 1.0, 10.0, 1.0 );
    AddComponent( greenAmount );
    greenAmount.SetWantsMouseNotifications( false );
    greenAmount.SetPosition( 16, 150 );
    greenAmount.SetSize( 25, 25 );
    greenAmount.SetSkin( "Plastic Mint" );
    greenAmount.SetRange( 1.0, 10.0, 1.0, false, 0 );
    greenAmount.SetKnobParams( 215, 145 );
    greenAmount.DisplayValueInPercent( false );
    greenAmount.SetKnobAdjustsRing( true );

    blueAmount = new VoltageKnob( "blueAmount", "blue drive", this, 1.0, 10.0, 1.0 );
    AddComponent( blueAmount );
    blueAmount.SetWantsMouseNotifications( false );
    blueAmount.SetPosition( 16, 250 );
    blueAmount.SetSize( 25, 25 );
    blueAmount.SetSkin( "Plastic Dark Blue" );
    blueAmount.SetRange( 1.0, 10.0, 1.0, false, 0 );
    blueAmount.SetKnobParams( 215, 145 );
    blueAmount.DisplayValueInPercent( false );
    blueAmount.SetKnobAdjustsRing( true );

    dcBlockSwitch = new VoltageSwitch( "dcBlockSwitch", "DC filter", this, 1 );
    AddComponent( dcBlockSwitch );
    dcBlockSwitch.SetWantsMouseNotifications( false );
    dcBlockSwitch.SetPosition( 10, 316 );
    dcBlockSwitch.SetSize( 37, 15 );
    dcBlockSwitch.SetSkin( "Rocker Switch Plastic Black Hor" );

    textInsect = new VoltageLabel( "textInsect", "insect laboratories", this, "insect" );
    AddComponent( textInsect );
    textInsect.SetWantsMouseNotifications( false );
    textInsect.SetPosition( 0, 335 );
    textInsect.SetSize( 57, 23 );
    textInsect.SetEditable( false, false );
    textInsect.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
    textInsect.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
    textInsect.SetColor( new Color( 147, 0, 0, 255 ) );
    textInsect.SetBkColor( new Color( 65, 65, 65, 0 ) );
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

    textCatalog = new VoltageLabel( "textCatalog", "catalog", this, "rgb" );
    AddComponent( textCatalog );
    textCatalog.SetWantsMouseNotifications( false );
    textCatalog.SetPosition( 3, 1 );
    textCatalog.SetSize( 28, 13 );
    textCatalog.SetEditable( false, false );
    textCatalog.SetJustificationFlags( VoltageLabel.Justification.Left );
    textCatalog.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
    textCatalog.SetColor( new Color( 147, 0, 0, 255 ) );
    textCatalog.SetBkColor( new Color( 65, 65, 65, 0 ) );
    textCatalog.SetBorderColor( new Color( 80, 0, 0, 0 ) );
    textCatalog.SetBorderSize( 1 );
    textCatalog.SetMultiLineEdit( false );
    textCatalog.SetIsNumberEditor( false );
    textCatalog.SetNumberEditorRange( 0, 100 );
    textCatalog.SetNumberEditorInterval( 1 );
    textCatalog.SetNumberEditorUsesMouseWheel( false );
    textCatalog.SetHasCustomTextHoverColor( false );
    textCatalog.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
    textCatalog.SetFont( "Courier New", 13, true, false );
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

    resetDsp();

    wasBypassed = false;

    smoothedRedAmount = redAmount.GetValue();

    smoothedGreenAmount = greenAmount.GetValue();

    smoothedBlueAmount = blueAmount.GetValue();
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

    // =============================================================
    // RGB V4 - TRIPLE OVERSAMPLED WAVESHAPER
    //
    // RED   = TANH
    // GREEN = ATAN
    // BLUE  = drive-dependent asymmetric exponential
    //
    // Voltage Modular I/O: 48 kHz
    // Internal nonlinear processing: 96 kHz
    // =============================================================

    // -------------------------------------------------------------
    // RESET FILTER STATE ON RETURN FROM TRUE BYPASS
    // -------------------------------------------------------------

    if (wasBypassed) {
        resetDsp();
        wasBypassed = false;
    }

    // -------------------------------------------------------------
    // PARAMETER SMOOTHING
    // -------------------------------------------------------------

    smoothedRedAmount += (redAmount.GetValue() - smoothedRedAmount) * CONTROL_SMOOTH;

    smoothedGreenAmount += (greenAmount.GetValue() - smoothedGreenAmount) * CONTROL_SMOOTH;

    smoothedBlueAmount += (blueAmount.GetValue() - smoothedBlueAmount) * CONTROL_SMOOTH;

    // Cache jack and switch states once per native sample.

    boolean redInputConnected = redIn.IsConnected();

    boolean greenInputConnected = greenIn.IsConnected();

    boolean blueInputConnected = blueIn.IsConnected();

    boolean dcBlockOn = dcBlockSwitch.GetValue() > 0.5;

    // Internal 96 kHz signal pairs.
    // A = first oversampled phase
    // B = second oversampled phase

    double redA = 0.0;
    double redB = 0.0;

    double greenA = 0.0;
    double greenB = 0.0;

    // =============================================================
    // RED - TANH
    // =============================================================

    if (redInputConnected) {
        double redInput = redIn.GetValue();

        double redInputA = 2.0 * redInputUpsampler.process(redInput);

        double redInputB = 2.0 * redInputUpsampler.process(0.0);

        double redDrive = smoothedRedAmount;

        double effectiveRedDrive = 1.0 + ((redDrive - 1.0) * RED_DRIVE_SCALE);

        double redCompensation = driveCompensation(redDrive);

        double redDriveScale = effectiveRedDrive / NOMINAL_VOLTAGE;

        redA = NOMINAL_VOLTAGE * Math.tanh(redInputA * redDriveScale) * redCompensation;

        redB = NOMINAL_VOLTAGE * Math.tanh(redInputB * redDriveScale) * redCompensation;

        if (dcBlockOn) {
            redA = dcBlockRed(redA);

            redB = dcBlockRed(redB);
        } else {
            resetRedDc();
        }

        double redOutput = redOutputDownsampler.process(redA);

        redOutputDownsampler.process(redB);

        redOut.SetValue(redOutput);
    } else {
        redOut.SetValue(0.0);

        redInputUpsampler.reset();
        redOutputDownsampler.reset();
        resetRedDc();
    }

    // =============================================================
    // GREEN - ATAN
    //
    // GREEN IN overrides the internally normalled RED signal.
    // =============================================================

    boolean greenHasInput = greenInputConnected || redInputConnected;

    if (greenHasInput) {
        double greenSourceA;
        double greenSourceB;

        if (greenInputConnected) {
            double greenInput = greenIn.GetValue();

            greenSourceA = 2.0 * greenInputUpsampler.process(greenInput);

            greenSourceB = 2.0 * greenInputUpsampler.process(0.0);
        } else {
            // RED is already at 96 kHz.

            greenInputUpsampler.reset();

            greenSourceA = redA;
            greenSourceB = redB;
        }

        double greenDrive = smoothedGreenAmount;

        double effectiveGreenDrive = 1.0 + ((greenDrive - 1.0) * GREEN_DRIVE_SCALE);

        double greenCompensation = driveCompensation(greenDrive);

        double greenDriveScale = effectiveGreenDrive / NOMINAL_VOLTAGE;

        greenA =
                NOMINAL_VOLTAGE
                        * Math.atan(greenSourceA * greenDriveScale)
                        * TWO_OVER_PI
                        * greenCompensation;

        greenB =
                NOMINAL_VOLTAGE
                        * Math.atan(greenSourceB * greenDriveScale)
                        * TWO_OVER_PI
                        * greenCompensation;

        if (dcBlockOn) {
            greenA = dcBlockGreen(greenA);

            greenB = dcBlockGreen(greenB);
        } else {
            resetGreenDc();
        }

        double greenOutput = greenOutputDownsampler.process(greenA);

        greenOutputDownsampler.process(greenB);

        greenOut.SetValue(greenOutput);
    } else {
        greenOut.SetValue(0.0);

        greenInputUpsampler.reset();
        greenOutputDownsampler.reset();
        resetGreenDc();
    }

    // =============================================================
    // BLUE - ASYMMETRIC EXPONENTIAL
    //
    // BLUE IN overrides the internally normalled GREEN signal.
    // =============================================================

    boolean blueHasInput = blueInputConnected || greenHasInput;

    if (blueHasInput) {
        double blueSourceA;
        double blueSourceB;

        if (blueInputConnected) {
            double blueInput = blueIn.GetValue();

            blueSourceA = 2.0 * blueInputUpsampler.process(blueInput);

            blueSourceB = 2.0 * blueInputUpsampler.process(0.0);
        } else {
            // GREEN is already at 96 kHz.

            blueInputUpsampler.reset();

            blueSourceA = greenA;
            blueSourceB = greenB;
        }

        double blueDrive = smoothedBlueAmount;

        double blueCompensation = driveCompensation(blueDrive);

        double blueDriveScale = blueDrive / NOMINAL_VOLTAGE;

        // ---------------------------------------------------------
        // DRIVE-DEPENDENT BLUE ASYMMETRY
        //
        // Drive 1:
        //     positive rate = 1.00
        //     negative rate = 1.00
        //
        // Drive 10:
        //     positive rate = 0.80
        //     negative rate = 1.40
        //
        // Square-root progression brings the asymmetry in
        // quickly without over-coloring the minimum setting.
        // ---------------------------------------------------------

        double blueDriveNormalized = (blueDrive - 1.0) / BLUE_DRIVE_SPAN;

        double blueAsymmetry = Math.sqrt(blueDriveNormalized);

        double bluePosRate =
                BLUE_POS_RATE_MIN + ((BLUE_POS_RATE_MAX - BLUE_POS_RATE_MIN) * blueAsymmetry);

        double blueNegRate =
                BLUE_NEG_RATE_MIN + ((BLUE_NEG_RATE_MAX - BLUE_NEG_RATE_MIN) * blueAsymmetry);

        double blueShapedA = blueShape(blueSourceA * blueDriveScale, bluePosRate, blueNegRate);

        double blueShapedB = blueShape(blueSourceB * blueDriveScale, bluePosRate, blueNegRate);

        double blueA = NOMINAL_VOLTAGE * blueShapedA * blueCompensation;

        double blueB = NOMINAL_VOLTAGE * blueShapedB * blueCompensation;

        if (dcBlockOn) {
            blueA = dcBlockBlue(blueA);

            blueB = dcBlockBlue(blueB);
        } else {
            resetBlueDc();
        }

        double blueOutput = blueOutputDownsampler.process(blueA);

        blueOutputDownsampler.process(blueB);

        blueOut.SetValue(blueOutput);
    } else {
        blueOut.SetValue(0.0);

        blueInputUpsampler.reset();
        blueOutputDownsampler.reset();
        resetBlueDc();
    }
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

    // =============================================================
    // TRUE BYPASS
    //
    // Preserve RED -> GREEN -> BLUE normalization,
    // but perform no waveshaping, filtering, smoothing,
    // or oversampling.
    // =============================================================

    double bypassRed = redIn.GetValue();

    redOut.SetValue(bypassRed);

    double bypassGreen;

    if (greenIn.IsConnected()) {
        bypassGreen = greenIn.GetValue();
    } else {
        bypassGreen = bypassRed;
    }

    greenOut.SetValue(bypassGreen);

    double bypassBlue;

    if (blueIn.IsConnected()) {
        bypassBlue = blueIn.GetValue();
    } else {
        bypassBlue = bypassGreen;
    }

    blueOut.SetValue(bypassBlue);

    // Reset DSP history once when active processing resumes.

    wasBypassed = true;
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
    if (component == redAmount)
        return "RED drive: "
                + (Math.round(redAmount.GetValue() * 100.0) / 100.0)
                + " (1-10; soft saturation)";
    if (component == redIn) return "RED audio input (unpatched: silence)";
    if (component == redOut)
        return "RED audio output; also feeds GREEN when its input is unpatched";
    if (component == greenAmount)
        return "GREEN drive: "
                + (Math.round(greenAmount.GetValue() * 100.0) / 100.0)
                + " (1-10; rounded saturation)";
    if (component == greenIn) return "GREEN audio input (unpatched: RED stage output)";
    if (component == greenOut)
        return "GREEN audio output; also feeds BLUE when its input is unpatched";
    if (component == blueAmount)
        return "BLUE drive: "
                + (Math.round(blueAmount.GetValue() * 100.0) / 100.0)
                + " (1-10; asymmetric saturation)";
    if (component == blueIn) return "BLUE audio input (unpatched: GREEN stage output)";
    if (component == blueOut) return "BLUE audio output";
    if (component == dcBlockSwitch)
        return dcBlockSwitch.GetValue() > 0.5 ? "DC blocking: ON" : "DC blocking: OFF";
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
private VoltageLabel textCatalog;
private VoltageLabel textInsect;
private VoltageSwitch dcBlockSwitch;
private VoltageKnob blueAmount;
private VoltageKnob greenAmount;
private VoltageKnob redAmount;
private VoltageAudioJack blueOut;
private VoltageAudioJack greenOut;
private VoltageAudioJack redOut;
private VoltageAudioJack blueIn;
private VoltageAudioJack greenIn;
private VoltageAudioJack redIn;


//[user-code-and-variables]    Add your own variables and functions here
// RGB 4.0.3 - insect laboratories.
// Colorbox: 2x processing; direct host bypass preserves routing and skips DSP.

// =============================================================
// RGB V4 - CONSTANTS
// =============================================================

// Voltage Modular processes at 48 kHz.
// rgb performs nonlinear processing internally at 96 kHz.

private static final double NOMINAL_VOLTAGE = 5.0;

private static final double TWO_OVER_PI = 0.6366197723675814;

// Approximately the same ~3.8 Hz DC-blocking corner
// as the original 48 kHz coefficient, adjusted for 96 kHz.

private static final double DC_POLE_2X = 0.9997499687421851;

// Approximately 10 ms parameter smoothing at 48 kHz.

private static final double CONTROL_SMOOTH = 0.002;

// Automatic output restraint.
// Deliberately not full level matching.

private static final double DRIVE_COMPENSATION_FACTOR = 0.10;

// -------------------------------------------------------------
// COLOR VOICING
// -------------------------------------------------------------

// RED = TANH: firmer and more aggressive.

private static final double RED_DRIVE_SCALE = 1.25;

// GREEN = ATAN: rounder and more gradual.

private static final double GREEN_DRIVE_SCALE = 0.70;

// BLUE = exponential:
// symmetric at Drive 1 and increasingly asymmetric toward 10.

private static final double BLUE_POS_RATE_MIN = 1.00;

private static final double BLUE_POS_RATE_MAX = 0.80;

private static final double BLUE_NEG_RATE_MIN = 1.00;

private static final double BLUE_NEG_RATE_MAX = 1.40;

// Drive runs from 1 to 10, giving a span of 9.

private static final double BLUE_DRIVE_SPAN = 9.0;

// =============================================================
// PARAMETER STATE
// =============================================================

private double smoothedRedAmount = 1.0;
private double smoothedGreenAmount = 1.0;
private double smoothedBlueAmount = 1.0;

// =============================================================
// DC BLOCKER STATE
// =============================================================

private double redDcPrevInput = 0.0;
private double redDcPrevOutput = 0.0;

private double greenDcPrevInput = 0.0;
private double greenDcPrevOutput = 0.0;

private double blueDcPrevInput = 0.0;
private double blueDcPrevOutput = 0.0;

// =============================================================
// DRIVE COMPENSATION
// =============================================================

private static double driveCompensation(double drive) {
    return 1.0 / (1.0 + DRIVE_COMPENSATION_FACTOR * (drive - 1.0));
}

// =============================================================
// BLUE WAVESHAPER
// =============================================================

private static double blueShape(double x, double positiveRate, double negativeRate) {
    if (x >= 0.0) {
        return 1.0 - Math.exp(-positiveRate * x);
    }

    return -(1.0 - Math.exp(negativeRate * x));
}

// =============================================================
// 96 KHZ DC BLOCKERS
// =============================================================

private double dcBlockRed(double input) {
    double output = input - redDcPrevInput + (DC_POLE_2X * redDcPrevOutput);

    redDcPrevInput = input;
    redDcPrevOutput = output;

    return output;
}

private double dcBlockGreen(double input) {
    double output = input - greenDcPrevInput + (DC_POLE_2X * greenDcPrevOutput);

    greenDcPrevInput = input;
    greenDcPrevOutput = output;

    return output;
}

private double dcBlockBlue(double input) {
    double output = input - blueDcPrevInput + (DC_POLE_2X * blueDcPrevOutput);

    blueDcPrevInput = input;
    blueDcPrevOutput = output;

    return output;
}

private void resetRedDc() {
    redDcPrevInput = 0.0;
    redDcPrevOutput = 0.0;
}

private void resetGreenDc() {
    greenDcPrevInput = 0.0;
    greenDcPrevOutput = 0.0;
}

private void resetBlueDc() {
    blueDcPrevInput = 0.0;
    blueDcPrevOutput = 0.0;
}

// =============================================================
// 2X HALF-BAND OVERSAMPLING FILTER
// =============================================================
//
// 19-tap linear-phase half-band FIR.
//
// Symmetry and zero-valued alternating coefficients reduce
// each filter tick to six multiplications.
//
// Filter state is preallocated. Nothing is allocated inside
// ProcessSample.
// =============================================================

private static final class HalfBand19 {
    private static final double C0 = 0.021277660466073;

    private static final double C2 = -0.027992303277934;

    private static final double C4 = 0.049537687283950;

    private static final double C6 = -0.095789590456413;

    private static final double C8 = 0.308510413777763;

    private static final double C9 = 0.488912264413122;

    // A doubled ring buffer avoids array shifting and modulo.

    private final double[] buffer = new double[38];

    private int position = 0;
    private boolean dirty = false;

    public double process(double input) {
        position--;

        if (position < 0) {
            position = 18;
        }

        buffer[position] = input;
        buffer[position + 19] = input;

        dirty = true;

        return C0 * (buffer[position] + buffer[position + 18])
                + C2 * (buffer[position + 2] + buffer[position + 16])
                + C4 * (buffer[position + 4] + buffer[position + 14])
                + C6 * (buffer[position + 6] + buffer[position + 12])
                + C8 * (buffer[position + 8] + buffer[position + 10])
                + C9 * buffer[position + 9];
    }

    public void reset() {
        // Avoid repeatedly clearing an already-empty filter.

        if (!dirty) {
            return;
        }

        for (int i = 0; i < 38; i++) {
            buffer[i] = 0.0;
        }

        position = 0;
        dirty = false;
    }
}

// =============================================================
// OVERSAMPLING FILTER INSTANCES
// =============================================================

// Input interpolators: 48 -> 96 kHz.

private final HalfBand19 redInputUpsampler = new HalfBand19();

private final HalfBand19 greenInputUpsampler = new HalfBand19();

private final HalfBand19 blueInputUpsampler = new HalfBand19();

// Output decimators: 96 -> 48 kHz.

private final HalfBand19 redOutputDownsampler = new HalfBand19();

private final HalfBand19 greenOutputDownsampler = new HalfBand19();

private final HalfBand19 blueOutputDownsampler = new HalfBand19();

// =============================================================
// DSP STATE RESET
// =============================================================

private void resetDsp() {
    redInputUpsampler.reset();
    greenInputUpsampler.reset();
    blueInputUpsampler.reset();

    redOutputDownsampler.reset();
    greenOutputDownsampler.reset();
    blueOutputDownsampler.reset();

    resetRedDc();
    resetGreenDc();
    resetBlueDc();
}

// True while Voltage Modular is calling ProcessBypassedSample.
// Active processing clears filter history once upon return.

private boolean wasBypassed = false;
//[/user-code-and-variables]
}

 