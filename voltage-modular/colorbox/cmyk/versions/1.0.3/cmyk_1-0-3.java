package com.insectlabs.cmyk;


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


public class CMYK extends VoltageModule
//[user-inheritance]

//[/user-inheritance]
{

@SuppressWarnings("this-escape") 
public CMYK( long moduleID, VoltageObjects voltageObjects )
{
    super( moduleID, voltageObjects, "cmyk", ModuleType.ModuleType_Processor, 1.6 );

    InitializeControls();


    canBeBypassed = true;
    SetSkin( "1b778002bb2842d9983a917e41534eff" );
}

void InitializeControls()
{

    textInsect = new VoltageLabel( "textInsect", "insect labratories", this, "insect" );
    AddComponent( textInsect );
    textInsect.SetWantsMouseNotifications( false );
    textInsect.SetPosition( 0, 335 );
    textInsect.SetSize( 115, 23 );
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

    textCatalog = new VoltageLabel( "textCatalog", "catalog number", this, "cmyk" );
    AddComponent( textCatalog );
    textCatalog.SetWantsMouseNotifications( false );
    textCatalog.SetPosition( 3, 1 );
    textCatalog.SetSize( 57, 13 );
    textCatalog.SetEditable( false, false );
    textCatalog.SetJustificationFlags( VoltageLabel.Justification.Left );
    textCatalog.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
    textCatalog.SetColor( new Color( 147, 0, 0, 255 ) );
    textCatalog.SetBkColor( new Color( 65, 65, 65, 0 ) );
    textCatalog.SetBorderColor( new Color( 85, 0, 0, 0 ) );
    textCatalog.SetBorderSize( 1 );
    textCatalog.SetMultiLineEdit( false );
    textCatalog.SetIsNumberEditor( false );
    textCatalog.SetNumberEditorRange( 0, 100 );
    textCatalog.SetNumberEditorInterval( 1 );
    textCatalog.SetNumberEditorUsesMouseWheel( false );
    textCatalog.SetHasCustomTextHoverColor( false );
    textCatalog.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
    textCatalog.SetFont( "Courier New", 13, true, false );

    cyanIn = new VoltageAudioJack( "cyanIn", "cyan in", this, JackType.JackType_AudioInput );
    AddComponent( cyanIn );
    cyanIn.SetWantsMouseNotifications( false );
    cyanIn.SetPosition( 10, 20 );
    cyanIn.SetSize( 25, 25 );
    cyanIn.SetSkin( "Jack Round 25px" );

    magentaIn = new VoltageAudioJack( "magentaIn", "magenta in", this, JackType.JackType_AudioInput );
    AddComponent( magentaIn );
    magentaIn.SetWantsMouseNotifications( false );
    magentaIn.SetPosition( 10, 95 );
    magentaIn.SetSize( 25, 25 );
    magentaIn.SetSkin( "Jack Round 25px" );

    yellowIn = new VoltageAudioJack( "yellowIn", "yellow in", this, JackType.JackType_AudioInput );
    AddComponent( yellowIn );
    yellowIn.SetWantsMouseNotifications( false );
    yellowIn.SetPosition( 10, 170 );
    yellowIn.SetSize( 25, 25 );
    yellowIn.SetSkin( "Jack Round 25px" );

    blackIn = new VoltageAudioJack( "blackIn", "black in", this, JackType.JackType_AudioInput );
    AddComponent( blackIn );
    blackIn.SetWantsMouseNotifications( false );
    blackIn.SetPosition( 10, 245 );
    blackIn.SetSize( 25, 25 );
    blackIn.SetSkin( "Jack Round 25px" );

    cyanCvIn = new VoltageAudioJack( "cyanCvIn", "cyan modulation", this, JackType.JackType_AudioInput );
    AddComponent( cyanCvIn );
    cyanCvIn.SetWantsMouseNotifications( false );
    cyanCvIn.SetPosition( 45, 20 );
    cyanCvIn.SetSize( 25, 25 );
    cyanCvIn.SetSkin( "Jack Round White Ring" );

    magentaCvIn = new VoltageAudioJack( "magentaCvIn", "magenta modulation", this, JackType.JackType_AudioInput );
    AddComponent( magentaCvIn );
    magentaCvIn.SetWantsMouseNotifications( false );
    magentaCvIn.SetPosition( 45, 95 );
    magentaCvIn.SetSize( 25, 25 );
    magentaCvIn.SetSkin( "Jack Round White Ring" );

    yellowCvIn = new VoltageAudioJack( "yellowCvIn", "yellow modulation", this, JackType.JackType_AudioInput );
    AddComponent( yellowCvIn );
    yellowCvIn.SetWantsMouseNotifications( false );
    yellowCvIn.SetPosition( 45, 170 );
    yellowCvIn.SetSize( 25, 25 );
    yellowCvIn.SetSkin( "Jack Round White Ring" );

    blackCvIn = new VoltageAudioJack( "blackCvIn", "black modulation", this, JackType.JackType_AudioInput );
    AddComponent( blackCvIn );
    blackCvIn.SetWantsMouseNotifications( false );
    blackCvIn.SetPosition( 45, 245 );
    blackCvIn.SetSize( 25, 25 );
    blackCvIn.SetSkin( "Jack Round White Ring" );

    cyanOut = new VoltageAudioJack( "cyanOut", "cyan out", this, JackType.JackType_AudioOutput );
    AddComponent( cyanOut );
    cyanOut.SetWantsMouseNotifications( false );
    cyanOut.SetPosition( 80, 20 );
    cyanOut.SetSize( 25, 25 );
    cyanOut.SetSkin( "Mini Jack 25px" );

    magentaOut = new VoltageAudioJack( "magentaOut", "magenta out", this, JackType.JackType_AudioOutput );
    AddComponent( magentaOut );
    magentaOut.SetWantsMouseNotifications( false );
    magentaOut.SetPosition( 80, 95 );
    magentaOut.SetSize( 25, 25 );
    magentaOut.SetSkin( "Mini Jack 25px" );

    yellowOut = new VoltageAudioJack( "yellowOut", "yellow out", this, JackType.JackType_AudioOutput );
    AddComponent( yellowOut );
    yellowOut.SetWantsMouseNotifications( false );
    yellowOut.SetPosition( 80, 170 );
    yellowOut.SetSize( 25, 25 );
    yellowOut.SetSkin( "Mini Jack 25px" );

    blackOut = new VoltageAudioJack( "blackOut", "black out", this, JackType.JackType_AudioOutput );
    AddComponent( blackOut );
    blackOut.SetWantsMouseNotifications( false );
    blackOut.SetPosition( 80, 245 );
    blackOut.SetSize( 25, 25 );
    blackOut.SetSkin( "Mini Jack 25px" );

    dcBlockSwitch = new VoltageSwitch( "dcBlockSwitch", "DC block", this, 1 );
    AddComponent( dcBlockSwitch );
    dcBlockSwitch.SetWantsMouseNotifications( false );
    dcBlockSwitch.SetPosition( 35, 316 );
    dcBlockSwitch.SetSize( 45, 15 );
    dcBlockSwitch.SetSkin( "Rocker Switch Plastic Black Hor" );

    magentaAmount = new VoltageKnob( "magentaAmount", "magenta drive", this, -1.0, 1.0, 1.0 );
    AddComponent( magentaAmount );
    magentaAmount.SetWantsMouseNotifications( false );
    magentaAmount.SetPosition( 10, 125 );
    magentaAmount.SetSize( 25, 25 );
    magentaAmount.SetSkin( "Plastic Plum" );
    magentaAmount.SetRange( -1.0, 1.0, 1.0, false, 0 );
    magentaAmount.SetKnobParams( 215, 145 );
    magentaAmount.DisplayValueInPercent( true );
    magentaAmount.SetKnobAdjustsRing( true );

    cyanAmount = new VoltageKnob( "cyanAmount", "cyan drive", this, -1.0, 1.0, 1.0 );
    AddComponent( cyanAmount );
    cyanAmount.SetWantsMouseNotifications( false );
    cyanAmount.SetPosition( 10, 50 );
    cyanAmount.SetSize( 25, 25 );
    cyanAmount.SetSkin( "Plastic Blue" );
    cyanAmount.SetRange( -1.0, 1.0, 1.0, false, 0 );
    cyanAmount.SetKnobParams( 215, 145 );
    cyanAmount.DisplayValueInPercent( true );
    cyanAmount.SetKnobAdjustsRing( true );

    yellowAmount = new VoltageKnob( "yellowAmount", "yellow drive", this, -1.0, 1.0, 1.0 );
    AddComponent( yellowAmount );
    yellowAmount.SetWantsMouseNotifications( false );
    yellowAmount.SetPosition( 10, 200 );
    yellowAmount.SetSize( 25, 25 );
    yellowAmount.SetSkin( "Plastic Yellow" );
    yellowAmount.SetRange( -1.0, 1.0, 1.0, false, 0 );
    yellowAmount.SetKnobParams( 215, 145 );
    yellowAmount.DisplayValueInPercent( true );
    yellowAmount.SetKnobAdjustsRing( true );

    blackAmount = new VoltageKnob( "blackAmount", "black drive", this, -1.0, 1.0, 1.0 );
    AddComponent( blackAmount );
    blackAmount.SetWantsMouseNotifications( false );
    blackAmount.SetPosition( 10, 275 );
    blackAmount.SetSize( 25, 25 );
    blackAmount.SetSkin( "Plastic Black" );
    blackAmount.SetRange( -1.0, 1.0, 1.0, false, 0 );
    blackAmount.SetKnobParams( 215, 145 );
    blackAmount.DisplayValueInPercent( true );
    blackAmount.SetKnobAdjustsRing( true );

    cyanCvAmount = new VoltageKnob( "cyanCvAmount", "cyan index", this, -1.0, 1.0, 0.0 );
    AddComponent( cyanCvAmount );
    cyanCvAmount.SetWantsMouseNotifications( false );
    cyanCvAmount.SetPosition( 45, 50 );
    cyanCvAmount.SetSize( 25, 25 );
    cyanCvAmount.SetSkin( "Plastic Blue" );
    cyanCvAmount.SetRange( -1.0, 1.0, 0.0, false, 0 );
    cyanCvAmount.SetKnobParams( 215, 145 );
    cyanCvAmount.DisplayValueInPercent( true );
    cyanCvAmount.SetKnobAdjustsRing( true );

    magentaCvAmount = new VoltageKnob( "magentaCvAmount", "magenta index", this, -1.0, 1.0, 0.0 );
    AddComponent( magentaCvAmount );
    magentaCvAmount.SetWantsMouseNotifications( false );
    magentaCvAmount.SetPosition( 45, 125 );
    magentaCvAmount.SetSize( 25, 25 );
    magentaCvAmount.SetSkin( "Plastic Plum" );
    magentaCvAmount.SetRange( -1.0, 1.0, 0.0, false, 0 );
    magentaCvAmount.SetKnobParams( 215, 145 );
    magentaCvAmount.DisplayValueInPercent( true );
    magentaCvAmount.SetKnobAdjustsRing( true );

    yellowCvAmount = new VoltageKnob( "yellowCvAmount", "yellow index", this, -1.0, 1.0, 0.0 );
    AddComponent( yellowCvAmount );
    yellowCvAmount.SetWantsMouseNotifications( false );
    yellowCvAmount.SetPosition( 45, 200 );
    yellowCvAmount.SetSize( 25, 25 );
    yellowCvAmount.SetSkin( "Plastic Yellow" );
    yellowCvAmount.SetRange( -1.0, 1.0, 0.0, false, 0 );
    yellowCvAmount.SetKnobParams( 215, 145 );
    yellowCvAmount.DisplayValueInPercent( true );
    yellowCvAmount.SetKnobAdjustsRing( true );

    blackCvAmount = new VoltageKnob( "blackCvAmount", "black index", this, -1.0, 1.0, 0.0 );
    AddComponent( blackCvAmount );
    blackCvAmount.SetWantsMouseNotifications( false );
    blackCvAmount.SetPosition( 45, 275 );
    blackCvAmount.SetSize( 25, 25 );
    blackCvAmount.SetSkin( "Plastic Black" );
    blackCvAmount.SetRange( -1.0, 1.0, 0.0, false, 0 );
    blackCvAmount.SetKnobParams( 215, 145 );
    blackCvAmount.DisplayValueInPercent( true );
    blackCvAmount.SetKnobAdjustsRing( true );
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
    // Reset DSP and initialize smoothing from the actual panel values.

    resetDsp();

    wasBypassed = false;

    smoothedCyanAmount = cyanAmount.GetValue();

    smoothedMagentaAmount = magentaAmount.GetValue();

    smoothedYellowAmount = yellowAmount.GetValue();

    smoothedBlackAmount = blackAmount.GetValue();

    smoothedCyanCvAmount = cyanCvAmount.GetValue();

    smoothedMagentaCvAmount = magentaCvAmount.GetValue();

    smoothedYellowCvAmount = yellowCvAmount.GetValue();

    smoothedBlackCvAmount = blackCvAmount.GetValue();
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
    // CMYK 1.0.3
    //
    // Four-stage oversampled wavefolder:
    //
    // CYAN     = Folder B
    // MAGENTA  = Folder A
    // YELLOW   = Folder B
    // BLACK    = Folder A
    //
    // Normalled cascade:
    //
    // CYAN -> MAGENTA -> YELLOW -> BLACK
    //   B        A          B         A
    //
    // Voltage Modular I/O: 48 kHz
    // Internal processing: 96 kHz
    // =============================================================

    if (wasBypassed) {
        resetDsp();
        wasBypassed = false;
    }

    // -------------------------------------------------------------
    // SMOOTH MANUAL CONTROLS
    // -------------------------------------------------------------

    smoothedCyanAmount = smoothControl(smoothedCyanAmount, cyanAmount.GetValue());

    smoothedMagentaAmount = smoothControl(smoothedMagentaAmount, magentaAmount.GetValue());

    smoothedYellowAmount = smoothControl(smoothedYellowAmount, yellowAmount.GetValue());

    smoothedBlackAmount = smoothControl(smoothedBlackAmount, blackAmount.GetValue());

    smoothedCyanCvAmount = smoothControl(smoothedCyanCvAmount, cyanCvAmount.GetValue());

    smoothedMagentaCvAmount =
            smoothControl(smoothedMagentaCvAmount, magentaCvAmount.GetValue());

    smoothedYellowCvAmount = smoothControl(smoothedYellowCvAmount, yellowCvAmount.GetValue());

    smoothedBlackCvAmount = smoothControl(smoothedBlackCvAmount, blackCvAmount.GetValue());

    // Cache switch and jack connection states once per host sample.

    boolean dcEnabled = dcBlockSwitch.GetValue() > 0.5;

    boolean magentaInputConnected = magentaIn.IsConnected();

    boolean yellowInputConnected = yellowIn.IsConnected();

    boolean blackInputConnected = blackIn.IsConnected();

    boolean cyanCvConnected = cyanCvIn.IsConnected();

    boolean magentaCvConnected = magentaCvIn.IsConnected();

    boolean yellowCvConnected = yellowCvIn.IsConnected();

    boolean blackCvConnected = blackCvIn.IsConnected();

    // =============================================================
    // UPSAMPLE EXTERNAL AUDIO INPUTS
    // =============================================================

    double cyanExternalA = 2.0 * cyanInputUpsampler.process(cyanIn.GetValue());

    double cyanExternalB = 2.0 * cyanInputUpsampler.process(0.0);

    double magentaExternalA = 2.0 * magentaInputUpsampler.process(magentaIn.GetValue());

    double magentaExternalB = 2.0 * magentaInputUpsampler.process(0.0);

    double yellowExternalA = 2.0 * yellowInputUpsampler.process(yellowIn.GetValue());

    double yellowExternalB = 2.0 * yellowInputUpsampler.process(0.0);

    double blackExternalA = 2.0 * blackInputUpsampler.process(blackIn.GetValue());

    double blackExternalB = 2.0 * blackInputUpsampler.process(0.0);

    // =============================================================
    // UPSAMPLE EXTERNAL CV
    //
    // Unpatched CV uses the locked internal +5 V normal.
    // We still advance the interpolation filter with zeros so
    // stale CV history drains while the jack is disconnected.
    // =============================================================

    double cyanCvA;
    double cyanCvB;

    if (cyanCvConnected) {
        cyanCvA = 2.0 * cyanCvUpsampler.process(cyanCvIn.GetValue());

        cyanCvB = 2.0 * cyanCvUpsampler.process(0.0);
    } else {
        cyanCvUpsampler.process(0.0);
        cyanCvUpsampler.process(0.0);

        cyanCvA = NOMINAL_VOLTAGE;
        cyanCvB = NOMINAL_VOLTAGE;
    }

    double magentaCvA;
    double magentaCvB;

    if (magentaCvConnected) {
        magentaCvA = 2.0 * magentaCvUpsampler.process(magentaCvIn.GetValue());

        magentaCvB = 2.0 * magentaCvUpsampler.process(0.0);
    } else {
        magentaCvUpsampler.process(0.0);
        magentaCvUpsampler.process(0.0);

        magentaCvA = NOMINAL_VOLTAGE;
        magentaCvB = NOMINAL_VOLTAGE;
    }

    double yellowCvA;
    double yellowCvB;

    if (yellowCvConnected) {
        yellowCvA = 2.0 * yellowCvUpsampler.process(yellowCvIn.GetValue());

        yellowCvB = 2.0 * yellowCvUpsampler.process(0.0);
    } else {
        yellowCvUpsampler.process(0.0);
        yellowCvUpsampler.process(0.0);

        yellowCvA = NOMINAL_VOLTAGE;
        yellowCvB = NOMINAL_VOLTAGE;
    }

    double blackCvA;
    double blackCvB;

    if (blackCvConnected) {
        blackCvA = 2.0 * blackCvUpsampler.process(blackCvIn.GetValue());

        blackCvB = 2.0 * blackCvUpsampler.process(0.0);
    } else {
        blackCvUpsampler.process(0.0);
        blackCvUpsampler.process(0.0);

        blackCvA = NOMINAL_VOLTAGE;
        blackCvB = NOMINAL_VOLTAGE;
    }

    // =============================================================
    // PHASE A
    // =============================================================

    // -------------------------------------------------------------
    // CYAN - FOLDER B
    // -------------------------------------------------------------

    double cyanInputA = cyanExternalA * smoothedCyanAmount;

    double cyanDepthA = getDepth(cyanCvA, smoothedCyanCvAmount);

    double cyanRawA = folderB(cyanInputA, cyanDepthA);

    // DC history intentionally advances even while DC BLOCK is OFF.

    double cyanBlockedA = dcBlockCyan(cyanRawA);

    double cyanProcessedA = dcEnabled ? cyanBlockedA : cyanRawA;

    // -------------------------------------------------------------
    // MAGENTA - FOLDER A
    // -------------------------------------------------------------

    double magentaSourceA = magentaInputConnected ? magentaExternalA : cyanProcessedA;

    double magentaInputA = magentaSourceA * smoothedMagentaAmount;

    double magentaDepthA = getDepth(magentaCvA, smoothedMagentaCvAmount);

    double magentaRawA = folderA(magentaInputA, magentaDepthA);

    double magentaBlockedA = dcBlockMagenta(magentaRawA);

    double magentaProcessedA = dcEnabled ? magentaBlockedA : magentaRawA;

    // -------------------------------------------------------------
    // YELLOW - FOLDER B
    // -------------------------------------------------------------

    double yellowSourceA = yellowInputConnected ? yellowExternalA : magentaProcessedA;

    double yellowInputA = yellowSourceA * smoothedYellowAmount;

    double yellowDepthA = getDepth(yellowCvA, smoothedYellowCvAmount);

    double yellowRawA = folderB(yellowInputA, yellowDepthA);

    double yellowBlockedA = dcBlockYellow(yellowRawA);

    double yellowProcessedA = dcEnabled ? yellowBlockedA : yellowRawA;

    // -------------------------------------------------------------
    // BLACK - FOLDER A
    // -------------------------------------------------------------

    double blackSourceA = blackInputConnected ? blackExternalA : yellowProcessedA;

    double blackInputA = blackSourceA * smoothedBlackAmount;

    double blackDepthA = getDepth(blackCvA, smoothedBlackCvAmount);

    double blackRawA = folderA(blackInputA, blackDepthA);

    double blackBlockedA = dcBlockBlack(blackRawA);

    double blackProcessedA = dcEnabled ? blackBlockedA : blackRawA;

    // =============================================================
    // PHASE B
    // =============================================================

    // -------------------------------------------------------------
    // CYAN - FOLDER B
    // -------------------------------------------------------------

    double cyanInputB = cyanExternalB * smoothedCyanAmount;

    double cyanDepthB = getDepth(cyanCvB, smoothedCyanCvAmount);

    double cyanRawB = folderB(cyanInputB, cyanDepthB);

    double cyanBlockedB = dcBlockCyan(cyanRawB);

    double cyanProcessedB = dcEnabled ? cyanBlockedB : cyanRawB;

    // -------------------------------------------------------------
    // MAGENTA - FOLDER A
    // -------------------------------------------------------------

    double magentaSourceB = magentaInputConnected ? magentaExternalB : cyanProcessedB;

    double magentaInputB = magentaSourceB * smoothedMagentaAmount;

    double magentaDepthB = getDepth(magentaCvB, smoothedMagentaCvAmount);

    double magentaRawB = folderA(magentaInputB, magentaDepthB);

    double magentaBlockedB = dcBlockMagenta(magentaRawB);

    double magentaProcessedB = dcEnabled ? magentaBlockedB : magentaRawB;

    // -------------------------------------------------------------
    // YELLOW - FOLDER B
    // -------------------------------------------------------------

    double yellowSourceB = yellowInputConnected ? yellowExternalB : magentaProcessedB;

    double yellowInputB = yellowSourceB * smoothedYellowAmount;

    double yellowDepthB = getDepth(yellowCvB, smoothedYellowCvAmount);

    double yellowRawB = folderB(yellowInputB, yellowDepthB);

    double yellowBlockedB = dcBlockYellow(yellowRawB);

    double yellowProcessedB = dcEnabled ? yellowBlockedB : yellowRawB;

    // -------------------------------------------------------------
    // BLACK - FOLDER A
    // -------------------------------------------------------------

    double blackSourceB = blackInputConnected ? blackExternalB : yellowProcessedB;

    double blackInputB = blackSourceB * smoothedBlackAmount;

    double blackDepthB = getDepth(blackCvB, smoothedBlackCvAmount);

    double blackRawB = folderA(blackInputB, blackDepthB);

    double blackBlockedB = dcBlockBlack(blackRawB);

    double blackProcessedB = dcEnabled ? blackBlockedB : blackRawB;

    // =============================================================
    // DECIMATE 96 KHZ -> 48 KHZ
    //
    // Keep each phase-A result.
    // Phase B advances the corresponding filter history.
    // =============================================================

    double cyanOutput = cyanOutputDownsampler.process(cyanProcessedA);

    cyanOutputDownsampler.process(cyanProcessedB);

    double magentaOutput = magentaOutputDownsampler.process(magentaProcessedA);

    magentaOutputDownsampler.process(magentaProcessedB);

    double yellowOutput = yellowOutputDownsampler.process(yellowProcessedA);

    yellowOutputDownsampler.process(yellowProcessedB);

    double blackOutput = blackOutputDownsampler.process(blackProcessedA);

    blackOutputDownsampler.process(blackProcessedB);

    // =============================================================
    // PHYSICAL OUTPUTS
    // =============================================================

    cyanOut.SetValue(cyanOutput);

    magentaOut.SetValue(magentaOutput);

    yellowOut.SetValue(yellowOutput);

    blackOut.SetValue(blackOutput);
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
    // Preserve:
    //
    // CYAN -> MAGENTA -> YELLOW -> BLACK
    //
    // normals, but perform no folding, CV processing,
    // oversampling, smoothing, DC blocking, or latency
    // compensation.
    // =============================================================

    double cyanBypass = cyanIn.GetValue();

    cyanOut.SetValue(cyanBypass);

    double magentaBypass;

    if (magentaIn.IsConnected()) {
        magentaBypass = magentaIn.GetValue();
    } else {
        magentaBypass = cyanBypass;
    }

    magentaOut.SetValue(magentaBypass);

    double yellowBypass;

    if (yellowIn.IsConnected()) {
        yellowBypass = yellowIn.GetValue();
    } else {
        yellowBypass = magentaBypass;
    }

    yellowOut.SetValue(yellowBypass);

    double blackBypass;

    if (blackIn.IsConnected()) {
        blackBypass = blackIn.GetValue();
    } else {
        blackBypass = yellowBypass;
    }

    blackOut.SetValue(blackBypass);

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
    if (component == cyanAmount)
        return "CYAN input level: "
                + Math.round(cyanAmount.GetValue() * 100.0)
                + "% (center: mute; left: inverted)";
    if (component == cyanCvAmount)
        return "CYAN fold CV depth: "
                + Math.round(cyanCvAmount.GetValue() * 100.0)
                + "% (center: no CV; left: inverted)";
    if (component == cyanCvIn) return "CYAN fold CV input (unpatched: +5 V)";
    if (component == cyanIn) return "CYAN audio input (unpatched: silence)";
    if (component == cyanOut)
        return "CYAN audio output; also feeds MAGENTA when its input is unpatched";
    if (component == magentaAmount)
        return "MAGENTA input level: "
                + Math.round(magentaAmount.GetValue() * 100.0)
                + "% (center: mute; left: inverted)";
    if (component == magentaCvAmount)
        return "MAGENTA fold CV depth: "
                + Math.round(magentaCvAmount.GetValue() * 100.0)
                + "% (center: no CV; left: inverted)";
    if (component == magentaCvIn) return "MAGENTA fold CV input (unpatched: +5 V)";
    if (component == magentaIn) return "MAGENTA audio input (unpatched: CYAN stage output)";
    if (component == magentaOut)
        return "MAGENTA audio output; also feeds YELLOW when its input is unpatched";
    if (component == yellowAmount)
        return "YELLOW input level: "
                + Math.round(yellowAmount.GetValue() * 100.0)
                + "% (center: mute; left: inverted)";
    if (component == yellowCvAmount)
        return "YELLOW fold CV depth: "
                + Math.round(yellowCvAmount.GetValue() * 100.0)
                + "% (center: no CV; left: inverted)";
    if (component == yellowCvIn) return "YELLOW fold CV input (unpatched: +5 V)";
    if (component == yellowIn) return "YELLOW audio input (unpatched: MAGENTA stage output)";
    if (component == yellowOut)
        return "YELLOW audio output; also feeds BLACK when its input is unpatched";
    if (component == blackAmount)
        return "BLACK input level: "
                + Math.round(blackAmount.GetValue() * 100.0)
                + "% (center: mute; left: inverted)";
    if (component == blackCvAmount)
        return "BLACK fold CV depth: "
                + Math.round(blackCvAmount.GetValue() * 100.0)
                + "% (center: no CV; left: inverted)";
    if (component == blackCvIn) return "BLACK fold CV input (unpatched: +5 V)";
    if (component == blackIn) return "BLACK audio input (unpatched: YELLOW stage output)";
    if (component == blackOut) return "BLACK audio output";
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
private VoltageKnob blackCvAmount;
private VoltageKnob yellowCvAmount;
private VoltageKnob magentaCvAmount;
private VoltageKnob cyanCvAmount;
private VoltageKnob blackAmount;
private VoltageKnob yellowAmount;
private VoltageKnob cyanAmount;
private VoltageKnob magentaAmount;
private VoltageSwitch dcBlockSwitch;
private VoltageAudioJack blackOut;
private VoltageAudioJack yellowOut;
private VoltageAudioJack magentaOut;
private VoltageAudioJack cyanOut;
private VoltageAudioJack blackCvIn;
private VoltageAudioJack yellowCvIn;
private VoltageAudioJack magentaCvIn;
private VoltageAudioJack cyanCvIn;
private VoltageAudioJack blackIn;
private VoltageAudioJack yellowIn;
private VoltageAudioJack magentaIn;
private VoltageAudioJack cyanIn;
private VoltageLabel textCatalog;
private VoltageLabel textInsect;


//[user-code-and-variables]    Add your own variables and functions here
// CMYK 1.0.3 - insect laboratories.
// Colorbox: 2x processing; direct host bypass preserves routing and skips DSP.
// =============================================================
// CMYK 1.0.3 - CONSTANTS
// =============================================================

private boolean wasBypassed = false;

// Voltage Modular nominal signal reference.

private static final double NOMINAL_VOLTAGE = 5.0;

// Manual-control smoothing.
// Approximately 8 ms at Voltage Modular's 48 kHz rate.

private static final double CONTROL_SMOOTH = 0.0026;

// 96 kHz DC-blocking pole.
// Approximately the same ~3.8 Hz corner used by rgb.

private static final double DC_POLE_2X = 0.9997499687421851;

// =============================================================
// 19-TAP HALF-BAND FIR COEFFICIENTS
// =============================================================

private static final double HB_C0 = 0.021277660466073;

private static final double HB_C2 = -0.027992303277934;

private static final double HB_C4 = 0.049537687283950;

private static final double HB_C6 = -0.095789590456413;

private static final double HB_C8 = 0.308510413777763;

private static final double HB_C9 = 0.488912264413122;

// =============================================================
// 2X HALF-BAND FILTER
// =============================================================
//
// Used for audio/CV interpolation and physical-output
// decimation.
//
// The filter implementation is intentionally unchanged from
// CMYK 1.0.1; only naming and encapsulation have been cleaned.
// =============================================================

private static final class HalfBand19 {
    private final double[] buffer = new double[19];

    private int position = 0;

    // True once the filter has processed any non-reset state.
    // Allows reset() to skip unnecessary buffer clearing.
    private boolean dirty = false;

    public double process(double input) {
        buffer[position] = input;

        dirty = true;

        double output =
                HB_C0 * (get(0) + get(18))
                        + HB_C2 * (get(2) + get(16))
                        + HB_C4 * (get(4) + get(14))
                        + HB_C6 * (get(6) + get(12))
                        + HB_C8 * (get(8) + get(10))
                        + HB_C9 * get(9);

        position++;

        if (position >= 19) {
            position = 0;
        }

        return output;
    }

    private double get(int delay) {
        int index = position - delay;

        if (index < 0) {
            index += 19;
        }

        return buffer[index];
    }

    public void reset() {
        // If the filter is already empty, there is nothing to do.

        if (!dirty) {
            return;
        }

        for (int i = 0; i < 19; i++) {
            buffer[i] = 0.0;
        }

        position = 0;
        dirty = false;
    }
}

// =============================================================
// AUDIO INPUT INTERPOLATORS
// =============================================================

private final HalfBand19 cyanInputUpsampler = new HalfBand19();

private final HalfBand19 magentaInputUpsampler = new HalfBand19();

private final HalfBand19 yellowInputUpsampler = new HalfBand19();

private final HalfBand19 blackInputUpsampler = new HalfBand19();

// =============================================================
// CV INPUT INTERPOLATORS
//
// These permit audio-rate folding-depth modulation.
// =============================================================

private final HalfBand19 cyanCvUpsampler = new HalfBand19();

private final HalfBand19 magentaCvUpsampler = new HalfBand19();

private final HalfBand19 yellowCvUpsampler = new HalfBand19();

private final HalfBand19 blackCvUpsampler = new HalfBand19();

// =============================================================
// PHYSICAL OUTPUT DECIMATORS
//
// Internal normalled signals remain at 96 kHz between stages.
// =============================================================

private final HalfBand19 cyanOutputDownsampler = new HalfBand19();

private final HalfBand19 magentaOutputDownsampler = new HalfBand19();

private final HalfBand19 yellowOutputDownsampler = new HalfBand19();

private final HalfBand19 blackOutputDownsampler = new HalfBand19();

// =============================================================
// SMOOTHED MANUAL CONTROL STATE
// =============================================================

private double smoothedCyanAmount = 0.0;

private double smoothedMagentaAmount = 0.0;

private double smoothedYellowAmount = 0.0;

private double smoothedBlackAmount = 0.0;

private double smoothedCyanCvAmount = 0.0;

private double smoothedMagentaCvAmount = 0.0;

private double smoothedYellowCvAmount = 0.0;

private double smoothedBlackCvAmount = 0.0;

// =============================================================
// CONTROL HELPERS
// =============================================================

private static double smoothControl(double current, double target) {
    return current + CONTROL_SMOOTH * (target - current);
}

private static double clamp01(double value) {
    if (value < 0.0) {
        return 0.0;
    }

    if (value > 1.0) {
        return 1.0;
    }

    return value;
}

// Unpatched CV is internally +5 V.
//
// Index:
//   -1 -> depth 0.0
//    0 -> depth 0.5
//   +1 -> depth 1.0
//
// Patched CV modulates around depth 0.5.

private static double getDepth(double cv, double cvAmount) {
    double depth = 0.5 + 0.5 * cvAmount * (cv / NOMINAL_VOLTAGE);

    return clamp01(depth);
}

// =============================================================
// FOLDER A
//
// Smooth/warm symmetric sine-derived folder.
//
// Used by:
// MAGENTA
// BLACK
// =============================================================

private static double folderA(double inputVolts, double depth) {
    double u = inputVolts / NOMINAL_VOLTAGE;

    double d2 = depth * depth;

    double foldScale = 1.0 + (3.75 * d2);

    double foldMix = 0.18 + (0.82 * depth);

    double phase = 0.5 * Math.PI * foldScale * u;

    double folded = Math.sin(phase);

    double result = u + foldMix * (folded - u);

    return result * NOMINAL_VOLTAGE;
}

// =============================================================
// FOLDER B
//
// More brittle/asymmetric sine + cosine-bias folder.
//
// Used by:
// CYAN
// YELLOW
// =============================================================

private static double folderB(double inputVolts, double depth) {
    double u = inputVolts / NOMINAL_VOLTAGE;

    double d2 = depth * depth;

    double foldScale = 1.0 + (4.50 * d2);

    double foldMix = 0.20 + (0.80 * depth);

    double phase = 0.5 * Math.PI * foldScale * u;

    double primary = Math.sin(phase);

    double evenBias = 0.10 + (0.70 * d2);

    double cosineFold = 1.0 - Math.cos(phase);

    double folded = primary + evenBias * cosineFold;

    // Preserve Folder B's built-in level containment.

    folded /= 1.0 + (0.70 * evenBias);

    double result = u + foldMix * (folded - u);

    return result * NOMINAL_VOLTAGE;
}

// =============================================================
// COLOR-SPECIFIC DC BLOCKER STATE
//
// Histories deliberately continue advancing while the
// DC BLOCK switch is OFF.
//
// Host bypass freezes these histories, matching 1.0.1.
// =============================================================

private double cyanDCPrevInput = 0.0;

private double cyanDCPrevOutput = 0.0;

private double magentaDCPrevInput = 0.0;

private double magentaDCPrevOutput = 0.0;

private double yellowDCPrevInput = 0.0;

private double yellowDCPrevOutput = 0.0;

private double blackDCPrevInput = 0.0;

private double blackDCPrevOutput = 0.0;

// -------------------------------------------------------------
// CYAN DC BLOCK
// -------------------------------------------------------------

private double dcBlockCyan(double input) {
    double output = input - cyanDCPrevInput + DC_POLE_2X * cyanDCPrevOutput;

    cyanDCPrevInput = input;

    cyanDCPrevOutput = output;

    return output;
}

// -------------------------------------------------------------
// MAGENTA DC BLOCK
// -------------------------------------------------------------

private double dcBlockMagenta(double input) {
    double output = input - magentaDCPrevInput + DC_POLE_2X * magentaDCPrevOutput;

    magentaDCPrevInput = input;

    magentaDCPrevOutput = output;

    return output;
}

// -------------------------------------------------------------
// YELLOW DC BLOCK
// -------------------------------------------------------------

private double dcBlockYellow(double input) {
    double output = input - yellowDCPrevInput + DC_POLE_2X * yellowDCPrevOutput;

    yellowDCPrevInput = input;

    yellowDCPrevOutput = output;

    return output;
}

// -------------------------------------------------------------
// BLACK DC BLOCK
// -------------------------------------------------------------

private double dcBlockBlack(double input) {
    double output = input - blackDCPrevInput + DC_POLE_2X * blackDCPrevOutput;

    blackDCPrevInput = input;

    blackDCPrevOutput = output;

    return output;
}

// =============================================================
// DSP STATE RESET
// =============================================================

private void resetDsp() {
    cyanInputUpsampler.reset();
    magentaInputUpsampler.reset();
    yellowInputUpsampler.reset();
    blackInputUpsampler.reset();

    cyanCvUpsampler.reset();
    magentaCvUpsampler.reset();
    yellowCvUpsampler.reset();
    blackCvUpsampler.reset();

    cyanOutputDownsampler.reset();
    magentaOutputDownsampler.reset();
    yellowOutputDownsampler.reset();
    blackOutputDownsampler.reset();

    cyanDCPrevInput = 0.0;
    cyanDCPrevOutput = 0.0;

    magentaDCPrevInput = 0.0;
    magentaDCPrevOutput = 0.0;

    yellowDCPrevInput = 0.0;
    yellowDCPrevOutput = 0.0;

    blackDCPrevInput = 0.0;
    blackDCPrevOutput = 0.0;
}
//[/user-code-and-variables]
}

 