package com.insectlabs.sw1;


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


public class sw1 extends VoltageModule
//[user-inheritance]
//[/user-inheritance]
{

    @SuppressWarnings("this-escape") 
    public sw1( long moduleID, VoltageObjects voltageObjects )
    {
        super( moduleID, voltageObjects, "Switch1", ModuleType.ModuleType_Utility, 0.8 );

        InitializeControls();


        canBeBypassed = true;
        SetSkin( "6f159f3f18154ab099af996ac699ae8b" );
    }

void InitializeControls()
{

        manufacturerLabel = new VoltageLabel( "manufacturerLabel", "manufacturerLabel", this, "insect" );
        AddComponent( manufacturerLabel );
        manufacturerLabel.SetWantsMouseNotifications( false );
        manufacturerLabel.SetPosition( 0, 335 );
        manufacturerLabel.SetSize( 57, 23 );
        manufacturerLabel.SetEditable( false, false );
        manufacturerLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        manufacturerLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        manufacturerLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        manufacturerLabel.SetBkColor( new Color( 51, 51, 51, 0 ) );
        manufacturerLabel.SetBorderColor( new Color( 85, 0, 0, 255 ) );
        manufacturerLabel.SetBorderSize( 4 );
        manufacturerLabel.SetMultiLineEdit( false );
        manufacturerLabel.SetIsNumberEditor( false );
        manufacturerLabel.SetNumberEditorRange( 0, 100 );
        manufacturerLabel.SetNumberEditorInterval( 1 );
        manufacturerLabel.SetNumberEditorUsesMouseWheel( false );
        manufacturerLabel.SetHasCustomTextHoverColor( false );
        manufacturerLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        manufacturerLabel.SetFont( "Courier New", 13, true, false );

        moduleTitleLabel = new VoltageLabel( "moduleTitleLabel", "moduleTitleLabel", this, "sw1" );
        AddComponent( moduleTitleLabel );
        moduleTitleLabel.SetWantsMouseNotifications( false );
        moduleTitleLabel.SetPosition( 3, 1 );
        moduleTitleLabel.SetSize( 46, 13 );
        moduleTitleLabel.SetEditable( false, false );
        moduleTitleLabel.SetJustificationFlags( VoltageLabel.Justification.Left );
        moduleTitleLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        moduleTitleLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        moduleTitleLabel.SetBkColor( new Color( 51, 51, 51, 0 ) );
        moduleTitleLabel.SetBorderColor( new Color( 51, 51, 51, 0 ) );
        moduleTitleLabel.SetBorderSize( 4 );
        moduleTitleLabel.SetMultiLineEdit( false );
        moduleTitleLabel.SetIsNumberEditor( false );
        moduleTitleLabel.SetNumberEditorRange( 0, 100 );
        moduleTitleLabel.SetNumberEditorInterval( 1 );
        moduleTitleLabel.SetNumberEditorUsesMouseWheel( false );
        moduleTitleLabel.SetHasCustomTextHoverColor( false );
        moduleTitleLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        moduleTitleLabel.SetFont( "Courier New", 13, true, false );

        outputBottom = new VoltageAudioJack( "outputBottom", "outputBottom", this, JackType.JackType_AudioOutput );
        AddComponent( outputBottom );
        outputBottom.SetWantsMouseNotifications( false );
        outputBottom.SetPosition( 8, 265 );
        outputBottom.SetSize( 37, 37 );
        outputBottom.SetSkin( "Rotated Half" );

        outputTop = new VoltageAudioJack( "outputTop", "outputTop", this, JackType.JackType_AudioOutput );
        AddComponent( outputTop );
        outputTop.SetWantsMouseNotifications( false );
        outputTop.SetPosition( 8, 230 );
        outputTop.SetSize( 37, 37 );
        outputTop.SetSkin( "Rotated Half" );

        inputBottom = new VoltageAudioJack( "inputBottom", "inputBottom", this, JackType.JackType_AudioInput );
        AddComponent( inputBottom );
        inputBottom.SetWantsMouseNotifications( false );
        inputBottom.SetPosition( 8, 165 );
        inputBottom.SetSize( 37, 37 );
        inputBottom.SetSkin( "Dark Jack Straight" );

        inputTop = new VoltageAudioJack( "inputTop", "inputTop", this, JackType.JackType_AudioInput );
        AddComponent( inputTop );
        inputTop.SetWantsMouseNotifications( false );
        inputTop.SetPosition( 8, 130 );
        inputTop.SetSize( 37, 37 );
        inputTop.SetSkin( "Dark Jack Straight" );

        relayButton = new VoltageButton( "relayButton", "relayButton", this );
        AddComponent( relayButton );
        relayButton.SetWantsMouseNotifications( false );
        relayButton.SetPosition( 8, 30 );
        relayButton.SetSize( 40, 40 );
        relayButton.SetSkin( "2500 Square Big On-On" );
        relayButton.ShowOverlay( false );
        relayButton.SetOverlayText( "" );
        relayButton.SetAutoRepeat( false );

        clickFilterSwitch = new VoltageSwitch( "clickFilterSwitch", "clickFilterSwitch", this, 1 );
        AddComponent( clickFilterSwitch );
        clickFilterSwitch.SetWantsMouseNotifications( false );
        clickFilterSwitch.SetPosition( 23, 307 );
        clickFilterSwitch.SetSize( 10, 15 );
        clickFilterSwitch.SetSkin( "2-State Slide Black" );

        inputNormalLed = new VoltageLED( "inputNormalLed", "inputNormalLed", this );
        AddComponent( inputNormalLed );
        inputNormalLed.SetWantsMouseNotifications( false );
        inputNormalLed.SetPosition( 4, 135 );
        inputNormalLed.SetSize( 8, 8 );
        inputNormalLed.SetSkin( "2500 Lamp Green" );

        inputAlternateLed = new VoltageLED( "inputAlternateLed", "inputAlternateLed", this );
        AddComponent( inputAlternateLed );
        inputAlternateLed.SetWantsMouseNotifications( false );
        inputAlternateLed.SetPosition( 4, 170 );
        inputAlternateLed.SetSize( 8, 8 );
        inputAlternateLed.SetSkin( "2500 Lamp Green" );

        outputNormalLed = new VoltageLED( "outputNormalLed", "outputNormalLed", this );
        AddComponent( outputNormalLed );
        outputNormalLed.SetWantsMouseNotifications( false );
        outputNormalLed.SetPosition( 43, 235 );
        outputNormalLed.SetSize( 8, 8 );
        outputNormalLed.SetSkin( "2500 Lamp Green" );

        outputAlternateLed = new VoltageLED( "outputAlternateLed", "outputAlternateLed", this );
        AddComponent( outputAlternateLed );
        outputAlternateLed.SetWantsMouseNotifications( false );
        outputAlternateLed.SetPosition( 43, 270 );
        outputAlternateLed.SetSize( 8, 8 );
        outputAlternateLed.SetSkin( "2500 Lamp Green" );

        modeKnob = new VoltageKnob( "modeKnob", "modeKnob", this, 0, 1, 1 );
        AddComponent( modeKnob );
        modeKnob.SetWantsMouseNotifications( false );
        modeKnob.SetPosition( 18, 85 );
        modeKnob.SetSize( 20, 20 );
        modeKnob.SetSkin( "Cosmo v2 Pointer" );
        modeKnob.SetRange( 0, 1, 1, false, 2 );
        modeKnob.SetKnobParams( 270, 90 );
        modeKnob.DisplayValueInPercent( false );
        modeKnob.SetKnobAdjustsRing( true );

        inputSectionLabel = new VoltageLabel( "inputSectionLabel", "inputSectionLabel", this, "I" );
        AddComponent( inputSectionLabel );
        inputSectionLabel.SetWantsMouseNotifications( false );
        inputSectionLabel.SetPosition( 17, 112 );
        inputSectionLabel.SetSize( 20, 20 );
        inputSectionLabel.SetEditable( false, false );
        inputSectionLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        inputSectionLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        inputSectionLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        inputSectionLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        inputSectionLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        inputSectionLabel.SetBorderSize( 1 );
        inputSectionLabel.SetMultiLineEdit( false );
        inputSectionLabel.SetIsNumberEditor( false );
        inputSectionLabel.SetNumberEditorRange( 0, 100 );
        inputSectionLabel.SetNumberEditorInterval( 1 );
        inputSectionLabel.SetNumberEditorUsesMouseWheel( false );
        inputSectionLabel.SetHasCustomTextHoverColor( false );
        inputSectionLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        inputSectionLabel.SetFont( "Arial Black", 13, true, false );

        toggleModeLabel = new VoltageLabel( "toggleModeLabel", "toggleModeLabel", this, "T" );
        AddComponent( toggleModeLabel );
        toggleModeLabel.SetWantsMouseNotifications( false );
        toggleModeLabel.SetPosition( 6, 91 );
        toggleModeLabel.SetSize( 8, 8 );
        toggleModeLabel.SetEditable( false, false );
        toggleModeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        toggleModeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        toggleModeLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        toggleModeLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        toggleModeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        toggleModeLabel.SetBorderSize( 1 );
        toggleModeLabel.SetMultiLineEdit( false );
        toggleModeLabel.SetIsNumberEditor( false );
        toggleModeLabel.SetNumberEditorRange( 0, 100 );
        toggleModeLabel.SetNumberEditorInterval( 1 );
        toggleModeLabel.SetNumberEditorUsesMouseWheel( false );
        toggleModeLabel.SetHasCustomTextHoverColor( false );
        toggleModeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        toggleModeLabel.SetFont( "Arial", 10, true, false );

        gateModeLabel = new VoltageLabel( "gateModeLabel", "gateModeLabel", this, "G" );
        AddComponent( gateModeLabel );
        gateModeLabel.SetWantsMouseNotifications( false );
        gateModeLabel.SetPosition( 41, 91 );
        gateModeLabel.SetSize( 8, 8 );
        gateModeLabel.SetEditable( false, false );
        gateModeLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        gateModeLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        gateModeLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        gateModeLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        gateModeLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        gateModeLabel.SetBorderSize( 1 );
        gateModeLabel.SetMultiLineEdit( false );
        gateModeLabel.SetIsNumberEditor( false );
        gateModeLabel.SetNumberEditorRange( 0, 100 );
        gateModeLabel.SetNumberEditorInterval( 1 );
        gateModeLabel.SetNumberEditorUsesMouseWheel( false );
        gateModeLabel.SetHasCustomTextHoverColor( false );
        gateModeLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        gateModeLabel.SetFont( "Arial", 10, true, false );

        clickFilterLabel = new VoltageLabel( "clickFilterLabel", "clickFilterLabel", this, "CLK" );
        AddComponent( clickFilterLabel );
        clickFilterLabel.SetWantsMouseNotifications( false );
        clickFilterLabel.SetPosition( 0, 323 );
        clickFilterLabel.SetSize( 57, 8 );
        clickFilterLabel.SetEditable( false, false );
        clickFilterLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        clickFilterLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        clickFilterLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        clickFilterLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        clickFilterLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        clickFilterLabel.SetBorderSize( 1 );
        clickFilterLabel.SetMultiLineEdit( false );
        clickFilterLabel.SetIsNumberEditor( false );
        clickFilterLabel.SetNumberEditorRange( 0, 100 );
        clickFilterLabel.SetNumberEditorInterval( 1 );
        clickFilterLabel.SetNumberEditorUsesMouseWheel( false );
        clickFilterLabel.SetHasCustomTextHoverColor( false );
        clickFilterLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        clickFilterLabel.SetFont( "Arial", 6, true, false );

        outputSectionLabel = new VoltageLabel( "outputSectionLabel", "outputSectionLabel", this, "0" );
        AddComponent( outputSectionLabel );
        outputSectionLabel.SetWantsMouseNotifications( false );
        outputSectionLabel.SetPosition( 17, 209 );
        outputSectionLabel.SetSize( 20, 20 );
        outputSectionLabel.SetEditable( false, false );
        outputSectionLabel.SetJustificationFlags( VoltageLabel.Justification.HorizCentered );
        outputSectionLabel.SetJustificationFlags( VoltageLabel.Justification.VertCentered );
        outputSectionLabel.SetColor( new Color( 232, 232, 232, 255 ) );
        outputSectionLabel.SetBkColor( new Color( 65, 65, 65, 0 ) );
        outputSectionLabel.SetBorderColor( new Color( 0, 0, 0, 0 ) );
        outputSectionLabel.SetBorderSize( 1 );
        outputSectionLabel.SetMultiLineEdit( false );
        outputSectionLabel.SetIsNumberEditor( false );
        outputSectionLabel.SetNumberEditorRange( 0, 100 );
        outputSectionLabel.SetNumberEditorInterval( 1 );
        outputSectionLabel.SetNumberEditorUsesMouseWheel( false );
        outputSectionLabel.SetHasCustomTextHoverColor( false );
        outputSectionLabel.SetTextHoverColor( new Color( 0, 0, 0, 255 ) );
        outputSectionLabel.SetFont( "Arial", 13, true, false );
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
        switchCore = new Switch1Core(SAMPLE_RATE);
        updateIndicators();



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
        switch( notification )
        {
            case Knob_Changed:   // doubleValue is the new VoltageKnob value
            {
            if (component == modeKnob) {
                switchCore.setGateMode(isGateMode());
                updateIndicators();
            }
            }
            break;
        
            case Slider_Changed:   // doubleValue is the new slider value
            {
            }
            break;
        
            case Button_Changed:   // doubleValue is the new button/toggle button value
            {
            if (component == relayButton) {
                switchCore.setButtonPressed(doubleValue > 0.5, isGateMode());
                updateIndicators();
            }
            }
            break;
        
            case Switch_Changed:   // doubleValue is the new switch value
            {
            }
            break;
        
            case Jack_Connected:   // longValue is the new cable ID
            {
            updateIndicators();
            }
            break;
        
            case Jack_Disconnected:   // All cables have been disconnected from this jack
            {
            updateIndicators();
            }
            break;
        
            case GUI_Update_Timer:   // Called every 50ms (by default) if turned on
            {
            }
            break;
        
            case Object_MouseMove:   // called when mouse is over an object that receives mouse notifications. 'object' parameter is a VoltageMouseKeyFlags object.
            {
            }
            break;
        
            case Object_MouseLeave:  // called when mouse leaves an object that receives mouse notifications. 'object' parameter is a VoltageMouseKeyFlags object.
            {
            }
            break;
        
            case Object_LeftButtonDown:   // called when user left-clicks on an object that receives mouse notifications. 'object' parameter is a VoltageMouseKeyFlags object.
            {
            }
            break;
        
            case Object_LeftButtonUp:   // called when user releases left mouse button on an object that receives mouse notifications. 'object' parameter is a VoltageMouseKeyFlags object.
            {
            }
            break;
        
            case Object_RightButtonDown:   // called when user releases right mouse button on an object that receives mouse notifications. 'object' parameter is a VoltageMouseKeyFlags object.
            {
            }
            break;
        
            case Object_RightButtonUp:   // called when user right-clicks on an object that receives mouse notifications
            {
            }
            break;
        
            case Object_LeftButtonDoubleClick: // called when user left-button double-clicks on an object that receives mouse notifications
            {
            }
            break;
        
            // Less common notifications:
        
            case Named_Timer:   // object contains a String with the name of the timer that has fired
            {
            }
            break;
        
            case Canvas_Painting:   // About to paint canvas.  object is a java.awt.Rectangle with painting boundaries
            {
            }
            break;
        
            case Canvas_Painted:   // Canvas painting is complete
            {
            }
            break;
        
            case Control_DragStart:    // A user has started dragging on a control that has been marked as draggable
            {
            }
            break;
        
            case Control_DragOn:       // This control has been dragged over during a drag operation. object contains the dragged object
            {
            }
            break;
        
            case Control_DragOff:      // This control has been dragged over during a drag operation. object contains the dragged object
            {
            }
            break;
        
            case Control_DragEnd:      // A user has ended their drag on a control that has been marked as draggable
            {
            }
            break;
        
            case Label_Changed:        // The text of an editable text control has changed
            {
            }
            break;
        
            case SoundPlayback_Start:   // A sound has begun playback
            {
            }
            break;
        
            case SoundPlayback_End:     // A sound has ended playback
            {
            }
            break;
        
            case Scrollbar_Position:    // longValue is the new scrollbar position
            {
            }
            break;
        
            case PolyVoices_Changed:    // longValue is the new number of poly voices
            {
            }
            break;
        
            case File_Dropped:     // 'object' is a String containing the file path
            {
            }
            break;
        
            case Preset_Loading_Start:   // called when preset loading begins
            {
            }
            break;
        
            case Preset_Loading_Finish:  // called when preset loading finishes
            {
            }
            break;
        
            case Variation_Loading_Start:    // sent when a variation is about to load
            {
            }
            break;
        
            case Variation_Loading_Finish:   // sent when a variation has just finished loading
            {
            }
            break;
        
            case Tempo_Changed:     // doubleValue is the new tempo
            {
            }
            break;
        
            case Randomized:     // called when the module's controls get randomized
            {
            }
            break;
        
            case VariationListChanged:   // sent when a variation gets added, deleted, or renamed, or the variations list gets reordered
            {
            }
            break;
        
            case Key_Press:     // sent when module has keyboard focus and a key is pressed; object is a VoltageKeyPressInfo object
            {
            }
            break;
        
            case Reset:    // sent when the module has been reset to default settings
            {
            switchCore.reset();
            updateIndicators();
            }
            break;
        
            case Keyboard_NoteOn:   // sent when a note has been pressed on a VoltageKeyboard object. longValue is the note value ( 0-127 )
            {
            }
            break;
        
            case Keyboard_NoteOff:   // sent when a note has been released on a VoltageKeyboard object. longValue is the note value ( 0-127 )
            {
            }
            break;
        
            case Curve_Changed:   // sent when user has edited a curve's value. 'object' will be a VoltageCurve.CurveChangeNotification object.
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
        switchCore.process(
                readInput(inputTop), inputTop.IsConnected(),
                readInput(inputBottom), inputBottom.IsConnected(),
                clickFilterSwitch.GetValue() < 0.5);
        outputTop.SetValue(switchCore.getOutputTop());
        outputBottom.SetValue(switchCore.getOutputBottom());
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
        switchCore.processBypassed(readInput(inputTop), readInput(inputBottom));
        outputTop.SetValue(switchCore.getOutputTop());
        outputBottom.SetValue(switchCore.getOutputBottom());
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
        if (component == relayButton) return isGateMode()
                ? "RELAY: hold for alternate routing; release returns to normal"
                : "RELAY: press to toggle normal/alternate routing";
        if (component == modeKnob) return isGateMode()
                ? "MODE: GATE (alternate route while the relay button is held)"
                : "MODE: TOGGLE (each relay-button press changes route)";
        if (component == clickFilterSwitch) return clickFilterSwitch.GetValue() < 0.5
                ? "CLICK FILTER: CP3-inspired output conditioning enabled"
                : "CLICK FILTER: direct relay output";
        if (component == inputTop) return "IN 1: normal source for OUT 1";
        if (component == inputBottom) return "IN 2: normal source for OUT 2";
        if (component == outputTop) return "OUT 1: IN 1 normal, IN 2 alternate";
        if (component == outputBottom) return "OUT 2: IN 2 normal, IN 1 alternate";
        return super.GetTooltipText( component );
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
        return new byte[] { (byte) (switchCore.isAlternate() ? 1 : 0) };
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
        boolean restoredAlternate = stateInfo != null && stateInfo.length > 0 && stateInfo[0] != 0;
        switchCore.restore(restoredAlternate, isGateMode());
        updateIndicators();
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
    private VoltageLabel outputSectionLabel;
    private VoltageLabel clickFilterLabel;
    private VoltageLabel gateModeLabel;
    private VoltageLabel toggleModeLabel;
    private VoltageLabel inputSectionLabel;
    private VoltageKnob modeKnob;
    private VoltageLED outputAlternateLed;
    private VoltageLED outputNormalLed;
    private VoltageLED inputAlternateLed;
    private VoltageLED inputNormalLed;
    private VoltageSwitch clickFilterSwitch;
    private VoltageButton relayButton;
    private VoltageAudioJack inputTop;
    private VoltageAudioJack inputBottom;
    private VoltageAudioJack outputTop;
    private VoltageAudioJack outputBottom;
    private VoltageLabel moduleTitleLabel;
    private VoltageLabel manufacturerLabel;


    //[user-code-and-variables]    Add your own variables and functions here
private static final double SAMPLE_RATE = 48000.0;
private Switch1Core switchCore;

private static double readInput(VoltageAudioJack jack) {
    return jack.IsConnected() ? jack.GetValue() : 0.0;
}

private boolean isGateMode() {
    return modeKnob.GetValue() >= 0.5;
}

private void updateIndicators() {
    boolean alternate = switchCore != null && switchCore.isAlternate();
    inputNormalLed.SetValue(alternate ? 0.0 : 1.0);
    outputNormalLed.SetValue(alternate ? 0.0 : 1.0);
    inputAlternateLed.SetValue(alternate ? 1.0 : 0.0);
    outputAlternateLed.SetValue(alternate ? 1.0 : 0.0);
}

/** Native-rate manual 2x2 relay router with optional, light contact smoothing. */
private static final class Switch1Core {
    private static final double GATE_VOLTS = 5.0;
    // Gentle 6 dB/octave contact conditioning: attenuates relay-edge hash without dulling audio.
    private static final double CLICK_FILTER_CUTOFF_HZ = 1800.0;
    // A short, fixed relay settling time removes the route-change discontinuity.
    private static final int RELAY_SETTLE_SAMPLES = 96;

    private final OnePole topOutputFilter;
    private final OnePole bottomOutputFilter;
    private final RelayContact topRelayContact;
    private final RelayContact bottomRelayContact;
    private boolean alternate;
    private boolean buttonPressed;
    private double topOutput;
    private double bottomOutput;

    private Switch1Core(double sampleRate) {
        topOutputFilter = new OnePole(CLICK_FILTER_CUTOFF_HZ, sampleRate);
        bottomOutputFilter = new OnePole(CLICK_FILTER_CUTOFF_HZ, sampleRate);
        topRelayContact = new RelayContact(RELAY_SETTLE_SAMPLES);
        bottomRelayContact = new RelayContact(RELAY_SETTLE_SAMPLES);
    }

    private void setButtonPressed(boolean pressed, boolean gateMode) {
        if (gateMode) {
            alternate = pressed;
        } else if (pressed && !buttonPressed) {
            alternate = !alternate;
        }
        buttonPressed = pressed;
    }

    private void setGateMode(boolean gateMode) {
        if (gateMode) alternate = buttonPressed;
    }

    private void reset() {
        alternate = false;
        buttonPressed = false;
        topOutputFilter.reset();
        bottomOutputFilter.reset();
        topRelayContact.reset();
        bottomRelayContact.reset();
    }

    private void restore(boolean restoredAlternate, boolean gateMode) {
        alternate = gateMode ? false : restoredAlternate;
        buttonPressed = false;
        topOutputFilter.reset();
        bottomOutputFilter.reset();
        topRelayContact.reset();
        bottomRelayContact.reset();
    }

    private boolean isAlternate() {
        return alternate;
    }

    private void process(
            double topInput, boolean topInputConnected,
            double bottomInput, boolean bottomInputConnected,
            boolean clickFilterEnabled) {
        double normalTopOutput = topInput;
        double alternateTopOutput = bottomInput;
        double normalBottomOutput = bottomInput;
        double alternateBottomOutput = topInput;
        if (!topInputConnected && !bottomInputConnected) {
            normalTopOutput = GATE_VOLTS;
            alternateTopOutput = 0.0;
            normalBottomOutput = 0.0;
            alternateBottomOutput = GATE_VOLTS;
        }
        double settledTopOutput = topRelayContact.process(
                normalTopOutput, alternateTopOutput, alternate, clickFilterEnabled);
        double settledBottomOutput = bottomRelayContact.process(
                normalBottomOutput, alternateBottomOutput, alternate, clickFilterEnabled);
        topOutput = topOutputFilter.process(settledTopOutput, clickFilterEnabled);
        bottomOutput = bottomOutputFilter.process(settledBottomOutput, clickFilterEnabled);
    }

    private void processBypassed(double topInput, double bottomInput) {
        topOutput = topInput;
        bottomOutput = bottomInput;
        topOutputFilter.reset();
        bottomOutputFilter.reset();
        topRelayContact.reset();
        bottomRelayContact.reset();
    }

    private double getOutputTop() {
        return topOutput;
    }

    private double getOutputBottom() {
        return bottomOutput;
    }

    private static final class OnePole {
        private final double coefficient;
        private double state;
        private boolean ready;

        private OnePole(double cutoff, double sampleRate) {
            coefficient = 1 - Math.exp(-2 * Math.PI * cutoff / sampleRate);
        }

        private double process(double value, boolean enabled) {
            if (!enabled) {
                state = value;
                ready = true;
                return value;
            }
            if (!ready) {
                state = value;
                ready = true;
            } else {
                state += coefficient * (value - state);
            }
            return state;
        }

        private void reset() {
            state = 0;
            ready = false;
        }
    }

    /** Crossfades only for the brief mechanical relay transition while CLK is engaged. */
    private static final class RelayContact {
        private final int settleSamples;
        private boolean activeAlternate;
        private int remainingSamples;
        private boolean ready;

        private RelayContact(int settleSamples) {
            this.settleSamples = settleSamples;
        }

        private double process(double normalValue, double alternateValue,
                boolean requestedAlternate, boolean enabled) {
            if (!ready || !enabled) {
                activeAlternate = requestedAlternate;
                remainingSamples = 0;
                ready = true;
                return requestedAlternate ? alternateValue : normalValue;
            }
            if (requestedAlternate != activeAlternate && remainingSamples == 0) {
                remainingSamples = settleSamples;
            }
            if (remainingSamples == 0) {
                return requestedAlternate ? alternateValue : normalValue;
            }
            double oldValue = activeAlternate ? alternateValue : normalValue;
            double newValue = requestedAlternate ? alternateValue : normalValue;
            double mix = (double) (settleSamples - remainingSamples + 1) / settleSamples;
            if (--remainingSamples == 0) activeAlternate = requestedAlternate;
            return oldValue + (newValue - oldValue) * mix;
        }

        private void reset() {
            activeAlternate = false;
            remainingSamples = 0;
            ready = false;
        }
    }
}
    //[/user-code-and-variables]
}

 